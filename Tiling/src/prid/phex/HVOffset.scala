/* Copyright 2018-23 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package prid; package phex

/** The direction and magnitude of an [[HVertAndOffset]]. These values are stored in an [[HVertOffsetLayer]]. The value of the [[HVert]] can be determined by its position in [[HVertOffsetLayer]]. */
class HVertOffset(val int1: Int) extends AnyVal with Int1Elem
{ def hvDirn: HVDirn = HVDirn.fromInt(int1 %% 8)
  def offset: Int = int1 / 8
}

/*class HVertOffsetSys(val unsafeArray: Array[Int])
{
  //def apply(hCen: HCen, vertNum: Int)
}*/

/** This class encodes a single or two [[HVertoffset]]s. */
class HVOffsets(val unsafeInt: Int) extends AnyVal{
  def v1(hVert: HVert): HVertAndOffset ={
    val dirn = HVDirn.fromInt((unsafeInt %% 32) / 4)
    val magnitude = (unsafeInt %% 256) / 32
    HVertAndOffset(hVert, dirn, magnitude)
  }

  def v2(hVert: HVert): HVertAndOffset = {
    val dirn = HVDirn.fromInt((unsafeInt %% 1024) / 256)
    val magnitude = (unsafeInt %% 8192) / 1024
    HVertAndOffset(hVert, dirn, magnitude)
  }

  def verts(hVert: HVert) = unsafeInt %% 4 match {
    case 0 => RArr(HVertAndOffset.none(hVert))
    case 1 => RArr(v1(hVert))
    case 2 => RArr(v1(hVert), v2(hVert))
    case n  => excep(s"$n is an invalid value for offsets.")
  }
}

/** Companion object for [[HVOffsets]], contains factory apply methods for creating no offset, single and double [[HVoffsets]]. */
object HVOffsets
{ def noOffset: HVOffsets = new HVOffsets(0)

  def single(dirn: HVDirn, magnitude : Int): HVOffsets = new HVOffsets(1 + 4 * dirn.intValue + magnitude * 32)

  def double(dirn1: HVDirn, magnitude1 : Int, dirn2: HVDirn, magnitude2 : Int): HVOffsets =
  { val v1 = dirn1.intValue * 4 + magnitude1 * 32
    val v2 = dirn2.intValue * 4 + magnitude2 * 32
    new HVOffsets(1 + v1 + v2 * 256)
  }
}

/** [[HGridSys]] data layer class that allows the hex tile vertices to be shifted by a small amount to create more pleasing terrain and to feature islands, straits and other tile side features. Every [[HCen]] hex tile in the [[HGridSys]] has 6 vertex
 * entries. */
class HVertOffsetLayer(val unsafeArray: Array[Int])
{
  def apply(hCen: HCen, vertNum: Int)(implicit gridSys: HGridSys): HVOffsets = new HVOffsets(unsafeArray(gridSys.arrIndex(hCen) * 6 + vertNum))

  def apply(hCenR: Int, hCenC: Int, vertNum: Int)(implicit gridSys: HGridSys): HVOffsets = new HVOffsets(unsafeArray(gridSys.arrIndex(hCenR, hCenC) * 6 + vertNum))
}