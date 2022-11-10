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

  def playersData: Map[Player, HDirnArr] = Map()

  def playerOrders: HDirnPathPairArr[Player] = HDirnPathPairArr()
  lazy val playersKey: HCenPairArr[Player] = oPlayers.somePairArr

  /** Resolves turn. Takes a list [[RArr]] of commands consisting in this simple case of (Player, HStep) pairs. The command is passed in as a relative
   * move. This is in accordance with the principle in more complex games that the entity issueing the command may not know its real location. */
  def endTurn(orderList: Map[Player, HDirnArr]): ThreeScen =
  {
    val targets: HCenBuffLayer[HCenStep] = gridSys.newHCenArrOfBuff

    orderList.foreach { ps =>  ps._2.ifHead { step =>
      //val hc1: HCen = playersKey.a1ByA2(ps._1)
      val hc1: HCen = oPlayers.get(ps._1)
      val optTarget: Option[HCen] = hc1.stepOpt(step)
       optTarget.foreach{ target => targets.appendAt(target, HCenStep(hc1, step)) }
    }
  }

    var newData: Map[Player, HDirnArr] = orderList

    /** A new Players grid is created by cloning the old one and then mutating it to the new state. This preserves the old turn state objects and
     * isolates mutation to within the method. */
    val oPlayersNew: HCenOptLayer[Player] = oPlayers.clone
    targets.foreach{ (hc2, buff) => buff.foreachLen1 { stCenStep => if (oPlayers.tileNone(hc2))
        { oPlayersNew.unsafeMoveMod(stCenStep.startHC, hc2) { ps => ps }
          newData = newData.modValue(oPlayers(stCenStep.startHC).get)( _.tail)
        }
      }
    }

    ThreeScen(turn + 1, gridSys, oPlayersNew, newData)
  }
}

/** Companion object for OneScen trait, contains factory apply method. */
object ThreeScen
{ /** Factory apply method for [[ThreeScen]] trait. */
  def apply(turnIn: Int, gridIn: HGridSys, opIn: HCenOptLayer[Player], newData: Map[Player, HDirnArr]): ThreeScen = new ThreeScen
  { override val turn = turnIn
    override implicit val gridSys: HGridSys = gridIn
    override def oPlayers: HCenOptLayer[Player] = opIn
    override def playersData: Map[Player, HDirnArr] = newData
  }
}