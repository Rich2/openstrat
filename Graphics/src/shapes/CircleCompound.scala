/* Copyright 2018-20 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat
package geom
import pWeb._

/** Compound Circle Graphic class. */
case class CircleCompound(shape: Circle, facets: Arr[GraphicFacet], children: Arr[ShapeCompound] = Arr()) extends EllipseCompound with CircleGraphic
{
  override def attribs: Arr[XmlAtt] = ???

  override def rendToCanvas(cp: pCanv.CanvasPlatform): Unit = facets.foreach
  { case FillFacet(c) => cp.circleFill(shape, c)
    case DrawFacet(w, c) => cp.circleDraw(shape, w, c)
    case fr: FillRadial => cp.circleFillRadial(shape, fr)  
    case sf => deb("Unrecognised ShapeFacet: " + sf.toString)
  }

  override def svgElem(bounds: BoundingRect): SvgCircle = SvgCircle(shape.reflectX.slate(0, bounds.minY + bounds.maxY).
    attribs ++ facets.flatMap(_.attribs))  
  
  /** Translate geometric transformation. */
  override def slate(offset: Vec2): CircleCompound = CircleCompound(shape.slate(offset), facets, children.slate(offset))

  /** Translate geometric transformation. */
  override def slate(xOffset: Double, yOffset: Double): CircleCompound =
    CircleCompound(shape.slate(xOffset, yOffset), facets, children.slate(xOffset, yOffset))

  /** Uniform scaling transformation. The scale name was chosen for this operation as it is normally the desired operation and preserves Circles and
   * Squares. Use the xyScale method for differential scaling. */
  override def scale(operand: Double): CircleCompound = CircleCompound(shape.scale(operand), facets, children.scale(operand))

  /** Mirror, reflection transformation across the X axis. This method has been left abstract in GeomElemNew to allow the return type to be narrowed
   * in sub classes. */
  override def reflectX: CircleCompound = CircleCompound(shape.reflectX, facets, children.reflectX)

  /** Mirror, reflection transformation across the X axis. This method has been left abstract in GeomElemNew to allow the return type to be narrowed
   * in sub classes. */
  override def reflectY: CircleCompound = CircleCompound(shape.reflectY, facets, children.reflectY)

  /** Mirror, reflection transformation across the line y = yOffset, which is parallel to the X axis. */
  override def reflectXOffset(yOffset: Double): CircleCompound = CircleCompound(shape.reflectXOffset(yOffset), facets, children.reflectXOffset(yOffset))

  /** Mirror, reflection transformation across the line x = xOffset, which is parallel to the X axis. */
  override def reflectYOffset(xOffset: Double): CircleCompound = CircleCompound(shape.reflectYOffset(xOffset), facets, children.reflectYOffset(xOffset))

  override def prolign(matrix: ProlignMatrix): CircleCompound = CircleCompound(shape.prolign(matrix), facets, children.prolign(matrix))

  override def rotateRadians(radians: Double): CircleCompound = CircleCompound(shape.rotateRadians(radians), facets, children.rotateRadians(radians))

  override def reflect(line: Line): CircleCompound = ??? //CircleGraphic(shape.reflect(line), facets, children.reflect(line))

  override def reflect(line: LineSeg): CircleCompound = ???

  override def xyScale(xOperand: Double, yOperand: Double): EllipseCompound = ???

  override def xShear(operand: Double): EllipseCompound = ???

  override def yShear(operand: Double): EllipseCompound = ???
}