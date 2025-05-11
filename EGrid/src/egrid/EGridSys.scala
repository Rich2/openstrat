/* Copyright 2018-25 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package egrid
import geom.*, pgui.*, pglobe.*, prid.phex.*

/** A hex grid system on the surface of the earth. */
trait EGridSys extends HGridSys
{
  override def projection: Panel => HSysProjection = HSysProjectionEarth(this, _)

  /** The length of one column coordinate delta */
  def cScale: LengthMetric

  /** hex coordinate to latitude and longitude. */
  def hCoordLL(hc: HCoord): LatLong

  def hCoordLLDirn(hc: HCoord, dirn: NorthSouthUp = NorthUp): LatLongDirn = hCoordLL(hc).andDirn(dirn)

  def sideLineLLs: LineSegLLArr = sepLineSegHCs.map(_.map(hCoordLL(_)))
  def innerSideLineLLs: LineSegLLArr = innerSepLineSegHCs.map(_.map(hCoordLL(_)))
  def outerSideLineLLs: LineSegLLArr = outerSepLineSegHCs.map(_.map(hCoordLL(_)))

  def sideLineM3s: LineSegM3Arr = sideLineLLs.map(_.map(_.toMetres3))
  def innerSideLineM3s: LineSegM3Arr = innerSideLineLLs.map(_.map(_.toMetres3))
  def outerSideLineM3s: LineSegM3Arr = outerSideLineLLs.map(_.map(_.toMetres3))
}

/** A hex grid on the surface of the earth. */
abstract class EGrid(bottomTileRow: Int, unsafeRowsArray: Array[Int], val cScale: LengthMetric) extends HGridGen(bottomTileRow, unsafeRowsArray), EGridSys