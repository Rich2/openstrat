/* Copyright 2018-22 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package prid; package phex

/** A Regular hex grid where the tile rows have the same length, except the tile rows where r %% 4 == 2 may differ in length by 1 from tile rows
 * where r %% 4 == 0 rows. */
class HGridReg(val bottomCenR: Int, val topCenR: Int, val leftCenC: Int, val rightCenC: Int) extends HGrid
{
  final override val numSides: Int =
  { var count = 0
    sidesForeach(r => count += 1)
    count
  }

  /** The [[HCenOrSide]] coordinate centre for this hex grid. */
  override def coordCen: HCoord = HCoord(rCen, cCen)

  /** Gives the index into an Arr / Array of Tile data from its [[HCen]] hex tile centre coordinate. Use sideIndex and vertIndex methods to access
   *  Side and Vertex Arr / Array data. */
  @inline def arrIndex(r: Int, c: Int): Int =
  { val thisRow: Int = r %% 4 match
    { case 2 => (c - leftrem2CenC) / 4
      case 0 => (c - leftRem0CenC) / 4
    }
    val r2s: Int = ((r - bottomRem2R).divRoundUp(4) * row2sTileNum).max0
    val r0s: Int = ((r - bottomRem0R).divRoundUp(4) * row0sTileNum).max0
    r0s + r2s + thisRow
  }

  /** The start minimum or by convention left column or c value for tile centre rows where r.Div4Rem2. This property is only available on
   * regular hex grids [[HGrid]]s, as this value is not fixed on irregular hex grids. */
  def leftrem2CenC: Int = leftCenC.roundUpTo(_.div4Rem2)

  /** The end, maximum or by convention right column coordinate or c value for tile centre rows where r.Div4Rem2. This property is only available on
   * regular hex grids [[HGrid]]s, as this value is not fixed on irregular hex grids. */
  def rightRem2CenC: Int = rightCenC.roundDownTo(_.div4Rem2)

  /** The number of tiles or tile centres in rows where r.Div4Rem0. */
  def row0sTileNum = ((rightRem0CenC - leftRem0CenC + 4) / 4).max(0)

  /** The number of tiles or tile centres in rows where r.Div4Rem2. */
  def row2sTileNum = ((rightRem2CenC - leftrem2CenC + 4) / 4).max0

  /** The starting, minimum or by convention left column coordinate c value for tile centre rows where r.Div4Rem0. This property is only available on
   * regular hex grids [[HGrid]]s, as this value is not fixed on irregular hex grids. */
  def leftRem0CenC: Int = leftCenC.roundUpTo(_.div4Rem0)

  /** The end maximum or by convention right column coordinate, or c value for tile centre rows where r.Div4Rem0. This property is only available on
   * regular hex grids [[HGrid]]s, as this value is not fixed on irregular hex grids. */
  def rightRem0CenC: Int = rightCenC.roundDownTo(_.div4Rem0)

  /** The bottom, lowest or minimum row r value for tile centre rows where r.Div4Rem2, r %% 4 == 2. */
  def bottomRem2R: Int = bottomCenR.roundUpTo(_.div4Rem2)

  /** The top, highest or maximum row r value for tile centre rows where r.Div4Rem2, r %% 4 == 2. */
  def topRem2R: Int = topCenR.roundDownTo(_.div4Rem2)

  /** The bottom, lowest or minimum row r value for tile centre rows where r.Div4Rem0, r %% 4 == 0. */
  def bottomRem0R: Int = bottomCenR.roundUpTo(_.div4Rem0)

  /** The top, highest or maximum row r value for tile centres rows where r.Div4Rem0, r %% 4 == 0. */
  def topRem0R: Int = topCenR.roundDownTo(_.div4Rem0)

  override def numRow0s: Int = ((topRem0R - bottomRem0R + 4) / 4).max(0)
  override def numRow2s: Int = ((topRem2R - bottomRem2R + 4) / 4).max(0)
  //override def numTiles: Int = numRow2s * row2sTileNum + numRow0s * row0sTileNum
  override def numTileRows: Int = numRow2s + numRow0s

  /** foreachs over each Tile's Roord in the given Row. The row is specified by its r value. */
  override def rowForeach(r: Int)(f: HCen => Unit): Unit =
    if(r %% 4 == 2) iToForeach(leftrem2CenC, rightRem2CenC, 4)(c => f(HCen(r, c)))
    else iToForeach(leftRem0CenC, rightRem0CenC, 4)(c => f(HCen(r, c)))

