/* Copyright 2018-25 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package geom
import pWeb.*

trait TriangleEqui extends TriangleIsos
{
  //override def slate(xOperand: Double, yOperand: Double): Triangle = super.slate(xOperand, yOperand)
}

object TriangleEqui
{ /** Factory apply method for equilateral triangle. */
  def apply(v0x: Double, v0y: Double, v1x: Double, v1y: Double, v2x: Double, v2y: Double): TriangleEqui = new TriangleEquiGen(v0x, v0y, v1x, v1y, v2x, v2y)
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