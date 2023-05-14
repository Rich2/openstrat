/* Copyright 2018-23 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package gTwo; package h2p
import prid._, phex._, gPlay._, gOne.hp1.GSys

class G2HGame(var scen: G2HScen)
{
  val gridSys = scen.gridSys
  /** Resolves turn. Takes a list [[RArr]] of commands consisting in this simple case of (Player, HStep) pairs. The command is passed in as a relative
   * move. This is in accordance with the principle in more complex games that the entity issuing the command may not know its real location. */
  def turnUnchecked(oldStates: HCenOptLayer[PlayerState]): G2HScen = G2HScen(scen.turn + 1, gridSys, scen.resolve(oldStates))
}