/* Copyright 2018-21 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package pDung
import pgui._, pGrid._, geom._

/** This uses the new Gui. */
case class DungeonGui(canv: CanvasPlatform, scen: DungeonScen) extends CmdBarGui("Dungeon Gui")
{
  statusText = "Welcome to Dungeon Gui"
  implicit def grid: TileGridOld = scen.grid
  val scale = grid.fullDisplayScale(mainWidth, mainHeight)
  val terrs = scen.terrs
  val tiles = grid.map{ r => r.tilePoly.fillTextActive(terrs(r).colour, r.toHexTile, r.ycStr, 16) }
  val sls = grid.sidesDraw(2.0)
  val players = scen.characs.mapSomeWithRoords { (r, cp) =>
    val poly1: Polygon = Rect(1.5, 1).insVerts(1, -0.25 pp 0.5, 0 pp 0.8, 0.25 pp 0.5).rotate(cp.facing.angle)
    val poly2: Polygon = poly1.scale( 0.75).slate(r.gridPt2)
    poly2.fillDrawTextActive(cp.charac.colour, cp, cp.charac.iden.toString, 16, 2.0, cp.charac.colour.contrast)
  }

  def thisTop(): Unit = reTop(Arr())
  thisTop()
  def frame: GraphicElems = (tiles +% sls ++ players).gridScale(scale)
  def repaint() = mainRepaint(frame)
  repaint()
}