  def hCenExists(r: Int, c:Int): Boolean = (r, c) match
  {
    case (r, c) if r < bottomCenR | r > topCenR => false
    case (r, c) if r.isOdd => false

    case (r, c) if r.div4Rem0 & (c < leftRem0CenC | c > rightRem0CenC) => false
    case (r, c) if r.div4Rem0 & c.div4Rem0 => true
    case (r, c) if r.div4Rem0 => false

    case (r, c) if r.div4Rem2 & (c < leftrem2CenC | c > rightRem2CenC) => false
    case (r, c) if r.div4Rem2 & c.div4Rem2 => true
    case _ => false
  }

  /* Methods that operate on Hex tile sides. ******************************************************/

  /** Combine adjacent tiles of the same value. */
  override def adjTilesOfTile(tile: HCen): HCenArr = tile.neibs.filter{hc => (hc.r, hc.c) match
  { case (r, _) if r > topCenR => false
    case (r, _) if r < bottomCenR => false
    case (_, c) if c > rightCenC => false
    case (_, c) if c < leftCenC => false
    case _ => true
  }}

  def topRowForeachSide(f: HSide => Unit): Unit = if(topSideRow.div4Rem3) iToForeach(leftrem2CenC - 1, rightRem2CenC + 1, 2){ c => f(HSide(topSideRow, c)) }
    else iToForeach(leftRem0CenC - 1, rightRem0CenC + 1, 2){ c => f(HSide(topSideRow, c)) }

  def bottomRowForeachSide(f: HSide => Unit): Unit = if(bottomSideRow.div4Rem3) iToForeach(leftrem2CenC - 1, rightRem2CenC + 1, 2){ c => f(HSide(bottomSideRow, c)) }
  else iToForeach(leftRem0CenC - 1, rightRem0CenC + 1, 2){ c => f(HSide(bottomSideRow, c)) }

  override def rowForeachSide(r: Int)(f: HSide => Unit): Unit = r match
  { case r if r == topSideRow & r.div4Rem3 => iToForeach(leftrem2CenC - 1, rightRem2CenC + 1, 2){ c => f(HSide(r, c)) }
    case r if r == topSideRow => iToForeach(leftRem0CenC - 1, rightRem0CenC + 1, 2){ c => f(HSide(r, c)) }
    case r if r.div4Rem2 => iToForeach(leftrem2CenC - 2, rightRem2CenC + 2, 4){ c => f(HSide(r, c)) }
    case r if r.div4Rem0 => iToForeach(leftRem0CenC - 2, rightRem0CenC + 2, 4){ c => f(HSide(r, c)) }
    case r if r == bottomSideRow & r.div4Rem1 => iToForeach(leftrem2CenC - 1, rightRem2CenC + 1, 2){ c => f(HSide(r, c)) }
    case r if r == bottomSideRow => iToForeach(leftRem0CenC - 1, rightRem0CenC + 1, 2){ c => f(HSide(r, c)) }
    case r => iToForeach(leftCenC - 1, rightCenC + 1, 2){ c => f(HSide(r, c)) }
  }

  override def rowForeachInnerSide(r: Int)(f: HSide => Unit): Unit = r match
  { case r if r == topSideRow =>
    case r if r == bottomSideRow =>
    case r if r.div4Rem2 => iToForeach(leftrem2CenC + 2, rightRem2CenC - 2, 4){ c => f(HSide(r, c)) }
    case r if r.div4Rem0 => iToForeach(leftRem0CenC + 2, rightRem0CenC - 2, 4){ c => f(HSide(r, c)) }
    case r => iToForeach(leftCenC + 1, rightCenC - 1, 2){ c => f(HSide(r, c)) }
  }

  override def rowNumTiles(row: Int): Int = row %% 4 match {
    case 0 => row0sTileNum
    case 2 => row2sTileNum
    case _ => excep("Invalid row number")
  }

  override def rowLeftCenC(row: Int): Int = row %% 4 match {
    case 0 => leftRem0CenC
    case 2 => leftrem2CenC
    case _ => excep("Invalid row number")
  }

  override def rowRightCenC(row: Int): Int = row %% 4 match {
    case 0 => rightRem0CenC
    case 2 => rightRem2CenC
    case _ => excep("Invalid row number")
  }

  /** Gives the index into an Arr / Array of Tile data from its tile [[HCen]]. Use sideIndex and vertIndex methods to access Side and Vertex Arr /
   * Array data. */
  override def sideArrIndex(r: Int, c: Int): Int = ???

  /** The number of tile sides in the top side row of the hex grid. */
  def topSideRowlen: Int = ife(topCenR.div4Rem0, row0sTileNum, row2sTileNum) * 2

  /** The number of tile sides in the bottom side row of the hex grid. */
  def bottomSideRowLen: Int = ife(bottomCenR.div4Rem0, row0sTileNum, row2sTileNum) * 2

  /** Array of indexs for Side data Arrs giving the index value for the start of each side row. */
  override def sideRowIndexArray: Array[Int] =
  {
    val array = new Array[Int](numOfSideRows)
    var count = 0
    sideRowsForeach{y =>
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