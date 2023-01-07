/* Copyright 2018-23 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package pEarth
import geom._, pglobe._, LatLong._, WTile._

object Hokkaido extends EArea2("Hokkaido", 43.50 ll 142.95, hills)
{ val north = 45.5 ll 141.93
  val east = 43.36 ll 145.74
  val southEast = 41.94 ll 143.24
  val capeChikyu = 42.30 ll 141.00
  val south = 41.40 ll 140.20
  val p10 = 41.59 ll 139.98
  override val polygonLL = PolygonLL(north, east, southEast, capeChikyu, south, p10)
}

object japan extends EArea2("Japan", degs(36.28, 138.71), hills)
{ val capeTappi = 41.26 ll 140.34
  val sKyshu = 31.08 ll 130.75
  val capeKute = 33.44 ll 135.76
  val p10 = 32.80 ll 131.97
  val neKyushu = 33.34 ll 129.45
  val kashiwazaki = 37.37 ll 138.55
  val choshi = 35.71 ll 140.85

  override val polygonLL = PolygonLL(capeTappi, choshi, capeKute, p10, sKyshu, neKyushu, kashiwazaki)
}

object Taiwan extends EArea2("Taiwan", 23.85 ll 120.99, hills)
{ val north = 25.29 ll 121.57
  val northEast = 25.01 ll 122.01
  val south = 21.90 ll 120.86
  val west = 23.10 ll 120.04
  val p10 = 25.05 ll 121.06

  override val polygonLL = PolygonLL(north, northEast, south, west, p10)
}