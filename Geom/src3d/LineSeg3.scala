/* Copyright 2018-22 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package geom

/** A 3 dimensional line segment. A straight line between two points in a 3d space. */
final class LineSeg3(val startX: Double, val startY: Double, val startZ: Double, val endX: Double, val endY: Double, val endZ: Double) extends
  LineSegLike[Pt3] with ElemDbl6
{ def typeStr: String = "LineSeg3"
  //override def canEqual(other: Any): Boolean = other.isInstanceOf[LineSeg3]
  override def dbl1: Double = startX
  override def dbl2: Double = startY
  override def dbl3: Double = startZ
  override def dbl4: Double = endX
  override def dbl5: Double = endY
  override def dbl6: Double = endZ

  /** The start [[Pt3]]. */
  def startPt: Pt3 = Pt3(startX, startY, startZ)

  /** The end [[Pt3]]. */
  def endPt: Pt3 = Pt3(endX, endY, endZ)

  /** Drops the z components of this 3D line segment, returns a 2D [[LineSeg]] with just the X and Y components. */
  def toXY: LineSeg = LineSeg(startPt.toXY, endPt.toXY)
}

/** Companion object for LineSeg3 contains factory apply methods. */
object LineSeg3
{
  def apply(pStart: Pt3, pEnd: Pt3): LineSeg3 = new LineSeg3(pStart.x, pStart.y, pStart.z, pEnd.x, pEnd.y, pEnd.z)

  def apply(xStart: Double, yStart: Double, zStart: Double, xEnd: Double, yEnd: Double, zEnd: Double): LineSeg3 =
    new LineSeg3(xStart, yStart, zStart: Double, xEnd: Double, yEnd: Double, zEnd: Double)
}