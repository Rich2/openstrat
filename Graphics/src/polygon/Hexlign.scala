/* Copyright 2018-21 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package geom

/** A regular Hexagon aligned to the X and Y axes. */
trait Hexlign extends HexReg
{ def width: Double
  def height: Double

  override def rotate90: Hexlign = this match
  { case HexXlign(h, cen) => HexYlign(h, cen.rotate90)
    case HexYlign(w, cen) => HexXlign(w, cen.rotate90)
  }

  override def rotate180: Hexlign = this match
  { case HexXlign(h, cen) => HexXlign(h, cen.rotate180)
    case HexYlign(w, cen) => HexYlign(w, cen.rotate180)
  }

  override def rotate270: Hexlign = this match
  { case HexXlign(h, cen) => HexYlign(h, cen.rotate270)
    case HexYlign(w, cen) => HexXlign(w, cen.rotate270)
  }
}