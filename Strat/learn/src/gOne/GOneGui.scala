package ostrat
package gOne
import pCanv._, geom._, pGrid._

/** Graphical user interface for GOne example game. */
case class GOneGui(canv: CanvasPlatform, scenStart: OneScen) extends CmdBarGui("Game One Gui")
{ var scen = scenStart
  var statusText = "Let click on Player to select. Right click on adjacent Hex to set move."
  implicit def grid = scen.grid
  def players = scen.oPlayers

  /** There are mo moves set. The Gui is reset to this state at the start of every turn. */
  val NoMoves: OptRefs[HTStep] = grid.newOptRefs[HTStep]

  /** This is the planned moves or orders for the next turn. */
  var moves: OptRefs[HTStep] = NoMoves

  /** The number of pixels / 2 displayed per row height. */
  val scale = grid.fullDisplayScale(mainWidth, mainHeight)

  def lunits = players.gridMapSomes{(r, p) => Rectangle(0.9, 0.6, r.gridVec2).fillDrawTextActive(p.colour, RPlayer(p, r),
    p.toString + "\n" + r.ycStr, 24, 2.0) }

  val tiles = grid.activeTiles
  val roardTexts = grid.cenSideVertRoordText

  val sls = grid.sidesDraw(2.0)

  def mMoves: Refs[LineDraw] ={
    moves.gridMapSomes{(r, step) =>
    val newR = r + step.roord
    RoordLine(r, newR).gridLine2.draw(2, players.gridElemGet(r).colour )
  } }

  def getOrders = moves.gridMapSomes((r, s) => r.andStep(s))
  def bTurn = clickButton("Turn " + (scen.turn + 1).toString, _ => {
    scen = scen.turn(getOrders)
    moves = NoMoves
    repaint()
    thisTop()
  })
  def thisTop(): Unit = reTop(Refs(bTurn, status))

  mainMouseUp = (b, cl, _) => (b, cl, selected) match
    { case (LeftButton, cl, _) =>
      { selected = cl
        statusText = selected.headToStringElse("Nothing Selected")
        thisTop()
      }

      case (RightButton, (t : HexTile) :: _, List(RPlayer(p, r), HexTile(y, c))) =>
      {
        val newM: OptRef[HTStep] = t.adjOf(r)
        newM.foreach(m => moves = moves.setSome(grid.index(r), m))
        repaint()
      }
       case (_, h, _) => deb("Other; " + h.toString)
    }
  thisTop()
  def frame = (tiles +- sls ++ roardTexts ++ lunits ++ mMoves).gridTrans(scale)
  def repaint() = mainRepaint(frame)
  repaint()
}