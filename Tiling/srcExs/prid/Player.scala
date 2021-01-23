/* Copyright 2018 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat
package prid
import geom._, Colour._

/** A Player has a very simple token with a letter and colour for recognition. */
case class Player(val char: Char, val colour: Colour)
{ override def toString = "Player " + char
}

object Player
{
  implicit val persistPlayer: PersistEq[Player] = Persist2[Char, Colour, Player]("Player", "char", _.char, "colour", _.colour, apply)
}
object PlayerA extends Player('A', Red)
object PlayerB extends Player('B', Orange)
object PlayerC extends Player('C', Green)
object PlayerD extends Player('D', Violet)

/** A class identifying a Player and a hex coordinate position. */
case class HPlayer(value: Player, hc: Hcen) extends HexMem[Player]