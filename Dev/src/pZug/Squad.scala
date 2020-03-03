/* Copyright 2018 Richard Oliver. Licensed under Apache Licence version 2.0 */
package ostrat
package pZug
import pGrid._, pStrat._

class Squad(val polity: Polity, var xCood: Int, var yCood: Int, val id: Int) extends Lunit
{
  var action: Action = NoAction
  def move(newMove: Cood *): Unit = action = Move(Coods(newMove:_*))
  def fire(x: Int, y: Int): Unit = action = Fire(x cc y)
  val colour = polity.colour
  override def toString = "Squad" + (polity.toString).enParenth
  
  override def equals(other: Any): Boolean = other match
  { case that: Squad => polity == that.polity
    case _ => false
  }
  
  def canMove(tile: ZugTileOld): Boolean = tile.terr != Lake
   
  def terrCost(tile: ZugTileOld): Int = tile.terr match
  { case Plain => 4
    case WheatField => 6
    case StoneBuilding => 10 
    case Hill => 6
    case _ => 4
  }
}

object Squad
{ def apply(polity: Polity, cood: Cood, id: Int): Squad = new Squad(polity, cood.x, cood.y, id)
  def apply(polity: Polity, x: Int, y: Int, id: Int): Squad = new Squad(polity, x, y, id)
}

sealed trait Action
case class Move(coods: Coods) extends Action
case class Fire(cood: Cood) extends Action
object NoAction extends Action

case class SquadTurn(id: Int, action: Action)