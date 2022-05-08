/* Copyright 2018-22 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package prid; package phex
import geom._

/** This class may be removed. Its for the development of [[HGridSys]]. So just 2 regular grids side by side, to make an easy start on the general problem. */
final class HGrids2(val minCenR: Int, val maxCenR: Int, val minC1: Int, val maxC1: Int, val minC2: Int, maxC2: Int) extends HGridMulti
{ type ManT = HGridMan
  type GridT = HGrid
  val grid1 = HGridReg(minCenR, maxCenR, minC1, maxC1)
  val grid2 = HGridReg(minCenR, maxCenR, minC2, maxC2)
  override def grids: Arr[HGrid] = Arr(grid1, grid2)

  val grid2OffsetC: Int = maxC1 - minC2 + 2

  val gridMan1: HGridMan = new HGridMan
  { override val grid: HGrid = grid1
    override val arrIndex: Int = 0

    override def offset: Vec2 = Vec2(0, 0)

    override def sidesForeach(f: HSide => Unit): Unit = grid.sidesForeach(f)

    override def innerSidesForeach(f: HSide => Unit): Unit = grid.innerSidesForeach(f)

    override def outerSidesForeach(f: HSide => Unit): Unit =
    { grid.bottomRowForeachSide(f)
      iToForeach(grid.bottomCenR, grid.topCenR){
        case r if r.isEven => f(HSide(r, grid.rowLeftCenC(r) - 2))
        case r => f(HSide(r, grid.leftCenC - 1))
      }
      grid.topRowForeachSide(f)
    }

    override def outSteps(r: Int, c: Int): HStepCenArr = (r, c) match
    { case (r, c) if r == maxCenR & c == maxC1 => HStepCenArr((HexRt, r, c - grid2OffsetC + 4), (HexDR, r - 2, c - grid2OffsetC + 2))
      case (r, c) if r == maxCenR & c == maxC1 - 2 => HStepCenArr((HexRt, r, c - grid2OffsetC + 4))

      case (r, c) if r == minCenR & c == maxC1 => HStepCenArr((HexUR, r + 2, c - grid2OffsetC + 2), (HexRt, r, c - grid2OffsetC + 4))
      case (r, c) if r == minCenR & c == maxC1 - 2 => HStepCenArr((HexRt, r, c - grid2OffsetC + 4))

      case (r, c) if c == maxC1 =>
        HStepCenArr((HexUR, r + 2, c - grid2OffsetC + 2), (HexRt, r, c - grid2OffsetC + 4), (HexDR, r - 2, c - grid2OffsetC + 2))

      case (r, c) if c == maxC1 - 2 => HStepCenArr((HexRt, r, c - grid2OffsetC + 4))
      case _ => HStepCenArr()
    }
  }

  val gridMan2: HGridMan = new HGridMan
  { override val grid: HGrid = grid2
    override val arrIndex: Int = grid1.numTiles
    override def offset: Vec2 = Vec2(maxC1 - minC2 + 2, 0)

    def sidesForeach(f: HSide => Unit): Unit = grid.sidesForeach { hs => hs match
      { case HSide(r, c) if c == grid.leftSideC + 1 & r == grid.topSideR => f(hs)
        case HSide(r, c) if c == grid.leftSideC + 1 & r == grid.bottomSideR => f(hs)
        case HSide(r, c) if c <= grid.leftCenC =>
        case _ => f(hs)
      }
    }
    override def innerSidesForeach(f: HSide => Unit): Unit = grid.innerSidesForeach(f)

    override def outerSidesForeach(f: HSide => Unit): Unit =
    { grid.bottomRowForeachSide(f)
      iToForeach(grid.bottomCenR, grid.topCenR){
        case r if r.isEven => f(HSide(r, grid.rowRightCenC(r) + 2))
        case r => f(HSide(r, grid.rightCenC + 1))
      }
      grid.topRowForeachSide(f)
    }

    override def outSteps(r: Int, c: Int): HStepCenArr = (r, c) match
    { case (r, c) if r == maxCenR & c == minC2 => HStepCenArr((HStepLt, r, c + grid2OffsetC - 4), (HexDL, r - 2, c + grid2OffsetC - 2))
      case (r, c) if r == maxCenR & c == minC2 + 2 => HStepCenArr((HStepLt, r, c + grid2OffsetC - 4))

      case (r, c) if r == minCenR & c == minC2 => HStepCenArr((HStepUL, r + 2, c + grid2OffsetC - 2), (HStepLt, r, c + grid2OffsetC - 4))
      case (r, c) if r == minCenR & c == minC2 + 2 => HStepCenArr((HStepLt, r, c + grid2OffsetC - 4))

      case (r, c) if c == minC2 =>
        HStepCenArr((HStepUL, r + 2, c + grid2OffsetC - 2), (HStepLt, r, c + grid2OffsetC - 4), (HexDL, r - 2, c + grid2OffsetC - 2))
      case (r, c) if c == minC2 + 2 => HStepCenArr((HStepLt, r, c + grid2OffsetC - 4))

      case _ => HStepCenArr()
    }
  }

