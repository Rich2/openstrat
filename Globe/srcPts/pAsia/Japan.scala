/* Copyright 2018-23 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package pEarth
import geom._, pglobe._, LatLong._, WTile._

object sakhalin extends EArea2("Sakhalin", 50.94 ll 142.90, taiga)
{ val sakhalinN = 54.38 ll 142.73
  val sakhalinW = 49.07 ll 144.37
  val poronayask = 49.21 ll 143.09
  val sakhalinS = 45.89 ll 142.08
  val pogibi = 52.22 ll 141.64
  val sakhalinNE = 53.39 ll 141.70

  override val polygonLL = PolygonLL(sakhalinN, sakhalinW, poronayask, sakhalinS, pogibi, sakhalinNE)
}

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