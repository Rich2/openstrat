/* Copyright 2018-24 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package pEarth; package pAsia
import geom._, pglobe._, egrid._, WTiles._

/** [[PolygonLL]] graphic for south east China depends on [[IndoChina]]. */
object ChinaNorth extends EArea2("China north", 40.0 ll 105.5, hillySteppe)
{ val qinhuangdao: LatLong = 39.92 ll 119.61
  val luanheMouth: LatLong = 39.43 ll 119.30
  val huituo: LatLong = 39.19 ll 118.98
  val duliujianMouth: LatLong = 38.767 ll 117.582
  val p5: LatLong = 38.15 ll 118.81
  val jiolaiMouth: LatLong = 37.11 ll 119.57
  val p10: LatLong = 37.82 ll 120.75
  val p12: LatLong = 36.90 ll 122.52
  val rongcheng: LatLong = 37.39 ll 122.69
  val p90: LatLong = 35.095 ll 119.385

  override val polygonLL: PolygonLL = PolygonLL(Manchuria.jinzhou, qinhuangdao, luanheMouth, huituo, duliujianMouth, p5, jiolaiMouth, p10, rongcheng, p12, p90,
    China.haitzhou, China.northWest, Mongolia.south, Mongolia.p50, Mongolia.southEast)
}

/** [[PolygonLL]] graphic for south east China depends on [[IndoChina]]. */
object China extends EArea2("China", 30.0 ll 105.5, hillySub)
{ val haitzhou: LatLong = 34.95 ll 119.20
  val p15: LatLong = 34.30 ll 120.28
  val p18 : LatLong = 31.71 ll 121.98
  val putuo: LatLong = 29.9 ll 122.34
  val p25: LatLong = 28.259 ll 121.620
  val suanyu: LatLong = 25.472 ll 119.875
  val longhai: LatLong = 24.26 ll 118.14
  val dashwei: LatLong = 22.938 ll 116.502
  val hongKong: LatLong = 22.44 ll 114.16
  val wuchuan: LatLong = 21.381 ll 110.750
  val p40: LatLong = 20.470 ll 110.535
  val xuwen: LatLong = 20.24 ll 110.18
  val yingzaiMouth: LatLong = 21.45 ll 109.90
  val baihai: LatLong = 21.444 ll 109.053
  val liuMouth: LatLong = 21.605 ll 109.037

  val northWest: LatLong = 34.742 ll 106.413

  override val polygonLL: PolygonLL = PolygonLL(
    haitzhou, p15, p18, putuo, p25, suanyu, longhai, dashwei, hongKong, wuchuan, p40, xuwen, yingzaiMouth, baihai, liuMouth, IndoChina.beilunMouth,
    IndoChina.north, Yunnan.northEast, northWest)
}

/** [[PolygonLL]] graphic for south east China depends on [[IndoChina]]. */
object Yunnan extends EArea2("Yunnan", 30.0 ll 105.5, mtainSavannah)
{
  val northEast: LatLong = 29.546 ll 103.475
  val northWest: LatLong = 27.499 ll 97.887
  override val polygonLL: PolygonLL = PolygonLL(northEast, IndoChina.north, IndoChina.northWest, northWest)
}

/** [[PolygonLL]] graphic for Hainan, depends on nothing. */
object Hainan extends EArea2("Hainan", 30.0 ll 105.5, hillySub)
{
  val north: LatLong = 20.150 ll 110.685
  val east = 19.641 ll 111.046
  val dongzhou: LatLong = 18.182 ll 109.702
  val southWest: LatLong = 18.504 ll 108.691
  val p75: LatLong = 19.371 ll 108.68
  val p85: LatLong = 19.913 ll 109.303
  override val polygonLL: PolygonLL = PolygonLL(north, east, dongzhou, p75, p85)
}

/** [[PolygonLL]] graphic for Taiwan depends on nothing. */
object Taiwan extends EArea2("Taiwan", 23.85 ll 120.99, hillyOce)
{ val north: LatLong = 25.29 ll 121.57
  val northEast: LatLong = 25.01 ll 122.01
  val south: LatLong = 21.90 ll 120.86
  val west: LatLong = 23.10 ll 120.04
  val p10: LatLong = 25.05 ll 121.06

  override val polygonLL: PolygonLL = PolygonLL(north, northEast, south, west, p10)
}