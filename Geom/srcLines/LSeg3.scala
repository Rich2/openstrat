/* Copyright 2018-25 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package geom

/** A 3-dimensional line segment. A straight line between two points in a 3d space. */
final class LSeg3(val dbl1: Double, val dbl2: Double, val dbl3: Double, val dbl4: Double, val dbl5: Double, val dbl6: Double) extends LSegDbl6[Pt3]
{ def typeStr: String = "LSeg3"

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

  /** Drops the z components of this 3D line segment, returns a 2D [[LSeg2]] with just the X and Y components. */
  def toXY: LSeg2 = LSeg2(startPt.toXY, endPt.toXY)
}

/** Companion object for LineSeg3 contains factory apply methods. */
object LSeg3
{ /** Factory apply method to create a line segment in 3 dimensions from 2 [[Pt3]] points. There is also a name overload to create a [[LSeg2]] from its 4
   * consituent [[Double]]s. */
  def apply(pStart: Pt3, pEnd: Pt3): LSeg3 = new LSeg3(pStart.x, pStart.y, pStart.z, pEnd.x, pEnd.y, pEnd.z)

  /** Factory apply method to create a line segment in 3 dimensions from 4 [[Double]]s. There is also a name overload to create a [[LSeg2]] from its [[Pt3]]
   * start and end points. */
  def apply(xStart: Double, yStart: Double, zStart: Double, xEnd: Double, yEnd: Double, zEnd: Double): LSeg3 =
    new LSeg3(xStart, yStart, zStart: Double, xEnd: Double, yEnd: Double, zEnd: Double)

  /** Implicit [[Show]] and [[Unshow]] instances / evidence for [[LSeg3]]. */
  implicit lazy val persistEv: Persist2Both[Pt3, Pt3, LSeg3] = Persist2Both[Pt3, Pt3, LSeg3]("LSeg3", "start", _.startPt, "end", _.endPt, apply)
}