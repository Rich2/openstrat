/* Copyright 2018-20 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat
package geom
import pWeb._

/** A compound polygon based Graphic. May contain multiple facets and child graphic members. */
trait PolygonCompound extends ShapeCompound with PolygonGraphic
{
  override def rendToCanvas(cp: pCanv.CanvasPlatform): Unit = facets.foreach
  { case FillFacet(c) => cp.polygonFill(shape, c)
    case DrawFacet(w, c) => cp.polygonDraw(shape, w, c)
    case TextFacet(s, col) => cp.textGraphic(s, 18, cen, col)
    // case fr: FillRadial => cp.circleFillRadial(shape, fr)
    case sf => deb("Unrecognised ShapeFacet: " + sf.toString)
  }
  
  override def attribs: Arr[XmlAtt] = ???

  override def svgStr: String = ???

  override def svgElem(bounds: BoundingRect): SvgElem = ???

  /** Translate geometric transformation. */
  override def slate(offset: Vec2): PolygonCompound = PolygonCompound(shape.slate(offset), facets, children.slate(offset))

  /** Translate geometric transformation. */
  override def slate(xOffset: Double, yOffset: Double): PolygonCompound =
    PolygonCompound(shape.slate(xOffset, yOffset), facets, children.slate(xOffset, yOffset))

  /** Uniform scaling transformation. The scale name was chosen for this operation as it is normally the desired operation and preserves Circles and
   * Squares. Use the xyScale method for differential scaling. */
  override def scale(operand: Double): PolygonCompound = PolygonCompound(shape.scale(operand), facets, children.scale(operand))

  /** Mirror, reflection transformation across the line x = xOffset, which is parallel to the X axis. */
  override def reflectYParallel(xOffset: Double): PolygonCompound =
    PolygonCompound(shape.reflectYParallel(xOffset), facets, children.reflectYOffset(xOffset))

  /** Mirror, reflection transformation across the line y = yOffset, which is parallel to the X axis. */
  override def reflectXParallel(yOffset: Double): PolygonCompound = ???

  /** Mirror, reflection transformation across the X axis. This method has been left abstract in GeomElemNew to allow the return type to be narrowed
   * in sub classes. */
  override def negY: PolygonCompound = ???

  /** Mirror, reflection transformation across the X axis. This method has been left abstract in GeomElemNew to allow the return type to be narrowed
   * in sub classes. */
  override def negX: PolygonCompound = PolygonCompound(shape.negX, facets, children.negX) 

  override def prolign(matrix: ProlignMatrix): PolygonCompound = ???

  override def rotateRadians(radians: Double): PolygonCompound = ???

  override def reflect(line: Line): PolygonCompound = ???

  override def xyScale(xOperand: Double, yOperand: Double): PolygonCompound = ???

  override def xShear(operand: Double): PolygonCompound = ???

  override def yShear(operand: Double): PolygonCompound = ???

  override def reflect(line: LineSeg): PolygonCompound = ???
}

object PolygonCompound
{
  def apply(shape: Polygon, facets: Arr[GraphicFacet], children: Arr[GraphicElem] = Arr()): PolygonCompound =
    new PolygonCompoundImp(shape, facets, children)
  
  implicit val slateImplicit: Slate[PolygonCompound] = (obj: PolygonCompound, offset: Vec2) => obj.slate(offset)
  implicit val scaleImplicit: Scale[PolygonCompound] = (obj: PolygonCompound, operand: Double) => obj.scale(operand)
  implicit val rotateImplicit: Rotate[PolygonCompound] = (obj: PolygonCompound, radians: Double) => obj.rotateRadians(radians)
  implicit val prolignImplicit: Prolign[PolygonCompound] = (obj, matrix) => obj.prolign(matrix)
  implicit val XYScaleImplicit: XYScale[PolygonCompound] = (obj, xOperand, yOperand) => obj.xyScale(xOperand, yOperand)

