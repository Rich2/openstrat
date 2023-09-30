/* Copyright 2018-23 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat
import pParse._, collection.mutable.ArrayBuffer

/** Base trait for [[PersistBase2]] and [[PersistBase3Plus]] classes. it declares the common properties of name1, name2, opt1 and opt2. It is not a base trait
 *  for [[Show2T]], as [[Show2eding]] classes do not need this data, as they can delegate to the [[Show2ed]] object to implement their interfaces. */
trait PersistBase2Plus[A1, A2] extends Any with PersistBaseN
{ /** 1st parameter name. */
  def name1: String

  /** 2nd parameter name. */
  def name2: String

  /** The optional default value for parameter 1. */
  def opt1: Option[A1]

  /** The optional default value for parameter 2. */
  def opt2: Option[A2]

  /** The declaration here allows the same field to cover [[ShowT]][A1], [[UnShow]][A1] and [[Persist]][A1]. */
  def persist1: ShowT[A1] | Unshow[A1]
}

/** A base trait for [[Show2ed]] and [[UnShow2]]. It is not a base trait for [[Show2T]], as [[Show2eding]] classes do not need this data, as they can
 *  delegate to the [[Show2ed]] object to implement their interfaces. */
trait PersistBase2[A1, A2] extends Any with PersistBase2Plus[A1, A2]
{ override def paramNames: StrArr = StrArr(name1, name2)
  override def numParams: Int = 2

  /** The declaration here allows the same field to be to cover [[ShowT]][A2] [[UnShow]][A2] and [[Persist]][A2]. */
  def persist2: ShowT[A2] | Unshow[A2]
}

/** Trait for [[ShowDec]] for a product of 2 logical elements. This trait is implemented directly by the type in question, unlike the corresponding
 *  [[Show2T]] trait which externally acts on an object of the specified type to create its String representations. For your own types it is better to
 *  inherit from Show2 and then use [[Show2eding]] or [[Persist2ElemT]] to create the type class instance for ShowT. The [[Show2eding]] or
 *  [[Persist2ed]] class will delegate to Show2 for some of its methods. It is better to use Show2 to override toString method than delegating the
 *  toString override to a [[Show2T]] instance. */
trait Show2ed[A1, A2] extends Any with ShowNed with PersistBase2[A1, A2]
{
  /** The optional default value for parameter 1. */
  override def opt1: Option[A1] = None

  /** The optional default value for parameter 2. */
  override def opt2: Option[A2] = None

  /** Element 1 of this Show 2 element product. */
  def show1: A1

  /** Element 2 of this Show 2 element product. */
  def show2: A2

  override def persist1: ShowT[A1]
  override def persist2: ShowT[A2]

  override def paramNames: StrArr = StrArr(name1, name2)
  def elemTypeNames: StrArr = StrArr(persist1.typeStr, persist2.typeStr)
  def showElemStrDecs(way: ShowStyle, decimalPlaces: Int): StrArr = StrArr(persist1.showDecT(show1, way, decimalPlaces, 0), persist2.showDecT(show2, way, decimalPlaces, 0))

  def el1Show(style: ShowStyle = ShowStandard, maxPlaces: Int = -1): String = persist1.showDecT(show1, style, maxPlaces, maxPlaces): String
  def el2Show(style: ShowStyle = ShowStandard, maxPlaces: Int = -1): String = persist2.showDecT(show2, style, maxPlaces, maxPlaces): String

  override def str: String = typeStr + (persist1.strT(show1).appendSemicolons(persist2.strT(show2))).enParenth

  override def syntaxDepth: Int = persist1.syntaxDepthT(show1).max(persist2.syntaxDepthT(show2)) + 1
}

/** Trait for Show for product of 2 Ints. This trait is implemented directly by the type in question, unlike the corresponding [[ShowInt2Eding]] trait
 *  which externally acts on an object of the specified type to create its String representations. For your own types ShowProduct is preferred over
 *  [[Show2T]]. */
trait ShowInt2Ed extends Any with Show2ed[Int, Int]
{ final override implicit def persist1: ShowT[Int] = ShowT.intPersistEv
  final override implicit def persist2: ShowT[Int] = ShowT.intPersistEv
  final override def syntaxDepth: Int = 2
}

/** Trait for Show for product of 2 Ints that is also an ElemInt2. This trait is implemented directly by the type in question, unlike the
 *  corresponding [[ShowInt2Eding]] trait which externally acts on an object of the specified type to create its String representations. For your own
 *  types ShowProduct is preferred over [[Show2T]]. */
