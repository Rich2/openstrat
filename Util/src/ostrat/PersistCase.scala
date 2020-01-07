/* Copyright 2018 Richard Oliver. Licensed under Apache Licence version 2.0 */
package ostrat
import pParse._

/** The base trait for the persistence of Case classes, aka Product types */
trait PersistCase[R] extends ShowCase[R] with PersistCompound[R]
{  
  def persistMems: ArrOld[Persist[_]]
  //override def showMems: Arr[Show[_]] = persistMems
  override def fromExpr(expr: ParseExpr): EMon[R] =  expr match
  {
    case AlphaBracketExpr(IdentifierUpperToken(_, typeName), Refs1(ParenthBlock(sts, _, _))) if typeStr == typeName => fromParameterStatements(sts)
    case AlphaBracketExpr(IdentifierUpperToken(fp, typeName), _) => fp.bad(typeName -- "does not equal" -- typeStr)
    case _ => expr.exprParseErr[R](this)
  }
}

/** Persistence class for single parameter case classes. 2 Methods not implemented. not sure about this class or its sub class PersistD1. */
class Persist1[A1, R](typeStr: String, name1: String, fArg1: R => A1, val newT: A1 => R)(implicit ev1: Persist[A1], eq1: Eq[A1]) extends
  Show1(typeStr, name1,fArg1: R => A1) with PersistCase[R]
{
  override def persistMems: ArrOld[Persist[_]] = Arr(ev1)
  def fromClauses(clauses: Refs[Clause]): EMon[R] = fromClauses1(newT, clauses)
  def fromParameterStatements(sts: Refs[Statement]): EMon[R] = (sts, opt1) match
  {
    case (Refs1(s1), _) => s1.errGet[A1].map(g1 => newT(g1))
    case (Refs0(), Some(d1)) => Good(newT(d1))
    case _ => sts.startPosn.bad(sts.lenStr -- "parameters, should be 1.")
  }
}

/** Persistence class for case classes taking a single Double parameter. Not sure about this class. It is currently being used for Double based value
 *  classes. I think this is wrong and that they need their own trait class. */
class PersistDbl1[R](typeStr: String, name1: String, fArg1: R => Double, newT: Double => R) extends Persist1[Double, R](typeStr, name1, fArg1, newT)

class PersistInt1[R](typeStr: String, name1: String, fArg1: R => Int, newT: Int => R) extends Persist1[Int, R](typeStr, name1, fArg1, newT)

/** Persistence class for 2 parameter case classes. */ 
class Persist2[A1, A2, R](typeStr: String, name1: String, fArg1: R => A1, name2: String, fArg2: R => A2, val newT: (A1, A2) => R,
  opt2: Option[A2] = None, opt1: Option[A1] = None)(implicit ev1: Persist[A1], ev2: Persist[A2], eq1: Eq[A1], eq2: Eq[A2]) extends
  Show2[A1, A2, R](typeStr, name1, fArg1, name2, fArg2, opt2, opt1) with PersistCase[R]
{
  override def persistMems: ArrOld[Persist[_]] = Arr(ev1, ev2)
  override def fromClauses(clauses: Refs[Clause]): EMon[R] = fromClauses2(newT, clauses)
  //override def fromParameterStatements(sts: Arr[Statement]): EMon[R] = sts.errFun2(newT)(ev1, ev2)
  override def fromParameterStatements(sts: Refs[Statement]): EMon[R] = (sts, opt1, opt2) match
  {
    case (Refs2(s1, s2), _, _) => for { g1 <- s1.errGet[A1](ev1); g2 <- s2.errGet[A2](ev2) } yield newT(g1, g2)
    case (Refs1(s1), _, Some(d2)) => s1.errGet[A1].map(g1 => newT(g1, d2))
    case (Refs0(), Some(d1), Some(d2)) => Good(newT(d1, d2))
    case _ => sts.startPosn.bad(sts.lenStr -- "parameters, should be 2.")
  }
}

object Persist2
{ def apply[A1, A2, R](typeStr: String, name1: String, fArg1: R => A1, name2: String, fArg2: R => A2, newT: (A1, A2) => R, opt2: Option[A2] = None, opt1: Option[A1] = None)(
  implicit ev1: Persist[A1], ev2: Persist[A2], eq1: Eq[A1], eq2: Eq[A2]): Persist2[A1, A2, R] =
  new Persist2(typeStr, name1, fArg1, name2, fArg2, newT, opt2, opt1)(ev1, ev2, eq1, eq2)
}

