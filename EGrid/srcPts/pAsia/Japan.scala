/* Copyright 2018-24 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package pEarth
import geom._, pglobe._, egrid._, WTiles._

object sakhalin extends EarthArea("Sakhalin", 50.94 ll 142.90, taiga)
{ val north: LatLong = 54.38 ll 142.73
  val p6: LatLong = 52.832 ll 143.334
  val p15: LatLong = 52.159 ll 143.139
  val west: LatLong = 49.07 ll 144.37
  val poronayask: LatLong = 49.21 ll 143.09
  val p45: LatLong = 46.847 ll 143.433
  val southEast: LatLong = 46.024 ll 143.415
  val p55: LatLong = 46.735 ll 142.707

  val south: LatLong = 45.89 ll 142.08
  val p77: LatLong = 48.767 ll 141.848
  val p82: LatLong = 50.541 ll 142.047
  val pogibi: LatLong = 52.22 ll 141.64
  val northEast: LatLong = 53.39 ll 141.70

  override val polygonLL: PolygonLL = PolygonLL(north, p6, p15, west, poronayask, p45, southEast, p55, south, p77, p82, pogibi, northEast)
}

object Hokkaido extends EarthArea("Hokkaido", 43.50 ll 142.95, hillyOce)
{ val north: LatLong = 45.5 ll 141.93
  val p10: LatLong = 43.931 ll 144.790
  val p20: LatLong = 44.34 ll 145.33
  val east: LatLong = 43.36 ll 145.74
  val southEast: LatLong = 41.94 ll 143.24
  val capeChikyu: LatLong = 42.30 ll 141.00
  val p60: LatLong = 41.809 ll 141.180
  val south: LatLong = 41.40 ll 140.20
  val p80: LatLong = 41.59 ll 139.98
  val capeKamui: LatLong = 43.332 ll 140.339
  val p85: LatLong = 43.724 ll 141.330
  val northWest: LatLong = 45.210 ll 141.565

  override val polygonLL: PolygonLL = PolygonLL(north, p10, p20, east, southEast, capeChikyu, p60, south, p80, capeKamui, p85, northWest)
}

object Honshu extends EarthArea("Honshu", 36.28 ll 138.71, hillyOce)
{ val oma: LatLong = 41.49 ll 140.94
  val p5: LatLong = 41.43 ll 141.46
  val p10: LatLong = 39.55 ll 142.06

  val p15: LatLong = 38.28 ll 141.52
  val naruseMouth: LatLong = 38.38 ll 141.18
  val takaseMouth: LatLong = 37.49 ll 141.04
  val p16: LatLong = 37.00 ll 140.97
  val choshi: LatLong = 35.71 ll 140.85
  val p18: LatLong = 34.92 ll 139.93
  val p22: LatLong = 34.61 ll 138.84
  val p24: LatLong = 34.28 ll 136.90
  val p25: LatLong = 34.18 ll 136.33

  val capeKute: LatLong = 33.44 ll 135.76
  val capeAhizuri: LatLong = 32.72 ll 133.02
  val p45: LatLong = 33.93 ll 131.27

  val p60: LatLong = 35.42 ll 132.63
  val p65: LatLong = 35.78 ll 135.22
  val p68: LatLong = 35.60 ll 135.46
  val p70: LatLong = 37.33 ll 136.73
  val p72: LatLong = 37.51 ll 137.34
  val kashiwazaki: LatLong = 37.37 ll 138.55
  val p85: LatLong = 37.79 ll 138.82
  val p90: LatLong = 40.62 ll 139.89
  val capeTappi: LatLong = 41.26 ll 140.34

  override val polygonLL: PolygonLL = PolygonLL(oma, p5, p10, p15, naruseMouth, takaseMouth, p16, choshi, p18, p22, p24, p25, capeKute, capeAhizuri, p45, p60,
    p65, p68, p70, p72, kashiwazaki, p85, p90, capeTappi)
}

object Kyshu extends EarthArea("Kyushu", 32.80 ll 131.02, hillyOce)
{ val north: LatLong = 33.95 ll 130.83
  val northWest: LatLong = 33.69 ll 131.59
  val sKyshu: LatLong = 31.08 ll 130.75
  val p30: LatLong = 32.80 ll 131.97
  val neKyushu: LatLong = 33.34 ll 129.45

  override val polygonLL: PolygonLL = PolygonLL(north, northWest, p30, sKyshu, neKyushu)
}