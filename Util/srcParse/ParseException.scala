/* Copyright 2018-26 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package pParse

/** Parser [[Exception]]. */
trait ParseException extends ExcPersist

object ParseException
{ def apply(message: String): ParseException = new Exception(message) with ParseException
  def apply(tp: TextPosn, detail: String): ParseException = new Exception(tp.fileName -- tp.lineNum.toString + ", " + tp.linePosn.toString + ": " + detail) with ParseException
}

/** [[Either]] with a [[ParseException]] [[Left]] type. */
type ParseExcEither[A] = Either[ParseException, A]

/** AST abstract syntax tree [[Exception]]. */
case class AstException(tp: TextPosn, detail: String) extends Exception(tp.fileName -- tp.lineNum.toString + ", " + tp.linePosn.toString + ": " + detail), ParseException

/** Either with an [[AstException]] [[Left]] type. */
type AstExcEither[+A] = Either[AstException, A]

object AstExcFail
{
  def apply(tp: TextPosn, detail: String): Fail[AstException] = Fail[AstException](AstException(tp, detail))
}

/** A lexar exception. */
case class LexarException(tp: TextPosn, detail: String) extends Exception(tp.fileName -- tp.lineNum.toString + ", " + tp.linePosn.toString + ": " + detail), ParseException

object LexarException
{
  given eqTEv: EqT[LexarException] = (exc1, exc2) => exc1.getMessage == exc2.getMessage
}

object LexarExcFail
{ /** Factory apply method to construct a lexar exception. */
  def apply(tp: TextPosn, detail: String): Fail[LexarException] = Fail[LexarException](LexarException(tp, detail))
}

type LexarExcEither[+A] = Either[LexarException, A] 

