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
class Persist1[A1, R](typeStr: String, fParam: R => A1, val newT: A1 => R)(implicit ev1: Persist[A1], eq1: Eq[A1]) extends Show1(typeStr, fParam: R => A1) with
   PersistCase[R]
{
  override def persistMems: Arr[Persist[_]] = Arr(ev1)
  def fromClauses(clauses: Arr[Clause]): EMon[R] = fromClauses1(newT, clauses)
  def fromParameterStatements(sts: Arr[Statement]): EMon[R] = (sts, opt1) match
  {
    case (Arr(s1), _) => s1.errGet[A1].map(g1 => newT(g1))
    case (Arr(), Some(d1)) => Good(newT(d1))
    case _ => bad1(sts.startPosn, sts.lenStr -- "parameters, should be 1.")
  }
}

/** Persistence class for case classes taking a single Double parameter. Not sure about this class. It is currently being used for Double based value
 *  classes. I think this is wrong and that they need their own trait class. */
class PersistD1[R](typeStr: String, fParam: R => Double, newT: Double => R) extends Persist1[Double, R](typeStr, fParam, newT)

/** Persistence class for 2 parameter case classes. */ 
class Persist2[A1, A2, R](typeStr: String, pName1: String, fArg1: R => A1, pName2: String, fArg2: R => A2, val newT: (A1, A2) => R, opt2: Option[A2] = None, opt1: Option[A1] = None)(
  implicit ev1: Persist[A1], ev2: Persist[A2], eq1: Eq[A1], eq2: Eq[A2]) extends Show2[A1, A2, R](typeStr, pName1, fArg1, pName2, fArg2, opt2, opt1) with
  PersistCase[R]
{
  override def persistMems: Arr[Persist[_]] = Arr(ev1, ev2)
  override def fromClauses(clauses: Arr[Clause]): EMon[R] = fromClauses2(newT, clauses)
  //override def fromParameterStatements(sts: Arr[Statement]): EMon[R] = sts.errFun2(newT)(ev1, ev2)
  override def fromParameterStatements(sts: Arr[Statement]): EMon[R] = (sts, opt1, opt2) match
  {
    case (Arr(s1, s2), _, _) => for { g1 <- s1.errGet[A1](ev1); g2 <- s2.errGet[A2](ev2) } yield newT(g1, g2)
    case (Arr(s1), _, Some(d2)) => s1.errGet[A1].map(g1 => newT(g1, d2))
    case (Arr(), Some(d1), Some(d2)) => Good(newT(d1, d2))
    case _ => bad1(sts.startPosn, sts.lenStr -- "parameters, should be 2.")
  }
}

object Persist2
{ def apply[A1, A2, R](typeStr: String, pName1: String, fArg1: R => A1, pName2: String, fArg2: R => A2, newT: (A1, A2) => R, opt2: Option[A2] = None, opt1: Option[A1] = None)(
  implicit ev1: Persist[A1], ev2: Persist[A2], eq1: Eq[A1], eq2: Eq[A2]): Persist2[A1, A2, R] =
  new Persist2(typeStr, pName1, fArg1, pName2, fArg2, newT, opt2, opt1)(ev1, ev2, eq1, eq2)
}

/** Persistence class for case classes consisting of 2 Int parameters. */
class PersistInt2[R](typeStr: String, pName1: String, fArg1: R => Int, pName2: String, fArg2: R => Int, newT: (Int, Int) => R) extends
  Persist2[Int, Int, R](typeStr, pName1, fArg1, pName2, fArg2, newT)

/** Persistence class for case classes consisting of 2 Double parameters. */
class PersistD2[R](typeStr: String, pName1: String, fArg1: R => Double, pName2: String, fArg2: R => Double, newT: (Double, Double) => R) extends
   Persist2[Double, Double, R](typeStr, pName1, fArg1, pName2, fArg2, newT)

