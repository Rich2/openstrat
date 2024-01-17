/* Copyright 2018-23 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package prid; package phex
import geom._

/** [[HGridSys]] data layer class that allows the hex tile vertices to be shifted by a small amount to create more pleasing terrain and to feature
 *  islands, straits and other tile side features. Every [[HCen]] hex tile in the [[HGridSys]] has 6 vertex entries. */
final class HCornerLayer(val unsafeArray: Array[Int])
{ /** The number of corners stored / specified in this [[HCornerLayer]] object. This will be 6 for every tile in the associated [[HGridSys]]. */
  def numCorners: Int = unsafeArray.length

  def numTiles: Int = unsafeArray.length / 6
  def unsafeIndex(hCen: HCen, vertNum: Int)(implicit gridSys: HGridSys): Int = gridSys.layerArrayIndex(hCen) * 6 + vertNum
  def unsafeIndex(cenR: Int, cenC: Int, vertNum: Int)(implicit gridSys: HGridSys): Int = gridSys.layerArrayIndex(cenR, cenC) * 6 + vertNum

  /** Returns the specified [[HCorner]] object which specifies, 1 or 2 [[HVOffset]]s. */
  def corner(hCen: HCen, vertNum: Int)(implicit gridSys: HGridSys): HCorner = new HCorner(unsafeArray(unsafeIndex(hCen, vertNum)))

  /** Returns the specified [[HCorner]] object which specifies, 1 or 2 [[HVOffset]]s. */
  def corner(hCenR: Int, hCenC: Int, vertNum: Int)(implicit gridSys: HGridSys): HCorner =
    new HCorner(unsafeArray(gridSys.layerArrayIndex(hCenR, hCenC) * 6 + vertNum))

  /** Returns the first and possibly only single [[HVOffset]] for an [[HCorner]]. This is used for drawing [[HSep]] hex side line segments. */
  def cornerV1(hCen: HCen, vertNum: Int)(implicit gridSys: HGridSys): HVOffset = corner(hCen, vertNum).v1(hCen.verts(vertNum))

  /** Returns the last [[HVOffset]] for an [[HCorner]]. This is used for drawing [[HSep]] hex side line segments. */
  def cornerVLast(hCen: HCen, vertNum: Int)(implicit gridSys: HGridSys): HVOffset = corner(hCen, vertNum).vLast(hCen.verts(vertNum))

  def isSpecial(hCen: HCen, vertNum: Int)(implicit gridSys: HGridSys): Boolean = corner(hCen, vertNum).isSpecial

  /** Produces an [[HSep]]'s line segment specified in [[HVOffset]] coordinates. */
  def sideLineHVAndOffset(hCen: HCen, vertNum1: Int, vertNum2: Int)(implicit gridSys: HGridSys): LineSegHVAndOffset =
    LineSegHVAndOffset(cornerVLast(hCen, vertNum1), cornerV1(hCen, vertNum2))

  /** Not sure about the safety of this method. */
  def sideLine(hCen: HCen, vertNum1: Int, vertNum2: Int)(implicit proj: HSysProjection): LineSeg =
    sideLineHVAndOffset(hCen, vertNum1, vertNum2)(proj.parent).map(proj.transHVOffset)

  def tileCorners(hCen: HCen)(implicit gridSys: HGridSys): RArr[HCorner] = iUntilMap(6){ i => corner(hCen, i) }
  def tileCorners(cenR: Int, cenC: Int)(implicit gridSys: HGridSys): RArr[HCorner] = iUntilMap(6){ i => corner(cenR, cenC, i) }
  def tilePoly(hCen: HCen)(implicit gridSys: HGridSys): PolygonHVOffset = tileCorners(hCen).iFlatMapPolygon{ (i, corn) => corn.verts(hCen.verts(i)) }
  def tilePoly(cenR: Int, cenC: Int)(implicit gridSys: HGridSys): PolygonHVOffset = tilePoly(HCen(cenR, cenC))

