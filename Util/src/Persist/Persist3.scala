/* Copyright 2018-21 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat

/** Trait for [[Show]] for a product of 3 logical elements. This trait is implemented directly by the type in question, unlike the corresponding
 *  [[Show3T]] trait which externally acts on an object of the specified type to create its String representations. For your own types it is better to
 *  inherit from Show3 and then use [[Show3ElemT]] or [[Persist3ElemT]] to create the type class instance for ShowT. The [[Show3ElemT]] or
 *  [[Persist3Elem]] class will delegate to Show3 for some of its methods. It is better to use Show3 to override toString method than delegating the
 *  toString override to a [[Show3T]] instance. */
trait Show3[A1, A2, A3] extends ShowProduct with Prod3[A1, A2, A3]
{ /** the name of the 1st element of this 3 element Show product. */
  def name1: String

  /** the name of the 2nd element of this 3 element Show product. */
  def name2: String

  /** the name of the 3rd element of this 3 element Show product. */
  def name3: String

  /** The ShowT type class instance for the 1st element of this 3 element Show product. */
  def showT1: ShowT[A1]

  /** The ShowT type class instance for the 2nd element of this 3 element Show product. */
  def showT2: ShowT[A2]

  /** The ShowT type class instance for the 3rd element of this 3 element Show product. */
  def showT3: ShowT[A3]

  def elemNames: Strings = Strings(name1, name2, name3)
  def elemTypeNames: Strings = Strings(showT1.typeStr, showT2.typeStr, showT3.typeStr)
  def shows(way: Show.Way, decimalPlaces: Int): Strings = Strings(showT1.showT(el1, way, decimalPlaces), showT2.showT(el2, way, decimalPlaces),
    showT3.showT(el3, way, decimalPlaces))
}

/** Show type class for 3 parameter case classes. */
class Show3T[A1, A2, A3, R](val typeStr: String, name1: String, fArg1: R => A1, name2: String, fArg2: R => A2, name3: String, fArg3: R => A3,
  val opt3: Option[A3] = None, opt2In: Option[A2] = None, opt1In: Option[A1] = None)(implicit ev1: ShowT[A1], ev2: ShowT[A2], ev3: ShowT[A3],
  eq1: EqT[A1], eq2: EqT[A2], eq3: EqT[A3]) extends Eq3T[A1, A2, A3, R](fArg1, fArg2, fArg3) with ShowProductT[R]
{
  val opt2: Option[A2] = ife(opt3.nonEmpty, opt2In, None)
  val opt1: Option[A1] = ife(opt2.nonEmpty, opt1In, None)
  val defaultNum = ife3(opt3.isEmpty, 0, opt2.isEmpty, 1, opt1.isEmpty, 2, 3)
  final override def syntaxDepthT(obj: R): Int = ev1.syntaxDepthT(fArg1(obj)).max(ev2.syntaxDepthT(fArg2(obj))).max(ev3.syntaxDepthT(fArg3(obj))) + 1
  override def strs(obj: R, way: Show.Way, decimalPlaces: Int): Strings =
    Strings(ev1.showT(fArg1(obj), way, decimalPlaces), ev2.showT(fArg2(obj), way, decimalPlaces), ev3.showT(fArg3(obj), way, decimalPlaces))
}