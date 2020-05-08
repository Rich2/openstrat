package ostrat

/*
trait EMonBool extends EMonBase[Boolean]
{ def baseMap[B, BB <: EMonBase[B]](f: Boolean => B)(implicit build: EMonBuild[B, BB]): BB
}

case class GoodBool(value: Boolean) extends EMonBool with GoodBase[Boolean]
{ override def baseMap[B, BB <: EMonBase[B]](f: Boolean => B)(implicit build: EMonBuild[B, BB]): BB = build(f(value))
  override def baseFlatMap[B, BB <: EMonBase[B]](f: Boolean => BB)(implicit build: EMonBuild[B, BB]): BB = f(value)
  override def forGood(f: Boolean => Unit): Unit = f(value)
  override def fold[B](noneValue: => B)(fGood: Boolean => B): B = fGood(value)
  override def fld[B](noneValue: => B, fGood: Boolean => B): B = fGood(value)
  @inline override def foldErrs[B](fGood: Boolean => B)(fBad: Strings => B): B = fGood(value)
  override def get: Boolean = value
  override def foldDo(fGood: Boolean => Unit)(fBad: Strings => Unit): Unit = fGood(value)
  override def getElse(elseValue: Boolean): Boolean = value
}
case class BadBool(errs: Arr[String]) extends EMonBool with BadBase[Boolean]
{ override def baseMap[B, BB <: EMonBase[B]](f: Boolean => B)(implicit build: EMonBuild[B, BB]): BB = build.newBad(errs)
  override def baseFlatMap[B, BB <: EMonBase[B]](f: Boolean => BB)(implicit build: EMonBuild[B, BB]): BB = build.newBad(errs)
  override def fold[B](noneValue: => B)(fGood: Boolean => B): B = noneValue
  override def fld[B](noneValue: => B, fGood: Boolean => B): B = noneValue
  @inline override def foldErrs[B](fGood: Boolean => B)(fBad: Strings => B): B = fBad(errs)
  override def getElse(elseValue: Boolean): Boolean = elseValue
}*/
