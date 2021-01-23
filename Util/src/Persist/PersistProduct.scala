/* Copyright 2018-21 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat
import pParse._

/** The base trait for the persistence of algebraic product types, including case classes. Note the arity of the product, its size is based on the
 *  number of logical parameters. For example, a LineSeg is a product 2, it has a start point and an end point, although its is stored as 4 parameters
 *  xStart, yStart, xEnd, yEnd. */
trait PersistProduct[R] extends ShowProductT[R] with PersistCompound[R]
{
  override def fromExpr(expr: ParseExpr): EMon[R] = expr match
  {
    case AlphaBracketExpr(IdentifierLwToken(_, typeName), Arr1(ParenthBlock(sts, _, _))) if typeStr == typeName => ??? // fromParameterStatements(sts)
    //case AlphaBracketExpr(IdentUpperToken(fp, typeName), _) => fp.bad(typeName -- "does not equal" -- typeStr)
    case _ => expr.exprParseErr[R](this)
  }
}

/** Persistence class for product 2 type class. It ShowTs and UnShows objects with 2 logical parameters. */
class Persist2[A1, A2, R](typeStr: String, name1: String, fArg1: R => A1, name2: String, fArg2: R => A2, val newT: (A1, A2) => R,
  opt2: Option[A2] = None, opt1: Option[A1] = None)(implicit ev1: Persist[A1], ev2: Persist[A2], eq1: Eq[A1], eq2: Eq[A2]) extends
  Show2T[A1, A2, R](typeStr, name1, fArg1, name2, fArg2, opt2, opt1) with PersistProduct[R]

/** Factory object for Persist product 2 type class */
object Persist2
{ def apply[A1, A2, R](typeStr: String, name1: String, fArg1: R => A1, name2: String, fArg2: R => A2, newT: (A1, A2) => R,
    opt2: Option[A2] = None, opt1: Option[A1] = None)(implicit ev1: Persist[A1], ev2: Persist[A2], eq1: Eq[A1], eq2: Eq[A2]): Persist2[A1, A2, R] =
    new Persist2(typeStr, name1, fArg1, name2, fArg2, newT, opt2, opt1)(ev1, ev2, eq1, eq2)
}

/** Persistence class for case classes consisting of 2 Int parameters. */
class Persist2Ints[R](typeStr: String, name1: String, fArg1: R => Int, name2: String, fArg2: R => Int, newT: (Int, Int) => R) extends
  Persist2[Int, Int, R](typeStr, name1, fArg1, name2, fArg2, newT)

/** Persistence class for case classes consisting of 2 Double parameters. */
class Persist2Dbls[R](typeStr: String, name1: String, fArg1: R => Double, name2: String, fArg2: R => Double, newT: (Double, Double) => R) extends
   Persist2[Double, Double, R](typeStr, name1, fArg1, name2, fArg2, newT)

/** Persistence class for 3 logical parameter product types. */
class Persist3[A1, A2, A3, R](typeStr: String, name1: String, fArg1: R => A1, name2: String, fArg2: R => A2, name3: String, fArg3: R => A3,
  val newT: (A1, A2, A3) => R, opt3: Option[A3] = None, opt2: Option[A2] = None, opt1: Option[A1] = None)(implicit ev1: Persist[A1], ev2: Persist[A2],
  ev3: Persist[A3], eq1: Eq[A1], eq2: Eq[A2], eq3: Eq[A3]) extends
  Show3T[A1, A2, A3, R](typeStr, name1, fArg1, name2, fArg2, name3, fArg3, opt3, opt2, opt1) with PersistProduct[R]

object Persist3
{ def apply[A1, A2, A3, R](typeStr: String, name1: String, fArg1: R => A1, name2: String, fArg2: R => A2, name3: String, fArg3: R => A3,
  newT: (A1, A2, A3) => R, opt3: Option[A3] = None, opt2: Option[A2] = None, opt1: Option[A1] = None)(
  implicit ev1: Persist[A1], ev2: Persist[A2], ev3: Persist[A3], eq1: Eq[A1], eq2: Eq[A2], eq3: Eq[A3]): Persist3[A1, A2, A3, R] =
  new Persist3(typeStr, name1, fArg1, name2, fArg2, name3, fArg3, newT, opt3, opt2, opt1)(ev1, ev2, ev3, eq1 , eq2, eq3)
}

/** Persistence class for case classes consisting of 3 Double parameters. */
abstract class PersistD3[R](typeStr: String, name1: String, fArg1: R => Double, name2: String, fArg2: R => Double, name3: String, fArg3: R => Double,
  newT: (Double, Double, Double) => R) extends Persist3[Double, Double, Double, R](typeStr, name1, fArg1, name2, fArg2, name3, fArg3, newT)

/** Persistence class for 4 parameter case classes. */
class Persist4[A1, A2, A3, A4, R](typeStr: String, name1: String, fArg1: R => A1, name2: String, fArg2: R => A2, name3: String, fArg3: R => A3,
  name4: String, fArg4: R => A4, val newT: (A1, A2, A3, A4) => R, opt4: Option[A4], opt3: Option[A3] = None, opt2: Option[A2] = None,
  opt1: Option[A1] = None)(implicit ev1: Persist[A1], ev2: Persist[A2], ev3: Persist[A3], ev4: Persist[A4], eq1: Eq[A1], eq2: Eq[A2], eq3: Eq[A3],
  eq4: Eq[A4]) extends Show4T(typeStr, name1, fArg1, name2, fArg2, name3, fArg3, name4, fArg4, opt4, opt3, opt2, opt1) with PersistProduct[R]

