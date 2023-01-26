/* Copyright 2018-23 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package pEarth; package pOceans
import geom._, pglobe._, LatLong._, WTile._

object Ssumatra extends EArea2("Sumatra", -0.02 ll 101.63, jungle)
{  val nSumatra = 5.65 ll 95.43
  val neSumatra = 5.24 ll 97.49
  val eSumatra = -3.22 ll 106.09
  val seSumatra = -5.90 ll 105.71
  val hutan = -5.94 ll 104.58

  override val polygonLL = PolygonLL(nSumatra, neSumatra, eSumatra, seSumatra, hutan)
}

object Australia extends EArea2("Australia", -24.45 ll 134.47, desert)
{ val capeLeeuwin = degs(-34.36, 115.13)
  val wAustralia = degs(-22.58, 113.95)
  val eightyMile = degs(-19.41, 121.24)
  val couloumbPoint = degs(-17.30, 122.12)
  val drysdaleRiver = degs(-13.77, 126.95)
  val victoriaMouth = degs(-15.13, 129.65)
  val thamarrurr = degs(-14.42, 129.36)
  val coxPeninsular = degs(-12.41, 130.64)
  val nAustralia = degs(-12.01, 133.56)
  val eastArnhem = degs(-12.31, 136.92)
  val limmen = degs(-14.73, 135.36)
  val karumba = degs(-17.52, 140.8)
  val nQueensland = degs(-11, 142.43)

  val nKennedy = degs(-14.49, 143.95)
  val capeMelville = degs(-14.17, 144.51)
  val coolbie = degs(-18.86, 146.27)
  val harveyBay = degs(-25.29, 152.89)
  val brisbane = degs(-27.05, 153.03)
  val byronBay = degs(-28.64, 153.62)
  val seAustralia = degs(-37.4, 149.58)

  val wilsonsProm = degs(-39.12, 146.38)
  val barwonHeads = degs(-38.27, 144.53)
  val capeOtway = degs(-38.85, 143.51)
  val portMacdonnell = degs(-38.06, 140.66)
  val carpenterRocks = degs(-37.89, 140.28)
  val capeJaffa = degs(-36.96, 139.67)
  val hardwicke = degs(-34.91, 137.46)
  val portAugusta = degs(-32.53, 137.77)
  val sleaford = degs(-34.92, 135.64)
  val smokyBay = degs(-32.52, 133.86)
  val yalata = degs(-31.35, 131.21)
  val nuytsland1 = degs(-32.96, 124.33)
  val nuytsland2 = degs(-33.86, 123.63)
  val windyHarbour = degs(-34.84, 116)

  override val polygonLL = PolygonLL(capeLeeuwin, wAustralia, eightyMile, couloumbPoint, drysdaleRiver, victoriaMouth, thamarrurr, coxPeninsular,
    nAustralia, eastArnhem, limmen, karumba, nQueensland, nKennedy, capeMelville, coolbie, harveyBay, brisbane, byronBay, seAustralia, wilsonsProm,
    barwonHeads, capeOtway, portMacdonnell, carpenterRocks, carpenterRocks, hardwicke, portAugusta, sleaford, smokyBay, yalata, nuytsland1,
    nuytsland2, windyHarbour)
}

object NewZealandNIsland extends EArea2 ("NewZealandNIsland", - 38.66 ll 176, plain)
{ val capeReinga = -34.42 ll 172.68
  val teHapua = -34.41 ll 173.05
  val aukland = -36.83 ll 174.81
  val eCape = -37.69 ll 178.54
  val capePalliser = -41.61 ll 175.29
  val makara = -41.29 ll 174.62
  val himtangi = -40.36 ll 175.22
  val capeEgmont = -39.28 ll 173.75

  override val polygonLL = PolygonLL(capeReinga, teHapua, aukland, eCape, capePalliser, makara, himtangi, capeEgmont)
}

object NewZealandSIsland extends EArea2("NewZealandSIsland", -43.68 ll 171.00, plain)
{ val swNewZealand = -45.98 ll 166.47
  val puponga = -40.51 ll 172.72
  val capeCambell = -41.73 ll 174.27
  val slopePoint = -46.67 ll 169.00

  override val polygonLL = PolygonLL(swNewZealand, puponga, capeCambell, slopePoint)
}

object Borneo extends EArea2("Borneo", 0.63 ll 114.132, jungle)
{ val nBorneo = 6.99 ll 117.12
  val seBorneo = -4.03 ll 116.09
  val swBorneo = -2.96 ll 110.29
  val nwSarawak = 2.08 ll 109.64

  override val polygonLL = PolygonLL(nBorneo,seBorneo, swBorneo, nwSarawak)
}

object Sulawesi extends EArea2 ("Sulawesi", - 2.16 ll 120.58, jungle)
{
  val seSulawesi = -5.41 ll 119.38
  val nwSulawesi = 0.72 ll 120.06
  val neSulawesi = 1.67 ll 125.15
  val ambesia = 0.52 ll 120.62
  val poso = -1.42 ll 120.68
  val teku = -0.76 ll 123.45
  val swSulawesi = -5.66 ll 122.78
  val nGulfBoni = -2.61 ll 120.81

  override val polygonLL = PolygonLL(seSulawesi, nwSulawesi, neSulawesi, ambesia, poso, teku, swSulawesi, nGulfBoni)
}

object javaIsland extends EArea2("Java", -7.39 ll 110.03, jungle)
{ val swJava = -6.83 ll 105.24
  val nwJava = -5.88 ll 106.04
  val ePulauMadura = -6.96 ll 114.11
  val seJava = -8.75 ll 114.58
  override val polygonLL = PolygonLL(swJava, nwJava, ePulauMadura, seJava)
}

object NewGuinea extends EArea2 ("NewGuinea", - 5.19 ll 141.03, jungle)
{ val wNewGuinea = -0.82 ll 130.45
  val manokwari = -0.73 ll 133.98
  val sCenderawasih = -3.39 ll 135.33
  val tebe = -1.46 ll 137.93
  val madang = -4.85 ll 145.78
  val eNewGuinea = -10.23 ll 150.87
  val morigo = -7.83 ll 143.98
  val saibai = -9.32 ll 142.63
  val aindua = -4.46 ll 135.21

  override val polygonLL = PolygonLL(wNewGuinea, manokwari, sCenderawasih, tebe, madang, eNewGuinea, morigo, saibai, aindua)
}