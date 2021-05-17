/* Copyright 2018-21 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat
import pParse._

/** The base trait for the persistence of algebraic product types, including case classes. Note the arity of the product, its size is based on the
 *  number of logical parameters. For example, a LineSeg is a product 2, it has a start point and an end point, although its is stored as 4 parameters
 *  xStart, yStart, xEnd, yEnd. */
trait PersistProduct[R] extends /*ShowProductT[R] with */Persist[R]
{
  override def fromExpr(expr: ParseExpr): EMon[R] = expr match
  {
    case AlphaBracketExpr(IdentLowerToken(_, typeName), Arr1(ParenthBlock(sts, _, _))) if typeStr == typeName => ??? // fromParameterStatements(sts)
    //case AlphaBracketExpr(IdentUpperToken(fp, typeName), _) => fp.bad(typeName -- "does not equal" -- typeStr)
    case _ => expr.exprParseErr[R](this)
  }
}

trait PersistShowProductT[R] extends PersistProduct[R] with ShowProductT[R]

trait PersistShowerProduct[R <: Show] extends PersistProduct[R]

/** Persistence class for 3 logical parameter product types. */
class Persist3[A1, A2, A3, R](typeStr: String, name1: String, fArg1: R => A1, name2: String, fArg2: R => A2, name3: String, fArg3: R => A3,
  val newT: (A1, A2, A3) => R, opt3: Option[A3] = None, opt2: Option[A2] = None, opt1: Option[A1] = None)(implicit ev1: Persist[A1], ev2: Persist[A2],
  ev3: Persist[A3], eq1: EqT[A1], eq2: EqT[A2], eq3: EqT[A3]) extends Show3T[A1, A2, A3, R](typeStr, name1, fArg1, name2, fArg2, name3, fArg3, opt3,
  opt2, opt1) with PersistShowProductT[R]

object Persist3
{
  def apply[A1, A2, A3, R](typeStr: String, name1: String, fArg1: R => A1, name2: String, fArg2: R => A2, name3: String, fArg3: R => A3,
    newT: (A1, A2, A3) => R, opt3: Option[A3] = None, opt2: Option[A2] = None, opt1: Option[A1] = None)(implicit ev1: Persist[A1], ev2: Persist[A2],
    ev3: Persist[A3], eq1: EqT[A1], eq2: EqT[A2], eq3: EqT[A3]): Persist3[A1, A2, A3, R] =
    new Persist3(typeStr, name1, fArg1, name2, fArg2, name3, fArg3, newT, opt3, opt2, opt1)(ev1, ev2, ev3, eq1 , eq2, eq3)
}

/** Persistence class for case classes consisting of 3 Double parameters. */
/*
abstract class PersistD3[R](typeStr: String, name1: String, fArg1: R => Double, name2: String, fArg2: R => Double, name3: String, fArg3: R => Double,
  newT: (Double, Double, Double) => R) extends Persist3[Double, Double, Double, R](typeStr, name1, fArg1, name2, fArg2, name3, fArg3, newT)*/
