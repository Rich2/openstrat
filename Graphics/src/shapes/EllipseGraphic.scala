/* Copyright 2018-20 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat
package geom
import pWeb._

trait EllipseGraphic extends ShapeCompound
{ override def shape: Ellipse

  /** Translate geometric transformation. Translates this Ellipse Graphic into a modified EllipseGraphic. */
  override def slate(offset: Vec2): EllipseGraphic

  /** Translate geometric transformation. */
  override def slate(xOffset: Double, yOffset: Double): EllipseGraphic

  /** Uniform scaling transformation. The scale name was chosen for this operation as it is normally the desired operation and preserves Circles and
   * Squares. Use the xyScale method for differential scaling. */
  override def scale(operand: Double): EllipseGraphic
 
  /** Mirror, reflection transformation across the X axis. This method has been left abstract in GeomElemNew to allow the return type to be narrowed
   * in sub classes. */
  override def reflectX: EllipseGraphic

  /** Mirror, reflection transformation across the X axis. This method has been left abstract in GeomElemNew to allow the return type to be narrowed
   * in sub classes. */
  override def reflectY: EllipseGraphic

  /** Mirror, reflection transformation across the line y = yOffset, which is parallel to the X axis. */
  override def reflectXOffset(yOffset: Double): EllipseGraphic

  /** Mirror, reflection transformation across the line x = xOffset, which is parallel to the X axis. */
  override def reflectYOffset(xOffset: Double): EllipseGraphic
  
  override def prolign(matrix: ProlignMatrix): EllipseGraphic

  /** Rotates 90 degrees or Pi/2 radians anticlockwise. */
  /*override def rotate90: EllipseGraphic

  /** Rotates 180 degrees or Pi radians. */
  override def rotate180: EllipseGraphic

  /** Rotates 90 degrees or Pi/2 radians clockwise. */
  override def rotate270: EllipseGraphic*/

  override def rotateRadians(radians: Double): EllipseGraphic

  override def reflect(line: Line): EllipseGraphic

  override def xyScale(xOperand: Double, yOperand: Double): EllipseGraphic

  override def xShear(operand: Double): EllipseGraphic

  override def yShear(operand: Double): EllipseGraphic

  override def reflect(line: LineSeg): EllipseGraphic
}

object EllipseGraphic
{ def apply(shape: Ellipse, facets: Arr[GraphicFacet], children: Arr[ShapeCompound] = Arr()): EllipseGraphic = new EllipseGraphicImplement(shape, facets, children)

  /** The implementation class for a general ellipse that is not defined as a circle. Most users will not need to interact with this class. It been
   * created non anonymously because the type might be useful for certain specialised performance usecases. */
  case class EllipseGraphicImplement(shape: Ellipse, facets: Arr[GraphicFacet], children: Arr[ShapeCompound] = Arr()) extends EllipseGraphic
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
    override def slate(offset: Vec2): EllipseGraphicImplement = EllipseGraphicImplement(shape.slate(offset), facets, children.slate(offset))

    /** Translate geometric transformation. */
    override def slate(xOffset: Double, yOffset: Double): EllipseGraphicImplement =
      EllipseGraphicImplement(shape.slate(xOffset, yOffset), facets, children.slate(xOffset, yOffset))

    /** Uniform scaling transformation. The scale name was chosen for this operation as it is normally the desired operation and preserves Circles and
     * Squares. Use the xyScale method for differential scaling. */
    override def scale(operand: Double): EllipseGraphicImplement = EllipseGraphicImplement(shape.scale(operand), facets, children.scale(operand))

    /** Mirror, reflection transformation across the X axis. This method has been left abstract in GeomElemNew to allow the return type to be narrowed
     * in sub classes. */
    override def reflectX: EllipseGraphicImplement = EllipseGraphicImplement(shape.reflectX, facets, children.reflectX)

    /** Mirror, reflection transformation across the X axis. This method has been left abstract in GeomElemNew to allow the return type to be narrowed
     * in sub classes. */
    override def reflectY: EllipseGraphicImplement = EllipseGraphicImplement(shape.reflectY, facets, children.reflectY)

    /** Mirror, reflection transformation across the line y = yOffset, which is parallel to the X axis. */
    override def reflectXOffset(yOffset: Double): EllipseGraphicImplement =
      EllipseGraphicImplement(shape.reflectXOffset(yOffset), facets, children.reflectXOffset(yOffset))

    /** Mirror, reflection transformation across the line x = xOffset, which is parallel to the X axis. */
    override def reflectYOffset(xOffset: Double): EllipseGraphicImplement =
      EllipseGraphicImplement(shape.reflectYOffset(xOffset), facets, children.reflectYOffset(xOffset))

    override def prolign(matrix: ProlignMatrix): EllipseGraphicImplement = EllipseGraphicImplement(shape.prolign(matrix), facets, children.prolign(matrix))

    /** Rotates 90 degrees or Pi/2 radians anticlockwise. */
   /* override def rotate90: EllipseGraphicImplement = EllipseGraphicImplement(shape.rotate90, facets, children.rotate90)

    /** Rotates 180 degrees or Pi radians. */
    override def rotate180: EllipseGraphicImplement = EllipseGraphicImplement(shape.rotate180, facets, children.rotate180)

    /** Rotates 90 degrees or Pi/2 radians clockwise. */
    override def rotate270: EllipseGraphicImplement = EllipseGraphicImplement(shape.rotate270, facets, children.rotate270)*/

    override def rotateRadians(radians: Double): EllipseGraphicImplement = EllipseGraphicImplement(shape.rotateRadians(radians), facets, children.rotateRadians(radians))

    override def reflect(line: Line): EllipseGraphicImplement = ??? //EllipseGenGraphic(shape.reflect(line), facets, children.reflect(line))

    override def xyScale(xOperand: Double, yOperand: Double): EllipseGraphicImplement = ???

    override def xShear(operand: Double): EllipseGraphicImplement = ???

    override def yShear(operand: Double): EllipseGraphicImplement = ???

    override def reflect(line: LineSeg): EllipseGraphicImplement = ???
  }
}