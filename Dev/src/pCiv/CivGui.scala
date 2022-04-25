/* Copyright 2018-22 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package pCiv
import geom._, prid._, phex._, pgui._

/** Gui for civilisation  game. */
case class CivGui(canv: CanvasPlatform, scen: CivScen) extends HGridFlatGui("Civ Rise Game Gui")
{ statusText = "Welcome to Civ Rise."
  implicit val gridSys: HGrid = scen.gridSys
  focus = gridSys.cenVec
  var cPScale = gridSys.fullDisplayScale(mainWidth, mainHeight)
  val sls = gridSys.sidesDraw()
  val terrs = scen.terrs
  val tiles = gridSys.map{ hc => hc.polygonReg.fillTextActive(terrs(hc).colour, hc, hc.strComma, 16) }
  val lunits = scen.lunits.gridHeadsMap { (hc, lu) =>
    Rectangle.curvedCornersCentred(1.2, 0.8, 0.3, hc.toPt2Reg).parentAll(lu, lu.colour, 2, lu.colour.contrast, 16, 4.toString)
  }

  def thisTop(): Unit = reTop(navButtons)
  thisTop()
  def frame: GraphicElems = (tiles +% sls ++ lunits).slate(-focus).scale(cPScale)
  repaint()
}