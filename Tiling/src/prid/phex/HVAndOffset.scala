/* Copyright 2018-23 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package prid; package phex
import geom._

import collection.mutable.ArrayBuffer

/** An [[HVert]] and an offset. The Offset of from the [[HVert]] measured in an offset towards a neighbouring [[HCen]] or [[HVert]]. */
class HVAndOffset(val int1: Int, val int2: Int, val int3: Int) extends Int3Elem
{
  inline def r: Int = int1

  inline def c: Int = int2

  /** The [[HVert]]. */
  def vert: HVert = HVert(r, c)

  def hvOffset: HVOffset = HVOffset.fromInt(int3)

  def magnitude: Int = hvOffset.magnitude

  def hvDirn: HVDirn = hvOffset.hvDirn

  def hCen2 = HCen(r + hvDirn.dCenR, c + hvDirn.dCenC)
  def hVert2 = HVert(r + hvDirn.dVertR, c + hvDirn.dVertC)
  def hVert3 = HVert(r - hvDirn.dVertR, c - hvDirn.dVertC)

  def isCenDirn: Boolean = hvDirn match {
    case HVUp | HVDR | HVDL if r.div4Rem3 & c.div4Rem0 => true
    case HVUp | HVDR | HVDL if r.div4Rem1 & c.div4Rem2  => true
    case HVUR | HVDn | HVUL if r.div4Rem1 & c.div4Rem0 => true
    case HVUR | HVDn | HVUL if r.div4Rem3 & c.div4Rem2 => true
    case _ => false
  }


  def toPt2Reg(f: HCoord => Pt2)(implicit hSys: HGridSys): Pt2 =
  {
    val p1 = f(vert)
    isCenDirn match
    { case _ if hvDirn == HVExact => p1

      case true if hSys.hCenExists(hCen2) =>
      { val p2 = f(hCen2)
        val x = ((8 - magnitude) * p1.x + magnitude * p2.x) / 8
        val y = ((8 - magnitude) * p1.y + magnitude * p2.y) / 8
        Pt2(x, y)
      }

      case true =>
      { val p2 = f(hVert3)
        val x = ((8 - magnitude) * p1.x + magnitude * p2.x) / 8
        val y = ((8 - magnitude) * p1.y + magnitude * p2.y) / 8
        Pt2(x, y)
      }

      case _ =>
      { val p2 = f(hVert2)
        val x = ((8 - magnitude) * p1.x + magnitude * p2.x) / 8
        val y = ((8 - magnitude) * p1.y + magnitude * p2.y) / 8
        Pt2(x, y)
      }
    }
  }
}

/** Companion object for [[HVAndOffset]] class contains factory apply and none methods. End users should rarely need to use the class constructor
 * directly. */
object HVAndOffset
{
  def apply(hVert: HVert, hvDirn: HVDirn, offset: Int): HVAndOffset = apply(hVert.r, hVert.c, hvDirn, offset)

  def apply(r: Int, c: Int, hvDirn: HVDirn, magnitude: Int): HVAndOffset =
  { val magnitude2 = ife(magnitude < 0, -magnitude, magnitude)
    val dirn2 = ife(magnitude < 0, hvDirn.opposite, hvDirn)

    val isCenDirn: Boolean = hvDirn match {
      case HVUp | HVDR | HVDL if r.div4Rem3 & c.div4Rem0 => true
      case HVUp | HVDR | HVDL if r.div4Rem1 & c.div4Rem2 => true
      case HVUR | HVDn | HVUL if r.div4Rem1 & c.div4Rem0 => true
      case HVUR | HVDn | HVUL if r.div4Rem3 & c.div4Rem0 => true
      case _ => false
    }

    val magnitude3 = ife(isCenDirn, magnitude2.min(7), magnitude2.min(3))

    val hVertOffset = HVOffset(dirn2, magnitude3)
    new HVAndOffset(r, c, hVertOffset)
  }

  def none(r: Int, c: Int) = new HVAndOffset(r, c, 0)
  def none(hVert: HVert) = new HVAndOffset(hVert.r, hVert.c, 0)

  /** Implicit type class instance / evidence for the [[HVAndOffset]] type class instance of [[PolygonLikeMapBuilder]]. */
  implicit val polygonBuildEv: PolygonHVAndOffsetMapBuilder = new PolygonHVAndOffsetMapBuilder

