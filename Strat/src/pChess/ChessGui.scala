package ostrat
package pChess
import geom._, pCanv._, Colour._

case class ChessGui(canv: CanvasPlatform) extends CmdBarGui("Chess")
{
  val grid = ChessStart.grid
  var statusText: String = "Welcome to Chess Gui"
  var player = true

  val darkSquareColour = Brown
  val lightSquareColour = Pink

  val margin = 15

  val s1 = Refs(Queen, Rook)
  val p = Rook.scale(200)
  
  val stuff = Refs(p.fill(DarkRed))
  val frame = ChessStart.grid
  def bTurn = clickButton("Turn ", _ => {
    //repaint()
    thisTop()
  })
  def thisTop(): Unit = reTop(Refs(bTurn, status))
  thisTop()
  //repaint(stuff)

}
