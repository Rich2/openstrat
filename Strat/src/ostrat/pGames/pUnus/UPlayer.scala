/* Copyright 2018 Richard Oliver. Licensed under Apache Licence version 2.0 */
package ostrat
package pGames.pUnus
import pGrid._

/** A Player has a very simple token with a letter and colour for recognition." */
case class MPlayer(player: Player, cood: Cood, move: Option[Cood] = None) extends WithColour
{  
  override def toString = player.toString
  override def colour = player.colour
}

case class Player(val char: Char, val colour: Colour)
{
  override def toString = "Player " + char
}

object Player
{
  implicit object Player extends Persist2[Char, Colour, Player]('Player, p => (p.char, p.colour), apply)
}

