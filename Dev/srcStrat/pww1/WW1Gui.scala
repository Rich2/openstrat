/* Copyright 2018-23 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package pww1
import geom._, prid._, phex._, pgui._, egrid._

/** 2D graphics class for [[WW1Scen]] games or descriptions. */
case class WW1Gui(canv: CanvasPlatform, scenIn: WW1Scen, viewIn: HGView, isFlat: Boolean = false) extends EGridBaseGui("WW1 Gui")
{ var scen = scenIn
  override implicit val gridSys: HGridSys = scenIn.gridSys
  val terrs: HCenLayer[WTile] = scen.terrs
  val sTerrs: HSideOptLayer[WSide, WSideSome] = scen.sTerrs
  val corners = scen.corners
  def lunits: HCenOptLayer[Lunit] = scen.lunits

  focus = gridSys.cenVec
  pixPerC = gridSys.fullDisplayScale(mainWidth, mainHeight)
  implicit val proj: HSysProjection = ife(isFlat, HSysProjectionFlat(gridSys, mainPanel), gridSys.projection(mainPanel))
  proj.setView(viewIn)

  override def frame: GraphicElems =
  {
    def hexStrs: GraphicElems = proj.hCenSizedMap(15) { (hc, pt) => pt.textAt(hc.strComma, 12, terrs(hc).contrastBW) }

    def units: GraphicElems = lunits.projSomeHcPtMap { (lunit, hc, pt) =>
      val str = ptScale.scaledStr(170, lunit.toString + "\n" + hc.strComma, 150, "A" + "\n" + hc.strComma, 60, lunit.toString)
      lunit.counter(proj.pixelsPerTile * 0.6, lunit, lunit.colour).slate(pt) //.fillDrawTextActive(p.colour, p.polity, str, 24, 2.0)
    }

    tileFills ++ tileActives ++ sideFills ++ sideActives ++ lines2++ hexStrs ++ units
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
      selected = cl.headOrNone
      statusText = selectedStr
      thisTop()
    }

    /*case (RightButton, HPlayer(hc1, pl), hits) => hits.findHCenForEach { hc2 =>
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