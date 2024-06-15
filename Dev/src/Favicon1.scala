/* Copyright 2018-24 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package pDev
import geom.*
import ostrat.pWeb.HtmlSvg

object Favicon1 {
  def apply() = Sqlign(200).fill(Colour.Red).svgInline.out()
}
