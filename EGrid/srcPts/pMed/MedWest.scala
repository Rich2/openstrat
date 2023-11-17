/* Copyright 2018-23 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package pEarth; package pMed
import geom._, pglobe._, egrid._, WTiles._

/** [[PolygonLL]] graphic for Majorca depends on nothing. */
object Majorca extends EArea2("Majorca", 39.59 ll 3.01, land)
{ val south: LatLong = 39.26 ll 3.05
  val palma: LatLong = 39.56 ll 2.63
  val portalsVells: LatLong = 39.45 ll 2.51
  val santElm: LatLong = 39.59 ll 2.34
  val capFormentor: LatLong = 39.96 ll 3.21
  val east: LatLong = 39.71 ll 3.47

  override val polygonLL: PolygonLL = PolygonLL(south, palma, portalsVells, santElm, capFormentor, east)
}

/** [[PolygonLL]] graphic for Sicily depends on nothing. */
object Sicily extends EArea2("Sicily", cen = 37.58 ll 14.27, land)
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
object Canarias extends EArea2("Canarias", 27.96 ll -15.60, land)
{ val elHierro: LatLong = 27.72 ll -18.15
  val laPalma: LatLong = 28.85 ll -17.92
  val lanzarote: LatLong = 29.24 ll -13.47
  val fuerteventura: LatLong = 28.24 ll -13.94
  val granCanaria: LatLong = 27.74 ll -15.60

  val polygonLL: PolygonLL = PolygonLL(elHierro, laPalma, lanzarote, fuerteventura, granCanaria)
}

/** [[PolygonLL]] graphic for Corsica depends on nothing. */
object Corsica extends EArea2("Corsica", 42.18 ll 9.17, hills)
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
object Sardina extends EArea2("Sardina", 40.12 ll 9.07, hills)
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

/** [[PolygonLL]] graphic for west Sahara depends on nothing. */
object Maghreb extends EArea2("Maghreb", 33 ll 2.32, desert)
{ val neTunis: LatLong = 37.07 ll 11.04
  val p28: LatLong = 36.87 ll 11.14
  val p30: LatLong = 36.46 ll 10.81
  val p32: LatLong = 36.17 ll 10.44
  val chebba: LatLong = 35.23 ll 11.16
  val p34: LatLong = 34.15 ll 10.02
  val gabes: LatLong = 33.88 ll 10.12
  val p35: LatLong = 33.66 ll 10.46
  val southEast: LatLong = 30.42 ll 10.08

  val agadir: LatLong = 30.42 ll -9.61
  val agadirPort: LatLong = 30.43 ll -9.65
  val capGhir: LatLong = 30.629 ll -9.889
  val capSim: LatLong = 31.39 ll -9.84
  val capTin: LatLong = 32.54 ll -9.28
  val elJadida: LatLong = 33.26 ll -8.51
  val rabat: LatLong = 34.04 ll -6.83
  val tangierW: LatLong = 35.79 ll -5.92
  val ceuta: LatLong = 35.88 ll -5.31
  val alHoceima: LatLong = 35.15 ll -4.38
  val biharaPlage: LatLong = 35.07 ll -2.01
  val p10: LatLong = 35.77 ll -0.80
  val capCarbon: LatLong = 35.91 ll -0.34
  val sidiMansour: LatLong = 35.79 ll -0.10
  val p15: LatLong = 36.12 ll 0.24
  val p20: LatLong = 36.51 ll 1.18
  val p22: LatLong = 36.92 ll 3.89
  val plageLota: LatLong = 36.64 ll 5.30
  val capAlAouna: LatLong = 36.78 ll 5.59
  val lePointNoir: LatLong = 37.09 ll 6.42
  val capSerat: LatLong = 37.24 ll 9.21
  val p25: LatLong = 37.35 ll 9.76
  val capTarf: LatLong = 37.18 ll 10.28
  val tunis: LatLong = 37.08 ll 10.20
  val p27: LatLong = 36.71 ll 10.41

  override val polygonLL: PolygonLL = PolygonLL(neTunis, p28, p30, p32, chebba, p34, gabes, p35, southEast,
    agadir, agadirPort, capGhir, capSim, capTin, elJadida, rabat, tangierW, ceuta, alHoceima, biharaPlage, p10, capCarbon, sidiMansour, p15, p20, p22,
    plageLota, capAlAouna, lePointNoir, capSerat, p25, capTarf, tunis, p27)
}

/** [[PolygonLL]] graphic for Canaries depends on [[Maghreb]]. */
object SaharaCentral extends EArea2("SaharaCentral", 26 ll 16, desert)
{ val p80: LatLong = 31.07 ll 17.39
  val elAgheila: LatLong = 30.12 ll 19.08
  val southEast: LatLong = 17 ll 16.75
  val southWest: LatLong = 17 ll 10.08

  val p84: LatLong = 33.89 ll 10.75
  val p85: LatLong = 33.82 ll 11.05
  val misrata: LatLong = 32.37 ll 15.03
  val buerat: LatLong = 31.41 ll 15.72

  override val polygonLL: PolygonLL = PolygonLL(p80, elAgheila, southEast, southWest, Maghreb.southEast, Maghreb.p35, p84, p85, misrata, buerat)
}