/* Copyright 2018-21 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat
import pParse._, collection.mutable.ArrayBuffer

/** Trait for [[Show]] for a product of 2 logical elements. This trait is implemented directly by the type in question, unlike the corresponding
 *  [[Show2T]] trait which externally acts on an object of the specified type to create its String representations. For your own types it is better to
 *  inherit from Show2 and then use [[ShowShow2T]] or [[Persist2ElemT]] to create the type class instance for ShowT. The [[ShowShow2T]] or
 *  [[PersistShow2]] class will delegate to Show2 for some of its methods. It is better to use Show2 to override toString method than delegating the
 *  toString override to a [[Show2T]] instance. */
trait Show2[A1, A2] extends Any with ShowProduct
{ /** the name of the 1st element of this 2 element product. */
  def name1: String

  /** the name of the 2nd element of this 2 element product. */
  def name2: String

  /** Element 1 of this Show 2 element product. */
  def show1: A1

  /** Element 2 of this Show 2 element product. */
  def show2: A2

  /** The ShowT type class instance for the 1st element of this 2 element product. */
  implicit def showT1: ShowT[A1]

  /** The ShowT type class instance for the 2nd element of this 2 element product. */
  implicit def showT2: ShowT[A2]

  def elemNames: Strings = Strings(name1, name2)
  def elemTypeNames: Strings = Strings(showT1.typeStr, showT2.typeStr)
  def showElemStrs(way: ShowStyle, decimalPlaces: Int): Strings = Strings(showT1.showT(show1, way, decimalPlaces, 0), showT2.showT(show2, way, decimalPlaces, 0))
}

/** Trait for Show for product of 2 Ints that is also an ElemInt2. This trait is implemented directly by the type in question, unlike the
 *  corresponding [[ShowShowInt2T]] trait which externally acts on an object of the specified type to create its String representations. For your own
 *  types ShowProduct is preferred over [[Show2T]]. */
trait ShowElemInt2 extends Any with Show2[Int, Int] with ElemInt2
{ final override implicit def showT1: ShowT[Int] = ShowT.intPersistImplicit
  final override implicit def showT2: ShowT[Int] = ShowT.intPersistImplicit
  final override def syntaxDepth: Int = 2
  final override def int1: Int = show1
  final override def int2: Int = show2
}

/** Trait for Show for product of 2 Ints. This trait is implemented directly by the type in question, unlike the corresponding [[ShowShowInt2T]]
 *  trait which externally acts on an object of the specified type to create its String representations. For your own types ShowProduct is preferred
 *  over [[Show2T]]. */
trait Show2Base32s extends Any with ShowElemInt2
{

}

/** Shows a class with 2 [[Double]] components. Note if the class also extends ElemDbl2, the dbl1 and dbl2 properties, may be different to the show1
 * and show2 properties, unless the class extends [[ShowElemDbl2]]. */
trait ShowDbl2 extends Any with Show2[Double, Double]
{ final override implicit def showT1: ShowT[Double] = ShowT.doublePersistImplicit
  final override implicit def showT2: ShowT[Double] = ShowT.doublePersistImplicit
  final override def syntaxDepth: Int = 2
}

/** Trait for Show for product of 2 Doubles that is also an [[ElemDbl2]]. This trait is implemented directly by the type in question, unlike the
 *  corresponding [[ShowShowDbl2T]] trait which externally acts on an object of the specified type to create its String representations. For your own
 *  types ShowProduct is preferred over [[Show2T]]. */
trait ShowElemDbl2 extends Any with ShowDbl2 with ElemDbl2
{ final override def dbl1: Double = show1
  final override def dbl2: Double = show2
}

trait ShowShow2T[A1, A2, R <: Show2[A1, A2]] extends ShowShowT[R]

object ShowShow2T
{
  def apply[A1, A2, R<: Show2[A1, A2]](typeStrIn: String): ShowShow2T[A1, A2, R] = new ShowShow2T[A1, A2, R]
  { override def typeStr: String = typeStrIn
    override def showT(obj: R, way: ShowStyle, maxPlaces: Int, minPlaces: Int): String = obj.show(way, maxPlaces, 0)
  }
}

/** A base trait for [[Show2T]] and [[UnShow2]], declares the common properties of name1, name2, opt1 and opt2. */
trait Persist2Base[A1, A2, R] extends TypeStred
{ /** 1st parameter name. */
  def name1: String

