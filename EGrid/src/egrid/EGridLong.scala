/* Copyright 2018-24 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package egrid
import geom._, pglobe._, prid._, phex._

/** An Earth grid covering all or part of a 30 degree range of longitude for the non polar regions. */
abstract class EGridLong(rBottomCen: Int, val longGridIndex: Int, cScale: MetricLength, val rOffset: Int, rowArray: Array[Int]) extends
  EGrid(rBottomCen, rowArray, cScale)
{ /** The C column coordinate of the middle of the full grid. */
  def cOffset: Int = (longGridIndex %% 12) * 1024 + 512

  /** The central line of longitude of the full grid. This is a multiple of 30 degrees. 0E, 30E ... 180E, 150W ... 30W. */
  def cenLong: Longitude = Longitude.degs(longGridIndex * 30)

  /** The latitude and longitude [[LatLong]] of an [[HCoord]] excluding the distorted east and west extremes of the grid. */
  def hCoordMiddleLL(hc: HCoord): LatLong = EGridLong.hCoordToLatLong0(hc.r - rOffset, hc.c - cOffset, cScale).addLong(cenLong)

  /** The latitude and longitude [[LatLong]] of an [[HCoord]] within the grid. */
  def hCoordLL(hc: HCoord): LatLong
}

object EGridLong
{ /** The key method to get the longitude delta for c based from 0° longitude. */
  def hCenToLatLong0(inp: HCoord, cOffset: Int, cScale: MetricLength): LatLong = hCenToLatLong0(inp.r, inp.c, cOffset, cScale)

  /** The key method to get the longitude delta for c based from 0° longitude. */
  def hCenToLatLong0(r: Int, c: Int, cOffset: Int, cScale: MetricLength): LatLong =
  { val ym = cScale * r * math.sqrt(3)
    val latRadians: Double = ym / EarthPolarRadius
    val longDelta = colToLongDelta(latRadians, c, cOffset, cScale)
    LatLong.radians(latRadians, longDelta)
  }

  def colToLongDelta(latRadians: Double, c: Int, cOffset: Int, cScale: MetricLength): Double =
  { val xm = cScale  * (c - cOffset)
    xm / (EarthEquatorialRadius * math.cos(latRadians))
  }

  def hCoordToLatLong0(r: Int, c: Int, cScale: MetricLength): LatLong = hCoordToLatLong0(HCoord(r, c), cScale)

  /** Copied from pGrid. The key method to get the longitude delta for x based from 0 degs longitude. */
  def hCoordToLatLong0(inp: HCoord, cScale: MetricLength): LatLong =
  { val adj: Pt2 = inp.toPt2Reg
    val d2: PtM2 = adj.toMetres(cScale)
    val latRadians: Double = d2.y / EarthPolarRadius
    val latcos = math.cos(latRadians)
    val longDelta: Double = d2.x / (EarthEquatorialRadius * latcos)
    LatLong.radians(latRadians, longDelta)
  }

  /** Returns the longitudinal delta for a given c at a given y (latitude) for an EGrid80Km Hex Grid. */
  def cDelta(r: Int, c: Int, cScale: MetricLength): Double =
  { val ll = hCenToLatLong0(HCoord(r, c), 0, cScale)
    ll.longDegs
  }
}