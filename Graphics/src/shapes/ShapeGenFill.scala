/* Copyright 2018-20 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat
package geom
import pCanv._

case class ShapeGenFill(shape: ShapeGen, colour: Colour) extends CanvElem
{
  /** Renders this functional immutable GraphicElem, using the imperative methods of the abstract [[pCanv.CanvasPlatform]] interface. */
  override def rendToCanvas(cp: CanvasPlatform): Unit = { deb("Not implemented.")}

  /** Translate 2D geometric transformation on a ShapeGenFill, returns a ShapeGenFill. The Return type will be narrowed in sub traits / classes. */
  override def xySlate(xOffset: Double, yOffset: Double): ShapeGenFill = ShapeGenFill(shape.xySlate(xOffset, yOffset), colour)

  /** Translate 2D geometric transformation on a ShapeGenFill, returns a ShapeGenFill. The Return type will be narrowed in sub traits / classes. This
   * overload might be removeable in Scala 3, but is necessary for the time being die to type inference problems. */
  //override def slate(offset: Vec2Like): ShapeGenFill = ShapeGenFill(shape.slate(offset), colour)

  /** Uniform scaling 2D geometric transformation on a ShapeGenFill, returns a ShapeGenFill. The Return type will be narrowed in sub traits / classes.
   * The scale name was chosen for this operation as it is normally the desired operation and preserves [[Circle]]s and [[Square]]s. Use the xyScale
   * method for differential scaling on the X and Y axes. */
  override def scale(operand: Double): ShapeGenFill = ShapeGenFill(shape.scale(operand), colour)

  /** Mirror, reflection 2D geometric transformation across the X axis on a ShapeGenFill, returns a ShapeGenFill. The Return type will be narrowed in
   * sub traits / classes. */
  override def negY: ShapeGenFill = ShapeGenFill(shape.negY, colour)

  /** Mirror, reflection 2D geometric transformation across the X axis on a ShapeGenFill, returns a ShapeGenFill. The Return type will be narrowed in
   * sub traits / classes. */
  override def negX: ShapeGenFill = ShapeGenFill(shape.negX, colour)

  /** 2D geometric transformation using a [[ProlignMatrix]] on a ShapeGenFill, returns a ShapeGenFill. The Return type will be narrowed in sub traits /
   * classes. */
  override def prolign(matrix: ProlignMatrix): ShapeGenFill = ShapeGenFill(shape.prolign(matrix), colour)

  /** Rotation 2D geometric transformation on a ShapeGenFill taking the rotation as a scalar measured in radians, returns a ShapeGenFill. The Return
   * type will be narrowed in sub traits / classes. */
  override def rotate(angle: AngleVec): ShapeGenFill = ShapeGenFill(shape.rotate(angle), colour)

  /** Reflect 2D geometric transformation across a line, line segment or ray on a ShapeGenFill, returns a ShapeGenFill. The Return type will be narrowed
   * in sub traits / classes. */
  override def reflect(lineLike: LineLike): ShapeGenFill = ShapeGenFill(shape.reflect(lineLike), colour)

  /** XY scaling 2D geometric transformation on a ShapeGenFill, returns a GrpahicElem. This allows different scaling factors across X and Y dimensions.
   * The return type will be narrowed in sub classes and traits. */
  override def xyScale(xOperand: Double, yOperand: Double): ShapeGenFill = ???

  /** Shear 2D geometric transformation along the X Axis on a ShapeGenFill, returns a ShapeGenFill. The return type will be narrowed in sub classes and
   * traits. */
  override def xShear(operand: Double): ShapeGenFill = ???

  /** Shear 2D geometric transformation along the Y Axis on a ShapeGenFill, returns a ShapeGenFill. The return type will be narrowed in sub classes and
   * traits. */
  override def yShear(operand: Double): ShapeGenFill = ???
}
