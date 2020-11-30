/* Copyright 2018-20 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat
package geom
import pWeb._

/** A compound polygon based Graphic. May contain multiple facets and child graphic members. */
trait PolygonCompound extends ShapeCompound with PolygonGraphic
{
  override def rendToCanvas(cp: pCanv.CanvasPlatform): Unit = facets.foreach
  { case c: Colour => cp.polygonFill(shape.fill(c))
    case DrawFacet(c, w) => cp.polygonDraw(shape.draw(c, w))
    case TextFacet(s, col) => cp.textGraphic(TextGraphic(s, cenDefault, 18, col))
    // case fr: FillRadial => cp.circleFillRadial(shape, fr)
    case sf => deb("Unrecognised ShapeFacet: " + sf.toString)
  }
  
  override def attribs: Arr[XmlAtt] = ???

  override def svgStr: String = ???

  override def svgElem(bounds: BoundingRect): SvgElem = ???

  /** Translate geometric transformation. */
  override def slate(xOffset: Double, yOffset: Double): PolygonCompound =
    PolygonCompound(shape.slate(xOffset, yOffset), facets, children.slate(xOffset, yOffset))

  /** Translate geometric transformation. */
  override def slate(offset: Vec2Like): PolygonCompound = PolygonCompound(shape.slate(offset), facets, children.slate(offset))

  /** Uniform scaling transformation. The scale name was chosen for this operation as it is normally the desired operation and preserves Circles and
   * Squares. Use the xyScale method for differential scaling. */
  override def scale(operand: Double): PolygonCompound = PolygonCompound(shape.scale(operand), facets, children.scale(operand))

  /** Mirror, reflection transformation across the X axis. This method has been left abstract in GeomElemNew to allow the return type to be narrowed
   * in sub classes. */
  override def negY: PolygonCompound = PolygonCompound(shape.negY, facets, children.negY)

  /** Mirror, reflection transformation across the X axis. This method has been left abstract in GeomElemNew to allow the return type to be narrowed
   * in sub classes. */
  override def negX: PolygonCompound = PolygonCompound(shape.negX, facets, children.negX)

  override def prolign(matrix: ProlignMatrix): PolygonCompound = PolygonCompound(shape.prolign(matrix), facets, children.prolign(matrix))

  override def rotate(angle: Angle): PolygonCompound = PolygonCompound(shape.rotate(angle), facets, children.rotate(angle))

  override def reflect(lineLike: LineLike): PolygonCompound = PolygonCompound(shape.reflect(lineLike), facets, children.reflect(lineLike))

  override def xyScale(xOperand: Double, yOperand: Double): PolygonCompound =
    PolygonCompound(shape.xyScale(xOperand, yOperand), facets, children.xyScale(xOperand, yOperand))

  override def xShear(operand: Double): PolygonCompound = ??? //PolygonCompound(shape.xShear(operand), facets, children.xShear(operand))

  override def yShear(operand: Double): PolygonCompound = ??? //PolygonCompound(shape.xShear(operand), facets, children.yShear(operand))

  def addChildren(newChildren: Arr[GraphicElem]): PolygonCompound = PolygonCompound(shape, facets, children ++ newChildren)
}

object PolygonCompound
{
  def apply(shape: Polygon, facets: Arr[GraphicFacet], children: Arr[GraphicElem] = Arr()): PolygonCompound =
    new PolygonCompoundImp(shape, facets, children)
  
  implicit val slateImplicit: Slate[PolygonCompound] = (obj: PolygonCompound, dx: Double, dy: Double) => obj.slate(dx, dy)
  implicit val scaleImplicit: Scale[PolygonCompound] = (obj: PolygonCompound, operand: Double) => obj.scale(operand)
  implicit val rotateImplicit: Rotate[PolygonCompound] = (obj: PolygonCompound, angle: Angle) => obj.rotate(angle)
  implicit val prolignImplicit: Prolign[PolygonCompound] = (obj, matrix) => obj.prolign(matrix)
  implicit val XYScaleImplicit: XYScale[PolygonCompound] = (obj, xOperand, yOperand) => obj.xyScale(xOperand, yOperand)
  implicit val reflectImplicit: Reflect[PolygonCompound] = (obj: PolygonCompound, lineLike: LineLike) => obj.reflect(lineLike)

