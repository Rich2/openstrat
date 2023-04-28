/* Copyright 2018-22 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package gTwo; package h2p
import pgui._, geom._, prid._, phex._, gPlay._

/** Graphical user interface for example game 3. A hex based game like game 1, that introduces multi turn directives. */
case class G2HGui(canv: CanvasPlatform, scenStart: G2HScen, viewIn: HGView) extends HGridSysGui("Game Two Hex Gui") {
  statusText = "Left click on Player to select. Right click on adjacent Hex to set move."
  var scen = scenStart
  var history: RArr[G2HScen] = RArr(scen)

  implicit def gridSys: HGridSys = scen.gridSys

  def playerStates: HCenOptLayer[PlayerState] = scen.playerStates

  implicit val proj: HSysProjection = gridSys.projection(mainPanel)
  proj.setView(viewIn)

  /** This is the planned moves or orders for the next turn. Note this is just a record of the planned moves it is not graphical display of those
   * moves. This data is state for the Gui. */
//  var moves: HDirnPathPairArr[Player] = scen.playerOrders

  val urect = Rect(1.4, 1)

  def frame: GraphicElems =
  {
    def units: GraphicElems = playerStates.projSomeHcPtMap { (ps, hc, pt) =>
      val player = ps.player
      val str = ptScale.scaledStr(170, player.toString + "\n" + hc.strComma, 150, player.charStr + "\n" + hc.strComma, 60, player.charStr)
      urect.scale(80).slate(pt).fillDrawTextActive(player.colour, HPlayer(hc, player), str, 24, 2.0)
    }

    /** [[TextGraphic]]s to display the [[HCen]] coordinate in the tiles that have no unit counters. */
    def hexStrs: RArr[TextGraphic] = playerStates.projNoneHcPtMap { (hc, pt) => TextGraphic(hc.strComma, 20, pt) }

    /** This makes the tiles active. They respond to mouse clicks. It does not paint or draw the tiles. */
    def actives: RArr[PolygonActive] = proj.tileActives

    /** Draws the tiles sides (or edges). */
    def sidesDraw: LinesDraw = proj.sidesDraw()

    /** This is the graphical display of the planned move orders. */
    def moveGraphics: GraphicElems = playerStates.someHCFlatMap { (ps, hc) =>
      val lps1: LinePathHC = ps.steps.pathHC(hc)
      val lps2: LineSegHCArr = lps1.lineSegArr
      val lps2a = lps2.init
      val lps2b = lps2.lastOpt
      val lps3a = lps2a.optMap(lh => proj.transOptLineSeg(lh)).map(_.draw(ps.player.colour))
      val lps3b = lps2b.flatMap(proj.transOptLineSeg(_)).map(_.draw(ps.player.colour).arrow)
      lps3a.concatsOption(lps3b)
    }

    actives ++ hexStrs +% sidesDraw ++ moveGraphics ++ units
}
  /** Creates the turn button and the action to commit on mouse click. */
  def bTurn: PolygonCompound = clickButton("Turn " + (scen.turn + 1).toString){_ =>
    //scen = scen.endTurn(moves)
    //moves = scen.playerOrders
    repaint()
    thisTop()
  }

  /** The frame to refresh the top command bar. Note it is a ref so will change with scenario state. */
  def thisTop(): Unit = reTop(RArr(bTurn) ++ proj.buttons)

  mainMouseUp = (b, cl, _) => (b, selected, cl) match {
    case (LeftButton, _, cl) => {
      selected = cl
      statusText = selected.headFoldToString("Nothing Selected")
      thisTop()
    }

    case (RightButton, AnyArrHead(HPlayer(hc1, p)), hits) => hits.findHCenForEach{ hc2 =>
      val newM: Option[HStep] = gridSys.findStep(hc1, hc2)
     // newM.fold[Unit]{ if (hc1 == hc2) moves = moves.replaceA1byA2(p, HStepPath(hc1)) } { m => moves = moves.replaceA1byA2(p, HStepPath(hc1, m)) }
      repaint()
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