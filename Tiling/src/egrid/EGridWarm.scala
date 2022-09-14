/* Copyright 2018-22 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package egrid
import geom._, pglobe._, prid._, phex._

abstract class EGridWarm(rBottomCen: Int, rTopCen: Int, val cenLongInt: Int, cScale: Length, val rOffset: Int, rowArray: Array[Int]) extends
  EGrid(rBottomCen, rowArray, cScale)
{
  def cOffset: Int = (cenLongInt %% 12) * 1024 + 512

  def cenLong: Longitude = Longitude.degs(cenLongInt * 30)

  def hCoordMiddleLL(hc: HCoord): LatLong = EGridWarm.hCoordToLatLong0(hc.r - rOffset, hc.c - cOffset, cScale).addLong(cenLong)

  def hCoordLL(hc: HCoord): LatLong = hc.c match {
    case _ if hc.isCen => hCoordMiddleLL(hc)

    case c if c == rowRightCoordC(hc.r, c) => {
      val rt = hCoordMiddleLL(hc)
      val lt = hCoordMiddleLL(HCoord(hc.r, rowLeftCoordC(hc.r, c)))
      val rtLong = rt.longMilliSecs
      val ltLong = (lt.long + 30.east).milliSecs
      val longMilliSecs = rtLong aver ltLong
      LatLong.milliSecs(rt.latMilliSecs, longMilliSecs)
    }

    case c if c == rowLeftCoordC(hc.r, c) => {
      val lt = hCoordMiddleLL(hc)
      val rt = hCoordMiddleLL(HCoord(hc.r, rowRightCoordC(hc.r, c)))
      val ltLong = lt.longMilliSecs
      val rtLong = (rt.long - 30.east).milliSecs
      val longMilliSecs = ltLong aver rtLong
      LatLong.milliSecs(lt.latMilliSecs, longMilliSecs)
    }

    case _ => hCoordMiddleLL(hc)
  }
}

object EGridWarm{
  /** The key method to get the longitude delta for c based from 0° longitude. */
  def hCenToLatLong0(inp: HCoord, cOffset: Int, cScale: Length): LatLong = hCenToLatLong0(inp.r, inp.c, cOffset, cScale)

  /** The key method to get the longitude delta for c based from 0° longitude. */
  def hCenToLatLong0(r: Int, c: Int, cOffset: Int, cScale: Length): LatLong = {
    val ym = r * cScale * math.sqrt(3)
    val latRadians: Double = ym / EarthPolarRadius
    val longDelta = colToLongDelta(latRadians, c, cOffset, cScale)
    LatLong.radians(latRadians, longDelta)
  }

  def colToLongDelta(latRadians: Double, c: Int, cOffset: Int, cScale: Length): Double = {
    val xm = (c - cOffset) * cScale
    xm / (EarthEquatorialRadius * math.cos(latRadians))
  }

  def hCoordToLatLong0(r: Int, c: Int, cScale: Length): LatLong = hCoordToLatLong0(HCoord(r, c), cScale)

  /** Copied from pGrid. The key method to get the longitude delta for x based from 0 degs longitude. */
  def hCoordToLatLong0(inp: HCoord, cScale: Length): LatLong = {
    val adj: Pt2 = inp.toPt2Reg
    val d2: PtM2 = adj.toMetres(cScale)
    val latRadians: Double = d2.y / EarthPolarRadius
    val latcos = math.cos(latRadians)
    val longDelta: Double = d2.x / (EarthEquatorialRadius * latcos)
    LatLong.radians(latRadians, longDelta)
  }

  /** Returns the longitudinal delta for a given c at a given y (latitude) for an EGrid80Km Hex Grid. */
  def cDelta(r: Int, c: Int, cScale: Length): Double = {
    val ll = hCenToLatLong0(HCoord(r, c), 0, cScale)
    ll.longDegs
  }
}
