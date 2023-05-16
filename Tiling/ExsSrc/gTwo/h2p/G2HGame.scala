/* Copyright 2018-23 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package gTwo; package h2p
import prid._, phex._, gPlay._, gOne.hp1.GSys

class G2HGame(scenIn: G2HScen, val guiCounters: RArr[Counter])
{
  protected var scen: G2HScen = scenIn
  val gridSys = scen.gridSys

  def getScen: G2HScen = restrict(scen)

  /** Resolves turn. Takes a list [[RArr]] of commands consisting in this simple case of (Player, HStep) pairs. The command is passed in as a relative
   * move. This is in accordance with the principle in more complex games that the entity issuing the command may not know its real location. */
  def turnUnchecked(oldStates: HCenOptLayer[CounterState]): G2HScen =
  { val newScen = G2HScen(scen.turn + 1, gridSys, scen.resolve(oldStates))
    scen = newScen
    restrict(scen)
  }

  /** Restricts intentions to the counters controled by the player. */
  def restrict(inp: G2HScen): G2HScen =
  { val newStates = inp.counterStates.map{ cs => ife(guiCounters.contains(cs.counter), cs, CounterState(cs.counter, HStepArr())) }
    G2HScen(inp.turn, gridSys, newStates)
  }
}

object G2HGame
{
  def apply(scenStart: G2HScen, guiCounters: RArr[Counter]): G2HGame = new G2HGame(scenStart, guiCounters)
}