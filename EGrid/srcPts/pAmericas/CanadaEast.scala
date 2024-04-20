/* Copyright 2018-24 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package pEarth; package pAmericas
import geom._, pglobe._, egrid._, WTiles._

/** [[polygonLL]] graphical representation for north east Canada. Depends on [[NewBrunswick]]. */
object Quebecia extends EarthArea("Quebec", 53.71 ll -70, taiga)
{ val jamesBayS: LatLong = 51.14 ll -79.79
  val eastMainMouth: LatLong = 52.24 ll -78.56
  val jamesBayMouthEast: LatLong = 54.63 ll -79.74

  val katavik50: LatLong = 58.82 ll -66.44
  val ungavaNE: LatLong = 60.49 ll -64.74
  val codIsland: LatLong = 57.781 ll -61.659
  val tununggayualokIsland: LatLong = 56.095 ll -60.956
  val labrador50: LatLong = 54.54 ll -57.30
  val spottedIsland = 53.519 ll -55.750
  val northCove = 53.780 ll -56.482
  val labrador60: LatLong = 52.10 ll -55.72
  val isleAuBois = 51.378 ll -57.136
  val labrador70: LatLong = 50.27 ll -59.91
  val septlles: LatLong = 50.23 ll -66.37
  val pointeDesMonts: LatLong = 49.31 ll -67.38
  val quebecCity: LatLong = 47.02 ll -70.80

  override val polygonLL: PolygonLL = PolygonLL(jamesBayS, eastMainMouth, jamesBayMouthEast, Ungava.southWest, Ungava.southEast, katavik50, ungavaNE,
    codIsland, tununggayualokIsland, labrador50, northCove, spottedIsland, labrador60, isleAuBois,  labrador70, septlles, pointeDesMonts, quebecCity, NewBrunswick.east)
}

/** [[polygonLL]] graphical representation for north east Canada. Depends on nothing. */
object Ungava extends EarthArea("Ungava", 59.882 ll -73.658, tundra)
{ val southWest: LatLong = 56.169 ll -76.652
  val hudsonBayEast: LatLong = 56.46 ll -76.52
  val nunavut120: LatLong = 58.68 ll -78.69
  val hudsonBayMouthE: LatLong = 62.57 ll -77.99
  val ungavaW: LatLong = 61.04 ll -69.56
  val koksoakMouth: LatLong = 58.90 ll -69.38
  val southEast: LatLong = 58.300 ll -67.436

  override val polygonLL: PolygonLL = PolygonLL(southWest, hudsonBayEast, nunavut120, hudsonBayMouthE, ungavaW, koksoakMouth, southEast)
}

  /** [[polygonLL]] graphical representation for south east Canada. Depends on nothing. */
object NovaScotia extends EarthArea("Nova Scotia", 45.12 ll -63.58, taiga)
{ val northWest: LatLong = 46.90 ll -64.72
  val edwardIIslandNorth: LatLong = 47.06 ll -64.00
  val edwardIIslandEast: LatLong = 46.45 ll -61.97
  val capeGeorge: LatLong = 45.89 ll -61.93
  val phantomPoint: LatLong = 47.04 ll -60.60
  val capeDauphin: LatLong = 46.34 ll -60.42

  val scatarieEast: LatLong = 46.03 ll -59.68
  val andrewIsland = 45.301 ll -60.943
  val baldRock = 44.459 ll -63.572
  val south: LatLong = 43.41 ll -65.62
  val cheboguePoint = 43.734 ll -66.119
  val whipplePoint: LatLong = 44.24 ll -66.39
  val p75: LatLong = 45.33 ll -64.94
  val stAndrews: LatLong = 45.02 ll -66.86

  override val polygonLL: PolygonLL = PolygonLL(northWest, edwardIIslandNorth, edwardIIslandEast, capeGeorge, phantomPoint, capeDauphin, scatarieEast,
    andrewIsland, baldRock, south, cheboguePoint, whipplePoint, p75, stAndrews)
}

/** [[polygonLL]] graphical representation for south east Canada. Depends on [[NovaScotia]]. */
object NewBrunswick extends EarthArea("New/nBrunswick", 47.2 ll -66.93, taiga)
{ val east: LatLong = 46.90 ll -70.86
  val p12: LatLong = 47.00 ll -70.58
  val grandMetis: LatLong = 48.64 ll -68.15
  val grossesRoches: LatLong = 48.94 ll -67.17
  val laMartre: LatLong = 49.20 ll -66.17
  val madeleine: LatLong = 49.25 ll -65.36
  val capRosiers: LatLong = 48.86 ll -64.20
  val newCarlisle: LatLong = 48.00 ll -65.33
  val restigoucheMouth: LatLong = 48.07 ll -66.28
  val p60: LatLong = 47.86 ll -65.76
  val nepisiguitMouth: LatLong = 47.66 ll -65.62
  val miscouNorth: LatLong = 48.02 ll -64.53
  val gasconsEst: LatLong = 48.21 ll -64.78

  /** 44.87 ll -66.93 */
  val maineE: LatLong = 44.87 ll -66.93

  override val polygonLL: PolygonLL = PolygonLL(east, p12, grandMetis, grossesRoches, laMartre, madeleine, capRosiers, gasconsEst, newCarlisle,
    restigoucheMouth, p60, nepisiguitMouth, miscouNorth, NovaScotia.northWest, NovaScotia.stAndrews, maineE)
}

/** [[polygonLL]] graphical representation for south east Canada. Depends on [[LakeHuron]], [[LakeOntario]], [[LakeErie]] and [[Quebecia]]. */
object CanadaSouthEast extends EarthArea("CanadaSouthEast", 46.68 ll -77.21, taiga)
{
  override val polygonLL: PolygonLL = LakeHuron.eastCanadaCoast ++
    LinePathLL(Quebecia.jamesBayS, NewBrunswick.east) ++ LakeOntario.canadaCoast |++| LakeErie.eastCanadaCoast
}

/** [[polygonLL]] graphical representation for Newfoundland. Depends on nothing. */
object NewFoundland extends EarthArea("Newfoundland", 48.72 ll -56.16, taiga)
{ val north: LatLong = 51.63 ll -55.43
  val pollardsPoint: LatLong = 49.75 ll -56.92
  val p10: LatLong = 50.15 ll -56.16
  val p20: LatLong = 49.25 ll -53.47
  val east: LatLong = 47.52 ll -52.64
  val southEast: LatLong = 46.650 ll -53.098
  val langlade = 46.799 ll -56.340
  val p55: LatLong = 47.538 ll -56.801
  val southWest: LatLong = 47.62 ll -59.30
  val capGeorge: LatLong = 48.46 ll -59.27
  val savageCove: LatLong = 51.33 ll -56.70

  override def polygonLL: PolygonLL = PolygonLL(north, pollardsPoint, p10, p20, east, southEast, langlade, p55, southWest, capGeorge, savageCove)
}
