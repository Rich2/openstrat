/* Copyright 2018-24 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat
import pParse._, reflect.ClassTag

/** A base trait for [[Show4]] and [[Unshow4]], declares the common properties of name1 - 4 and opt1 - 4. */
trait Persist4Plus[A1, A2, A3, A4] extends Any with Persist3Plus[A1, A2, A3]
{ /** 4th parameter name. */
  def name4: String

  /** The optional default value for parameter 4. */
  def opt4: Option[A4]
}

/** Base trait for [[Tell4]], [[Show4]] and [[Unshow4]]. */
trait Persist4[A1, A2, A3, A4] extends Any with Persist4Plus[A1, A2, A3, A4]
{ final override def paramNames: StrArr = StrArr(name1, name2, name3, name4)
  override def numParams: Int = 4
}

/** [[Show]] type class for 4 field product types. */
trait Show4Plus[A1, A2, A3, A4, A] extends Show3Plus[A1, A2, A3, A] with Persist4Plus[A1, A2, A3, A4]
{ /** Gets the 2nd show field from the object. The Show fields do not necessarily correspond to the fields in memory.*/
  def fArg4: A => A4

  /** Show type class instance for the 2nd Show field. */
  implicit def showEv4: Show[A4]

  /** Shows parameter 4 of the object. */
  def show4(obj: A, way: ShowStyle, maxPlaces: Int = -1, minPlaces: Int = 0): String = showEv4.show(fArg4(obj), way, maxPlaces, minPlaces)
}

/** Show type class for 4 parameter case classes. */
trait Show4[A1, A2, A3, A4, A] extends Persist4[A1,A2, A3, A4] with Show4Plus[A1, A2, A3, A4, A]
{ override def fieldShows: RArr[Show[?]] = RArr(show1Ev, show2Ev, show3Ev, showEv4)

  override def strs(obj: A, way: ShowStyle, maxPlaces: Int = -1, minPlaces: Int = 0): StrArr = opt4 match
  { case Some(a4) if opt1 == Some(fArg1(obj)) && opt2 == Some(fArg2(obj)) && opt3 == Some(fArg3(obj)) && a4 == fArg4(obj) => StrArr()
    case Some(a4) if opt2 == Some(fArg2(obj)) && opt3 == Some(fArg3(obj)) && a4 == fArg4(obj) => StrArr(show1(obj, way, maxPlaces, minPlaces))
    case Some(a4) if opt3 == Some(fArg3(obj)) && a4 == fArg4(obj) => StrArr(show1(obj, way, maxPlaces, minPlaces),
      show2(obj, way, maxPlaces, minPlaces))
    case Some(a4) if a4 == fArg4(obj) => StrArr(show1(obj, way, maxPlaces, minPlaces), show2(obj, way, maxPlaces, minPlaces),
      show3(obj, way, maxPlaces, minPlaces))
    case _ => StrArr(show1(obj, way, maxPlaces, minPlaces), show2(obj, way, maxPlaces, minPlaces), show3(obj, way, maxPlaces, minPlaces),
      show4(obj, way, maxPlaces, minPlaces))
  }
}

object Show4
{ /** Factory apply method for general cases of [[Show4]] type class instances / evidence. */
  def apply[A1, A2, A3, A4, A](typeStr: String, name1: String, fArg1: A => A1, name2: String, fArg2: A => A2, name3: String, fArg3: A => A3,
    name4: String, fArg4: A => A4, opt4: Option[A4] = None, opt3: Option[A3] = None, opt2: Option[A2] = None, opt1: Option[A1] = None)(implicit
    show1: Show[A1], show2: Show[A2], show3: Show[A3], show4: Show[A4], ct: ClassTag[A]): Show4[A1, A2, A3, A4, A] =
    new Show4Imp[A1, A2, A3, A4, A](typeStr, name1, fArg1, name2, fArg2, name3, fArg3, name4, fArg4, ArrPairStr[A](), opt4, opt3, opt2, opt1)(show1,
      show2, show3, show4: Show[A4])

