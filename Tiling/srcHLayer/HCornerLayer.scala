/* Copyright 2018-24 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package prid; package phex
import geom._

/** [[HGridSys]] data layer class that allows the hex tile vertices to be shifted by a small amount to create more pleasing terrain and to feature islands,
 *  straits and other tile side features. Every [[HCen]] hex tile in the [[HGridSys]] has 6 vertex entries. */
final class HCornerLayer(val unsafeArray: Array[Int])
{ /** The number of corners stored / specified in this [[HCornerLayer]] object. This will be 6 for every tile in the associated [[HGridSys]]. */
  def numCorners: Int = unsafeArray.length

  /** Number of tiles specified in this [[HCornerLayer]]. */
  def numTiles: Int = unsafeArray.length / 6

  /** Returns the [[HCorner]] encoded as an [[Int]]. */
  def indexUnsafe(hCen: HCen, vertNum: Int)(implicit gridSys: HGridSys): Int = gridSys.layerArrayIndex(hCen) * 6 + vertNum

  /** Returns the [[HCorner]] encoded as an [[Int]]. */
  def indexUnsafe(cenR: Int, cenC: Int, vertNum: Int)(implicit gridSys: HGridSys): Int = gridSys.layerArrayIndex(cenR, cenC) * 6 + vertNum

  /** Returns the specified [[HCorner]] object which specifies, 1 or 2 [[HvOffset]]s. */
  def corner(hCen: HCen, vertNum: Int)(implicit gridSys: HGridSys): HCorner = new HCorner(unsafeArray(indexUnsafe(hCen, vertNum)))

  /** Returns the specified [[HCorner]] object which specifies, 1 or 2 [[HvOffset]]s. */
  def corner(hCenR: Int, hCenC: Int, vertNum: Int)(implicit gridSys: HGridSys): HCorner =
    new HCorner(unsafeArray(gridSys.layerArrayIndex(hCenR, hCenC) * 6 + vertNum))

  /** Returns the first and possibly only single [[HvOffset]] for an [[HCorner]]. This is used for drawing [[HSep]] hex side line segments. */
  def cornerV1(hCen: HCen, vertNum: Int)(implicit gridSys: HGridSys): HvOffset = corner(hCen, vertNum).v1(hCen.verts(vertNum))

  /** Returns the last [[HvOffset]] for an [[HCorner]]. This is used for drawing [[HSep]] hex side line segments. */
  def cornerVLast(hCen: HCen, vertNum: Int)(implicit gridSys: HGridSys): HvOffset = corner(hCen, vertNum).vLast(hCen.verts(vertNum))

  def isSpecial(hCen: HCen, vertNum: Int)(implicit gridSys: HGridSys): Boolean = corner(hCen, vertNum).isSpecial

  /** Produces an [[HSep]]'s line segment specified in [[HvOffset]] coordinates. */
  def sideLineHVAndOffset(hCen: HCen, vertNum1: Int, vertNum2: Int)(implicit gridSys: HGridSys): LineSegHvOffset =
    LineSegHvOffset(cornerVLast(hCen, vertNum1), cornerV1(hCen, vertNum2))

  /** Not sure about the safety of this method. */
  def sideLine(hCen: HCen, vertNum1: Int, vertNum2: Int)(implicit proj: HSysProjection): LineSeg =
    sideLineHVAndOffset(hCen, vertNum1, vertNum2)(proj.parent).map(proj.transHVOffset)

  def tileCorners(hCen: HCen)(implicit gridSys: HGridSys): RArr[HCorner] = iUntilMap(6){ i => corner(hCen, i) }
  def tileCorners(cenR: Int, cenC: Int)(implicit gridSys: HGridSys): RArr[HCorner] = iUntilMap(6){ i => corner(cenR, cenC, i) }
  def tilePoly(hCen: HCen)(implicit gridSys: HGridSys): PolygonHvOffset = tileCorners(hCen).iFlatMapPolygon{ (i, corn) => corn.verts(hCen.verts(i)) }
  def tilePoly(cenR: Int, cenC: Int)(implicit gridSys: HGridSys): PolygonHvOffset = tilePoly(HCen(cenR, cenC))

