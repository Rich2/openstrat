/* Copyright 2018-20 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat
package geom

case class RectGraphic(shape: RectImplement, facets: Arr[ShapeFacet], children: Arr[ShapeGraphic] = Arr()) extends PolygonGraphic
{
  override def svgElem(bounds: BoundingRect): SvgRect = SvgRect(shape.reflectX.slate(0, bounds.minY + bounds.maxY).
    shapeAttribs ++ facets.flatMap(_.attribs))

  /** Translate geometric transformation. */
  override def slate(offset: Vec2): RectGraphic = RectGraphic(shape.slate(offset), facets, children.slate(offset))

  /** Translate geometric transformation. */
  override def slate(xOffset: Double, yOffset: Double): RectGraphic =
    RectGraphic(shape.slate(xOffset, yOffset), facets, children.slate(xOffset, yOffset))

  /** Uniform scaling transformation. The scale name was chosen for this operation as it is normally the desired operation and preserves Circles and
   * Squares. Use the xyScale method for differential scaling. */
  override def scale(operand: Double): RectGraphic = RectGraphic(shape.scale(operand), facets, children.scale(operand))
  
  /** Mirror, reflection transformation across the X axis. This method has been left abstract in GeomElemNew to allow the return type to be narrowed
   * in sub classes. */
  override def reflectX: RectGraphic = RectGraphic(shape.reflectX, facets, children.reflectX)

  /** Mirror, reflection transformation across the X axis. This method has been left abstract in GeomElemNew to allow the return type to be narrowed
   * in sub classes. */
  override def reflectY: RectGraphic = RectGraphic(shape.reflectY, facets, children.reflectY)
  
  /** Mirror, reflection transformation across the line y = yOffset, which is parallel to the X axis. */
  override def reflectXOffset(yOffset: Double): RectGraphic = RectGraphic(shape.reflectXOffset(yOffset), facets, children.reflectXOffset(yOffset))

  /** Mirror, reflection transformation across the line x = xOffset, which is parallel to the X axis. */
  override def reflectYOffset(xOffset: Double): RectGraphic = RectGraphic(shape.reflectYOffset(xOffset), facets, children.reflectYOffset(xOffset))

  override def prolign(matrix: ProlignMatrix): RectGraphic = RectGraphic(shape.prolign(matrix), facets, children.prolign(matrix))

  /** Rotates 90 degrees or Pi/2 radians anticlockwise. */
  override def rotate90: RectGraphic = RectGraphic(shape.rotate90, facets, children.rotate90)

  /** Rotates 180 degrees or Pi radians. */
  override def rotate180: RectGraphic = RectGraphic(shape.rotate180, facets, children.rotate180)

  /** Rotates 90 degrees or Pi/2 radians clockwise. */
  override def rotate270: RectGraphic = RectGraphic(shape.rotate270, facets, children.rotate270)

  override def rotateRadians(radians: Double): RectGraphic = RectGraphic(shape.rotateRadians(radians), facets, children.rotateRadians(radians))

  override def reflect(line: Line): PolygonGraphic = ???

  override def xyScale(xOperand: Double, yOperand: Double): PolygonGraphic = ???

  override def shearX(operand: Double): PolygonGraphic = ???

  override def shearY(operand: Double): PolygonGraphic = ???

  override def reflect(line: Sline): RectGraphic = ???

  
}