/** Persistence class for 3 parameter case classes. */   
class Persist3[A1, A2, A3, R](typeStr: String, pName1: String, fArg1: R => A1, pName2: String, fArg2: R => A2, pName3: String, fArg3: R => A3,
  val newT: (A1, A2, A3) => R, opt3: Option[A3] = None, opt2: Option[A2] = None, opt1: Option[A1] = None)(
  implicit ev1: Persist[A1], ev2: Persist[A2], ev3: Persist[A3], eq1: Eq[A1], eq2: Eq[A2], eq3: Eq[A3]) extends
  Show3[A1, A2, A3, R](typeStr,fArg1, fArg2, fArg3, opt3, opt2, opt1) with PersistCase[R]
{
  override def persistMems: Arr[Persist[_]] = Arr(ev1, ev2, ev3)
  override def fromClauses(clauses: Arr[Clause]): EMon[R] = fromClauses3(newT, clauses)
  override def fromParameterStatements(sts: Arr[Statement]): EMon[R] = (sts, opt1, opt2, opt3) match
  {
    case (Arr(s1, s2, s3), _, _, _) => for { g1 <- s1.errGet[A1](ev1); g2 <- s2.errGet[A2](ev2); g3 <- s3.errGet[A3](ev3) } yield newT(g1, g2, g3)
    case (Arr(s1, s2), _, _, Some(d3)) => for { g1 <- s1.errGet[A1](ev1); g2 <- s2.errGet[A2](ev2) } yield newT(g1, g2, d3)
    case (Arr(s1), _, Some(d2), Some(d3)) => s1.errGet[A1](ev1).map(g1 => newT(g1, d2, d3))
    case (Arr(), Some(d1), Some(d2), Some(d3)) => Good(newT(d1, d2, d3))
    case _ => bad1(sts.startPosn, sts.lenStr -- "parameters, should be 3.")
  }
   // sts.errGet3(ev1, ev2, ev3).map{case (a, b, c) => newT(a, b, c)} // sts.errFun3(newT)(ev1, ev2, ev3)
}

object Persist3
{ def apply[A1, A2, A3, R](typeStr: String, pName1: String, fArg1: R => A1, pName2: String, fArg2: R => A2, pName3: String, fArg3: R => A3,
  newT: (A1, A2, A3) => R, opt3: Option[A3] = None, opt2: Option[A2] = None, opt1: Option[A1] = None)(
  implicit ev1: Persist[A1], ev2: Persist[A2], ev3: Persist[A3], eq1: Eq[A1], eq2: Eq[A2], eq3: Eq[A3]): Persist3[A1, A2, A3, R] =
  new Persist3(typeStr, pName1, fArg1, pName2, fArg2, pName3, fArg3, newT, opt3, opt2, opt1)(ev1, ev2, ev3, eq1 , eq2, eq3)
}

/** Persistence class for case classes consisting of 3 Double parameters. */
abstract class PersistD3[R](typeStr: String, pName1: String, fArg1: R => Double, pName2: String, fArg2: R => Double, pName3: String,
  fArg3: R => Double, newT: (Double, Double, Double) => R) extends Persist3[Double, Double, Double, R](
  typeStr, pName1, fArg1, pName2, fArg2, pName3, fArg3, newT)

/** Persistence class for 4 parameter case classes. */   
class Persist4[A1, A2, A3, A4, R](typeStr: String, fArg1: R => A1, fArg2: R => A2, fArg3: R => A3, fArg4: R => A4, val newT: (A1, A2, A3, A4) => R, opt4: Option[A4],
  opt3: Option[A3] = None, opt2: Option[A2] = None, opt1: Option[A1] = None)(implicit ev1: Persist[A1], ev2: Persist[A2], ev3: Persist[A3],
  ev4: Persist[A4], eq1: Eq[A1], eq2: Eq[A2], eq3: Eq[A3], eq4: Eq[A4]) extends Show4(typeStr, fArg1, fArg2, fArg3, fArg4, opt4, opt3, opt2, opt1) with PersistCase[R]
{ override def persistMems: Arr[Persist[_]] = Arr(ev1, ev2, ev3, ev4)
  override def fromClauses(clauses: Arr[Clause]): EMon[R] = fromClauses4(newT, clauses)
  override def fromParameterStatements(sts: Arr[Statement]): EMon[R] = (sts, opt1, opt2, opt3, opt4) match
  {
    case (Arr(s1, s2, s3, s4), _, _, _, _) =>
      for { g1 <- s1.errGet[A1](ev1); g2 <- s2.errGet[A2](ev2); g3 <- s3.errGet[A3](ev3); g4 <- s4.errGet[A4] } yield newT(g1, g2, g3, g4)
    case (Arr(s1, s2, s3), _, _, _, Some(d4)) =>
      for { g1 <- s1.errGet[A1](ev1); g2 <- s2.errGet[A2](ev2); g3 <- s3.errGet[A3](ev3) } yield newT(g1, g2, g3, d4)
    case (Arr(s1, s2), _, _, Some(d3), Some(d4)) => for { g1 <- s1.errGet[A1](ev1); g2 <- s2.errGet[A2](ev2) } yield newT(g1, g2, d3, d4)
    case (Arr(s1), _, Some(d2), Some(d3), Some(d4)) => s1.errGet[A1](ev1).map(g1 => newT(g1, d2, d3, d4))
    case (Arr(), Some(d1), Some(d2), Some(d3), Some(d4)) => Good(newT(d1, d2, d3, d4))
    case _ => bad1(sts.startPosn, sts.lenStr -- "parameters, should be 4.")
  }
}

