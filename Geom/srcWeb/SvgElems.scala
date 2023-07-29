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

class SvgText(val x: Double, val y: Double, val text: String) extends SvgElem
{ override def tag: String = "text"
  override def attribs: RArr[XmlAtt] = RArr(XAttrib(x), YAttrib(y))
  override def contents: RArr[XCon] = RArr(text.xCon)
}

object SvgText
{
  def apply(x: Double, y: Double, text: String): SvgText = new SvgText(x, y, text)
  def apply(posn: Pt2, text: String): SvgText = new SvgText(posn.x, posn.y, text)
}