trait ShowElemInt2 extends Any with ShowInt2Ed with Int2Elem
{ final override def int1: Int = show1
  final override def int2: Int = show2
}

/** Shows a class with 2 [[Double]] components. Note if the class also extends ElemDbl2, the dbl1 and dbl2 properties, may be different to the show1
 * and show2 properties, unless the class extends [[ShowElemDbl2]]. */
trait ShowDbl2Ed extends Any with Show2ed[Double, Double]
{ final override implicit def persist1: ShowT[Double] = ShowT.doublePersistEv
  final override implicit def persist2: ShowT[Double] = ShowT.doublePersistEv
  final override def syntaxDepth: Int = 2
}

/** Trait for Show for product of 2 Doubles that is also an [[Dbl2Elem]]. This trait is implemented directly by the type in question, unlike the
 *  corresponding [[ShowDbl2Eding]] trait which externally acts on an object of the specified type to create its String representations. For your own
 *  types ShowProduct is preferred over [[Show2T]]. */
trait ShowElemDbl2 extends Any with ShowDbl2Ed with Dbl2Elem
{ final override def dbl1: Double = show1
  final override def dbl2: Double = show2
}

/** Show type class for 2 parameter case classes. */
trait Show2T[A1, A2, R] extends ShowNT[R]
{ def fArg1: R => A1
  def fArg2: R => A2
  implicit def persist1: ShowT[A1]
  implicit def persist2: ShowT[A2]
}

/** Companion object for the [[Show2T]] type class trait that shows object with 2 logical fields. */
object Show2T
{
  def apply[A1, A2, R](typeStr: String, name1: String, fArg1: R => A1, name2: String, fArg2: R => A2, opt2: Option[A2] = None,
    opt1In: Option[A1] = None)(implicit ev1: ShowT[A1], ev2: ShowT[A2]): Show2T[A1, A2, R] =
    new Show2TImp[A1, A2, R](typeStr, name1, fArg1, name2, fArg2, opt2, opt1In)

  class Show2TImp[A1, A2, R](val typeStr: String, val name1: String, val fArg1: R => A1, val name2: String, val fArg2: R => A2, val opt2: Option[A2] = None,
    opt1In: Option[A1] = None)(implicit val persist1: ShowT[A1], val persist2: ShowT[A2]) extends Show2T[A1, A2, R] //with TypeStr2Plus[A1,A2]
  { val opt1: Option[A1] = ife(opt2.nonEmpty, opt1In, None)
    override def syntaxDepthT(obj: R): Int = persist1.syntaxDepthT(fArg1(obj)).max(persist2.syntaxDepthT(fArg2(obj))) + 1

    override def strDecs(obj: R, way: ShowStyle, maxPlaces: Int): StrArr =
      StrArr(persist1.showDecT(fArg1(obj), way, maxPlaces, 0), persist2.showDecT(fArg2(obj), way, maxPlaces, 0))
  }
}

class Show2TExtensions[A1, A2, -T](ev: Show2T[A1, A2, T], thisVal: T)
{
  /** Intended to be a multiple parameter comprehensive Show method. Intended to be paralleled by showT method on [[ShowT]] type class instances. */
  def show2(way: ShowStyle = ShowStandard, way1: ShowStyle = ShowStandard, places1: Int = -1, way2: ShowStyle = ShowStandard, places2: Int = -1):
    String = ???
}

/** Type class trait for Showing [[Show2ed]] objects. */
trait Show2eding[A1, A2, R <: Show2ed[A1, A2]] extends Show2T[A1, A2, R] with Showeding[R]
{ override def strDecs(obj: R, way: ShowStyle, maxPlaces: Int): StrArr = obj.showElemStrDecs(way, maxPlaces)
  override def fArg1: R => A1 = _.show1
  override def fArg2: R => A2 = _.show2
}

object Show2eding
{
  def apply[A1, A2, R<: Show2ed[A1, A2]](typeStr: String)(
    implicit ev1: ShowT[A1], ev2: ShowT[A2]): Show2eding[A1, A2, R] =
    new Show2edingImp[A1, A2, R](typeStr)

  /** Implementation class for the general cases of the [[Show2eding]] trait. */
  class Show2edingImp[A1, A2, R<: Show2ed[A1, A2]](val typeStr: String)(implicit val persist1: ShowT[A1], val persist2: ShowT[A2]) extends
    Show2eding[A1, A2, R]
}

