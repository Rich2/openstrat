/* Copyright 2025 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package geom
import ostrat.pWeb.*, pgui.*

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
{
  def apply(shape: Sqlign, fillFacet: FillFacet): SqlignFill = new SqlignFill(shape, fillFacet)
}

/** A compound graphic based on a [[Sqlign]]. Can only execute limited geometric transformations, that preserve the [[Sqlign]] shape. Hence, it does not extend
 * [[SquareCompound]] or [[RectCompound]]. */
class SqlignCompound(val shape: Sqlign, val facets: RArr[GraphicFacet], val childs: RArr[Sqlign => GraphicElems], val adopted: GraphicElems) extends
  SqlignGraphic, ParentGraphic2[Sqlign]
{ def children: RArr[Graphic2Elem] = childs.flatMap(ch => ch(shape)) ++ adopted
  override def attribs: RArr[XAtt] = ???
  override def svgInline: SvgSvgRel = ???
  override def svgElems: RArr[SvgOwnLine] = ???
  override def rendToCanvas(cp: CanvasPlatform): Unit = ???
  override def slate(operand: VecPt2): SqlignCompound = SqlignCompound(shape.slate(operand), facets, childs, children.slate(operand))

  override def slate(xOperand: Double, yOperand: Double): SqlignCompound =
    SqlignCompound(shape.slate(xOperand, yOperand), facets, childs, children.slate(xOperand, yOperand))

  override def slateFrom(operand: VecPt2): SqlignCompound = SqlignCompound(shape.slateFrom(operand), facets, childs, children.slateFrom(operand))

  override def slateFrom(xOperand: Double, yOperand: Double): SqlignCompound =
    SqlignCompound(shape.slateFrom(xOperand, yOperand), facets, childs, children.slateFrom(xOperand, yOperand))  

  override def slateX(xOperand: Double): SqlignCompound = SqlignCompound(shape.slateX(xOperand), facets, childs, children.slateX(xOperand))
  override def slateY(yOperand: Double): SqlignCompound = SqlignCompound(shape.slateY(yOperand), facets, childs, children.slateY(yOperand))
  override def scale(operand: Double): SqlignCompound = SqlignCompound(shape.scale(operand), facets, childs, children.scale(operand))
  override def negX: SqlignCompound = SqlignCompound(shape.negX, facets, childs, children.negX)
  override def negY: SqlignCompound = SqlignCompound(shape.negY, facets, childs, children.negY)
  override def rotate90: SqlignCompound = SqlignCompound(shape.rotate90, facets, childs, children.rotate90)
  override def rotate180: SqlignCompound = SqlignCompound(shape.rotate180, facets, childs, children.rotate180)
  override def rotate270: SqlignCompound = SqlignCompound(shape.rotate270, facets, childs, children.rotate270)
  override def prolign(matrix: AxlignMatrix): SqlignCompound = SqlignCompound(shape.prolign(matrix), facets, childs, children.prolign(matrix))
}

object SqlignCompound
{
  def apply(shape: Sqlign, facets: RArr[GraphicFacet], childs: RArr[Sqlign => GraphicElems], adopted: GraphicElems = RArr()): SqlignCompound =
    new SqlignCompound(shape, facets, childs, adopted)
}