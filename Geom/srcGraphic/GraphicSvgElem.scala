/* Copyright 2018-23 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package geom
import ostrat.pWeb._

/** Graphic element that can be described with single [[SvgElem]] */
trait GraphicSvgElem extends GraphicElem
{
  def svgElem: SvgElem

  override def svgElems: RArr[SvgElem] = RArr(svgElem)
}
