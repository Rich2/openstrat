/* Copyright 2026 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package pWeb; package osweb

object CentralRule extends CssClassesRule
{ override def classStr: String = "central"
  override def decsArr: RArr[CssDecs] = RArr(MaxWidthDec(68.em), MarginLRAutoDec)
}