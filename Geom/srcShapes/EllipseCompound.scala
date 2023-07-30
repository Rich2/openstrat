/* Copyright 2018-23 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package geom
import pWeb._

/** Compound graphic trait for an ellipse. The final sub classes of this trait are [[CircleCompound]] and[[Ellipse.EllipseImp]]. */
trait EllipseCompound extends ShapeCompound with EllipseGraphic
{ /** Translate geometric transformation. */
  override def slateXY(xDelta: Double, yDelta: Double): EllipseCompound

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

  override def scaleXY(xOperand: Double, yOperand: Double): EllipseCompound

  override def shearX(operand: Double): EllipseCompound

  override def shearY(operand: Double): EllipseCompound
}

/** Companion object for the [[EllipseCompound]] trait contains factory apply method and implicit instances for the 2D geometric transformations.  */
object EllipseCompound
{
  def apply(shape: Ellipse, facets: RArr[GraphicFacet], children: RArr[GraphicElem] = RArr()): EllipseCompound =
    new EllipseCompoundImplement(shape, facets, children)

  /** The implementation class for a general ellipse that is not defined as a circle. Most users will not need to interact with this class. It been
   * created non anonymously because the type might be useful for certain specialised performance usecases. */
  final case class EllipseCompoundImplement(shape: Ellipse, facets: RArr[GraphicFacet], children: RArr[GraphicElem] = RArr()) extends
    EllipseCompound with AxisFree
  {
    override type ThisT = EllipseCompoundImplement
    override def attribs: RArr[XmlAtt] = ???

    /** Return type narrowed to [[SvgEllipse]] from [[SvgElem]] */
/*    override def svgElem: SvgEllipse =
    { val newEllipse = shape.negY.slateXY(0, boundingRect.bottom + boundingRect.top)
      val newAtts = newEllipse.attribs
      val atts2 = if (shape.alignAngle == 0.degs) newAtts else newAtts +% SvgRotate(- shape.alignAngle.degs, shape.cenX, shape.cenY)
      SvgEllipse(atts2 ++ facets.flatMap(_.attribs))
    }*/

    override def rendToCanvas(cp: pgui.CanvasPlatform): Unit = facets.foreach {
      case c: Colour => cp.ellipseFill(EllipseFill(shape, c))
      //case CurveDraw(w, c) => cp.circleDraw(shape, w, c)
      //case fr: FillRadial => cp.circleFillRadial(shape, fr)*/
      case sf => deb("Unrecognised ShapeFacet: " + sf.toString)
    }

    /** Translate geometric transformation. */
    override def slateXY(xDelta: Double, yDelta: Double): EllipseCompoundImplement =
      EllipseCompoundImplement(shape.slateXY(xDelta, yDelta), facets, children.SlateXY(xDelta, yDelta))

    /** Uniform scaling transformation. The scale name was chosen for this operation as it is normally the desired operation and preserves Circles and
     * Squares. Use the xyScale method for differential scaling. */
    override def scale(operand: Double): EllipseCompoundImplement = EllipseCompoundImplement(shape.scale(operand), facets, children.scale(operand))

    override def prolign(matrix: ProlignMatrix): EllipseCompoundImplement = EllipseCompoundImplement(shape.prolign(matrix), facets, children.prolign(matrix))

    override def rotate(angle: AngleVec): EllipseCompoundImplement = EllipseCompoundImplement(shape.rotate(angle), facets, children.rotate(angle))

    override def reflect(lineLike: LineLike): EllipseCompoundImplement = ??? //EllipseGenGraphic(shape.reflect(line), facets, children.reflect(line))

    override def scaleXY(xOperand: Double, yOperand: Double): EllipseCompoundImplement = ???

    override def shearX(operand: Double): EllipseCompoundImplement = ???

    override def shearY(operand: Double): EllipseCompoundImplement = ???
    //override def slateTo(newCen: Pt2): EllipseCompoundImplement = ???
  }
}