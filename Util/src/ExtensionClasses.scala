/* Copyright 2018-26 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat
import scala.annotation.unchecked.uncheckedVariance, pParse.*, reflect.ClassTag

type Succ[B] = Right[Nothing, B]

object Succ
{ def apply[B](value: B): Succ[B] = Right[Nothing, B](value)
}

/** Exception from a find search for a type. */
sealed trait ExcFind extends Exception

object ExcNotFound extends Exception("Not found") with ExcFind

/** [[ExcNotFound]] error monad. */
type NotFoundEither[+A] = Either[ExcNotFound.type , A]

/** A [[Left]] with a not found Exception. */
val NotFoundLeft: Left[ExcNotFound.type, Nothing] = Left(ExcNotFound)

/** A found multiple values of type [[Exception]]. */
case class ExcFoundMulti(val num: Int) extends Exception(s"$num values of type found.") with ExcFind

/** A found multiple values of type [[Left]], */
def FailFoundMulti(num: Int): Left[ExcFoundMulti, Nothing] = Left(ExcFoundMulti(num))

/** An [[Either]] with a [[Throwable]] [[Left]] type. */
type ThrowEither[+A] = Either[Throwable, A]

/** A Throwable error monad with an [[RArr]] for success. */
type ThrowEitherRArr[+A] = Either[Throwable, RArr[A]]

/** An [[Exception]] error monad. */
type ExcEither[+A] = Either[Exception, A]

object LeftExc
{ /** Factory apply method to construct a [[Left]] with an [[Exception]] type. */
  @inline def apply(message: String): LeftExc = new Left[Exception, Nothing](new Exception(message))
}

/** Java IO [[Exception]] */
type IOExc = java.io.IOException

object IOExc
{ /** Factory apply method to construct [[java.io.IOException]]. */
  def apply(message: String): IOExc = new java.io.IOException(message)
}

/** A [[java.io.IOException]] error monad. */
type IOExcEither[+B] = Either[IOExc, B]

/** A [[Left]] with [[Exception]] type. */
type LeftExc = Left[Exception, Nothing]

/** A [[Left]] with [[IOException]] type. */
type LeftIO = Left[IOExc, Nothing]

object FailIO
{ /** Factory apply method to construct a [[Left]] from an [[java.io.IOException]] type. */
  @inline def apply(err: IOExc): LeftIO = new Left[IOExc, Nothing](err)

  /** Factory apply method to construct a [[Left]] with an [[java.io.IOException]] type. */
  @inline def apply(message: String): LeftIO = new Left[IOExc, Nothing](new IOExc(message))
}

/** A [[None]] value converted to an [[Extension]]. */
case object NoneExc extends Exception("None")

/** [[Left]] with a [[NoneExc]] value. */
val LNone: Left[NoneExc.type, Nothing] = Left(NoneExc)

/** Error bifunctor for [[Tuple2]]. */
type throwEitherT2[E <: Throwable, A1, A2] = Either[E, (A1, A2)]

/** Success for a [[Tuple2]] value. */
type Succ2[B1, B2] = Right[Nothing, (B1, B2)]

object Succ2
{ /** Factory apply method for creating [[Right]] with a [[Tuple2]] value. */
  def apply[B1, B2](b1: B1, b2: B2): Succ2[B1, B2] = new Right[Nothing, (B1, B2)]((b1, b2))

  /** unapply extractor for success on an [[Either]] with a [[Tuple2]] value type. */
  def unapply[B1, B2](inp: throwEitherT2[?, B1, B2]): Option[(B1, B2)] = inp match
  { case Right(pair) => Some(pair._1, pair._2)
    case _ => None
  }
}

/** Error bifunctor for [[Tuple3]]. */
type ErrBi3[+E <: Throwable, +A1, +A2, +A3] = Either[E, (A1, A2, A3)]

/** Extension class for [[Exception]] bifunctor for [[Tuple3]]s. */
extension[E <: Throwable, A1, A2, A3](thisEE3: ErrBi3[E, A1, A2, A3])
{
  def flatMap3[B1, B2, B3](f: (A1, A2, A3) => ErrBi3[E, B1, B2, B3])(using ct1: ClassTag[A1], ct2: ClassTag[A2], ct3: ClassTag[A3]): ErrBi3[E, B1, B2, B3] =
    thisEE3 match
    { case Right(tuple) => f(tuple._1, tuple._2, tuple._3)
    case Left(err) => Left(err)
    }
}

/** Success for a [[Tuple3]] value. */
type Succ3[B1, B2, B3] = Right[Nothing, (B1, B2, B3)]

object Succ3
{ /** Factory apply method for creating [[Right]] with a [[Tuple3]] value. */
  def apply[B1, B2, B3](b1: B1, b2: B2, b3: B3): Succ3[B1, B2, B3] = new Right[Nothing, (B1, B2, B3)]((b1, b2, b3))
}

object Right3
{ /** unapply extractor for an [[Either]] with a [[Tuple3]] value type. */
  def unapply[B1, B2, B3](inp: Either[Any, (Any, Any, Any)])(using ct1: ClassTag[B1], ct2: ClassTag[B2], ct3: ClassTag[B3]): Option[(B1, B2, B3)] = inp match {
    case Right(tuple) => {
      val op1 = ct1.unapply(tuple._1)
      val op2 = ct2.unapply(tuple._2)
      val op3 = ct3.unapply(tuple._3)
      OptionMap3(op1, op2, op3) { (b1, b2, b3) => (b1, b2, b3) }
    }
    case _ => None
  }
}