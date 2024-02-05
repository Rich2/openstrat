/* Copyright 2018-23 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package dless
import geom._, prid._, phex._, pgui._, egrid._

/** Graphical User Interface for [[DLessScen]]. */
class DLessGui(val canv: CanvasPlatform, val game: DLessGame, val settings: DLessSettings) extends EGridBaseGui("Diceless Gui")
{ var scen: DLessScen = game.scen
  override implicit val gridSys: EGridSys = scen.gridSys
  val terrs: LayerHcRefSys[WTile] = scen.terrs
  val sTerrs: LayerHSOptSys[WSep, WSepSome] = scen.sTerrs
  val corners: HCornerLayer = scen.corners
  def armies: LayerHcRArr[Army] = scen.armies

  def NoMoves: HCenStepPairArr[Army] = HCenStepPairArr[Army]()
  var moves: HCenStepPairArr[Army] = NoMoves

  implicit val proj: HSysProjection = ife(settings.isFlat, HSysProjectionFlat(gridSys, mainPanel), gridSys.projection(mainPanel))
  proj.setView(settings.view)

  override def frame: GraphicElems =
  {
    def units: GraphicElems = armies.projSomesHcPtMap { (armies, hc, pt) =>
      val str: String = pixPerTile.scaledStr(170, armies.toString + "\n" + hc.strComma, 150, "A" + "\n" + hc.strComma, 60, armies.toString)
      val ref = ife(armies.length == 1, HCenPair(hc, armies.head), HCenPair(hc, armies))
      val rect = Rect(1.4).fillActiveText(armies.head.colour, ref, armies.foldStr(_.num.str, ", "), 4, armies.head.contrastBW)
      rect.scale(proj.pixelsPerTile * 0.45).slate(pt)
    }

    def moveSegPairs: LineSegPairArr[Army] = moves.optMapOnA1(_.projLineSeg)

    /** This is the graphical display of the planned move orders. */
    def moveGraphics: GraphicElems = moveSegPairs.pairFlatMap { (seg, pl) => seg.draw(lineColour = pl.colour).arrow }

    tileFills ++ tileActives ++ sideFills ++ sideActives ++ lines2 ++ hexStrs2(armies.emptyTile(_)) ++ units ++ moveGraphics
  }

  /** Creates the turn button and the action to commit on mouse click. */
  def bTurn: PolygonCompound = clickButton("Turn " + (scen.turn + 1).toString) { _ =>
    scen = scen.resolve(moves)
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
{ def apply(canv: CanvasPlatform, game: DLessGame, settings: DLessSettings): DLessGui = new DLessGui(canv, game, settings)
}