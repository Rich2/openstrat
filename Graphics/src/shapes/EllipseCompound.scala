/* Copyright 2018-20 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat
package geom
import pWeb._

trait EllipseCompound extends ShapeCompound with EllipseGraphic
{ /** Translate geometric transformation. Translates this Ellipse Graphic into a modified EllipseGraphic. */
  override def slate(offset: Vec2): EllipseCompound

  /** Translate geometric transformation. */
  override def slate(xOffset: Double, yOffset: Double): EllipseCompound

  /** Uniform scaling transformation. The scale name was chosen for this operation as it is normally the desired operation and preserves Circles and
   * Squares. Use the xyScale method for differential scaling. */
  override def scale(operand: Double): EllipseCompound
 
  /** Mirror, reflection transformation across the X axis. This method has been left abstract in GeomElemNew to allow the return type to be narrowed
   * in sub classes. */
  override def reflectX: EllipseCompound

  /** Mirror, reflection transformation across the X axis. This method has been left abstract in GeomElemNew to allow the return type to be narrowed
   * in sub classes. */
  override def reflectY: EllipseCompound

  /** Mirror, reflection transformation across the line y = yOffset, which is parallel to the X axis. */
  override def reflectXOffset(yOffset: Double): EllipseCompound

  /** Mirror, reflection transformation across the line x = xOffset, which is parallel to the X axis. */
  override def reflectYOffset(xOffset: Double): EllipseCompound
  
  override def prolign(matrix: ProlignMatrix): EllipseCompound

  override def rotateRadians(radians: Double): EllipseCompound

  override def reflect(line: Line): EllipseCompound

  override def xyScale(xOperand: Double, yOperand: Double): EllipseCompound

  override def xShear(operand: Double): EllipseCompound

  override def yShear(operand: Double): EllipseCompound

  override def reflect(line: LineSeg): EllipseCompound
}

object EllipseCompound
{ def apply(shape: Ellipse, facets: Arr[GraphicFacet], children: Arr[ShapeCompound] = Arr()): EllipseCompound = new EllipseCompoundImplement(shape, facets, children)

  /** The implementation class for a general ellipse that is not defined as a circle. Most users will not need to interact with this class. It been
   * created non anonymously because the type might be useful for certain specialised performance usecases. */
  case class EllipseCompoundImplement(shape: Ellipse, facets: Arr[GraphicFacet], children: Arr[ShapeCompound] = Arr()) extends EllipseCompound
  {
    override def attribs: Arr[XmlAtt] = ???

    override def svgStr: String = ???

    /** Return type narrowed to [[SvgEllipse]] from [[SvgElem]] */
    override def svgElem(bounds: BoundingRect): SvgEllipse =
    { //val bounds = shape.boundingRect
      val newEllipse = shape.reflectX.slate(0, bounds.minY + bounds.maxY)
      val newAtts = newEllipse.shapeAttribs
      val atts2 = if (shape.ellipeRotation == 0.degs) newAtts else newAtts +- SvgRotate(- shape.ellipeRotation.degs, shape.xCen, shape.yCen)
      SvgEllipse(atts2 ++ facets.flatMap(_.attribs))
    }

    override def rendToCanvas(cp: pCanv.CanvasPlatform): Unit = facets.foreach
    {
      case FillFacet(c) => cp.ellipseFill(shape, c)
      //case CurveDraw(w, c) => cp.circleDraw(shape, w, c)
      //case fr: FillRadial => cp.circleFillRadial(shape, fr)*/
      case sf => deb("Unrecognised ShapeFacet: " + sf.toString)
    }

    /** Translate geometric transformation. Translates this Ellipse Graphic into a modified EllipseGraphic. */
    override def slate(offset: Vec2): EllipseCompoundImplement = EllipseCompoundImplement(shape.slate(offset), facets, children.slate(offset))

    /** Translate geometric transformation. */
    override def slate(xOffset: Double, yOffset: Double): EllipseCompoundImplement =
      EllipseCompoundImplement(shape.slate(xOffset, yOffset), facets, children.slate(xOffset, yOffset))

    /** Uniform scaling transformation. The scale name was chosen for this operation as it is normally the desired operation and preserves Circles and
     * Squares. Use the xyScale method for differential scaling. */
    override def scale(operand: Double): EllipseCompoundImplement = EllipseCompoundImplement(shape.scale(operand), facets, children.scale(operand))

    /** Mirror, reflection transformation across the X axis. This method has been left abstract in GeomElemNew to allow the return type to be narrowed
     * in sub classes. */
    override def reflectX: EllipseCompoundImplement = EllipseCompoundImplement(shape.reflectX, facets, children.reflectX)

    /** Mirror, reflection transformation across the X axis. This method has been left abstract in GeomElemNew to allow the return type to be narrowed
     * in sub classes. */
    override def reflectY: EllipseCompoundImplement = EllipseCompoundImplement(shape.reflectY, facets, children.reflectY)

    /** Mirror, reflection transformation across the line y = yOffset, which is parallel to the X axis. */
    override def reflectXOffset(yOffset: Double): EllipseCompoundImplement =
      EllipseCompoundImplement(shape.reflectXOffset(yOffset), facets, children.reflectXOffset(yOffset))

    /** Mirror, reflection transformation across the line x = xOffset, which is parallel to the X axis. */
    override def reflectYOffset(xOffset: Double): EllipseCompoundImplement =
      EllipseCompoundImplement(shape.reflectYOffset(xOffset), facets, children.reflectYOffset(xOffset))

    override def prolign(matrix: ProlignMatrix): EllipseCompoundImplement = EllipseCompoundImplement(shape.prolign(matrix), facets, children.prolign(matrix))

    override def rotateRadians(radians: Double): EllipseCompoundImplement = EllipseCompoundImplement(shape.rotateRadians(radians), facets, children.rotateRadians(radians))

    override def reflect(line: Line): EllipseCompoundImplement = ??? //EllipseGenGraphic(shape.reflect(line), facets, children.reflect(line))

    override def xyScale(xOperand: Double, yOperand: Double): EllipseCompoundImplement = ???

    override def xShear(operand: Double): EllipseCompoundImplement = ???

    override def yShear(operand: Double): EllipseCompoundImplement = ???

    override def reflect(line: LineSeg): EllipseCompoundImplement = ???
  }
}