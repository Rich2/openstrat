package ostrat
package pGrid

/** This grid is irregular in the length of the Hex rows. The (0) value gives the number of rows. The (1) value gives yTileMin. Each row from lowest
 *  to highest has two values the xMin for the row and the index into a data array for the first tile of the grid row. */
class HexGridIrr(val indexArr: Array[Int]) extends AnyVal with HexGrid
{
  override def numOfRows: Int = indexArr(0)
  @inline override def yTileMin: Int = indexArr(1)
  def index(c: Int, y: Int): Int = (c - indexArr(y - yTileMin + 2)) / 4
  def numOfTiles: Int = iToFoldInt(0, numOfRows, 2){(acc, i) => acc + indexArr(i + 2)}
  def xRowStart(y: Int): Int = indexArr(y + 2)
  def tilesAllForeach(f: ostrat.pGrid.Cood => Unit): Unit = tileRowsAllForeach{y => iToForeach(0, 0){x => f(Cood(x, y)) } }

  @inline override def yTileMax: Int = yTileMin + (numOfRows - 1).max0 * 2

}

