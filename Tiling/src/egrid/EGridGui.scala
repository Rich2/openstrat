/* Copyright 2018-21 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package egrid
import  pgui._, geom._, prid._, pEarth._

case class EGridGui(canv: CanvasPlatform, scen: EScenBasic) extends HexMapGui("North West Europe Gui")
{
  statusText = "Welcome to the new EGrids"
  implicit val grid: EGridMain = scen.eGrid
  var rScale: Double = grid.fullDisplayScale(mainWidth, mainHeight)
  def metresScale = rScale / grid.cScale.mMetresNum
  debvar(metresScale)
  val terrs: HCenArr[WTile] = scen.terrs
  def tiles = grid.map{ hc =>
    val str = grid.hCoordLL(hc).degStr --- hc.rcStr
    hc.polygonReg.fillActive(terrs(hc).colour, hc.polygonReg)
  }
  def tileStrs = grid.map{ hc =>
    val str = grid.hCoordLL(hc).degStr --- hc.rcStr
    hc.polygonReg.fillTextActive(terrs(hc).colour, hc.polygonReg, str, 16, terrs(hc).contrastBW)
  }

  def thisTop(): Unit = reTop(Arr(zoomIn, zoomOut))//, goNorth, goSouth, goWest, goEast))
  def frame: GraphicElems = ife(metresScale > 550, tileStrs,tiles).gridScale(rScale)
  repaint()
  thisTop()
}