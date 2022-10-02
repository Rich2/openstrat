/* Copyright 2018-22 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package gTwo
import prid._, psq._, gPlay._

/** Scenario trait for Game Two. */
trait TwoScen extends SqGridScen
{ /** An optional player can occupy each tile. This is the only tile data in the game. this is the same as Game one. */
  def oPlayers: SqCenOptLayer[Player]

  def endTurn(orderList: Arr[(Player, SqDirn)]): TwoScen =
  {
    val playersKey: Map[Player, SqCen] = oPlayers.keyMap

    /** A mutable grid of data. The tile data is an Array buffer of [[SqDirn]]s, the SqStep pointing back to the origin [[SqCen]] of the player. */
    val targets: SqCenBuffLayer[SqDirn] = gSys.newSqCenBuffLayer
    orderList.foreach { (player, step) =>
      val sc1 = playersKey(player)
      val optTarget: Option[SqCen] = sc1.stepOpt(step)
      optTarget.foreach { target => targets.appendAt(target, step.reverse) }
    }

    /** A new Players grid is created by cloning the old one and then mutating it to the new state. This preserves the old turn state objects and
     * isolates mutation to within the method. */
    val oPlayersNew: SqCenOptLayer[Player] = oPlayers.clone
    targets.foreach{ (sc2, buff) => buff.partition(_.isPerp) match
      { case _ if !(oPlayers.tileNone(sc2)) =>
        case (Arr1(sc), _) => oPlayersNew.unsafeMove(sc2.step(sc), sc2)
        case (Arr0(), Arr1(sc)) => oPlayersNew.unsafeMove(sc2.step(sc), sc2)
        case _ =>
      }
    }

    TwoScen(turn + 1, gSys, oPlayersNew)
  }
}

/** Companion object for TwoScen trait, contains factory apply method. */
object TwoScen
{ /** Apply factory method for TwoScen game. */
  def apply(turnIn: Int, gSysIn: SqGridSys, opIn: SqCenOptLayer[Player]): TwoScen = new TwoScen
  { override val turn = turnIn
    override implicit val gSys: SqGridSys = gSysIn
    override def oPlayers: SqCenOptLayer[Player] = opIn
  }
}

/** This trait just puts the value 0 in for the turn. */
trait TwoScenStart extends TwoScen
{ override val turn: Int = 0
}

/** A class identifying a Player and a hex coordinate position. */
case class SPlayer(value: Player, sc: SqCen) extends SqMem[Player]