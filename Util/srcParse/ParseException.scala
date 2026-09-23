/* Copyright 2018-26 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package pParse
import reflect.ClassTag

/** Parser [[Exception]]. */
trait ParseException extends Exception

object ParseException
{ /** Factory apply method to create a Parse [[Exception]]. */
  def apply(message: String): ParseException = new Exception(message) with ParseException

  /** Factory apply method to create a Parse [[Exception]]. */
  def apply(tp: TextPosn, detail: String): ParseException = new Exception(tp.fileName -- tp.lineNum.toString + ", " + tp.linePosn.toString + ": " + detail) with ParseException
  
  /** [[EqT]] type class instance / evidence for [[ParseException]]. */
  given eqTEv: EqT[ParseException] = (pexc1, pexc2) => pexc1.getMessage == pexc2.getMessage

  given multiBuild[E <: ParseException]: ErrMultiBuilder[ParseException, E, ParseExcMulti[E]] = new ErrMultiBuilder[ParseException, E, ParseExcMulti[E]]
  { override def multi(arr: RArr[E]): ParseExcMulti[E] = ParseExcMulti(arr)
    override def multi(errs: E*)(using ct: ClassTag[E]): ParseExcMulti[E] = ParseExcMulti(errs.toRArr)
  }
}

/** [[Either]] with a [[ParseException]] [[Left]] type. */
type ParseExcEither[A] = Either[ParseException, A]

/** [[Left]] with a [[ParseException]] type. */
type LeftParseExc = Left[ParseException, Nothing]

object LeftParseExc
{ /** Factory apply method to construct [[Left]] eith a [[ParseException]]. */
  def apply(detail: String): Left[ParseException, Nothing] = Left(ParseException(detail))
}

trait ParseExcMulti[E <: ParseException] extends ExcMulti[E], ParseException

object ParseExcMulti
{
  def apply[E <: ParseException](exceps: RArr[E]): ParseExcMulti[E] = ParseExcMultiGen(exceps)

  def apply[E <: ParseException](errors: E*)(using ClassTag[E]): ParseExcMulti[E] = ParseExcMultiGen(errors.toRArr)

  /** Implementation class for the general case of [[ExcMulti]]. */
  case class ParseExcMultiGen[E <: ParseException](mems: RArr[E]) extends ParseExcMulti[E]
}

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

object LexarExcLeft
{ /** Factory apply method to construct a lexar exception. */
  def apply(tp: TextPosn, detail: String): Left[LexarException, Nothing] = Left(LexarException(tp, detail))
}

/** Lexar [[Exception]] [[Either]]. */
type LexarExcEither[+A] = Either[LexarException, A]

/** Failure to retrieve a unique Setting of the correct type. */
sealed trait SettingFailException extends ParseException

/** Setting nor found [[Exception]]. */
class SettingNotFoundException(detail: String) extends Exception(detail -- "setting not found"), SettingFailException

/** Setting multiple values found [[Exception]]. */
class SettingMultiFoundException(n: Int, settingStr: String) extends Exception(s"$n settings of" -- settingStr -- "found."),
  SettingFailException

object SettingNotFoundException
{
  def apply(detail: String): SettingNotFoundException = new SettingNotFoundException(detail)
}

/** The expression can not construct / [[Unshow]] the given type. */
class ExprNotTypeExc(typeStr: String) extends Exception(s"Expression can not construct $typeStr type"), ParseException

object ExprNotTypeExc
{ /** Factory apply method to construct [[Exception]] for expression that can not construct / [[Unshow]] the given type. */
  def apply(typeStr: String): ExprNotTypeExc = new ExprNotTypeExc(typeStr)
}

/** [[Left]] with a [[ExprNotTypeExc]] type. */
type LeftExprNotType = Left[ExprNotTypeExc, Nothing]