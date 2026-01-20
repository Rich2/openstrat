/* Copyright 2018-25 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package geom
import Colour.*, pgui.*

trait RectangleGraphic extends QuadGraphic, ShapeGraphicCentred
{ override def shape: Rectangle
  override def slate(operand: VecPt2): RectangleGraphic
  override def slate(xOperand: Double, yOperand: Double): RectangleGraphic
  override def scale(operand: Double): RectangleGraphic
}

trait RectangleGraphicSimple extends QuadGraphicSimple, RectangleGraphic
{ override def slate(operand: VecPt2): RectangleGraphicSimple
  override def slate(xOperand: Double, yOperand: Double): RectangleGraphicSimple
  override def scale(operand: Double): RectangleGraphicSimple
  override def negX: RectangleGraphicSimple
  override def negY: RectangleGraphicSimple
  override def rotate90: RectangleGraphicSimple
  override def rotate180: RectangleGraphicSimple
  override def rotate270: RectangleGraphicSimple
}

/** Graphic that draws a rectangle. */
trait RectangleDraw extends QuadDraw, RectangleGraphicSimple
{ override def slate(operand: VecPt2): RectangleDraw = RectangleDraw(shape.slate(operand), lineWidth, lineColour)
  override def slate(xOperand: Double, yOperand: Double): RectangleDraw = RectangleDraw(shape.slate(xOperand, yOperand), lineWidth, lineColour)
  override def slateFrom(operand: VecPt2): RectangleDraw = RectangleDraw(shape.slateFrom(operand), lineWidth, lineColour)
  override def slateFrom(xOperand: Double, yOperand: Double): RectangleDraw = RectangleDraw(shape.slateFrom(xOperand, yOperand), lineWidth, lineColour)
  override def slateX(xOperand: Double): RectangleDraw = RectangleDraw(shape.slateX(xOperand), lineWidth, lineColour)
  override def slateY(yOperand: Double): RectangleDraw = RectangleDraw(shape.slateY(yOperand), lineWidth, lineColour)
  override def scale(operand: Double): RectangleDraw = RectangleDraw(shape.scale(operand), lineWidth, lineColour)
  override def negX: RectangleDraw = RectangleDraw(shape.negX, lineWidth, lineColour)
  override def negY: RectangleDraw = RectangleDraw(shape.negY, lineWidth, lineColour)
  override def rotate90: RectangleDraw = RectangleDraw(shape.rotate90, lineWidth, lineColour)
  override def rotate180: RectangleDraw = RectangleDraw(shape.rotate180, lineWidth, lineColour)
  override def rotate270: RectangleDraw = RectangleDraw(shape.rotate270, lineWidth, lineColour)
  override def prolign(matrix: AxlignMatrix): RectangleDraw = RectangleDraw(shape.prolign(matrix), lineWidth, lineColour)
  override def rotate(rotation: AngleVec): RectangleDraw = RectangleDraw(shape.rotate(rotation), lineWidth, lineColour)
  override def mirror(lineLike: LineLike): RectangleDraw = RectangleDraw(shape.mirror(lineLike), lineWidth, lineColour)
}

/** Companion object for RectangleDraw contains factory method and implementation class. */
object RectangleDraw
{
  def apply(shape: Rectangle, lineWidth: Double = 2, lineColour: Colour = Black): RectangleDraw = RectangleDrawGen(shape, lineWidth, lineColour)

  /** Implicit [[Slate2]] type class instance / evidence for [[RectangleDraw]]. */
  given slate2Ev: Slate2[RectangleDraw] = new Slate2[RectangleDraw]
  { override def slate(obj: RectangleDraw, operand: VecPt2): RectangleDraw = obj.slate(operand)
    override def slateXY(obj: RectangleDraw, xOperand: Double, yOperand: Double): RectangleDraw = obj.slate(xOperand, yOperand)
    override def slateFrom(obj: RectangleDraw, operand: VecPt2): RectangleDraw = obj.slateFrom(operand)
    override def slateFromXY(obj: RectangleDraw, xOperand: Double, yOperand: Double): RectangleDraw = obj.slateFrom(xOperand, yOperand)
    override def slateX(obj: RectangleDraw, xOperand: Double): RectangleDraw = obj.slateX(xOperand)
    override def slateY(obj: RectangleDraw, yOperand: Double): RectangleDraw = obj.slateY(yOperand)
  }