  /** 2nd parameter name. */
  def name2: String

  /** The optional default value for parameter 1. */
  def opt1: Option[A1]

  /** The optional default value for parameter 2. */
  def opt2: Option[A2]
}

/** Show type class for 2 parameter case classes. */
trait Show2T[A1, A2, R] extends ShowProductT[R] with Persist2Base[A1, A2, R]
{ def fArg1: R => A1
  def fArg2: R => A2
  implicit def ev1: ShowT[A1]
  implicit def ev2: ShowT[A2]
  override def syntaxDepthT(obj: R): Int = ev1.syntaxDepthT(fArg1(obj)).max(ev2.syntaxDepthT(fArg2(obj))) + 1

  override def strs(obj: R, way: ShowStyle, decimalPlaces: Int): Strings =
    Strings(ev1.showT(fArg1(obj), way, decimalPlaces, 0), ev2.showT(fArg2(obj), way, decimalPlaces, 0))
}

object Show2T
{
  def apply [A1, A2, R](typeStrIn: String, name1In: String, fArg1In: R => A1, name2In: String, fArg2In: R => A2, opt2In: Option[A2] = None,
    opt1In: Option[A1] = None)(implicit ev1In: ShowT[A1], ev2In: ShowT[A2]): Show2T[A1, A2, R] = new Show2T[A1, A2, R]
  {
    override def typeStr: String = typeStrIn
    override def name1: String = name1In
    override def fArg1: R => A1 = fArg1In
    override def name2: String = name2In
    override def fArg2: R => A2 = fArg2In
    override implicit def ev1: ShowT[A1] = ev1In
    override implicit def ev2: ShowT[A2] = ev2In
    val opt2: Option[A2] = opt2In
    val opt1: Option[A1] = ife(opt2.nonEmpty, opt1In, None)
  }
}

class Show2TExtensions[A1, A2, -T](ev: Show2T[A1, A2, T], thisVal: T)
{
  /** Intended to be a multiple parameter comprehensive Show method. Intended to be paralleled by showT method on [[ShowT]] type class instances. */
  def show2(way: ShowStyle = ShowStandard, way1: ShowStyle = ShowStandard, places1: Int = -1, way2: ShowStyle = ShowStandard, places2: Int = -1):
    String = ???
}

/** A trait for making quick ShowT instances for [[ShowDbl2]] types. It uses the functionality of the [[ShowDbl2]]. */
trait ShowShowDbl2T[R <: ShowDbl2] extends ShowShow2T[Double, Double, R]

object ShowShowDbl2T
{ /** Factory apply method for creating quick ShowT instances for products of 2 Doubles. */
  def apply[R <: ShowElemDbl2](typeStrIn: String): ShowShowDbl2T[R] = new ShowShowDbl2T[R]()
  { val typeStr: String = typeStrIn
  }
}

/** A trait for making quick ShowT instances for [[ShowElemInt2]] classes. It uses the functionality of the [[ShowelemInt2]]. */
trait ShowShowInt2T[R <: ShowElemInt2] extends ShowShow2T[Int, Int, R]

object ShowShowInt2T
{ /** Factory apply method for creating quick ShowT instances for products of 2 [[Int]]s. */
  def apply[R <: ShowElemInt2](typeStrIn: String): ShowShowInt2T[R] = new ShowShowInt2T[R]()
  { val typeStr: String = typeStrIn
  }
}

/** A trait for making quick ShowT instances for products of 2 Ints persisted in Base32. */
trait Show2Base32sT[R <: Show2Base32s] extends ShowShow2T[Int, Int, R]

object Show2Base32sT
{ /** Factory apply method for creating quick ShowT instances for products of 2 [[Int]]s persisted in Base32. */
  def apply[R <: Show2Base32s](typeStrIn: String): Show2Base32sT[R] = new Show2Base32sT[R]()
  { val typeStr: String = typeStrIn
  }
}



/** UnShow type class trait for a 2 element Product. */
trait UnShow2T[A1, A2, R] extends UnShowProduct[R] with Persist2Base[A1, A2, R]
{ /** Derive the 1st parameter from an object of type R. */
  def fArg1: R => A1

  /** The UnShow type class instance for type A1. */
  def unShowA1: UnShow[A1]

