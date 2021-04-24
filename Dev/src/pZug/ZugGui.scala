/* Copyright 2018-21 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package pZug
import pCanv._, prid._, geom._, Colour._, pStrat._

/** Uses the old Roards from pGrid, but with the new simpler Gui. */
case class ZugGui(canv: CanvasPlatform, scen: ZugScen) extends CmdBarGui("ZugFuhrer Gui")
{
  implicit val grid: HGrid = scen.grid
  val scale = grid.fullDisplayScale(mainWidth, mainHeight)
  val terrs = scen.terrs
  //val tiles = grid.map{ r => r.tilePoly.fillTextActive(terrs(r).colour, r.toHexTile, r.ycStr, 16) }

  override var statusText: String = "Welcome to ZugFuher"
  def thisTop(): Unit = reTop(Arr())
  thisTop()
}