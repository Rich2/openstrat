/* Copyright 2018-20 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat
package geom

/** Rectangle that is in part specified by points s2Cen and s4Cen. This is the [[Square.SquareImp]] and [[Rect.RectImp]] classes. This trait is purely
 *  for implementation. It does not have value as a library user interface type. */
trait RectS2S4 extends Rectangle
{
  final override def cen: Pt2 = sd2Cen midPt sd0Cen
  final override def cenX: Double = cen.x
  final override def cenY: Double = cen.y
  final override def width1: Double = sd3Cen.distTo(sd1Cen)
  final def rotationRadians: Double = alignAngle.radians
  @inline final override def alignAngle: AngleVec = sd3Cen.angleTo(sd1Cen).rotationFrom0
  final override def v0: Pt2 = sd1Cen + yVec2(width2 / 2).rotate(alignAngle)
  final override def v0x: Double = v0.x
  final override def v0y: Double = v0.y
  final override def v1: Pt2 = sd1Cen + yVec2(-width2 / 2).rotate(alignAngle)
  final override def v1x: Double = v1.x
  final override def v1y: Double = v1.y
  @inline final def v2: Pt2 = sd3Cen + yVec2(-width2 / 2).rotate(alignAngle)
  @inline final def v2x: Double = v2.x
  @inline final def v2y: Double = v2.y
  @inline final def v3: Pt2 = sd3Cen + yVec2(width2 / 2).rotate(alignAngle)
  @inline final def v3x: Double = v3.x
  @inline final def v3y: Double = v3.y


  /** The X component of the centre or half way point of side 1 of this polygon. Side 1 starts at the vLast vertex and ends at the v1 vertex. This can
   * be thought of as vertex 0.5. */
  override def sd0CenX: Double = ???

  /** The Y component of the centre or half way point of side 1 of this polygon. Side 1 starts at the vLast vertex and ends at the v1 vertex. This can
   * be thought of as vertex 0.5. */
  override def sd0CenY: Double = ???

  final override def sd0Cen: Pt2 = v3 midPt v0
  final override def sd2Cen: Pt2 = v1 midPt v2

  /** The X component of the centre or half way point of side 3 of this polygon. Side 3 starts at the v2 vertex and ends at the v3 vertex. This can be
   * thought of as vertex 2.5. */
  override def sd2CenX: Double = ???

  /** The Y component of the centre or half way point of side 3 of this polygon. Side 3 starts at the v2 vertex and ends at the v3 vertex. This can be
   * thought of as vertex 2.5. */
  override def sd2CenY: Double = ???

  final override  def sd1Cen: Pt2 = Pt2(sd1CenX, sd1CenY)

  final override def sd3Cen: Pt2 = Pt2(sd3CenX, sd3CenY)
}