  /** Sets a single [[HCorner]]. Sets one vertex offset for one adjacent hex. This could leave a gap for side terrain such as straits. */
  def setCorner(cenR: Int, cenC: Int, vertNum: Int, dirn: HVDirnOpt, magnitude: Int = 3)(implicit grid: HGrid): Unit =
  { if(grid.hCenExists(cenR, cenC))
    { val corner = HCorner.single(dirn, magnitude)
      val index = unsafeIndex(cenR, cenC, vertNum)
      unsafeArray(index) = corner.unsafeInt
    }
  }

  /** Sets a single [[HCorner]] with 1 [[HVOffsetDelta]]. Sets one vertex offset for one adjacent hex. This could leave a gap for side terrain such as straits. */
  def setCorner(hCen: HCen, vertNum: Int, dirn: HVDirnOpt, magnitude: Int)(implicit grid: HGrid): Unit =
  { if(grid.hCenExists(hCen))
    { val corner = HCorner.single(dirn, magnitude)
      val index = unsafeIndex(hCen, vertNum)
      unsafeArray(index) = corner.unsafeInt
    }
  }

  /** Sets all 3 corners of a bend. The inside of the bend is at vertex <b>0</b> of the specified [[HCen]], Bend direction defined as [[HVDn]]. The
   *  other 2 corners are offset in the opposite direction [[HVUp]]. */
  def setBend0All(r: Int, c: Int, magIn: Int, magOut: Int)(implicit grid: HGrid): Unit =
  { setCornerIn(r, c, 0, magIn)
    setCorner(r + 2, c - 2, 2, HVUp, magOut)
    setCorner(r + 2, c + 2, 4, HVUp, magOut)
  }

  /** Sets all 3 corners of a bend. The inside of the bend is at vertex <b>1</b> of the specified [[HCen]]. Bend direction defined as [[HVDL]]. The
   *  other 2 corners are offset in the opposite direction [[HVUR]]. */
  def setBend1All(r: Int, c: Int, magIn: Int, magOut: Int)(implicit grid: HGrid): Unit =
  { setCornerIn(r, c, 1, magIn)
    setCorner(r + 2, c + 2, 3, HVUR, magOut)
    setCorner(r, c + 4, 5, HVUR, magOut)
  }

  /** Sets all 3 corners of a bend. The inside of the bend is at vertex <b>2</b> of the specified [[HCen]]. Bend direction defined as [[HVUL]]. The
   *  other 2 corners are offset in the opposite direction [[HVDR]]. */
  def setBend2All(r: Int, c: Int, magIn: Int, magOut: Int)(implicit grid: HGrid): Unit =
  { setCornerIn(r, c, 2, magIn)
    setCorner(r, c + 4, 4, HVDR, magOut)
    setCorner(r - 2, c + 2, 0, HVDR, magOut)
  }

  /** Sets all 3 corners of a bend. The inside of the bend is at vertex <b>3</b> of the specified [[HCen]]. Bend direction defined as [[HVUp]]. The
   *  other 2 corners are offset in the opposite direction [[HVDn]]. */
  def setBend3All(r: Int, c: Int, magIn: Int, magOut: Int)(implicit grid: HGrid): Unit =
  { setCornerIn(r, c, 3, magIn)
    setCorner(r - 2, c + 2, 5, HVDn, magOut)
    setCorner(r - 2, c - 2, 1, HVDn, magOut)
  }

  /** Sets all 3 corners of a bend. The inside of the bend is at vertex <b>4</b> of the specified [[HCen]]. Bend direction defined as [[HVUR]] The
   *  other 2 corners are offset in the opposite direction [[HVDL]]. */
  def setBend4All(r: Int, c: Int, magIn: Int, magOut: Int)(implicit grid: HGrid): Unit =
  { setCornerIn(r, c, 4, magIn)
    setCorner(r - 2, c - 2, 0, HVDL, magOut)
    setCorner(r, c - 4, 2, HVDL, magOut)
  }

