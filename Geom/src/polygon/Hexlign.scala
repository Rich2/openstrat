/* Copyright 2018-21 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package geom

/** A regular Hexagon aligned to the X and Y axes. */
trait Hexlign extends HexReg with DataDbl2s[Pt2]
{
  def width: Double
  def height: Double

  final override def dataElem(d1: Double, d2: Double): Pt2 = Pt2(d1, d2)
  final override def fElemStr: Pt2 => String = _.str

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