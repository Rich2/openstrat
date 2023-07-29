/* Copyright 2018-23 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package geom

trait RectangleGraphic extends PolygonGraphic with ShapeGraphicCentred
{ override def shape: Rectangle
}

trait RectangleGraphicSimple extends PolygonGraphicSimple with RectangleGraphic