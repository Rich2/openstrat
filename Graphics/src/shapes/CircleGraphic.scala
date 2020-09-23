/* Copyright 2018-20 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat
package geom

/** A circle based Graphic, may be simple or compound. */
trait CircleGraphic extends ShapeGraphic
{ override def shape: Circle
}
