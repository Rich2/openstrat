/* Copyright 2018-22 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat
import pParse._

/** A base trait for [[Show3T]] and [[Unshow3]], declares the common properties of name1 - 3 and opt1 - 3. */
trait TypeStr3[A1, A2, A3] extends Any with TypeStr2[A1, A2]
{ /** 3rd parameter name. */
  def name3: String

  /** The optional default value for parameter 3. */
  def opt3: Option[A3]
}

/** Trait for [[Show]] for a product of 3 logical elements. This trait is implemented directly by the type in question, unlike the corresponding
 *  [[ShowEq3T]] trait which externally acts on an object of the specified type to create its String representations. For your own types it is better to
 *  inherit from Show3 and then use [[Show3ElemT]] or [[Persist3ElemT]] to create the type class instance for ShowT. The [[Show3ElemT]] or
 *  [[Persist3Elem]] class will delegate to Show3 for some of its methods. It is better to use Show3 to override toString method than delegating the
 *  toString override to a [[ShowEq3T]] instance. */
trait Show3[A1, A2, A3] extends Any with ShowN with TypeStr3[A1, A2, A3]
{
  override def opt1: Option[A1] = None
  override def opt2: Option[A2] = None
  override def opt3: Option[A3] = None

  /** Element 1 of this 3 element Show product. */
  def show1: A1

  /** Element 2 of this 3 element Show product. */
  def show2: A2

  /** Element 3 of this 3 element Show product. */
  def show3: A3

  /** The [[ShowT]] type class instance for the 1st element of this 3 element Show product. */
  def showT1: ShowT[A1]

  /** The [[ShowT]] type class instance for the 2nd element of this 3 element Show product. */
  def showT2: ShowT[A2]

  /** The [[ShowT]] type class instance for the 3rd element of this 3 element Show product. */
  def showT3: ShowT[A3]

  override def elemNames: Strings = Strings(name1, name2, name3)
  override def elemTypeNames: Strings = Strings(showT1.typeStr, showT2.typeStr, showT3.typeStr)

  override def showElemStrs(way: ShowStyle): Strings = Strings(showT1.showT(show1, way), showT2.showT(show2, way), showT3.showT(show3, way))
}

/** Trait for [[ShowDec]] for a product of 3 logical elements. This trait is implemented directly by the type in question, unlike the corresponding
 *  [[ShowEq3T]] trait which externally acts on an object of the specified type to create its String representations. For your own types it is better to
 *  inherit from Show3 and then use [[Show3ElemT]] or [[Persist3ElemT]] to create the type class instance for ShowT. The [[Show3ElemT]] or
 *  [[Persist3Elem]] class will delegate to Show3 for some of its methods. It is better to use Show3 to override toString method than delegating the
 *  toString override to a [[ShowEq3T]] instance. */
trait ShowDec3[A1, A2, A3] extends Any with ShowDecN with Show3[A1, A2, A3]
{ /** The [[ShowDecT]] type class instance for the 1st element of this 3 element [[ShowDec]] product. */
  override def showT1: ShowDecT[A1]

  /** The [[ShowDecT]] type class instance for the 2nd element of this 3 element [[ShowDec]] product. */
  override def showT2: ShowDecT[A2]

  /** The [[ShowDecT]] type class instance for the 3rd element of this 3 element [[ShowDec]] product. */
  override def showT3: ShowDecT[A3]

  override def showElemStrDecs(way: ShowStyle, decimalPlaces: Int): Strings = Strings(showT1.showDecT(show1, way, decimalPlaces, 0), showT2.showDecT(show2, way, decimalPlaces, 0),
    showT3.showDecT(show3, way, decimalPlaces, 0))
}

trait ShowDbl3 extends Any with ShowDec3[Double, Double, Double]
{ final override def syntaxDepth: Int = 2
  final override implicit def showT1: PersistDec[Double] = ShowT.doublePersistEv
  final override implicit def showT2: PersistDec[Double] = ShowT.doublePersistEv
  final override implicit def showT3: PersistDec[Double] = ShowT.doublePersistEv
}

/** Trait for Show for product of 2 Doubles. This trait is implemented directly by the type in question, unlike the corresponding [[ShowShowDbl2T]]
 *  trait which externally acts on an object of the specified type to create its String representations. For your own types ShowProduct is preferred
 *  over [[ShowDec2T]]. */
trait ShowElemDbl3 extends Any with ShowDbl3 with ElemDbl3
{ final override def dbl1: Double = show1
  final override def dbl2: Double = show2
  final override def dbl3: Double = show2
}

/** Show type class for 3 parameter case classes. */
trait Show3T[A1, A2, A3, R] extends ShowNT[R]

object Show3T
{
  def apply[A1, A2, A3, R](typeStr: String, name1: String, fArg1: R => A1, name2: String, fArg2: R => A2, name3: String, fArg3: R => A3,
    opt3: Option[A3] = None, opt2In: Option[A2] = None, opt1In: Option[A1] = None)(implicit ev1: ShowT[A1], ev2: ShowT[A2], ev3: ShowT[A3]):
  Show3T[A1, A2, A3, R] = new Show3TImp[A1, A2, A3, R](typeStr, name1, fArg1, name2, fArg2, name3, fArg3,opt3, opt2In, opt1In)

