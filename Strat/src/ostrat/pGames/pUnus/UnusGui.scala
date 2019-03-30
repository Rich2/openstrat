/* Copyright 2018 Richard Oliver. Licensed under Apache Licence version 2.0 */
package ostrat
package pGames.pUnus
import geom._, pCanv._, pGrid._

/** Main application for Unus Game. */
class UnusGui(canv: CanvasPlatform, grid: UnusGrid)
{
  new UnusSetGui(canv, grid)
}

class UnusSetGui(val canv: CanvasPlatform, val grid: UnusGrid) extends TileGridGui[UTile, SideBare, UnusGrid]("Unus Game")
{
  //Required members
  var pScale: Double = scaleAlignMin
  deb(pScale.toString)
  var focus: Vec2 = grid.cen  
  override def mapObjs = tilesCoodFlatMapListAll{c => List(coodXYDisp(c)) } :::
  grid.tilesOptionDispAll(t => t.oPlayer.map(p => Rectangle(120, 80, coodToDisp(t.cood)).fillDraw(p.colour, 2)))  :::  sidesDrawAll()
  
  //optional members
  mapPanel.backColour = Colour.Wheat
  eTop()
  repaintMap
}