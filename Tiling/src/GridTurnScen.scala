/* Copyright 2018-22 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package prid

/** The state of a simultaneous turn tile grid scenario. */
trait GridTurnScen
{ def title: String

  /** The turn number. This will normally start at 0. The player will then give their instructions for turn 1. The scenario will take these orders /
   * instructions and return the new game state at turn 1. */
  def turn: Int
}