  /** Sets all 3 corners of a bend. The inside of the bend is at vertex <b>5</b> of the specified [[HCen]]. Bend direction defined as [[HVDR]]. The
   *  other 2 corners are offset in the opposite direction [[HVUL]]. */
  def setBend5All(r: Int, c: Int, magIn: Int, magOut: Int)(implicit grid: HGrid): Unit =
  { setCornerIn(r, c, 5, magIn)
    setCorner(r, c - 4, 1, HVUL, magOut)
    setCorner(r + 2, c - 2, 3, HVUL, magOut)
  }

  /** Sets the 2 outer corners of a bend The inside of the bend ia at vertex <b>0</b> of the specified [[HCen]]. Bend direction defined as [[HVDn]]
   * although the inner corner is not offset. Sets the corners of the two outer corners in the opposite direction [[HVUp]]. */
  def setVert0Out(r: Int, c: Int, magnitude: Int = 6)(implicit grid: HGrid): Unit =
  { setCorner(r + 2, c - 2, 2, HVUp, magnitude)
    setCorner(r + 2, c + 2, 4, HVUp, magnitude)
  }

  /** Sets the 2 outer corners of a bend The inside of the bend ia at vertex <b>1</b> of the specified [[HCen]]. Bend direction defined as [[HVDL]]
   *  although the inner corner is not offset. Sets the corners of the two outer corners in the opposite direction [[HVUR]]. */
  def setBend1Out(r: Int, c: Int, magnitude: Int = 6)(implicit grid: HGrid): Unit =
  { setCorner(r + 2, c + 2, 3, HVUR, magnitude)
    setCorner(r, c + 4, 5, HVUR, magnitude)
  }

  /** Sets the 2 outer corners of a bend The inside of the bend ia at vertex <b>2</b> of the specified [[HCen]]. Bend direction defined as [[HVUL]]
   * although the inner corner is not offset. Sets the corners of the two outer corners in the opposite direction [[HVDR]]. */
  def setBend2Out(r: Int, c: Int, magnitude: Int = 6)(implicit grid: HGrid): Unit =
  { setCorner(r, c + 4, 4, HVDR, magnitude)
    setCorner(r - 2, c + 2, 0, HVDR, magnitude)
  }

  /** Sets the 2 outer corners of a bend The inside of the bend ia at vertex <b>3</b> of the specified [[HCen]]. Bend direction defined as [[HVUL]]
   *  although the inner corner is not offset. Sets the corners of the two outer corners in the opposite direction [[HVDR]]. */
  def setBend3Out(r: Int, c: Int, magnitude: Int = 6)(implicit grid: HGrid): Unit =
  { setCorner(r - 2, c + 2, 5, HVDn, magnitude)
    setCorner(r - 2, c - 2, 1, HVDn, magnitude)
  }

  /** Sets the 2 outer corners of a bend The inside of the bend ia at vertex <b>4</b> of the specified [[HCen]]. Bend direction defined as [[HVUR]]
   * although the inner corner is not offset. Sets the corners of the two outer corners in the opposite direction [[HVDL]]. */
  def setBend4Out(r: Int, c: Int, magnitude: Int = 6)(implicit grid: HGrid): Unit =
  { setCorner(r - 2, c - 2, 0, HVDL, magnitude)
    setCorner(r, c - 4, 2, HVDL, magnitude)
  }

  /** Sets the 2 outer corners of a bend The inside of the bend ia at vertex <b>5</b> of the specified [[HCen]]. Bend direction defined as [[HVDR]]
   *  although the inner corner is not offset. Sets the corners of the two outer corners in the opposite direction [[HVUL]]. */
  def setBend5Out(r: Int, c: Int, magnitude: Int = 6)(implicit grid: HGrid): Unit =
  { setCorner(r, c - 4, 1, HVUL, magnitude)
    setCorner(r + 2, c - 2, 3, HVUL, magnitude)
  }

  def setVertEqual(r: Int, c: Int, magnitude: Int = 3)(implicit grid: HGrid): Unit =
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

