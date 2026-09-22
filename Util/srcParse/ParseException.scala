/* Copyright 2018-26 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package pParse

/** Parser [[Exception]]. */
trait ParseException extends ExcPersist

object ParseException
{ /** Factory apply method to create a Parse [[Exception]]. */
  def apply(message: String): ParseException = new Exception(message) with ParseException

  /** Factory apply method to create a Parse [[Exception]]. */
  def apply(tp: TextPosn, detail: String): ParseException = new Exception(tp.fileName -- tp.lineNum.toString + ", " + tp.linePosn.toString + ": " + detail) with ParseException
  
  /** [[EqT]] type class instance / evidence for [[ParseException]]. */
  given eqTEv: EqT[ParseException] = (pexc1, pexc2) => pexc1.getMessage == pexc2.getMessage
}

/** [[Either]] with a [[ParseException]] [[Left]] type. */
type ParseExcEither[A] = Either[ParseException, A]

/** [[Left]] with a [[ParseException]] type. */
type ParseExcLeft = Left[ParseException, Nothing]

/** AST abstract syntax tree [[Exception]]. */
case class AstException(tp: TextPosn, detail: String) extends Exception(tp.fileName -- tp.lineNum.toString + ", " + tp.linePosn.toString + ": " + detail), ParseException

/** Either with an [[AstException]] [[Left]] type. */
type AstExcEither[+A] = Either[AstException, A]

object AstExcFail
{ /** Factory apply method to create a [[Left]] with an [[AstException]], Abstract Syntax Tree exception. */
  def apply(tp: TextPosn, detail: String): Left[AstException, Nothing] = Left(AstException(tp, detail))
}

/** A lexar exception. */
case class LexarException(tp: TextPosn, detail: String) extends Exception(tp.fileName -- tp.lineNum.toString + ", " + tp.linePosn.toString + ": " + detail), ParseException

object LexarException
{ /** [[EqT]] type class instance / evidence for [[LexarException]]. */
  given eqTEv: EqT[LexarException] = (exc1, exc2) => exc1.getMessage == exc2.getMessage
}

object LexarExcFail
{ /** Factory apply method to construct a lexar exception. */
  def apply(tp: TextPosn, detail: String): Left[LexarException, Nothing] = Left(LexarException(tp, detail))
}

type LexarExcEither[+A] = Either[LexarException, A]

/**  */
class SettingNotFoundException(detail: String) extends Exception(detail -- "setting not found"), ParseException

object SettingNotFoundException
{
  def apply(detail: String): SettingNotFoundException = new SettingNotFoundException(detail)
}