  implicit val reflectAxesImplicit: ReflectAxes[PolygonCompound] = new ReflectAxes[PolygonCompound]
  { override def negYT(obj: PolygonCompound): PolygonCompound = obj.negY
    override def negXT(obj: PolygonCompound): PolygonCompound = obj.negX
  }

  implicit val shearImplicit: Shear[PolygonCompound] = new Shear[PolygonCompound]
  { override def xShearT(obj: PolygonCompound, yFactor: Double): PolygonCompound = obj.xShear(yFactor)
    override def yShearT(obj: PolygonCompound, xFactor: Double): PolygonCompound = obj.yShear(xFactor)
  }

  /** A compound polygon based Graphic. May contain multiple facets and child graphic members. */
  case class PolygonCompoundImp(shape: Polygon, facets: Arr[GraphicFacet], children: Arr[GraphicElem] = Arr()) extends PolygonCompound
  {
    override def rendToCanvas(cp: pCanv.CanvasPlatform): Unit = facets.foreach
    { case c: Colour => cp.polygonFill(shape.fill(c))
      case DrawFacet(c, w) => cp.polygonDraw(shape.draw(c, w))
      case TextFacet(s, col) => cp.textGraphic(TextGraphic(s, cenDefault, 18, col))
      // case fr: FillRadial => cp.circleFillRadial(shape, fr)
      case sf => deb("Unrecognised ShapeFacet: " + sf.toString)
    }

    override def attribs: Arr[XmlAtt] = ???

    override def svgStr: String = ???

    override def svgElem(bounds: BoundingRect): SvgElem = ???

    /** Translate geometric transformation. */
    override def slate(xOffset: Double, yOffset: Double): PolygonCompoundImp =
      PolygonCompoundImp(shape.slate(xOffset, yOffset), facets, children.slate(xOffset, yOffset))

    /** Translate geometric transformation. */
    override def slate(offset: Vec2Like): PolygonCompoundImp = PolygonCompoundImp(shape.slate(offset), facets, children.slate(offset))

    /** Uniform scaling transformation. The scale name was chosen for this operation as it is normally the desired operation and preserves Circles and
     * Squares. Use the xyScale method for differential scaling. */
    override def scale(operand: Double): PolygonCompoundImp = PolygonCompoundImp(shape.scale(operand), facets, children.scale(operand))

    /** Mirror, reflection transformation across the X axis. This method has been left abstract in GeomElemNew to allow the return type to be narrowed
     * in sub classes. */
    override def negY: PolygonCompoundImp = PolygonCompoundImp(shape.negY, facets, children.negY)

    /** Mirror, reflection transformation across the X axis. This method has been left abstract in GeomElemNew to allow the return type to be narrowed
     * in sub classes. */
    override def negX: PolygonCompoundImp = PolygonCompoundImp(shape.negX, facets, children.negX)

    override def prolign(matrix: ProlignMatrix): PolygonCompoundImp = PolygonCompoundImp(shape.prolign(matrix), facets, children.prolign(matrix))

    override def rotate(angle: Angle): PolygonCompoundImp = PolygonCompoundImp(shape.rotate(angle), facets, children.rotate(angle))

    override def reflect(lineLike: LineLike): PolygonCompoundImp = PolygonCompoundImp(shape.reflect(lineLike), facets, children.reflect(lineLike))

    override def xyScale(xOperand: Double, yOperand: Double): PolygonCompoundImp = ???

    override def xShear(operand: Double): PolygonCompoundImp = ???

    override def yShear(operand: Double): PolygonCompoundImp = ???
  }  
}