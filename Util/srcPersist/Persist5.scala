/* Copyright 2018-24 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat
import pParse._ , reflect.ClassTag

/** A base trait for Unshow5+, declares the common properties of name1 - 5 and opt1 - 5. */
trait Persist5Plus[A1, A2, A3, A4, A5] extends Any with Persist4Plus[A1, A2, A3, A4]
{ /** 5th parameter name. */
  def name5: String

  /** The optional default value for parameter 5. */
  def opt5: Option[A5]
}

trait Persist5[A1, A2, A3, A4, A5] extends Any with Persist5Plus[A1, A2, A3, A4, A5]
{ override def paramNames: StrArr = StrArr(name1, name2, name3, name4, name5)
  override def numParams: Int = 5
}

/** [[Show]] type class for 5 field product types. */
trait Show5Plus[A1, A2, A3, A4, A5, A] extends Show4Plus[A1, A2, A3, A4, A] with Persist5Plus[A1, A2, A3, A4, A5]
{ /** Gets the 5th show field from the object. The Show fields do not necessarily correspond to the fields in memory.*/
  def fArg5: A => A5

  /** Show type class instance for the 5th Show field. */
  implicit def showEv5: Show[A5]

  /** Shows parameter 5 of the object. */
  def show5(obj: A, way: ShowStyle, maxPlaces: Int = -1, minPlaces: Int = 0): String = showEv5.show(fArg5(obj), way, maxPlaces, minPlaces)
}

/** [[Show]] type class for 5 parameter case classes. */
trait Show5[A1, A2, A3, A4, A5, A] extends Persist5[A1, A2, A3, A4, A5] with Show5Plus[A1, A2, A3, A4, A5, A]
{ override def fieldShows: RArr[Show[?]] = RArr(show1Ev, show2Ev, show3Ev, showEv4, showEv5)

  override def strs(obj: A, way: ShowStyle, maxPlaces: Int = -1, minPlaces: Int = 0): StrArr = opt5 match
  { case Some(a5) if opt1 == Some(fArg1(obj)) && opt2 == Some(fArg2(obj)) && opt3 == Some(fArg3(obj)) && opt4 == Some(fArg4(obj)) &&
      a5 == fArg5(obj) => StrArr()

    case Some(a5) if opt2 == Some(fArg2(obj)) && opt3 == Some(fArg3(obj)) &&  opt4 == Some(fArg4(obj)) && a5 == fArg5(obj) =>
      StrArr(show1(obj, way, maxPlaces, minPlaces))

    case Some(a5) if opt3 == Some(fArg3(obj)) && opt4 == Some(fArg4(obj)) && a5 == fArg5(obj) =>
      StrArr(show1(obj, way, maxPlaces, minPlaces), show2(obj, way, maxPlaces, minPlaces))

    case Some(a5) if opt4 == Some(fArg4(obj)) && a5 == fArg5(obj) => StrArr(show1(obj, way, maxPlaces, minPlaces),
      show2(obj, way, maxPlaces, minPlaces), show3(obj, way, maxPlaces, minPlaces))

    case Some(a5) if a5 == fArg5(obj) => StrArr(show1(obj, way, maxPlaces, minPlaces), show2(obj, way, maxPlaces, minPlaces), show3(obj, way, maxPlaces, minPlaces),
      show4(obj, way, maxPlaces, minPlaces))

    case _ => StrArr(show1(obj, way, maxPlaces, minPlaces), show2(obj, way, maxPlaces, minPlaces), show3(obj, way, maxPlaces, minPlaces),
      show4(obj, way, maxPlaces, minPlaces), show5(obj, way, maxPlaces, minPlaces))
  }
}

/** Companion object for [[Show5]] trait contains implementation class and factory apply method. */
object Show5
{
  def apply[A1, A2, A3, A4, A5, A](typeStr: String, name1: String, fArg1: A => A1, name2: String, fArg2: A => A2, name3: String, fArg3: A => A3,
    name4: String, fArg4: A => A4, name5: String, fArg5: A => A5, opt5: Option[A5] = None, opt4: Option[A4] = None, opt3: Option[A3] = None,
    opt2: Option[A2] = None, opt1: Option[A1] = None)(implicit show1: Show[A1], show2: Show[A2], show3: Show[A3], show4: Show[A4], show5: Show[A5],
    ct: ClassTag[A]) =
    new Show5Imp[A1, A2, A3, A4, A5, A](typeStr, name1, fArg1, name2, fArg2, name3, fArg3, name4, fArg4, name5, fArg5, ArrPairStr[A](), opt5, opt4,
    opt3, opt2, opt1)(show1, show2, show3, show4, show5)

