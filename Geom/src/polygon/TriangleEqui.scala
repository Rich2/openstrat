/* Copyright 2018-22 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package geom
import pWeb._

/** Equilateral triangle. will become a trait. */
final case class TriangleEqui(unsafeArray: Array[Double]) extends TriangleIsos with AxisFree
{
  type ThisT = TriangleEqui
  override def height: Double = ???
  override def attribs: Arr[XANumeric] = ???
  override def vertsTrans(f: Pt2 => Pt2): ThisT = ???

  override def rotate(angle: AngleVec): TriangleEqui = ???

  override def reflect(lineLike: LineLike): TriangleEqui = ???


}