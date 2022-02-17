/* Copyright 2018-22 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat
import pParse._, collection.mutable.ArrayBuffer

/** A base trait for [[Show2]] and [[UnShow2]] it declares the common properties of name1, name2, opt1 and opt2. It is not a base trait for
 *  [[Show2T]], as [[ShowShow2T]] classes do not need this data, as they can delgate to the [[Show2]] object to implement their interfaces. */
trait TypeStr2[A1, A2] extends Any with TypeStr
{ /** 1st parameter name. */
  def name1: String

  /** 2nd parameter name. */
  def name2: String

  /** The optional default value for parameter 1. */
  def opt1: Option[A1]

  /** The optional default value for parameter 2. */
  def opt2: Option[A2]
}

/** Trait for [[ShowDec]] for a product of 2 logical elements. This trait is implemented directly by the type in question, unlike the corresponding
 *  [[Show2T]] trait which externally acts on an object of the specified type to create its String representations. For your own types it is better to
 *  inherit from Show2 and then use [[ShowShow2T]] or [[Persist2ElemT]] to create the type class instance for ShowT. The [[ShowShow2T]] or
 *  [[PersistShow2]] class will delegate to Show2 for some of its methods. It is better to use Show2 to override toString method than delegating the
 *  toString override to a [[Show2T]] instance. */
trait Show2[A1, A2] extends Any with ShowProductDec with TypeStr2[A1, A2]
{
  /** The optional default value for parameter 1. */
  override def opt1: Option[A1] = None

  /** The optional default value for parameter 2. */
  override def opt2: Option[A2] = None

  /** Element 1 of this Show 2 element product. */
  def show1: A1

  /** Element 2 of this Show 2 element product. */
  def show2: A2

  /** The ShowT type class instance for the 1st element of this 2 element product. */
  implicit def showT1: ShowDecT[A1]

  /** The ShowT type class instance for the 2nd element of this 2 element product. */
  implicit def showT2: ShowDecT[A2]

  def elemNames: Strings = Strings(name1, name2)
  def elemTypeNames: Strings = Strings(showT1.typeStr, showT2.typeStr)
  def showElemStrDecs(way: ShowStyle, decimalPlaces: Int): Strings = Strings(showT1.showDecT(show1, way, decimalPlaces, 0), showT2.showDecT(show2, way, decimalPlaces, 0))

  def el1Show(style: ShowStyle = ShowStandard, maxPlaces: Int = -1): String = showT1.showDecT(show1, style, maxPlaces, maxPlaces): String
  def el2Show(style: ShowStyle = ShowStandard, maxPlaces: Int = -1): String = showT2.showDecT(show2, style, maxPlaces, maxPlaces): String
}

/** Trait for Show for product of 2 Ints that is also an ElemInt2. This trait is implemented directly by the type in question, unlike the
 *  corresponding [[ShowShowInt2T]] trait which externally acts on an object of the specified type to create its String representations. For your own
 *  types ShowProduct is preferred over [[Show2T]]. */
trait ShowElemInt2 extends Any with Show2[Int, Int] with ElemInt2
{ final override implicit def showT1: ShowDecT[Int] = ShowT.intPersistImplicit
  final override implicit def showT2: ShowDecT[Int] = ShowT.intPersistImplicit
  final override def syntaxDepth: Int = 2
  final override def int1: Int = show1
  final override def int2: Int = show2
}

/** Shows a class with 2 [[Double]] components. Note if the class also extends ElemDbl2, the dbl1 and dbl2 properties, may be different to the show1
 * and show2 properties, unless the class extends [[ShowElemDbl2]]. */
trait ShowDbl2 extends Any with Show2[Double, Double]
{ final override implicit def showT1: ShowDecT[Double] = ShowT.doublePersistEv
  final override implicit def showT2: ShowDecT[Double] = ShowT.doublePersistEv
  final override def syntaxDepth: Int = 2
}

