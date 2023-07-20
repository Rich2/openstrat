/* Copyright 2018-23 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package egrid
import pWeb._

object EGridPage extends HtmlPage
{
  override def head: HtmlHead = HtmlHead.titleCss("EGrid Module", "https://richstrat.com/Documentation/documentation")

  override def body: HtmlBody = HtmlBody(HtmlH1("EGrid Module"), central)

  def central: HtmlDiv = HtmlDiv.classAtt("central", open)

  //def list: HtmlOlWithLH = HtmlOlWithLH(HtmlH2("The Earth module contains"), open)

  def open = HtmlP("Hex tile grids for the Earth at various scales, 320km, 220km, 160km, 120km and 80km. The tile grids work the same as normal hex" --
    "grids from in the\n  Tiling module, except they have to be joined togethor at the 15, 45, 75 degree longitude boundaries.")
}