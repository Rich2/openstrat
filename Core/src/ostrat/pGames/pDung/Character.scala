/* Copyright 2018 Richard Oliver. Licensed under Apache Licence version 2.0 */
package ostrat
package pGames
package pDung
import pGrid._, SqCode._, Colour._

sealed class Faction(val sym: Symbol, val colour: Colour) extends StringerSingleton
{ def typeSym = 'Faction  
}

object Fac1 extends Faction('Fac1, Orange)
object Fac2 extends Faction('Fac2, Green)

class Character(val iden: Char, val faction: Faction, var xCood: Int = 0, var yCood: Int = 0) extends CoodMover with StringerSingleton
{ def typeSym = 'Character
  def colour = faction.colour
  var facing: Facing = FaceUp
  val sym: Symbol = Symbol("Character" -- iden.toString)
  def canMove(tile: DTile): Boolean = tile.terr != Wall
  def turnMovePts = 10
  var movePts: Int = turnMovePts
  def resetMovePts(): Unit = movePts = turnMovePts
}

object CharacA extends Character('A', Fac1)
object CharacB extends Character('B', Fac1)

object CharacY extends Character('Y', Fac2)
object CharacZ extends Character('Z', Fac2)