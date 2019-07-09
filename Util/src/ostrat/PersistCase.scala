/* Copyright 2018 Richard Oliver. Licensed under Apache Licence version 2.0 */
package ostrat
import pParse._

/** The base trait for the persistence of Case classes, aka Product types */
trait PersistCase[R] extends ShowCase[R] with PersistCompound[R]
{  
  def persistMems: Arr[Persist[_]]
  //override def showMems: Arr[Show[_]] = persistMems
  override def fromExpr(expr: ParseExpr): EMon[R] =  expr match
  {
    case AlphaBracketExpr(AlphaToken(_, typeName), Arr(ParenthBlock(sts, _, _))) if typeStr == typeName => fromParameterStatements(sts)
    case AlphaBracketExpr(AlphaToken(fp, typeName), _) => bad1(fp, typeName -- "does not equal" -- typeStr)
    case _ => expr.exprParseErr[R](this)
  }
}

/** Persistence class for single parameter case classes. 2 Methods not implemented. not sure about this class or its sub class PersistD1. */
class Persist1[A1, R](typeStr: String, fParam: R => A1, val newT: A1 => R)(implicit ev1: Persist[A1]) extends Show1(typeStr, fParam: R => A1) with
   PersistCase[R]
{
  override def persistMems: Arr[Persist[_]] = Arr(ev1)
  def fromClauses(clauses: Arr[Clause]): EMon[R] = fromClauses1(newT, clauses)
  def fromParameterStatements(sts: Arr[Statement]): EMon[R] = sts.errFun1(newT)(ev1)
}

/** Persistence class for case classes taking a single Double parameter. Not sure about this class. It is currently being used for Double based value
 *  classes. I think this is wrong and that they need their own trait class. */
class PersistD1[R](typeStr: String, fParam: R => Double, newT: Double => R) extends Persist1[Double, R](typeStr, fParam, newT)

/** Persistence class for 2 parameter case classes. */ 
class Persist2[A1, A2, R](typeStr: String, fParam: R => (A1, A2), val newT: (A1, A2) => R, opt2: Option[A2] = None, opt1: Option[A1] = None)(
  implicit ev1: Persist[A1], ev2: Persist[A2]) extends Show2[A1, A2, R](typeStr, fParam, opt2, opt1) with PersistCase[R]
{
  override def persistMems: Arr[Persist[_]] = Arr(ev1, ev2)
  override def fromClauses(clauses: Arr[Clause]): EMon[R] = fromClauses2(newT, clauses)
  override def fromParameterStatements(sts: Arr[Statement]): EMon[R] = sts.errFun2(newT)(ev1, ev2)
}

object Persist2
{ def apply[A1, A2, R](typeStr: String, fParam: R => (A1, A2), newT: (A1, A2) => R, opt2: Option[A2] = None, opt1: Option[A1] = None)(
  implicit ev1: Persist[A1], ev2: Persist[A2]): Persist2[A1, A2, R] = new Persist2(typeStr, fParam, newT, opt2, opt1)(ev1, ev2)
}
/** Persistence class for case classes consisting of 2 Double parameters. */
class PersistD2[R](typeStr: String, fParam: R => (Double, Double), newT: (Double, Double) => R) extends
   Persist2[Double, Double, R](typeStr, fParam, newT)

/** Persistence class for 3 parameter case classes. */   
class Persist3[A1, A2, A3, R](typeStr: String, fParam: R => (A1, A2, A3), val newT: (A1, A2, A3) => R, opt3: Option[A3] = None,
  opt2: Option[A2] = None, opt1: Option[A1] = None)(implicit ev1: Persist[A1], ev2: Persist[A2],
  ev3: Persist[A3]) extends Show3[A1, A2, A3, R](typeStr,fParam, opt3, opt2, opt1) with PersistCase[R]
{ override def persistMems: Arr[Persist[_]] = Arr(ev1, ev2, ev3)
  override def fromClauses(clauses: Arr[Clause]): EMon[R] = fromClauses3(newT, clauses)
  override def fromParameterStatements(sts: Arr[Statement]): EMon[R] = (sts, opt1, opt2, opt3) match
  {
    case (Arr(s1, s2, s3), _, _, _) => s1.errGet[A1](ev1).map3(s2.errGet[A2](ev2), s3.errGet[A3](ev3), (g1: A1, g2: A2, g3: A3) => newT(g1, g2, g3))
    case (Arr(s1, s2), _, _, Some(d3)) => s1.errGet[A1](ev1).map2(s2.errGet[A2](ev2), (g1: A1, g2: A2) => newT(g1, g2, d3))
    case (Arr(s1), _, Some(d2), Some(d3)) => s1.errGet[A1](ev1).map(g1 => newT(g1, d2, d3))
    case (Arr(), Some(d1), Some(d2), Some(d3)) => Good(newT(d1, d2, d3))
    case _ => bad1(sts.startPosn, sts.length.str -- "parameters, should be 3.")
  }
   // sts.errGet3(ev1, ev2, ev3).map{case (a, b, c) => newT(a, b, c)} // sts.errFun3(newT)(ev1, ev2, ev3)
}

object Persist3
{ def apply[A1, A2, A3, R](typeStr: String, fParam: R => (A1, A2, A3), newT: (A1, A2, A3) => R, opt3: Option[A3] = None, opt2: Option[A2] = None,
  opt1: Option[A1] = None)(implicit ev1: Persist[A1], ev2: Persist[A2], ev3: Persist[A3]): Persist3[A1, A2, A3, R] =
  new Persist3(typeStr, fParam, newT, opt3, opt2, opt1)(ev1, ev2, ev3)
}

/** Persistence class for case classes consisting of 3 Double parameters. */
abstract class PersistD3[R](typeStr: String, fParam: R => (Double, Double, Double), newT: (Double, Double, Double) => R) extends
   Persist3[Double, Double, Double, R](typeStr, fParam, newT)

/** Persistence class for 4 parameter case classes. */   
class Persist4[A1, A2, A3, A4, R](typeStr: String, fParam: R => (A1, A2, A3, A4), val newT: (A1, A2, A3, A4) => R, opt4: Option[A4],
  opt3: Option[A3] = None, opt2: Option[A2] = None, opt1: Option[A1] = None)(implicit ev1: Persist[A1], ev2: Persist[A2], ev3: Persist[A3],
  ev4: Persist[A4]) extends Show4(typeStr, fParam, opt4, opt3, opt2, opt1) with PersistCase[R]
{ override def persistMems: Arr[Persist[_]] = Arr(ev1, ev2, ev3, ev4)
  override def fromClauses(clauses: Arr[Clause]): EMon[R] = fromClauses4(newT, clauses)
  override def fromParameterStatements(sts: Arr[Statement]): EMon[R] = sts.errFun4(newT)(ev1, ev2, ev3, ev4)
}

object Persist4
{
  def apply[A1, A2, A3, A4, R](typeStr: String, fParam: R => (A1, A2, A3, A4), newT: (A1, A2, A3, A4) => R, opt4: Option[A4] = None, opt3: Option[A3] = None,
    opt2: Option[A2] = None, opt1: Option[A1] = None)(implicit ev1: Persist[A1], ev2: Persist[A2], ev3: Persist[A3], ev4: Persist[A4]):
    Persist4[A1, A2, A3, A4, R] = new Persist4(typeStr, fParam, newT, opt4, opt3, opt2, opt1)(ev1, ev2, ev3, ev4)
}
