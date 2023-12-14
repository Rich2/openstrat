/* Copyright 2018-23 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat
import pParse._, reflect.ClassTag

/** A base trait for Show6+ and Unshow6+ classses. Declares the common properties of name1 - 6 and opt1 - 6. */
trait Persist6Plus[A1, A2, A3, A4, A5, A6] extends Any with Persist5Plus[A1, A2, A3, A4, A5]
{ /** 6th parameter name. */
  def name6: String

  /** The optional default value for parameter 6. */
  def opt6: Option[A6]
}

trait Persist6[A1, A2, A3, A4, A5, A6] extends Any with Persist6Plus[A1, A2, A3, A4, A5, A6]
{ override def paramNames: StrArr = StrArr(name1, name2, name3, name4, name5, name6)
  override def numParams: Int = 6
}

/** [[Show]] type class for 6 field product types. */
trait Show6Plus[A1, A2, A3, A4, A5, A6, A] extends Show5Plus[A1, A2, A3, A4, A5, A] with Persist6Plus[A1, A2, A3, A4, A5, A6]
{ /** Gets the 6th show field from the object. The Show fields do not necessarily correspond to the fields in memory.*/
  def fArg6: A => A6

  /** Show type class instance for the 5th Show field. */
  implicit def showEv6: Show[A6]

  /** Shows parameter 6 of the object. */
  def show6(obj: A, way: ShowStyle, maxPlaces: Int = -1, minPlaces: Int = 0): String = showEv6.show(fArg6(obj), way, maxPlaces, minPlaces)
}

/** [[Show]] type class for 6 parameter case classes. */
trait Show6[A1, A2, A3, A4, A5, A6, A] extends Show6Plus[A1, A2, A3, A4, A5, A6, A] with Persist6[A1, A2, A3, A4, A5, A6]
{ override def fieldShows: RArr[Show[_]] = RArr(show1Ev, show2Ev, show3Ev, showEv4, showEv5, showEv6)

  override def strs(obj: A, way: ShowStyle, maxPlaces: Int = -1, minPlaces: Int = 0): StrArr = opt5 match {
    case Some(a6) if opt1 == Some(fArg1(obj)) && opt2 == Some(fArg2(obj)) && opt3 == Some(fArg3(obj)) && opt4 == Some(fArg4(obj)) &&
      opt5 == Some(fArg5(obj)) && a6 == fArg6(obj) => StrArr()

    case Some(a6) if opt2 == Some(fArg2(obj)) && opt3 == Some(fArg3(obj)) && opt4 == Some(fArg4(obj)) && opt5 == Some(fArg5(obj)) &&
      a6 == fArg6(obj) => StrArr(show1(obj, way, maxPlaces, minPlaces))

    case Some(a6) if opt3 == Some(fArg3(obj)) && opt4 == Some(fArg4(obj)) && opt5 == Some(fArg5(obj)) && a6 == fArg6(obj) =>
      StrArr(show1(obj, way, maxPlaces, minPlaces), show2(obj, way, maxPlaces, minPlaces))

    case Some(a6) if opt4 == Some(fArg4(obj)) && opt5 == Some(fArg5(obj)) && a6 == fArg6(obj) => StrArr(show1(obj, way, maxPlaces, minPlaces),
      show2(obj, way, maxPlaces, minPlaces), show3(obj, way, maxPlaces, minPlaces))

    case Some(a6) if opt5 == Some(fArg5(obj)) && a6 == fArg6(obj) => StrArr(show1(obj, way, maxPlaces, minPlaces), show2(obj, way, maxPlaces, minPlaces), show3(obj, way, maxPlaces, minPlaces),
      show4(obj, way, maxPlaces, minPlaces))

    case Some(a6) if a6 == fArg6(obj) => StrArr(show1(obj, way, maxPlaces, minPlaces), show2(obj, way, maxPlaces, minPlaces), show3(obj, way, maxPlaces, minPlaces),
      show4(obj, way, maxPlaces, minPlaces), show5(obj, way, maxPlaces, minPlaces))

    case _ => StrArr(show1(obj, way, maxPlaces, minPlaces), show2(obj, way, maxPlaces, minPlaces), show3(obj, way, maxPlaces, minPlaces),
      show4(obj, way, maxPlaces, minPlaces), show5(obj, way, maxPlaces, minPlaces), show6(obj, way, maxPlaces, minPlaces))
  }
}

