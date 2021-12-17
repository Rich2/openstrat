/* Copyright 2018-21 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package prid

/** A Regular hex grid where the tile rows have the same length, except the tile rows where r %% 4 == 2 may differ in length by 1 from tile rows
 * where r %% 4 == 0 rows. */
class HGridReg(val bottomTileRow: Int, val topTileRow: Int, val tileColMin: Int, val tileColMax: Int) extends HGrid
{
  final override val numSides: Int =
  { var count = 0
    sidesForeach(r => count += 1)
    count
  }

  override def width: Double = xRight - xLeft
  override def height: Double =xTop - xBottom

  /** The left most point of the grid. */
  def xLeft: Double = (tileColMin - 2)// * xRatio

  /** The right most point of the grid. */
  def xRight: Double = (tileColMax + 2)// * xRatio

  /** The top point of the grid. */
  def xTop: Double = topTileRow * yRatio + 4.0/Sqrt3

  /** The bottom point of the grid. */
  def xBottom: Double = bottomTileRow * yRatio - 4.0/Sqrt3

  /** Gives the index into an Arr / Array of Tile data from its [[HCen]] hex tile centre coordinate. Use sideIndex and vertIndex methods to access
   *  Side and Vertex Arr / Array data. */
  @inline def arrIndex(r: Int, c: Int): Int =
  { val thisRow: Int = r %% 4 match
    { case 2 => (c - row2sStart) / 4
      case 0 => (c - row0sStart) / 4
    }
    val r2s: Int = ((r - bottomRem2Row).divRoundUp(4) * row2sTileNum).max0
    val r0s: Int = ((r - bottomRem0Row).divRoundUp(4) * row0sTileNum).max0
    r0s + r2s + thisRow
  }

  /** The start minimum or by convention left column or c value for tile centre rows where r.Div4Rem2. This property is only available on
   * regular hex grids [[HGrid]]s, as this value is not fixed on irregular hex grids. */
  def row2sStart: Int = tileColMin.roundUpTo(_.div4Rem2)

  /** The end, maximum or by convention right column coordinate or c value for tile centre rows where r.Div4Rem2. This property is only available on
   * regular hex grids [[HGrid]]s, as this value is not fixed on irregular hex grids. */
  def row2sEnd: Int = tileColMax.roundDownTo(_.div4Rem2)

  /** The number of tiles or tile centres in rows where r.Div4Rem0. */
  def row0sTileNum = ((row0sEnd - row0sStart + 4) / 4).max(0)

  /** The number of tiles or tile centres in rows where r.Div4Rem2. */
  def row2sTileNum = ((row2sEnd - row2sStart + 4) / 4).max0

  /** The starting, minimum or by convention left column coordinate c value for tile centre rows where r.Div4Rem0. This property is only available on
   * regular hex grids [[HGrid]]s, as this value is not fixed on irregular hex grids. */
  def row0sStart: Int = tileColMin.roundUpTo(_.div4Rem0)

  /** The end maximum or by convention right column coordinate, or c value for tile centre rows where r.Div4Rem0. This property is only available on
   * regular hex grids [[HGrid]]s, as this value is not fixed on irregular hex grids. */
  def row0sEnd: Int = tileColMax.roundDownTo(_.div4Rem0)

  /** The bottom, lowest or minimum row r value for tile centre rows where r.Div4Rem2, r %% 4 == 2. */
  def bottomRem2Row: Int = bottomTileRow.roundUpTo(_.div4Rem2)

  /** The top, highest or maximum row r value for tile centre rows where r.Div4Rem2, r %% 4 == 2. */
  def topRem2Row: Int = topTileRow.roundDownTo(_.div4Rem2)

  /** The bottom, lowest or minimum row r value for tile centre rows where r.Div4Rem0, r %% 4 == 0. */
  def bottomRem0Row: Int = bottomTileRow.roundUpTo(_.div4Rem0)

  /** The top, highest or maximum row r value for tile centres rows where r.Div4Rem0, r %% 4 == 0. */
  def topRem0Row: Int = topTileRow.roundDownTo(_.div4Rem0)

  override def numRow0s: Int = ((topRem0Row - bottomRem0Row + 4) / 4).max(0)
  override def numRow2s: Int = ((topRem2Row - bottomRem2Row + 4) / 4).max(0)
  override def numTiles: Int = numRow2s * row2sTileNum + numRow0s * row0sTileNum
  override def numTileRows: Int = numRow2s + numRow0s

