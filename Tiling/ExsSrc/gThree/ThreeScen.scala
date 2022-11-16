/* Copyright 2018-22 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package gThree
import prid._, phex._, gPlay._

case class PlayerState(player: Player, steps: HDirnArr)

object PlayerState
{
  def apply(player: Player, steps: HDirn*): PlayerState = PlayerState(player, HDirnArr(steps: _*))
}

/** A scenario turn or state for Game Three. Adds in multiple turn orders which are now part of the game state. */
trait ThreeScen extends HSysTurnScen
{ /** An optional player can occupy each tile. This is the only tile data in the game. */
  def oPlayers: HCenOptLayer[Player]

  def playerOrders: HDirnPathPairArr[Player] = HDirnPathPairArr()
  lazy val playersKey: HCenPairArr[Player] = oPlayers.somePairArr

  /** Resolves turn. Takes a list [[RArr]] of commands consisting in this simple case of (Player, HStep) pairs. The command is passed in as a relative
   * move. This is in accordance with the principle in more complex games that the entity issueing the command may not know its real location. */
  def endTurn(orderList: HDirnPathPairArr[Player]): ThreeScen =
  {
    val targets: HCenBuffLayer[HDirnPathPair[Player]] = gridSys.newHCenArrOfBuff

    orderList.foreach { pr =>
      val path = pr.path
      if (path.length > 0) {
      val hc1: HCen = path.startCen
      val step = path.head
      val optTarget: Option[HCen] = hc1.stepOpt(step)
       optTarget.foreach{ target => targets.appendAt(target, pr) }// HCenStep(hc1, step)) }
      }
    }

    var newOrders: HDirnPathPairArr[Player] = orderList

    /** A new Players grid is created by cloning the old one and then mutating it to the new state. This preserves the old turn state objects and
     * isolates mutation to within this method. */
    val oPlayersNew: HCenOptLayer[Player] = oPlayers.clone
    targets.foreach{ (hc2, buff) => buff.foreachLen1 { pathPlayer => if (oPlayers.tileNone(hc2))
        { oPlayersNew.unsafeMove(pathPlayer.path.startCen, hc2)
          newOrders = newOrders.replaceA1Value(pathPlayer.a2, pathPlayer.tail(hc2))
        }
      }
    }

    ThreeScen(turn + 1, gridSys, oPlayersNew, newOrders)
  }
}

/** Companion object for OneScen trait, contains factory apply method. */
object ThreeScen
{ /** Factory apply method for [[ThreeScen]] trait. */
  def apply(turnIn: Int, gridIn: HGridSys, opIn: HCenOptLayer[Player], newData: HDirnPathPairArr[Player]): ThreeScen = new ThreeScen
  { override val turn = turnIn
    override implicit val gridSys: HGridSys = gridIn
    override def oPlayers: HCenOptLayer[Player] = opIn
    override def playerOrders: HDirnPathPairArr[Player] = newData
  }
}