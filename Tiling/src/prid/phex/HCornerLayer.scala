/* Copyright 2018-23 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package prid; package phex
import geom._

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

  /** Returns the first and possibly only single [[HVAndOffset]] for an [[HCorner]]. This is used for drawing [[HSide]] hex side line segments. */
  def cornerV1(hCen: HCen, vertNum: Int)(implicit gridSys: HGridSys): HVAndOffset = corner(hCen, vertNum).v1(hCen.verts(vertNum))

  def cornerForSide(hCen: HCen, vertNum: Int)(implicit gridSys: HGridSys): HVAndOffsetArr = corner(hCen, vertNum).sideVerts(hCen.verts(vertNum))
  def cornerForSideSpecial(hCen: HCen, vertNum: Int)(implicit gridSys: HGridSys): HVAndOffsetArr = corner(hCen, vertNum).sideVertsSpecial(hCen.verts(vertNum))

  /** Returns the last [[HVAndOffset]] for an [[HCorner]]. This is used for drawing [[HSide]] hex side line segments. */
  def cornerVLast(hCen: HCen, vertNum: Int)(implicit gridSys: HGridSys): HVAndOffset = corner(hCen, vertNum).vLast(hCen.verts(vertNum))

  /** Produces an [[HSide]]'s line segment specified in [[HVAndOffset]] coordinates. */
  def sideLine(hCen: HCen, vertNum1: Int, vertNum2: Int)(implicit gridSys: HGridSys): LineSegHVAndOffset =
    LineSegHVAndOffset(cornerVLast(hCen, vertNum1), cornerV1(hCen, vertNum2))

  def tileCorners(hCen: HCen)(implicit gridSys: HGridSys): RArr[HCorner] = iUntilMap(6){ i => corner(hCen, i) }
  def tileCorners(cenR: Int, cenC: Int)(implicit gridSys: HGridSys): RArr[HCorner] = iUntilMap(6){ i => corner(cenR, cenC, i) }
  def tilePoly(hCen: HCen)(implicit gridSys: HGridSys): PolygonHVAndOffset = tileCorners(hCen).iFlatMapPolygon{ (i, corn) => corn.verts(hCen.verts(i)) }
  def tilePoly(cenR: Int, cenC: Int)(implicit gridSys: HGridSys): PolygonHVAndOffset = tilePoly(HCen(cenR, cenC))

  /** Sets a single [[HCorner]]. Sets one vertex offset for one adjacent hex. This could leave a gap for side terrain such as straits. */
  def setCorner(cenR: Int, cenC: Int, vertNum: Int, dirn: HVDirnOpt, magnitude: Int = 3)(implicit gridSys: HGridSys): Unit =
  { val corner = HCorner.single(dirn, magnitude)
    val index = unsafeIndex(cenR, cenC, vertNum)
    unsafeArray(index) = corner.unsafeInt
  }

  /** Sets a single [[HCorner]] with 1 [[HVOffset]]. Sets one vertex offset for one adjacent hex. This could leave a gap for side terrain such as straits. */
  def setCorner(hCen: HCen, vertNum: Int, dirn: HVDirnOpt, magnitude: Int)(implicit gridSys: HGridSys): Unit =
  { val corner = HCorner.single(dirn, magnitude)
    val index = unsafeIndex(hCen, vertNum)
    unsafeArray(index) = corner.unsafeInt
  }

  /** Sets the vertex for 3 tiles. The specified [[HCen]] is the upper left of the three [[HCen]]s sharing the vertex. Sets the given tile's vert 2
   *  [[HVUp]]. Sets the tile to the right HCen(r,  c + 4) vert 4 [[HVUp]] and sets the tile down and to the right HCen(r - 2, C + 2) vert 0
   *  [[HVDn]]. */
  def setVert2Up1Dn(r: Int, c: Int, magnitude: Int = 3)(implicit gridSys: HGridSys): Unit =
  { setCorner(r, c, 2, HVUp, magnitude)
    setCorner(r, c + 4, 4, HVUp, magnitude)
    setCorner(r - 2, c + 2, 0, HVDn, magnitude)
  }

  /** Sets the vertex for 3 tiles. The specified [[HCen]] is the bottom left of the three [[HCen]]s sharing the vertex. Sets the given tile's vert 1
   *  [[HVDn]]. Sets the tile to the right HCen(r,  c + 4) vert 5 [[HVDn]] and sets the tile down and to the right HCen(r - 2, C + 2) vert 0
   *  [[HVDn]]. */
  def setVert2Dn1Up(r: Int, c: Int, magnitude: Int = 3)(implicit gridSys: HGridSys): Unit = {
    setCorner(r, c, 1, HVDn, magnitude)
    setCorner(r, c + 4, 5, HVDn, magnitude)
    setCorner(r + 2, c + 2, 3, HVUp, magnitude)
  }

  /** Sets the vertex for 3 tiles. The specified [[HCen]] is the bottom of the three [[HCen]]s sharing the vertex. Sets the given tile's vert 0
   * [[HVDR]]. Sets the tile to the upper right HCen(r + 2,  c + 2) vert 4 [[HVDR]] and sets the tile to the upper left HCen(r + 2, C - 2) vert 2
   * [[HVUR]]. */
  def setVert2DR1UL(r: Int, c: Int, magnitude: Int = 3)(implicit gridSys: HGridSys): Unit = {
    setCorner(r, c, 0, HVDR, magnitude)
    setCorner(r + 2, c + 2, 4, HVDR, magnitude)
    setCorner(r + 2, c - 2, 2, HVUL, magnitude)
  }

  /** Sets the vertex for 3 tiles. The specified [[HCen]] is the top left of the three [[HCen]]s sharing the vertex. Sets the given tile's vert 2
   * [[HVDL]]. Sets the tile to the bottom HCen(r - 2,  c + 2) vert 0 [[HVDL]] and sets the tile to the upper right HCen(r, C + 4) vert 4 [[HVUR]]. */
  def setVert2DL1UR(r: Int, c: Int, magnitude: Int = 3)(implicit gridSys: HGridSys): Unit = {
    setCorner(r, c, 2, HVDL, magnitude)
    setCorner(r - 2, c + 2, 0, HVDL, magnitude)
    setCorner(r, c + 4, 4, HVUR, magnitude)
  }


  /** Sets the vertex for 3 tiles. The specified [[HCen]] is the bottom left of the three [[HCen]]s sharing the vertex. Sets the given tile's vert 1
   * [[HVUL]]. Sets the tile to the upper right HCen(r + 2,  c + 2) vert 3 [[HVUL]] and sets the tile to the right HCen(r, C + 4) vert 5 [[HVDR]]. */
  def setVert2UL1DR(r: Int, c: Int, magnitude: Int = 3)(implicit gridSys: HGridSys): Unit =
  { setCorner(r, c, 1, HVUL, magnitude)
    setCorner(r + 2, c + 2, 3, HVUL, magnitude)
    setCorner(r, c + 4, 5, HVDR, magnitude)
  }

  /** Sets the vertex for 3 tiles. The specified [[HCen]] is the top of the three [[HCen]]s sharing the vertex. Sets the given tile's vert 3 [[HVUR]].
   *  Sets the tile to the lower right HCen(r - 2,  c + 2) vert 5 [[HVUR]] and sets the tile to the lower left HCen(r - 2, C -2) vert 1 [[HVDL]]. */
  def setVert2UR1DL(r: Int, c: Int, magnitude: Int = 3)(implicit gridSys: HGridSys): Unit =
  { setCorner(r, c, 3, HVUR, magnitude)
    setCorner(r - 2, c + 2, 5, HVUR, magnitude)
    setCorner(r -2, c - 2, 1, HVDL, magnitude)
  }

  /** Sets the corner inward [[HVDn]] on the 0 vertex of the given [[HCen]]. Sets the corners of the other two [[HCen]]s sharing the [[HVert]] vertex
   * in the opposite direction [[HVUp]]. */
  def setVert0In(r: Int, c: Int, magnitude: Int = 3)(implicit gridSys: HGridSys): Unit =
  { setCornerIn(r, c, 0, magnitude)
    setCorner(r + 2, c - 2, 2, HVUp, magnitude)
    setCorner(r + 2, c + 2, 4, HVUp, magnitude)
  }

  /** Sets the corner inward [[HVDL]] on the 1 vertex of the given [[HCen]]. Sets the corners of the other two [[HCen]]s sharing the [[HVert]] vertex
   * in the opposite direction [[HVUR]]. */
  def setVert1In(r: Int, c: Int, magnitude: Int = 3)(implicit gridSys: HGridSys): Unit =
  { setCornerIn(r, c, 1, magnitude)
    setCorner(r + 2, c + 2, 3, HVUR, magnitude)
    setCorner(r, c + 4, 5, HVUR, magnitude)
  }

  /** Sets the corner inward [[HVUL]] on the 2 vertex of the given [[HCen]]. Sets the corners of the other two [[HCen]]s sharing the [[HVert]] vertex
   * in the opposite direction [[HVDR]]. */
  def setVert2In(r: Int, c: Int, magnitude: Int = 3)(implicit gridSys: HGridSys): Unit =
  { setCornerIn(r, c, 2, magnitude)
    setCorner(r, c + 4, 4, HVDR, magnitude)
    setCorner(r - 2, c + 2, 0, HVDR, magnitude)
  }

  /** Sets the corner inward [[HVUp]] on the 3 vertex of the given [[HCen]]. Sets the corners of the other two [[HCen]]s sharing the [[HVert]] vertex
   * in the opposite direction [[HVDn]]. */
  def setVert3In(r: Int, c: Int, magnitude: Int = 3)(implicit gridSys: HGridSys): Unit =
  { setCornerIn(r, c, 3, magnitude)
    setCorner(r - 2, c + 2, 5, HVDn, magnitude)
    setCorner(r - 2, c - 2, 1, HVDn, magnitude)
  }

  /** Sets the corner in [[HVUR]] on the 4 vertex of the given [[HCen]]. Sets the corners of the other two [[HCen]]s sharing the [[HVert]] vertex
   * in the opposite direction [[HVDL]]. */
  def setVert4In(r: Int, c: Int, magnitude: Int = 3)(implicit gridSys: HGridSys): Unit = {
    setCornerIn(r, c, 4, magnitude)
    setCorner(r - 2, c - 2, 0, HVDL, magnitude)
    setCorner(r, c - 4, 2, HVDL, magnitude)
  }

  /** Sets the corner in [[HVDR]] on the 5 vertex of the given [[HCen]]. Sets the corners of the other two [[HCen]]s sharing the [[HVert]] vertex
   * in the opposite direction [[HVUL]]. */
  def setVert5In(r: Int, c: Int, magnitude: Int = 3)(implicit gridSys: HGridSys): Unit =
  { setCornerIn(r, c, 5, magnitude)
    setCorner(r, c - 4, 1, HVUL, magnitude)
    setCorner(r + 2, c - 2, 3, HVUL, magnitude)
  }

  /** Sets the end of a side terrain at vertex for all 3 tiles. For example the the mouth of Straits the given [[HCen]] is the sea tile, for a wall
   * it would be the hex tile looking at the end of the wall. The vertex for this tile would be 0. */
  def setMouth0(r: Int, c: Int, magnitude: Int = 3)(implicit gridSys: HGridSys): Unit =
  { setCorner2(r, c, 0, HVUL, HVUR, magnitude, magnitude)
    setCorner(r + 2, c - 2, 2, HVUL, magnitude)
    setCorner(r + 2, c + 2, 4, HVUR, magnitude)
  }

  /** Sets the end of a side terrain at vertex for all 3 tiles. For example the the mouth of Straits the given [[HCen]] is the sea tile, for a wall
   * it would be the hex tile looking at the end of the wall. The vertex for this tile would be 1. */
  def setMouth1(r: Int, c: Int, magnitude: Int = 3)(implicit gridSys: HGridSys): Unit =
  { setCorner2(r, c, 1, HVUp, HVDR, magnitude, magnitude)
    setCorner(r + 2, c + 2, 3, HVUp, magnitude)
    setCorner(r, c + 4, 5, HVDR, magnitude)
  }

  /** Sets the end of a side terrain at vertex for all 3 tiles. For example the the mouth of Straits the given [[HCen]] is the sea tile, for a wall
   * it would be the hex tile looking at the end of the wall. The vertex for this tile would be 2. */
  def setMouth2(r: Int, c: Int, magnitude: Int = 3)(implicit gridSys: HGridSys): Unit =
  { setCorner2(r, c, 2, HVUR, HVDn, magnitude, magnitude)
    setCorner(r, c + 4, 4, HVUR, magnitude)
    setCorner(r - 2, c + 2, 0, HVDn, magnitude)
  }

  /** Sets the end of a side terrain at vertex for all 3 tiles. For example the the mouth of Straits the given [[HCen]] is the sea tile, for a wall
   * it would be the hex tile looking at the end of the wall. The vertex for this tile would be 3. */
  def setMouth3(r: Int, c: Int, magnitude: Int = 3)(implicit gridSys: HGridSys): Unit =
  { setCorner2(r, c, 3, HVDR, HVDL, magnitude, magnitude)
    setCorner(r - 2, c + 2, 5, HVDR, magnitude)
    setCorner(r - 2, c - 2, 1, HVDL, magnitude)
  }

  /** Sets the end of a side terrain at vertex for all 3 tiles. For example the the mouth of Straits the given [[HCen]] is the sea tile, for a wall
   * it would be the hex tile looking at the end of the wall. The vertex for this tile would be 4. */
  def setMouth4(r: Int, c: Int, magnitude: Int = 3)(implicit gridSys: HGridSys): Unit =
  { setCorner2(r, c, 4, HVDn, HVUL, magnitude, magnitude)
    setCorner(r - 2, c - 2, 0, HVDn, magnitude)
    setCorner(r, c - 4, 2, HVUL, magnitude)
  }

  /** Sets the end of a side terrain at vertex for all 3 tiles. For example the the mouth of Straits the given [[HCen]] is the sea tile, for a wall
   * it would be the hex tile looking at the end of the wall. The vertex for this tile would be 5. */
  def setMouth5(r: Int, c: Int, magnitude: Int = 3)(implicit gridSys: HGridSys): Unit =
  { setCorner2(r, c, 5, HVDL, HVUp, magnitude, magnitude)
    setCorner(r, c - 4, 1, HVDL, magnitude)
    setCorner(r + 2, c - 2, 3, HVUp, magnitude)
  }

  /** Sets the end of a side terrain from off the [[HGrid]] at what would have been vertex 3 for the other 2 tiles. For example the the mouth of
   *  Straits the given [[HCen]] is the sea tile, for a wall it would be the hex tile looking at the end of the wall. */
  def setMouth3OffGrid(r: Int, c: Int, magnitude: Int = 3)(implicit gridSys: HGridSys): Unit =
  { setCorner(r - 2, c + 2, 5, HVDR, magnitude)
    setCorner(r - 2, c - 2, 1, HVDL, magnitude)
  }

  /** Sets the end of a side terrain from off the [[HGrid]] at what would have been vertex 4 for the other 2 tiles. For example the the mouth of
   *  Straits the given [[HCen]] is the sea tile, for a wall it would be the hex tile looking at the end of the wall. */
  def setMouth4OffGrid(r: Int, c: Int, magnitude: Int = 3)(implicit gridSys: HGridSys): Unit =
  { setCorner(r - 2, c - 2, 0, HVDn, magnitude)
    setCorner(r, c - 4, 2, HVUL, magnitude)
  }

  /** Sets the end of a side terrain  from off the [[HGrid]] at what would have been vertex 5 for the other 2 tiles. For example the the mouth of
   * Straits the given [[HCen]] is the sea tile, for a wall it would be the hex tile looking at the end of the wall. The vertex for this tile would be 5. */
  def setMouth5OffGrid(r: Int, c: Int, magnitude: Int = 3)(implicit gridSys: HGridSys): Unit =
  { setCorner(r, c - 4, 1, HVDL, magnitude)
    setCorner(r + 2, c - 2, 3, HVUp, magnitude)
  }

  /** Sets the end of a side terrain at vertex for one tile. For example the the mouth of Straits the given [[HCen]] is the sea tile, for a wall
   * it would be the hex tile looking at the end of the wall. The vertex for this tile would be 3. */
  def setMouth3Corner(r: Int, c: Int, magnitude: Int = 3)(implicit gridSys: HGridSys): Unit = setCorner2(r, c, 3, HVDR, HVDL, magnitude, magnitude)

  /** Sets the end of a side terrain at vertex for all 3 tiles. For example the the mouth of Straits the given [[HCen]] is the sea tile, for a wall
   * it would be the hex tile looking at the end of the wall. The vertex for this tile would be 5. */
  def setMouth5Corner(r: Int, c: Int, magnitude: Int = 3)(implicit gridSys: HGridSys): Unit = setCorner2(r, c, 5, HVDL, HVUp, magnitude, magnitude)

  /** Sets the corner in towards the [[HCen]] with a single [[HVOffset]]. */
  def setCornerIn(cenR: Int, cenC: Int, vertNum: Int, magnitude: Int = 3)(implicit gridSys: HGridSys): Unit =
  { val i = vertNum %% 6
    val dirn = i match
    { case 0 => HVDn
      case 1 => HVDL
      case 2 => HVUL
      case 3 => HVUp
      case 4 => HVUR
      case 5 => HVDR
    }
    setCorner(cenR, cenC, vertNum, dirn, magnitude)
  }

  /** Sets a single [[HCorner]] corner with 2 [[HVOffset]]s. */
  def setCorner2(cenR: Int, cenC: Int, vertNum: Int, dirn1: HVDirnOpt, dirn2: HVDirnOpt, magnitude1: Int = 3, magnitude2: Int = 3)(
    implicit gridSys: HGridSys): Unit = setCorner2(HCen(cenR, cenC), vertNum, dirn1, magnitude1, dirn2, magnitude2)

  /** Sets a single [[HCorner]] corner with 2 [[HVOffset]]s. */
  def setCorner2(hCen: HCen, vertNum: Int, dirn1: HVDirnOpt, magnitude1: Int, dirn2: HVDirnOpt, magnitude2: Int)(implicit gridSys: HGridSys): Unit =
  { val corner = HCorner.double(dirn1, magnitude1, dirn2, magnitude2)
    val index = unsafeIndex(hCen, vertNum)
    unsafeArray(index) = corner.unsafeInt
  }

  /** Sets a single [[HCorner]] corner with 1 [[HVOffset]] for the tile 2 for the [[hSide]]. */
  def setSideCorner2(cenR: Int, cenC: Int, vertNum: Int, dirn1: HVDirnOpt, dirn2: HVDirnOpt, magnitude1: Int = 3, magnitude2: Int = 3)(
    implicit gridSys: HGridSys): Unit = setSideCorner2(HCen(cenR, cenC), vertNum, dirn1, magnitude1, dirn2, magnitude2)

  /** Sets a single [[HCorner]] corner with [[HVOffset]] for the tile 2 for the HSide. */
  def setSideCorner2(hCen: HCen, vertNum: Int, dirn1: HVDirnOpt, magnitude1: Int, dirn2: HVDirnOpt, magnitude2: Int)(implicit gridSys: HGridSys): Unit =
  { val corner = HCorner.sideDouble(dirn1, magnitude1, dirn2, magnitude2)
    val index = unsafeIndex(hCen, vertNum)
    unsafeArray(index) = corner.unsafeInt
  }


  /** Sets the same vertex offset for all three adjacent hexs. This leaves no gap for side terrain such as straits. */
  def setVertSingle(r: Int, c: Int, dirn: HVDirnOpt, magnitude: Int)(implicit gridSys: HGridSys): Unit = setVertSingle(HVert(r, c), dirn, magnitude)

  /** Sets the same vertex offset for all three adjacent hexs. This leaves no gap for side terrain such as straits. */
  def setVertSingle(hVert: HVert, dirn: HVDirnOpt, magnitude: Int)(implicit gridSys: HGridSys): Unit =
  { val mag2 = magnitude.abs
    val dirn2 = ife(magnitude < 0, dirn.opposite, dirn)
    val mag3 = mag2 match {
      case m if /*hVert.dirnToCen(dirn) & */ m > 6 => { deb("> 7"); m.min(7) }
      //case m if !hVert.dirnToCen(dirn) & m > 3 => { deb("> 3"); m.min(3) }
      case m => m
    }
    hVert.adjHCenCorners.foreach{pair => setCorner(pair._1, pair._2, dirn2, mag3)}
  }

  /** Incomplete needs design adjustment. */
  def setVertAllIn(hVertR: Int, hVertC: Int, magnitude: Int = 3)(implicit gridSys: HGridSys): Unit ={
    if (HVert(hVertR, hVertC).hexIsUp)
      { setCornerIn(hVertR - 1, hVertC - 2, 1, magnitude)
        setCornerIn(hVertR + 1, hVertC + 2, 3, magnitude)
        setCornerIn(hVertR - 1, hVertC - 2, 1, magnitude)
      }
  }

  /** Returns the [[PolygonHVAndOffset]] [[PolygonLike]] for the given [[HSide]]. */
  def sideVerts(hs: HSide)(implicit gridSys: HGridSys): PolygonHVAndOffset = hs.tile1Opt match
  {
    case None =>
    { val (h2, vi) = hs.tile2AndVert
      val p1 = cornerV1(h2, vi)
      val p2 = cornerV1(h2, (vi - 1) %% 6)
      val p3 = hs.vert2.noOffset
      val p4 = hs.vert1.noOffset
      PolygonHVAndOffset(p1, p2, p3, p4)
    }

    case Some(h1) if hs.isVertical =>
    { val (h2, vi) = hs.tile2AndVert
      val ps1 = cornerForSideSpecial(h2, vi)
      val ps2 = cornerForSideSpecial(h2, (vi - 1) %% 6)
      val ps3 = cornerForSideSpecial(h1, (vi - 3) %% 6)
      val ps4 = cornerForSideSpecial(h1, (vi + 2) %% 6)
      val ps = ps1 ++ ps2 ++ ps3 ++ ps4
      ps.toPolygon
    }

    case Some(h1) =>
    { val (h2, vi) = hs.tile2AndVert
      val p1 = cornerV1(h2, vi)
      val p2 = cornerV1(h2, (vi - 1) %% 6)
      val p3 = cornerV1(h1, (vi - 3) %% 6)
      val p4 = cornerV1(h1, (vi + 2) %% 6)
      PolygonHVAndOffset(p1, p2, p3, p4)
    }
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