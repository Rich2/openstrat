/* Copyright 2018-22 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package prid; package phex; package pduo
import geom._

class GridMan1(val sys: HGridsDuo, val grid: HGridReg) extends HGridMan
{ override def thisInd: Int = 0
  override val indexStart: Int = 0

  override def offset: Vec2 = Vec2(0, 0)

  override def sidesForeach(f: HSide => Unit): Unit = grid.sidesForeach(f)

  override def linksForeach(f: HSide => Unit): Unit = grid.linksForeach(f)

  override def outerSidesForeach(f: HSide => Unit): Unit =
  { grid.bottomRowForeachSide(f)
  iToForeach(grid.bottomCenR, grid.topCenR){
  case r if r.isEven => f(HSide(r, grid.rowLeftCenC(r) - 2))
  case r => f(HSide(r, grid.leftCenC - 1))
  }
  grid.topRowForeachSide(f)
  }

  override def outSteps(r: Int, c: Int): HStepCenArr = (r, c) match
  { case (r, c) if r == sys.maxCenR & c == sys.maxC1 => HStepCenArr((HexRt, r, c - sys.grid2OffsetC + 4), (HexDR, r - 2, c - sys.grid2OffsetC + 2))
  case (r, c) if r == sys.maxCenR & c == sys.maxC1 - 2 => HStepCenArr((HexRt, r, c - sys.grid2OffsetC + 4))

  case (r, c) if r == sys.minCenR & c == sys.maxC1 => HStepCenArr((HexUR, r + 2, c - sys.grid2OffsetC + 2), (HexRt, r, c - sys.grid2OffsetC + 4))
  case (r, c) if r == sys.minCenR & c == sys.maxC1 - 2 => HStepCenArr((HexRt, r, c - sys.grid2OffsetC + 4))

  case (r, c) if c == sys.maxC1 =>
  HStepCenArr((HexUR, r + 2, c - sys.grid2OffsetC + 2), (HexRt, r, c - sys.grid2OffsetC + 4), (HexDR, r - 2, c - sys.grid2OffsetC + 2))

  case (r, c) if c == sys.maxC1 - 2 => HStepCenArr((HexRt, r, c - sys.grid2OffsetC + 4))
  case _ => HStepCenArr()
  }
}