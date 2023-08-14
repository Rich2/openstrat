/* Copyright 2018-23 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package pnap
import geom._, prid._, phex._, pgui._, egrid._

case class NapGui(canv: CanvasPlatform, scenIn: NapScen, viewIn: HGView, isFlat: Boolean = false) extends EGridBaseGui("AD1783 Gui")
{ var scen = scenIn
  override implicit val gridSys: HGridSys = scenIn.gridSys
  def terrs: HCenLayer[WTile] = scen.terrs
  def sTerrs: HSideOptLayer[WSide, WSideSome] = scen.sTerrs
  val corners: HCornerLayer = scen.corners
  def corps: HCenOptLayer[Corps] = scen.corps

  def NoMoves: HCenStepPairArr[Corps] = HCenStepPairArr[Corps]()

  var moves: HCenStepPairArr[Corps] = NoMoves

  focus = gridSys.cenVec
  pixPerC = gridSys.fullDisplayScale(mainWidth, mainHeight)
  implicit val proj: HSysProjection = ife(isFlat, HSysProjectionFlat(gridSys, mainPanel), gridSys.projection(mainPanel))
  proj.setView(viewIn)

  override def frame: GraphicElems =
  {
    def units: GraphicElems = corps.projSomesHcPtMap { (corps, hc, pt) =>
      val str = pixPerTile.scaledStr(170, corps.toString + "\n" + hc.strComma, 150, "A" + "\n" + hc.strComma, 60, corps.toString)
      pStrat.InfantryCounter(proj.pixelsPerTile * 0.6, HCenPair(hc, corps), corps.colour).slate(pt) //.fillDrawTextActive(p.colour, p.polity, str, 24, 2.0)
    }

    tileFills ++ tileActives ++ sideFills ++ sideActives ++ lines2 ++ hexStrs2(corps.emptyTile(_)) ++ units
  }

  /** Creates the turn button and the action to commit on mouse click. */
  def bTurn: PolygonCompound = clickButton("Turn " + (scen.turn + 1).toString) { _ =>
    //scen = scen.endTurn()
    repaint()
    thisTop()
  }
  statusText = "Welcome to AD1783"

  mainMouseUp = (b, cl, _) => (b, selected, cl) match
  { case (LeftButton, _, cl) =>
    { selected = cl.headOrNone
      statusText = selectedStr
      thisTop()
    }

    case (RightButton, HCenPair(hc1, pl: Corps), hits) => hits.findHCenForEach { hc2 =>
      val newM: Option[HStep] = gridSys.stepFind(hc1, hc2)
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