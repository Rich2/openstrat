package ostrat
package pGrid

/** A tile Grid where the x positions of the tiles lie midway between the x postions of the tiles on the rows above and below. */
trait AltGridReg extends TileGridReg
{
  def xRatio: Double
  override def numOfTiles: Int = numOfRow2s * row2sTileLen + numOfRow0s * row0sTileLen
  final override def xCen: Double = (cTileMin + cTileMax) / 2.0 * xRatio

  @inline override def index(c: Int, y: Int): Int =
  {
    val thisRow: Int = y %% 4 match
    { case 2 => (c - cRow2sMin) / 4
      case 0 => (c - cRow0sMin) / 4
    }
    val y2s: Int = ((y - yRow2sMin).divRoundUp(4) * row2sTileLen).max0
    val y0s: Int = ((y - yRow0sMin).divRoundUp(4) * row0sTileLen).max0
    y0s + y2s + thisRow
  }

  override def tilesAllForeach(f: Cood => Unit): Unit =
  { ijToForeach(yRow2sMin, yRow2sMax, 4)(cRow2sMin, cRow2sMax, 4)((y, x) => f(Cood(x, y)))
    ijToForeach(yRow0sMin, yRow0sMax, 4)(cRow0sMin, cRow0sMax, 4)((y, x) => f(Cood(x, y)))
  }

  /** Minimum c for Rows where y.Div4Rem2. */
  def cRow2sMin: Int = cTileMin.roundUpTo(_.div4Rem2)

  /** Maximum c for Rows where y.Div4Rem2. */
  def cRow2sMax: Int = cTileMax.roundDownTo(_.div4Rem2)

  def row2sTileLen = ((cRow2sMax - cRow2sMin + 4) / 4).max(0)

  /** Minimum c for Rows where y.Div4Rem0. */
  def cRow0sMin: Int = cTileMin.roundUpTo(_.div4Rem0)

  /** Maximum x for Rows where y.Div4Rem0. */
  def cRow0sMax: Int = cTileMax.roundDownTo(_.div4Rem0)

  def row0sTileLen = ((cRow0sMax - cRow0sMin + 4) / 4).max(0)

  def yRow2sMin: Int = yTileMin.roundUpTo(_.div4Rem2)
  def yRow2sMax: Int = yTileMax.roundDownTo(_.div4Rem2)
  /** Number of Rows where y.Div4Rem2. */
  def numOfRow2s: Int = ((yRow2sMax - yRow2sMin + 4) / 4).max(0)

  def yRow0sMin: Int = yTileMin.roundUpTo(_.div4Rem0)
  def yRow0sMax: Int = yTileMax.roundDownTo(_.div4Rem0)

  /** The y coordinate of the bottom row of this grid divided by 4 leaves remainder of 2. */
  def bottomRowIs2: Boolean = yTileMin.div4Rem2

  /** The y coordinate of the bottom row of this grid divided by 4 leaves remainder of 0. */
  def bottomRowIs0: Boolean = yTileMin.div4Rem0

  /** Number of Rows where y.Div4Rem0. */
  def numOfRow0s: Int = ((yRow0sMax - yRow0sMin + 4) / 4).max(0)
}