  /** Implicit type class instance / evidence for the [[HVAndOffset]] type class instance of [[PolygonLikeMapBuilder]]. */
  implicit val polygonFlatBuildEv: PolygonHVAndOffsetFlatBuilder = new PolygonHVAndOffsetFlatBuilder
}

/** A Line segment where the vertices of specified in [[HVAndOffset]]s. */
class LineSegHVAndOffset(val int1: Int, val int2: Int, val int3: Int, val int4: Int, val int5: Int, val int6: Int) extends
  LineSegLikeInt6[HVAndOffset]
{ override def startPt: HVAndOffset = new HVAndOffset(int1, int2, int3)
  override def endPt: HVAndOffset = new HVAndOffset(int4, int5, int6)
}

object LineSegHVAndOffset{
  def apply(v1: HVAndOffset, v2: HVAndOffset): LineSegHVAndOffset = new LineSegHVAndOffset(v1.int1, v1.int2, v1.int3, v2.int1, v2.int2, v2.int3)
}

trait HVAndOffsetSeqLike extends Int3SeqLike[HVAndOffset]
{
  final override def newElem(int1: Int, int2: Int, int3: Int): HVAndOffset = new HVAndOffset(int1, int2, int3)
  final override def fElemStr: HVAndOffset => String = _.toString
}

class HVAndOffsetArr(val unsafeArray: Array[Int]) extends HVAndOffsetSeqLike with Int3Arr[HVAndOffset]
{ override type ThisT = HVAndOffsetArr
  override def typeStr: String = "HVAndOffsetArr"
  override def fromArray(array: Array[Int]): HVAndOffsetArr = new HVAndOffsetArr(array)
}

object HVAndOffsetArr extends Int3SeqLikeCompanion [HVAndOffset, HVAndOffsetArr]
{ override def fromArray(array: Array[Int]): HVAndOffsetArr = new HVAndOffsetArr(array)


}

/** Specialised [[Buff]] class for [[HVAndOffset]]s. The [[HVert]] with offset class. */
class HVAndOffsetBuff(val unsafeBuffer: ArrayBuffer[Int]) extends Int3Buff[HVAndOffset]
{ override type ThisT = HVAndOffsetBuff
  override type ArrT = HVAndOffsetArr
  override def typeStr: String = "HVAndoffsetBuff"
  override def newElem(int1: Int, int2: Int, int3: Int): HVAndOffset = new HVAndOffset(int1, int2, int3)
}

/** A polygon where the vertices are specified in [[HVAndOffset]]s. */
class PolygonHVAndOffset(val unsafeArray: Array[Int]) extends HVAndOffsetSeqLike with PolygonLikeInt3[HVAndOffset]
{ override type ThisT = PolygonHVAndOffset
  override type SideT = LineSegHVAndOffset
  override def typeStr: String = "HVAndOffsetPolygon"

  override def fromArray(array: Array[Int]): PolygonHVAndOffset = new PolygonHVAndOffset(array)

  @inline def side(index: Int): LineSegHVAndOffset = LineSegHVAndOffset(vert(index), ife(index == vertsNum - 1, vert(0), vert(index + 1)))

  override def sidesForeach[U](f: LineSegHVAndOffset => U): Unit =
  { var i = 0
    while (i < vertsNum) {
      f(side(i)); i += 1
    }
  }
}

trait PolgonHVAndOffsetCommonBuilder extends Int3SeqLikeCommonBuilder[PolygonHVAndOffset]
{ override type BuffT = HVAndOffsetBuff
  override def fromIntArray(array: Array[Int]): PolygonHVAndOffset = new PolygonHVAndOffset(array)
  override def fromIntBuffer(inp: ArrayBuffer[Int]): HVAndOffsetBuff = new HVAndOffsetBuff(inp)
}

class PolygonHVAndOffsetMapBuilder extends PolgonHVAndOffsetCommonBuilder with PolygonInt3MapBuilder[HVAndOffset, PolygonHVAndOffset]

class PolygonHVAndOffsetFlatBuilder extends PolgonHVAndOffsetCommonBuilder with PolygonInt3FlatBuilder[HVAndOffset, PolygonHVAndOffset]
{ /** converts a the buffer type to the target compound class. */
  override def buffToSeqLike(buff: HVAndOffsetBuff): PolygonHVAndOffset = ???
}