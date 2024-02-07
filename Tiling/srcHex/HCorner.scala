/* Copyright 2018-23 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package prid; package phex

/** Hex tile corner. A corner encodes 1 or 2 [[HVOffsetDelta]]s. An [[HVert]] is shared between 3 hex tiles and 3 [[HSep]]s. An [[HCorner]] only
 *  applies to a single hex tile. Hence unless it is on the edge of the [[HGridSys]] there will be 3 [[HCorner]]s associated with each [[HVert]]. This
 *  class encodes a single or two [[HVertoffset]]s. */
class HCorner(val unsafeInt: Int) extends AnyVal
{
  def numVerts: Int = unsafeInt %% 4

  override def toString: String = "HCorner " + numVerts

  /** Returns the first, going clockwise and possibly only [[HVOffsetDelta]] of this corner */
  def v1(hVert: HVert): HvOffset =
  { val dirn = HVDirnOpt.fromInt((unsafeInt %% 64) / 4)
    val magnitude = (unsafeInt %% 1024) / 64
    HvOffset(hVert, dirn, magnitude)
  }

  /** Returns the second, going clockwise [[HVOffsetDelta]] of this corner. throws an exception if there is only 1. */
  def v2(hVert: HVert): HvOffset =
  { if(numVerts < 2) excep(s"Trying to access the second HVOffset for a Corner that has only $numVerts.")
    val dirn = HVDirnOpt.fromInt((unsafeInt %% 32768) / 2048)
    val magnitude = (unsafeInt %% 524288) / 32768
    HvOffset(hVert, dirn, magnitude)
  }

  /** Returns the second, going clockwise [[HVOffsetDelta]] of this corner if there is a second [[HVOffsetDelta]] on this [[HCorner]] else returns first. */
  def vLast(hVert: HVert): HvOffset = ife(numVerts == 2, v2(hVert), v1(hVert))

  def verts(hVert: HVert): HvOffsetArr = unsafeInt %% 4 match
  { case 0 => HvOffsetArr(HvOffset.none(hVert))
    case 1 | 3 => HvOffsetArr(v1(hVert))

    case 2 =>
    { val r1: HvOffset = v1(hVert)
      val r2: HvOffset = v2(hVert)
      HvOffsetArr(r1, r2)
    }
    case n  => excep(s"$n is an invalid value for offsets.")
  }

  def isSpecial: Boolean = numVerts == 3
}

/** Companion object for [[HCorner]], contains factory apply methods for creating no offset, single and double [[HVoffsets]]. */
object HCorner
{ def noOffset: HCorner = new HCorner(0)

  /** Creates a corner with a single vertex. */
  def single(dirn: HVDirnOpt, magnitude : Int): HCorner = new HCorner(1 + 4 * dirn.int1 + magnitude * 64)

  /** Creates a corner with 2 vertexs. For example the corner of a sea hex at the mouth of straits or river needs 2 vertexs for the corresponding
   * corners of the land hexs. */
  def double(dirn1: HVDirnOpt, magnitude1 : Int, dirn2: HVDirnOpt, magnitude2 : Int): HCorner =
  { val v1 = dirn1.int1 * 4 + magnitude1 * 64
    val v2 = dirn2.int1 + magnitude2 * 16
    new HCorner(2 + v1 + v2 * 2048)
  }

  def sideSpecial(dirn1: HVDirnOpt, magnitude1: Int): HCorner =
  { val v1 = dirn1.int1 * 4 + magnitude1 * 64
    new HCorner(3 + v1)
  }
}