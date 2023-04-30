/* Copyright 2018-23 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package gThree
import pgui._, prid._, phex._, geom._, gPlay._

case class GThreeGui(canv: CanvasPlatform, scenStart: ThreeScen, viewIn: HGView) extends HGridSysGui("Game Three Gui") {
  statusText = "Welcome to Game Three."
  val scen = scenStart

  def terrs: HCenLayer[Terr] = scen.terrs

  def lunits: HCenOptLayer[Lunit] = scen.lunits

  var history: RArr[ThreeScen] = RArr(scen)

  implicit def gridSys: HGridSys = scen.gridSys

  cPScale = viewIn.pixelsPerC
  focus = viewIn.vec
  implicit val proj: HSysProjection = gridSys.projection(mainPanel)
  proj.setView(viewIn)

  def frame: GraphicElems =
  {
    def lines: RArr[LineSegDraw] = terrs.projLinksLineOptMap { (ls, t1, t2) => ife(t1 == t2, Some(ls.draw(t1.contrastBW)), None) }

    def terrPolys: RArr[PolygonFill] = terrs.projRowsCombinePolygons.map { pt => pt.a1.fill(pt.a2.colour) }

    debvar(terrPolys.length)

    /** This makes the tiles active. They respond to mouse clicks. It does not paint or draw the tiles. */
    def actives: RArr[PolygonActive] = proj.tileActives

    def unitGraphics: RArr[PolygonCompound] = lunits.projSomeHcPtMap { (p, hc, pt) =>
      Rect(160, 120, pt).fillDrawTextActive(p.colour, p, p.team.name + "\n" + hc.rcStr, 24, 2.0)
  }

  def texts: RArr[TextGraphic] = lunits.projNoneHcPtMap { (hc, pt) => pt.textAt(hc.rcStr, 16, terrs(hc).contrastBW) }

  def moveGraphics: GraphicElems = lunits.someHCMapArr { (u, hc) => LineSegHC(hc, hc.unsafeStepDepr(u.cmds(0))).lineSeg.draw(lunits.getex(hc).colour) }

  terrPolys ++ actives ++ lines ++ unitGraphics ++ texts
  }

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
     val newM: Option[HStep] = gridSys.stepFind(hc1, hc2)
      //newM.fold{ if (hc1 == hc2) moves = moves.setNone(hc1) }(m => moves = moves.setSome(hc1, m))
      repaint()
    }

    case (_, _, h) => deb("Other; " + h.toString)
  }

  /** The frame to refresh the top command bar. Note it is a ref so will change with scenario state. */
  def thisTop(): Unit = reTop(bTurn %: proj.buttons)
  statusText = s"Game Three. Scenario has ${gridSys.numTiles} tiles."
  thisTop()

  proj.getFrame = () => frame
  proj.setStatusText = { str =>
    statusText = str
    thisTop()
  }
  repaint()
}