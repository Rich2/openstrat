/* Copyright 2018-20 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat
package pAltReact
import geom._, prid._, psq._, Colour._

case class Player(str: String, colour: Colour)
object PlayerA extends Player("A", Red)
object PlayerB extends Player("B", Violet)

case class Balls(player: Player, num: Int)

case class AltScen(turn: Int, grid: SqGrid, balls: SqCenArrOpt[Balls])
{
  //def terrs: TileBooleans
  //def oPlayers: TilesArrOpt[Player]*/
}

object AltScen
{
  def start(r: Int, c: Int): AltScen =
  {
    implicit val grid = SqGrid(2, r * 2, 2, c * 2)
    val balls = grid.newTileArrOpt[Balls]
    balls.unsafeSetSome(6, 6, Balls(PlayerA, 2))
    balls.unsafeSetSome(4, 4, Balls(PlayerB, 1))
    AltScen(0, grid, balls)
  }
}