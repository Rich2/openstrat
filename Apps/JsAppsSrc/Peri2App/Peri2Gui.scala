/* Copyright 2018-23 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package peri
import geom._, prid._, phex._, pgui._, egrid._, org.scalajs.dom._

class Peri2Gui(val canv: CanvasPlatform, scenIn: PeriScen, viewIn: HGView, isFlat: Boolean = false) extends EGridBaseGui("Peri Gui") {
  val xhr = new XMLHttpRequest()
  xhr.open("POST", "http://37.221.93.158/peri")
  xhr.onload = { (e: Event) =>
    statusText = "Status = " + xhr.status -- xhr.responseType
    deb("Recieved")
    thisTop()
  }
  xhr.send()
  var scen: PeriScen = scenIn
  override implicit val gridSys: EGridSys = scenIn.gridSys

  override def terrs: LayerHcRefSys[WTile] = scen.terrs

  override def sTerrs: LayerHSOptSys[WSep, WSepSome] = scen.sTerrs

  override def corners: HCornerLayer = scen.corners

  def armies: LayerHcOptSys[Army] = scen.armies

  focus = gridSys.cenVec
  pixPerC = gridSys.fullDisplayScale(mainWidth, mainHeight)
  override implicit val proj: HSysProjection = ife(isFlat, HSysProjectionFlat(gridSys, mainPanel), gridSys.projection(mainPanel))
  proj.setView(viewIn)

  override def frame: GraphicElems =
  {
    def units: GraphicElems = armies.projSomesHcPtMap { (army, hc, pt) =>
      Circle(proj.pixelsPerTile / 2).fillActiveText(army.colour, HCenPair(hc, army), army.num.str, 4, army.contrastBW).slate(pt)
    }
    //def moveSegPairs: LineSegPairArr[Army] = moves.optMapOnA1(_.projLineSeg)

    /** This is the graphical display of the planned move orders. */
    //def moveGraphics: GraphicElems = moveSegPairs.pairFlatMap { (seg, pl) => seg.draw(lineColour = pl.colour).arrow }

    tileFills ++ tileActives ++ sideFills ++ sideActives ++ lines2 ++ hexStrs2(armies.emptyTile(_)) ++ units// ++ moveGraphics
  }

  mainMouseUp = (b, cl, _) => (b, selected, cl) match
  {
    case (LeftButton, _, cl) =>
    { selected = cl.headOrNone
      statusText = selectedStr
      thisTop()
    }

    case (RightButton, HCenPair(hc1, army: Army), hits) => hits.findHCenForEach { hc2 =>
      if(gridSys.stepExists(hc1, hc2))
      { scen = scen.invMove(hc1, hc2)
        repaint()
      }
    }
    case (RightButton, sel, a) => deb(s"Just Right, $sel, $a")
    case (_, _, h) => deb("Other; " + h.toString)
  }
  /** Creates the turn button and the action to commit on mouse click. */
  def bTurn: PolygonCompound = clickButton("Turn " /* + (scen.turn + 1).toString*/) { _ =>
    //scen = scen.endTurn(moves)
    //moves = NoMoves
    repaint()
    thisTop()
  }
  override def thisTop(): Unit = reTop(bTurn %: proj.buttons)

  thisTop()

  proj.getFrame = () => frame
  proj.setStatusText = { str =>
    statusText = str
    thisTop()
  }
  mainRepaint(frame)
}

object Peri2Gui
{ def apply(canv: CanvasPlatform, scen: PeriScen, view: HGView, isFlat: Boolean = false): Peri2Gui = new Peri2Gui(canv, scen, view, isFlat)
}