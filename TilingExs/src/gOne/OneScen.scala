/* Copyright 2018-22 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package gOne
import prid._, phex._, gPlay._

/** A scenario turn or state for Game One. Consists of just a turn number and a tile Grid. Each tile can contain a single player or can be empty. */
trait OneScen extends HexGriderFlatScen
{ /** An optional player can occupy each tile. This is the only tile data in the game. */
  def oPlayers: HCenArrOpt[Player]

  /** Resolves turn. Takes a list [[Arr]] of commands consisting in this simple case of (Player, HStep) pairs. The command is passed in as a relative
   * move. This is in accordance with the principle in more complex games that the entity issueing the command may not know its real location. */
  def endTurn(orderList: Arr[(Player, HStep)]): OneScen =
  {
    val playersKey: Map[Player, HCen] = oPlayers.keyMap

    val targets2: HCenArrOfBuff[HexAndStep] = grider.newHCenArrOfBuff

    /** A mutable grid of data. The tile data is an Array buffer of [[HStep]]s, the HStep pointing back to the origin [[HCen]] of the player. */
    val targets: HCenArrOfBuff[HStep] = grider.newHCenArrOfBuff

    orderList.foreach { (player: Player, step: HStep) =>
      val hc1: HCen = playersKey(player)
      val optTarget: Option[HCen] = hc1.stepOpt(step)
      optTarget.foreach { target => targets.appendAt(target, step.reverse) }
    }

    /** A new Players grid is created by cloning the old one and then mutating it to the new state. This preserves the old turn state objects and
     * isolates mutation to within the method. */
    val oPlayersNew: HCenArrOpt[Player] = oPlayers.clone
    targets.foreach{ (hc2, buff) => buff.foreachLen1(backStep => if (oPlayers.tileNone(hc2)) oPlayersNew.unsafeMove(hc2.unsafeStep(backStep), hc2)) }

    OneScen(turn + 1, grider, oPlayersNew)
  }
}

/** Companion object for OneScen trait, contains factory apply method. */
object OneScen
{ /** Factory apply method for OneScen trait. */
  def apply(turnIn: Int, gridIn: HGriderFlat, opIn: HCenArrOpt[Player]): OneScen = new OneScen
  { override val turn = turnIn
    override implicit val grider: HGriderFlat = gridIn
    override def oPlayers: HCenArrOpt[Player] = opIn
  }
}