/* Copyright 2025 Richard Oliver, W0d. Licensed under Apache Licence version 2.0. */
package ostrat; package geom

object Stadium
{
  /** Temporary apply method to create stadium shape. */
  def apply(fullWidth: Double, height: Double = 1, cenX: Double = 0, cenY: Double = 0): ShapeGenOld =
  { val fw = fullWidth

    /** Half full width */
    val hfw = fw / 2

    /** top and bottom width or short width */
    val sw = fw - height

    /** Half short width */
    val hsw = sw / 2

    /** half height */
    val hh = height / 2

    ShapeGenOld(ArcTail(cenX + hsw, cenY, cenX + hfw, cenY), ArcTail(cenX + hsw, cenY, cenX + hsw, cenY - hh), LineTail(cenX -hsw, cenY - hh),
      ArcTail(cenX - hsw, cenY, cenX - hfw, cenY), ArcTail(cenX - hsw, cenY, cenX - hsw, cenY + hh), LineTail(cenX + hsw, cenY + hh))
  }
}