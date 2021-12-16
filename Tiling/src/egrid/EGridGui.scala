/* Copyright 2018-21 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package egrid
import  pgui._, geom._, prid._, pEarth._

case class EGridGui(canv: CanvasPlatform, scen: EScenBasic) extends HexMapGui("North West Europe Gui")
{
  statusText = "Welcome to the new EGrids"
  implicit val grid: HGrid = scen.eGrid
  var rScale: Double = grid.fullDisplayScale(mainWidth, mainHeight)
  val terrs: HCenArr[WTile] = scen.terrs
  val tiles = grid.map{ hc => hc.polygonReg.fillTextActive(terrs(hc).colour, hc.polygonReg, hc.rcStr, 16, terrs(hc).contrastBW) }
  def thisTop(): Unit = reTop(Arr(zoomIn, zoomOut))//, goNorth, goSouth, goWest, goEast))
  def frame: GraphicElems = (tiles).gridScale(rScale)
  repaint()
  thisTop()
}