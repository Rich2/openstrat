/* Copyright 2018-25 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package pWeb
import geom._, Colour.Black

/** An SVG element. */
trait SvgElem extends XmlMaybeSingle

/** An HTML element for SVG. */
case class HtmlSvg(contents: RArr[XConElem], attribs: RArr[XHAtt]) extends HtmlMultiLine
{ override def tag: String = "svg"
}

object HtmlSvg
{
  def apply(rect: Rect, contents: RArr[Graphic2Elem], otherAtts: RArr[XHAtt] = RArr()): HtmlSvg =
  { val atts = RArr(WidthAtt(rect.width), HeightAtt(rect.height), ViewBox(rect.left, -rect.top, rect.width, rect.height)) ++ otherAtts
    val svgElems = contents.flatMap(_.svgElems)
    new HtmlSvg(svgElems, atts)
  }

  def bounds(minX: Double, minY: Double, width: Double, height: Double, arr: RArr[XConElem]): HtmlSvg =
    new HtmlSvg(arr, RArr(WidthAtt(width), HeightAtt(height), ViewBox(minX, minY, width, height)))

  def bounds(rect: Rect, contents: RArr[XConElem], otherAtts: RArr[XHAtt] = RArr()): HtmlSvg =
  { val atts = RArr(WidthAtt(rect.width), HeightAtt(rect.height), ViewBox(rect.left, rect.bottom, rect.width, rect.height)) ++ otherAtts
    new HtmlSvg(contents, atts)
  }

  def auto(margin: Double, contents: RArr[Graphic2Elem], otherAtts: RArr[XHAtt] = RArr()): HtmlSvg = autoHorrVert(margin, margin, contents, otherAtts)

  def autoHorrVert(horrMargin: Double, vertMargin: Double, contents: RArr[Graphic2Elem], otherAtts: RArr[XHAtt] = RArr()): HtmlSvg =
  { val rect: Rect = Rect.bounding(contents).addHorrVertMargin(horrMargin, vertMargin)
    val atts = RArr(WidthAtt(rect.width), HeightAtt(rect.height), ViewBox(rect.left, -rect.top, rect.width, rect.height)) ++ otherAtts
    val svgElems = contents.flatMap(_.svgElems)
    new HtmlSvg(svgElems, atts)
  }
}

class SvgCircle(attribsIn: RArr[XHAtt], val contents: RArr[XConElem] = RArr()) extends SvgElem
{ override def tag: String = "circle"
  override val attribs: RArr[XHAtt] = attribsIn.explicitFill
}

object SvgCircle
{ /** Factory apply method for SVG circle class. */
  def apply(attribsIn: RArr[XHAtt], contents: RArr[XConElem] = RArr()): SvgCircle = new SvgCircle(attribsIn, contents)
}

class SvgEllipse(attribsIn: RArr[XHAtt], val contents: RArr[XConElem] = RArr()) extends SvgElem
{ override def tag: String = "ellipse"
  override val attribs: RArr[XHAtt] = attribsIn.explicitFill
}

object SvgEllipse
{ /** Factory apply method for SVG ellipse class. */
  def apply(attribsIn: RArr[XHAtt], contents: RArr[XConElem] = RArr()): SvgEllipse = new SvgEllipse(attribsIn, contents)
}

class SvgPolygon(attribsIn: RArr[XHAtt], val contents: RArr[XConElem] = RArr()) extends SvgElem
{ override def tag: String = "polygon"
  override val attribs: RArr[XHAtt] = attribsIn.explicitFill
}

object SvgPolygon
{ /** Factory apply method for SVG polygon class. */
  def apply(attribs: RArr[XHAtt], contents: RArr[XConElem] = RArr()): SvgPolygon = new SvgPolygon(attribs, contents)
}

class SvgRect(attribsIn: RArr[XHAtt], val contents: RArr[XConElem] = RArr()) extends SvgElem
{ override def tag: String = "rect"
  override val attribs: RArr[XHAtt] = attribsIn.explicitFill
}

object SvgRect
{ /** Factory apply method for SVG RECT rectangle class. */
  def apply(attribs: RArr[XHAtt], contents: RArr[XConElem] = RArr()): SvgRect = new SvgRect(attribs, contents)
}

/** Class to produce an SVG line. */
class SvgLine(val x1: Double, val y1: Double, val x2: Double, val y2: Double, otherAttribs: RArr[XHAtt]) extends SvgElem
{ override def tag: String = "line"
  val x1Att: XHAtt = XHAtt("x1", x1.toString)
  val y1Att: XHAtt = XHAtt("y1", (-y1).toString)
  val x2Att: XHAtt = XHAtt("x2", x2.toString)
  val y2Att: XHAtt = XHAtt("y2", (-y2).toString)
  override def attribs: RArr[XHAtt] = RArr(x1Att, y1Att, x2Att, y2Att) ++ otherAttribs
  override def contents: RArr[XConElem] = RArr()
}

object SvgLine
{
  def apply(x1: Double, y1: Double, x2: Double, y2: Double, colour: Colour, width: Double, otherAttribs: XHAtt*): SvgLine =
  { val attribs1: RArr[XHAtt] = RArr(StrokeAttrib(colour), StrokeWidthAttrib(width))
    new SvgLine(x1, y1, x2, y2, attribs1 ++ otherAttribs.toArr)
  }

  def bare(x1: Double, y1: Double, x2: Double, y2: Double, otherAttribs: XHAtt*): SvgLine = new SvgLine(x1, y1, x2, y2, otherAttribs.toArr)
}

class SvgText(val x: Double, val y: Double, val text: String, val align: TextAlign, colour: Colour) extends SvgElem
{ override def tag: String = "text"
  override def attribs: RArr[XHAtt] = RArr(XXmlAtt(x), YXmlAtt(y), align.attrib).appendIf(colour != Black, FillAttrib(colour))
  override def contents: RArr[XConElem] = RArr(text.xCon)
}

object SvgText
{ def xy(x: Double, y: Double, text: String, align: TextAlign, colour: Colour = Black): SvgText = new SvgText(x, y, text, align, colour)
  def apply(posn: Pt2, text: String, align: TextAlign, colour: Colour = Black): SvgText = new SvgText(posn.x, posn.y, text, align, colour)
}

class SvgGroup(val contents: RArr[XConElem], val attribs: RArr[XHAtt])extends SvgElem
{
  override def tag: String = "g"
}

object SvgGroup{
  def apply(contents: RArr[XConElem], attribs: XHAtt*): SvgGroup = new SvgGroup(contents, attribs.toArr)
}