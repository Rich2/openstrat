/* Copyright 2018-23 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package gFour; package hp4
import pgui._, prid._, phex._, geom._, gPlay._

case class G4HGui(canv: CanvasPlatform, scenStart: G4HScen, viewIn: HGView) extends HGridSysGui("Game Four Hex Gui")
{ statusText = "Welcome to Game Three."
  val scen = scenStart

//  def terrs: HCenLayer[Terr] = scen.terrs

  def lunits: LayerHcRArr[LunitState] = scen.lunits

  var moves: LayerHcRArr[LunitState] = lunits.copy

  var history: RArr[G4HScen] = RArr(scen)

  implicit def gridSys: HGridSys = scen.gridSys

  pixPerC = viewIn.pixelsPerC
  focus = viewIn.vec
  implicit val proj: HSysProjection = gridSys.projection(mainPanel)
  proj.setView(viewIn)
  //def pixPerTile: Double = proj.pixelsPerTile

  canv.keyDown = s => deb("Key down" -- s)

  def frame: GraphicElems =
  { //def lines: RArr[LineSegDraw] = terrs.projLinksLineOptMap { (ls, t1, t2) => ife(t1 == t2, Some(ls.draw(t1.contrastBW)), None) }

 //   def terrPolys: RArr[PolygonFill] = terrs.projRowsCombinePolygons.map { pt => pt.a1.fill(pt.a2.colour) }

    /** This makes the tiles active. They respond to mouse clicks. It does not paint or draw the tiles. */
    def actives: RArr[PolygonActive] = proj.tileActives

    /** Draws the tiles sides (or edges). */
    def sidesDraw: LinesDraw = proj.sidesDraw()

    def unitGraphics: RArr[PolygonCompound] = ??? /*lunits.projSomesHcPtMap { (rarr, hc, pt) =>
      val str: String = rarr.head.team.toString --- rarr.foldStr(us => us.lunit.num.str, ", ") --- hc.rcStr
      Rect(pixPerTile * 0.45, proj.pixelsPerTile * 0.3, pt).fillActiveDrawText(rarr.head.colour, rarr, str, pixPerTile / 15, 2.0)
    }*/

    def texts: RArr[TextGraphic] = proj.hCensIfPtMap(lunits.emptyTile(_)){ (hc, pt) => pt.textAt(hc.rcStr, 16, Colour.Black) }

    /** This is the graphical display of the planned move orders. */
    def moveGraphics: GraphicElems = ??? /*moves.mapHcFlatMap { (ps, hc) =>
      val lps1: LinePathHC = ps.intentions.pathHC(hc)
      val lps2: LineSegHCArr = lps1.lineSegArr
      val lps2a: LineSegHCArr = lps2.init
      val lps2b = lps2.lasts
      val lps3a = lps2a.optMap(lh => proj.transOptLineSeg(lh)).map(_.draw(lineColour = ps.colour))
      val lps3b = lps2b.optMap(proj.transOptLineSeg(_)).flatMap(_.draw(lineColour = ps.colour).arrow)
      lps3a ++ lps3b
    }*/


    actives +% sidesDraw ++ moveGraphics ++ unitGraphics ++ texts
  }

  /** Creates the turn button and the action to commit on mouse click. */
  def bTurn: PolygonCompound = simpleButton("Turn " + (scen.turn + 1).toString){
    /*val getOrders = moves.mapSomes(rs => rs)
    scen = scen.doTurn(getOrders)
    moves = NoMoves*/
    repaint()
    thisTop()
  }

  mainMouseUp = (b, cl, _) => (b, selected, cl) match
  { case (LeftButton, _, cl) =>
    { selected = cl.headOrNone
      statusText = selectedStr
      thisTop()
    }

   /* case (RightButton, AnyArrHead(HPlayer(hc1, _)), hits) => hits.findHCenForEach{ hc2 =>
     val newM: Option[HStep] = gridSys.stepFind(hc1, hc2)
      //newM.fold{ if (hc1 == hc2) moves = moves.setNone(hc1) }(m => moves = moves.setSome(hc1, m))
      repaint()
    }*/

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
