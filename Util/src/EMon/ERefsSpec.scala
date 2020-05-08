package ostrat
import annotation.unchecked.uncheckedVariance

sealed trait ERefsSpec[+A] extends EMonBase[Arr[A]]
{ def baseMap[B, BB <: EMonBase[B]](f: Arr[A] => B)(implicit build: EMonBuild[B, BB]): BB
  def toUnspecialised: EMon[Arr[A]] = foldErrs[EMon[Arr[A]]](Good(_))(Bad(_))
  def flatMapRefs[B <: AnyRef](f: Arr[A] => ERefsSpec[B]): ERefsSpec[B]
}

final case class GoodRefsSpec[+A](value: Arr[A]) extends ERefsSpec[A] with GoodBase[Arr[A]]
{ override def baseMap[B, BB <: EMonBase[B]](f: Arr[A] => B)(implicit build: EMonBuild[B, BB]): BB = build(f(value))
  override def baseFlatMap[B, BB <: EMonBase[B]](f: Arr[A] => BB)(implicit build: EMonBuild[B, BB]): BB = f(value)
  override def forGood(f: Arr[A] => Unit): Unit = f(value)
  override def fold[B](noneValue: => B)(fGood: Arr[A] => B): B = fGood(value)
  override def fld[B](noneValue: => B, fGood: Arr[A] => B): B = fGood(value)
  @inline override def foldErrs[B](fGood: Arr[A] => B)(fBad: Strings => B): B = fGood(value)
  override def get: Arr[A] = value
  override def foldDo(fGood: Arr[A] => Unit)(fBad: Strings => Unit): Unit = fGood(value)
  override def getElse(elseValue: Arr[A] @uncheckedVariance): Arr[A] = value
  override def flatMapRefs[B <: AnyRef](f: Arr[A] => ERefsSpec[B]): ERefsSpec[B] = f(value)
}
case class BadRefsSpec[+A](errs: Arr[String]) extends ERefsSpec[A] with BadBase[Arr[A]]
{ override def baseMap[B, BB <: EMonBase[B]](f: Arr[A] => B)(implicit build: EMonBuild[B, BB]): BB = build.newBad(errs)
  override def baseFlatMap[B, BB <: EMonBase[B]](f: Arr[A] => BB)(implicit build: EMonBuild[B, BB]): BB = build.newBad(errs)
  override def fold[B](noneValue: => B)(fGood: Arr[A] => B): B = noneValue
  override def fld[B](noneValue: => B, fGood: Arr[A] => B): B = noneValue
  @inline override def foldErrs[B](fGood: Arr[A] => B)(fBad: Strings => B): B = fBad(errs)
  override def getElse(elseValue: Arr[A] @uncheckedVariance): Arr[A] = elseValue
  override def flatMapRefs[B <: AnyRef](f: Arr[A] => ERefsSpec[B]): ERefsSpec[B] = BadRefsSpec[B](errs)
}