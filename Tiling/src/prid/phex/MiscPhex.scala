/* Copyright 2018-22 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package prid; package phex

trait HexGriderScen extends GridTurnScen
{
  implicit def grider: HGridSys
  def defaultView(pxScale: Double = 50): HGridView = grider.defaultView(pxScale)
}

trait HexGriderFlatScen extends HexGriderScen
{
  implicit override def grider: HGridSysFlat
}

trait HexGridScen extends HexGriderScen
{ /** This gives the structure of the hex grid. It contains no data about the elements of the grid. But it allows the scenario to create and operate
   *  on flat arrays of data. */
  implicit override def grider: HGrid
}