/* Copyright 2018-22 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package geom
import pWeb._

/** An isosceles triangle. This trait is implemented by the equilateral triangle, [[TriangleEqui]] and the general case [[TriangleIsos]]. */
trait TriangleIsos extends Triangle
{ def unsafeArray: Array[Double]

  /** The height of this isosceles triangle. */
  def height: Double = ???

  def v0x: Double = unsafeArray(0)
  def v0y: Double = unsafeArray(1)
  def v1x: Double = unsafeArray(2)
  def v1y: Double = unsafeArray(3)
  inline override def v1: Pt2 = v1x pp v1y
  def v2x: Double = unsafeArray(4)
  def v2y: Double = unsafeArray(5)
}

/** The general case of an isosceles triangle. */
final case class TriangleIsosGen(unsafeArray: Array[Double]) extends TriangleIsos with AxisFree
{	type ThisT = TriangleIsosGen

  override def vertsTrans(f: Pt2 => Pt2): TriangleIsosGen = ???

  override def attribs: Arr[XANumeric] = ???

  override def rotate(angle: AngleVec): TriangleIsosGen = ???

  override def reflect(lineLike: LineLike): TriangleIsosGen = ???
}