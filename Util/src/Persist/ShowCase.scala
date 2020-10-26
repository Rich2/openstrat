/* Copyright 2018-20 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat

/** The base trait for the persistence of Case classes, aka Product types */
trait ShowCase[R] extends ShowCompound[R]
{ def showMems(): Arr[Show[_]]
  final override def syntaxDepth: Int = showMems().fMax(_.syntaxDepth) + 1
  def showSemiNames(obj: R): String
  def showCommaNames(obj: R): String
}

/** Show type class for 1 parameter case classes. */
abstract class Show1[A1, R](val typeStr: String, name1: String, fArg1: R => A1, val opt1: Option[A1] = None)(implicit ev1: Show[A1], eq1: Eq[A1])
  extends EqCase1[A1, R](fArg1) with ShowCase[R]
{
  final override def showMems(): Arr[Show[_]] = Arr(ev1)
  def showSemi(obj: R): String = ev1.showComma(fArg1(obj))
  def showComma(obj: R): String = ev1.show(fArg1(obj))
  override def showSemiNames(obj: R): String = name1 -:- ev1.showComma(fArg1(obj))
  override def showCommaNames(obj: R): String = name1 -:- ev1.show(fArg1(obj))
}

/** Show type class for 2 parameter case classes. */
class Show2[A1, A2, R](val typeStr: String, name1: String, fArg1: R => A1, name2: String, fArg2: R => A2, val opt2: Option[A2] = None, opt1In: Option[A1] = None)(
  implicit ev1: Show[A1], ev2: Show[A2], eq1: Eq[A1], eq2: Eq[A2]) extends EqCase2[A1, A2, R](fArg1, fArg2) with ShowCase[R]
{
  val opt1: Option[A1] = ife(opt2.nonEmpty, opt1In, None)
  final override def showMems(): Arr[Show[_]] = Arr(ev1, ev2)
  override def showSemi(obj: R): String = ev1.showComma(fArg1(obj)) + "; " + ev2.showComma(fArg2(obj))
  override def showComma(obj: R): String = ev1.show(fArg1(obj)) + ", " + ev2.show(fArg2(obj))
  override def showSemiNames(obj: R): String = name1 -:- ev1.showComma(fArg1(obj)) + "; " + name2 -:- ev2.showComma(fArg2(obj))
  override def showCommaNames(obj: R): String = name1 -:- ev1.show(fArg1(obj)) + ", " + name2 -:- ev2.show(fArg2(obj))
}

/** Show type class for 3 parameter case classes. */
class Show3[A1, A2, A3, R](val typeStr: String, name1: String, fArg1: R => A1, name2: String, fArg2: R => A2, name3: String,
  fArg3: R => A3, val opt3: Option[A3] = None, opt2In: Option[A2] = None, opt1In: Option[A1] = None)(
  implicit ev1: Show[A1], ev2: Show[A2], ev3: Show[A3], eq1: Eq[A1], eq2: Eq[A2], eq3: Eq[A3]) extends
  EqCase3[A1, A2, A3, R](fArg1, fArg2, fArg3) with ShowCase[R]
{
  val opt2: Option[A2] = ife(opt3.nonEmpty, opt2In, None)
  val opt1: Option[A1] = ife(opt2.nonEmpty, opt1In, None)
  val defaultNum = ife3(opt3.isEmpty, 0, opt2.isEmpty, 1, opt1.isEmpty, 2, 3)
  override def showMems(): Arr[Show[_]] = Arr(ev1, ev2, ev3)

  final override def showSemi(obj: R): String =
  { val p1 = fArg1(obj)
    val p2 = fArg2(obj)
    val p3 = fArg3(obj)
    (opt1, opt2, opt3) match
    {
      case (Some(v1), Some(v2), Some(v3)) if v1 == p1 & v2 == p2 & v3 == p3 => ""
      case (_, Some(v2), Some(v3)) if v2 == p2 & v3 == p3 => ev1.showComma(p1)
      case (_, _, Some(v3)) if v3 == p3 => ev1.showComma(p1).appendSemicolons(ev2.showComma(p2))
      case _ => ev1.showComma(p1).appendSemicolons(ev2.showComma(p2), ev3.showComma(p3))
    }
  }

  final override def showComma(obj: R): String =
  { val p1 = fArg1(obj)
    val p2 = fArg2(obj)
    val p3 = fArg3(obj)

    (opt1, opt2, opt3) match
    { case (Some(v1), Some(v2), Some(v3)) if v1 == p1 & v2 == p2 & v3 == p3 => ""
      case (_, Some(v2), Some(v3)) if v2 == p2 & v3 == p3 => ev1.show(p1) + ","
      case (_, _, Some(v3)) if v3 == p3 => ev1.showComma(p1).appendCommas(ev2.showComma(p2))
      case _ => ev1.showComma(p1).appendCommas(ev2.showComma(p2), ev3.showComma(p3))
    }
  }
  override def showSemiNames(obj: R): String = name1 -:- ev1.showComma(fArg1(obj)) + "; " + name2 -:- ev2.showComma(fArg2(obj)) + "; " +
    name3 -:- ev3.showComma(fArg3(obj))
  
  override def showCommaNames(obj: R): String = name1 -:- ev1.show(fArg1(obj)) + ", " + name2 -:- ev2.show(fArg2(obj)) + name3 -:- ev3.show(fArg3(obj))
}

