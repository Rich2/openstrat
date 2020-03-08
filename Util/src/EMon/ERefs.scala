package ostrat
import annotation.unchecked.uncheckedVariance

sealed trait ERefs[+A <: AnyRef] extends EMonBase[Refs[A]]
{ def baseMap[B, BB <: EMonBase[B]](f: Refs[A] => B)(implicit build: EMonBuild[B, BB]): BB
  @deprecated def toOld: EMon[Refs[A]] = foldErrs[EMon[Refs[A]]](Good(_))(Bad(_))
  def flatMapRefs[B <: AnyRef](f: Refs[A] => ERefs[B]): ERefs[B]
}

final case class GoodRefs[+A <: AnyRef](value: Refs[A]) extends ERefs[A] with GoodBase[Refs[A]]
{ override def baseMap[B, BB <: EMonBase[B]](f: Refs[A] => B)(implicit build: EMonBuild[B, BB]): BB = build(f(value))
  override def baseFlatMap[B, BB <: EMonBase[B]](f: Refs[A] => BB)(implicit build: EMonBuild[B, BB]): BB = f(value)
  override def forGood(f: Refs[A] => Unit): Unit = f(value)
  override def fold[B](noneValue: => B)(fGood: Refs[A] => B): B = fGood(value)
  override def fld[B](noneValue: => B, fGood: Refs[A] => B): B = fGood(value)
  @inline override def foldErrs[B](fGood: Refs[A] => B)(fBad: Strings => B): B = fGood(value)
  override def get: Refs[A] = value
  override def foldDo(fGood: Refs[A] => Unit)(fBad: Strings => Unit): Unit = fGood(value)
  override def getElse(elseValue: => Refs[A] @uncheckedVariance): Refs[A] = value
  override def flatMapRefs[B <: AnyRef](f: Refs[A] => ERefs[B]): ERefs[B] = f(value)
  /*override def goodMap[B, BB <: ArrImut[B]](f: A => B)(implicit build: ArrBuild[B, BB]): BB =
  { val res = build.imutNew(value.length)
    var count = 0

    while (count < value.length)
    {
      build.imutSet(res, count, f(value(count)))
      count += 1
    }
    res
  }*/
}
case class BadRefs[+A <: AnyRef](errs: Refs[String]) extends ERefs[A] with BadBase[Refs[A]]
{ override def baseMap[B, BB <: EMonBase[B]](f: Refs[A] => B)(implicit build: EMonBuild[B, BB]): BB = build.newBad(errs)
  override def baseFlatMap[B, BB <: EMonBase[B]](f: Refs[A] => BB)(implicit build: EMonBuild[B, BB]): BB = build.newBad(errs)
  override def fold[B](noneValue: => B)(fGood: Refs[A] => B): B = noneValue
  override def fld[B](noneValue: => B, fGood: Refs[A] => B): B = noneValue
  @inline override def foldErrs[B](fGood: Refs[A] => B)(fBad: Strings => B): B = fBad(errs)
  override def getElse(elseValue: => Refs[A] @uncheckedVariance): Refs[A] = elseValue
  override def flatMapRefs[B <: AnyRef](f: Refs[A] => ERefs[B]): ERefs[B] = BadRefs[B](errs)
}

object NoRefs extends BadRefs[Nothing](Refs())