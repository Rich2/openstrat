/* Copyright 2018-22 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package egrid
import geom._, pglobe._, prid._, phex._

trait EGridSys extends HGridSys
{ /** The length of one column coordinate delta */
  def cScale: Length

  /** hex coordinate to latiutde and longitude. */
  def hCoordLL(hc: HCoord): LatLong

  def sideLineLLs: LineSegLLArr = sideLineSegHCs.map(lsh => LineSegLL(hCoordLL(lsh.startPt), hCoordLL(lsh.endPt)))
  def sideLineM3s = sideLineLLs.map(lsh => LineSegM3(lsh.startPt.toMetres3, lsh.endPt.toMetres3))
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
trait EScenBasic extends HSysScen
{
  def gridSys: EGridMainSys
  def terrs: HCenDGrid[pEarth.WTile]
}

/** A basic EGrid scenario, containing grid and basic terrain data. */
object EScenBasic
{
  def apply(gridSys: EGridMainSys, terrs: HCenDGrid[pEarth.WTile]): EScenBasic = new EScenBasicImp(gridSys, terrs)

  class EScenBasicImp(val gridSys: EGridMainSys, val terrs: HCenDGrid[pEarth.WTile]) extends EScenBasic
}

case class EScenBasicFlat(gridSys: EGridMain, terrs: HCenDGrid[pEarth.WTile]) extends EScenBasic