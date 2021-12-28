/* Copyright 2018-21 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package prid
import geom._

/** Common trait for hex centre and hex side coordinate. The position of these coordinates is proportional, unlike the Hex vertices positions. */
trait HCenOrSide extends HCoord with TileCenOrSide
{ override def toVec: Vec2 = Vec2(c, r * Sqrt3)
  override def toPt2: Pt2 = Pt2(c, r  * Sqrt3)
}
/** Companion object for [[HCenOrSide]] trait, contians factory apply method and implicit [[Persist]] instance. */
object HCenOrSide
{ /** Apply factory method for creating [[HCenOrSide]] instances. Will throw exception on illegal values.  */
  def apply(r: Int, c: Int): HCenOrSide = r %% 4 match
  { case 0 if c.div4Rem0 => new HCen(r, c)
    case 2 if c.div4Rem2 => new HCen(r, c)
    case 0 if c.div4Rem0 => new HSide(r, c)
    case 1 | 3 if c.isOdd => new HSide(r, c)
    case 2 if c.div4Rem0 => new HSide(r, c)
    case _ => excep(s"$r, $c is not a valid HCenOrSide hex grid coordinate.")
  }

  implicit val persistImplicit: PersistShowInt2[HCenOrSide] = new PersistShowInt2[HCenOrSide]("HCenOrSide", "r", "c", HCenOrSide(_, _))
}