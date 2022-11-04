/* Copyright 2018-22 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package geom

/** A 3 dimensional line segment. A straight line between two points in a 3d space. */
final class LineSeg3(val dbl1: Double, val dbl2: Double, val dbl3: Double, val dbl4: Double, val dbl5: Double, val dbl6: Double) extends
  LineSegLikeDbl6[Pt3]
{ def typeStr: String = "LineSeg3"

  inline def startX: Double = dbl1
  inline def startY: Double = dbl2
  inline def startZ: Double = dbl3
  inline def endX: Double = dbl4
  inline def endY: Double = dbl5
  inline def endZ: Double = dbl6

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