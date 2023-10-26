/* Copyright 2018-23 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat
import pParse._

/** Base trait for [[PersistBase2]] and [[Persist3Plus]] classes. it declares the common properties of name1, name2, opt1 and opt2. It is not a base trait
 *  for [[Show2]], as [[ShowTell2]] classes do not need this data, as they can delegate to the [[Tell2]] object to implement their interfaces. */
trait PersistBase2Plus[A1, A2] extends Any with PersistBaseN
{ /** 1st parameter name. */
  def name1: String

  /** 2nd parameter name. */
  def name2: String

  /** The optional default value for parameter 1. */
  def opt1: Option[A1]

  /** The optional default value for parameter 2. */
  def opt2: Option[A2]
}

/** A base trait for [[Tell2]] and [[UnShow2]]. It is not a base trait for [[Show2]], as [[ShowTell2]] classes do not need this data, as they can
 *  delegate to the [[Tell2]] object to implement their interfaces. */
trait PersistBase2[A1, A2] extends Any with PersistBase2Plus[A1, A2]
{ override def paramNames: StrArr = StrArr(name1, name2)
  override def numParams: Int = 2
}

/** [[Show]] type class for 2 parameter case classes. */
trait Show2[A1, A2, R] extends ShowN[R]
{ def fArg1: R => A1
  def fArg2: R => A2
  implicit def show1: Show[A1]
  implicit def show2: Show[A2]

  override def strDecs(obj: R, way: ShowStyle, maxPlaces: Int): StrArr =
    StrArr(show1.showDec(fArg1(obj), way, maxPlaces, 0), show2.showDec(fArg2(obj), way, maxPlaces, 0))
}

/** Companion object for the [[Show2]] type class trait that shows object with 2 logical fields. */
object Show2
{
  def apply[A1, A2, R](typeStr: String, name1: String, fArg1: R => A1, name2: String, fArg2: R => A2, opt2: Option[A2] = None,
    opt1: Option[A1] = None)(implicit show1: Show[A1], show2: Show[A2]): Show2[A1, A2, R] =
    new Show2Imp[A1, A2, R](typeStr, name1, fArg1, name2, fArg2, opt2, opt1)

  /** Implementation class for the general cases of [[Show2]] trait. */
  class Show2Imp[A1, A2, R](val typeStr: String, val name1: String, val fArg1: R => A1, val name2: String, val fArg2: R => A2, val opt2: Option[A2] = None,
    opt1In: Option[A1] = None)(implicit val show1: Show[A1], val show2: Show[A2]) extends Show2[A1, A2, R]
  { val opt1: Option[A1] = ife(opt2.nonEmpty, opt1In, None)
    override def syntaxDepth(obj: R): Int = show1.syntaxDepth(fArg1(obj)).max(show2.syntaxDepth(fArg2(obj))) + 1
  }
}

/** Extension methods for [[Show2]] type class instances. */
class Show2Extensions[A1, A2, -T](ev: Show2[A1, A2, T], thisVal: T)
{
  /** Intended to be a multiple parameter comprehensive Show method. Intended to be paralleled by showT method on [[Show]] type class instances. */
  def show2(way: ShowStyle = ShowStandard, way1: ShowStyle = ShowStandard, places1: Int = -1, way2: ShowStyle = ShowStandard, places2: Int = -1):
    String = ???
}

/** [[Show]] type class trait for types with 2 [[Int]] Show components. */
trait ShowInt2[R] extends Show2[Int, Int, R]
{ override def show1: Show[Int] = Show.intEv
  override def show2: Show[Int] = Show.intEv
  override def syntaxDepth(obj: R): Int = 2
}

object ShowInt2
{
  def apply[R](typeStr: String, name1: String, fArg1: R => Int, name2: String, fArg2: R => Int, opt2: Option[Int] = None, opt1: Option[Int] = None):
    ShowInt2[R] = new ShowInt2Imp[R](typeStr, name1, fArg1, name2, fArg2, opt2, opt1)

  /** Implementation class for the general cases of [[ShowInt2]] trait. */
  class ShowInt2Imp[R](val typeStr: String, val name1: String, val fArg1: R => Int, val name2: String, val fArg2: R => Int, val opt2: Option[Int] = None,
    opt1In: Option[Int] = None) extends ShowInt2[R]
  { val opt1: Option[Int] = ife(opt2.nonEmpty, opt1In, None)
  }
}


/** [[Show]] type class trait for types with 2 [[Double]] Show components. */
trait ShowDbl2[R] extends Show2[Double, Double, R]
{ override def show1: Show[Double] = Show.doublePersistEv
  override def show2: Show[Double] = Show.doublePersistEv
  override def syntaxDepth(obj: R): Int = 2
}