/** Companion object for [[Show6]] contains implementation class and factory apply method. */
object Show6
{
  def apply[A1, A2, A3, A4, A5, A6, A](typeStr: String, name1: String, fArg1: A => A1, name2: String, fArg2: A => A2, name3: String, fArg3: A => A3,
    name4: String, fArg4: A => A4, name5: String, fArg5: A => A5, name6: String, fArg6: A => A6, opt6: Option[A6] = None, opt5: Option[A5] = None,
    opt4: Option[A4] = None, opt3: Option[A3] = None, opt2: Option[A2] = None, opt1: Option[A1] = None)(implicit show1: Show[A1], show2: Show[A2],
    show3: Show[A3], show4: Show[A4], show5: Show[A5], show6: Show[A6], ct: ClassTag[A]): Show6[A1, A2, A3, A4, A5, A6, A] =
    new Show6Imp[A1, A2, A3, A4, A5, A6, A](typeStr, name1, fArg1, name2, fArg2, name3, fArg3, name4, fArg4, name5, fArg5, name6, fArg6,
      ArrPairStr[A](), opt6, opt5, opt4, opt3, opt2, opt1)(show1, show2, show3, show4, show5, show6)

  /** Implementation class for general cases of [[Show6]] type class. */
  class Show6Imp[A1, A2, A3, A4, A5, A6, A](val typeStr: String, val name1: String, val fArg1: A => A1, val name2: String, val fArg2: A => A2,
    val name3: String, val fArg3: A => A3, val name4: String, val fArg4: A => A4, val name5: String, val fArg5: A => A5, val name6: String,
    val fArg6: A => A6, val shortKeys: ArrPairStr[A], val opt6: Option[A6], val opt5In: Option[A5] = None, opt4In: Option[A4] = None, opt3In: Option[A3] = None,
    opt2In: Option[A2] = None, opt1In: Option[A1] = None)(
    implicit val show1Ev: Show[A1], val show2Ev: Show[A2], val show3Ev: Show[A3], val showEv4: Show[A4], val showEv5: Show[A5],
    val showEv6: Show[A6]) extends Show6[A1, A2, A3, A4, A5, A6, A] with ShowNFixed[A]
  { val opt5: Option[A5] = ife(opt6.nonEmpty, opt5In, None)
    override val opt4: Option[A4] = ife(opt5.nonEmpty, opt4In, None)
    override val opt3: Option[A3] = ife(opt4.nonEmpty, opt3In, None)
    override val opt2: Option[A2] = ife(opt3.nonEmpty, opt2In, None)
    override val opt1: Option[A1] = ife(opt2.nonEmpty, opt1In, None)

    final override def syntaxDepth(obj: A): Int = show1Ev.syntaxDepth(fArg1(obj)).max(show2Ev.syntaxDepth(fArg2(obj))).max(show3Ev.syntaxDepth(fArg3(obj))).
      max(showEv4.syntaxDepth(fArg4(obj))).max(showEv5.syntaxDepth(fArg5(obj))).max(showEv6.syntaxDepth(fArg6(obj))) + 1
  }
}

/** Common trait for [[Unshow]] type class instances for sum types with 6 or more components. */
trait Unshow6Plus[A1, A2, A3, A4, A5, A6, A] extends Unshow5Plus[A1, A2, A3, A4, A5, A] with Persist6Plus[A1, A2, A3, A4, A5, A6]
{ /** The [[Unshow]] type class instance for type A6. */
  def unshow6: Unshow[A6]
}

/** [[UnShow]] trait for 6 parameter / product / component classes. */
trait Unshow6[A1, A2, A3, A4, A5, A6, A] extends Unshow6Plus[A1, A2, A3, A4, A5, A6, A] with Persist6[A1, A2, A3, A4, A5, A6]
{ /** Allows this [[Unshow]] instance to create object from it's 6 components. */
  val newT: (A1, A2, A3, A4, A5, A6) => A

  protected def fromSortedExprs(sortedExprs: RArr[Expr], pSeq: IntArr): EMon[A] =
  { val len: Int = sortedExprs.length
    val e1: EMon[A1] = ife(len > pSeq(0), unshow1Ev.fromSettingOrExpr(name1, sortedExprs(pSeq(0))), opt1.toEMon)
    def e2: EMon[A2] = ife(len > pSeq(1), unshow2Ev.fromSettingOrExpr(name2, sortedExprs(pSeq(1))), opt2.toEMon)
    def e3: EMon[A3] = ife(len > pSeq(2), unshow3Ev.fromSettingOrExpr(name3, sortedExprs(pSeq(2))), opt3.toEMon)
    def e4: EMon[A4] = ife(len > pSeq(3), unshow4.fromSettingOrExpr(name4, sortedExprs(pSeq(3))), opt4.toEMon)
    def e5: EMon[A5] = ife(len > pSeq(4), unshow5.fromSettingOrExpr(name5, sortedExprs(pSeq(4))), opt5.toEMon)
    def e6: EMon[A6] = ife(len > pSeq(5), unshow6.fromSettingOrExpr(name6, sortedExprs(pSeq(5))), opt6.toEMon)
    e1.map6(e2, e3, e4, e5, e6)(newT)
  }
}