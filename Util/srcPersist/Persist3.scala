/* Copyright 2018-23 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat
import pParse._, reflect.ClassTag

/** A base trait for Tell3+, Show3+ and Unshow3+ classes. Declares the common properties of name1 - 3 and opt1 - 3. */
trait Persist3Plus[A1, A2, A3] extends Any with Persist2Plus[A1, A2]
{ /** 3rd parameter name. */
  def name3: String

  /** The optional default value for parameter 3. */
  def opt3: Option[A3]
}

/** Common base trait for [[Show3]], [[Unshow3]] and [[Persist3]]. */
trait Persist3[A1, A2, A3] extends Any with Persist3Plus[A1, A2, A3]
{ final override def paramNames: StrArr = StrArr(name1, name2, name3)
  final override def numParams: Int = 3
}

/** [[Show]] type class for 3 field product types. */
trait Show3Plus[A1, A2, A3, A] extends Show2Plus[A1, A2, A] with Persist3Plus[A1, A2, A3]
{ /** Gets the 2nd show field from the object. The Show fields do not necessarily correspond to the fields in memory.*/
  def fArg3: A => A3

  /** Show type class instance for the 2nd Show field. */
  implicit def showEv3: Show[A3]

  /** Shows parameter 3 of the object. */
  def show3(obj: A, way: ShowStyle, maxPlaces: Int = -1, minPlaces: Int = 0): String = showEv3.show(fArg3(obj), way, maxPlaces, minPlaces)
}

/** Show type class for 3 parameter case classes. */
trait Show3[A1, A2, A3, A] extends Show3Plus[A1, A2, A3, A] with Persist3[A1, A2, A3] with ShowN[A]
{ override def fieldShows: RArr[Show[_]] = RArr(showEv1, showEv2, showEv3)

  override def strs(obj: A, way: ShowStyle, maxPlaces: Int = -1, minPlaces: Int = 0): StrArr = opt3 match {
    case Some(a3) if opt1 == Some(fArg1(obj)) && opt2 == Some(fArg2(obj)) && a3 == fArg3(obj) => StrArr()
    case Some(a3) if opt2 == Some(fArg2(obj)) && a3 == fArg3(obj) => StrArr(show1(obj, way, maxPlaces, minPlaces))
    case Some(a3) if a3 == fArg3(obj) => StrArr(show1(obj, way, maxPlaces, minPlaces), show2(obj, way, maxPlaces, minPlaces))
    case _ => StrArr(show1(obj, way, maxPlaces, minPlaces), show2(obj, way, maxPlaces, minPlaces), show3(obj, way, maxPlaces, minPlaces))
  }
}

object Show3
{
  def apply[A1, A2, A3, R](typeStr: String, name1: String, fArg1: R => A1, name2: String, fArg2: R => A2, name3: String, fArg3: R => A3,
    opt3: Option[A3] = None, opt2In: Option[A2] = None, opt1In: Option[A1] = None)(implicit ev1: Show[A1], ev2: Show[A2], ev3: Show[A3],
    ct: ClassTag[R]):
  Show3[A1, A2, A3, R] = new Show3Imp[A1, A2, A3, R](typeStr, name1, fArg1, name2, fArg2, name3, fArg3, ArrPairStr[R](), opt3, opt2In, opt1In)

  def shorts[A1, A2, A3, R](typeStr: String, name1: String, fArg1: R => A1, name2: String, fArg2: R => A2, name3: String, fArg3: R => A3,
    shortKeys: ArrPairStr[R], opt3: Option[A3] = None, opt2In: Option[A2] = None, opt1In: Option[A1] = None)(implicit ev1: Show[A1], ev2: Show[A2],
    ev3: Show[A3]): Show3[A1, A2, A3, R] =
    new Show3Imp[A1, A2, A3, R](typeStr, name1, fArg1, name2, fArg2, name3, fArg3, shortKeys, opt3, opt2In, opt1In)

  /** Implementation class for the general cases of the [[Show3]] trait. */
  class Show3Imp[A1, A2, A3, R](val typeStr: String, val name1: String, val fArg1: R => A1, val name2: String, val fArg2: R => A2, val name3: String,
    val fArg3: R => A3, val shortKeys: ArrPairStr[R], override val opt3: Option[A3] = None, opt2In: Option[A2] = None, opt1In: Option[A1] = None)(
    implicit val showEv1: Show[A1], val showEv2: Show[A2], val showEv3: Show[A3]) extends Show3[A1, A2, A3, R]
  { override val opt2: Option[A2] = ife(opt3.nonEmpty, opt2In, None)
    override val opt1: Option[A1] = ife(opt2.nonEmpty, opt1In, None)
    override def syntaxDepth(obj: R): Int = showEv1.syntaxDepth(fArg1(obj)).max(showEv2.syntaxDepth(fArg2(obj))).max(showEv3.syntaxDepth(fArg3(obj))) + 1
  }
}

