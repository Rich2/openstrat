/* Copyright 2018-22 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package prid; package phex
import geom._, collection.mutable.ArrayBuffer

/** A coordinate with in a Hex grid. It may be a Hex tile centre [[HCen]], a HexSide [[HSide]] or Hex tile vertice [[HVert]]. */
trait HCoord extends Any with TileCoord
{
  override def equals(obj: Any): Boolean = obj match {
    case hc: HCoord if r == hc.r & c == hc.c => true
    case _ => false
  }

  def canEqual(a: Any) = a.isInstanceOf[HCoord]

  override def hashCode: Int =
  { val prime = 31
    val f = 1
    val result1 = (prime * f) + r
    prime * result1 + c * 17
  }

  def subR(rDelta: Int): HCoord = HCoord(r -rDelta, c)

  def view(pxScale: Double = 50): HGridView = HGridView(r, c, pxScale)
}

/** Companion object for Hex coordinate trait, contains apply factory method and persist and PolygonBuilder implicit instances. */
object HCoord
{ /** Factory apply method for creating [[HCoord]]s .Creates an [[HCen]], an [[HSide]], an [[HVert]] or an [[HCoordOther]] depending on the values of
   *  r and c. */
  def apply(r: Int, c: Int): HCoord = r %% 4 match
  { case 0 if c.div4Rem0 => new HCen(r, c)
    case 0 if c.div4Rem2 => new HSide(r, c)
    case 2 if c.div4Rem0 => new HSide(r, c)
    case 2 if c.div4Rem2 => new HCen(r, c)
    case 1 | 3 if c.isOdd => new HSide(r, c)
    case 1 | 3 => HVert(r, c)
    case _ => new HCoordOther(r, c)// excep(s"$r, $c, where r is even and c is odd is not a valid HCoord hex grid coordinate.")
  }

  implicit val persistImplicit: PersistPrecision[HCoord] = new PersistShowInt2[HCoord]("HCoord", "r", "c", HCoord(_, _))

  implicit val polygonBuildImplicit: PolygonInt2sBuilder[HCoord, PolygonHC] = new PolygonInt2sBuilder[HCoord, PolygonHC]
  { override type BuffT = HCoordBuff
    override def fromIntArray(array: Array[Int]): PolygonHC = new PolygonHC(array)
    override def fromIntBuffer(inp: ArrayBuffer[Int]): HCoordBuff = new HCoordBuff(inp)
  }
}

trait HNotVert extends HCoord
{ override def toVec: Vec2 = Vec2(c, r * Sqrt3)
  override def toPt2: Pt2 = Pt2(c, r  * Sqrt3)
}

/** Common trait for hex centre and hex side coordinate. The position of these coordinates is proportional, unlike the Hex vertices positions. */
trait HCenOrSide extends HNotVert with TileCenOrSide

/** Companion object for [[HCenOrSide]] trait, contains factory apply method and implicit [[PersistPrecision]] instance. */
object HCenOrSide
{ /** Apply factory method for creating [[HCenOrSide]] instances. Will throw exception on illegal values.  */
  def apply(r: Int, c: Int): HCenOrSide = r %% 4 match
  { case 0 if c.div4Rem0 => new HCen(r, c)
    case 2 if c.div4Rem2 => new HCen(r, c)
    case 0 if c.div4Rem2 => new HSide(r, c)
    case 1 | 3 if c.isOdd => new HSide(r, c)
    case 2 if c.div4Rem0 => new HSide(r, c)
    case _ => excep(s"$r, $c is not a valid HCenOrSide hex grid coordinate.")
  }

  implicit val persistImplicit: PersistShowInt2[HCenOrSide] = new PersistShowInt2[HCenOrSide]("HCenOrSide", "r", "c", HCenOrSide(_, _))
}

/** The only purpose of this class is to ensure that all [[HCoord]] combinations of r and c are valid. Thisis for the combinations where r is even and
 *  c is odd. */
class HCoordOther(val r: Int, val c: Int) extends HNotVert
{ override def typeStr: String = "HCoordOther"
}