  class Show3TImp[A1, A2, A3, R](val typeStr: String, val name1: String, val fArg1: R => A1, val name2: String, val fArg2: R => A2, val name3: String,
    val fArg3: R => A3, val opt3: Option[A3] = None, opt2In: Option[A2] = None, opt1In: Option[A1] = None)(
    implicit val ev1: ShowT[A1], val ev2: ShowT[A2], val ev3: ShowT[A3]) extends Show3T[A1, A2, A3, R] with TypeStr3[A1, A2, A3]
  {
    val opt2: Option[A2] = ife(opt3.nonEmpty, opt2In, None)
    val opt1: Option[A1] = ife(opt2.nonEmpty, opt1In, None)
    val defaultNum = ife3(opt3.isEmpty, 0, opt2.isEmpty, 1, opt1.isEmpty, 2, 3)

    override def syntaxDepthT(obj: R): Int = ev1.syntaxDepthT(fArg1(obj)).max(ev2.syntaxDepthT(fArg2(obj))).max(ev3.syntaxDepthT(fArg3(obj))) + 1

    override def strs(obj: R, way: ShowStyle): Strings = Strings(ev1.showT(fArg1(obj), way), ev2.showT(fArg2(obj), way), ev3.showT(fArg3(obj), way))

  }
}

/** Show type class for 3 parameter case classes. */
trait ShowDec3T[A1, A2, A3, R] extends Show3T[A1, A2, A3, R] with ShowDecNT[R]

trait ShowShow3T[A1, A2, A3, R <: Show3[A1, A2, A3]] extends Show3T[A1, A2, A3, R] with ShowShowNT[R]

trait ShowShowDec3T[A1, A2, A3, R <: ShowDec3[A1, A2, A3]] extends ShowShow3T[A1, A2, A3, R] with ShowDec3T[A1, A2, A3, R] with ShowShowDecNT[R]

trait ShowShowDbl3T[R <: ShowDbl3] extends ShowShowDec3T[Double, Double, Double, R] with ShowDecNT[R]

object ShowShowDbl3T
{ /** Factory apply method for creating quick ShowT instances for products of 3 Doubles. */
  def apply[R <: ShowElemDbl3](typeStr: String, name1: String, name2: String, name3: String, opt2: Option[Double] = None, opt1In: Option[Double] = None):
  ShowShowDbl3TImp[R] = new ShowShowDbl3TImp[R](typeStr, name1, name2, name3, opt2, opt1In)

  class ShowShowDbl3TImp[R <: ShowDbl3](val typeStr: String, val name1: String, val name2: String, val name3: String, val opt3: Option[Double] = None,
    opt2In: Option[Double] = None, opt1In: Option[Double] = None) extends ShowShowDbl3T[R]
  { val opt2: Option[Double] = ife(opt3.nonEmpty, opt2In, None)
    val opt1: Option[Double] = ife(opt2.nonEmpty, opt1In, None)
  }
}

/** UnShow class for 3 logical parameter product types. */
trait Unshow3[A1, A2, A3, R] extends Unshow[R] with TypeStr3[A1, A2, A3]
{
  /** The UnShow type class instance for type A1. */
  def ev1: Unshow[A1]

  /** The UnShow type class instance for type A2. */
  def ev2: Unshow[A2]

  /** The UnShow type class instance for type A3. */
  def ev3: Unshow[A3]

  def newT: (A1, A2, A3) => R

  override def fromExpr(expr: Expr): EMon[R] = expr match
  {
    case AlphaBracketExpr(IdentUpperToken(_, typeName), Arr1(ParenthBlock(Arr3(s1, s2, s3), _, _))) if typeStr == typeName =>
      ev1.fromExpr(s1.expr).map3(ev2.fromExpr(s2.expr), ev3.fromExpr(s3.expr)){ (a1, a2, a3) => newT(a1, a2, a3) }

    case AlphaBracketExpr(IdentUpperToken(fp, typeName), _) => fp.bad(typeName -- "does not equal" -- typeStr)

    case ClausesExpr(clauses) if clauses.dataLength == 3 => ev1.fromExpr(clauses(0).expr).map3(
      ev2.fromExpr(clauses(1).expr), ev3.fromExpr(clauses(2).expr)){ (a1, a2, a3) => newT(a1, a2, a3) }

    case _ => expr.exprParseErr[R](this)
  }
}

object Unshow3
{
  /*(val typeStr: String, name1: String, fArg1: R => A1, name2: String, fArg2: R => A2, name3: String, fArg3: R => A3,
  val newT: (A1, A2, A3) => R, opt3: Option[A3] = None, opt2: Option[A2] = None, opt1: Option[A1] = None)(implicit ev1: UnShow[A1], ev2: UnShow[A2],
  ev3: UnShow[A3], eq1: EqT[A1], eq2: EqT[A2], eq3: EqT[A3])*/
}

