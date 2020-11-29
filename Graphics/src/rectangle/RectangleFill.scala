/* Copyright 2018-20 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat
package geom

/** Graphic to fill a Rectangle with a single colour. */
trait RectangleFill extends PolygonFill with RectangleGraphicSimple
{
  /** Translate geometric transformation. */
  override def slate(xOffset: Double, yOffset: Double): RectangleFill = RectangleFill(shape.slate(xOffset, yOffset), fill)

  /** Translate geometric transformation. */
  override def slate(offset: Vec2Like): RectangleFill = RectangleFill(shape.slate(offset), fill)

  /** Uniform scaling transformation. The scale name was chosen for this operation as it is normally the desired operation and preserves Circles and
   * Squares. Use the xyScale method for differential scaling. */
  override def scale(operand: Double): RectangleFill = RectangleFill(shape.scale(operand), fill)

  /** Mirror, reflection transformation across the X axis. This method has been left abstract in GeomElemNew to allow the return type to be narrowed
   * in sub classes. */
  override def negY: RectangleFill = RectangleFill(shape.negY, fill)

  /** Mirror, reflection transformation across the X axis. This method has been left abstract in GeomElemNew to allow the return type to be narrowed
   * in sub classes. */
  override def negX: RectangleFill = RectangleFill(shape.negX, fill)

  override def prolign(matrix: ProlignMatrix): RectangleFill = RectangleFill(shape.prolign(matrix), fill)

  override def rotate(angle: Angle): RectangleFill = RectangleFill(shape.rotate(angle), fill)

  override def reflect(lineLike: LineLike): RectangleFill = RectangleFill(shape.reflect(lineLike), fill)

  override def xyScale(xOperand: Double, yOperand: Double): RectangleFill = RectangleFill(shape.xyScale(xOperand, yOperand), fill)

  override def slateTo(newCen: Pt2): RectangleFill = ???
}

/** Companion object for RectangleFill, contains an Implementation class [[RectangleFill.RectangleFillImp]] and an apply factor method that delegates
 * to it. It also contains implicit instances for 2D geometric transformations. */
object RectangleFill
{
  def apply(shape: Rectangle, fillFacet: FillFacet): RectangleFill = RectangleFillImp(shape, fillFacet)

  implicit val slateImplicit: Slate[RectangleFill] = (obj: RectangleFill, dx: Double, dy: Double) => obj.slate(dx, dy)
  implicit val scaleImplicit: Scale[RectangleFill] = (obj: RectangleFill, operand: Double) => obj.scale(operand)
  implicit val rotateImplicit: Rotate[RectangleFill] = (obj: RectangleFill, angle: Angle) => obj.rotate(angle)
  implicit val prolignImplicit: Prolign[RectangleFill] = (obj, matrix) => obj.prolign(matrix)

  implicit val reflectAxesImplicit: ReflectAxes[RectangleFill] = new ReflectAxes[RectangleFill]
  { override def negYT(obj: RectangleFill): RectangleFill = obj.negY
    override def negXT(obj: RectangleFill): RectangleFill = obj.negX
  }

  /** Implementatin class for the general case of a [[RectangleFill]]. */
  case class RectangleFillImp(shape: Rectangle, fill: FillFacet) extends RectangleFill
}