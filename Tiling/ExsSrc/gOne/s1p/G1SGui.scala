/* Copyright 2018-23 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package gOne; package s1p
import pgui._, prid._, psq._, geom._, gPlay._, Colour.Black

/** Graphical user interface for Game Two. It differs from the first in that it is on a square grid and adjacent moves take priority over diagonal
 *  tile steps. */
case class G1SGui(canv: CanvasPlatform, game: G1SGame, settings: G1SGuiSettings) extends SqSysGui("Game one Square")
{ statusText = "Let click on Player to select. Right click on adjacent square to set move."
  var scen = game.scen

  implicit def gridSys: SqGridSys = scen.gridSys

  def counters: SqCenOptLayer[Counter] = scen.counters
  val counterSet = settings.counterSet

  pixPerC = gridSys.fullDisplayScale(mainWidth, mainHeight)
  focus = settings.view.vec
  implicit val proj: SqSysProjection = gridSys.projection(mainPanel)

  def NoMoves: SqCenStepPairArr[Counter] = SqCenStepPairArr[Counter]()

  /** This is the planned moves or orders for the next turn. Note this is just a record of the planned moves it is not graphical display of* those
   *  moves. This data is state for the Gui. */
  var moves: SqCenStepPairArr[Counter] = NoMoves

  def frame: GraphicElems =
  {
    /** This makes the tiles active. They respond to mouse clicks. It does not paint or draw the tiles. */
    def actives: RArr[PolygonActive] = proj.tileActives

    def lunits: RArr[PolygonCompound] = counters.projSomeScPtMap { (counter, sc, pt) =>
      val str = pixPerTile.scaledStr(170, counter.toString + "\n" + sc.strComma, 150, counter.charStr + "\n" + sc.strComma, 60, counter.charStr)
      Rect(120, 90, pt).fillDrawTextActive(counter.colour, SqCenPair(sc, counter), str, 24, 2.0, Black, counter.contrastBW)
    }

    /** Not sure why this is called css. */
    def css: RArr[TextGraphic] = counters.projNoneScPtMap((sc, pt) => pt.textAt(sc.rcStr, 20))


    def moveSegPairs: LineSegPairArr[Counter] = moves.optMapOnA1(_.projLineSeg)

    def moveGraphics: GraphicElems = moveSegPairs.pairFlatMap { (seg, pl) => seg.draw(pl.colour).arrow }

    actives ++ lunits +% sidesDraw ++ css ++ moveGraphics
  }

  /** Creates the turn button and the action to commit on mouse click. */
  def bTurn: PolygonCompound = simpleButton("Turn " + (scen.turn + 1).toString){
    scen = game.endTurn(moves)
    moves = NoMoves
    repaint()
    thisTop()
  }

  /** Draws the tiles sides (or edges). */
  def sidesDraw: LinesDraw = proj.sidesDraw()

  mainMouseUp = (b, pointerHits, _) => (b, selected, pointerHits) match
  { case (LeftButton, _, pointerHits) =>
    { selected = pointerHits.headOrNone
      statusText = selectedStr
      thisTop()
    }

    case (RightButton, SqCenPair(sc1, selectedCounter: Counter), hits) => hits.sqCenForFirst{ sc2 =>
      val newM: Option[SqStep] = gridSys.stepFind(sc1, sc2)
      newM.foreach{ d =>
        if(counterSet.contains(selectedCounter)) moves = moves.replaceA1byA2OrAppend(selectedCounter, sc1.andStep(d))
        else { statusText = s"Illegal move You don't have control of $selectedCounter"; thisTop()}
      }
      repaint()
    }

    case (RightButton, _, pointerHits) => deb("Right Other; " -- selected.toString -- pointerHits.toString)
    case (_, _, pointerHits) => deb("Other mouse; " -- selected.toString -- pointerHits.toString)
  }

  /** The frame to refresh the top command bar. Note it is a ref so will change with scenario state. */
  def thisTop(): Unit = reTop(bTurn %: proj.buttons)
  thisTop()

  proj.getFrame = () => frame
  proj.setStatusText = { str =>
    statusText = str
    thisTop()
  }
  repaint()
}