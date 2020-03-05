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