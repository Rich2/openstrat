/* Copyright 2018-25 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package geom
import Colour.Black, pWeb._

trait TextGraphic extends CanvElem{
  def str: String
  def fontSize: Double
  def xPosn: Double
  def yPosn: Double
  def colour: Colour
  def textAlign: TextAlign
  def baseLine: BaseLine

  override def slate(xOperand: Double, yOperand: Double): TextGraphic
  override def negY: TextGraphic
  def posn: Pt2 = Pt2(xPosn, yPosn)
  override def rendToCanvas(cp: pgui.CanvasPlatform): Unit = cp.textGraphic(this)
}

/** Text graphic fixed in font size and orientation.
 * @param posn The point to orient from. By default this Vec2 defines the centre but from right or left depending  on alignment. */
final case class TextFixed(str: String, fontSize: Double, xPosn: Double, yPosn: Double, colour: Colour, textAlign: TextAlign, baseLine: BaseLine) extends
TextGraphic with GraphicAffineElem with GraphicSvgElem
{ type ThisT = TextFixed
  override def ptsTrans(f: Pt2 => Pt2) = TextFixed(str, fontSize, f(posn), colour, textAlign, baseLine)
  override def svgElem: SvgElem = SvgText.xy(xPosn, yPosn, str, textAlign, colour)
}

object TextFixed
{
  def apply(str: String, fontSize: Double = 24, posn: Pt2 = Pt2Z, colour: Colour = Black, align: TextAlign = CenAlign, baseLine: BaseLine = BaseLine.Middle):
    TextFixed = new TextFixed(str, fontSize, posn.x, posn.y, colour, align, baseLine)

  def xy(str: String, fontSize: Double = 24, xPosn: Double, yPosn: Double, colour: Colour = Black, align: TextAlign = CenAlign,
    baseLine: BaseLine = BaseLine.Middle) = new TextFixed(str, fontSize, xPosn, yPosn, colour, align, baseLine)

  def lines(strs: StrArr, fontSize: Double = 24, posn: Pt2 = Pt2Z, fontColour: Colour = Black, lineSpacing: Double = 1, align: TextAlign = CenAlign,
    baseLine: BaseLine = BaseLine.Alphabetic): RArr[TextFixed] =
  { val len = strs.length
    if(len == 0) RArr()
    else strs.iMap((i, str) => TextFixed(str, fontSize, posn.slateY(((len -1) / 2.0 - i) * fontSize * lineSpacing), fontColour, align, baseLine))
  }
}

/** A text Graphic aligned with the X and Y axes, but with a scaled font. */
final case class Textlign(str: String, fontSize: Double, xPosn: Double, yPosn: Double, colour: Colour, textAlign: TextAlign, baseLine: BaseLine) extends
TextGraphic
{ type ThisT = Textlign
  override def slate(operand: VecPt2): Textlign = copy(str, fontSize, xPosn + operand.x, yPosn + operand.y)
  override def slate(xOperand: Double, yOperand: Double): Textlign = copy(str, fontSize, xPosn + xOperand, yPosn + yOperand)
  override def slateX(xOperand: Double): Textlign = copy(str, fontSize, xPosn + xOperand, yPosn)
  override def slateY(yOperand: Double): Textlign = copy(str, fontSize, xPosn, yPosn + yOperand)
  override def scale(operand: Double): Textlign = copy(str, fontSize * operand, xPosn * operand, yPosn * operand)
  override def negY: Textlign = copy(str, fontSize, xPosn, -yPosn)
  override def negX: Textlign = copy(str, fontSize, -xPosn, yPosn)
  override def prolign(matrix: AxlignMatrix): Textlign = ???
  override def rotate90: Textlign = ???
  override def rotate180: Textlign = ???
  override def rotate270: Textlign = ???
  override def rotate(rotation: AngleVec): Textlign = ???
  override def reflect(lineLike: LineLike): Textlign = ???
  override def scaleXY(xOperand: Double, yOperand: Double): Textlign = ???
  override def shearX(operand: Double): Textlign = ???
  override def shearY(operand: Double): Textlign = ???
  override def svgElems: RArr[SvgElem] = ???
}

object Textlign
{
  def apply(str: String, fontSize: Double = 24, posn: Pt2 = Pt2Z, colour: Colour = Black, align: TextAlign = CenAlign,
    baseLine: BaseLine = BaseLine.Middle): Textlign =
    new Textlign(str, fontSize, posn.x, posn.y, colour, align, baseLine)

  def xy(str: String, fontSize: Double = 24, xPosn: Double, yPosn: Double, colour: Colour = Black, align: TextAlign = CenAlign,
    baseLine: BaseLine = BaseLine.Middle) =
    new Textlign(str, fontSize, xPosn, yPosn, colour, align, baseLine)

  def lines(strs: StrArr, fontSize: Double = 24, posn: Pt2 = Pt2Z, fontColour: Colour = Black, lineSpacing: Double = 1,
    align: TextAlign = CenAlign, baseLine: BaseLine = BaseLine.Alphabetic): RArr[Textlign] =
  { val len = strs.length
    if(len == 0) RArr()
    else strs.iMap((i, str) => Textlign(str, fontSize, posn.slateY(((len -1) / 2.0 - i) * fontSize * lineSpacing), fontColour, align, baseLine))
  }
}