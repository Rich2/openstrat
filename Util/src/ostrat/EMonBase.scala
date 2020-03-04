package ostrat

/** This is the base trait for the specialisations of Error Monads. Each specialisation eg EMonInt has its own specialised Good eg GoodInt and Bad eg
 * BadInt sub classes. It can be Good eg GoodInt containing a value of A eg Int, or Bad eg BadInt containing a Refs of Strings. A special case of Bad
 * is the NoGood object eg NoInt which has not value of type A and no error strings. To avoid boxeing most methods are left abstract to be implemented
 * in the leaf classes, to avoid unnecessary boxing on generic functions. */
trait EMonBase[+A]
{
  /** This is called map for typeclass map. Hope to have this as the standard map. */
  def map[B, BB <: EMonBase[B]](f: A => B)(implicit build: EMonBuild[B, BB]): BB

  /** This is called map for typeclass map. Hope to have this as the standard map. */
  def flatMap[B, BB <: EMonBase[B]](f: A => BB)(implicit build: EMonBuild[B, BB]): BB

  def errs: Strings
  /** Will perform action if Good. Does nothing if Bad. */
  def forGood(f: A => Unit): Unit
  /** Fold the EMon of type A into a type of B. */
  @inline def foldErrs[B](fGood: A => B)(fBad: Strings => B): B
  def fold[B](noneValue: => B)(fGood: A => B): B
}

trait GoodBase[+A] extends EMonBase[A]
{ def value: A
  def errs: Strings = Refs()
}

trait BadBase[+A] extends EMonBase[A]
{ override def forGood(f: A => Unit): Unit = {}
}

trait NoBase[+A] extends BadBase[A]

trait EMonInt extends EMonBase[Int]
{ def map[B, BB <: EMonBase[B]](f: Int => B)(implicit build: EMonBuild[B, BB]): BB
}

case class GoodInt(value: Int) extends EMonInt with GoodBase[Int]
{ override def map[B, BB <: EMonBase[B]](f: Int => B)(implicit build: EMonBuild[B, BB]): BB = build(f(value))
  override def flatMap[B, BB <: EMonBase[B]](f: Int => BB)(implicit build: EMonBuild[B, BB]): BB = f(value)
  override def forGood(f: Int => Unit): Unit = f(value)
  override def fold[B](noneValue: => B)(fGood: Int => B): B = fGood(value)
  @inline override def foldErrs[B](fGood: Int => B)(fBad: Strings => B): B = fGood(value)
}
case class BadInt(errs: Refs[String]) extends EMonInt with BadBase[Int]
{ override def map[B, BB <: EMonBase[B]](f: Int => B)(implicit build: EMonBuild[B, BB]): BB = build.newBad(errs)
  override def flatMap[B, BB <: EMonBase[B]](f: Int => BB)(implicit build: EMonBuild[B, BB]): BB = build.newBad(errs)
  override def fold[B](noneValue: => B)(fGood: Int => B): B = noneValue
  @inline override def foldErrs[B](fGood: Int => B)(fBad: Strings => B): B = fBad(errs)
}

object NoInt extends BadInt(Refs()) with EMonInt with NoBase[Int]

trait EMonDbl extends EMonBase[Double]
{ def map[B, BB <: EMonBase[B]](f: Double => B)(implicit build: EMonBuild[B, BB]): BB
}

case class GoodDbl(value: Double) extends EMonDbl with GoodBase[Double]
{ override def map[B, BB <: EMonBase[B]](f: Double => B)(implicit build: EMonBuild[B, BB]): BB = build(f(value))
  override def flatMap[B, BB <: EMonBase[B]](f: Double => BB)(implicit build: EMonBuild[B, BB]): BB = f(value)
  override def forGood(f: Double => Unit): Unit = f(value)
  override def fold[B](noneValue: => B)(fGood: Double => B): B = fGood(value)
  @inline override def foldErrs[B](fGood: Double => B)(fBad: Strings => B): B = fGood(value)
}
case class BadDbl(errs: Refs[String]) extends EMonDbl with BadBase[Double]
{ override def map[B, BB <: EMonBase[B]](f: Double => B)(implicit build: EMonBuild[B, BB]): BB = build.newBad(errs)
  override def flatMap[B, BB <: EMonBase[B]](f: Double => BB)(implicit build: EMonBuild[B, BB]): BB = build.newBad(errs)
  override def fold[B](noneValue: => B)(fGood: Double => B): B = noneValue
  @inline override def foldErrs[B](fGood: Double => B)(fBad: Strings => B): B = fBad(errs)
}

object NoDbl extends BadDbl(Refs()) with EMonDbl with NoBase[Double]

trait EMonBool extends EMonBase[Boolean]
{ def map[B, BB <: EMonBase[B]](f: Boolean => B)(implicit build: EMonBuild[B, BB]): BB
}

case class GoodBool(value: Boolean) extends EMonBool with GoodBase[Boolean]
{ override def map[B, BB <: EMonBase[B]](f: Boolean => B)(implicit build: EMonBuild[B, BB]): BB = build(f(value))
  override def flatMap[B, BB <: EMonBase[B]](f: Boolean => BB)(implicit build: EMonBuild[B, BB]): BB = f(value)
  override def forGood(f: Boolean => Unit): Unit = f(value)
  override def fold[B](noneValue: => B)(fGood: Boolean => B): B = fGood(value)
  @inline override def foldErrs[B](fGood: Boolean => B)(fBad: Strings => B): B = fGood(value)
}
case class BadBool(errs: Refs[String]) extends EMonBool with BadBase[Boolean]
{ override def map[B, BB <: EMonBase[B]](f: Boolean => B)(implicit build: EMonBuild[B, BB]): BB = build.newBad(errs)
  override def flatMap[B, BB <: EMonBase[B]](f: Boolean => BB)(implicit build: EMonBuild[B, BB]): BB = build.newBad(errs)
  override def fold[B](noneValue: => B)(fGood: Boolean => B): B = noneValue
  @inline override def foldErrs[B](fGood: Boolean => B)(fBad: Strings => B): B = fBad(errs)
}

object NoBool extends BadBool(Refs()) with EMonBool with NoBase[Boolean]
