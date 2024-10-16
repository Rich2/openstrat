/* Copyright 2018-24 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat
import scala.annotation.unchecked.uncheckedVariance

/** An Errors handling class. Consider changing name to EHan. The main ways to consume the final result of the flatMap operation are fold, getElse,
 * foreach and forEither. This corresponds, but is not functionally equivalent to an Either[StrList, A] or Either[List[String], +A]. There are
 * advantages to having a separate class and I find that I rarely use Either apart from with standard errors as the Left type. However use the
 * methods biMap, to Either, eitherMap and eitherFlatMap when interoperability with Either is required. In my view Either[T] class is redundant and is
 * rarely used except as an error handler. So it makes sense to use a dedicated class. */
sealed trait EMonOld[+A]
{ /** Maps the Good case of this EMon with the function. */
  def map[B](f: A => B): EMonOld[B]

  def flatMap[B](f: A => EMonOld[B]): EMonOld[B]

  /** Gets the value of Good or returns the elseValue parameter if Bad. Both Good and Bad should be implemented in the leaf classes to avoid unnecessary boxing
   *  of primitive values. */
  def getElse(elseValue: A @uncheckedVariance): A

  def errs: StrArr

  /** Will perform action if Good. Does nothing if Bad. */
  def forGood(f: A => Unit): Unit

  /** Fold the EMon of type A into a type of B. */
  @inline def foldErrs[B](fGood: A => B)(fBad: StrArr => B): B

  /** This is just a Unit returning fold, but is preferred because the method  is explicit that it is called for effects, rather than to return a value. This
   *  method is implemented in the leaf Good classes to avoid boxing. */
  def forGoodForBad(fGood: A => Unit)(fBad: StrArr => Unit): Unit

  /** Gets the value of Good, throws exception on Bad. */
  def get: A

  def fold[B](noneValue: => B)(fGood: A => B): B
  def fld[B](noneValue: => B, fGood: A => B): B

  def toOption: Option[A]
  def toEither: Either[StrArr, A]
  def isGood: Boolean
  def isBad: Boolean

  /** Maps Good to Right[Strings, D] and Bad to Left[Strings, D]. These are implemented in the base traits GoodBase[+A] and BadBase[+A] as
   *  Either[+A, +B] boxes all value classes. */
  def mapToEither[D](f: A => D): Either[StrArr, D]

  /** These are implemented in the base traits GoodBase[+A] and BadBase[+A] as Either[+A, +B] boxes all value classes. */
  def flatMapToEither[D](f: A => Either[StrArr, D]): Either[StrArr, D]

  /** These are implemented in the base traits GoodBase[+A] and BadBase[+A] as Either[+A, +B] boxes all value classes. */
  def biMap[L2, R2](fLeft: StrArr => L2, fRight: A => R2): Either[L2, R2]

  def mapToOption[B](f: A => B): Option[B]
  def flatMap2ToOption[A2, B](o2: EMonOld[A2], f: (A, A2) => B): Option[B]

  def goodOrOther[A1 >: A](otherEMon: => EMonOld[A1] @uncheckedVariance): EMonOld[A1]
}

/** The Good sub class of EMon[+A]. This corresponds, but is not functionally equivalent to an Either[List[String], +A] based
 *  Right[Refs[String], +A]. */
final case class Good[+A](val value: A) extends EMonOld[A]
{
  override def map[B](f: A => B): EMonOld[B] = Good[B](f(value))
  override def flatMap[B](f: A => EMonOld[B]): EMonOld[B] = f(value)
  @inline override def foldErrs[B](fGood: A => B)(fBad: StrArr => B): B = fGood(value)

  override def fold[B](noneValue: => B)(fGood: A => B): B = fGood(value)
  override def fld[B](noneValue: => B, fGood: A => B): B = fGood(value)


  override def forGoodForBad(fGood: A => Unit)(fBad: StrArr => Unit): Unit = fGood(value)
  override def forGood(f: A => Unit): Unit = f(value)
  override def get: A = value
  override def getElse(elseValue: A @uncheckedVariance): A = value
  
  override def errs: StrArr = StrArr()
  override def toOption: Option[A] = Some(value)
  override def toEither: Either[StrArr, A] = Right(value)
  override def isGood: Boolean = true
  override def isBad: Boolean = false
  override def mapToEither[D](f: A => D): Either[StrArr, D] = Right(f(value))
  override def flatMapToEither[D](f: A => Either[StrArr, D]): Either[StrArr, D] = f(value)
  override def biMap[L2, R2](fLeft: StrArr => L2, fRight: A => R2): Either[L2, R2] = Right(fRight(value))
  override def mapToOption[B](f: A => B): Option[B] = Some[B](f(value))
  override def flatMap2ToOption[A2, B](e2: EMonOld[A2], f: (A, A2) => B): Option[B] = e2.fld(None, a2 => Some(f(value, a2)))
  override def goodOrOther[A1 >: A](otherEMon: => EMonOld[A1] @uncheckedVariance): Good[A] = this
}

/** The errors case of EMon[+A]. This corresponds, but is not functionally equivalent to an Either[List[String], +A] based Left[List[String], +A]. */
class Bad[+A](val errs: StrArr) extends EMonOld[A]
{ override def toString: String =  "Bad" + errs.foldLeft("")(_ + _.enquote).enParenth
  override def map[B](f: A => B): EMonOld[B] = Bad[B](errs)
  override def flatMap[B](f: A => EMonOld[B]): EMonOld[B] = Bad[B](errs)
  override def fold[B](noneValue: => B)(fGood: A => B): B = noneValue
  override def fld[B](noneValue: => B, fGood: A => B): B = noneValue
  @inline override def foldErrs[B](fGood: A => B)(fBad: StrArr => B): B = fBad(errs)

  override def getElse(elseValue: A @uncheckedVariance): A = elseValue
  override def forGood(f: A => Unit): Unit = {}
  override def toOption: Option[A] = None
  override def toEither: Either[StrArr, A] = Left(errs)
  override def get: A = excep("Called get on Bad.")
  override def forGoodForBad(fGood: A => Unit)(fBad: StrArr => Unit): Unit = fBad(errs)
  override def isGood: Boolean = false
  override def isBad: Boolean = true
  override def mapToEither[D](f: A => D): Either[StrArr, D] = Left(errs)
  override def flatMapToEither[D](f: A => Either[StrArr, D]): Either[StrArr, D] = (Left(errs))
  override def biMap[L2, R2](fLeft: StrArr => L2, fRight: A => R2): Either[L2, R2] = Left(fLeft(errs))
  override def mapToOption[B](f: A => B): Option[B] = None
  override def flatMap2ToOption[A2, B](e2: EMonOld[A2], f: (A, A2) => B): Option[B] = None
  override def goodOrOther[A1 >: A](otherEMon: => EMonOld[A1] @uncheckedVariance): EMonOld[A1] = otherEMon
}

object Bad
{
  def apply[A](errs: StrArr): Bad[A] = new Bad[A](errs)
}