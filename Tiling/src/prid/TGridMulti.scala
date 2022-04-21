/* Copyright 2018-22 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package prid

/** A tile grid system consisting of multiple tile grids. */
trait TGridMulti extends TGridSys
{ /** The type of the tile grids within this tile grid system. */
  type GridT <: TGrid

  /** The grids of this tile gird system. */
  def grids: Arr[GridT]

  override def foreachRow(f: Int => Unit): Unit = grids.foreach(_.foreachRow(f))
  def numTileRows: Int = grids.sumBy(_.numTileRows)
}