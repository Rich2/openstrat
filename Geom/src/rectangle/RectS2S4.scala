/* Copyright 2018-20 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat
package geom

/** Rectangle that is in part specified by points s2Cen and s4Cen. This is the [[Square.SquareImp]] and [[Rect.RectImp]] classes. This trait is purely
 *  for implementation. It does not have value as a library user interface type. */
trait RectS2S4 extends Rectangle
{
  final override def cen: Pt2 = sd3Cen midPt sd1Cen
  final override def cenX: Double = cen.x
  final override def cenY: Double = cen.y
  final override def width1: Double = sd4Cen.distTo(sd2Cen)
  final def rotationRadians: Double = alignAngle.radians
  @inline final override def alignAngle: AngleVec = sd4Cen.angleTo(sd2Cen).rotationFrom0
  final override def v1: Pt2 = sd2Cen + yVec2(width2 / 2).rotate(alignAngle)
  final override def v1x: Double = v1.x
  final override def v1y: Double = v1.y
  final override def v2: Pt2 = sd2Cen + yVec2(-width2 / 2).rotate(alignAngle)
  final override def v2x: Double = v2.x
  final override def v2y: Double = v2.y
  @inline final def v3: Pt2 = sd4Cen + yVec2(-width2 / 2).rotate(alignAngle)
  @inline final def v3x: Double = v3.x
  @inline final def v3y: Double = v3.y
  @inline final def v4: Pt2 = sd4Cen + yVec2(width2 / 2).rotate(alignAngle)
  @inline final def v4x: Double = v4.x
  @inline final def v4y: Double = v4.y


  /** The X component of the centre or half way point of side 1 of this polygon. Side 1 starts at the vLast vertex and ends at the v1 vertex. This can
   * be thought of as vertex 0.5. */
  override def sd1CenX: Double = ???

  /** The Y component of the centre or half way point of side 1 of this polygon. Side 1 starts at the vLast vertex and ends at the v1 vertex. This can
   * be thought of as vertex 0.5. */
  override def sd1CenY: Double = ???

  final override def sd1Cen: Pt2 = v4 midPt v1
  final override def sd3Cen: Pt2 = v2 midPt v3

  /** The X component of the centre or half way point of side 3 of this polygon. Side 3 starts at the v2 vertex and ends at the v3 vertex. This can be
   * thought of as vertex 2.5. */
  override def sd3CenX: Double = ???

  /** The Y component of the centre or half way point of side 3 of this polygon. Side 3 starts at the v2 vertex and ends at the v3 vertex. This can be
   * thought of as vertex 2.5. */
  override def sd3CenY: Double = ???

  final override  def sd2Cen: Pt2 = Pt2(sd2CenX, sd2CenY)

  final override def sd4Cen: Pt2 = Pt2(sd4CenX, sd4CenY)
}