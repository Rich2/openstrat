/* Copyright 2018-23 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package pEarth; package pOceans
import geom._, pglobe._, LatLong._, egrid._, WTiles._

/** [[polygonLL]] graphical representation of Sumatra. Depends on nothing. */
object Sumatra extends EArea2("Sumatra", -0.02 ll 101.63, jungle)
{ val nSumatra: LatLong = 5.65 ll 95.43
  val neSumatra: LatLong = 5.24 ll 97.49
  val tanjungDatuk: LatLong = -0.005 ll 103.812
  val eSumatra: LatLong = -3.22 ll 106.09
  val seSumatra: LatLong = -5.90 ll 105.71
  val hutan: LatLong = -5.94 ll 104.58
  val p70 = -2.154 ll 100.820

  override val polygonLL: PolygonLL = PolygonLL(nSumatra, neSumatra, tanjungDatuk, eSumatra, seSumatra, hutan, p70)
}
/** [[polygonLL]] graphical representation of Australia. Depends on nothing. */
object Australia extends EArea2("Australia", -24.45 ll 134.47, sahel)
{ val capeLeeuwin: LatLong = -34.36 ll 115.13
  val wAustralia: LatLong = -22.58 ll 113.95
  val eightyMile: LatLong = -19.41 ll 121.24
  val couloumbPoint: LatLong = -17.30 ll 122.12
  val drysdaleRiver: LatLong = -13.77 ll 126.95
  val victoriaMouth: LatLong = -15.13 ll 129.65
  val thamarrurr: LatLong = -14.42 ll 129.36
  val coxPeninsular: LatLong = -12.41 ll 130.64
  val nAustralia: LatLong = -12.01 ll 133.56
  val eastArnhem: LatLong = -12.31 ll 136.92
  val limmen: LatLong = -14.73 ll 135.36
  val karumba: LatLong = -17.52 ll 140.8
  val nQueensland: LatLong = -11 ll 142.43

  val nKennedy: LatLong = -14.49 ll 143.95
  val capeMelville: LatLong = -14.17 ll 144.51
  val coolbie: LatLong = -18.86 ll 146.27
  val harveyBay: LatLong = -25.29 ll 152.89
  val brisbane: LatLong = -27.05 ll 153.03
  val byronBay: LatLong = -28.64 ll 153.62
  val seAustralia: LatLong = -37.4 ll 149.58

  val wilsonsProm: LatLong = -39.12 ll 146.38
  val barwonHeads: LatLong = -38.27 ll 144.53
  val capeOtway: LatLong = -38.85 ll 143.51
  val portMacdonnell: LatLong = -38.06 ll 140.66
  val carpenterRocks: LatLong = -37.89 ll 140.28
  val capeJaffa: LatLong = -36.96 ll 139.67
  val hardwicke: LatLong = -34.91 ll 137.46
  val portAugusta: LatLong = -32.53 ll 137.77
  val sleaford: LatLong = -34.92 ll 135.64
  val smokyBay: LatLong = -32.52 ll 133.86
  val yalata: LatLong = -31.35 ll 131.21
  val nuytsland1: LatLong = -32.96 ll 124.33
  val nuytsland2: LatLong = -33.86 ll 123.63
  val windyHarbour: LatLong = -34.84 ll 116

  override val polygonLL: PolygonLL = PolygonLL(capeLeeuwin, wAustralia, eightyMile, couloumbPoint, drysdaleRiver, victoriaMouth, thamarrurr,
    coxPeninsular, nAustralia, eastArnhem, limmen, karumba, nQueensland, nKennedy, capeMelville, coolbie, harveyBay, brisbane, byronBay, seAustralia,
    wilsonsProm, barwonHeads, capeOtway, portMacdonnell, carpenterRocks, carpenterRocks, hardwicke, portAugusta, sleaford, smokyBay, yalata,
    nuytsland1, nuytsland2, windyHarbour)
}
/** [[polygonLL]] graphical representation of the North Ilsand of New Zealand. Depends on nothing. */
object NZNorthIsland extends EArea2("NewZealandNIsland", -38.66 ll 176, plain)
{ val capeReinga: LatLong = -34.42 ll 172.68
  val teHapua: LatLong = -34.41 ll 173.05
  val aukland: LatLong = -36.83 ll 174.81
  val eCape: LatLong = -37.69 ll 178.54
  val capePalliser: LatLong = -41.61 ll 175.29
  val makara: LatLong = -41.29 ll 174.62
  val himtangi: LatLong = -40.36 ll 175.22
  val capeEgmont: LatLong = -39.28 ll 173.75

  override val polygonLL: PolygonLL = PolygonLL(capeReinga, teHapua, aukland, eCape, capePalliser, makara, himtangi, capeEgmont)
}

/** [[polygonLL]] graphical representation of the South Island of New Zealand. Depends on nothing. */
object NZSouthIsland extends EArea2("NewZealandSIsland", -43.68 ll 171.00, plain)
{ val swNewZealand: LatLong = -45.98 ll 166.47
  val puponga: LatLong = -40.51 ll 172.72
  val capeCambell: LatLong = -41.73 ll 174.27
  val slopePoint: LatLong = -46.67 ll 169.00

  override val polygonLL: PolygonLL = PolygonLL(swNewZealand, puponga, capeCambell, slopePoint)
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
  val p70 = 2.798 ll 111.333

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
{ val swJava: LatLong = -6.83 ll 105.24
  val nwJava: LatLong = -5.88 ll 106.04
  val ePulauMadura: LatLong = -6.96 ll 114.11
  val seJava: LatLong = -8.75 ll 114.58

  override val polygonLL: PolygonLL = PolygonLL(swJava, nwJava, ePulauMadura, seJava)
}

/** [[polygonLL]] graphical representation of the island of Sumatra. Depends on nothing. */
object NewGuinea extends EArea2("NewGuinea", -5.19 ll 141.03, hillyJungle)
{ val waigeoWest: LatLong = -0.113 ll 130.295
  val waigeoNorth: LatLong = -0.007 ll 130.814
  val manokwari: LatLong = -0.73 ll 133.98
  val sCenderawasih: LatLong = -3.39 ll 135.33
  val tebe: LatLong = -1.46 ll 137.93
  val madang: LatLong = -4.85 ll 145.78
  val p40: LatLong = -5.918 ll 147.339
  val eNewGuinea: LatLong = -10.23 ll 150.87
  val hulaBlackSand: LatLong = -10.103 ll 147.726
  val morigo: LatLong = -7.83 ll 143.98
  val saibai: LatLong = -9.32 ll 142.63
  val southWest: LatLong = -8.431 ll 137.655
  val p70: LatLong = -7.518 ll 138.145
  val aindua: LatLong = -4.46 ll 135.21
  val wNewGuinea: LatLong = -0.82 ll 130.45

  override val polygonLL: PolygonLL = PolygonLL(waigeoWest, waigeoNorth/*, manokwari, sCenderawasih*/, tebe, madang, p40, eNewGuinea, hulaBlackSand,
    morigo, saibai, southWest, p70, aindua, wNewGuinea)
}