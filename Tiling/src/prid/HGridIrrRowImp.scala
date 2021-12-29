/* Copyright 2018-21 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package prid

/** An irregular hex grid, where the rows have different lengths and irregular start row coordinates. This is backed by an Array[Int]. There are 2
* values for each row. Each row from lowest to highest has two values. The rTileMin coordinate for the row followed by the length of the row in the
* number of tile centres [[HCen]]s. */
abstract class HGridIrr() extends HGrid

/** An irregular hex grid, where the rows have different lengths and irregular start row coordinates. This is backed by an Array[Int]. The length of
 *  this Array is twice the number of tile rows in the grid. Each row from lowest to highest has two values length of the row in the number of tile
 *  centres [[HCen]]s and the rTileMin coordinate for the row.
 * @constructor creates a new HexGridIrr with a defined grid.
 * @param yTileMin         The y value for the bottom tile row of the TileGrid
 * @param tileRowsStartEnd the Array contains 2 values per Tile Row, the cStart Tile and the cEnd Tile */
class HGridIrrRows(val bottomCenRow: Int, val unsafeRowsArray: Array[Int]) extends HGridIrr
{
  final val numTileRows: Int = unsafeRowsArray.length / 2

  final override def topCenRow: Int = bottomCenRow + numTileRows * 2 - 2

  /** The total number of hex tiles in the tile Grid. This is determined from the unsafeRowsArray */
  final override def numTiles: Int = iUntilFoldInt(0, unsafeRowsArray.length, 2)((acc, i) => acc + unsafeRowsArray(i))

  final override val numSides: Int =
  { var count = 0
    sidesForeach(r => count += 1)
    count
  }
  /** The [[HCenOrSide]] coordinate centre for this hex grid. */
  override def coordCen: HCoord = HCoord(rCen, cCen)

  override def rowForeachSide(r: Int)(f: HSide => Unit): Unit = r match
  {
    case r if r.isEven =>
    { rowForeach(r){ hc => f(HSide(hc.r, hc.c -2)) }
      if (rowNumTiles(r) > 0) f(HSide(r, rowCenRight(r) + 2))
    }

    case r if r == sideRowBottom => rowForeach(r + 1){ hc => f(HSide(r, hc.c - 1)); f(HSide(r, hc.c + 1)) }
    case r if r == sideRowTop => rowForeach(r - 1){ hc => f(HSide(r, hc.c - 1)); f(HSide(r, hc.c + 1)) }

    case r =>
    { val start = rowCenLeft(r - 1).min(rowCenLeft(r + 1)) - 1
      val end = rowCenRight(r - 1).max(rowCenRight(r + 1)) + 1
      iToForeach(start, end, 2){ c => f(HSide(r, c)) }
    }
  }

  def cSideRowMin(r: Int): Int = ???

  /** Gives the index into an Arr / Array of Tile data from its tile [[HCen]]. Use sideIndex and vertIndex methods to access Side and Vertex Arr /
   * Array data. */
  override def sideArrIndex(r: Int, c: Int): Int = sideRowIndexArray(r - bottomSideRow) + (c - cSideRowMin(r)) / r.ifEvenElse(4, 2)

  /** An Array of index values into an Array of Side data. 1 Int value for the start index of each Row. */
  val sideRowIndexArray: Array[Int] = new Array[Int](0)
  /*{
    val res = new Array[Int](rSideMax - rSideMin + 1)
    var count = 0
    iUntilForeach(0, numOfSideRows){ i =>
      res(i) = count
      val y = rSideMin + i
      val rowLen: Int = y match {
        case y if y == rSideMax => (cRowEnd(y - 1) - cRowStart(y - 1)) / 2 + 2
        case y if y == ySideMin => (cRowEnd(y + 1) - cRowStart(y + 1)) / 2 + 2
        case y if y.isEven => (cRowEnd(y) - cRowStart(y)) / 4 + 2
        case y => (cRowEnd(y - 1).max(cRowEnd(y + 1)) - cRowStart(y - 1).min(cRowStart(y + 1))) / 2 + 2
      }
      count += rowLen.atMost0
    }
    res
  }*/

