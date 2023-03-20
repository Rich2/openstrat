/* Copyright 2018-23 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package p305
import geom._, prid._, phex._, pgui._, egrid._

case class BC305Gui(canv: CanvasPlatform, scenIn: BCScen, viewIn: HGView, isFlat: Boolean = false) extends HGridSysGui("BC305 Gui")
{ var scen = scenIn
  override implicit val gridSys: HGridSys = scenIn.gridSys
  val terrs: HCenLayer[WTile] = scen.terrs
  val sTerrs: HSideLayer[WSide] = scen.sTerrs
  val corners = scen.corners
  def armies: HCenOptLayer[Legion] = scen.armies

  def NoMoves: HCenStepPairArr[Legion] = HCenStepPairArr[Legion]()
  var moves: HCenStepPairArr[Legion] = NoMoves

  focus = gridSys.cenVec
  cPScale = gridSys.fullDisplayScale(mainWidth, mainHeight)
  implicit val proj: HSysProjection = ife(isFlat, HSysProjectionFlat(gridSys, mainPanel), gridSys.projection(mainPanel))
  proj.setView(viewIn)

  override def frame: GraphicElems =
  { val polys: HCenPairArr[Polygon] = proj.hCenPolygons(corners)
   // def tileFills: RArr[PolygonFill] = terrs.projRowsCombinePolygons.map { pp => pp.a1.fill(pp.a2.colour) }

    def tileFills: RArr[PolygonFill] = polys.pairMap{ (hc, poly) => poly.fill(terrs(hc)(gridSys).colour) }

    def actives: RArr[PolygonActive] = proj.tileActives

    def sides1: GraphicElems = RArr()
    /*  proj.sidesOptMap { (hs: HSide) =>
      val sTerr: Option[WSide] = sTerrs(hs)
      sTerr.map { st => corners.sideVerts(hs).project(proj).fill(st.colour) }
    }*/

    def lines1: GraphicElems = proj.linksOptMap { hs =>
      val hc1 = hs.tileLt
      val hc2 = hs.tileRt
      val t1 = terrs(hc1)
      val t2 = terrs(hc2)
      if (sTerrs(hs).nonEmpty | t1 != t2) None
      else {
        val cs: (HCen, Int, Int) = hs.corners
        val ls1 = corners.sideLineHVAndOffset(cs._1, cs._2, cs._3)
        val ls2 = ls1.map(hva => hva.toPt2(proj.transCoord(_)))
        Some(ls2.draw(t1.contrastBW))
      }
    }

    def lines2: GraphicElems = proj.ifTileScale(40, lines1)

    def hexStrs1: GraphicElems = proj.hCenSizedMap(15) { (hc, pt) => pt.textAt(hc.strComma, 12, terrs(hc).contrastBW) }

    def hexStrs2: GraphicElems = proj.ifTileScale(50, hexStrs1)

    def units: GraphicElems = armies.projSomeHcPtMap { (legion, hc, pt) =>
      val str = ptScale.scaledStr(170, legion.toString + "\n" + hc.strComma, 150, "A" + "\n" + hc.strComma, 60, legion.toString)
      pStrat.UnitCounters.infantry(proj.pixelsPerTile * 0.6, HCenPair(hc, legion), legion.colour).slate(pt) //.fillDrawTextActive(p.colour, p.polity, str, 24, 2.0)
    }

    def moveSegPairs: LineSegPairArr[Legion] = moves.optMapOnA1(_.projLineSeg)

    /** This is the graphical display of the planned move orders. */
    def moveGraphics: GraphicElems = moveSegPairs.pairFlatMap { (seg, pl) => seg.draw(pl.colour).arrow }

    tileFills ++ actives ++ sides1 ++ lines2 ++ hexStrs2 ++ units ++ moveGraphics
  }

  /** Creates the turn button and the action to commit on mouse click. */
  def bTurn: PolygonCompound = clickButton("Turn " + (scen.turn + 1).toString) { _ =>
    scen = scen.endTurn(moves)
    moves = NoMoves
    repaint()
    thisTop()
  }
  statusText = "Welcome to BC305"

  mainMouseUp = (b, cl, _) => (b, selected, cl) match
  {
    case (LeftButton, _, cl) =>
    { selected = cl
      statusText = selected.headFoldToString("Nothing Selected")
      thisTop()
    }

    case (RightButton, AnyArrHead(HCenPair(hc1, pl: Legion)), hits) => hits.findHCenForEach { hc2 =>
      val newM: Option[HStep] = gridSys.findStep(hc1, hc2)
      newM.foreach { d => moves = moves.replaceA1byA2OrAppend(pl, hc1.andStep(d)) }
      repaint()
    }

    case (_, _, h) => deb("Other; " + h.toString)
  }

  def thisTop(): Unit = reTop(bTurn %: proj.buttons)

  thisTop()

  proj.getFrame = () => frame
  proj.setStatusText = { str =>
    statusText = str
    thisTop()
  }
  mainRepaint(frame)
}