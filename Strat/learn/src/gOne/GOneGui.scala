package ostrat
package gOne
import pCanv._, geom._, pGrid._

/** Graphical user interface for GOne example game. */
case class GOneGui(canv: CanvasPlatform, scenStart: OneScen) extends CmdBarGui("Game One Gui")
{ var scen = scenStart.turn(Refs(HTileAndStep(4, 4, HTStepUL), HTileAndStep(4, 8, HTStepUL), HTileAndStep(6, 10, HTStepLt)))
  var statusText = "Let click on Player to select. Right click on adjacent Hex to set move."
  implicit def grid = scen.grid
  def players = scen.oPlayers
  val moves0: OptRefs[HTStep] = grid.newOptRefs[HTStep]
  moves0.foreachSome(println)
  var moves: OptRefs[HTStep] = moves0

  val scale = grid.fullDisplayScale(mainWidth, mainHeight)

  def lunits = players.gridMapSomes{(r, p) => Rectangle(0.9, 0.6, r.gridVec2).fillDrawTextActive(p.colour, RPlayer(p, r),
    p.toString + "\n" + r.ycStr, 24, 2.0) }

  val tiles = grid.activeTiles
  val roardTexts = grid.cenSideVertRoordText

  val sls = grid.sidesDraw(2.0)

  def mMoves: Refs[LineDraw] ={
    moves.gridMapSomes{(r, step) =>
    val newR = r + step.roord
    RoordLine(r, newR).gridLine2.draw(2, {deb("Get Colour"); players.gridElemGet(r).colour} )
  } }

  def getOrders = moves.gridMapSomes((r, s) => r.andStep(s))
  val bTurn = clickButton("Turn " + (scen.turn + 1).toString, _ => {

    scen = scen.turn(getOrders)

    moves = moves0
    moves.foreachSome(a => deb(a.toString))
    repaint()

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
        val newM = t.adjOf(r)
        moves.set(grid.index(r), newM)
        repaint()
      }
       case (_, h, _) => deb("Other; " + h.toString)
    }
  thisTop()
  def frame = {
    val r = (tiles +- sls ++ roardTexts ++ lunits ++ mMoves)

      r.gridTrans(scale)
  }
  def repaint() = mainRepaint(frame)
  repaint()
}