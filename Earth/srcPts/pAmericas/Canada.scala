/* Copyright 2018-23 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package pEarth; package pAmericas
import geom._, pglobe._

object CentralCanada extends EArea2("Central Canada", 52.37 ll -86.94, Taiga)
{ val manitoba20 = 57.26 ll -90.89
  val jamesBayNW: LatLong = 55.07 ll -82.31
  val attapiskatMouth = 52.97 ll -82.26
  val moosoneeMouth = 51.36 ll -80.40

  override def polygonLL: PolygonLL = LinePathLL(SouthWestCanada.wUsaNE, SouthWestCanada.nelsonMouth, manitoba20, jamesBayNW, attapiskatMouth, moosoneeMouth, CanadaNorthEast.jamesBayS) ++
    LakeHuron.centralCanadaCoast ++! LakeSuperior.canadaCoast
}

/** [[polygonLL]] graphical representation for north east Canada. Depends on [[NewBrunswick]]. */
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
    ungavaW, koksoakMouth, ungavaS, katavik50,ungavaE, labrador50, labrador60,  labrador70, septlles, pointeDesMonts, quebecCity, NewBrunswick.east)
}

/** [[polygonLL]] graphical representation for south east Canada. Depends on nothing. */
object NovaScotia extends EArea2("Nova Scotia", 45.12  ll -63.58, Taiga)
{ val northWest: LatLong = 46.90 ll -64.72
  val edwardIIslandNorth: LatLong = 47.06 ll -64.00
  val edwardIIslandEast: LatLong = 46.45 ll -61.97
  val capeGeorge = 45.89 ll -61.93
  val phantomPoint: LatLong = 47.04 ll -60.60
  val scatarieEast: LatLong = 46.03 ll -59.68
  val south: LatLong = 43.41 ll -65.62
  val whipplePoint: LatLong = 44.24 ll -66.39
  val p75: LatLong = 45.33 ll -64.94
  val stAndrews: LatLong = 45.02 ll -66.86

  override val polygonLL: PolygonLL = PolygonLL(northWest, edwardIIslandNorth, edwardIIslandEast, capeGeorge, phantomPoint, scatarieEast, south,
    whipplePoint, p75, stAndrews)
}

/** [[polygonLL]] graphical representation for south east Canada. Depends on [[NovaScotia]]. */
object NewBrunswick extends EArea2("New/nBrunswick", 47.2  ll -66.93, Taiga)
{ val east: LatLong = 46.90 ll -70.86
  val p12: LatLong = 47.00 ll -70.58
  val grossesRoches: LatLong = 48.94 ll -67.17
  val madeleine: LatLong = 49.25 ll -65.36
  val capRosiers: LatLong = 48.86 ll -64.20
  val gasconsEst: LatLong = 48.21 ll -64.78

  /** 44.87 ll -66.93 */
  val maineE: LatLong = 44.87 ll -66.93

  override val polygonLL: PolygonLL = PolygonLL(east, p12, grossesRoches, madeleine, capRosiers, gasconsEst, NovaScotia.northWest, NovaScotia.stAndrews, maineE)
}

/** [[polygonLL]] graphical representation for south east Canada. Depends on [[LakeHuron]], [[LakeOntario]], [[LakeErie]] and [[CanadaNorthEast]]. */
object CanadaSouthEast extends EArea2("CanadaSouthEast", 46.68  ll -77.21, Taiga)
{
  override val polygonLL: PolygonLL = LakeHuron.eastCanadaCoast ++
    LinePathLL(CanadaNorthEast.jamesBayS, NewBrunswick.east) ++ LakeOntario.canadaCoast ++! LakeErie.eastCanadaCoast
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
