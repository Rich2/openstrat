/* Copyright 2018-23 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package gTwo; package s2p
import prid._, psq._, gPlay._

/** Simple game manager for [[G2SqScen]]. Contains the scenario and the set of [[Counter]]s controlled by the single GUI player. */
class G2SGame(scenIn: G2SqScen, val guiCounters: RArr[Counter])
{
  protected var scen: G2SqScen = scenIn
  protected var history: RArr[G2SqScen] = RArr(scen)
  implicit val gridSys: SqGridSys = scen.gridSys

  def getScen: G2SqScen = restrict(scen)

  /** Resolves turn. Takes in the directives from the single GUI player and sets the valid directives as intentions. The command is passed in as a
   *  relative move. This is in accordance with the principle in more complex games that the entity issuing the command may not know its real
   *  location. */
  def resolveTurn(directives: SqCenOptLayer[CounterState]): G2SqScen =
  {
    val intentions = scen.counterStates.scMap{(hc, currentState) => directives(hc) match
      { case Some(directiveState) if guiCounters.contains(currentState.counter) & directiveState.counter == currentState.counter => directiveState
        case _ => currentState
      }
    }
    val newScen = G2SqScen(scen.turn + 1, gridSys, scen.resolve(intentions))
    scen = newScen
    restrict(scen)
  }

  /** Restricts intentions to the counters controled by the player. */
  def restrict(inp: G2SqScen): G2SqScen =
  { val newStates = inp.counterStates.map{ cs => ife(guiCounters.contains(cs.counter), cs, CounterState(cs.counter, SqStepArr())) }
    G2SqScen(inp.turn, gridSys, newStates)
  }
}

object G2SGame
{ /** Factory apply method for [[G2SGame]] trait. */
  def apply(scenStart: G2SqScen, guiCounters: RArr[Counter]): G2SGame = new G2SGame(scenStart, guiCounters)
}