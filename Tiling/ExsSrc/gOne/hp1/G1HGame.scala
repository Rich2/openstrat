/* Copyright 2018-23 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package gOne; package hp1
import prid._, phex._, gPlay._

/** Game manager for [[G1HScen]]. */
class G1HGame(var scen: G1HScen, val guiPlayers: RArr[Player])
{
  implicit val gridSys: HGridSys = scen.gridSys

  /** Resolves turn. Takes a list [[RArr]] of commands consisting in this simple case of (Player, HStep) pairs. The command is passed in as a relative
   * move. This is in accordance with the principle in more complex games that the entity issuing the command may not know its real location. */
  def endTurn(orders: HCenStepPairArr[Player]): G1HScen =
  { val res1 = HCenOptStepLayer[Player]()
    orders.pairForeach { (hcst, pl) => if (guiPlayers.exists(_ == pl)) res1.setSome(hcst.startHC, hcst.step, pl) }
    val playersNew = scen.resolve(res1)
    val newScen = G1HScen(scen.turn + 1, scen.gridSys, playersNew)
    scen = newScen
    scen
  }
}

object G1HGame
{
  def apply(startScen: G1HScen, guiPlayers: RArr[Player]): G1HGame = new G1HGame(startScen, guiPlayers)
}