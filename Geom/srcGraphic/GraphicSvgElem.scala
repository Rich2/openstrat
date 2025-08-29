/* Copyright 2018-23 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package geom
import ostrat.pWeb._

/** Graphic element that can be described with single [[SvgOwnLine]] */
trait GraphicSvgElem extends Graphic2Elem
{
  def svgElem: SvgOwnLine

  override def svgElems: RArr[SvgOwnLine] = RArr(svgElem)
}
