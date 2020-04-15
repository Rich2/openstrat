package ostrat
package pGrid

/** This grid is irregular in the length of the Hex rows. The (0) value gives yTileMin. There are 2 more values for row. Each row from lowest
 *  to highest has two values the xMin for the row and the index into a data array for the first tile of the grid row. */
class HexGridIrr(override val yTileMin: Int, val indexArr: Array[Int]) extends HexGrid
{
  override def numOfRows: Int = indexArr.length / 2
  val tileIndexArray: Array[Int] =
  {
    val res = new Array[Int](numOfRows)
    var count = 0
    iUntilForeach(0, numOfRows){ i =>
      res(i) = count
      val y = yTileMin + i * 2
      count += cRowLen(y)
    }
    res
  }

  def index(y: Int, c: Int): Int = tileIndexArray((y - yTileMin) / 2)  + (c - cRowStart(y)) / 4
  def numOfTiles: Int = iToFoldInt(yTileMin, yTileMax, 2) { (acc, y) => acc + cRowLen(y) }

  val sideIndexArray: Array[Int] =
  {
    val res = new Array[Int](ySideMax - ySideMin + 1)
    var count = 0
    iUntilForeach(0, numOfSideRows){ i =>
      res(i) = count
      val y = ySideMin + i //* 2
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

  def cRowStart(y: Int): Int = indexArr(y - yTileMin)
  def cRowEnd(y: Int): Int = indexArr(y - yTileMin + 1)

  def cRowLen(y: Int): Int = ((cRowEnd(y) - cRowStart(y) + 4) / 4).max0

  def rowForeachTile(y: Int)(f: Roord => Unit): Unit = iToForeach(cRowStart(y), cRowEnd(y), 4) { c => f(Roord(y, c)) }

  @inline override def yTileMax: Int = yTileMin + indexArr.length - 2

  final override def cTileMin: Int = if (numOfRows == 0) 0
    else iToFoldInt(yTileMin + 2, yTileMax, 2, cRowStart(yTileMin) ) { (acc, y) => acc.min(cRowStart(y)) }

  def cTileMax: Int = if (numOfRows == 0) 0
  else iToFoldInt(yTileMin + 2, yTileMax, 2, cRowEnd(yTileMin) ) { (acc, y) => acc.max(cRowEnd(y)) }

  override def rowForeachSide(y: Int)(f: Roord => Unit): Unit = y match
  { case y if y == ySideMax => iToForeach(cRowStart(y - 1) - 1, cRowEnd(y - 1) + 1, 2){ c => f(Roord(y, c)) }
    case y if y == ySideMin => iToForeach(cRowStart(y + 1) - 1, cRowEnd(y + 1) + 1, 2){ c => f(Roord(y, c)) }
    case y if y.isEven => iToForeach(cRowStart(y) - 2, cRowEnd(y) + 2, 4){ c => f(Roord(y, c)) }
    case y => iToForeach(cRowStart(y - 1).min(cRowStart(y + 1)) - 1, cRowEnd(y - 1).max(cRowEnd(y + 1)) + 1, 2){ c => f(Roord(y, c)) }
  }

  override def rowForeachVert(y: Int)(f: Roord => Unit): Unit = iToForeach(cRowStart(y) - 2, cRowEnd(y) + 2, 2)(c => f(Roord(y, c)))
}

