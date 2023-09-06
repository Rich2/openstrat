/* Copyright 2018-23 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package pEarth; package pAmericas
import geom._, pglobe._, egrid._, WTile._

/** [[polygonLL]] graphical representation for Alaska. Depends on Nothing. */
object Alaska extends EArea2("Alaska", 66.33 ll -151.16, taiga)
{  val northEast = 69.45 ll -141
  val yakut10: LatLong = 59.93 ll -141.03
  val susitnaMouth = 61.25 ll -150.61
  val p20 = 57.18 ll -156.35
  val nikolski = 52.88 ll -168.94
  val portHeiden = 57.63 ll -157.69
  val kvichakMouth = 58.87 ll -157.05
  val capeNewenham = 58.57 ll -161.73
  val p30: LatLong = 60.50 ll -164.55
  val p33: LatLong = 61.60 ll -166.18
  val koyuk: LatLong = 64.93 ll -161.19
  val p40: LatLong = 64.65 ll -166.39
  val capeDouglas: LatLong = 65.0 ll -166.70
  val teller: LatLong = 65.26 ll -166.36
  val imurukMouth: LatLong = 65.23 ll -166.04
  val brevig: LatLong = 65.34 ll -166.50
  val west: LatLong = 65.66 ll -168.11
  val northWest: LatLong = 70.11 ll -161.87
  val p10: LatLong = 70.11 ll -143.20

  override def polygonLL: PolygonLL = PolygonLL(northEast, yakut10, susitnaMouth, p20, nikolski, portHeiden, kvichakMouth, capeNewenham, p30, p33,
    koyuk, p40, capeDouglas, teller, imurukMouth, brevig, west, northWest, p10)
}

/** [[polygonLL]] graphical representation for nunavut territory Canada. Depends on Nothing. */
object Nunavut extends EArea2("Nunavut", 67.00 ll -96.58, tundra)
{ val northWest = 68.27 ll -108.77
  val nunavut15: LatLong = 67.68 ll -101.53
  val nunavut17: LatLong = 68.55 ll -97.80
  val nunavut25: LatLong = 68.05 ll -94.76
  val nunavut30: LatLong = 69.32 ll -94.23
  val nunavut40: LatLong = 71.39 ll -96.17
  val somersetNW: LatLong = 73.99 ll -95.28
  val somersetNE = 73.91 ll -90.18
  val somersetSE = 72.73 ll -92.27
  val nunavut45 = 72.72 ll -93.79
  val nunavut47 = 71.76 ll -93.69
  val nunavut49 = 71.35 ll -92.97
  val nunavut54 = 70.17 ll -91.57
  val nunavut56 = 69.70 ll -92.48
  val nunavut60 = 69.50 ll -91.83
  val nunavut64 = 68.45 ll -90.59
  val nunavut65 = 68.24 ll -90.23
  val nunavut67 = 69.26 ll -89.13
  val nunavut70 = 68.80 ll -88.02
  val nunavut80 = 67.21 ll -87.19
  val nunavut83 = 68.72 ll -85.59
  val nunavut85 = 69.85 ll -85.55
  val nunavut87 = 69.65 ll -82.13
  val nunavut88: LatLong = 69.20 ll -81.35
  val navut20 = 67.45 ll -81.21
  val naujaat10 = 66.35 ll -83.41
  val naujaat12 = 66.18 ll -84.44
  val nunavut90 = 66.27 ll -85.23
  val nunavut92 = 66.54 ll -86.76
  val naujaat15 = 66.20 ll -85.90
  val naujaat17 = 64.00 ll -88.76
  val rockHouseIsland = 63.44 ll -90.63

  override def polygonLL: PolygonLL = PolygonLL(northWest, nunavut15, nunavut17, nunavut25, nunavut30, nunavut40, somersetNW, somersetNE, somersetSE,
    nunavut45, nunavut47, nunavut49, nunavut54, nunavut56, nunavut60, nunavut65, nunavut67, nunavut70, nunavut80, nunavut83, nunavut85, nunavut87,
    nunavut88, navut20, naujaat10, naujaat12, nunavut90, nunavut92, naujaat15, naujaat17, rockHouseIsland)
}

/** [[polygonLL]] graphical representation for Great Bear Lake. Depends on nothing. */
object GreatBearLake extends EArea2("Great Bear\nLake", 66.00 ll -120.25, Lake)
{ val north = 67.04 ll -119.80
  val p20 = 66.86 ll -119.45
  val p30 = 66.40 ll -120.51
  val p36 = 66.29 ll -117.97
  val p40 = 65.60 ll -119.92
  val p55 = 64.93 ll -122.06
  val p70 = 66.06 ll -121.25
  val northWest = 66.26 ll -125.87


  override def polygonLL: PolygonLL = PolygonLL(north, p20, p30, p36, p40, p55, p70, northWest)
}

/** [[polygonLL]] graphical representation for Great Bear Lake. Depends on nothing. */
object GreatSlaveLake extends EArea2("Great Slave\nLake", 61.66 ll -113.83, Lake)
{ val north = 62.96 ll -110.42
  val east = 62.77 ll -108.91
  val south = 60.83 ll -115.58
  val bigIslandEast = 61.13 ll -116.44
  val p70 = 61.88 ll -114.59
  val northWest = 62.76 ll -116.00
  val p80 = 61.99 ll -113.50
  override def polygonLL: PolygonLL = PolygonLL(north, east, south, bigIslandEast, p70, northWest, p80)
}


  /** [[polygonLL]] graphical representation for north west Canada. Depends on [[Alaska]]. */
object CanadaNorthWest extends EArea2("Canada\n north west", 64.051 ll -129.98, taiga)
{ val yakut50: LatLong = 60 ll -139.60
  val yakut10: LatLong = 68.90 ll -136.53
  val inuvik10: LatLong = 70.56 ll -128.00
  val nunavut10: LatLong = 69.00 ll -115.80
  val raeMouth: LatLong = 67.92 ll -115.34
  val hudsonBay60W = 60 ll -94.82

  override def polygonLL: PolygonLL = PolygonLL(yakut50, Alaska.yakut10, Alaska.northEast, yakut10, inuvik10, nunavut10, raeMouth, Nunavut.northWest,
    Nunavut.rockHouseIsland, hudsonBay60W)
}

object CanadaSouthWest extends EArea2("Canada\n south west", 55 ll -110, taiga)
{ //val wUsaNE = 50 ll -98
  val montanaNE = 49 ll -104
  val w49th: LatLong = 49 ll -125.66
  val vancouverNW = 50.77 ll -128.38
  val p50 = 54.71 ll -132.81
  val eggIsland : LatLong= 59.91 ll -94.85

  val churchillMouth = 58.79 ll -94.20
  val manitoba10 = 58.75 ll -93.24
  val nelsonMouth = 57.09 ll -92.47
  override def polygonLL: PolygonLL = PolygonLL(CanadaNorthWest.yakut50, CanadaNorthWest.hudsonBay60W, eggIsland, churchillMouth, manitoba10,
    nelsonMouth, LakeWinnipeg.northWest, LakeWinnipeg.p75, LakeWinnipeg.redMouth, montanaNE/*, wUsaNE*/, w49th, vancouverNW, p50)
}