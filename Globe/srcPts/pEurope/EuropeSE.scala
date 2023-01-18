/* Copyright 2018-22 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package pEarth; package pEurope
import geom._, pglobe._, WTile._

/** Balkans polygon depends on [[Alpsland]] and [[Peloponnese]]. */
object Balkans extends EArea2("Balkans", 43.9 ll 22.1, plain)
{ val odessa = 46.48 ll 30.74
  val burgas = 42.51 ll 27.58
  val bosphorusN = 41.22 ll 29.13
  val istanbul = 41.00 ll 29.00
  val marmaraN = 41.08 ll 28.23
  val dardanellesE = 40.23 ll 26.74
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

  override val polygonLL: PolygonLL = PolygonLL(Alpsland.monfalcone, Alpsland.zagreb, odessa, burgas, bosphorusN, istanbul, marmaraN, dardanellesE,
    seddElBahr, thessalonika, nEuboea, sEuboea, sAttica, Peloponnese.ePeninsular, Peloponnese.nPeninsular, monstrika, oxia,seLefkada, wCorfu, vlore,
    shengjin, dubrovnik, paleniOtoci, puntaPlanka, b1, zadar, matulji, pula, basanija, trieste)
}

object Crete extends EArea2("Crete", 35.23 ll 24.92, hills)
{
  val northEast = 35.32 ll 26.31
  val southEast = 35.02 ll 26.19
  val p10 = 34.92 ll 24.73
  val p15 = 35.09 ll 24.72
  val p20 = 35.23 ll 23.59
  val p30 = 35.29 ll 23.52
  val capeGramvousa = 35.62 ll 23.60
  override val polygonLL: PolygonLL = PolygonLL(northEast, southEast, p10, p15, p20, p30, capeGramvousa)
}

object Crimea extends EArea2("Crimea", 45.33 ll 34.15, plain)
{ val henichesk = 46.17 ll 34.82
  val kerch = 45.39 ll 36.63
  val crimeaS = 44.39 ll 33.74
  val crimeaW = 45.40 ll 32.48
  val crimeaNW = 45.93 ll 33.76
  val polygonLL: PolygonLL = PolygonLL(henichesk, kerch, crimeaS, crimeaW, crimeaNW)
}

object Ukraine extends EArea2("Ukraine", 49 ll 34, plain)
{ val caspianWLat = 44.53.north
  val asiaMinorNM = caspianWLat ll 38.09
  val caspianW = caspianWLat ll 46.65
  val llich = 45.41 ll 36.76
  val rostov = 47.17 ll 39.29

  val koblev = 46.63 ll 31.18

  override val polygonLL: PolygonLL = PolygonLL(Baltland.southEast, caspianW, asiaMinorNM, llich, rostov, Crimea.henichesk, Crimea.crimeaNW, koblev,
     Balkans.odessa, Polandia.cenEast)
}

object MarmaraSea extends EArea2("Marmara", 40.73 ll 28.21, sea)
{
  override val polygonLL: PolygonLL = PolygonLL(Balkans.istanbul, Anatolia.darica, Anatolia.bandirama, Balkans.dardanellesE, Balkans.marmaraN)
}

object Anatolia extends EArea2("AsiaMinor", 39.46 ll 33.07, hills)
{ val sinopeN = 42.09 ll 34.99
  val bodrum = 37.06 ll 27.35
  val darica = 40.76 ll 28.36
  val bandirama = 40.35 ll 27.96

  val surmene = 40.91 ll 40.12
  val cizre = 37.30 ll 42.15
  val damascus = 33.51 ll 36.82
  val eilat = 29.54 ll 34.98
  val sSinai = 27.73 ll 34.25

  val eGaza = 31.32 ll 34.22
  val yukanbumaz = 36.94 ll 36.04
  val polygonLL: PolygonLL = PolygonLL( Balkans.istanbul, Balkans.bosphorusN, sinopeN, surmene, cizre, damascus, eilat, sSinai, SaharaEast.suez,
    SaharaEast.portSaid, eGaza, yukanbumaz, bodrum, Balkans.seddElBahr, Balkans.dardanellesE, bandirama, darica)
}

object Caucasus extends EArea2("Caucasus", 42.0 ll 45.0, hills)
{ val blackSeaE = 41.84 ll 41.77
  val sumqayit = 40.64 ll 49.55
  val baku = 40.44 ll 50.21
  val sangachal = 40.18 ll 49.47
  val asiaMinorE = 50.03.east

  val polygonLL: PolygonLL = PolygonLL(Anatolia.surmene, blackSeaE, Ukraine.asiaMinorNM, Ukraine.caspianW, sumqayit, baku, sangachal, pAsia.Caspian.southWest,
     Anatolia.cizre)
}