  /** Sets the end of a side terrain at vertex for all 3 tiles. For example the the mouth of Straits the given [[HCen]] is the sea tile, for a wall
   * it would be the hex tile looking at the end of the wall. The vertex for this tile would be 0. */
  def setMouth0(r: Int, c: Int, magnitude: Int)(implicit grid: HGrid): Unit =
  { setCornerPair(r, c, 0, HVDL, HVDR, magnitude, magnitude)
    setCorner(r + 2, c - 2, 2, HVDL, magnitude)
    setCorner(r + 2, c + 2, 4, HVDR, magnitude)
  }

  /** Sets the end of a side terrain at vertex for all 3 tiles right. For example the the mouth of Straits the given [[HCen]] is the sea tile, for a wall
   * it would be the hex tile looking at the end of the wall. The vertex for this tile would be 0. */
  def setMouth0Rt(r: Int, c: Int, magnitude: Int)(implicit grid: HGrid): Unit =
  { setCornerPair(r, c, 0, HVDL, HVDR, 0, magnitude)
    //setCorner(r + 2, c - 2, 2, HVDL, magnitude)
    setCorner(r + 2, c + 2, 4, HVDR, magnitude)
  }

  /** Sets the end of a side terrain at vertex for all 3 tiles. For example the the mouth of Straits the given [[HCen]] is the sea tile, for a wall
   * it would be the hex tile looking at the end of the wall. The vertex for this tile would be 1. */
  def setMouth1(r: Int, c: Int, magnitude: Int)(implicit grid: HGrid): Unit =
  { setCornerPair(r, c, 1, HVUL, HVDn, magnitude, magnitude)
    setCorner(r + 2, c + 2, 3, HVUL, magnitude)
    setCorner(r, c + 4, 5, HVDn, magnitude)
  }

  /** Sets the end of a side terrain at vertex for all 3 tiles left. For example the the mouth of Straits the given [[HCen]] is the sea tile, for a wall
   * it would be the hex tile looking at the end of the wall. The vertex for this tile would be 1. */
  def setMouth1Lt(r: Int, c: Int, magnitude: Int)(implicit grid: HGrid): Unit =
  { setCornerPair(r, c, 1, HVUL, HVDn, magnitude, 0)
    setCorner(r + 2, c + 2, 3, HVUL, magnitude)
  }

  /** Sets the end of a side terrain at vertex for all 3 tiles to right. For example the the mouth of Straits the given [[HCen]] is the sea tile, for a wall
   * it would be the hex tile looking at the end of the wall. The vertex for this tile would be 1. */
  def setMouth1Rt(r: Int, c: Int, magnitude: Int)(implicit grid: HGrid): Unit =
  { setCornerPair(r, c, 1, HVUL, HVDn, 0, magnitude)
    setCorner(r, c + 4, 5, HVDn, magnitude)
  }

  /** Sets the end of a side terrain at vertex for all 3 tiles. For example the the mouth of Straits the given [[HCen]] is the sea tile, for a wall
   * it would be the hex tile looking at the end of the wall. The vertex for this tile would be 2. */
  def setMouth2(r: Int, c: Int, magnitude: Int)(implicit grid: HGrid): Unit =
  { setCornerPair(r, c, 2, HVUp, HVDL, magnitude, magnitude)
    setCorner(r, c + 4, 4, HVUp, magnitude)
    setCorner(r - 2, c + 2, 0, HVDL, magnitude)
  }

  /** Sets the end of a side terrain at vertex for all 3 tiles left. For example the the mouth of Straits the given [[HCen]] is the sea tile, for a wall
   * it would be the hex tile looking at the end of the wall. The vertex for this tile would be 2. */
  def setMouth2Lt(r: Int, c: Int, magnitude: Int)(implicit grid: HGrid): Unit =
  { setCornerPair(r, c, 2, HVUp, HVDL, magnitude, 0)
    setCorner(r, c + 4, 4, HVUp, magnitude)
  }

