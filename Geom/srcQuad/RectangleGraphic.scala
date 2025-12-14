/* Copyright 2018-23 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package geom
import Colour.*, pgui.*

trait RectangleGraphic extends PolygonGraphic, ShapeGraphicCentred
{ override def shape: Rectangle
  override def slate(operand: VecPt2): RectangleGraphic
  override def slate(xOperand: Double, yOperand: Double): RectangleGraphic
  override def scale(operand: Double): RectangleGraphic
}

trait RectangleGraphicSimple extends PolygonGraphicSimple, RectangleGraphic
{ override def slate(operand: VecPt2): _root_.ostrat.geom.RectangleGraphicSimple
  override def slate(xOperand: Double, yOperand: Double): RectangleGraphicSimple
  override def scale(operand: Double): RectangleGraphicSimple
  override def negX: RectangleGraphicSimple
  override def negY: RectangleGraphicSimple
  override def rotate90: RectangleGraphicSimple
  override def rotate180: RectangleGraphicSimple
  override def rotate270: RectangleGraphicSimple
}

/** Graphic that draws a rectangle. */
trait RectangleDraw extends PolygonDraw with RectangleGraphicSimple
{ override def slate(operand: VecPt2): RectangleDraw = RectangleDraw(shape.slate(operand), lineWidth, lineColour)
  override def slate(xOperand: Double, yOperand: Double): RectangleDraw = RectangleDraw(shape.slate(xOperand, yOperand), lineWidth, lineColour)
  override def scale(operand: Double): RectangleDraw = RectangleDraw(shape.scale(operand), lineWidth, lineColour)
  override def negX: RectangleDraw = RectangleDraw(shape.negX, lineWidth, lineColour)
  override def negY: RectangleDraw = RectangleDraw(shape.negY, lineWidth, lineColour)
  override def rotate90: RectangleDraw = RectangleDraw(shape.rotate90, lineWidth, lineColour)
  override def rotate180: RectangleDraw = RectangleDraw(shape.rotate180, lineWidth, lineColour)
  override def rotate270: RectangleDraw = RectangleDraw(shape.rotate270, lineWidth, lineColour)
  override def prolign(matrix: AxlignMatrix): RectangleDraw = RectangleDraw(shape.prolign(matrix), lineWidth, lineColour)
  override def rotate(rotation: AngleVec): RectangleDraw = RectangleDraw(shape.rotate(rotation), lineWidth, lineColour)
  override def mirror(lineLike: LineLike): RectangleDraw = RectangleDraw(shape.mirror(lineLike), lineWidth, lineColour)
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
{ override def slate(operand: VecPt2): RectangleFill
  override def slate(xOperand: Double, yOperand: Double): RectangleFill
  override def slateX(xOperand: Double): RectangleFill
  override def slateY(yOperand: Double): RectangleFill
  override def scale(operand: Double): RectangleFill
  override def negY: RectangleFill
  override def negX: RectangleFill
  override def rotate90: RectangleFill
  override def rotate180: RectangleFill
  override def rotate270: RectangleFill
  override def prolign(matrix: AxlignMatrix): RectangleFill
  override def rotate(rotation: AngleVec): RectangleFill
  override def mirror(lineLike: LineLike): RectangleFill
  override def shearX(operand: Double): PolygonFill = PolygonFill(shape.shearX(operand), fillFacet)
  override def shearY(operand: Double): PolygonFill = PolygonFill(shape.shearY(operand), fillFacet)  
  override def scaleXY(xOperand: Double, yOperand: Double): RectangleFill = RectangleFill(shape.scaleXY(xOperand, yOperand), fillFacet)
}

/** Companion object for RectangleFill, contains an Implementation class [[RectangleFill.RectangleFillGen]] and an apply factor method that delegates to it. It
 * also contains implicit instances for 2D geometric transformations. */
object RectangleFill
{ /** Factory apply method to construct a [[RectangleFill]] graphic element. */
  def apply(shape: Rectangle, fillFacet: FillFacet): RectangleFill = RectangleFillGen(shape, fillFacet)

  /** Implicit [[Slate2]] type class instance / evidence for [[RectangleFill]]. */
  given slate2Ev: Slate2[RectangleFill] = new Slate2[RectangleFill]
  { override def slate(obj: RectangleFill, operand: VecPt2): RectangleFill = obj.slate(operand)
    override def slateXY(obj: RectangleFill, xOperand: Double, yOperand: Double): RectangleFill = obj.slate(xOperand, yOperand)
    override def slateX(obj: RectangleFill, xOperand: Double): RectangleFill = obj.slateX(xOperand)
    override def slateY(obj: RectangleFill, yOperand: Double): RectangleFill = obj.slateY(yOperand)
  }

  /** Implicit [[Scale]] type class instance / evidence for [[RectangleFill]]. */
  given scaleEv: Scale[RectangleFill] = (obj: RectangleFill, operand: Double) => obj.scale(operand)

  /** Implicit [[Rotate]] type class instance / evidence for [[RectangleFill]]. */
  given rotateEv: Rotate[RectangleFill] = (obj: RectangleFill, angle: AngleVec) => obj.rotate(angle)

  /** Implicit [[Prolign]] type class instance / evidence for [[RectangleFill]]. */
  given prolignEv: Prolign[RectangleFill] = (obj, matrix) => obj.prolign(matrix)
  