/** [[Show]] type class trait for types with 3 [[Int]] Show components. */
trait ShowInt3[R] extends Show3[Int, Int, Int, R]
{ def showEv1: Show[Int] = Show.intEv
  def showEv2: Show[Int] = Show.intEv
  def showEv3: Show[Int] = Show.intEv
  override def syntaxDepth(obj: R): Int = 2
}

object ShowInt3
{
  def apply[R](typeStr: String, name1: String, fArg1: R => Int, name2: String, fArg2: R => Int, name3: String, fArg3: R => Int,
    newT: (Int, Int, Int) => R, opt3: Option[Int] = None, opt2: Option[Int] = None, opt1: Option[Int] = None)(implicit ct: ClassTag[R]): ShowInt3[R] =
    new ShowInt3Imp[R](typeStr, name1, fArg1, name2, fArg2, name3: String, fArg3, newT, ArrPairStr[R](), opt3, opt2, opt1)

  /** Implementation class for the general cases of [[ShowInt2]] trait. */
  class ShowInt3Imp[R](val typeStr: String, val name1: String, val fArg1: R => Int, val name2: String, val fArg2: R => Int, val name3: String,
    val fArg3: R => Int, val newT: (Int, Int, Int) => R, val shortKeys: ArrPairStr[R], override val opt3: Option[Int], opt2In: Option[Int] = None,
    opt1In: Option[Int] = None) extends ShowInt3[R]
  { override val opt2: Option[Int] = ife(opt3.nonEmpty, opt2In, None)
    override val opt1: Option[Int] = ife(opt2.nonEmpty, opt1In, None)
  }
}

/** [[Show]] type class trait for types with 3 [[Double]] Show components. */
trait ShowDbl3[R] extends Show3[Double, Double, Double, R]
{ def showEv1: Show[Double] = Show.doublePersistEv
  def showEv2: Show[Double] = Show.doublePersistEv
  def showEv3: Show[Double] = Show.doublePersistEv
  override def syntaxDepth(obj: R): Int = 2
}

object ShowDbl3
{
  def apply[R](typeStr: String, name1: String, fArg1: R => Double, name2: String, fArg2: R => Double, name3: String, fArg3: R => Double,
    opt3: Option[Double] = None, opt2: Option[Double] = None, opt1: Option[Double] = None)(implicit classTag: ClassTag[R]): ShowDbl3[R] =
    new ShowDbl3Imp[R](typeStr, name1, fArg1, name2, fArg2, name3, fArg3, ArrPairStr[R](), opt3, opt2, opt1)

  class ShowDbl3Imp[R](val typeStr: String, val name1: String, val fArg1: R => Double, val name2: String, val fArg2: R => Double, val name3: String,
    val fArg3: R => Double, val shortKeys: ArrPairStr[R], override val opt3: Option[Double] = None, opt2In: Option[Double] = None,
    opt1In: Option[Double] = None) extends ShowDbl3[R]
  { override def opt2: Option[Double] = ife(opt3.nonEmpty, opt2In, None)
    override def opt1: Option[Double] = ife(opt2.nonEmpty, opt1In, None)
  }
}

/** common trait for [[Unshow]] type class instances for sum types with 3 or more components. */
trait Unshow3Plus[A1, A2, A3, R] extends Unshow2Plus[A1, A2, R] with Persist3Plus[A1, A2, A3]
{ /** The [[Unshow]] type class instance for type A3. */
  def unshow3: Unshow[A3]
}

/** UnShow class for 3 logical parameter product types. */
trait Unshow3[A1, A2, A3, R] extends Unshow3Plus[A1, A2, A3, R] with Persist3[A1, A2, A3]
{ /** Method fpr creating a value of type R from values A1, A2, A3. */
  def newT: (A1, A2, A3) => R

  protected def fromSortedExprs(sortedExprs: RArr[Expr], pSeq: IntArr): EMon[R] =
  { val len: Int = sortedExprs.length
    val e1: EMon[A1] = ife(len > pSeq(0), unshow1.fromSettingOrExpr(name1, sortedExprs(pSeq(0))), opt1.toEMon)
    def e2: EMon[A2] = ife(len > pSeq(1), unshow2.fromSettingOrExpr(name2, sortedExprs(pSeq(1))), opt2.toEMon)
    def e3: EMon[A3] = ife(len > pSeq(2), unshow3.fromSettingOrExpr(name3, sortedExprs(pSeq(2))), opt3.toEMon)
    e1.map3(e2, e3)(newT)
  }
}

