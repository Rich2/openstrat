/* Copyright 2018-22 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package egrid
import geom._, prid._, phex._

trait EGridMainMan extends EGridMan
{ def seqInd: Int
  final override lazy val grid: EGridWarm = sys.grids(seqInd)
  def sys: EGridWarmMulti
  final override def offset: Vec2 = Vec2(0, sys.cGridDelta * seqInd)
  final override def arrIndex: Int = grid.numTiles * seqInd

  override def sidesForeach(f: HSide => Unit): Unit = if(seqInd == sys.grids.length -1)
  { grid.bottomRowForeachSide(f)
    iToForeach(grid.bottomCenR, grid.topCenR){
      case r if r.isEven => iToForeach(grid.rowLeftCenC(r) - 2, grid.rowRightCenC(r) + 2, 4){c => f(HSide(r, c)) }
      case r => {
        val ls = grid.rowLeftCenC(r - 1).min(grid.rowLeftCenC(r + 1)) - 1
        val rs = grid.rowRightCenC(r - 1).max(grid.rowRightCenC(r + 1)) + 1
        iToForeach(ls, rs, 2)(c => f(HSide(r, c)))
      }
    }
    grid.topRowForeachSide(f)
  }
  else {
    grid.bottomRowForeachSide(f)
    iToForeach(grid.bottomCenR, grid.topCenR){
      case r if r.isEven => iToForeach(grid.rowLeftCenC(r) - 2, grid.rowRightCenC(r) -2, 4){c => f(HSide(r, c)) }
      case r => {
        val ls = grid.rowLeftCenC(r - 1).min(grid.rowLeftCenC(r + 1)) - 1
        val rs = grid.rowRightCenC(r - 1).min(grid.rowRightCenC(r + 1)) + 1
        iToForeach(ls, rs, 2)(c => f(HSide(r, c)))
      }
    }
    grid.topRowForeachSide(f)
  }

  override def outerSidesForeach(f: HSide => Unit): Unit = seqInd match
  {
    case 0 =>
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

    case n if n < sys.grids.length - 1 =>
    { if(grid.rowNumTiles(grid.bottomCenR) > 0) iToForeach(grid.rowLeftCenC(grid.bottomCenR) - 1, grid.rowRightCenC(grid.bottomCenR) + 1, 2)(c => f(HSide(grid.bottomSideR, c)))
      if(grid.rowNumTiles(grid.topCenR) > 0) iToForeach(grid.rowLeftCenC(grid.topCenR) - 1, grid.rowRightCenC(grid.topCenR) + 1, 2)(c => f(HSide(grid.topSideR, c)))
    }

    case _ =>
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
  }

  override def innerRowForeachInnerSide(r: Int)(f: HSide => Unit): Unit = if (seqInd == 0) grid.innerRowForeachInnerSide(r)(f)
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
}