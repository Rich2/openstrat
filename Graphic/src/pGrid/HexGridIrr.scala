package ostrat
package pGrid

class HexGridIrr(val indexArr: Array[Int]) extends AnyVal with HexGrid
{
  def cTileMax: Int = ???
  def cTileMin: Int = ???
  def index(c: Int, y: Int): Int = ???
  def numOfTiles: Int = ???
  def tilesAllForeach(f: ostrat.pGrid.Cood => Unit): Unit = ???
  def yTileMax: Int = ???
  def yTileMin: Int = ???
}