  /** Implicit [[TransAxes]] type class instance / evidence for [[RectangleFill]]. */
  given transAxesEv: TransAxes[RectangleFill] = new TransAxes[RectangleFill]
  { override def negYT(obj: RectangleFill): RectangleFill = obj.negY
    override def negXT(obj: RectangleFill): RectangleFill = obj.negX
    override def rotate90(obj: RectangleFill): RectangleFill = obj.rotate90
    override def rotate180(obj: RectangleFill): RectangleFill = obj.rotate180
    override def rotate270(obj: RectangleFill): RectangleFill = obj.rotate270
  }
  
  /** Implementation class for the general case of a [[RectangleFill]]. */
  case class RectangleFillGen(shape: Rectangle, fillFacet: FillFacet) extends RectangleFill
  { override def slate(operand: VecPt2): RectangleFillGen = RectangleFillGen(shape.slate(operand), fillFacet)
    override def slate(xOperand: Double, yOperand: Double): RectangleFillGen = RectangleFillGen(shape.slate(xOperand, yOperand), fillFacet)
    override def slateX(xOperand: Double): RectangleFillGen = RectangleFillGen(shape.slateX(xOperand), fillFacet)
    override def slateY(yOperand: Double): RectangleFillGen = RectangleFillGen(shape.slateY(yOperand), fillFacet)
    override def scale(operand: Double): RectangleFillGen = RectangleFillGen(shape.scale(operand), fillFacet)
    override def negX: RectangleFillGen = RectangleFillGen(shape.negX, fillFacet)
    override def negY: RectangleFillGen = RectangleFillGen(shape.negY, fillFacet)
    override def rotate90: RectangleFillGen = RectangleFillGen(shape.rotate90, fillFacet)
    override def rotate180: RectangleFillGen = RectangleFillGen(shape.rotate180, fillFacet)
    override def rotate270: RectangleFillGen = RectangleFillGen(shape.rotate270, fillFacet)
    override def prolign(matrix: AxlignMatrix): RectangleFillGen = RectangleFillGen(shape.prolign(matrix), fillFacet)
    override def rotate(rotation: AngleVec): RectangleFillGen = RectangleFillGen(shape.rotate(rotation), fillFacet)
    override def mirror(lineLike: LineLike): RectangleFill = RectangleFill(shape.mirror(lineLike), fillFacet)
  }
}

/** A compound graphic for rectangles. */
trait RectangleCompound extends PolygonCompound with RectangleGraphic
{ override def slate(operand: VecPt2): RectangleCompound = RectangleCompound(shape.slate(operand), facets, children.slate(operand))
  
  override def slate(xOperand: Double, yOperand: Double): RectangleCompound =
    RectangleCompound(shape.slate(xOperand, yOperand), facets, children.slate(xOperand, yOperand))
  
  override def scale(operand: Double): RectangleCompound = RectangleCompound(shape.scale(operand), facets, children.scale(operand))
  override def negX: RectangleCompound = RectangleCompound(shape.negX, facets, children.negX)
  override def negY: RectangleCompound = RectangleCompound(shape.negY, facets, children.negY)
  override def prolign(matrix: AxlignMatrix): RectangleCompound = RectangleCompound(shape.prolign(matrix), facets, children.prolign(matrix))
  override def rotate90: RectangleCompound = ???
  override def rotate(rotation: AngleVec): RectangleCompound = RectangleCompound(shape.rotate(rotation), facets, children.rotate(rotation))
  override def mirror(lineLike: LineLike): RectangleCompound = ???
  override def scaleXY(xOperand: Double, yOperand: Double): RectangleCompound = ???
  override def shearX(operand: Double): PolygonCompound = ???
  override def shearY(operand: Double): PolygonCompound = ???
}

/** Companion object for RectangleCompound. Contains the [[RectangleCompound.RectangleCompoundImp]] implementation class for the general case of Rectangles and
 * an apply factor method that delegates to it. */
object RectangleCompound
{ /** Factory apply method to construct a [[RectangleCompound]]. */
  def apply(shape: Rectangle, facets: RArr[GraphicFacet], children: RArr[Graphic2Elem] = RArr()) : RectangleCompound =
    new RectangleCompoundImp(shape, facets, children)

  /** Implementation class for the general case of [[RectangleCompound]]. */
  case class RectangleCompoundImp(shape: Rectangle, facets: RArr[GraphicFacet], children: RArr[Graphic2Elem] = RArr()) extends RectangleCompound, AxisFree
  { override type ThisT = RectangleCompoundImp
    override def slate(operand: VecPt2): RectangleCompoundImp = RectangleCompoundImp(shape.slate(operand), facets, children.slate(operand))
    
    override def slate(xOperand: Double, yOperand: Double): RectangleCompoundImp =
      RectangleCompoundImp(shape.slate(xOperand, yOperand), facets, children.slate(xOperand, yOperand))
    
    override def scale(operand: Double): RectangleCompoundImp = RectangleCompoundImp(shape.scale(operand), facets, children.scale(operand))
    override def prolign(matrix: AxlignMatrix): RectangleCompoundImp = RectangleCompoundImp(shape.prolign(matrix), facets, children.prolign(matrix))
    override def rotate(rotation: AngleVec): RectangleCompoundImp = RectangleCompoundImp(shape.rotate(rotation), facets, children.rotate(rotation))
    override def mirror(lineLike: LineLike): RectangleCompoundImp = ???
    override def scaleXY(xOperand: Double, yOperand: Double): RectangleCompoundImp = ???
    override def shearX(operand: Double): PolygonCompound = ???
    override def shearY(operand: Double): PolygonCompound = ???
  }
}