/** Persistence class for case classes consisting of 2 Int parameters. */
class PersistInt2[R](typeStr: String, name1: String, fArg1: R => Int, name2: String, fArg2: R => Int, newT: (Int, Int) => R) extends
  Persist2[Int, Int, R](typeStr, name1, fArg1, name2, fArg2, newT)

/** Persistence class for case classes consisting of 2 Double parameters. */
class PersistD2[R](typeStr: String, name1: String, fArg1: R => Double, name2: String, fArg2: R => Double, newT: (Double, Double) => R) extends
   Persist2[Double, Double, R](typeStr, name1, fArg1, name2, fArg2, newT)

/** Persistence class for 3 parameter case classes. */   
class Persist3[A1, A2, A3, R](typeStr: String, name1: String, fArg1: R => A1, name2: String, fArg2: R => A2, name3: String, fArg3: R => A3,
  val newT: (A1, A2, A3) => R, opt3: Option[A3] = None, opt2: Option[A2] = None, opt1: Option[A1] = None)(
  implicit ev1: Persist[A1], ev2: Persist[A2], ev3: Persist[A3], eq1: Eq[A1], eq2: Eq[A2], eq3: Eq[A3]) extends
  Show3[A1, A2, A3, R](typeStr, name1, fArg1, name2, fArg2, name3, fArg3, opt3, opt2, opt1) with PersistCase[R]
{
  override def persistMems: ArrOld[Persist[_]] = Arr(ev1, ev2, ev3)
  override def fromClauses(clauses: Refs[Clause]): EMon[R] = fromClauses3(newT, clauses)
  override def fromParameterStatements(sts: Refs[Statement]): EMon[R] = (sts, opt1, opt2, opt3) match
  {
    case (Refs3(s1, s2, s3), _, _, _) => for { g1 <- s1.errGet[A1](ev1); g2 <- s2.errGet[A2](ev2); g3 <- s3.errGet[A3](ev3) } yield newT(g1, g2, g3)
    case (Refs2(s1, s2), _, _, Some(d3)) => for { g1 <- s1.errGet[A1](ev1); g2 <- s2.errGet[A2](ev2) } yield newT(g1, g2, d3)
    case (Refs1(s1), _, Some(d2), Some(d3)) => s1.errGet[A1](ev1).map(g1 => newT(g1, d2, d3))
    case (Refs0(), Some(d1), Some(d2), Some(d3)) => Good(newT(d1, d2, d3))
    case _ => sts.startPosn.bad(sts.lenStr -- "parameters, should be 3.")
  }
   // sts.errGet3(ev1, ev2, ev3).map{case (a, b, c) => newT(a, b, c)} // sts.errFun3(newT)(ev1, ev2, ev3)
}

object Persist3
{ def apply[A1, A2, A3, R](typeStr: String, name1: String, fArg1: R => A1, name2: String, fArg2: R => A2, name3: String, fArg3: R => A3,
  newT: (A1, A2, A3) => R, opt3: Option[A3] = None, opt2: Option[A2] = None, opt1: Option[A1] = None)(
  implicit ev1: Persist[A1], ev2: Persist[A2], ev3: Persist[A3], eq1: Eq[A1], eq2: Eq[A2], eq3: Eq[A3]): Persist3[A1, A2, A3, R] =
  new Persist3(typeStr, name1, fArg1, name2, fArg2, name3, fArg3, newT, opt3, opt2, opt1)(ev1, ev2, ev3, eq1 , eq2, eq3)
}

/** Persistence class for case classes consisting of 3 Double parameters. */
abstract class PersistD3[R](typeStr: String, name1: String, fArg1: R => Double, name2: String, fArg2: R => Double, name3: String,
  fArg3: R => Double, newT: (Double, Double, Double) => R) extends Persist3[Double, Double, Double, R](
  typeStr, name1, fArg1, name2, fArg2, name3, fArg3, newT)

