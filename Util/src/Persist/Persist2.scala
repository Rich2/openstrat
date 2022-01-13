/* Copyright 2018-22 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat
import pParse._, collection.mutable.ArrayBuffer

trait Show2[A1, A2] extends Any with ShowProduct
{
  /** the name of the 1st element of this 2 element product. */
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

/** Trait for [[ShowPrec]] for a product of 2 logical elements. This trait is implemented directly by the type in question, unlike the corresponding
 *  [[ShowPrec2T]] trait which externally acts on an object of the specified type to create its String representations. For your own types it is better to
 *  inherit from Show2 and then use [[ShowShowPrec2T]] or [[Persist2ElemT]] to create the type class instance for ShowT. The [[ShowShowPrec2T]] or
 *  [[PersistShowPrec2]] class will delegate to Show2 for some of its methods. It is better to use Show2 to override toString method than delegating the
 *  toString override to a [[ShowPrec2T]] instance. */
trait ShowPrec2[A1, A2] extends Any with ShowProductPrec with Show2[A1, A2]
{
  /** The ShowT type class instance for the 1st element of this 2 element product. */
  implicit override def showT1: ShowPrecisionT[A1]

  /** The ShowT type class instance for the 2nd element of this 2 element product. */
  implicit override def showT2: ShowPrecisionT[A2]

}

/** Trait for Show for product of 2 Ints that is also an ElemInt2. This trait is implemented directly by the type in question, unlike the
 *  corresponding [[ShowShowInt2T]] trait which externally acts on an object of the specified type to create its String representations. For your own
 *  types ShowProduct is preferred over [[ShowPrec2T]]. */
trait ShowElemInt2 extends Any with ShowPrec2[Int, Int] with ElemInt2
{ final override implicit def showT1: ShowPrecisionT[Int] = ShowT.intPersistImplicit
  final override implicit def showT2: ShowPrecisionT[Int] = ShowT.intPersistImplicit
  final override def syntaxDepth: Int = 2
  final override def int1: Int = show1
  final override def int2: Int = show2
}

/** Shows a class with 2 [[Double]] components. Note if the class also extends ElemDbl2, the dbl1 and dbl2 properties, may be different to the show1
 * and show2 properties, unless the class extends [[ShowElemDbl2]]. */
trait ShowDbl2 extends Any with ShowPrec2[Double, Double]
{ final override implicit def showT1: ShowPrecisionT[Double] = ShowT.doublePersistImplicit
  final override implicit def showT2: ShowPrecisionT[Double] = ShowT.doublePersistImplicit
  final override def syntaxDepth: Int = 2
}

/** Trait for Show for product of 2 Doubles that is also an [[ElemDbl2]]. This trait is implemented directly by the type in question, unlike the
 *  corresponding [[ShowShowDbl2T]] trait which externally acts on an object of the specified type to create its String representations. For your own
 *  types ShowProduct is preferred over [[ShowPrec2T]]. */
trait ShowElemDbl2 extends Any with ShowDbl2 with ElemDbl2
{ final override def dbl1: Double = show1
  final override def dbl2: Double = show2
}



/** A base trait for [[ShowPrec2T]] and [[UnShow2]], declares the common properties of name1, name2, opt1 and opt2. */
trait TypeStred2[A1, A2, R] extends TypeStred
{ /** 1st parameter name. */
  def name1: String

  /** 2nd parameter name. */
  def name2: String

  /** The optional default value for parameter 1. */
  def opt1: Option[A1]

  /** The optional default value for parameter 2. */
  def opt2: Option[A2]
}

trait Show2T[A1, A2, R] extends ShowProductT[R] with TypeStred2[A1, A2, R]
{
  def fArg1: R => A1
  def fArg2: R => A2
  implicit def ev1: ShowT[A1]
  implicit def ev2: ShowT[A2]
  override def syntaxDepthT(obj: R): Int = ev1.syntaxDepthT(fArg1(obj)).max(ev2.syntaxDepthT(fArg2(obj))) + 1
  override def names: Strings = Strings(name1, name2)
  override def strs(obj: R, style: ShowStyle, decimalPlaces: Int): Strings =
    Strings(ev1.showT(fArg1(obj), style, decimalPlaces, 0), ev2.showT(fArg2(obj), style, decimalPlaces, 0))
}

