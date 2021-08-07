/* Copyright 2018-21 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package prid
import geom._, collection.mutable.ArrayBuffer

/** A hex tile vertex coordinate. */
class HVert private(val bLong: Long) extends AnyVal with HCoord with ElemInt2
{
  @inline def r: Int = bLong.>>(32).toInt
  @inline def c: Int = bLong.toInt
  override def int1: Int = r
  override def int2: Int = c
  override def typeStr: String = "HVert"
  override def canEqual(that: Any): Boolean = ???

  override def toVec: Vec2 = (r %% 4, c %% 4) match
  { case (1, 0) | (3, 2)  =>  Vec2(c / Sqrt3, r + 1.0 / 3)
    case _ => Vec2(c / Sqrt3, r - 1.0 / 3)
  }

  override def toPt2: Pt2 = (r %% 4, c %% 4) match
  { case (1, 0) | (3, 2)  =>  Pt2(c / Sqrt3, r + 1.0 / 3)
    case _ => Pt2(c / Sqrt3, r - 1.0 / 3)
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