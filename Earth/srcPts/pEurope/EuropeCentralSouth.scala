/* Copyright 2018-23 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package pEarth; package pEurope
import geom._, pglobe._

/** [[PolygonLL]] graphic depends on [[Peloponnese]]. */
object Greece extends EArea2("Greece", 39.54 ll 21.62, Hilly)
{ val northEast = 40.46 ll 22.59
  val p20 = 39.19 ll 23.35
  val nEuboea = 39.03 ll 23.31
  val p25 = 38.65 ll 24.15
  val capeKafireas = 38.16 ll 24.60
  val sEuboea = 37.95 ll 24.51
  val p30 = 38.00 ll 24.03
  val sAttica = 37.64 ll 24.02
  val monstrika = 38.40 ll 21.92
  val oxia = 38.31 ll 21.15
  val p70 = 38.50 ll 21.03

  val lefkadaSE = 38.56 ll 20.54
  val lefkadaNE = 38.83 ll 20.65
  val p71 = 38.86 ll 20.79
  val kanali = 39.07 ll 20.69
  val p73 = 39.54 ll 20.15
  val p75 = 39.69 ll 19.98
  val p77 = 39.87 ll 20.01
  val vlore = 40.30 ll 19.38
  val northWest = 40.67 ll 19.32

  override val polygonLL: PolygonLL = PolygonLL(northEast, p20, nEuboea, p25, capeKafireas, sEuboea, p30, sAttica,Peloponnese.ePeninsular,
    Peloponnese.nPeninsular, monstrika, oxia, p70, lefkadaSE, lefkadaNE, p71, kanali, p73, p75, p77, vlore, northWest)
}

/** Balkans polygon depends on [[Alpsland]] and [[Greece]]. */
object BalkansWest extends EArea2("BalkansWest", 44.0 ll 19.65, Hilly)
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
object BalkansEast extends EArea2("BalkansEast", 44.0 ll 25.5, Plain)
{ val odessa = 46.48 ll 30.74
  val ochakivskeMouth = 45.46 ll 29.78
  val p10 = 44.84 ll 29.59
  val p12 = 44.76 ll 29.11
  val capekaliakra = 43.36 ll 28.47
  val p20 = 43.38 ll 28.10
  val burgas = 42.51 ll 27.58
  val p25 = 41.60 ll 28.13
  val bosphorusN = 41.22 ll 29.13
  val seddElBahr = 40.06 ll 26.18
  val p40 = 40.32 ll 26.22
  val p47 = 40.66 ll 26.79
  val p50 = 40.61 ll 26.13
  val p55 = 40.84 ll 25.96
  val thasosS = 40.57 ll 24.64
  val p62 = 40.74 ll 24.06
  val p66 = 40.53 ll 23.91
  val p70 = 40.14 ll 24.40
  val p80 = 39.91 ll 23.74
  val p85 = 40.48 ll 22.82
  val thessalonika = 40.65 ll 22.9

  override val polygonLL: PolygonLL = PolygonLL(BalkansWest.northEast, odessa, ochakivskeMouth, p10, p12, capekaliakra, p20, burgas, p25, bosphorusN,
    MarmaraSea.istanbul, MarmaraSea.north, MarmaraSea.tekirdag, MarmaraSea.p70, MarmaraSea.dardanellesE, seddElBahr, p40, p47, p50, p55, thasosS, p62,
    p66, p70, p80, p85, thessalonika, Greece.northEast)
}

/** [[PolygonLL]] graphic for the Peloponnese, depends on nothing. */
object Peloponnese extends EArea2("Peloponnese", 37.56 ll 22.10, Hilly)
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

/** [[PolygonLL]] graphic for Marmara Sea, depends on nothing. */
object MarmaraSea extends EArea2("Marmara", 40.73 ll 28.21, Seas)
{ val istanbul = 41.00 ll 29.00
  val darica = 40.76 ll 28.36
  val bandirama = 40.35 ll 27.96
  val dardanellesE = 40.23 ll 26.74
  val p70 = 40.85 ll 27.45
  val tekirdag = 40.97 ll 27.51
  val north = 41.08 ll 28.23

  override val polygonLL: PolygonLL = PolygonLL(istanbul, darica, bandirama, dardanellesE, p70, tekirdag, north)
}