/** Companion object for the [[Show2T]] type class trait that shows object with 2 logical fields. */
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

/** Show type class for 2 parameter case classes. */
trait ShowPrec2T[A1, A2, R] extends ShowProductPrecT[R] with Show2T[A1, A2, R]
{
  implicit override def ev1: ShowPrecisionT[A1]
  implicit override def ev2: ShowPrecisionT[A2]
}

/** Companion object for the [[ShowPrec2T]] type class trait that shows object with 2 logical fields. */
object ShowPrec2T
{
  def apply [A1, A2, R](typeStrIn: String, name1In: String, fArg1In: R => A1, name2In: String, fArg2In: R => A2, opt2In: Option[A2] = None,
    opt1In: Option[A1] = None)(implicit ev1In: ShowPrecisionT[A1], ev2In: ShowPrecisionT[A2]): ShowPrec2T[A1, A2, R] = new ShowPrec2T[A1, A2, R]
  {
    override def typeStr: String = typeStrIn
    override def name1: String = name1In
    override def fArg1: R => A1 = fArg1In
    override def name2: String = name2In
    override def fArg2: R => A2 = fArg2In
    override implicit def ev1: ShowPrecisionT[A1] = ev1In
    override implicit def ev2: ShowPrecisionT[A2] = ev2In
    val opt2: Option[A2] = opt2In
    val opt1: Option[A1] = ife(opt2.nonEmpty, opt1In, None)
  }
}

/** Type class trait for Showing [[Show2]] objects. */
trait ShowShow2T[A1, A2, R <: Show2[A1, A2]] extends ShowShowT[R] with Show2T[A1, A2, R]
{ final override def fArg1: R => A1 = _.show1
  final override def fArg2: R => A2 = _.show2
}

/** Type class trait for Showing [[ShowPrec2]] objects. */
trait ShowShowPrec2T[A1, A2, R <: ShowPrec2[A1, A2]] extends ShowPrecisionShowT[R] with ShowShow2T[A1, A2, R]

object ShowShowPrec2T
{
  /*def apply[A1, A2, R<: ShowPrec2[A1, A2]](typeStrIn: String): ShowShowPrec2T[A1, A2, R] = new ShowShowPrec2T[A1, A2, R]
  { override def typeStr: String = typeStrIn
    override def showT(obj: R, way: ShowStyle, maxPlaces: Int, minPlaces: Int): String = obj.show(way, maxPlaces, 0)
  }*/
}

class Show2TExtensions[A1, A2, -T](ev: ShowPrec2T[A1, A2, T], thisVal: T)
{
  /** Intended to be a multiple parameter comprehensive Show method. Intended to be paralleled by showT method on [[ShowPrecisionT]] type class instances. */
  def show2(way: ShowStyle = ShowStandard, way1: ShowStyle = ShowStandard, places1: Int = -1, way2: ShowStyle = ShowStandard, places2: Int = -1):
    String = ???
}

/** A trait for making quick ShowT instances for [[ShowDbl2]] types. It uses the functionality of the [[ShowDbl2]]. */
trait ShowShowDbl2T[R <: ShowDbl2] extends ShowShowPrec2T[Double, Double, R]

object ShowShowDbl2T
{ /** Factory apply method for creating quick ShowT instances for products of 2 Doubles. */
  /*def apply[R <: ShowElemDbl2](typeStrIn: String): ShowShowDbl2T[R] = new ShowShowDbl2T[R]()
  { val typeStr: String = typeStrIn
  }*/
}

