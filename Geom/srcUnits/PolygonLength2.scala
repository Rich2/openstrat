/* Copyright 2018-24 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package geom

trait PolygonLength2[VT <: PtLength2] extends Any with PolygonLikeDbl2[VT]
{ type ThisT <: PolygonLength2[VT]
  type SideT <: LineSegLength2[VT]

  def revY: PolygonLength2[VT]
  def revYIf(cond: Boolean): PolygonLength2[VT]
  def rotate180: PolygonLength2[VT]
  def rotate180If(cond: Boolean): PolygonLength2[VT]
  def rotate180IfNot(cond: Boolean): PolygonLength2[VT]
}