/** Show type class for 4 parameter case classes. */
abstract class Show4[A1, A2, A3, A4, R](val typeStr: String, name1: String, fArg1: R => A1, name2: String, fArg2: R => A2, name3: String,
  fArg3: R => A3, name4: String, fArg4: R => A4, val opt4: Option[A4] = None, opt3In: Option[A3] = None, opt2In: Option[A2] = None,
  opt1In: Option[A1] = None)(implicit ev1: Show[A1], ev2: Show[A2],  ev3: Show[A3], ev4: Show[A4], eq1: Eq[A1], eq2: Eq[A2], eq3: Eq[A3],
  eq4: Eq[A4]) extends EqCase4[A1, A2, A3, A4, R](fArg1, fArg2, fArg3, fArg4)(eq1, eq2, eq3, eq4) with ShowCase[R]
{
  val opt3: Option[A3] = ife(opt4.nonEmpty, opt3In, None)
  val opt2: Option[A2] = ife(opt3.nonEmpty, opt2In, None)
  val opt1: Option[A1] = ife(opt2.nonEmpty, opt1In, None)

  final override def showMems(): Arr[Show[_]] = Arr(ev1, ev2, ev3, ev4)

  override def showSemi(obj: R): String =
  { val p1 = fArg1(obj)
    val p2 = fArg2(obj)
    val p3 = fArg3(obj)
    val p4 = fArg4(obj)
    ev1.showComma(p1).appendSemicolons(ev2.showComma(p2), ev3.showComma(p3), ev4.showComma(p4))
  }

  final override def showComma(obj: R): String =
  { val p1 = fArg1(obj)
    val p2 = fArg2(obj)
    val p3 = fArg3(obj)
    val p4 = fArg4(obj)
    ev1.show(p1).appendCommas(ev2.show(p2), ev3.show(p3), ev4.show(p4))
  }

  override def showSemiNames(obj: R): String = name1 -:- ev1.showComma(fArg1(obj)) + "; " + name2 -:- ev2.showComma(fArg2(obj)) + "; " +
    name3 -:- ev3.showComma(fArg3(obj)) + "; " + name4 -:- ev4.showComma(fArg4(obj))
  
  override def showCommaNames(obj: R): String = name1 -:- ev1.show(fArg1(obj)) + ", " + name2 -:- ev2.show(fArg2(obj)) + name3 -:- ev3.show(fArg3(obj)) +
    name4 -:- ev4.show(fArg4(obj))
}

