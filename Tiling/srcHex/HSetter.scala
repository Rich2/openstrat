/* Copyright 2018-23 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package prid; package phex
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
  {
    def numIndentedVerts: Int
    def indentStartIndex: Int
    def terr: TT
    def sideTerrs: SST

    def run(row: Int, c: Int): Unit =
    {
      terrs.set(row, c, terr)
      corners.setNCornersIn(row, c, numIndentedVerts, indentStartIndex, 7)

      iUntilForeach(numIndentedVerts) { i0 =>
        val i: Int = (indentStartIndex + i0) %% 6
        corners.setCornerIn(row, c, i, 7)
      }

      iToForeach(numIndentedVerts + 1) { i0 =>
        val i: Int = (indentStartIndex + i0 - 1) %% 6
        val side = HCen(row, c).side(i)
        sTerrs.set(side, sideTerrs)
      }
    }
  }

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

  trait VertInBase
  { def c: Int
    def dirn: HVDirn
    def terr: SST
    //def side2: SST

    def run(row: Int): Unit = dirn match
    { case HVUR =>
      { corners.setVert4In(row + 1, c + 2)
       // sTerrs.setIf(row + 1, c, side1)
        sTerrs.setIf(row, c + 1, terr)
      }

      case HVDR =>
      { corners.setVert5In(row - 1, c + 2)
        //sTerrs.set(row - 1, c, side1)
        sTerrs.set(row, c + 1, terr)
      }

      case HVDn =>
      { corners.setVert0In(row - 1, c, 3)
       // sTerrs.setIf(row, c - 1, side1)
        sTerrs.setIf(row, c + 1, terr)
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
      //  sTerrs.setIf(row, c - 1, side1)
        sTerrs.setIf(row, c + 1, terr)
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
}