/** A trait for making quick ShowT instances for [[ShowDbl2Ed]] types. It uses the functionality of the [[ShowDbl2Ed]]. */
trait ShowDbl2Eding[R <: ShowDbl2Ed] extends Show2eding[Double, Double, R]
{ override implicit def persist1: Persist[Double] = ShowT.doublePersistEv
  override implicit def persist2: Persist[Double] = ShowT.doublePersistEv
}

object ShowDbl2Eding
{ /** Factory apply method for creating quick ShowT instances for products of 2 Doubles. */
  def apply[R <: ShowElemDbl2](typeStr: String): ShowShowDbl2TImp[R] = new ShowShowDbl2TImp[R](typeStr)

  /** Implementation class for the general cases of the [[ShowDbl2Eding]] trait. */
  class ShowShowDbl2TImp[R <: ShowDbl2Ed](val typeStr: String) extends ShowDbl2Eding[R]
}

/** A trait for making quick ShowT instances for [[ShowElemInt2]] classes. It uses the functionality of the [[ShowelemInt2]]. */
trait ShowInt2Eding[R <: ShowInt2Ed] extends Show2eding[Int, Int, R]
{ override implicit def persist1: Persist[Int] = ShowT.intPersistEv
  override implicit def persist2: Persist[Int] = ShowT.intPersistEv
}

object ShowInt2Eding
{ /** Factory apply method for creating quick ShowT instances for products of 2 [[Int]]s. */
  def apply[R <: ShowElemInt2](typeStr: String, name1: String, name2: String, opt2: Option[Int] = None, opt1In: Option[Int] = None):
    ShowInt2Eding[R] = new ShowShowInt2TImp[R](typeStr, name1, name2, opt2,opt1In)

  class ShowShowInt2TImp[R <: ShowElemInt2](val typeStr: String, val name1: String, val name2: String, val opt2: Option[Int] = None,
    opt1In: Option[Int] = None) extends ShowInt2Eding[R]
  { val opt1: Option[Int] = ife(opt2.nonEmpty, opt1In, None)
  }
}

/** UnShow type class trait for a 2 element Product. */
trait Unshow2[A1, A2, R] extends UnshowN[R] with PersistBase2[A1, A2]
{ /** The UnShow type class instance for type A1. */
  def persist1: Unshow[A1]

  /** The UnShow type class instance for type A2. */
  def persist2: Unshow[A2]

  def newT: (A1, A2) => R

  protected def fromSortedExprs(sortedExprs: RArr[Expr], pSeq: IntArr): EMon[R] =
  { val len: Int = sortedExprs.length
    val r0: EMon[A1] = ife(len > pSeq(0), persist1.fromSettingOrExpr(name1, sortedExprs(pSeq(0))), opt1.toEMon)
    def e2: EMon[A2] = ife(len > pSeq(1), persist2.fromSettingOrExpr(name2,sortedExprs(pSeq(1))), opt2.toEMon)
    r0.map2(e2)(newT)
  }
}

object Unshow2
{
  def apply[A1, A2, R](typeStr: String, name1: String, name2: String, newT: (A1, A2) => R, opt2: Option[A2] = None, opt1: Option[A1] = None)(implicit
    ev1: Unshow[A1], ev2: Unshow[A2]): Unshow2[A1, A2, R] = new Unshow2Imp[A1, A2, R](typeStr, name1, name2, newT, opt2, opt1)

  case class Unshow2Imp[A1, A2, R](typeStr: String, name1: String, name2: String, newT: (A1, A2) => R, val opt2: Option[A2], opt1In: Option[A1])(implicit
    val persist1: Unshow[A1], val persist2: Unshow[A2]) extends Unshow2[A1, A2, R]
  { val opt1: Option[A1] = ife(opt2.nonEmpty, opt1In, None)
  }
}

/** Persistence class for product 2 type class. It ShowTs and UnShows objects with 2 logical parameters. */
trait Persist2[A1, A2, R] extends Show2T[A1, A2, R] with Unshow2[A1, A2, R] with PersistN[R]
{ override def persist1: Persist[A1]
  override def persist2: Persist[A2]
}

