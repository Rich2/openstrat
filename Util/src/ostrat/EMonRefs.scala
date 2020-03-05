package ostrat

sealed trait EMonRefs[+A <: AnyRef] extends EMonBase[Refs[A]]
{ def map[B, BB <: EMonBase[B]](f: Refs[A] => B)(implicit build: EMonBuild[B, BB]): BB
  @deprecated def toOld: EMon[Refs[A]] = foldErrs[EMon[Refs[A]]](Good(_))(Bad(_))
}

final case class GoodRefs[+A <: AnyRef](value: Refs[A]) extends EMonRefs[A] with GoodBase[Refs[A]]
{ override def map[B, BB <: EMonBase[B]](f: Refs[A] => B)(implicit build: EMonBuild[B, BB]): BB = build(f(value))
  override def flatMap[B, BB <: EMonBase[B]](f: Refs[A] => BB)(implicit build: EMonBuild[B, BB]): BB = f(value)
  override def forGood(f: Refs[A] => Unit): Unit = f(value)
  override def fold[B](noneValue: => B)(fGood: Refs[A] => B): B = fGood(value)
  @inline override def foldErrs[B](fGood: Refs[A] => B)(fBad: Strings => B): B = fGood(value)
  override def get: Refs[A] = value
  override def foldDo(fGood: Refs[A] => Unit)(fBad: Strings => Unit): Unit = fGood(value)
}
case class BadRefs[+A <: AnyRef](errs: Refs[String]) extends EMonRefs[A] with BadBase[Refs[A]]
{ override def map[B, BB <: EMonBase[B]](f: Refs[A] => B)(implicit build: EMonBuild[B, BB]): BB = build.newBad(errs)
  override def flatMap[B, BB <: EMonBase[B]](f: Refs[A] => BB)(implicit build: EMonBuild[B, BB]): BB = build.newBad(errs)
  override def fold[B](noneValue: => B)(fGood: Refs[A] => B): B = noneValue
  @inline override def foldErrs[B](fGood: Refs[A] => B)(fBad: Strings => B): B = fBad(errs)
}

object NoRefs extends BadRefs[Nothing](Refs())