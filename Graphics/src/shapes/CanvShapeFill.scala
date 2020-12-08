/* Copyright 2018-20 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat
package geom

trait CanvShapeFill extends ShapeFill with CanvElem
{
  /** Translate 2D geometric transformation on a CanvShapeFill, returns a CanvShapeFill. The Return type will be narrowed in sub traits / classes. This
   * overload might be removeable in Scala 3, but is necessary for the time being die to type inference problems. */
  override def slate(offset: Vec2Like): CanvShapeFill

  /** Translate 2D geometric transformation on a CanvShapeFill, returns a CanvShapeFill. The Return type will be narrowed in sub traits / classes. */
  override def slate(xOffset: Double, yOffset: Double): CanvShapeFill

  /** Uniform scaling 2D geometric transformation on a CanvShapeFill, returns a CanvShapeFill. The Return type will be narrowed in sub traits / classes.
   * The scale name was chosen for this operation as it is normally the desired operation and preserves [[Circle]]s and [[Square]]s. Use the xyScale
   * method for differential scaling on the X and Y axes. */
  override def scale(operand: Double): CanvShapeFill

  /** Mirror, reflection 2D geometric transformation across the X axis on a CanvShapeFill, returns a CanvShapeFill. The Return type will be narrowed in
   * sub traits / classes. */
  override def negY: CanvShapeFill

  /** Mirror, reflection 2D geometric transformation across the X axis on a CanvShapeFill, returns a CanvShapeFill. The Return type will be narrowed in
   * sub traits / classes. */
  override def negX: CanvShapeFill

  /** 2D geometric transformation using a [[ProlignMatrix]] on a CanvShapeFill, returns a CanvShapeFill. The Return type will be narrowed in sub traits /
   * classes. */
  override def prolign(matrix: ProlignMatrix): CanvShapeFill

  /** Rotation 2D geometric transformation on a CanvShapeFill taking the rotation as a scalar measured in radians, returns a CanvShapeFill. The Return
   * type will be narrowed in sub traits / classes. */
  override def rotate(angle: AngleVec): CanvShapeFill

  /** Reflect 2D geometric transformation across a line, line segment or ray on a CanvShapeFill, returns a CanvShapeFill. The Return type will be narrowed
   * in sub traits / classes. */
  override def reflect(lineLike: LineLike): CanvShapeFill

  /** XY scaling 2D geometric transformation on a CanvShapeFill, returns a GrpahicElem. This allows different scaling factors across X and Y dimensions.
   * The return type will be narrowed in sub classes and traits. */
  override def xyScale(xOperand: Double, yOperand: Double): CanvShapeFill

  /** Shear 2D geometric transformation along the X Axis on a CanvShapeFill, returns a CanvShapeFill. The return type will be narrowed in sub classes and
   * traits. */
  override def xShear(operand: Double): CanvShapeFill

  /** Shear 2D geometric transformation along the Y Axis on a CanvShapeFill, returns a CanvShapeFill. The return type will be narrowed in sub classes and
   * traits. */
  override def yShear(operand: Double): CanvShapeFill
}