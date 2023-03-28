/* Copyright 2018-23 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package pEarth; package pAmericas
import geom._
import ostrat.pEarth.pAmericas
import pglobe._

object Alaska extends EArea2("Alaska", 66.33 ll -151.16, Taiga)
{  val northEast = 69.45 ll -141
  val yakut10: LatLong = 59.93 ll -141.03
  val susitnaMouth = 61.25 ll -150.61
  val p20 = 57.18 ll -156.35
  val nikolski = 52.88 ll -168.94
  val portHeiden = 57.63 ll -157.69
  val kvichakMouth = 58.87 ll -157.05
  val capeNewenham = 58.57 ll -161.73
  val p30: LatLong = 60.50 ll -164.55
  val p33 = 61.60 ll -166.18
  val koyuk = 64.93 ll -161.19
  val p40 = 64.65 ll -166.39
  val capeDouglas = 65.0 ll -166.70
  val teller = 65.26 ll -166.36
  val imurukMouth = 65.23 ll -166.04
  val brevig = 65.34 ll -166.50
  val west = 65.66 ll -168.11
  val northWest: LatLong = 70.11 ll -161.87
  val p10 = 70.11 ll -143.20

  override def polygonLL: PolygonLL = PolygonLL(northEast, yakut10, susitnaMouth, p20, nikolski, portHeiden, kvichakMouth, capeNewenham, p30, p33, koyuk, p40, capeDouglas, teller, imurukMouth, brevig, west, northWest, p10)
}

/** [[polygonLL]] graphical representation for nunavut territory Canada. Depends on Nothing. */
object Nunavut extends EArea2("Nunavut", 67.00 ll -96.58, Tundra)
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

/** [[polygonLL]] graphical representation for north west Canada. Depends on [[Alaska]]. */
object NorthWestCanada extends EArea2("NorthWest Canada", 64.051 ll-129.98, Taiga)
{ val yakut50: LatLong = 60 ll -139.60
  val yakut10: LatLong = 68.90 ll -136.53
  val inuvik10: LatLong = 70.56 ll -128.00
  val nunavut10: LatLong = 69.00 ll -115.80
  val raeMouth: LatLong = 67.92 ll -115.34
  val hudsonBay60W = 60 ll -94.82

  override def polygonLL: PolygonLL = PolygonLL(yakut50, Alaska.yakut10, Alaska.northEast, yakut10, inuvik10, nunavut10, raeMouth, Nunavut.northWest,
    Nunavut.rockHouseIsland, hudsonBay60W)
}

object SouthWestCanada extends EArea2("SouthWest Canada", 55 ll-110, Taiga)
{ val wUsaNE = 50 ll -98
  val w49th: LatLong = 49 ll -125.66
  val vancouverNW = 50.77 ll -128.38
  val p50 = 54.71 ll -132.81
  val eggIsland : LatLong= 59.91 ll -94.85

  val churchillMouth = 58.79 ll -94.20
  val manitoba10 = 58.75 ll -93.24
  val nelsonMouth = 57.09 ll -92.47
  override def polygonLL: PolygonLL = PolygonLL(wUsaNE, w49th, vancouverNW, p50, NorthWestCanada.yakut50, NorthWestCanada.hudsonBay60W, eggIsland, churchillMouth, manitoba10, nelsonMouth)
}

object CentralCanada extends EArea2("Central Canada", 52.37 ll -86.94, Taiga)
{ val manitoba20 = 57.26 ll -90.89
  val jamesBayNW: LatLong = 55.07 ll -82.31
  val attapiskatMouth = 52.97 ll -82.26
  val moosoneeMouth = 51.36 ll -80.40


  override def polygonLL: PolygonLL = LinePathLL(SouthWestCanada.wUsaNE, SouthWestCanada.nelsonMouth, manitoba20, jamesBayNW, attapiskatMouth, moosoneeMouth, CanadaNorthEast.jamesBayS) ++
    LakeHuron.centralCanadaCoast ++! LakeSuperior.canadaCoast
}

