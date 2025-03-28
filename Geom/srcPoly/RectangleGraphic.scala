/* Copyright 2018-23 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package geom
import Colour.*, pgui.*

trait RectangleGraphic extends PolygonGraphic with ShapeGraphicCentred
{ override def shape: Rectangle
}

trait RectangleGraphicSimple extends PolygonGraphicSimple with RectangleGraphic

/** Graphic that draws a rectangle. */
trait RectangleDraw extends PolygonDraw with RectangleGraphicSimple
{ override def slate(operand: VecPt2): RectangleDraw = RectangleDraw(shape.slate(operand), lineWidth, lineColour)
  override def slate(xOperand: Double, yOperand: Double): RectangleDraw = RectangleDraw(shape.slate(xOperand, yOperand), lineWidth, lineColour)
  override def scale(operand: Double): RectangleDraw = RectangleDraw(shape.scale(operand), lineWidth, lineColour)
  override def negY: RectangleDraw = RectangleDraw(shape.negY, lineWidth, lineColour)
  override def negX: RectangleDraw = RectangleDraw(shape.negX, lineWidth, lineColour)
  override def rotate90: RectangleDraw = ???
  override def rotate180: RectangleDraw = ???
  override def rotate270: RectangleDraw = ???
  override def prolign(matrix: ProlignMatrix): RectangleDraw = RectangleDraw(shape.prolign(matrix), lineWidth, lineColour)
  override def rotate(angle: AngleVec): RectangleDraw = RectangleDraw(shape.rotate(angle), lineWidth, lineColour)
  override def reflect(lineLike: LineLike): RectangleDraw = RectangleDraw(shape.reflect(lineLike), lineWidth, lineColour)
  override def scaleXY(xOperand: Double, yOperand: Double): RectangleDraw = RectangleDraw(shape.scaleXY(xOperand, yOperand), lineWidth, lineColour)
}

/** Companion object for RectangleDraw contains factory method and implementation class. */
object RectangleDraw
{
  def apply(shape: Rectangle, lineWidth: Double = 2, lineColour: Colour = Black): RectangleDraw = RectangleDrawImp(shape, lineWidth, lineColour)

  /** Immutable Graphic element that defines and draws a Polygon. */
  case class RectangleDrawImp(shape: Rectangle, lineWidth: Double = 2, lineColour: Colour = Black) extends RectangleDraw
  {
    override def rendToCanvas(cp: CanvasPlatform): Unit = cp.polygonDraw(this)
  }
}

/** Graphic to fill a Rectangle with a single colour. */
trait RectangleFill extends PolygonFill with RectangleGraphicSimple
{ override def slate(operand: VecPt2): RectangleFill = RectangleFill(shape.slate(operand), fillFacet)
  override def slate(xDelta: Double, yDelta: Double): RectangleFill = RectangleFill(shape.slate(xDelta, yDelta), fillFacet)
  override def scale(operand: Double): RectangleFill = RectangleFill(shape.scale(operand), fillFacet)
  override def negY: RectangleFill = RectangleFill(shape.negY, fillFacet)
  override def negX: RectangleFill = RectangleFill(shape.negX, fillFacet)
  override def rotate90: RectangleFill = RectangleFill(shape.rotate90, fillFacet)
  override def rotate180: RectangleFill = RectangleFill(shape.rotate180, fillFacet)
  override def rotate270: RectangleFill = RectangleFill(shape.rotate270, fillFacet)
  override def prolign(matrix: ProlignMatrix): RectangleFill = RectangleFill(shape.prolign(matrix), fillFacet)
  override def rotate(angle: AngleVec): RectangleFill = RectangleFill(shape.rotate(angle), fillFacet)
  override def reflect(lineLike: LineLike): RectangleFill = RectangleFill(shape.reflect(lineLike), fillFacet)
  override def scaleXY(xOperand: Double, yOperand: Double): RectangleFill = RectangleFill(shape.scaleXY(xOperand, yOperand), fillFacet)
}

/** Companion object for RectangleFill, contains an Implementation class [[RectangleFill.RectangleFillImp]] and an apply factor method that delegates to it. It
 * also contains implicit instances for 2D geometric transformations. */
object RectangleFill
{ /** Factory apply method to construct a [[RectangleFill]] graphic element. */
  def apply(shape: Rectangle, fillFacet: FillFacet): RectangleFill = RectangleFillImp(shape, fillFacet)

  /** Implicit [[Slate]] type class instance / evidence for [[RectangleFill]]. */
  implicit val slateEv: Slate[RectangleFill] = (obj, operand) => obj.slate(operand)