  /** foreachs over each Tile's Roord in the given Row. The row is specified by its r value. */
  override def rowForeach(r: Int)(f: HCen => Unit): Unit =
    if(r %% 4 == 2) iToForeach(row2sStart, row2sEnd, 4)(c => f(HCen(r, c)))
    else iToForeach(row0sStart, row0sEnd, 4)(c => f(HCen(r, c)))

  /** foreachs over each Tile's Roord in the given Row. The row is specified by its r value. */
  override def rowIForeach(r: Int, startCount: Int)(f: (HCen, Int) => Unit): Int =
  {
    var count: Int = startCount
    if (r %% 4 == 2)
      iToForeach(row2sStart, row2sEnd, 4) { c =>
        f(HCen(r, c), count)
        count += 1
      }
    else
      iToForeach(row0sStart, row0sEnd, 4){ c =>
        f(HCen(r, c), count)
        count += 1
      }
    count
  }

  def hCenExists(r: Int, c:Int): Boolean = (r, c) match
  {
    case (r, c) if r < bottomTileRow | r > topTileRow => false
    case (r, c) if r.isOdd => false

    case (r, c) if r.div4Rem0 & (c < row0sStart | c > row0sEnd) => false
    case (r, c) if r.div4Rem0 & c.div4Rem0 => true
    case (r, c) if r.div4Rem0 => false

    case (r, c) if r.div4Rem2 & (c < row2sStart | c > row2sEnd) => false
    case (r, c) if r.div4Rem2 & c.div4Rem2 => true
    case _ => false
  }

  /* Methods that operate on Hex tile sides. ******************************************************/

  override def rowForeachSide(r: Int)(f: HSide => Unit): Unit = r match
  {
    case y if y == topSideRow & y.div4Rem3 => iToForeach(row2sStart - 1, row2sEnd + 1, 2){ c => f(HSide(y, c)) }
    case y if y == topSideRow => iToForeach(row0sStart - 1, row0sEnd + 1, 2){ c => f(HSide(y, c)) }
    case y if y.div4Rem2 => iToForeach(row2sStart - 2, row2sEnd + 2, 4){ c => f(HSide(y, c)) }
    case y if y.div4Rem0 => iToForeach(row0sStart - 2, row0sEnd + 2, 4){ c => f(HSide(y, c)) }
    case y if y == bottomSideRow & y.div4Rem1 => iToForeach(row2sStart - 1, row2sEnd + 1, 2){ c => f(HSide(y, c)) }
    case y if y == bottomSideRow => iToForeach(row0sStart - 1, row0sEnd + 1, 2){ c => f(HSide(y, c)) }
    case y => iToForeach(tileColMin - 1, tileColMax + 1, 2){ c => f(HSide(y, c)) }
  }

  override def rowNumTiles(row: Int): Int = row %% 4 match {
    case 0 => row0sTileNum
    case 2 => row2sTileNum
    case _ => excep("Invalid row number")
  }

  override def tileRowStart(row: Int): Int = row %% 4 match {
    case 0 => row0sStart
    case 2 => row2sStart
    case _ => excep("Invalid row number")
  }

  override def tileRowEnd(row: Int): Int = row %% 4 match {
    case 0 => row0sEnd
    case 2 => row2sEnd
    case _ => excep("Invalid row number")
  }

  /** Gives the index into an Arr / Array of Tile data from its tile [[HCen]]. Use sideIndex and vertIndex methods to access Side and Vertex Arr /
   * Array data. */
  override def sideArrIndex(r: Int, c: Int): Int = ???

  /** The number of tile sides in the top side row of the hex grid. */
  def topSideRowNum: Int = ife(topTileRow.div4Rem0, row0sTileNum, row2sTileNum) * 2

  /** The number of tile sides in the bottom side row of the hex grid. */
  def bottomSideRowNum: Int = ife(bottomTileRow.div4Rem0, row0sTileNum, row2sTileNum) * 2

  /** Array of indexs for Side data Arrs giving the index value for the start of each side row. */
  override def sideRowIndexArray: Array[Int] =
  {
    val array = new Array[Int](numOfSideRows)
    var count = 0
    sideRowForeach{y =>
      array(y - bottomSideRow) = count
      rowForeachSide(y)(_ => count += 1)
    }
    array
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
    val rowsNum = (rMax - rMin + 2).max0 / 2

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