/* Copyright 2018-22 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package prid; package psq
import collection.mutable.ArrayBuffer

trait SqCenOrSide extends Any with SqCoord with TCenOrSide

object SqCenOrSide
{
  def apply(r: Int, c:Int): SqCenOrSide = r match {
    case r if r.isEven & c.isEven => new SqCen(r, c)
    case r if r.isEven => new SqSide(r, c)
    case r if c.isEven => new SqSide(r, c)
    case _ => debexc(s"$r, $c is not a valid SqCenOrSide coordinate it is a SqVert Square grid vertex coordinate.")
  }
}

/** A Square tile side square grid [[SqGrid]] coordinate. */
class SqSide(val r: Int, val c: Int) extends SqCenOrSide with TSide
{ override def typeStr: String = "Sqside"
}

object SqSide{
  def apply(r: Int, c: Int): SqSide = new SqSide(r, c)
}

/** A Square tile vertex square grid [[SqGrid]] coordinate. */
class SqVert private(val bLong: Long) extends AnyVal with SqCoord with TCoord
{ override def typeStr: String = "Sqvert"
  @inline def r: Int = bLong.>>(32).toInt
  @inline def c: Int = bLong.toInt

  def + (sqCen: SqCen): SqVert = SqVert(r + sqCen.r, c + sqCen.c)
}

/** Companion object for [[SqVert]] trait. Contains [[ShowT]] and builder implicit instances. */
object SqVert
{ val showTImplicit: ShowT[SqVert] = ShowShowInt2T("Sqvert", "r", "c")
  def apply(r: Int, c: Int): SqVert = if (r.isOdd & c.isOdd)
    new SqVert(r.toLong.<<(32) | (c & 0xFFFFFFFFL))
  else excep(s"$r, $c is not a valid Hex vertex tile coordinate.")

  implicit val sqVertsBuildImplicit: Int2ArrBuilder[SqVert, SqVertArr] = new Int2ArrBuilder[SqVert, SqVertArr]
  { type BuffT = SqVertBuff
    override def fromIntArray(array: Array[Int]): SqVertArr = new SqVertArr(array)
    override def fromIntBuffer(buffer: ArrayBuffer[Int]): SqVertBuff = new SqVertBuff(buffer)
  }
}