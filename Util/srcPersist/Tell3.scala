/* Copyright 2018-23 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat
import pParse._

/** Trait for [[TellDec]] for a product of 3 logical elements. This trait is implemented directly by the type in question, unlike the corresponding
 *  [[ShowEq3T]] trait which externally acts on an object of the specified type to create its String representations. For your own types it is better to
 *  inherit from Show3 and then use [[Show3ElemT]] or [[Persist3ElemT]] to create the type class instance for ShowT. The [[Show3ElemT]] or
 *  [[Persist3Elem]] class will delegate to Show3 for some of its methods. It is better to use Show3 to override toString method than delegating the
 *  toString override to a [[ShowEq3T]] instance. */
trait Tell3[A1, A2, A3] extends Any with Show3Plused[A1, A2, A3]
{ override def numParams: Int = 3
  override def paramNames: StrArr = StrArr(name1, name2, name3)
  override def elemTypeNames: StrArr = StrArr(persist1.typeStr, persist2.typeStr, persist3.typeStr)

  override def showElemStrs(way: ShowStyle): StrArr = StrArr(persist1.showT(show1, way), persist2.showT(show2, way), persist3.showT(show3, way))

  override def showElemStrDecs(way: ShowStyle, decimalPlaces: Int): StrArr =
    StrArr(persist1.showDecT(show1, way, decimalPlaces, 0), persist2.showDecT(show2, way, decimalPlaces, 0),
      persist3.showDecT(show3, way, decimalPlaces, 0))
}