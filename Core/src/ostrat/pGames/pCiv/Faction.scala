/* Copyright 2018 Richard Oliver. Licensed under Apache Licence version 2.0 */
package ostrat
package pGames
package pCiv
import Colour._

trait Faction extends PersisterSingleton
{ def typeSym = 'Faction
  def colour: Colour
}

object Uruk extends Faction
{ def str = "Uruk"
  val colour = Red
}

object Eridu extends Faction
{ def str = "Eridu"
  val colour = Blue
}