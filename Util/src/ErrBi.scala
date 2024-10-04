/* Copyright 2018-24 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat
import scala.annotation.unchecked.uncheckedVariance

/** Biased bifunctor for errors. */
sealed trait ErrBi[+E <: Throwable, +A]
{ /** Classic map function as we see on a Scala [[Option]] or [[List]]. */
  def map[B](f: A => B): ErrBi[E, B]

  /** Classic flatMap function as we see on a Scala [[Option]]. */
  def flatMap[B](f: A => ErrBi[E, B] @uncheckedVariance): ErrBi[E, B]

  def isSucc: Boolean

  /** Will perform action if success. Does nothing if [[Fail]]. */
  def forSucc(f: A => Unit): Unit

  /** Fold this [[ErrBi]] into a type B. Takes two function parameters, one converts from A to B as in a normal map method. The second parameter in its own
   * parameter list converts from the Error type to type B. */
  @inline def fold[B](fSucc: A => B)(fFail: E => B): B
  
  def toEMon: EMon[A] = this match{
    case Succ(value) => Good(value)
    case Fail(err) => Bad(StrArr(err.toString))
  }
}

/** Success, boxes a good value of the desired type. */
case class Succ[+E <: Throwable, +A](value: A) extends ErrBi[E, A]
{ override def map[B](f: A => B): ErrBi[E, B] = new Succ[E, B](f(value))

  override def flatMap[B](f: A => ErrBi[E, B] @uncheckedVariance): ErrBi[E, B] = f(value) match
  { case succ: Succ[E, B] => succ
    case fail: Fail[E, B] => fail
  }

  override def isSucc: Boolean = true
  override def forSucc(f: A => Unit): Unit = f(value)
  override def fold[B](fSucc: A => B)(fFail: E => B): B = fSucc(value)
}

/** Failure to return a value of the desired type. Boxes a [[Throwable]] error. */
case class Fail[+E <: Throwable, +A](val error: E) extends ErrBi[E, A]
{ override def map[B](f: A => B): ErrBi[E, B] = new Fail[E, B](error)
  override def flatMap[B](f: A => ErrBi[E, B] @uncheckedVariance): ErrBi[E, B] = new Fail[E, B](error)
  override def isSucc: Boolean = false
  override def forSucc(f: A => Unit): Unit = {}
  override def fold[B](fSucc: A => B)(fFail: E => B): B = fFail(error)
}

type ExcMon[+A] = ErrBi[Exception, A]
type SuccExc[+A] = Succ[Exception, A]
type FailExc[+A] = Fail[Exception, A]

object FailExc
{
  @inline def apply[A](message: String): FailExc[A] = new Fail[Exception, A](new Exception(message)) 
}

object NoneExc extends Exception("None")

/** Error bifunctor for [[RArr]] values. */
type ErrBiArr[E <: Throwable, AE <: AnyRef] = ErrBi[E, RArr[AE]]

/** Extractor function object for a successful Arr Sequence of length 1. */
object SuccArr1
{ /** Extractor method for a successful [[Arr]] Sequence of length 1. */
  def unapply[E<: Throwable, A <: AnyRef](eArr: ErrBiArr[E, A]): Option[A] = eArr match
  { case Succ(Arr1(head)) => Some(head)
    case _ => None
  }
}

/** Error bifunctor for [[Tuple2]]. */
type ErrBi2[E <: Throwable, A1, A2] = ErrBi[E, (A1, A2)]

/** Extension class for [[Exception]] bifunctor for [[Tuple2]]s. */
implicit class ExcBi2Extensions[E <: Throwable, A1, A2](val thisEE2: ErrBi2[E, A1, A2])
{
  /*def toEMon2: EMon3[A1, A2] = thisEE2 match {
    case Succ2(a1, a2) => Good2(a1)
    case Fail(err) => Bad2(StrArr(err.toString))
  }*/

  def t2FlatMap[B1, B2](f: (A1, A2) => ErrBi2[E, B1, B2]): ErrBi2[E, B1, B2] = thisEE2 match
  { case Succ2(a1, a2) => f(a1, a2)
    case Fail(err) => Fail(err)
  }
}

/** Success for a [[Tuple2]] value. */
type Succ2[E <: Throwable, A1, A2] = Succ[E, (A1, A2)]

object Succ2
{
  /** Factory apply method for creating [[Succ]] with a [[Tuple2]] value. */
    def apply[E <: Throwable, A1, A2](a1: A1, a2: A2): Succ2[E, A1, A2] = new Succ[E, (A1, A2)]((a1, a2))

  /** unapply extractor for success on an [[ErrBi]] with a [[Tuple3]] value type. */
  def unapply[E <: Throwable, A1, A2](inp: ErrBi2[E, A1, A2]): Option[(A1, A2)] = inp match
  { case succ: Succ2[E, A1, A2] => Some(succ.value._1, succ.value._2)
    case _ => None
  }
}

/** Error bifunctor for [[Tuple3]]. */
type ErrBi3[E <: Throwable, A1, A2, A3] = ErrBi[E, (A1, A2, A3)]

/** Extension class for [[Exception]] bifunctor for [[Tuple3]]s. */
implicit class ExcBi3Extensions[E <: Throwable, A1, A2, A3](val thisEE3: ErrBi3[E, A1, A2, A3])
{
  /*def toEMon3: EMon3[A1, A2, A3] = thisEE3 match {
    case Succ3(a1, a2, a3) => Good3(a1, a2, a3)
    case Fail(err) => Bad3(StrArr(err.toString))
  }*/

  def flatMap3[B1, B2, B3](f: (A1, A2, A3) => ErrBi3[E, B1, B2, B3]): ErrBi3[E, B1, B2, B3] = thisEE3 match
  { case Succ3(a1, a2, a3) => f(a1, a2, a3)
    case Fail(err) => Fail(err)
  }
}

/** Success for a [[Tuple3]] value. */
type Succ3[E <: Throwable, A1, A2, A3] = Succ[E, (A1, A2, A3)]

object Succ3
{ /** Factory apply method for creating [[Succ]] with a [[Tuple3]] value. */
  def apply[E <: Throwable, A1, A2, A3](a1: A1, a2: A2, a3: A3): Succ3[E, A1, A2, A3] = new Succ[E, (A1, A2, A3)]((a1, a2, a3))

  /** unapply extractor for success on an [[ErrBi]] with a [[Tuple3]] value type. */
  def unapply[E <: Throwable, A1, A2, A3](inp: ErrBi3[E, A1, A2, A3]): Option[(A1, A2, A3)] = inp match{
    case succ: Succ3[E, A1, A2, A3] => Some(succ.value._1, succ.value._2, succ.value._3)
    case _ => None
  }
}