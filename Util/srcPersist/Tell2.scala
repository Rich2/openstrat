/* Copyright 2018-23 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat
import pParse._, collection.mutable.ArrayBuffer

/** Trait for [[TellDec]] for a product of 2 logical elements. This trait is implemented directly by the type in question, unlike the corresponding
 *  [[Show2ing]] trait which externally acts on an object of the specified type to create its String representations. For your own types it is better to
 *  inherit from Show2 and then use [[Show2eding]] or [[Persist2ElemT]] to create the type class instance for ShowT. The [[Show2eding]] or
 *  [[Persist2ed]] class will delegate to Show2 for some of its methods. It is better to use Show2 to override toString method than delegating the
 *  toString override to a [[Show2ing]] instance. */
trait Tell2[A1, A2] extends Any with Show2Plused[A1, A2] with PersistBase2[A1, A2]
{ override def paramNames: StrArr = StrArr(name1, name2)
  def elemTypeNames: StrArr = StrArr(persist1.typeStr, persist2.typeStr)
  def showElemStrDecs(way: ShowStyle, decimalPlaces: Int): StrArr = StrArr(persist1.showDecT(show1, way, decimalPlaces, 0), persist2.showDecT(show2, way, decimalPlaces, 0))

  def el1Show(style: ShowStyle = ShowStandard, maxPlaces: Int = -1): String = persist1.showDecT(show1, style, maxPlaces, maxPlaces): String
  def el2Show(style: ShowStyle = ShowStandard, maxPlaces: Int = -1): String = persist2.showDecT(show2, style, maxPlaces, maxPlaces): String

  override def str: String = typeStr + (persist1.strT(show1).appendSemicolons(persist2.strT(show2))).enParenth

  override def syntaxDepth: Int = persist1.syntaxDepthT(show1).max(persist2.syntaxDepthT(show2)) + 1
}

/** Trait for Show for product of 2 Ints. This trait is implemented directly by the type in question, unlike the corresponding [[ShowInt2Eding]] trait
 *  which externally acts on an object of the specified type to create its String representations. For your own types ShowProduct is preferred over
 *  [[Show2ing]]. */
trait TellInt2 extends Any with Tell2[Int, Int]
{ final override implicit def persist1: Show[Int] = Show.intPersistEv
  final override implicit def persist2: Show[Int] = Show.intPersistEv
  final override def syntaxDepth: Int = 2
}

/** Shows a class with 2 [[Double]] components. Note if the class also extends ElemDbl2, the dbl1 and dbl2 properties, may be different to the show1
 * and show2 properties, unless the class extends [[TellElemDbl2]]. */
trait TellDbl2 extends Any with Tell2[Double, Double]
{ final override implicit def persist1: Show[Double] = Show.doublePersistEv
  final override implicit def persist2: Show[Double] = Show.doublePersistEv
  final override def syntaxDepth: Int = 2
}

/** Trait for Show for product of 2 Doubles that is also an [[Dbl2Elem]]. This trait is implemented directly by the type in question, unlike the
 *  corresponding [[ShowDbl2Eding]] trait which externally acts on an object of the specified type to create its String representations. For your own
 *  types ShowProduct is preferred over [[Show2ing]]. */
trait TellElemDbl2 extends Any with TellDbl2 with Dbl2Elem
{ final override def dbl1: Double = show1
  final override def dbl2: Double = show2
}