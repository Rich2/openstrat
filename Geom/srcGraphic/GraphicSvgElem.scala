/* Copyright 2018-26 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package geom
import ostrat.pweb.*

/** Graphic element that can be described with single [[pWeb.SvgOwnLine]] */
trait GraphicSvgElem extends Graphic2Elem
{
  def svgElem: SvgOwnLine

  override def svgElems: RArr[SvgOwnLine] = RArr(svgElem)
}