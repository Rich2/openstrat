/* Copyright 2018-21 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package egrid
import geom._, pglobe._, prid._

/** A hex grid on the surface of the earth. */
trait EGrid extends HGridIrr
{
  /** The scale of the c or column coordinate in metres. */
  val cScale: Length
}

/** One of the main hex grids for the earth not a polar grid. */
trait EGridMain extends EGrid
{ /** The row offset for the longitude centre */
  def rOffset: Int

  /** The c offset for the Equator */
  def cOffset: Int
}

/** Functions for Earth tile grids where the hex tile is 80 km from side to side. */
object EGridMain
{ /** The key method to get the longitude delta for c based from 0° longitude. */
  def hCenToLatLong0(inp: HCen, cOffset: Int, cScale: Length): LatLong = hCenToLatLong0(inp.r, inp.c, cOffset, cScale)

  /** The key method to get the longitude delta for c based from 0° longitude. */
  def hCenToLatLong0(r: Int, c: Int, cOffset: Int, cScale: Length): LatLong =
  { val ym = r * cScale * math.sqrt(3)
    val latRadians: Double = ym / EarthPolarRadius
    val longDelta = colToLongDelta(latRadians, c, cOffset, cScale)
    LatLong.radians(latRadians, longDelta)
  }

  def colToLongDelta(latRadians: Double, c: Int, cOffset: Int, cScale: Length): Double =
  { val xm = (c - cOffset) * cScale
    xm / (EarthEquatorialRadius * math.cos(latRadians))
  }

  /** Copied from pGrid. The key method to get the longitude delta for x based from 0 degs longitude. */
  def hCoordToLatLong0(inp: HCoord, cScale: Length): LatLong =
  { val adj: Pt2 = inp.subR(300).toPt2
    val d2: PtM2 = adj.toMetres(cScale)
    val lat: Double = d2.y / EarthPolarRadius
    val longDelta: Double =   d2.x / (EarthEquatorialRadius * math.cos(lat))
    LatLong.radians(lat, longDelta)
  }

  /** Returns the longitudinal delta for a given c at a given y (latitude) for an EGrid80Km Hex Grid. */
  def cDelta(r: Int, c: Int, cScale: Length): Double = hCoordToLatLong0(HCoord(r, c), cScale).longDegs

  /** Returns the min and max columns of a tile row in an EGrid80Km grid for a given y (latitude) with a given c offset. */
  def tileRowMaxC(r: Int, cOffset: Int, cScale: Length): (Int, Int) =
  {
    val startC: Int = ife(r %% 4 == 0, 0, 2)
    val hexDelta: Double = cDelta(r, 4, cScale)
    val margin = 15 - hexDelta

    def loop(cAcc: Int): (Int, Int) =
    {
      val newPt = cDelta(r, cAcc, cScale)
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

  /** Copied from Old. This would seem to return the Array that has the irregular HexGrid row specifications. */
  def getBounds(c0Offset: Int, rTileMin: Int, rTileMax: Int, cScale: Length): Array[Int] =
  { val bounds: Array[Int] = new Array[Int]((rTileMax - rTileMin + 2).atLeast0 + 2)
    bounds(0) = ((rTileMax - rTileMin) / 2 + 1).atLeast0
    bounds(1) = rTileMin
    (rTileMin to rTileMax by 2).foreach{ y =>
      val p = (y - rTileMin) + 2
      val pair = tileRowMaxC(y, c0Offset, cScale)
      bounds(p) = pair._1
      bounds(p + 1) = pair._2
    }
    bounds
  }
}