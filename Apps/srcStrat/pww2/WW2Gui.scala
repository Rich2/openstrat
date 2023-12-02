/* Copyright 2018-23 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package pww2
import geom._, prid._, phex._, pgui._, egrid._

/** Graphical user interface for WW2 game. */
case class WW2Gui(canv: CanvasPlatform, scenIn: WW2Scen, viewIn: HGView, isFlat: Boolean = false) extends EGridBaseGui("WW2 Gui")
{ var scen: WW2Scen = scenIn
  override implicit val gridSys: HGridSys = scenIn.gridSys
  val terrs: LayerHcSys[WTile] = scen.terrs
  val sTerrs: HSideOptLayer[WSide, WSideSome] = scen.sTerrs
  val corners: HCornerLayer = scen.corners
  def armies: HCenOptLayer[Army] = scen.armies

  focus = gridSys.cenVec
  pixPerC = gridSys.fullDisplayScale(mainWidth, mainHeight)
  implicit val proj: HSysProjection = ife(isFlat, HSysProjectionFlat(gridSys, mainPanel), gridSys.projection(mainPanel))
  proj.setView(viewIn)

  def NoMoves: HCenStepPairArr[Army] = HCenStepPairArr[Army]()
  var moves: HCenStepPairArr[Army] = NoMoves

  override def frame: GraphicElems =
  {
    def units: GraphicElems = armies.projSomesHcPtMap { (army, hc, pt) =>
      val str = pixPerTile.scaledStr(170, army.toString + "\n" + hc.strComma, 150, "A" + "\n" + hc.strComma, 60, army.toString)
      pStrat.InfantryCounter(proj.pixelsPerTile * 0.45, HCenPair(hc, army), army.colour).slate(pt)
    }

    def moveSegPairs: LineSegPairArr[Army] = moves.optMapOnA1(_.projLineSeg)

    /** This is the graphical display of the planned move orders. */
    def moveGraphics: GraphicElems = moveSegPairs.pairFlatMap { (seg, pl) => seg.draw(lineColour = pl.colour).arrow }

    tileFills ++ tileActives ++ sideFills ++ sideActives ++ lines2 ++ hexStrs2(armies.emptyTile(_)) ++ units ++ moveGraphics
  }

  /** Creates the turn button and the action to commit on mouse click. */
  def bTurn: PolygonCompound = clickButton("Turn " + (scen.turn + 1).toString) { _ =>
    scen = scen.endTurn(moves)
    moves = NoMoves
    repaint()
    thisTop()
  }
  statusText = "Welcome to WW2"

  mainMouseUp = (b, cl, _) => (b, selected, cl) match
  { case (LeftButton, _, cl) =>
    { selected = cl.headOrNone
      statusText = selectedStr
      thisTop()
    }

    case (RightButton, HCenPair(hc1, army: Army), hits) => hits.findHCenForEach { hc2 =>
      val newM: Option[HStep] = gridSys.stepFind(hc1, hc2)
      newM.foreach { d => moves = moves.replaceA1byA2OrAppend(army, hc1.andStep(d)) }
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