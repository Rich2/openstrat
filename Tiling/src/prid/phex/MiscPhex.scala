/* Copyright 2018-22 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package prid; package phex

trait HexGridBasedScen extends GridTurnScen
{
  implicit def grid: HGridBased
}

trait HexGridScen extends HexGridBasedScen{
  /** This gives the structure of the hex grid. It contains no data about the elements of the grid. But it allows the scenario to create and operate
   *  on flat arrays of data. */
  implicit override def grid: HGrid

  def defaultView(pxScale: Double = 50): HGridView = grid.coordCen.view(pxScale)
}