/** A trait for making quick ShowT instances for [[ShowElemInt2]] classes. It uses the functionality of the [[ShowelemInt2]]. */
trait ShowShowInt2T[R <: ShowElemInt2] extends ShowShow2T[Int, Int, R]

object ShowShowInt2T
{ /** Factory apply method for creating quick ShowT instances for products of 2 [[Int]]s. */
  /*def apply[R <: ShowElemInt2](typeStrIn: String): ShowShowInt2T[R] = new ShowShowInt2T[R]()
  { val typeStr: String = typeStrIn
  }*/
}

/** UnShow type class trait for a 2 element Product. */
trait UnShow2T[A1, A2, R] extends UnShowProduct[R] with TypeStred2[A1, A2, R]
{ /** Derive the 1st parameter from an object of type R. */
  def fArg1: R => A1

  /** The UnShow type class instance for type A1. */
  def unShowA1: UnShow[A1]

  /** Derive the 2nd parameter from an object of type R. */
  def fArg2: R => A2

  /** The UnShow type class instance for type A2. */
  def unShowA2: UnShow[A2]
}

trait Persist2[A1, A2, R] extends Show2T[A1, A2, R] with PersistShowProductT[R]
{
  def newT: (A1, A2) => R
  override implicit def ev1: Persist[A1]
  override implicit def ev2: Persist[A2]

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

/** Persistence class for product 2 type class. It ShowTs and UnShows objects with 2 logical parameters. */
trait PersistPrec2[A1, A2, R] extends ShowPrec2T[A1, A2, R] with
  PersistShowProductPrecT[R] with Persist2[A1, A2, R]
{
  def ev1: PersistPrec[A1]
  def ev2: PersistPrec[A2]
}

object PersistPrec2
{
  def apply[A1, A2, R](typeStrIn: String, name1In: String, fArg1In: R => A1, name2In: String, fArg2In: R => A2, newTIn: (A1, A2) => R,
    opt2In: Option[A2] = None, opt1In: Option[A1] = None)(implicit ev1In: PersistPrec[A1], ev2In: PersistPrec[A2]): PersistPrec2[A1, A2, R] =
    new PersistPrec2[A1, A2, R]
  {
    override val typeStr: String = typeStrIn
    override val name1: String = name1In
    override val fArg1: R => A1 = fArg1In
    override val name2: String = name2In
    override val fArg2: R => A2 = fArg2In
    override val newT: (A1, A2) => R = newTIn
    override val opt2: Option[A2] = opt2In
    override val opt1: Option[A1] = ife(opt2.nonEmpty, opt1In, None)
    override implicit val ev1: PersistPrec[A1] = ev1In
    override implicit val ev2: PersistPrec[A2] = ev2In
  }
}

/** Factory object for Persist product 2 type class that persists objects with 2 parameters. */
/*object PersistPrec2
{
  def apply[A1, A2, R](typeStr: String, name1: String, fArg1: R => A1, name2: String, fArg2: R => A2, newT: (A1, A2) => R,
    opt2: Option[A2] = None, opt1: Option[A1] = None)(implicit ev1: PersistPrecision[A1], ev2: PersistPrecision[A2], eq1: EqT[A1], eq2: EqT[A2]): PersistPrec2[A1, A2, R] =
    new PersistPrec2(typeStr, name1, fArg1, name2, fArg2, newT, opt2, opt1)(ev1, ev2)
}*/

/** Persist type class for persisting types that extends [[Show2]]. */
trait PersistShow2[A1, A2, R <: Show2[A1, A2]] extends Persist2[A1, A2, R] with ShowShow2T[A1, A2, R]


/** Companion object for the PersistShow2 trait that persists types that extend the [[Show2]][A1, A2] trait. Contains factory apply method. */
object PersistShow2
{ /** Factory apply method for [[PersistShow2]]. */
  def apply[A1, A2, R <: Show2[A1, A2]](typeStr: String, name1: String, name2: String, newT: (A1, A2) => R,
    opt2: Option[A2] = None, opt1: Option[A1] = None)(implicit ev1In: Persist[A1], ev2In: Persist[A2]): PersistShow2[A1, A2,R] =
    new PersistShow2Imp[A1, A2, R] (typeStr, name1, name2, newT: (A1, A2) => R,opt2, opt1)

