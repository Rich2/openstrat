package ostrat
package pGrid

/* A Regular hex grid where the rows have the same length, except div4rem2 rows may differ in length by 1 from div4rem0 rows. A div4rem2 row is
* where the y coordinate divided by 4 has a remainder of 2. */
case class HexGridReg(yTileMin: Int, yTileMax: Int, cTileMin: Int, cTileMax: Int) extends HexGrid with TileGridReg
{
  override def numOfRows: Int = numOfRow2s + numOfRow0s

  //def roordToVec2Rel(roord: Roord): Vec2 = roordToVec2(roord) - cen

  /* Override methods */
  override def tileExists(r: Roord): Boolean =  ???

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

  override def numOfTiles: Int = numOfRow2s * row2sTileLen + numOfRow0s * row0sTileLen

  @inline override def index(y: Int, c: Int): Int =
  {
    val thisRow: Int = y %% 4 match
    { case 2 => (c - cRow2sMin) / 4
      case 0 => (c - cRow0sMin) / 4
    }
    val y2s: Int = ((y - yRow2sMin).divRoundUp(4) * row2sTileLen).max0
    val y0s: Int = ((y - yRow0sMin).divRoundUp(4) * row0sTileLen).max0
    y0s + y2s + thisRow
  }
  def rowForeachTile(y: Int)(f: Roord => Unit): Unit =
    if(y %% 4 == 2) iToForeach(cRow2sMin, cRow2sMax, 4)(c => f(Roord(y, c)))
    else iToForeach(cRow0sMin, cRow0sMax, 4)(c => f(Roord(y, c)))

  override def rowForeachSide(y: Int)(f: Roord => Unit): Unit = y match
  {
    case y if y == ySideMax & y.div4Rem3 => iToForeach(cRow2sMin - 1, cRow2sMax + 1, 2){ c => f(Roord(y, c)) }
    case y if y == ySideMax => iToForeach(cRow0sMin - 1, cRow0sMax + 1, 2){ c => f(Roord(y, c)) }
    case y if y.div4Rem2 => iToForeach(cRow2sMin - 2, cRow2sMax + 2, 4){ c => f(Roord(y, c)) }
    case y if y.div4Rem0 => iToForeach(cRow0sMin - 2, cRow0sMax + 2, 4){ c => f(Roord(y, c)) }
    case y if y == ySideMin & y.div4Rem1 => iToForeach(cRow2sMin - 1, cRow2sMax + 1, 2){ c => f(Roord(y, c)) }
    case y if y == ySideMin => iToForeach(cRow0sMin - 1, cRow0sMax + 1, 2){ c => f(Roord(y, c)) }
    case y => iToForeach(cTileMin - 1, cTileMax + 1, 2){ c => f(Roord(y, c)) }
  }

  override def rowForeachVert(y: Int)(f: Roord => Unit): Unit = iToForeach(cTileMin - 1, cTileMax + 1, 2)(c => f(Roord(y, c)))
}