  /** Sets a single [[HCorner]]. Sets one vertex offset for one adjacent hex. This could leave a gap for side terrain such as straits. */
  def setCorner(cenR: Int, cenC: Int, vertNum: Int, dirn: HVDirnOpt, magnitude: Int)(implicit grid: HGrid): Unit =
  { if(grid.hCenExists(cenR, cenC))
    { val corner = HCorner.single(dirn, magnitude)
      val index = indexUnsafe(cenR, cenC, vertNum)
      unsafeArray(index) = corner.unsafeInt
    }
  }

  /** Sets a single [[HCorner]] with 1 [[HVOffsetDelta]]. Sets one vertex offset for one adjacent hex. This could leave a gap for side terrain such as
   *  straits. */
  def setCorner(hCen: HCen, vertNum: Int, dirn: HVDirnOpt, magnitude: Int)(implicit grid: HGrid): Unit =
  { if(grid.hCenExists(hCen))
    { val corner = HCorner.single(dirn, magnitude)
      val index = indexUnsafe(hCen, vertNum)
      unsafeArray(index) = corner.unsafeInt
    }
  }

  /** Sets all 3 corners of a bend. The inside of the bend is at vertex <b>0</b> of the specified [[HCen]], Bend direction defined as [[HVDn]]. The other 2
   *  corners are offset in the opposite direction [[HVUp]]. */
  def setBend0(r: Int, c: Int, magIn: Int, magOut: Int)(implicit grid: HGrid): Unit =
  { setCornerIn(r, c, 0, magIn)
    setCorner(r + 2, c - 2, 2, HVUp, magOut)
    setCorner(r + 2, c + 2, 4, HVUp, magOut)
  }

  /** Sets all 3 corners of a bend. The inside of the bend is at vertex <b>1</b> of the specified [[HCen]]. Bend direction defined as [[HVDL]]. The other 2
   *  corners are offset in the opposite direction [[HVUR]]. */
  def setBend1(r: Int, c: Int, magIn: Int, magOut: Int)(implicit grid: HGrid): Unit =
  { setCornerIn(r, c, 1, magIn)
    setCorner(r + 2, c + 2, 3, HVUR, magOut)
    setCorner(r, c + 4, 5, HVUR, magOut)
  }

  /** Sets all 3 corners of a bend. The inside of the bend is at vertex <b>2</b> of the specified [[HCen]]. Bend direction defined as [[HVUL]]. The other 2
   *  corners are offset in the opposite direction [[HVDR]]. */
  def setBend2(r: Int, c: Int, magIn: Int, magOut: Int)(implicit grid: HGrid): Unit =
  { setCornerIn(r, c, 2, magIn)
    setCorner(r, c + 4, 4, HVDR, magOut)
    setCorner(r - 2, c + 2, 0, HVDR, magOut)
  }

  /** Sets all 3 corners of a bend. The inside of the bend is at vertex <b>3</b> of the specified [[HCen]]. Bend direction defined as [[HVUp]]. The other 2
   *  corners are offset in the opposite direction [[HVDn]]. */
  def setBend3(r: Int, c: Int, magIn: Int, magOut: Int)(implicit grid: HGrid): Unit =
  { setCornerIn(r, c, 3, magIn)
    setCorner(r - 2, c + 2, 5, HVDn, magOut)
    setCorner(r - 2, c - 2, 1, HVDn, magOut)
  }

