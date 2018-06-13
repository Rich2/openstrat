/* Copyright 2018 Richard Oliver. Licensed under Apache Licence version 2.0 */
package rich
package geom
package pEarth
import Terrain._
import pEuropeWest._

 object Balkans extends Area2('Balkans, 43.9 ll 22.1, plain)
{
   val odessa = 46.48 ll 30.74
   val burgas = 42.51 ll 27.58
   val istanbul = 41.21 ll 29.04
   val seddElBahr = 40.06 ll 26.18
   val thessalonika = 40.65 ll 22.9
   val nEuboea = 39.03 ll 23.31
   val sEuboea = 37.95 ll 24.51
   val sAttica = 37.64 ll 24.02
   
   val monstrika = 38.40 ll 21.92
   
   val oxia = 38.31 ll 21.15
   val seLefkada = 38.56 ll 20.54
   val wCorfu = 39.75 ll 19.64
   val vlore = 40.30 ll 19.38   
   val shengjin = 41.80 ll 19.59
   val dubrovnik = 42.65 ll 18.06
   val paleniOtoci = 43.16 ll 16.33
   val puntaPlanka = 43.49 ll 15.97
   val b1 = 43.64 ll 15.90
   val zadar = 44.12 ll 15.22
   val matulji = 45.35 ll 14.32
   val pula = 44.81 ll 13.91
   val basanija = 45.48 ll 13.48
   val trieste = 45.70 ll 13.73
   
   val latLongs = LatLongs(Alpsland.monfalcone, Alpsland.zagreb, odessa, burgas, istanbul, seddElBahr,
               thessalonika, nEuboea, sEuboea, sAttica, Peloponnese.ePeninsular, Peloponnese.nPeninsular,
                monstrika, oxia,seLefkada, wCorfu, vlore, shengjin, dubrovnik, paleniOtoci, puntaPlanka,
                b1, zadar, matulji, pula, basanija, trieste)   
}

object Finlandia  extends Area2('Scandanavia, 65.56 ll 29.95, taiga)
{
   val helsinki = 60.15 ll 24.94
   val neGOfFinland = 60.64 ll 28.49
   val swFinland = 60.30 ll 21.91
   val wVaasa = 63.11 ll 21.47
   val oulu = 65.00 ll 25.41
   val olhava = 65.46 ll 25.33
   
   val svaerholt = 70.96 ll 26.67
   val vardo = 70.36 ll 31.12
   val karlebotn = 70.11 ll 28.57
   val tulomaMouth = 69.33 ll 33.56   
   val wMurmanask = 67.11 ll 41.28
   val sMurmansk = 66.07 ll 38.44   
   val kandalasaksha = 67.13 ll 32.26
   
   
   val latLongs = LatLongs(EuropePts.stPetersburg, neGOfFinland, helsinki, swFinland, wVaasa, oulu, olhava, Scandanavia.haparanda,
         Scandanavia.lakselv, svaerholt, vardo, karlebotn, tulomaMouth, wMurmanask, sMurmansk, kandalasaksha, EuropePts.onezhsky)
}

object EuropeCentral extends Area2('CEurope, 50.07 ll 20.13, plain)
{
   val mielno = 54.26 ll 16.06
   val jaroslawiec = 54.54 ll 16.53
   val jastrzebia = 54.83 ll 18.33
   val wladyslawowo = 54.79 ll 18.42
   val danzig = 54.39 ll 18.65
   val kaliningrad = 54.93 ll 21.26
   val latLongs = LatLongs(Germania.swinoujscie, mielno, jaroslawiec, jastrzebia, wladyslawowo, danzig, kaliningrad, Balkans.odessa,
         Alpsland.zagreb, Alpsland.vienna)
}

object Crimea extends Area2('Crimea, 45.33 ll 34.15, plain)
{
   val henichesk = 46.17 ll 34.82
   val kerch = 45.39 ll 36.63
   val crimeaS = 44.39 ll 33.74
   val crimeaW = 45.40 ll 32.48
   val crimeaNW = 45.93 ll 33.76
   val latLongs = LatLongs(henichesk, kerch, crimeaS, crimeaW, crimeaNW)
}

object EuropePts
{
   import LatLong._
   
   val noarootsi = deg(59.2, 23.5)
   val nRusS = 54.28.north   
   
   val cEuropeE = 30.26.east
   val stPetersburg = 59.91.north * cEuropeE   
          
   val onezhsky = 63.79 ll 37.35 
   val nRusSW = nRusS * 44.east
   val caspianWLat = 44.53.north
   val asiaMinorNM = caspianWLat ll 38.09 
   val caspianW = caspianWLat ll 46.65   
   val rostov = deg(47.17, 39.29)   
   
   val koblev = deg (46.63, 31.18)     
   
   val eEurope = Area2('CEurope, deg(50, 24), plain, EuropeCentral.kaliningrad, noarootsi, stPetersburg,onezhsky, nRusSW,
               caspianW, asiaMinorNM, rostov, Crimea.henichesk, Crimea.crimeaNW, koblev, Balkans.odessa)               
       
   val sinopeN = deg(42.09, 34.99)
   val bodrum = deg(37.06, 27.35)
   val surmene = deg(40.91, 40.12)
   val cizre = 37.30 ll 42.15
   val damascus = 33.51 ll 36.82
   val eilat = deg(29.54, 34.98)
   val sSinai = deg(27.73, 34.25)
   
   val eGaza = deg(31.32, 34.22) 
   val yukanbumaz = deg(36.94, 36.04)
   val asiaMinor = Area2('AsiaMinor, 39.46 ll 33.07, hills, Balkans.istanbul, sinopeN, surmene, cizre, damascus,
         eilat, sSinai, SaharaEast.suez, SaharaEast.portSaid, eGaza, yukanbumaz, bodrum, Balkans.seddElBahr)
   
   val blackSeaE = deg(41.84, 41.77)
   val sumqayit = deg(40.64, 49.55)
   val baku = deg(40.44, 50.21)
   val sangachal = deg(40.18, 49.47)
   val asiaMinorE = 50.03.east
   val caspianSW = 37.41.north * asiaMinorE  
   val caucasus = Area2('Caucasus, 42.0 ll 45.0, hills, surmene, blackSeaE, asiaMinorNM, EuropePts.caspianW,
         sumqayit, baku, sangachal, caspianSW, cizre)   
}
