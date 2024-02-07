/* Copyright 2018-23 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package prid; package phex
import geom._, collection.mutable.ArrayBuffer

/** The relative offset from an [[HVert]] towards a neighbouring [[HCen]] or [[HVert]], defined in [[Int]]s. */
class HvRelOffset(val int1: Int, val int2: Int, val int3: Int) extends Int3Elem
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

  /*def target: HCoord = hvDirn match
  { case HVUp if vHigh => HVertLow(r + 2, c)
    case HVUp => HCen(r + 1, c)
    case HVUR if vHigh => HCen(r + 1, c + 2)
    case HVUR => HVertHigh(r, c + 2)
    case HVDR if vHigh => HVertLow(r, c + 2)
    case HVDR => HCen(r - 1, c + 2)
    case HVDn if vHigh => HCen(r - 1, c)
    case HVDn => HVertHigh(r - 2, c)
    case HVDL if vHigh => HVertLow(r, c - 2)
    case HVDL => HCen(r - 1, c - 2)
    case HVUL if vHigh => HCen(r + 1, c - 2)
    case HVUL => HVertHigh(r, c - 2)
    //case HVRt if vHigh => HCen(r + 1, c - 2)
    case HVRt => HVert(r, c + 4)
    //case HVLt if vHigh => HCen(r + 1, c - 2)
    case HVLt => HVert(r, c - 4)
    case HVExact => vert
  }*/

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

  /** Converts this [[HvRelOffset]] to a [[PtM3]] using an [[HVert]] to [[PtM3]] function.  */
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

  /** Companion object for [[HvRelOffset]] class contains factory apply and none methods. End users should rarely need to use the class constructor
 * directly. */
object HvRelOffset
{
  def apply(hVert: HVert, hvDirn: HVDirnOpt, offset: Int): HvRelOffset = apply(hVert.r, hVert.c, hvDirn, offset)

  def apply(r: Int, c: Int, hvDirn: HVDirnOpt, magnitude: Int): HvRelOffset =
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
    new HvRelOffset(r, c, hVertOffset)
  }

  def none(r: Int, c: Int) = new HvRelOffset(r, c, 0)
  def none(hVert: HVert) = new HvRelOffset(hVert.r, hVert.c, 0)

  implicit val sarrMapBuilderImplicit: BuilderArrInt3Map[HvRelOffset, HvRelOffsetArr]  = new BuilderArrInt3Map[HvRelOffset, HvRelOffsetArr]
  { type BuffT = HVOffsetBuff
    override def fromIntBuffer(buffer: ArrayBuffer[Int]): HVOffsetBuff = new HVOffsetBuff(buffer)
    override def fromIntArray(array: Array[Int]): HvRelOffsetArr = new HvRelOffsetArr(array)
  }

  /** Implicit type class instance / evidence for the [[HvRelOffset]] type class instance of [[PolygonLikeMapBuilder]]. */
  implicit val polygonBuildEv: PolygonHVAndOffsetMapBuilder = new PolygonHVAndOffsetMapBuilder

  /** Implicit type class instance / evidence for the [[HvRelOffset]] type class instance of [[PolygonLikeMapBuilder]]. */
  implicit val polygonFlatBuildEv: PolygonHVAndOffsetFlatBuilder = new PolygonHVAndOffsetFlatBuilder
}

trait HvRelOffsetSeqLike extends SeqLikeInt3[HvRelOffset]
{
  final override def newElem(int1: Int, int2: Int, int3: Int): HvRelOffset = new HvRelOffset(int1, int2, int3)
  final override def fElemStr: HvRelOffset => String = _.toString
}

class HvRelOffsetArr(val arrayUnsafe: Array[Int]) extends HvRelOffsetSeqLike with ArrInt3[HvRelOffset]
{ override type ThisT = HvRelOffsetArr
  override def typeStr: String = "HVAndOffsetArr"
  override def fromArray(array: Array[Int]): HvRelOffsetArr = new HvRelOffsetArr(array)
}

object HvRelOffsetArr extends CompanionSeqLikeInt3 [HvRelOffset, HvRelOffsetArr]
{ override def fromArray(array: Array[Int]): HvRelOffsetArr = new HvRelOffsetArr(array)
}

/** Specialised [[BuffSequ]] class for [[HvRelOffset]]s. The [[HVert]] with offset class. */
class HVOffsetBuff(val unsafeBuffer: ArrayBuffer[Int]) extends BuffInt3[HvRelOffset]
{ override type ThisT = HVOffsetBuff
  override type ArrT = HvRelOffsetArr
  override def typeStr: String = "HVAndoffsetBuff"
  override def newElem(int1: Int, int2: Int, int3: Int): HvRelOffset = new HvRelOffset(int1, int2, int3)
}