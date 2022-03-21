/* Copyright 2018-22 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package geom
import pWeb._

/** An isosceles triangle. This trait is implemented by the equilateral triangle, [[TriangleEqui]] and the general case [[TriangleIsos]]. */
trait TriangleIsos extends Triangle
{ def unsafeArray: Array[Double]

  /** The height of this isosceles triangle. */
  def height: Double = ???


}

/** The general case of an isosceles triangle. */
final case class TriangleIsosGen(unsafeArray: Array[Double]) extends TriangleIsos with AxisFree
{	type ThisT = TriangleIsosGen

  override def vertsTrans(f: Pt2 => Pt2): TriangleIsosGen = ???

  override def attribs: Arr[XANumeric] = ???

  override def rotate(angle: AngleVec): TriangleIsosGen = ???

  override def reflect(lineLike: LineLike): TriangleIsosGen = ???
}