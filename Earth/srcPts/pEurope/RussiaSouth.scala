/* Copyright 2018-23 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package pEarth; package pEurope
import geom._, pglobe._, WTile._

/** [[PolygonLL]] graphic for Crimea depends on nothing. */
object Crimea extends EArea2("Crimea", 45.33 ll 34.15, plain)
{ val henichesk = 46.17 ll 34.82
  val kamyanske = 45.28 ll 35.53
  val kerch = 45.39 ll 36.63
  val southEast = 45.10 ll 36.45
  val p40 = 44.79 ll 35.08
  val south = 44.39 ll 33.74
  val p60 = 44.58 ll 33.38
  val west = 45.40 ll 32.48
  val p80 = 45.93 ll 33.76
  val northWest = 46.16 ll 33.59
  val polygonLL: PolygonLL = PolygonLL(henichesk, kamyanske, kerch, southEast, p40, south, p60, west, p80, northWest)
}

/** [[PolygonLL]] graphic for Ukraine depends on [[Baltland]], [[Crimea]] [[BalkansEast]] and [[Polandia]]. */
object Ukraine extends EArea2("Ukraine", 49 ll 34, plain)
{ val caspianW = 44.53 ll 46.65
  val rostov = 47.17 ll 39.29
  val koblev = 46.63 ll 31.18

  override val polygonLL: PolygonLL = PolygonLL(Baltland.southEast, caspianW, rostov, Crimea.henichesk, Crimea.northWest, koblev,
    BalkansEast.odessa, Polandia.cenEast)
}

/** [[PolygonLL]] graphic for South Russia, depends on [[Ukraine]]. */
object RussiaSouth extends EArea2("RussiaSouth", 45.00 ll 42.57, desert)
{ val p10 = 43.87 ll 47.44
  val sumqayit = 40.64 ll 49.55
  val blackSeaE = 41.84 ll 41.77
  val p60 = 42.74 ll 41.44
  val p70 = 44.53 ll 38.09
  val p72 = 44.95 ll 37.29
  val p77 = 45.20 ll 36.60
  val p75 = 45.11 ll 36.73
  val llich = 45.41 ll 36.76

  override val polygonLL: PolygonLL = PolygonLL(Ukraine.rostov, Ukraine.caspianW, p10, sumqayit, blackSeaE, p60, p70, p72, p75, p77, llich
  )
}