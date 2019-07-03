/* Copyright 2018 Richard Oliver. Licensed under Apache Licence version 2.0 */
package ostrat
import pParse._

/** The base trait for the persistence of Case classes, aka Product types */
trait PersistCase[R] extends ShowCase[R] with PersistCompound[R]
{  
  def persistMems: Arr[Persist[_]]
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
{ def fromClauses(clauses: Arr[Clause]): EMon[R] = fromClauses1(newT, clauses)
  def fromParameterStatements(sts: Arr[Statement]): EMon[R] = sts.errFun1(newT)(ev1)
}

/** Persistence class for case classes taking a single Double parameter. Not sure about this class. It is currently being used for Double based value
 *  classes. I think this is wrong and that they need their own trait class. */
class PersistD1[R](typeStr: String, fParam: R => Double, newT: Double => R) extends Persist1[Double, R](typeStr, fParam, newT)

/** Persistence class for 2 parameter case classes. */ 
class Persist2[A1, A2, R](typeStr: String, fParam: R => (A1, A2), val newT: (A1, A2) => R)(implicit ev1: Persist[A1], ev2: Persist[A2])
   extends Show2[A1, A2, R](typeStr, fParam) with PersistCase[R]
{
   
  override def fromClauses(clauses: Arr[Clause]): EMon[R] = fromClauses2(newT, clauses)
  override def fromParameterStatements(sts: Arr[Statement]): EMon[R] = sts.errFun2(newT)(ev1, ev2)
}

/** Persistence class for case classes consisting of 2 Double parameters. */
class PersistD2[R](typeStr: String, fParam: R => (Double, Double), newT: (Double, Double) => R) extends
   Persist2[Double, Double, R](typeStr, fParam, newT)

/** Persistence class for 3 parameter case classes. */   
class Persist3[A1, A2, A3, R](typeStr: String, fParam: R => (A1, A2, A3), val newT: (A1, A2, A3) => R)(implicit ev1: Persist[A1],
  ev2: Persist[A2], ev3: Persist[A3]) extends Show3[A1, A2, A3, R](typeStr,fParam) with PersistCase[R]
{ override def fromClauses(clauses: Arr[Clause]): EMon[R] = fromClauses3(newT, clauses)
  override def fromParameterStatements(sts: Arr[Statement]): EMon[R] = sts.errFun3(newT)(ev1, ev2, ev3)
}

/** Persistence class for case classes consisting of 3 Double parameters. */
abstract class PersistD3[R](typeStr: String, fParam: R => (Double, Double, Double), newT: (Double, Double, Double) => R) extends
   Persist3[Double, Double, Double, R](typeStr, fParam, newT)

/** Persistence class for 4 parameter case classes. */   
abstract class Persist4[A1, A2, A3, A4, R](typeStr: String, fParam: R => (A1, A2, A3, A4), val newT: (A1, A2, A3, A4) => R)(
    implicit ev1: Persist[A1], ev2: Persist[A2], ev3: Persist[A3], ev4: Persist[A4]) extends Show4(typeStr, fParam) with PersistCase[R]
{ override def fromClauses(clauses: Arr[Clause]): EMon[R] = fromClauses4(newT, clauses)
  override def fromParameterStatements(sts: Arr[Statement]): EMon[R] = sts.errFun4(newT)(ev1, ev2, ev3, ev4)
}
