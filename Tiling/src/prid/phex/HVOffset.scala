/* Copyright 2018-23 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package prid; package phex
import geom._

/** [[HVert]] offset. The direction and magnitude of an [[HVAndOffset]]. An [[HCorner]] consists of 1 or 2 of these [[HVOffset]]. The [[HCorner]]
 * values are stored in an [[HCornerLayer]]. The value of the [[HVert]] can be determined by its position in [[HCornerLayer]]. */
class HVOffset(val int1: Int) extends AnyVal with Int1Elem
{ def hvDirn: HVDirn = HVDirn.fromInt(int1 %% 8)
  def magnitude: Int = int1 / 8
}

/** Companion object for [[HVOffset]] class, contains factory apply and fromInt methods. */
object HVOffset
{
  def apply(dirn: HVDirn, magnitude: Int): Int =
  { val m2 = magnitude match {
    case m if m >= 8 => { deb("> 8"); 7 }
    case m if m < 0 => { deb("< 0"); 0 }
    case m => m
  }
    dirn.int1 + m2 * 8
  }
  def fromInt(inp: Int): HVOffset = new HVOffset(inp)
}

/** Hex tile corner. A corner encodes 1 or 2 [[HVOffset]]s. An [[HVert]] is shared between 3 hex tiles and 3 [[HSide]]s. An [[HCoroner]] only applies
 *  to a single hex tile. Hence unless it is on the edge of the [[HGridSys]] there will be 3 [[HCorner]]s associated with each [[HVert]]. This class
 *  encodes a single or two [[HVertoffset]]s. */
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
    case 2 => {
      val r1 = v1(hVert)
      val r2 = v2(hVert)
      deb(s"Double verts $r1, $r2.")
      HVAndOffsetArr(r1, r2)
    }
    case n  => excep(s"$n is an invalid value for offsets.")
  }
}

/** Companion object for [[HCorner]], contains factory apply methods for creating no offset, single and double [[HVoffsets]]. */
object HCorner
{ def noOffset: HCorner = new HCorner(0)

  def single(dirn: HVDirn, magnitude : Int): HCorner = new HCorner(1 + 4 * dirn.int1 + magnitude * 32)

  def double(dirn1: HVDirn, magnitude1 : Int, dirn2: HVDirn, magnitude2 : Int): HCorner =
  { val v1 = dirn1.int1 * 4 + magnitude1 * 32
    val v2 = dirn2.int1 + magnitude2 * 32
    new HCorner(2 + v1 + v2 * 256)
  }
}

/** [[HGridSys]] data layer class that allows the hex tile vertices to be shifted by a small amount to create more pleasing terrain and to feature
 *  islands, straits and other tile side features. Every [[HCen]] hex tile in the [[HGridSys]] has 6 vertex entries. */
class HCornerLayer(val unsafeArray: Array[Int])
{ /** The number of corners stored / specified in this [[HCornerLayer]] object. This will be 6 for every tile in the associated [[HGridSys]]. */
  def numCorners: Int = unsafeArray.length

  def numTiles: Int = unsafeArray.length / 6
  def unsafeIndex(hCen: HCen, vertNum: Int)(implicit gridSys: HGridSys): Int = gridSys.layerArrayIndex(hCen) * 6 + vertNum
  def unsafeIndex(cenR: Int, cenC: Int, vertNum: Int)(implicit gridSys: HGridSys): Int = gridSys.layerArrayIndex(cenR, cenC) * 6 + vertNum

  /** Returns the specified [[HCorner]] object which specifies, 1 or 2 [[HVAndOffset]]s. */
  def corner(hCen: HCen, vertNum: Int)(implicit gridSys: HGridSys): HCorner = new HCorner(unsafeArray(unsafeIndex(hCen, vertNum)))

  /** Returns the specified [[HCorner]] object which specifies, 1 or 2 [[HVAndOffset]]s. */
  def corner(hCenR: Int, hCenC: Int, vertNum: Int)(implicit gridSys: HGridSys): HCorner =
    new HCorner(unsafeArray(gridSys.layerArrayIndex(hCenR, hCenC) * 6 + vertNum))

  /** Returns a single [[HVAndOffset]] for an [[HCorner]]. This is used for drawing [[HSide]] hex side line segments. */
  def cornerV1(hCen: HCen, vertNum: Int)(implicit gridSys: HGridSys): HVAndOffset = corner(hCen, vertNum).v1(hCen.verts(vertNum))

