/* Copyright 2018-20 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat
package geom
import pWeb._

case class CircleGraphic(shape: Circle, facets: Arr[ShapeFacet], children: Arr[ShapeGraphic] = Arr()) extends EllipseGraphic// with SimilarPreserve
{ /*override type ThisT = CircleDisplay */
  override def rendToCanvas(cp: pCanv.CanvasPlatform): Unit = facets.foreach
  { case FillColour(c) => cp.circleFill(shape, c)
    case CurveDraw(w, c) => cp.circleDraw(shape, w, c)
    case fr: FillRadial => cp.circleFillRadial(shape, fr)  
    case sf => deb("Unrecognised ShapeFacet: " + sf.toString)
  }

  override def svgElem: SvgCircle = SvgCircle(shape.reflectX.slate(0, shape.boundingRect.minY + shape.boundingRect.maxY).
    shapeAttribs ++ facets.flatMap(_.attribs))  
  
  /** Translate geometric transformation. */
  override def slate(offset: Vec2): CircleGraphic = CircleGraphic(shape.slate(offset), facets, children.map(_.slate(offset)))

  /** Translate geometric transformation. */
  override def slate(xOffset: Double, yOffset: Double): CircleGraphic =
    CircleGraphic(shape.slate(xOffset, yOffset), facets, children.map(_.slate(xOffset, yOffset)))

  /** Uniform scaling transformation. The scale name was chosen for this operation as it is normally the desired operation and preserves Circles and
   * Squares. Use the xyScale method for differential scaling. */
  override def scale(operand: Double): CircleGraphic = CircleGraphic(shape.scale(operand), facets, children.map(_.scale(operand)))

  /** Mirror, reflection transformation across the line x = xOffset, which is parallel to the X axis. */
  override def reflectYOffset(xOffset: Double): CircleGraphic =
    CircleGraphic(shape.reflectYOffset(xOffset), facets, children.map(_.reflectYOffset(xOffset)))

  /** Mirror, reflection transformation across the line y = yOffset, which is parallel to the X axis. */
  override def reflectXOffset(yOffset: Double): CircleGraphic = ???

  /** Mirror, reflection transformation across the X axis. This method has been left abstract in GeomElemNew to allow the return type to be narrowed
   * in sub classes. */
  override def reflectX: CircleGraphic = ???

  /** Mirror, reflection transformation across the X axis. This method has been left abstract in GeomElemNew to allow the return type to be narrowed
   * in sub classes. */
  override def reflectY: CircleGraphic = CircleGraphic(shape.reflectY, facets, children.map(_.reflectY))

  override def prolign(matrix: ProlignMatrix): CircleGraphic = ???

  /** Rotates 90 degrees or Pi/2 radians anticlockwise. */
  override def rotate90: CircleGraphic = ???

  /** Rotates 180 degrees or Pi radians. */
  override def rotate180: CircleGraphic = ???

  /** Rotates 90 degrees or Pi/2 radians clockwise. */
  override def rotate270: CircleGraphic = ???

  override def rotateRadians(radians: Double): CircleGraphic = ???

  override def reflect(line: Line): CircleGraphic = ???

  override def reflect(line: Sline): CircleGraphic = ???

  override def scaleXY(xOperand: Double, yOperand: Double): EllipseGenGraphic = ???

  override def shearX(operand: Double): EllipseGenGraphic = ???

  override def shearY(operand: Double): EllipseGenGraphic = ???
}