/* Copyright 2018-24 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package pEarth; package pEurope
import geom._, pglobe._, egrid._, WTiles._

/** [[PolygonLL]] graphic for Crimea depends on nothing. */
object Crimea extends EArea2("Crimea", 45.33 ll 34.15, oceanic)
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

  override val polygonLL: PolygonLL = PolygonLL(AzovSea.henichesk, AzovSea.kamyanske, AzovSea.straitsWest, kerch, southEast, p40, south, p60, p65, zaozerne,
    west, p80, northWest)
}

/** [[PolygonLL]] graphic for Ukraine depends on [[AzovSea]], [[Baltland]], [[Crimea]] [[BalkansEast]] and [[Polandia]]. */
object Ukraine extends EArea2("Ukraine", 49 ll 34, oceanic)
{
  val koblev: LatLong = 46.63 ll 31.18

  override val polygonLL: PolygonLL = PolygonLL(Baltland.voronezh, AzovSea.northEast, AzovSea.henichesk, Crimea.northWest, koblev, BalkansEast.odessa,
    Polandia.cenEast)
}

/** [[PolygonLL]] graphic for the Sea of Azov, depends on nothing. */
object AzovSea extends EArea2("AzovSea", 46.13 ll 36.80, sea)
{ val northEast: LatLong = 47.28 ll 39.20
  val zaymoObryv: LatLong = 47.02 ll 39.29
  val dolzhanskaya: LatLong = 46.67 ll 37.75
  val pereprava: LatLong = 46.266 ll 38.284
  val azovSE: LatLong = 45.35 ll 37.46
  val llich: LatLong = 45.41 ll 36.76
  val straitsEast: LatLong = 45.44 ll 36.79
  val straitsWest: LatLong = 45.44 ll 36.60
  val kamyanske: LatLong = 45.28 ll 35.53
  val henichesk: LatLong = 46.17 ll 34.82

  val eastCoast: LinePathLL = LinePathLL(northEast, zaymoObryv, dolzhanskaya, pereprava, azovSE, llich, straitsEast)

  override val polygonLL: PolygonLL = eastCoast |++| LinePathLL(straitsWest, kamyanske, henichesk)
}

/** [[PolygonLL]] graphic for South Russia, depends on [[AzovSea]] and [[Ukraine]]. */
object VolgaRegion extends EArea2("Volga Region", 45.00 ll 42.57, steppe)
{ override val polygonLL: PolygonLL = LinePathLL(pAsia.RusNorth.p50) ++< Caspian.volgaCoast |++| LinePathLL(AzovSea.northEast, Baltland.voronezh,
    Baltland.southEast)
}

/** [[PolygonLL]] graphic for South Russia, depends on [[AzovSea]] and [[Ukraine]]. */
object RussiaSouth extends EArea2("Russia South", 45.00 ll 42.57, oceanic)
{ val blackSeaE: LatLong = 41.84 ll 41.77
  val p60: LatLong = 42.74 ll 41.44
  val bzipiMouth: LatLong = 43.187 ll 40.280
  val mzymtaMouth: LatLong = 43.415 ll 39.923
  val p70: LatLong = 44.53 ll 38.09
  val p72: LatLong = 44.95 ll 37.29
  val p75: LatLong = 45.11 ll 36.73
  val p77: LatLong = 45.20 ll 36.60

  override val polygonLL: PolygonLL = LinePathLL(AzovSea.northEast) ++< Caspian.russiaSouthCost ++ LinePathLL(blackSeaE, p60, bzipiMouth, mzymtaMouth, p70, p72,
    p75, p77) |++<| AzovSea.eastCoast.tail
}

/** [[PolygonLL]] graphic for Caspian Sea depends on nothing. */
object Caspian extends EArea2("Caspian Sea", 42.10 ll 50.64, lake)
{ val north: LatLong = 47.05 ll 51.36
  val p4: LatLong = 46.782 ll 52.268
  val northEast: LatLong = 46.66 ll 53.03
  val p10: LatLong = 45.860 ll 53.064
  val east1: LatLong = 45.247 ll 54.951
  val mangystau: LatLong = 45.48 ll 52.78
  val p20: LatLong = 45.411 ll 51.767
  val p22: LatLong = 44.644 ll 50.314
  val bautino: LatLong = 44.53 ll 50.24
  val p25: LatLong = 43.155 ll 51.274
  val kendirliBay: LatLong = 42.73 ll 52.74
  val persiaN: LatLong = 38.86 ll 53.99
  val kazakCoast: LinePathLL = LinePathLL(north, p4, northEast, p10, east1, mangystau, p20, p22, bautino, p25, kendirliBay, persiaN)

  val southEast: LatLong = 36.92 ll 54.03
  val p50: LatLong = 36.576 ll 51.835
  val p51: LatLong = 37.142 ll 50.311
  val p52: LatLong = 37.383 ll 50.201
  val southWest: LatLong = 37.41 ll 50.03
  val persianCoast: LinePathLL = LinePathLL(persiaN, southEast, p50, p51, p52, southWest)

  val sangachal: LatLong = 40.18 ll 49.47
  val baku: LatLong = 40.44 ll 50.21
  val sumqayit: LatLong = 40.635 ll 49.553
  val armeniaCoast = LinePathLL(southWest, sangachal, baku, sumqayit)


  val p75: LatLong = 43.982 ll 47.862
  val p77: LatLong = 44.001 ll 47.372
  val west: LatLong = 44.53 ll 46.65
  val russiaSouthCost: LinePathLL = LinePathLL(sumqayit, p75, p77, west)

  val p90: LatLong = 45.634 ll 48.697
  val volodarsky: LatLong = 46.39 ll 49.03

  val volgaCoast: LinePathLL = LinePathLL(west, p90, volodarsky, north)

  override val polygonLL: PolygonLL = kazakCoast.init ++ persianCoast.init ++ armeniaCoast.init ++ russiaSouthCost.init |++| volgaCoast.init
}