  /** Produces an [[HSide]]'s line segment specified in [[HVAndOffset]] coordinates. */
  def sideLine(hCen: HCen, vertNum1: Int, vertNum2: Int)(implicit gridSys: HGridSys): LineSegHVAndOffset = LineSegHVAndOffset(cornerV1(hCen, vertNum1), cornerV1(hCen, vertNum2))

  def tileCorners(hCen: HCen)(implicit gridSys: HGridSys): RArr[HCorner] = iUntilMap(6){ i => corner(hCen, i) }
  def tileCorners(cenR: Int, cenC: Int)(implicit gridSys: HGridSys): RArr[HCorner] = iUntilMap(6){ i => corner(cenR, cenC, i) }
  def tilePoly(hCen: HCen)(implicit gridSys: HGridSys): PolygonHVAndOffset = tileCorners(hCen).iFlatMapPolygon{ (i, corn) => corn.verts(hCen.verts(i)) }
  def tilePoly(cenR: Int, cenC: Int)(implicit gridSys: HGridSys): PolygonHVAndOffset = tilePoly(HCen(cenR, cenC))

  /** Sets the vertex offset for one adjacent hex. This could leave a gap for side terrain such as straits. */
  def setSingle(cenR: Int, cenC: Int, vertNum: Int, dirn: HVDirn, magnitude: Int)(implicit gridSys: HGridSys): Unit =
  { val corner = HCorner.single(dirn, magnitude)
    val index = unsafeIndex(cenR, cenC, vertNum)
    unsafeArray(index) = corner.unsafeInt
  }

  /** Sets the vertex offset for one adjacent hex. This could leave a gap for side terrain such as straits. */
  def setSingle(hCen: HCen, vertNum: Int, dirn: HVDirn, magnitude: Int)(implicit gridSys: HGridSys): Unit =
  { val corner = HCorner.single(dirn, magnitude)
    val index = unsafeIndex(hCen, vertNum)
    unsafeArray(index) = corner.unsafeInt
  }

  def setDouble(cenR: Int, cenC: Int, vertNum: Int, dirn1: HVDirn, magnitude1: Int, dirn2: HVDirn, magnitude2: Int)(implicit gridSys: HGridSys): Unit =
    setDouble(HCen(cenR, cenC), vertNum, dirn1, magnitude1, dirn2, magnitude2)

  def setDouble(hCen: HCen, vertNum: Int, dirn1: HVDirn, magnitude1: Int, dirn2: HVDirn, magnitude2: Int)(implicit gridSys: HGridSys): Unit = {
    val corner = HCorner.double(dirn1, magnitude1, dirn2, magnitude2)
    val index = unsafeIndex(hCen, vertNum)
    unsafeArray(index) = corner.unsafeInt
  }

  /** Sets the same vertex offset for all three adjacent hexs. This leaves no gap for side terrain such as straits. */
  def setVertSingle(r: Int, c: Int, dirn: HVDirn, magnitude: Int)(implicit gridSys: HGridSys): Unit = setVertSingle(HVert(r, c), dirn, magnitude)

  /** Sets the same vertex offset for all three adjacent hexs. This leaves no gap for side terrain such as straits. */
  def setVertSingle(hVert: HVert, dirn: HVDirn, magnitude: Int)(implicit gridSys: HGridSys): Unit = {
    val mag2 = magnitude.abs
    val dirn2 = ife(magnitude < 0, dirn.opposite, dirn)
    val mag3 = mag2 match {
      case m if hVert.dirnToCen(dirn) & m > 6 => { deb("> 6"); m.min(6) }
      case m if !hVert.dirnToCen(dirn) & m > 3 => { deb("> 3"); m.min(3) }
      case m => m
    }
    hVert.adjHCenCorners.foreach{pair => setSingle(pair._1, pair._2, dirn2, mag3)}
  }
}

object HCornerLayer
{
  implicit class RArrHCornerLayerExtension(val thisArr: RArr[HCornerLayer])
  {
    /** Combines by appending the data grids to produce a single layer. */
    def combine: HCornerLayer =
    {
      val newLen = thisArr.sumBy(_.numCorners)
      val newArray = new Array[Int](newLen)
      var i = 0
      thisArr.foreach { ar => ar.unsafeArray.copyToArray(newArray, i); i += ar.numCorners }
      new HCornerLayer(newArray)
    }
  }
}