/* Copyright 2018-21 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package pEarth
import  pgui._, prid._, pParse._, pEurope._

case class E80GridGui(canv: CanvasPlatform) extends CmdBarGui("North West Europe Gui")
{
  statusText = "Welcome to the new E80 grids"
  implicit val grid: HGrid = EuropeNWGrid
  val terrs = EuropeNW80Terr()
  //val tiles = grid.map{ r => r.polygonReg.fillTextActive(terrs(r).colour, r.toHexTile, r.ycStr, 16) }
  def thisTop(): Unit = reTop(Arr())//zoomIn, zoomOut, goNorth, goSouth, goWest, goEast))

  //repaint()
  thisTop()
}

/** object to launch EarthBasic Gui. */
object E80BasicLaunch extends GuiLaunchMore {
  override def settingStr: String = "E80Grid"

  override def default: (CanvasPlatform => Any, String) = (cv => E80GridGui(cv), "JavaFx Eath 80KM Grid")

  override def fromStatments(sts: Arr[Statement]): (CanvasPlatform => Any, String) = (cv => E80GridGui(cv), "JavaFx Earth")
}
