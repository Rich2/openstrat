/* Copyright 2018 Richard Oliver. Licensed under Apache Licence version 2.0 */
package ostrat
package pDung
import pGrid._

class CharacterOld(val iden: Char, val faction: Faction, var xCood: Int = 0, var yCood: Int = 0) extends CoodMover with PersistSingleton
{ //def typeSym = 'Character
  def colour = faction.colour
  var facing: SqFace = SFaceUp
  val str: String = "Character" -- iden.toString
  def canMove(tile: DTileOld): Boolean = tile.terr != Wall
  def turnMovePts = 10
  var movePts: Int = turnMovePts
  def resetMovePts(): Unit = movePts = turnMovePts
}

object CharacOldA extends CharacterOld('A', Fac1)
object CharacOldB extends CharacterOld('B', Fac1)

object CharacOldY extends CharacterOld('Y', Fac2)
object CharacOldZ extends CharacterOld('Z', Fac2)

sealed trait Action
sealed trait Turn extends Action
object RtTurn extends Turn
object LtTurn extends Turn
object MoveFwd extends Action

case class ActionSeq(charac: CharacterOld, actions: List[Action])