/* Copyright 2018-23 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package pEarth; package pAsia
import geom._, pglobe._, egrid._, WTiles._

/** [[PolygonLL]] graphic for Manchura. */
object Manchuria extends EArea2("Manchuria", 58 ll 128, oceanic)
{ val udaMouth: LatLong = 54.72 ll 135.28
  val khab10: LatLong = 54.64 ll 136.81
  val khab15: LatLong = 54.28 ll 139.75
  val khab20: LatLong = 53.29 ll 141.42
  val p25: LatLong = 51.956 ll 141.418
  val p28: LatLong = 50.095 ll 140.680
  val khab30: LatLong = 48.46 ll 140.16
  val primorsky10: LatLong = 45.82 ll 137.68
  val nakhodka: LatLong = 42.69 ll 133.14
  val vladivostok: LatLong = 43.17 ll 132.00
  val jinzhou: LatLong = 40.93 ll 121.22
  val hulunbir: LatLong = 49.265 ll 119.752

  override val polygonLL = LinePathLL(Yakutia.khabarovsk, udaMouth, khab10, khab15, khab20, p25, p28, khab30, primorsky10, nakhodka, vladivostok,
    Korea.northEast, Korea.liaoheMouth,jinzhou, Mongolia.southEast, hulunbir) |++<| LakeBaikal.eastCoast
}

/** [[PolygonLL]] graphic for south east China depends on [[IndoChina]]. */
object Xinjiang extends EArea2("Xinjiang", 42 ll 85, hillyDeshot)
{ val south: LatLong = 36.505 ll 80.950

  override val polygonLL: PolygonLL = PolygonLL(Mongolia.west, Mongolia.southWestOffical, Mongolia.southWest, south, Kyrgyyzstan.p20, Kyrgyyzstan.p10,
    Kyrgyyzstan.issykKulWest, SiberiaWest.p55, SiberiaSouth.southWest)
}

/** [[polygonLL]] graphical representation of Mongolia, depends on [[Manchuria]], [[SiberiaSouth]] and [[LakeBaikal]]. */
object Mongolia extends EArea2("Mongolia", 42 ll 115, deshot)
{ val southEast: LatLong = 41.096 ll 114.088
  val p50: LatLong = 39.860 ll 106.965
  val south: LatLong = 37.310 ll 103.875
  val southWest: LatLong = 40.022 ll 96.864
  val southWestOffical: LatLong = 42.745 ll 96.383
  val west: LatLong = 49.170 ll 87.821

  override val polygonLL: PolygonLL = LakeBaikal.southCoast.reverse |++| LinePathLL(Manchuria.hulunbir, southEast, p50, south, southWest, southWestOffical,
    west, SiberiaSouth.khuvsgulLakeN)
}

/** [[polygonLL]] graphical representation of Korea.Depends on nothing. */
object Korea extends EArea2("Korea", 37.77 ll 127.55, hillyOce)
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