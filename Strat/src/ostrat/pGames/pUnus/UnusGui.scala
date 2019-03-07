/* Copyright 2018 Richard Oliver. Licensed under Apache Licence version 2.0 */
package ostrat
package pGames.pUnus
import geom._, pCanv._, pGrid._

class UnusGui(canv: CanvasPlatform, grid: UnusGrid)
{
  new UnusSetGui(canv, grid)
}

class UnusSetGui(val canv: CanvasPlatform, val grid: UnusGrid) extends TileGridGui[UTile, UnusGrid]("Unus Game")
{
  //Required members
  var pScale: Double = 10
  var focus: Vec2 = grid.cen 
  def mapObjs = Nil
  
  //optional members
  def stuff: List[Cood] = grid.tileCoodMap{c => c}
  mapPanel.backColour = Colour.Wheat
  eTop()
  mapPanel.repaint(Nil)
}