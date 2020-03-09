package ostrat
import annotation.unchecked.uncheckedVariance

sealed trait ERefsSpec[+A <: AnyRef] extends EMonBase[Refs[A]]
{ def baseMap[B, BB <: EMonBase[B]](f: Refs[A] => B)(implicit build: EMonBuild[B, BB]): BB
  @deprecated def toOld: EMon[Refs[A]] = foldErrs[EMon[Refs[A]]](Good(_))(Bad(_))
  def flatMapRefs[B <: AnyRef](f: Refs[A] => ERefsSpec[B]): ERefsSpec[B]
}

final case class GoodRefsSpec[+A <: AnyRef](value: Refs[A]) extends ERefsSpec[A] with GoodBase[Refs[A]]
{ override def baseMap[B, BB <: EMonBase[B]](f: Refs[A] => B)(implicit build: EMonBuild[B, BB]): BB = build(f(value))
  override def baseFlatMap[B, BB <: EMonBase[B]](f: Refs[A] => BB)(implicit build: EMonBuild[B, BB]): BB = f(value)
  override def forGood(f: Refs[A] => Unit): Unit = f(value)
  override def fold[B](noneValue: => B)(fGood: Refs[A] => B): B = fGood(value)
  override def fld[B](noneValue: => B, fGood: Refs[A] => B): B = fGood(value)
  @inline override def foldErrs[B](fGood: Refs[A] => B)(fBad: Strings => B): B = fGood(value)
  override def get: Refs[A] = value
  override def foldDo(fGood: Refs[A] => Unit)(fBad: Strings => Unit): Unit = fGood(value)
  override def getElse(elseValue: => Refs[A] @uncheckedVariance): Refs[A] = value
  override def flatMapRefs[B <: AnyRef](f: Refs[A] => ERefsSpec[B]): ERefsSpec[B] = f(value)
}
case class BadRefsSpec[+A <: AnyRef](errs: Refs[String]) extends ERefsSpec[A] with BadBase[Refs[A]]
{ override def baseMap[B, BB <: EMonBase[B]](f: Refs[A] => B)(implicit build: EMonBuild[B, BB]): BB = build.newBad(errs)
  override def baseFlatMap[B, BB <: EMonBase[B]](f: Refs[A] => BB)(implicit build: EMonBuild[B, BB]): BB = build.newBad(errs)
  override def fold[B](noneValue: => B)(fGood: Refs[A] => B): B = noneValue
  override def fld[B](noneValue: => B, fGood: Refs[A] => B): B = noneValue
  @inline override def foldErrs[B](fGood: Refs[A] => B)(fBad: Strings => B): B = fBad(errs)
  override def getElse(elseValue: => Refs[A] @uncheckedVariance): Refs[A] = elseValue
  override def flatMapRefs[B <: AnyRef](f: Refs[A] => ERefsSpec[B]): ERefsSpec[B] = BadRefsSpec[B](errs)
}