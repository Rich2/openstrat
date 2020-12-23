/* Copyright 2018-20 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat
package geom
import pCanv._, Colour.Black

case class ShapeGenDraw(shape: ShapeGen, lineColour: Colour = Black, lineWidth: Double = 2) extends CanvElem
{
  /** Renders this functional immutable GraphicElem, using the imperative methods of the abstract [[pCanv.CanvasPlatform]] interface. */
  override def rendToCanvas(cp: CanvasPlatform): Unit = { deb("Not implemented.")}

  /** Translate 2D geometric transformation on a ShapeGenDraw, returns a ShapeGenDraw. The Return type will be narrowed in sub traits / classes. */
  override def xySlate(xOffset: Double, yOffset: Double): ShapeGenDraw = ShapeGenDraw(shape.xySlate(xOffset, yOffset), lineColour, lineWidth)

  /** Translate 2D geometric transformation on a ShapeGenDraw, returns a ShapeGenDraw. The Return type will be narrowed in sub traits / classes. This
   * overload might be removable in Scala 3, but is necessary for the time being die to type inference problems. */
  //override def slate(offset: Vec2Like): ShapeGenDraw = ShapeGenDraw(shape.slate(offset), lineColour, lineWidth)

  /** Uniform scaling 2D geometric transformation on a ShapeGenDraw, returns a ShapeGenDraw. The Return type will be narrowed in sub traits / classes.
   * The scale name was chosen for this operation as it is normally the desired operation and preserves [[Circle]]s and [[Square]]s. Use the xyScale
   * method for differential scaling on the X and Y axes. */
  override def scale(operand: Double): ShapeGenDraw = ShapeGenDraw(shape.scale(operand), lineColour, lineWidth)

  /** Mirror, reflection 2D geometric transformation across the X axis on a ShapeGenDraw, returns a ShapeGenDraw. The Return type will be narrowed in
   * sub traits / classes. */
  override def negY: ShapeGenDraw = ShapeGenDraw(shape.negY, lineColour, lineWidth)

  /** Mirror, reflection 2D geometric transformation across the X axis on a ShapeGenDraw, returns a ShapeGenDraw. The Return type will be narrowed in
   * sub traits / classes. */
  override def negX: ShapeGenDraw = ShapeGenDraw(shape.negX, lineColour, lineWidth)

  /** 2D geometric transformation using a [[ProlignMatrix]] on a ShapeGenDraw, returns a ShapeGenDraw. The Return type will be narrowed in sub traits /
   * classes. */
  override def prolign(matrix: ProlignMatrix): ShapeGenDraw = ShapeGenDraw(shape.prolign((matrix)), lineColour, lineWidth)

  /** Rotation 2D geometric transformation on a ShapeGenDraw taking the rotation as a scalar measured in radians, returns a ShapeGenDraw. The Return
   * type will be narrowed in sub traits / classes. */
  override def rotate(angle: AngleVec): ShapeGenDraw = ShapeGenDraw(shape.rotate(angle), lineColour, lineWidth)

  /** Reflect 2D geometric transformation across a line, line segment or ray on a ShapeGenDraw, returns a ShapeGenDraw. The Return type will be narrowed
   * in sub traits / classes. */
  override def reflect(lineLike: LineLike): ShapeGenDraw = ShapeGenDraw(shape.reflect(lineLike), lineColour, lineWidth)

  /** XY scaling 2D geometric transformation on a ShapeGenDraw, returns a GrpahicElem. This allows different scaling factors across X and Y dimensions.
   * The return type will be narrowed in sub classes and traits. */
  override def xyScale(xOperand: Double, yOperand: Double): ShapeGenDraw = ShapeGenDraw(shape.xyScale(xOperand, yOperand), lineColour, lineWidth)

  /** Shear 2D geometric transformation along the X Axis on a ShapeGenDraw, returns a ShapeGenDraw. The return type will be narrowed in sub classes and
   * traits. */
  override def xShear(operand: Double): ShapeGenDraw = ShapeGenDraw(shape.xShear(operand), lineColour, lineWidth)

  /** Shear 2D geometric transformation along the Y Axis on a ShapeGenDraw, returns a ShapeGenDraw. The return type will be narrowed in sub classes and
   * traits. */
  override def yShear(operand: Double): ShapeGenDraw = ShapeGenDraw(shape.yShear(operand), lineColour, lineWidth)
}