/* Copyright 2018-22 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package gThree
import prid._, phex._, gPlay._

case class PlayerState(player: Player, steps: HStepArr)

object PlayerState
{
  def apply(player: Player, steps: HStep*): PlayerState = PlayerState(player, HStepArr(steps: _*))
}

/** A scenario turn or state for Game Three. Adds in multiple turn orders which are now part of the game state. */
trait ThreeScen extends HexGriderFlatScen
{ /** An optional player can occupy each tile. This is the only tile data in the game. */
  //def oPlayersSt: HCenOptDGrid[PlayerState]
  def oPlayers: HCenOptDGrid[Player]
  def playersData: Map[Player, HStepArr] = Map()
  lazy val playersKey: Map[Player, HCen] = oPlayers.keyMap

  /** Resolves turn. Takes a list [[Arr]] of commands consisting in this simple case of (Player, HStep) pairs. The command is passed in as a relative
   * move. This is in accordance with the principle in more complex games that the entity issueing the command may not know its real location. */
  def endTurn(orderList: Map[Player, HStepArr]): ThreeScen =
  {
    //val playersKey: Map[Player, HCen] = oPlayersSt.keyMap.map(p => (p._1.player, p._2))

    val targets: HCenBuffDGrid[HCenStep] = grider.newHCenArrOfBuff

   /* orderList.foreach { ps =>  ps.steps.ifHead { step =>
        val hc1: HCen = playersKey(ps.player)
        val optTarget: Option[HCen] = hc1.stepOpt(step)
        optTarget.foreach{ target => targets.appendAt(target, HCenStep(hc1, step)) }
      }
    }*/

    /** A new Players grid is created by cloning the old one and then mutating it to the new state. This preserves the old turn state objects and
     * isolates mutation to within the method. */
    /*val oPlayersNew: HCenOptDGrid[PlayerState] = oPlayersSt.clone
    targets.foreach{ (hc2, buff) => buff.foreachLen1 { stCenStep => if (oPlayersSt.tileNone(hc2))
        oPlayersNew.unsafeMoveMod(stCenStep.startHC, hc2) { ps => PlayerState(ps.player, ps.steps.tail) }
      }
    }*/

    //ThreeScen(turn + 1, grider, oPlayersNew)
    ???
  }
}

/** Companion object for OneScen trait, contains factory apply method. */
object ThreeScen
{ /** Factory apply method for OneScen trait. */
  def apply(turnIn: Int, gridIn: HGriderFlat, opIn: HCenOptDGrid[Player]): ThreeScen = new ThreeScen
  { override val turn = turnIn
    override implicit val grider: HGriderFlat = gridIn
    override def oPlayers: HCenOptDGrid[Player] = opIn
  }
}