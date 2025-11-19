/* Copyright 2018-25 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package geom
import ostrat.Colour.Black, pWeb.*

/** A graphic based on a [[Rect], a rectangle aligned to the X and Y axes. */
trait RectGraphic extends RectangleGraphic, ShapeGraphicOrdinaled
{ override def shape: Rect
}

/** A simple non-compound graphic based on a [[Rect], a rectangle aligned to the X and Y axes. */
trait RectGraphicSimple extends RectGraphic, RectangleGraphicSimple
{ override def svgElem: SvgOwnLine = SvgRect(attribs)
}

/** A rectangular Graphic aligned to the axes, filled with a single colour. */
trait RectFill extends RectGraphicSimple, RectangleFill
{ type ThisT <: RectFill
  override def slate(operand: VecPt2): RectFill
  override def slate(xOperand: Double, yOperand: Double): RectFill
  override def slateX(xOperand: Double): RectFill
  override def slateY(yOperand: Double): RectFill
  override def scale(operand: Double): RectFill
  override def negX: RectFill
  override def negY: RectFill
  override def rotate90: RectFill
  override def rotate180: RectFill
  override def rotate270: RectFill
  override def prolign(matrix: AxlignMatrix): RectFill
}

/** Companion object for the RectFill trait, contains a RectFillImp implementation class and an apply method that delegates to it. */
object RectFill
{ /** Factory method for RectFill. A rectangular Graphic aligned to the axes, filled with a single colour. it delegates to the [[RectFillImp]] implementation
   * class, but has a return type of RectFill. */
  def apply(rect: Rect, fillFacet: FillFacet): RectFill = RectFillImp(rect, fillFacet)

  /** An implementation class for a [[RectFill]] that is not specified as a [[SquareFill]]. */
  case class RectFillImp(shape: Rect, fillFacet: FillFacet) extends RectFill
  { override type ThisT = RectFillImp
    override def slate(operand: VecPt2): RectFillImp = RectFillImp(shape.slate(operand), fillFacet)
    override def slate(xOperand: Double, yOperand: Double): RectFillImp = RectFillImp(shape.slate(xOperand, yOperand), fillFacet)
    override def slateX(xOperand: Double): RectFillImp = RectFillImp(shape.slateX(xOperand), fillFacet)
    override def slateY(yOperand: Double): RectFillImp = RectFillImp(shape.slateY(yOperand), fillFacet)
    override def scale(operand: Double): RectFillImp = RectFillImp(shape.scale(operand), fillFacet)
    override def negX: RectFillImp = RectFillImp(shape.negX, fillFacet)
    override def negY: RectFillImp = RectFillImp(shape.negY, fillFacet)
    override def rotate90: RectFillImp = RectFillImp(shape.rotate90, fillFacet)
    override def rotate180: RectFillImp = RectFillImp(shape.rotate180, fillFacet)
    override def rotate270: RectFillImp = RectFillImp(shape.rotate270, fillFacet)
    override def prolign(matrix: AxlignMatrix): RectFillImp = RectFillImp(shape.prolign(matrix), fillFacet)
    override def reflect(lineLike: LineLike): RectangleFill = RectangleFill(shape.reflect(lineLike), fillFacet)
    override def rotate(rotation: AngleVec): RectangleFill = RectangleFill(shape.rotate(rotation), fillFacet)
  }
}

/** A rectangular Graphic aligned to the axes, filled with a single colour. */
trait RectDraw extends RectGraphicSimple, RectangleDraw

/** Companion object for the [[RectDraw]] trait, contains a RectFillImp implementation class and an apply method that delegates to it. */
object RectDraw
{ /** Factory method for RectFill. A rectangular Graphic aligned to the axes, filled with a single colour. it delegates to the [[RectDrawImp]] implementation
   * class, but has a return type of RectFill. */
  def apply(rect: Rect, lineWidth: Double = 2, lineColour: Colour = Black): RectDraw = RectDrawImp(rect, lineWidth, lineColour)

  /** An implementation class for a [RectDraw]] that is not specified as a [[SquareDraw]]. */
  case class RectDrawImp(shape: Rect, lineWidth: Double = 2, lineColour: Colour = Black) extends RectDraw
}

/** This is a compound graphic based on a Rect shape. A rectangle aligned to the X and Y axes.  */
trait RectCompound extends RectGraphic, RectangleCompound
{
  override def shape: Rect

