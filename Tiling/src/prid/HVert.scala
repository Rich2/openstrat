/* Copyright 2018-21 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package prid
import geom._

/** A hex tile vertex coordinate. */
class HVert private(val bLong: Long) extends AnyVal with HCoord with TileCoord
{ @inline def r: Int = bLong.>>(32).toInt
  @inline def c: Int = bLong.toInt
  override def typeStr: String = "HVert"
  override def canEqual(that: Any): Boolean = ???

  override def toVec: Vec2 = (r %% 4, c %% 4) match
  { case (1, 0) | (3, 2)  =>  Vec2(c, r * Sqrt3 + 1.0/Sqrt3)
    case _ => Vec2(c , r * Sqrt3 - 1.0/Sqrt3)
  }

  override def toPt2: Pt2 = (r %% 4, c %% 4) match
  { case (1, 0) | (3, 2)  =>  Pt2(c, r  * Sqrt3 + 1.0/Sqrt3)
    case _ => Pt2(c, r * Sqrt3 - 1.0/Sqrt3)
  }

  def + (hCen: HCen): HVert = HVert(r + hCen.r, c + hCen.c)
}

object HVert
{
  def apply(r: Int, c: Int): HVert = if (r.isOdd & c.isEven)
    new HVert(r.toLong.<<(32) | (c & 0xFFFFFFFFL))
    else excep(s"$r, $c is not a valid Hex vertex tile coordinate.")

 // implicit object persistImplicit extends Persist2Ints[HVert]("Rood", "r", "c", apply)

  implicit val hVertsBuildImplicit: ArrInt2sBuilder[HVert, HVerts] = new ArrInt2sBuilder[HVert, HVerts]
  { type BuffT = HVertBuff
    override def fromIntArray(array: Array[Int]): HVerts = new HVerts(array)
    override def fromIntBuffer(inp: Buff[Int]): HVertBuff = new HVertBuff(inp)
  }
}