/* Copyright 2018-23 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package egrid
import geom._, pglobe._, prid._, phex._

/** A system of hex grids for the earth containing multiple grids. */
trait EGridMulti extends EGridSys with HGridMulti
{ override type GridT = EGrid
  override type ManT = EGridMan
  override def hCoordLL(hc: HCoord): LatLong = unsafeGetManFunc(hc)(_.grid.hCoordLL(hc))

  override def coordCen: HCoord = grids(numGrids / 2).coordCen

  override def flatHCoordToPt2(hCoord: HCoord): Pt2 = unsafeGetManFunc(hCoord){m => m.grid.flatHCoordToPt2(hCoord) + m.offset }

  override def rowsCombine[A <: AnyRef](layer: HCenLayer[A], indexingGSys: HGridSys): RArr[HCenRowPair[A]] = grids.flatMap(_.rowsCombine(layer, this))
}

trait EGridMan extends HGridMan
{ override def grid: EGrid
  def innerRowInnerSidesForeach(r: Int)(f: HSide => Unit): Unit
  final def linksForeach(f: HSide => Unit): Unit = grid.innerSideRowsForeach(r => innerRowInnerSidesForeach(r)(f))
}