/* Copyright 2018-23 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package gTwo; package h2p
import pgui._, geom._, prid._, phex._

/** Graphical user interface for example game 3. A hex based game like game 1, that introduces multi turn directives. */
case class G2HGui(canv: CanvasPlatform, game: G2HGame, settings: G2HGuiSettings) extends HGridSysGui("Game Two Hex Gui")
{ def controlStr: String = settings.counterSet.map(_.charStr).mkString(", ")
  statusText = "You control players" -- controlStr -- ". Left click on Counter to select. Right click on adjacent Hex to set move."
  var scen: G2HScen = game.getScen

  implicit def gridSys: HGridSys = scen.gridSys

  def counterStates: HCenOptLayer[CounterState] = scen.counterStates
  val counterSet = settings.counterSet

  implicit val proj: HSysProjection = gridSys.projection(mainPanel)
  proj.setView(settings.view)

  /** This is the planned moves or orders for the next turn. Note this is just a record of the planned moves it is not graphical display of those
   * moves. This data is state for the Gui. */
  var moves: HCenOptLayer[CounterState] = counterStates.copy

  def frame: GraphicElems =
  {
    def units: GraphicElems = counterStates.projSomesHcPtMap { (cs, hc, pt) =>
      val counter = cs.counter
      val str = pixPerTile.scaledStr(170, counter.toString + "\n" + hc.strComma, 150, counter.charStr + "\n" + hc.strComma, 60, counter.charStr)
      Rect(1.4).scale(pixPerTile * 0.4).slate(pt).fillDrawTextActive(counter.colour, HCounter(hc, counter), str, 24, 2.0)
    }

    /** [[TextGraphic]]s to display the [[HCen]] coordinate in the tiles that have no unit counters. */
    def hexStrs: RArr[TextGraphic] = counterStates.projNoneHcPtMap { (hc, pt) => TextGraphic(hc.strComma, 20, pt) }
    def hexStrs2: GraphicElems = proj.ifTileScale(60, hexStrs)

    /** This makes the tiles active. They respond to mouse clicks. It does not paint or draw the tiles. */
    def actives: RArr[PolygonActive] = proj.tileActives

    /** Draws the tiles sides (or edges). */
    def sidesDraw: LinesDraw = proj.sidesDraw()

    /** This is the graphical display of the planned move orders. */
    def moveGraphics: GraphicElems = moves.somesHcFlatMap { (ps, hc) =>
      val lps1: LinePathHC = ps.steps.pathHC(hc)
      val lps2: LineSegHCArr = lps1.lineSegArr
      val lps2a: LineSegHCArr = lps2.init
      val lps2b = lps2.lasts
      val lps3a = lps2a.optMap(lh => proj.transOptLineSeg(lh)).map(_.draw(lineColour = ps.counter.colour))
      val lps3b = lps2b.optMap(proj.transOptLineSeg(_)).flatMap(_.draw(lineColour = ps.counter.colour).arrow)
      lps3a ++ lps3b
    }

    actives ++ hexStrs2 +% sidesDraw ++ moveGraphics ++ units
}

  /** Creates the turn button and the action to commit on mouse click. */
  def bTurn: PolygonCompound = clickButton("Turn " + (scen.turn + 1).toString){_ =>
    scen = game.resolveTurn(moves)
    moves = scen.counterStates
    repaint()
    thisTop()
  }

  /** The frame to refresh the top command bar. Note it is a ref so will change with scenario state. */
  def thisTop(): Unit = reTop(RArr(bTurn) ++ proj.buttons)

  mainMouseUp = (b, cl, _) => (b, selected, cl) match
  {
    case (LeftButton, _, cl) =>
    { selected = cl.headOrNone
      statusText = selectedStr
      thisTop()
    }

    case (RightButton, HCounter(hc1, selectedCounter), hits) if counterSet.contains(selectedCounter) => hits.findHCenForEach{ hc2 =>
      if(canv.shiftDown)
      { val oldState: CounterState = moves.applyUnsafe(hc1)
        val oldSteps = oldState.steps
        val oldEnd: Option[HCen] = gridSys.stepsEndFind(hc1, oldSteps)
        val optNewStep = oldEnd.flatMap(currEnd => gridSys.stepFind(currEnd, hc2))
        optNewStep.foreach{ newStep => moves.setSomeMut(hc1, CounterState(selectedCounter, oldSteps +% newStep)) }
      }
      else gridSys.stepFind(hc1, hc2).foreach{ step =>
        if (hc1 == hc2) moves.setSomeMut(hc1, CounterState(selectedCounter))
        else moves.setSomeMut(hc1, CounterState(selectedCounter, step)) }
      repaint()
    }

    case (RightButton, HCounter(_, selectedCounter), _) =>
    { statusText = s"Illegal move You don't have control of $selectedCounter"
      thisTop()
    }

    case (_, _, h) => deb("Other; " + h.toString)
  }
  thisTop()

  proj.getFrame = () => frame
  proj.setStatusText = { str =>
    statusText = str
    thisTop()
  }
  repaint()
}