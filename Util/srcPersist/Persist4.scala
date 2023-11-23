/* Copyright 2018-23 Richard Oliver. Licensed under Apache Licence version 2.0. */
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
trait Show4Plus[A1, A2, A3, A4, R] extends Show3Plus[A1, A2, A3, R] with Persist4Plus[A1, A2, A3, A4]
{ /** Gets the 2nd show field from the object. The Show fields do not necessarily correspond to the fields in memory.*/
  def fArg4: R => A4

  /** Show type class instance for the 2nd Show field. */
  implicit def showEv4: Show[A4]

  /** Shows parameter 4 of the object. */
  def show4(obj: R, way: ShowStyle, maxPlaces: Int = -1, minPlaces: Int = 0): String = showEv4.show(fArg4(obj), way, maxPlaces, minPlaces)
}

/** Show type class for 4 parameter case classes. */
trait Show4[A1, A2, A3, A4, R] extends Persist4[A1,A2, A3, A4] with Show4Plus[A1, A2, A3, A4, R]
{
  override def fieldShows: RArr[Show[_]] = RArr(showEv1, showEv2, showEv3, showEv4)

  override def strs(obj: R, way: ShowStyle, maxPlaces: Int = -1, minPlaces: Int = 0): StrArr = opt4 match
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
{
  def apply[A1, A2, A3, A4, R](typeStr: String, name1: String, fArg1: R => A1, name2: String, fArg2: R => A2, name3: String, fArg3: R => A3,
    name4: String, fArg4: R => A4, opt4: Option[A4] = None, opt3: Option[A3] = None, opt2: Option[A2] = None, opt1: Option[A1] = None)(implicit
    show1: Show[A1], show2: Show[A2], show3: Show[A3], show4: Show[A4], ct: ClassTag[R]): Show4[A1, A2, A3, A4, R] =
    new Show4Imp[A1, A2, A3, A4, R](typeStr, name1, fArg1, name2, fArg2, name3, fArg3, name4, fArg4, ArrPairStr[R](), opt4, opt3, opt2, opt1)(show1,
      show2, show3, show4: Show[A4])

  /** Implementation class for the general cases of [[Show4]] trait. */
  class Show4Imp[A1, A2, A3, A4, R](val typeStr: String, val name1: String, val fArg1: R => A1, val name2: String, val fArg2: R => A2,
    val name3: String, val fArg3: R => A3, val name4: String, val fArg4: R => A4, val shortKeys: ArrPairStr[R],  override val opt4: Option[A4] = None,
    opt3In: Option[A3] = None, opt2In: Option[A2] = None, opt1In: Option[A1] = None)(implicit val showEv1: Show[A1], val showEv2: Show[A2],
    val showEv3: Show[A3], val showEv4: Show[A4]) extends Show4[A1, A2, A3, A4, R]
  { override val opt3: Option[A3] = ife(opt4.nonEmpty, opt3In, None)
    override val opt2: Option[A2] = ife(opt3.nonEmpty, opt2In, None)
    override val opt1: Option[A1] = ife(opt2.nonEmpty, opt1In, None)

    final override def syntaxDepth(obj: R): Int = showEv1.syntaxDepth(fArg1(obj)).max(showEv2.syntaxDepth(fArg2(obj))).max(showEv3.syntaxDepth(fArg3(obj))).
      max(showEv4.syntaxDepth(fArg4(obj))) + 1
  }
}

/** Produces [[Show]] type class instances for 4 [[Int]] types. */
trait ShowInt4[R] extends Show4[Int, Int, Int, Int, R]
{ override def showEv1: Show[Int] = Show.intEv
  override def showEv2: Show[Int] = Show.intEv
  override def showEv3: Show[Int] = Show.intEv
  override def showEv4: Show[Int] = Show.intEv
  override def syntaxDepth(obj: R): Int = 2
}

object ShowInt4
{
  /** Factory apply method for creating quick ShowDecT instances for products of 4 Ints. */
  def apply[R](typeStr: String, name1: String, fArg1: R => Int, name2: String, fArg2: R => Int, name3: String, fArg3: R => Int, name4: String,
    fArg4: R => Int, opt4: Option[Int] = None, opt3: Option[Int] = None, opt2: Option[Int] = None, opt1: Option[Int] = None)(implicit ct: ClassTag[R]):
  ShowInt4Imp[R] = new ShowInt4Imp[R](typeStr, name1, fArg1, name2, fArg2, name3, fArg3, name4, fArg4, ArrPairStr[R](), opt4, opt3, opt2, opt1)