  /** Implementation class for the general cases of [[Show4]] trait. */
  class Show4Imp[A1, A2, A3, A4, A](val typeStr: String, val name1: String, val fArg1: A => A1, val name2: String, val fArg2: A => A2,
    val name3: String, val fArg3: A => A3, val name4: String, val fArg4: A => A4, val shortKeys: ArrPairStr[A],  override val opt4: Option[A4] = None,
    opt3In: Option[A3] = None, opt2In: Option[A2] = None, opt1In: Option[A1] = None)(implicit val show1Ev: Show[A1], val show2Ev: Show[A2],
    val show3Ev: Show[A3], val showEv4: Show[A4]) extends Show4[A1, A2, A3, A4, A]
  { override val opt3: Option[A3] = ife(opt4.nonEmpty, opt3In, None)
    override val opt2: Option[A2] = ife(opt3.nonEmpty, opt2In, None)
    override val opt1: Option[A1] = ife(opt2.nonEmpty, opt1In, None)

    final override def syntaxDepth(obj: A): Int = show1Ev.syntaxDepth(fArg1(obj)).max(show2Ev.syntaxDepth(fArg2(obj))).max(show3Ev.syntaxDepth(fArg3(obj))).
      max(showEv4.syntaxDepth(fArg4(obj))) + 1
  }
}

/** Produces [[Show]] type class instances for 4 [[Int]] types. */
trait ShowInt4[A] extends Show4[Int, Int, Int, Int, A]
{ override def show1Ev: Show[Int] = Show.intEv
  override def show2Ev: Show[Int] = Show.intEv
  override def show3Ev: Show[Int] = Show.intEv
  override def showEv4: Show[Int] = Show.intEv
  override def syntaxDepth(obj: A): Int = 2
}

object ShowInt4
{ /** Factory apply method for creating quick ShowDecT instances for products of 4 Ints. */
  def apply[A](typeStr: String, name1: String, fArg1: A => Int, name2: String, fArg2: A => Int, name3: String, fArg3: A => Int, name4: String,
    fArg4: A => Int, opt4: Option[Int] = None, opt3: Option[Int] = None, opt2: Option[Int] = None, opt1: Option[Int] = None)(implicit ct: ClassTag[A]):
  ShowInt4Imp[A] = new ShowInt4Imp[A](typeStr, name1, fArg1, name2, fArg2, name3, fArg3, name4, fArg4, ArrPairStr[A](), opt4, opt3, opt2, opt1)

  class ShowInt4Imp[A](val typeStr: String, val name1: String, val fArg1: A => Int, val name2: String, val fArg2: A => Int, val name3: String,
    val fArg3: A => Int, val name4: String, val fArg4: A => Int, val shortKeys: ArrPairStr[A], override val opt4: Option[Int],
    opt3In: Option[Int] = None, opt2In: Option[Int] = None, opt1In: Option[Int] = None) extends ShowInt4[A]
  { override val opt3: Option[Int] = ife(opt4.nonEmpty, opt3In, None)
    override val opt2: Option[Int] = ife(opt3.nonEmpty, opt2In, None)
    override val opt1: Option[Int] = ife(opt2.nonEmpty, opt1In, None)

    override def strs(obj: A, way: ShowStyle, maxPlaces: Int = -1, minPlaces: Int = 0): StrArr = StrArr(
      show1Ev.show(fArg1(obj), way, maxPlaces, minPlaces), show2Ev.show(fArg2(obj), way, maxPlaces, minPlaces),
      show3Ev.show(fArg3(obj), way, maxPlaces, minPlaces), showEv4.show(fArg4(obj), way, maxPlaces, minPlaces))
  }
}

