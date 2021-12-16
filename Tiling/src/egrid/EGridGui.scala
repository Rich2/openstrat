/* Copyright 2018-21 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package egrid
import  pgui._, prid._

case class EGridGui(canv: CanvasPlatform, scen: EScenBasic) extends CmdBarGui("North West Europe Gui")
{
  statusText = "Welcome to the new EGrids"
  implicit val grid: HGrid = scen.eGrid
  debvar(grid.numTiles)
  val terrs = scen.terrs
  debvar(terrs.length)
  val tiles = grid.map{ hc => hc.polygonReg.fillTextActive(terrs(hc).colour, hc.polygonReg, hc.rcStr, 16) }
  def thisTop(): Unit = reTop(Arr())//zoomIn, zoomOut, goNorth, goSouth, goWest, goEast))

  //repaint()
  thisTop()
}