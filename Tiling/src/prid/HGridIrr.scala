/* Copyright 2018-21 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package prid

/** An irregular hex grid. This grid is irregular in the length of the hex rows. */
trait HGridIrr extends HGrid
{

}

object HGridIrr
{

}

/** An irregular hex grid, where the rows have different lengths and irregular start row coordinates. This is backed by an Array[Int] The (0) value
 *  gives the number of tile centre [[HCen]] rows. The (1) value gives the rTileMin the bottom row coordinate. There are 2 more values for row. Each
 *  row from lowest to highest has two values length of the row in the number of tile centres [[HCen]]s and the rTileMin coordinate for the row.
 * @constructor creates a new HexGridIrr with a defined grid.
 * @param yTileMin         The y value for the bottom tile row of the TileGrid
 * @param tileRowsStartEnd the Array contains 2 values per Tile Row, the cStart Tile and the cEnd Tile */
case class HGridIrrRowLengths(unsafeArray: Array[Int]) extends HGrid
{
  override def rCenMin: Int = iUntilFoldSumInt(0, numCenRows){ i => unsafeArray(i * 2 + 3) }

  override def rCenMax: Int = ???

  /** Minimum c or column value. This is not called x because in some grids there is not a 1 to 1 ratio from column coordinate to x. */
  override def cTileMin: Int = ???

  /** Maximum c or column value. This is not called x because in some grids there is not a 1 to 1 ratio from column coordinate to x. */
  override def cTileMax: Int = ???

  override def numCenRows: Int = unsafeArray(0)
  override def numRow0s: Int = ???
  override def numRow2s: Int = ???

  /** The total number of Tiles in the tile Grid. */
  override def numCens: Int = ???

  def rowForeach(r: Int)(f: HCen => Unit): Unit = ???

  override def rowIForeach(r: Int, count: Int)(f: (HCen, Int) => Unit): Int = ???

  override def width: Double = ???

  override def height: Double = ???

  override def rowForeachSide(r: Int)(f: HSide => Unit): Unit = ???

  override def arrIndex(r: Int, c: Int): Int = ???

  override def rowNumCens(row: Int): Int = ???

  override def cRowCenMin(row: Int): Int = ???

  override def hCenExists(r: Int, c: Int): Boolean = ???

  def cSideRowMin(r: Int): Int = ???

  /** Gives the index into an Arr / Array of Tile data from its tile [[HCen]]. Use sideIndex and vertIndex methods to access Side and Vertex Arr /
   * Array data. */
  override def sideArrIndex(r: Int, c: Int): Int = sideRowIndexArray(r - rSideMin) + (c - cSideRowMin(r)) / r.ifEvenElse(4, 2)

  /** An Array of index values into an Array of Side data. 1 Int value for the start index of each Row. */
  val sideRowIndexArray: Array[Int] = ???
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
}