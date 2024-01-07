/* Copyright 2018-23 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package geom
import Colour.Black, pgui.CanvasPlatform, pWeb._

trait TextGraphic extends CanvElem{
  def str: String
  def fontSize: Double
  def xPosn: Double
  def yPosn: Double
  def colour: Colour
  def textAlign: TextAlign
  def baseLine: BaseLine

  override def slateXY(xDelta: Double, yDelta: Double): TextGraphic
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

  override def svgElem: SvgElem = SvgText(xPosn, yPosn, str, textAlign)
}

object TextFixed
{
  def apply(str: String, fontSize: Double = 24, posn: Pt2 = Pt2Z, colour: Colour = Black, align: TextAlign = CenAlign,
    baseLine: BaseLine = BaseLine.Middle): TextFixed =
    new TextFixed(str, fontSize, posn.x, posn.y, colour, align, baseLine)

  def xy(str: String, fontSize: Double = 24, xPosn: Double, yPosn: Double, colour: Colour = Black, align: TextAlign = CenAlign,
    baseLine: BaseLine = BaseLine.Middle) =
    new TextFixed(str, fontSize, xPosn, yPosn, colour, align, baseLine)

  def lines(strs: StrArr, fontSize: Double = 24, posn: Pt2 = Pt2Z, fontColour: Colour = Black, lineSpacing: Double = 1,
    align: TextAlign = CenAlign, baseLine: BaseLine = BaseLine.Alphabetic): RArr[TextFixed] =
  { val len = strs.length
    if(len == 0) RArr()
    else strs.iMap((i, str) => TextFixed(str, fontSize, posn.addY(((len -1) / 2.0 - i) * fontSize * lineSpacing), fontColour, align, baseLine))
  }
}

/** A text Graphic aligned with the X and Y axes, but with a scaled font. */
final case class Textlign(str: String, fontSize: Double, xPosn: Double, yPosn: Double, colour: Colour, textAlign: TextAlign, baseLine: BaseLine) extends
TextGraphic
{ type ThisT = Textlign

  /** Translate 2D geometric transformation on this [[Textlign]]. */
  override def slateXY(xDelta: Double, yDelta: Double): Textlign = copy(str, fontSize, xPosn + xDelta, yPosn + yDelta )

  /** Uniform scaling 2D geometric transformation on this [[Textlign]], returns a TextAligned. Scales the font size as well as the x and y
   *  positions. */
  override def scale(operand: Double): Textlign = copy(str, fontSize * operand, xPosn * operand, yPosn * operand)

  /** Mirror, reflection 2D geometric transformation across the X axis on a TextAligned, returns a TextAligned. The Return type will be narrowed in
   * sub traits / classes. */
  override def negY: Textlign = copy(str, fontSize, xPosn, -yPosn)

  /** Mirror, reflection 2D geometric transformation across the X axis on a TextAligned, returns a TextAligned. The Return type will be narrowed in
   * sub traits / classes. */
  override def negX: Textlign = copy(str, fontSize, -xPosn, yPosn)

  /** 2D geometric transformation using a [[ProlignMatrix]] on a TextAligned, returns a TextAligned. The Return type will be narrowed in sub traits /
   * classes. */
  override def prolign(matrix: ProlignMatrix): Textlign = ???

  /** Rotation positive or anti clockwise 90 degrees, 2D geometric transformation on a TextAligned, returns a TextAligned. The return type will be
   * narrowed in sub classes and traits. */
  override def rotate90: Textlign = ???

  /** Rotation positive or anti clockwise 180 degrees, 2D geometric transformation on a TextAligned, returns a TextAligned. The return type will be
   * narrowed in sub classes and traits. */
  override def rotate180: Textlign = ???

  /** Rotation positive or anti clockwise 270 degrees, 2D geometric transformation on a TextAligned, returns a TextAligned. The return type will be
   * narrowed in sub classes and traits. */
  override def rotate270: Textlign = ???

  /** Rotation 2D geometric transformation on a TextAligned taking the rotation as a scalar measured in radians, returns a TextAligned. The Return
   * type will be narrowed in sub traits / classes. */
  override def rotate(angle: AngleVec): Textlign = ???

  /** Reflect 2D geometric transformation across a line, line segment or ray on a TextAligned, returns a TextAligned. The Return type will be narrowed
   * in sub traits / classes. */
  override def reflect(lineLike: LineLike): Textlign = ???

  /** XY scaling 2D geometric transformation on a TextAligned, returns a GrpahicElem. This allows different scaling factors across X and Y dimensions.
   * The return type will be narrowed in sub classes and traits. */
  override def scaleXY(xOperand: Double, yOperand: Double): Textlign = ???

  /** Shear 2D geometric transformation along the X Axis on a TextAligned, returns a TextAligned. The return type will be narrowed in sub classes and
   * traits. */
  override def shearX(operand: Double): Textlign = ???

  /** Shear 2D geometric transformation along the Y Axis on a TextAligned, returns a TextAligned. The return type will be narrowed in sub classes and
   * traits. */
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
    else strs.iMap((i, str) => Textlign(str, fontSize, posn.addY(((len -1) / 2.0 - i) * fontSize * lineSpacing), fontColour, align, baseLine))
  }
}