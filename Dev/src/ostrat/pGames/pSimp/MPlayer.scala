/* Copyright 2018 Richard Oliver. Licensed under Apache Licence version 2.0 */
package ostrat
package pGames.pSimp
import pGrid._

/** A Player has a very simple token with a letter and colour for recognition." */
case class MPlayer(player: Player, cood: Cood, move: Option[Cood] = None) extends WithColour
{  
  override def toString = player.toString
  override def colour = player.colour
}

object MPlayer
{
  implicit val persistMPlayer: Persist[MPlayer] = new Persist3[Player, Cood, Option[Cood], MPlayer]("MPlayer", _.player, _.cood, _.move, apply)
}

case class Player(val char: Char, val colour: Colour)
{
  override def toString = "Player " + char
}

object Player
{
  implicit val persistPlayer: Persist[Player] = new Persist2[Char, Colour, Player]("Player", _.char, _.colour, apply)
}

