/* Copyright 2018-23 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package geom
import pWeb._

/** Equilateral triangle. will become a trait. */
final class TriangleEqui(val unsafeArray: Array[Double]) extends TriangleIsos with AxisFree
{ type ThisT = TriangleEqui

  override def typeStr: String = "TriangleEqui"
  override def fromArray(array: Array[Double]): TriangleEqui = new TriangleEqui(array)

  override def height: Double = ???
  override def attribs: RArr[XmlAtt] = ???
  override def vertsTrans(f: Pt2 => Pt2): TriangleEqui = ???

  override def rotate(angle: AngleVec): TriangleEqui = ???

  override def reflect(lineLike: LineLike): TriangleEqui = ???
}