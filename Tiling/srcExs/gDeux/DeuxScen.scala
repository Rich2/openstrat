/* Copyright 2018-20 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat
package gDeux
import prid._

trait DeuxScen
{
  /*def grid: SquareGridSimple
  def terrs: TileBooleans
  def oPlayers: TilesArrOpt[Player]*/
}


/*object TwoScen1 extends TwoScen
{
  implicit val grid = new SquareGridSimple(2, 8, 2, 10)
  val terrs = grid.newTileBooleans
  val oPlayers = grid.newTileArrOpt[Player]
  oPlayers.mutSetSome(4, 4, PlayerA)
  oPlayers.unsafeSetSomes((4, 8, PlayerB), (6, 10, PlayerC), (6, 4, PlayerD))
}*/

object DeuxScen1 extends DeuxScen