  /** Sets the end of a side terrain at vertex for all 3 tiles right. For example the the mouth of Straits the given [[HCen]] is the sea tile, for a wall
   * it would be the hex tile looking at the end of the wall. The vertex for this tile would be 2. */
  def setMouth2Rt(r: Int, c: Int, magnitude: Int)(implicit grid: HGrid): Unit =
  { setCornerPair(r, c, 2, HVUp, HVDL, 0, magnitude)
    setCorner(r - 2, c + 2, 0, HVDL, magnitude)
  }

  /** Sets the end of a side terrain at vertex for all 3 tiles. For example the the mouth of Straits the given [[HCen]] is the sea tile, for a wall
   * it would be the hex tile looking at the end of the wall. The vertex for this tile would be 3. */
  def setMouth3(r: Int, c: Int, magnitude: Int)(implicit grid: HGrid): Unit =
  { setCornerPair(r, c, 3, HVUR, HVUL, magnitude, magnitude)
    setCorner(r - 2, c + 2, 5, HVUR, magnitude)
    setCorner(r - 2, c - 2, 1, HVUL, magnitude)
  }

  /** Sets the end of a side terrain at vertex for all 3 tiles left. For example the the mouth of Straits the given [[HCen]] is the sea tile, for a wall
   * it would be the hex tile looking at the end of the wall. The vertex for this tile would be 3. */
  def setMouth3Lt(r: Int, c: Int, magnitude: Int)(implicit grid: HGrid): Unit =
  { setCornerPair(r, c, 3, HVUR, HVUL, magnitude, 0)
    setCorner(r - 2, c + 2, 5, HVUR, magnitude)
  }

  /** Sets the end of a side terrain at vertex for all 3 tiles right. For example the the mouth of Straits the given [[HCen]] is the sea tile, for a wall
   * it would be the hex tile looking at the end of the wall. The vertex for this tile would be 3. */
  def setMouth3Rt(r: Int, c: Int, magnitude: Int)(implicit grid: HGrid): Unit =
  { setCornerPair(r, c, 3, HVUR, HVUL, 0, magnitude)
    setCorner(r - 2, c - 2, 1, HVUL, magnitude)
  }

  /** Sets the end of a side terrain at vertex for all 3 tiles. For example the the mouth of Straits the given [[HCen]] is the sea tile, for a wall
   * it would be the hex tile looking at the end of the wall. The vertex for this tile would be 4. */
  def setMouth4(r: Int, c: Int, magnitude: Int)(implicit grid: HGrid): Unit =
  { setCornerPair(r, c, 4, HVDR, HVUp, magnitude, magnitude)
    setCorner(r - 2, c - 2, 0, HVDR, magnitude)
    setCorner(r, c - 4, 2, HVUp, magnitude)
  }

  /** Sets the end of a side terrain at vertex for all 3 tiles left. For example the the mouth of Straits the given [[HCen]] is the sea tile, for a wall
   * it would be the hex tile looking at the end of the wall. The vertex for this tile would be 4. */
  def setMouth4Lt(r: Int, c: Int, magnitude: Int)(implicit grid: HGrid): Unit =
  { setCornerPair(r, c, 4, HVDR, HVUp, magnitude, 0)
    setCorner(r - 2, c - 2, 0, HVDR, magnitude)
  }

  /** Sets the end of a side terrain at vertex for all 3 tiles right. For example the the mouth of Straits the given [[HCen]] is the sea tile, for a wall
   * it would be the hex tile looking at the end of the wall. The vertex for this tile would be 4. */
  def setMouthRt4(r: Int, c: Int, magnitude: Int)(implicit grid: HGrid): Unit =
  { setCornerPair(r, c, 4, HVDR, HVUp, 0, magnitude)
    setCorner(r, c - 4, 2, HVUp, magnitude)
  }

  /** Sets the end of a side terrain at vertex for all 3 tiles. For example the the mouth of Straits the given [[HCen]] is the sea tile, for a wall
   * it would be the hex tile looking at the end of the wall. The vertex for this tile would be 5. */
  def setMouth5(r: Int, c: Int, magnitude: Int)(implicit grid: HGrid): Unit =
  { setCornerPair(r, c, 5, HVDn, HVUR, magnitude, magnitude)
    setCorner(r, c - 4, 1, HVDn, magnitude)
    setCorner(r + 2, c - 2, 3, HVUR, magnitude)
  }

