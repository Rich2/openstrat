/* Copyright 2018-22 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package prid; package phex

/** This class may be removed. Its ofr the development of [[HGrider]]. So just 2 regular grids side by side, to make an easy start on the general problem. */
class HGrid2(val minCenR: Int, val maxCenR: Int, val minC1: Int, val maxC1: Int, val minC2: Int, maxC2: Int) extends HGridMultiFlat
{
  val grid1 = HGridReg(minCenR, maxCenR, minC1, maxC1)
  val grid2 = HGridReg(minCenR, maxCenR, minC2, maxC2)

  override val grids: Arr[HGrid] = Arr(grid1, grid2)
  final override def top: Double = maxCenR * Sqrt3 + 4.0/Sqrt3
  final override def bottom: Double = minCenR * Sqrt3 - 4.0/Sqrt3

  /** Boolean. True if the specified hex centre exists in this hex grid. */
  override def hCenExists(r: Int, c: Int): Boolean = grid1.hCenExists(r, c) | grid2.hCenExists(r, c)

  /** Gives the index into an Arr / Array of Tile data from its tile [[HCen]]. Use sideIndex and vertIndex methods to access Side and Vertex Arr /
   * Array data. */
  override def arrIndex(r: Int, c: Int): Int = (r, c) match {
    case (r, c) if grid1.hCenExists(r, c) => grid1.arrIndex(r, c)
    case (r, c) if grid2.hCenExists(r, c) => grid1.numTiles + grid2.arrIndex(r, c)
    case (r, c) => excep(s"$r, $c not valid coordinates for this grid.")
  }

  override def adjTilesOfTile(tile: HCen): HCens = ???
}

object HGrid2
{
  def apply(minR: Int, maxR: Int, minC1: Int, maxC1: Int, minC2: Int, maxC2: Int): HGrid2 = minC2 match {
    case m2 if m2 >= minC1 & m2 <= maxC1 => excep("Overlapping grids")
    case _ if maxC2 >= minC1 & maxC2 <= maxC1 => excep("Overlapping grids")
    case _ => new HGrid2(minR, maxR, minC1, maxC1, minC2, maxC2)
  }
}