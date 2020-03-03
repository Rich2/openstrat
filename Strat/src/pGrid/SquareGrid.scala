package ostrat
package pGrid

/** Currently all SquareGrids are regular. */
case class SquareGrid(xTileMin: Int, xTileMax: Int, yTileMin: Int, yTileMax: Int) extends TileGrid
{
  def rowTileLen: Int = ((xTileMax.roundDownToEven - xTileMin.roundUpToEven + 2) / 2).min0
  def numOfRows: Int = ((yTileMax.roundDownToEven - yTileMin + 2) / 2).min0
  def numOfTiles: Int = numOfRows * numOfTiles
}
