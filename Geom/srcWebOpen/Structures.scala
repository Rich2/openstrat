/* Copyright 2026 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package pWeb; package osweb

/** The main content of the web page, it will take the full width up to 68em that will appear in the centre of the page if there is sufficient space, allowing
 * for subsidiary content to left and right. */
object CentralRule extends CssClassesRule
{ override def classStr: String = "central"
  override def decsArr: RArr[CssDecs] = RArr(MaxWidthDec(68.em), MarginLRAutoDec)
}