  /** Sets all 3 corners of a bend. The inside of the bend is at vertex <b>4</b> of the specified [[HCen]]. Bend direction defined as [[HVUR]] The other 2
   *  corners are offset in the opposite direction [[HVDL]]. */
  def setBend4(r: Int, c: Int, magIn: Int, magOut: Int)(implicit grid: HGrid): Unit =
  { setCornerIn(r, c, 4, magIn)
    setCorner(r - 2, c - 2, 0, HVDL, magOut)
    setCorner(r, c - 4, 2, HVDL, magOut)
  }

  /** Sets all 3 corners of a bend. The inside of the bend is at vertex <b>5</b> of the specified [[HCen]]. Bend direction defined as [[HVDR]]. The  other 2
   *  corners are offset in the opposite direction [[HVUL]]. */
  def setBend5(r: Int, c: Int, magIn: Int, magOut: Int)(implicit grid: HGrid): Unit =
  { setCornerIn(r, c, 5, magIn)
    setCorner(r, c - 4, 1, HVUL, magOut)
    setCorner(r + 2, c - 2, 3, HVUL, magOut)
  }

  /** Sets the 2 outer corners of a bend The inside of the bend ia at vertex <b>0</b> of the specified [[HCen]]. Bend direction defined as [[HVDn]] although the
   *  inner corner is not offset. Sets the corners of the two outer corners in the opposite direction [[HVUp]]. */
  def setVert0Out(r: Int, c: Int, magnitude: Int)(implicit grid: HGrid): Unit =
  { setCorner(r + 2, c - 2, 2, HVUp, magnitude)
    setCorner(r + 2, c + 2, 4, HVUp, magnitude)
  }

  /** Sets the 2 outer corners of a bend The inside of the bend ia at vertex <b>1</b> of the specified [[HCen]]. Bend direction defined as [[HVDL]] although the
   *  inner corner is not offset. Sets the corners of the two outer corners in the opposite direction [[HVUR]]. */
  def setBend1Out(r: Int, c: Int, magnitude: Int)(implicit grid: HGrid): Unit =
  { setCorner(r + 2, c + 2, 3, HVUR, magnitude)
    setCorner(r, c + 4, 5, HVUR, magnitude)
  }

  /** Sets the 2 outer corners of a bend The inside of the bend ia at vertex <b>2</b> of the specified [[HCen]]. Bend direction defined as [[HVUL]] although the
   *  inner corner is not offset. Sets the corners of the two outer corners in the opposite direction [[HVDR]]. */
  def setBend2Out(r: Int, c: Int, magnitude: Int)(implicit grid: HGrid): Unit =
  { setCorner(r, c + 4, 4, HVDR, magnitude)
    setCorner(r - 2, c + 2, 0, HVDR, magnitude)
  }

  /** Sets the 2 outer corners of a bend The inside of the bend ia at vertex <b>3</b> of the specified [[HCen]]. Bend direction defined as [[HVUL]] although the
   *  inner corner is not offset. Sets the corners of the two outer corners in the opposite direction [[HVDR]]. */
  def setBend3Out(r: Int, c: Int, magnitude: Int)(implicit grid: HGrid): Unit =
  { setCorner(r - 2, c + 2, 5, HVDn, magnitude)
    setCorner(r - 2, c - 2, 1, HVDn, magnitude)
  }

  /** Sets the 2 outer corners of a bend The inside of the bend ia at vertex <b>4</b> of the specified [[HCen]]. Bend direction defined as [[HVUR]] although the
   *  inner corner is not offset. Sets the corners of the two outer corners in the opposite direction [[HVDL]]. */
  def setBend4Out(r: Int, c: Int, magnitude: Int)(implicit grid: HGrid): Unit =
  { setCorner(r - 2, c - 2, 0, HVDL, magnitude)
    setCorner(r, c - 4, 2, HVDL, magnitude)
  }

