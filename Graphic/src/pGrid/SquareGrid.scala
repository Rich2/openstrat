package ostrat
package pGrid
import geom._

/** Currently all SquareGrids are regular. */
case class SquareGrid(cTileMin: Int, cTileMax: Int, yTileMin: Int, yTileMax: Int) extends TileGridReg
{
  final override def xCen: Double = (cTileMin + cTileMax) / 2.0
  override def roordToVec2Abs(roord: Roord): Vec2 = Vec2(roord.c, roord.y)
  override def sideRoordToRoordLine(sideRoord: ostrat.pGrid.Roord): RoordLine = SquareGrid.sideRoordToRoordLine(sideRoord)
  def rowTileLen: Int = ((cTileMax.roundDownToEven - cTileMin.roundUpToEven + 2) / 2).max0
  def numOfRows: Int = ((yTileMax.roundDownToEven - yTileMin + 2) / 2).max0
  def numOfTiles: Int = numOfRows * rowTileLen

  override def foreachRoord(f: Roord => Unit): Unit = ijToForeach(yTileMin, yTileMax, 2)(cTileMin, cTileMax, 2)((y, x) => f(Roord(x, y)))
  @inline override def index(c: Int, y: Int): Int = (y - yTileMin) / 2 * rowTileLen + (c - cTileMin) / 2

  @inline override def sideRoordsOfTile(tileRoord: Roord): Roords = SquareGrid.sideRoordsOfTile(tileRoord)

  override def xLeft: Double = cTileMin - 1
  override def xRight: Double = cTileMax + 1
  override def top: Double = yTileMax + 1
  override def bottom: Double = yTileMin - 1
  override def tileVertRoords(roord : Roord): Roords = SquareGrid.vertRoordsOfTile(roord)
}

object SquareGrid
{
  val vertRoordsOfTile00: Roords = Roords(1 rr 1,  -1 rr 1,  -1 rr -1, 1 rr -1)
  def vertRoordsOfTile(y: Int, c: Int): Roords = vertRoordsOfTile(y rr c)
  def vertRoordsOfTile(inp: Roord): Roords = vertRoordsOfTile00.pMap(inp + _)
  val adjTileRoordsOfTile00: Roords = Roords(2 rr 0, 2 rr 2, 0 rr 2, -2 rr 2, -2 rr 0, -2 rr -2, 0 rr -2)
  def adjTileRoordsOfTile(tileRoord: Roord): Roords = adjTileRoordsOfTile00.pMap(tileRoord + _)
  def sideRoordsOfTile(inp: Roord): Roords = Roords(inp.addY(1), inp.addC(1), inp.subY(1), inp.subC(1))

  sealed trait PathDirn
  object Rt extends PathDirn
  object Lt extends PathDirn
  object Up extends PathDirn
  object Dn extends PathDirn
  def sideRoordToLineRel(sideRoord: Roord, scale: Double, relPosn: Vec2): Line2 =
    sideRoordToRoordLine(sideRoord).toLine2(r => (Vec2(r.c, r.y) -relPosn) * scale)

  def sideRoordToRoordLine(sideRoord: Roord): RoordLine = sideRoordToRoordLine(sideRoord.y, sideRoord.c)

  def sideRoordToRoordLine(x: Int, y: Int): RoordLine = (x %% 2, y %% 2) match
  { case (1, 0) => RoordLine(x, y + 1, x, y - 1)
    case (0, 1)=> RoordLine(x - 1, y, x + 1, y)
    case _ => excep("Invalid Square Roord for a side")
  }
}


