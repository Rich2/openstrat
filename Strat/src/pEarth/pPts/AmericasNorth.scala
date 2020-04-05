/* Copyright 2018 Richard Oliver. Licensed under Apache Licence version 2.0 */
package ostrat
package pEarth
package pPts
import geom._, LatLong._, WTile._

object AmericasNorth extends Area1("AmericasNorth", 49 ll -100)
{  
   val w49th = deg(49, -125.66)
   val yakut = deg(59.93, -141.03)
   val swAlaska = deg(60.50, -164.55)
   val nwAlaska = deg(70.11, -161.87)
   val wCanadaE = 110.west
   val wCanadaEN = 72.97.north * wCanadaE   
   val wCanadaES = 49.north * wCanadaE
   
   val wCanada = Area2("WCanada", deg(64.051, -129.98), taiga, w49th, yakut, swAlaska, nwAlaska, wCanadaEN, wCanadaES)  

   val nwPass = deg(69.5, -82.82)
   val eggIsland = deg(59.91, -94.85)
   val jamesBayNW = deg(55.07, -82.31)
   val jamesBayS = deg(51.14, -79.79)
   val hudsonBayMouthE = deg(62.57, -77.99)
   val ungavaW = deg(61.04, -69.56)
   val ungavaS = deg(58.26, -67.45)   
   val h49th80 = deg(49, -80)
   
      /** Camden County Georgia USA */    
   val NAtlanticSW = 31 ll  -81.47
   val ungavaE = deg(60.49, -64.74)
   val labradorE = deg(52.42, -56.05)
   val newFoundlandE = deg(47.52, -52.64)
   //val e49th = deg(49, -64.41) Removed as Newfoundland is the close
   val maineE = deg(44.87, -66.93)     
   val eNovaScotia = 46.16 ll -59.86
   
   val eCanadaCoast = List(ungavaE, labradorE, newFoundlandE, eNovaScotia)
   
   val eCanada = new Area2("ECanada", deg(53.71, -94), taiga)
   {
      override val latLongs: LatLongs = (List(wCanadaEN, nwPass, eggIsland, jamesBayNW, jamesBayS, hudsonBayMouthE, ungavaW, ungavaS) :::
            eCanadaCoast ::: List(maineE, h49th80, wCanadaES)).toArrProdHomo
   }
         
   val cAmericaN =  22.8.north  
   val cAmericaNW= cAmericaN ll -105.97
   val sanDiego = deg(32.57, -117.11)
   val humboldt = 40.44 ll -124.40
   val ensenada = 31.74 ll -116.73
   val seFlorida = deg(25.34, -80.39)
   val swFlorida = deg(25.19, -81.13)
   val nwFlorida = deg(30.14, -84.06)
   val galveston = 29.31 ll -94.77
   val rockyPoint = 31.16 ll -113.02
   
   val montague = 31.70 ll -114.71
   
   
   lazy val usa = Area2("UnitedStates", deg(39.8, -98.6), plain,
         /*cAmericaNW, */ sanDiego, humboldt, w49th, wCanadaES, h49th80, maineE,
         NAtlanticSW, seFlorida, swFlorida, nwFlorida, galveston, rockyPoint, montague)
   
   val cabotPulmo = 23.37 ll -109.44
   val sanLucas = 22.87 ll -109.91
   val wBaja = 27.84 ll -115.07
   val baja = Area2("Baja", 27.80 ll -113.31, plain, sanDiego, montague, cabotPulmo, sanLucas, wBaja)      
   
   val mariato = deg(7.22, -80.88)
   val quebrada = deg(8.04, -82.88)
   val swGuatemala = deg(14.55, -92.21)
   val pochutala = deg(15.76, -96.50)
   val manzanillo = deg(19.15, -104)   
   
   val brownsville = deg(25.98, -97.26)
  // val cAmericaNE= cAmericaN ll -97.79
         
   val sePanama = deg(7.26, -77.9)      
   val coatz = deg(18.13, -94.5)
   val champeton = deg(19.36, -90.71)
   val nwYucatan =deg(21.01, -90.3)
   val neYucatan = deg(21.48, -86.97)
   val seBelize = deg(15.88, -88.91)
   val eHonduras = deg(15.0, -83.17)
   val kusapin = deg(8.79, -81.38)
   val stIsabel = deg(9.53, -79.25)
   val stIgnacio = deg(9.26, -78.12)
   val nePanama = deg(8.43, -77.26) 
   val cAmerica = Area2("CAmerica", deg(17.31, -94.16), jungle,
         sePanama, mariato, quebrada, swGuatemala, pochutala, manzanillo, cAmericaNW, rockyPoint, galveston, brownsville,
          coatz, champeton, nwYucatan, neYucatan, seBelize, eHonduras, kusapin, stIsabel, stIgnacio, nePanama)
   
   val wCuba = 21.86 ll -84.95
   val havana = 23.14 ll -82.39
   val eCuba = 20.22 ll -74.13
   val cabotCruz = 19.84 ll -77.73
   val yara = 20.45 ll -77.07
   val surgidero = 22.68 ll -82.29
   val cuba = Area2("Cuba", 21.97 ll -78.96, jungle, wCuba, havana, eCuba, cabotCruz, yara, surgidero)
         
   type A2Type = Area2   
   
   override val a2Seq = Refs(usa, wCanada, eCanada, baja, cAmerica, cuba)
   
}