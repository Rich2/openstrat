/* Copyright 2018-25 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package geom

trait PolygonLen2[VT <: PtLen2] extends Any, GeomLen2Elem, PolygonLikeDbl2[VT]
{ type ThisT <: PolygonLen2[VT]
  type SideT <: LineSegLen2[VT]

  override def slate(operand: VecPtLen2): PolygonLen2[VT]  
  override def slate(xDelta: Length, yDelta: Length): PolygonLen2[VT]
  override def slateX(operand: Length): PolygonLen2[VT]
  override def slateY(operand: Length): PolygonLen2[VT]
  override def scale(operand: Double): PolygonLen2[VT]

  def revY: PolygonLen2[VT]
  def revYIf(cond: Boolean): PolygonLen2[VT]
  def rotate180: PolygonLen2[VT]
  def rotate180If(cond: Boolean): PolygonLen2[VT]
  def rotate180IfNot(cond: Boolean): PolygonLen2[VT]
}