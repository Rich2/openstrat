/* Copyright 2018-21 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat
package pCiv
import geom._, Colour._

trait Faction extends ShowSimple
{ override def typeStr: String = "Faction"
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