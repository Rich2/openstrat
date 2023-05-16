/* Copyright 2018-23 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package gOne; package hp1
import prid._, phex._, gPlay._

/** Game manager for [[G1HScen]]. Contains the Scenario and sequence of counters controled by the single GUI player. */
class G1HGame(var scen: G1HScen, val guiCounters: RArr[Counter])
{
  implicit val gridSys: HGridSys = scen.gridSys

  /** Resolves turn. Takes a list [[RArr]] of commands consisting in this simple case of (Player, HStep) pairs. The command is passed in as a relative
   * move. This is in accordance with the principle in more complex games that the entity issuing the command may not know its real location. */
  def endTurn(directives: HCenStepPairArr[Counter]): G1HScen =
  { val res1 = HCenOptStepLayer[Counter]()
    directives.pairForeach { (hcst, ct) => if (guiCounters.contains(ct)) res1.setSome(hcst.startHC, hcst.step, ct) }
    val playersNew = scen.resolve(res1)
    val newScen = G1HScen(scen.turn + 1, scen.gridSys, playersNew)
    scen = newScen
    scen
  }
}

object G1HGame
{
  def apply(startScen: G1HScen, guiCounters: RArr[Counter]): G1HGame = new G1HGame(startScen, guiCounters)
}