  /** Sets the end of a side terrain at vertex for all 3 tiles left. For example the the mouth of Straits the given [[HCen]] is the sea tile, for a wall
   * it would be the hex tile looking at the end of the wall. The vertex for this tile would be 5. */
  def setMouth5Lt(r: Int, c: Int, magnitude: Int)(implicit grid: HGrid): Unit =
  { setCornerPair(r, c, 5, HVDn, HVUR, magnitude, 0)
    setCorner(r, c - 4, 1, HVDn, magnitude)
    //setCorner(r + 2, c - 2, 3, HVUR, magnitude)
  }

  /** Sets the end of a side terrain at vertex for all 3 tiles right. For example the the mouth of Straits the given [[HCen]] is the sea tile, for a wall
   * it would be the hex tile looking at the end of the wall. The vertex for this tile would be 5. */
  def setMouthRt5(r: Int, c: Int, magnitude: Int)(implicit grid: HGrid): Unit =
  { setCornerPair(r, c, 5, HVDn, HVUR, 0, magnitude)
    setCorner(r + 2, c - 2, 3, HVUR, magnitude)
  }

  /** Sets the end of a side terrain from off the [[HGrid]] at what would have been vertex 3 for the other 2 tiles. For example the the mouth of
   *  Straits the given [[HCen]] is the sea tile, for a wall it would be the hex tile looking at the end of the wall. */
  def setMouth3OffGrid(r: Int, c: Int, magnitude: Int = 3)(implicit grid: HGrid): Unit =
  { setCorner(r - 2, c + 2, 5, HVDR, magnitude)
    setCorner(r - 2, c - 2, 1, HVDL, magnitude)
  }

  /** Sets the end of a side terrain from off the [[HGrid]] at what would have been vertex 4 for the other 2 tiles. For example the the mouth of
   *  Straits the given [[HCen]] is the sea tile, for a wall it would be the hex tile looking at the end of the wall. */
  def setMouth4OffGrid(r: Int, c: Int, magnitude: Int = 3)(implicit grid: HGrid): Unit =
  { setCorner(r - 2, c - 2, 0, HVDn, magnitude)
    setCorner(r, c - 4, 2, HVUL, magnitude)
  }

  /** Sets the end of a side terrain  from off the [[HGrid]] at what would have been vertex 5 for the other 2 tiles. For example the the mouth of
   * Straits the given [[HCen]] is the sea tile, for a wall it would be the hex tile looking at the end of the wall. The vertex for this tile would be 5. */
  def setMouth5OffGrid(r: Int, c: Int, magnitude: Int = 3)(implicit grid: HGrid): Unit =
  { setCorner(r, c - 4, 1, HVDL, magnitude)
    setCorner(r + 2, c - 2, 3, HVUp, magnitude)
  }

  /** [[HVUL]] and [[HVUR]]. Sets the end of a side terrain at vertex for one tile. For example the the mouth of Straits the given [[HCen]] is the sea
   * tile, for a wall it would be the hex tile looking at the end of the wall. The vertex for this tile would be 0. */
  def setMouth0Corner(r: Int, c: Int, magnitude: Int = 3)(implicit grid: HGrid): Unit = setCornerPair(r, c, 0, HVUL, HVUR, magnitude, magnitude)

  /** [[HVUp]] and [[HVDR]]. Sets the end of a side terrain at vertex for one tile. For example the the mouth of Straits the given [[HCen]] is the sea
   * tile, for a wall it would be the hex tile looking at the end of the wall. The vertex for this tile would be 1. */
  def setMouth1Corner(r: Int, c: Int, magnitude: Int = 3)(implicit grid: HGrid): Unit = setCornerPair(r, c, 1, HVUp, HVDR, magnitude, magnitude)