  /** Implementation class for the general cases of [[Show5]] type class. */
  class Show5Imp[A1, A2, A3, A4, A5, A](val typeStr: String, val name1: String, val fArg1: A => A1, val name2: String, val fArg2: A => A2,
    val name3: String, val fArg3: A => A3, val name4: String, val fArg4: A => A4, val name5: String, val fArg5: A => A5, val shortKeys: ArrPairStr[A],
    override val opt5: Option[A5], opt4In: Option[A4] = None, opt3In: Option[A3] = None, opt2In: Option[A2] = None, opt1In: Option[A1] = None)(
    implicit val show1Ev: Show[A1], val show2Ev: Show[A2], val show3Ev: Show[A3], val showEv4: Show[A4], val showEv5: Show[A5]) extends
    Show5[A1, A2, A3, A4, A5, A]
  { override val opt4: Option[A4] = ife(opt5.nonEmpty, opt4In, None)
    override val opt3: Option[A3] = ife(opt4.nonEmpty, opt3In, None)
    override val opt2: Option[A2] = ife(opt3.nonEmpty, opt2In, None)
    override val opt1: Option[A1] = ife(opt2.nonEmpty, opt1In, None)

    final override def syntaxDepth(obj: A): Int = show1Ev.syntaxDepth(fArg1(obj)).max(show2Ev.syntaxDepth(fArg2(obj))).max(show3Ev.syntaxDepth(fArg3(obj))).
      max(showEv4.syntaxDepth(fArg4(obj))).max(showEv5.syntaxDepth(fArg5(obj))) + 1
  }
}

trait ShowInt5[A] extends Show5[Int, Int, Int, Int, Int, A]
{ override def show1Ev: Show[Int] = Show.intEv
  override def show2Ev: Show[Int] = Show.intEv
  override def show3Ev: Show[Int] = Show.intEv
  override def showEv4: Show[Int] = Show.intEv
  override def showEv5: Show[Int] = Show.intEv
}

/** Common trait for [[Unshow]] type class instances for sum types with 5 or more components. */
trait Unshow5Plus[A1, A2, A3, A4, A5, A] extends Unshow4Plus[A1, A2, A3, A4, A] with Persist5Plus[A1, A2, A3, A4, A5]
{ /** The [[Unshow]] type class instance for type A5. */
  def unshow5: Unshow[A5]
}

/** [[Unshow]] trait for 5 parameter product / case classes. */
trait Unshow5[A1, A2, A3, A4, A5, A] extends Unshow5Plus[A1, A2, A3, A4, A5, A] with Persist5[A1, A2, A3, A4, A5]
{ /** Allows this [[Unshow]] instance to create object from it's 5 components. */
  def newT: (A1, A2, A3, A4, A5) => A

  protected def fromSortedExprs(sortedExprs: RArr[Expr], pSeq: IntArr): EMon[A] =
  { val len: Int = sortedExprs.length
    val e1: EMon[A1] = ife(len > pSeq(0), unshow1Ev.fromSettingOrExpr(name1, sortedExprs(pSeq(0))), opt1.toEMon)
    def e2: EMon[A2] = ife(len > pSeq(1), unshow2Ev.fromSettingOrExpr(name2, sortedExprs(pSeq(1))), opt2.toEMon)
    def e3: EMon[A3] = ife(len > pSeq(2), unshow3Ev.fromSettingOrExpr(name3, sortedExprs(pSeq(2))), opt3.toEMon)
    def e4: EMon[A4] = ife(len > pSeq(3), unshow4.fromSettingOrExpr(name4, sortedExprs(pSeq(3))), opt4.toEMon)
    def e5: EMon[A5] = ife(len > pSeq(4), unshow5.fromSettingOrExpr(name5, sortedExprs(pSeq(4))), opt5.toEMon)
    e1.map5(e2, e3, e4, e5)(newT)
  }
}

class UnshowInt5[A](val typeStr: String, val name1: String, val name2: String, val name3: String, val name4: String, val name5: String,
  val shortKeys: ArrPairStr[A], val newT: (Int, Int, Int, Int, Int) => A, override val opt5: Option[Int] = None, opt4In: Option[Int] = None,
  opt3In: Option[Int] = None, opt2In: Option[Int] = None, opt1In: Option[Int] = None) extends Unshow5[Int, Int, Int, Int, Int, A]
{
  override val opt4: Option[Int] = ife(opt5.nonEmpty, opt4In, None)
  override val opt3: Option[Int] = ife(opt4.nonEmpty, opt3In, None)
  override val opt2: Option[Int] = ife(opt3.nonEmpty, opt2In, None)
  override val opt1: Option[Int] = ife(opt2.nonEmpty, opt1In, None)

  override def unshow1Ev: Unshow[Int] = Unshow.intEv
  override def unshow2Ev: Unshow[Int] = Unshow.intEv
  override def unshow3Ev: Unshow[Int] = Unshow.intEv
  override def unshow4: Unshow[Int] = Unshow.intEv
  override def unshow5: Unshow[Int] = Unshow.intEv
}

object UnshowInt5
{
  def apply[A](typeStr: String, name1: String, name2: String, name3: String, name4: String, name5: String, newT: (Int, Int, Int, Int, Int) => A,
    opt5: Option[Int] = None, opt4: Option[Int] = None, opt3: Option[Int] = None, opt2: Option[Int] = None, opt1: Option[Int] = None)(implicit
    ct: ClassTag[A]): UnshowInt5[A] =
    new UnshowInt5[A](typeStr, name1, name2, name3, name4, name5, ArrPairStr[A](), newT, opt5, opt4, opt3, opt2, opt1)
}