/* Copyright 2018-24 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package prid; package phex
import geom._, collection.mutable.ArrayBuffer

/** A point in [[HCoord]] space offset from an [[HVert]] towards a neighbouring [[HCen]] or [[HVert]], defined in [[Int]]s. */
class HvOffset(val int1: Int, val int2: Int, val int3: Int) extends Int3Elem
{ /** The r or row component of the [[HVert]]. */
  inline def r: Int = int1

  /** The c or column component of the [[HVert]]. */
  inline def c: Int = int2

  override def toString: String = "HVAndOffset".appendParenthSemis(int1.str, int2.str, hvDirn.toString, magnitude.toString)

  /** The [[HVert]]. */
  def vert: HVert = HVert(r, c)

  def delta: HVOffsetDelta = HVOffsetDelta.fromInt(int3)

  def magnitude: Int = delta.magnitude

  def hvDirn: HVDirnOpt = delta.hvDirn

  def vHigh: Boolean = None match

  { case _ if r.div4Rem1 & c.div4Rem0 => true
    case _ if r.div4Rem3 & c.div4Rem2 => true
    case _ if r.div4Rem1 & c.div4Rem2 => false
    case _ if r.div4Rem3 & c.div4Rem0 => false
    case _ => excep(s"r = $r, c = $c Invalid valuse for HVert.")
  }

  /** The [[HCen]] the [[HVDirn]] points to if it is coming from the correct type of [[HVert]]. */
  def hCen: HCen = HCen(r + hvDirn.dCenR, c + hvDirn.dCenC)

  /** Not sure what this is. */
  def hVert2: HVert = HVert(r + hvDirn.dVertR, c + hvDirn.dVertC)

  /** Not sure what this is. */
  def hVert3: HVert = HVert(r - hvDirn.dVertR, c - hvDirn.dVertC)

  def isCenDirn: Boolean = hvDirn match {
    case HVUp | HVDR | HVDL if r.div4Rem3 & c.div4Rem0 => true
    case HVUp | HVDR | HVDL if r.div4Rem1 & c.div4Rem2  => true
    case HVUR | HVDn | HVUL if r.div4Rem1 & c.div4Rem0 => true
    case HVUR | HVDn | HVUL if r.div4Rem3 & c.div4Rem2 => true
    case _ => false
  }

  /** Converts this offset [[HVert]] to [[Pt2]]. */
  def toPt2(f: HCoord => Pt2)(implicit hSys: HGridSys): Pt2 = hvDirn match
  { case HVExact => f(vert)
    case hd: HVDirn => hSys.vertToCoordFind(vert, hd) match
    { case Some(hc2) =>
      { val p2 = f(hc2)
        val frac: Int = hd match {
          case HVLt | HVRt => 32
          case _ => 16
        }
        val x = ((frac - magnitude) * f(vert).x + magnitude * p2.x) / frac
        val y = ((frac - magnitude) * f(vert).y + magnitude * p2.y) / frac
        Pt2(x, y)
      }
      case _ => f(vert)
    }
  }

  /** Converts this [[HvOffset]] to a [[PtM3]] using an [[HVert]] to [[PtM3]] function.  */
  def toPtM3(f: HCoord => PtM3)(implicit hSys: HGridSys): PtM3 = hvDirn match {
    case HVExact => f(vert)
    case hd: HVDirn => hSys.vertToCoordFind(vert, hd) match {
      case Some(hc2) => {
        val p2 = f(hc2)
        val frac: Int = hd match {
          case HVLt | HVRt => 32
          case _ => 16
        }
        val x = ((frac - magnitude) * f(vert).x + magnitude * p2.x) / frac
        val y = ((frac - magnitude) * f(vert).y + magnitude * p2.y) / frac
        val z = ((frac - magnitude) * f(vert).z + magnitude * p2.z) / frac
        PtM3(x, y, z)
      }
      case _ => f(vert)
    }
  }
}

  /** Companion object for [[HvOffset]] class contains factory apply and none methods. End users should rarely need to use the class constructor
 * directly. */