  class ShowInt4Imp[R](val typeStr: String, val name1: String, val fArg1: R => Int, val name2: String, val fArg2: R => Int, val name3: String,
    val fArg3: R => Int, val name4: String, val fArg4: R => Int, val shortKeys: ArrPairStr[R], override val opt4: Option[Int],
    opt3In: Option[Int] = None, opt2In: Option[Int] = None, opt1In: Option[Int] = None) extends ShowInt4[R]
  { override val opt3: Option[Int] = ife(opt4.nonEmpty, opt3In, None)
    override val opt2: Option[Int] = ife(opt3.nonEmpty, opt2In, None)
    override val opt1: Option[Int] = ife(opt2.nonEmpty, opt1In, None)

    override def strs(obj: R, way: ShowStyle, maxPlaces: Int = -1, minPlaces: Int = 0): StrArr = StrArr(
      showEv1.show(fArg1(obj), way, maxPlaces, minPlaces), showEv2.show(fArg2(obj), way, maxPlaces, minPlaces),
      showEv3.show(fArg3(obj), way, maxPlaces, minPlaces), showEv4.show(fArg4(obj), way, maxPlaces, minPlaces))
  }
}

/** Produces [[Show]] type class instances for types with 4 [[Double]] components. Note a LineSeg does not use this class although it is held in
 * memory as 4 [[Double]]s. As its logical components are 2 points. */
abstract class ShowDbl4[R] extends Show4[Double, Double, Double, Double, R]
{ override def showEv1: Show[Double] = Show.doublePersistEv
  override def showEv2: Show[Double] = Show.doublePersistEv
  override def showEv3: Show[Double] = Show.doublePersistEv
  override def showEv4: Show[Double] = Show.doublePersistEv
  override def syntaxDepth(obj: R): Int = 2
}

/** common trait for [[Unshow]] type class instances for sum types with 4 or more components. */
trait Unshow4Plus[A1, A2, A3, A4, R] extends Unshow3Plus[A1, A2, A3, R] with Persist4Plus[A1, A2, A3, A4]
{ /** The [[Unshow]] type class instance for type A4. */
  def unshow4: Unshow[A4]
}

/** UnShow class for 4 logical parameter product types. */
trait Unshow4[A1, A2, A3, A4, R] extends Unshow4Plus[A1,A2, A3, A4, R] with Persist4[A1, A2, A3, A4]
{ /** Allows this [[Unshow]] instance to create object from it's 4 components. */
  def newT: (A1, A2, A3, A4) => R

  protected def fromSortedExprs(sortedExprs: RArr[Expr], pSeq: IntArr): EMon[R] =
  { val len: Int = sortedExprs.length
    val e1: EMon[A1] = ife(len > pSeq(0), unshow1.fromSettingOrExpr(name1, sortedExprs(pSeq(0))), opt1.toEMon)
    def e2: EMon[A2] = ife(len > pSeq(1), unshow2.fromSettingOrExpr(name2, sortedExprs(pSeq(1))), opt2.toEMon)
    def e3: EMon[A3] = ife(len > pSeq(2), unshow3.fromSettingOrExpr(name3, sortedExprs(pSeq(2))), opt3.toEMon)
    def e4: EMon[A4] = ife(len > pSeq(3), unshow4.fromSettingOrExpr(name4, sortedExprs(pSeq(3))), opt4.toEMon)
    e1.map4(e2, e3, e4)(newT)
  }
}

object Unshow4
{
  def apply[A1, A2, A3, A4, R](typeStr: String, name1: String, name2: String, name3: String, name4: String, newT: (A1, A2, A3, A4) => R,
    opt4: Option[A4] = None, opt3: Option[A3] = None, opt2: Option[A2] = None,  opt1: Option[A1] = None)(implicit unshow1: Unshow[A1],
    unshow2: Unshow[A2], unshow3: Unshow[A3], unshow4: Unshow[A4], ct: ClassTag[R]): Unshow4[A1, A2, A3, A4, R] =
    new Unshow4Imp(typeStr, name1, name2, name3, name4, newT, ArrPairStr[R](), opt4, opt3, opt2, opt1)(unshow1, unshow2, unshow3, unshow4)

