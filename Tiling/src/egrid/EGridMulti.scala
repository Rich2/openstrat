/* Copyright 2018-22 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package egrid
import geom._, pglobe._, prid._, phex._

/** A system of hex grids for the earth containing multiple grids. */
trait EGridMulti extends EGridSys with HGridMulti
{ override type GridT = EGrid
  override type ManT = EGridMan
  override def hCoordLL(hc: HCoord): LatLong = unsafeGetManFunc(hc)(_.grid.hCoordLL(hc))
}

trait EGridMan extends HGridMan
{ override def grid: EGrid
  def innerRowInnerSidesForeach(r: Int)(f: HSide => Unit): Unit
  final def innerSidesForeach(f: HSide => Unit): Unit = grid.innerSideRowsForeach(r => innerRowInnerSidesForeach(r)(f))

}