/* Copyright 2018-21 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package pEarth; package pEurope
import geom._, pglobe._, WTile._

object Balkans extends EarthLevel2("Balkans", 43.9 ll 22.1, plain)
{ val odessa = 46.48 ll 30.74
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
   
  val polygonLL: PolygonLL = PolygonLL(Alpsland.monfalcone, Alpsland.zagreb, odessa, burgas, istanbul, seddElBahr, thessalonika, nEuboea, sEuboea,
     sAttica, Peloponnese.ePeninsular, Peloponnese.nPeninsular, monstrika, oxia,seLefkada, wCorfu, vlore, shengjin, dubrovnik, paleniOtoci,
     puntaPlanka, b1, zadar, matulji, pula, basanija, trieste)
}

object Crimea extends EarthLevel2("Crimea", 45.33 ll 34.15, plain)
{ val henichesk = 46.17 ll 34.82
  val kerch = 45.39 ll 36.63
  val crimeaS = 44.39 ll 33.74
  val crimeaW = 45.40 ll 32.48
  val crimeaNW = 45.93 ll 33.76
  val polygonLL: PolygonLL = PolygonLL(henichesk, kerch, crimeaS, crimeaW, crimeaNW)
}

object Ukraine extends EarthLevel2("Ukraine", 50 ll 24, plain)
{ val caspianWLat = 44.53.north
  val asiaMinorNM = caspianWLat ll 38.09
  val caspianW = caspianWLat ll 46.65
  val llich = 45.41 ll 36.76
  val rostov = 47.17 ll 39.29
   
  val koblev = 46.63 ll 31.18
   
  val polygonLL: PolygonLL = PolygonLL(Baltland.southEast, caspianW, asiaMinorNM, llich, rostov, Crimea.henichesk, Crimea.crimeaNW, koblev,
     Balkans.odessa, Polandia.cenEast)
}

object Anatolia extends EarthLevel2("AsiaMinor", 39.46 ll 33.07, hills)
{ val sinopeN = 42.09 ll 34.99
  val bodrum = 37.06 ll 27.35
  val surmene = 40.91 ll 40.12
  val cizre = 37.30 ll 42.15
  val damascus = 33.51 ll 36.82
  val eilat = 29.54 ll 34.98
  val sSinai = 27.73 ll 34.25
   
  val eGaza = 31.32 ll 34.22
  val yukanbumaz = 36.94 ll 36.04
  val polygonLL: PolygonLL = PolygonLL( Balkans.istanbul, sinopeN, surmene, cizre, damascus, eilat, sSinai, SaharaEast.suez, SaharaEast.portSaid, eGaza,
     yukanbumaz, bodrum, Balkans.seddElBahr)
}

object Caucasus extends EarthLevel2("Caucasus", 42.0 ll 45.0, hills)
{ val blackSeaE = 41.84 ll 41.77
  val sumqayit = 40.64 ll 49.55
  val baku = 40.44 ll 50.21
  val sangachal = 40.18 ll 49.47
  val asiaMinorE = 50.03.east
  val caspianSW = 37.41.north * asiaMinorE
  val polygonLL: PolygonLL = PolygonLL(Anatolia.surmene, blackSeaE, Ukraine.asiaMinorNM, Ukraine.caspianW, sumqayit, baku, sangachal, caspianSW,
     Anatolia.cizre)
}