  /** [[HVUR]] and [[HVDn]]. Sets the end of a side terrain at vertex for one tile. For example the the mouth of Straits the given [[HCen]] is the sea
   *  tile, for a wall it would be the hex tile looking at the end of the wall. The vertex for this tile would be 2. */
  def setMouth2Corner(r: Int, c: Int, magnitude: Int = 3)(implicit grid: HGrid): Unit = setCornerPair(r, c, 2, HVUR, HVDn, magnitude, magnitude)

  /** [[HVDR]] and [[HVDL]] Sets the end of a side terrain at vertex for one tile. For example the the mouth of Straits the given [[HCen]] is the sea
   *  tile, for a wall it would be the hex tile looking at the end of the wall. The vertex for this tile would be 3. */
  def setMouth3Corner(r: Int, c: Int, magnitude: Int = 3)(implicit grid: HGrid): Unit = setCornerPair(r, c, 3, HVDR, HVDL, magnitude, magnitude)

  /** [[HVDn]] and [[HVUL]]. Sets the end of a side terrain at vertex for one tile. For example the the mouth of Straits the given [[HCen]] is the sea
   *  tile, for a wall it would be the hex tile looking at the end of the wall. The vertex for this tile would be 4. */
  def setMouth4Corner(r: Int, c: Int, magnitude: Int = 3)(implicit grid: HGrid): Unit = setCornerPair(r, c, 4, HVDn, HVUL, magnitude, magnitude)

  /** Sets the end of a side terrain at vertex for all 3 tiles. For example the the mouth of Straits the given [[HCen]] is the sea tile, for a wall
   * it would be the hex tile looking at the end of the wall. The vertex for this tile would be 5. */
  def setMouth5Corner(r: Int, c: Int, magnitude: Int = 3)(implicit grid: HGrid): Unit = setCornerPair(r, c, 5, HVDL, HVUp, magnitude, magnitude)

  /** Sets thr corner in at the specified vertex if the specified [[HCen]] exists. */
  def setCornerIn(cenR: Int, cenC: Int, vertNum: Int, magnitude: Int)(implicit grid: HGrid): Unit =
  { val dirn = HVDirn.inFromVertIndex(vertNum)
    if (grid.hCenExists(cenR, cenC))
    { val corner: HCorner = HCorner.sideSpecial(dirn, magnitude)
      val index: Int = unsafeIndex(cenR, cenC, vertNum)
      unsafeArray(index) = corner.unsafeInt
    }
  }

  def set2CornersIn(cenR: Int, cenC: Int, firstVertNum: Int, magnitude: Int = 3)(implicit grid: HGrid): Unit =
    iUntilForeach(2) { i => setCornerIn(cenR, cenC, (firstVertNum + i) %% 6, magnitude) }

  def set3CornersIn(cenR: Int, cenC: Int, firstVertNum: Int, magnitude: Int = 3)(implicit grid: HGrid): Unit =
    iUntilForeach(3) { i => setCornerIn(cenR, cenC, (firstVertNum + i) %% 6, magnitude) }

  def set4CornersIn(cenR: Int, cenC: Int, firstVertNum: Int, magnitude: Int = 3)(implicit grid: HGrid): Unit =
    iUntilForeach(4){i => setCornerIn(cenR, cenC, (firstVertNum + i) %% 6, magnitude) }

  def set6CornersIn(cenR: Int, cenC: Int, magnitude: Int = 3)(implicit grid: HGrid): Unit =
    iUntilForeach(6) { i => setCornerIn(cenR, cenC, i, magnitude) }

  def setNCornersIn(cenR: Int, cenC: Int, numIndents: Int, firstVertNum: Int, magnitude: Int)(implicit grid: HGrid): Unit =
    iUntilForeach(numIndents) { i => setCornerIn(cenR, cenC, (firstVertNum + i) %% 6, magnitude) }

