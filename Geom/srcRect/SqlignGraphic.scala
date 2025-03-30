/* Copyright 2025 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package geom

trait SqlignGraphic extends RectGraphic, SquareGraphic
{ override def shape: Sqlign
}

trait SqlignGraphicSimple extends SqlignGraphic, RectGraphicSimple, SquareGraphicSimple

class SqlignFill(val shape: Sqlign, val fillFacet: FillFacet) extends SqlignGraphicSimple, RectFill
{ override def slate(operand: VecPt2): SqlignFill = SqlignFill(shape.slate(operand), fillFacet)
  override def slate(xDelta: Double, yDelta: Double): SqlignFill = SqlignFill(shape.slate(xDelta, yDelta), fillFacet)
  override def scale(operand: Double): SqlignFill = SqlignFill(shape.scale(operand), fillFacet)
  override def negX: SqlignFill = SqlignFill(shape.negX, fillFacet)
  override def negY: SqlignFill = SqlignFill(shape.negY, fillFacet)
  override def rotate90: SqlignFill = SqlignFill(shape.rotate90, fillFacet)
  override def rotate180: SqlignFill = SqlignFill(shape.rotate180, fillFacet)
  override def rotate270: SqlignFill = SqlignFill(shape.rotate270, fillFacet)
}

object SqlignFill
{
  def apply(shape: Sqlign, fillFacet: FillFacet): SqlignFill = new SqlignFill(shape, fillFacet)
}