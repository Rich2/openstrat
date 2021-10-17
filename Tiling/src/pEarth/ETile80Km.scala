/* Copyright 2018-21 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package pEarth
import geom._, prid._

object ETile80Km
{
  val scale = 20000.metres * math.sqrt(3)

  /** The key method to get the longitude delta for x based from 0 degs longitude. */
  def hCenToLatLong0(inp: HCen): LatLong =
  { val adj: Pt2 = inp.toPt2// HexGridOld.roordToVec2(inp.subYC(300, 0))
    val d2: PtMetre2 = adj.toMetres(scale)
    val lat: Double = d2.y / EarthPolarRadius
    val longDelta: Double =   d2.x / (EarthEquatorialRadius * math.cos(lat))
    LatLong.radians(lat, longDelta)
  }
}
