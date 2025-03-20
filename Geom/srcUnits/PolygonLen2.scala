/* Copyright 2018-25 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package geom

trait PolygonLen2[VT <: PtLen2] extends Any, GeomLen2Elem, PolygonLikeDbl2[VT]
{ type ThisT <: PolygonLen2[VT]
  type SideT <: LineSegLen2[VT]

  override def slate(operand: VecPtLen2): PolygonLen2[VT]  
  override def slate(xOperand: Length, yOperand: Length): PolygonLen2[VT]
  override def slateX(xOperand: Length): PolygonLen2[VT]
  override def slateY(yOperand: Length): PolygonLen2[VT]
  override def scale(operand: Double): PolygonLen2[VT]
  def mapGeom2(operand: Length): Polygon

  def revY: PolygonLen2[VT]
  def revYIf(cond: Boolean): PolygonLen2[VT]
  def rotate180: PolygonLen2[VT]
  def rotate180If(cond: Boolean): PolygonLen2[VT]
  def rotate180IfNot(cond: Boolean): PolygonLen2[VT]
}