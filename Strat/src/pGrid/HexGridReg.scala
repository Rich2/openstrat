package ostrat
package pGrid
import geom._

/* A Regular hex grid where the rows have the same length, except div4rem2 rows may differ in length by 1 from div4rem0 rows. A div4rem2 row is
* where the y coordinate divided by 4 has a remainder of 2. */
case class HexGridReg(xTileMin: Int, xTileMax: Int, yTileMin: Int, yTileMax: Int) extends HexGrid
{
  def coodToVec2(cood: Cood): Vec2 = HexGrid.coodToVec2(cood)

  /** Minimum x for Rows where y.Div4Rem2. */
  def xRow2sMin: Int = xTileMax.roundUpTo(_.div4Rem2)

  /** Maximum x for Rows where y.Div4Rem2. */
  def xRow2sMax: Int = xTileMax.roundDownTo(_.div4Rem2)

  def row2sTileLen = ((xRow2sMax - xRow2sMin + 4) / 4).min0

  /** Minimum x for Rows where y.Div4Rem0. */
  def xRow0sMin: Int = xTileMax.roundUpTo(_.div4Rem0)

  /** Maximum x for Rows where y.Div4Rem0. */
  def xRow0sMax: Int = xTileMax.roundDownTo(_.div4Rem0)

  def row0sTileLen = ((xRow0sMax - xRow0sMin + 4) / 4).min0

  def yRow2sMin: Int = yTileMin.roundUpTo(_.div4Rem2)
  def yRow2sMax: Int = yTileMax.roundDownTo(_.div4Rem2)
  /** Number of Rows where y.Div4Rem2. */
  def numOfRow2s: Int = ((yRow2sMax - yRow2sMin + 4) / 4).min0

  def yRow0sMin: Int = yTileMin.roundUpTo(_.div4Rem0)
  def yRow0sMax: Int = yTileMax.roundDownTo(_.div4Rem0)

  /** Number of Rows where y.Div4Rem0. */
  def numOfRow0s: Int = ((yRow0sMax - yRow0sMin + 4) / 4).min0

  override def numOfTiles: Int = numOfRow2s * row2sTileLen + numOfRow0s * row0sTileLen

  override def allTilesForeach(f: Cood => Unit): Unit =
  { ijToForeach(yRow2sMin, yRow2sMax, 4)(xRow2sMin, xRow2sMax, 4)((y, x) => f(Cood(x, y)))
    ijToForeach(yRow0sMin, yRow0sMax, 4)(xRow0sMin, xRow0sMax, 4)((y, x) => f(Cood(x, y)))
  }

  def bottomRowIs2: Boolean = yTileMin.div4Rem2
  def bottomRowIs0: Boolean = yTileMin.div4Rem0

  @inline override def index(x: Int, y: Int): Int =
  {
    val thisRow: Int = y %% 4 match
    { case 2 => (x - xRow2sMin) / 4
      case 0 => (x - xRow0sMin) / 4
    }
    val y2s: Int = row2sTileLen * (y - yRow2sMin) / 4
    val y0s: Int = row0sTileLen * (y - yRow0sMin) / 4
    y0s + y2s + thisRow
  }
}