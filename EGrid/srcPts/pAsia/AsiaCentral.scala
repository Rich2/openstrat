/* Copyright 2018-24 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package pEarth; package pAsia
import geom._, pglobe._, egrid._, WTiles._

/** [[polygonLL]] graphical representation for the Sayan mountains, includes the kunetsk Alatau and the Tannu-Ola. Depends on nothing. */
object SayanMtains extends EarthPoly("Sayan Mountains", 50.551 ll 86.503, mtainTaiga)
{
  val northWest: LatLong = 55.643 ll 88.105
  override val polygonLL: PolygonLL = PolygonLL(northWest)
}

/** [[polygonLL]] graphical representation for the Altai mountains. Depends on nothing. */
object AltaiMtains extends EarthPoly("Altai Mountains", 50.551 ll 86.503, mtainTaiga)
{
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

  override val polygonLL: PolygonLL = northBorder |++| LinePathLL( kharUsLakeWest, southEast, ulungurLakeNE, southWest)
}

/** [[polygonLL]] graphical representation of the Tarbagatia mountains and the region between the Atai and the */
object Tarbagatai extends EarthPoly("Tarbagatai", 47.150 ll 83.015, hillySteppe)
{
  val karamay: LatLong = 45.615 ll 84.882
  override val polygonLL: PolygonLL = PolygonLL(AltaiMtains.ulungurLakeNE, karamay, AltaiMtains.southWest)
}

/** [[polygonLL]] graphical representation of Khazakstan, depends on [[middleEast.Caspian]], [[middleEast.Persia]], [[SiberiaWest]] and [[Tajikstan]]. */
object Kazak extends EarthPoly("Kazak", 47 ll 60, deshot)
{ override val polygonLL: PolygonLL = LinePathLL(SiberiaWest.p75, SiberiaWest.p55, AltaiMtains.northWest, AltaiMtains.southWest, Jetisu.northEast) ++<
  LakeBalkhash.northCoast ++ LinePathLL(Jetisu.southWest, TianShan.p90, TianShan.northWest, TianShan.southWest, Tajikstan.p65, middleEast.Persia.north) ++<
  pEurope.Caspian.kazakCoast |++| LinePathLL(RusNorth.p50)
}

object LakeBalkhash extends LakePoly("Lake Balkhash", 46.143 ll 74.255, lake)
{ override val area: Kilares = 16400.kilares

  val northEast: LatLong = 46.696 ll 79.288
  val p5: LatLong = 46.370 ll 78.907
  val yuzhnyyBereg: LatLong = 46.409 ll 74.903
  val south: LatLong = 44.817 ll 74.384
  val southCoast: LinePathLL = LinePathLL(northEast, p5, yuzhnyyBereg, south)

  val p55: LatLong = 45.598 ll 73.419
  val p70: LatLong = 46.771 ll 74.639
  val northCoast: LinePathLL = LinePathLL(south, p55, p70, northEast)

  override def polygonLL: PolygonLL = southCoast |-++-| northCoast
}

/** [[polygonLL]] graphical representation of Lake Alakol north of the Tian Shan. Depends on nothing. */
object LakeAlakol extends LakePoly("Lake Alakol", 46.165 ll 81.712, lake)
{ override val area: Kilares = 2650.kilares

  val northWest: LatLong = 46.381 ll 81.926
  val southEast: LatLong = 45.730 ll 82.141
  val southWest: LatLong = 46.025 ll 81.365

  override def polygonLL: PolygonLL = PolygonLL(northWest, southEast, southWest)
}

/** [[polygonLL]] graphical representation of historical region of Jetisu in east Kazakhstan. Depends on [[LakeBalkhash]]. */
object Jetisu extends EarthPoly("Jetisu", 45.427 ll 76.859, steppe)
{ val northEast: LatLong = 46.222 ll 80.538
  val sarkland: LatLong = 45.413 ll 79.911
  val p25: LatLong = 45.383 ll 77.939
  val southEast: LatLong = 43.530 ll 76.635
  val southWest: LatLong = 43.516 ll 74.040
  val southBorder = LinePathLL(northEast, sarkland, p25, southEast, southWest)

  override def polygonLL: PolygonLL = southBorder |++<| LakeBalkhash.southCoast
}

/** The Tian Shan mountain range. */
object TianShan extends EarthPoly("Tian Shan", 42.513 ll 79.741, mtainTundra)
{
  val p10: LatLong = 44.644 ll 83.109
  val northEast: LatLong = 43.787 ll 87.276
  val east: LatLong = 42.373 ll 88.957

  val ferganaEast: LatLong = 40.766 ll 73.260
  val southWest: LatLong = 40.126 ll 69.249
  val northWest: LatLong = 42.037 ll 69.756
  val p90: LatLong = 42.922 ll 71.491

  override def polygonLL: PolygonLL = LinePathLL(p10, northEast, east) ++< TarimBasin.northBorder ++ LinePathLL(ferganaEast, southWest, northWest, p90) ++<
    Jetisu.southBorder |++| LinePathLL(LakeAlakol.southWest, LakeAlakol.southEast)
}

object PamirAlay
{
  //val alayValleyEast: LatLong = 39.635 ll 73.713
  val dushanbe: LatLong = 38.551 ll 68.756
  val southWest: LatLong = 37.755 ll 66.162
}
object Himalayas extends EarthPoly("Himalayas", 32 ll 75, mtainTundra)
{
  override val polygonLL: PolygonLL = LinePathLL(Mongolia.southWest, Yunnan.northWest, India.indiaNE, India.kotdwar, Tajikstan.islamabad) |++<|
    TarimBasin.southBorder
}

/** [[polygonLL]] graphical representation of eastern Tibet depends on [[Mongolia]], [[China]], [[Yunnan]]. */
object TibetEast extends EarthPoly("Tibet east", 32 ll 75, mtainTaiga)
{ override val polygonLL: PolygonLL = PolygonLL(Mongolia.southWest, Mongolia.south, China.northWest, Yunnan.northEast, Yunnan.northWest)
}

object Tajikstan extends EarthPoly("Tajikstan", 47 ll 76, mtainSavannah)
{ val islamabad: LatLong = 33.749 ll 73.19
  val p40: LatLong = 33.124 ll 69.584
  val kandahar: LatLong = 31.607 ll 65.730
  val p65: LatLong = 35.367 ll 62.146
  //val northWest: LatLong = 42.802 ll 71.400
  val southWest: LatLong = 32.244 ll 62.256

  override val polygonLL: PolygonLL = PolygonLL(islamabad, p40, kandahar, southWest, p65, TianShan.southWest, TianShan.ferganaEast, TarimBasin.p85,
    TarimBasin.west, TarimBasin.southWest)
}