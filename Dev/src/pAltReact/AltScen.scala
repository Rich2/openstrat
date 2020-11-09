/* Copyright 2018-20 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat
package pAltReact
import prid._

case class Player(str: String, colour: Colour)

sealed trait Balls
{ def num: Int
}

object NoBalls extends Balls
{ val num: Int = 0
}

case class SomeBalls(player: Player, num: Int)

trait AltScen
{ val turn: Int
  def grid: SqGrid
  //def terrs: TileBooleans
  //def oPlayers: TilesArrOpt[Player]*/
}