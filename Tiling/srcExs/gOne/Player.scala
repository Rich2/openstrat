/* Copyright 2018-21 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package gOne
import prid._, Colour._

/** A Player has a very simple token with a letter and colour for recognition. */
case class Player(val char: Char, val colour: Colour) extends Show//2[Char, Colour]
{ //override def toString = "Player " + char
  /** the name of the type of this object. */
  override def typeStr: String = "Player"

  /** The most basic Show method, paralleling the strT method on ShowT type class instances. */
  override def str: String = "Player" + char

  /** Intended to be a multiple parameter comprehensive Show method. Intended to be paralleled by showT method on [[ShowT]] type class instances. */
  override def show(way: Show.Way, maxPlaces: Int, minPlaces: Int): String = "Player" + char

  override def syntaxdepth: Int = 1
}

/** Companion object for Player case class contains implicit instance for Persist. */
object Player
{
  implicit val showPlayer: ShowT[Player] = Show2T[Char, Colour, Player]("Player", "char", _.char, "colour", _.colour)
}
object PlayerA extends Player('A', Red)
object PlayerB extends Player('B', Orange)
object PlayerC extends Player('C', Pink)
object PlayerD extends Player('D', Violet)

/** A class identifying a Player and a hex coordinate position. */
case class HPlayer(value: Player, hc: HCen) extends HexMem[Player]

/** A class identifying a Player and a hex coordinate position. */
case class SPlayer(value: Player, sc: SqCen) extends SqMem[Player]