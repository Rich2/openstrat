/* Copyright 2018-22 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package prid; package phex; package pduo
import geom._

class GridMan2(val grid: HGridReg, grid2OffsetC: Int) extends HGridMan
{
  def thisInd: Int = 1
  def sys: HGridsDuo = ???
  override val indexStart: Int = numTiles

  override def offset: Vec2 = Vec2(grid2OffsetC + 2, 0)

  def sidesForeach(f: HSide => Unit): Unit = grid.sidesForeach {
    case hs if hs.c == grid.leftSideC + 1 & hs.r == grid.topSideR => f(hs)
    case hs if hs.c == grid.leftSideC + 1 & hs.r == grid.bottomSideR => f(hs)
    case hs if hs.c <= grid.leftCenC =>
    case hs => f(hs)
  }

  override def linksForeach(f: HSide => Unit): Unit = grid.innerSideRowsForeach(r => innerRowForeachInnerSide(r)(f))

  def innerRowForeachInnerSide(r: Int)(f: HSide => Unit): Unit = r match
  { case r if r >= grid.topSideRow => excep(r.str + " is not an inner row.")
    case r if r <= grid.bottomSideR => excep(r.str + " is not an inner row.")
    case r if r.div4Rem2 => iToForeach(grid.leftrem2CenC - 2, grid.rightRem2CenC - 2, 4) { c => f(HSide(r, c)) }
    case r if r.div4Rem0 => iToForeach(grid.leftRem0CenC - 2, grid.rightRem0CenC - 2, 4) { c => f(HSide(r, c)) }
    case r => iToForeach(grid.leftCenC - 1, grid.rightCenC - 1, 2) { c => f(HSide(r, c)) }
  }

  override def outerSidesForeach(f: HSide => Unit): Unit =
  { grid.bottomRowForeachSide(f)
    iToForeach(grid.bottomCenR, grid.topCenR) {
      case r if r.isEven => f(HSide(r, grid.rowRightCenC(r) + 2))
      case r => f(HSide(r, grid.rightCenC + 1))
    }
    grid.topRowForeachSide(f)
  }

  override def outSteps(r: Int, c: Int): HStepCenArr = (r, c) match
  { case (r, c) if r == grid.topCenR & c == grid.leftCenC => HStepCenArr((HexLt, r, c + grid2OffsetC - 4), (HexDL, r - 2, c + grid2OffsetC - 2))
    case (r, c) if r == grid.topCenR & c == grid.leftCenC + 2 => HStepCenArr((HexLt, r, c + grid2OffsetC - 4))

    case (r, c) if r == grid.bottomCenR & c == grid.leftCenC => HStepCenArr((HStepUL, r + 2, c + grid2OffsetC - 2), (HexLt, r, c + grid2OffsetC - 4))
    case (r, c) if r == grid.bottomCenR & c == grid.leftCenC + 2 => HStepCenArr((HexLt, r, c + grid2OffsetC - 4))

    case (r, c) if c == grid.leftCenC =>
      HStepCenArr((HStepUL, r + 2, c + grid2OffsetC - 2), (HexLt, r, c + grid2OffsetC - 4), (HexDL, r - 2, c + grid2OffsetC - 2))
    case (r, c) if c == grid.leftCenC + 2 => HStepCenArr((HexLt, r, c + grid2OffsetC - 4))

    case _ => HStepCenArr()
  }
}