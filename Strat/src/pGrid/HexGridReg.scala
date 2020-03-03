package ostrat
package pGrid

case class HexGridReg(xTileMin: Int, xTileMax: Int, yTileMin: Int, yTileMax: Int) extends HexGrid
{
  /** Minimum x for Rows where y.Div4Rem2. */
  def row2sxMin: Int = xTileMax.roundUpTo(_.div4Rem2)

  /** Maximum x for Rows where y.Div4Rem2. */
  def row2sxMax: Int = xTileMax.roundDownTo(_.div4Rem2)

  def row2sTileLen = ((row2sxMax - row2sxMin + 4) / 4).min0

  /** Minimum x for Rows where y.Div4Rem0. */
  def row0sxMin: Int = xTileMax.roundUpTo(_.div4Rem0)

  /** Maximum x for Rows where y.Div4Rem0. */
  def row0sxMax: Int = xTileMax.roundDownTo(_.div4Rem0)

  def row0sTileLen = ((row0sxMax - row0sxMin + 4) / 4).min0

  /** Number of Rows where y.Div4Rem2. */
  def numOfRow2s: Int = ((yTileMax.roundDownTo(_.div4Rem2) - yTileMin.roundUpTo(_.div4Rem2) + 4) / 4).min0

  /** Number of Rows where y.Div4Rem0. */
  def numOfRow0s: Int = ((yTileMax.roundDownTo(_.div4Rem0) - yTileMin.roundUpTo(_.div4Rem0) + 4) / 4).min0

  def numOfTiles: Int = numOfRow2s * row2sTileLen + numOfRow0s * row0sTileLen
}
