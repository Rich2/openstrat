/* Copyright 2018-24 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package pEarth; package pEurope
import geom._, pglobe._, egrid._, WTiles._

/** [[polygonLL]] graphical representation of Kola Peninsula. Depends on nothing. */
object KolaPeninsula extends EArea2("KolaPeninsula", 67.80 ll 36.52, tundra)
{ val northWest: LatLong = 69.50 ll 31.81
  val p5: LatLong = 69.95 ll 31.93
  val p7: LatLong = 69.73 ll 33.10
  val p15: LatLong = 69.46 ll 32.90
  val tulomaMouth: LatLong = 69.33 ll 33.56
  val ostrov: LatLong = 69.09 ll 36.27
  val mayakGorodetsky: LatLong = 67.70 ll 40.95
  val ponoyNorth: LatLong = 67.44 ll 41.07
  val ponoyEast: LatLong = 67.11 ll 41.36
  val sosnovka: LatLong = 66.53 ll 40.68
  val mayakNikodimsky: LatLong = 66.10 ll 39.11
  val tetrino: LatLong = 66.06 ll 38.24
  val olenitsa: LatLong = 66.42 ll 35.37
  val umbaWest: LatLong = 66.71 ll 33.52
  val luvenga: LatLong = 67.08 ll 32.75
  val kandalasaksha: LatLong = 67.13 ll 32.26

  override val polygonLL = PolygonLL(northWest, p5, p7, p15, tulomaMouth, ostrov, mayakGorodetsky, ponoyNorth, ponoyEast, sosnovka, mayakNikodimsky,
    tetrino, olenitsa, umbaWest, luvenga, kandalasaksha)
}

/** [[polygonLL]] graphical representation of Finland. Depends on [[KolaPeninsula]], [[SwedenNorth]] and [[Baltland]]. */
object Finlandia extends EArea2("Finlandia", 65.56 ll 29.95, taiga)
{ val lisyNos = 60.01 ll 29.96
  val laskovyy = 60.15 ll 29.92
  val ozerki = 60.18 ll 29.01
  val baltiyets = 60.61 ll 28.38
  val helsinki = 60.15 ll 24.94
  val hanko = 59.82 ll 22.94

  /** Startof East Baltic Coast. */
  val point1 = 59.92 ll 22.89
  val kimitoonSE = 60.01 ll 22.76
  val hyppeis = 60.22 ll 21.26
  val lyperto = 60.61 ll 21.16
  val pooskeri = 61.80 ll 21.43
  val sidebySW = 61.99 ll 21.28
  val wVaasa = 63.11 ll 21.47
  val vasankariWest = 64.34 ll 23.93
  val oulu = 65.00 ll 25.41
  val olhava = 65.46 ll 25.33

  /** Start of Barents Sea. */
  val svaerholt = 70.96 ll 26.67
  val vardo = 70.36 ll 31.12
  val karlebotn = 70.11 ll 28.57

  /** Start of White Sea West. */
  val kovdaEast = 66.66 ll 33.24
  val keretEast = 66.22 ll 34.09
  val kalgalakshaEast = 65.75 ll 35.02
  val kuzemaEast = 65.32 ll 34.51
  val vygMouth = 64.53 ll 34.78
  val kohezmaNorth = 64.38 ll 35.61

  val pusunsaari = 61.55 ll 31.43
  val ladogaNorth = 61.61 ll 30.92
  val ladogaNW = 61.17 ll 29.98
  val ladozhskiy = 60.02 ll 31.12
  val medvezhyegorskNorth = 62.91 ll 34.49
  val medvezhyegorskSouth = 62.89 ll 34.44
  val onega11 = 62.27 ll 35.60
  val onega13 = 61.89 ll 35.32
  val onega15 = 62.02 ll 34.73
  val petrozavodsk = 61.81 ll 34.55
  val shcheleyki = 61.14 ll 35.70

