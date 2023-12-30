/* Copyright 2018-23 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package prid; package phex

/** Helper trait for setting an [[LayerHcRefSys]], [[HSideLayer]] and a [[HCornerLayer]] at the same time. This allows the basic geometry of the
 *  terrain to be laid out in systematic row order. */
trait HSetter[TT <: AnyRef, ST, SST <: ST with HSideSome]
{ implicit def grid: HGrid

  def terrs: LayerHcRefSys[TT]

  def sTerrs: LayerHSOptSys[ST, SST]

  def corners: HCornerLayer

  trait IsleNBase
  { /** The tile terrain. typically land terrain. */
    def terr: TT

    /** The Side terrain, Typically water terrain, */
    def sTerr: SST

    def magnitude: Int

    def run(row: Int, c: Int): Unit =
    { terrs.set(row, c, terr)
      corners.setNCornersIn(row, c, 6, 0, magnitude)
      //iUntilForeach(6) { i => corners.setCornerIn(row, c, i, magnitude) }
      iUntilForeach(6) { i =>
        val side = HCen(row, c).side(i)
        sTerrs.set(side, sTerr)
      }
    }
  }

  /** Sets the side terrain and corners for an Island, with a radius of 10/16 of the radius of the hex. */
  trait IsleBase extends IsleNBase
  { override def magnitude: Int = 6
  }

  /** Sets the side terrain and corners for an Island, with a radius of 8/16 of the radius of the hex. */
  trait Isle8Base extends IsleNBase
  { override def magnitude: Int = 8
  }

  /** Sets the side terrain and corners for an Island, with a radius of 6/16 of the radius of the hex. */
  trait Isle6Base extends IsleNBase
  { override def magnitude: Int = 10
  }

  /** Sets the side terrain and corners for an Island, with a radius of 5/16 of the radius of the hex. */
  trait Isle5Base extends IsleNBase
  { override def magnitude: Int = 11
  }

  /** Sets the side terrain and corners for an Island, with a radius of 4/16 of the radius of the hex. */
  trait Isle4Base extends IsleNBase
  { override def magnitude: Int = 12
  }

  /** Sets the side terrain and corners for an Island, with a radius of 3/16 of the radius of the hex. */
  trait Isle3Base extends IsleNBase
  { override def magnitude: Int = 13
  }

  /** Sets a side in the tile row. This is type B side. */
  trait SideBBase
  { /** The Side terrain. */
    def sTerr: SST

    def run(row: Int, c: Int): Unit = sTerrs.set(row, c - 2, sTerr)
  }

  /** Base trait for capes / headlands / peninsulas. */
  trait CapeBase
  { /** The number of the first vertex to be indented. */
    def indentStartIndex: Int

    /** The number of indented vertices. */
    def numIndentedVerts: Int

    /** The terrain of the main tile, typically a type of land. */
    def terr: TT

    /** The terrain of the sides, typically a type of water. */
    def sideTerrs: SST

    def run(row: Int, c: Int): Unit =
    { terrs.set(row, c, terr)
      corners.setNCornersIn(row, c, numIndentedVerts, indentStartIndex, 7)

      iUntilForeach(-1, numIndentedVerts) { i0 =>
        val i: Int = (indentStartIndex + i0) %% 6
        val side = HCen(row, c).side(i)
        sTerrs.set(side, sideTerrs)
      }
    }
  }

  /** Base trait [Isthmus](https://en.wikipedia.org/wiki/Isthmus). Generally this will be used for Isthmuses, but it can be used for any [[HCen]] and
   * [[HSep]] terrain that fits the geometry. */
  trait IsthmusBase
  { /** The number of the first vertex to be indented. */
    def indentIndex: Int

    /** The number of the first vertex to be indented. */
    def oppositeIndex: Int = (indentIndex + 3) %% 6

    /** The terrain of the main tile, typically a type of land. */
    def terr: TT

    /** The terrain of the sides, next to the index vertex, typically a type of water. */
    def sideTerrs1: SST

    /** The terrain of the sides, next to the opposite vertex typically a type of water. */
    def sideTerrs2: SST

