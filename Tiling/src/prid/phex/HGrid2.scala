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
  
  override def arrIndex(r: Int, c: Int): Int = (r, c) match {
    case (r, c) if grid1.hCenExists(r, c) => grid1.arrIndex(r, c)
    case (r, c) if grid2.hCenExists(r, c) => grid1.numTiles + grid2.arrIndex(r, c)
    case (r, c) => excep(s"$r, $c not valid coordinates for this grid.")
  }

  override def adjTilesOfTile(tile: HCen): HCens = ???

  override def gridSides(gridNum: Int): HSides = ???
}

object HGrid2
{
  def apply(minR: Int, maxR: Int, minC1: Int, maxC1: Int, minC2: Int, maxC2: Int): HGrid2 = minC2 match
  { case m2 if m2 >= minC1 & m2 <= maxC1 => excep("Overlapping grids")
    case _ if maxC2 >= minC1 & maxC2 <= maxC1 => excep("Overlapping grids")
    case _ => new HGrid2(minR, maxR, minC1, maxC1, minC2, maxC2)
  }
}