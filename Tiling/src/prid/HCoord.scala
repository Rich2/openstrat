/* Copyright 2018-21 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package prid
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

/** Companion object for Hex coordinate trait, contains apply factory object. */
object HCoord
{
  def apply(r: Int, c: Int): HCoord = r %% 4 match
  { case 0 if c.div4Rem0 => new HCen(r, c)
    case 2 if c.div4Rem2 => new HCen(r, c)
    case 0 if c.div4Rem0 => new HSide(r, c)
    case 1 | 3 if c.isOdd => new HSide(r, c)
    case 2 if c.div4Rem0 => new HSide(r, c)
    case _ if r.isOdd & c.isEven => HVert(r, c)
    case _ => excep(s"$r, $c is not a valid HCoord hex grid coordinate.")
  }

  implicit val persistImplicit: Persist[HCoord] = new PersistShowInt2[HCoord]("HCoord", "r", "c", HCoord(_, _))

  implicit val polygonBuildImplicit: PolygonInt2sBuilder[HCoord, PolygonHC] = new PolygonInt2sBuilder[HCoord, PolygonHC]
  { override type BuffT = HCoordBuff
    override def fromIntArray(array: Array[Int]): PolygonHC = new PolygonHC(array)
    override def fromIntBuffer(inp: ArrayBuffer[Int]): HCoordBuff = new HCoordBuff(inp)
  }
}