    /** Sets the [[HCen]] terrain. Sets the two opposite [[HCorner]]s and sets the four [[HSep]] terrains adjacent to the pulled in vertices. The
     * side terrains can be different on either side of the isthmus. For eample it could be sea on one side and fresh water lake on the other. */
    def run(row: Int, c: Int): Unit =
    { terrs.set(row, c, terr)
      corners.setCornerIn(row, c, indentIndex, 7)
      corners.setCornerIn(row, c, oppositeIndex, 7)

      sTerrs.set(HCen(row, c).side(indentIndex - 1), sideTerrs1)
      sTerrs.set(HCen(row, c).side(indentIndex), sideTerrs1)
      sTerrs.set(HCen(row, c).side(indentIndex - 4), sideTerrs2)
      sTerrs.set(HCen(row, c).side(indentIndex + 3), sideTerrs2)
    }
  }

  /** Sets the mouth in the given direction and the side terrain in the opposite direction from the vertex. */
  trait MouthBase
  { /** The C coordinate of the vertex. */
    def c: Int

    /** The direction of the mouth. */
    def dirn: HVDirnPrimary

    /** The magnitude of the offsets. */
    def magnitude: Int

    /** The terrain of the left most side of the junction. */
    def terr: SST

    def run(row: Int): Unit = dirn match
    { case HVUp =>
      { corners.setMouth3(row + 1, c, magnitude)
        sTerrs.set(row - 1, c, terr)
      }

      case HVUR =>
      { corners.setMouth4(row + 1, c + 2, magnitude)
        sTerrs.set(row, c - 1, terr)
      }

      case HVDR =>
      { corners.setMouth5(row - 1, c + 2, magnitude)
        sTerrs.set(row, c - 1, terr)
      }

      case HVDn =>
      { corners.setMouth0(row - 1, c, magnitude)
        sTerrs.set(row + 1, c, terr)
      }

      case HVDL =>
      { corners.setMouth1(row - 1, c - 2, magnitude)
        sTerrs.set(row, c + 1, terr)
      }

      case HVUL =>
      { corners.setMouth2(row + 1, c - 2, magnitude)
        sTerrs.set(row, c + 2, terr)
      }
    }
  }

  /** Sets the mouth in the given direction and the side terrain in the opposite direction from the vertex. */
  trait MouthSpecBase
  { /** The C coordinate of the vertex. */
    def c: Int

    /** The direction of the mouth. */
    def mouthDirn: HVDirnPrimary

    def dirn1: HVDirn

    def dirn2: HVDirn

    /** The magnitude of the 1st offset of the mouth. */
    def magnitude1: Int

    def magnitude2: Int

    /** The terrain of the left most side of the junction. */
    def terr: SST

    def run(row: Int): Unit = mouthDirn match
    { case HVUp =>
      { corners.setCorner(row - 1, c + 2, 5, dirn1, magnitude1)
        corners.setCorner(row - 1, c - 2, 1, dirn2, magnitude2)
        corners.setCornerPair(row + 1, c, 3, dirn1, dirn2, magnitude1, magnitude2)
        sTerrs.set(row - 1, c, terr)
      }

      case HVUR =>
      { corners.setCorner(row - 1, c, 0, dirn1, magnitude1)
        corners.setCorner(row + 1, c - 2, 2, dirn2, magnitude2)
        corners.setCornerPair(row + 1, c + 2, 4, dirn1, dirn2, magnitude1, magnitude2)
        sTerrs.set(row, c - 1, terr)
      }

      case HVDR =>
      { corners.setCorner(row - 1, c - 2, 1, dirn1, magnitude1)
        corners.setCorner(row + 1, c, 3, dirn2, magnitude2)
        corners.setCornerPair(row - 1, c + 2, 5, dirn1, dirn2, magnitude1, magnitude2)
        sTerrs.set(row, c - 1, terr)
      }

      case HVDn =>
      { corners.setCorner(row + 1, c - 2, 2, dirn1, magnitude1)
        corners.setCorner(row + 1, c + 2, 4, dirn2, magnitude2)
        corners.setCornerPair(row - 1, c, 0, dirn1, dirn2, magnitude1, magnitude2)
        sTerrs.set(row + 1, c, terr)
      }

      case HVDL =>
      { corners.setCorner(row + 1, c, 3, dirn1, magnitude1)
        corners.setCorner(row - 1, c + 2, 5, dirn2, magnitude2)
        corners.setCornerPair(row - 1, c - 2, 1, dirn1, dirn2, magnitude1, magnitude2)
        sTerrs.set(row, c + 1, terr)
      }

      case HVUL =>
      { corners.setCorner(row + 1, c + 2, 4, dirn1, magnitude1)
        corners.setCorner(row - 1, c, 0, dirn2, magnitude2)
        corners.setCornerPair(row + 1, c - 2, 2, dirn1, dirn2, magnitude1, magnitude2)
        sTerrs.set(row, c + 2, terr)
      }
    }
  }

