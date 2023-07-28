/* Copyright 2018-23 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package geom

/** A Dodecahedron aligned with the X and Y Axis so v0 is vertically up from the centre and v6 vertically down. */
class DoDeclign(val unsafeArray: Array[Double]) extends AnyVal with Polygon6Plus
{ override type ThisT = DoDeclign
  override def typeStr: String = "DoDeclign"
  override def fromArray(array: Array[Double]): DoDeclign = new DoDeclign(array)
}

object DoDeclign
{
  def apply(radiusOut: Double, x: Double, y: Double): DoDeclign = {
    val array = Array[Double](
      x, y + radiusOut,
      x + Cos60 * radiusOut, y + Sin60 * radiusOut,
      x + Cos30 * radiusOut, y + Sin30 * radiusOut,
      x + radiusOut,         y,
      x + Cos30 * radiusOut, y - Sin30 * radiusOut,
      x + Cos60 * radiusOut, y - Sin60 * radiusOut,
      x,                     y - radiusOut,
      x - Cos60 * radiusOut, y - Sin60 * radiusOut,
      x - Cos30 * radiusOut, y - Sin30 * radiusOut,
      x - radiusOut,         y,
      x - Cos30 * radiusOut, y + Sin30 * radiusOut,
      x - Cos60 * radiusOut, y + Sin60 * radiusOut
    )
    new DoDeclign(array)
  }
}