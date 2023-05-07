/* Copyright 2018-23 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package gOne; package sp1
import prid._, psq._, gPlay._

/** Scenario trait for Game Two. */
trait G1SqScen extends SqGridScen
{  override def title: String = "Game 1 Squares scen."
  /** An optional player can occupy each tile. This is the only tile data in the game. this is the same as Game one. */
  def players: SqCenOptLayer[Player]

  def endTurn(orderList: SqCenStepPairArr[Player]): G1SqScen =
  {
    val playersKey: Map[Player, SqCen] = players.keyMap

    /** A mutable grid of data. The tile data is an Array buffer of [[SqStep]]s, the SqStep pointing back to the origin [[SqCen]] of the player. */
    val targets: SqCenBuffLayer[SqCenStep] = gSys.newSqCenBuffLayer

    orderList.foreach { pair =>
      val sc1 = playersKey(pair.a2)
      val optTarget: Option[SqCen] = sc1.stepOpt(pair.step)
      optTarget.foreach { target => targets.appendAt(target, pair.a1) }
    }

    /** A new Players grid is created by cloning the old one and then mutating it to the new state. This preserves the old turn state objects and
     * isolates mutation to within the method. */
    val playersNew: SqCenOptLayer[Player] = players.copy
    targets.foreach{ (sc2, buff) => buff.partition(_.step.isPerp) match
      { case _ if !(players.tileNone(sc2)) =>
        case (Arr1(scs), _) => playersNew.unsafeMove(scs.startSC, sc2)
        case (Arr0(), Arr1(scs)) => playersNew.unsafeMove(scs.startSC, sc2)
        case _ =>
      }
    }

    G1SqScen(turn + 1, gSys, playersNew)
  }

  /** Contains the resolution logic. The actions are presumed to be correct. Combining and checking of actions should be done before calling this
   * method. */
  def resolve(actions: SqCenOptStepLayer[Player]): SqCenOptLayer[Player] =
  { val playersNew: SqCenOptLayer[Player] = players.copy
    val acc: SqCenAccLayer[Player] = actions.mapAcc
    acc.foreach { (target, arr) => if (arr.length == 1 & players(target).isEmpty) playersNew.moveUnsafe(arr.headSqCen, target) }
    playersNew
  }
}

/** Companion object for TwoScen trait, contains factory apply method. */
object G1SqScen
{ /** Apply factory method for TwoScen game. */
  def apply(turnIn: Int, gSysIn: SqGridSys, opIn: SqCenOptLayer[Player]): G1SqScen = new G1SqScen
  { override val turn = turnIn
    override implicit val gSys: SqGridSys = gSysIn
    override def players: SqCenOptLayer[Player] = opIn
  }
}

/** This trait just puts the value 0 in for the turn. */
trait G1SqScenStart extends G1SqScen
{ override val turn: Int = 0
}

/** A class identifying a Player and a hex coordinate position. */
case class SPlayer(value: Player, sc: SqCen)