/** Trait for Show for product of 2 Doubles that is also an [[ElemDbl2]]. This trait is implemented directly by the type in question, unlike the
 *  corresponding [[ShowShowDbl2T]] trait which externally acts on an object of the specified type to create its String representations. For your own
 *  types ShowProduct is preferred over [[Show2T]]. */
trait ShowElemDbl2 extends Any with ShowDbl2 with ElemDbl2
{ final override def dbl1: Double = show1
  final override def dbl2: Double = show2
}

/** Show type class for 2 parameter case classes. */
trait Show2T[A1, A2, R] extends ShowProductDecT[R]
{ def fArg1: R => A1
  def fArg2: R => A2
  implicit def ev1: ShowDecT[A1]
  implicit def ev2: ShowDecT[A2]
  override def syntaxDepthT(obj: R): Int = ev1.syntaxDepthT(fArg1(obj)).max(ev2.syntaxDepthT(fArg2(obj))) + 1

  override def strDecs(obj: R, way: ShowStyle, decimalPlaces: Int): Strings =
    Strings(ev1.showDecT(fArg1(obj), way, decimalPlaces, 0), ev2.showDecT(fArg2(obj), way, decimalPlaces, 0))
}

/** Companion object for the [[Show2T]] type class trait that shows object with 2 logical fields. */
object Show2T
{
  def apply[A1, A2, R](typeStr: String, name1: String, fArg1: R => A1, name2: String, fArg2: R => A2, opt2: Option[A2] = None,
    opt1In: Option[A1] = None)(implicit ev1: ShowDecT[A1], ev2: ShowDecT[A2]): Show2T[A1, A2, R] =
    new Show2TImp[A1, A2, R](typeStr, name1, fArg1, name2, fArg2, opt2, opt1In)

  class Show2TImp[A1, A2, R](val typeStr: String, val name1: String, val fArg1: R => A1, val name2: String, val fArg2: R => A2, val opt2: Option[A2] = None,
    opt1In: Option[A1] = None)(implicit val ev1: ShowDecT[A1], val ev2: ShowDecT[A2]) extends Show2T[A1, A2, R] with TypeStr2[A1,A2]
  { val opt1: Option[A1] = ife(opt2.nonEmpty, opt1In, None)
  }
}

class Show2TExtensions[A1, A2, -T](ev: Show2T[A1, A2, T], thisVal: T)
{
  /** Intended to be a multiple parameter comprehensive Show method. Intended to be paralleled by showT method on [[ShowDecT]] type class instances. */
  def show2(way: ShowStyle = ShowStandard, way1: ShowStyle = ShowStandard, places1: Int = -1, way2: ShowStyle = ShowStandard, places2: Int = -1):
    String = ???
}

/** Type class trait for Showing [[Show2]] objects. */
trait ShowShow2T[A1, A2, R <: Show2[A1, A2]] extends Show2T[A1, A2, R] with ShowShowDecT[R]
{ override def fArg1: R => A1 = _.show1
  override def fArg2: R => A2 = _.show2
}

object ShowShow2T
{
  def apply[A1, A2, R<: Show2[A1, A2]](typeStr: String, name1: String, name2: String, opt2: Option[A2] = None, opt1In: Option[A1] = None)(
    implicit ev1: ShowDecT[A1], ev2: ShowDecT[A2]): ShowShow2T[A1, A2, R] =
    new ShowShow2TImp[A1, A2, R](typeStr)

  class ShowShow2TImp[A1, A2, R<: Show2[A1, A2]](val typeStr: String)(implicit val ev1: ShowDecT[A1], val ev2: ShowDecT[A2]) extends
    ShowShow2T[A1, A2, R]
}

/** A trait for making quick ShowT instances for [[ShowDbl2]] types. It uses the functionality of the [[ShowDbl2]]. */
trait ShowShowDbl2T[R <: ShowDbl2] extends ShowShow2T[Double, Double, R]
{ override implicit def ev1: PersistDec[Double] = ShowT.doublePersistEv
  override implicit def ev2: PersistDec[Double] = ShowT.doublePersistEv
}

