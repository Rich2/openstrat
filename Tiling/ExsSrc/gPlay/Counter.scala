/* Copyright 2018-23 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package gPlay
import Colour._

/** A counter has a very simple token with a letter and colour for recognition. */
case class Counter(char: Char, colour: Colour) extends Tell2[Char, Colour] with Coloured
{ override def typeStr: String = "Counter"
  override def tell1: Char = char
  override def tell2: Colour = colour
  override implicit def show1: Show[Char] = Show.charEv
  override implicit def show2: Show[Colour] = Colour.showEv
  override def name1: String = "char"
  override def name2: String = "colour"
  override def str: String = "Counter" + char
  def charStr: String = char.toString
  override def tell(style: ShowStyle, decimalPlaces: Int = -1, minPlaces: Int = -1): String = "Counter" + char
  override def tellDepth: Int = 1
}

/** Companion object for [[Counter]] case class contains implicit instance for Persist. */
object Counter
{
  lazy val shorts: ArrPairStr[Counter] = ArrPairStr[Counter](("CounterA", CounterA), ("CounterB", CounterB), ("CounterC", CounterC),
    ("CounterD", CounterD), ("CounterE", CounterE))

  /* Implicit [[Show]] type class instance / evidence for [[Counter]]. */
  implicit val showEv: Persist2Both[Char, Colour, Counter] = Persist2Both.shorts[Char, Colour, Counter]("Counter", "Char", _.char, "colour", _.colour, shorts, apply)


  //implicit lazy val unshowEv: Unshow2[Char, Colour, Counter] = Unshow2.shorts[Char, Colour, Counter]("Counter", "char", "colour", apply, shorts)
}

object CounterA extends Counter('A', Red)
object CounterB extends Counter('B', Orange)
object CounterC extends Counter('C', Pink)
object CounterD extends Counter('D', Violet)
object CounterE extends Counter('E', DarkBlue)