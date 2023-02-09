/* Copyright 2018-23 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package dless
import geom._, pEarth._, prid._, phex._, pgui._

case class DLessGui(canv: CanvasPlatform, scenIn: DLessScen, viewIn: HGView, isFlat: Boolean = false) extends HGridSysGui("Diceless Gui")
{ var scen = scenIn
  override implicit val gridSys: HGridSys = scenIn.gridSys
  val terrs: HCenLayer[WTile] = scen.terrs
  val sTerrs: HSideOptLayer[WSide] = scen.sTerrs
  val corners = scen.corners
  def armies: HCenOptLayer[Nation] = scen.armies
  focus = gridSys.cenVec
  cPScale = gridSys.fullDisplayScale(mainWidth, mainHeight)
  implicit val proj: HSysProjection = ife(isFlat, HSysProjectionFlat(gridSys, mainPanel), gridSys.projection(mainPanel))
  proj.setView(viewIn)

  //def tiles: RArr[PolygonFill] = terrs.projRowsCombinePolygons.map { pp => pp.a1.fill(pp.a2.colour) }

  def tiles2: RArr[PolygonFill] = gridSys.map { hc =>
    corners.tilePoly(hc).map { hvo => hvo.toPt2Reg(proj.transCoord(_)) }.fill(terrs(hc).colour)
  }

  def actives: RArr[PolygonActive] = proj.tileActives

  def straits2: GraphicElems = sTerrs.projOptsHsLineSegMap{(st, ls) => Rectangle.fromAxisRatio(ls, 0.3).fill(st.colour) }

  def straits3: GraphicElems = proj.sidesOptMap { (hs: HSide) =>
    val sTerr: Option[WSide] = sTerrs(hs)
    sTerr.map { st => corners.sideVerts(hs).project(proj).fill(st.colour) }
  }

  def lines: RArr[LineSegDraw] = proj.linkLineSegsOptMap { (hs, ls) =>
    if (sTerrs(hs).nonEmpty) None
    else {
      val t1 = terrs(hs.tileLt)
      val t2 = terrs(hs.tileRt)
      ife(t1 == t2, Some(ls.draw(t1.contrastBW)), None)
    }
  }

  def lines2: GraphicElems = proj.ifTileScale(50, lines)

  def lines3: GraphicElems = proj.linksOptMap { hs =>
    val hc1 = hs.tileLt
    val hc2 = hs.tileRt
    val t1 = terrs(hc1)

    def t2 = terrs(hs.tileRt)

    if (sTerrs(hs).nonEmpty | t1 != t2) None
    else {
      val cs: (HCen, Int, Int) = hs.corners
      val ls1 = corners.sideLine(cs._1, cs._2, cs._3)
      val ls2 = ls1.map(hva => hva.toPt2Reg(proj.transCoord(_)))
      Some(ls2.draw(t1.contrastBW))
    }
  }

  def lines4: GraphicElems = proj.ifTileScale(50, lines3)

  //def hexStrs: GraphicElems = proj.hCenSizedMap(15){ (pt, hc) => pt.textAt(hc.rcStr32, 12, terrs(hc).contrastBW) }

  def hexStrs: RArr[TextGraphic] = terrs.hcOptFlatMap { (hc, terr) =>
    proj.transOptCoord(hc).map { pt =>
      val strs: StrArr = StrArr(hc.rcStr32)/*.appendOption(proj.hCoordOptStr(hc))*/ +% hc.strComma
      TextGraphic.lines(strs, 12, pt, terr.contrastBW)
    }
  }

  def hexStrs2: GraphicElems = proj.ifTileScale(72, hexStrs)

  def units: GraphicElems = armies.projSomeHcPtMap { (army, hc, pt) =>
    val str = ptScale.scaledStr(170, army.toString + "\n" + hc.strComma, 150, "A" + "\n" + hc.strComma, 60, army.toString)
    pStrat.UnitCounters.infantry(proj.pixelsPerTile * 0.6, army, army.colour).slate(pt) //.fillDrawTextActive(p.colour, p.polity, str, 24, 2.0)
  }

  override def frame: GraphicElems = tiles2 ++ actives ++ straits3 ++ lines4 ++ hexStrs2 ++ units

  /** Creates the turn button and the action to commit on mouse click. */
  def bTurn: PolygonCompound = clickButton("Turn " + (scen.turn + 1).toString) { _ =>
    //scen = scen.endTurn()
    repaint()
    thisTop()
  }
  statusText = "Welcome to Diceless"

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