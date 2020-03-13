package ostrat
package pGrid
import geom._

/** Currently all SquareGrids are regular. */
case class SquareGrid(xTileMin: Int, xTileMax: Int, yTileMin: Int, yTileMax: Int) extends TileGridReg
{
  def cen = Vec2(xCen, yCen)
  def yCen: Double = (yTileMin + yTileMax) / 2.0
  def rowTileLen: Int = ((xTileMax.roundDownToEven - xTileMin.roundUpToEven + 2) / 2).min0
  def numOfRows: Int = ((yTileMax.roundDownToEven - yTileMin + 2) / 2).min0
  def numOfTiles: Int = numOfRows * numOfTiles

  override def tilesAllForeach(f: Cood => Unit): Unit = ijToForeachOld(yTileMin, yTileMax, 2)(xTileMin, xTileMax, 2)((y, x) => f(Cood(x, y)))
  @inline override def index(x: Int, y: Int): Int = (y - yTileMin) / 2 * rowTileLen + (x - xTileMin) / 2
}
