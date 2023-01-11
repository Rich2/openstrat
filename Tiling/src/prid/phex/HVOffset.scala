/* Copyright 2018-23 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package prid; package phex
import geom._

/** [[HVert]] offset. The direction and magnitude of an [[HVAndOffset]]. These values are stored in an [[HVertOffsetLayer]]. The value of the
 *  [[HVert]] can be determined by its position in [[HVertOffsetLayer]]. */
class HVOffset(val int1: Int) extends AnyVal with Int1Elem
{ def hvDirn: HVDirn = HVDirn.fromInt(int1 %% 8)
  def magnitude: Int = int1 / 8
}

/** Companion object for [[HVOffset]] class, contains factory apply and fromInt methods. */
object HVOffset
{ def apply(dirn: HVDirn, magnitude: Int): Int = dirn.intValue + magnitude * 8
  def fromInt(inp: Int): HVOffset = new HVOffset(inp)
}

/** Hex tile corner. An [[HVert]] is shared between 3 hex tiles and 3 [[HSide]]s. An [[HCoroner]] only applies to a single hex tile. Hence unless it
 * is on the edge of the [[HGridSys]] there will be 3 [[HCorner]]s associated with each [[HVert]]. This class encodes a single or two
 * [[HVertoffset]]s. */
class HCorner(val unsafeInt: Int) extends AnyVal
{
  def v1(hVert: HVert): HVAndOffset =
  { val dirn = HVDirn.fromInt((unsafeInt %% 32) / 4)
    val magnitude = (unsafeInt %% 256) / 32
    HVAndOffset(hVert, dirn, magnitude)
  }

  def v2(hVert: HVert): HVAndOffset =
  { val dirn = HVDirn.fromInt((unsafeInt %% 1024) / 256)
    val magnitude = (unsafeInt %% 8192) / 1024
    HVAndOffset(hVert, dirn, magnitude)
  }

  def verts(hVert: HVert): HVAndOffsetArr = unsafeInt %% 4 match
  { case 0 => HVAndOffsetArr(HVAndOffset.none(hVert))
    case 1 => HVAndOffsetArr(v1(hVert))
    case 2 => HVAndOffsetArr(v1(hVert), v2(hVert))
    case n  => excep(s"$n is an invalid value for offsets.")
  }
}

/** Companion object for [[HCorner]], contains factory apply methods for creating no offset, single and double [[HVoffsets]]. */
object HCorner
{ def noOffset: HCorner = new HCorner(0)

  def single(dirn: HVDirn, magnitude : Int): HCorner = new HCorner(1 + 4 * dirn.intValue + magnitude * 32)

  def double(dirn1: HVDirn, magnitude1 : Int, dirn2: HVDirn, magnitude2 : Int): HCorner =
  { val v1 = dirn1.intValue * 4 + magnitude1 * 32
    val v2 = dirn2.intValue * 4 + magnitude2 * 32
    new HCorner(1 + v1 + v2 * 256)
  }
}

/** [[HGridSys]] data layer class that allows the hex tile vertices to be shifted by a small amount to create more pleasing terrain and to feature islands, straits and other tile side features. Every [[HCen]] hex tile in the [[HGridSys]] has 6 vertex
 * entries. */
class HVertOffsetLayer(val unsafeArray: Array[Int])
{
  def unsafeIndex(hCen: HCen, vertNum: Int)(implicit gridSys: HGridSys): Int = gridSys.arrIndex(hCen) * 6 + vertNum
  def corner(hCen: HCen, vertNum: Int)(implicit gridSys: HGridSys): HCorner = new HCorner(unsafeArray(unsafeIndex(hCen, vertNum)))

  def corner(hCenR: Int, hCenC: Int, vertNum: Int)(implicit gridSys: HGridSys): HCorner =
    new HCorner(unsafeArray(gridSys.arrIndex(hCenR, hCenC) * 6 + vertNum))

  def tileCorners(hCen: HCen)(implicit gridSys: HGridSys): Arr[HCorner] = iUntilMap(6){ i => corner(hCen, i) }
  def tileCorners(cenR: Int, cenC: Int)(implicit gridSys: HGridSys): Arr[HCorner] = iUntilMap(6){ i => corner(cenR, cenC, i) }
  def tilePoly(hCen: HCen)(implicit gridSys: HGridSys): PolygonHVAndOffset = tileCorners(hCen).iFlatMapPolygon{ (i, corn) => corn.verts(hCen.verts(i)) }
  def tilePoly(cenR: Int, cenC: Int)(implicit gridSys: HGridSys): PolygonHVAndOffset = tilePoly(HCen(cenR, cenC))

  def setSingle(hCen: HCen, vertNum: Int, dirn: HVDirn, magnitude: Int)(implicit gridSys: HGridSys): Unit =
  { val corner = HCorner.single(dirn, magnitude)
    val index = unsafeIndex(hCen, vertNum)
    unsafeArray(index) = corner.unsafeInt
  }
}