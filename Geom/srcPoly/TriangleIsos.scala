/* Copyright 2018-25 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package geom
import pWeb.*

/** An isosceles triangle. This trait is implemented by the equilateral triangle, [[TriangleEqui]] and the general case [[TriangleIsos]]. */
trait TriangleIsos extends Triangle
{ type ThisT <: TriangleIsos
  override def typeStr: String = "TriangleIsos"

  /** The height of this isosceles triangle from the bisection of the thrid side to the vertex where the 2 euql length sides meet. */
  def height: Double = ???
}

/** The general case of an isosceles triangle. */
final case class TriangleIsosGen(arrayUnsafe: Array[Double]) extends TriangleIsos, AxisFree, PolygonLikeDbl2[Pt2], Pt2SeqSpec
{	type ThisT = TriangleIsosGen

  override def fromArray(array: Array[Double]): TriangleIsosGen = new TriangleIsosGen(array)
  override def vertsTrans(f: Pt2 => Pt2): TriangleIsosGen = ???

  override def attribs: RArr[XmlAtt] = ???

  override def rotate(rotation: AngleVec): TriangleIsosGen = ???

  override def reflect(lineLike: LineLike): TriangleIsosGen = ???

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
  override def unsafeNegX: Array[Double] = arrayD1Map(d => -d)
  override def unsafeNegY: Array[Double] = arrayD2Map(d => -d)
  override def sides: LineSegArr = new LineSegArr(arrayForSides)
}