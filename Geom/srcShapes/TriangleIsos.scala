/* Copyright 2018-22 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package geom
import pWeb._

/** An isosceles triangle. This trait is implemented by the equilateral triangle, [[TriangleEqui]] and the general case [[TriangleIsos]]. */
trait TriangleIsos extends Triangle
{ type ThisT <: TriangleIsos
  override def typeStr: String = "TriangleIsos"

  /** The height of this isosceles triangle from the bisection of the thrid side to the vertex where the 2 euql length sides meet. */
  def height: Double = ???
}

/** The general case of an isosceles triangle. */
final case class TriangleIsosGen(unsafeArray: Array[Double]) extends TriangleIsos with AxisFree
{	type ThisT = TriangleIsosGen

  override def fromArray(array: Array[Double]): TriangleIsosGen = new TriangleIsosGen(array)
  override def vertsTrans(f: Pt2 => Pt2): TriangleIsosGen = ???

  override def attribs: RArr[XAttNumeric] = ???

  override def rotate(angle: AngleVec): TriangleIsosGen = ???

  override def reflect(lineLike: LineLike): TriangleIsosGen = ???
}