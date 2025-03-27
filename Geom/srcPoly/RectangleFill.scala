/* Copyright 2018-25 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package geom

/** Graphic to fill a Rectangle with a single colour. */
trait RectangleFill extends PolygonFill with RectangleGraphicSimple
{
  /** Translate geometric transformation. */
  override def slateXY(xDelta: Double, yDelta: Double): RectangleFill = RectangleFill(shape.slateXY(xDelta, yDelta), fillFacet)

  /** Uniform scaling transformation. The scale name was chosen for this operation as it is normally the desired operation and preserves Circles and Squares.
   * Use the xyScale method for differential scaling. */
  override def scale(operand: Double): RectangleFill = RectangleFill(shape.scale(operand), fillFacet)

  /** Mirror, reflection transformation across the X axis. This method has been left abstract in GeomElemNew to allow the return type to be narrowed in
   * subclasses. */
  override def negY: RectangleFill = RectangleFill(shape.negY, fillFacet)

  /** Mirror, reflection transformation across the X axis. This method has been left abstract in GeomElemNew to allow the return type to be narrowed in
   * subclasses. */
  override def negX: RectangleFill = RectangleFill(shape.negX, fillFacet)

  override def rotate90: RectangleFill = RectangleFill(shape.rotate90, fillFacet)
  override def rotate180: RectangleFill = RectangleFill(shape.rotate180, fillFacet)
  override def rotate270: RectangleFill = RectangleFill(shape.rotate270, fillFacet)

  override def prolign(matrix: ProlignMatrix): RectangleFill = RectangleFill(shape.prolign(matrix), fillFacet)

  override def rotate(angle: AngleVec): RectangleFill = RectangleFill(shape.rotate(angle), fillFacet)

  override def reflect(lineLike: LineLike): RectangleFill = RectangleFill(shape.reflect(lineLike), fillFacet)

  override def scaleXY(xOperand: Double, yOperand: Double): RectangleFill = RectangleFill(shape.scaleXY(xOperand, yOperand), fillFacet)
}

/** Companion object for RectangleFill, contains an Implementation class [[RectangleFill.RectangleFillImp]] and an apply factor method that delegates to it. It
 * also contains implicit instances for 2D geometric transformations. */
object RectangleFill
{
  def apply(shape: Rectangle, fillFacet: FillFacet): RectangleFill = RectangleFillImp(shape, fillFacet)

  implicit val slateImplicit: SlateXY[RectangleFill] = (obj: RectangleFill, dx: Double, dy: Double) => obj.slateXY(dx, dy)
  implicit val scaleImplicit: Scale[RectangleFill] = (obj: RectangleFill, operand: Double) => obj.scale(operand)
  implicit val rotateImplicit: Rotate[RectangleFill] = (obj: RectangleFill, angle: AngleVec) => obj.rotate(angle)
  implicit val prolignImplicit: Prolign[RectangleFill] = (obj, matrix) => obj.prolign(matrix)

  implicit val reflectAxesImplicit: TransAxes[RectangleFill] = new TransAxes[RectangleFill]
  { override def negYT(obj: RectangleFill): RectangleFill = obj.negY
    override def negXT(obj: RectangleFill): RectangleFill = obj.negX
    override def rotate90(obj: RectangleFill): RectangleFill = obj.rotate90
    override def rotate180(obj: RectangleFill): RectangleFill = obj.rotate180
    override def rotate270(obj: RectangleFill): RectangleFill = obj.rotate270
  }

  /** Implementation class for the general case of a [[RectangleFill]]. */
  case class RectangleFillImp(shape: Rectangle, fillFacet: FillFacet) extends RectangleFill
}