  override val polygonLL = PolygonLL(Baltland.piterland, lisyNos, laskovyy, ozerki, baltiyets, helsinki, hanko,
    /*East Baltic */point1, kimitoonSE, hyppeis, lyperto, pooskeri, sidebySW, wVaasa, vasankariWest, oulu, olhava, SwedenNorth.haparanda,
    /*Barents Sea */ SwedenNorth.lakselv, svaerholt, vardo, karlebotn, KolaPeninsula.northWest, KolaPeninsula.kandalasaksha,
    /* White Sea west */kovdaEast, keretEast, kalgalakshaEast, kuzemaEast, vygMouth, kohezmaNorth,
    Baltland.onezhsky, Baltland.chelmuzhiEast, medvezhyegorskNorth, medvezhyegorskSouth, onega11, onega13, onega15, petrozavodsk, shcheleyki,
    Baltland.svirMouth, Baltland.ladogaEast, pusunsaari, ladogaNorth, ladogaNW, ladozhskiy, Baltland.nevaMouth)
}


/** [[polygonLL]] graphical representation of north Sweden. Depends on [[SwedenSouth]]. */
object SwedenNorth extends EArea2("SwedenNorth", 62.75 ll 14.30, taiga)
{ val haparanda: LatLong = 65.77 ll 24.17
  val ranea: LatLong = 65.86 ll 22.36

  /** Start of West Baltic Coast. */
  val hertsonEast = 65.53 ll 22.39
  val ostanbackSouth = 64.82 ll 21.15
  val eLappviken = 64.44 ll 21.60
  val skeppsMalen = 63.19 ll 19.03
  val skeppshamnSouth = 62.38 ll 17.74
  val spikarna = 62.36 ll 17.53
  val bredsand = 62.35 ll 17.37
  val junibosand = 62.23 ll 17.65
  val holick = 61.62 ll 17.48

  /** Start of South Coast. */
  val hvasser = 59.07 ll 10.47
  val nevlunghavn = 58.96 ll 9.85
  val lindesnes = 57.98 ll 7.05
  val flekkeroy = 58.06 ll 8.00
  val borhag = 58.11 ll 6.55

  /** Start of West Coast. */
  val bryne = 58.75 ll 5.49
  val rennesoy = 59.12 ll 5.56
  val swKarmoy = 59.14 ll 5.19
  val ytreSula = 61.04 ll 4.63
  val bremangerlandet = 61.85 ll 4.82
  val wRunde = 62.41 ll 5.58
  val bud = 62.910 ll 6.903
  val svelllingen = 63.79 ll 8.68
  val uthaug = 63.72 ll 9.55
  val p75 = 64.885 ll 10.728
  val p78 = 66.011 ll 12.146
  val bodo = 67.26 ll 14.32
  val hadseloya = 68.56 ll 14.63
  val nordskot = 67.82 ll 14.70
  val baroya = 68.33 ll 16.03

  /** Start of North Coast */
  val sorvagen = 67.83 ll 12.82
  val andenes = 69.32 ll 16.11
  val gapoyholman = 68.88 ll 16.06
  val sandsvika = 69.37 ll 16.87
  val torsvag = 70.28 ll 19.59
  val nordkapp = 71.16 ll 25.78
  val lakselv = 70.05 ll 25.00

  override val polygonLL = PolygonLL(haparanda, ranea, hertsonEast, ostanbackSouth, eLappviken, skeppsMalen, skeppshamnSouth, spikarna, bredsand, junibosand,
    holick, SwedenSouth.gavie,
    /* South Coast */SwedenSouth.oslo, hvasser, nevlunghavn, flekkeroy, lindesnes, borhag,
    /* West Coast */bryne, rennesoy, swKarmoy, ytreSula, bremangerlandet, wRunde, bud, svelllingen, uthaug, p75, p78, bodo, nordskot, baroya,
    /* North Coast */sorvagen, andenes, gapoyholman, sandsvika, torsvag, nordkapp, lakselv)
}