/** Show type class for 5 parameter case classes. */
class Show5[A1, A2, A3, A4, A5, R](val typeStr: String, name1: String, fArg1: R => A1, name2: String, fArg2: R => A2, name3: String, fArg3: R => A3,
  name4: String, fArg4: R => A4, name5: String, fArg5: R => A5, val opt5: Option[A5], opt4In: Option[A4] = None, opt3In: Option[A3] = None,
  opt2In: Option[A2] = None, opt1In: Option[A1] = None)(implicit ev1: Show[A1], ev2: Show[A2], ev3: Show[A3], ev4: Show[A4], ev5: Show[A5],
  eq1: Eq[A1], eq2: Eq[A2], eq3: Eq[A3], eq4: Eq[A4], eq5: Eq[A5]) extends EqCase5[A1, A2, A3, A4, A5, R](fArg1, fArg2, fArg3, fArg4, fArg5) with
  ShowCase[R]
{
  val opt4: Option[A4] = ife(opt5.nonEmpty, opt4In, None)
  val opt3: Option[A3] = ife(opt4.nonEmpty, opt3In, None)
  val opt2: Option[A2] = ife(opt3.nonEmpty, opt2In, None)
  val opt1: Option[A1] = ife(opt2.nonEmpty, opt1In, None)

  final override def showMems(): Arr[Show[_]] = Arr(ev1, ev2, ev3, ev4, ev5)

  override def showSemi(obj: R): String =
  { val p1 = fArg1(obj)
    val p2 = fArg2(obj)
    val p3 = fArg3(obj)
    val p4 = fArg4(obj)
    val p5 = fArg5(obj)
    ev1.showComma(p1).appendSemicolons(ev2.showComma(p2), ev3.showComma(p3), ev4.showComma(p4), ev5.showComma(p5))
  }

  final override def showComma(obj: R): String =
  { val p1 = fArg1(obj)
    val p2 = fArg2(obj)
    val p3 = fArg3(obj)
    val p4 = fArg4(obj)
    val p5 = fArg5(obj)
    ev1.show(p1).appendCommas(ev2.show(p2), ev3.show(p3), ev4.show(p4), ev5.show(p5))
  }

  override def showSemiNames(obj: R): String = name1 -:- ev1.showComma(fArg1(obj)) + "; " + name2 -:- ev2.showComma(fArg2(obj)) + "; " +
    name3 -:- ev3.showComma(fArg3(obj)) + "; " + name4 -:- ev4.showComma(fArg4(obj))+ name5 -:- ev5.showComma(fArg5(obj))
  
  override def showCommaNames(obj: R): String = name1 -:- ev1.show(fArg1(obj)) + ", " + name2 -:- ev2.show(fArg2(obj)) + name3 -:- ev3.show(fArg3(obj)) +
    name4 -:- ev4.show(fArg4(obj))+ name5 -:- ev5.show(fArg5(obj))
}

object Show5
{
  def apply[A1, A2, A3, A4, A5, R](typeStr: String, name1: String, fArg1: R => A1, name2: String, fArg2: R => A2, name3: String, fArg3: R => A3,
    name4: String, fArg4: R => A4, name5: String, fArg5: R => A5, opt5: Option[A5] = None, opt4: Option[A4] = None, opt3: Option[A3] = None,
    opt2: Option[A2] = None, opt1: Option[A1] = None)(implicit ev1: Show[A1], ev2: Show[A2], ev3: Show[A3], ev4: Show[A4], ev5: Show[A5], eq1: Eq[A1],
    eq2: Eq[A2], eq3: Eq[A3], eq4: Eq[A4], eq5: Eq[A5]) =
    new Show5[A1, A2, A3, A4, A5, R](typeStr, name1, fArg1, name2, fArg2, name3, fArg3, name4, fArg4, name5, fArg5, opt5, opt4, opt3, opt2, opt1)(
    ev1, ev2, ev3, ev4, ev5, eq1, eq2, eq3, eq4: Eq[A4], eq5)
}

