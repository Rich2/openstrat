/* Copyright 2018-20 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat
package prid

/** A TileGrid is a description of an abstract TileGrid. It contains no data for the elements of any particular TileGrid. The Data for TileGrids is
 *  stored in flat arrays. The TileGrid gives the dimensions of a tileGrid. It has methods to interpret the data in flat Arrays created for that
 *  TileGrid specification. It has methods to map the elements of an Array to the the 2 dimensional geometry of the Tile Grid. On its own a TileGrid
 *  can produce the outlines of the grid, coordinates vector positions and other pure mathematical data. Combined with a simple function it can for
 *  example produce a Chess board. Combined with a 64 length array it can produce a Chess board position. For anything but the most simple games, you
 *  will probably want multiple arrays to describe the game state. The terrain for example may remain invariant, so the terrain data does not need to
 *  be reproduced with every move.
 *
 *  A TileGrid is for use cases where the proportions of the Grid predetermine the proportions of the visual representation, as opposed to a use case
 *  where the proportions of the enclosing space are a factor in determining the proportions of the grid. For example the various grid layouts of the
 *  Stars on the American flag. */
trait TGrid
{
  /** Number of rows of tiles. This will be different to the number of rows of sides and and will be different to the number of rows of vertices for
   * HexGrids. */
  def numOfTileRows: Int

  def width: Double = 800
  def height: Double = 400

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
