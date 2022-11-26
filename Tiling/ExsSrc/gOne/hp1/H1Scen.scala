/* Copyright 2018-22 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package gOne; package hp1
import prid._, phex._, gPlay._

/** A scenario turn or state for Game One. Consists of just a turn number and a tile Grid. Each tile can contain a single player or can be empty. */
trait H1Scen extends HSysTurnScen
{ /** An optional player can occupy each tile. This is the only tile data in the game. */
  def oPlayers: HCenOptLayer[Player]

  /** Resolves turn. Takes a list [[RArr]] of commands consisting in this simple case of (Player, HStep) pairs. The command is passed in as a relative
   * move. This is in accordance with the principle in more complex games that the entity issueing the command may not know its real location. */
  def endTurn(orderList: HCenStepPairArr[Player]): H1Scen =
  {
    val playersKey: Map[Player, HCen] = oPlayers.SomesKeyMap
    val targets: HCenBuffLayer[HCenStep] = gridSys.newHCenArrOfBuff
    deb(s"Orders: ${orderList.length}")

    orderList.foreach { pair =>
      val hc1: HCen = playersKey(pair.a2)
      val optTarget: Option[HCen] = hc1.stepOpt(pair.step)
      optTarget.foreach { target => targets.appendAt(target, pair.a1) }
    }

    /** A new Players grid is created by cloning the old one and then mutating it to the new state. This preserves the old turn state objects and
     * isolates mutation to within the method. */
    val oPlayersNew: HCenOptLayer[Player] = oPlayers.clone
    targets.foreach{ (hc2, buff) => buff.foreachLen1(stCenStep => if (oPlayers.tileNone(hc2)) oPlayersNew.unsafeMove(stCenStep.startHC , hc2)) }

    H1Scen(turn + 1, gridSys, oPlayersNew)
  }
}

/** Companion object for OneScen trait, contains factory apply method. */
object H1Scen
{ /** Factory apply method for OneScen trait. */
  def apply(turnIn: Int, gridIn: HGridSys, opIn: HCenOptLayer[Player]): H1Scen = new H1Scen
  { override val turn = turnIn
    override implicit val gridSys: HGridSys = gridIn
    override def oPlayers: HCenOptLayer[Player] = opIn
  }
}