/** Produces [[Show]] type class instances for types with 4 [[Double]] components. Note a LineSeg does not use this class although it is held in
 * memory as 4 [[Double]]s. As its logical components are 2 points. */
abstract class ShowDbl4[A] extends Show4[Double, Double, Double, Double, A]
{ override def show1Ev: Show[Double] = Show.doubleEv
  override def show2Ev: Show[Double] = Show.doubleEv
  override def show3Ev: Show[Double] = Show.doubleEv
  override def showEv4: Show[Double] = Show.doubleEv
  override def syntaxDepth(obj: A): Int = 2
}

/** common trait for [[Unshow]] type class instances for sum types with 4 or more components. */
trait Unshow4Plus[A1, A2, A3, A4, A] extends Unshow3Plus[A1, A2, A3, A] with Persist4Plus[A1, A2, A3, A4]
{ /** The [[Unshow]] type class instance for type A4. */
  def unshow4: Unshow[A4]
}

/** UnShow class for 4 logical parameter product types. */
trait Unshow4[A1, A2, A3, A4, A] extends Unshow4Plus[A1,A2, A3, A4, A] with Persist4[A1, A2, A3, A4]
{ /** Allows this [[Unshow]] instance to create object from it's 4 components. */
  def newT: (A1, A2, A3, A4) => A

  protected def fromSortedExprs(sortedExprs: RArr[Expr], pSeq: IntArr): EMon[A] =
  { val len: Int = sortedExprs.length
    val e1: EMon[A1] = ife(len > pSeq(0), unshow1Ev.fromSettingOrExpr(name1, sortedExprs(pSeq(0))), opt1.toEMon)
    def e2: EMon[A2] = ife(len > pSeq(1), unshow2Ev.fromSettingOrExpr(name2, sortedExprs(pSeq(1))), opt2.toEMon)
    def e3: EMon[A3] = ife(len > pSeq(2), unshow3Ev.fromSettingOrExpr(name3, sortedExprs(pSeq(2))), opt3.toEMon)
    def e4: EMon[A4] = ife(len > pSeq(3), unshow4.fromSettingOrExpr(name4, sortedExprs(pSeq(3))), opt4.toEMon)
    e1.map4(e2, e3, e4)(newT)
  }
}

object Unshow4
{
  def apply[A1, A2, A3, A4, A](typeStr: String, name1: String, name2: String, name3: String, name4: String, newT: (A1, A2, A3, A4) => A,
    opt4: Option[A4] = None, opt3: Option[A3] = None, opt2: Option[A2] = None,  opt1: Option[A1] = None)(implicit unshow1: Unshow[A1],
    unshow2: Unshow[A2], unshow3: Unshow[A3], unshow4: Unshow[A4], ct: ClassTag[A]): Unshow4[A1, A2, A3, A4, A] =
    new Unshow4Imp(typeStr, name1, name2, name3, name4, newT, ArrPairStr[A](), opt4, opt3, opt2, opt1)(unshow1, unshow2, unshow3, unshow4)

  class Unshow4Imp[A1, A2, A3, A4, A](val typeStr: String, val name1: String, val name2: String, val name3: String, val name4: String,
    val newT: (A1, A2, A3, A4) => A, val shortKeys: ArrPairStr[A], override val opt4: Option[A4] = None, val opt3In: Option[A3] = None,
    opt2In: Option[A2] = None, opt1In: Option[A1] = None)(implicit val unshow1Ev: Unshow[A1], val unshow2Ev: Unshow[A2], val unshow3Ev: Unshow[A3],
    val unshow4: Unshow[A4]) extends Unshow4[A1, A2, A3, A4, A]
  { override val opt3: Option[A3] = ife(opt4.nonEmpty, opt3In, None)
    override val opt2: Option[A2] = ife(opt3.nonEmpty, opt2In, None)
    override val opt1: Option[A1] = ife(opt2.nonEmpty, opt1In, None)

    val defaultNum: Int = None match
    { case _ if opt3.isEmpty => 0
      case _ if opt2.isEmpty => 1
      case _ if opt1.isEmpty => 2
      case _ => 3
    }
  }
}