/** Persistence class for 4 parameter case classes. */   
class Persist4[A1, A2, A3, A4, R](typeStr: String, name1: String, fArg1: R => A1, name2: String, fArg2: R => A2, name3: String, fArg3: R => A3,
  name4: String, fArg4: R => A4, val newT: (A1, A2, A3, A4) => R, opt4: Option[A4], opt3: Option[A3] = None, opt2: Option[A2] = None,
  opt1: Option[A1] = None)(implicit ev1: Persist[A1], ev2: Persist[A2], ev3: Persist[A3], ev4: Persist[A4], eq1: Eq[A1], eq2: Eq[A2], eq3: Eq[A3],
  eq4: Eq[A4]) extends Show4(typeStr, name1, fArg1, name2, fArg2, name3, fArg3, name4, fArg4, opt4, opt3, opt2, opt1) with PersistCase[R]
{ override def persistMems: ArrOld[Persist[_]] = Arr(ev1, ev2, ev3, ev4)
  override def fromClauses(clauses: Refs[Clause]): EMon[R] = fromClauses4(newT, clauses)
  override def fromParameterStatements(sts: Refs[Statement]): EMon[R] = (sts, opt1, opt2, opt3, opt4) match
  {
    case (Refs4(s1, s2, s3, s4), _, _, _, _) =>
      for { g1 <- s1.errGet[A1](ev1); g2 <- s2.errGet[A2](ev2); g3 <- s3.errGet[A3](ev3); g4 <- s4.errGet[A4] } yield newT(g1, g2, g3, g4)
    case (Refs3(s1, s2, s3), _, _, _, Some(d4)) =>
      for { g1 <- s1.errGet[A1](ev1); g2 <- s2.errGet[A2](ev2); g3 <- s3.errGet[A3](ev3) } yield newT(g1, g2, g3, d4)
    case (Refs2(s1, s2), _, _, Some(d3), Some(d4)) => for { g1 <- s1.errGet[A1](ev1); g2 <- s2.errGet[A2](ev2) } yield newT(g1, g2, d3, d4)
    case (Refs1(s1), _, Some(d2), Some(d3), Some(d4)) => s1.errGet[A1](ev1).map(g1 => newT(g1, d2, d3, d4))
    case (Refs0(), Some(d1), Some(d2), Some(d3), Some(d4)) => Good(newT(d1, d2, d3, d4))
    case _ => sts.startPosn.bad(sts.lenStr -- "parameters, should be 4.")
  }
}

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
  ev4: Persist[A4], ev5: Persist[A5], eq1: Eq[A1], eq2: Eq[A2], eq3: Eq[A3], eq4: Eq[A4], eq5: Eq[A5]) extends
  Show5(typeStr, name1, fArg1, name2, fArg2, name3, fArg3, name4, fArg4, name5, fArg5, opt5, opt4, opt3, opt2, opt1) with PersistCase[R]
{ override def persistMems: ArrOld[Persist[_]] = Arr(ev1, ev2, ev3, ev4, ev5)
  override def fromClauses(clauses: Refs[Clause]): EMon[R] = fromClauses5(newT, clauses)
  override def fromParameterStatements(sts: Refs[Statement]): EMon[R] = (sts, opt1, opt2, opt3, opt4, opt5) match
  {
    case (Refs5(s1, s2, s3, s4, s5), _, _, _, _, _) =>
      for { g1 <- s1.errGet[A1](ev1); g2 <- s2.errGet[A2](ev2); g3 <- s3.errGet[A3](ev3); g4 <- s4.errGet[A4]; g5 <- s5.errGet[A5] } yield
        newT(g1, g2, g3, g4, g5)

    case (Refs4(s1, s2, s3, s4), _, _, _, _, Some(d5)) =>
      for { g1 <- s1.errGet[A1](ev1); g2 <- s2.errGet[A2](ev2); g3 <- s3.errGet[A3](ev3); g4 <- s4.errGet[A4] } yield newT(g1, g2, g3, g4, d5)

    case (Refs3(s1, s2, s3), _, _, _, Some(d4), Some(d5)) =>
      for { g1 <- s1.errGet[A1](ev1); g2 <- s2.errGet[A2](ev2); g3 <- s3.errGet[A3](ev3) } yield newT(g1, g2, g3, d4, d5)

    case (Refs2(s1, s2), _, _, Some(d3), Some(d4), Some(d5)) => for { g1 <- s1.errGet[A1](ev1); g2 <- s2.errGet[A2](ev2) } yield
      newT(g1, g2, d3, d4, d5)

    case (Refs1(s1), _, Some(d2), Some(d3), Some(d4), Some(d5)) => s1.errGet[A1](ev1).map(g1 => newT(g1, d2, d3, d4, d5))
    case (Refs0(), Some(d1), Some(d2), Some(d3), Some(d4), Some(d5)) => Good(newT(d1, d2, d3, d4, d5))
    case _ => sts.startPosn.bad(sts.lenStr -- "parameters, should be 4.")
  }
}

