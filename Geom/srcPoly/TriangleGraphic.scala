/* Copyright 2018-25 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package geom

trait TriangleGraphic extends PolygonGraphic
{
  override def shape: Triangle
}

trait TriangleGraphicSimple extends TriangleGraphic, PolygonGraphicSimple

case class TriangleFill(shape: Triangle, fillFacet: FillFacet) extends TriangleGraphicSimple, PolygonFill