  /** Sets a single [[HCorner]] corner with 2 [[HVOffsetDelta]]s. */
  def setCornerPair(cenR: Int, cenC: Int, vertNum: Int, dirn1: HVDirnOpt, dirn2: HVDirnOpt, magnitude1: Int = 3, magnitude2: Int = 3)(
    implicit grid: HGrid): Unit = setCornerPair(HCen(cenR, cenC), vertNum, dirn1, magnitude1, dirn2, magnitude2)

  /** Sets a single [[HCorner]] corner with 2 [[HVOffsetDelta]]s. */
  def setCornerPair(hCen: HCen, vertNum: Int, dirn1: HVDirnOpt, magnitude1: Int, dirn2: HVDirnOpt, magnitude2: Int)(implicit grid: HGrid): Unit =
    if(grid.hCenExists(hCen))
    { val corner = HCorner.double(dirn1, magnitude1, dirn2, magnitude2)
      val index = unsafeIndex(hCen, vertNum)
      unsafeArray(index) = corner.unsafeInt
    }

  /** Sets a single [[HCorner]] corner with 1 [[HVOffsetDelta]] for the tile 2 for the [[hSide]]. */
  def setSideCornerSpecial(cenR: Int, cenC: Int, vertNum: Int, dirn1: HVDirnOpt, magnitude1: Int = 3)(
    implicit grid: HGrid): Unit = setSideSpecial(HCen(cenR, cenC), vertNum, dirn1, magnitude1)

  /** Sets a single [[HCorner]] corner with [[HVOffsetDelta]] for the tile 2 for the HSide. */
  def setSideSpecial(hCen: HCen, vertNum: Int, dirn1: HVDirnOpt, magnitude1: Int)(implicit grid: HGrid): Unit =
    if(grid.hCenExists(hCen))
    { val corner = HCorner.sideSpecial(dirn1, magnitude1)
      val index = unsafeIndex(hCen, vertNum)
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

  /** Returns the [[PolygonHVOffset]] [[PolygonLike]] for the given [[HSep]]. */
  def sidePoly(hs: HSep)(implicit gridSys: HGridSys): PolygonHVOffset = hs.tileLtOpt match
  {
    case None =>
    { val (hcRt, vi) = hs.tileRtAndVert
      val p1 = cornerV1(hcRt, vi)
      val p2 = cornerV1(hcRt, (vi - 1) %% 6)
      val p3 = hs.vertLower.noOffset
      val p4 = hs.vertUpper.noOffset
      PolygonHVOffset(hcRt.vExact(vi), p1, p2, p3, p4)
    }

    case Some(hcLt) => hs.tileRtOpt match
    {
      case None =>
      { val (hcLt, vi) = hs.tileLtAndVert
        val p1 = hs.vertUpper.noOffset
        val p2 = hs.vertLower.noOffset
        val p3 = cornerV1(hcLt, (vi + 1) %% 6)
        val p4 = cornerV1(hcLt, vi)
        PolygonHVOffset(p1, p2, hcLt.vExact((vi + 1) %% 6), p3, p4)
      }

      case Some(_) =>
      { val (hcRt, vi) = hs.tileRtAndVert
        val (hcLt, lvi) = hs.tileLtAndVertFromRt(hcRt.r)
        val p1 = cornerV1(hcRt, vi)
        val vi2 = (vi - 1) %% 6
        val p2: HVOffset = cornerV1(hcRt, vi2)
        val vi3 = (lvi + 1) %% 6
        val p3: HVOffset = cornerV1(hcLt, vi3)
        val vi4 = lvi %% 6
        val p4: HVOffset = cornerV1(hcLt, vi4)
        val arr1: HVOffsetArr = ife(isSpecial(hcRt, vi) & isSpecial(hcLt, vi4), HVOffsetArr(hcRt.vExact(vi), p1, p2), HVOffsetArr(p1, p2))
        val arr2: HVOffsetArr = ife(isSpecial(hcRt, vi2) & isSpecial(hcLt, vi3), HVOffsetArr(hcRt.vExact(vi2), p3, p4), HVOffsetArr(p3, p4))
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