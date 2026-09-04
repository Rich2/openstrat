/* Copyright 2018-26 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat

/** Persistence [[Exception]]. */
trait ExcPersist extends Exception

/** Persistence typing [[Exception]]. */
trait ExcSemantic extends ExcPersist

/** RSON expression has wrong type [[Exception]]. */
object ExcWrongType extends Exception("Expression has wrong type") with ExcSemantic

/** RSON expression has wrong type [[Exception]] [[Left]]. */
//object FailWrongType extends Left[ExcWrongType.type](ExcWrongType)

/** No [[pParse.Expr]] at index N [[Exception]]. */
case class ExcNoExprAtN(index: Int, unshow: Unshow[?]) extends Exception(s"No expression at index $index to find type ${unshow.typeStr}") with ExcSemantic

/** No [[pParse.Expr]] at index N [[Left]]. */
def FailNoExprAtN(index: Int, unshow: Unshow[?]): Fail[ExcNoExprAtN] = Fail[ExcNoExprAtN](ExcNoExprAtN(index, unshow))

/** Exception from a find search for a type. */
sealed trait ExcFind extends Exception

object ExcNotFound extends Exception("Not found") with ExcFind

val NotFound: Fail[ExcNotFound.type] = Fail(ExcNotFound)

/** [[ExcNotFound]] singleton type. */
type ExcNFT = ExcNotFound.type

/** [[ExcNotFound]] error monad. */
type ExcNFTMon[+A] = Either[ExcNFT, A]

/** A [[Left]] with a not found Exception. */
val FailNotFound: Fail[ExcNotFound.type] = Fail(ExcNotFound)

/** A found multiple values of type [[Exception]]. */
case class ExcFoundMulti(val num: Int) extends Exception(s"$num values of type found.") with ExcFind

/** A found multiple values of type [[Left]], */
def FailFoundMulti(num: Int): Fail[ExcFoundMulti] = Left(ExcFoundMulti(num))