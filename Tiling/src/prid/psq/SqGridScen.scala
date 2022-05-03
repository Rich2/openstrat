/* Copyright 2018-22 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package prid; package psq

abstract class SqSysGui(title: String) extends TGridSysGui(title)
{ override def ptScale: Double = cPScale * 2
}

trait SqGridScen extends GridTurnScen
{
  /** This gives the structure of the square grid. It contains no data about the elements of the grid. But it allows the scenario to create and
   *  operate on flat arrays of data. */
  implicit val grid: SqGrid

  def defaultView(pxScale: Double = 50): SqGridView = grid.defaultView(pxScale)
}