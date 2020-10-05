/* Copyright 2018-20 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat
package geom

/** A Polygon based graphic. If you just want a general polygon as opposed to specifically specified Polygons such as Rectangle, Square or Triangle
 *  use the implementation class [[PolygonCompound]]. */
trait PolygonGraphic extends ShapeGraphic with BoundedGraphic
{
  override def shape: Polygon
  override def boundingRect: BoundingRect = shape.boundingRect
  override def svgElem(bounds: BoundingRect): SvgElem = ???
  /** Translate geometric transformation. */
  override def slate(offset: Vec2): PolygonGraphic

  /** Translate geometric transformation. */
  override def slate(xOffset: Double, yOffset: Double): PolygonGraphic

  /** Uniform scaling transformation. The scale name was chosen for this operation as it is normally the desired operation and preserves Circles and
   * Squares. Use the xyScale method for differential scaling. */
  override def scale(operand: Double): PolygonGraphic  

  /** Mirror, reflection transformation across the X axis. This method has been left abstract in GeomElemNew to allow the return type to be narrowed
   * in sub classes. */
  override def negY: PolygonGraphic

  /** Mirror, reflection transformation across the X axis. This method has been left abstract in GeomElemNew to allow the return type to be narrowed
   * in sub classes. */
  override def negX: PolygonGraphic

  override def rotate90: PolygonGraphic

  override def rotate180: PolygonGraphic

  override def rotate270: PolygonGraphic

  override def prolign(matrix: ProlignMatrix): PolygonGraphic
  
  override def rotate(angle: Angle): PolygonGraphic

  override def reflect(lineLike: LineLike): PolygonGraphic

  override def xyScale(xOperand: Double, yOperand: Double): PolygonGraphic

  override def xShear(operand: Double): PolygonGraphic

  override def yShear(operand: Double): PolygonGraphic
}

object PolygonGraphic
{
  implicit val slateImplicit: Slate[PolygonGraphic] = (obj: PolygonGraphic, offset: Vec2) => obj.slate(offset)
  implicit val scaleImplicit: Scale[PolygonGraphic] = (obj: PolygonGraphic, operand: Double) => obj.scale(operand)
  implicit val rotateImplicit: Rotate[PolygonGraphic] = (obj: PolygonGraphic, angle: Angle) => obj.rotate(angle)
  implicit val XYScaleImplicit: XYScale[PolygonGraphic] = (obj, xOperand, yOperand) => obj.xyScale(xOperand, yOperand)
  implicit val prolignImplicit: Prolign[PolygonGraphic] = (obj, matrix) => obj.prolign(matrix)

  implicit val reflectAxesImplicit: TransAxes[PolygonGraphic] = new TransAxes[PolygonGraphic]
  { override def negYT(obj: PolygonGraphic): PolygonGraphic = obj.negY
    override def negXT(obj: PolygonGraphic): PolygonGraphic = obj.negX
    override def rotate90T(obj: PolygonGraphic): PolygonGraphic = obj.rotate90
    override def rotate180T(obj: PolygonGraphic): PolygonGraphic = obj.rotate180
    override def rotate270T(obj: PolygonGraphic): PolygonGraphic = obj.rotate270
  }
}