  /** Sets the 2 outer corners of a bend The inside of the bend ia at vertex <b>5</b> of the specified [[HCen]]. Bend direction defined as [[HVDR]] although the
   *  inner corner is not offset. Sets the corners of the two outer corners in the opposite direction [[HVUL]]. */
  def setBend5Out(r: Int, c: Int, magnitude: Int)(implicit grid: HGrid): Unit =
  { setCorner(r, c - 4, 1, HVUL, magnitude)
    setCorner(r + 2, c - 2, 3, HVUL, magnitude)
  }

  def setVertEqual(r: Int, c: Int, magnitude: Int)(implicit grid: HGrid): Unit =
  {
    if (HVert.rcISHigh(r, c))
    { grid.hCenExistsIfDo(r + 1, c + 2){ setCornerIn(r + 1, c + 2, 4, magnitude) }
      grid.hCenExistsIfDo(r - 1, c){ setCornerIn(r - 1, c, 0, magnitude) }
      grid.hCenExistsIfDo(r + 1, c - 2){ setCornerIn(r + 1, c - 2, 2, magnitude) }
    }
    else
    { grid.hCenExistsIfDo(r + 1, c) { setCornerIn(r + 1, c, 3, magnitude) }
      grid.hCenExistsIfDo(r - 1, c + 2) { setCornerIn(r - 1, c + 2, 5, magnitude) }
      grid.hCenExistsIfDo(r - 1, c - 2) { setCornerIn(r - 1, c -2, 1, magnitude) }
    }
  }

  def setVertEqual(hv: HVert, magnitude: Int)(implicit grid: HGrid): Unit = setVertEqual(HVert(hv.r, hv.c), magnitude)

  /** Set the 3 [[HCorner]]s of an [[HSep]] source or end point. */
  def setVertSource(r: Int, c: Int, dirn: HVDirn, magLeft: Int, magRight: Int)(implicit grid: HGrid): Unit = dirn match
  {
    case HVUp =>
    { setCornerPair(r - 1, c, 0, HVDL, magLeft, HVDR, magRight)
      setCorner(r + 1, c - 2, 2, HVDL, magLeft)
      setCorner(r + 1, c + 2, 4, HVDR, magRight)
    }

    case HVUR =>
    { setCornerPair(r - 1, c - 2, 1, HVUL, magLeft, HVDn, magRight)
      setCorner(r + 1, c, 3, HVUL, magLeft)
      setCorner(r - 1, c + 2, 5, HVDn, magRight)
    }

    case HVDR =>
    { setCornerPair(r + 1, c - 2, 2, HVUp, magLeft, HVDL, magRight)
      setCorner(r + 1, c + 2, 4, HVUp, magLeft)
      setCorner(r - 1, c, 0, HVDL, magRight)
    }

    case HVDn =>
    { setCornerPair(r + 1, c, 3, HVUR, magLeft, HVUL, magRight)
      setCorner(r - 1, c + 2, 5, HVUR, magLeft)
      setCorner(r - 1, c - 2, 1, HVUL, magRight)
    }

    case HVDL => {
      setCornerPair(r + 1, c + 2, 4, HVDR, magLeft, HVUp, magRight)
      setCorner(r - 1, c, 0, HVDR, magLeft)
      setCorner(r + 1, c - 2, 2, HVUp, magRight)
  }

    case HVUL =>
    { setCornerPair(r - 1, c + 2, 5, HVDn, magLeft, HVUR, magRight)
      setCorner(r - 1, c - 2, 1, HVDn, magLeft)
      setCorner(r + 1, c, 3, HVUR, magRight)
    }

    case _ => debexc("Not implemented")
  }

  /** Sets the end of an [[HSep]] terrain at vertex for all 3 tiles. For example the the mouth of Straits the given [[HCen]] is the sea tile, for a wall it
   *  would be the hex tile looking at the end of the wall. The vertex for this tile would be 0. */
  def setMouth0(r: Int, c: Int, magLeft: Int, magRight: Int)(implicit grid: HGrid): Unit =
  { setCornerPair(r, c, 0, HVDL, magLeft, HVDR, magRight)
    setCorner(r + 2, c - 2, 2, HVDL, magLeft)
    setCorner(r + 2, c + 2, 4, HVDR, magRight)
  }

