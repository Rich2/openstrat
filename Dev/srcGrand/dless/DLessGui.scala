/* Copyright 2018-23 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package dless
import geom._, pEarth._, prid._, phex._, pgui._

case class DLessGui(canv: CanvasPlatform, scenIn: DLessScen, viewIn: HGView, isFlat: Boolean = false) extends HGridSysGui("Diceless Gui")
{ var scen = scenIn
  override implicit val gridSys: HGridSys = scenIn.gridSys
  val terrs: HCenLayer[WTile] = scen.terrs
  val sTerrs: HSideBoolLayer = scen.sTerrs
  focus = gridSys.cenVec
  cPScale = gridSys.fullDisplayScale(mainWidth, mainHeight)
  implicit val proj: HSysProjection = ife(isFlat, HSysProjectionFlat(gridSys, mainPanel), gridSys.projection(mainPanel))
  proj.setView(viewIn)

  def polyFills: RArr[PolygonFill] = terrs.projRowsCombinePolygons.map { pp => pp.a1.fill(pp.a2.colour) }
  def actives: RArr[PolygonActive] = proj.tileActives
  def sides1: GraphicElems = sTerrs.projTruesLineSegMap{ls => Rectangle.fromAxisRatio(ls, 0.3).fill(Colour.DarkBlue) }
  def lines: RArr[LineSegDraw] = terrs.projLinksLineOptMap{ (ls, t1, t2 ) => ife(t1 == t2, Some(ls.draw(t1.contrastBW)), None) }

  def lines2: RArr[LineSegDraw] = sTerrs.projFalseLinksScLineSegOptMap(proj){ (hs, ls) =>
    val t1 = terrs(hs.tile1Reg)
    val t2 = terrs(hs.tile2Reg)
    ife( t1 == t2, Some(ls.draw(t1.contrastBW)), None)
  }

  def hexStrs: GraphicElems = proj.hCenSizedMap(15){ (pt, hc) => pt.textAt(hc.strComma, 12, terrs(hc).contrastBW) }

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
  override def frame: GraphicElems = polyFills ++ actives ++ sides1 ++ lines2 ++ hexStrs

  proj.getFrame = () => frame
  proj.setStatusText = { str =>
    statusText = str
    thisTop()
  }
  mainRepaint(frame)
}