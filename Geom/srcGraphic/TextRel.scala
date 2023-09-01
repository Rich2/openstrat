/* Copyright 2018-23 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package geom
import Colour.Black

case class TextRel(str: String, fontRel: Double, xPosn: Double, yPosn: Double, colour: Colour, align: TextAlign, baseLine: BaseLine)
{
  def abs(ratio: Double): TextFixed = TextFixed(str, fontRel * ratio, xPosn * ratio, yPosn * ratio, colour, textAlign = CenAlign, baseLine = BaseLine.Middle)
}
object TextRel
{
  def apply(str: String, fontSize: Double = 24, posn: Pt2 = Pt2Z, colour: Colour = Black, align: TextAlign = CenAlign,
    baseLine: BaseLine = BaseLine.Middle): TextRel = new TextRel(str, fontSize, posn.x, posn.y, colour, align, baseLine)

  def xy(str: String, fontSize: Double = 24, xPosn: Double, yPosn: Double, colour: Colour = Black, align: TextAlign = CenAlign,
    baseLine: BaseLine = BaseLine.Middle): TextRel = new TextRel(str, fontSize, xPosn, yPosn, colour, align, baseLine)
}