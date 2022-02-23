/* Copyright 2018-22 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package gOne
import prid._, phex._, Colour._

/** A Player has a very simple token with a letter and colour for recognition. */
case class Player(char: Char, colour: Colour) extends ShowNoDec
{ //override def toString = "Player " + char
  /** the name of the type of this object. */
  override def typeStr: String = "Player"

  /** The most basic Show method, paralleling the strT method on ShowT type class instances. */
  override def str: String = "Player" + char

  def charStr: String = char.toString

  /** Intended to be a multiple parameter comprehensive Show method. Intended to be paralleled by showT method on [[ShowT]] type class instances. */
  override def show(style: ShowStyle): String = "Player" + char

  override def syntaxDepth: Int = 1
}

/** Companion object for Player case class contains implicit instance for Persist. */
object Player
{ /* Implicit [[ShowT]] instance / evidence for [[Player]]. */
  implicit val showTEv: Show2T[Char, Colour, Player] = Show2T[Char, Colour, Player]("Player", "char", _.char, "colour", _.colour)
}
object PlayerA extends Player('A', Red)
object PlayerB extends Player('B', Orange)
object PlayerC extends Player('C', Pink)
object PlayerD extends Player('D', Violet)

/** A class identifying a Player and a hex coordinate position. */
case class HPlayer(value: Player, hc: HCen) extends HexMem[Player] with Show2[Player, HCen]
{ override def typeStr: String = "HPlayer"
  override def show1: Player = value
  override def show2: HCen = hc
  override implicit def showT1: ShowT[Player] = Player.showTEv
  override implicit def showT2: ShowT[HCen] = HCen.persistEv
  override def name1: String = "player"
  override def name2: String = "hCen"
  override def syntaxDepth: Int = 2
}