  class PersistShow2Imp[A1, A2, R <: Show2[A1, A2]](val typeStr: String, val name1: String, val name2: String, val newT: (A1, A2) => R,
    opt2In: Option[A2] = None, opt1In: Option[A1] = None)(implicit val ev1: Persist[A1], val ev2: Persist[A2]) extends PersistShow2[A1, A2, R]
  {
    override def opt2: Option[A2] = opt2In
    override def opt1: Option[A1] = ife(opt2.nonEmpty, opt1In, None)
  }
}

/** Persist type class for types that extends [[ShowPrec2]]. */
trait PersistShowPrec2[A1, A2, R <: ShowPrec2[A1, A2]] extends PersistPrec2[A1, A2, R] with PersistShow2[A1, A2, R]
{ override def ev1: PersistPrec[A1]
  override def ev2: PersistPrec[A2]
}

/** Companion object for the [[PersistShowPrec2]] class the persists object that extend [[ShowPrec2]]. Contains an apply factory method. */
object PersistShowPrec2
{
  def apply[A1, A2, R <: ShowPrec2[A1, A2]](typeStr: String, name1: String, name2: String, newT: (A1, A2) => R,
    opt2: Option[A2] = None, opt1: Option[A1] = None)(implicit ev1In: PersistPrec[A1], ev2In: PersistPrec[A2]): PersistShowPrec2[A1, A2, R] =
    new PersistShowPrec2Imp[A1, A2, R](typeStr, name1, name2,  newT, opt2, opt1)

  class PersistShowPrec2Imp[A1, A2, R <: ShowPrec2[A1, A2]](val typeStr: String, val name1: String, val name2: String, val newT: (A1, A2) => R,
    val opt2: Option[A2] = None, val opt1: Option[A1] = None)(implicit val ev1: PersistPrec[A1], val ev2: PersistPrec[A2]) extends
    PersistShowPrec2[A1, A2, R]
}

  /** Persistence type class for types that extend [[ShowElemInt2]]. */
class PersistShowInt2[R <: ShowElemInt2](val typeStr: String, val name1: String, val name2: String, val newT: (Int, Int) => R,
    opt2In: Option[Int] = None, opt1In: Option[Int] = None) extends PersistShow2[Int, Int, R] with ShowShowInt2T[R] {
    override def opt2: Option[Int] = opt2In
    override def opt1: Option[Int] = ife(opt2.nonEmpty, opt1In, None)
    override implicit def ev1: Persist[Int] = ShowT.intPersistImplicit
    override implicit def ev2: Persist[Int] = ShowT.intPersistImplicit
  }

  /** Persistence class for types that extends [[Show2Dl]]. */
class PersistShowDbl2[R <: ShowDbl2](val typeStr: String, val name1: String, val name2: String, val newT: (Double, Double) => R) extends
  PersistShowPrec2[Double, Double, R] with ShowShowDbl2T[R]//(typeStr, name1, name2, newT)
{
  override def ev1: PersistPrec[Double] = ShowT.doublePersistImplicit
  override def ev2: PersistPrec[Double] = ShowT.doublePersistImplicit

  /** The optional default value for parameter 1. */
  override def opt1: Option[Double] = ???

  /** The optional default value for parameter 2. */
  override def opt2: Option[Double] = ???
}


/**  Class to persist [[ArrInt2s]] collection classes. */
abstract class PersistArrInt2s[A <: ElemInt2, M <: ArrInt2s[A]](val typeStr: String) extends DataIntNsPersist[A, M]
{
  override def appendtoBuffer(buf: ArrayBuffer[Int], value: A): Unit =
  { buf += value.int1
    buf += value.int2
  }

  override def syntaxDepthT(obj: M): Int = 3
}