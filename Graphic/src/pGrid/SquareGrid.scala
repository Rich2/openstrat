package ostrat
package pGrid
import geom._

/** Currently all SquareGrids are regular. */
case class SquareGrid(yTileMin: Int, yTileMax: Int, cTileMin: Int, cTileMax: Int) extends TileGridReg
{
  final override def xCen: Double = (cTileMin + cTileMax) / 2.0
  override def roordToVec2(roord: Roord): Vec2 = Vec2(roord.c, roord.y)
  override def sideRoordToRoordLine(sideRoord: Roord): RoordLine = SquareGrid.sideRoordToRoordLine(sideRoord)
  def rowTileLen: Int = ((cTileMax.roundDownToEven - cTileMin.roundUpToEven + 2) / 2).max0
  def numOfRows: Int = ((yTileMax.roundDownToEven - yTileMin + 2) / 2).max0
  def numOfTiles: Int = numOfRows * rowTileLen
  def cStep: Int = 2
  @inline override def index(y: Int, c: Int): Int = (y - yTileMin) / 2 * rowTileLen + (c - cTileMin) / 2

  @inline override def sideRoordsOfTile(tileRoord: Roord): Roords = SquareGrid.sideRoordsOfTile(tileRoord)

  override def xLeft: Double = cTileMin - 1
  override def xRight: Double = cTileMax + 1
  override def top: Double = yTileMax + 1
  override def bottom: Double = yTileMin - 1
  override def tileVertRoords(roord : Roord): Roords = SquareGrid.vertRoordsOfTile(roord)
  def isTileRoord(r: Roord): Boolean = r.y.isEven & r.c.isEven

  /** The active tiles without any PaintElems. */
  override def activeTiles: Arr[PolyActiveOnly] = map{ roord =>
    val vcs = tileVertRoords(roord)
    val vvs = vcs.map(r => roordToVec2(r))
    vvs.toPolygon.active(roord.toHexTile)
  }
  def rowForeachTile(y: Int)(f: Roord => Unit): Unit = iToForeach(cTileMin, cTileMax, 2)(c => f(Roord(y, c)))
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

  def sideRoordToRoordLine(y: Int, c: Int): RoordLine = (y %% 2, c %% 2) match
  { case (1, 0) => RoordLine(y, c - 1, y, c + 1)
    case (0, 1)=> RoordLine(y - 1, c, y + 1, c)
    case _ => excep("Invalid Square Roord for a side")
  }
}


