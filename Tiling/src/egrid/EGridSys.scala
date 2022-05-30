/* Copyright 2018-22 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package egrid
import geom._
import pglobe._
import prid._
import phex._
import pEarth._

import scala.reflect.ClassTag

trait EGridSys extends HGridSys
{ /** The length of one column coordinate delta */
  def cScale: Length

  /** hex coordinate to latiutde and longitude. */
  def hCoordLL(hc: HCoord): LatLong

  def sideLineLLs: LineSegLLArr = sideLineSegHCs.map(_.map(hCoordLL(_)))
  def innerSideLineLLs: LineSegLLArr = innerSideLineSegHCs.map(_.map(hCoordLL(_)))
  def outerSideLineLLs: LineSegLLArr = outerSideLineSegHCs.map(_.map(hCoordLL(_)))

  def sideLineM3s: LineSegM3Arr = sideLineLLs.map(_.map(_.toMetres3))
  def innerSideLineM3s: LineSegM3Arr = innerSideLineLLs.map(_.map(_.toMetres3))
  def outerSideLineM3s: LineSegM3Arr = outerSideLineLLs.map(_.map(_.toMetres3))
}
/** A hex grid on the surface of the earth. */
abstract class EGrid(bottomTileRow: Int, unsafeRowsArray: Array[Int], val cScale: Length) extends HGridIrr(bottomTileRow, unsafeRowsArray) with
  EGridSys

trait EScenFlat extends HSysScen
{ def terrs: HCenDGrid[WTile]
  def sTerrs: HSideBoolDGrid
  def title: String = "EScenFlat"
}

/** A basic EGrid scenario, containing grid and basic terrain data. */
trait EScenWarm extends EScenFlat
{ override def gridSys: EGridWarmSys
  override def title: String = "EScenBasic"
  def warms: Arr[WarmTerrs] = Arr()
  override def terrs: HCenDGrid[WTile] = warms.tailfold(warms(0).terrs)(_ ++ _.terrs)
}

/** A basic EGrid scenario, containing grid and basic terrain data. */
object EScenWarm
{
  def apply(gridSys: EGridWarmSys, terrs: HCenDGrid[WTile], sTerrs: HSideBoolDGrid): EScenWarm = new EScenWarmImp(gridSys, terrs, sTerrs)

  class EScenWarmImp(val gridSys: EGridWarmSys, override val terrs: HCenDGrid[WTile], val sTerrs: HSideBoolDGrid) extends EScenWarm
}

trait EScenWarmMulti extends EScenWarm{
  override def gridSys: EGridWarmMulti
}