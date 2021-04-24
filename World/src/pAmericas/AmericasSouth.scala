/* Copyright 2018-21 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package pEarth; package pPts
import geom._, LatLong._, pEarth._, WTile._

object AmericasSouth extends EarthLevel1("SAmericas", -6.52 ll -62.28)
{ val swArgentine = -54.28 ll -65.06
  val sChile = -55.26 ll -69.48
  val sSAmericaN: Latitude = 21.south
  val sSAmericaNW = sSAmericaN * 70.16.west
  val nwAmericaE = 58.west
  val nwSAmericaES = sSAmericaN * nwAmericaE
  val sSAmericaNE = sSAmericaN * 40.81.west
  val saoPaulo = degs(-24, -46)
  val puntaDelEste = degs(-35, -54)
  val buenosAires = degs(-34, -59)
  val sBuenos = degs(-36, -57)

  val fSAmerica: EarthLevel2 = EarthLevel2("FSAmerica", degs(-33.51, -65.36), plain,
     sChile, sSAmericaNW, nwSAmericaES, sSAmericaNE, saoPaulo, puntaDelEste, buenosAires, sBuenos, swArgentine)
     
  val nChile = degs(-18, -70)
  val nPeru = degs(-5, -81)
   
  val nColumbia = 12.19 ll -71.27
  val caracas = degs(11, -71)
  val nwSAmericaEN = 6.77.north * nwAmericaE
   
  val wSAmerica: EarthLevel2 = EarthLevel2("WSAmerica", degs(-10.42, -47.78), jungle,
     sSAmericaNW,nChile, nPeru, AmericasNorth.sePanama, AmericasNorth.nePanama, nColumbia, caracas, nwSAmericaEN, nwSAmericaES)
   
  val nAmapa = degs(4.39, -51.51)
  val amazonMouthS = degs(-0.18, -49.3)
  val paraiba = degs(-7.15, -34.82)
  val eSAmerica = EarthLevel2("ESAmerica", degs(-2.04, -70.81), jungle, nwSAmericaEN, nAmapa, amazonMouthS, paraiba, sSAmericaNE, nwSAmericaES)
   
  override val a2Arr: Arr[EarthLevel2] = Arr(fSAmerica, wSAmerica, eSAmerica)
}