  /** Derive the 2nd parameter from an object of type R. */
  def fArg2: R => A2

  /** The UnShow type class instance for type A2. */
  def unShowA2: UnShow[A2]
}

/** Persistence class for product 2 type class. It ShowTs and UnShows objects with 2 logical parameters. */
class Persist2[A1, A2, R](val typeStr: String, val name1: String, val fArg1: R => A1, val name2: String, val fArg2: R => A2, val newT: (A1, A2) => R,
  val opt2: Option[A2] = None, opt1In: Option[A1] = None)(implicit ev1In: Persist[A1], ev2In: Persist[A2]) extends Show2T[A1, A2, R] with
  PersistShowProductT[R]
{
  val opt1: Option[A1] = ife(opt2.nonEmpty, opt1In, None)
  override implicit def ev1: Persist[A1] = ev1In
  override implicit def ev2: Persist[A2] = ev2In

  override def fromExpr(expr: Expr): EMon[R] = expr match
  {
    case AlphaBracketExpr(IdentUpperToken(_, typeName), Arr1(ParenthBlock(Arr2(s1, s2), _, _))) if typeStr == typeName =>
      ev1.fromExpr(s1.expr).flatMap(a1 => ev2.fromExpr(s2.expr).map{a2 => newT(a1, a2)})

    case AlphaBracketExpr(IdentUpperToken(fp, typeName), _) => fp.bad(typeName -- "does not equal" -- typeStr)

    case ClausesExpr(clauses) if clauses.dataLength == 2 =>
      ev1.fromExpr(clauses(0).expr).flatMap(a1 => ev2.fromExpr(clauses(1).expr).map{a2 => newT(a1, a2)})

    case _ => expr.exprParseErr[R](this)
  }
}

/** Factory object for Persist product 2 type class */
object Persist2
{
  def apply[A1, A2, R](typeStr: String, name1: String, fArg1: R => A1, name2: String, fArg2: R => A2, newT: (A1, A2) => R,
    opt2: Option[A2] = None, opt1: Option[A1] = None)(implicit ev1: Persist[A1], ev2: Persist[A2], eq1: EqT[A1], eq2: EqT[A2]): Persist2[A1, A2, R] =
    new Persist2(typeStr, name1, fArg1, name2, fArg2, newT, opt2, opt1)(ev1, ev2)
}

/** Persist type class for types that extends [[Show2]]. */
class PersistShow2[A1, A2, R <: Show2[A1, A2]](typeStr: String, name1: String, name2: String, newT: (A1, A2) => R,
  opt2: Option[A2] = None, opt1: Option[A1] = None)(implicit ev1In: Persist[A1], ev2In: Persist[A2]) extends
  Persist2[A1, A2, R](typeStr, name1, _.show1, name2, _.show2, newT, opt2, opt1) with ShowShow2T[A1, A2, R]

object PersistShow2
{
  def apply[A1, A2, R <: Show2[A1, A2]](typeStr: String, name1: String, name2: String, newT: (A1, A2) => R,
    opt2: Option[A2] = None, opt1: Option[A1] = None)(implicit ev1In: Persist[A1], ev2In: Persist[A2]): PersistShow2[A1, A2, R] =
    new PersistShow2[A1, A2, R](typeStr, name1, name2, newT, opt2, opt1)
}

  /** Persistence type class for types that extend [[ShowElemInt2]]. */
class PersistShowInt2[R <: ShowElemInt2](typeStr: String, name1: String, name2: String, newT: (Int, Int) => R) extends
  PersistShow2[Int, Int, R](typeStr, name1, name2, newT)

  /** Persistence class for types that extends [[Show2Dl]]. */
class PersistShowDbl2[R <: ShowDbl2](typeStr: String, name1: String, name2: String, newT: (Double, Double) => R) extends
  PersistShow2[Double, Double, R](typeStr, name1, name2, newT)


/**  Class to persist [[ArrInt2s]] collection classes. */
abstract class PersistArrInt2s[A <: ElemInt2, M <: ArrInt2s[A]](val typeStr: String) extends DataIntNsPersist[A, M]
{
  override def appendtoBuffer(buf: ArrayBuffer[Int], value: A): Unit =
  { buf += value.int1
    buf += value.int2
  }

  override def syntaxDepthT(obj: M): Int = 3
}