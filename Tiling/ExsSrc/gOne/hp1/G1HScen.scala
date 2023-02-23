/* Copyright 2018-22 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package gOne; package hp1
import prid._, phex._, gPlay._

/** A scenario turn or state for Game One. Consists of just a turn number and a tile Grid. Each tile can contain a single player or can be empty. */
trait G1HScen extends HSysTurnScen
{ /** An optional player can occupy each tile. This is the only tile data in the game. */
  def players: HCenOptLayer[Player]

  /** Resolves turn. Takes a list [[RArr]] of commands consisting in this simple case of (Player, HStep) pairs. The command is passed in as a relative
   * move. This is in accordance with the principle in more complex games that the entity issueing the command may not know its real location. */
  def endTurn(orders: HCenStepPairArr[Player]): G1HScen =
  { val psKey = players.somePairs
    val targets: HCenBuffLayer[HCenStep] = gridSys.newHCenArrOfBuff

    orders.foreach { pair =>
      val hc1 = psKey.a2GetA1(pair.a2)
      val optTarget: Option[HCen] = hc1.stepOpt(pair.step)
      optTarget.foreach { target => targets.appendAt(target, pair.a1) }
    }

    /** A new Players grid is created by cloning the old one and then mutating it to the new state. This preserves the old turn state objects and
     * isolates mutation to within the method. */
    val playersNew: HCenOptLayer[Player] = players.clone
    targets.foreach{ (hc2, buff) => buff.foreachLen1(stCenStep => if (players.tileNone(hc2)) playersNew.unsafeMove(stCenStep.startHC , hc2)) }

    G1HScen(turn + 1, gridSys, playersNew)
  }
}

/** Companion object for OneScen trait, contains factory apply method. */
object G1HScen
{ /** Factory apply method for OneScen trait. */
  def apply(turnIn: Int, gridIn: HGridSys, opIn: HCenOptLayer[Player]): G1HScen = new G1HScen
  { override val turn = turnIn
    override implicit val gridSys: HGridSys = gridIn
    override def players: HCenOptLayer[Player] = opIn
  }
}