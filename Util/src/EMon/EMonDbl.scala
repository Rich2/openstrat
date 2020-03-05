package ostrat

sealed trait EMonDbl extends EMonBase[Double]
{ def map[B, BB <: EMonBase[B]](f: Double => B)(implicit build: EMonBuild[B, BB]): BB
}

final case class GoodDbl(value: Double) extends EMonDbl with GoodBase[Double]
{ override def map[B, BB <: EMonBase[B]](f: Double => B)(implicit build: EMonBuild[B, BB]): BB = build(f(value))
  override def flatMap[B, BB <: EMonBase[B]](f: Double => BB)(implicit build: EMonBuild[B, BB]): BB = f(value)
  override def forGood(f: Double => Unit): Unit = f(value)
  override def fold[B](noneValue: => B)(fGood: Double => B): B = fGood(value)
  @inline override def foldErrs[B](fGood: Double => B)(fBad: Strings => B): B = fGood(value)
  override def get: Double = value
  override def foldDo(fGood: Double => Unit)(fBad: Strings => Unit): Unit = fGood(value)
  override def getElse(elseValue: => Double): Double = value
}
case class BadDbl(errs: Refs[String]) extends EMonDbl with BadBase[Double]
{ override def map[B, BB <: EMonBase[B]](f: Double => B)(implicit build: EMonBuild[B, BB]): BB = build.newBad(errs)
  override def flatMap[B, BB <: EMonBase[B]](f: Double => BB)(implicit build: EMonBuild[B, BB]): BB = build.newBad(errs)
  override def fold[B](noneValue: => B)(fGood: Double => B): B = noneValue
  @inline override def foldErrs[B](fGood: Double => B)(fBad: Strings => B): B = fBad(errs)
  override def getElse(elseValue: => Double): Double = elseValue
}

object NoDbl extends BadDbl(Refs()) //with EMonDbl with NoBase[Double]

trait EMonBool extends EMonBase[Boolean]
{ def map[B, BB <: EMonBase[B]](f: Boolean => B)(implicit build: EMonBuild[B, BB]): BB
}

case class GoodBool(value: Boolean) extends EMonBool with GoodBase[Boolean]
{ override def map[B, BB <: EMonBase[B]](f: Boolean => B)(implicit build: EMonBuild[B, BB]): BB = build(f(value))
  override def flatMap[B, BB <: EMonBase[B]](f: Boolean => BB)(implicit build: EMonBuild[B, BB]): BB = f(value)
  override def forGood(f: Boolean => Unit): Unit = f(value)
  override def fold[B](noneValue: => B)(fGood: Boolean => B): B = fGood(value)
  @inline override def foldErrs[B](fGood: Boolean => B)(fBad: Strings => B): B = fGood(value)
  override def get: Boolean = value
  override def foldDo(fGood: Boolean => Unit)(fBad: Strings => Unit): Unit = fGood(value)
  override def getElse(elseValue: => Boolean): Boolean = value
}
case class BadBool(errs: Refs[String]) extends EMonBool with BadBase[Boolean]
{ override def map[B, BB <: EMonBase[B]](f: Boolean => B)(implicit build: EMonBuild[B, BB]): BB = build.newBad(errs)
  override def flatMap[B, BB <: EMonBase[B]](f: Boolean => BB)(implicit build: EMonBuild[B, BB]): BB = build.newBad(errs)
  override def fold[B](noneValue: => B)(fGood: Boolean => B): B = noneValue
  @inline override def foldErrs[B](fGood: Boolean => B)(fBad: Strings => B): B = fBad(errs)
  override def getElse(elseValue: => Boolean): Boolean = elseValue
}

object NoBool extends BadBool(Refs()) //with EMonBool with NoBase[Boolean]

