/* Copyright 2018-23 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package pEarth; package pAmericas
import geom._, pglobe._, WTile._

object DevonIsland extends EArea2("Devon Island", 73.12 ll -121.13, tundraHills)
{
  val p10 = 75.77 ll -81.09
  val p20 = 75.46 ll -79.62
  val p30 = 74.60 ll -80.30
  val p40 = 74.77 ll -91.96
  val p50 = 76.22 ll -96.93
  val p60 = 77.03 ll -96.44

  override val polygonLL: PolygonLL = PolygonLL(p10, p20, p30, p40, p50, p60)
}

object BanksIsland extends EArea2("Banks Island", 75.15 ll -87.1, tundra)
{ val p0 = 74.28 ll -118
  val p10 = 73.54 ll -115.34
  val p18 = 72.66 ll -119.15
  val p30 = 71.58 ll -120.50
  val south = 71.11 ll -123.06
  val southWest = 71.96 ll -125.81
  val northWest = 74.35 ll -124.70
  val north = 74.56 ll -121.49
  override val polygonLL: PolygonLL = PolygonLL(p0, p10, p18, p30, south, southWest, northWest, north)
}

object MelvilleIsland extends EArea2("Melville Island", 75.43 ll -110.86, tundraHills)
{
  val north = 76.83 ll -108.75
  val northWest = 75.86 ll -105.44
  val southWest = 75.06 ll -105.99
  val south = 74.41 ll -113.00
  val patricSouthWest = 75.98 ll -122.63
  val patricNorthWest = 77.32 ll -119.17

  override val polygonLL: PolygonLL = PolygonLL(north, northWest, southWest, south, patricSouthWest, patricNorthWest)
}

object VictoriaIsland extends EArea2("Victoria Island", 70.65 ll -109.36, tundra)
{ val stefanssonN = 73.75 ll -105.29
  val vic5 = 71.12 ll -104.60
  val vic10 = 70.21 ll -101.34
  val southEast = 69.00 ll -101.79
  val southWest = 68.46 ll -113.21
  val vic30 = 69.22 ll -113.69
  val pointCaen = 69.30 ll -115.95
  val vic40 = 71.60 ll -118.90
  val northWest = 73.36 ll -114.57
  val p10 = 73.00 ll -110.49

  override val polygonLL: PolygonLL = PolygonLL(stefanssonN, vic5, vic10, southEast, southWest, vic30, pointCaen, vic40, northWest, p10)
}

object PrinceWalesIsland extends EArea2("Prince of Wales Island", 72.87 ll -99.13, tundraHills)
{
  val northEast = 73.86 ll -97.20
  val east = 72.43 ll -96.28
  val south = 71.30 ll -98.71
  val west = 72.81 ll -102.71
  val p10 = 73.81 ll -100.99
  override val polygonLL: PolygonLL = PolygonLL(northEast, east, south, west, p10)
}

object SouthamptonIsland extends EArea2("Southampton Island", 64.5 ll -84.35, tundra)
{ val north = 66.02 ll -85.08
  val p20 = 65.27 ll -84.26
  val east = 63.78 ll -80.16
  val p30 = 63.45 ll -81.00
  val p32 = 63.44 ll -80.97
  val south = 63.11 ll -85.46
  val southEast = 63.56 ll -87.13
  val p52 = 64.09 ll -86.18
  val p80 = 65.73 ll -85.96

  override val polygonLL: PolygonLL = PolygonLL(north, p20, east, p30, p32, south, southEast, p52, p80)
}

object BaffinIsland extends EArea2("Baffin Island", 69.55 ll -72.64, tundra)
{
  val p3 = 73.75 ll -84.94
  val p4 = 72.65 ll -86.70
  val p12 = 73.73 ll -82.82
  val bylotNE = 73.67 ll -78.13
  val p20 = 70.54 ll -68.31
  val east = 66.67 ll -61.29
  val southEast = 61.88 ll -65.96
  val p35 = 64.37 ll -74.67
  val p40 = 64.43 ll -78.02
  val p47 = 66.17 ll -74.43
  val p60 = 69.76 ll -77.61
  val p70 = 70.50 ll -88.72
  val p80 = 73.58 ll -88.21
  override def polygonLL: PolygonLL = PolygonLL(p3, p4, p12, bylotNE, p20, east, southEast, p35, p40, p47, p60, p70, p80)
}