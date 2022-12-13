/* Copyright 2018-22 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package egrid
import  pgui._, geom._, prid._, phex._, pEarth._

case class EGridFlatGui(canv: CanvasPlatform, scen: EScenFlat, viewIn: HGView) extends HGridSysGui("North West Europe Gui")
{
  statusText = "Welcome to the new EGrids"
  implicit val gridSys: HGridSys = scen.gridSys
  implicit val proj: HSysProjection = HSysProjectionFlat(gridSys, mainPanel)
  proj.setView(viewIn)
  focus = viewIn.vec
  cPScale = viewIn.cPScale
  //def metresScale: Double = cPScale / gridSys.cScale.mMetresNum
  val terrs: HCenLayer[WTile] = scen.terrs
  val sTerrs: HSideBoolLayer = scen.sTerrs

  def terrPolys: RArr[PolygonFill] = terrs.projRowsCombinePolygons.map { pp => pp.a1.fill(pp.a2.colour) }
  //debvar(terrPolys.length)

 // def sides: GraphicElems = sTerrs.truesMap{hs => Rectangle.fromAxisRatio(hs.lineSegDepr, 0.3).fill(Colour.DarkBlue) }
  def sides1: GraphicElems = sTerrs.projTruesLineSegMap{ls => Rectangle.fromAxisRatio(ls, 0.3).fill(Colour.DarkBlue) }

  def actives: RArr[PolygonActive] = proj.tileActives

  def tileStrs2: GraphicElems = proj.hCenMap{ (pt, hc) => pt.textAt(hc.rcStr --- hc.rcStr32, 12, terrs(hc).contrastBW)}

  def thisTop(): Unit = reTop(proj.buttons)// ++ navButtons)
  def frame: GraphicElems = terrPolys ++ actives ++ sides1 ++ tileStrs2
 //  /* (ife(cPScale > 25, tileStrs, tiles) ++*/( sides).slate(-focus).scale(cPScale)

  mainMouseUp = (b, cl, _) => (b, selected, cl) match {
    case (LeftButton, _, cl) => {
      selected = cl
      statusText = selected.headFoldToString("Nothing Selected")
      thisTop()
    }

    case (_, _, h) => deb("Other; " + h.toString)
  }


  repaint()
  proj.getFrame = () => frame
  proj.setStatusText = { str =>
    statusText = str
    thisTop()
  }
  thisTop()
}