/** Show type class for 5 parameter case classes. */
class Show6[A1, A2, A3, A4, A5, A6, R](val typeStr: String, name1: String, fArg1: R => A1, name2: String, fArg2: R => A2, name3: String,
  fArg3: R => A3, name4: String, fArg4: R => A4, name5: String, fArg5: R => A5, name6: String, fArg6: R => A6, val opt6: Option[A6],
  val opt5In: Option[A5] = None, opt4In: Option[A4] = None, opt3In: Option[A3] = None, opt2In: Option[A2] = None, opt1In: Option[A1] = None)(
  implicit ev1: Show[A1], ev2: Show[A2], ev3: Show[A3], ev4: Show[A4], ev5: Show[A5], ev6: Show[A6], eq1: Eq[A1], eq2: Eq[A2], eq3: Eq[A3],
  eq4: Eq[A4], eq5: Eq[A5], eq6: Eq[A6]) extends EqCase6[A1, A2, A3, A4, A5, A6, R](fArg1, fArg2, fArg3, fArg4, fArg5, fArg6) with ShowCase[R]
{
  val opt5: Option[A5] = ife(opt6.nonEmpty, opt5In, None)
  val opt4: Option[A4] = ife(opt5.nonEmpty, opt4In, None)
  val opt3: Option[A3] = ife(opt4.nonEmpty, opt3In, None)
  val opt2: Option[A2] = ife(opt3.nonEmpty, opt2In, None)
  val opt1: Option[A1] = ife(opt2.nonEmpty, opt1In, None)

  final override def showMems(): Arr[Show[_]] = Arr(ev1, ev2, ev3, ev4, ev5, ev6)

  override def showSemi(obj: R): String =
  { val p1 = fArg1(obj)
    val p2 = fArg2(obj)
    val p3 = fArg3(obj)
    val p4 = fArg4(obj)
    val p5 = fArg5(obj)
    val p6 = fArg6(obj)
    ev1.showComma(p1).appendSemicolons(ev2.showComma(p2), ev3.showComma(p3), ev4.showComma(p4), ev5.showComma(p5), ev6.showComma(p6))
  }

  final override def showComma(obj: R): String =
  { val p1 = fArg1(obj)
    val p2 = fArg2(obj)
    val p3 = fArg3(obj)
    val p4 = fArg4(obj)
    val p5 = fArg5(obj)
    val p6 = fArg6(obj)
    ev1.show(p1).appendCommas(ev2.show(p2), ev3.show(p3), ev4.show(p4), ev5.show(p5), ev6.show(p6))
  }

  override def showSemiNames(obj: R): String = name1 -:- ev1.showComma(fArg1(obj)) + "; " + name2 -:- ev2.showComma(fArg2(obj)) + "; " +
    name3 -:- ev3.showComma(fArg3(obj)) + "; " + name4 -:- ev4.showComma(fArg4(obj)) + name5 -:- ev5.showComma(fArg5(obj)) +
    name6 -:- ev6.showComma(fArg6(obj))

  override def showCommaNames(obj: R): String = name1 -:- ev1.show(fArg1(obj)) + ", " + name2 -:- ev2.show(fArg2(obj)) + name3 -:- ev3.show(fArg3(obj)) +
    name4 -:- ev4.show(fArg4(obj)) + name5 -:- ev5.show(fArg5(obj)) + name6 -:- ev6.show(fArg6(obj))
}

object Show6
{
  def apply[A1, A2, A3, A4, A5, A6, R](typeStr: String, name1: String, fArg1: R => A1, name2: String, fArg2: R => A2, name3: String, fArg3: R => A3,
    name4: String, fArg4: R => A4, name5: String, fArg5: R => A5, name6: String, fArg6: R => A6, opt6: Option[A6] = None, opt5: Option[A5] = None,
    opt4: Option[A4] = None, opt3: Option[A3] = None, opt2: Option[A2] = None, opt1: Option[A1] = None)(implicit
    ev1: Show[A1], ev2: Show[A2], ev3: Show[A3], ev4: Show[A4], ev5: Show[A5], ev6: Show[A6],
    eq1: Eq[A1], eq2: Eq[A2], eq3: Eq[A3], eq4: Eq[A4], eq5: Eq[A5], eq6: Eq[A6]) =
    new Show6[A1, A2, A3, A4, A5, A6, R](typeStr, name1, fArg1, name2, fArg2, name3, fArg3, name4, fArg4, name5, fArg5, name6, fArg6,
    opt6, opt5, opt4, opt3, opt2, opt1)(ev1, ev2, ev3, ev4, ev5, ev6, eq1, eq2, eq3, eq4, eq5, eq6)
}