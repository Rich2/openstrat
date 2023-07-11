/* Copyright 2018-20 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat
package geom

trait CanvShapeDraw extends ShapeDraw with CanvElem
{ /** Translate 2D geometric transformation on a CanvShapeDraw, returns a CanvShapeDraw. The Return type will be narrowed in sub traits / classes. This
   * overload might be removable in Scala 3, but is necessary for the time being die to type inference problems. */
  //override def slate(offset: Vec2Like): CanvShapeDraw

  /** Translate 2D geometric transformation on a CanvShapeDraw, returns a CanvShapeDraw. The Return type will be narrowed in sub traits / classes. */
  override def slateXY(xDelta: Double, yDelta: Double): CanvShapeDraw

  /** Uniform scaling 2D geometric transformation on a CanvShapeDraw, returns a CanvShapeDraw. The Return type will be narrowed in sub traits / classes.
   * The scale name was chosen for this operation as it is normally the desired operation and preserves [[Circle]]s and [[Square]]s. Use the xyScale
   * method for differential scaling on the X and Y axes. */
  override def scale(operand: Double): CanvShapeDraw

  /** Mirror, reflection 2D geometric transformation across the X axis on a CanvShapeDraw, returns a CanvShapeDraw. The Return type will be narrowed in
   * sub traits / classes. */
  override def negY: CanvShapeDraw

  /** Mirror, reflection 2D geometric transformation across the X axis on a CanvShapeDraw, returns a CanvShapeDraw. The Return type will be narrowed in
   * sub traits / classes. */
  override def negX: CanvShapeDraw

  /** 2D geometric transformation using a [[ProlignMatrix]] on a CanvShapeDraw, returns a CanvShapeDraw. The Return type will be narrowed in sub traits /
   * classes. */
  override def prolign(matrix: ProlignMatrix): CanvShapeDraw

  override def rotate90: CanvShapeDraw
  override def rotate180: CanvShapeDraw
  override def rotate270: CanvShapeDraw

  /** Rotation 2D geometric transformation on a CanvShapeDraw taking the rotation as a scalar measured in radians, returns a CanvShapeDraw. The Return
   * type will be narrowed in sub traits / classes. */
  override def rotate(angle: AngleVec): CanvShapeDraw

  /** Reflect 2D geometric transformation across a line, line segment or ray on a CanvShapeDraw, returns a CanvShapeDraw. The Return type will be narrowed
   * in sub traits / classes. */
  override def reflect(lineLike: LineLike): CanvShapeDraw

  /** XY scaling 2D geometric transformation on a CanvShapeDraw, returns a GrpahicElem. This allows different scaling factors across X and Y dimensions.
   * The return type will be narrowed in sub classes and traits. */
  override def scaleXY(xOperand: Double, yOperand: Double): CanvShapeDraw

  /** Shear 2D geometric transformation along the X Axis on a CanvShapeDraw, returns a CanvShapeDraw. The return type will be narrowed in sub classes and
   * traits. */
  override def shearX(operand: Double): CanvShapeDraw

  /** Shear 2D geometric transformation along the Y Axis on a CanvShapeDraw, returns a CanvShapeDraw. The return type will be narrowed in sub classes and
   * traits. */
  override def shearY(operand: Double): CanvShapeDraw
}