/* Copyright 2018-23 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package pww2
import geom._, prid._, phex._, pgui._, egrid._, pStrat._

/** Graphical user interface for WW2 game. */
case class WW2Gui(canv: CanvasPlatform, scenIn: WW2Scen, viewIn: HGView, isFlat: Boolean = false) extends EGridBaseGui("WW2 Gui")
{ var scen: WW2Scen = scenIn
  override implicit val gridSys: HGridSys = scenIn.gridSys
  val terrs: LayerHcRefSys[WTile] = scen.terrs
  val sTerrs: LayerHSOptSys[WSide, WSideSome] = scen.sTerrs
  val corners: HCornerLayer = scen.corners
  def armies: LayerHcRArr[Lunit] = scen.lunitSts

  implicit val proj: HSysProjection = ife(isFlat, HSysProjectionFlat(gridSys, mainPanel), gridSys.projection(mainPanel))
  proj.setView(viewIn)

  def NoMoves: HCenStepPairArr[BrArmyDesigNum] = HCenStepPairArr[BrArmyDesigNum]()
  var moves: HCenStepPairArr[BrArmyDesigNum] = NoMoves

  override def frame: GraphicElems =
  {
    def units: GraphicElems = armies.projSomesHcPtMap { (armies, hc, pt) =>
      val pt2 = tilePolys.a1GetA2(hc).inRectFrom(pt, 1.5).cen
      val str: String = pixPerTile.scaledStr(170, armies.toString + "\n" + hc.strComma, 150, "A" + "\n" + hc.strComma, 60, armies.toString)
      val head: Lunit = armies.head
      val ref = ife(armies.length == 1, HCenPair(hc, head), HCenPair(hc, armies))
      head.counter(proj.pixelsPerTile * 0.45, ref, head.colour).slate(pt2)
    }

    def moveSegPairs: LineSegPairArr[BrArmyDesigNum] = moves.optMapOnA1(_.projLineSeg)

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

    case (RightButton, HCenPair(hc1, army: BrArmyDesigNum), hits) => hits.findHCenForEach { hc2 =>
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