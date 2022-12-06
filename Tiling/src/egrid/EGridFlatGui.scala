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

  def terrPolys: RArr[PolygonFill] = terrs.projRowsCombinePolygons.map { pt => pt.a1.fill(pt.a2.colour) }
  //debvar(terrPolys.length)
  gridSys match {
    case gs: EGridLongMulti => { deb(s"hcDelta ${gs.hcDelta}")
      deb(s"cGridDelta ${gs.gridsXSpacing}") }
    case _ =>
  }

  def tiles: RArr[PolygonCompound] = gridSys.map{ hc => hc.polygonReg.fillActive(terrs(hc).colour.modAlpha(128), hc.polygonReg) }
  def sides: GraphicElems = sTerrs.truesMap{hs => Rectangle.fromAxisRatio(hs.lineSegDepr, 0.3).fill(Colour.DarkBlue) }

  def tileStrs: RArr[PolygonCompound] = gridSys.map{ hc =>
    hc.polygonReg.fillTextActive(terrs(hc).colour.modAlpha(128), hc.polygonReg, hc.rcStr32 --- hc.rcStr, 12, terrs(hc).contrastBW)
  }

  def thisTop(): Unit = reTop(proj.buttons ++ navButtons)
  def frame: GraphicElems = //terrPolys ++
    (ife(cPScale > 25, tileStrs, tiles) ++ sides).slate(-focus).scale(cPScale)
  repaint()
  proj.getFrame = () => frame
  proj.setStatusText = { str =>
    statusText = str
    thisTop()
  }
  thisTop()
}