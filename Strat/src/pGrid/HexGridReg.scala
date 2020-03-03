package ostrat
package pGrid

case class HexGridReg(xTileMin: Int, xTileMax: Int, yTileMin: Int, yTileMax: Int) extends HexGrid
{
  /** Minimum x for Rows where y.Div4Rem2. */
  def row2sxMin: Int = xTileMax.roundUpTo(_.div4Rem2)

  /** Maximum x for Rows where y.Div4Rem2. */
  def row2sxMax: Int = xTileMax.roundDownTo(_.div4Rem2)

  def Row2sTileLen = ((row2sxMax - row2sxMin + 4) / 4).min0

  /** Minimum x for Rows where y.Div4Rem0. */
  def row0sxMin: Int = xTileMax.roundUpTo(_.div4Rem0)

  /** Maximum x for Rows where y.Div4Rem0. */
  def row0sxMax: Int = xTileMax.roundDownTo(_.div4Rem0)

  def Row0sTileLen = ((row0sxMax - row0sxMin + 4) / 4).min0
}