object Persist4
{
  def apply[A1, A2, A3, A4, R](typeStr: String, fArg1: R => A1, fArg2: R => A2, fArg3: R => A3, fArg4: R => A4, newT: (A1, A2, A3, A4) => R, opt4: Option[A4] = None, opt3: Option[A3] = None,
    opt2: Option[A2] = None, opt1: Option[A1] = None)(implicit ev1: Persist[A1], ev2: Persist[A2], ev3: Persist[A3], ev4: Persist[A4], eq1: Eq[A1],
    eq2: Eq[A2], eq3: Eq[A3], eq4: Eq[A4]): Persist4[A1, A2, A3, A4, R] =
    new Persist4(typeStr, fArg1, fArg2, fArg3, fArg4, newT, opt4, opt3, opt2, opt1)(ev1, ev2, ev3, ev4, eq1, eq2, eq3, eq4)
}

/** Persistence class for 5 parameter case classes. */
class Persist5[A1, A2, A3, A4, A5, R](typeStr: String, fArg1: R => A1, fArg2: R => A2, fArg3: R => A3, fArg4: R => A4, fArg5: R => A5,
  val newT: (A1, A2, A3, A4, A5) => R, opt5: Option[A5], opt4: Option[A4] = None, opt3: Option[A3] = None, opt2: Option[A2] = None,
  opt1: Option[A1] = None)(implicit ev1: Persist[A1], ev2: Persist[A2], ev3: Persist[A3], ev4: Persist[A4], ev5: Persist[A5], eq1: Eq[A1],
  eq2: Eq[A2], eq3: Eq[A3], eq4: Eq[A4], eq5: Eq[A5]) extends Show5(typeStr, fArg1, fArg2, fArg3, fArg4, fArg5, opt5, opt4, opt3, opt2, opt1) with
  PersistCase[R]
{ override def persistMems: Arr[Persist[_]] = Arr(ev1, ev2, ev3, ev4)
  override def fromClauses(clauses: Arr[Clause]): EMon[R] = fromClauses5(newT, clauses)
  override def fromParameterStatements(sts: Arr[Statement]): EMon[R] = (sts, opt1, opt2, opt3, opt4, opt5) match
  {
    case (Arr(s1, s2, s3, s4, s5), _, _, _, _, _) =>
      for { g1 <- s1.errGet[A1](ev1); g2 <- s2.errGet[A2](ev2); g3 <- s3.errGet[A3](ev3); g4 <- s4.errGet[A4]; g5 <- s5.errGet[A5] } yield
        newT(g1, g2, g3, g4, g5)

    case (Arr(s1, s2, s3, s4), _, _, _, _, Some(d5)) =>
      for { g1 <- s1.errGet[A1](ev1); g2 <- s2.errGet[A2](ev2); g3 <- s3.errGet[A3](ev3); g4 <- s4.errGet[A4] } yield newT(g1, g2, g3, g4, d5)

    case (Arr(s1, s2, s3), _, _, _, Some(d4), Some(d5)) =>
      for { g1 <- s1.errGet[A1](ev1); g2 <- s2.errGet[A2](ev2); g3 <- s3.errGet[A3](ev3) } yield newT(g1, g2, g3, d4, d5)

    case (Arr(s1, s2), _, _, Some(d3), Some(d4), Some(d5)) => for { g1 <- s1.errGet[A1](ev1); g2 <- s2.errGet[A2](ev2) } yield
      newT(g1, g2, d3, d4, d5)

    case (Arr(s1), _, Some(d2), Some(d3), Some(d4), Some(d5)) => s1.errGet[A1](ev1).map(g1 => newT(g1, d2, d3, d4, d5))
    case (Arr(), Some(d1), Some(d2), Some(d3), Some(d4), Some(d5)) => Good(newT(d1, d2, d3, d4, d5))
    case _ => bad1(sts.startPosn, sts.lenStr -- "parameters, should be 4.")
  }
}
