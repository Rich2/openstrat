/* Copyright 2018-22 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat
import pParse._

/** A base trait for [[Show4T]] and [[Unshow4]], declares the common properties of name1 - 4 and opt1 - 4. */
trait TypeStr4[A1, A2, A3, A4] extends Any with TypeStr3[A1, A2, A3]
{ /** 4th parameter name. */
  def name4: String

  /** The optional default value for parameter 4. */
  def opt4: Option[A4]
}

/** Show type class for 4 parameter case classes. */
trait Show4T[A1, A2, A3, A4, R] extends ShowNT[R]

object Show4T
{
  class Show4TImp[A1, A2, A3, A4, R](val typeStr: String, val name1: String, val fArg1: R => A1, val name2: String, val fArg2: R => A2,
    val name3: String, val fArg3: R => A3, val name4: String, val fArg4: R => A4, val opt4: Option[A4] = None, opt3In: Option[A3] = None, opt2In: Option[A2] = None,
    opt1In: Option[A1] = None)(implicit ev1: ShowDecT[A1], ev2: ShowDecT[A2], ev3: ShowDecT[A3], ev4: ShowDecT[A4]) extends Show4T[A1, A2, A3, A4, R] with
    TypeStr4[A1, A2, A3, A4]
  {
    val opt3: Option[A3] = ife(opt4.nonEmpty, opt3In, None)
    val opt2: Option[A2] = ife(opt3.nonEmpty, opt2In, None)
    val opt1: Option[A1] = ife(opt2.nonEmpty, opt1In, None)

    final override def syntaxDepthT(obj: R): Int = ev1.syntaxDepthT(fArg1(obj)).max(ev2.syntaxDepthT(fArg2(obj))).max(ev3.syntaxDepthT(fArg3(obj))).
      max(ev4.syntaxDepthT(fArg4(obj))) + 1

    override def strDecs(obj: R, way: ShowStyle, maxPlaces: Int): Strings = Strings(ev1.showDecT(fArg1(obj), way, maxPlaces), ev2.showDecT(fArg2(obj), way, maxPlaces),
      ev3.showDecT(fArg3(obj), way, maxPlaces), ev4.showDecT(fArg4(obj), way, maxPlaces))
  }
}

/** UnShow class for 3 logical parameter product types. */
trait Unshow4[A1, A2, A3, A4, R] extends Unshow[R] with TypeStr4[A1, A2, A3, A4]
{
  /** The UnShow type class instance for type A1. */
  def ev1: Unshow[A1]

  /** The UnShow type class instance for type A2. */
  def ev2: Unshow[A2]

  /** The UnShow type class instance for type A3. */
  def ev3: Unshow[A3]

  /** The UnShow type class instance for type A4. */
  def ev4: Unshow[A4]

  def newT: (A1, A2, A3, A4) => R

  override def fromExpr(expr: Expr): EMon[R] = expr match
  {
    case AlphaBracketExpr(IdentUpperToken(_, typeName), Arr1(ParenthBlock(Arr4(s1, s2, s3, s4), _, _))) if typeStr == typeName =>
      ev1.fromExpr(s1.expr).map4(ev2.fromExpr(s2.expr), ev3.fromExpr(s3.expr), ev4.fromExpr(s4.expr)){
        (a1, a2, a3, a4) => newT(a1, a2, a3, a4) }

    case AlphaBracketExpr(IdentUpperToken(fp, typeName), _) => fp.bad(typeName -- "does not equal" -- typeStr)

    case ClausesExpr(clauses) if clauses.dataLength == 4 => ev1.fromExpr(clauses(0).expr).map4(
      ev2.fromExpr(clauses(1).expr), ev3.fromExpr(clauses(2).expr), ev4.fromExpr(clauses(3).expr)){ (a1, a2, a3, a4) =>
        newT(a1, a2, a3, a4) }

    case _ => expr.exprParseErr[R](this)
  }
}