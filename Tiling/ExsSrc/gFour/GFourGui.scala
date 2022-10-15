/* Copyright 2018-22 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package gFour
import pgui._, prid._, phex._, geom._, gPlay._

case class GFourGui(canv: CanvasPlatform, scenStart: FourScen, viewIn: HGView) extends HGridSysGui("Game Three Gui")
{ statusText = "Welcome to Game Four."
  val scen = scenStart
  def terrs: HCenLayer[Terr] = scen.terrs
  var history: RArr[FourScen] = RArr(scen)
  implicit def gridSys: HGridSys = scen.gridSys
  cPScale = viewIn.cPScale
  focus = viewIn.vec
  implicit val proj: HSysProjection = gridSys.projection(mainPanel)
  proj.setView(viewIn)
  def lines: RArr[LineSegDraw] = terrs.projLinksLineOptMap{ (ls, t1, t2 ) => ife(t1 == t2, Some(ls.draw(t1.contrastBW)), None) }

  def terrPolys: RArr[PolygonFill] = terrs.projRowsCombinePolygons.map{ pt => pt.polygon.fill(pt.a2.colour) }
  debvar(terrPolys.length)

  /** This makes the tiles active. They respond to mouse clicks. It does not paint or draw the tiles. */
  def actives: RArr[PolygonActive] = proj.tileActives

  def units: HCenOptLayer[Lunit] = scen.units

  def unitGraphics: RArr[PolygonCompound] = units.projSomeHcPtMap { (p, hc, pt) =>
    Rect(160, 120, pt).fillDrawTextActive(p.colour, p, p.team.name + "\n" + hc.rcStr, 24, 2.0) }

  def texts: RArr[TextGraphic] = units.projNoneHcPtMap{ (hc, pt) => pt.textAt(hc.rcStr, 14, terrs(hc).contrastBW) }

  def moveGraphics: GraphicElems = units.someHCMap{ (u, hc) => LineSegHC(hc, hc.unsafeStep(u.cmds(0))).oldLineSeg.draw(units.unSafeApply(hc).colour)}

  /** Creates the turn button and the action to commit on mouse click. */
  def bTurn: PolygonCompound = simpleButton("Turn " + (scen.turn + 1).toString){
    /*val getOrders = moves.mapSomes(rs => rs)
    scen = scen.doTurn(getOrders)
    moves = NoMoves*/
    repaint()
    thisTop()
  }

  mainMouseUp = (b, cl, _) => (b, selected, cl) match {
    case (LeftButton, _, cl) => {
      selected = cl
      statusText = selected.headFoldToString("Nothing Selected")
      thisTop()
    }

    case (RightButton, AnyArrHead(HPlayer(hc1, _)), hits) => hits.findHCenForEach{ hc2 =>
     val newM: Option[HDirn] = gridSys.findStep(hc1, hc2)
      //newM.fold{ if (hc1 == hc2) moves = moves.setNone(hc1) }(m => moves = moves.setSome(hc1, m))
      repaint()
    }

    case (_, _, h) => deb("Other; " + h.toString)
  }

  /** The frame to refresh the top command bar. Note it is a ref so will change with scenario state. */
  def thisTop(): Unit = reTop(bTurn %: proj.buttons)// navButtons)
  statusText = s"Game Four. Scenario has ${gridSys.numTiles} tiles."
  thisTop()

  def frame: GraphicElems = terrPolys/*).slate(-focus).scale(cPScale)*/ ++ actives ++ lines ++ unitGraphics ++ texts

  proj.getFrame = () => frame
  proj.setStatusText = { str =>
    statusText = str
    thisTop()
  }
  repaint()
}