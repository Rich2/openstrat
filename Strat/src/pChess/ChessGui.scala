package ostrat
package pChess
import geom._, pCanv._, Colour._, pGrid._

case class ChessGui(canv: CanvasPlatform, scen: ChessScen) extends CmdBarGui("Chess")
{ implicit val grid = scen.grid
  var statusText: String = "Welcome to Chess Gui"
  val scale = grid.fullDisplayScale(mainWidth, mainHeight)
  val darkSquareColour = DarkGreen
  val lightSquareColour = LightBlue
  val tiles: GraphicElemFulls = grid.mapRPolygons{ (r, p) =>
    val col = ife(r.yPlusC %% 4 == 0, darkSquareColour, lightSquareColour)
    val yStr: String = ('A' + r.y / 2 - 1).toChar.toString
    val cStr: String = ('0' + r.c / 2).toChar.toString
    p.fillText(col, yStr + cStr, 20) }
  val pieces = scen.pieces.mapSomes((r, p) => p.piece().slate(r.gridVec2).fillDraw(p.player.colour, 2.0, p.player.contrastBW))

  def bTurn = clickButton("Turn ", _ => {
    repaint()
    thisTop()
  })
  def thisTop(): Unit = reTop(Arr(bTurn, status))
  thisTop()
  def frame = (tiles ++ pieces).gridScaleOld(scale)
  def repaint() = mainRepaint(frame)
  repaint()
}