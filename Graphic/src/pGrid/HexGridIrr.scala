/* Copyright 2018-20 Richard Oliver. Licensed under Apache Licence version 2.0 */
package ostrat
package pGrid

/** This grid is irregular in the length of the Hex rows. The (0) value gives yTileMin. There are 2 more values for row. Each row from lowest
 *  to highest has two values the xMin for the row and the index into a data array for the first tile of the grid row.
 *  @param yTileMin The y vlaue for the bottom tile row of the TileGrid
 *  @param tileRowsStartEnd the Array contains 2 values per Tile Row, the cStart Tile and the cEnd Tile */
class HexGridIrr(override val yTileMin: Int, val tileRowsStartEnd: Array[Int]) extends HexGrid
{
  /** Number of rows of tiles. This will be different to the number of rows of sides and the number of rows of vertices. */
  override def numOfTileRows: Int = tileRowsStartEnd.length / 2

  /** An Array of Ints, 1 for each Tile Row, containing the Tile data Array index for the beginning of the tileRow. */
  val tileIndexArray: Array[Int] =
  { val res = new Array[Int](numOfTileRows)
    var count = 0
    iUntilForeach(0, numOfTileRows){ i =>
      res(i) = count
      val y = yTileMin + i * 2
      count += cRowLen(y)
    }
    res
  }

  /** Gives the index into a Tile Array for an irregular Hex Grid from its Roord. Use sideIndex and vertIndex methods to access Side and Vertex Arr /
   *  Array data. */
  def arrIndex(y: Int, c: Int): Int = tileIndexArray((y - yTileMin) / 2)  + (c - cRowStart(y)) / 4

  def numOfTiles: Int = iToFoldInt(yTileMin, yTileMax, 2) { (acc, y) => acc + cRowLen(y) }

  val sideRowIndex: Array[Int] =
  {
    val res = new Array[Int](ySideMax - ySideMin + 1)
    var count = 0
    iUntilForeach(0, numOfSideRows){ i =>
      res(i) = count
      val y = ySideMin + i
      val rowLen: Int = y match {
        case y if y == ySideMax => (cRowEnd(y - 1) - cRowStart(y - 1)) / 2 + 2
        case y if y == ySideMin => (cRowEnd(y + 1) - cRowStart(y + 1)) / 2 + 2
        case y if y.isEven => (cRowEnd(y) - cRowStart(y)) / 4 + 2
        case y => (cRowEnd(y - 1).max(cRowEnd(y + 1)) - cRowStart(y - 1).min(cRowStart(y + 1))) / 2 + 2
      }
      count += rowLen.max0
    }
    res
  }

  override def sideArrIndex(y: Int, c: Int): Int =
  { var count = 0
    var res: Int = -1
    sidesForeach{r =>
      if (r == Roord(y, c)) res = count
      count += 1
    }
    res
  }

  def cRowStart(y: Int): Int = tileRowsStartEnd(y - yTileMin)
  def cRowEnd(y: Int): Int = tileRowsStartEnd(y - yTileMin + 1)

  def cRowLen(y: Int): Int = ((cRowEnd(y) - cRowStart(y) + 4) / 4).max0

  def rowForeachTile(y: Int)(f: Roord => Unit): Unit = iToForeach(cRowStart(y), cRowEnd(y), 4) { c => f(Roord(y, c)) }

  @inline override def yTileMax: Int = yTileMin + tileRowsStartEnd.length - 2

  final override def cTileMin: Int = if (numOfTileRows == 0) 0
    else iToFoldInt(yTileMin + 2, yTileMax, 2, cRowStart(yTileMin) ) { (acc, y) => acc.min(cRowStart(y)) }

  def cTileMax: Int = if (numOfTileRows == 0) 0
  else iToFoldInt(yTileMin + 2, yTileMax, 2, cRowEnd(yTileMin) ) { (acc, y) => acc.max(cRowEnd(y)) }

  override def rowForeachSide(y: Int)(f: Roord => Unit): Unit = y match
  { case y if y == ySideMax => iToForeach(cRowStart(y - 1) - 1, cRowEnd(y - 1) + 1, 2){ c => f(Roord(y, c)) }
    case y if y == ySideMin => iToForeach(cRowStart(y + 1) - 1, cRowEnd(y + 1) + 1, 2){ c => f(Roord(y, c)) }
    case y if y.isEven => iToForeach(cRowStart(y) - 2, cRowEnd(y) + 2, 4){ c => f(Roord(y, c)) }
    case y => iToForeach(cRowStart(y - 1).min(cRowStart(y + 1)) - 1, cRowEnd(y - 1).max(cRowEnd(y + 1)) + 1, 2){ c => f(Roord(y, c)) }
  }

  override def rowForeachVert(y: Int)(f: Roord => Unit): Unit = y match
  { case y if y == ySideMax => iToForeach(cRowStart(y - 1) - 2, cRowEnd(y - 1) + 2, 2){ c => f(Roord(y, c)) }
    case y if y == ySideMin => iToForeach(cRowStart(y + 1) - 2, cRowEnd(y + 1) + 2, 2){ c => f(Roord(y, c)) }
    case y if y.isEven => excep("Illegal vertex Roord value. y can not be even.")
    case y => iToForeach(cRowStart(y - 1).min(cRowStart(y + 1)) - 2, cRowEnd(y - 1).max(cRowEnd(y + 1)) + 2, 2){ c => f(Roord(y, c)) }
  }
}

