/* Copyright 2018-26 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package gFour; package h4p
import prid.*, phex.*, gPlay.*

/** Simple game manager for [[G3HScen]]. Contains the scenario and the set of [[Team]]s controlled by the single GUI player. */
class G4HGame(scenIn: G4HScen, val guiTeams: RArr[Team])
{
  protected var scen: G4HScen = scenIn
  implicit val gridSys: HGridSys = scen.gridSys

  def getScen: G4HScen = restrict(scen)

  /** Resolves turn. Takes in the directives from the single GUI player and sets the valid directives as intentions. The command is passed in as a
   *  relative move. This is in accordance with the principle in more complex games that the entity issuing the command may not know its real
   *  location. */
  def resolveTurn(directives: RArr[LunitState]): G4HScen = ???
  /*{ val intentions = scen.lunitStates

    val newScen = G4HScen(scen.turn + 1, gridSys, scen.resolve(intentions), scen.teamSet)
    scen = newScen
    restrict(scen)
  }*/

  /** Restricts intentions to the counters controled by the player. */
  def restrict(inp: G4HScen): G4HScen = ???
  /*{ val ias: HCenIntNArrLayer[HCen, HCenArr] = inp.lunitStates.map{ hf => HCenArr()}
    val oldStates: LayerHcRArr[LunitState]= inp.lunitStates
    val newStates = oldStates.mapMap{ cs => ife(guiTeams.contains(cs.team), cs, LunitState(cs.lunit, HStepArr())) }
    G4HScen(inp.turn, gridSys, newStates, inp.teamSet)
  }*/
}

object G4HGame
{ /** Factory apply method for [[G3HGame]] trait. */
  def apply(scenStart: G4HScen, guiCounters: RArr[Team]): G4HGame = new G4HGame(scenStart, guiCounters)
}