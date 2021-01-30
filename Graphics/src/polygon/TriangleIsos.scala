/* Copyright 2018-20 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat
package geom
import pWeb._

/** An isosceles triangle. This trait is implemented by the equilateral triangle, [[TriangleEqui]] and the general case [[TriangleIsos]]. */
trait TriangleIsos extends Triangle
{	def height: Double
  def v2x: Double = ???
  def v2y: Double = ???
  override def v2: Pt2 = ???
}

/** The general case of an isosceles triangle. */
final case class TriangleIsosGen(v1x: Double, v1y: Double, v3x: Double, v3y: Double, height: Double) extends TriangleIsos with AxisFree
{	type ThisT = TriangleIsosGen

  override def vertsTrans(f: Pt2 => Pt2): TriangleIsosGen = ???
  override def v2: Pt2 = ???
  override def attribs: Arr[XANumeric] = ???

  override def rotate(angle: AngleVec): TriangleIsosGen = ???

  override def reflect(lineLike: LineLike): TriangleIsosGen = ???
}