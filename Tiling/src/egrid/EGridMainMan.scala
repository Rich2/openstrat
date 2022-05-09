/* Copyright 2018-22 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package egrid
import geom._, pglobe._, prid._, phex._

trait EGridMainMan extends EGridMan
{ override val grid: EGridMain
}

trait EGridMainManHead extends EGridMainMan
{ override val arrIndex: Int = 0
  override def offset: Vec2 = Vec2(0, 0)
  override def sidesForeach(f: HSide => Unit): Unit = grid.sidesForeach(f)
  override def innerSidesForeach(f: HSide => Unit): Unit = grid.innerSidesForeach(f)
  override def outerSidesForeach(f: HSide => Unit): Unit = //grid.outerSidesForeach(f)
  {
    if(grid.rowNumTiles(grid.bottomCenR) > 0) iToForeach(grid.rowLeftCenC(grid.bottomCenR) - 1, grid.rowRightCenC(grid.bottomCenR) + 1, 2)(c => f(HSide(grid.bottomSideR, c)))
    iToForeach(grid.bottomCenR, grid.topCenR){r => r match{
      case r if r.isEven => f(HSide(r, grid.rowLeftCenC(r) -2))
      case r => {
        val bLeft = grid.rowLeftCenC(r - 1)
        val uLeft = grid.rowLeftCenC(r + 1)
        val leftStart = bLeft.min(uLeft) - 1
        val leftEnd = bLeft.max(uLeft) - 3
        iToForeach(leftStart, leftEnd, 2){c => f(HSide(r, c)) }
        val bRight = grid.rowRightCenC(r - 1)
        val uRight = grid.rowRightCenC(r + 1)
        val rightEnd = bRight.max(uRight) + 1
        val rightStart = bRight.min(uRight) + 3
        iToForeach(rightStart, rightEnd, 2){c => f(HSide(r, c)) }
      }
    }}
    if(grid.rowNumTiles(grid.topCenR) > 0) iToForeach(grid.rowLeftCenC(grid.topCenR) - 1, grid.rowRightCenC(grid.topCenR) + 1, 2)(c => f(HSide(grid.topSideR, c)))
  }
}

trait EGridMainManLast extends EGridMainMan
{ override def sidesForeach(f: HSide => Unit): Unit = grid.sidesForeach(f)
  override def innerSidesForeach(f: HSide => Unit): Unit = grid.innerSidesForeach(f)
  override def outerSidesForeach(f: HSide => Unit): Unit =
  {
    if(grid.rowNumTiles(grid.bottomCenR) > 0) iToForeach(grid.rowLeftCenC(grid.bottomCenR) - 1, grid.rowRightCenC(grid.bottomCenR) + 1, 2)(c => f(HSide(grid.bottomSideR, c)))
    iToForeach(grid.bottomCenR, grid.topCenR){r => r match{
      case r if r.isEven => f(HSide(r, grid.rowRightCenC(r) + 2))
      case r => {
        val bLeft = grid.rowLeftCenC(r - 1)
        val uLeft = grid.rowLeftCenC(r + 1)
        val leftStart = bLeft.min(uLeft) - 1
        val leftEnd = bLeft.max(uLeft) - 3
        iToForeach(leftStart, leftEnd, 2){c => f(HSide(r, c)) }
        val bRight = grid.rowRightCenC(r - 1)
        val uRight = grid.rowRightCenC(r + 1)
        val rightEnd = bRight.max(uRight) + 1
        val rightStart = bRight.min(uRight) + 3
        iToForeach(rightStart, rightEnd, 2){c => f(HSide(r, c)) }
      }
    }}
    if(grid.rowNumTiles(grid.topCenR) > 0) iToForeach(grid.rowLeftCenC(grid.topCenR) - 1, grid.rowRightCenC(grid.topCenR) + 1, 2)(c => f(HSide(grid.topSideR, c)))
  }
}