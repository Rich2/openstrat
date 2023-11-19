/* Copyright 2018-23 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package pEarth; package pAsia
import geom._, pglobe._, egrid._, WTiles._

/** [[PolygonLL]] graphic for Manchura. */
object Manchuria extends EArea2("Manchuria", 58 ll 128, land)
{val lensk: LatLong = 60.709 ll 114.92

  val udaMouth: LatLong = 54.72 ll 135.28
  val khab10: LatLong = 54.64 ll 136.81
  val khab15: LatLong = 54.28 ll 139.75
  val khab20: LatLong = 53.29 ll 141.42
  val khab30: LatLong = 48.46 ll 140.16
  val primorsky10: LatLong = 45.82 ll 137.68
  val nakhodka: LatLong = 42.69 ll 133.14
  val vladivostok: LatLong = 43.17 ll 132.00
  val jinzhou: LatLong = 40.93 ll 121.22
  val hulunbir: LatLong = 49.265 ll 119.752

  override val polygonLL = LinePathLL(Yakutia.khabarovsk, udaMouth, khab10, khab15, khab20, khab30, primorsky10, nakhodka, vladivostok,
    Korea.northEast, Korea.liaoheMouth,jinzhou, Mongolia.southEast, hulunbir) |++<| LakeBaikal.eastCoast // |++| LinePathLL(Manchuria.lensk)
}

/** [[PolygonLL]] graphic for south east China depends on [[IndoChina]]. */
object Xinjiang extends EArea2("Xinjiang", 42 ll 85, sahel)
{ val south: LatLong = 36.505 ll 80.950
  val east: LatLong = 39.377 ll 75.528

  override val polygonLL: PolygonLL = PolygonLL(Mongolia.west, Mongolia.southWestOffical, Mongolia.southWest, south, east)
}

object Mongolia extends EArea2("Mongolia", 42 ll 115, desert)
{
  val southEast = 41.096 ll 114.088
  val p50: LatLong = 39.860 ll 106.965
  val southWest: LatLong = 40.022 ll 96.864
  val southWestOffical: LatLong = 42.745 ll 96.383
  val west: LatLong = 49.170 ll 87.821

  override val polygonLL: PolygonLL = LakeBaikal.southCoast.reverse |++| LinePathLL(Manchuria.hulunbir, southEast, p50, southWest, southWestOffical, west)
}

/** [[PolygonLL]] graphic for south east China depends on [[IndoChina]]. */
object China extends EArea2("China", 30.0 ll 105.5, hills)
{ val qinhuangdao: LatLong = 39.92 ll 119.61
  val luanheMouth: LatLong = 39.43 ll 119.30
  val huituo: LatLong = 39.19 ll 118.98
  val duliujianMouth: LatLong = 38.767 ll 117.582
  val p5: LatLong = 38.15 ll 118.81
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

  override val polygonLL: PolygonLL = PolygonLL(Manchuria.jinzhou, qinhuangdao, luanheMouth, huituo, duliujianMouth, p5, jiolaiMouth, p10, rongcheng, p12, haitzhou, p15, p18, putuo, longhai, hongKong, xuwen,
    yingzaiMouth, IndoChina.beilunMouth, IndoChina.chittagong, India.magdhara, India.indiaNE, Mongolia.southWest, Mongolia.p50, Mongolia.southEast)
}

/** [[polygonLL]] graphical representation of Korea.Depends on nothing. */
object Korea extends EArea2("Korea", 37.77 ll 127.55, hills)
{ val northEast: LatLong = 41.49 ll 129.65
  val kaima: LatLong = 40.84 ll 129.71
  val hwaDo: LatLong =39.76 ll 127.54
  val kaigochiRi: LatLong = 39.31 ll 127.57
  val p15: LatLong = 39.30 ll 127.39
  val p19: LatLong = 39.19 ll 127.41
  val p20: LatLong = 39.13 ll 127.74
  val koreaE: LatLong = 37.06 ll 129.40
  val busan: LatLong = 35.19 ll 129.19
  val jindo: LatLong = 34.39 ll 126.14
  val ryongyon: LatLong = 38.12 ll 124.78
  val taeryongMouth: LatLong = 39.49 ll 125.31
  val dalianSouth: LatLong = 38.76 ll 121.16
  val p80: LatLong = 39.53 ll 121.23
  val xianshuiMouth: LatLong = 40.48 ll 122.28
  val liaoheMouth: LatLong = 40.95 ll 121.82

  override val polygonLL = PolygonLL(northEast, kaima, hwaDo, kaigochiRi, p15, p19, p20, koreaE, busan, jindo, ryongyon, taeryongMouth, dalianSouth,
    p80, xianshuiMouth, liaoheMouth)
}