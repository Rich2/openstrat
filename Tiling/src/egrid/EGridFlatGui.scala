/* Copyright 2018-22 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package egrid
import  pgui._, geom._, prid._, phex._, pEarth._

case class EGridFlatGui(canv: CanvasPlatform, scen: EScenFlat, viewIn: HGView) extends HGridSysGui("North West Europe Gui")
{
  statusText = "Welcome to the new EGrids"
  implicit val gridSys: HGridSys = scen.gridSys
  focus = viewIn.vec
  override var cPScale: Double = viewIn.pxScale
  //def metresScale: Double = cPScale / gridSys.cScale.mMetresNum
  val terrs: HCenDGrid[WTile] = scen.terrs

  def tiles: Arr[PolygonCompound] = gridSys.map{ hc =>
    val str = /*gridSys.hCoordLL(hc).degStr --- */ hc.rcStr
    hc.polygonReg.fillActive(terrs(hc).colour, hc.polygonReg)
  }

  def tileStrs: Arr[PolygonCompound] = gridSys.map{ hc =>
    val str = hc.rcStr32 /* --- gridSys.hCoordLL(hc).degStr */ --- hc.rcStr
    hc.polygonReg.fillTextActive(terrs(hc).colour, hc.polygonReg, str, 12, terrs(hc).contrastBW)
  }

  def thisTop(): Unit = reTop(navButtons)
  def frame: GraphicElems = ife(cPScale > 25, tileStrs,tiles).slate(-focus).scale(cPScale)
  repaint()
  thisTop()
}