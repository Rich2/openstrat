/* Copyright 2018 Richard Oliver. Licensed under Apache Licence version 2.0 */
package ostrat
package pGames
package pCiv
import Colour._

trait Faction extends SingletonLeaf
{ def typeSym = 'Faction
  def colour: Colour
}

object Faction
{
  implicit object FactionPersistImplicit extends PersistSingletons[Faction]('Faction)
  {
    override def singletonList = List(Uruk, Eridu)
  }
}

object Uruk extends Faction
{ def objSym = 'Uruk
  val colour = Red
}

object Eridu extends Faction
{ def objSym = 'Eridu
  val colour = Blue
}