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
  var pScale: Double = scaleAlignMin
  var focus: Vec2 = grid.cen 
  override def mapObjs = tileCoodLMap{c =>
    val posn = (grid.coodToVec2(c) - focus) * pScale
    TextGraphic(c.xyStr, 12, posn)}
  
  //optional members
  mapPanel.backColour = Colour.Wheat
  eTop()
  repaintMap
}