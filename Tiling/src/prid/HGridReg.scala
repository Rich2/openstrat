/* Copyright 2018-21 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package prid

/** A Regular hex grid where the rows have the same length, except div4rem2 rows may differ in length by 1 from div4rem0 rows. A div4rem2 row is
 * where the y coordinate divided by 4 has a remainder of 2. This class replaces the old [[HexGridReg]], which used [[Roord]]s rather than
*  [[HCen]]s etc. */
class HGridReg(val rCenMin: Int, val rCenMax: Int, val cTileMin: Int, val cTileMax: Int) extends HGrid
{
  override def width: Double = xRight - xLeft
  override def height: Double =xTop - xBottom
  def xLeft: Double = (cTileMin - 2) * xRatio
  def xRight: Double = (cTileMax + 2) * xRatio
  def xTop: Double = rCenMax + 4.0 / 3
  def xBottom: Double = rCenMin - 4.0 / 3

  /** Gives the index into an Arr / Array of Tile data from its tile Roord. Use sideIndex and vertIndex methods to access Side and Vertex Arr / Array
   *  data. */
  @inline def arrIndex(r: Int, c: Int): Int =
  { val thisRow: Int = r %% 4 match
    { case 2 => (c - cRow2sMin) / 4
      case 0 => (c - cRow0sMin) / 4
    }
    val r2s: Int = ((r - rRow2sMin).divRoundUp(4) * row2sTileLen).atMost0
    val r0s: Int = ((r - rRow0sMin).divRoundUp(4) * row0sTileLen).atMost0
    r0s + r2s + thisRow
  }

  /** Minimum column or c value for tile centre rows where r.Div4Rem2. */
  def cRow2sMin: Int = cTileMin.roundUpTo(_.div4Rem2)

  /** Maximum column or c value for tile centre rows where r.Div4Rem2. */
  def cRow2sMax: Int = cTileMax.roundDownTo(_.div4Rem2)

  /** Length or number of tiles for tile centre rows where r.Div4Rem2. */
  def row2sTileLen = ((cRow2sMax - cRow2sMin + 4) / 4).atLeast0

  /** Minimum c or column vlaue for tile centre rows where r.Div4Rem0. */
  def cRow0sMin: Int = cTileMin.roundUpTo(_.div4Rem0)

  /** Maximum column or c value for tile centre rows where r.Div4Rem0. */
  def cRow0sMax: Int = cTileMax.roundDownTo(_.div4Rem0)

  /** Length or number of tiles for tile centre rows where r.Div4Rem0. */
  def row0sTileLen = ((cRow0sMax - cRow0sMin + 4) / 4).max(0)

  /** The lowest row or value, i.e the bottom row, where r.Div4Rem2. */
  def rRow2sMin: Int = rCenMin.roundUpTo(_.div4Rem2)

  /** The maximum row or value, i.e the top row, where r.Div4Rem2. */
  def rRow2sMax: Int = rCenMax.roundDownTo(_.div4Rem2)

  /** Number of Rows where r.Div4Rem2. */
  def numOfRow2s: Int = ((rRow2sMax - rRow2sMin + 4) / 4).max(0)

  /** The minimum row or value, ie the bottom row where r.Div4Rem0. */
  def rRow0sMin: Int = rCenMin.roundUpTo(_.div4Rem0)

  /** The maximum row or value, ie the top row where r.Div4Rem0. */
  def rRow0sMax: Int = rCenMax.roundDownTo(_.div4Rem0)

  /** The r coordinate of the bottom row of this grid divided by 4 leaves remainder of 2. */
  def bottomRowIs2: Boolean = rCenMin.div4Rem2

  /** The r coordinate of the bottom row of this grid divided br 4 leaves remainder of 0. */
  def bottomRowIs0: Boolean = rCenMin.div4Rem0

  /** The r coordinate of the top row of this grid divided br 4 leaves remainder of 2. */
  def topRowIs2: Boolean = rCenMin.div4Rem2

  /** The r coordinate of the top row of this grid divided by 4 leaves remainder of 0. */
  def topRowIs0: Boolean = rCenMin.div4Rem0

  /** Number of Rows where r.Div4Rem0. */
  def numOfRow0s: Int = ((rRow0sMax - rRow0sMin + 4) / 4).max(0)

  override def numOfTiles: Int = numOfRow2s * row2sTileLen + numOfRow0s * row0sTileLen

