/* Copyright 2018-23 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package pEarth; package pMalay
import geom._, pglobe._, egrid._, WTiles._

/** [[polygonLL]] graphical representation of Sumatra. Depends on nothing. */
object Sumatra extends EArea2("Sumatra", -0.02 ll 101.63, jungle)
{ val nSumatra: LatLong = 5.65 ll 95.43
  val neSumatra: LatLong = 5.24 ll 97.49
  val tanjungDatuk: LatLong = -0.005 ll 103.812
  val eSumatra: LatLong = -3.22 ll 106.09
  val seSumatra: LatLong = -5.90 ll 105.71
  val hutan: LatLong = -5.94 ll 104.58
  val p70: LatLong = -2.154 ll 100.820

  override val polygonLL: PolygonLL = PolygonLL(nSumatra, neSumatra, tanjungDatuk, eSumatra, seSumatra, hutan, p70)
}

/** [[polygonLL]] graphical representation of Lesser Sunda Islands except eastern islands. Depends on nothing. */
object LesserSunda extends EArea2("Lesser Sunda", -8.538 ll 118.510, jungle)
{ val baliNW: LatLong = -8.090 ll 114
  val baliNorth: LatLong = -8.061 ll 115.184
  val wetarNorth: LatLong = -7.559 ll 126.627
  val timorEast: LatLong = -8.402 ll 127.288
  val roteSE: LatLong = -10.936 ll 122.848

  override val polygonLL: PolygonLL = PolygonLL(baliNW, baliNorth, wetarNorth, timorEast, roteSE)
}

/** [[polygonLL]] graphical representation of the island of Borneo. Depends on nothing. */
object Borneo extends EArea2("Borneo", 0.63 ll 114.132, jungle)
{ val nBorneo: LatLong = 6.99 ll 117.12
  val northEast: LatLong = 5.382 ll 119.241
  val cenEast: LatLong = 1.022 ll 118.986
  val southEast: LatLong = -4.03 ll 116.09
  val southWest: LatLong = -2.96 ll 110.29
  val p60: LatLong = 0.816 ll 108.841
  val nwSarawak: LatLong = 2.08 ll 109.64
  val batangLuparMouth: LatLong = 1.512 ll 110.988
  val p70: LatLong = 2.798 ll 111.333

  override val polygonLL: PolygonLL = PolygonLL(nBorneo, northEast, cenEast, southEast, southWest, p60, nwSarawak, batangLuparMouth, p70)
}

/** [[polygonLL]] graphical representation of the island of Sulawesi. Depends on nothing. */
object Sulawesi extends EArea2("Sulawesi", -2.16 ll 120.58, jungle)
{ val seSulawesi: LatLong = -5.41 ll 119.38
  val nwSulawesi: LatLong = 0.72 ll 120.06
  val neSulawesi: LatLong = 1.67 ll 125.15
  val ambesia: LatLong = 0.52 ll 120.62
  val poso: LatLong = -1.42 ll 120.68
  val teku: LatLong = -0.76 ll 123.45
  val swSulawesi: LatLong = -5.66 ll 122.78
  val nGulfBoni: LatLong = -2.61 ll 120.81

  override val polygonLL: PolygonLL = PolygonLL(seSulawesi, nwSulawesi, neSulawesi, ambesia, poso, teku, swSulawesi, nGulfBoni)
}

/** [[polygonLL]] graphical representation of the island of Java. Depends on nothing. */
object javaIsland extends EArea2("Java", -7.39 ll 110.03, jungle)
{ val pasuruan: LatLong = -7.627 ll 112.919
  val ePulauMadura: LatLong = -6.96 ll 114.11
  val seJava: LatLong = -8.75 ll 114.58
  val p30: LatLong = -8.382 ll 111.701
  val p40: LatLong = -7.382 ll 106.405
  val p44: LatLong = -6.993 ll 106.377
  val swJava: LatLong = -6.83 ll 105.24
  val nwJava: LatLong = -5.88 ll 106.04
  val p65: LatLong = -5.920 ll 107.007
  val p72: LatLong = -6.245 ll 108.348
  val p75: LatLong = -6.758 ll 108.599
  val p80: LatLong = -6.931 ll 110.462
  val p82: LatLong = -6.448 ll 110.722

  override val polygonLL: PolygonLL = PolygonLL(ePulauMadura, pasuruan, seJava, p30, p40, p44, swJava, nwJava, p65, p72, p75, p80, p82)
}

/** [[polygonLL]] graphical representation of the island of New Guinea. Depends on nothing. */
object NewGuinea extends EArea2("New Guinea", -5.19 ll 141.03, hillyJungle)
{ val waigeoWest: LatLong = -0.113 ll 130.295
  val waigeoNorth: LatLong = -0.007 ll 130.814
  val manokwari: LatLong = -0.73 ll 133.98
  val sCenderawasih: LatLong = -3.39 ll 135.33
  val tebe: LatLong = -1.46 ll 137.93
  val papuaNW = -2.606 ll 141
  val madang: LatLong = -4.85 ll 145.78
  val p40: LatLong = -5.918 ll 147.339
  val east: LatLong = -10.23 ll 150.87

  val hulaBlackSand: LatLong = -10.103 ll 147.726
  val p53: LatLong = -8.067 ll 146.031
  val morigo: LatLong = -7.83 ll 143.98
  val saibai: LatLong = -9.32 ll 142.63
  val p55: LatLong = -9.231 ll 141.135
  val p60 = -8.113 ll 139.951
  val southWest: LatLong = -8.431 ll 137.655
  val p70: LatLong = -7.518 ll 138.145
  val heilwigMouth: LatLong = -5.359 ll 137.866
  val aindua: LatLong = -4.46 ll 135.21
  val p85: LatLong = -4.083 ll 132.915
  val wNewGuinea: LatLong = -0.82 ll 130.45

  override val polygonLL: PolygonLL = PolygonLL(waigeoWest, waigeoNorth, manokwari, sCenderawasih, tebe, papuaNW, madang, p40, east,
    hulaBlackSand, p53, morigo, saibai, p55, p60, southWest, p70, heilwigMouth, aindua, p85, wNewGuinea)
}
