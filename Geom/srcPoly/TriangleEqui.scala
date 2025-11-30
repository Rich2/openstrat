/* Copyright 2018-25 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package geom
import pWeb.*

trait TriangleEqui extends TriangleIsos
{
  
}

/** Equilateral triangle. will become a trait. */
final class TriangleEquiGen(val v0x: Double, val v0y: Double, val v1x: Double, val v1y: Double, val v2x: Double, val v2y: Double) extends TriangleEqui, AxisFree
{ type ThisT = TriangleEquiGen
  override def typeStr: String = "TriangleEqui"

  override def height: Double = ???
  override def attribs: RArr[XAtt] = ???
  override def vertsTrans(f: Pt2 => Pt2): TriangleEqui = ???

  override def rotate(rotation: AngleVec): TriangleEquiGen = ???

  override def reflect(lineLike: LineLike): TriangleEquiGen = ???
}

final class TriangleEquiXParrX(val v0x: Double, val v0y: Double, val v1x: Double, val v1y: Double, val v2x: Double, val v2y: Double) extends TriangleEqui,
  TriangleIsosParrX
{ override type ThisT = TriangleEquiXParrX

}