  override val gridMans: Arr[HGridMan] = Arr(gridMan1, gridMan2)

  override def unsafeGetMan(r: Int, c: Int): HGridMan = ife(c <= maxC1, gridMan1, gridMan2)

  val grid1Offset: Vec2 = 0 vv 0
  val grid2Offset: Vec2 = Vec2(grid2OffsetC, 0)

  override def top: Double = maxCenR * Sqrt3 + 4.0/Sqrt3
  override def bottom: Double = minCenR * Sqrt3 - 4.0/Sqrt3
  override def left: Double = grid1.left
  override def right: Double = grid1.right + grid2.width - 2

  override def hCoordToPt2(hCoord: HCoord): Pt2 = hCoord.c match
  { case c if c >= (grid1.leftCenC - 2) & c <= (grid1.rightCenC + 4) => grid1.hCoordToPt2(hCoord)
    case c if c >= (grid2.leftCenC - 4) & c <= (grid2.rightCenC + 2) => grid2.hCoordToPt2(hCoord) + grid2Offset
    case c => excep(c.toString + " out of range in hCoordToPt2")
  }

  /*override def findStep(startHC: HCen, endHC: HCen): Option[HStep] = (startHC, endHC) match {
    case (shc, ehc) if grid1.hCenExists(shc) => gridMan1.findStep(shc, ehc)// & grid1.hCenExists(ehc) => grid1.findStep(shc, ehc)
    case (shc, ehc) if grid2.hCenExists(shc) => gridMan2.findStep(shc, ehc)// & grid2.hCenExists(ehc) => grid2.findStep(shc, ehc)
    case (hc1, hc2) if hc1.c == grid1.rowRightCenC(hc1.c) & hc2.c == grid2.rowLeftCenC(hc1.c) => Some(HStepRt)
    case (hc1, hc2) if hc1.c == grid2.rowLeftCenC(hc1.c) & hc2.c == grid1.rowRightCenC(hc1.c) => Some(HStepLt)
    case _ => None
  }*/

  /** H cost for A* path finding. To move 1 tile has a cost 2. This is because the G cost or actual cost is the sum of the terrain cost of tile of
   * departure and the tile of arrival. */
  override def getHCost(startCen: HCen, endCen: HCen): Int = ???
}

object HGrids2
{
  def apply(minR: Int, maxR: Int, minC1: Int, maxC1: Int, minC2: Int, maxC2: Int): HGrids2 = minC2 match
  { case m2 if m2 >= minC1 & m2 <= maxC1 => excep("Overlapping grids")
    case _ if maxC2 >= minC1 & maxC2 <= maxC1 => excep("Overlapping grids")
    case _ if (maxC1 + minC2).div4Rem0 => excep("Grids do not align. (maxC1 + minC2).div4 == 0")
    case _ => new HGrids2(minR, maxR, minC1, maxC1, minC2, maxC2)
  }
}