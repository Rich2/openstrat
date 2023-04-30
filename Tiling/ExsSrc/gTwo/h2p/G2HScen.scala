/* Copyright 2018-23 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package gTwo; package h2p
import prid._, phex._, gPlay._

/** The Player and its intentions. I'm thinking that a game entity and its state should generally be stored in the same layer. */
case class PlayerState(player: Player, steps: HStepArr)
{
  def takeStep: PlayerState = PlayerState(player, steps.tail)
}

object PlayerState
{
  def apply(player: Player, steps: HStep*): PlayerState = PlayerState(player, HStepArr(steps: _*))
}

/** A scenario turn or state for Game Three. Adds in multiple turn orders which are now part of the game state. */
trait G2HScen extends HSysTurnScen
{ override def title: String = "Game 2 hex scenario"

  /** An optional player can occupy each tile. This is the only tile data in the game. */
  def playerStates: HCenOptLayer[PlayerState]

  //def playerOrders: HDirnPathPairArr[Player] = HDirnPathPairArr()

  /** Resolves turn. Takes a list [[RArr]] of commands consisting in this simple case of (Player, HStep) pairs. The command is passed in as a relative
   * move. This is in accordance with the principle in more complex games that the entity issueing the command may not know its real location. */
  def turnUnchecked(oldStates: HCenOptLayer[PlayerState]): G2HScen = G2HScen(turn + 1, gridSys, resolve(oldStates))

  /** Resolution currently incorect. */
  def resolve(oldStates: HCenOptLayer[PlayerState]): HCenOptLayer[PlayerState] =
  { val acc: HCenAccLayer[PlayerState] = HCenAccLayer()
    oldStates.somesHcForeach{ (ps, origin) =>
      val steps: HStepArr = ps.steps
      val target: HCen = if(steps.length == 0) origin else gridSys.stepEndOrStart(origin, steps.head)
      acc.append(target, origin, ps)
    }
    val newStates = oldStates.clone
    acc.foreach{ (target, pairArr) => pairArr match
      { case HCenPairArr1(origin, ps: PlayerState) if origin != target =>
        { newStates.setSomeMut(target, ps.takeStep)
          newStates.setNoneMut(origin)
        }
        case _ =>
      }
    }
    newStates
  }
}

/** Companion object for OneScen trait, contains factory apply method. */
object G2HScen
{ /** Factory apply method for [[G2HScen]] trait. */
  def apply(turnIn: Int, gridIn: HGridSys, opIn: HCenOptLayer[PlayerState]): G2HScen = new G2HScen
  { override val turn = turnIn
    override implicit val gridSys: HGridSys = gridIn
    override def playerStates: HCenOptLayer[PlayerState] = opIn
    //override def playerOrders: HDirnPathPairArr[Player] = newData
  }
}