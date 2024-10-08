/* Copyright 2018-23 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package pEarth; package pMed
import geom._, pglobe._, egrid._, WTiles._

/** [[PolygonLL]] graphic for Majorca depends on nothing. */
object Majorca extends EarthArea("Majorca", 39.59 ll 3.01, oceanic)
{ val south: LatLong = 39.26 ll 3.05
  val palma: LatLong = 39.56 ll 2.63
  val portalsVells: LatLong = 39.45 ll 2.51
  val santElm: LatLong = 39.59 ll 2.34
  val capFormentor: LatLong = 39.96 ll 3.21
  val east: LatLong = 39.71 ll 3.47

  override val polygonLL: PolygonLL = PolygonLL(south, palma, portalsVells, santElm, capFormentor, east)
}

/** [[PolygonLL]] graphic for Sicily depends on nothing. */
object Sicily extends EarthArea("Sicily", cen = 37.58 ll 14.27, oceanic)
{ val sSicily: LatLong = 36.66 ll 15.08
  val kartibubbo: LatLong = 37.56 ll 12.67
  val marsala: LatLong = 37.80 ll 12.42
  val calaRossa: LatLong = 38.18 ll 12.73
  val mondello: LatLong = 38.22 ll 13.32
  val n1: LatLong = 37.97 ll 13.74
  val torreFaro: LatLong = 38.26 ll 15.65
  val contradoFortino: LatLong = 38.24 ll 15.58
  val messina: LatLong = 38.18 ll 15.56
  val catania: LatLong = 37.48 ll 15.08

  override val polygonLL: PolygonLL = PolygonLL(sSicily, kartibubbo, marsala, calaRossa, mondello, n1, torreFaro, contradoFortino, messina, catania)
}

/** [[PolygonLL]] graphic for Canaries depends on nothing. */
object Canarias extends EarthArea("Canarias", 27.96 ll -15.60, oceanic)
{ val elHierro: LatLong = 27.72 ll -18.15
  val laPalma: LatLong = 28.85 ll -17.92
  val lanzarote: LatLong = 29.24 ll -13.47
  val fuerteventura: LatLong = 28.24 ll -13.94
  val granCanaria: LatLong = 27.74 ll -15.60

  val polygonLL: PolygonLL = PolygonLL(elHierro, laPalma, lanzarote, fuerteventura, granCanaria)
}

/** [[PolygonLL]] graphic for Corsica depends on nothing. */
object Corsica extends EarthArea("Corsica", 42.18 ll 9.17, hillyOce)
{ val nCorsica: LatLong = 43.00 ll 9.42
  val bastia: LatLong = 42.70 ll 9.45
  val p10: LatLong = 42.57 ll 9.53
  val olmuccia: LatLong = 41.69 ll 9.40
  val sCorsica: LatLong = 41.37 ll 9.21
  val swCorsica: LatLong = 41.56 ll 8.79
  val scandola: LatLong = 42.37 ll 8.54
  val nwCalvi: LatLong = 42.57 ll 8.71
  val pointeMignola: LatLong = 42.73 ll 9.16
  val fromontica: LatLong = 42.67 ll 9.29

  override val polygonLL = PolygonLL(nCorsica, bastia, p10, olmuccia, sCorsica, swCorsica, scandola, nwCalvi, pointeMignola, fromontica)
}

/** [[PolygonLL]] graphic for Sardinia depends on nothing. */
object Sardina extends EarthArea("Sardina", 40.12 ll 9.07, hillyOce)
{ val north: LatLong = 41.25 ll 9.23
  val east: LatLong = 40.52 ll 9.82
  val p20: LatLong = 40.24 ll 9.62
  val capoMonteSanto: LatLong = 40.08 ll 9.74
  val calaCaterina: LatLong = 39.10 ll 9.51
  val perdaLonga: LatLong = 38.87 ll 8.84
  val capoTeulada: LatLong = 38.86 ll 8.64
  val portscuso: LatLong = 39.21 ll 8.36
  val alghero: LatLong = 40.56 ll 8.31
  val capoCaccia: LatLong = 40.56 ll 8.16
  val capoFalcone: LatLong = 40.97 ll 8.20
  val platamona: LatLong = 40.81 ll 8.46

  override val polygonLL = PolygonLL(north, east, p20, capoMonteSanto, calaCaterina, perdaLonga, capoTeulada, portscuso, alghero, capoCaccia,
    capoFalcone, platamona)
}

