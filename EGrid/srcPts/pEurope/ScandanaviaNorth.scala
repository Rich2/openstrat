/* Copyright 2018-24 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package pEarth; package pEurope
import geom._, pglobe._, egrid._, WTiles._

/** [[polygonLL]] graphical representation of north Sweden and north Norway. Depends on nothing. */
object SwedenNorth extends EarthArea("Sweden North", 67.489 ll 20.872, hillyCont)
{
  /** Start of North Coast */
  val sorvagen: LatLong = 67.83 ll 12.82
  val andenes: LatLong = 69.32 ll 16.11
  val gapoyholman: LatLong = 68.88 ll 16.06
  val sandsvika: LatLong = 69.37 ll 16.87
  val torsvag: LatLong = 70.28 ll 19.59
  val slida: LatLong = 70.392 ll 21.702
  val p85: LatLong = 70.669 ll 21.979
  val ingoya: LatLong = 71.073 ll 23.948
  val northSeaCoast: LinePathLL = LinePathLL(sorvagen, andenes, gapoyholman, sandsvika, torsvag, slida, p85, ingoya)

  val nordkapp: LatLong = 71.16 ll 25.78
  val reinoya: LatLong = 70.298 ll 25.309
  val barentsCoast: LinePathLL = LinePathLL(ingoya, nordkapp, reinoya)

  val haparanda: LatLong = 65.77 ll 24.17
  val balticNW: LatLong = 65.856 ll 22.350

  val vegaoyan: LatLong = 65.615 ll 11.754
  val p78: LatLong = 66.011 ll 12.146
  val bodo: LatLong = 67.26 ll 14.32
  val hadseloya: LatLong = 68.56 ll 14.63
  val nordskot: LatLong = 67.82 ll 14.70
  val baroya: LatLong = 68.33 ll 16.03

  override val polygonLL: PolygonLL = northSeaCoast ++- barentsCoast |++| LinePathLL(haparanda, balticNW, vegaoyan, p78, bodo, nordskot, baroya,

  )
}

/** [[polygonLL]] graphical representation of north Finland and north-east Karelia. Depends on [[KolaPeninsula]] and [[SwedenNorth]]. */
object FinlandNorth extends EarthArea("Finland North", 67.614 ll 27.638, hillyTundra)
{ val svaerholt: LatLong = 70.96 ll 26.67
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
object KolaPeninsula extends EarthArea("KolaPeninsula", 67.80 ll 36.52, tundra)
{ val northWest: LatLong = 69.50 ll 31.81
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

  override val polygonLL = barentsCoast |++-| whiteSeaCaost
}

/** [[polygonLL]] graphical representation of Finland. Depends on [[FinlandNorth]] and [[Baltland]]. */
object FinlandSouth extends EarthArea("Finland south", 65.56 ll 29.95, taiga)
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
  
  /** Start of White Sea West. */
  val vygMouth: LatLong = 64.53 ll 34.78
  val kohezmaNorth: LatLong = 64.38 ll 35.61
  val whiteSeaCoast: LinePathLL = LinePathLL(FinlandNorth.southEast, vygMouth, kohezmaNorth, Baltland.onezhsky)

  val pusunsaari: LatLong = 61.55 ll 31.43
  val ladogaNorth: LatLong = 61.61 ll 30.92
  val ladogaNW: LatLong = 61.17 ll 29.98
  val ladozhskiy: LatLong = 60.02 ll 31.12
  val medvezhyegorskNorth: LatLong = 62.91 ll 34.49
  val medvezhyegorskSouth: LatLong = 62.89 ll 34.44
  val onega11: LatLong = 62.27 ll 35.60
  val onega13: LatLong = 61.89 ll 35.32
  val onega15: LatLong = 62.02 ll 34.73
  val petrozavodsk: LatLong = 61.81 ll 34.55
  val shcheleyki: LatLong = 61.14 ll 35.70

  override val polygonLL: PolygonLL = LinePathLL(Baltland.piterland, lisyNos, laskovyy, ozerki, baltiyets, helsinki, hanko,
    /*East Baltic */ p10, kimitoonSE, hyppeis, lyperto, pooskeri, sidebySW, wVaasa, vasankariWest, oulu, olhava, FinlandNorth.balticNE) ++ whiteSeaCoast |++|
    LinePathLL(Baltland.chelmuzhiEast, medvezhyegorskNorth, medvezhyegorskSouth, onega11, onega13, onega15, petrozavodsk, shcheleyki,
    Baltland.svirMouth, Baltland.ladogaEast, pusunsaari, ladogaNorth, ladogaNW, ladozhskiy, Baltland.nevaMouth)
}


/** [[polygonLL]] graphical representation of middle (between north and south) Sweden and middle Norway. Depends on [[SwedenNorth]] and [[SwedenSouth]]. */
object SwedenMid extends EarthArea("Sweden Middle", 64.883 ll 17.125, hillyTaiga)
{ val hertsonEast: LatLong = 65.53 ll 22.39
  val ostanbackSouth: LatLong = 64.82 ll 21.15
  val eLappviken: LatLong = 64.44 ll 21.60
  val skeppsMalen: LatLong = 63.19 ll 19.03
  val skeppshamnSouth: LatLong = 62.38 ll 17.74
  val spikarna: LatLong = 62.36 ll 17.53
  val bredsand: LatLong = 62.35 ll 17.37
  val junibosand: LatLong = 62.23 ll 17.65
  val holick: LatLong = 61.62 ll 17.48

  val balticWestCoast: LinePathLL = LinePathLL(SwedenNorth.balticNW, hertsonEast, ostanbackSouth, eLappviken, skeppsMalen, skeppshamnSouth, spikarna, bredsand, junibosand,
    holick, SwedenSouth.gavie)

  /** Start of South Coast. */
  val hvasser: LatLong = 59.07 ll 10.47
  val nevlunghavn: LatLong = 58.96 ll 9.85
  val lindesnes: LatLong = 57.98 ll 7.05
  val flekkeroy: LatLong = 58.06 ll 8.00
  val borhag: LatLong = 58.11 ll 6.55

  /** Start of West Coast. */
  val bryne: LatLong = 58.75 ll 5.49
  val rennesoy: LatLong = 59.12 ll 5.56
  val swKarmoy: LatLong = 59.14 ll 5.19
  val ytreSula: LatLong = 61.04 ll 4.63
  val bremangerlandet: LatLong = 61.85 ll 4.82
  val wRunde: LatLong = 62.41 ll 5.58
  val bud: LatLong = 62.910 ll 6.903
  val svelllingen: LatLong = 63.79 ll 8.68
  val uthaug: LatLong = 63.72 ll 9.55
  val p75: LatLong = 64.885 ll 10.728

  override val polygonLL: PolygonLL = balticWestCoast |++| LinePathLL(/* South Coast */SwedenSouth.oslo, hvasser, nevlunghavn, flekkeroy, lindesnes, borhag,
    /* West Coast */ bryne, rennesoy, swKarmoy, ytreSula, bremangerlandet, wRunde, bud, svelllingen, uthaug, p75, SwedenNorth.vegaoyan)
}