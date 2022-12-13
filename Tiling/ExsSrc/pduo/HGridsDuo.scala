/* Copyright 2018-22 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package prid; package phex; package pduo
import geom._

/** This class may be removed. Its for the development of [[HGridSys]]. So just 2 regular grids side by side, to make an easy start on the general problem. */
final class HGridsDuo(val minCenR: Int, val maxCenR: Int, val minC1: Int, val maxC1: Int, val minC2: Int, maxC2: Int) extends HGridMulti
{ This2 =>
  type ManT = HGridMan

  type GridT = HGrid
  val grid1 = HGridReg(minCenR, maxCenR, minC1, maxC1)
  val grid2 = HGridReg(minCenR, maxCenR, minC2, maxC2)
  override def grids: RArr[HGrid] = RArr(grid1, grid2)

  val grid2OffsetC: Int = maxC1 - minC2 + 2

  val gridMan1: HGridMan = new GridMan1(grid1, grid2OffsetC)
  val gridMan2: HGridMan = new GridMan2(grid2, grid2OffsetC)
  override val gridMans: RArr[HGridMan] = RArr(gridMan1, gridMan2)

  override def unsafeGetMan(r: Int, c: Int): HGridMan = ife(c <= maxC1, gridMan1, gridMan2)

  val grid1Offset: Vec2 = 0 vv 0
  val grid2Offset: Vec2 = Vec2(grid2OffsetC, 0)

  override def top: Double = maxCenR * Sqrt3 + 4.0/Sqrt3
  override def bottom: Double = minCenR * Sqrt3 - 4.0/Sqrt3
  override def left: Double = grid1.left
  override def right: Double = grid1.right + grid2.width - 2

  override def flatHCoordToPt2(hCoord: HCoord): Pt2 = hCoord.c match
  { case c if c >= (grid1.leftCenC - 2) & c <= (grid1.rightCenC + 4) => grid1.flatHCoordToPt2(hCoord)
    case c if c >= (grid2.leftCenC - 4) & c <= (grid2.rightCenC + 2) => grid2.flatHCoordToPt2(hCoord) + grid2Offset
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

object HGridsDuo
{
  def apply(minR: Int, maxR: Int, minC1: Int, maxC1: Int, minC2: Int, maxC2: Int): HGridsDuo = minC2 match
  { case m2 if m2 >= minC1 & m2 <= maxC1 => excep("Overlapping grids")
    case _ if maxC2 >= minC1 & maxC2 <= maxC1 => excep("Overlapping grids")
    case _ if (maxC1 + minC2).div4Rem0 => excep("Grids do not align. (maxC1 + minC2).div4 == 0")
    case _ => new HGridsDuo(minR, maxR, minC1, maxC1, minC2, maxC2)
  }
}