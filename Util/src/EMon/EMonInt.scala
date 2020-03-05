package ostrat

sealed trait EMonInt extends EMonBase[Int]
{ def map[B, BB <: EMonBase[B]](f: Int => B)(implicit build: EMonBuild[B, BB]): BB
}

final case class GoodInt(value: Int) extends EMonInt with GoodBase[Int]
{ override def map[B, BB <: EMonBase[B]](f: Int => B)(implicit build: EMonBuild[B, BB]): BB = build(f(value))
  override def flatMap[B, BB <: EMonBase[B]](f: Int => BB)(implicit build: EMonBuild[B, BB]): BB = f(value)
  override def forGood(f: Int => Unit): Unit = f(value)
  override def fold[B](noneValue: => B)(fGood: Int => B): B = fGood(value)
  @inline override def foldErrs[B](fGood: Int => B)(fBad: Strings => B): B = fGood(value)
  override def get: Int = value
  override def foldDo(fGood: Int => Unit)(fBad: Strings => Unit): Unit = fGood(value)
}
case class BadInt(errs: Refs[String]) extends EMonInt with BadBase[Int]
{ override def map[B, BB <: EMonBase[B]](f: Int => B)(implicit build: EMonBuild[B, BB]): BB = build.newBad(errs)
  override def flatMap[B, BB <: EMonBase[B]](f: Int => BB)(implicit build: EMonBuild[B, BB]): BB = build.newBad(errs)
  override def fold[B](noneValue: => B)(fGood: Int => B): B = noneValue
  @inline override def foldErrs[B](fGood: Int => B)(fBad: Strings => B): B = fBad(errs)
}

object NoInt extends BadInt(Refs())


sealed trait EMonInts extends EMonBase[Ints]
{ def map[B, BB <: EMonBase[B]](f: Ints => B)(implicit build: EMonBuild[B, BB]): BB
}

final case class GoodInts(value: Ints) extends EMonInts with GoodBase[Ints]
{ override def map[B, BB <: EMonBase[B]](f: Ints => B)(implicit build: EMonBuild[B, BB]): BB = build(f(value))
  override def flatMap[B, BB <: EMonBase[B]](f: Ints => BB)(implicit build: EMonBuild[B, BB]): BB = f(value)
  override def forGood(f: Ints => Unit): Unit = f(value)
  override def fold[B](noneValue: => B)(fGood: Ints => B): B = fGood(value)
  @inline override def foldErrs[B](fGood: Ints => B)(fBad: Strings => B): B = fGood(value)
  override def get: Ints = value
  override def foldDo(fGood: Ints => Unit)(fBad: Strings => Unit): Unit = fGood(value)
}
case class BadInts(errs: Refs[String]) extends EMonInts with BadBase[Ints]
{ override def map[B, BB <: EMonBase[B]](f: Ints => B)(implicit build: EMonBuild[B, BB]): BB = build.newBad(errs)
  override def flatMap[B, BB <: EMonBase[B]](f: Ints => BB)(implicit build: EMonBuild[B, BB]): BB = build.newBad(errs)
  override def fold[B](noneValue: => B)(fGood: Ints => B): B = noneValue
  @inline override def foldErrs[B](fGood: Ints => B)(fBad: Strings => B): B = fBad(errs)
}

object NoInts extends BadInts(Refs())
