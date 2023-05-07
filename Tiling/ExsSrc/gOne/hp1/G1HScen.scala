/* Copyright 2018-22 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package gOne; package hp1
import prid._, phex._, gPlay._

/** A scenario turn or state for Game One. Consists of just a turn number and a tile Grid. Each tile can contain a single player or can be empty. */
trait G1HScen extends HSysTurnScen
{ override def title: String = "Game 1 hex Scen"

  /** An optional player can occupy each tile. This is the only tile data in the game. */
  def players: HCenOptLayer[Player]

  /** Resolves turn. Takes a list [[RArr]] of commands consisting in this simple case of (Player, HStep) pairs. The command is passed in as a relative
   * move. This is in accordance with the principle in more complex games that the entity issuing the command may not know its real location. */
  def endTurn(orders: HCenStepPairArr[Player]): G1HScen =
  { val res1 = HCenOptStepLayer[Player]()
    orders.pairForeach{ (hcst, pl) =>  res1.setSome(hcst.startHC, hcst.step, pl) }
    val playersNew = resolve(res1)
    G1HScen(turn + 1, gridSys, playersNew)
  }

  /** Contains the resolution logic. The actions are presumed to be correct. Combining and checking of actions should be done before calling this
   *  method. */
  def resolve(actions: HCenOptStepLayer[Player]): HCenOptLayer[Player] =
  { val playersNew: HCenOptLayer[Player] = players.copy
    val acc: HCenAccLayer[Player] = actions.mapAcc
    acc.foreach{ (target, arr) => if(arr.length == 1 & players(target).isEmpty) playersNew.moveUnsafe(arr.headHCen, target) }
    playersNew
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