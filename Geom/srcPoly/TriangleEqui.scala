/* Copyright 2018-25 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package geom
import pWeb.*

trait TriangleEqui extends TriangleIsos

/** Equilateral triangle. will become a trait. */
final class TriangleEquiGen(val v0x: Double, val v0y: Double, val v1x: Double, val v1y: Double, val v2x: Double, val v2y: Double) extends TriangleEqui, AxisFree
{ type ThisT = TriangleEquiGen
  override def typeStr: String = "TriangleEqui"

  override def height: Double = ???
  override def attribs: RArr[XAtt] = ???
  override def vertsTrans(f: Pt2 => Pt2): TriangleEqui = ???

  override def rotate(rotation: AngleVec): TriangleEquiGen = ???

  override def reflect(lineLike: LineLike): TriangleEquiGen = ???
  
  override def side0: LSeg2 = LSeg2(v0x, v0y, v1x, v1y)
  override def sd0CenX: Double = v0x \/ vertX(1)
  override def sd0CenY: Double = v0y \/ vertY(1)
  override def sd0Cen: Pt2 = Pt2(sd0CenX, sd0CenY)  
}

final class TriangleEquiXParrX(val v0x: Double, val v0y: Double, val v1x: Double, val v1y: Double, val v2x: Double, val v2y: Double) extends TriangleEqui
{
  override type ThisT = TriangleEquiXParrX

  /** Polygon side 0 from vertex 0 to vertex 1. */
  override def side0: LSeg2 = ???

  /** The X component of the centre or halfway point of side 0 of this polygon. */
  override def sd0CenX: Double = ???

  /** The Y component of the centre or halfway point of side 0 of this polygon. */
  override def sd0CenY: Double = ???
}