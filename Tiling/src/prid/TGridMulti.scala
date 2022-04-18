/* Copyright 2018-22 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package prid

trait TGridMulti extends TGridSys
{ type GridT <: TGrid
  def grids: Arr[GridT]
  def foreachRow(f: Int => Unit): Unit = grids.foreach(_.foreachRow(f))

  /** Number of rows of tile centres. This will be different to the number of rows of sides and and will be different to the number of rows of
   *  vertices for HexGrids. */
  def numTileRows: Int = grids.sumBy(_.numTileRows)
}