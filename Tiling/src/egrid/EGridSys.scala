/* Copyright 2018-22 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package egrid
import geom._, pglobe._, prid._, phex._, pEarth._

trait EGridSys extends HGridSys
{ /** The length of one column coordinate delta */
  def cScale: Length

  /** hex coordinate to latiutde and longitude. */
  def hCoordLL(hc: HCoord): LatLong

  def sideLineLLs: LineSegLLArr = sideLineSegHCs.map(lsh => LineSegLL(hCoordLL(lsh.startPt), hCoordLL(lsh.endPt)))
  def innerSideLineLLs: LineSegLLArr = innerSideLineSegHCs.map(lsh => LineSegLL(hCoordLL(lsh.startPt), hCoordLL(lsh.endPt)))
  def outerSideLineLLs: LineSegLLArr = outerSideLineSegHCs.map(lsh => LineSegLL(hCoordLL(lsh.startPt), hCoordLL(lsh.endPt)))

  def sideLineM3s: LineSegM3Arr = sideLineLLs.map(lsh => LineSegM3(lsh.startPt.toMetres3, lsh.endPt.toMetres3))
  def innerSideLineM3s: LineSegM3Arr = innerSideLineLLs.map(lsh => LineSegM3(lsh.startPt.toMetres3, lsh.endPt.toMetres3))
  def outerSideLineM3s: LineSegM3Arr = outerSideLineLLs.map(lsh => LineSegM3(lsh.startPt.toMetres3, lsh.endPt.toMetres3))
}
/** A hex grid on the surface of the earth. */
abstract class EGrid(bottomTileRow: Int, unsafeRowsArray: Array[Int], val cScale: Length) extends HGridIrr(bottomTileRow, unsafeRowsArray) with
  EGridSys

trait EGridMan extends HGridMan
{ override def grid: EGrid
  def innerRowForeachInnerSide(r: Int)(f: HSide => Unit): Unit
  final def innerSidesForeach(f: HSide => Unit): Unit = grid.innerSideRowsForeach(r => innerRowForeachInnerSide(r)(f))
}

trait EGridMulti extends EGridSys with HGridMulti
{ override type GridT = EGrid
  override type ManT = EGridMan
  override def hCoordLL(hc: HCoord): LatLong = unsafeGetManFunc(hc)(_.grid.hCoordLL(hc))
}

trait EScenFlat extends HSysScen
{ def terrs: HCenDGrid[WTile]
  def sTerrs: HSideBoolDGrid
  def title: String = "EScenFlat"
}

/** A basic EGrid scenario, containing grid and basic terrain data. */
trait EScenBasic extends EScenFlat
{ override def gridSys: EGridMainSys
  override def title: String = "EScenBasic"
}

/** A basic EGrid scenario, containing grid and basic terrain data. */
object EScenBasic
{
  def apply(gridSys: EGridMainSys, terrs: HCenDGrid[WTile], sTerrs: HSideBoolDGrid): EScenBasic = new EScenBasicImp(gridSys, terrs, sTerrs)

  class EScenBasicImp(val gridSys: EGridMainSys, val terrs: HCenDGrid[WTile], val sTerrs: HSideBoolDGrid) extends EScenBasic
}