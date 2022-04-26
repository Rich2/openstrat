/* Copyright 2018-22 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package prid; package phex

/** Scenario based on a [[HGrid]] system. */
trait HexGridSysScen extends GridTurnScen
{
  implicit def gridSys: HGridSys
  def defaultView(pxScale: Double = 50): HGridView = gridSys.defaultView(pxScale)
}

/** Scenario based on a falt 2D [[HGrid]] system. */
trait HexGriderFlatScen extends HexGridSysScen
{
  implicit override def gridSys: HGridSysFlat
}

trait HexGridScen extends HexGridSysScen
{ /** This gives the structure of the hex grid. It contains no data about the elements of the grid. But it allows the scenario to create and operate
   *  on flat arrays of data. */
  implicit override def gridSys: HGrid
}