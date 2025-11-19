/* Copyright 2018-25 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package pWeb
import geom.*, Colour.Black

/** An SVG element. */
trait SvgOwnLine extends XmlOwnLine

/** Class to produce an SVG line. */
class SvgLine(val x1: Double, val y1: Double, val x2: Double, val y2: Double, otherAttribs: RArr[XAtt]) extends SvgOwnLine
{ override def tagName: String = "line"
  val x1Att: XAtt = XAtt("x1", x1.str2)
  val y1Att: XAtt = XAtt("y1", (-y1).str2)
  val x2Att: XAtt = XAtt("x2", x2.str2)
  val y2Att: XAtt = XAtt("y2", (-y2).str2)
  override def attribs: RArr[XAtt] = RArr(x1Att, y1Att, x2Att, y2Att) ++ otherAttribs
  override def contents: RArr[XConCompound] = RArr()
}

object SvgLine
{
  def apply(x1: Double, y1: Double, x2: Double, y2: Double, colour: Colour, width: Double, otherAttribs: XAtt*): SvgLine =
  { val attribs1: RArr[XAtt] = RArr(StrokeAttrib(colour), StrokeWidthAttrib(width))
    new SvgLine(x1, y1, x2, y2, attribs1 ++ otherAttribs.toArr)
  }

  def bare(x1: Double, y1: Double, x2: Double, y2: Double, otherAttribs: XAtt*): SvgLine = new SvgLine(x1, y1, x2, y2, otherAttribs.toArr)
}

class SvgText(val x: Double, val y: Double, val text: String, val align: TextAlign, colour: Colour) extends SvgOwnLine
{ override def tagName: String = "text"
  override def attribs: RArr[XAtt] = RArr(XXmlAtt(x), YXmlAtt(y), align.attrib).appendIf(colour != Black, FillAttrib(colour))
  override def contents: RArr[XCon] = RArr(text)
}

object SvgText
{ def xy(x: Double, y: Double, text: String, align: TextAlign, colour: Colour = Black): SvgText = new SvgText(x, y, text, align, colour)
  def apply(posn: Pt2, text: String, align: TextAlign, colour: Colour = Black): SvgText = new SvgText(posn.x, posn.y, text, align, colour)
}

class SvgGroup(val contents: RArr[XConCompound], val attribs: RArr[XAtt])extends SvgOwnLine
{
  override def tagName: String = "g"
}

object SvgGroup{
  def apply(contents: RArr[XConCompound], attribs: XAtt*): SvgGroup = new SvgGroup(contents, attribs.toArr)
}