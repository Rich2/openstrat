/* Copyright 2018-23 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package prid; package phex

/** Helper trait for setting an [[HCenLayer]], [[HSideLayer]] and a [[HCornerLayer]] at the same time. This allows the basic geometry of the
 *  terrain to be laid out in systematic row order. */
trait HSetter[TT <: AnyRef, ST, SST <: ST with HSideSome]
{
  implicit def grid: HGrid
  def terrs: HCenLayer[TT]
  def sTerrs: HSideOptLayer[ST, SST]
  def corners: HCornerLayer

  trait IsleBase
  {
    def terr: TT
    def sTerr: SST
    def run(row: Int, c: Int): Unit =
    { terrs.set(row, c, terr)
      corners.setNCornersIn(row, c, 6, 0, 7)
      iUntilForeach(6) { i => corners.setCornerIn(row, c, i, 7) }
      iUntilForeach(6) { i =>
        val side = HCen(row, c).side(i)
        sTerrs.set(side, sTerr)
      }
    }
  }

  trait HlandBase
  { /** The number of indented vertices. */
    def numIndentedVerts: Int

    /** The number of the first vertex to be indented. */
    def indentStartIndex: Int

    /** The terrain of the main tile, typically a type of land. */
    def terr: TT

    /** The land of the sides, typically a type of water. */
    def sideTerrs: SST

    def run(row: Int, c: Int): Unit =
    {
      terrs.set(row, c, terr)
      corners.setNCornersIn(row, c, numIndentedVerts, indentStartIndex, 7)

      iUntilForeach(numIndentedVerts) { i0 =>
        val i: Int = (indentStartIndex + i0) %% 6
        corners.setCornerIn(row, c, i, 7)
      }

      iUntilForeach(-1, numIndentedVerts) { i0 =>
        val i: Int = (indentStartIndex + i0) %% 6
        val side = HCen(row, c).side(i)
        sTerrs.set(side, sideTerrs)
      }
    }
  }

  /** Sets the mouth in the given direction and the side terrain in the opposite direction from the vertex. */
  trait MouthBase
  {
    def c: Int
    def dirn: HVDirn
    def st: SST

    def run(row: Int): Unit = dirn match
    { case HVUp =>
      { corners.setMouth3(row + 1, c)
        sTerrs.set(row - 1, c, st)
      }

      case HVUR =>
      { corners.setMouth4(row + 1, c + 2)
        sTerrs.set(row, c - 1, st)
      }

      case HVDR =>
      { corners.setMouth5(row - 1, c + 2)
        sTerrs.set(row, c - 1, st)
      }

      case HVDn =>
      { corners.setMouth0(row - 1, c)
        sTerrs.set(row + 1, c, st)
      }

      case HVDL =>
      { corners.setMouth1(row - 1, c - 2)
        sTerrs.set(row, c + 1, st)
      }

      case HVUL =>
      { corners.setMouth2(row + 1, c - 2)
        sTerrs.set(row, c + 2, st)
      }

      case d => excep("Unimplemented dirn.")
    }
  }

  /** Sets the Vert in for a side terrain, eg Straits / River / Wall and sets the left most of the sides.  */
  trait VertInBase
  { def c: Int
    def dirn: HVDirn
    def terr: SST

    def run(row: Int): Unit = dirn match
    { case HVUR =>
      { corners.setVert4In(row + 1, c + 2)
        sTerrs.setIf(row + 1, c, terr)
        //sTerrs.setIf(row, c + 1, terr)
      }

      case HVDR =>
      { corners.setVert5In(row - 1, c + 2)
        sTerrs.set(row - 1, c, terr)
        //sTerrs.set(row, c + 1, terr)
      }

      case HVDn =>
      { corners.setVert0In(row - 1, c, 3)
        sTerrs.setIf(row, c - 1, terr)
       // sTerrs.setIf(row, c + 1, terr)
      }

      case HVDL =>
      { corners.setVert1In(row - 1, c - 2)
        sTerrs.set(row, c - 1, terr)
      //  sTerrs.set(row - 1, c, side2)
      }

      case HVUL =>
      { corners.setVert2In(row + 1, c - 2)
      //  sTerrs.setIf(row + 1, c, side1)
        sTerrs.setIf(row, c - 1, terr)
      }

      case HVUp =>
      { corners.setVert3In(row + 1, c, 3)
        sTerrs.setIf(row, c - 1, terr)
       // sTerrs.setIf(row, c + 1, terr)
      }
    }
  }

  trait ThreeWayBase
  { def c: Int
    def st: SST
    def magnitude: Int

    def run(row: Int): Unit =
    { corners.setVertEqual(row, c, magnitude)
      sTerrs.set(row, c + 1, st)
    }
  }

  /** This is for setting sides on the edge of grids that sit within the hex area of the tile on the neighbouring
   *  grid. */
  trait SetSideBase
  { def c: Int
    def terr: SST
    def run(row: Int): Unit = sTerrs.set(row, c, terr)
  }

  /** Used for setting the a vertex on the left edge of a grid. Sets the vetex to the right on both hex tiles. */
  trait VertRightsRightBase
  { /** The c coordinate of the vertex. */
    def c: Int

    /** The magnitude of the offset. */
    def magnitude: Int

    def run(row: Int): Unit = if (HVert.rcISHigh(row, c)){
      corners.setCorner(row + 1, c + 2, 4, HVRt, magnitude)
      corners.setCorner(row - 1, c, 0, HVRt, magnitude)
    }
    else{
      corners.setCorner(row + 1, c, 3, HVRt, magnitude)
      corners.setCorner(row - 1, c + 2, 5, HVRt, magnitude)
    }
  }

  /** Used for setting the a vertex on the right edge of a grid. Sets the vertex to the left on both hex tiles. */
  trait VertLeftsLeftBase
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
