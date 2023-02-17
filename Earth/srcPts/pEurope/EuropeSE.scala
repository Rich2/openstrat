/* Copyright 2018-23 Richard Oliver. Licensed under Apache Licence version 2.0. */
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

object Peloponnese extends EArea2("Peloponnese", 37.56 ll 23.18, hills)
{ val ePeninsular = 38.04 ll 23.56
  val kechries = 37.88 ll 22.99
  val p1 = 37.44 ll 23.51
  val neaKios = 37.58 ll 22.74
  val voia =  36.44 ll 23.17
  val eElos = 36.79 ll 22.78
  val wElos = 36.80 ll 22.61
  val sGreece = 36.38 ll 22.48
  val koroni = 36.73 ll 21.87
  val kyllini = 37.93 ll 21.13
  val rioPio = 38.30 ll 21.77
  val corinth = 37.94 ll 22.93
  val nPeninsular = 38.15 ll 23.22
  val polygonLL: PolygonLL = PolygonLL(ePeninsular, kechries, p1, neaKios, voia, eElos, wElos, sGreece, koroni, kyllini, rioPio, corinth, nPeninsular)
}

object MarmaraSea extends EArea2("Marmara", 40.73 ll 28.21, sea)
{
  override val polygonLL: PolygonLL = PolygonLL(Balkans.istanbul, middleEast.Anatolia.darica, middleEast.Anatolia.bandirama, Balkans.dardanellesE, Balkans.marmaraN)
}