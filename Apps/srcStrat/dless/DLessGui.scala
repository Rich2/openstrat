/* Copyright 2018-23 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package dless
import geom._, prid._, phex._, pgui._, egrid._

/** Graphical User Interface for [[DLessScen]]. */
class DLessGui(val canv: CanvasPlatform, scenIn: DLessScen, viewIn: HGView, isFlat: Boolean = false) extends EGridBaseGui("Diceless Gui")
{ var scen: DLessScen = scenIn
  override implicit val gridSys: EGridSys = scenIn.gridSys
  val terrs: HCenLayer[WTile] = scen.terrs
  val sTerrs: HSideOptLayer[WSide, WSideSome] = scen.sTerrs
  val corners = scen.corners
  def armies: HCenRArrLayer[Army] = scen.armies

  def NoMoves: HCenStepPairArr[Army] = HCenStepPairArr[Army]()
  var moves: HCenStepPairArr[Army] = NoMoves

  focus = gridSys.cenVec
  pixPerC = gridSys.fullDisplayScale(mainWidth, mainHeight)
  implicit val proj: HSysProjection = ife(isFlat, HSysProjectionFlat(gridSys, mainPanel), gridSys.projection(mainPanel))
  proj.setView(viewIn)

  override def frame: GraphicElems =
  {
    def units: GraphicElems = armies.projSomesHcPtMap { (armies, hc, pt) =>
      val str = pixPerTile.scaledStr(170, armies.toString + "\n" + hc.strComma, 150, "A" + "\n" + hc.strComma, 60, armies.toString)
      val ref = ife(armies.length == 1, HCenPair(hc, armies.head), HCenPair(hc, armies))
      pStrat.InfantryCounter(proj.pixelsPerTile * 0.45, ref, armies.head.colour).slate(pt)
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

  statusText = "Welcome to Diceless"

  override def selectedStr: String = selected match
  { case hc: HCen => hc.rcStr -- terrs(hc).strSemi
    case _ => super.selectedStr
  }

  mainMouseUp = (b, cl, _) => (b, selected, cl) match
  {
    case (LeftButton, _, cl) =>
    { selected = cl.headOrNone
      statusText = selectedStr
      thisTop()
    }

    case (RightButton, HCenPair(hc1, army: Army), hits) => hits.findHCenForEach { hc2 =>
      val newM: Option[HStep] = gridSys.stepFind(hc1, hc2)
      newM.foreach { d => moves = moves.replaceA1byA2OrAppend(army, hc1.andStep(d)) }
      repaint()
    }

    case (_, sel, hits) => deb(s"Other; $sel " + hits.toString)
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

object DLessGui
{ def apply(canv: CanvasPlatform, scen: DLessScen, view: HGView, isFlat: Boolean = false): DLessGui = new DLessGui(canv, scen, view, isFlat)
}