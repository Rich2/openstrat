package ostrat

sealed trait EMonDbl extends EMonBase[Double]
{ def baseMap[B, BB <: EMonBase[B]](f: Double => B)(implicit build: EMonBuild[B, BB]): BB
}

final case class GoodDbl(value: Double) extends EMonDbl with GoodBase[Double]
{ override def baseMap[B, BB <: EMonBase[B]](f: Double => B)(implicit build: EMonBuild[B, BB]): BB = build(f(value))
  override def baseFlatMap[B, BB <: EMonBase[B]](f: Double => BB)(implicit build: EMonBuild[B, BB]): BB = f(value)
  override def forGood(f: Double => Unit): Unit = f(value)
  override def fold[B](noneValue: => B)(fGood: Double => B): B = fGood(value)
  override def fld[B](noneValue: => B, fGood: Double => B): B = fGood(value)
  @inline override def foldErrs[B](fGood: Double => B)(fBad: Strings => B): B = fGood(value)
  override def get: Double = value
  override def foldDo(fGood: Double => Unit)(fBad: Strings => Unit): Unit = fGood(value)
  override def getElse(elseValue: => Double): Double = value
}
case class BadDbl(errs: Refs[String]) extends EMonDbl with BadBase[Double]
{ override def baseMap[B, BB <: EMonBase[B]](f: Double => B)(implicit build: EMonBuild[B, BB]): BB = build.newBad(errs)
  override def baseFlatMap[B, BB <: EMonBase[B]](f: Double => BB)(implicit build: EMonBuild[B, BB]): BB = build.newBad(errs)
  override def fold[B](noneValue: => B)(fGood: Double => B): B = noneValue
  override def fld[B](noneValue: => B, fGood: Double => B): B = noneValue
  @inline override def foldErrs[B](fGood: Double => B)(fBad: Strings => B): B = fBad(errs)
  override def getElse(elseValue: => Double): Double = elseValue
}

object NoDbl extends BadDbl(Refs()) //with EMonDbl with NoBase[Double]
