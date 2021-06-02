/* Copyright 2018-21 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package geom

/** A regular Hexagon aligned to the X and Y axes. */
trait Hexlign extends HexReg
{ def width: Double
  def height: Double

  override def rotate90: Hexlign = this match
  { case HexParrX(h, cen) => HexParrY(h, cen.rotate90)
    case HexParrY(w, cen) => HexParrX(w, cen.rotate90)
  }

  override def rotate180: Hexlign = this match
  { case HexParrX(h, cen) => HexParrX(h, cen.rotate180)
    case HexParrY(w, cen) => HexParrY(w, cen.rotate180)
  }

  override def rotate270: Hexlign = this match
  { case HexParrX(h, cen) => HexParrY(h, cen.rotate270)
    case HexParrY(w, cen) => HexParrX(w, cen.rotate270)
  }
}