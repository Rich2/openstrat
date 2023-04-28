/* Copyright 2018-23 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package gTwo; package h2p
import prid._, phex._, gPlay._

/** The Player and its intentions. I'm thinking that a game entity and its state should generally be stored in the same layer. */
case class PlayerState(player: Player, steps: HStepArr)

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
  def endTurn(orderList: HStepPathPairArr[Player]): G2HScen =
  {
    val targets: HCenBuffLayer[HDirnPathPair[Player]] = gridSys.newHCenArrOfBuff

    orderList.foreach { pr =>
      val path = pr.path
      if (path.length > 0) {
      val hc1: HCen = path.startCen
      val step = path.getHead
      val optTarget: Option[HCen] = hc1.stepOpt(step)
       optTarget.foreach{ target => targets.appendAt(target, pr) }// HCenStep(hc1, step)) }
      }
    }

    var newOrders: HStepPathPairArr[Player] = orderList

    /** A new Players grid is created by cloning the old one and then mutating it to the new state. This preserves the old turn state objects and
     * isolates mutation to within this method. */
    val playersNew: HCenOptLayer[PlayerState] = playerStates.clone
    targets.foreach{ (hc2, buff) => buff.foreachLen1 { pathPlayer => if (playerStates.tileNone(hc2))
        { playersNew.moveMut(pathPlayer.path.startCen, hc2)
          newOrders = newOrders.replaceA1byA2(pathPlayer.a2, pathPlayer.tail(hc2))
        }
      }
    }

    G2HScen(turn + 1, gridSys, playersNew)
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