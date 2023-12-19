/* Copyright 2018-23 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package prid; package psq
import collection.mutable.ArrayBuffer

/** Common trait for a [[SqCen]] square centres and a [[SqSep]] square separator coordinates. */
trait SqCenOrSep extends Any with SqCoord with TCenOrSep

object SqCenOrSep
{
  def apply(r: Int, c:Int): SqCenOrSep = r match
  { case r if r.isEven & c.isEven => new SqCen(r, c)
    case r if r.isEven => new SqSep(r, c)
    case r if c.isEven => new SqSep(r, c)
    case _ => debexc(s"$r, $c is not a valid SqCenOrSide coordinate it is a SqVert Square grid vertex coordinate.")
  }
}

/** A Square tile seprartor square grid [[SqGrid]] coordinate. */
class SqSep(val r: Int, val c: Int) extends SqCenOrSep with TSep
{ override def typeStr: String = "Sqside"
}

object SqSep
{ def apply(r: Int, c: Int): SqSep = new SqSep(r, c)
}

/** A Square tile vertex square grid [[SqGrid]] coordinate. */
class SqVert private(val bLong: Long) extends AnyVal with SqCoord with TCoord
{ override def typeStr: String = "Sqvert"
  @inline def r: Int = bLong.>>(32).toInt
  @inline def c: Int = bLong.toInt

  def + (sqCen: SqCen): SqVert = SqVert(r + sqCen.r, c + sqCen.c)
}

/** Companion object for [[SqVert]] trait. Contains [[Show]] and builder implicit instances. */
object SqVert
{
  def apply(r: Int, c: Int): SqVert = if (r.isOdd & c.isOdd) new SqVert(r.toLong.<<(32) | (c & 0xFFFFFFFFL))
    else excep(s"$r, $c is not a valid Hex vertex tile coordinate.")



  implicit val sqVertsBuildImplicit: BuilderArrInt2Map[SqVert, SqVertArr] = new BuilderArrInt2Map[SqVert, SqVertArr]
  { type BuffT = SqVertBuff
    override def fromIntArray(array: Array[Int]): SqVertArr = new SqVertArr(array)
    override def fromIntBuffer(buffer: ArrayBuffer[Int]): SqVertBuff = new SqVertBuff(buffer)
  }

  /** [[Show]] type class instance / evidence for [[SqVert]]. */
  implicit lazy val showEv: Show[SqVert] = ShowTellInt2("Sqvert")

  /** [[Unshow]] type class instance / evidence for [[SqVert]]. */
  implicit lazy val unshowEv: UnshowInt2[SqVert] = UnshowInt2("Sqvert", "r", "c", apply)
}