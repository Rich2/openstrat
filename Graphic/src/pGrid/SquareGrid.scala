package ostrat
package pGrid
import geom._

/** Currently all SquareGrids are regular. */
case class SquareGrid(xTileMin: Int, xTileMax: Int, yTileMin: Int, yTileMax: Int) extends TileGridReg
{
  def cen = Vec2(xCen, yCen)
  //def yCen: Double = (yTileMin + yTileMax) / 2.0
  def xCen: Double = (xTileMin + xTileMax) / 2.0
  def rowTileLen: Int = ((xTileMax.roundDownToEven - xTileMin.roundUpToEven + 2) / 2).min0
  def numOfRows: Int = ((yTileMax.roundDownToEven - yTileMin + 2) / 2).min0
  def numOfTiles: Int = numOfRows * numOfTiles

  override def tilesAllForeach(f: Cood => Unit): Unit = ijToForeach(yTileMin, yTileMax, 2)(xTileMin, xTileMax, 2)((y, x) => f(Cood(x, y)))
  @inline override def index(x: Int, y: Int): Int = (y - yTileMin) / 2 * rowTileLen + (x - xTileMin) / 2

  @inline override def sideCoodsOfTile(tileCood: Cood): Coods = SquareGrid.sideCoodsOfTile(tileCood)
}

object SquareGrid
{
  val vertCoodsOfTile00: Coods = Coods(1 cc 1,  1 cc -1,  -1 cc -1, -1 cc 1)
  def vertCoodsOfTile(x: Int, y: Int): Coods = vertCoodsOfTile(x cc y)
  def vertCoodsOfTile(inp: Cood): Coods = vertCoodsOfTile00.pMap(inp + _)
  val adjTileCoodsOfTile00: Coods = Coods(0 cc 2, 2 cc 2, 2 cc 0, 2 cc -2, 0 cc -2, -2 cc -2, -2 cc 0)
  def adjTileCoodsOfTile(tileCood: Cood): Coods = adjTileCoodsOfTile00.pMap(tileCood + _)
  def sideCoodsOfTile(inp: Cood): Coods = Coods(inp.addY(1), inp.addX(1), inp.subY(1), inp.subY(1))

  sealed trait PathDirn
  object Rt extends PathDirn
  object Lt extends PathDirn
  object Up extends PathDirn
  object Dn extends PathDirn

  def vertCoodLineOfSide(sideCood: Cood): CoodLine = vertCoodLineOfSide(sideCood.x, sideCood.y)

  def vertCoodLineOfSide(x: Int, y: Int): CoodLine = (x %% 2, y %% 2) match
  { case (1, 0) => CoodLine(x, y + 1, x, y - 1)
    case (0, 1)=> CoodLine(x - 1, y, x + 1, y)
    case _ => excep("Invalid Square Cood for a side")
  }
}
