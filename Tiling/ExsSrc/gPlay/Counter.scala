/* Copyright 2018-23 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package gPlay
import Colour._

/** A counter has a very simple token with a letter and colour for recognition. */
case class Counter(char: Char, colour: Colour) extends Tell2[Char, Colour] with Coloured
{ override def typeStr: String = "Player"
  override def show1: Char = char
  override def show2: Colour = colour
  override implicit def persist1: Show[Char] = Show.charImplicit
  override implicit def persist2: Show[Colour] = Colour.persistImplicit
  override def name1: String = "char"
  override def name2: String = "colour"
  override def str: String = "Counter" + char
  def charStr: String = char.toString
  override def tell(style: ShowStyle): String = "Player" + char
  override def syntaxDepth: Int = 1
}

/** Companion object for [[Counter]] case class contains implicit instance for Persist. */
object Counter
{ /* Implicit [[ShowT]] instance / evidence for [[Player]]. */
  implicit val showTEv: Show2ing[Char, Colour, Counter] = Show2eding[Char, Colour, Counter]("Counter")
}

object CounterA extends Counter('A', Red)
object CounterB extends Counter('B', Orange)
object CounterC extends Counter('C', Pink)
object CounterD extends Counter('D', Violet)
object CounterE extends Counter('E', DarkBlue)