  /** Sets the end of an [[HSep]] terrain at vertex for all 3 tiles. For example the the mouth of Straits the given [[HCen]] is the sea tile, for a  wall it
   *  would be the hex tile looking at the end of the wall. The vertex for this tile would be 1. */
  def setMouth1(r: Int, c: Int, magLeft: Int, magRight: Int)(implicit grid: HGrid): Unit =
  { setCornerPair(r, c, 1, HVUL, magLeft, HVDn, magRight)
    setCorner(r + 2, c + 2, 3, HVUL, magLeft)
    setCorner(r, c + 4, 5, HVDn, magRight)
  }

  /** Sets the end of an [[HSep]] terrain at vertex for all 3 tiles. For example the the mouth of Straits the given [[HCen]] is the sea tile, for a wall it
   *  would be the hex tile looking at the end of the wall. The vertex for this tile would be 2. */
  def setMouth2(r: Int, c: Int, magLeft: Int, magRight: Int)(implicit grid: HGrid): Unit =
  { setCornerPair(r, c, 2, HVUp, magLeft, HVDL, magRight)
    setCorner(r, c + 4, 4, HVUp, magLeft)
    setCorner(r - 2, c + 2, 0, HVDL, magRight)
  }

  /** Sets the end of an [[HSep]] terrain at vertex for all 3 tiles. For example the the mouth of Straits the given [[HCen]] is the sea tile, for a wall it
   *  would be the hex tile looking at the end of the wall. The vertex for this tile would be 3. */
  def setMouth3(r: Int, c: Int, magLeft: Int, magRight: Int)(implicit grid: HGrid): Unit =
  { setCornerPair(r, c, 3, HVUR, magLeft, HVUL, magRight)
    setCorner(r - 2, c + 2, 5, HVUR, magLeft)
    setCorner(r - 2, c - 2, 1, HVUL, magRight)
  }

  /** Sets the end of an [[HSep]] terrain at vertex for all 3 tiles. For example the the mouth of Straits the given [[HCen]] is the sea tile, for a  wall it
   *  would be the hex tile looking at the end of the wall. The vertex for this tile would be 4. */
  def setMouth4(r: Int, c: Int, magLeft: Int, magRight: Int)(implicit grid: HGrid): Unit =
  { setCornerPair(r, c, 4, HVDR, magLeft, HVUp, magRight)
    setCorner(r - 2, c - 2, 0, HVDR, magLeft)
    setCorner(r, c - 4, 2, HVUp, magRight)
  }

  /** Sets the end of an [[HSep]] terrain at vertex for all 3 tiles. For example the the mouth of Straits the given [[HCen]] is the sea tile, for a  wall it
   *  would be the hex tile looking at the end of the wall. The vertex for this tile would be 5. */
  def setMouth5(r: Int, c: Int, magLeft: Int, magRight: Int)(implicit grid: HGrid): Unit =
  { setCornerPair(r, c, 5, HVDn, magLeft, HVUR, magRight)
    setCorner(r, c - 4, 1, HVDn, magLeft)
    setCorner(r + 2, c - 2, 3, HVUR, magRight)
  }

  /** Sets the end of an [[HSep]] terrain from off the [[HGrid]] at what would have been vertex 3 for the other 2 tiles. For example the the mouth of Straits
   *  the given [[HCen]] is the sea tile, for a wall it would be the hex tile looking at the end of the wall. */
  def setMouth3OffGrid(r: Int, c: Int, magnitude: Int)(implicit grid: HGrid): Unit =
  { setCorner(r - 2, c + 2, 5, HVDR, magnitude)
    setCorner(r - 2, c - 2, 1, HVDL, magnitude)
  }

