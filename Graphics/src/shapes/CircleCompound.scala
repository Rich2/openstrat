/* Copyright 2018-20 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat
package geom
import pWeb._

/** Compound Circle Graphic class. */
case class CircleCompound(shape: Circle, facets: Arr[GraphicFacet], children: Arr[GraphicElem] = Arr()) extends EllipseCompound with CircleGraphic
{
  override def attribs: Arr[XmlAtt] = ???

  override def rendToCanvas(cp: pCanv.CanvasPlatform): Unit = facets.foreach
  { case FillFacet(c) => cp.circleFill(shape, c)
    case DrawFacet(w, c) => cp.circleDraw(shape, w, c)
    case fr: FillRadial => cp.circleFillRadial(shape, fr)  
    case sf => deb("Unrecognised ShapeFacet: " + sf.toString)
  }

  override def svgElem(bounds: BoundingRect): SvgCircle = SvgCircle(shape.negY.slate(0, bounds.minY + bounds.maxY).
    attribs ++ facets.flatMap(_.attribs))  
  
  /** Translate geometric transformation. */
  override def slate(offset: Vec2): CircleCompound = CircleCompound(shape.slate(offset), facets, children.slate(offset))

  /** Translate geometric transformation. */
  override def slate(xOffset: Double, yOffset: Double): CircleCompound =
    CircleCompound(shape.slate(xOffset, yOffset), facets, children.slate(xOffset, yOffset))

  /** Uniform scaling transformation. The scale name was chosen for this operation as it is normally the desired operation and preserves Circles and
   * Squares. Use the xyScale method for differential scaling. */
  override def scale(operand: Double): CircleCompound = CircleCompound(shape.scale(operand), facets, children.scale(operand))

  /** Mirror, reflection 2D geometric transformation across the Y axis on a CircleCompound returns a CircleCompound. This transformation negates the X
   *  values.*/
  override def negX: CircleCompound = CircleCompound(shape.negX, facets, children.negX)

  /** Mirror, reflection 2D geometric transformation across the X axis on a CircleCompound returns a CircleCompound. This transformation negates the Y
   *  values. */
  override def negY: CircleCompound = CircleCompound(shape.negY, facets, children.negY)

  /** Rotate 90 degrees anti clockwise or rotate 270 degrees clockwise 2D geometric transformation on a CircleCompound, returns a CircleCompound. */
  override def rotate90: CircleCompound = CircleCompound(shape.rotate90, facets, children.rotate90)

  /** Rotate 180 degrees 2D geometric transformation on a CircleCompound, returns a CircleCompound. */
  override def rotate180: CircleCompound =  CircleCompound(shape.rotate180, facets, children.rotate180)

  /** Rotate 270 degrees anti clockwise or rotate 90 degrees clockwise 2D geometric transformation on a CircleCompound, returns a CircleCompound. */
  override def rotate270: CircleCompound =  CircleCompound(shape.rotate270, facets, children.rotate270)

  override def prolign(matrix: ProlignMatrix): CircleCompound = CircleCompound(shape.prolign(matrix), facets, children.prolign(matrix))

  override def rotateRadians(radians: Double): CircleCompound = CircleCompound(shape.rotateRadians(radians), facets, children.rotateRadians(radians))

  override def reflect(lineLike: LineLike): CircleCompound = ??? //CircleCompound(shape.reflect(lineLike), facets, children.reflect(lineLike))

  override def xyScale(xOperand: Double, yOperand: Double): EllipseCompound = ???

  override def xShear(operand: Double): EllipseCompound = ???

  override def yShear(operand: Double): EllipseCompound = ???
}