/** Factory object for Persist product 2 type class that persists objects with 2 parameters. */
object Persist2
{ /** apply facory method for [[Persist2]] type class instances. */
  def apply[A1, A2, R](typeStr: String, name1: String, fArg1: R => A1, name2: String, fArg2: R => A2, newT: (A1, A2) => R,
    opt2: Option[A2] = None, opt1: Option[A1] = None)(implicit ev1: Persist[A1], ev2: Persist[A2]): Persist2[A1, A2, R] =
    new Persist2Imp(typeStr, name1, fArg1, name2, fArg2, newT, opt2, opt1)(ev1, ev2)

  class Persist2Imp[A1, A2, R](val typeStr: String, val name1: String, val fArg1: R => A1, val name2: String, val fArg2: R => A2, val newT: (A1, A2) => R,
    val opt2: Option[A2] = None, opt1In: Option[A1] = None)(implicit val persist1: Persist[A1], val persist2: Persist[A2]) extends Persist2[A1, A2, R]
  { val opt1: Option[A1] = ife(opt2.nonEmpty, opt1In, None)

    override def strDecs(obj: R, way: ShowStyle, maxPlaces: Int): StrArr = ???

    /** Simple values such as Int, String, Double have a syntax depth of one. A Tuple3[String, Int, Double] has a depth of 2. Not clear whether this
     * should always be determined at compile time or if sometimes it should be determined at runtime. */
    override def syntaxDepthT(obj: R): Int = ???
  }
}

/** Persist type class for types that extends [[Show2ed]]. */
trait Persist2ed[A1, A2, R <: Show2ed[A1, A2]] extends Persist2[A1, A2, R] with Show2eding[A1, A2, R]

/** Companion object for the [[Persist2ed]] class the persists object that extend [[Show2ed]]. Contains an apply factory method. */
object Persist2ed
{ /** Factory apply method for [[Persist2ed]], that Persists [[Show2ed]] objects. */
  def apply[A1, A2, R <: Show2ed[A1, A2]](typeStr: String, name1: String, name2: String, newT: (A1, A2) => R,
    opt2: Option[A2] = None, opt1: Option[A1] = None)(implicit ev1In: Persist[A1], ev2In: Persist[A2]): Persist2ed[A1, A2, R] =
    new PersistShow2Imp[A1, A2, R](typeStr, name1, name2, newT, opt2, opt1)

  class PersistShow2Imp[A1, A2, R <: Show2ed[A1, A2]](val typeStr: String, val name1: String, val name2: String, val newT: (A1, A2) => R,
    val opt2: Option[A2] = None, opt1In: Option[A1] = None)(implicit val persist1: Persist[A1], val persist2: Persist[A2]) extends Persist2ed[A1, A2, R]
  { val opt1: Option[A1] = ife(opt2.nonEmpty, opt1In, None)
  }
}

/** Persistence type class for types that extend [[ShowElemInt2]]. */
class PersistInt2Ed[R <: ShowElemInt2](val typeStr: String, val name1: String, val name2: String, val newT: (Int, Int) => R,
  val opt2: Option[Int] = None, opt1In: Option[Int] = None) extends Persist2ed[Int, Int, R] with ShowInt2Eding[R]
{ val opt1: Option[Int] = ife(opt2.nonEmpty, opt1In, None)
}

object PersistInt2Ed
{ /** Factory apply method for [[PersistInt2Ed]] that persists objects of type [[ShowElemInt2]]. */
  def apply[R <: ShowElemInt2](typeStr: String, name1: String, name2: String, newT: (Int, Int) => R): PersistInt2Ed[R] =
    new PersistInt2Ed[R](typeStr, name1, name2, newT)
}

/** Persistence class for types that extend [[ShowDbl2Ed]]. */
class PersistDbl2Ed[R <: ShowDbl2Ed](val typeStr: String, val name1: String, val name2: String, val newT: (Double, Double) => R,
  val opt2: Option[Double] = None, opt1In: Option[Double] = None) extends Persist2ed[Double, Double, R] with ShowDbl2Eding[R]
{ val opt1: Option[Double] = ife(opt2.nonEmpty, opt1In, None)
}

/**  Class to persist [[Int2Arr]] collection classes. */
abstract class PersistArrInt2s[A <: Int2Elem, M <: Int2Arr[A]](val typeStr: String) extends IntNSeqLikePersist[A, M]
{
  override def appendtoBuffer(buf: ArrayBuffer[Int], value: A): Unit =
  { buf += value.int1
    buf += value.int2
  }

  override def syntaxDepthT(obj: M): Int = 3
}