/* Copyright 2018-21 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat
package geom

/** ShapeFill element that is a [[CanvElem]] */
trait CanvShapeFill extends ShapeFill with CanvElem
{
  /** Translate 2D geometric transformation on a CanvShapeFill, returns a CanvShapeFill. The Return type will be narrowed in sub traits / classes. */
  override def slateXY(xDelta: Double, yDelta: Double): CanvShapeFill

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

  override def rotate90: CanvShapeFill
  override def rotate180: CanvShapeFill
  override def rotate270: CanvShapeFill

  /** Rotation 2D geometric transformation on a CanvShapeFill taking the rotation as a scalar measured in radians, returns a CanvShapeFill. The Return
   * type will be narrowed in sub traits / classes. */
  override def rotate(angle: AngleVec): CanvShapeFill

  /** Reflect 2D geometric transformation across a line, line segment or ray on a CanvShapeFill, returns a CanvShapeFill. The Return type will be narrowed
   * in sub traits / classes. */
  override def reflect(lineLike: LineLike): CanvShapeFill

  /** XY scaling 2D geometric transformation on a CanvShapeFill, returns a GrpahicElem. This allows different scaling factors across X and Y dimensions.
   * The return type will be narrowed in sub classes and traits. */
  override def scaleXY(xOperand: Double, yOperand: Double): CanvShapeFill

  /** Shear 2D geometric transformation along the X Axis on a CanvShapeFill, returns a CanvShapeFill. The return type will be narrowed in sub classes and
   * traits. */
  override def shearX(operand: Double): CanvShapeFill

  /** Shear 2D geometric transformation along the Y Axis on a CanvShapeFill, returns a CanvShapeFill. The return type will be narrowed in sub classes and
   * traits. */
  override def shearY(operand: Double): CanvShapeFill
}