  class Unshow4Imp[A1, A2, A3, A4, R](val typeStr: String, val name1: String, val name2: String, val name3: String, val name4: String,
    val newT: (A1, A2, A3, A4) => R, val shortKeys: ArrPairStr[R], override val opt4: Option[A4] = None, val opt3In: Option[A3] = None,
    opt2In: Option[A2] = None, opt1In: Option[A1] = None)(implicit val unshow1: Unshow[A1], val unshow2: Unshow[A2], val unshow3: Unshow[A3],
    val unshow4: Unshow[A4]) extends Unshow4[A1, A2, A3, A4, R]
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

trait UnshowInt4[R] extends Unshow4[Int, Int, Int, Int, R]
{ override def unshow1: Unshow[Int] = Unshow.intEv
  override def unshow2: Unshow[Int] = Unshow.intEv
  override def unshow3: Unshow[Int] = Unshow.intEv
  override def unshow4: Unshow[Int] = Unshow.intEv
}

object UnshowInt4
{
  def apply[R](typeStr: String, name1: String, name2: String, name3: String, name4: String, newT: (Int, Int, Int, Int) => R, opt4: Option[Int] = None,
    opt3: Option[Int] = None, opt2: Option[Int] = None, opt1: Option[Int] = None)(implicit ct: ClassTag[R]): UnshowInt4[R] =
    new UnshowInt4Imp(typeStr, name1, name2, name3, name4, newT, ArrPairStr[R](), opt4, opt3, opt2, opt1)

  class UnshowInt4Imp[R](val typeStr: String, val name1: String, val name2: String, val name3: String, val name4: String,
    val newT: (Int, Int, Int, Int) => R, val shortKeys: ArrPairStr[R], override val opt4: Option[Int] = None, opt3In: Option[Int] = None,
    opt2In: Option[Int] = None, opt1In: Option[Int] = None) extends UnshowInt4[R]
  { override val opt3: Option[Int] = ife(opt4.nonEmpty, opt3In, None)
    override val opt2: Option[Int] = ife(opt3.nonEmpty, opt2In, None)
    override val opt1: Option[Int] = ife(opt2.nonEmpty, opt1In, None)

    val defaultNum: Int = None match {
      case _ if opt3.isEmpty => 0
      case _ if opt2.isEmpty => 1
      case _ if opt1.isEmpty => 2
      case _ => 3
    }
  }
}

trait UnshowDbl4[R] extends Unshow4[Double, Double, Double, Double, R]
{ override def unshow1: Unshow[Double] = Unshow.doubleEv
  override def unshow2: Unshow[Double] = Unshow.doubleEv
  override def unshow3: Unshow[Double] = Unshow.doubleEv
  override def unshow4: Unshow[Double] = Unshow.doubleEv
}

object UnshowDbl4
{
  def apply[R](typeStr: String, name1: String, name2: String, name3: String, name4: String, newT: (Double, Double, Double, Double) => R,
    opt4: Option[Double] = None, opt3: Option[Double] = None, opt2: Option[Double] = None, opt1: Option[Double] = None)(implicit ct: ClassTag[R]):
    UnshowDbl4[R] = new UnshowDbl4Imp[R](typeStr, name1, name2, name3, name4, newT, ArrPairStr[R](), opt4, opt3, opt2, opt1)

  /** Implementation class for [[UnshowDbl4]]. */
  class UnshowDbl4Imp[R](val typeStr: String, val name1: String, val name2: String, val name3: String, val name4: String,
    val newT: (Double, Double, Double, Double) => R, val shortKeys: ArrPairStr[R], val opt4: Option[Double] = None, val opt3In: Option[Double] = None,
    opt2In: Option[Double] = None, opt1In: Option[Double] = None) extends UnshowDbl4[R]
  { override val opt3: Option[Double] = ife(opt4.nonEmpty, opt3In, None)
    override val opt2: Option[Double] = ife(opt3.nonEmpty, opt2In, None)
    override val opt1: Option[Double] = ife(opt2.nonEmpty, opt1In, None)
  }
}