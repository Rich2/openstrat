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
  val terrs = scen.terrs
  val tiles = grid.map{ r => r.tilePoly.fillTextActive(terrs(r).colour, r.toHexTile, r.ycStr, 16) }
  val lunits = scen.lunits.gridHeadsMap { (roord, lu) =>
    Rectangle.curvedCornersCentred(1.2, 0.8, 0.3, roord.gridVec2).parentAll(lu, lu.colour, 2, lu.colour.contrast, 16, 4.toString)
  }

  def thisTop(): Unit = reTop(Arr(status))
  thisTop()
  def frame = (tiles +- sls ++ lunits).gridScale(scale)
  def repaint() = mainRepaint(frame)
  repaint()
}