object ShowShowDbl2T
{ /** Factory apply method for creating quick ShowT instances for products of 2 Doubles. */
  def apply[R <: ShowElemDbl2](typeStr: String): ShowShowDbl2TImp[R] = new ShowShowDbl2TImp[R](typeStr)

  class ShowShowDbl2TImp[R <: ShowDbl2](val typeStr: String) extends ShowShowDbl2T[R]
}

/** A trait for making quick ShowT instances for [[ShowElemInt2]] classes. It uses the functionality of the [[ShowelemInt2]]. */
trait ShowShowInt2T[R <: ShowElemInt2] extends ShowShow2T[Int, Int, R]
{ override implicit def ev1: PersistDec[Int] = ShowT.intPersistImplicit
  override implicit def ev2: PersistDec[Int] = ShowT.intPersistImplicit
}

object ShowShowInt2T
{ /** Factory apply method for creating quick ShowT instances for products of 2 [[Int]]s. */
  def apply[R <: ShowElemInt2](typeStr: String, name1: String, name2: String, opt2: Option[Int] = None, opt1In: Option[Int] = None):
    ShowShowInt2T[R] = new ShowShowInt2TImp[R](typeStr, name1, name2, opt2,opt1In)

  class ShowShowInt2TImp[R <: ShowElemInt2](val typeStr: String, val name1: String, val name2: String, val opt2: Option[Int] = None,
    opt1In: Option[Int] = None) extends ShowShowInt2T[R]
  { val opt1: Option[Int] = ife(opt2.nonEmpty, opt1In, None)
  }
}

/** UnShow type class trait for a 2 element Product. */
trait Unshow2[A1, A2, R] extends Unshow[R] with TypeStr2[A1, A2]
{ /** The UnShow type class instance for type A1. */
  def ev1: Unshow[A1]

  /** The UnShow type class instance for type A2. */
  def ev2: Unshow[A2]

  def newT: (A1, A2) => R

  override def fromExpr(expr: Expr): EMon[R] = expr match
  {
    case AlphaBracketExpr(IdentUpperToken(_, typeName), Arr1(ParenthBlock(Arr2(s1, s2), _, _))) if typeStr == typeName =>
      ev1.fromExpr(s1.expr).map2(ev2.fromExpr(s2.expr)){ (a1, a2) => newT(a1, a2) }

    case AlphaBracketExpr(IdentUpperToken(fp, typeName), _) => fp.bad(typeName -- "does not equal" -- typeStr)

    case ClausesExpr(clauses) if clauses.dataLength == 2 =>
      ev1.fromExpr(clauses(0).expr).map2(ev2.fromExpr(clauses(1).expr)){ (a1, a2) => newT(a1, a2) }

    case _ => expr.exprParseErr[R](this)
  }
}

object Unshow2{
  def apply[A1, A2, R](typeStr: String, name1: String, name2: String, newT: (A1, A2) => R, opt2: Option[A2] = None, opt1: Option[A1] = None)(implicit
    ev1: Unshow[A1], ev2: Unshow[A2]): Unshow2[A1, A2, R] = new Unshow2Imp[A1, A2, R](typeStr, name1, name2, newT, opt2, opt1)

  case class Unshow2Imp[A1, A2, R](typeStr: String, name1: String, name2: String, newT: (A1, A2) => R, val opt2: Option[A2], opt1In: Option[A1])(implicit
    val ev1: Unshow[A1], val ev2: Unshow[A2]) extends Unshow2[A1, A2, R]
  { val opt1: Option[A1] = ife(opt2.nonEmpty, opt1In, None)
  }
}

/** Persistence class for product 2 type class. It ShowTs and UnShows objects with 2 logical parameters. */
trait Persist2[A1, A2, R] extends Show2T[A1, A2, R] with Unshow2[A1, A2, R] with PersistProductDec[R]
{ override def ev1: PersistDec[A1]
  override def ev2: PersistDec[A2]
}

