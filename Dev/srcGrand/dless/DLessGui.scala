/* Copyright 2018-23 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package dless
import geom._, pEarth._, prid._, phex._, pgui._

case class DLessGui(canv: CanvasPlatform, scenIn: DLessScen, viewIn: HGView, isFlat: Boolean = false) extends HGridSysGui("Diceless Gui")
{ var scen = scenIn
  override implicit val gridSys: HGridSys = scenIn.gridSys
  val terrs: HCenLayer[WTile] = scen.terrs
  val sTerrs: HSideOptLayer[WSide] = scen.sTerrs

  focus = gridSys.cenVec
  cPScale = gridSys.fullDisplayScale(mainWidth, mainHeight)
  implicit val proj: HSysProjection = ife(isFlat, HSysProjectionFlat(gridSys, mainPanel), gridSys.projection(mainPanel))
  proj.setView(viewIn)

  def polyFills: RArr[PolygonFill] = terrs.projRowsCombinePolygons.map { pp => pp.a1.fill(pp.a2.colour) }
  def actives: RArr[PolygonActive] = proj.tileActives

  /** Note we only represent links, no outer sides, so as the side terrain can use data from both of its adjacent tiles. */

  def straits2: GraphicElems = sTerrs.projOptsHsLineSegMap{(st, ls) => Rectangle.fromAxisRatio(ls, 0.3).fill(st.colour) }


  def lines = proj.linkLineSegsOptMap { (hs, ls) =>
    if (sTerrs(hs).nonEmpty) None
    else {
      val t1 = terrs(hs.tile1)
      val t2 = terrs(hs.tile2)
      ife(t1 == t2, Some(ls.draw(t1.contrastBW)), None)
    }
  }

  def lines2: GraphicElems = proj.ifTileScale(50, lines)

  //def hexStrs: GraphicElems = proj.hCenSizedMap(15){ (pt, hc) => pt.textAt(hc.rcStr32, 12, terrs(hc).contrastBW) }

  def hexStrs: RArr[TextGraphic] = terrs.hcOptFlatMap { (hc, terr) =>
    proj.transOptCoord(hc).map { pt =>
      val strs: StrArr = StrArr(hc.rcStr32).appendOption(proj.hCoordOptStr(hc)) +% hc.strComma
      TextGraphic.lines(strs, 12, pt, terr.contrastBW)
    }
  }

  def hexStrs2: GraphicElems = proj.ifTileScale(72, hexStrs)

  override def frame: GraphicElems = polyFills ++ actives ++ straits2 ++ lines2 ++ hexStrs2

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