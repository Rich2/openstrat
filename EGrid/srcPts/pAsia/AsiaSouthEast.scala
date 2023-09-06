/* Copyright 2018-23 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package pEarth; package pAsia
import geom._, pglobe._, egrid._, WTile._

/** [[PolygonLL]] graphic for Taiwan depends on nothing. */
object Taiwan extends EArea2("Taiwan", 23.85 ll 120.99, hills)
{ val north = 25.29 ll 121.57
  val northEast = 25.01 ll 122.01
  val south = 21.90 ll 120.86
  val west = 23.10 ll 120.04
  val p10 = 25.05 ll 121.06

  override val polygonLL = PolygonLL(north, northEast, south, west, p10)
}

/** [[PolygonLL]] graphic for south east China depends on [[IndoChina]]. */
object ChinaSE extends EArea2("ChinaSE", 30.0 ll 105.5, plain)
{ val p5: LatLong = 38.15 ll 118.81
  val jiolaiMouth: LatLong = 37.11 ll 119.57
  val p10: LatLong = 37.82 ll 120.75
  val p12: LatLong = 36.90 ll 122.52
  val rongcheng: LatLong = 37.39 ll 122.69
  val haitzhou: LatLong = 34.95 ll 119.20
  val p15: LatLong = 34.30 ll 120.28
  val p18 : LatLong = 31.71 ll 121.98
  val putuo: LatLong = 29.9 ll 122.34
  val longhai: LatLong = 24.26 ll 118.14
  val hongKong: LatLong = 22.44 ll 114.16
  val xuwen: LatLong = 20.24 ll 110.18
  val yingzaiMouth: LatLong = 21.45 ll 109.90

  override val polygonLL = PolygonLL(CEAsia.binhai, p5, jiolaiMouth, p10, rongcheng, p12, haitzhou, p15, p18, putuo, longhai, hongKong, xuwen,
    yingzaiMouth, IndoChina.beilunMouth, IndoChina.chittagong, India.magdhara, India.indiaNE,  CentralAsia.southEast)
}

/** [[PolygonLL]] graphic for IndoChina depends on nothing. */
object IndoChina extends EArea2("IndoChina", 16.11 ll 103.75, jungle)
{ val beilunMouth: LatLong = 21.50 ll 108.08
  val eVietnam: LatLong = 12.93 ll 109.37
  val dienChau: LatLong = 18.99 ll 105.56
  val sVietnam: LatLong = 8.68 ll 104.92
  val bankok: LatLong = 13.59 ll 100.39
  val seMalaysia: LatLong = 1.39 ll 104.25
  val swMalaysia: LatLong = 1.32 ll 103.47
  val selekoh: LatLong = 3.89 ll 100.73
  val neMalayPen: LatLong = 13.48 ll 98.45
  val sittangMouth: LatLong = 17.36 ll 96.89
  val pathein: LatLong = 16.17 ll 94.31
  val chittagong: LatLong = 22.74 ll 91.54

  override val polygonLL = PolygonLL(beilunMouth, dienChau, eVietnam, sVietnam, bankok, seMalaysia, swMalaysia, selekoh, neMalayPen, sittangMouth,
    pathein, chittagong)
}