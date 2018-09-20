/* Copyright 2018 Richard Oliver. Licensed under Apache Licence version 2.0 */
package ostrat
package pGames
package pDung
import Colour._

sealed trait Faction { def colour: Colour }
object Fac1 extends Faction { val colour = Orange }
object Fac2 extends Faction { val colour = Green }

case class Character(iden: Char, faction: Faction)
{ def colour = faction.colour 
}

object CharacA extends Character('A', Fac1)
object CharacB extends Character('B', Fac1)

object CharacY extends Character('Y', Fac2)
object CharacZ extends Character('Z', Fac2)