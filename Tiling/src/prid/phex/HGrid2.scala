/* Copyright 2018-22 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package prid; package phex
import geom._

/** This class may be removed. Its ofr the development of [[HGrider]]. So just 2 regular grids side by side, to make an easy start on the general problem. */
final class HGrid2(val minCenR: Int, val maxCenR: Int, val minC1: Int, val maxC1: Int, val minC2: Int, maxC2: Int) extends HGridMultiFlat
{
  val grid1 = HGridReg(minCenR, maxCenR, minC1, maxC1)
  val grid2 = HGridReg(minCenR, maxCenR, minC2, maxC2)

  override val grids: Arr[HGrid] = Arr(grid1, grid2)
  val grid1Offset: Vec2 = 0 vv 0
  val grid2Offset: Vec2 = Vec2(0, grid1.right - grid2.left - 2)

  override def gridsOffsets: Vec2s = Vec2s(grid1Offset, grid2Offset)

  override def top: Double = maxCenR * Sqrt3 + 4.0/Sqrt3
  override def bottom: Double = minCenR * Sqrt3 - 4.0/Sqrt3
  override def left: Double = grid1.left
  override def right: Double = grid1.right + grid2.width - 2
  override def hCenExists(r: Int, c: Int): Boolean = grid1.hCenExists(r, c) | grid2.hCenExists(r, c)
  
  override def arrIndex(r: Int, c: Int): Int = gridsHCenFold(r, c, grid1.arrIndex(r, c), grid1.numTiles + grid2.arrIndex(r, c))

  def gridsHCenFold[A](hCen: HCen, if1: => A, if2: A): A = gridsHCenFold(hCen.r, hCen.c, if1, if2)

  def gridsHCenFold[A](r: Int, c: Int, if1: => A, if2: A): A = (r, c) match
  { case (r, c) if grid1.hCenExists(r, c) => if1
    case (r, c) if grid2.hCenExists(r, c) => if2
    case (r, c) => excep(s"$r, $c not valid coordinates for this grid.")
  }

  def gridNumFold[A](gridNum: Int, if1: => A, if2: A): A = gridNum match
  { case 0 => if1
    case 1 => if2
  }

  override def adjTilesOfTile(tile: HCen): HCens = gridsHCenFold(tile, grid1.adjTilesOfTile(tile), grid2.adjTilesOfTile(tile))

  override def gridSides(gridNum: Int): HSides =
    gridNumFold(gridNum, grid1.sides,grid2.sides.filterNot(hs => hs.c == grid2.leftCenCol | hs.c == (grid2.leftCenCol + 2)))

  override def gridNumSides(gridNum: Int): Int = gridNumFold(gridNum, grid1.numSides, grid2.numSides - grid2.numTileRows * 2)
}

object HGrid2
{
  def apply(minR: Int, maxR: Int, minC1: Int, maxC1: Int, minC2: Int, maxC2: Int): HGrid2 = minC2 match
  { case m2 if m2 >= minC1 & m2 <= maxC1 => excep("Overlapping grids")
    case _ if maxC2 >= minC1 & maxC2 <= maxC1 => excep("Overlapping grids")
    case _ if (maxC1 + minC2).div4Rem0 => excep("Grids do not align. (maxC1 + minC2).div4 == 0")
    case _ => new HGrid2(minR, maxR, minC1, maxC1, minC2, maxC2)
  }
}