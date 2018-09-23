/* Copyright 2018 Richard Oliver. Licensed under Apache Licence version 2.0 */
package ostrat
package pGames
package pDung
import pGrid._, pSq._, Colour._

sealed trait Faction extends PersistSingle { def colour: Colour }
object Fac1 extends Faction
{ val colour = Orange 
  val str = "Fac1"
}
object Fac2 extends Faction
{ val colour = Green 
  val str = "Fac2"
}

class Character(val iden: Char, val faction: Faction, var xCood: Int = 0, var yCood: Int = 0) extends CoodMover with PersistSingle 
{ def colour = faction.colour
  var facing: Facing = FaceUp
  val str: String = "Character" -- iden.toString
}

object CharacA extends Character('A', Fac1)
object CharacB extends Character('B', Fac1)

object CharacY extends Character('Y', Fac2)
object CharacZ extends Character('Z', Fac2)