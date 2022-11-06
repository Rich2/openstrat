/* Copyright 2018-22 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package gTwo
import pgui._, prid._, psq._, geom._, gPlay._

/** Graphical user interface for Game Two. It differs from the first in that it is on a square grid
 * and adjacent moves take priority over diagonal tile steps. */
case class GTwoGui(canv: CanvasPlatform, scenStart: TwoScen, viewIn: SqGridView) extends SqSysGui("Game Two Gui")
{ statusText = "Let click on Player to select. Right click on adjacent square to set move."
  var scen = scenStart
  implicit def gridSys: SqGridSys = scen.gSys
  def players: SqCenOptLayer[Player] = scen.oPlayers
  cPScale = gridSys.fullDisplayScale(mainWidth, mainHeight)
  focus = viewIn.vec
  implicit val proj: SqSysProjection = gridSys.projection(mainPanel)

  /** This makes the tiles active. They respond to mouse clicks. It does not paint or draw the tiles. */
  def actives: RArr[PolygonActive] = proj.tileActives

  def lunits: RArr[PolygonCompound] = players.projSomeScPtMap { (pl, sc, pt) =>
    val str = ptScale.scaledStr(170, pl.toString + "\n" + sc.strComma, 150, pl.charStr + "\n" + sc.strComma, 60, pl.charStr)
    Rect(80, 60, pt).fillDrawTextActive(pl.colour, SPlayer(pl, sc), str, 24, 2.0)  }

  /** Not sure why this is called css. */
  def css: RArr[TextGraphic] = players.projNoneScPtMap((sc, pt) => pt.textAt(sc.rcStr, 20))

  /** This is the planned moves or orders for the next turn. Note this is just a record of the planned moves it is not graphical display of
   *  those moves. This data is state for the Gui. */
  var moves: SqCenOptLayer[SqDirn] = NoMoves

  def moveGraphics: GraphicElems = moves.someSCOptFlatMap { (step, sc) =>
    proj.transOptLineSeg(sc.segStepTo(step)).map(_.draw(players.unSafeApply(sc).colour).arrow)
  }

  /** This is left in as an example for more coplex games with multi step orders. */
  def mg2: LineSegSCPairArr[Colour] = moves.scSomesMapPair{ (sc, step) => sc.segStepTo(step)}{ (sc, _) => players.unSafeApply(sc).colour }


  /** Creates the turn button and the action to commit on mouse click. */
  def bTurn: PolygonCompound = simpleButton("Turn " + (scen.turn + 1).toString){
    val getOrders = players.some2sMap(moves)((player, step) => (player, step))//moves.mapSomes(rs => rs)
    scen = scen.endTurn(getOrders)
    moves = NoMoves
    repaint()
    thisTop()
  }

  /** Draws the tiles sides (or edges). */
  def sidesDraw: LinesDraw = proj.sidesDraw()

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
  def thisTop(): Unit = reTop(bTurn %: proj.buttons)
  thisTop()


  def frame: GraphicElems = actives ++ lunits +% sidesDraw ++ css ++ moveGraphics

  proj.getFrame = () => frame
  proj.setStatusText = { str =>
    statusText = str
    thisTop()
  }
  repaint()
}