/** Persistence class for 3 logical parameter product types. */
trait Persist3[A1, A2, A3, R] extends ShowDec3T[A1, A2, A3, R] with Unshow3[A1, A2, A3, R] with PersistDecN[R]
{ override def ev1: PersistDec[A1]
  override def ev2: PersistDec[A2]
  override def ev3: PersistDec[A3]
}

/** Companion object for [[Persist3]] trait contains implementation class and factory apply method. */
object Persist3
{
  def apply[A1, A2, A3, R](typeStr: String, name1: String, fArg1: R => A1, name2: String, fArg2: R => A2, name3: String, fArg3: R => A3,
    newT: (A1, A2, A3) => R, opt3: Option[A3] = None, opt2: Option[A2] = None, opt1: Option[A1] = None)(
    implicit ev1: PersistDec[A1], ev2: PersistDec[A2], ev3: PersistDec[A3]): Persist3[A1, A2, A3, R] =
    new Persist3Imp(typeStr, name1, fArg1, name2, fArg2, name3, fArg3, newT, opt3, opt2, opt1)(ev1, ev2, ev3)

  class Persist3Imp[A1, A2, A3, R](val typeStr: String, val name1: String, val fArg1: R => A1, val name2: String, val fArg2: R => A2,
    val name3: String, val fArg3: R => A3, val newT: (A1, A2, A3) => R, val opt3: Option[A3] = None, opt2In: Option[A2] = None,
    opt1In: Option[A1] = None)(implicit val ev1: PersistDec[A1], val ev2: PersistDec[A2], val ev3: PersistDec[A3]) extends Persist3[A1, A2, A3, R]
  { val opt2: Option[A2] = ife(opt3.nonEmpty, opt2In, None)
    val opt1: Option[A1] = ife(opt2.nonEmpty, opt1In, None)
    val defaultNum = ife3(opt3.isEmpty, 0, opt2.isEmpty, 1, opt1.isEmpty, 2, 3)
    override def syntaxDepthT(obj: R): Int = ???

    override def strDecs(obj: R, way: ShowStyle, maxPlaces: Int): Strings = ???
  }
}

trait PersistShow3[A1, A2 <: ShowDec, A3 <: ShowDec, R <: ShowDec3[A1, A2, A3]] extends Persist3[A1, A2, A3, R]  with PersistShowDecN[R] with ShowShowDec3T[A1, A2, A3, R]

/** Companion object for the [[PersistShow3]] class the persists object that extend [[ShowDec3]]. Contains an apply factory method. */
object PersistShow3
{ /** Factory apply method for [[PersistShow3]], that Persists [[ShowDec3]] objects. */
  def apply[A1 <: ShowDec, A2 <: ShowDec, A3 <: ShowDec, R <: ShowDec3[A1, A2, A3]](typeStr: String, name1: String, name2: String, name3: String, newT: (A1, A2, A3) => R,
    opt3: Option[A3] = None, opt2: Option[A2] = None, opt1: Option[A1] = None)(implicit ev1In: PersistDec[A1], ev2In: PersistDec[A2], ev3In: PersistDec[A3]):
    PersistShow3[A1, A2, A3, R] = new PersistShow3Imp[A1, A2, A3, R](typeStr, name1, name2, name3, newT, opt3,opt2, opt1)

  class PersistShow3Imp[A1 <: ShowDec, A2 <: ShowDec, A3 <: ShowDec, R <: ShowDec3[A1, A2, A3]](val typeStr: String, val name1: String, val name2: String, val name3: String,
    val newT: (A1, A2, A3) => R, val opt3: Option[A3] = None, opt2In: Option[A2] = None, opt1In: Option[A1] = None)(
    implicit val ev1: PersistDec[A1], val ev2: PersistDec[A2], val ev3: PersistDec[A3]) extends PersistShow3[A1, A2, A3, R]
  { val opt2: Option[A2] = ife(opt3.nonEmpty, opt2In, None)
    val opt1: Option[A1] = ife(opt2.nonEmpty, opt1In, None)
  }
}

/** Persistence class for types that extends [[ShowDbl3]]. */
class PersistShowDbl3[R <: ShowDbl3](val typeStr: String, val name1: String, val name2: String, val name3: String,
  val newT: (Double, Double, Double) => R, val opt3: Option[Double] = None, val opt2In: Option[Double] = None, opt1In: Option[Double] = None) extends
  Persist3[Double, Double, Double, R] with PersistShowN[R] with ShowShowDbl3T[R]
{ val opt2: Option[Double] = ife(opt3.nonEmpty, opt2In, None)
  val opt1: Option[Double] = ife(opt2.nonEmpty, opt1In, None)
  override def ev1: PersistDec[Double] = ShowT.doublePersistEv
  override def ev2: PersistDec[Double] = ShowT.doublePersistEv
  override def ev3: PersistDec[Double] = ShowT.doublePersistEv
}