/* Copyright 2018-24 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package pEarth; package pAsia
import geom._, pglobe._, egrid._, WTiles._

object AltaiMtains extends EarthPoly("Altai Mountains", 50.551 ll 86.503, mtainTaiga)
{
  val northWest: LatLong = 51.330 ll 82.160
  val north: LatLong = 52.436 ll 86.375
  val teletskoyeNorth: LatLong = 51.769 ll 87.631
  val teletskoyeSouth: LatLong = 51.350 ll 87.791
  val uvsLakeWest: LatLong = 50.387 ll 92.217
  val northFront: LinePathLL = LinePathLL(northWest, north, teletskoyeNorth, teletskoyeSouth, uvsLakeWest)
  val kharUsLakeWest: LatLong = 47.998 ll 91.961
  val southEast: LatLong = 45.446 ll 94.177
  val southWest: LatLong = 49.146 ll 82.284

  override val polygonLL: PolygonLL = northFront |++| LinePathLL( kharUsLakeWest, southEast, southWest)
}

/** [[polygonLL]] graphical representation of Khazakstan, depends on [[middleEast.Caspian]], [[middleEast.Persia]], [[SiberiaWest]] and [[Kyrgyyzstan]]. */
object Kazak extends EarthPoly("Kazak", 47 ll 60, deshot)
{ override val polygonLL: PolygonLL = LinePathLL(SiberiaWest.p75, SiberiaWest.p55, AltaiMtains.northWest, AltaiMtains.southWest, Jetisu.northEast) ++<
  LakeBalkhash.northCoast ++ LinePathLL(Jetisu.southWest, Kyrgyyzstan.northWest, Kyrgyyzstan.p65, middleEast.Persia.north) ++< pEurope.Caspian.kazakCoast |++|
  LinePathLL(RusNorth.p50)
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

/** [[polygonLL]] graphical representation of historical region of Jetisu in east Kazakhstan. Depends on [[LakeBalkhash]] */
object Jetisu extends EarthPoly("Jetisu", 45.427 ll 76.859, steppe)
{
  val northEast: LatLong = 46.222 ll 80.538
  val sarkland: LatLong = 45.413 ll 79.911
  val p25: LatLong = 45.383 ll 77.939
  val southEast: LatLong = 43.530 ll 76.635
  val southWest: LatLong = 43.516 ll 74.040

  override def polygonLL: PolygonLL = LinePathLL(northEast, sarkland, p25, southEast, southWest) |++<| LakeBalkhash.southCoast
}

object Himalayas extends EarthPoly("Himalayas", 32 ll 75, mtainTundra)
{
  override val polygonLL: PolygonLL = LinePathLL(Mongolia.southWest, Yunnan.northWest, India.indiaNE, India.kotdwar, Kyrgyyzstan.islamabad) |++<|
    TarimBasin.southBorder
}

/** [[polygonLL]] graphical representation of eastern Tibet depends on [[Mongolia]], [[China]], [[Yunnan]]. */
object TibetEast extends EarthPoly("Tibet east", 32 ll 75, mtainTaiga)
{ override val polygonLL: PolygonLL = PolygonLL(Mongolia.southWest, Mongolia.south, China.northWest, Yunnan.northEast, Yunnan.northWest)
}

object Kyrgyyzstan extends EarthPoly("Kyrgyzstan", 47 ll 76, mtainSavannah)
{ val islamabad: LatLong = 33.749 ll 73.19
  val p40: LatLong = 33.124 ll 69.584
  val kandahar: LatLong = 31.607 ll 65.730
  val p65: LatLong = 35.367 ll 62.146
  val northWest: LatLong = 42.802 ll 71.400
  //val north: LatLong = 42.787 ll 74.513
  //val issykKulWest: LatLong = 42.443 ll 76.196
  val southWest: LatLong = 32.244 ll 62.256

  override val polygonLL: PolygonLL = PolygonLL(Jetisu.southEast, TarimBasin.p85, TarimBasin.west, TarimBasin.southWest, islamabad, p40, kandahar, southWest,
    p65, northWest, Jetisu.southWest)// /*north,*/ issykKulWest)
}