/** [[polygonLL]] graphical representation for north east Canada. Depends on nothing. */
object CanadaNorthEast extends EArea2("CanadaNorthEast", 53.71 ll-70, Tundra)
{ val jamesBayS: LatLong = 51.14 ll -79.79
  val eastMainMouth: LatLong = 52.24 ll -78.56
  val jamesBayMouthEast: LatLong = 54.63 ll -79.74
  val hudsonBayEast: LatLong = 56.46 ll -76.52
  val nunavut120: LatLong = 58.68 ll -78.69
  val hudsonBayMouthE: LatLong = 62.57 ll -77.99
  val ungavaW: LatLong = 61.04 ll -69.56
  val koksoakMouth: LatLong = 58.90 ll -69.38
  val ungavaS: LatLong = 58.26 ll -67.45
  val katavik50: LatLong = 58.82 ll -66.44
  val ungavaE: LatLong = 60.49 ll -64.74
  val labrador50: LatLong = 54.54 ll -57.30
  val labrador60: LatLong = 52.10 ll -55.72
  val labrador70: LatLong = 50.27 ll -59.91
  val septlles: LatLong = 50.23 ll -66.37
  val pointeDesMonts: LatLong = 49.31 ll -67.38
  val quebecCity: LatLong = 47.02 ll -70.80

  override val polygonLL: PolygonLL = PolygonLL(jamesBayS, eastMainMouth, jamesBayMouthEast, hudsonBayEast, nunavut120, hudsonBayMouthE,
    ungavaW, koksoakMouth, ungavaS, katavik50,ungavaE, labrador50, labrador60,  labrador70, septlles, pointeDesMonts, quebecCity)
}

/** [[polygonLL]] graphical representation for south east Canada. Depends on [[LakeHuron]], [[LakeOntario]], [[LakeErie]] and [[CanadaNorthEast]]. */
object CanadaSouthEast extends EArea2("CanadaSouthEast", 46.68  ll -77.21, Taiga)
{ val p10: LatLong = 46.90 ll -70.86
  val p12: LatLong = 47.00 ll -70.58
  val grossesRoches: LatLong = 48.94 ll -67.17
  val madeleine: LatLong = 49.25 ll -65.36
  val capRosiers: LatLong = 48.86 ll -64.20
  val gasconsEst: LatLong = 48.21 ll -64.78
  val scoudoucMouth: LatLong = 46.22 ll -64.55
  val eNovaScotia: LatLong = 46.16 ll -59.86
  val novaScotiaS: LatLong = 43.43 ll -65.61

  /** 44.87 ll -66.93 */
  val maineE: LatLong = 44.87 ll -66.93

  val eCanadaCoast: LinePathLL = LinePathLL( madeleine, capRosiers, gasconsEst,
    scoudoucMouth, eNovaScotia, novaScotiaS)

  override val polygonLL: PolygonLL = LakeHuron.eastCanadaCoast ++ LinePathLL(CanadaNorthEast.jamesBayS, CanadaNorthEast.quebecCity, p10, p12, grossesRoches) ++  eCanadaCoast +% maineE ++
    LakeOntario.canadaCoast ++! LakeErie.eastCanadaCoast
}

object NewFoundland extends EArea2("Newfoundland", 48.72 ll -56.16, Taiga)
{ val north = 51.63 ll -55.43
  val pollardsPoint = 49.75 ll -56.92
  val p10 = 50.15 ll -56.16
  val p20 = 49.25 ll -53.47
  val east: LatLong = 47.52 ll -52.64
  val southWest = 47.62 ll -59.30
  val capGeorge = 48.46 ll -59.27
  val savageCove = 51.33 ll -56.70
  override def polygonLL: PolygonLL = PolygonLL(north, pollardsPoint, p10, p20, east, southWest, capGeorge, savageCove)
}
