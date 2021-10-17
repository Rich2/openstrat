/* Copyright 2018-21 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package pEarth
import geom._, prid._

object ETile80Km
{
  val scale = 20000.metres * math.sqrt(3)

  /** The key method to get the longitude delta for c based from 0 degs longitude. */
  def hCenToLatLong0(inp: HCen): LatLong =
  { val adj: Pt2 = inp.toPt2
    val p2m: PtMetre2 = adj.toMetres(scale)
    val lat: Double = p2m.y / EarthPolarRadius
    val longDelta: Double =   p2m.x / (EarthEquatorialRadius * math.cos(lat))
    LatLong.radians(lat, longDelta)
  }

  def hCenToLatLong0(r: Int, c: Int): LatLong =
  { val x = c / Sqrt3
    val xm = c * 20000.metres
    val ym = r * 20000.metres * math.sqrt(3)
    val lat: Double = ym / EarthPolarRadius
    val longDelta: Double =   xm / (EarthEquatorialRadius * math.cos(lat))
    LatLong.radians(lat, longDelta)
  }

  //def colToLong(latRadians: Double)
}