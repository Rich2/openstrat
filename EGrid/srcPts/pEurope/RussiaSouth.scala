/* Copyright 2018-23 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package pEarth; package pEurope
import geom._, pglobe._, egrid._, WTile._

/** [[PolygonLL]] graphic for Crimea depends on nothing. */
object Crimea extends EArea2("Crimea", 45.33 ll 34.15, plain)
{ val kerch: LatLong = 45.39 ll 36.63
  val southEast: LatLong = 45.10 ll 36.45
  val p40: LatLong = 44.79 ll 35.08
  val south: LatLong = 44.39 ll 33.74
  val p60: LatLong = 44.58 ll 33.38
  val p65: LatLong = 45.01 ll 33.55
  val zaozerne: LatLong = 45.15 ll 33.27
  val west: LatLong = 45.40 ll 32.48
  val p80: LatLong = 45.93 ll 33.76
  val northWest: LatLong = 46.16 ll 33.59

  override val polygonLL: PolygonLL = PolygonLL(AzovSea.henichesk, AzovSea.kamyanske, AzovSea.straitsWest, kerch, southEast, p40, south, p60, p65,
    zaozerne,  west, p80, northWest)
}

/** [[PolygonLL]] graphic for Ukraine depends on [[AzovSea]], [[Baltland]], [[Crimea]] [[BalkansEast]] and [[Polandia]]. */
object Ukraine extends EArea2("Ukraine", 49 ll 34, plain)
{ val caspianW: LatLong = 44.53 ll 46.65
  val koblev: LatLong = 46.63 ll 31.18

  override val polygonLL: PolygonLL = PolygonLL(Baltland.southEast, caspianW, AzovSea.northEast, AzovSea.henichesk, Crimea.northWest, koblev,
    BalkansEast.odessa, Polandia.cenEast)
}

/** [[PolygonLL]] graphic for the Sea of Azov, depends on nothing. */
object AzovSea extends EArea2("AzovSea", 46.13 ll 36.80, sea)
{ val northEast = 47.28 ll 39.20
  val zaymoObryv = 47.02 ll 39.29
  val azovSE: LatLong = 45.35 ll 37.46
  val dolzhanskaya = 46.67 ll 37.75
  val llich: LatLong = 45.41 ll 36.76
  val straitsEast = 45.44 ll 36.79
  val straitsWest = 45.44 ll 36.60
  val kamyanske = 45.28 ll 35.53
  val henichesk = 46.17 ll 34.82

  override val polygonLL: PolygonLL = PolygonLL(northEast, zaymoObryv, dolzhanskaya, azovSE, llich, straitsEast, straitsWest, kamyanske, henichesk)
}

/** [[PolygonLL]] graphic for South Russia, depends on [[AzovSea]] and [[Ukraine]]. */
object RussiaSouth extends EArea2("RussiaSouth", 45.00 ll 42.57, plain)
{ val p10: LatLong = 43.87 ll 47.44
  val sumqayit: LatLong = 40.64 ll 49.55
  val blackSeaE: LatLong = 41.84 ll 41.77
  val p60: LatLong = 42.74 ll 41.44
  val p70: LatLong = 44.53 ll 38.09
  val p72: LatLong = 44.95 ll 37.29
  val p75: LatLong = 45.11 ll 36.73
  val p77: LatLong = 45.20 ll 36.60

  override val polygonLL: PolygonLL = PolygonLL(AzovSea.northEast, Ukraine.caspianW, p10, sumqayit, blackSeaE, p60, p70, p72, p75, p77,
    AzovSea.straitsEast, AzovSea.llich, AzovSea.azovSE, AzovSea.dolzhanskaya, AzovSea.zaymoObryv)
}