/* Copyright 2018-22 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package gOne
import pgui._, geom._, prid._, phex._, gPlay._

/** Graphical user interface for GOne example game. */
case class GOneGui(canv: CanvasPlatform, scenStart: OneScen, viewIn: HGView) extends HGridSysGui("Game One Gui")
{
  statusText = "Left click on Player to select. Right click on adjacent Hex to set move."
  var scen = scenStart
  var history: RArr[OneScen] = RArr(scen)
  implicit def gridSys: HGridSys = scen.gridSys
  def players: HCenOptLayer[Player] = scen.oPlayers
  cPScale = viewIn.cPScale
  focus = viewIn.vec
  implicit val proj: HSysProjection = gridSys.projection(mainPanel)
  proj.setView(viewIn)

  /** There are no moves set. The Gui is reset to this state at the start of every turn. */
  def NoMoves: HCenOptLayer[HDirn] = gridSys.newHCenOptLayer[HDirn]

  /** This is the planned moves or orders for the next turn. Note this is just a record of the planned moves it is not graphical display of those
   *  moves. This data is state for the Gui. */
  var moves: HCenOptLayer[HDirn] = NoMoves

  val urect = Rect(1.4, 1)

  def units: GraphicElems = players.projSomeHcPtMap { (p, hc, pt) =>
    val str = ptScale.scaledStr(170, p.toString + "\n" + hc.strComma, 150, p.charStr + "\n" + hc.strComma, 60, p.charStr)
    urect.scale(80).slate(pt).fillDrawTextActive(p.colour, HPlayer(hc, p), str, 24, 2.0)
  }

  /** [[TextGraphic]]s to display the [[HCen]] coordinate in the tiles that have no unit counters. */
  def hexStrs: RArr[TextGraphic] = players.projNoneHcPtMap{ (hc, pt) => pt.textAt(hc.strComma, 20) }

  /** This makes the tiles active. They respond to mouse clicks. It does not paint or draw the tiles. */
  val actives: RArr[PolygonActive] = proj.tileActives

  /** Draws the tiles sides (or edges). */
  def sidesDraw: LinesDraw = proj.sidesDraw()

  /** Draws the tiles sides (or edges). */
  def innerSidesDraw: LinesDraw = proj.innerSidesDraw()

  /** Draws the tiles sides (or edges). */
  def outerSidesDraw: LinesDraw = proj.outerSidesDraw(2, Colour.Gold)

  /** This is the graphical display of the planned move orders. */
  def moveGraphics: GraphicElems = moves.someHCOptFlatMap { (step, hc) =>
    proj.transOptLineSeg(LineSegHC(hc, hc.unsafeStep(step))).map(_.draw(players.unSafeApply(hc).colour).arrow)
  }

  /** Creates the turn button and the action to commit on mouse click. */
  def bTurn: PolygonCompound = clickButton("Turn " + (scen.turn + 1).toString){_ =>
    val getOrders: RArr[(Player, HDirn)] = players.zipSomesMap(moves)((player, step) => (player, step))
    scen = scen.endTurn(getOrders)
    moves = NoMoves
    repaint()
    thisTop()
  }

  /** The frame to refresh the top command bar. Note it is a ref so will change with scenario state. */
  def thisTop(): Unit = reTop(RArr(bTurn) ++ proj.buttons)

  mainMouseUp = (b, cl, _) => (b, selected, cl) match
  {
    case (LeftButton, _, cl) => {
      selected = cl
      statusText = selected.headFoldToString("Nothing Selected")
      thisTop()
    }

    case (RightButton, AnyArrHead(HPlayer(hc1, _)), hits) => hits.findHCenForEach{ hc2 =>
      val newM: Option[HDirn] = gridSys.findStep(hc1, hc2)
      newM.fold{ if (hc1 == hc2) moves = moves.setNone(hc1) }(m => moves = moves.setSome(hc1, m))
      repaint()
    }

    case (_, _, h) => deb("Other; " + h.toString)
  }
  thisTop()

  def frame: GraphicElems = actives ++ units +% innerSidesDraw +% outerSidesDraw ++ moveGraphics ++ hexStrs
  proj.getFrame = () => frame
  proj.setStatusText = {str =>
    statusText = str
    thisTop()
  }
  repaint()
}