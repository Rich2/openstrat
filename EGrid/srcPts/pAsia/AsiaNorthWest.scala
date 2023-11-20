/* Copyright 2018-23 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package pEarth; package pAsia
import geom._, pglobe._, egrid._, WTiles._

/** [[polygonLL]] graphical representation of north Russoa. Depends on [[pEurope.Baltland]] and [[SiberiaWest]]. */
object RusNorth extends EArea2("NRus", 61 ll 54, taiga)
{ /** North Coast */
  val chizhaSouth = 66.90 ll 44.52
  val chizhaWest = 67.19 ll 43.77
  val shoynaNorth1 = 68.30 ll 44.21
  val shoynaNorth2 = 68.66 ll 42.28
  val shoynaNorth3 = 68.53 ll 44.07
  val shoynaNorth4 = 68.47 ll 45.78
  val shoynaEast1 = 68.11 ll 46.52
  val shoynaEast2 = 67.81 ll 46.65
  val kiyaEast1 = 67.69 ll 45.32
  val chizhaEast1 = 67.33 ll 44.90
  val vizhas = 66.81 ll 45.96
  val amderma = 69.76 ll 61.68

  override val polygonLL: PolygonLL = PolygonLL(pEurope.Baltland.mezenMouth, chizhaSouth, chizhaWest, shoynaNorth1, shoynaNorth2, shoynaNorth3, shoynaNorth4, shoynaEast1,
     shoynaEast2, kiyaEast1, chizhaEast1, vizhas, amderma, SiberiaWest.uralsNorthEnd, SiberiaWest.uralsNW, SiberiaWest.p80, SiberiaWest.p75, pEurope.Baltland.southEast)
}

/**  [[polygonLL]] graphical representation of Severny Island north of Russia. Depends on nothing. */
object SevernyIsland extends EArea2("Severny", 74.38 ll 57.29, ice)
{ val wSeverny: LatLong = 71.81 ll 51.49
  val severny1: LatLong = 75.37 ll 57.03
  val severnyN = 76.99 ll 67.91
  val severny2 = 72.28 ll 55.36
  val eSeverny = 70.71 ll 57.59

  override val polygonLL: PolygonLL = PolygonLL(wSeverny, severny1, severnyN, severny2, eSeverny)
}

/** [[polygonLL]] graphical representation of west Siberia. Depends on [[SiberiaNorth]] and [[SiberiaSouth]]. */
object SiberiaWest extends EArea2("SiberiaWest", 70 ll 90, taiga)
{ val north: LatLong = 73.478 ll 70.843
  val p55: LatLong = 53.055 ll 78.136
  val p75: LatLong = 53.132 ll 56.307
  val p80: LatLong = 59.138 ll 58.378
  val uralsNW: LatLong = 64.939 ll 58.963
  val uralsNorthEnd: LatLong = 68.829 ll 67.069
  val northWest: LatLong = 72.928 ll 69.396

  override val polygonLL: PolygonLL = PolygonLL(north, SiberiaNorth.yeniseiMouth, SiberiaNorth.yenisei70, SiberiaSouth.southWest, p55, p75, p80,
    uralsNW, uralsNorthEnd,northWest)
}