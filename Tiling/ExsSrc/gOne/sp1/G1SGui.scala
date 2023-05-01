/* Copyright 2018-22 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package gOne; package sp1
import pgui._, prid._, psq._, geom._, gPlay._

/** Graphical user interface for Game Two. It differs from the first in that it is on a square grid
 * and adjacent moves take priority over diagonal tile steps. */
case class G1SGui(canv: CanvasPlatform, scenStart: G1SqScen, viewIn: SqGridView) extends SqSysGui("Game Two Gui")
{ statusText = "Let click on Player to select. Right click on adjacent square to set move."
  var scen = scenStart
  implicit def gridSys: SqGridSys = scen.gSys
  def players: SqCenOptLayer[Player] = scen.oPlayers
  pixPerC = gridSys.fullDisplayScale(mainWidth, mainHeight)
  focus = viewIn.vec
  implicit val proj: SqSysProjection = gridSys.projection(mainPanel)

  /** This makes the tiles active. They respond to mouse clicks. It does not paint or draw the tiles. */
  def actives: RArr[PolygonActive] = proj.tileActives

  def lunits: RArr[PolygonCompound] = players.projSomeScPtMap { (pl, sc, pt) =>
    val str = ptScale.scaledStr(170, pl.toString + "\n" + sc.strComma, 150, pl.charStr + "\n" + sc.strComma, 60, pl.charStr)
    Rect(120, 90, pt).fillDrawTextActive(pl.colour, SPlayer(pl, sc), str, 24, 2.0)  }

  /** Not sure why this is called css. */
  def css: RArr[TextGraphic] = players.projNoneScPtMap((sc, pt) => pt.textAt(sc.rcStr, 20))

  def NoMoves: SqCenStepPairArr[Player] = SqCenStepPairArr[Player]()

  /** This is the planned moves or orders for the next turn. Note this is just a record of the planned moves it is not graphical display of
   *  those moves. This data is state for the Gui. */
  var moves: SqCenStepPairArr[Player] = NoMoves

  def moveSegPairs: LineSegPairArr[Player] = moves.optMapOnA1(_.projLineSeg)

  def moveGraphics: GraphicElems = moveSegPairs.pairFlatMap { (seg, pl) => seg.draw(pl.colour).arrow }

  /** Creates the turn button and the action to commit on mouse click. */
  def bTurn: PolygonCompound = simpleButton("Turn " + (scen.turn + 1).toString){
    scen = scen.endTurn(moves)
    moves = NoMoves
    repaint()
    thisTop()
  }

  /** Draws the tiles sides (or edges). */
  def sidesDraw: LinesDraw = proj.sidesDraw()

  mainMouseUp = (b, pointerHits, _) => (b, selected, pointerHits) match
  { case (LeftButton, _, pointerHits) =>
    { selected = pointerHits
      statusText = selected.headFoldToString("Nothing Selected")
      thisTop()
    }

    case (RightButton, AnyArrHead(SPlayer(pl, sc1)), hits) => hits.sqCenForFirst{ sc2 =>
      val newM: Option[SqDirn] = sc1.findStep(sc2)
      newM.foreach{ d => moves = moves.replaceA1byA2OrAppend(pl, sc1.andStep(d)) }
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