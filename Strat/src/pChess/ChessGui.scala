package ostrat
package pChess
import geom._, pCanv._, Colour._, pGrid._

case class ChessGui(canv: CanvasPlatform) extends CmdBarGui("Chess")
{
  implicit val grid = ChessStart.grid
  var statusText: String = "Welcome to Chess Gui"
  val scale = grid.fullDisplayScale(mainWidth, mainHeight)
  val darkSquareColour = Brown
  val lightSquareColour = Pink
  val tiles: GraphicElems = grid.mapPolygons{(r, p) =>
    val col = ife(r.yPlusC %% 4 == 0, darkSquareColour, lightSquareColour)
    val yStr: String = ('A' + r.y / 2 - 1).toChar.toString
    val cStr: String = ('0' + r.c / 2).toChar.toString
    p.fillText(col, yStr + cStr, 20) }
  var player = true

  val margin = 15

  val s1 = Refs(Queen, Rook)
  val p = Rook.scale(200)

  def bTurn = clickButton("Turn ", _ => {
    repaint()
    thisTop()
  })
  def thisTop(): Unit = reTop(Refs(bTurn, status))
  thisTop()
  def frame = (tiles).gridTrans(scale)
  def repaint() = mainRepaint(frame)
  repaint()
}