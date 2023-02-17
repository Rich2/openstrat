/* Copyright 2018-23 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package pdisc
import geom._, pEarth._, prid._, phex._, pgui._

case class DiscGui(canv: CanvasPlatform, scenIn: DiscScen, viewIn: HGView, isFlat: Boolean = false) extends HGridSysGui("AD1492 Gui")
{ var scen = scenIn
  override implicit val gridSys: HGridSys = scenIn.gridSys
  val terrs: HCenLayer[WTile] = scen.terrs
  val sTerrs: HSideOptLayer[WSide] = scen.sTerrs
  val corners = scen.corners

  focus = gridSys.cenVec
  cPScale = gridSys.fullDisplayScale(mainWidth, mainHeight)
  implicit val proj: HSysProjection = ife(isFlat, HSysProjectionFlat(gridSys, mainPanel), gridSys.projection(mainPanel))
  proj.setView(viewIn)

  override def frame: GraphicElems =
  { val polys: HCenPairArr[Polygon] = proj.hCenPolygons(corners)
   // def tileFills: RArr[PolygonFill] = terrs.projRowsCombinePolygons.map { pp => pp.a1.fill(pp.a2.colour) }

    def tileFills: RArr[PolygonFill] = polys.pairMap{ (hc, poly) => poly.fill(terrs(hc)(gridSys).colour) }

    def actives: RArr[PolygonActive] = proj.tileActives

    def sides1: GraphicElems = proj.sidesOptMap { (hs: HSide) =>
      val sTerr: Option[WSide] = sTerrs(hs)
      sTerr.map { st => corners.sideVerts(hs).project(proj).fill(st.colour) }
    }

    def lines1: GraphicElems = proj.linksOptMap { hs =>
      val hc1 = hs.tileLt
      val hc2 = hs.tileRt
      val t1 = terrs(hc1)
      val t2 = terrs(hc2)
      if (sTerrs(hs).nonEmpty | t1 != t2) None
      else {
        val cs: (HCen, Int, Int) = hs.corners
        val ls1 = corners.sideLine(cs._1, cs._2, cs._3)
        val ls2 = ls1.map(hva => hva.toPt2(proj.transCoord(_)))
        Some(ls2.draw(t1.contrastBW))
      }
    }

    def lines2: GraphicElems = proj.ifTileScale(40, lines1)

    def hexStrs1: GraphicElems = proj.hCenSizedMap(15) { (hc, pt) => pt.textAt(hc.strComma, 12, terrs(hc).contrastBW) }

    def hexStrs2: GraphicElems = proj.ifTileScale(50, hexStrs1)

    tileFills ++ actives ++ sides1 ++ lines2 ++ hexStrs2
  }

  /** Creates the turn button and the action to commit on mouse click. */
  def bTurn: PolygonCompound = clickButton("Turn " + (scen.turn + 1).toString) { _ =>
    //scen = scen.endTurn()
    repaint()
    thisTop()
  }
  statusText = "Welcome to DISC305"

  mainMouseUp = (b, cl, _) => (b, selected, cl) match {
    case (LeftButton, _, cl) => {
      selected = cl
      statusText = selected.headFoldToString("Nothing Selected")
      thisTop()
    }

    /*case (RightButton, AnyArrHead(HPlayer(hc1, pl)), hits) => hits.findHCenForEach { hc2 =>
      val newM: Option[HDirn] = gridSys.findStep(hc1, hc2)
      newM.foreach { d => moves2 = moves2.replaceA1byA2OrAppend(pl, hc1.andStep(d)) }
      repaint()
    }*/

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