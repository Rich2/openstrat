/* Copyright 2018-23 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package dless
import geom._, pEarth._, prid._, phex._, pgui._

case class DLessGui(canv: CanvasPlatform, scenIn: DLessScen, viewIn: HGView, isFlat: Boolean = false) extends HGridSysGui("Diceless Gui")
{ var scen = scenIn
  override implicit val gridSys: HGridSys = scenIn.gridSys
  val terrs: HCenLayer[WTile] = scen.terrs
  val sTerrs: HSideOptLayer[WSide] = scen.sTerrs
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
    def tiles: RArr[PolygonFill] = proj.hCensMap { hc =>
      corners.tilePoly(hc).map { hvo => hvo.toPt2(proj.transCoord(_)) }.fill(terrs(hc).colour)
    }

    def actives: RArr[PolygonActive] = proj.hCensMap { hc =>
      corners.tilePoly(hc).map { hvo => hvo.toPt2(proj.transCoord(_)) }.active(hc) }

    def straits: GraphicElems = proj.sidesOptMap { (hs: HSide) =>
      val sTerr: Option[WSide] = sTerrs(hs)
      sTerr.map { st => corners.sideVerts(hs).project(proj).fill(st.colour) }
    }

    def lines1: GraphicElems = proj.linksOptMap { hs =>
      val hc1 = hs.tileLt
      val hc2 = hs.tileRt
      val t1 = terrs(hc1)

      def t2 = terrs(hs.tileRt)

      if (sTerrs(hs).nonEmpty | t1 != t2) None
      else
      { val cs: (HCen, Int, Int) = hs.corners
        val ls1 = corners.sideLine(cs._1, cs._2, cs._3)
        val ls2 = ls1.map(hva => hva.toPt2(proj.transCoord(_)))
        Some(ls2.draw(t1.contrastBW))
      }
    }

    def lines2: GraphicElems = proj.ifTileScale(50, lines1)

    def hexStrs: RArr[TextGraphic] = terrs.hcOptFlatMap { (hc, terr) =>
      proj.transOptCoord(hc).map { pt =>
        val strs: StrArr = StrArr(hc.rcStr32) /*.appendOption(proj.hCoordOptStr(hc))*/ +% hc.strComma
        TextGraphic.lines(strs, 12, pt, terr.contrastBW)
      }
    }

    def hexStrs2: GraphicElems = proj.ifTileScale(72, hexStrs)

    def units: GraphicElems = armies.projSomeHcPtMap { (army, hc, pt) =>
      val str = ptScale.scaledStr(170, army.toString + "\n" + hc.strComma, 150, "A" + "\n" + hc.strComma, 60, army.toString)
      pStrat.UnitCounters.infantry(proj.pixelsPerTile * 0.6, HCenPair(hc, army), army.colour).slate(pt) //.fillDrawTextActive(p.colour, p.polity, str, 24, 2.0)
    }

    def moveSegPairs: LineSegPairArr[Army] = moves.optMapOnA1(_.projLineSeg)

    /** This is the graphical display of the planned move orders. */
    def moveGraphics: GraphicElems = moveSegPairs.pairFlatMap { (seg, pl) => seg.draw(pl.colour).arrow }

    tiles ++ actives ++ straits ++ lines2 ++ hexStrs2 ++ units ++ moveGraphics
  }

  /** Creates the turn button and the action to commit on mouse click. */
  def bTurn: PolygonCompound = clickButton("Turn " + (scen.turn + 1).toString) { _ =>
    scen = scen.endTurn(moves)
    moves = NoMoves
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
    case (RightButton, AnyArrHead(HCenPair(hc1, pl: Army)), hits) => hits.findHCenForEach { hc2 =>
      val newM: Option[HStep] =
      { val ost = gridSys.findStep(hc1, hc2)
        debvar(ost)
        ost
      }
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