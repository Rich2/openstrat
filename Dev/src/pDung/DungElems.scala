/* Copyright 2018-22 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package pDung
import geom._, Colour._, prid._, psq._

sealed class Faction(val str: String, val colour: Colour) extends ShowSimple
{ override def typeStr: String = "Faction"
}

object Fact1 extends Faction("Fac1", Orange)
object Fact2 extends Faction("Fac2", Green)

class Character(val iden: Char, val faction: Faction) extends ShowSimple // with CoodMover
{ override def typeStr = "Character"
  def colour = faction.colour
  val str: String = "Character" -- iden.toString
 // def canMove(tile: DTileOld): Boolean = tile.terr != Wall
  def turnMovePts = 10
  var movePts: Int = turnMovePts
  def resetMovePts(): Unit = movePts = turnMovePts
}

object CharacA extends Character('A', Fact1)
object CharacB extends Character('B', Fact1)

object CharacY extends Character('Y', Fact2)
object CharacZ extends Character('Z', Fact2)

case class CharacState(charac: Character, facing: SqDirn)