/* Copyright 2018-20 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat
package geom
import pWeb._

case class RectGraphic(shape: Rect, facets: Arr[ShapeFacet], children: Arr[ShapeGraphic] = Arr()) extends PolygonGraphic
{
  /** Translate geometric transformation. */
  override def slate(offset: Vec2): RectGraphic = RectGraphic(shape.slate(offset), facets, children.slate(offset))

  /** Translate geometric transformation. */
  override def slate(xOffset: Double, yOffset: Double): PolygonGraphic =
    RectGraphic(shape.slate(xOffset, yOffset), facets, children.slate(xOffset, yOffset))

  /** Uniform scaling transformation. The scale name was chosen for this operation as it is normally the desired operation and preserves Circles and
   * Squares. Use the xyScale method for differential scaling. */
  override def scale(operand: Double): PolygonGraphic = ???

  /** Mirror, reflection transformation across the line x = xOffset, which is parallel to the X axis. */
  override def reflectYOffset(xOffset: Double): PolygonGraphic = ???

  /** Mirror, reflection transformation across the line y = yOffset, which is parallel to the X axis. */
  override def reflectXOffset(yOffset: Double): PolygonGraphic = ???

  /** Mirror, reflection transformation across the X axis. This method has been left abstract in GeomElemNew to allow the return type to be narrowed
   * in sub classes. */
  override def reflectX: PolygonGraphic = ???

  /** Mirror, reflection transformation across the X axis. This method has been left abstract in GeomElemNew to allow the return type to be narrowed
   * in sub classes. */
  override def reflectY: PolygonGraphic = ???

  override def prolign(matrix: ProlignMatrix): PolygonGraphic = ???

  /** Rotates 90 degrees or Pi/2 radians anticlockwise. */
  override def rotate90: PolygonGraphic = ???

  /** Rotates 180 degrees or Pi radians. */
  override def rotate180: PolygonGraphic = ???

  /** Rotates 90 degrees or Pi/2 radians clockwise. */
  override def rotate270: PolygonGraphic = ???

  override def rotateRadians(radians: Double): PolygonGraphic = ???

  override def reflect(line: Line): PolygonGraphic = ???

  override def scaleXY(xOperand: Double, yOperand: Double): PolygonGraphic = ???

  override def shearX(operand: Double): PolygonGraphic = ???

  override def shearY(operand: Double): PolygonGraphic = ???

  override def reflect(line: Sline): PolygonGraphic = ???

  override def svgElem(bounds: BoundingRect): SvgElem = ???
}