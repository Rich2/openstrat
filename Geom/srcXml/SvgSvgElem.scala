/* Copyright 2018-25 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package pWeb
import geom.*, Colour.Black

/** The Xmins SVG attribute. */
object XminsSvg extends XmlnsAtt("http://www.w3.org/2000/svg")

/** An XML SVG element. */
class SvgSvgElem(val vbX: Double, val vbY: Double, val vbWidth: Double, vbHeight: Double, val contents: RArr[XConCompound], val otherAttribs: RArr[XAtt]) extends
  XmlTagLines
{ override def tagName: String = "svg"

  override def attribs: RArr[XAtt] = RArr(XminsSvg, ViewBox(vbX, vbY, vbWidth, vbHeight)) ++ otherAttribs
}

object SvgSvgElem
{
  def apply(rect: Rect, contents: RArr[Graphic2Elem], otherAtts: RArr[XAtt] = RArr()): SvgSvgElem =
    new SvgSvgElem(rect.left, -rect.top, rect.width, rect.height, contents.flatMap(_.svgElems), otherAtts)

  def bounds(minX: Double, minY: Double, width: Double, height: Double, arr: RArr[XConCompound]): SvgSvgElem =
    new SvgSvgElem(minX, minY, width, height, arr, RArr())

  def bounds(rect: Rect, contents: RArr[XConCompound], otherAtts: RArr[XAtt] = RArr()): SvgSvgElem =
    new SvgSvgElem(rect.left, rect.bottom, rect.width, rect.height, contents, otherAtts)

  def auto(margin: Double, contents: RArr[Graphic2Elem], otherAtts: RArr[XAtt] = RArr()): SvgSvgElem = autoHorrVert(margin, margin, contents, otherAtts)

  def autoHorrVert(horrMargin: Double, vertMargin: Double, contents: RArr[Graphic2Elem], otherAtts: RArr[XAtt] = RArr()): SvgSvgElem =
  { val rect: Rect = Rect.bounding(contents).addHorrVertMargin(horrMargin, vertMargin)
    new SvgSvgElem(rect.left, -rect.top, rect.width, rect.height, contents.flatMap(_.svgElems), otherAtts)
  }
}