object Unshow3
{
  def apply[A1, A2, A3, R](typeStr: String, name1: String, name2: String, name3: String, newT: (A1, A2, A3) => R, opt3: Option[A3] = None,
    opt2: Option[A2] = None, opt1: Option[A1] = None)(implicit persist1: Unshow[A1], persist2: Unshow[A2], persist3: Unshow[A3], ct: ClassTag[R]):
  Unshow3[A1, A2, A3, R] = new Unshow3Imp[A1, A2, A3, R](typeStr, name1, name2, name3, newT, ArrPairStr[R](), opt3, opt2, opt1)

  def shorts[A1, A2, A3, R](typeStr: String, name1: String, name2: String, name3: String, newT: (A1, A2, A3) => R, shortKeys: ArrPairStr[R],
    opt3: Option[A3] = None, opt2: Option[A2] = None, opt1: Option[A1] = None)(implicit persist1: Unshow[A1], persist2: Unshow[A2],
    persist3: Unshow[A3]): Unshow3[A1, A2, A3, R] = new Unshow3Imp[A1, A2, A3, R](typeStr, name1, name2, name3, newT, shortKeys, opt3, opt2, opt1)

  class Unshow3Imp[A1, A2, A3, R](val typeStr: String, val name1: String, val name2: String, val name3: String, val newT: (A1, A2, A3) => R,
    val shortKeys: ArrPairStr[R], override val opt3: Option[A3] = None, opt2In: Option[A2] = None, opt1In: Option[A1] = None)(implicit
    val unshow1: Unshow[A1], val unshow2: Unshow[A2], val unshow3: Unshow[A3]) extends Unshow3[A1, A2, A3, R]
  {
    override def opt2: Option[A2] = ife(opt3.nonEmpty , opt2In, None)
    override def opt1: Option[A1] = ife(opt2.nonEmpty , opt1In, None)
  }
}

trait UnshowInt3[R] extends Unshow3[Int, Int, Int, R]
{ override def unshow1: Unshow[Int] = Unshow.intEv
  override def unshow2: Unshow[Int] = Unshow.intEv
  override def unshow3: Unshow[Int] = Unshow.intEv
}

/** Companion object for [[UnshowInt3]] trait contains implementation class and factory apply method. */
object UnshowInt3
{ def apply[R](typeStr: String, name1: String, name2: String, name3: String, newT: (Int, Int, Int) => R, opt3: Option[Int] = None,
    opt2: Option[Int] = None, opt1: Option[Int] = None)(implicit classTag: ClassTag[R]): UnshowInt3[R] =
    new UnshowInt3Imp(typeStr, name1, name2, name3, newT, ArrPairStr[R](), opt3, opt2, opt1)

  class UnshowInt3Imp[R](val typeStr: String, val name1: String, val name2: String, val name3: String, val newT: (Int, Int, Int) => R,
  val shortKeys: ArrPairStr[R], override val opt3: Option[Int] = None, opt2In: Option[Int] = None, opt1In: Option[Int] = None) extends UnshowInt3[R]
  { override val opt2: Option[Int] = ife(opt3.nonEmpty, opt2In, None)
    override val opt1: Option[Int] = ife(opt2.nonEmpty, opt1In, None)

    val defaultNum: Int = None match
    { case _ if opt3.isEmpty => 0
      case _ if opt2.isEmpty => 1
      case _ if opt1.isEmpty => 2
      case _ => 3
    }
  }
}

trait UnshowDbl3[R] extends Unshow3[Double, Double, Double, R]
{ override def unshow1: Unshow[Double] = Unshow.doubleEv
  override def unshow2: Unshow[Double] = Unshow.doubleEv
  override def unshow3: Unshow[Double] = Unshow.doubleEv
}

object UnshowDbl3
{ def apply[R](typeStr: String, name1: String, name2: String, name3: String, newT: (Double, Double, Double) => R, opt3: Option[Double] = None,
    opt2: Option[Double] = None, opt1: Option[Double] = None)(implicit ct: ClassTag[R]): UnshowDbl3[R] =
    new UnshowDbl3Imp[R](typeStr, name1, name2, name3, newT, ArrPairStr[R](), opt3, opt2, opt1)

  /** Implementation class for [[UnshowDbl3]]. */
  class UnshowDbl3Imp[R](val typeStr: String, val name1: String, val name2: String, val name3: String, val newT: (Double, Double, Double) => R,
    val shortKeys: ArrPairStr[R], override val opt3: Option[Double] = None, opt2In: Option[Double] = None, opt1In: Option[Double] = None) extends
    UnshowDbl3[R]
  { override val opt2: Option[Double] = ife(opt3.nonEmpty, opt2In, None)
    override val opt1: Option[Double] = ife(opt2.nonEmpty, opt1In, None)
  }
}