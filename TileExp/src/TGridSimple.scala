/* Copyright 2018-20 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat
package prid

trait TGridSimple
{
  /** Number of rows of tiles. This will be different to the number of rows of sides and and will be different to the number of rows of vertices for
   * HexGrids. */
  def numOfTileRows: Int

  /** The number of Rows of Sides. */
  @inline final def numOfSideRows: Int = numOfTileRows * 2 + 1

  /** The number of Rows of vertices. */
  @inline final def numOfVertRows: Int = numOfTileRows + 1

  /** The total number of Tiles in the tile Grid. */
  def numOfTiles: Int

  def xRatio: Double
}