object Persist4
{
  def apply[A1, A2, A3, A4, R](typeStr: String, name1: String, fArg1: R => A1, name2: String, fArg2: R => A2, name3: String, fArg3: R => A3,
    name4: String, fArg4: R => A4, newT: (A1, A2, A3, A4) => R, opt4: Option[A4] = None, opt3: Option[A3] = None,  opt2: Option[A2] = None,
    opt1: Option[A1] = None)(
    implicit ev1: Persist[A1], ev2: Persist[A2], ev3: Persist[A3], ev4: Persist[A4], eq1: Eq[A1],  eq2: Eq[A2], eq3: Eq[A3], eq4: Eq[A4]):
    Persist4[A1, A2, A3, A4, R] = new Persist4(typeStr, name1, fArg1, name2, fArg2, name3, fArg3, name4, fArg4, newT, opt4, opt3, opt2, opt1)(
      ev1, ev2, ev3, ev4, eq1, eq2, eq3, eq4)
}

/** Persistence class for 5 parameter case classes. */
class Persist5[A1, A2, A3, A4, A5, R](typeStr: String, name1: String, fArg1: R => A1, name2: String, fArg2: R => A2, name3: String, fArg3: R => A3,
  name4: String, fArg4: R => A4, name5: String, fArg5: R => A5, val newT: (A1, A2, A3, A4, A5) => R, opt5: Option[A5], opt4: Option[A4] = None,
  opt3: Option[A3] = None, opt2: Option[A2] = None, opt1: Option[A1] = None)(implicit ev1: Persist[A1], ev2: Persist[A2], ev3: Persist[A3],
  ev4: Persist[A4], ev5: Persist[A5]) extends
  Show5T(typeStr, name1, fArg1, name2, fArg2, name3, fArg3, name4, fArg4, name5, fArg5, opt5, opt4, opt3, opt2, opt1) with PersistProduct[R]

object Persist5
{
  def apply[A1, A2, A3, A4, A5, R](typeStr: String, name1: String, fArg1: R => A1, name2: String, fArg2: R => A2, name3: String, fArg3: R => A3,
    name4: String, fArg4: R => A4, name5: String, fArg5: R => A5, newT: (A1, A2, A3, A4, A5) => R, opt5: Option[A5] = None, opt4: Option[A4] = None,
    opt3: Option[A3] = None,  opt2: Option[A2] = None, opt1: Option[A1] = None)(implicit ev1: Persist[A1], ev2: Persist[A2], ev3: Persist[A3],
    ev4: Persist[A4], ev5: Persist[A5], eq1: Eq[A1],  eq2: Eq[A2], eq3: Eq[A3], eq4: Eq[A4], eq5: Eq[A5]): Persist5[A1, A2, A3, A4, A5, R] =
    new Persist5(typeStr, name1, fArg1, name2, fArg2, name3, fArg3, name4, fArg4, name5, fArg5, newT, opt5, opt4, opt3, opt2, opt1)(
    ev1, ev2, ev3, ev4, ev5)
}

/** Persistence class for 6 parameter case classes. */
class Persist6[A1, A2, A3, A4, A5, A6, R](typeStr: String, name1: String, fArg1: R => A1, name2: String, fArg2: R => A2,
  name3: String, fArg3: R => A3, name4: String, fArg4: R => A4, name5: String, fArg5: R => A5, name6: String, fArg6: R => A6,
  val newT: (A1, A2, A3, A4, A5, A6) => R, opt6: Option[A6], opt5: Option[A5], opt4: Option[A4] = None, opt3: Option[A3] = None,
  opt2: Option[A2] = None, opt1: Option[A1] = None)(implicit ev1: Persist[A1], ev2: Persist[A2], ev3: Persist[A3], ev4: Persist[A4], ev5: Persist[A5],
  ev6: Persist[A6], eq1: Eq[A1], eq2: Eq[A2], eq3: Eq[A3], eq4: Eq[A4], eq5: Eq[A5], eq6: Eq[A6]) extends
  Show6T(typeStr, name1, fArg1, name2, fArg2, name3, fArg3, name4, fArg4, name5, fArg5, name6, fArg6, opt6, opt5, opt4, opt3, opt2, opt1) with
  PersistProduct[R]

object Persist6
{
  def apply[A1, A2, A3, A4, A5, A6, R](typeStr: String, name1: String, fArg1: R => A1, name2: String, fArg2: R => A2, name3: String, fArg3: R => A3,
    name4: String, fArg4: R => A4, name5: String, fArg5: R => A5, name6: String, fArg6: R => A6, newT: (A1, A2, A3, A4, A5, A6) => R,
    opt6: Option[A6] = None, opt5: Option[A5] = None, opt4: Option[A4] = None, opt3: Option[A3] = None,  opt2: Option[A2] = None, opt1: Option[A1] = None)(
    implicit ev1: Persist[A1], ev2: Persist[A2], ev3: Persist[A3], ev4: Persist[A4], ev5: Persist[A5], ev6: Persist[A6], eq1: Eq[A1],  eq2: Eq[A2],
    eq3: Eq[A3], eq4: Eq[A4], eq5: Eq[A5], eq6: Eq[A6]):
  Persist6[A1, A2, A3, A4, A5, A6, R] =
    new Persist6(typeStr, name1, fArg1, name2, fArg2, name3, fArg3, name4, fArg4, name5, fArg5, name6, fArg6, newT,
    opt6, opt5, opt4, opt3, opt2, opt1)(ev1, ev2, ev3, ev4, ev5, ev6, eq1, eq2, eq3, eq4, eq5, eq6)
}