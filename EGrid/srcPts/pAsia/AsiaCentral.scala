/* Copyright 2018-25 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package pEarth; package pAsia
import geom._, pglobe._, egrid._, WTiles._

/** [[polygonLL]] graphical representation of the Tarbagatia mountains and the region between the Atai mountains and the Tian Shan. */
object Tarbagatai extends EarthPoly("Tarbagatai", 47.150 ll 83.015, hillySteppe)
{ val karamay: LatLong = 45.615 ll 84.882

  override val polygonLL: PolygonLL = PolygonLL(AltaiMtains.ulungurLakeNE, karamay, TianShan.p10, TianShan.alakolSE, TianShan.alakolSW, Jetisu.northEast,
    AltaiMtains.southWest)
}

/** [[polygonLL]] graphical representation of Kazakhstan, depends on [[middleEast.Caspian]], [[middleEast.Persia]], [[SiberiaWest]] and [[Tajikstan]]. */
object Kazakhstan extends EarthPoly("Kazak", 47 ll 60, deshot)
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
{ val p10: LatLong = 44.644 ll 83.109
  val northEast: LatLong = 43.787 ll 87.276
  val east: LatLong = 42.373 ll 88.957

  val ferganaEast: LatLong = 40.766 ll 73.260
  val southWest: LatLong = 40.126 ll 69.249
  val northWest: LatLong = 42.037 ll 69.756
  val p90: LatLong = 42.922 ll 71.491

  val alakolSE: LatLong = 45.730 ll 82.141
  val alakolSW: LatLong = 46.025 ll 81.365

  override def polygonLL: PolygonLL = LinePathLL(alakolSW, alakolSE, p10, northEast, east) ++< TarimBasin.northBorder ++ LinePathLL(ferganaEast, southWest,
    northWest, p90) |++<| Jetisu.southBorder
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

/** [[PolygonLL]] graphic for the Tarim Basin in eeast Xinjiang depends on nothing. */
object TarimBasin extends EarthPoly("Tarim Basin", 39.183 ll 82.561, descold)
{ val west: LatLong = 39.354 ll 75.729
  val p85: LatLong = 39.752 ll 76.253
  val p90: LatLong = 39.954 ll 78.416
  val aksu: LatLong = 41.219 ll 80.202
  val north: LatLong = 42.082 ll 85.017
  val northEast: LatLong = 41.010 ll 88.264
  val northBorder: LinePathLL = LinePathLL(p85, p90, aksu, north, northEast)

  val southEast: LatLong = 39.010 ll 88.870
  val south: LatLong = 36.338 ll 81.040
  val p60: LatLong = 37.416 ll 77.361
  val southWest: LatLong = 39.097 ll 75.625
  val southBorder: LinePathLL = LinePathLL(southEast, south, p60, southWest)


  /** Former saline lake. Prior to 1950s. */
  val lopNorNorth: LatLong = 40.610 ll 90.175

  override val polygonLL: PolygonLL = northBorder ++ southBorder |+%| west
}

/** [[PolygonLL]] graphic for Xinjiang depends on [[Mongolia]], [[TarimBasin]], [[TianShan]], [[Tarbagatai]] and [[AltaiMtains]]. */
object Xinjiang extends EarthPoly("Xinjiang", 42 ll 85, hillyDeshot)
{ override val polygonLL: PolygonLL = PolygonLL(Mongolia.southWestOffical, Mongolia.southWest, TarimBasin.southEast, TarimBasin.northEast, TianShan.east,
    TianShan.northEast, TianShan.p10, Tarbagatai.karamay, AltaiMtains.ulungurLakeNE, AltaiMtains.southEast)
}

/** [[polygonLL]] graphical representation of eastern Tibet depends on [[Mongolia]], [[China]], [[Yunnan]]. */
object TibetEast extends EarthPoly("Tibet east", 32 ll 75, mtainSub)
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