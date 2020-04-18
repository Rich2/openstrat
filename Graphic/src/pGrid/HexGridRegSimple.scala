package ostrat
package pGrid

/* A Regular hex grid where the rows have the same length, except div4rem2 rows may differ in length by 1 from div4rem0 rows. A div4rem2 row is
* where the y coordinate divided by 4 has a remainder of 2. */
class HexGridRegSimple(val yTileMin: Int, val yTileMax: Int, val cTileMin: Int, val cTileMax: Int) extends HexGrid
{
  override def numOfTileRows: Int = numOfRow2s + numOfRow0s

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

  @inline override def arrIndex(y: Int, c: Int): Int =
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

  override def rowForeachVert(y: Int)(f: Roord => Unit): Unit = iToForeach(cTileMin - 2, cTileMax + 2, 2)(c => f(Roord(y, c)))
  override def sideRowIndex: Array[Int] =
  {
    val array = new Array[Int](numOfSideRows)
    var count = 0
    sideRowForeach{y =>
      array(y) = count
      rowForeachSide(y)(_ => count += 1)
    }
    array
  }
  override def sideArrIndex(y: Int, c: Int): Int =/* y match {
    case y if y == ySideMin & y.div4Rem1 => (c - cRow2sMin + 1) / 2
    case y if y == ySideMin => (c - cRow0sMin + 1) / 2
    case y if y == ySideMax & y.div4Rem1 =>
  }*/
  { //val oddRows = (y - ySideMin + 1).max0 / 2 *
    var count = 0
    var res: Int = -1
    sidesForeach{r =>
      if (r == Roord(y, c)) res = count
      count += 1
    }
    res
  }
}

object HexGridRegSimple
{ def apply(yTileMin: Int, yTileMax: Int, cTileMin: Int, cTileMax: Int): HexGridSimple = new HexGridRegSimple(yTileMin, yTileMax, cTileMin, cTileMax)
}