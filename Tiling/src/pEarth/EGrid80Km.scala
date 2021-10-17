/* Copyright 2018-21 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package pEarth
import geom._, prid._

trait EGrid80Km extends HGridIrr
{
  def rOffset: Int
  def cOffset: Int
}

/** Functions for Earth tile grids where the hex tile is 80 km from side to side. */
object EGrid80Km
{
  /** The scale of the c or column coordinate in metres. */
  val cScale: Metres = 20000.metres * Sqrt3

  /** The r or row value of tiles at the equator. */
  val rOffset: Int = 300

  /** The key method to get the longitude delta for c based from 0° longitude. */
  def hCenToLatLong0(inp: HCen, cOffset: Int): LatLong =hCenToLatLong0(inp.r, inp.c, cOffset)

  /** The key method to get the longitude delta for c based from 0° longitude. */
  def hCenToLatLong0(r: Int, c: Int, cOffset: Int): LatLong =
  { val ym = r * 20000.metres * math.sqrt(3)
    val latRadians: Double = ym / EarthPolarRadius
    val longDelta = colToLongDelta(latRadians, c, cOffset)
    LatLong.radians(latRadians, longDelta)
  }

  def colToLongDelta(latRadians: Double, c: Int, cOffset: Int): Double =
  { val xm = (c - cOffset) * 20000.metres
    xm / (EarthEquatorialRadius * math.cos(latRadians))
  }
}