  /** Sets the end of an [[HSep]] terrain from off the [[HGrid]] at what would have been vertex 4 for the other 2 tiles. For example the the mouth of Straits
   *  the given [[HCen]] is the sea tile, for a wall it would be the hex tile looking at the end of the wall. */
  def setMouth4OffGrid(r: Int, c: Int, magnitude: Int)(implicit grid: HGrid): Unit =
  { setCorner(r - 2, c - 2, 0, HVDn, magnitude)
    setCorner(r, c - 4, 2, HVUL, magnitude)
  }

  /** Sets the end of an [[HSep]] terrain  from off the [[HGrid]] at what would have been vertex 5 for the other 2 tiles. For example the the mouth of Straits
   *  the given [[HCen]] is the sea tile, for a wall it would be the hex tile looking at the end of the wall. The vertex for this tile would be 5. */
  def setMouth5OffGrid(r: Int, c: Int, magnitude: Int)(implicit grid: HGrid): Unit =
  { setCorner(r, c - 4, 1, HVDL, magnitude)
    setCorner(r + 2, c - 2, 3, HVUp, magnitude)
  }

  /** Sets thr corner in at the specified vertex if the specified [[HCen]] exists. */
  def setCornerIn(cenR: Int, cenC: Int, vertNum: Int, magnitude: Int)(implicit grid: HGrid): Unit =
  { val dirn = HVDirn.inFromVertIndex(vertNum)
    if (grid.hCenExists(cenR, cenC))
    { val corner: HCorner = HCorner.sideSpecial(dirn, magnitude)
      val index: Int = indexUnsafe(cenR, cenC, vertNum)
      unsafeArray(index) = corner.unsafeInt
    }
  }

  def setNCornersIn(cenR: Int, cenC: Int, numIndents: Int, firstVertNum: Int, magnitude: Int)(implicit grid: HGrid): Unit =
    iUntilForeach(numIndents) { i => setCornerIn(cenR, cenC, (firstVertNum + i) %% 6, magnitude) }

  /** Sets a single [[HCorner]] corner with 2 [[HVOffsetDelta]]s. */
  def setCornerPair(cenR: Int, cenC: Int, vertNum: Int, dirn1: HVDirnOpt, magnitude1: Int, dirn2: HVDirnOpt, magnitude2: Int)(implicit grid: HGrid): Unit =
    setCornerPair(HCen(cenR, cenC), vertNum, dirn1, magnitude1, dirn2, magnitude2)

  /** Sets a single [[HCorner]] corner with 2 [[HVOffsetDelta]]s. */
  def setCornerPair(hCen: HCen, vertNum: Int, dirn1: HVDirnOpt, magnitude1: Int, dirn2: HVDirnOpt, magnitude2: Int)(implicit grid: HGrid): Unit =
    if(grid.hCenExists(hCen))
    { val corner = HCorner.double(dirn1, magnitude1, dirn2, magnitude2)
      val index = indexUnsafe(hCen, vertNum)
      unsafeArray(index) = corner.unsafeInt
    }

  /** Sets a single [[HCorner]] corner with 1 [[HVOffsetDelta]] for the tile 2 for the [[hSide]]. */
  def setSideCornerSpecial(cenR: Int, cenC: Int, vertNum: Int, dirn1: HVDirnOpt, magnitude1: Int)(implicit grid: HGrid): Unit =
    setSideSpecial(HCen(cenR, cenC), vertNum, dirn1, magnitude1)

  /** Sets a single [[HCorner]] corner with [[HVOffsetDelta]] for the tile 2 for the HSide. */
  def setSideSpecial(hCen: HCen, vertNum: Int, dirn1: HVDirnOpt, magnitude1: Int)(implicit grid: HGrid): Unit = if(grid.hCenExists(hCen))
    { val corner = HCorner.sideSpecial(dirn1, magnitude1)
      val index = indexUnsafe(hCen, vertNum)
      unsafeArray(index) = corner.unsafeInt
    }

