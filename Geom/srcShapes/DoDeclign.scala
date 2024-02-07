/* Copyright 2018-23 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package geom

/** A Dodecahedron aligned with the X and Y Axis so v0 is vertically up from the centre and v6 vertically down. */
class DoDeclign(val arrayUnsafe: Array[Double]) extends AnyVal with Polygon6Plus
{ override type ThisT = DoDeclign
  override def typeStr: String = "DoDeclign"
  override def fromArray(array: Array[Double]): DoDeclign = new DoDeclign(array)
}

object DoDeclign
{
  def apply(radiusOut: Double, cen: Pt2): DoDeclign = DoDeclign(radiusOut, cen.x, cen.y)

  def apply(radiusOut: Double, cx: Double = 0, cy: Double = 0): DoDeclign =
  {
    val array = Array[Double](
      cx, cy + radiusOut,
      cx + Cos60 * radiusOut, cy + Sin60 * radiusOut,
      cx + Cos30 * radiusOut, cy + Sin30 * radiusOut,
      cx + radiusOut,         cy,
      cx + Cos30 * radiusOut, cy - Sin30 * radiusOut,
      cx + Cos60 * radiusOut, cy - Sin60 * radiusOut,
      cx,                     cy - radiusOut,
      cx - Cos60 * radiusOut, cy - Sin60 * radiusOut,
      cx - Cos30 * radiusOut, cy - Sin30 * radiusOut,
      cx - radiusOut,         cy,
      cx - Cos30 * radiusOut, cy + Sin30 * radiusOut,
      cx - Cos60 * radiusOut, cy + Sin60 * radiusOut
    )
    new DoDeclign(array)
  }
}