  /** foreachs over each Tile's Roord in the given Row. The row is specified by its r value. */
  override def rowForeach(r: Int)(f: HCen => Unit): Unit =
    if(r %% 4 == 2) iToForeach(cRow2sMin, cRow2sMax, 4)(c => f(HCen(r, c)))
    else iToForeach(cRow0sMin, cRow0sMax, 4)(c => f(HCen(r, c)))

  /** foreachs over each Tile's Roord in the given Row. The row is specified by its r value. */
  override def rowIForeach(r: Int, startCount: Int)(f: (HCen, Int) => Unit): Int =
  {
    var count: Int = startCount
    if (r %% 4 == 2)
      iToForeach(cRow2sMin, cRow2sMax, 4) { c =>
        f(HCen(r, c), count)
        count += 1
      }
    else
      iToForeach(cRow0sMin, cRow0sMax, 4){c =>
        f(HCen(r, c), count)
        count += 1
      }
    count
  }

  def hCenExists(r: Int, c:Int): Boolean = (r, c) match
  {
    case (r, c) if r < rCenMin | r > rCenMax => false
    case (r, c) if r.isOdd => false

    case (r, c) if r.div4Rem0 & (c < cRow0sMin | c > cRow0sMax) => false
    case (r, c) if r.div4Rem0 & c.div4Rem0 => true
    case (r, c) if r.div4Rem0 => false

    case (r, c) if r.div4Rem2 & (c < cRow2sMin | c > cRow2sMax) => false
    case (r, c) if r.div4Rem2 & c.div4Rem2 => true
    case _ => false
  }

  /* Methods that operate on Hex tile sides. ******************************************************/

  override def rowForeachSide(r: Int)(f: HSide => Unit): Unit = r match
  {
    case y if y == rSideMax & y.div4Rem3 => iToForeach(cRow2sMin - 1, cRow2sMax + 1, 2){ c => f(HSide(y, c)) }
    case y if y == rSideMax => iToForeach(cRow0sMin - 1, cRow0sMax + 1, 2){ c => f(HSide(y, c)) }
    case y if y.div4Rem2 => iToForeach(cRow2sMin - 2, cRow2sMax + 2, 4){ c => f(HSide(y, c)) }
    case y if y.div4Rem0 => iToForeach(cRow0sMin - 2, cRow0sMax + 2, 4){ c => f(HSide(y, c)) }
    case y if y == rSideMin & y.div4Rem1 => iToForeach(cRow2sMin - 1, cRow2sMax + 1, 2){ c => f(HSide(y, c)) }
    case y if y == rSideMin => iToForeach(cRow0sMin - 1, cRow0sMax + 1, 2){ c => f(HSide(y, c)) }
    case y => iToForeach(cTileMin - 1, cTileMax + 1, 2){ c => f(HSide(y, c)) }
  }

  override def cenRowLen(row: Int): Int = row %% 4 match {
    case 0 => row0sTileLen
    case 2 => row2sTileLen
    case _ => excep("Invalid row number")
  }

  override def cenRowMin(row: Int): Int = row %% 4 match {
    case 0 => cRow0sMin
    case 2 => cRow2sMin
    case _ => excep("Invalid row number")
  }

}

/** Companion object for the HGridReg class. Contains an applr method that corrects the r and Y minimum and maximum values. */
object HGridReg
{
  /** Corrects the X and Y minimum and maximum values. */
  def apply(rTileMin: Int, rTileMax: Int, cTileMin: Int, cTileMax: Int): HGridReg =
  {
    val rMin = rTileMin.roundUpToEven
    val rMax = rTileMax.roundDownToEven
    val rowsNum = (rMax - rMin + 2).atLeast0 / 2

    val cMin = rowsNum match
    { case 1 if rMin.div4Rem0 => cTileMin.roundUpTo(_.div4Rem0)
      case 1 => cTileMin.roundUpTo(_.div4Rem2)
      case _ =>  cTileMin.roundUpToEven
    }

    val cMax =rowsNum match
    { case 1 if rMin.div4Rem0 => cTileMax.roundDownTo(_.div4Rem0)
      case 1 => cTileMax.roundDownTo(_.div4Rem2)
      case _ => cTileMax.roundDownToEven
    }

    new HGridReg(rMin, rMax, cMin, cMax)
  }
}