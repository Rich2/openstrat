/* Copyright 2025 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package geom

/**  */
trait SqlignLen2Graphic extends SquareLen2Graphic, RectLen2Graphic
{ override def shape: SqlignLen2[PtLen2]
}

case class SqlignLen2Fill(shape: SqlignLen2[PtLen2], fillFacet: FillFacet) extends SqlignLen2Graphic, SquareLen2Fill, RectLen2Fill
{ override def slate(operand: VecPtLen2): SqlignLen2Fill = SqlignLen2Fill(shape.slate(operand), fillFacet)
  override def slate(xOperand: Length, yOperand: Length): SqlignLen2Fill = SqlignLen2Fill(shape.slate(xOperand, yOperand), fillFacet)
  override def slateX(xOperand: Length): SqlignLen2Fill = SqlignLen2Fill(shape.slateX(xOperand), fillFacet)
  override def slateY(yOperand: Length): SqlignLen2Fill = SqlignLen2Fill(shape.slateY(yOperand), fillFacet)
  override def scale(operand: Double): SqlignLen2Fill = SqlignLen2Fill(shape.scale(operand), fillFacet)
  override def mapGeom2(operand: Length): SqlignFill = SqlignFill(shape.mapGeom2(operand), fillFacet)
}

object SqlignLen2Fill
{
  /** Implicit [[MapGeom2]] type class instance / evidence for [[SqlignLen2Fill]] to [[SqlignFill]] */
  implicit val mapGeomEv: MapGeom2[SqlignLen2Fill, SqlignFill] = (obj, operand) => obj.mapGeom2(operand)
}