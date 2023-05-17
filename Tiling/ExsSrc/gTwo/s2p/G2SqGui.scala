/* Copyright 2018-23 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package gTwo; package s2p
import pgui._, geom._, prid._, psq._, gPlay._

/** Class may not be needed. A class identifying a [[Counter]] and an [[SqCen]] hex coordinate position. */
/*
case class SqCounter(hc: SqCen, value: Counter) extends SqMemShow[Counter]
{ override def typeStr: String = "SqCounter"
  override def name2: String = "counter"
  override implicit def showT2: ShowT[Counter] = Counter.showTEv
  override def syntaxDepth: Int = 2
}

/** Graphical user interface for example game 3. A hex based game like game 1, that introduces multi turn directives. */
case class G2HGui(canv: CanvasPlatform, game: G2SqGame, settings: G2HGuiSettings) extends HGridSysGui("Game Two Hex Gui")
{ statusText = "Left click on Counter to select. Right click on adjacent Hex to set move. Right click with shift to extend move."
  var scen: G2HScen = game.getScen
  var history: RArr[G2HScen] = RArr(scen)

  implicit def gridSys: HGridSys = scen.gridSys

  def counterStates: SqCenOptLayer[CounterState] = scen.counterStates
  val counterSet = settings.counterSet

  implicit val proj: HSysProjection = gridSys.projection(mainPanel)
  proj.setView(settings.view)

  /** This is the planned moves or orders for the next turn. Note this is just a record of the planned moves it is not graphical display of those
   * moves. This data is state for the Gui. */
  var moves: SqCenOptLayer[CounterState] = counterStates.copy

  val urect = Rect(1.4, 1)

  def frame: GraphicElems =
  {
    def units: GraphicElems = counterStates.projSomeHcPtMap { (ps, hc, pt) =>
      val player = ps.counter
      val str = ptScale.scaledStr(170, player.toString + "\n" + hc.strComma, 150, player.charStr + "\n" + hc.strComma, 60, player.charStr)
      urect.scale(80).slate(pt).fillDrawTextActive(player.colour, SqCounter(hc, player), str, 24, 2.0)
    }

    /** [[TextGraphic]]s to display the [[SqCen]] coordinate in the tiles that have no unit counters. */
    def hexStrs: RArr[TextGraphic] = counterStates.projNoneHcPtMap { (hc, pt) => TextGraphic(hc.strComma, 20, pt) }

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
      val lps3a = lps2a.optMap(lh => proj.transOptLineSeg(lh)).map(_.draw(ps.counter.colour))
      val lps3b = lps2b.optMap(proj.transOptLineSeg(_)).flatMap(_.draw(ps.counter.colour).arrow)
      lps3a ++ lps3b
    }

    actives ++ hexStrs +% sidesDraw ++ moveGraphics ++ units
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

    case (RightButton, SqCounter(hc1, selectedCounter), hits) if counterSet.contains(selectedCounter) => hits.findSqCenForEach{ hc2 =>
      if(canv.shiftDown)
      { val oldState: CounterState = moves.applyUnsafe(hc1)
        val oldSteps = oldState.steps
        val oldEnd: Option[SqCen] = gridSys.stepsEndFind(hc1, oldSteps)
        val optNewStep = oldEnd.flatMap(currEnd => gridSys.stepFind(currEnd, hc2))
        optNewStep.foreach{ newStep => moves.setSomeMut(hc1, CounterState(selectedCounter, oldSteps +% newStep)) }
      }
      else gridSys.stepFind(hc1, hc2).foreach{ step =>
        if (hc1 == hc2) moves.setSomeMut(hc1, CounterState(selectedCounter))
        else moves.setSomeMut(hc1, CounterState(selectedCounter, step)) }
      repaint()
    }

    case (RightButton, SqCounter(_, selectedCounter), _) =>
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
}*/
