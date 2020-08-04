/* Copyright 2018-20 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat
package pCiv
import Colour._

trait Faction extends PersistSingleton
{ //def typeSym = 'Faction
  def colour: Colour
}

object Faction
{
  implicit object FactionPersistImplicit extends PersistSingletons[Faction]("Faction")
  {
    override def singletonList = List(Uruk, Eridu)
  }
}

object Uruk extends Faction
{ def str: String = "Uruk"
  val colour = Red
}

object Eridu extends Faction
{ def str: String = "Eridu"
  val colour = Blue
}