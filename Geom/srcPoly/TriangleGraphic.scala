/* Copyright 2018-25 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package geom

trait TriangleGraphic extends PolygonGraphic
{
  override def shape: Triangle
}

trait TriangleGraphicSimple extends TriangleGraphic, PolygonGraphicSimple

case class TriangleFill(shape: Triangle, fillFacet: FillFacet) extends TriangleGraphicSimple, PolygonFill
{ override type ThisT = TriangleFill
  override def slate(operand: VecPt2): TriangleFill = TriangleFill(shape.slate(operand), fillFacet)
  override def slate(xDelta: Double, yDelta: Double): TriangleFill = TriangleFill(shape.slate(xDelta, yDelta), fillFacet)
  override def scale(operand: Double): TriangleFill = TriangleFill(shape.scale(operand), fillFacet)
  override def negX: PolygonFill = TriangleFill(shape.negX, fillFacet)
  override def negY: PolygonFill = TriangleFill(shape.negY, fillFacet)
}
