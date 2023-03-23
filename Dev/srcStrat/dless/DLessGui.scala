/* Copyright 2018-23 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package dless
import geom._, prid._, phex._, pgui._, egrid._

case class DLessGui(canv: CanvasPlatform, scenIn: DLessScen, viewIn: HGView, isFlat: Boolean = false) extends EGridBaseGui("Diceless Gui")
{ var scen: DLessScen = scenIn
  override implicit val gridSys: EGridSys = scenIn.gridSys
  val terrs: HCenLayer[WTile] = scen.terrs
  val sTerrs: HSideLayer[WSide] = scen.sTerrs
  val corners = scen.corners
  def armies: HCenOptLayer[Army] = scen.armies

  def NoMoves: HCenStepPairArr[Army] = HCenStepPairArr[Army]()
  var moves: HCenStepPairArr[Army] = NoMoves

  focus = gridSys.cenVec
  cPScale = gridSys.fullDisplayScale(mainWidth, mainHeight)
  implicit val proj: HSysProjection = ife(isFlat, HSysProjectionFlat(gridSys, mainPanel), gridSys.projection(mainPanel))
  proj.setView(viewIn)

  override def frame: GraphicElems =
  {
    //def lines2: GraphicElems = proj.ifTileScale(50, lines1)

    def hexStrs: RArr[TextGraphic] = terrs.hcOptFlatMap { (hc, terr) =>
      proj.transOptCoord(hc).map { pt =>
        val strs: StrArr = StrArr(hc.rcStr32) /*.appendOption(proj.hCoordOptStr(hc))*/ +% hc.strComma
        TextGraphic.lines(strs, 12, pt, terr.contrastBW)
      }
    }

    def hexStrs2: GraphicElems = proj.ifTileScale(72, hexStrs)

    def units: GraphicElems = armies.projSomeHcPtMap { (army, hc, pt) =>
      val str = ptScale.scaledStr(170, army.toString + "\n" + hc.strComma, 150, "A" + "\n" + hc.strComma, 60, army.toString)
      pStrat.UnitCounters.infantry(proj.pixelsPerTile * 0.45, HCenPair(hc, army), army.colour).slate(pt) //.fillDrawTextActive(p.colour, p.polity, str, 24, 2.0)
    }

    def moveSegPairs: LineSegPairArr[Army] = moves.optMapOnA1(_.projLineSeg)

    /** This is the graphical display of the planned move orders. */
    def moveGraphics: GraphicElems = moveSegPairs.pairFlatMap { (seg, pl) => seg.draw(pl.colour).arrow }

    tileBackFills ++ tileFrontFills ++ tileActives ++ sideFills ++ sideActives ++ lines2 ++ lines4 ++ hexStrs2 ++ units ++ moveGraphics
  }

  /** Creates the turn button and the action to commit on mouse click. */
  def bTurn: PolygonCompound = clickButton("Turn " + (scen.turn + 1).toString) { _ =>
    scen = scen.endTurn(moves)
    moves = NoMoves
    repaint()
    thisTop()
  }

  statusText = "Welcome to Diceless"

  mainMouseUp = (b, cl, _) => (b, selected, cl) match
  {
    case (LeftButton, _, cl) =>
    { selected = cl
      statusText = selected.headFoldToString("Nothing Selected")
      thisTop()
    }

    case (RightButton, AnyArrHead(HCenPair(hc1, army: Army)), hits) => hits.findHCenForEach { hc2 =>
      val newM: Option[HStep] = gridSys.findStep(hc1, hc2)
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