  /*override def svgElem: SvgRect = SvgRect(shape.negY.slateXY(0, boundingRect.bottom + boundingRect.top).
    attribs ++ facets.flatMap(_.attribs))*/
  override def mainSvgElem: SvgRect = SvgRect(attribs)

  override def slate(operand: VecPt2): RectCompound = RectCompound(shape.slate(operand), facets, children.slate(operand))
  
  override def slate(xOperand: Double, yOperand: Double): RectCompound =
    RectCompound(shape.slate(xOperand, yOperand), facets, children.slate(xOperand, yOperand))

  override def slateX(xOperand: Double): RectCompound = RectCompound(shape.slateX(xOperand), facets, children.slateX(xOperand))
  override def slateY(yOperand: Double): RectCompound = RectCompound(shape.slateY(yOperand), facets, children.slateY(yOperand))  
  override def scale(operand: Double): RectCompound = RectCompound(shape.scale(operand), facets, children.scale(operand))
  override def negX: RectCompound = RectCompound(shape.negX, facets, children.negX)
  override def negY: RectCompound = RectCompound(shape.negY, facets, children.negY)
  override def rotate90: RectCompound = RectCompound(shape.rotate90, facets, children.rotate90)
  override def rotate180: RectCompound = RectCompound(shape.rotate180, facets, children.rotate180)
  override def rotate270: RectCompound = RectCompound(shape.rotate270, facets, children.rotate270)
  override def prolign(matrix: AxlignMatrix): RectCompound = RectCompound(shape.prolign(matrix), facets, children.prolign(matrix))

  override def scaleXY(xOperand: Double, yOperand: Double): RectCompound =
    RectCompound(shape.scaleXY(xOperand, yOperand), facets, children.scaleXY(xOperand, yOperand) )

  override def addChildren(newChildren: Arr[Graphic2Elem]): RectCompound = RectCompound(shape, facets, children ++ newChildren)

  def htmlSvg: SvgSvgRel =
  { val atts = RArr(WidthAtt(shape.width), HeightAtt(shape.height), CentreBlockAtt)
    val svgElems = children.flatMap(_.svgElems)
    new SvgSvgRel(shape.left, -shape.top, shape.width, shape.height, svgElems, atts)
  }
}

/** Companion object for the RectCompound trait, contains implicit instances for 2D geometric transformation type classes. */
object RectCompound
{
  def apply(shape: Rect, facets: RArr[GraphicFacet], children: RArr[Graphic2Elem] = RArr()): RectCompound =
    RectCompoundImp(shape, facets, children)

  /** [[Slate2]] type class instance / evidence for [[RectCompound]]. */
  given slate2Ev: Slate2[RectCompound] = new Slate2[RectCompound]
  { override def slate(obj: RectCompound, operand: VecPt2): RectCompound = obj.slate(operand)
    override def slateXY(obj: RectCompound, xOperand: Double, yOperand: Double): RectCompound = obj.slate(xOperand, yOperand)
    override def slateX(obj: RectCompound, xOperand: Double): RectCompound = obj.slateX(xOperand)
    override def slateY(obj: RectCompound, yOperand: Double): RectCompound = obj.slateY(yOperand)
  }
  /** [[Scale]] type class instance / evidence for [[RectCompound]]. */
  given scaleEv: Scale[RectCompound] = (obj: RectCompound, operand: Double) => obj.scale(operand)
  
  /** [[ScaleXY]] type class instance / evidence for [[RectCompound]]. */
  given scaleXYEv: ScaleXY[RectCompound] = (obj, xOperand, yOperand) => obj.scaleXY(xOperand, yOperand)
  
  /** [[Prolign]] type class instance / evidence for [[RectCompound]]. */
  given prolignEv: Prolign[RectCompound] = (obj, matrix) => obj.prolign(matrix)

  /** [[TransAxed]] type class instance / evidence for [[RectCompound]]. */
  given transAxesEv: TransAxes[RectCompound] = new TransAxes[RectCompound]
  { override def negYT(obj: RectCompound): RectCompound = obj.negY
    override def negXT(obj: RectCompound): RectCompound = obj.negX
    override def rotate90(obj: RectCompound): RectCompound = obj.rotate90
    override def rotate180(obj: RectCompound): RectCompound = obj.rotate180
    override def rotate270(obj: RectCompound): RectCompound = obj.rotate270
  }

  case class RectCompoundImp(shape: Rect, facets: RArr[GraphicFacet], children: RArr[Graphic2Elem] = RArr()) extends RectCompound
}