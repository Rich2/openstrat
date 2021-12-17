/* Copyright 2018-21 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package egrid
import geom._, pglobe._, prid._

/** One of the main hex grids for the earth not a polar grid.  ((rTopCen - rBottomCen + 2) / 2).max0 */
abstract class EGridMain(rBottomCen: Int, rTopCen: Int, val cenLong: Longitude, cScale: Length, val rOffset: Int,
  val cOffset: Int) extends EGrid(rBottomCen, EGridMain.getBounds(rBottomCen, rTopCen, rOffset, cOffset, cScale), cScale)
{
  def hCoordLL(hc: HCoord): LatLong = EGridMain.hCenToLatLong0(hc.r - rOffset, hc.c - cOffset, 0, cScale)
}

/** Functions for Earth tile grids. */
object EGridMain
{ /** The key method to get the longitude delta for c based from 0° longitude. */
  def hCenToLatLong0(inp: HCoord, cOffset: Int, cScale: Length): LatLong = hCenToLatLong0(inp.r, inp.c, cOffset, cScale)

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

  def hCoordToLatLong0(r: Int, c: Int, cScale: Length): LatLong = hCoordToLatLong0(HCoord(r, c), cScale)

  /** Copied from pGrid. The key method to get the longitude delta for x based from 0 degs longitude. */
  def hCoordToLatLong0(inp: HCoord, cScale: Length): LatLong =
  { val adj: Pt2 = inp.toPt2
    val d2: PtM2 = adj.toMetres(cScale)
    val latRadians: Double = d2.y / EarthPolarRadius
    val latcos = math.cos(latRadians)
    val longDelta: Double =   d2.x / (EarthEquatorialRadius * latcos)
    LatLong.radians(latRadians, longDelta)
  }

  /** Returns the longitudinal delta for a given c at a given y (latitude) for an EGrid80Km Hex Grid. */
  def cDelta(r: Int, c: Int, cScale: Length): Double = {
    val ll = hCenToLatLong0(HCoord(r, c), 0, cScale)
    ll.longDegs
  }

  /** Returns the min and max columns of a tile row in an EGrid80Km grid for a given y (latitude) with a given c offset. */
  def tileRowMinMaxC(r: Int, rOffset: Int, cOffset: Int, cScale: Length): (Int, Int) =
  {
    val startC: Int = ife(r %% 4 == 0, 0, 2)
    val hexDelta: Double = cDelta(r - rOffset, 4, cScale)
    val margin = 15 - hexDelta

    def loop(cAcc: Int): (Int, Int) =
    {
      val longDegsAcc: Double = cDelta(r - rOffset, cAcc, cScale)
      val overlapRatio = (longDegsAcc - margin) / hexDelta
      val res: (Int, Int) = longDegsAcc match {
        case lds if (lds < margin) => loop(cAcc + 4)
        case _ if overlapRatio < 0.5 => (-cAcc, cAcc)
        case _ => (4 - cAcc, cAcc)
      }
      res
    }
    val (neg, pos) = loop(startC)
    (neg + cOffset , pos + cOffset)
  }

  /** Copied from Old. This would seem to return the Array that has the irregular HexGrid row specifications. */
  def getBounds(rTileMin: Int, rTileMax: Int, rOffset: Int, c0Offset: Int, cScale: Length): Array[Int] =
  { val bounds: Array[Int] = new Array[Int]((rTileMax - rTileMin + 2).max0)
    iToForeach(rTileMin, rTileMax, 2){ r =>
      val p = (r - rTileMin)
      val pair = tileRowMinMaxC(r, rOffset, c0Offset, cScale)
      bounds(p) = ((pair._2 - pair._1 + 4)/ 4).max0
      bounds(p + 1) = pair._1
    }
    bounds
  }
}