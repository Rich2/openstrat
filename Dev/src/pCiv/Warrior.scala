/* Copyright 2018 Richard Oliver. Licensed under Apache Licence version 2.0 */
package ostrat
package pCiv
import pGrid._, pStrat._, pEarth._

class Warrior(val faction: Faction, var xCood: Int, var yCood: Int) extends Lunit //with Stringer
{ def typeStr: String = "Warrior"
  //override def str: String = persist2(faction, cood)
   override def equals(other: Any): Boolean = other match
   { case that: Warrior => faction == that.faction
     case _ => false
   }
   def turnMovePts = 10
   var movePts: Int = turnMovePts
   def resetMovePts(): Unit = movePts = turnMovePts
   def terrCost(tile: CTileOld): Int = tile.terr match
   { case l: Land if l.terr == Mountains => 10
     case l: Land if l.terr == Hilly => 6
     case _ => 4
   }
}

object Warrior
{ def apply(polity: Faction, cood: Cood): Warrior = new Warrior(polity, cood.c, cood.y)
  def apply(polity: Faction, x: Int, y: Int): Warrior = new Warrior(polity, x, y)
}