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
  var focus: Vec2 = grid.cen  
  override def mapObjs = tilesCoodFlatMapListAll{c => List(coodXYDisp(c)) } :::
  grid.tilesOptionDispAll(t => t.mem.map(p => Rectangle(150, 100).fill(p.colour)))  :::  sidesDrawAll()
  
  //optional members
  mapPanel.backColour = Colour.Wheat
  eTop()
  repaintMap
}