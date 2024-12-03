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

/** No [[Expr]] at index N [[Exception]]. */
case class ExcNoExprAtN(index: Int, unshow: Unshow[?]) extends Exception(s"No expression at index $index to find type ${unshow.typeStr}") with ExcSemantic

/** No [[Expr]] at index N [[Fail]]. */
case class FailNoExprAtN(index: Int, unshow: Unshow[?]) extends Fail[ExcNoExprAtN](ExcNoExprAtN(index, unshow))

/** Exception from a find search for a type. */
sealed trait ExcFind extends Exception

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