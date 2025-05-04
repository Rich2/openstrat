/* Copyright 2018-25 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package geom

/** A polygon with at least 3 vertices, defined in [[Length]] units. The PolygonNPlus traits include values for the vertices and the x and y components of the
 * vertices. The X and Y components are included because Graphics implementation APIs use them. */
trait PolygonLen2P3[+VT <: PtLen2] extends Any, PolygonLen2[VT]
{
  def v1x: Length
  def v1y: Length
  def v1: PtLen2
  def v2x: Length
  def v2y: Length
  def v2: PtLen2
}

trait PolygonM2P3 extends Any, PolygonLen2P3[PtM2], PolygonM2
{ def v1xMNum: Double
  def v1yMNum: Double
  def v2xMNum: Double
  def v2yMNum: Double
  
  final override def v1x: Metres = Metres(v1xMNum)
  final override def v1y: Metres = Metres(v1yMNum)
  final override def v1: PtM2 = PtM2(v1xMNum, v1yMNum)
  final override def v2x: Metres = Metres(v2xMNum)
  final override def v2y: Metres = Metres(v2yMNum)
  final override def v2: PtM2 = PtM2(v2xMNum, v2yMNum)
}

/** A polygon with at least 4 vertices, defined in [[Length]] units. */
trait PolygonLen2P4[+VT <: PtLen2] extends Any, PolygonLen2P3[VT]
{ def v3x: Length
  def v3y: Length
  def v3: PtLen2
}

trait PolygonM2P4 extends Any, PolygonLen2P4[PtM2], PolygonM2P3
{ def v3xMNum: Double
  def v3yMNum: Double
  final override def v3x: Metres = Metres(v3xMNum)
  final override def v3y: Metres = Metres(v3yMNum)
  final override def v3: PtM2 = PtM2(v3xMNum, v3yMNum)
}