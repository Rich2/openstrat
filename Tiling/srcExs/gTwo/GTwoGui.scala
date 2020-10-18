/* Copyright 2018-20 Richard Oliver. Licensed under Apache Licence version 2.0 */
package ostrat
package gTwo
import geom._, pCanv._, pGrid._

case class GTwoGui(canv: CanvasPlatform, scen: TwoScen) extends CmdBarGui("Game Two Square Grid")
{
  implicit val grid = scen.grid
  val scale = grid.fullDisplayScale(mainWidth, mainHeight)
  def players: TilesArrOpt[Player] = scen.oPlayers
  def lunits = players.mapSomes{(r, p) => Rect(0.9, 0.6, r.gridVec2).fillDrawTextActive(p.colour, RPlayer(p, r),
    p.toString + "\n" + r.ycStr, 24, 2.0) }

  var statusText = "Let click on Player to select. Right click on adjacent Hex to set move."
  val sls: LinesDraw = grid.sidesDraw(2.0)
  val csvr = grid.cenRoordTexts() ++ grid.sideRoordTexts() ++ grid.vertRoordTexts()
  /** This makes the tiles active. They respond to mouse clicks. It does not paint or draw the tiles. */
  val tiles = grid.activeTiles

  val frame = (tiles +- sls ++ csvr ++ lunits).gridScale(scale)


  /** The frame to refresh the top command bar. Note it is a ref so will change with scenario state. */
  def thisTop(): Unit = reTop(Arr(status))
  thisTop()
  mainRepaint(frame)
}
