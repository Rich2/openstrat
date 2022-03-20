/* Copyright 2018-22 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package gTwo
import pgui._, prid._, psq._, geom._, gPlay._

case class GTwoGui(canv: CanvasPlatform, scenStart: TwoScen) extends SquareMapGui("Game Two Gui")
{
  statusText = "Let click on Player to select. Right click on adjacent square to set move."
  var scen = scenStart
  implicit def grider: SqGrid = scen.grid
  def players: SqCenArrOpt[Player] = scen.oPlayers

  /** The number of pixels / 2 displayed per row height. */
  var cPScale: Double = grider.fullDisplayScale(mainWidth, mainHeight)

  focus = grider.cenVec

  /** This makes the tiles active. They respond to mouse clicks. It does not paint or draw the tiles. */
  def tiles: Arr[PolygonActive] = grider.activeTiles

  def lunits: Arr[PolygonCompound] = players.scSomesMap{ (sc, p) =>
    val str = ptScale.scaledStr(170, p.toString + "\n" + sc.strComma, 150, p.charStr + "\n" + sc.strComma, 60, p.charStr)
    Rect(1.2, 0.8, sc.toPt2Reg).fillDrawTextActive(p.colour, SPlayer(p, sc), str, 24, 2.0)  }

  def css: Arr[TextGraphic] = players.cMapNones(hc => TextGraphic(hc.rcStr, 20, hc.toPt2Reg))

  /** This is the planned moves or orders for the next turn. Note this is just a record of the planned moves it is not graphical display of
   *  those moves. This data is state for the Gui. */
  var moves: SqCenArrOpt[SqStep] = NoMoves

  /** This is the graphical display of the planned move orders. */
  def moveGraphics: Arr[LineSegDraw] = moves.scSomesMap { (sc, step) =>
    LineSegSC(sc, sc.step(step)).lineSeg.draw(players.unSafeApply(sc).colour)
  }

  /** Creates the turn button and the action to commit on mouse click. */
  def bTurn = simpleButton("Turn " + (scen.turn + 1).toString){
    val getOrders = players.some2sMap(moves)((player, step) => (player, step))//moves.mapSomes(rs => rs)
    scen = scen.endTurn(getOrders)
    moves = NoMoves
    repaint()
    thisTop()
  }

  /** Draws the tiles sides (or edges). */
  val sidesDraw = grider.sidesDraw()

  /** There are mo moves set. The Gui is reset to this state at the start of every turn. */
  def NoMoves: SqCenArrOpt[SqStep] = grider.newTileArrOpt[SqStep]

  mainMouseUp = (b, pointerHits, _) => (b, selected, pointerHits) match
  { case (LeftButton, _, pointerHits) =>
    { selected = pointerHits
      statusText = selected.headFoldToString("Nothing Selected")
      thisTop()
    }

    case (RightButton, AnysHead(SPlayer(p, sc1)), hits) => hits.sqCenForFirst{ sc2 =>
      val newM: OptRef[SqStep] = sc1.findStep(sc2)
      newM.foldDo{ if (sc1 == sc2) moves = moves.setNone(sc1) }(m => moves = moves.setSome(sc1, m))
      repaint()
    }

    case (RightButton, _, pointerHits) => deb("Right Other; " -- selected.toString -- pointerHits.toString)
    case (_, _, pointerHits) => deb("Other mouse; " -- selected.toString -- pointerHits.toString)
  }

  /** The frame to refresh the top command bar. Note it is a ref so will change with scenario state. */
  def thisTop(): Unit = reTop(bTurn %: this.navButtons)
  thisTop()
  def moveGraphics2: GraphicElems = moveGraphics.slate(-focus).scale(cPScale).flatMap(_.arrow)

  def frame: GraphicElems = (tiles ++ lunits +% sidesDraw ++ css).slate(-focus).scale(cPScale) ++ moveGraphics2
  repaint()
}