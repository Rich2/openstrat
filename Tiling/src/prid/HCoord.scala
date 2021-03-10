/* Copyright 2018-21 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat
package prid
import geom._

/** A coordinate with in a Hex grid. It may be a Hex tile centre [[HCen]], a HexSide [[HSide]] or Hex tile vertice [[HVert]]. */
trait HCoord extends Any with TCoord
{ def toPt2: Pt2
  def toVec: Vec2

  override def equals(obj: Any): Boolean = obj match {
    case hc: HCoord if r == hc.r & c == hc.c => true
    case _ => false
  }
  def canEqual(a: Any) = a.isInstanceOf[HCoord]

  // Step 8 - implement a corresponding hashCode c=method
  override def hashCode: Int = {
    val prime = 31
    var result = 1
    result = prime * result + r;
    result = prime * result + c * 17
    result
  }
}

/** Companion object for Hex coordinate trait, contains apply factory object. */
object HCoord
{
  def apply(r: Int, c: Int): HCoord = r %% 4 match
  { case 0 if c.div4Rem0 => new HCen(r, c)
    case 2 if c.div4Rem2 => new HCen(r, c)
    case 0 if c.div4Rem0 => new HSide(r, c)
    case 1 | 3 if c.isOdd => new HSide(r, c)
    case 2 if c.div4Rem2 => new HSide(r, c)
    case _ if r.isOdd & c.isEven => HVert(r, c)
    case _ => excep(s"$r, $c is not a valid Hex Grid coordinate.")
  }
}

/** Common trait for hex centre and hex side coordinate. The position of these coordinates is proportional, unlike the Hex vertices positions. */
trait HCenOrSide extends HCoord
{ override def toVec: Vec2 = Vec2(c / Sqrt3, r)
  override def toPt2: Pt2 = Pt2(c / Sqrt3, r)
}