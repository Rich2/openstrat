/* Copyright 2018-22 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package prid; package phex

trait HexGridScen extends GridTurnScen
{
  /** This gives the structure of the hex grid. It contains no data about the elements of the grid. But it allows the scenario to create and operate
   *  on flat arrays of data. */
  implicit val grid: HGrid

  def defaultView(pxScale: Double = 50): HGridView = grid.coordCen.view(pxScale)// HGridView(4, 4)
}