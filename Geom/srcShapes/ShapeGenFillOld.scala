/* Copyright 2018-21 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package geom
import pgui._

/** To be phased out. */
final case class ShapeGenFillOld(shape: ShapeGenOld, colour: Colour) extends CanvElem with AxisFree
{
  override type ThisT = ShapeGenFillOld
  /** Renders this functional immutable GraphicElem, using the imperative methods of the abstract [[pCanv.CanvasPlatform]] interface. */
  override def rendToCanvas(cp: CanvasPlatform): Unit = { deb("Not implemented.")}

  /** Translate 2D geometric transformation on a ShapeGenFill, returns a ShapeGenFill. The Return type will be narrowed in sub traits / classes. */
  override def slateXY(xDelta: Double, yDelta: Double): ShapeGenFillOld = ShapeGenFillOld(shape.slateXY(xDelta, yDelta), colour)

  /** Uniform scaling 2D geometric transformation on a ShapeGenFill, returns a ShapeGenFill. The Return type will be narrowed in sub traits / classes.
   * The scale name was chosen for this operation as it is normally the desired operation and preserves [[Circle]]s and [[Square]]s. Use the xyScale
   * method for differential scaling on the X and Y axes. */
  override def scale(operand: Double): ShapeGenFillOld = ShapeGenFillOld(shape.scale(operand), colour)

  /** 2D geometric transformation using a [[ProlignMatrix]] on a ShapeGenFill, returns a ShapeGenFill. The Return type will be narrowed in sub traits /
   * classes. */
  override def prolign(matrix: ProlignMatrix): ShapeGenFillOld = ShapeGenFillOld(shape.prolign(matrix), colour)

  /** Rotation 2D geometric transformation on a ShapeGenFill taking the rotation as a scalar measured in radians, returns a ShapeGenFill. The Return
   * type will be narrowed in sub traits / classes. */
  override def rotate(angle: AngleVec): ShapeGenFillOld = ShapeGenFillOld(shape.rotate(angle), colour)

  /** Reflect 2D geometric transformation across a line, line segment or ray on a ShapeGenFill, returns a ShapeGenFill. The Return type will be narrowed
   * in sub traits / classes. */
  override def reflect(lineLike: LineLike): ShapeGenFillOld = ShapeGenFillOld(shape.reflect(lineLike), colour)

  /** XY scaling 2D geometric transformation on a ShapeGenFill, returns a GrpahicElem. This allows different scaling factors across X and Y dimensions.
   * The return type will be narrowed in sub classes and traits. */
  override def scaleXY(xOperand: Double, yOperand: Double): ShapeGenFillOld = ???

  /** Shear 2D geometric transformation along the X Axis on a ShapeGenFill, returns a ShapeGenFill. The return type will be narrowed in sub classes and
   * traits. */
  override def shearX(operand: Double): ShapeGenFillOld = ???

  /** Shear 2D geometric transformation along the Y Axis on a ShapeGenFill, returns a ShapeGenFill. The return type will be narrowed in sub classes and
   * traits. */
  override def shearY(operand: Double): ShapeGenFillOld = ???
}
