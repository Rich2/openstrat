/* Copyright 2018-20 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat
package geom
import pWeb._

case class RectangleCompound(shape: Rectangle, facets: Arr[GraphicFacet], children: Arr[GraphicElem] = Arr()) extends ShapeCompound with RectangleGraphic
{
  override def attribs: Arr[XmlAtt] = ???

  override def svgStr: String = ???

  override def svgElem(bounds: BoundingRect): SvgRect = SvgRect(shape.reflectX.slate(0, bounds.minY + bounds.maxY).
    attribs ++ facets.flatMap(_.attribs))

  /** Translate geometric transformation. */
  override def slate(offset: Vec2): RectangleCompound = RectangleCompound(shape.slate(offset), facets, children.slate(offset))

  /** Translate geometric transformation. */
  override def slate(xOffset: Double, yOffset: Double): RectangleCompound =
    RectangleCompound(shape.slate(xOffset, yOffset), facets, children.slate(xOffset, yOffset))

  /** Uniform scaling transformation. The scale name was chosen for this operation as it is normally the desired operation and preserves Circles and
   * Squares. Use the xyScale method for differential scaling. */
  override def scale(operand: Double): RectangleCompound = RectangleCompound(shape.scale(operand), facets, children.scale(operand))
  
  /** Mirror, reflection transformation across the X axis. This method has been left abstract in GeomElemNew to allow the return type to be narrowed
   * in sub classes. */
  override def reflectX: RectangleCompound = RectangleCompound(shape.reflectX, facets, children.reflectX)

  /** Mirror, reflection transformation across the X axis. This method has been left abstract in GeomElemNew to allow the return type to be narrowed
   * in sub classes. */
  override def reflectY: RectangleCompound = RectangleCompound(shape.reflectY, facets, children.reflectY)
  
  /** Mirror, reflection transformation across the line y = yOffset, which is parallel to the X axis. */
  override def reflectXParallel(yOffset: Double): RectangleCompound = RectangleCompound(shape.reflectXParallel(yOffset), facets, children.reflectXOffset(yOffset))

  /** Mirror, reflection transformation across the line x = xOffset, which is parallel to the X axis. */
  override def reflectYParallel(xOffset: Double): RectangleCompound = RectangleCompound(shape.reflectYParallel(xOffset), facets, children.reflectYOffset(xOffset))

  override def prolign(matrix: ProlignMatrix): RectangleCompound = RectangleCompound(shape.prolign(matrix), facets, children.prolign(matrix))

  override def rotateRadians(radians: Double): RectangleCompound = RectangleCompound(shape.rotateRadians(radians), facets, children.rotateRadians(radians))

  override def reflect(line: Line): RectangleCompound = ???

  override def xyScale(xOperand: Double, yOperand: Double): PolygonCompound = ???

  override def xShear(operand: Double): PolygonCompound = ???

  override def yShear(operand: Double): PolygonCompound = ???

  override def reflect(line: LineSeg): RectangleCompound = ???
}