object HvOffset
{
  def apply(hVert: HVert, hvDirn: HVDirnOpt, offset: Int): HvOffset = apply(hVert.r, hVert.c, hvDirn, offset)

  def apply(r: Int, c: Int, hvDirn: HVDirnOpt, magnitude: Int): HvOffset =
  { val magnitude2 = ife(magnitude < 0, -magnitude, magnitude)
    val dirn2 = ife(magnitude < 0, hvDirn.opposite, hvDirn)

    val isCenDirn: Boolean = hvDirn match {
      case HVUp | HVDR | HVDL if r.div4Rem3 & c.div4Rem0 => true
      case HVUp | HVDR | HVDL if r.div4Rem1 & c.div4Rem2 => true
      case HVUR | HVDn | HVUL if r.div4Rem1 & c.div4Rem0 => true
      case HVUR | HVDn | HVUL if r.div4Rem3 & c.div4Rem0 => true
      case _ => false
    }

    val magnitude3 = magnitude match
    { case m if  m > 15 => { deb(s"$m > 15 of HCen dirn"); m.min(7) }
      case m => m
    }

    val hVertOffset = HVOffsetDelta(dirn2, magnitude3)
    new HvOffset(r, c, hVertOffset)
  }

  def none(r: Int, c: Int) = new HvOffset(r, c, 0)
  def none(hVert: HVert) = new HvOffset(hVert.r, hVert.c, 0)

  implicit val sarrMapBuilderImplicit: BuilderArrInt3Map[HvOffset, HvOffsetArr]  = new BuilderArrInt3Map[HvOffset, HvOffsetArr]
  { type BuffT = HVOffsetBuff
    override def fromIntBuffer(buffer: ArrayBuffer[Int]): HVOffsetBuff = new HVOffsetBuff(buffer)
    override def fromIntArray(array: Array[Int]): HvOffsetArr = new HvOffsetArr(array)
  }

  /** Implicit type class instance / evidence for the [[HvOffset]] type class instance of [[PolygonLikeMapBuilder]]. */
  implicit val polygonBuildEv: PolygonHVAndOffsetMapBuilder = new PolygonHVAndOffsetMapBuilder

  /** Implicit type class instance / evidence for the [[HvOffset]] type class instance of [[PolygonLikeMapBuilder]]. */
  implicit val polygonFlatBuildEv: PolygonHVAndOffsetFlatBuilder = new PolygonHVAndOffsetFlatBuilder
}

trait HvOffsetSeqLike extends Any with SeqLikeInt3[HvOffset]
{ final override def newElem(int1: Int, int2: Int, int3: Int): HvOffset = new HvOffset(int1, int2, int3)
  final override def fElemStr: HvOffset => String = _.toString
}

class HvOffsetArr(val arrayUnsafe: Array[Int]) extends HvOffsetSeqLike with ArrInt3[HvOffset]
{ override type ThisT = HvOffsetArr
  override def typeStr: String = "HVAndOffsetArr"
  override def fromArray(array: Array[Int]): HvOffsetArr = new HvOffsetArr(array)
}

object HvOffsetArr extends CompanionSeqLikeInt3 [HvOffset, HvOffsetArr]
{ override def fromArray(array: Array[Int]): HvOffsetArr = new HvOffsetArr(array)
}

/** Specialised [[BuffSequ]] class for [[HvOffset]]s. The [[HVert]] with offset class. */
class HVOffsetBuff(val unsafeBuffer: ArrayBuffer[Int]) extends BuffInt3[HvOffset]
{ override type ThisT = HVOffsetBuff
  override type ArrT = HvOffsetArr
  override def typeStr: String = "HVAndoffsetBuff"
  override def newElem(int1: Int, int2: Int, int3: Int): HvOffset = new HvOffset(int1, int2, int3)
}