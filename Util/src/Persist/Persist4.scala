/* Copyright 2018-22 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat
import pParse._

/** A base trait for [[Show4T]] and [[Unshow4]], declares the common properties of name1 - 4 and opt1 - 4. */
trait TypeStr4Plus[A1, A2, A3, A4] extends Any with TypeStr3Plus[A1, A2, A3]
{ /** 4th parameter name. */
  def name4: String

  /** The optional default value for parameter 4. */
  def opt4: Option[A4]
}

trait TypeStr4[A1, A2, A3, A4] extends Any with TypeStr4Plus[A1, A2, A3, A4]
{ override def paramNames: StringArr = StringArr(name1, name2, name3, name4)
  override def numParams: Int = 4
}

/** Trait for [[ShowDec]] for a product of 3 logical elements. This trait is implemented directly by the type in question, unlike the corresponding
 *  [[ShowEq3T]] trait which externally acts on an object of the specified type to create its String representations. For your own types it is better to
 *  inherit from Show3 and then use [[Show3ElemT]] or [[Persist3ElemT]] to create the type class instance for ShowT. The [[Show3ElemT]] or
 *  [[Persist3Elem]] class will delegate to Show3 for some of its methods. It is better to use Show3 to override toString method than delegating the
 *  toString override to a [[ShowEq3T]] instance. */
trait Show4[A1, A2, A3, A4] extends Any with ShowN with TypeStr4[A1, A2, A3, A4]
{
  override def opt1: Option[A1] = None
  override def opt2: Option[A2] = None
  override def opt3: Option[A3] = None
  override def opt4: Option[A4] = None

  /** Element 1 of this 4 element Show product. */
  def show1: A1

  /** Element 2 of this 4 element Show product. */
  def show2: A2

  /** Element 3 of this 4 element Show product. */
  def show3: A3

  /** Element 4 of this 4 element Show product. */
  def show4: A4

  /** The [[ShowT]] type class instance for the 1st element of this 4 element Show product. */
  def showT1: ShowT[A1]

  /** The [[ShowT]] type class instance for the 2nd element of this 4 element Show product. */
  def showT2: ShowT[A2]

  /** The [[ShowT]] type class instance for the 3rd element of this 4 element Show product. */
  def showT3: ShowT[A3]

  /** The [[ShowT]] type class instance for the 4th element of this 4 element Show product. */
  def showT4: ShowT[A4]

  override def paramNames: StringArr = StringArr(name1, name2, name3, name4)
  override def elemTypeNames: StringArr = StringArr(showT1.typeStr, showT2.typeStr, showT3.typeStr, showT4.typeStr)

  override def showElemStrs(way: ShowStyle): StringArr = StringArr(showT1.showT(show1, way), showT2.showT(show2, way), showT3.showT(show3, way), showT4.showT(show4, way))

  override def showElemStrDecs(way: ShowStyle, decimalPlaces: Int): StringArr = StringArr(showT1.showDecT(show1, way, decimalPlaces, 0), showT2.showDecT(show2, way, decimalPlaces, 0),
    showT3.showDecT(show3, way, decimalPlaces, 0), showT4.showDecT(show4, way, decimalPlaces, 0))
}

/** Show type class for 4 parameter case classes. */
trait Show4T[A1, A2, A3, A4, R] extends ShowNT[R]

object Show4T
{
  class Show4TImp[A1, A2, A3, A4, R](val typeStr: String, val name1: String, val fArg1: R => A1, val name2: String, val fArg2: R => A2,
    val name3: String, val fArg3: R => A3, val name4: String, val fArg4: R => A4, val opt4: Option[A4] = None, opt3In: Option[A3] = None, opt2In: Option[A2] = None,
    opt1In: Option[A1] = None)(implicit ev1: ShowT[A1], ev2: ShowT[A2], ev3: ShowT[A3], ev4: ShowT[A4]) extends Show4T[A1, A2, A3, A4, R] //with TypeStr4Plus[A1, A2, A3, A4]
  {
    val opt3: Option[A3] = ife(opt4.nonEmpty, opt3In, None)
    val opt2: Option[A2] = ife(opt3.nonEmpty, opt2In, None)
    val opt1: Option[A1] = ife(opt2.nonEmpty, opt1In, None)

    final override def syntaxDepthT(obj: R): Int = ev1.syntaxDepthT(fArg1(obj)).max(ev2.syntaxDepthT(fArg2(obj))).max(ev3.syntaxDepthT(fArg3(obj))).
      max(ev4.syntaxDepthT(fArg4(obj))) + 1

    override def strDecs(obj: R, way: ShowStyle, maxPlaces: Int): StringArr = StringArr(ev1.showDecT(fArg1(obj), way, maxPlaces), ev2.showDecT(fArg2(obj), way, maxPlaces),
      ev3.showDecT(fArg3(obj), way, maxPlaces), ev4.showDecT(fArg4(obj), way, maxPlaces))
  }
}

/** UnShow class for 3 logical parameter product types. */
trait Unshow4[A1, A2, A3, A4, R] extends UnshowN[R] with TypeStr4[A1, A2, A3, A4]
{
  /** The UnShow type class instance for type A1. */
  def ev1: Unshow[A1]

  /** The UnShow type class instance for type A2. */
  def ev2: Unshow[A2]

  /** The UnShow type class instance for type A3. */
  def ev3: Unshow[A3]

  /** The UnShow type class instance for type A4. */
  def ev4: Unshow[A4]

  def newT: (A1, A2, A3, A4) => R

  protected def fromSortedExprs(sortedExprs: RArr[Expr], pSeq: IntArr): EMon[R] =
  { val len: Int = sortedExprs.length
    val e1: EMon[A1] = ife(len > pSeq(0), ev1.fromSettingOrExpr(name1, sortedExprs(pSeq(0))), opt1.toEMon)
    def e2: EMon[A2] = ife(len > pSeq(1), ev2.fromSettingOrExpr(name2, sortedExprs(pSeq(1))), opt2.toEMon)
    def e3: EMon[A3] = ife(len > pSeq(2), ev3.fromSettingOrExpr(name3, sortedExprs(pSeq(2))), opt3.toEMon)
    def e4: EMon[A4] = ife(len > pSeq(3), ev4.fromSettingOrExpr(name4, sortedExprs(pSeq(3))), opt4.toEMon)
    e1.map4(e2, e3, e4)(newT)
  }
}