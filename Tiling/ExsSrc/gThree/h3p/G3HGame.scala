/* Copyright 2018-23 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package gThree; package h3p
import prid._, phex._, gPlay._

/** Simple game manager for [[G3HScen]]. Contains the scenario and the set of [[Team]]s controlled by the single GUI player. */
class G3HGame(scenIn: G3HScen, val guiTeams: RArr[Team])
{
  protected var scen: G3HScen = scenIn
  implicit val gridSys: HGridSys = scen.gridSys

  def getScen: G3HScen = restrict(scen)

  /** Resolves turn. Takes in the directives from the single GUI player and sets the valid directives as intentions. The command is passed in as a
   *  relative move. This is in accordance with the principle in more complex games that the entity issuing the command may not know its real
   *  location. */
  /*def resolveTurn(directives: HCenRArrLayer[LunitState]): G3HScen = {
    val intentions = scen.lunitStates.hcMap { (hc, currentState) =>
      directives(hc) match {
        case Some(directiveState) if guiCounters.contains(currentState.counter) & directiveState.counter == currentState.counter => directiveState
        case _ => currentState
      }
    }
    val newScen = G3HScen(scen.turn + 1, gridSys, scen.resolve(intentions))
    scen = newScen
    restrict(scen)
  }*/

  /** Restricts intentions to the counters controled by the player. */
  def restrict(inp: G3HScen): G3HScen =
  { val ias: HCenIntNArrLayer[HCen, HCenArr] = inp.lunitStates.map{ hf => HCenArr()}
    val oldStates: HCenRArrLayer[LunitState]= inp.lunitStates
    val newStates = oldStates.mapMap{ cs => ife(guiTeams.contains(cs.team), cs, LunitState(cs.lunit, HStepArr())) }
    G3HScen(inp.turn, gridSys, newStates, inp.teamSet)
  }
}

object G3HGame
{ /** Factory apply method for [[G3HGame]] trait. */
  def apply(scenStart: G3HScen, guiCounters: RArr[Team]): G3HGame = new G3HGame(scenStart, guiCounters)
}