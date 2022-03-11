/* Copyright 2018-22 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package pGrid
import geom._, proord._

/** A deci-Roordinate. Divides the line between 2 Roords into graduations of ten. Defines the point a certain number of tenths toward the second
 *  Roord. */
case class RoordOff(yV: Int, cV: Int, dirn: HVOffDirn, tenths: Int)
{ def rVert: Roord = yV rr cV
  def r2: Roord = rVert + dirn.r
  def toVec2(f: Roord => Pt2): Pt2 = f(rVert).slate(f((r2 - rVert) * tenths).invScale(10))
}

object RoordOff
{ def apply(r1: Roord, dirn: HVOffDirn, tenths: Int): RoordOff = new RoordOff(r1.y, r1.c, dirn, tenths)
}

/** yDiv4Rem0 Up, DR, DL or yDiv4Rem2 UR, Dn, UL */
sealed class HVOffDirn(val y: Int, val c: Int)
{ def r: Roord = y rr c
}

case object HVOffUp extends HVOffDirn(1, 0)
case object HVOffUR extends HVOffDirn(1, 2)
case object HVOffDR extends HVOffDirn(-1, 2)
case object HVOffDown extends HVOffDirn(-1, 0)
case object HVOffDL extends HVOffDirn(-1, -2)
case object HVOffUL extends HVOffDirn(1, -2)