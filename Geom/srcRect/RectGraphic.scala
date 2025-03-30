/* Copyright 2018-25 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package geom
import ostrat.Colour.Black, pWeb._

/** A graphic based on a [[Rect], a rectangle aligned to the X and Y axes. */
trait RectGraphic extends RectangleGraphic with ShapeGraphicOrdinaled
{ override def shape: Rect
}

/** A simple non-compound graphic based on a [[Rect], a rectangle aligned to the X and Y axes. */
trait RectGraphicSimple extends RectGraphic with RectangleGraphicSimple
{
  override def svgElem: SvgElem = SvgRect(attribs)
}

/** A rectangular Graphic aligned to the axes, filled with a single colour. */
trait RectFill extends RectGraphicSimple with RectangleFill
{ type ThisT <: RectFill
  override def slate(operand: VecPt2): RectFill
  override def slate(xDelta: Double, yDelta: Double): RectFill
  override def scale(operand: Double): RectFill
  override def negX: RectFill
  override def negY: RectFill
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
    override def slate(xDelta: Double, yDelta: Double): RectFillImp = RectFillImp(shape.slate(xDelta, yDelta), fillFacet)
    override def scale(operand: Double): RectFillImp = RectFillImp(shape.scale(operand), fillFacet)
    override def negX: RectFillImp = RectFillImp(shape.negX, fillFacet)
    override def negY: RectFillImp = RectFillImp(shape.negY, fillFacet)
    override def rotate90: RectFillImp = RectFillImp(shape.rotate90, fillFacet)
    override def rotate180: RectFillImp = RectFillImp(shape.rotate180, fillFacet)
    override def rotate270: RectFillImp = RectFillImp(shape.rotate270, fillFacet)
  }
}

/** A rectangular Graphic aligned to the axes, filled with a single colour. */
trait RectDraw extends RectGraphicSimple with RectangleDraw

/** Companion object for the [[RectDraw]] trait, contains a RectFillImp implementation class and an apply method that delegates to it. */
object RectDraw
{ /** Factory method for RectFill. A rectangular Graphic aligned to the axes, filled with a single colour. it delegates to the [[RectDrawImp]] implementation
   * class, but has a return type of RectFill. */
  def apply(rect: Rect, lineWidth: Double = 2, lineColour: Colour = Black): RectDraw = RectDrawImp(rect, lineWidth, lineColour)

  /** An implementation class for a [RectDraw]] that is not specified as a [[SquareDraw]]. */
  case class RectDrawImp(shape: Rect, lineWidth: Double = 2, lineColour: Colour = Black) extends RectDraw
}

/** This is a compound graphic based on a Rect shape. A rectangle aligned to the X and Y axes.  */
trait RectCompound extends RectGraphic with RectangleCompound
{
  override def shape: Rect

  /*override def svgElem: SvgRect = SvgRect(shape.negY.slateXY(0, boundingRect.bottom + boundingRect.top).
    attribs ++ facets.flatMap(_.attribs))*/
  override def mainSvgElem: SvgRect = SvgRect(attribs)
  /** Translate geometric transformation. */
  override def slate(xOperand: Double, yOperand: Double): RectCompound =
    RectCompound(shape.slate(xOperand, yOperand), facets, children.slateXY(xOperand, yOperand))

  /** Uniform scaling transformation. The scale name was chosen for this operation as it is normally the desired operation and preserves Circles and
   * Squares. Use the xyScale method for differential scaling. */
  override def scale(operand: Double): RectCompound = RectCompound(shape.scale(operand), facets, children.scale(operand))

  /** Mirror, reflection transformation across the X axis. This method has been left abstract in GeomElemNew to allow the return type to be narrowed
   * in sub classes. */
  override def negY: RectCompound = RectCompound(shape.negY, facets, children.negY)

  /** Mirror, reflection transformation across the X axis. This method has been left abstract in GeomElemNew to allow the return type to be narrowed
   * in sub classes. */
  override def negX: RectCompound = RectCompound(shape.negX, facets, children.negX)

  override def rotate90: RectCompound = RectCompound(shape.rotate90, facets, children.rotate90)
  override def rotate180: RectCompound = RectCompound(shape.rotate180, facets, children.rotate180)
  override def rotate270: RectCompound = RectCompound(shape.rotate270, facets, children.rotate270)

  override def prolign(matrix: ProlignMatrix): RectCompound = RectCompound(shape.prolign(matrix), facets, children.prolign(matrix))

  override def scaleXY(xOperand: Double, yOperand: Double): RectCompound =
    RectCompound(shape.scaleXY(xOperand, yOperand), facets, children.scaleXY(xOperand, yOperand) )

  override def addChildren(newChildren: Arr[Graphic2Elem]): RectCompound = RectCompound(shape, facets, children ++ newChildren)

  def htmlSvg: HtmlSvg =
  { val atts = RArr(WidthAtt(shape.width), HeightAtt(shape.height), ViewBox(shape.left, -shape.top, shape.width, shape.height), CentreBlockAtt)
    val svgElems = children.flatMap(_.svgElems)
    new HtmlSvg(svgElems, atts)
  }
}

/** Companion object for the RectCompound trait, contains implicit instances for 2D geometric transformation type classes. */
object RectCompound
{
  def apply(shape: Rect, facets: RArr[GraphicFacet], children: RArr[Graphic2Elem] = RArr()): RectCompound =
    RectCompoundImp(shape, facets, children)

  implicit val slateImplicit: SlateXY[RectCompound] = (obj: RectCompound, dx: Double, dy: Double) => obj.slate(dx, dy)
  implicit val scaleImplicit: Scale[RectCompound] = (obj: RectCompound, operand: Double) => obj.scale(operand)
  implicit val XYScaleImplicit: ScaleXY[RectCompound] = (obj, xOperand, yOperand) => obj.scaleXY(xOperand, yOperand)
  implicit val prolignImplicit: Prolign[RectCompound] = (obj, matrix) => obj.prolign(matrix)

  implicit val reflectAxesImplicit: TransAxes[RectCompound] = new TransAxes[RectCompound]
  { override def negYT(obj: RectCompound): RectCompound = obj.negY
    override def negXT(obj: RectCompound): RectCompound = obj.negX
    override def rotate90(obj: RectCompound): RectCompound = obj.rotate90
    override def rotate180(obj: RectCompound): RectCompound = obj.rotate180
    override def rotate270(obj: RectCompound): RectCompound = obj.rotate270
  }

  case class RectCompoundImp(shape: Rect, facets: RArr[GraphicFacet], children: RArr[Graphic2Elem] = RArr()) extends RectCompound
}