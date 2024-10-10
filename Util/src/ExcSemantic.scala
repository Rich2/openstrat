/* Copyright 2018-24 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat

/** Persistence [[Exception]]. */
trait ExcPersist extends Exception

/** Persistence typing [[Exception]]. */
trait ExcSemantic extends ExcPersist

/** RSON expression has wrong type [[Expression]]. */
object ExcWrongType extends Exception("Expression has wrong type") with ExcSemantic

/** RSON expression has wrong type [[Expression]] [[Fail]]. */
object FailWrongType extends Fail[ExcWrongType.type](ExcWrongType)

case class ExcNoExprAtN(index: Int, unshow: Unshow[?]) extends Exception(s"No expression of type ${unshow.typeStr} at index $index") with ExcSemantic

case class FailNoExprAtN(index: Int, unshow: Unshow[?]) extends Fail[ExcNoExprAtN](ExcNoExprAtN(index, unshow))

/** Exception from a find search for a type. */
sealed trait ExcFind extends Exception

/** A [[Fail]] with [[Exception]] type. */
type FailExc = Fail[Exception]
object ExcNotFound extends Exception("Not found") with ExcFind

object NotFound
{ def apply(): Fail[ExcNotFound.type] = Fail(ExcNotFound)
}

/** [[ExcNotFound]] singleton type. */
type ExcNFT = ExcNotFound.type

/** [[ExcNotFound]] error monad. */
type ExcNFTMon[+A] = ErrBi[ExcNFT, A]

/** A [[Fail]] with a not found Exception. */
object FailNotFound extends Fail(ExcNotFound)

/** A found multiple values of type [[Exception]]. */
case class ExcFoundMulti(val num: Int) extends Exception(s"$num values of type found.") with ExcFind

/** A found multiple values of type [[Fail]], */
class FailFoundMulti(num: Int) extends Fail(ExcFoundMulti(num))
