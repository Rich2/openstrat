/* Copyright 2018-25 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package pEarth; package pEurope
import geom.*, pglobe.*, egrid.*, WTiles.*

/** [[polygonLL]] graphical representation of north Finland and north-east Karelia. Depends on [[KolaPeninsula]] and [[SwedenNorth]]. */
object FinlandNorth extends EarthPoly("Finland North", 67.614 ll 27.638, hillyTundra) {
  val svaerholt: LatLong = 70.96 ll 26.67
  val north: LatLong = 71.132 ll 27.647
  val p10: LatLong = 70.979 ll 28.540
  val p12: LatLong = 70.719 ll 28.279
  val p14: LatLong = 70.877 ll 28.839
  val p16: LatLong = 70.707 ll 30.078
  val vardo: LatLong = 70.36 ll 31.12
  val ekkeroy: LatLong = 70.070 ll 30.165
  val karlebotn: LatLong = 70.11 ll 28.57
  val bugoya: LatLong = 69.977 ll 29.690
  val barentsCoast = LinePathLL(svaerholt, north, p10, p12, p14, p16, vardo, ekkeroy, karlebotn, bugoya, KolaPeninsula.northWest)

  val kovdaEast: LatLong = 66.66 ll 33.24
  val keretEast: LatLong = 66.22 ll 34.09
  val kalgalakshaEast: LatLong = 65.75 ll 35.02
  val southEast: LatLong = 65.356 ll 34.429
  val whiteSeaCoast: LinePathLL = LinePathLL(KolaPeninsula.kandalasaksha, kovdaEast, keretEast, kalgalakshaEast, southEast)

  val balticNE: LatLong = 65.561 ll 25.212

  override val polygonLL: PolygonLL = barentsCoast ++ whiteSeaCoast |++| LinePathLL(balticNE, SwedenNorth.haparanda, SwedenNorth.reinoya)
}

/** [[polygonLL]] graphical representation of Kola Peninsula. Depends on nothing. */
object KolaPeninsula extends EarthPoly("KolaPeninsula", 67.80 ll 36.52, tundra) {
  val northWest: LatLong = 69.50 ll 31.81
  val p5: LatLong = 69.95 ll 31.93
  val p7: LatLong = 69.73 ll 33.10
  val p15: LatLong = 69.46 ll 32.90
  val tulomaMouth: LatLong = 69.33 ll 33.56
  val ostrov: LatLong = 69.09 ll 36.27
  val p30: LatLong = 68.156 ll 39.743
  val mayakGorodetsky: LatLong = 67.731 ll 40.894
  val barentsCoast: LinePathLL = LinePathLL(northWest, p5, p7, p15, tulomaMouth, ostrov, p30, mayakGorodetsky)

  val ponoyNorth: LatLong = 67.44 ll 41.07
  val ponoyEast: LatLong = 67.11 ll 41.36
  val sosnovka: LatLong = 66.53 ll 40.68
  val mayakNikodimsky: LatLong = 66.10 ll 39.11
  val tetrino: LatLong = 66.06 ll 38.24
  val olenitsa: LatLong = 66.42 ll 35.37
  val umbaWest: LatLong = 66.71 ll 33.52
  val luvenga: LatLong = 67.08 ll 32.75
  val kandalasaksha: LatLong = 67.13 ll 32.26
  val whiteSeaCaost: LinePathLL = LinePathLL(mayakGorodetsky, ponoyNorth, ponoyEast, sosnovka, mayakNikodimsky, tetrino, olenitsa, umbaWest, luvenga, kandalasaksha)

  override val polygonLL = barentsCoast |+-+| whiteSeaCaost
}

