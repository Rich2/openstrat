/* Copyright 2018-2% Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package geom
import pWeb.*

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

  override def slate(operand: VecPt2): PolygonCompound = PolygonCompound(shape.slate(operand), facets, children.slate(operand))
  
  override def slate(xOperand: Double, yOperand: Double): PolygonCompound =
    PolygonCompound(shape.slate(xOperand, yOperand), facets, children.slateXY(xOperand, yOperand))
  
  override def scale(operand: Double): PolygonCompound = PolygonCompound(shape.scale(operand), facets, children.scale(operand))
  override def negY: PolygonCompound = PolygonCompound(shape.negY, facets, children.negY)
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

  def addChildren(newChildren: Arr[Graphic2Elem]): PolygonCompound = PolygonCompound(shape, facets, children ++ newChildren)

  def rightX: Double = shape.rightX
}

/** Companion object for the PolygonCompound trait contains factory apply method and implicit instances for the 2D geometric transformation type
 * classes. */
object PolygonCompound
{
  def apply(shape: Polygon, facets: RArr[GraphicFacet], children: RArr[Graphic2Elem] = RArr()): PolygonCompound =
    new PolygonCompoundImp(shape, facets, children)

  //implicit val showTImplicit: Show3T[Polygon, Arr[GraphicFacet], Arr[GraphicElem], PolygonCompound] = Show3T[Polygon, Arr[GraphicFacet], Arr[GraphicElem], PolygonCompound]()
  
  /** Implicit [[Slate]] type class instance evidence for [[PolygonCompound]]. */
  implicit val slateEv: Slate[PolygonCompound] = (obj, operand) => obj.slate(operand)

  /** Implicit [[SlateXY]] type class instance evidence for [[PolygonCompound]]. */
  implicit val slateXYEv: SlateXY[PolygonCompound] = (obj: PolygonCompound, dx: Double, dy: Double) => obj.slate(dx, dy)

  /** Implicit [[Scale]] type class instance evidence for [[PolygonCompound]]. */
  implicit val scaleEv: Scale[PolygonCompound] = (obj: PolygonCompound, operand: Double) => obj.scale(operand)

  /** Implicit [[Rotate]] type class instance evidence for [[PolygonCompound]]. */
  implicit val rotateEv: Rotate[PolygonCompound] = (obj: PolygonCompound, angle: AngleVec) => obj.rotate(angle)

  /** Implicit [[Prolign]] type class instance evidence for [[PolygonCompound]]. */
  implicit val prolignEv: Prolign[PolygonCompound] = (obj, matrix) => obj.prolign(matrix)

  /** Implicit [[ScaleXY]] type class instance evidence for [[PolygonCompound]]. */
  implicit val scaleXYEv: ScaleXY[PolygonCompound] = (obj, xOperand, yOperand) => obj.scaleXY(xOperand, yOperand)
  
  /** Implicit [[Reflect]] type class instance evidence for [[PolygonCompound]]. */
  implicit val reflectEv: Reflect[PolygonCompound] = (obj: PolygonCompound, lineLike: LineLike) => obj.reflect(lineLike)
  
  /** Implicit [[RefelctAxes]] type class instance evidence for [[PolygonCompound]]. */
  implicit val reflectAxesEv: TransAxes[PolygonCompound] = new TransAxes[PolygonCompound]
  { override def negYT(obj: PolygonCompound): PolygonCompound = obj.negY
    override def negXT(obj: PolygonCompound): PolygonCompound = obj.negX
    override def rotate90(obj: PolygonCompound): PolygonCompound = obj.rotate90
    override def rotate180(obj: PolygonCompound): PolygonCompound = obj.rotate180
    override def rotate270(obj: PolygonCompound): PolygonCompound = obj.rotate270
  }
  
  /** Implicit [[Shear]] type class instance evidence for [[PolygonCompound]]. */
  implicit val shearEv: Shear[PolygonCompound] = new Shear[PolygonCompound]
  { override def shearXT(obj: PolygonCompound, yFactor: Double): PolygonCompound = obj.shearX(yFactor)
    override def shearYT(obj: PolygonCompound, xFactor: Double): PolygonCompound = obj.shearY(xFactor)
  }

  /** A compound polygon based Graphic. May contain multiple facets and child graphic members. */
  case class PolygonCompoundImp(shape: Polygon, facets: RArr[GraphicFacet], children: RArr[Graphic2Elem] = RArr()) extends PolygonCompound, AxisFree
  {
    override type ThisT = PolygonCompoundImp

    override def mainSvgElem: SvgPolygon = SvgPolygon(attribs)

    override def rendToCanvas(cp: pgui.CanvasPlatform): Unit = facets.foreach{
      case c: Colour => cp.polygonFill(shape.fill(c))
      case DrawFacet(c, w) => cp.polygonDraw(shape.draw(w, c))

      case TextFacet(s, ratio, colour, ta, bl, min) =>
      { val size = boundingWidth /ratio
        if (size >= min) cp.textGraphic(TextFixed(s, size, cenDefault, colour, ta, bl))
      }
      // case fr: FillRadial => cp.circleFillRadial(shape, fr)
      case sf => deb("Unrecognised ShapeFacet: " + sf.toString)
    }

    override def slate(operand: VecPt2): PolygonCompoundImp = PolygonCompoundImp(shape.slate(operand), facets, children.slate(operand))
    override def slate(xOperand: Double, yOperand: Double): PolygonCompoundImp =
      PolygonCompoundImp(shape.slate(xOperand, yOperand), facets, children.slateXY(xOperand, yOperand))

    override def scale(operand: Double): PolygonCompoundImp = PolygonCompoundImp(shape.scale(operand), facets, children.scale(operand))
    override def prolign(matrix: ProlignMatrix): PolygonCompoundImp = PolygonCompoundImp(shape.prolign(matrix), facets, children.prolign(matrix))
    override def rotate(angle: AngleVec): PolygonCompoundImp = PolygonCompoundImp(shape.rotate(angle), facets, children.rotate(angle))
    override def reflect(lineLike: LineLike): PolygonCompoundImp = PolygonCompoundImp(shape.reflect(lineLike), facets, children.reflect(lineLike))

    override def scaleXY(xOperand: Double, yOperand: Double): PolygonCompoundImp =
      PolygonCompoundImp(shape.scaleXY(xOperand, yOperand), facets, children.scaleXY(xOperand, yOperand))

    override def shearX(operand: Double): PolygonCompoundImp = ???// PolygonCompoundImp(shape.shearX(operand), facets, children.shearX)

    override def shearY(operand: Double): PolygonCompoundImp = ???
  }  
}