  /** Implicit [[SlateXY]] type class instance / evidence for [[RectangleFill]]. */
  implicit val slateXYEv: SlateXY[RectangleFill] = (obj: RectangleFill, dx: Double, dy: Double) => obj.slate(dx, dy)

  /** Implicit [[Scale]] type class instance / evidence for [[RectangleFill]]. */
  implicit val scaleEv: Scale[RectangleFill] = (obj: RectangleFill, operand: Double) => obj.scale(operand)

  /** Implicit [[Rotate]] type class instance / evidence for [[RectangleFill]]. */
  implicit val rotateEv: Rotate[RectangleFill] = (obj: RectangleFill, angle: AngleVec) => obj.rotate(angle)

  /** Implicit [[Prolign]] type class instance / evidence for [[RectangleFill]]. */
  implicit val prolignEv: Prolign[RectangleFill] = (obj, matrix) => obj.prolign(matrix)
  
  /** Implicit [[TransAxes]] type class instance / evidence for [[RectangleFill]]. */
  implicit val transAxesEv: TransAxes[RectangleFill] = new TransAxes[RectangleFill]
  { override def negYT(obj: RectangleFill): RectangleFill = obj.negY
    override def negXT(obj: RectangleFill): RectangleFill = obj.negX
    override def rotate90(obj: RectangleFill): RectangleFill = obj.rotate90
    override def rotate180(obj: RectangleFill): RectangleFill = obj.rotate180
    override def rotate270(obj: RectangleFill): RectangleFill = obj.rotate270
  }
  
  /** Implementation class for the general case of a [[RectangleFill]]. */
  case class RectangleFillImp(shape: Rectangle, fillFacet: FillFacet) extends RectangleFill
}


/** A compound graphic for rectangles. */
trait RectangleCompound extends PolygonCompound with RectangleGraphic
{
  override def slate(operand: VecPt2): RectangleCompound = RectangleCompound(shape.slate(operand), facets, children.slate(operand))
  
  override def slate(xOperand: Double, yOperand: Double): RectangleCompound =
    RectangleCompound(shape.slate(xOperand, yOperand), facets, children.slateXY(xOperand, yOperand))
  
  override def scale(operand: Double): RectangleCompound = RectangleCompound(shape.scale(operand), facets, children.scale(operand))
  override def negX: RectangleCompound = RectangleCompound(shape.negX, facets, children.negX)
  override def negY: RectangleCompound = RectangleCompound(shape.negY, facets, children.negY)
  override def prolign(matrix: ProlignMatrix): RectangleCompound = RectangleCompound(shape.prolign(matrix), facets, children.prolign(matrix))
  override def rotate90: RectangleCompound = ???
  override def rotate(angle: AngleVec): RectangleCompound = RectangleCompound(shape.rotate(angle), facets, children.rotate(angle))
  override def reflect(lineLike: LineLike): RectangleCompound = ???
  override def scaleXY(xOperand: Double, yOperand: Double): RectangleCompound = ???
}

/** Companion object for RectangleCompound. Contains the [[RectangleCompound.RectangleCompoundImp]] implementation class for the general case of Rectangles and
 * an apply factor method that delegates to it. */
object RectangleCompound
{
  def apply(shape: Rectangle, facets: RArr[GraphicFacet], children: RArr[Graphic2Elem] = RArr()) : RectangleCompound =
    new RectangleCompoundImp(shape, facets, children)

  case class RectangleCompoundImp(shape: Rectangle, facets: RArr[GraphicFacet], children: RArr[Graphic2Elem] = RArr()) extends RectangleCompound, AxisFree
  { override type ThisT = RectangleCompoundImp
    override def slate(operand: VecPt2): RectangleCompoundImp = RectangleCompoundImp(shape.slate(operand), facets, children.slate(operand))
    
    override def slate(xOperand: Double, yOperand: Double): RectangleCompoundImp =
      RectangleCompoundImp(shape.slate(xOperand, yOperand), facets, children.slateXY(xOperand, yOperand))
    
    override def scale(operand: Double): RectangleCompoundImp = RectangleCompoundImp(shape.scale(operand), facets, children.scale(operand))
    override def prolign(matrix: ProlignMatrix): RectangleCompoundImp = RectangleCompoundImp(shape.prolign(matrix), facets, children.prolign(matrix))
    override def rotate(angle: AngleVec): RectangleCompoundImp = RectangleCompoundImp(shape.rotate(angle), facets, children.rotate(angle))
    override def reflect(lineLike: LineLike): RectangleCompoundImp = ???
    override def scaleXY(xOperand: Double, yOperand: Double): RectangleCompoundImp = ???
    override def shearX(operand: Double): PolygonCompound = ???
    override def shearY(operand: Double): PolygonCompound = ???
  }
}