object Persist5
{
  def apply[A1, A2, A3, A4, A5, R](typeStr: String, name1: String, fArg1: R => A1, name2: String, fArg2: R => A2, name3: String, fArg3: R => A3,
    name4: String, fArg4: R => A4, name5: String, fArg5: R => A5, newT: (A1, A2, A3, A4, A5) => R, opt5: Option[A5] = None, opt4: Option[A4] = None,
    opt3: Option[A3] = None,  opt2: Option[A2] = None, opt1: Option[A1] = None)(implicit ev1: Persist[A1], ev2: Persist[A2], ev3: Persist[A3],
    ev4: Persist[A4], ev5: Persist[A5], eq1: Eq[A1],  eq2: Eq[A2], eq3: Eq[A3], eq4: Eq[A4], eq5: Eq[A5]):
  Persist5[A1, A2, A3, A4, A5, R] =
    new Persist5(typeStr, name1, fArg1, name2, fArg2, name3, fArg3, name4, fArg4, name5, fArg5, newT, opt5, opt4, opt3, opt2, opt1)(
    ev1, ev2, ev3, ev4, ev5, eq1, eq2, eq3, eq4, eq5)
}

/** Persistence class for 6 parameter case classes. */
class Persist6[A1, A2, A3, A4, A5, A6, R](typeStr: String, name1: String, fArg1: R => A1, name2: String, fArg2: R => A2,
  name3: String, fArg3: R => A3, name4: String, fArg4: R => A4, name5: String, fArg5: R => A5, name6: String, fArg6: R => A6,
  val newT: (A1, A2, A3, A4, A5, A6) => R, opt6: Option[A6], opt5: Option[A5], opt4: Option[A4] = None, opt3: Option[A3] = None,
  opt2: Option[A2] = None, opt1: Option[A1] = None)(implicit ev1: Persist[A1], ev2: Persist[A2], ev3: Persist[A3], ev4: Persist[A4], ev5: Persist[A5],
  ev6: Persist[A6], eq1: Eq[A1], eq2: Eq[A2], eq3: Eq[A3], eq4: Eq[A4], eq5: Eq[A5], eq6: Eq[A6]) extends
  Show6(typeStr, name1, fArg1, name2, fArg2, name3, fArg3, name4, fArg4, name5, fArg5, name6, fArg6, opt6, opt5, opt4, opt3, opt2, opt1) with
  PersistCase[R]
{ override def persistMems: ArrOld[Persist[_]] = Arr(ev1, ev2, ev3, ev4, ev5, ev6)
  override def fromClauses(clauses: Refs[Clause]): EMon[R] = fromClauses6(newT, clauses)
  override def fromParameterStatements(sts: Refs[Statement]): EMon[R] = (sts, opt1, opt2, opt3, opt4, opt5, opt6) match
  {
    case (Refs6(s1, s2, s3, s4, s5, s6), _, _, _, _, _, _) =>
      for { g1 <- s1.errGet[A1](ev1); g2 <- s2.errGet[A2](ev2); g3 <- s3.errGet[A3](ev3); g4 <- s4.errGet[A4]; g5 <- s5.errGet[A5];
            g6 <- s6.errGet[A6]} yield
        newT(g1, g2, g3, g4, g5, g6)

    case (Refs5(s1, s2, s3, s4, s5), _, _, _, _, _, Some(d6)) =>
      for { g1 <- s1.errGet[A1](ev1); g2 <- s2.errGet[A2](ev2); g3 <- s3.errGet[A3](ev3); g4 <- s4.errGet[A4]; g5 <- s5.errGet[A5] } yield
        newT(g1, g2, g3, g4, g5, d6)

    case (Refs4(s1, s2, s3, s4), _, _, _, _, Some(d5), Some(d6)) =>
      for { g1 <- s1.errGet[A1](ev1); g2 <- s2.errGet[A2](ev2); g3 <- s3.errGet[A3](ev3); g4 <- s4.errGet[A4] } yield newT(g1, g2, g3, g4, d5, d6)

    case (Refs3(s1, s2, s3), _, _, _, Some(d4), Some(d5), Some(d6)) =>
      for { g1 <- s1.errGet[A1](ev1); g2 <- s2.errGet[A2](ev2); g3 <- s3.errGet[A3](ev3) } yield newT(g1, g2, g3, d4, d5, d6)

    case (Refs2(s1, s2), _, _, Some(d3), Some(d4), Some(d5), Some(d6)) => for { g1 <- s1.errGet[A1](ev1); g2 <- s2.errGet[A2](ev2) } yield
      newT(g1, g2, d3, d4, d5, d6)

    case (Refs1(s1), _, Some(d2), Some(d3), Some(d4), Some(d5), Some(d6)) => s1.errGet[A1](ev1).map(g1 => newT(g1, d2, d3, d4, d5, d6))
    case (Refs0(), Some(d1), Some(d2), Some(d3), Some(d4), Some(d5), Some(d6)) => Good(newT(d1, d2, d3, d4, d5, d6))
    case _ => sts.startPosn.bad(sts.lenStr -- "parameters, should be 4.")
  }
}

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