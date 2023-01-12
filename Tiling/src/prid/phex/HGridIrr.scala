/* Copyright 2018-22 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package prid; package phex

/** An irregular hex grid, where the rows have different lengths and irregular start row coordinates. This is backed by an Array[Int]. The length of
 *  this Array is twice the number of tile rows in the grid. Each row from lowest to highest has two values length of the row in the number of tile
 *  centres [[HCen]]s and the cTileMin coordinate for the row.
 * @constructor creates a new HexGridIrr with a defined grid.
 * @param bottomCenR The r value for the bottom tile row of the TileGrid.
 * @param tileRowsStartEnd the Array contains 2 values per Tile Row, the cStart Tile and the cEnd Tile */
class HGridIrr(val bottomCenR: Int, val unsafeRowsArray: Array[Int]) extends HGrid
{
  final val numTileRows: Int = unsafeRowsArray.length / 2

  final override def topCenR: Int = bottomCenR + numTileRows * 2 - 2

  /** Combine adjacent tiles of the same value. */
  override def adjTilesOfTile(tile: HCen): HCenArr = ???

  /** The [[HCenOrSide]] coordinate centre for this hex grid. */
  override def coordCen: HCoord = HCoord(rCen, cCen)

  override def rowForeachSide(r: Int)(f: HSide => Unit): Unit = r match
  { case r if r.isEven =>
    { rowForeach(r){ hc => f(HSide(hc.r, hc.c -2)) }
      if (rowNumTiles(r) > 0) f(HSide(r, rowRightCenC(r) + 2))
    }

    case r if r == bottomSideR => rowForeach(r + 1){ hc => f(HSide(r, hc.c - 1)); f(HSide(r, hc.c + 1)) }
    case r if r == topSideR => rowForeach(r - 1){ hc => f(HSide(r, hc.c - 1)); f(HSide(r, hc.c + 1)) }

    case r =>
    { val start = rowLeftCenC(r - 1).min(rowLeftCenC(r + 1)) - 1
      val end = rowRightCenC(r - 1).max(rowRightCenC(r + 1)) + 1
      iToForeach(start, end, 2){ c => f(HSide(r, c)) }
    }
  }

  override def innerRowForeachInnerSide(r: Int)(f: HSide => Unit): Unit = r match
  {
    case r if r.isEven => iToForeach(rowLeftCenC(r) + 2, rowRightCenC(r) -2, 4){ c => f(HSide(r, c)) }
    case r if r == bottomSideR =>
    case r if r == topSideR =>

    case r =>
    { val start = rowLeftCenC(r - 1).max(rowLeftCenC(r + 1)) - 1
      val end = rowRightCenC(r - 1).min(rowRightCenC(r + 1)) + 1
      iToForeach(start, end, 2){ c => f(HSide(r, c)) }
    }
  }

  override def edgesForeach(f: HSide => Unit): Unit = if (numTileRows > 0)
  {
    if(rowNumTiles(bottomCenR) > 0) iToForeach(rowLeftCenC(bottomCenR) - 1, rowRightCenC(bottomCenR) + 1, 2)(c => f(HSide(bottomSideR, c)))
    iToForeach(bottomCenR, topCenR){r => r match{
      case r if r.isEven => { f(HSide(r, rowLeftCenC(r) -2)); f(HSide(r, rowRightCenC(r) + 2)) }
      case r => {
        val bLeft = rowLeftCenC(r - 1)
        val uLeft = rowLeftCenC(r + 1)
        val leftStart = bLeft.min(uLeft) - 1
        val leftEnd = bLeft.max(uLeft) - 3
        iToForeach(leftStart, leftEnd, 2){c => f(HSide(r, c)) }
        val bRight = rowRightCenC(r - 1)
        val uRight = rowRightCenC(r + 1)
        val rightEnd = bRight.max(uRight) + 1
        val rightStart = bRight.min(uRight) + 3
        iToForeach(rightStart, rightEnd, 2){c => f(HSide(r, c)) }
      }
    }}
    if(rowNumTiles(topCenR) > 0) iToForeach(rowLeftCenC(topCenR) - 1, rowRightCenC(topCenR) + 1, 2)(c => f(HSide(topSideR, c)))
  }