  /*implicit val mirrorAxisImplicit: ReflectAxesOffset[PolygonCompound] = new ReflectAxesOffset[PolygonCompound]
  { override def reflectXOffsetT(obj: PolygonCompound, yOffset: Double): PolygonCompound = obj.reflectXParallel(yOffset)
    override def reflectYOffsetT(obj: PolygonCompound, xOffset: Double): PolygonCompound = obj.reflectYParallel(xOffset)
  }*/

  implicit val shearImplicit: Shear[PolygonCompound] = new Shear[PolygonCompound]
  { override def xShearT(obj: PolygonCompound, yFactor: Double): PolygonCompound = obj.xShear(yFactor)
    override def yShearT(obj: PolygonCompound, xFactor: Double): PolygonCompound = obj.yShear(xFactor)
  }

  /** A compound polygon based Graphic. May contain multiple facets and child graphic members. */
  case class PolygonCompoundImp(shape: Polygon, facets: Arr[GraphicFacet], children: Arr[GraphicElem] = Arr()) extends PolygonCompound
  {
    override def rendToCanvas(cp: pCanv.CanvasPlatform): Unit = facets.foreach
    { case FillFacet(c) => cp.polygonFill(shape, c)
    case DrawFacet(w, c) => cp.polygonDraw(shape, w, c)
    case TextFacet(s, col) => cp.textGraphic(s, 18, cen, col)
    // case fr: FillRadial => cp.circleFillRadial(shape, fr)
    case sf => deb("Unrecognised ShapeFacet: " + sf.toString)
    }

    override def attribs: Arr[XmlAtt] = ???

    override def svgStr: String = ???

    override def svgElem(bounds: BoundingRect): SvgElem = ???

    /** Translate geometric transformation. */
    override def slate(offset: Vec2): PolygonCompoundImp = PolygonCompoundImp(shape.slate(offset), facets, children.slate(offset))

    /** Translate geometric transformation. */
    override def slate(xOffset: Double, yOffset: Double): PolygonCompoundImp =
      PolygonCompoundImp(shape.slate(xOffset, yOffset), facets, children.slate(xOffset, yOffset))

    /** Uniform scaling transformation. The scale name was chosen for this operation as it is normally the desired operation and preserves Circles and
     * Squares. Use the xyScale method for differential scaling. */
    override def scale(operand: Double): PolygonCompoundImp = PolygonCompoundImp(shape.scale(operand), facets, children.scale(operand))

    /** Mirror, reflection transformation across the line x = xOffset, which is parallel to the X axis. */
    override def reflectYParallel(xOffset: Double): PolygonCompoundImp =
      PolygonCompoundImp(shape.reflectYParallel(xOffset), facets, children.reflectYOffset(xOffset))

    /** Mirror, reflection transformation across the line y = yOffset, which is parallel to the X axis. */
    override def reflectXParallel(yOffset: Double): PolygonCompoundImp = ???

    /** Mirror, reflection transformation across the X axis. This method has been left abstract in GeomElemNew to allow the return type to be narrowed
     * in sub classes. */
    override def negY: PolygonCompoundImp = ???

    /** Mirror, reflection transformation across the X axis. This method has been left abstract in GeomElemNew to allow the return type to be narrowed
     * in sub classes. */
    override def negX: PolygonCompoundImp = PolygonCompoundImp(shape.negX, facets, children.negX)

    override def prolign(matrix: ProlignMatrix): PolygonCompoundImp = ???

    override def rotateRadians(radians: Double): PolygonCompoundImp = ???

    override def reflect(line: Line): PolygonCompoundImp = ???

    override def xyScale(xOperand: Double, yOperand: Double): PolygonCompoundImp = ???

    override def xShear(operand: Double): PolygonCompoundImp = ???

    override def yShear(operand: Double): PolygonCompoundImp = ???

    override def reflect(line: LineSeg): PolygonCompoundImp = ???
  }  
}