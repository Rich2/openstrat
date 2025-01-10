/* Copyright 2018-25 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package pEarth; package pAsia
import geom._, pglobe._, egrid._, WTiles._

/** [[polygonLL]] graphical representation of north Siberia.Depends on nothing. */
object SiberiaNorth extends EarthPoly("SiberiaNorth", 70 ll 95, tundra)
{ val krasnoyarsk: LatLong = 77.43 ll 103.99
  val kras10: LatLong = 76.62 ll 112.46
  val kras20: LatLong = 75.38 ll 113.69
  val khatangaMouth: LatLong = 73.21 ll 106.23
  val khat10: LatLong = 74.02 ll 110.26
  val sakha10: LatLong = 74.00 ll 112.83
  val anabarMouth: LatLong = 73.534 ll 113.478
  val anabarHead: LatLong = 70.774 ll 113.337
  val southEast: LatLong = 66.877 ll 114.084
  val p50: LatLong = 66.344 ll 99.119

  val yenisei70: LatLong = 66.775 ll 86.738
  val yeniseiMouth: LatLong = 71.831 ll 82.730
  val p90: LatLong = 75.64 ll 91.5

  override val polygonLL: PolygonLL = PolygonLL(krasnoyarsk, kras10, kras20, khatangaMouth, khat10, sakha10, anabarMouth, anabarHead, southEast, p50,
    yenisei70, yeniseiMouth, p90
  )
}

/** [[polygonLL]] graphical representation of south Siberia.Depends on [[SiberiaNorth]] and [[LakeBaikal]]. */
object SiberiaSouth extends EarthPoly("SiberiaSouth", 60 ll 92, taiga)
{ val lensk: LatLong = 60.709 ll 114.92

  override val polygonLL = LinePathLL(SiberiaNorth.southEast, lensk) ++< LakeBaikal.westCoast  |++|
    LinePathLL(SiberiaNorth.yenisei70, SiberiaNorth.p50)
}

/** [[polygonLL]] graphical representation for the Sayan mountains, includes the kunetsk Alatau and the Tannu-Ola. Depends on nothing. */
object SayanMtains extends EarthPoly("Sayan Mountains", 50.551 ll 86.503, mtainTaiga) {
  val northWest: LatLong = 55.643 ll 88.105
  val northEast: LatLong = 55.829 ll 93.467
  val irkutsk: LatLong = 52.238 ll 104.265
  val southEast: LatLong = 49.602 ll 97.355

  override val polygonLL: PolygonLL = PolygonLL(northEast, irkutsk, LakeBaikal.angaraMouth, LakeBaikal.west, southEast, northWest)
}

/** [[polygonLL]] graphical representation for the Altai mountains. Depends on nothing. */
object AltaiMtains extends EarthPoly("Altai Mountains", 50.551 ll 86.503, mtainTaiga) {
  val northWest: LatLong = 51.330 ll 82.160
  val north: LatLong = 52.436 ll 86.375
  val teletskoyeNorth: LatLong = 51.769 ll 87.631
  val teletskoyeSouth: LatLong = 51.350 ll 87.791
  val uvsLakeWest: LatLong = 50.387 ll 92.217
  val northBorder: LinePathLL = LinePathLL(northWest, north, teletskoyeNorth, teletskoyeSouth, uvsLakeWest)

  val kharUsLakeWest: LatLong = 47.998 ll 91.961
  val southEast: LatLong = 45.446 ll 94.177
  val ulungurLakeNE: LatLong = 47.422 ll 87.569

  val southWest: LatLong = 49.146 ll 82.284

  override val polygonLL: PolygonLL = northBorder |++| LinePathLL(kharUsLakeWest, southEast, ulungurLakeNE, southWest)
}

/** [[polygonLL]] graphical representation of Korea.Depends on nothing. */
object LakeBaikal extends LakePoly("Lake Baikal", 53.463 ll 108.157, lake)
{ override val area: Kilares = 31722.kilares

  val north: LatLong = 55.872 ll 109.742
  val northEast: LatLong = 55.670 ll 109.963
  val east: LatLong = 54.157 ll 109.556
  val mamai: LatLong = 51.456 ll 104.768

  /** The east coast of Lake Baikal in clockwise direction. Shares both north and south [[LatLong]] points with the westCoast. */
  val eastCoast = LinePathLL(north, northEast, east, mamai)

  val west: LatLong = 51.697 ll 103.699

  /** The east coast of Lake Baikal in clockwise direction. Shares both north and south [[LatLong]] points with the westCoast. */
  val southCoast = LinePathLL(mamai, west)
  val angaraMouth: LatLong = 51.870 ll 104.82
  val p80: LatLong = 53.927 ll 108.197

  /** The west coast of Lake Baikal in clockwise direction. Shares both south and north [[LatLong]] points with the eastCoast. */
  val westCoast: LinePathLL = LinePathLL(angaraMouth, p80, north)

  override val polygonLL: PolygonLL = eastCoast +-+ southCoast |++| westCoast
}