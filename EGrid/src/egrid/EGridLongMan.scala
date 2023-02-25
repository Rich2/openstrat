/* Copyright 2018-23 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package egrid
import geom._, prid._, phex._

/** Manages an [[EGridLong]]. */
final case class EGridLongMan(thisInd: Int, sys: EGridLongMulti) extends EGridMan
{ final override def grid: EGridLong = sys.grids(thisInd)

  override def adjTilesOfTile(origR: Int, origC: Int): HCenArr = grid.adjTilesOfTile(origR, origC)
  //override def adjTilesOfTile(origin: HCen): HCenArr = grid.adjTilesOfTile(origin)

  /** This manages the left or west most grid. System does not cover all longitudes. */
  def isLeftMan: Boolean = thisInd == 0 & sys.grids.length != 12

  /** This manages the right or east most grid. System does not cover all longitudes. */
  def isRightMan: Boolean = thisInd == sys.grids.length - 1 & sys.grids.length != 12

  /** The grid to the left or west of the grid this manages. */
  def ltGrid: EGridLong = thisInd match
  { case 0 if sys.grids.length != 12 =>excep("No left grid")
    case 0 => sys.grids(11)
    case n => sys.grids(n - 1)
  }

  /** The grid to the right or east of the grid this manages. */
  def rtGrid: EGridLong = thisInd match
  { case _ if isRightMan => excep("No right grid")
    case 12 => sys.grids(0)
    case n => sys.grids(n + 1)
  }


  final override def offset: Vec2 = Vec2((sys.gridsXSpacing - sys.hcDelta) * thisInd, 0)

  final override def indexStart: Int = grid.numTiles * thisInd

  override def sidesForeach(f: HSide => Unit): Unit = iToForeach(grid.bottomCenR - 1, grid.topCenR + 1)(rowSidesForeach(_)(f))

  override def hCenExists(r: Int, c: Int): Boolean = None match
  { case _ if r > grid.topCenR => false
    case _ if r < grid.bottomCenR => false
    case _ if isLeftMan & c < grid.leftCenC => false
    case _ if isRightMan & c > grid.rightCenC => false
    case _ => true
  }

  def rowSidesForeach(r: Int)(f: HSide => Unit): Unit = r match
  { case r if r == grid.topSideR | r == grid.bottomSideR => grid.rowForeachSide(r)(f)

    case r if r.isEven & (thisInd == sys.grids.length - 1) & sys.grids.length != 12 =>
      iToForeach(grid.rowLeftSideC(r), grid.rowRightCenC(r) + 2, 4){c => f(HSide(r, c)) }

    case r if r.isEven => iToForeach(grid.rowLeftSideC(r), grid.rowRightCenC(r) - 2, 4){c => f(HSide(r, c)) }

    case r if isRightMan =>
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

  override def outerSidesForeach(f: HSide => Unit): Unit = None match
  {
    case _ if isLeftMan =>
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

    case _ if isRightMan =>
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
    if (isLeftMan) grid.innerRowForeachInnerSide(r)(f)
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

  override def sideArrIndex(r: Int, c : Int): Int =
    sideRowIndexArray(r - grid.bottomSideR) + ife(r.isEven, (c - grid.rowLeftSideC(r)) / 4,(c - grid.rowLeftSideC(r)) / 2)

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

  def sideTileLtFind(hSide: HSide): Option[HCen] =
  { val hCen1 = hSide.tileLtReg
    if (grid.hCenExists(hCen1)) Some(hCen1)
    else hSide match
    { case _ if isLeftMan => None
      case HSideA(r, c) if r <=ltGrid.bottomSideR => None
      case HSideA(r, _) if ltGrid.rowRightCenC(r - 1) == ltGrid.rowRightCenC(r + 1) + 2 => Some(HCen(r - 1, ltGrid.rowRightCenC(r - 1)))
      case HSideA(r, _) => Some(HCen(r + 1, ltGrid.rowRightCenC(r + 1)))
      case HSideB(r, _) => Some(HCen(r, ltGrid.rowRightCenC(r)))
      case HSideC(r, _) if r >= ltGrid.topSideR => None
      case HSideC(r, _) if ltGrid.rowRightCenC(r + 1) == ltGrid.rowRightCenC(r - 1) + 2 => Some(HCen(r + 1, ltGrid.rowRightCenC(r + 1)))
      case HSideC(r, _) => Some(HCen(r - 1, ltGrid.rowRightCenC(r - 1)))
    }
  }

  def sideTileRtFind(hSide: HSide): Option[HCen] =
  { val hCen1 = hSide.tileRtReg
    if (grid.hCenExists(hCen1)) Some(hCen1)
    else hSide match
    { case _ if isRightMan => None
      case HSideA(r, c) if r >= rtGrid.topSideR => None
      case HSideA(r, _) if rtGrid.rowLeftCenC(r + 1) == rtGrid.rowLeftCenC(r - 1) - 2 => Some(HCen(r + 1, rtGrid.rowLeftCenC(r + 1)))
      case HSideA(r, _) => Some(HCen(r - 1, rtGrid.rowLeftCenC(r - 1)))
      case HSideB(r, _) => Some(HCen(r, rtGrid.rowLeftCenC(r)))
      case HSideC(r, _) if r <= rtGrid.bottomSideR => None
      case HSideC(r, _) if rtGrid.rowLeftCenC(r - 1) == rtGrid.rowLeftCenC(r + 1) - 2 => Some(HCen(r - 1, rtGrid.rowLeftCenC(r - 1)))
      case HSideC(r, _) => Some(HCen(r + 1, rtGrid.rowLeftCenC(r + 1)))
    }
  }

  def sideTileLtAndVertFind(hSide: HSide): Option[(HCen, Int)] =
  { val hCen1 = hSide.tileLtReg
    if (grid.hCenExists(hCen1)) Some(hSide.tileLtAndVert)
    else hSide match
    { case HSideA(r, c) if r <= ltGrid.bottomSideR => None
      case HSideA(r, _) if ltGrid.rowRightCenC(r - 1) == ltGrid.rowRightCenC(r + 1) + 2 => Some((HCen(r - 1, ltGrid.rowRightCenC(r - 1)), 0))
      case HSideA(r, _) => Some((HCen(r + 1, ltGrid.rowRightCenC(r + 1)), 3))
      case HSideB(r, _) => Some((HCen(r, ltGrid.rowRightCenC(r)), 1))
      case HSideC(r, _) if r >= ltGrid.topSideR => None
      case HSideC(r, _) if ltGrid.rowRightCenC(r + 1) == ltGrid.rowRightCenC(r - 1) + 2 => Some((HCen(r + 1, ltGrid.rowRightCenC(r + 1)), 2))
      case HSideC(r, _) => Some((HCen(r - 1, ltGrid.rowRightCenC(r - 1)), 0))
    }
  }

  /** Vert nums incorrect. */
  def sideTileRtAndVertFind(hSide: HSide): Option[(HCen, Int)] =
  { val hCen2 = hSide.tileRtReg
    if (grid.hCenExists(hCen2)) Some(hSide.tileRtAndVert)
    else hSide match
    { case HSideA(r, c) if r >= rtGrid.topSideR => None
      case HSideA(r, _) if rtGrid.rowLeftCenC(r + 1) == rtGrid.rowRightCenC(r - 1) - 2 => Some((HCen(r + 1, rtGrid.rowRightCenC(r + 1)), 0))
      case HSideA(r, _) => Some((HCen(r - 1, rtGrid.rowLeftCenC(r - 1)), 3))
      case HSideB(r, _) => Some((HCen(r, rtGrid.rowLeftCenC(r)), 1))
      case HSideC(r, _) if r <= rtGrid.bottomSideR => None
      case HSideC(r, _) if rtGrid.rowLeftCenC(r - 1) == rtGrid.rowRightCenC(r + 1) - 2 => Some((HCen(r - 1, rtGrid.rowRightCenC(r - 1)), 2))
      case HSideC(r, _) => Some((HCen(r + 1, rtGrid.rowLeftCenC(r + 1)), 0))
    }
  }

  override def sideTileLtAndVertUnsafe(hSide: HSide): (HCen, Int) =
  { val hCen1 = hSide.tileLtReg
    if (grid.hCenExists(hCen1)) hSide.tileLtAndVert
    else hSide match
    { case HSideA(r, c) if r <= ltGrid.bottomSideR => excep("HCen below bottom.")
      case HSideA(r, _) if ltGrid.rowRightCenC(r - 1) == ltGrid.rowRightCenC(r + 1) + 2 => (HCen(r - 1, ltGrid.rowRightCenC(r - 1)), 0)
      case HSideA(r, _) => (HCen(r + 1, ltGrid.rowRightCenC(r + 1)), 3)
      case HSideB(r, _) => (HCen(r, ltGrid.rowRightCenC(r)), 1)
      case HSideC(r, _) if r >= ltGrid.topSideR => excep("HCen above top.")
      case HSideC(r, _) if ltGrid.rowRightCenC(r + 1) == ltGrid.rowRightCenC(r - 1) + 2 => (HCen(r + 1, ltGrid.rowRightCenC(r + 1)), 2)
      case HSideC(r, _) => ((HCen(r - 1, ltGrid.rowRightCenC(r - 1)), 0))
    }
  }

  override def sideTileLtUnsafe(hSide: HSide): HCen =
  { val hCen1 = hSide.tileLtReg
    if (grid.hCenExists(hCen1)) hCen1
    else hSide match
    { case HSideA(r, c) if r <= ltGrid.bottomSideR => excep(s"Bottom, $r, $c, returning ${hSide.tileRtReg}")
      case HSideA(r, _) if ltGrid.rowRightCenC(r - 1) == ltGrid.rowRightCenC(r + 1) + 2 =>  HCen(r - 1, ltGrid.rowRightCenC(r - 1))
      case HSideA(r, _) => HCen(r + 1, ltGrid.rowRightCenC(r + 1))
      case HSideB(r, _) => HCen(r, ltGrid.rowRightCenC(r))
      case HSideC(r, _) if r >= ltGrid.topSideR => { deb("Top"); hSide.tileRtReg }
      case HSideC(r, _) if ltGrid.rowRightCenC(r + 1) == ltGrid.rowRightCenC(r - 1) + 2 => HCen(r + 1, ltGrid.rowRightCenC(r + 1))
      case HSideC(r, _) => HCen(r - 1, ltGrid.rowRightCenC(r - 1))
    }
  }

  override def sideTileRtUnsafe(hSide: HSide): HCen =
  { val hCen1 = hSide.tileRtReg
    if (grid.hCenExists(hCen1)) hCen1
    else hSide match {
      case HSideA(r, c) if r >= rtGrid.topSideR => {
        excep(s"Top, $r, $c, returning ${hSide.tileLtReg}"); hSide.tileLtReg
      }
      case HSideA(r, _) if rtGrid.rowLeftCenC(r + 1) == rtGrid.rowLeftCenC(r - 1) - 2 => HCen(r + 1, ltGrid.rowLeftCenC(r + 1))
      case HSideA(r, _) => HCen(r - 1, rtGrid.rowLeftCenC(r - 1))
      case HSideB(r, _) => HCen(r, rtGrid.rowLeftCenC(r))
      case HSideC(r, _) if r <= rtGrid.bottomSideR => {
        deb("Bottom"); hSide.tileLtReg
      }
      case HSideC(r, _) if rtGrid.rowLeftCenC(r - 1) == rtGrid.rowLeftCenC(r + 1) - 2 => HCen(r - 1, ltGrid.rowRightCenC(r - 1))
      case HSideC(r, _) => HCen(r + 1, rtGrid.rowLeftCenC(r + 1))
    }
  }

  override def findStepEnd(startHC: HCen, step: HStep): Option[HCen] =
  { val r0 = startHC.r
    val tr = r0 + step.tr
    val tc = startHC.c + step.tc
    def std: HCen = startHC.stepToUnsafe(step)
    step match
    { case HexUR if tr > grid.topCenR => None
      case HexUR if tc <= grid.rowRightCenC(tr) => Some(std)
      case HexRt if isRightMan => None
      case HexUR =>
      { val c1 = rtGrid.rowLeftCenC(tr)
        val c0 = rtGrid.rowLeftCenC(r0)
        val hc = ife(c1 < c0, HCen(tr, c1), HCen(r0, c0))
        Some(hc)
      }

      case HexRt if tc <= grid.rowRightCenC(r0) => Some(std)
      case HexRt if isRightMan => None
      case HexRt => Some(HCen(r0, rtGrid.rowLeftCenC(r0)))

      case HexDR if tr < grid.bottomCenR => None
      case HexDR if tc <= grid.rowRightCenC(tr) => Some(std)
      case HexDR if isRightMan => None
      case HexDR => {
        val c1 = rtGrid.rowLeftCenC(tr)
        val c0 = rtGrid.rowLeftCenC(r0)
        val hc = ife(c1 < c0, HCen(tr, c1), HCen(r0, c0))
        Some(hc)
      }

      case HexDL if tr < grid.bottomCenR => None
      case HexDL if tc >= grid.rowLeftCenC(tr) => Some(std)
      case HexDL if isLeftMan => None
      case HexDL => {
        val c1 = ltGrid.rowRightCenC(tr)
        val c0 = ltGrid.rowRightCenC(r0)
        val hc = ife(c1 > c0, HCen(tr, c1), HCen(r0, c0))
        Some(hc)
      }

      case HexLt if tc >= grid.rowLeftCenC(r0) => Some(std)
      case HexLt if isLeftMan => None
      case HexLt => Some(HCen(r0, rtGrid.rowLeftCenC(r0)))

      case HexUL if tr > grid.topCenR => None
      case HexUL if tc >= grid.rowLeftCenC(tr) => Some(std)
      case HexUL if isLeftMan => None
      case HexUL =>
      { val c1 = ltGrid.rowRightCenC(tr)
        val c0 = ltGrid.rowRightCenC(r0)
        val hc = ife(c1 > c0, HCen(tr, c1), HCen(r0, c0))
        Some(hc)
      }
    }
  }

  /** This method is not yet fully correct. */
  override def vertToCoordFind(r: Int, c: Int, dirn: HVDirn): Option[HCoord] =
  { val hv1 = HVert(r, c)
    val hc = hv1.dirnTo(dirn)
    val vUp = hv1.hexIsDown

    dirn match
    { case _ if grid.hCoordExists(hc) => Some(hc)
      case HVUp | HVUR | HVUL if r == grid.topSideR => None
      case HVDR | HVDn | HVDL if r == grid.bottomSideR => None
      case HVUR | HVDR if isRightMan => None
      case HVDL | HVUL if isLeftMan => None

      case HVUp if (c < grid.rowLeftCenC(r + 1 )) & vUp => Some(HVertLow(r + 2, ltGrid.rowRightCenC(r + 1) + 2))
      case HVUp if c < grid.rowLeftCenC(r + 1) => {
        val hc1 = ltGrid.rowRightCenC(r + 1)
        val hc0 = ltGrid.rowRightCenC(r - 1)
        val value = ife(hc1 > hc0, HCen(r + 1, hc1), HVertHigh(r, hc0))
        Some(value)
      }

      case HVUp if vUp => Some(HVertLow(r + 2, rtGrid.rowLeftCenC(r + 1) - 2))
      case HVUp =>
      { val hc1 = rtGrid.rowLeftCenC(r + 1)
        val hc0 = rtGrid.rowLeftCenC(r - 1)
        val value = ife(hc1 < hc0, HCen(r + 1, hc1), HVertHigh(r, hc0))
        Some(value)
      }

      case HVUR if vUp => Some(HCen(r + 1, rtGrid.rowLeftCenC(r + 1)))
      case HVUR => Some(HVertHigh(r, rtGrid.rowLeftCenC(r - 1)))

      case HVDR if vUp => Some(HVertLow(r, rtGrid.rowLeftCenC(r - 1) - 2))
      case HVDR => Some(HCen(r - 1, rtGrid.rowLeftCenC(r - 1)))

      case HVDn if (c < grid.rowLeftCenC(r - 1 )) & vUp => Some(HCen(r - 1, ltGrid.rowRightCenC(r - 1)))
      case HVDn if c < grid.rowLeftCenC(r - 1) => Some(HVertHigh(r - 2, ltGrid.rowRightCenC(r - 1) - 2))

      case HVDn if vUp =>
      { val hc1 = rtGrid.rowLeftCenC(r - 1)
        val hc0 = rtGrid.rowLeftCenC(r + 1)
        val value = ife(hc1 < hc0, HCen(r - 1, hc1), HVertHigh(r, hc0))
        Some(value)
      }

      case HVDn => Some(HVertHigh(r - 2, rtGrid.rowLeftCenC(r - 1) - 2))

      case HVDL if vUp => Some(HVertLow(r, ltGrid.rowRightCenC(r - 1) + 2))
      case HVDL => Some(HCen(r - 1, ltGrid.rowRightCenC(r - 1)))

      case HVUL if vUp => Some(HCen(r + 1, ltGrid.rowRightCenC(r + 1)))
      case HVUL => Some(HVertHigh(r, ltGrid.rowRightCenC(r - 1)))
    }
  }
}