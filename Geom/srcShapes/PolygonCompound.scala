/* Copyright 2018-23 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package geom
import pWeb._

/** A compound polygon based Graphic. May contain multiple facets and child graphic members. */
trait PolygonCompound extends ShapeCompound with PolygonGraphic
{
  override def mainSvgElem: SvgElem = SvgPolygon(attribs)

  override def rendToCanvas(cp: pgui.CanvasPlatform): Unit = facets.foreach {
    case c: Colour => cp.polygonFill(shape.fill(c))
    case DrawFacet(c, w) => cp.polygonDraw(shape.draw(w, c))
    case TextFacet(s, rel, col, ta, bl, min) => cp.textGraphic(TextFixed(s, boundingWidth/ rel, cenDefault, col, ta, bl))
    // case fr: FillRadial => cp.circleFillRadial(shape, fr)
    case sf => deb("Unrecognised ShapeFacet: " + sf.toString)
  }

  override def canvElems: RArr[CanvElem] = ???

  /** Translate geometric transformation. */
  override def slateXY(xDelta: Double, yDelta: Double): PolygonCompound =
    PolygonCompound(shape.slateXY(xDelta, yDelta), facets, children.SlateXY(xDelta, yDelta))

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

  override def rotate90: PolygonCompound = PolygonCompound(shape.rotate90, facets, children.rotate90)
  override def rotate180: PolygonCompound = PolygonCompound(shape.rotate180, facets, children.rotate180)
  override def rotate270: PolygonCompound = PolygonCompound(shape.rotate270, facets, children.rotate270)

  override def rotate(angle: AngleVec): PolygonCompound = PolygonCompound(shape.rotate(angle), facets, children.rotate(angle))

  override def reflect(lineLike: LineLike): PolygonCompound = PolygonCompound(shape.reflect(lineLike), facets, children.reflect(lineLike))

  override def scaleXY(xOperand: Double, yOperand: Double): PolygonCompound =
    PolygonCompound(shape.scaleXY(xOperand, yOperand), facets, children.scaleXY(xOperand, yOperand))

  override def shearX(operand: Double): PolygonCompound = ??? //PolygonCompound(shape.xShear(operand), facets, children.xShear(operand))

  override def shearY(operand: Double): PolygonCompound = ??? //PolygonCompound(shape.xShear(operand), facets, children.yShear(operand))

  def addChildren(newChildren: Arr[GraphicElem]): PolygonCompound = PolygonCompound(shape, facets, children ++ newChildren)

  def rightX: Double = shape.rightX
}

/** Companion object for the PolygonCompound trait contains factory apply method and implicit instances for the 2D geometric transformation type
 * classes. */
object PolygonCompound
{
  def apply(shape: Polygon, facets: RArr[GraphicFacet], children: RArr[GraphicElem] = RArr()): PolygonCompound =
    new PolygonCompoundImp(shape, facets, children)

  //implicit val showTImplicit: Show3T[Polygon, Arr[GraphicFacet], Arr[GraphicElem], PolygonCompound] = Show3T[Polygon, Arr[GraphicFacet], Arr[GraphicElem], PolygonCompound]()
  
  implicit val slateImplicit: Slate[PolygonCompound] = (obj: PolygonCompound, dx: Double, dy: Double) => obj.slateXY(dx, dy)
  implicit val scaleImplicit: Scale[PolygonCompound] = (obj: PolygonCompound, operand: Double) => obj.scale(operand)
  implicit val rotateImplicit: Rotate[PolygonCompound] = (obj: PolygonCompound, angle: AngleVec) => obj.rotate(angle)
  implicit val prolignImplicit: Prolign[PolygonCompound] = (obj, matrix) => obj.prolign(matrix)
  implicit val XYScaleImplicit: ScaleXY[PolygonCompound] = (obj, xOperand, yOperand) => obj.scaleXY(xOperand, yOperand)
  implicit val reflectImplicit: Reflect[PolygonCompound] = (obj: PolygonCompound, lineLike: LineLike) => obj.reflect(lineLike)

  implicit val reflectAxesImplicit: TransAxes[PolygonCompound] = new TransAxes[PolygonCompound]
  { override def negYT(obj: PolygonCompound): PolygonCompound = obj.negY
    override def negXT(obj: PolygonCompound): PolygonCompound = obj.negX
    override def rotate90(obj: PolygonCompound): PolygonCompound = obj.rotate90
    override def rotate180(obj: PolygonCompound): PolygonCompound = obj.rotate180
    override def rotate270(obj: PolygonCompound): PolygonCompound = obj.rotate270
  }

  implicit val shearImplicit: Shear[PolygonCompound] = new Shear[PolygonCompound]
  { override def shearXT(obj: PolygonCompound, yFactor: Double): PolygonCompound = obj.shearX(yFactor)
    override def shearYT(obj: PolygonCompound, xFactor: Double): PolygonCompound = obj.shearY(xFactor)
  }

  /** A compound polygon based Graphic. May contain multiple facets and child graphic members. */
  case class PolygonCompoundImp(shape: Polygon, facets: RArr[GraphicFacet], children: RArr[GraphicElem] = RArr()) extends PolygonCompound with
    AxisFree
  {
    override type ThisT = PolygonCompoundImp

    override def mainSvgElem: SvgPolygon = SvgPolygon(attribs)

    override def rendToCanvas(cp: pgui.CanvasPlatform): Unit = facets.foreach{
      case c: Colour => cp.polygonFill(shape.fill(c))
      case DrawFacet(c, w) => cp.polygonDraw(shape.draw(w, c))
      case TextFacet(s, ratio, colour, ta, bl, min) =>
      { val size = boundingWidth /ratio
        if (size >= min) cp.textGraphic(TextFixed(s, size, cenDefault, colour, ta, bl))}
      // case fr: FillRadial => cp.circleFillRadial(shape, fr)
      case sf => deb("Unrecognised ShapeFacet: " + sf.toString)
    }

    /** Translate geometric transformation. */
    override def slateXY(xDelta: Double, yDelta: Double): PolygonCompoundImp =
      PolygonCompoundImp(shape.slateXY(xDelta, yDelta), facets, children.SlateXY(xDelta, yDelta))

    /** Uniform scaling transformation. The scale name was chosen for this operation as it is normally the desired operation and preserves Circles and
     * Squares. Use the xyScale method for differential scaling. */
    override def scale(operand: Double): PolygonCompoundImp = PolygonCompoundImp(shape.scale(operand), facets, children.scale(operand))

    override def prolign(matrix: ProlignMatrix): PolygonCompoundImp = PolygonCompoundImp(shape.prolign(matrix), facets, children.prolign(matrix))

    override def rotate(angle: AngleVec): PolygonCompoundImp = PolygonCompoundImp(shape.rotate(angle), facets, children.rotate(angle))

    override def reflect(lineLike: LineLike): PolygonCompoundImp = PolygonCompoundImp(shape.reflect(lineLike), facets, children.reflect(lineLike))

    override def scaleXY(xOperand: Double, yOperand: Double): PolygonCompoundImp = ???

    override def shearX(operand: Double): PolygonCompoundImp = ???

    override def shearY(operand: Double): PolygonCompoundImp = ???
  }  
}