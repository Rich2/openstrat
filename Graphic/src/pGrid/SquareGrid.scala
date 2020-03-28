package ostrat
package pGrid
import geom._

/** Currently all SquareGrids are regular. */
case class SquareGrid(cTileMin: Int, cTileMax: Int, yTileMin: Int, yTileMax: Int) extends TileGridReg
{
  final override def xCen: Double = (cTileMin + cTileMax) / 2.0
  override def coodToVec2Abs(cood: Cood): Vec2 = Vec2(cood.xi, cood.yi)
  override def sideCoodToCoodLine(sideCood: ostrat.pGrid.Cood): CoodLine = SquareGrid.sideCoodToCoodLine(sideCood)
  def rowTileLen: Int = ((cTileMax.roundDownToEven - cTileMin.roundUpToEven + 2) / 2).max0
  def numOfRows: Int = ((yTileMax.roundDownToEven - yTileMin + 2) / 2).max0
  def numOfTiles: Int = numOfRows * rowTileLen

  override def foreachTileCood(f: Cood => Unit): Unit = ijToForeach(yTileMin, yTileMax, 2)(cTileMin, cTileMax, 2)((y, x) => f(Cood(x, y)))
  @inline override def index(c: Int, y: Int): Int = (y - yTileMin) / 2 * rowTileLen + (c - cTileMin) / 2

  @inline override def sideCoodsOfTile(tileCood: Cood): Coods = SquareGrid.sideCoodsOfTile(tileCood)

  override def xLeft: Double = cTileMin - 1
  override def xRight: Double = cTileMax + 1
  override def top: Double = yTileMax + 1
  override def bottom: Double = yTileMin - 1
  override def tileVertCoods(cood : Cood): Coods = SquareGrid.vertCoodsOfTile(cood)
}

object SquareGrid
{
  val vertCoodsOfTile00: Coods = Coods(1 cc 1,  1 cc -1,  -1 cc -1, -1 cc 1)
  def vertCoodsOfTile(x: Int, y: Int): Coods = vertCoodsOfTile(x cc y)
  def vertCoodsOfTile(inp: Cood): Coods = vertCoodsOfTile00.pMap(inp + _)
  val adjTileCoodsOfTile00: Coods = Coods(0 cc 2, 2 cc 2, 2 cc 0, 2 cc -2, 0 cc -2, -2 cc -2, -2 cc 0)
  def adjTileCoodsOfTile(tileCood: Cood): Coods = adjTileCoodsOfTile00.pMap(tileCood + _)
  def sideCoodsOfTile(inp: Cood): Coods = Coods(inp.addY(1), inp.addX(1), inp.subY(1), inp.subX(1))

  sealed trait PathDirn
  object Rt extends PathDirn
  object Lt extends PathDirn
  object Up extends PathDirn
  object Dn extends PathDirn
  def sideCoodToLineRel(sideCood: Cood, scale: Double, relPosn: Vec2): Line2 =
    sideCoodToCoodLine(sideCood).toLine2(c => (Vec2(c.xi, c.yi) -relPosn) * scale)

  def sideCoodToCoodLine(sideCood: Cood): CoodLine = sideCoodToCoodLine(sideCood.xi, sideCood.yi)

  def sideCoodToCoodLine(x: Int, y: Int): CoodLine = (x %% 2, y %% 2) match
  { case (1, 0) => CoodLine(x, y + 1, x, y - 1)
    case (0, 1)=> CoodLine(x - 1, y, x + 1, y)
    case _ => excep("Invalid Square Cood for a side")
  }
}