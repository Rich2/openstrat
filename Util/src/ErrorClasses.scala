/* Copyright 2018-26 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat
import scala.annotation.unchecked.uncheckedVariance, pParse.*, reflect.ClassTag

/** Exception from a find search for a type. */
sealed trait FindException extends Exception, ParseException

object ExcNotFound extends Exception("Not found") with FindException

/** [[ExcNotFound]] error monad. */
type NotFoundEither[+A] = Either[ExcNotFound.type , A]

/** A [[Left]] with a not found Exception. */
val NotFoundLeft: Left[ExcNotFound.type, Nothing] = Left(ExcNotFound)

/** A found multiple values of type [[Exception]]. */
case class ExcFoundMulti(val num: Int) extends Exception(s"$num values of type found.") with FindException

/** A found multiple values of type [[Left]], */
def FailFoundMulti(num: Int): Left[ExcFoundMulti, Nothing] = Left(ExcFoundMulti(num))

/** An [[Either]] with a [[Throwable]] [[Left]] type. */
type ThrowEither[+A] = Either[Throwable, A]

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
case object NoneExc extends Exception("None"), ParseException

/** [[Left]] with a [[NoneExc]] value. */
val LNone: Left[NoneExc.type, Nothing] = Left(NoneExc)