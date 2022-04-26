/* Copyright 2018-22 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package egrid
import geom.pglobe._, prid._, phex._

trait EGridSys extends HGridSys
{ def cScale: Length
  def hCoordLL(hc: HCoord): LatLong
}
/** A hex grid on the surface of the earth. */
abstract class EGrid(bottomTileRow: Int, unsafeRowsArray: Array[Int], val cScale: Length) extends HGridIrr(bottomTileRow, unsafeRowsArray) with
  EGridSys

trait EGridMan extends HGridMan
{ override val grid: EGrid
}

trait EGridMulti extends EGridSys with HGridMulti
{ override type GridT = EGrid
  override type ManT = EGridMan
  override def hCoordLL(hc: HCoord): LatLong = unsafeGetManFunc(hc)(_.grid.hCoordLL(hc))
}

/** A basic EGrid scenario, containing grid and basic terrain data. */
class EScenBasic(val eGrid: EGridMainSys, val terrs: HCenDGrid[pEarth.WTile])