/** [[polygonLL]] graphical representation of south Finland. Depends on [[FinlandNorth]] and [[Baltland]]. */
object FinlandSouth extends EarthPoly("Finland south", 65.56 ll 29.95, lakesTaiga)
{ val lisyNos: LatLong = 60.01 ll 29.96
  val laskovyy: LatLong = 60.15 ll 29.92
  val ozerki: LatLong = 60.18 ll 29.01
  val baltiyets: LatLong = 60.61 ll 28.38
  val helsinki: LatLong = 60.15 ll 24.94
  val hanko: LatLong = 59.82 ll 22.94

  /** Start of East Baltic Coast. */
  val p10: LatLong = 59.92 ll 22.89
  val kimitoonSE: LatLong = 60.01 ll 22.76
  val hyppeis: LatLong = 60.22 ll 21.26
  val lyperto: LatLong = 60.61 ll 21.16
  val pooskeri: LatLong = 61.80 ll 21.43
  val sidebySW: LatLong = 61.99 ll 21.28
  val wVaasa: LatLong = 63.11 ll 21.47
  val vasankariWest: LatLong = 64.34 ll 23.93
  val oulu: LatLong = 65.00 ll 25.41
  val olhava: LatLong = 65.46 ll 25.33
  
  val ladozhskiy: LatLong = 60.02 ll 31.12

  
  override val polygonLL: PolygonLL = LinePathLL(lisyNos, laskovyy, ozerki, baltiyets, helsinki, hanko,
    /*East Baltic */ p10, kimitoonSE, hyppeis, lyperto, pooskeri, sidebySW, wVaasa, vasankariWest, oulu, olhava, FinlandNorth.balticNE) |++|
    LinePathLL(
    )
}

/** [[polygonLL]] graphical representation of Karelia. Depends on [[FinlandNorth]] and [[Baltland]]. */
object Karelia extends EarthPoly("Karelia", 65.56 ll 29.95, ice)
{
  /** Start of White Sea West. */
  val vygMouth: LatLong = 64.53 ll 34.78
  val kohezmaNorth: LatLong = 64.38 ll 35.61
  val whiteSeaCoast: LinePathLL = LinePathLL(FinlandNorth.southEast, vygMouth, kohezmaNorth, RussiaNE.onezhsky)

  val medvezhyegorskNorth: LatLong = 62.91 ll 34.49
  val medvezhyegorskSouth: LatLong = 62.89 ll 34.44
  val petrozavodsk: LatLong = 61.81 ll 34.55
  val shcheleyki: LatLong = 61.14 ll 35.70
  
  override val polygonLL: PolygonLL =  whiteSeaCoast ++ LinePathLL(RussiaNE.chelmuzhiEast, medvezhyegorskNorth, medvezhyegorskSouth, petrozavodsk, shcheleyki,
    RussiaNE.svirMouth) |++<| LakeLagoda.kareliaCoast
}

/** [[polygonLL]] graphical representation of Lake Lagoda. Depends on nothing. */
object LakeLagoda extends LakePoly("Lake Lagoda", 60.877 ll 31.577, lake)
{ override val area: Kilares = 17891.kilares

  val north: LatLong = 61.608 ll 30.898
  val northEast: LatLong = 61.554 ll 31.475
  val kareliaSW: LatLong = 60.678 ll 32.932
  val kareliaCoast = LinePathLL(north, northEast, kareliaSW)

  val southEast: LatLong = 60.191 ll 32.589
  val p40: LatLong = 60.253 ll 31.717
  val p45: LatLong = 59.938 ll 31.547
  val oreshek: LatLong = 59.954 ll 31.046
  val southCoast: LinePathLL = LinePathLL(kareliaSW, southEast, p40, p45, oreshek)

  override val polygonLL: PolygonLL = kareliaCoast |+-+| southCoast
}

/** [[polygonLL]] graphical representation of Lake Onega. Depends on nothing. */
object LakeOnega extends LakePoly("Lake Onega", 61.711 ll 35.367, lake)
{ override val area: Kilares = 9891.kilares

  val north: LatLong = 62.915 ll 34.493

  override val polygonLL: PolygonLL = PolygonLL(north)
}