  def cSideRowMin(r: Int): Int = ???

  /** Gives the index into an Arr / Array of Tile data from its tile [[HCen]]. Use sideIndex and vertIndex methods to access Side and Vertex Arr /
   * Array data. */
  override def sideLayerArrayIndex(r: Int, c: Int): Int =
  { val cDelta = r match
    { case r if r == topSideR => c - rowLeftCenC(r - 1) + 1
      case r if r == bottomSideR => c - rowLeftCenC(r + 1) + 1
      case r if r.isEven => (c - rowLeftCenC(r) + 2) / 2
      case r => c - rowLeftCenC(r - 1).min(rowLeftCenC(r + 1)) + 1
    }
    val ic = cDelta / 2
    sideRowIndexArray(r - bottomSideR) + ic
  }

  override def leftCenC: Int = foldRows(Int.MaxValue - 1)((acc, r) => acc.min(rowLeftCenC(r)))
  override def rightCenC: Int = foldRows(Int.MinValue )((acc, r) => acc.max(rowRightCenC(r)))

  override def numRow0s: Int = numTileRows.ifMod(bottomCenR.div4Rem0, _.roundUpToEven) / 2
  override def numRow2s: Int = numTileRows.ifMod(bottomCenR.div4Rem2, _.roundUpToEven) / 2

  @inline protected def unsafeRowArrayindex(row: Int): Int = row - bottomCenR

  override def layerArrayIndex(r: Int, c: Int): Int =
  { val wholeRows = iUntilIntSum(bottomCenR, r, 2){ r => rowNumTiles(r) }
    wholeRows + (c - rowLeftCenC(r)) / 4
  }

  override def rowNumTiles(row: Int): Int = unsafeRowsArray(row - bottomCenR)

  /** Foreachs over each tile centre of the specified row applying the side effecting function to the [[HCen]]. */
  def rowForeach(r: Int)(f: HCen => Unit): Unit = iToForeach(rowLeftCenC(r), rowRightCenC(r), 4){ c => f(HCen(r, c))}

  /** The start (or by default left column) of the tile centre of the given row. Will throw on illegal values. */
  override def rowLeftCenC(row: Int): Int = row match
  { case r if r.isOdd => excep(s"$r is odd number which is illegal for a tile row in tileRowStart method.")
    case r if r > topCenR =>
      excep(s"Row number $r is greater than top tile row $topCenR. There are $numTileRows rows. Exception in tileRowStart method.")
    case r if r < bottomCenR => excep(s"$r Row number less than bottom tile row in tileRowStart method.")
    case _ => unsafeRowsArray(row - bottomCenR + 1)
  }

  /** The end (or by default right) column number of the tile centre of the given row. Will throw on illegal values. */
  override def rowRightCenC(row: Int): Int = row match
  { case r if r.isOdd => excep(s"$r is odd number which is illegal for a tile row in tileRowEnd method.")
    case r if r > topCenR => excep(s"Row number $r is greater than top tile row $topCenR in tileRowEnd method.")
    case r if r < bottomCenR => excep(s"$r Row number less than $bottomCenR, the bottom tile row value in tileRowEnd method.")
    case _ => rowLeftCenC(row) + (rowNumTiles(row) - 1) * 4
  }

  override def hCenExists(r: Int, c: Int): Boolean = r match
  { case r if r > topCenR => false
    case r if r < bottomCenR => false
    case r => c >= rowLeftCenC(r) & c <= rowRightCenC(r)
  }
}

object HGridIrr
{
  /** Takes the top row number followed by pairs of the number of tiles in the row ad the tile centre start coordinate. */
  def apply(rMax: Int, cLenMins: (Int, Int) *): HGridIrr =
  { val array = new Array[Int](cLenMins.length * 2)
    val len = cLenMins.length
    val rMin = rMax - (len - 1) * 2
    iUntilForeach(len){ i =>
      val (rLen, cMin) = cLenMins(len - 1 - i)
      array(i * 2) = rLen
      array(i * 2 + 1) = cMin
    }
    new HGridIrr(rMin, array)
  }
}