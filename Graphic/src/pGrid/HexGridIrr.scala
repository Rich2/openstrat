package ostrat
package pGrid

/** This grid is irregular in the length of the Hex rows. The (0) value gives yTileMin. There are 2 more values for row. Each row from lowest
 *  to highest has two values the xMin for the row and the index into a data array for the first tile of the grid row. */
class HexGridIrr(override val yTileMin: Int, val indexArr: Array[Int]) extends HexGrid
{
  override def numOfRows: Int = indexArr.length / 2
  def index(y: Int, c: Int): Int = iUntilFoldInt(yTileMin, y, 2, (c - cRowStart(y)) / 4 ) { (acc, y) =>  acc + cRowLen(y) }
  def numOfTiles: Int = iToFoldInt(yTileMin, yTileMax, 2) { (acc, y) => acc + cRowLen(y) }
  def cRowStart(y: Int): Int = indexArr(y - yTileMin)
  def cRowEnd(y: Int): Int = indexArr(y - yTileMin + 1)

  def cRowLen(y: Int): Int = ((cRowEnd(y) - cRowStart(y) + 4) / 4).max0

  def foreach(f: Roord => Unit): Unit = ForeachRow{ y => iToForeach(cRowStart(y), cRowEnd(y), 4) { c => f(Roord(y, c)) } }

  @inline override def yTileMax: Int = yTileMin + indexArr.length - 2

  final override def cTileMin: Int = if (numOfRows == 0) 0
    else iToFoldInt(yTileMin + 2, yTileMax, 2, cRowStart(yTileMin) ) { (acc, y) => acc.min(cRowStart(y)) }

  def cTileMax: Int = if (numOfRows == 0) 0
  else iToFoldInt(yTileMin + 2, yTileMax, 2, cRowEnd(yTileMin) ) { (acc, y) => acc.max(cRowEnd(y)) }
}

