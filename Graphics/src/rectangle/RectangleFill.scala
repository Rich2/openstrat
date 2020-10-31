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

  override def slateTo(newCen: Vec2): RectangleFill = ???
}

object RectangleFill
{
  def apply(shape: Rectangle, colour: Colour): RectangleFill = RectangleFillImp(shape, colour)

  implicit val slateImplicit: Slate[RectangleFill] = (obj: RectangleFill, offset: Vec2) => obj.slate(offset)
  implicit val scaleImplicit: Scale[RectangleFill] = (obj: RectangleFill, operand: Double) => obj.scale(operand)
  implicit val rotateImplicit: Rotate[RectangleFill] = (obj: RectangleFill, angle: Angle) => obj.rotate(angle)
  implicit val prolignImplicit: Prolign[RectangleFill] = (obj, matrix) => obj.prolign(matrix)

  implicit val reflectAxesImplicit: ReflectAxes[RectangleFill] = new ReflectAxes[RectangleFill]
  { override def negYT(obj: RectangleFill): RectangleFill = obj.negY
    override def negXT(obj: RectangleFill): RectangleFill = obj.negX
    /*override def rotate90T(obj: RectangleFill): RectangleFill = obj.rotate90
    override def rotate180T(obj: RectangleFill): RectangleFill = obj.rotate180
    override def rotate270T(obj: RectangleFill): RectangleFill = obj.rotate270*/
  }

  case class RectangleFillImp(shape: Rectangle, colour: Colour) extends RectangleFill
}