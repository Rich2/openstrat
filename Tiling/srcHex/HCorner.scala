/* Copyright 2018-23 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package prid; package phex

/** Hex tile corner. A corner encodes 1 or 2 [[HVOffset]]s. An [[HVert]] is shared between 3 hex tiles and 3 [[HSide]]s. An [[HCoroner]] only applies
 *  to a single hex tile. Hence unless it is on the edge of the [[HGridSys]] there will be 3 [[HCorner]]s associated with each [[HVert]]. This class
 *  encodes a single or two [[HVertoffset]]s. */
class HCorner(val unsafeInt: Int) extends AnyVal
{
  def numVerts: Int = unsafeInt %% 4

  override def toString: String = "HCorner " + numVerts

  /** Returns the first, going clockwise and possibly only [[HVOffset]] of this corner */
  def v1(hVert: HVert): HVAndOffset =
  { val dirn = HVDirnOpt.fromInt((unsafeInt %% 32) / 4)
    val magnitude = (unsafeInt %% 256) / 32
    HVAndOffset(hVert, dirn, magnitude)
  }

  /** Returns the second, going clockwise [[HVOffset]] of this corner. throws an exception if there is only 1. */
  def v2(hVert: HVert): HVAndOffset =
  { if(numVerts < 2) excep(s"Trying to access the second HVOffset for a Corner that has only $numVerts.")
    val dirn = HVDirnOpt.fromInt((unsafeInt %% 8192) / 1024)
    val magnitude = (unsafeInt %% 65536) / 8192
    HVAndOffset(hVert, dirn, magnitude)
  }

  /** Returns the second, going clockwise [[HVOffset]] of this corner if there is a second [[HVOffset]] on this [[HCorner]] else returns first. */
  def vLast(hVert: HVert): HVAndOffset = ife(numVerts == 2, v2(hVert), v1(hVert))

  def verts(hVert: HVert): HVAndOffsetArr = unsafeInt %% 4 match
  { case 0 => HVAndOffsetArr(HVAndOffset.none(hVert))
    case 1 | 3 => HVAndOffsetArr(v1(hVert))

    case 2 =>
    { val r1: HVAndOffset = v1(hVert)
      val r2: HVAndOffset = v2(hVert)
      HVAndOffsetArr(r1, r2)
    }
    case n  => excep(s"$n is an invalid value for offsets.")
  }

  def sideVerts(hVert: HVert): HVAndOffsetArr = HVAndOffsetArr(v1(hVert))

  def sideVertsSpecial(hVert: HVert): HVAndOffsetArr = numVerts match
  { case 0 | 1 | 2 => HVAndOffsetArr(v1(hVert))
    case 3 => HVAndOffsetArr(v1(hVert), v2(hVert))
    case n => excep(s"$n is an invalid value for offsets.")
  }
}

/** Companion object for [[HCorner]], contains factory apply methods for creating no offset, single and double [[HVoffsets]]. */
object HCorner
{ def noOffset: HCorner = new HCorner(0)

  def single(dirn: HVDirnOpt, magnitude : Int): HCorner = new HCorner(1 + 4 * dirn.int1 + magnitude * 32)

  def double(dirn1: HVDirnOpt, magnitude1 : Int, dirn2: HVDirnOpt, magnitude2 : Int): HCorner =
  { val v1 = dirn1.int1 * 4 + magnitude1 * 32
    val v2 = dirn2.int1 + magnitude2 * 8
    new HCorner(2 + v1 + v2 * 1024)
  }

  def sideDouble(dirn1: HVDirnOpt, magnitude1: Int, dirn2: HVDirnOpt, magnitude2: Int): HCorner =
  { val v1 = dirn1.int1 * 4 + magnitude1 * 32
    val v2 = dirn2.int1 + magnitude2 * 8
    new HCorner(3 + v1 + v2 * 1024)
  }
}