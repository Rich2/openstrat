/* Copyright 2018-23 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package pWeb
import geom._

/** An SVG element. */
trait SvgElem extends Xml1Lineable

case class HtmlSvg(contents: RArr[XCon], attribs: RArr[XmlAtt]) extends HtmlMultiLine
{ override def tag: String = "svg"
}

object HtmlSvg
{
  def apply(rect: Rect, contents: RArr[GraphicElem], otherAtts: RArr[XmlAtt] = RArr()): HtmlSvg =
  { val atts = RArr(WidthAtt(rect.width), HeightAtt(rect.height), ViewBox(rect.left, rect.bottom, rect.width, rect.height)) ++ otherAtts
    val svgElems = contents.flatMap(_.svgElems)
    new HtmlSvg(svgElems, atts)
  }

  def bounds(minX: Double, minY: Double, width: Double, height: Double, arr: RArr[XCon]): HtmlSvg =
    new HtmlSvg(arr, RArr(WidthAtt(width), HeightAtt(height), ViewBox(minX, minY, width, height)))

  def bounds(rect: Rect, contents: RArr[XCon], otherAtts: RArr[XmlAtt] = RArr()): HtmlSvg =
  { val atts = RArr(WidthAtt(rect.width), HeightAtt(rect.height), ViewBox(rect.left, rect.bottom, rect.width, rect.height)) ++ otherAtts
    new HtmlSvg(contents, atts)
  }
}

class SvgCircle(attribsIn: RArr[XmlAtt], val contents: RArr[XCon] = RArr()) extends SvgElem
{ override def tag: String = "circle"
  override val attribs: RArr[XmlAtt] = attribsIn.explicitFill
}

object SvgCircle
{ /** Factory apply method for SVG circle class. */
  def apply(attribsIn: RArr[XmlAtt], contents: RArr[XCon] = RArr()): SvgCircle = new SvgCircle(attribsIn, contents)
}

class SvgEllipse(attribsIn: RArr[XmlAtt], val contents: RArr[XCon] = RArr()) extends SvgElem
{ override def tag: String = "ellipse"
  override val attribs: RArr[XmlAtt] = attribsIn.explicitFill
}

object SvgEllipse
{ /** Factory apply method for SVG ellipse class. */
  def apply(attribsIn: RArr[XmlAtt], contents: RArr[XCon] = RArr()): SvgEllipse = new SvgEllipse(attribsIn, contents)
}

class SvgPolygon(attribsIn: RArr[XmlAtt], val contents: RArr[XCon] = RArr()) extends SvgElem
{ override def tag: String = "polygon"
  override val attribs: RArr[XmlAtt] = attribsIn.explicitFill
}

object SvgPolygon
{ /** Factory apply method for SVG polygon class. */
  def apply(attribs: RArr[XmlAtt], contents: RArr[XCon] = RArr()): SvgPolygon = new SvgPolygon(attribs, contents)
}

class SvgRect(attribsIn: RArr[XmlAtt], val contents: RArr[XCon] = RArr()) extends SvgElem
{ override def tag: String = "rect"
  override val attribs: RArr[XmlAtt] = attribsIn.explicitFill
}

object SvgRect
{ /** Factory apply method for SVG RECT rectangle class. */
  def apply(attribs: RArr[XmlAtt], contents: RArr[XCon] = RArr()): SvgRect = new SvgRect(attribs, contents)
}

case class SvgLine(x1: Double, y1: Double, x2: Double, y2: Double, colour: Colour, width: Double) extends SvgElem
{ override def tag: String = "line"
  override def attribs: RArr[XmlAtt] = RArr(XmlAtt("x1", x1.toString), XmlAtt("y1", y1.toString), XmlAtt("x2", x2.toString),
    XmlAtt("y2", y2.toString), StrokeAttrib(colour), StrokeWidthAttrib(width))

  /** The content of this XML / HTML element. */
  override def contents: RArr[XCon] = RArr()
}

class SvgText(val x: Double, val y: Double, val text: String, val align: TextAlign) extends SvgElem
{ override def tag: String = "text"
  override def attribs: RArr[XmlAtt] = RArr(XXmlAtt(x), YXmlAtt(y), align.attrib)
  override def contents: RArr[XCon] = RArr(text.xCon)
}

object SvgText
{ def apply(x: Double, y: Double, text: String, align: TextAlign): SvgText = new SvgText(x, y, text, align)
  def apply(posn: Pt2, text: String, align: TextAlign): SvgText = new SvgText(posn.x, posn.y, text, align)
}

class SvgGroup(val contents: RArr[XCon], val attribs: RArr[XmlAtt])extends SvgElem
{
  override def tag: String = "g"
}

object SvgGroup{
  def apply(contents: RArr[XCon], attribs: XmlAtt*): SvgGroup = new SvgGroup(contents, attribs.toArr)
}