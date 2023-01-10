/* Copyright 2018-23 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package prid; package phex

/** The direction and magnitude of an [[HVAndOffset]]. These values are stored in an [[HVertOffsetLayer]]. The value of the [[HVert]] can be determined by its position in [[HVertOffsetLayer]]. */
class HVertOffset(val int1: Int) extends AnyVal with Int1Elem
{ def hvDirn: HVDirn = HVDirn.fromInt(int1 %% 8)
  def offset: Int = int1 / 8
}

/** This class encodes a single or two [[HVertoffset]]s. */
class HVOffsetNode(val unsafeInt: Int) extends AnyVal{
  def v1(hVert: HVert): HVAndOffset ={
    val dirn = HVDirn.fromInt((unsafeInt %% 32) / 4)
    val magnitude = (unsafeInt %% 256) / 32
    HVAndOffset(hVert, dirn, magnitude)
  }

  def v2(hVert: HVert): HVAndOffset = {
    val dirn = HVDirn.fromInt((unsafeInt %% 1024) / 256)
    val magnitude = (unsafeInt %% 8192) / 1024
    HVAndOffset(hVert, dirn, magnitude)
  }

  def verts(hVert: HVert) = unsafeInt %% 4 match {
    case 0 => RArr(HVAndOffset.none(hVert))
    case 1 => RArr(v1(hVert))
    case 2 => RArr(v1(hVert), v2(hVert))
    case n  => excep(s"$n is an invalid value for offsets.")
  }
}

/** Companion object for [[HVOffsetNode]], contains factory apply methods for creating no offset, single and double [[HVoffsets]]. */
object HVOffsetNode
{ def noOffset: HVOffsetNode = new HVOffsetNode(0)

  def single(dirn: HVDirn, magnitude : Int): HVOffsetNode = new HVOffsetNode(1 + 4 * dirn.intValue + magnitude * 32)

  def double(dirn1: HVDirn, magnitude1 : Int, dirn2: HVDirn, magnitude2 : Int): HVOffsetNode =
  { val v1 = dirn1.intValue * 4 + magnitude1 * 32
    val v2 = dirn2.intValue * 4 + magnitude2 * 32
    new HVOffsetNode(1 + v1 + v2 * 256)
  }
}

/** [[HGridSys]] data layer class that allows the hex tile vertices to be shifted by a small amount to create more pleasing terrain and to feature islands, straits and other tile side features. Every [[HCen]] hex tile in the [[HGridSys]] has 6 vertex
 * entries. */
class HVertOffsetLayer(val unsafeArray: Array[Int])
{
  def apply(hCen: HCen, vertNum: Int)(implicit gridSys: HGridSys): HVOffsetNode = new HVOffsetNode(unsafeArray(gridSys.arrIndex(hCen) * 6 + vertNum))

  def apply(hCenR: Int, hCenC: Int, vertNum: Int)(implicit gridSys: HGridSys): HVOffsetNode = new HVOffsetNode(unsafeArray(gridSys.arrIndex(hCenR, hCenC) * 6 + vertNum))

  def hVertOffsetsPolygon(hCen: HCen)(implicit gridSys: HGridSys): Arr[HVOffsetNode] = iUntilMap(6){ i => apply(hCen, i) }
  //def hVertAndOffsetPolygon(hCen: HCen)/*: HVertAndOffsetPolygon*/ = iUntilMap(6){ i => apply(hCen, i)}
}