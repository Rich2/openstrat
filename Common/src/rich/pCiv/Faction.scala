/* Copyright 2018 Richard Oliver. Licensed under Apache Licence version 2.0 */package rich
package pCiv
import Colour._

trait Faction
{
   def colour: Colour
}

object Uruk extends Faction
{
   val colour = Red
}

object Eridu extends Faction
{
   val colour = Blue
}