  /** Implicit [[Scale]] type class instance / evidence for [[RectangleDraw]]. */
  given scaleEv: Scale[RectangleDraw] = (obj: RectangleDraw, operand: Double) => obj.scale(operand)

  /** Implicit [[Prolgn]] type class instance / evidence for [[RectangleDraw]]. */
  given prolignEv: Prolign[RectangleDraw] = (obj, matrix) => obj.prolign(matrix)

  /** Implicit [[Rotate]] type class instance / evidence for [[RectangleDraw]]. */
  given rotateEv: Rotate[RectangleDraw] = (obj: RectangleDraw, angle: AngleVec) => obj.rotate(angle)

  /** Implicit [[MirrorAxes]] type class instance / evidence for [[RectangleDraw]]. */
  given transAxesEv: MirrorAxes[RectangleDraw] = new MirrorAxes[RectangleDraw]
  { override def negXT(obj: RectangleDraw): RectangleDraw = obj.negX
    override def negYT(obj: RectangleDraw): RectangleDraw = obj.negY
    override def rotate90(obj: RectangleDraw): RectangleDraw = obj.rotate90
    override def rotate180(obj: RectangleDraw): RectangleDraw = obj.rotate180
    override def rotate270(obj: RectangleDraw): RectangleDraw = obj.rotate270
  }
  
  /** Implementation class for the general case pf [[Rectangle]] draw graphic. */
  case class RectangleDrawGen(shape: Rectangle, lineWidth: Double = 2, lineColour: Colour = Black) extends RectangleDraw
  { override def rendToCanvas(cp: CanvasPlatform): Unit = cp.polygonDraw(this)
  }
}

/** Graphic to fill a [[Rectangle]] with a single colour. */
trait RectangleFill extends QuadFill, RectangleGraphicSimple
{ override def slate(operand: VecPt2): RectangleFill
  override def slate(xOperand: Double, yOperand: Double): RectangleFill
  override def slateFrom(operand: VecPt2): RectangleFill
  override def slateFrom(xOperand: Double, yOperand: Double): RectangleFill
  override def slateX(xOperand: Double): RectangleFill
  override def slateY(yOperand: Double): RectangleFill
  override def scale(operand: Double): RectangleFill
  override def negY: RectangleFill
  override def negX: RectangleFill
  override def rotate90: RectangleFill
  override def rotate180: RectangleFill
  override def rotate270: RectangleFill
  override def prolign(matrix: AxlignMatrix): RectangleFill
  override def rotate(rotation: AngleVec): RectangleFill = RectangleFill(shape.rotate(rotation), fillFacet)
  override def mirror(lineLike: LineLike): RectangleFill = RectangleFill(shape.mirror(lineLike), fillFacet)
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
    override def slateFrom(obj: RectangleFill, operand: VecPt2): RectangleFill = obj.slateFrom(operand)
    override def slateFromXY(obj: RectangleFill, xOperand: Double, yOperand: Double): RectangleFill = obj.slateFrom(xOperand, yOperand)
    override def slateX(obj: RectangleFill, xOperand: Double): RectangleFill = obj.slateX(xOperand)
    override def slateY(obj: RectangleFill, yOperand: Double): RectangleFill = obj.slateY(yOperand)
  }

  /** Implicit [[Scale]] type class instance / evidence for [[RectangleFill]]. */
  given scaleEv: Scale[RectangleFill] = (obj: RectangleFill, operand: Double) => obj.scale(operand)

  /** Implicit [[Rotate]] type class instance / evidence for [[RectangleFill]]. */
  given rotateEv: Rotate[RectangleFill] = (obj: RectangleFill, angle: AngleVec) => obj.rotate(angle)

  /** Implicit [[Prolign]] type class instance / evidence for [[RectangleFill]]. */
  given prolignEv: Prolign[RectangleFill] = (obj, matrix) => obj.prolign(matrix)
  
  /** Implicit [[MirrorAxes]] type class instance / evidence for [[RectangleFill]]. */
  given transAxesEv: MirrorAxes[RectangleFill] = new MirrorAxes[RectangleFill]
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
    override def slateFrom(operand: VecPt2): RectangleFillGen = RectangleFillGen(shape.slateFrom(operand), fillFacet)
    override def slateFrom(xOperand: Double, yOperand: Double): RectangleFillGen = RectangleFillGen(shape.slateFrom(xOperand, yOperand), fillFacet)
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
trait RectangleCompound extends QuadCompound, RectangleGraphic
{ override def slate(operand: VecPt2): RectangleCompound = RectangleCompound(shape.slate(operand), facets, children.slate(operand))
  
