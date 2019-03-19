/* Copyright 2018 Richard Oliver. Licensed under Apache Licence version 2.0 */
package ostrat
package pGames.pUnus
import geom._, pCanv._, pGrid._

class UnusGui(canv: CanvasPlatform, grid: UnusGrid)
{
  new UnusSetGui(canv, grid)
}

class UnusSetGui(val canv: CanvasPlatform, val grid: UnusGrid) extends TileGridGui[UTile, SideBare, UnusGrid]("Unus Game")
{
  //Required members
  var pScale: Double = scaleAlignMin
  var focus: Vec2 = grid.cen
  def lines = grid.allSideLines.lMap(l => l.fTrans(v =>( v - focus /1) * pScale).draw(2, Colour.Red))
  override def mapObjs = allTilesCoodMapList{c => TextGraphic(c.xyStr, 12, coodToDisp(c)) }// ++ lines
  
  //optional members
  mapPanel.backColour = Colour.Wheat
  eTop()
  repaintMap
}