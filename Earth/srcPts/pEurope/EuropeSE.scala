/* Copyright 2018-23 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package pEarth; package pEurope
import geom._, pglobe._, WTile._

/** [[PolygonLL]] graphic depends on [[Peloponnese]]. */
object Greece extends EArea2("Greece", 39.54 ll 21.62, hills)
{ val northEast = 40.46 ll 22.59
  val nEuboea = 39.03 ll 23.31
  val sEuboea = 37.95 ll 24.51
  val sAttica = 37.64 ll 24.02
  val monstrika = 38.40 ll 21.92
  val oxia = 38.31 ll 21.15
  val seLefkada = 38.56 ll 20.54
  val wCorfu = 39.75 ll 19.64
  val vlore = 40.30 ll 19.38
  val northWest = 40.67 ll 19.32

  override val polygonLL: PolygonLL = PolygonLL(northEast, nEuboea, sEuboea, sAttica,Peloponnese.ePeninsular, Peloponnese.nPeninsular, monstrika,
    oxia, seLefkada, wCorfu, vlore, northWest)
}

/** Balkans polygon depends on [[Alpsland]] and [[Greece]]. */
object BalkansWest extends EArea2("BalkansWest", 44.0 ll 19.65, hills)
{ val northEast = 46.0 ll 22.59
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

  override val polygonLL: PolygonLL = PolygonLL(northEast, Greece.northEast, Greece.northWest, shengjin, dubrovnik, paleniOtoci, puntaPlanka, b1, zadar, matulji, pula,
    basanija, trieste, Alpsland.monfalcone, Alpsland.zagreb,
  )
}

/** [[PolygonLL]] graphic for east Balkans depends on [[BalkansWest]], [[Alpsland]] and [[Greece]]. */
object BalkansEast extends EArea2("BalkansEast", 44.0 ll 25.5, plain)
{ val odessa = 46.48 ll 30.74
  val ochakivskeMouth = 45.46 ll 29.78
  val p10 = 44.84 ll 29.59
  val p12 = 44.76 ll 29.11
  val capekaliakra = 43.36 ll 28.47
  val p20 = 43.38 ll 28.10
  val burgas = 42.51 ll 27.58
  val p25 = 41.60 ll 28.13
  val bosphorusN = 41.22 ll 29.13

  val istanbul = 41.00 ll 29.00
  val marmaraN = 41.08 ll 28.23
  val dardanellesE = 40.23 ll 26.74
  val seddElBahr = 40.06 ll 26.18
  val thessalonika = 40.65 ll 22.9

  override val polygonLL: PolygonLL = PolygonLL(BalkansWest.northEast, odessa, ochakivskeMouth, p10, p12, capekaliakra, p20, burgas, p25, bosphorusN,
    istanbul, marmaraN, dardanellesE, seddElBahr, thessalonika, Greece.northEast)
}

/** [[PolygonLL]] grahic for Crimea depends on nothing. */
object Crimea extends EArea2("Crimea", 45.33 ll 34.15, plain)
{ val henichesk = 46.17 ll 34.82
  val kamyanske = 45.28 ll 35.53
  val kerch = 45.39 ll 36.63
  val southEast = 45.10 ll 36.45
  val p40 = 44.79 ll 35.08
  val south = 44.39 ll 33.74
  val p60 = 44.58 ll 33.38
  val west = 45.40 ll 32.48
  val p80 = 45.93 ll 33.76
  val northWest = 46.16 ll 33.59
  val polygonLL: PolygonLL = PolygonLL(henichesk, kamyanske, kerch, southEast, p40, south, p60, west, p80, northWest)
}

object Ukraine extends EArea2("Ukraine", 49 ll 34, plain)
{ //val caspianWLat = 44.53.north

  val caspianW = 44.53 ll 46.65

  val rostov = 47.17 ll 39.29

  val koblev = 46.63 ll 31.18

  override val polygonLL: PolygonLL = PolygonLL(Baltland.southEast, caspianW, rostov, Crimea.henichesk, Crimea.northWest, koblev,
     BalkansEast.odessa, Polandia.cenEast)
}

/** [[PolygonLL]] graphic depends on nothing. */
object Peloponnese extends EArea2("Peloponnese", 37.56 ll 22.10, hills)
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
  override val polygonLL: PolygonLL = PolygonLL(BalkansEast.istanbul, middleEast.Anatolia.darica, middleEast.Anatolia.bandirama, BalkansEast.dardanellesE, BalkansEast.marmaraN)
}