  /** Sets all the corners of Vertex for a bend side terrain, Sets the left most of the sides of this vertex. The orientation of the bend is specified
   *  by the direction of the inside of the bend. */
  trait BendInOutBase
  { def c: Int
    def magIn: Int
    def magOut: Int
    def dirn: HVDirn
    def terr: SST

    def run(row: Int): Unit = dirn match
    { case HVUR =>
      { corners.setBend4All(row + 1, c + 2, magIn, magOut)
        sTerrs.setIf(row + 1, c, terr)
      }

      case HVDR =>
      { corners.setBend5All(row - 1, c + 2, magIn, magOut)
        sTerrs.set(row - 1, c, terr)
      }

      case HVDn =>
      { corners.setBend0All(row - 1, c, magIn, magOut)
        sTerrs.setIf(row, c - 1, terr)
      }

      case HVDL =>
      { corners.setBend1All(row - 1, c - 2, magIn, magOut)
        sTerrs.set(row, c - 1, terr)
      }

      case HVUL =>
      { corners.setBend2All(row + 1, c - 2, magIn, magOut)
        sTerrs.setIf(row, c - 1, terr)
      }

      case HVUp =>
      { corners.setBend3All(row + 1, c, magIn, magOut)
        sTerrs.setIf(row, c - 1, terr)
      }

      case HVLt | HVRt => excep("HVLt and HVRt not implemented")
    }
  }

  trait BendAllBase extends BendInOutBase
  {
    def magnitude: Int

    override def magIn: Int = magnitude

    override def magOut: Int = magnitude
  }

  /** Sets the 2 outer corners of the bend for side terrain, Also sets the left most of the sides of the bend vertex. The orientation of the bend is
   * specified by the direction of the inside of the bend. */
  trait BendOutBase
  { /** The coordinate of the vertex of the bend. */
    def c: Int

    /** The magnitude of the offset for the 2 outer corners of the bend vertex. */
    def magnitude: Int

    /** The direction of the inside of the bend. */
    def dirn: HVDirn

    /** The terrain of the sides. */
    def terr: SST

    /** Runs the setting actions.  */
    def run(row: Int): Unit = dirn match
    { case HVUR =>
      { corners.setBend4Out(row + 1, c + 2, magnitude)
        sTerrs.setIf(row + 1, c, terr)
      }
      case HVDR =>
      { corners.setBend5Out(row - 1, c + 2, magnitude)
        sTerrs.set(row - 1, c, terr)
      }
      case HVDn =>
      { corners.setVert0Out(row - 1, c, magnitude)
        sTerrs.setIf(row, c - 1, terr)
      }
      case HVDL =>
      { corners.setBend1Out(row - 1, c - 2, magnitude)
        sTerrs.set(row, c - 1, terr)
      }
      case HVUL =>
      { corners.setBend2Out(row + 1, c - 2, magnitude)
        sTerrs.setIf(row, c - 1, terr)
      }
      case HVUp =>
      { corners.setBend3Out(row + 1, c, magnitude)
        sTerrs.setIf(row, c - 1, terr)
      }
      case HVLt | HVRt => excep("HVLt and HVRt not implemented")
    }
  }