object ShowDbl2
{
  def apply[R](typeStr: String, name1: String, fArg1: R => Double, name2: String, fArg2: R => Double, opt2: Option[Double] = None, opt1: Option[Double] = None):
  ShowDbl2[R] = new ShowDbl2Imp[R](typeStr, name1, fArg1, name2, fArg2, opt2, opt1)

  /** Implementation class for the general cases of [[ShowDbl2]] trait. */
  class ShowDbl2Imp[R](val typeStr: String, val name1: String, val fArg1: R => Double, val name2: String, val fArg2: R => Double, val opt2: Option[Double] = None,
    opt1In: Option[Double] = None) extends ShowDbl2[R]
  { val opt1: Option[Double] = ife(opt2.nonEmpty, opt1In, None)
  }
}


/** UnShow type class trait for a 2 element Product. */
trait Unshow2[A1, A2, R] extends UnshowN[R] with PersistBase2[A1, A2]
{ /** The [[Unshow]] type class instance for type A1. */
  def unshow1: Unshow[A1]

  /** The [[Unshow]] type class instance for type A2. */
  def unshow2: Unshow[A2]

  /** The function to construct an object of type R from its 2 components." */
  def newT: (A1, A2) => R

  protected def fromSortedExprs(sortedExprs: RArr[Expr], pSeq: IntArr): EMon[R] =
  { val len: Int = sortedExprs.length
    val r0: EMon[A1] = ife(len > pSeq(0), unshow1.fromSettingOrExpr(name1, sortedExprs(pSeq(0))), opt1.toEMon)
    def e2: EMon[A2] = ife(len > pSeq(1), unshow2.fromSettingOrExpr(name2,sortedExprs(pSeq(1))), opt2.toEMon)
    r0.map2(e2)(newT)
  }
}

object Unshow2
{
  def apply[A1, A2, R](typeStr: String, name1: String, name2: String, newT: (A1, A2) => R, opt2: Option[A2] = None, opt1: Option[A1] = None)(implicit
    ev1: Unshow[A1], ev2: Unshow[A2]): Unshow2[A1, A2, R] = new Unshow2Imp[A1, A2, R](typeStr, name1, name2, newT, opt2, opt1)

  case class Unshow2Imp[A1, A2, R](typeStr: String, name1: String, name2: String, newT: (A1, A2) => R, override val opt2: Option[A2], opt1In: Option[A1])(implicit
    val unshow1: Unshow[A1], val unshow2: Unshow[A2]) extends Unshow2[A1, A2, R]
  { override val opt1: Option[A1] = ife(opt2.nonEmpty, opt1In, None)
  }
}

trait UnshowInt2[R] extends Unshow2[Int, Int, R]
{ override implicit def unshow1: Unshow[Int] = Unshow.intEv
  override implicit def unshow2: Unshow[Int] = Unshow.intEv
}

object UnshowInt2
{
  def apply[R](typeStr: String, name1: String, name2: String, newT: (Int, Int) => R, opt2: Option[Int] = None,
    opt1In: Option[Int] = None): UnshowInt2[R] = new UnshowInt2Imp[R](typeStr, name1, name2, newT, opt2, opt1In)

  /** Implementation class for the general cases of [[UnshowDbl2]] trait. */
  class UnshowInt2Imp[R](val typeStr: String, val name1: String, val name2: String, val newT: (Int, Int) => R,
    override val opt2: Option[Int] = None, opt1In: Option[Int] = None) extends UnshowInt2[R]
  { override val opt1: Option[Int] = ife(opt2.nonEmpty, opt1In, None)
  }
}

trait UnshowDbl2[R] extends Unshow2[Double, Double, R]
{ override implicit def unshow1: Unshow[Double] = Unshow.doubleEv
  override implicit def unshow2: Unshow[Double] = Unshow.doubleEv
}

object UnshowDbl2
{
  def apply[R](typeStr: String, name1: String, name2: String, newT: (Double, Double) => R, opt2: Option[Double] = None,
    opt1In: Option[Double] = None): UnshowDbl2[R] = new UnshowDbl2Imp[R](typeStr, name1, name2, newT, opt2, opt1In)

  /** Implementation class for the general cases of [[UnshowDbl2]] trait. */
  class UnshowDbl2Imp[R](val typeStr: String, val name1: String, val name2: String, val newT: (Double, Double) => R,
    override val opt2: Option[Double] = None, opt1In: Option[Double] = None) extends UnshowDbl2[R]
  { override val opt1: Option[Double] = ife(opt2.nonEmpty, opt1In, None)
  }
}