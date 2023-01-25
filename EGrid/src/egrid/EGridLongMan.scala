/* Copyright 2018-23 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package egrid
import geom._, prid._, phex._

/** Manages an [[EGridLong]]. */
case class EGridLongMan(thisInd: Int, sys: EGridLongMulti) extends EGridMan
{
  final override def grid: EGridLong = sys.grids(thisInd)

  final override def offset: Vec2 = Vec2((sys.gridsXSpacing - sys.hcDelta) * thisInd, 0)

  final override def indexStart: Int = grid.numTiles * thisInd

  override def sidesForeach(f: HSide => Unit): Unit = iToForeach(grid.bottomCenR - 1, grid.topCenR + 1)(rowSidesForeach(_)(f))


  def rowSidesForeach(r: Int)(f: HSide => Unit): Unit = r match
  { case r if r == grid.topSideR | r ==grid.bottomSideR => grid.rowForeachSide(r)(f)

    case r if r.isEven & (thisInd == sys.grids.length - 1) & sys.grids.length != 12 =>
      iToForeach(grid.rowLeftSideC(r), grid.rowRightCenC(r) + 2, 4){c => f(HSide(r, c)) }

    case r if r.isEven => iToForeach(grid.rowLeftSideC(r), grid.rowRightCenC(r) - 2, 4){c => f(HSide(r, c)) }

    case r  if (thisInd == sys.grids.length - 1) & sys.grids.length != 12 =>
    { val ls: Int = grid.rowLeftSideC(r)
      val rs: Int = grid.rowRightCenC(r - 1).max(grid.rowRightCenC(r + 1)) + 1
      iToForeach(ls, rs, 2)(c => f(HSide(r, c)))
    }

    case r =>
    { val ls = grid.rowLeftCenC(r - 1).min(grid.rowLeftCenC(r + 1)) - 1
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

  override def innerRowInnerSidesForeach(r: Int)(f: HSide => Unit): Unit =
    if (thisInd == 0 & sys.grids.length != 12) grid.innerRowForeachInnerSide(r)(f)
    else r match
    {
      case r if r.isEven => iToForeach(grid.rowLeftCenC(r) - 2, grid.rowRightCenC(r) - 2, 4){ c => f(HSide(r, c)) }
      case r if r == grid.bottomSideR =>
      case r if r == grid.topSideR =>

      case r =>
      { val start = grid.rowLeftCenC(r - 1).min(grid.rowLeftCenC(r + 1)) - 1
        val end = grid.rowRightCenC(r - 1).min(grid.rowRightCenC(r + 1)) + 1
        iToForeach(start, end, 2){ c => f(HSide(r, c)) }
      }
  }

  override def sideArrIndex(r: Int, c : Int): Int = sideRowIndexArray(r - grid.bottomSideR) + ife(r.isEven, (c - grid.rowLeftSideC(r)) / 4,(c - grid.rowLeftSideC(r)) / 2)

  final override def outSteps(r: Int, c: Int): HStepCenArr = HStepCenArr()

  /** Array of indexes for Side data Arrs giving the index value for the start of each side row. */
  lazy val sideRowIndexArray: Array[Int] =
  { val array = new Array[Int](grid.numOfSideRows)
    array(0) = 0
    var count = 0
    grid.bottomRowForeachSide{_ => count += 1}
    grid.innerSideRowsForeach{ r =>
      array(r - grid.bottomSideR) = count
      rowSidesForeach(r){_ => count += 1}
    }
    array(grid.topSideR - grid.bottomSideR) = count
    array
  }

  /** Returns a c of 0, if the row doesn't exist. */
  override def sideTile1(hSide: HSide): HCen =
  { val hCen1 = hSide.tile1Reg
    if (grid.hCenExists(hCen1)) hCen1
    else {
      val gi = ife(thisInd == 0, sys.numGrids - 2, thisInd - 1)
      val sc = hSide.c
      val cenR = hSide.r match
      { case sr if sr.isEven => sr
        case sr if (sr.div4Rem3 & sc.div4Rem1) | (sr.div4Rem1 & sc.div4Rem3) => sr + 1//These are up and to the left
        case sr => sr - 1//These are down and to the left.
      }

      val c = ife(cenR > grid.topCenR | cenR < grid.bottomCenR, 0, sys.grids(gi).rowRightCenC(cenR))
      HCen(cenR, c)
    }
  }

  override def sideTile2(hSide: HSide): HCen = grid.sideTile2(hSide)
}