/** Factory object for Persist product 2 type class that persists objects with 2 parameters. */
object Persist2
{
  def apply[A1, A2, R](typeStr: String, name1: String, fArg1: R => A1, name2: String, fArg2: R => A2, newT: (A1, A2) => R,
    opt2: Option[A2] = None, opt1: Option[A1] = None)(implicit ev1: PersistDec[A1], ev2: PersistDec[A2], eq1: EqT[A1], eq2: EqT[A2]): Persist2[A1, A2, R] =
    new Persist2Imp(typeStr, name1, fArg1, name2, fArg2, newT, opt2, opt1)(ev1, ev2)

  class Persist2Imp[A1, A2, R](val typeStr: String, val name1: String, val fArg1: R => A1, val name2: String, val fArg2: R => A2, val newT: (A1, A2) => R,
    val opt2: Option[A2] = None, opt1In: Option[A1] = None)(implicit val ev1: PersistDec[A1], val ev2: PersistDec[A2]) extends Persist2[A1, A2, R]
  { val opt1: Option[A1] = ife(opt2.nonEmpty, opt1In, None)
  }
}

/** Persist type class for types that extends [[Show2]]. */
trait PersistShow2[A1, A2, R <: Show2[A1, A2]] extends Persist2[A1, A2, R] with ShowShow2T[A1, A2, R]

/** Companion object for the [[PersistShow2]] class the persists object that extend [[Show2]]. Contains an apply factory method. */
object PersistShow2
{ /** Factory apply method for [[PersistShow2]], that Persists [[Show2]] objects. */
  def apply[A1, A2, R <: Show2[A1, A2]](typeStr: String, name1: String, name2: String, newT: (A1, A2) => R,
    opt2: Option[A2] = None, opt1: Option[A1] = None)(implicit ev1In: PersistDec[A1], ev2In: PersistDec[A2]): PersistShow2[A1, A2, R] =
    new PersistShow2Imp[A1, A2, R](typeStr, name1, name2, newT, opt2, opt1)

  class PersistShow2Imp[A1, A2, R <: Show2[A1, A2]](val typeStr: String, val name1: String, val name2: String, val newT: (A1, A2) => R,
    val opt2: Option[A2] = None, opt1In: Option[A1] = None)(implicit val ev1: PersistDec[A1], val ev2: PersistDec[A2]) extends PersistShow2[A1, A2, R]
  { val opt1: Option[A1] = ife(opt2.nonEmpty, opt1In, None)
  }
}

/** Persistence type class for types that extend [[ShowElemInt2]]. */
class PersistShowInt2[R <: ShowElemInt2](val typeStr: String, val name1: String, val name2: String, val newT: (Int, Int) => R,
  val opt2: Option[Int] = None, opt1In: Option[Int] = None) extends PersistShow2[Int, Int, R] with ShowShowInt2T[R]
{ val opt1: Option[Int] = ife(opt2.nonEmpty, opt1In, None)
}

object PersistShowInt2
{ /** Factory apply method for [[PersistShowInt2]] that persists objects of type [[ShowElemInt2]]. */
  def apply[R <: ShowElemInt2](typeStr: String, name1: String, name2: String, newT: (Int, Int) => R): PersistShowInt2[R] =
    new PersistShowInt2[R](typeStr, name1, name2, newT)
}

/** Persistence class for types that extends [[ShowDbl2]]. */
class PersistShowDbl2[R <: ShowDbl2](val typeStr: String, val name1: String, val name2: String, val newT: (Double, Double) => R,
  val opt2: Option[Double] = None, opt1In: Option[Double] = None) extends PersistShow2[Double, Double, R] with ShowShowDbl2T[R]
{ val opt1: Option[Double] = ife(opt2.nonEmpty, opt1In, None)
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