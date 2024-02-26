/* Copyright 2018-23 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package pEarth; package pAsia
import geom._, pglobe._, egrid._, WTiles._

/** [[PolygonLL]] graphic for Taiwan depends on nothing. */
object Taiwan extends EArea2("Taiwan", 23.85 ll 120.99, hillyOce)
{ val north: LatLong = 25.29 ll 121.57
  val northEast: LatLong = 25.01 ll 122.01
  val south: LatLong = 21.90 ll 120.86
  val west: LatLong = 23.10 ll 120.04
  val p10: LatLong = 25.05 ll 121.06

  override val polygonLL: PolygonLL = PolygonLL(north, northEast, south, west, p10)
}


/** [[PolygonLL]] graphic for IndoChina depends on [[MalayPeninsula]]. */
object IndoChina extends EArea2("IndoChina", 16.11 ll 103.75, jungle)
{ val beilunMouth: LatLong = 21.50 ll 108.08
  val dienChau: LatLong = 18.99 ll 105.56
  val p25: LatLong = 15.243 ll 108.939
  val eVietnam: LatLong = 12.93 ll 109.37
  val p30: LatLong = 10.142 ll 106.796
  val sVietnam: LatLong = 8.68 ll 104.92
  val p40 = 9.850 ll 104.900
  val khaoLaemPu: LatLong = 12.648 ll 100.852
  val bankok: LatLong = 13.59 ll 100.39

  val lethabaukMau: LatLong = 13.553 ll 98.121
  val sittangMouth: LatLong = 17.36 ll 96.89
  val p80: LatLong = 15.733 ll 95.429
  val pathein: LatLong = 16.17 ll 94.31
  val sonadia: LatLong = 21.538 ll 91.841
  val chittagong: LatLong = 22.74 ll 91.54

  override val polygonLL: PolygonLL = PolygonLL(beilunMouth, dienChau, p25, eVietnam, p30, sVietnam, p40, khaoLaemPu, bankok, MalayPeninsula.maeKongMouth,
    MalayPeninsula.northWest, lethabaukMau, sittangMouth, p80, pathein, sonadia, chittagong)
}

/** [[PolygonLL]] graphic for Maly Peninsula depends on nothing. */
object MalayPeninsula extends EArea2("MaylayPeninsula", 4.84 ll 102, hillyJungle)
{ val maeKongMouth: LatLong = 13.358 ll 100.009
  val p15: LatLong = 12.187 ll 100.018
  val tongNode = 9.303 ll 99.837
  val khaeKhae = 6.845 ll 101.557

  val seMalaysia: LatLong = 1.39 ll 104.25
  val swMalaysia: LatLong = 1.32 ll 103.47
  val selekoh: LatLong = 3.89 ll 100.73
  val capeKrathing: LatLong = 7.775 ll 98.288
  val northWest: LatLong = 13.569 ll 98.373

  override val polygonLL: PolygonLL = PolygonLL(maeKongMouth, p15, tongNode, khaeKhae, seMalaysia, swMalaysia, selekoh, capeKrathing, northWest)
}