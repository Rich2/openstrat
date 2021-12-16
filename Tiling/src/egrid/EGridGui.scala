/* Copyright 2018-21 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package egrid
import  pgui._, prid._, pParse._, eg80._//, pEurope._

case class EGridGui(canv: CanvasPlatform, scen: EScenBasic) extends CmdBarGui("North West Europe Gui")
{

  statusText = "Welcome to the new E80 grids"
  implicit val grid: HGrid = scen.eGrid// EGrid80Km.l0(446)
  deb("st2")
  val terrs = EuropeNW80Terr()
  //val tiles = grid.map{ r => r.polygonReg.fillTextActive(terrs(r).colour, r.toHexTile, r.ycStr, 16) }
  def thisTop(): Unit = reTop(Arr())//zoomIn, zoomOut, goNorth, goSouth, goWest, goEast))

  //repaint()
  thisTop()
}