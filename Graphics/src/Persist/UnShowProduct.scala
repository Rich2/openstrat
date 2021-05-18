/* Copyright 2018-21 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat
import pParse._

trait UnShowProduct[R] extends UnShow[R]
{
  override def fromExpr(expr: ParseExpr): EMon[R] = expr match
  {
    case AlphaBracketExpr(IdentLowerToken(_, typeName), Arr1(ParenthBlock(sts, _, _))) if typeStr == typeName => ??? // fromParameterStatements(sts)
    //case AlphaBracketExpr(IdentUpperToken(fp, typeName), _) => fp.bad(typeName -- "does not equal" -- typeStr)
    case _ => expr.exprParseErr[R](this)
  }
}

/** UnShow type class trait for a 2 element Product. */
trait UnShow2T[A1, A2, R] extends UnShowProduct[R]
{ /** 1st parameter name. */
  def name1: String

  /** Derive the 1st parameter from an object of type R. */
  def fArg1: R => A1

  def unShowA1: UnShow[A1]

  /** 2nd parameter name. */
  def name2: String

  /** Derive the 2nd parameter from an object of type R. */
  def fArg2: R => A2

  def unShowA2: UnShow[A2]
}

/** UnShow class for 3 logical parameter product types. */
class UnShow3[A1, A2, A3, R](val typeStr: String, name1: String, fArg1: R => A1, name2: String, fArg2: R => A2, name3: String, fArg3: R => A3,
  val newT: (A1, A2, A3) => R, opt3: Option[A3] = None, opt2: Option[A2] = None, opt1: Option[A1] = None)(implicit ev1: UnShow[A1], ev2: UnShow[A2],
  ev3: UnShow[A3], eq1: EqT[A1], eq2: EqT[A2], eq3: EqT[A3]) extends UnShowProduct[R]

/** Unshow trait for 5 parameter product / case classes. */
trait UnShow5[A1, A2, A3, A4, A5, R] extends UnShowProduct[R]
{
  def name1: String
  def fArg1: R => A1
  def name2: String
  def fArg2: R => A2
  def name3: String
  def fArg3: R => A3
  def name4: String
  def fArg4: R => A4
  def name5: String
  def fArg5: R => A5
  def newT: (A1, A2, A3, A4, A5) => R
  def opt5: Option[A5]
  def opt4: Option[A4] = None
  def opt3: Option[A3] = None
  def opt2: Option[A2] = None
  def opt1: Option[A1] = None
  implicit def ev1: UnShow[A1]
  implicit def ev2: UnShow[A2]
  implicit def ev3: UnShow[A3]
  implicit def ev4: UnShow[A4]
  implicit def ev5: UnShow[A5]
}

/** UnShow trait for 6 parameter product / case classes. */
trait UnShow6[A1, A2, A3, A4, A5, A6, R] extends UnShowProduct[R]
{ def name1: String
  def fArg1: R => A1
  def name2: String
  def fArg2: R => A2
  def name3: String
  def fArg3: R => A3
  def name4: String
  def fArg4: R => A4
  def name5: String
  def fArg5: R => A5
  def name6: String
  def fArg6: R => A6
  val newT: (A1, A2, A3, A4, A5, A6) => R
  def opt6: Option[A6]
  def opt5: Option[A5] = None
  def opt4: Option[A4] = None
  def opt3: Option[A3] = None
  def opt2: Option[A2] = None
  def opt1: Option[A1] = None
  implicit def ev1: UnShow[A1]
  implicit def ev2: UnShow[A2]
  implicit def ev3: UnShow[A3]
  implicit def ev4: UnShow[A4]
  implicit def ev5: UnShow[A5]
  implicit def ev6: UnShow[A6]
}
