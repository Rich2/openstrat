/* Copyright 2018-23 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package pCiv
import geom._, prid._, phex._, pgui._

/** Gui for civilisation  game. */
case class CivGui(canv: CanvasPlatform, scen: CivScen) extends HGridSysGui("Civ Rise Game Gui")
{ statusText = "Welcome to Civ Rise."
  implicit val gridSys: HGridSys = scen.gridSys
  val terrs: LayerHcRefSys[VTile] = scen.terrs
  val sTerrs: LayerHSOptSys[VSep, VSepSome] = scen.sTerrs
  val corners: HCornerLayer = scen.corners
  val lunits: LayerHcRArr[Warrior] = scen.lunits

  implicit val proj: HSysProjection = gridSys.projection(mainPanel)
  //def view: HGView()
  //proj.setView(viewIn)

  def frame: GraphicElems =
  { def tileFillActives: GraphicElems = terrs.projHCenPolyMap(proj, corners){ (hc, poly, t) => poly.fillActive(t.colour, hc) }
    def sideFills: GraphicElems = sTerrs.somePolyMap(proj, corners){ (st, poly) => poly.fill(st.colour) }
    def sideActives: GraphicElems = sTerrs.someOnlyHSPolyMap(proj, corners){ (hs, poly) => poly.active(hs) }

    def lines1: GraphicElems = proj.linksOptMap { hs =>
      def t1: VTile = terrs(hs.tileLt)
      def t2: VTile = terrs(hs.tileRt)

      sTerrs(hs) match
      { case VSepNone if t1.colour == t2.colour =>
        { val cs: (HCen, Int, Int) = hs.cornerNums
          val ls1: LineSeg = corners.sideLine(cs._1, cs._2, cs._3)
          Some(ls1.draw(lineColour = t1.contrastBW))
        }
        case _: VSepSome if t1.isWater => Some(hs.leftCorners(corners).map(proj.transHVOffset).draw(lineColour = t1.contrastBW))
        case _: VSepSome if t2.isWater => Some(hs.rightCorners(corners).map(proj.transHVOffset).draw(lineColour = t2.contrastBW))
        case _ => None
      }
    }

    def unitFills: RArr[PolyCurveParentFull] = lunits.headsMap { (hc, lu) =>
      Rectangle.curvedCornersCentred(120, 80, 3, hc.projPt2).parentAll(lu, lu.colour, 2, lu.colour.contrast, 16, 4.toString) }

    def texts: RArr[TextFixed] = lunits.projEmptyHcPtMap(proj) { (hc, pt) => pt.textAt(hc.rcStr, 16, terrs(hc).contrastBW) }

    tileFillActives ++ unitFills ++ sideFills ++ sideActives ++ lines1 ++ texts
  }

  mainMouseUp = (b, cl, _) => (b, selected, cl) match
  { case (LeftButton, _, cl) =>
    { selected = cl.headOrNone
      statusText = selectedStr
      thisTop()
    }

    /*case (RightButton, HCenPair(hc1, army: Army), hits) => hits.findHCenForEach { hc2 =>
      val newM: Option[HStep] = gridSys.findStep(hc1, hc2)
      newM.foreach { d => moves = moves.replaceA1byA2OrAppend(army, hc1.andStep(d)) }
      repaint()
    }*/

    case (_, _, h) => deb("Other; " + h.toString)
  }

  def thisTop(): Unit = reTop(proj.buttons)

  proj.getFrame = () => frame
  proj.setStatusText = { str =>
    statusText = str
    thisTop()
  }

  thisTop()
  repaint()
}