  /** Sets only the inside corner of Vertex for a bend side terrain, Sets the left most of the sides of this vertex. The orientation of the bend is
   *  specified by the direction of the inside of the bend. */
  trait BendInBase
  { def c: Int

    def magnitude: Int

    def dirn: HVDirn

    def terr: SST

    def run(row: Int): Unit = dirn match
    { case HVUR =>
      { corners.setCornerIn(row + 1, c + 2, 4, magnitude)
        sTerrs.setIf(row + 1, c, terr)
      }

      case HVDR =>
      { corners.setCornerIn(row - 1, c + 2, 5, magnitude)
        sTerrs.set(row - 1, c, terr)
      }

      case HVDn =>
      { corners.setCornerIn(row - 1, c, 0, magnitude)
        sTerrs.setIf(row, c - 1, terr)
      }

      case HVDL =>
      { corners.setCornerIn(row - 1, c - 2, 1, magnitude)
        sTerrs.set(row, c - 1, terr)
      }

      case HVUL =>
      { corners.setCornerIn(row + 1, c - 2, 2, magnitude)
        sTerrs.setIf(row, c - 1, terr)
      }

      case HVUp =>
      { corners.setCornerIn(row + 1, c, 3, magnitude)
        sTerrs.setIf(row, c - 1, terr)
      }

      case HVLt | HVRt => excep("HVLt and HVRt not implemented")
    }
  }

  /** Used for setting a vertex where 3 side terrains meet. Also sets the left most side. */
  trait ThreeWayBase
  { def c: Int
    def st: SST
    def magnitude: Int

    def run(row: Int): Unit =
    { corners.setVertEqual(row, c, magnitude)
      sTerrs.set(row, c - 1, st)
    }
  }

  /** Used for setting a vertex where 3 side terrains meet. Also sets the left most side. */
  trait ThreeDownBase
  { def c: Int
    def st: SST
    def magUp: Int
    def magDR: Int
    def magDL: Int

    def run(row: Int): Unit =
    { grid.hCenExistsIfDo(row + 1, c) { corners.setCornerIn(row + 1, c, 3, magUp) }
      grid.hCenExistsIfDo(row - 1, c + 2) { corners.setCornerIn(row - 1, c + 2, 5, magDR) }
      grid.hCenExistsIfDo(row - 1, c - 2) { corners.setCornerIn(row - 1, c -2, 1, magDL) }
      sTerrs.set(row, c - 1, st)
    }
  }

  /** This is for setting sides on the edge of grids that sit within the hex area of the tile on the neighbouring grid. */
  trait SetSideBase
  { def c: Int
    def terr: SST
    def run(row: Int): Unit = sTerrs.set(row, c, terr)
  }

  /** Used for setting the a vertex on the left edge of a grid. Sets the vertex to the right on both hex tiles. */
  trait VertLeftsRightBase
  { /** The c coordinate of the vertex. */
    def c: Int

    /** The magnitude of the offset. */
    def magnitude: Int

    def run(row: Int): Unit = if (HVert.rcISHigh(row, c))
    { corners.setCorner(row + 1, c + 2, 4, HVRt, magnitude)
      corners.setCorner(row - 1, c, 0, HVRt, magnitude)
    }
    else
    { corners.setCorner(row + 1, c, 3, HVRt, magnitude)
      corners.setCorner(row - 1, c + 2, 5, HVRt, magnitude)
    }
  }

  /** Used for setting the a vertex on the right edge of a grid. Sets the vertex to the left on both hex tiles. */
  trait VertRightsLeftBase
  { /** The c coordinate of the vertex. */
    def c: Int

    /** The magnitude of the offset. */
    def magnitude: Int

    def run(row: Int): Unit = if (HVert.rcISHigh(row, c))
    { corners.setCorner(row + 1, c - 2, 2, HVLt, magnitude)
      corners.setCorner(row - 1, c, 0, HVLt, magnitude)
    }
    else
    { corners.setCorner(row + 1, c, 3, HVLt, magnitude)
      corners.setCorner(row - 1, c - 2, 1, HVLt, magnitude)
    }
  }
}
