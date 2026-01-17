/* Copyright 2025 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package geom
import pWeb.*, pgui.*, Colour.Black

/** Graphic based on a [[Square]] aligned to the X and Y axes. */
trait SqlignGraphic extends RectGraphic, SquareGraphic
{ override def shape: Sqlign
}

/** A simple Graphic based on a [[Square]] aligned to the X and Y axes. */
trait SqlignGraphicSimple extends RectGraphicSimple, SquareGraphicSimple, SqlignGraphic
{ override def slate(operand: VecPt2): SqlignGraphicSimple
  override def slate(xOperand: Double, yOperand: Double): SqlignGraphicSimple
  override def slateFrom(operand: VecPt2): SqlignGraphicSimple
  override def slateFrom(xOperand: Double, yOperand: Double): SqlignGraphicSimple
  override def slateX(xOperand: Double): SqlignGraphicSimple
  override def slateY(yOperand: Double): SqlignGraphicSimple
  override def scale(operand: Double): SqlignGraphicSimple
  override def negX: SqlignGraphicSimple
  override def negY: SqlignGraphicSimple
  override def rotate90: SqlignGraphicSimple
  override def rotate180: SqlignGraphicSimple
  override def rotate270: SqlignGraphicSimple
  override def prolign(matrix: AxlignMatrix): SqlignGraphicSimple
}

/** Graphic that draws a [[Sqlign]], a [[Square]] aligned to the X and Y axes. */
case class SqlignDraw (shape: Sqlign, lineWidth: Double = 2, lineColour: Colour = Black)extends RectDraw, SquareDraw, SqlignGraphicSimple
{ override def slate(operand: VecPt2): SqlignDraw = SqlignDraw(shape.slate(operand), lineWidth, lineColour)
  override def slate(xOperand: Double, yOperand: Double): SqlignDraw = SqlignDraw(shape.slate(xOperand, yOperand), lineWidth, lineColour)
  override def slateFrom(operand: VecPt2): SqlignDraw = SqlignDraw(shape.slateFrom(operand), lineWidth, lineColour)
  override def slateFrom(xOperand: Double, yOperand: Double): SqlignDraw = SqlignDraw(shape.slateFrom(xOperand, yOperand), lineWidth, lineColour)
  override def slateX(xOperand: Double): SqlignDraw = SqlignDraw(shape.slateX(xOperand), lineWidth, lineColour)
  override def slateY(yOperand: Double): SqlignDraw = SqlignDraw(shape.slateY(yOperand), lineWidth, lineColour)
  override def scale(operand: Double): SqlignDraw = SqlignDraw(shape.scale(operand), lineWidth, lineColour)
  override def negX: SqlignDraw = SqlignDraw(shape.negX, lineWidth, lineColour)
  override def negY: SqlignDraw = SqlignDraw(shape.negY, lineWidth, lineColour)
  override def rotate90: SqlignDraw = SqlignDraw(shape.rotate90, lineWidth, lineColour)
  override def rotate180: SqlignDraw = SqlignDraw(shape.rotate180, lineWidth, lineColour)
  override def rotate270: SqlignDraw = SqlignDraw(shape.rotate270, lineWidth, lineColour)
  override def prolign(matrix: AxlignMatrix): SqlignDraw = SqlignDraw(shape.prolign(matrix), lineWidth, lineColour)
}

