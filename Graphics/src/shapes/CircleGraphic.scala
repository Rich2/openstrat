/* Copyright 2018-20 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat
package geom

case class CircleGraphic(shape: Circle, facets: Arr[ShapeFacet], children: Arr[ShapeGraphic] = Arr()) extends EllipseGraphic
{
  override def rendToCanvas(cp: pCanv.CanvasPlatform): Unit = facets.foreach
  { case FillColour(c) => cp.circleFill(shape, c)
    case CurveDraw(w, c) => cp.circleDraw(shape, w, c)
    case fr: FillRadial => cp.circleFillRadial(shape, fr)  
    case sf => deb("Unrecognised ShapeFacet: " + sf.toString)
  }

  override def svgElem(bounds: BoundingRect): SvgCircle = SvgCircle(shape.reflectX.slate(0, bounds.minY + bounds.maxY).
    shapeAttribs ++ facets.flatMap(_.attribs))  
  
  /** Translate geometric transformation. */
  override def slate(offset: Vec2): CircleGraphic = CircleGraphic(shape.slate(offset), facets, children.slate(offset))

  /** Translate geometric transformation. */
  override def slate(xOffset: Double, yOffset: Double): CircleGraphic =
    CircleGraphic(shape.slate(xOffset, yOffset), facets, children.slate(xOffset, yOffset))

  /** Uniform scaling transformation. The scale name was chosen for this operation as it is normally the desired operation and preserves Circles and
   * Squares. Use the xyScale method for differential scaling. */
  override def scale(operand: Double): CircleGraphic = CircleGraphic(shape.scale(operand), facets, children.scale(operand))

  /** Mirror, reflection transformation across the X axis. This method has been left abstract in GeomElemNew to allow the return type to be narrowed
   * in sub classes. */
  override def reflectX: CircleGraphic = CircleGraphic(shape.reflectX, facets, children.reflectX)

  /** Mirror, reflection transformation across the X axis. This method has been left abstract in GeomElemNew to allow the return type to be narrowed
   * in sub classes. */
  override def reflectY: CircleGraphic = CircleGraphic(shape.reflectY, facets, children.reflectY)

  /** Mirror, reflection transformation across the line y = yOffset, which is parallel to the X axis. */
  override def reflectXOffset(yOffset: Double): CircleGraphic = CircleGraphic(shape.reflectXOffset(yOffset), facets, children.reflectXOffset(yOffset))

  /** Mirror, reflection transformation across the line x = xOffset, which is parallel to the X axis. */
  override def reflectYOffset(xOffset: Double): CircleGraphic = CircleGraphic(shape.reflectYOffset(xOffset), facets, children.reflectYOffset(xOffset))

  override def prolign(matrix: ProlignMatrix): CircleGraphic = CircleGraphic(shape.prolign(matrix), facets, children.prolign(matrix))

  /** Rotates 90 degrees or Pi/2 radians anticlockwise. */
  /*override def rotate90: CircleGraphic = CircleGraphic(shape.rotate90, facets, children.rotate90)

  /** Rotates 180 degrees or Pi radians. */
  override def rotate180: CircleGraphic = CircleGraphic(shape.rotate180, facets, children.rotate180)

  /** Rotates 90 degrees or Pi/2 radians clockwise. */
  override def rotate270: CircleGraphic = CircleGraphic(shape.rotate270, facets, children.rotate270)*/

  override def rotateRadians(radians: Double): CircleGraphic = CircleGraphic(shape.rotateRadians(radians), facets, children.rotateRadians(radians))

  override def reflect(line: Line): CircleGraphic = ??? //CircleGraphic(shape.reflect(line), facets, children.reflect(line))

  override def reflect(line: LineSeg): CircleGraphic = ???

  override def xyScale(xOperand: Double, yOperand: Double): EllipseGraphic = ???

  override def xShear(operand: Double): EllipseGraphic = ???

  override def yShear(operand: Double): EllipseGraphic = ???
}