  override def leftCenCol: Int = foldRows(Int.MaxValue - 1)((acc, r) => acc.min(rowCenLeft(r)))
  override def rightCenCol: Int = foldRows(Int.MinValue )((acc, r) => acc.max(rowCenRight(r)))

  override def numRow0s: Int = numTileRows.ifMod(bottomCenRow.div4Rem0, _.roundUpToEven) / 2
  override def numRow2s: Int = numTileRows.ifMod(bottomCenRow.div4Rem2, _.roundUpToEven) / 2

  @inline protected def unsafeRowArrayindex(row: Int): Int = row - bottomCenRow

  override def arrIndex(r: Int, c: Int): Int =
  { val wholeRows = iUntilFoldInt(bottomCenRow, r, 2){ (acc, r) => acc + rowNumTiles(r) }
    wholeRows + (c - rowCenLeft(r)) / 4
  }

  override def rowNumTiles(row: Int): Int = unsafeRowsArray(row - bottomCenRow)

  /** Foreachs over each tile centre of the specified row applying the side effecting function to the [[HCen]]. */
  def rowForeach(r: Int)(f: HCen => Unit): Unit = iToForeach(rowCenLeft(r), rowCenRight(r), 4){ c => f(HCen(r, c))}

  override def rowIForeach(r: Int, initCount: Int = 0)(f: (HCen, Int) => Unit): Int = {
    var count = initCount
    iToForeach(rowCenLeft(r), rowCenRight(r), 4){ c => f(HCen(r, c), count); count += 1 }
    count
  }

  /** The start (or by default left column) of the tile centre of the given row. Will throw on illegal values. */
  override def rowCenLeft(row: Int): Int = row match
  { case r if r.isOdd => excep(s"$r is odd number which is illegal for a tile row in tileRowStart method.")
    case r if r > topCenRow =>
      excep(s"Row number $r is greater than top tile row $topCenRow. There are $numTileRows rows. Exception in tileRowStart method.")
    case r if r < bottomCenRow => excep(s"$r Row number less than bottom tile row in tileRowStart method.")
    case _ => unsafeRowsArray(row - bottomCenRow + 1)
  }

  /** The end (or by default right) column number of the tile centre of the given row. Will throw on illegal values. */
  override def rowCenRight(row: Int): Int = row match
  { case r if r.isOdd => excep(s"$r is odd number which is illegal for a tile row in tileRowEnd method.")
    case r if r > topCenRow => excep(s"Row number $r is greater than top tile row $topCenRow in tileRowEnd method.")
    case r if r < bottomCenRow => excep(s"$r Row number less than bottom tile row value in tileRowEnd method.")
    case _ => rowCenLeft(row) + (rowNumTiles(row) - 1) * 4
  }

  override def hCenExists(r: Int, c: Int): Boolean = r match
  { case r if r > topCenRow => false
    case r if r < bottomCenRow => false
    case r => c >= rowCenLeft(r) & c <= rowCenRight(r)
  }
  override def width: Double = (rightCenCol - leftCenCol + 4) / Sqrt3
  override def height: Double = topCenRow - bottomCenRow + 3

}

object HGridIrrRows
{
  def apply(rMax: Int, cMinMaxs: (Int, Int) *): HGridIrrRows =
  { val array = new Array[Int](cMinMaxs.length * 2)
    val len = cMinMaxs.length
    val rMin = rMax - (len - 1) * 2
    iUntilForeach(0, len){ i =>
      val (rLen, cMin) = cMinMaxs(len - 1 - i)
      array(i * 2) = rLen
      array(i * 2 + 1) = cMin
    }
    val arrayLen = array.length
    debvar(arrayLen)
    new HGridIrrRows(rMin, array)
  }
}