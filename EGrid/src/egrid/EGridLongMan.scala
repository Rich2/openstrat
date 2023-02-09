/* Copyright 2018-23 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package egrid
import geom._, prid._, phex._

/** Manages an [[EGridLong]]. */
final case class EGridLongMan(thisInd: Int, sys: EGridLongMulti) extends EGridMan
{
  final override def grid: EGridLong = sys.grids(thisInd)

  override def adjTilesOfTile(tile: HCen): HCenArr = ???
  final override def offset: Vec2 = Vec2((sys.gridsXSpacing - sys.hcDelta) * thisInd, 0)

  final override def indexStart: Int = grid.numTiles * thisInd

  override def sidesForeach(f: HSide => Unit): Unit = iToForeach(grid.bottomCenR - 1, grid.topCenR + 1)(rowSidesForeach(_)(f))

  override def hCenExists(r: Int, c: Int): Boolean = grid.hCenExists(r, c)

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

  def sideTile1Find(hSide: HSide): Option[HCen] = {
    val hCen1 = hSide.tile1Reg
    if (grid.hCenExists(hCen1)) Some(hCen1)
    else {
      val gridIndex = ife(thisInd == 0, sys.numGrids - 2, thisInd - 1)
      val gr = sys.grids(gridIndex)
      hSide match {
        case HSideA(r, c) if r <= gr.bottomSideR => None
        case HSideA(r, _) if gr.rowRightCenC(r - 1) == gr.rowRightCenC(r + 1) + 2 => Some(HCen(r - 1, gr.rowRightCenC(r - 1)))
        case HSideA(r, _) => Some(HCen(r + 1, gr.rowRightCenC(r + 1)))
        case HSideB(r, _) => Some(HCen(r, gr.rowRightCenC(r)))
        case HSideC(r, _) if r >= gr.topSideR => None
        case HSideC(r, _) if gr.rowRightCenC(r + 1) == gr.rowRightCenC(r - 1) + 2 => Some(HCen(r + 1, gr.rowRightCenC(r + 1)))
        case HSideC(r, _) => Some(HCen(r - 1, gr.rowRightCenC(r - 1)))
      }
    }
  }

  def sideTileLtAndVertFind(hSide: HSide): Option[(HCen, Int)] =
  { val hCen1 = hSide.tile1Reg
    if (grid.hCenExists(hCen1)) Some(hSide.tileLtAndVert)
    else {
      val gridIndex = ife(thisInd == 0, sys.numGrids - 2, thisInd - 1)
      val gr = sys.grids(gridIndex)
      hSide match {
        case HSideA(r, c) if r <= gr.bottomSideR => None
        case HSideA(r, _) if gr.rowRightCenC(r - 1) == gr.rowRightCenC(r + 1) + 2 => Some((HCen(r - 1, gr.rowRightCenC(r - 1)), 0))
        case HSideA(r, _) => Some((HCen(r + 1, gr.rowRightCenC(r + 1)), 3))
        case HSideB(r, _) => Some((HCen(r, gr.rowRightCenC(r)), 1))
        case HSideC(r, _) if r >= gr.topSideR => None
        case HSideC(r, _) if gr.rowRightCenC(r + 1) == gr.rowRightCenC(r - 1) + 2 => Some((HCen(r + 1, gr.rowRightCenC(r + 1)), 2))
        case HSideC(r, _) => Some((HCen(r - 1, gr.rowRightCenC(r - 1)), 0))
      }
    }
  }

  /** Vert nums incorrect. */
  def sideTileRtAndVertFind(hSide: HSide): Option[(HCen, Int)] =
  { val hCen2 = hSide.tile2Reg
    if (grid.hCenExists(hCen2)) Some(hSide.tile2AndVert)
    else {
      val gridIndex = ife(thisInd >= sys.numGrids - 1, 0, thisInd + 1)
      val gr = sys.grids(gridIndex)
      hSide match {
        case HSideA(r, c) if r >= gr.topSideR => None
        case HSideA(r, _) if gr.rowLeftCenC(r + 1) == gr.rowRightCenC(r - 1) - 2 => Some((HCen(r + 1, gr.rowRightCenC(r + 1)), 0))
        case HSideA(r, _) => Some((HCen(r - 1, gr.rowLeftCenC(r - 1)), 3))
        case HSideB(r, _) => Some((HCen(r, gr.rowLeftCenC(r)), 1))
        case HSideC(r, _) if r <= gr.bottomSideR => None
        case HSideC(r, _) if gr.rowLeftCenC(r - 1) == gr.rowRightCenC(r + 1) - 2 => Some((HCen(r - 1, gr.rowRightCenC(r - 1)), 2))
        case HSideC(r, _) => Some((HCen(r + 1, gr.rowLeftCenC(r + 1)), 0))
      }
    }
  }

  override def sideTileLtAndVertUnsafe(hSide: HSide): (HCen, Int) =
  { val hCen1 = hSide.tile1Reg
    if (grid.hCenExists(hCen1)) hSide.tileLtAndVert
    else
    { val gridIndex = ife(thisInd == 0, sys.numGrids - 2, thisInd - 1)
      val gr = sys.grids(gridIndex)
      hSide match
      { case HSideA(r, c) if r <= gr.bottomSideR => excep("HCen below bottom.")
        case HSideA(r, _) if gr.rowRightCenC(r - 1) == gr.rowRightCenC(r + 1) + 2 => (HCen(r - 1, gr.rowRightCenC(r - 1)), 0)
        case HSideA(r, _) => (HCen(r + 1, gr.rowRightCenC(r + 1)), 3)
        case HSideB(r, _) => (HCen(r, gr.rowRightCenC(r)), 1)
        case HSideC(r, _) if r >= gr.topSideR => excep("HCen above top.")
        case HSideC(r, _) if gr.rowRightCenC(r + 1) == gr.rowRightCenC(r - 1) + 2 => (HCen(r + 1, gr.rowRightCenC(r + 1)), 2)
        case HSideC(r, _) => ((HCen(r - 1, gr.rowRightCenC(r - 1)), 0))
      }
    }
  }

  override def sideTile1Unsafe(hSide: HSide): HCen =
  { val hCen1 = hSide.tile1Reg
    if (grid.hCenExists(hCen1)) hCen1
    else {
      val gridIndex = ife(thisInd == 0, sys.numGrids - 2, thisInd - 1)
      val gr = sys.grids(gridIndex)
      hSide match
      { case HSideA(r, c) if r <= gr.bottomSideR => {excep(s"Bottom, $r, $c, returning ${hSide.tile2Reg}"); hSide.tile2Reg }
        case HSideA(r, _) if gr.rowRightCenC(r - 1) == gr.rowRightCenC(r + 1) + 2 =>  HCen(r - 1, gr.rowRightCenC(r - 1))
        case HSideA(r, _) => HCen(r + 1, gr.rowRightCenC(r + 1))
        case HSideB(r, _) => HCen(r, gr.rowRightCenC(r))
        case HSideC(r, _) if r >= gr.topSideR => { deb("Top"); hSide.tile2Reg }
        case HSideC(r, _) if gr.rowRightCenC(r + 1) == gr.rowRightCenC(r - 1) + 2 => HCen(r + 1, gr.rowRightCenC(r + 1))
        case HSideC(r, _) => HCen(r - 1, gr.rowRightCenC(r - 1))
      }
    }
  }

  override def sideTile2Unsafe(hSide: HSide): HCen = grid.unsafeSideTile2(hSide)
}