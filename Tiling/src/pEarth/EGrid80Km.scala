/* Copyright 2018-21 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package pEarth
import geom._, pglobe._, prid._

trait EGrid80Km extends HGridIrr
{ def rOffset: Int
  def cOffset: Int
}

/** Functions for Earth tile grids where the hex tile is 80 km from side to side. */
object EGrid80Km
{
  /** The scale of the c or column coordinate in metres. */
  val cScale: Length = 20000.metres * Sqrt3

  /** The r or row value of tiles at the equator. */
  val rOffset: Int = 300

  /** The key method to get the longitude delta for c based from 0° longitude. */
  def hCenToLatLong0(inp: HCen, cOffset: Int): LatLong = hCenToLatLong0(inp.r, inp.c, cOffset)

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

  /** Copied from pGrid. The key method to get the longitude delta for x based from 0 degs longitude. */
  def hCoordToLatLong0(inp: HCoord): LatLong =
  { val adj: Pt2 = inp.subR(300).toPt2
    val d2: PtM2 = adj.toMetres(cScale)
    val lat: Double = d2.y / EarthPolarRadius
    val longDelta: Double =   d2.x / (EarthEquatorialRadius * math.cos(lat))
    LatLong.radians(lat, longDelta)
  }


  /** Returns the longitudinal delta for a given c at a given y (latitude) for an EGrid80Km Hex Grid. */
  def cDelta(r: Int, c: Int): Double = hCoordToLatLong0(HCoord(r, c)).longDegs

  /** Returns the min and max columns of a tile row in an EGrid80Km grid for a given y (latitude) with a given c offset. */
  def tileRowMaxC(r: Int, cOffset: Int): (Int, Int) =
  {
    val startC: Int = ife(r %% 4 == 0, 0, 2)
    val hexDelta: Double = cDelta(r, 4)
    val margin = 15 - hexDelta

    def loop(cAcc: Int): (Int, Int) =
    {
      val newPt = cDelta(r, cAcc)
      val overlapRatio = (newPt - margin) / hexDelta
      newPt match
      { case r if r < margin => loop(cAcc + 4)
      //case r if overlapRatio < 0.2 => (-xAcc, xAcc + 4)
      case r if overlapRatio < 0.5 => (-cAcc, cAcc)
      case r => (4 - cAcc, cAcc)
      }
    }
    val (neg, pos) = loop(startC)
    (neg + cOffset , pos + cOffset)
  }
}