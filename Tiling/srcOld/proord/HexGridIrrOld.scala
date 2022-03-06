/* Copyright 2018-21 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package pGrid

/** This grid is irregular in the length of the Hex rows. The (0) value gives yTileMin. There are 2 more values for row. Each row from lowest
 *  to highest has two values the xMin for the row and the index into a data array for the first tile of the grid row. *
 *  @constructor creates a new HexGridIrr with a defined grid.
 *  @param yTileMin The y value for the bottom tile row of the TileGrid
 *  @param tileRowsStartEnd the Array contains 2 values per Tile Row, the cStart Tile and the cEnd Tile */
class HexGridIrrOld(override val yTileMin: Int, val tileRowsStartEnd: Array[Int]) extends HexGridOld
{
  /** Number of rows of tiles. This will be different to the number of rows of sides and the number of rows of vertices. */
  override def numOfTileRows: Int = tileRowsStartEnd.length / 2

  /** Return the Side Row start for the given Row y value. */
  override def cSideRowMin(y: Int): Int = y match {
    case y if y == ySideMax => cRowStart(y - 1) - 1
    case y if y == ySideMin => cRowStart(y + 1) - 1
    case y if y.isEven => cRowStart(y) - 2
    case y => cRowStart(y - 1).min(cRowStart(y + 1) - 1)
  }

  /** An Array of index values into an Array of Tile data 1 Int index value for each Tile Row, containing the Tile data Array index for the beginning
   *  of the tileRow. */
  val tileRowIndexArray: Array[Int] =
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
  def arrIndex(y: Int, c: Int): Int = tileRowIndexArray((y - yTileMin) / 2)  + (c - cRowStart(y)) / 4

  /** The number of Tiles in the TileGrid. */
  def numOfTiles: Int = iToFoldInt(yTileMin, yTileMax, 2) { (acc, y) => acc + cRowLen(y) }

  /** An Array of index values into an Array of Side data. 1 Int value for the start index of each Row. */
  val sideRowIndexArray: Array[Int] =
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
      count += rowLen.min0
    }
    res
  }

  /** Method gives the index into an Arr / Array of Side Data for a given Side Roord. */
  override def sideArrIndex(y: Int, c: Int): Int = sideRowIndexArray(y - ySideMin) + (c - cSideRowMin(y)) / y.ifEvenElse(4, 2)

  /** c Tile Row start value for a given y Row.  */
  def cRowStart(y: Int): Int = tileRowsStartEnd(y - yTileMin)

  /** c Tile Row End value for a given y Row. */
  def cRowEnd(y: Int): Int = tileRowsStartEnd(y - yTileMin + 1)

  /** Tile Row length for a give n y Row. */
  def cRowLen(y: Int): Int = ((cRowEnd(y) - cRowStart(y) + 4) / 4).min0

  /** foreach Tile in a given Row, calls the effectful function on the Tiles Roord. */
  def rowForeachTile(y: Int)(f: Roord => Unit): Unit = iToForeach(cRowStart(y), cRowEnd(y), 4) { c => f(Roord(y, c)) }

  /** The maximum y Row value for this HexGridIrr. */
  @inline override def yTileMax: Int = yTileMin + tileRowsStartEnd.length - 2

  /** Minimum c or column value. This is not called x because in some grids there is not a 1 to 1 ratio from column coordinate to x. */
  final override def cTileMin: Int = if (numOfTileRows == 0) 100
    else iToFoldInt(yTileMin + 2, yTileMax, 2, cRowStart(yTileMin) ) { (acc, y) => acc.min(cRowStart(y)) }

  /** Maximum c or column value. This is not called x because in some grids there is not a 1 to 1 ratio from column coordinate to x. */
  def cTileMax: Int = if (numOfTileRows == 0) -100
  else iToFoldInt(yTileMin + 2, yTileMax, 2, cRowEnd(yTileMin) ) { (acc, y) => acc.max(cRowEnd(y)) }

  /** foreachs over each Side's Roord in the given Row. Users will not normally need to access this method directly. */
  override def rowForeachSide(y: Int)(f: Roord => Unit): Unit = y match
  { case y if y == ySideMax => iToForeach(cRowStart(y - 1) - 1, cRowEnd(y - 1) + 1, 2){ c => f(Roord(y, c)) }
    case y if y == ySideMin => iToForeach(cRowStart(y + 1) - 1, cRowEnd(y + 1) + 1, 2){ c => f(Roord(y, c)) }
    case y if y.isEven => iToForeach(cRowStart(y) - 2, cRowEnd(y) + 2, 4){ c => f(Roord(y, c)) }
    case y => iToForeach(cRowStart(y - 1).min(cRowStart(y + 1)) - 1, cRowEnd(y - 1).max(cRowEnd(y + 1)) + 1, 2){ c => f(Roord(y, c)) }
  }

  /** foreach Vertice's Roord in the vertex row applies the effectful function. */
  override def rowForeachVert(y: Int)(f: Roord => Unit): Unit = y match
  { case y if y == ySideMax => iToForeach(cRowStart(y - 1) - 2, cRowEnd(y - 1) + 2, 2){ c => f(Roord(y, c)) }
    case y if y == ySideMin => iToForeach(cRowStart(y + 1) - 2, cRowEnd(y + 1) + 2, 2){ c => f(Roord(y, c)) }
    case y if y.isEven => excep("Illegal vertex Roord value. y can not be even.")
    case y => iToForeach(cRowStart(y - 1).min(cRowStart(y + 1)) - 2, cRowEnd(y - 1).max(cRowEnd(y + 1)) + 2, 2){ c => f(Roord(y, c)) }
  }
}