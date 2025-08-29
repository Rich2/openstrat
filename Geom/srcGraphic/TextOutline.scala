/* Copyright 2018-24 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package geom
import pWeb._, Colour.Black

class TextOutline(val str: String, val fontSize: Double, val x: Double, val y: Double, val colour: Colour, val lineWidth: Double,
  val align: TextAlign, val baseLine: BaseLine) extends GraphicAffineElem
{ override type ThisT = TextOutline
  def posn: Pt2 = Pt2(x, y)
  override def ptsTrans(f: Pt2 => Pt2) = TextOutline(str, fontSize, f(posn), colour, lineWidth, align, baseLine)
  override def rendToCanvas(cp: pgui.CanvasPlatform): Unit = cp.textOutline(this)

  override def negY: TextOutline = ???

  override def svgElems: RArr[SvgOwnLine] = ???
}

object TextOutline
{
  def apply(str: String, fontSize: Double = 24, posn: Pt2 = Pt2Z, colour: Colour = Black, lineWidth: Double = 1.0, align: TextAlign = CenAlign,
    baseLine: BaseLine = BaseLine.Alphabetic): TextOutline =
    new TextOutline(str, fontSize, posn.x, posn.y, colour, lineWidth, align, baseLine)

  def xy(str: String, fontSize: Double = 24, xPosn: Double, yPosn: Double, colour: Colour = Black, lineWidth: Double = 1.0,
    align: TextAlign = CenAlign, baseLine: BaseLine = BaseLine.Alphabetic): TextOutline =
    new TextOutline(str, fontSize, xPosn, yPosn, colour, lineWidth, align, baseLine)
}