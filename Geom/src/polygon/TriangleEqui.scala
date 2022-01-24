/* Copyright 2018-20 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat
package geom
import pWeb._

/** Equilateral triangle. will become a trait. */
final case class TriangleEqui(v0x: Double, v0y: Double, v2x: Double, v2y: Double) extends TriangleIsos with AxisFree
{
  type ThisT = TriangleEqui
  override def height: Double = ???
  override def attribs: Arr[XANumeric] = ???
  override def vertsTrans(f: Pt2 => Pt2): ThisT = ???

  override def rotate(angle: AngleVec): TriangleEqui = ???

  override def reflect(lineLike: LineLike): TriangleEqui = ???
}