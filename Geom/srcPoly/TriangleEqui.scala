/* Copyright 2018-25 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package geom
import pWeb.*

/** Equilateral triangle. will become a trait. */
final class TriangleEqui(val arrayUnsafe: Array[Double]) extends TriangleIsos, AxisFree, PolygonLikeDbl2[Pt2], Pt2SeqSpec
{ type ThisT = TriangleEqui

  override def typeStr: String = "TriangleEqui"
  override def fromArray(array: Array[Double]): TriangleEqui = new TriangleEqui(array)

  override def height: Double = ???
  override def attribs: RArr[XmlAtt] = ???
  override def vertsTrans(f: Pt2 => Pt2): TriangleEqui = ???

  override def rotate(rotation: AngleVec): TriangleEqui = ???

  override def reflect(lineLike: LineLike): TriangleEqui = ???

  override def v0x: Double = arrayUnsafe(0)
  override def v0y: Double = arrayUnsafe(1)
  override def v0: Pt2 = Pt2(arrayUnsafe(0), arrayUnsafe(1))
  override def vLastX: Double = arrayUnsafe(numVerts - 2)
  override def vLastY: Double = arrayUnsafe(numVerts - 1)
  override def vLast: Pt2 = Pt2(vLastX, vLastY)
  override def side0: LineSeg = LineSeg(v0x, v0y, vertX(1), vertY(1))
  override def sd0CenX: Double = v0x \/ vertX(1)
  override def sd0CenY: Double = v0y \/ vertY(1)
  override def sd0Cen: Pt2 = Pt2(sd0CenX, sd0CenY)
  override def vertX(index: Int): Double = arrayUnsafe(index * 2)
  override def vertY(index: Int): Double = arrayUnsafe(index * 2 + 1)
  override def sides: LineSegArr = new LineSegArr(arrayForSides)
}