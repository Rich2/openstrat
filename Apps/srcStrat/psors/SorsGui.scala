/* Copyright 2018-23 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package psors
import geom._, prid._, phex._, pgui._, egrid._

case class SorsGui(canv: CanvasPlatform, scenIn: SorsScen, viewIn: HGView, isFlat: Boolean = false) extends EGridBaseGui("Sors Imperiorum Gui")
{ var scen = scenIn
  override implicit val gridSys: HGridSys = scenIn.gridSys
  val terrs: LayerHcRefSys[WTile] = scen.terrs
  val sTerrs: LayerHSOptSys[WSide, WSideSome] = scen.sTerrs
  val corners = scen.corners

  focus = gridSys.cenVec
  pixPerC = gridSys.fullDisplayScale(mainWidth, mainHeight)
  implicit val proj: HSysProjection = ife(isFlat, HSysProjectionFlat(gridSys, mainPanel), gridSys.projection(mainPanel))
  proj.setView(viewIn)

  override def frame: GraphicElems =
  { def hexStrs1: GraphicElems = proj.hCenSizedMap(15) { (hc, pt) => pt.textAt(hc.strComma, 12, terrs(hc).contrastBW) }

    def hexStrs2: GraphicElems = proj.ifTileScale(55, hexStrs1)

    tileFills ++ tileActives ++ sideFills ++ sideActives ++ lines2 ++ hexStrs2
  }

  /** Creates the turn button and the action to commit on mouse click. */
  def bTurn: PolygonCompound = clickButton("Turn " + (scen.turn + 1).toString) { _ =>
    //scen = scen.endTurn()
    repaint()
    thisTop()
  }
  statusText = "Welcome to Sors Imperiorum"

  mainMouseUp = (b, cl, _) => (b, selected, cl) match
  { case (LeftButton, _, cl) =>
    { selected = cl.headOrNone
      statusText = selectedStr
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