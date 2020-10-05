/* Copyright 2018-20 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat
package geom

trait RectangleFill extends PolygonFill with RectangleGraphicSimple
{
  override def slate(offset: Vec2): RectangleFill = RectangleFill(shape.slate(offset), colour)

  /** Translate geometric transformation. */
  override def slate(xOffset: Double, yOffset: Double): RectangleFill = RectangleFill(shape.slate(xOffset, yOffset), colour)

  /** Uniform scaling transformation. The scale name was chosen for this operation as it is normally the desired operation and preserves Circles and
   * Squares. Use the xyScale method for differential scaling. */
  override def scale(operand: Double): RectangleFill = RectangleFill(shape.scale(operand), colour)

  /** Mirror, reflection transformation across the X axis. This method has been left abstract in GeomElemNew to allow the return type to be narrowed
   * in sub classes. */
  override def negY: RectangleFill = RectangleFill(shape.negY, colour)

  /** Mirror, reflection transformation across the X axis. This method has been left abstract in GeomElemNew to allow the return type to be narrowed
   * in sub classes. */
  override def negX: RectangleFill = RectangleFill(shape.negX, colour)

  override def prolign(matrix: ProlignMatrix): RectangleFill = RectangleFill(shape.prolign(matrix), colour)

  override def rotate(angle: Angle): RectangleFill = RectangleFill(shape.rotate(angle), colour)

  override def reflect(lineLike: LineLike): RectangleFill = RectangleFill(shape.reflect(lineLike), colour)

  override def xyScale(xOperand: Double, yOperand: Double): RectangleFill = RectangleFill(shape.xyScale(xOperand, yOperand), colour)
}

object RectangleFill
{
  def apply(shape: Rectangle, colour: Colour): RectangleFill = RectangleFillImp(shape, colour)

  case class RectangleFillImp(shape: Rectangle, colour: Colour) extends RectangleFill
}