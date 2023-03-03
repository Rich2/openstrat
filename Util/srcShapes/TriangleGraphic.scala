/* Copyright 2018-20 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat
package geom

trait TriangleGraphic extends PolygonGraphic
{
  override def shape: Triangle
}

trait TriangleGraphicSimple extends TriangleGraphic with PolygonGraphicSimple

case class TriangleFill(shape: Triangle, fill: FillFacet) extends TriangleGraphicSimple with PolygonFill