/* Copyright 2018-20 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat
package geom

/** A Polygon based graphic. If you just want a gneral polygon as opposed to specifically specified Polygons sucha Rectangle, Square or Trinagle use
 * the implementation class [[PolygonCompound]]. */
trait PolygonGraphic extends ShapeGraphic
{
  override def shape: Polygon
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
  override def reflectX: PolygonGraphic

  /** Mirror, reflection transformation across the X axis. This method has been left abstract in GeomElemNew to allow the return type to be narrowed
   * in sub classes. */
  override def reflectY: PolygonGraphic

  /** Mirror, reflection transformation across the line y = yOffset, which is parallel to the X axis. */
  override def reflectXOffset(yOffset: Double): PolygonGraphic

  /** Mirror, reflection transformation across the line x = xOffset, which is parallel to the X axis. */
  override def reflectYOffset(xOffset: Double): PolygonGraphic

  override def prolign(matrix: ProlignMatrix): PolygonGraphic
  
  override def rotateRadians(radians: Double): PolygonGraphic

  override def reflect(line: Line): PolygonGraphic

  override def xyScale(xOperand: Double, yOperand: Double): PolygonGraphic

  override def xShear(operand: Double): PolygonGraphic

  override def yShear(operand: Double): PolygonGraphic

  override def reflect(line: LineSeg): PolygonGraphic
}
