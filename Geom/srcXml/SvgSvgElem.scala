/* Copyright 2018-25 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package pWeb
import geom.*, Colour.Black

/** The Xmins SVG attribute. */
object XminsSvg extends XmlnsAtt("http://www.w3.org/2000/svg")

/** An XML SVG element. */
trait SvgSvgElem extends XmlTagLines
{ override def tagName: String = "svg"
}

/** An XML SVG element with a viewBox but without a specified width and height. */
class SvgSvgRel(val vbX: Double, val vbY: Double, val vbWidth: Double, vbHeight: Double, val contents: RArr[XCon], val otherAttribs: RArr[XAtt]) extends
  SvgSvgElem
{ override def attribs: RArr[XAtt] = RArr(XminsSvg, ViewBox(vbX, vbY, vbWidth, vbHeight)) ++ otherAttribs
}

object SvgSvgRel
{
  def apply(rect: Rect, contents: RArr[Graphic2Elem], otherAtts: RArr[XAtt] = RArr()): SvgSvgRel =
    new SvgSvgRel(rect.left, -rect.top, rect.width, rect.height, contents.flatMap(_.svgElems), otherAtts)

  def bounds(minX: Double, minY: Double, width: Double, height: Double, arr: RArr[XConCompound]): SvgSvgRel =
    new SvgSvgRel(minX, minY, width, height, arr, RArr())

  def bounds(rect: Rect, contents: RArr[XConCompound], otherAtts: RArr[XAtt] = RArr()): SvgSvgRel =
    new SvgSvgRel(rect.left, rect.bottom, rect.width, rect.height, contents, otherAtts)

  def auto(margin: Double, contents: RArr[Graphic2Elem], otherAtts: RArr[XAtt] = RArr()): SvgSvgRel = autoHorrVert(margin, margin, contents, otherAtts)

  def autoHorrVert(horrMargin: Double, vertMargin: Double, contents: RArr[Graphic2Elem], otherAtts: RArr[XAtt] = RArr()): SvgSvgRel =
  { val rect: Rect = Rect.bounding(contents).addHorrVertMargin(horrMargin, vertMargin)
    new SvgSvgRel(rect.left, -rect.top, rect.width, rect.height, contents.flatMap(_.svgElems), otherAtts)
  }

  /** Produced an SVG XML element with a square viewBox centered on the origin. */
  def square(width: Double, contents: Graphic2Elem*): SvgSvgRel = new SvgSvgRel(-width / 2, -width / 2, width, width, contents.flatMapArr(_.svgElems), RArr())
}

object FaviconSvg
{
  /** Produced an SVG XML element with a 100 width square viewBox centered on the origin. There is an apply name overload that takes an [[RArr]] of
   * [[Graphic2Elem]] and an [[RArr]] of [[XAtt]] as parameters. */
  def apply(contents: Graphic2Elem*): SvgSvgRel = new SvgSvgRel(-100 / 2, -100 / 2, 100, 100, contents.flatMapArr(_.svgElems), RArr())

  /** Produced an SVG XML element with a 100 width square viewBox centered on the origin. */
  def apply(contents: RArr[Graphic2Elem], otherAtts: RArr[XAtt] = RArr()): SvgSvgRel =
    new SvgSvgRel(-100 / 2, -100 / 2, 100, 100, contents.flatMap(_.svgElems), otherAtts)

  /** Produced an SVG XML element with a square viewBox centered on the origin. */
  def width(width: Double, contents: Graphic2Elem*): SvgSvgRel = new SvgSvgRel(-width / 2, -width / 2, width, width, contents.flatMapArr(_.svgElems), RArr())
}

/** XML SVG element with a given width and height. */
class SvgSvgScaled(val width: Double, val height: Double, val vbX: Double, val vbY: Double, val vbWidth: Double, vbHeight: Double,
  val contents: RArr[XConCompound], val otherAttribs: RArr[XAtt]) extends SvgSvgElem
{ override def attribs: RArr[XAtt] = RArr(WidthAtt(width), HeightAtt(height), XminsSvg, ViewBox(vbX, vbY, vbWidth, vbHeight)) ++ otherAttribs
}

object SvgSvgScaled
{ /** Factory apply method for XML SVG element with a given width and height. */
  def apply(rect: Rect, contents: RArr[Graphic2Elem], otherAtts: RArr[XAtt] = RArr()): SvgSvgScaled =
    new SvgSvgScaled(rect.width, rect.height, rect.left, -rect.top, rect.width, rect.height, contents.flatMap(_.svgElems), otherAtts)
}