/* Copyright 2018-21 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package egrid
import geom._, pglobe._, prid._

/** A hex grid on the surface of the earth. */
abstract class EGrid(bottomTileRow: Int, numTileRows: Int, unsafeRowsArray: Array[Int], val cScale: Length) extends HGridIrrRows(bottomTileRow, numTileRows, unsafeRowsArray)
{
  /** The scale of the c or column coordinate in metres. */

}

/** One of the main hex grids for the earth not a polar grid. */
abstract class EGridMain(bottomTileRow: Int, numTileRows: Int, val cenLong: Longitude, cScale: Length, val rOffset: Int,
  val cOffset: Int) extends EGrid(bottomTileRow, numTileRows, EGridMain.getBounds(bottomTileRow, numTileRows, rOffset, cOffset, cScale), cScale)


/** Functions for Earth tile grids. */
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
  { val adj: Pt2 = inp/*.subR(300)*/.toPt2
    val d2: PtM2 = adj.toMetres(cScale)
    val d2x = d2.x
    val latRadians: Double = d2.y / EarthPolarRadius
    val latcos = math.cos(latRadians)
    val longDelta: Double =   d2x / (EarthEquatorialRadius * latcos)
    LatLong.radians(latRadians, longDelta)
  }

  /** Returns the longitudinal delta for a given c at a given y (latitude) for an EGrid80Km Hex Grid. */
  def cDelta(r: Int, c: Int, cScale: Length): Double = {
    val ll = hCoordToLatLong0(HCoord(r, c), cScale)
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
      val newPt: Double = cDelta(r - rOffset, cAcc, cScale)
      val overlapRatio = (newPt - margin) / hexDelta
      val res = newPt match {
        case pt if (pt < margin) => loop(cAcc + 4)
        case pt if overlapRatio < 0.5 => (-cAcc, cAcc)
        case pt => (4 - cAcc, cAcc)
      }
      res
    }
    val (neg, pos) = loop(startC)
    (neg + cOffset , pos + cOffset)
  }

  /** Copied from Old. This would seem to return the Array that has the irregular HexGrid row specifications. */
  def getBounds(rTileMin: Int, rTileMax: Int, rOffset: Int, c0Offset: Int, cScale: Length): Array[Int] =
  { deb("Get Bounds")
    val bounds: Array[Int] = new Array[Int]((rTileMax - rTileMin + 2).max0)// + 2)
    deb("Get bounds 2")
    iToForeach(rTileMin, rTileMax, 2){ r =>
      val p = (r - rTileMin)// + 2
      val pair = tileRowMinMaxC(r, c0Offset, rOffset, cScale)
      bounds(p) = pair._1//(((pair._2 - pair._1)/ 4) + 1).max0
      bounds(p + 1) = ((pair._2 - pair._1 + 4)/ 4).max0//pair._1
    }
    deb("Get bounds 4")
    bounds
  }
}