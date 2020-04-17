/* Copyright 2018-20 Richard Oliver. Licensed under Apache Licence version 2.0 */
package ostrat
package gTwo
import geom._, pCanv._, pGrid._

case class GTwoGui(canv: CanvasPlatform, scen: TwoScen) extends CanvasNoPanels("Game One Square Grid")
{
  implicit val grid = scen.grid// new SquareGridSimple(2, 8, 2, 10)
  val scale = grid.fullDisplayScale(width, height)
  val sls: LinesDraw = grid.sidesDraw(2.0)
  val csvr = grid.cenRoordTexts() ++ grid.sideRoordTexts() ++ grid.vertRoordTexts()
  val frame = (sls +: csvr ).gridScale(scale)
  repaint(frame)
}
