/* Copyright 2018-20 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat
package geom
import pWeb._

/** Compound Circle Graphic class. */
case class CircleCompound(shape: Circle, facets: Arr[GraphicFacet], children: Arr[GraphicElem] = Arr()) extends EllipseCompound with CircleGraphic
{
  override def attribs: Arr[XmlAtt] = ???

  override def rendToCanvas(cp: pCanv.CanvasPlatform): Unit = facets.foreach
  { case c: Colour => cp.circleFill(CircleFill(shape, c))
    case DrawFacet(c, w) => cp.circleDraw(shape.draw(c, w))
    case fr: FillRadial => cp.circleFillRadial(shape, fr)  
    case sf => deb("Unrecognised ShapeFacet: " + sf.toString)
  }

  override def svgElem(bounds: BoundingRect): SvgCircle = SvgCircle(shape.negY.xySlate(0, bounds.minY + bounds.maxY).
    attribs ++ facets.flatMap(_.attribs))  
  
  /** Translate geometric transformation. */
  override def slate(offset: Vec2Like): CircleCompound = CircleCompound(shape.slate(offset), facets, children.slate(offset))

  /** Translate geometric transformation. */
  override def xySlate(xOffset: Double, yOffset: Double): CircleCompound =
    CircleCompound(shape.xySlate(xOffset, yOffset), facets, children.xySlate(xOffset, yOffset))

  /** Uniform scaling transformation. The scale name was chosen for this operation as it is normally the desired operation and preserves Circles and
   * Squares. Use the xyScale method for differential scaling. */
  override def scale(operand: Double): CircleCompound = CircleCompound(shape.scale(operand), facets, children.scale(operand))

  /** Mirror, reflection 2D geometric transformation across the Y axis on a CircleCompound returns a CircleCompound. This transformation negates the X
   *  values.*/
  override def negX: CircleCompound = CircleCompound(shape.negX, facets, children.negX)

  /** Mirror, reflection 2D geometric transformation across the X axis on a CircleCompound returns a CircleCompound. This transformation negates the Y
   *  values. */
  override def negY: CircleCompound = CircleCompound(shape.negY, facets, children.negY)

  override def prolign(matrix: ProlignMatrix): CircleCompound = CircleCompound(shape.prolign(matrix), facets, children.prolign(matrix))

  override def rotate(angle: AngleVec): CircleCompound = CircleCompound(shape.rotate(angle), facets, children.rotate(angle))

  override def reflect(lineLike: LineLike): CircleCompound = ??? //CircleCompound(shape.reflect(lineLike), facets, children.reflect(lineLike))

  override def xyScale(xOperand: Double, yOperand: Double): EllipseCompound = ???

  override def xShear(operand: Double): EllipseCompound = ???

  override def yShear(operand: Double): EllipseCompound = ???

 // override def slateTo(newCen: Pt2): EllipseCompound = ???
}