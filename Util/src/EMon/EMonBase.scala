package ostrat
import annotation.unchecked.uncheckedVariance

/** This is the base trait for the specialisations of Error Monads. Each specialisation eg EMonInt has its own specialised Good eg GoodInt and Bad eg
 * BadInt sub classes. It can be Good eg GoodInt containing a value of A eg Int, or Bad eg BadInt containing a Refs of Strings. A special case of Bad
 * is the NoGood object eg NoInt which has not value of type A and no error strings. To avoid boxing most methods are left abstract to be implemented
 * in the leaf classes, to avoid unnecessary boxing on generic functions. */
trait EMonBase[+A]
{
  /** Gets the value of Good or returns the elseValue parameter if Bad. Both Good and Bad should be implemented in the leaf classes to avoid
   * unnecessary boxing of primitive values. */
  def getElse(elseValue: => A @uncheckedVariance): A
  /** This is called map for typeclass map. Hope to have this as the standard map. */
  def baseMap[B, BB <: EMonBase[B]](f: A => B)(implicit build: EMonBuild[B, BB]): BB

  /** This is called map for typeclass map. Hope to have this as the standard map. */
  def baseFlatMap[B, BB <: EMonBase[B]](f: A => BB)(implicit build: EMonBuild[B, BB]): BB

  def errs: Strings

  /** Will perform action if Good. Does nothing if Bad. */
  def forGood(f: A => Unit): Unit

  /** Fold the EMon of type A into a type of B. */
  @inline def foldErrs[B](fGood: A => B)(fBad: Strings => B): B

  /** This is just a Unit returning fold, but is preferred because the method  is explicit that it is called for effects, rather than to return a
   *  value. This method is implemented in the leaf Good classes to avoid boxing. */
  def foldDo(fGood: A => Unit)(fBad: Strings => Unit): Unit

  /** Gets the value of Good, throws exception on Bad. */
  def get: A

  def fold[B](noneValue: => B)(fGood: A => B): B
  def fld[B](noneValue: => B, fGood: A => B): B


  def toEither: Either[Strings, A]
  def isGood: Boolean
  def isBad: Boolean

  /** Maps Good to Right[Strings, D] and Bad to Left[Strings, D]. These are implemented in the base traits GoodBase[+A] and BadBase[+A] as
   *  Either[+A, +B] boxes all value classes. */
  def mapToEither[D](f: A => D): Either[Strings, D]

  /** These are implemented in the base traits GoodBase[+A] and BadBase[+A] as Either[+A, +B] boxes all value classes. */
  def flatMapToEither[D](f: A => Either[Strings, D]): Either[Strings, D]

  /** These are implemented in the base traits GoodBase[+A] and BadBase[+A] as Either[+A, +B] boxes all value classes. */
  def biMap[L2, R2](fLeft: Strings => L2, fRight: A => R2): Either[L2, R2]
}

trait GoodBase[+A] extends EMonBase[A]
{ def value: A
  override def errs: Strings = Refs()
  override def toEither: Either[Strings, A] = Right(value)
  override def isGood: Boolean = true
  override def isBad: Boolean = false
  override def mapToEither[D](f: A => D): Either[Strings, D] = Right(f(value))
  override def flatMapToEither[D](f: A => Either[Strings, D]): Either[Strings, D] = f(value)
  override def biMap[L2, R2](fLeft: Strings => L2, fRight: A => R2): Either[L2, R2] = Right(fRight(value))
}

trait BadBase[+A] extends EMonBase[A]
{ override def forGood(f: A => Unit): Unit = {}
  override def toEither: Either[Strings, A] = Left(errs)
  override def get: A = excep("Called get on Bad.")
  override def foldDo(fGood: A => Unit)(fBad: Strings => Unit): Unit = fBad(errs)
 // override def mapArr[B, BB <: ArrImut[B]](f: A => B)(implicit build: ArrBuild[B, BB]): BB = build.imutNew(0)

  override def isGood: Boolean = false
  override def isBad: Boolean = true
  override def mapToEither[D](f: A => D): Either[Strings, D] = Left(errs)
  override def flatMapToEither[D](f: A => Either[Strings, D]): Either[Strings, D] = (Left(errs))
  override def biMap[L2, R2](fLeft: Strings => L2, fRight: A => R2): Either[L2, R2] = Left(fLeft(errs))
}