  override def slate(xOperand: Double, yOperand: Double): RectangleCompound =
    RectangleCompound(shape.slate(xOperand, yOperand), facets, children.slate(xOperand, yOperand))

  override def slateFrom(operand: VecPt2): RectangleCompound = RectangleCompound(shape.slateFrom(operand), facets, children.slateFrom(operand))

  override def slateFrom(xOperand: Double, yOperand: Double): RectangleCompound =
    RectangleCompound(shape.slateFrom(xOperand, yOperand), facets, children.slateFrom(xOperand, yOperand))

  override def slateX(xOperand: Double): RectangleCompound = RectangleCompound(shape.slateX(xOperand), facets, children.slateX(xOperand))
  override def slateY(yOperand: Double): RectangleCompound = RectangleCompound(shape.slateY(yOperand), facets, children.slateY(yOperand))
  override def scale(operand: Double): RectangleCompound = RectangleCompound(shape.scale(operand), facets, children.scale(operand))
  override def negX: RectangleCompound = RectangleCompound(shape.negX, facets, children.negX)
  override def negY: RectangleCompound = RectangleCompound(shape.negY, facets, children.negY)
  override def prolign(matrix: AxlignMatrix): RectangleCompound = RectangleCompound(shape.prolign(matrix), facets, children.prolign(matrix))
  override def rotate90: RectangleCompound = RectangleCompound(shape.rotate90, facets, children.rotate90)
  override def rotate180: RectangleCompound = RectangleCompound(shape.rotate180, facets, children.rotate180)
  override def rotate270: RectangleCompound = RectangleCompound(shape.rotate270, facets, children.rotate270)
  override def rotate(rotation: AngleVec): RectangleCompound = RectangleCompound(shape.rotate(rotation), facets, children.rotate(rotation))
  override def mirror(lineLike: LineLike): RectangleCompound = RectangleCompound(shape.mirror(lineLike), facets, children.mirror(lineLike))
}

/** Companion object for RectangleCompound. Contains the [[RectangleCompound.RectangleCompoundGen]] implementation class for the general case of Rectangles and
 * an apply factor method that delegates to it. */
object RectangleCompound
{ /** Factory apply method to construct a [[RectangleCompound]]. */
  def apply(shape: Rectangle, facets: RArr[GraphicFacet], children: RArr[Graphic2Elem] = RArr()) : RectangleCompound =
    new RectangleCompoundGen(shape, facets, children)

  /** Implementation class for the general case of [[RectangleCompound]]. */
  case class RectangleCompoundGen(shape: Rectangle, facets: RArr[GraphicFacet], children: RArr[Graphic2Elem] = RArr()) extends RectangleCompound, AxisFree
  { override type ThisT = RectangleCompoundGen
    override def slate(operand: VecPt2): RectangleCompoundGen = RectangleCompoundGen(shape.slate(operand), facets, children.slate(operand))
    
    override def slate(xOperand: Double, yOperand: Double): RectangleCompoundGen =
      RectangleCompoundGen(shape.slate(xOperand, yOperand), facets, children.slate(xOperand, yOperand))

    override def slateFrom(operand: VecPt2): RectangleCompoundGen = RectangleCompoundGen(shape.slateFrom(operand), facets, children.slateFrom(operand))

    override def slateFrom(xOperand: Double, yOperand: Double): RectangleCompoundGen =
      RectangleCompoundGen(shape.slateFrom(xOperand, yOperand), facets, children.slateFrom(xOperand, yOperand))
    
    override def scale(operand: Double): RectangleCompoundGen = RectangleCompoundGen(shape.scale(operand), facets, children.scale(operand))
    override def prolign(matrix: AxlignMatrix): RectangleCompoundGen = RectangleCompoundGen(shape.prolign(matrix), facets, children.prolign(matrix))
    override def rotate(rotation: AngleVec): RectangleCompoundGen = RectangleCompoundGen(shape.rotate(rotation), facets, children.rotate(rotation))
    override def mirror(lineLike: LineLike): RectangleCompoundGen = RectangleCompoundGen(shape.mirror(lineLike), facets, children.mirror(lineLike))
  }
}