/** A fill graphic for a square aligned to the X and Y axes. */
class SqlignFill(val shape: Sqlign, val fillFacet: FillFacet) extends SqlignGraphicSimple, RectFill, SquareFill
{ override def slate(operand: VecPt2): SqlignFill = SqlignFill(shape.slate(operand), fillFacet)
  override def slate(xOperand: Double, yOperand: Double): SqlignFill = SqlignFill(shape.slate(xOperand, yOperand), fillFacet)
  override def slateFrom(operand: VecPt2): SqlignFill = SqlignFill(shape.slateFrom(operand), fillFacet)
  override def slateFrom(xOperand: Double, yOperand: Double): SqlignFill = SqlignFill(shape.slateFrom(xOperand, yOperand), fillFacet)
  override def slateX(xOperand: Double): SqlignFill = SqlignFill(shape.slateX(xOperand), fillFacet)
  override def slateY(yOperand: Double): SqlignFill = SqlignFill(shape.slateY(yOperand), fillFacet)
  override def scale(operand: Double): SqlignFill = SqlignFill(shape.scale(operand), fillFacet)
  override def negX: SqlignFill = SqlignFill(shape.negX, fillFacet)
  override def negY: SqlignFill = SqlignFill(shape.negY, fillFacet)
  override def rotate90: SqlignFill = SqlignFill(shape.rotate90, fillFacet)
  override def rotate180: SqlignFill = SqlignFill(shape.rotate180, fillFacet)
  override def rotate270: SqlignFill = SqlignFill(shape.rotate270, fillFacet)
  override def prolign(matrix: AxlignMatrix): SqlignFill = SqlignFill(shape.prolign(matrix), fillFacet)
}

object SqlignFill
{ /** Factory apply method to create a [[Sqlign]] fill graphic. */
  def apply(shape: Sqlign, fillFacet: FillFacet): SqlignFill = new SqlignFill(shape, fillFacet)
}

/** A compound graphic based on a [[Sqlign]]. Can only execute limited geometric transformations, that preserve the [[Sqlign]] shape. Hence, it does not extend
 * [[SquareCompound]] or [[RectCompound]]. */
class SqlignCompound(val shape: Sqlign, val facets: RArr[GraphicFacet], val children: GraphicElems) extends
  SqlignGraphic, RectCompound
{ //def children: RArr[Graphic2Elem] = childs.flatMap(ch => ch(shape)) ++ adopted
  override def svgInline: SvgSvgRel = ???
  override def svgElems: RArr[SvgOwnLine] = ???
//  override def rendToCanvas(cp: CanvasPlatform): Unit = ???
  override def slate(operand: VecPt2): SqlignCompound = SqlignCompound(shape.slate(operand), facets, children.slate(operand))

  override def slate(xOperand: Double, yOperand: Double): SqlignCompound =
    SqlignCompound(shape.slate(xOperand, yOperand), facets, children.slate(xOperand, yOperand))

  override def slateFrom(operand: VecPt2): SqlignCompound = SqlignCompound(shape.slateFrom(operand), facets, children.slateFrom(operand))

  override def slateFrom(xOperand: Double, yOperand: Double): SqlignCompound =
    SqlignCompound(shape.slateFrom(xOperand, yOperand), facets, children.slateFrom(xOperand, yOperand))

  override def slateX(xOperand: Double): SqlignCompound = SqlignCompound(shape.slateX(xOperand), facets, children.slateX(xOperand))
  override def slateY(yOperand: Double): SqlignCompound = SqlignCompound(shape.slateY(yOperand), facets, children.slateY(yOperand))
  override def scale(operand: Double): SqlignCompound = SqlignCompound(shape.scale(operand), facets, children.scale(operand))
  override def negX: SqlignCompound = SqlignCompound(shape.negX, facets, children.negX)
  override def negY: SqlignCompound = SqlignCompound(shape.negY, facets, children.negY)
  override def rotate90: SqlignCompound = SqlignCompound(shape.rotate90, facets, children.rotate90)
  override def rotate180: SqlignCompound = SqlignCompound(shape.rotate180, facets, children.rotate180)
  override def rotate270: SqlignCompound = SqlignCompound(shape.rotate270, facets, children.rotate270)
  override def prolign(matrix: AxlignMatrix): SqlignCompound = SqlignCompound(shape.prolign(matrix), facets, children.prolign(matrix))
}

object SqlignCompound
{
  def apply(shape: Sqlign, facets: RArr[GraphicFacet], children: GraphicElems = RArr()): SqlignCompound = new SqlignCompound(shape, facets, children)

  def apply(shape: Sqlign, facets: GraphicFacet*)(children: Graphic2Elem*): SqlignCompound = new SqlignCompound(shape, facets.toRArr, children.toRArr)
}