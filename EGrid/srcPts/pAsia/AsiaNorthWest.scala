/* Copyright 2018-23 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package pEarth; package pAsia
import geom._, pglobe._, LatLong._, egrid._, WTiles._

object RusNorth extends EArea2("NRus", 61 ll 54, taiga)
{
   /** North Coast */
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
   

  val cEuropeE = 51.36.east
  //val nRusSW = 55.north * cEuropeE

  override val polygonLL: PolygonLL = PolygonLL(pEurope.Baltland.mezenMouth, chizhaSouth, chizhaWest, shoynaNorth1, shoynaNorth2, shoynaNorth3, shoynaNorth4, shoynaEast1,
     shoynaEast2, kiyaEast1, chizhaEast1, vizhas, amderma, SiberiaNorth.uralsNorthEnd, SiberiaNorth.uralsNW, pEurope.Baltland.southEast)
}

object Kazak extends EArea2("Kazak", 47 ll 60, land)
{
  val bautino = degs(44.53, 50.24)
  val kendirliBay = degs(42.73, 52.74)
  val mangystau = degs(45.48, 52.78)
  val volodarsky = degs(46.39, 49.03)

  val kazakNE = 55.north * India.wAsiaE

  override val polygonLL: PolygonLL = PolygonLL(kazakNE, middleEast.Persia.persiaNE, middleEast.Caspian.persiaN, kendirliBay, bautino, mangystau,
    middleEast.Caspian.northEast, middleEast.Caspian.north, volodarsky, pEurope.Ukraine.caspianW, pEurope.Baltland.southEast)
}

/** Visual display of Severny Island north of Russia. */
object SevernyIsland extends EArea2("Severny", 74.38 ll 57.29, ice)
{ val wSeverny: LatLong = 71.81 ll 51.49
  val severny1: LatLong = 75.37 ll 57.03
  val severnyN = 76.99 ll 67.91
  val severny2 = 72.28 ll 55.36
  val eSeverny = 70.71 ll 57.59

  override val polygonLL: PolygonLL = PolygonLL(wSeverny, severny1, severnyN, severny2, eSeverny)
}

object SiberiaNorth extends EArea2("SiberiaNorth", 70 ll 90, tundra)
{
  val p10: LatLong = 75.64 ll 91.5//north * indiaE
  val krasnoyarsk: LatLong = 77.43 ll 103.99
  val kras10: LatLong = 76.62 ll 112.46

  val p75 = 53.132 ll 56.307
  val p80: LatLong = 59.138 ll 58.378
  val uralsNW: LatLong = 64.939 ll 58.963
  val uralsNorthEnd: LatLong = 68.829 ll 67.069

  override val polygonLL: PolygonLL = PolygonLL(krasnoyarsk, kras10, p75, p80, uralsNW, uralsNorthEnd, p10,
  )
}