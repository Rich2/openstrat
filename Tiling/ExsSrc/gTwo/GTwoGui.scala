/* Copyright 2018-22 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package gTwo
import pgui._, prid._, psq._, geom._, gPlay._

case class GTwoGui(canv: CanvasPlatform, scenStart: TwoScen, viewIn: SqGridView) extends SqSysGui("Game Two Gui")
{ statusText = "Let click on Player to select. Right click on adjacent square to set move."
  var scen = scenStart
  implicit def gridSys: SqGridSys = scen.gSys
  def players: SqCenOptLayer[Player] = scen.oPlayers
  cPScale = gridSys.fullDisplayScale(mainWidth, mainHeight)
  focus = viewIn.vec
  implicit val proj: SqSysProjection = gridSys.projection(mainPanel)

  /** This makes the tiles active. They respond to mouse clicks. It does not paint or draw the tiles. */
  def actives: Arr[PolygonActive] = proj.tileActives

  def lunits: Arr[PolygonCompound] = players.scSomesMap{ (sc, p) =>
    val str = ptScale.scaledStr(170, p.toString + "\n" + sc.strComma, 150, p.charStr + "\n" + sc.strComma, 60, p.charStr)
    Rect(1.2, 0.8, sc.toPt2Reg).fillDrawTextActive(p.colour, SPlayer(p, sc), str, 24, 2.0)  }

  /** Not sure why this is called css. */
  def css: Arr[TextGraphic] = players.scNoneMap(hc => TextGraphic(hc.rcStr, 20, hc.toPt2Reg))

  /** This is the planned moves or orders for the next turn. Note this is just a record of the planned moves it is not graphical display of
   *  those moves. This data is state for the Gui. */
  var moves: SqCenOptLayer[SqDirn] = NoMoves

  /** This is the graphical display of the planned move orders. */
  def moveGraphics: Arr[LineSegDraw] = moves.scSomesMap { (sc, step) =>
    LineSegSC(sc, sc.step(step)).lineSeg.draw(players.unSafeApply(sc).colour)
  }

  /** Creates the turn button and the action to commit on mouse click. */
  def bTurn: PolygonCompound = simpleButton("Turn " + (scen.turn + 1).toString){
    val getOrders = players.some2sMap(moves)((player, step) => (player, step))//moves.mapSomes(rs => rs)
    scen = scen.endTurn(getOrders)
    moves = NoMoves
    repaint()
    thisTop()
  }

  /** Draws the tiles sides (or edges). */
  val sidesDraw = gridSys.sidesDraw()

  /** There are mo moves set. The Gui is reset to this state at the start of every turn. */
  def NoMoves: SqCenOptLayer[SqDirn] = gridSys.newSCenOptDGrider[SqDirn]

  mainMouseUp = (b, pointerHits, _) => (b, selected, pointerHits) match
  { case (LeftButton, _, pointerHits) =>
    { selected = pointerHits
      statusText = selected.headFoldToString("Nothing Selected")
      thisTop()
    }

    case (RightButton, AnyArrHead(SPlayer(p, sc1)), hits) => hits.sqCenForFirst{ sc2 =>
      val newM: Option[SqDirn] = sc1.findStep(sc2)
      newM.fold{ if (sc1 == sc2) moves = moves.setNone(sc1) }(m => moves = moves.setSome(sc1, m))
      repaint()
    }

    case (RightButton, _, pointerHits) => deb("Right Other; " -- selected.toString -- pointerHits.toString)
    case (_, _, pointerHits) => deb("Other mouse; " -- selected.toString -- pointerHits.toString)
  }

  /** The frame to refresh the top command bar. Note it is a ref so will change with scenario state. */
  def thisTop(): Unit = reTop(bTurn %: proj.buttons ++ navButtons)
  thisTop()
  def moveGraphics2: GraphicElems = moveGraphics.slate(-focus).scale(cPScale).flatMap(_.arrow)

  def frame: GraphicElems = actives ++ (lunits +% sidesDraw ++ css).slate(-focus).scale(cPScale) ++ moveGraphics2

  proj.getFrame = () => frame
  proj.setStatusText = { str =>
    statusText = str
    thisTop()
  }
  repaint()
}