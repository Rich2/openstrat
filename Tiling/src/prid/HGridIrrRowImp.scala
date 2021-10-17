/* Copyright 2018-21 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package prid

/** An irregular hex grid, where the rows have different lengths and irregular start row coordinates. This is backed by an Array[Int] The (0) value
 *  gives the number of tile centre [[HCen]] rows. The (1) value gives the rTileMin the bottom row coordinate. There are 2 more values for row. Each
 *  row from lowest to highest has two values length of the row in the number of tile centres [[HCen]]s and the rTileMin coordinate for the row.
 * @constructor creates a new HexGridIrr with a defined grid.
 * @param yTileMin         The y value for the bottom tile row of the TileGrid
 * @param tileRowsStartEnd the Array contains 2 values per Tile Row, the cStart Tile and the cEnd Tile */
class HGridIrrRowLengths(val unsafeArray: Array[Int]) extends HGridIrr
{
  /** Foreachs over each tile centre of the specified row applying the side effecting function to the [[HCen]]. */
  def rowForeach(r: Int)(f: HCen => Unit): Unit = iToForeach(tileRowStart(r), tileRowEnd(r), 4){ c => f(HCen(r, c))}

  override def rowIForeach(r: Int, initCount: Int = 0)(f: (HCen, Int) => Unit): Int = {
    var count = initCount
    iToForeach(tileRowStart(r), tileRowEnd(r), 4){ c => f(HCen(r, c), count); count += 1 }
    count
  }

  override def width: Double = 22

  override def height: Double = topTileRow - bottomTileRow

  override def rowForeachSide(r: Int)(f: HSide => Unit): Unit = r match
  {
    case r if r.isEven =>
    { rowForeach(r){ hc => f(HSide(hc.r, hc.c -2)) }
      if (rowNumTiles(r) > 0) f(HSide(r, tileRowEnd(r) + 2))
    }

    case r if r == sideRowBottom => rowForeach(r + 1){ hc => f(HSide(r, hc.c - 1)); f(HSide(r, hc.c + 1)) }
    case r if r == sideRowTop => rowForeach(r - 1){ hc => f(HSide(r, hc.c - 1)); f(HSide(r, hc.c + 1)) }

    case r =>
    { val start = tileRowStart(r - 1).min(tileRowStart(r + 1)) - 1
      val end = tileRowEnd(r - 1).max(tileRowEnd(r + 1)) + 1
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
}

object HGridIrrRowLengths
{
  def apply(rMax: Int, cMinMaxs: (Int, Int) *): HGridIrrRowLengths =
  { val array = new Array[Int](cMinMaxs.length * 2 + 2)
    val len = cMinMaxs.length
    array(0) = len
    array(1) = rMax - (len - 1) * 2
    iUntilForeach(0, len){ i =>
      val (rLen, cMin) = cMinMaxs(len - 1 - i)
      array(i * 2 + 2) = rLen
      array(i * 2 + 3) = cMin
    }
    new HGridIrrRowLengths(array)
  }
}