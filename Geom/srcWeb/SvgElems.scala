/* Copyright 2018-23 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package pWeb
import geom._

/** An SVG element. */
trait SvgElem extends XmlMulti

case class SvgSvgElem(contents: RArr[XCon], attribs: RArr[XmlAtt]) extends SvgElem
{ override def tag: String = "svg"  
}

object SvgSvgElem
{
  def apply(minX: Double, minY: Double, width: Double, height: Double, contents: XCon*): SvgSvgElem =
  new SvgSvgElem(contents.toArr, RArr(WidthAtt(width), HeightAtt(height), ViewBox(minX, minY, width, height), ClassAtt("centreBlock")))

  def apply(minX: Double, minY: Double, width: Double, height: Double, arr: RArr[XCon]): SvgSvgElem =
    new SvgSvgElem(arr, RArr(WidthAtt(width), HeightAtt(height), ViewBox(minX, minY, width, height)))

  def bounds(rect: Rect, contents: RArr[XCon], otherAtts: RArr[XmlAtt] = RArr()): SvgSvgElem =
  { val atts = RArr(WidthAtt(rect.width), HeightAtt(rect.height), ViewBox(rect.left, rect.bottom, rect.width, rect.height)) ++ otherAtts
    new SvgSvgElem(contents, atts)
  }
}

class SvgCircle(attribsIn: RArr[XmlAtt], val contents: RArr[XCon] = RArr()) extends SvgElem
{ override def tag: String = "circle"
  override val attribs: RArr[XmlAtt] = attribsIn.explicitFill
}

case class SvgEllipse(attribs: RArr[XmlAtt], contents: RArr[XCon] = RArr()) extends SvgElem
{ override def tag: String = "ellipse"
}

class SvgPolygon(attribsIn: RArr[XmlAtt], val contents: RArr[XCon] = RArr()) extends SvgElem
{ override def tag: String = "polygon"
  override val attribs: RArr[XmlAtt] = attribsIn.explicitFill
}

case class SvgRect(attribs: RArr[XmlAtt], contents: RArr[XCon] = RArr()) extends SvgElem
{ override def tag: String = "rect"
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
{
  def apply(x: Double, y: Double, text: String, align: TextAlign): SvgText = new SvgText(x, y, text, align)
  def apply(posn: Pt2, text: String, align: TextAlign): SvgText = new SvgText(posn.x, posn.y, text, align)
}