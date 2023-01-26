/* Copyright 2018-23 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package egrid
import geom._, pglobe._, prid._, phex._, reflect.ClassTag

trait EGridMan extends HGridMan
{
  /** The multi grid system that contains the grid that this is managing. */
  def sys: EGridMulti
  override def grid: EGrid
  def innerRowInnerSidesForeach(r: Int)(f: HSide => Unit): Unit
  final def linksForeach(f: HSide => Unit): Unit = grid.innerSideRowsForeach(r => innerRowInnerSidesForeach(r)(f))

  lazy val sideIndexStart: Int =
    ife(thisInd == 0, 0, sys.gridMans(thisInd - 1).sideIndexStart + sys.gridMans(thisInd - 1).numSides)

}