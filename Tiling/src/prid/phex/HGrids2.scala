/* Copyright 2018-22 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package prid; package phex
import geom._

/** This class may be removed. Its for the development of [[HGrider]]. So just 2 regular grids side by side, to make an easy start on the general problem. */
final class HGrids2(val minCenR: Int, val maxCenR: Int, val minC1: Int, val maxC1: Int, val minC2: Int, maxC2: Int) extends HGridMultiFlat
{
  val grid1 = HGridReg(minCenR, maxCenR, minC1, maxC1)
  val grid2 = HGridReg(minCenR, maxCenR, minC2, maxC2)

  val gridMan1: HGridMan = new HGridMan(grid1){
    //override def sides: HSides = HSides()
  }

  val gridMan2: HGridMan = new HGridMan(grid2){
    override def sides: HSides = grid.sides.filter {
      case HSide(r, c) if c == grid.leftSideC + 1 & r == grid.topSideR => true
      case HSide(r, c) if c == grid.leftSideC + 1 & r == grid.bottomSideR => true
      case HSide(r, c) if c <= grid.leftCenC => false
      case _ => true
    }

    override def sideLines: LineSegs = sides.map(_.lineSeg).slateX(xGrid2Offset)
  }

  override val gridMans: Arr[HGridMan] = Arr(gridMan1, gridMan2)
  val grid1Offset: Vec2 = 0 vv 0
  val xGrid2Offset: Double = grid1.right - grid2.left - 2
  val grid2Offset: Vec2 = Vec2(xGrid2Offset, 0)

  override def gridsOffsets: Vec2s = Vec2s(grid1Offset, grid2Offset)

  override def top: Double = maxCenR * Sqrt3 + 4.0/Sqrt3
  override def bottom: Double = minCenR * Sqrt3 - 4.0/Sqrt3
  override def left: Double = grid1.left
  override def right: Double = grid1.right + grid2.width - 2
  override def hCenExists(r: Int, c: Int): Boolean = grid1.hCenExists(r, c) | grid2.hCenExists(r, c)

  override def hCoordToPt2(hCoord: HCoord): Pt2 = hCoord.c match
  { case c if c >= (grid1.leftCenC - 2) & c <= (grid1.rightCenC + 2) => grid1.hCoordToPt2(hCoord)
    case c if c >= (grid2.leftCenC - 2) & c <= (grid2.rightCenC + 2) => grid2.hCoordToPt2(hCoord) + grid2Offset
  }

  override def arrIndex(r: Int, c: Int): Int = unsafeGridsHCenFold(r, c, _.arrIndex(r, c), grid1.numTiles + _.arrIndex(r, c))

  def unsafeGridsHCenFold[A](hCen: HCen, if1: HGrid =>  A, if2: HGrid => A): A = unsafeGridsHCenFold(hCen.r, hCen.c, if1, if2)

  def unsafeGridsHCenFold[A](r: Int, c: Int, if1: HGrid => A, if2: HGrid => A): A = (r, c) match
  { case (r, c) if grid1.hCenExists(r, c) => if1(grid1)
    case (r, c) if grid2.hCenExists(r, c) => if2(grid2)
    case (r, c) => excep(s"$r, $c not valid coordinates for this grid.")
  }

  def gridNumFold[A](gridNum: Int, if1: => A, if2: A): A = gridNum match
  { case 0 => if1
    case 1 => if2
  }

  override def adjTilesOfTile(tile: HCen): HCens = unsafeGridsHCenFold(tile, _.adjTilesOfTile(tile), _.adjTilesOfTile(tile))

  override def findStep(startHC: HCen, endHC: HCen): OptRef[HStep] = unsafeGridsHCenFold(startHC, g1 => g1.findStep(startHC, endHC), g2 => {debvar((startHC, endHC)); g2.findStep(startHC, endHC)})

  override def gridNumSides(gridNum: Int): Int = gridNumFold(gridNum, grid1.numSides, grid2.numSides - grid2.numTileRows * 2)
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