/** [[PolygonLL]] graphic for Mag west Sahara depends on nothing. */
object MaghrebWest extends EarthArea("Maghreb west", 33 ll 2.32, hillySahel)
{  val ceuta: LatLong = 35.88 ll -5.31
  val alHoceima: LatLong = 35.15 ll -4.38
  val sidiAmar: LatLong = 35.439 ll -2.973
  val northEast: LatLong = 35.085 ll -2.11
  val southEast: LatLong = 32.110 ll -1.154

  val agadir: LatLong = 30.42 ll -9.61
  val agadirPort: LatLong = 30.43 ll -9.65
  val capGhir: LatLong = 30.629 ll -9.889
  val capSim: LatLong = 31.39 ll -9.84
  val capTin: LatLong = 32.54 ll -9.28
  val elJadida: LatLong = 33.26 ll -8.51
  val rabat: LatLong = 34.04 ll -6.83
  val tangierW: LatLong = 35.79 ll -5.92

  override val polygonLL: PolygonLL = PolygonLL(ceuta, alHoceima, sidiAmar, northEast, southEast,
    agadir, agadirPort, capGhir, capSim, capTin, elJadida, rabat, tangierW)
}

/** [[PolygonLL]] graphic for west Sahara depends on nothing. */
object MaghrebEast extends EarthArea("Maghreb east", 33 ll 2.32, hillySahel)
{ val neTunis: LatLong = 37.07 ll 11.04
  val p28: LatLong = 36.87 ll 11.14
  val p30: LatLong = 36.46 ll 10.81
  val p32: LatLong = 36.17 ll 10.44
  val chebba: LatLong = 35.23 ll 11.16
  val p34: LatLong = 34.15 ll 10.02
  val gabes: LatLong = 33.88 ll 10.12
  val p35: LatLong = 33.66 ll 10.46
  val southEast: LatLong = 30.42 ll 10.08
  
  val p10: LatLong = 35.77 ll -0.80
  val capCarbon: LatLong = 35.91 ll -0.34
  val sidiMansour: LatLong = 35.79 ll -0.10
  val p15: LatLong = 36.12 ll 0.24
  val p20: LatLong = 36.51 ll 1.18
  val p21: LatLong = 36.644 ll 2.347
  val p22: LatLong = 36.92 ll 3.89
  val plageLota: LatLong = 36.64 ll 5.30
  val capAlAouna: LatLong = 36.78 ll 5.59
  val lePointNoir: LatLong = 37.09 ll 6.42
  val capDeFer: LatLong = 37.080 ll 7.169
  val capRosa: LatLong = 36.951 ll 8.227
  val capTabarka: LatLong = 36.969 ll 8.740
  val capSerat: LatLong = 37.24 ll 9.21
  val p25: LatLong = 37.35 ll 9.76
  val capTarf: LatLong = 37.18 ll 10.28
  val tunis: LatLong = 37.08 ll 10.20
  val p27: LatLong = 36.71 ll 10.41

  override val polygonLL: PolygonLL = PolygonLL(neTunis, p28, p30, p32, chebba, p34, gabes, p35, southEast, MaghrebWest.southEast, MaghrebWest.northEast,
    p10, capCarbon, sidiMansour, p15, p20, p21, p22, plageLota, capAlAouna, lePointNoir, capDeFer, capRosa, capTabarka, capSerat, p25, capTarf, tunis, p27)
}

/** [[PolygonLL]] graphic for Sahara central depends on [[MaghrebEast]]. */
object SaharaCentral extends EarthArea("SaharaCentral", 26 ll 16, deshot)
{ val southEast: LatLong = 17 ll 16.75
  val southWest: LatLong = 17 ll 10.08
  val p84: LatLong = 33.89 ll 10.75
  val djerbaMidun: LatLong = 33.825 ll 11.052
  val tripoli: LatLong = 32.911 ll 13.194
  val misrata: LatLong = 32.373 ll 15.213
  val buerat: LatLong = 31.41 ll 15.72
  val p80: LatLong = 31.07 ll 17.39
  val elAgheila: LatLong = 30.12 ll 19.08
  val p90: LatLong = 30.780 ll 18.187

  override val polygonLL: PolygonLL = PolygonLL(elAgheila, southEast, southWest, MaghrebEast.southEast, MaghrebEast.p35, p84, djerbaMidun, tripoli, misrata, buerat,
    p80, p90)
}