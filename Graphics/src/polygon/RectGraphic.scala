/* Copyright 2018-20 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat
package geom

trait RectGraphic extends PolygonGraphic 
{ override def shape: Rect
}

//trait RectGraphicSimple extends RectGraphic with PolygonGraphicSimple

//trait RectFill extends RectGraphicSimple// with PolygonFill
