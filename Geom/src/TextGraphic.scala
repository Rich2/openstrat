/* Copyright 2018-23 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package geom
import Colour.Black, pWeb._


/** A Graphical display of Text.
 * @param posn The point to orient from. By default this Vec2 defines the centre but from right or left depending  on alignment. */
case class TextGraphic(str: String, fontSize: Double, xPosn: Double, yPosn: Double, colour: Colour, align: TextAlign, baseLine: BaseLine) extends
  GraphicAffineElem with CanvElem with GraphicAndSvgElem
{ type ThisT = TextGraphic
  def posn: Pt2 = Pt2(xPosn, yPosn)
  override def ptsTrans(f: Pt2 => Pt2) = TextGraphic(str, fontSize, f(posn), colour, align, baseLine)
  override def rendToCanvas(cp: pgui.CanvasPlatform): Unit = cp.textGraphic(this)

  override def svgElem: SvgElem = SvgText(xPosn, -yPosn, str, align)
}

object TextGraphic
{
  def apply(str: String, fontSize: Double = 24, posn: Pt2 = Pt2Z, colour: Colour = Black, align: TextAlign = CenAlign,
    baseLine: BaseLine = BaseLine.Middle) = new TextGraphic(str, fontSize, posn.x, posn.y, colour, align, baseLine)

  def xy(str: String, fontSize: Double = 24, xPosn: Double, yPosn: Double, colour: Colour = Black, align: TextAlign = CenAlign,
    baseLine: BaseLine = BaseLine.Middle) = new TextGraphic(str, fontSize, xPosn, yPosn, colour, align, baseLine)

  def lines(strs: StrArr, fontSize: Double = 24, posn: Pt2 = Pt2Z, fontColour: Colour = Black, lineSpacing: Double = 1,
    align: TextAlign = CenAlign, baseLine: BaseLine = BaseLine.Alphabetic): RArr[TextGraphic] =
  { val len = strs.length
    if(len == 0) RArr()
    else strs.iMap((i, str) => TextGraphic(str, fontSize, posn.addY(((len -1) / 2.0 - i) * fontSize * lineSpacing), fontColour, align, baseLine))
  }
}