  /** Sets the same vertex offset for all three adjacent hexs. This leaves no gap for side terrain such as straits. */
  def setVertSingle(r: Int, c: Int, dirn: HVDirnOpt, magnitude: Int)(implicit grid: HGrid): Unit = setVertSingle(HVert(r, c), dirn, magnitude)

  /** Sets the same vertex offset for all three adjacent hexs. This leaves no gap for side terrain such as straits. */
  def setVertSingle(hVert: HVert, dirn: HVDirnOpt, magnitude: Int)(implicit grid: HGrid): Unit =
  { val mag2 = magnitude.abs
    val dirn2 = ife(magnitude < 0, dirn.opposite, dirn)
    val mag3 = mag2 match {
      case m if  m > 7 => { deb("> 7"); m.min(7) }
      case m => m
    }
    hVert.adjHCenCorners.foreach{pair => setCorner(pair._1, pair._2, dirn2, mag3)}
  }

  /** Returns the [[PolygonHvOffset]] [[PolygonLike]] for the given [[HSep]]. */
  def sidePoly(hs: HSep)(implicit gridSys: HGridSys): PolygonHvOffset = hs.tileLtOpt match
  {
    case None =>
    { val (hcRt, vi) = hs.tileRtAndVert
      val p1 = cornerV1(hcRt, vi)
      val p2 = cornerV1(hcRt, (vi - 1) %% 6)
      val p3 = hs.vertLower.noOffset
      val p4 = hs.vertUpper.noOffset
      PolygonHvOffset(hcRt.vExact(vi), p1, p2, p3, p4)
    }

    case Some(hcLt) => hs.tileRtOpt match
    {
      case None =>
      { val (hcLt, vi) = hs.tileLtAndVert
        val p1 = hs.vertUpper.noOffset
        val p2 = hs.vertLower.noOffset
        val p3 = cornerV1(hcLt, (vi + 1) %% 6)
        val p4 = cornerV1(hcLt, vi)
        PolygonHvOffset(p1, p2, hcLt.vExact((vi + 1) %% 6), p3, p4)
      }

      case Some(_) =>
      { val (hcRt, vi) = hs.tileRtAndVert
        val (hcLt, lvi) = hs.tileLtAndVertFromRt(hcRt.r)
        val p1 = cornerV1(hcRt, vi)
        val vi2 = (vi - 1) %% 6
        val p2: HvOffset = cornerV1(hcRt, vi2)
        val vi3 = (lvi + 1) %% 6
        val p3: HvOffset = cornerV1(hcLt, vi3)
        val vi4 = lvi %% 6
        val p4: HvOffset = cornerV1(hcLt, vi4)
        val arr1: HvOffsetArr = ife(isSpecial(hcRt, vi) & isSpecial(hcLt, vi4), HvOffsetArr(hcRt.vExact(vi), p1, p2), HvOffsetArr(p1, p2))
        val arr2: HvOffsetArr = ife(isSpecial(hcRt, vi2) & isSpecial(hcLt, vi3), HvOffsetArr(hcRt.vExact(vi2), p3, p4), HvOffsetArr(p3, p4))
        (arr1 ++ arr2).toPolygon
      }
    }
  }

  /** Spawns a new [[HCornerLayer]] data layer for the child [[HGridSys]]. */
  def spawn(parentGridSys: HGridSys, childGridSys: HGridSys): HCornerLayer =
  { val array: Array[Int] = new Array[Int](childGridSys.numCorners)
    childGridSys.foreach { hc =>
      iUntilForeach(6) { i => array(childGridSys.cornerLayerArrayIndex(hc, i)) = unsafeArray(parentGridSys.cornerLayerArrayIndex(hc, i)) }
    }
    new HCornerLayer(array)
  }
}

object HCornerLayer
{
  def apply()(implicit gridSys: HGridSys): HCornerLayer = new HCornerLayer(new Array[Int](gridSys.numCorners))

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