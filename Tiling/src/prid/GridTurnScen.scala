/* Copyright 2018-21 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package prid

/** The state of a simultaneous turn tile grid scenario. */
trait GridTurnScen
{
  /** The turn number. This will normally start at 0. The player will then give their instructions for turn 1. The scenario will take these orders /
   * instructions and return the new game state at turn 1. */
  def turn: Int
}

trait HexGridScen extends GridTurnScen
{
  /** This gives the structure of the hex grid. It contains no data about the elements of the grid. But it allows the scenario to create and operate
   *  on flat arrays of data. */
  implicit val grid: HGrid

  def defaultView: HGridView = HGridView(4, 4)
}

trait SqGridScen extends GridTurnScen
{
  /** This gives the structure of the square grid. It contains no data about the elements of the grid. But it allows the scenario to create and
   *  operate on flat arrays of data. */
  implicit val grid: SqGrid
}