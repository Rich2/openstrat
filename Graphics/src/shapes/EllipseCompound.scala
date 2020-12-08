/* Copyright 2018-20 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat
package geom
import pWeb._

trait EllipseCompound extends ShapeCompound with EllipseGraphic
{ /** Translate geometric transformation. Translates this Ellipse Graphic into a modified EllipseGraphic. */
  override def slate(offset: Vec2Like): EllipseCompound

  /** Translate geometric transformation. */
  override def slate(xOffset: Double, yOffset: Double): EllipseCompound

  /** Uniform scaling transformation. The scale name was chosen for this operation as it is normally the desired operation and preserves Circles and
   * Squares. Use the xyScale method for differential scaling. */
  override def scale(operand: Double): EllipseCompound
 
  /** Mirror, reflection transformation across the X axis. This method has been left abstract in GeomElemNew to allow the return type to be narrowed
   * in sub classes. */
  override def negY: EllipseCompound

  /** Mirror, reflection transformation across the X axis. This method has been left abstract in GeomElemNew to allow the return type to be narrowed
   * in sub classes. */
  override def negX: EllipseCompound

  override def prolign(matrix: ProlignMatrix): EllipseCompound

  override def rotate(angle: AngleVec): EllipseCompound

  override def reflect(lineLike: LineLike): EllipseCompound

  override def xyScale(xOperand: Double, yOperand: Double): EllipseCompound

  override def xShear(operand: Double): EllipseCompound

  override def yShear(operand: Double): EllipseCompound
}

object EllipseCompound
{
  def apply(shape: Ellipse, facets: Arr[GraphicFacet], children: Arr[GraphicElem] = Arr()): EllipseCompound =
    new EllipseCompoundImplement(shape, facets, children)

  /** The implementation class for a general ellipse that is not defined as a circle. Most users will not need to interact with this class. It been
   * created non anonymously because the type might be useful for certain specialised performance usecases. */
  case class EllipseCompoundImplement(shape: Ellipse, facets: Arr[GraphicFacet], children: Arr[GraphicElem] = Arr()) extends EllipseCompound
  {
    override def attribs: Arr[XmlAtt] = ???

    override def svgStr: String = ???

    /** Return type narrowed to [[SvgEllipse]] from [[SvgElem]] */
    override def svgElem(bounds: BoundingRect): SvgEllipse =
    { //val bounds = shape.boundingRect
      val newEllipse = shape.negY.slate(0, bounds.minY + bounds.maxY)
      val newAtts = newEllipse.attribs
      val atts2 = if (shape.alignAngle == 0.degs) newAtts else newAtts +- SvgRotate(- shape.alignAngle.degs, shape.xCen, shape.yCen)
      SvgEllipse(atts2 ++ facets.flatMap(_.attribs))
    }

    override def rendToCanvas(cp: pCanv.CanvasPlatform): Unit = facets.foreach
    {
      case c: Colour => cp.ellipseFill(EllipseFill(shape, c))
      //case CurveDraw(w, c) => cp.circleDraw(shape, w, c)
      //case fr: FillRadial => cp.circleFillRadial(shape, fr)*/
      case sf => deb("Unrecognised ShapeFacet: " + sf.toString)
    }

    /** Translate geometric transformation. Translates this Ellipse Graphic into a modified EllipseGraphic. */
    override def slate(offset: Vec2Like): EllipseCompoundImplement = EllipseCompoundImplement(shape.slate(offset), facets, children.slate(offset))

    /** Translate geometric transformation. */
    override def slate(xOffset: Double, yOffset: Double): EllipseCompoundImplement =
      EllipseCompoundImplement(shape.slate(xOffset, yOffset), facets, children.slate(xOffset, yOffset))

    /** Uniform scaling transformation. The scale name was chosen for this operation as it is normally the desired operation and preserves Circles and
     * Squares. Use the xyScale method for differential scaling. */
    override def scale(operand: Double): EllipseCompoundImplement = EllipseCompoundImplement(shape.scale(operand), facets, children.scale(operand))

    /** Mirror, reflection transformation across the X axis. This method has been left abstract in GeomElemNew to allow the return type to be narrowed
     * in sub classes. */
    override def negY: EllipseCompoundImplement = EllipseCompoundImplement(shape.negY, facets, children.negY)

    /** Mirror, reflection transformation across the X axis. This method has been left abstract in GeomElemNew to allow the return type to be narrowed
     * in sub classes. */
    override def negX: EllipseCompoundImplement = EllipseCompoundImplement(shape.negX, facets, children.negX)

    override def prolign(matrix: ProlignMatrix): EllipseCompoundImplement = EllipseCompoundImplement(shape.prolign(matrix), facets, children.prolign(matrix))

    override def rotate(angle: AngleVec): EllipseCompoundImplement = EllipseCompoundImplement(shape.rotate(angle), facets, children.rotate(angle))

    override def reflect(lineLike: LineLike): EllipseCompoundImplement = ??? //EllipseGenGraphic(shape.reflect(line), facets, children.reflect(line))

    override def xyScale(xOperand: Double, yOperand: Double): EllipseCompoundImplement = ???

    override def xShear(operand: Double): EllipseCompoundImplement = ???

    override def yShear(operand: Double): EllipseCompoundImplement = ???
    //override def slateTo(newCen: Pt2): EllipseCompoundImplement = ???
  }
}