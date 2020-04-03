/* Copyright 2018-20 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat
package pCiv
import geom._, pGrid._, pCanv._

case class CivGui(canv: CanvasPlatform, scen: CivScen) extends CmdBarGui("Civ Rise Game Gui")
{
  var statusText = "Welcome to Civ Rise."
  implicit val grid = scen.grid
  val scale = grid.fullDisplayScale(mainWidth, mainHeight)
  val sls = grid.sidesDraw(2.0)
  val tiles = grid.activeTiles
  val roardTexts = grid.cenSideVertRoordText


  def thisTop(): Unit = reTop(Refs(status))
  thisTop()
  def frame = (tiles +- sls ++ roardTexts).gridTrans(scale)
  def repaint() = mainRepaint(frame)
  repaint()
}
