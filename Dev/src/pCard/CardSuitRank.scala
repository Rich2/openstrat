/* Copyright 2018-20 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat
package pCard

case class Rank(val value: Int) extends AnyVal

sealed trait Suit
object Spade extends Suit
object Heart extends Suit
object Diamond extends Suit
object Club extends Suit
