/* Copyright 2018-23 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package geom
import Colour.Black, pgui.CanvasPlatform, pWeb._

/** Text graphic fixed in font size and orientation.
 * @param posn The point to orient from. By default this Vec2 defines the centre but from right or left depending  on alignment. */
final case class TextFixed(str: String, fontSize: Double, xPosn: Double, yPosn: Double, colour: Colour, textAlign: TextAlign, baseLine: BaseLine) extends
GraphicAffineElem with CanvElem with GraphicSvgElem
{ type ThisT = TextFixed
  def posn: Pt2 = Pt2(xPosn, yPosn)
  override def ptsTrans(f: Pt2 => Pt2) = TextFixed(str, fontSize, f(posn), colour, textAlign, baseLine)
  override def rendToCanvas(cp: pgui.CanvasPlatform): Unit = cp.textGraphic(this)

  override def svgElem: SvgElem = SvgText(xPosn, -yPosn, str, textAlign)
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
final case class TextAligned(str: String, fontSize: Double, xPosn: Double, yPosn: Double, colour: Colour, align: TextAlign, baseLine: BaseLine) extends
GraphicElem
{ type ThisT = TextAligned

  def posn: Pt2 = Pt2(xPosn, yPosn)

  /** Renders this functional immutable GraphicElem, using the imperative methods of the abstract [[pCanv.CanvasPlatform]] interface. */
  override def rendToCanvas(cp: CanvasPlatform): Unit = ???

  /** Translate 2D geometric transformation on a GraphicElem, returns a GraphicElem. The Return type will be narrowed in sub traits / classes. */
  override def slateXY(xDelta: Double, yDelta: Double): GraphicElem = ???

  /** Uniform scaling 2D geometric transformation on a GraphicElem, returns a GraphicElem. The Return type will be narrowed in sub traits / classes.
   * The scale name was chosen for this operation as it is normally the desired operation and preserves [[Circle]]s and [[Square]]s. Use the xyScale
   * method for differential scaling on the X and Y axes. */
  override def scale(operand: Double): GraphicElem = ???

  /** Mirror, reflection 2D geometric transformation across the X axis on a GraphicElem, returns a GraphicElem. The Return type will be narrowed in
   * sub traits / classes. */
  override def negY: GraphicElem = ???

  /** Mirror, reflection 2D geometric transformation across the X axis on a GraphicElem, returns a GraphicElem. The Return type will be narrowed in
   * sub traits / classes. */
  override def negX: GraphicElem = ???

  /** 2D geometric transformation using a [[ProlignMatrix]] on a GraphicElem, returns a GraphicElem. The Return type will be narrowed in sub traits /
   * classes. */
  override def prolign(matrix: ProlignMatrix): GraphicElem = ???

  /** Rotation positive or anti clockwise 90 degrees, 2D geometric transformation on a GraphicElem, returns a GraphicElem. The return type will be
   * narrowed in sub classes and traits. */
  override def rotate90: GraphicElem = ???

  /** Rotation positive or anti clockwise 180 degrees, 2D geometric transformation on a GraphicElem, returns a GraphicElem. The return type will be
   * narrowed in sub classes and traits. */
  override def rotate180: GraphicElem = ???

  /** Rotation positive or anti clockwise 270 degrees, 2D geometric transformation on a GraphicElem, returns a GraphicElem. The return type will be
   * narrowed in sub classes and traits. */
  override def rotate270: GraphicElem = ???

  /** Rotation 2D geometric transformation on a GraphicElem taking the rotation as a scalar measured in radians, returns a GraphicElem. The Return
   * type will be narrowed in sub traits / classes. */
  override def rotate(angle: AngleVec): GraphicElem = ???

  /** Reflect 2D geometric transformation across a line, line segment or ray on a GraphicElem, returns a GraphicElem. The Return type will be narrowed
   * in sub traits / classes. */
  override def reflect(lineLike: LineLike): GraphicElem = ???

  /** XY scaling 2D geometric transformation on a GraphicElem, returns a GrpahicElem. This allows different scaling factors across X and Y dimensions.
   * The return type will be narrowed in sub classes and traits. */
  override def scaleXY(xOperand: Double, yOperand: Double): GraphicElem = ???

  /** Shear 2D geometric transformation along the X Axis on a GraphicElem, returns a GraphicElem. The return type will be narrowed in sub classes and
   * traits. */
  override def shearX(operand: Double): GraphicElem = ???

  /** Shear 2D geometric transformation along the Y Axis on a GraphicElem, returns a GraphicElem. The return type will be narrowed in sub classes and
   * traits. */
  override def shearY(operand: Double): GraphicElem = ???
}