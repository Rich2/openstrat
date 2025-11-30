/* Copyright 2018-25 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package geom
import pWeb.*

/** An isosceles triangle. This trait is implemented by the equilateral triangle, [[TriangleEqui]] and the general case [[TriangleIsos]]. */
trait TriangleIsos extends Triangle
{ type ThisT <: TriangleIsos
  override def typeStr: String = "TriangleIsos"

  /** The height of this isosceles triangle from the bisection of the third side to the vertex where the 2 equal length sides meet. */
  def height: Double = ???
}

/** The general case of an isosceles triangle. */
final class TriangleIsosGen(val v0x: Double, val v0y: Double, val v1x: Double, val v1y: Double, val v2x: Double, val v2y: Double) extends TriangleIsos,
AxisFree//, PolygonLikeDbl2[Pt2], Pt2SeqSpec
{	type ThisT = TriangleIsosGen

  //override def fromArray(array: Array[Double]): TriangleIsosGen = new TriangleIsosGen(array)
  override def vertsTrans(f: Pt2 => Pt2): TriangleIsosGen = ???

  override def attribs: RArr[XAtt] = ???

  override def rotate(rotation: AngleVec): TriangleIsosGen = ???

  override def reflect(lineLike: LineLike): TriangleIsosGen = ???

  
  override def side0: LSeg2 = LSeg2(v0x, v0y, vertX(1), vertY(1))
  override def sd0CenX: Double = v0x \/ vertX(1)
  override def sd0CenY: Double = v0y \/ vertY(1)
  override def sd0Cen: Pt2 = Pt2(sd0CenX, sd0CenY)    
}

/** An iososolese triangle with its base parralel to the X axis. */
trait TriangleIsosParrX extends TriangleIsos
{
  
}