/* Copyright 2018-22 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package egrid
import geom._, prid._, phex._

case class EGridWarmMan(thisInd: Int, sys: EGridWarmMulti) extends EGridMan
{
  final override lazy val grid: EGridWarm = sys.grids(thisInd)

  final override def offset: Vec2 = Vec2(0, sys.cGridDelta * thisInd)
  final override def arrIndex: Int = grid.numTiles * thisInd

  override def sidesForeach(f: HSide => Unit): Unit =
  { grid.bottomRowForeachSide(f)
    iToForeach(grid.bottomCenR, grid.topCenR)(innerSideRowForeachSide(_)(f))
    grid.topRowForeachSide(f)
  }

  def innerSideRowForeachSide(r: Int)(f: HSide => Unit): Unit = if(thisInd == sys.grids.length -1 & sys.grids.length != 12) r match {
    case r if r.isEven => iToForeach(grid.rowLeftCenC(r) - 2, grid.rowRightCenC(r) + 2, 4){c => f(HSide(r, c)) }
    case r => {
      val ls: Int = grid.rowLeftCenC(r - 1).min(grid.rowLeftCenC(r + 1)) - 1
      val rs: Int = grid.rowRightCenC(r - 1).max(grid.rowRightCenC(r + 1)) + 1
      iToForeach(ls, rs, 2)(c => f(HSide(r, c)))
    }
  }
  else r match {
    case r if r.isEven => iToForeach(grid.rowLeftCenC(r) - 2, grid.rowRightCenC(r) -2, 4){c => f(HSide(r, c)) }
    case r => {
      val ls = grid.rowLeftCenC(r - 1).min(grid.rowLeftCenC(r + 1)) - 1
      val rs = grid.rowRightCenC(r - 1).min(grid.rowRightCenC(r + 1)) + 1
      iToForeach(ls, rs, 2)(c => f(HSide(r, c)))
    }
  }

  override def outerSidesForeach(f: HSide => Unit): Unit = thisInd match
  {
    case 0 if sys.grids.length != 12 =>
    { if(grid.rowNumTiles(grid.bottomCenR) > 0) iToForeach(grid.rowLeftCenC(grid.bottomCenR) - 1, grid.rowRightCenC(grid.bottomCenR) + 1, 2)(c => f(HSide(grid.bottomSideR, c)))
      iToForeach(grid.bottomCenR, grid.topCenR){r => r match{
        case r if r.isEven => f(HSide(r, grid.rowLeftCenC(r) -2))
        case r =>
        { val bLeft = grid.rowLeftCenC(r - 1)
          val uLeft = grid.rowLeftCenC(r + 1)
          val leftStart = bLeft.min(uLeft) - 1
          val leftEnd = bLeft.max(uLeft) - 3
          iToForeach(leftStart, leftEnd, 2){c => f(HSide(r, c)) }
        }
      }}
      if(grid.rowNumTiles(grid.topCenR) > 0) iToForeach(grid.rowLeftCenC(grid.topCenR) - 1, grid.rowRightCenC(grid.topCenR) + 1, 2)(c => f(HSide(grid.topSideR, c)))
    }

    case n if n == sys.grids.length - 1 & sys.grids.length != 12 =>
    { if(grid.rowNumTiles(grid.bottomCenR) > 0) iToForeach(grid.rowLeftCenC(grid.bottomCenR) - 1, grid.rowRightCenC(grid.bottomCenR) + 1, 2)(c => f(HSide(grid.bottomSideR, c)))
      iToForeach(grid.bottomCenR, grid.topCenR){r => r match{
        case r if r.isEven => f(HSide(r, grid.rowRightCenC(r) + 2))
        case r =>
        { val bRight = grid.rowRightCenC(r - 1)
          val uRight = grid.rowRightCenC(r + 1)
          val rightEnd = bRight.max(uRight) + 1
          val rightStart = bRight.min(uRight) + 3
          iToForeach(rightStart, rightEnd, 2){c => f(HSide(r, c)) }
        }
      }}
      if(grid.rowNumTiles(grid.topCenR) > 0) iToForeach(grid.rowLeftCenC(grid.topCenR) - 1, grid.rowRightCenC(grid.topCenR) + 1, 2)(c => f(HSide(grid.topSideR, c)))
    }

    case _ =>
    { if(grid.rowNumTiles(grid.bottomCenR) > 0) iToForeach(grid.rowLeftCenC(grid.bottomCenR) - 1, grid.rowRightCenC(grid.bottomCenR) + 1, 2)(c => f(HSide(grid.bottomSideR, c)))
      if(grid.rowNumTiles(grid.topCenR) > 0) iToForeach(grid.rowLeftCenC(grid.topCenR) - 1, grid.rowRightCenC(grid.topCenR) + 1, 2)(c => f(HSide(grid.topSideR, c)))
    }
  }

  override def innerRowForeachInnerSide(r: Int)(f: HSide => Unit): Unit = if (thisInd == 0 & sys.grids.length != 12) grid.innerRowForeachInnerSide(r)(f)
  else r match
  {
    case r if r.isEven => iToForeach(grid.rowLeftCenC(r) - 2, grid.rowRightCenC(r) -2, 4){ c => f(HSide(r, c)) }
    case r if r == grid.bottomSideR =>
    case r if r == grid.topSideR =>

    case r =>
    { val start = grid.rowLeftCenC(r - 1).min(grid.rowLeftCenC(r + 1)) - 1
      val end = grid.rowRightCenC(r - 1).min(grid.rowRightCenC(r + 1)) + 1
      iToForeach(start, end, 2){ c => f(HSide(r, c)) }
    }
  }

  final override def outSteps(r: Int, c: Int): HStepCenArr = HStepCenArr()

  /** Array of indexes for Side data Arrs giving the index value for the start of each side row. */
  lazy val sideRowIndexArray: Array[Int] =
  { val array = new Array[Int](grid.numOfSideRows)
    array(0) = 0
    var count = 0
    grid.bottomRowForeachSide{_ => count += 1}
    grid.innerSideRowsForeach{ r =>
      innerSideRowForeachSide(r){_ => count += 1}
      array(r - grid.bottomSideR) = count
    }
    array
  }
}