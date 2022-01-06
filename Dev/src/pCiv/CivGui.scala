/* Copyright 2018-22 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package pCiv
import geom._, prid._, phex._, pgui._

/** Gui for civilisation  game. */
case class CivGui(canv: CanvasPlatform, scen: CivScen) extends HexMapGui("Civ Rise Game Gui")
{ statusText = "Welcome to Civ Rise."
  implicit val grid: HGrid = scen.grid
  focus = grid.cenVec
  var cPScale = grid.fullDisplayScale(mainWidth, mainHeight)
  val sls = grid.sidesDraw()
  val terrs = scen.terrs
  val tiles = grid.map{ hc => hc.polygonReg.fillTextActive(terrs(hc).colour, hc, hc.strComma, 16) }
  val lunits = scen.lunits.gridHeadsMap { (hc, lu) =>
    Rectangle.curvedCornersCentred(1.2, 0.8, 0.3, hc.toPt2).parentAll(lu, lu.colour, 2, lu.colour.contrast, 16, 4.toString)
  }

  def thisTop(): Unit = reTop(navButtons)
  thisTop()
  def frame: GraphicElems = (tiles +% sls ++ lunits).slate(-focus).scale(cPScale)
  repaint()
}