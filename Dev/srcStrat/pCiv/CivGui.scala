/* Copyright 2018-23 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package pCiv
import geom._, prid._, phex._, pgui._

/** Gui for civilisation  game. */
case class CivGui(canv: CanvasPlatform, scen: CivScen) extends HGridSysGui("Civ Rise Game Gui")
{ statusText = "Welcome to Civ Rise."
  implicit val gridSys: HGridSys = scen.gridSys
  val terrs: HCenLayer[VTile] = scen.terrs
  val sTerrs: HSideOptLayer[VSideSome] = scen.sTerrs
  val corners: HCornerLayer = scen.corners
  val lunits: HCenArrLayer[Warrior] = scen.lunits

  focus = gridSys.cenVec
  cPScale = gridSys.fullDisplayScale(mainWidth, mainHeight)
  implicit val proj: HSysProjection = gridSys.projection(mainPanel)
  //def view: HGView()
  //proj.setView(viewIn)
  def frame: GraphicElems =
  {  def tileFills2: GraphicElems = terrs.projHCenPolyMap(proj, corners) { (hc, poly, t) => poly.fillTextActive(t.colour, hc,hc.strComma, 16, t.contrastBW) }

    def sides1: GraphicElems = proj.sidesOptMap { (hs: HSide) =>
      val sTerr: Option[VSideSome] = sTerrs(hs)
      sTerr.map{
        case st: VSideCen => corners.sideVerts(hs).project(proj).fill(st.colour)
        case st => corners.sideVerts(hs).project(proj).fill(st.colour)
      }
    }

    def lines1: GraphicElems = proj.linksOptMap { hs =>
      val hc1 = hs.tileLt
      val hc2 = hs.tileRt
      val t1 = terrs(hc1)
      val t2 = terrs(hc2)
      if (sTerrs(hs).nonEmpty | t1.colour != t2.colour) None
      else {
        val cs: (HCen, Int, Int) = hs.corners
        val ls1 = corners.sideLine(cs._1, cs._2, cs._3)
        val ls2 = ls1.map(hva => hva.toPt2(proj.transCoord(_)))
        Some(ls2.draw(t1.contrastBW))
      }
    }

    def unitFills: RArr[PolyCurveParentFull] = lunits.gridHeadsMap { (hc, lu) =>
      Rectangle.curvedCornersCentred(120, 80, 3, hc.toPt2).parentAll(lu, lu.colour, 2, lu.colour.contrast, 16, 4.toString)
    }

    tileFills2 ++ unitFills ++ sides1 ++ lines1
  }

  mainMouseUp = (b, cl, _) => (b, selected, cl) match
  {
    case (LeftButton, _, cl) => {
      selected = cl
      statusText = selected.headFoldToString("Nothing Selected")
      thisTop()
    }

    /*case (RightButton, AnyArrHead(HCenPair(hc1, army: Army)), hits) => hits.findHCenForEach { hc2 =>
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