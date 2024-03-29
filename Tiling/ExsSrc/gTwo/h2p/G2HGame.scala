/* Copyright 2018-23 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package gTwo; package h2p
import prid._, phex._, gPlay._

/** Simple game manager for [[G2HScen]]. Contains the scenario and the set of [[Counter]]s controlled by the single GUI player. */
class G2HGame(scenIn: G2HScen, val guiCounters: RArr[Counter])
{
  protected var scen: G2HScen = scenIn
  implicit val gridSys: HGridSys = scen.gridSys
  var history: RArr[G2HScen] = RArr(scen)
  def getScen: G2HScen = restrict(scen)

  /** Resolves turn. Takes in the directives from the single GUI player and sets the valid directives as intentions. The command is passed in as a
   *  relative move. This is in accordance with the principle in more complex games that the entity issuing the command may not know its real
   *  location. */
  def resolveTurn(directives: LayerHcOptSys[CounterState]): G2HScen =
  {
    val intentions = scen.counterStates.hcMap{(hc, currentState) => directives(hc) match
      { case Some(directiveState) if guiCounters.contains(currentState.counter) & directiveState.counter == currentState.counter => directiveState
        case _ => currentState
      }
    }
    val newScen = G2HScen(scen.turn + 1, gridSys, scen.resolve(intentions))
    scen = newScen
    history +%= scen
    restrict(scen)
  }

  /** Restricts intentions to the counters controlled by the player. */
  def restrict(inp: G2HScen): G2HScen =
  { val newStates = inp.counterStates.map{ cs => ife(guiCounters.contains(cs.counter), cs, CounterState(cs.counter, HStepArr())) }
    G2HScen(inp.turn, gridSys, newStates)
  }
}

object G2HGame
{ /** Factory apply method for [[G2HGame]] trait. */
  def apply(scenStart: G2HScen, guiCounters: RArr[Counter]): G2HGame = new G2HGame(scenStart, guiCounters)
}