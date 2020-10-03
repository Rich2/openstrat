/* Copyright 2018-20 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat
package pEarth
import geom._, pCanv._, pGrid._

/** Gui to display E80Data objects in isolation. */
case class E80GridGui(canv: CanvasPlatform, scen: E80Data, cenRoord: Roord) extends CmdBarGui("North West Europe Gui")
{
  implicit val grid = scen.grid
  val scale = 40
  val terrs = scen.terrs
  val tiles = grid.map{ r => r.tilePoly.fillTextActive(terrs(r).colour, r.toHexTile, r.ycStr, 16) }
  val sides: GraphicElems = scen.sTerrs.gridMap { (r, b) =>
    if (b) grid.sidePolygon(r).fill(Colour.Blue)
    else grid.sideRoordToLine2(r).draw(2.0)
  }
  var statusText = "Tile Grid for North West Europe"
  def thisTop(): Unit = reTop(Arr(status))
  thisTop()
  def frame = (tiles ++ sides).gridRoordScale(cenRoord, scale)
  mainRepaint(frame)
}