trait UnshowInt4[A] extends Unshow4[Int, Int, Int, Int, A]
{ override def unshow1Ev: Unshow[Int] = Unshow.intEv
  override def unshow2Ev: Unshow[Int] = Unshow.intEv
  override def unshow3Ev: Unshow[Int] = Unshow.intEv
  override def unshow4: Unshow[Int] = Unshow.intEv
}

object UnshowInt4
{
  def apply[A](typeStr: String, name1: String, name2: String, name3: String, name4: String, newT: (Int, Int, Int, Int) => A, opt4: Option[Int] = None,
    opt3: Option[Int] = None, opt2: Option[Int] = None, opt1: Option[Int] = None)(implicit ct: ClassTag[A]): UnshowInt4[A] =
    new UnshowInt4Imp(typeStr, name1, name2, name3, name4, newT, ArrPairStr[A](), opt4, opt3, opt2, opt1)

  class UnshowInt4Imp[A](val typeStr: String, val name1: String, val name2: String, val name3: String, val name4: String,
    val newT: (Int, Int, Int, Int) => A, val shortKeys: ArrPairStr[A], override val opt4: Option[Int] = None, opt3In: Option[Int] = None,
    opt2In: Option[Int] = None, opt1In: Option[Int] = None) extends UnshowInt4[A]
  { override val opt3: Option[Int] = ife(opt4.nonEmpty, opt3In, None)
    override val opt2: Option[Int] = ife(opt3.nonEmpty, opt2In, None)
    override val opt1: Option[Int] = ife(opt2.nonEmpty, opt1In, None)

    val defaultNum: Int = None match
    { case _ if opt3.isEmpty => 0
      case _ if opt2.isEmpty => 1
      case _ if opt1.isEmpty => 2
      case _ => 3
    }
  }
}

/** [[Unshow]] type class instances for classes with 4 [[Double]] [[Unshow]] components. Note this wouldn't include classes like e line segement,
 * because although it would be stored in memory as 4 [[Double]]s for efficiency, for persistence purpose it has 2 point components. */
trait UnshowDbl4[A] extends Unshow4[Double, Double, Double, Double, A]
{ override def unshow1Ev: Unshow[Double] = Unshow.doubleEv
  override def unshow2Ev: Unshow[Double] = Unshow.doubleEv
  override def unshow3Ev: Unshow[Double] = Unshow.doubleEv
  override def unshow4: Unshow[Double] = Unshow.doubleEv
}

object UnshowDbl4
{
  def apply[A](typeStr: String, name1: String, name2: String, name3: String, name4: String, newT: (Double, Double, Double, Double) => A,
    opt4: Option[Double] = None, opt3: Option[Double] = None, opt2: Option[Double] = None, opt1: Option[Double] = None)(implicit ct: ClassTag[A]):
    UnshowDbl4[A] = new UnshowDbl4Imp[A](typeStr, name1, name2, name3, name4, newT, ArrPairStr[A](), opt4, opt3, opt2, opt1)

  /** Implementation class for [[UnshowDbl4]]. */
  class UnshowDbl4Imp[A](val typeStr: String, val name1: String, val name2: String, val name3: String, val name4: String,
    val newT: (Double, Double, Double, Double) => A, val shortKeys: ArrPairStr[A], val opt4: Option[Double] = None, val opt3In: Option[Double] = None,
    opt2In: Option[Double] = None, opt1In: Option[Double] = None) extends UnshowDbl4[A]
  { override val opt3: Option[Double] = ife(opt4.nonEmpty, opt3In, None)
    override val opt2: Option[Double] = ife(opt3.nonEmpty, opt2In, None)
    override val opt1: Option[Double] = ife(opt2.nonEmpty, opt1In, None)
  }
}