/* Copyright 2018-23 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package pww1
import geom._, pEarth._, prid._, phex._, pgui._

/** 2D graphics class for [[WW1Scen]] games or descriptions. */
case class WW1Gui(canv: CanvasPlatform, scenIn: WW1Scen, viewIn: HGView, isFlat: Boolean = false) extends HGridSysGui("WW1 Gui")
{ var scen = scenIn
  override implicit val gridSys: HGridSys = scenIn.gridSys
  val terrs: HCenLayer[WTile] = scen.terrs
  val sTerrs: HSideOptLayer[WSide] = scen.sTerrs
  def armies: HCenOptLayer[Army] = scen.armies

  focus = gridSys.cenVec
  cPScale = gridSys.fullDisplayScale(mainWidth, mainHeight)
  implicit val proj: HSysProjection = ife(isFlat, HSysProjectionFlat(gridSys, mainPanel), gridSys.projection(mainPanel))
  proj.setView(viewIn)

  def polyFills: RArr[PolygonFill] = terrs.projRowsCombinePolygons.map { pp => pp.a1.fill(pp.a2.colour) }
  def actives: RArr[PolygonActive] = proj.tileActives
  def sides1: GraphicElems = sTerrs.projOptsHsLineSegMap{(st, ls) => Rectangle.fromAxisRatio(ls, 0.3).fill(st.colour) }

  def lines: RArr[LineSegDraw] = proj.linkLineSegsOptMap { (hs, ls) =>
    if (sTerrs(hs).nonEmpty) None
    else
    { val t1 = terrs(hs.tile1)
      val t2 = terrs(hs.tile2)
      ife(t1 == t2, Some(ls.draw(t1.contrastBW)), None)
    }
  }

  def lines2: GraphicElems = proj.ifTileScale(50, lines)

  def hexStrs: GraphicElems = proj.hCenSizedMap(15){ (hc, pt) => pt.textAt(hc.strComma, 12, terrs(hc).contrastBW) }

  def units: GraphicElems = armies.projSomeHcPtMap { (army, hc, pt) =>
    val str = ptScale.scaledStr(170, army.toString + "\n" + hc.strComma, 150, "A" + "\n" + hc.strComma, 60, army.toString)
    pStrat.UnitCounters.infantry(proj.pixelsPerTile * 0.6, army, army.colour).slate(pt) //.fillDrawTextActive(p.colour, p.polity, str, 24, 2.0)
  }

  /** Creates the turn button and the action to commit on mouse click. */
  def bTurn: PolygonCompound = clickButton("Turn " + (scen.turn + 1).toString) { _ =>
    //scen = scen.endTurn()
    repaint()
    thisTop()
  }
  statusText = "Welcome to WW1"

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
  override def frame: GraphicElems = polyFills ++ actives ++ sides1 ++ lines2 ++ hexStrs ++ units

  proj.getFrame = () => frame
  proj.setStatusText = { str =>
    statusText = str
    thisTop()
  }
  mainRepaint(frame)
}