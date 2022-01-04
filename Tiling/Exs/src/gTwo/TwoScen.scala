/* Copyright 2018-22 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package gTwo
import prid._, gOne.Player

/** Scenario trait for Game Two. */
trait TwoScen extends SqGridScen
{ /** An optional player can occupy each tile. This is the only tile data in the game. this is the same as Game one. */
  def oPlayers: SqCenArrOpt[Player]

  def endTurn(orderList: Arr[(Player, SqStep)]): TwoScen =
  {
    val playersKey: Map[Player, SqCen] = oPlayers.keyMap

    /** A mutable grid of data. The tile data is an Array buffer of [[SqStep]]s, the SqStep pointing back to the origin [[SqCen]] of the player. */
    val targets: SqCenArrOfBuff[SqStep] = grid.newSqCenArrOfBuff

    orderList.foreach { (player, step) =>
      val sc1 = playersKey(player)
      val optTarget: Option[SqCen] = sc1.stepOpt(step)
      optTarget.foreach { target => targets.appendAt(target, step.reverse) }
    }

    /** A new Players grid is created by cloning the old one and then mutating it to the new state. This preserves the old turn state objects and
     * isolates mutation to within the method. */
    val oPlayersNew: SqCenArrOpt[Player] = oPlayers.clone
    targets.foreach{ (sc2, buff) => if (oPlayers.tileNone(sc2))
        buff.length match
        {
          case 0 =>
          case 1 => oPlayersNew.unsafeMove(sc2.step(buff(0)), sc2)
          case _ =>
        //if (oPlayers.tileNone(sc2)) oPlayersNew.unsafeMove(sc2.step(backStep), sc2)
        }
      }

    TwoScen(turn + 1, grid, oPlayersNew)
  }
}

/** Companion object for TwoScen trait, contains factory apply method. */
object TwoScen
{ /** Apply factory method for TwoScen game. */
  def apply(turnIn: Int, gridIn: SqGrid, opIn: SqCenArrOpt[Player]): TwoScen = new TwoScen
  { override val turn = turnIn
    override implicit val grid: SqGrid = gridIn
    override def oPlayers: SqCenArrOpt[Player] = opIn
  }
}

/** This trait just puts the value 0 in for the turn. */
trait TwoScenStart extends TwoScen
{ override val turn: Int = 0
}