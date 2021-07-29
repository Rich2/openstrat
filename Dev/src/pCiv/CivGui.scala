/* Copyright 2018-21 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package pCiv
import geom._, prid._, pCanv._

/** Gui for civilisation  game. */
case class CivGui(canv: CanvasPlatform, scen: CivScen) extends CmdBarGui("Civ Rise Game Gui")
{ statusText = "Welcome to Civ Rise."
  implicit val grid: HGrid = scen.grid
  val scale = grid.fullDisplayScale(mainWidth, mainHeight)
  val sls = grid.sidesDraw()
  val terrs = scen.terrs
  val tiles = grid.map{ hc => hc.polygonReg.fillTextActive(terrs(hc).colour, hc, hc.str, 16) }
  val lunits = scen.lunits.gridHeadsMap { (roord, lu) =>
    Rectangle.curvedCornersCentred(1.2, 0.8, 0.3, roord.toPt2).parentAll(lu, lu.colour, 2, lu.colour.contrast, 16, 4.toString)
  }

  def thisTop(): Unit = reTop(Arr())
  thisTop()
  def frame = (tiles +- sls ++ lunits).gridScale(scale)
  def repaint() = mainRepaint(frame)
  repaint()
}