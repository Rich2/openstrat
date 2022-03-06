/* Copyright 2018-22 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package pEarth
import geom._, pgui._, pGrid._, proord._

/** Gui to display E80Data objects in isolation. */
case class E80GridGuiOld(canv: CanvasPlatform, scen: E80DataOld, cenRoord: Roord) extends CmdBarGui("North West Europe Gui")
{
  implicit val grid: HexGridIrrOld = scen.grid
  val scale: Double = 40
  val terrs = scen.terrs
  val tiles = grid.map{ r => r.tilePoly.fillTextActive(terrs(r).colour, r.toHexTile, r.ycStr, 16) }

  val sides: GraphicElems = scen.sTerrs.gridMap { (r, b) =>
    if (b) grid.sidePolygon(r).fill(Colour.Blue)
    else grid.sideRoordToLine2(r).draw()
  }

  statusText = "Tile Grid for North West Europe"
  def thisTop(): Unit = reTop(Arr())
  thisTop()
  def frame = (tiles ++ sides).gridRoordScale(cenRoord, scale)
  mainRepaint(frame)
}