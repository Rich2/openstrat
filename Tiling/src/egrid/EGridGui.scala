/* Copyright 2018-22 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package egrid
import  pgui._, geom._, prid._, phex._, pEarth._

case class EGridGui(canv: CanvasPlatform, scen: EScenBasic, viewIn: HGridView) extends HexMapGui("North West Europe Gui")
{
  statusText = "Welcome to the new EGrids"
  implicit val grider: EGridMain = scen.eGrid
  focus = viewIn.vec
  var cPScale: Double = viewIn.pxScale
  def metresScale = cPScale / grider.cScale.mMetresNum

  val terrs: HCenDGrid[WTile] = scen.terrs
  def tiles = grider.map{ hc =>
    val str = grider.hCoordLL(hc).degStr --- hc.rcStr
    hc.polygonReg.fillActive(terrs(hc).colour, hc.polygonReg)
  }
  def tileStrs = grider.map{ hc =>
    val str = hc.rcStr32 --- grider.hCoordLL(hc).degStr --- hc.rcStr
    hc.polygonReg.fillTextActive(terrs(hc).colour, hc.polygonReg, str, 16, terrs(hc).contrastBW)
  }

  def thisTop(): Unit = reTop(navButtons)
  def frame: GraphicElems = ife(metresScale > 1400, tileStrs,tiles).slate(-focus).scale(cPScale)
  repaint()
  thisTop()
}