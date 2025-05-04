/* Copyright 2018-25 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package geom

/** A polygon with at least 3 vertices, defined in [[Length]] units. The PolygonNPlus traits include values for the vertices and the x and y components of the
 * vertices. The X and Y components are included because Graphics implementation APIs use them. */
trait PolygonLen2P3[+VT <: PtLen2] extends Any, PolygonLen2[VT]
{

}

/** A polygon with at least 4 vertices, defined in [[Length]] units. */
trait PolygonLen2P4[+VT <: PtLen2] extends Any, PolygonLen2P3[VT]