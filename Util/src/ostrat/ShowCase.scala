/* Copyright 2018 Richard Oliver. Licensed under Apache Licence version 2.0 */
package ostrat

/** The base trait for the persistence of Case classes, aka Product types */
trait ShowCase[R]/*(val typeStr: String)*/ extends ShowCompound[R]
{ def showMems: Arr[Show[_]]
  final override def syntaxDepth: Int = showMems.map(_.syntaxDepth).max + 1
}

/** Show type class for 1 parameter case classes. */
abstract class Show1[A1, R](val typeStr: String, val fParam: R => A1, val opt1: Option[A1] = None)(implicit ev1: Show[A1]) extends ShowCase[R]
{ final override def showMems: Arr[Show[_]] = Arr(ev1)
  def showSemi(obj: R): String = ev1.showComma(fParam(obj))
  def showComma(obj: R): String = ev1.show(fParam(obj))
}

/** Show type class for 2 parameter case classes. */
class Show2[A1, A2, R](val typeStr: String, val fArg1: R => A1, val fArg2: R => A2, val opt2: Option[A2] = None, opt1In: Option[A1] = None)(
  implicit ev1: Show[A1], ev2: Show[A2]) extends ShowCase[R]
{
  val opt1: Option[A1] = ife(opt2.nonEmpty, opt1In, None)
  final override def showMems: Arr[Show[_]] = Arr(ev1, ev2)
  override def showSemi(obj: R): String = ev1.showComma(fArg1(obj)) + "; " + ev2.showComma(fArg2(obj))
  override def showComma(obj: R): String = ev1.show(fArg1(obj)) + ", " + ev2.show(fArg2(obj))
}

/** Show type class for 3 parameter case classes. */
class Show3[A1, A2, A3, R](val typeStr: String, val fArg1: R => A1, val fArg2: R => A2, fArg3: R => A3, val opt3: Option[A3] = None, opt2In: Option[A2] = None,
    opt1In: Option[A1] = None)(implicit ev1: Show[A1], ev2: Show[A2], ev3: Show[A3]) extends ShowCase[R]
{
  val opt2: Option[A2] = ife(opt3.nonEmpty, opt2In, None)
  val opt1: Option[A1] = ife(opt2.nonEmpty, opt1In, None)
  val defaultNum = ife3(opt3.isEmpty, 0, opt2.isEmpty, 1, opt1.isEmpty, 2, 3)
  override def showMems: Arr[Show[_]] = Arr(ev1, ev2, ev3)

  final override def showSemi(obj: R): String =
  { val p1 = fArg1(obj)
    val p2 = fArg2(obj)
    val p3 = fArg3(obj)
    (opt1, opt2, opt3) match
    {
      case (Some(v1), Some(v2), Some(v3)) if v1 == p1 & v2 == p2 & v3 == p3 => ""
      case (_, Some(v2), Some(v3)) if v2 == p2 & v3 == p3 => ev1.showComma(p1)
      case (_, _, Some(v3)) if v3 == p3 => ev1.showComma(p1).semicolonAppend(ev2.showComma(p2))
      case _ => ev1.showComma(p1).semicolonAppend(ev2.showComma(p2), ev3.showComma(p3))
    }
  }

  final override def showComma(obj: R): String =
  { val p1 = fArg1(obj)
    val p2 = fArg2(obj)
    val p3 = fArg3(obj)

    (opt1, opt2, opt3) match
    { case (Some(v1), Some(v2), Some(v3)) if v1 == p1 & v2 == p2 & v3 == p3 => ""
      case (_, Some(v2), Some(v3)) if v2 == p2 & v3 == p3 => ev1.show(p1) + ","
      case (_, _, Some(v3)) if v3 == p3 => ev1.showComma(p1).commaAppend(ev2.showComma(p2))
      case _ => ev1.showComma(p1).commaAppend(ev2.showComma(p2), ev3.showComma(p3))
    }
  }
}

/** Show type class for 4 parameter case classes. */
abstract class Show4[A1, A2, A3, A4, R](val typeStr: String, val fParam: R => (A1, A2, A3, A4), val opt4: Option[A4] = None, opt3In: Option[A3] = None,
  opt2In: Option[A2] = None, opt1In: Option[A1] = None)(implicit ev1: Show[A1], ev2: Show[A2], ev3: Show[A3], ev4: Show[A4]) extends
  ShowCase[R]
{
  val opt3: Option[A3] = ife(opt4.nonEmpty, opt3In, None)
  val opt2: Option[A2] = ife(opt3.nonEmpty, opt2In, None)
  val opt1: Option[A1] = ife(opt2.nonEmpty, opt1In, None)

  final override def showMems = Arr(ev1, ev2, ev3, ev4)

  override def showSemi(obj: R): String = {
    val (p1, p2, p3, p4) = fParam(obj)
    ev1.showComma(p1).semicolonAppend(ev2.showComma(p2), ev3.showComma(p3), ev4.showComma(p4))
  }

  final override def showComma(obj: R): String =
  { val (p1, p2, p3, p4) = fParam(obj)
    ev1.show(p1).commaAppend(ev2.show(p2), ev3.show(p3), ev4.show(p4))
  }
}

/** Show type class for 4 parameter case classes. */
class Show5[A1, A2, A3, A4, A5, R](val typeStr: String, val fParam: R => (A1, A2, A3, A4, A5), val opt5: Option[A5], optIn4: Option[A4] = None,
  opt3In: Option[A3] = None, opt2In: Option[A2] = None, opt1In: Option[A1] = None)(implicit ev1: Show[A1], ev2: Show[A2], ev3: Show[A3],
  ev4: Show[A4], ev5: Show[A5]) extends ShowCase[R]
{
  val opt4: Option[A4] = ife(opt5.nonEmpty, optIn4, None)
  val opt3: Option[A3] = ife(opt4.nonEmpty, opt3In, None)
  val opt2: Option[A2] = ife(opt3.nonEmpty, opt2In, None)
  val opt1: Option[A1] = ife(opt2.nonEmpty, opt1In, None)

  final override def showMems = Arr(ev1, ev2, ev3, ev4, ev5)

  override def showSemi(obj: R): String = {
    val (p1, p2, p3, p4, p5) = fParam(obj)
    ev1.showComma(p1).semicolonAppend(ev2.showComma(p2), ev3.showComma(p3), ev4.showComma(p4), ev5.showComma(p5))
  }

  final override def showComma(obj: R): String =
  { val (p1, p2, p3, p4, p5) = fParam(obj)
    ev1.show(p1).commaAppend(ev2.show(p2), ev3.show(p3), ev4.show(p4), ev5.show(p5))
  }
}

object Show5
{
  def apply[A1, A2, A3, A4, A5, R](typeStr: String, fParam: R => (A1, A2, A3, A4, A5), opt5: Option[A5] = None, opt4: Option[A4] = None,
  opt3: Option[A3] = None, opt2: Option[A2] = None, opt1: Option[A1] = None)(implicit ev1: Show[A1], ev2: Show[A2], ev3: Show[A3], ev4: Show[A4],
    ev5: Show[A5]) = new Show5[A1, A2, A3, A4, A5, R](typeStr, fParam, opt5, opt4, opt3, opt2, opt1)(ev1, ev2, ev3, ev4, ev5)
}