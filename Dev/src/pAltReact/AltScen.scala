/* Copyright 2018-20 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat
package pAltReact
import prid._, Colour._

case class Player(str: String, colour: Colour)
object PlayerA extends Player("A", Red)

sealed trait Balls
{ def num: Int
}

object NoBalls extends Balls
{ val num: Int = 0
}

case class SomeBalls(player: Player, num: Int) extends Balls

case class AltScen(turn: Int, grid: SqGrid, arr: SqcenArr[Balls])
{
  //def terrs: TileBooleans
  //def oPlayers: TilesArrOpt[Player]*/
}

object AltScen
{
  def start(r: Int, c: Int): AltScen =
  {
    val grid = SqGrid(2, r * 2, 2, c * 2)
    val balls = grid.newTileArr[Balls](NoBalls)
    //balls
    AltScen(0, grid, balls)
  }
}