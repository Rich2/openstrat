/* Copyright 2018-20 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat
package prid

trait TGridSimple
{
  /** Number of rows of tiles. This will be different to the number of rows of sides and and will be different to the number of rows of vertices for
   * HexGrids. */
  def numOfTileRows: Int

  def width: Double = ???
  def height: Double = ???

  /** The number of Rows of Sides. */
  @inline final def numOfSideRows: Int = numOfTileRows * 2 + 1

  /** The number of Rows of vertices. */
  @inline final def numOfVertRows: Int = numOfTileRows + 1

  /** The total number of Tiles in the tile Grid. */
  def numOfTiles: Int

  def xRatio: Double

  def fullDisplayScale(dispWidth: Double, dispHeight: Double, padding: Double = 20): Double =
  {
    def adj(inp : Double): Double =inp match
    { case n if n > 1000 => inp - padding
      case n if n > 500 => inp - padding * inp / 1000.0
      case n if n > 10 => n
      case _ => 10
    }
    (adj(dispWidth) / adj(width).max(1)).min(adj(dispHeight) / height.max(1))
  }
}
