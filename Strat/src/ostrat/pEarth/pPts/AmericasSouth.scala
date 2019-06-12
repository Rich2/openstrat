/* Copyright 2018 Richard Oliver. Licensed under Apache Licence version 2.0 */
package ostrat
package pEarth
package pPts
import geom._, LatLong._, pEarth._, Terrain._

object AmericasSouth extends Area1("SAmericas", -6.52 ll -62.28)
{      
   val swArgentine = -54.28 ll -65.06
   val sChile = -55.26 ll -69.48
   val sSAmericaN: Latitude = 21.south
   val sSAmericaNW = sSAmericaN * 70.16.west
   val nwAmericaE = 58.west 
   val nwSAmericaES = sSAmericaN * nwAmericaE
   val sSAmericaNE = sSAmericaN * 40.81.west
   val saoPaulo = deg(-24, -46)
   val puntaDelEste = deg(-35, -54)
   val buenosAires = deg(-34, -59)
   val sBuenos = deg(-36, -57)
   val fSAmerica = Area2("FSAmerica", deg(-33.51, -65.36), plain, sChile, sSAmericaNW, nwSAmericaES, sSAmericaNE, 
         saoPaulo, puntaDelEste, buenosAires, sBuenos, swArgentine)         
     
   val nChile = deg(-18, -70)   
   val nPeru = deg(-5, -81)   
   
   val nColumbia = 12.19 ll -71.27
   val caracas = deg(11, -71)
   val nwSAmericaEN = 6.77.north * nwAmericaE
   
   val wSAmerica = Area2("WSAmerica", deg(-10.42, -47.78), jungle,
         sSAmericaNW,nChile, nPeru, AmericasNorth.sePanama, AmericasNorth.nePanama, nColumbia, caracas, nwSAmericaEN, nwSAmericaES)   
   
   val nAmapa = deg(4.39, -51.51)
   val amazonMouthS = deg(-0.18, -49.3)
   val paraiba = deg(-7.15, -34.82)
   val eSAmerica = Area2("ESAmerica", deg(-2.04, -70.81), jungle, nwSAmericaEN, nAmapa, amazonMouthS, paraiba, sSAmericaNE, nwSAmericaES)
   
   override val a2Seq = Arr(fSAmerica, wSAmerica, eSAmerica)
         
}