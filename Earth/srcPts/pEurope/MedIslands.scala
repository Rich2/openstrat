/* Copyright 2018-23 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package pEarth; package pEurope
import geom._, pglobe._

/** [[PolygonLL]] graphic for Majorca depends on nothing. */
object Majorca extends EArea2("Majorca", 39.59 ll 3.01, Plain)
{ val south = 39.26 ll 3.05
  val palma = 39.56 ll 2.63
  val portalsVells = 39.45 ll 2.51
  val santElm = 39.59 ll 2.34
  val capFormentor = 39.96 ll 3.21
  val east = 39.71 ll 3.47

  override val polygonLL: PolygonLL = PolygonLL(south, palma, portalsVells, santElm, capFormentor, east)
}

/** [[PolygonLL]] graphic for Sicily depends on nothing. */
object Sicily extends EArea2("Sicily", cen = 37.58 ll 14.27, Plain)
{ val sSicily = 36.66 ll 15.08
  val kartibubbo = 37.56 ll 12.67
  val marsala = 37.80 ll 12.42
  val calaRossa = 38.18 ll 12.73
  val mondello = 38.22 ll 13.32
  val n1 =37.97 ll 13.74
  val torreFaro = 38.26 ll 15.65
  val contradoFortino = 38.24 ll 15.58
  val messina = 38.18 ll 15.56
  val catania = 37.48 ll 15.08

  override val polygonLL: PolygonLL = PolygonLL(sSicily, kartibubbo, marsala, calaRossa, mondello, n1, torreFaro, contradoFortino, messina, catania)
}

/** [[PolygonLL]] graphic for Canaries depends on nothing. */
object Canarias extends EArea2("Canarias", 27.96 ll -15.60, Plain)
{ val elHierro = 27.72 ll -18.15
  val laPalma = 28.85 ll -17.92
  val lanzarote = 29.24 ll -13.47
  val fuerteventura = 28.24 ll -13.94
  val granCanaria = 27.74 ll -15.60

  val polygonLL: PolygonLL = PolygonLL(elHierro, laPalma, lanzarote, fuerteventura, granCanaria)
}

/** [[PolygonLL]] graphic for Crete depends on nothing. */
object Crete extends EArea2("Crete", 35.23 ll 24.92, Hill)
{ val northEast = 35.32 ll 26.31
  val southEast = 35.02 ll 26.19
  val p10 = 34.92 ll 24.73
  val p15 = 35.09 ll 24.72
  val p20 = 35.23 ll 23.59
  val p30 = 35.29 ll 23.52
  val capeGramvousa = 35.62 ll 23.60
  override val polygonLL: PolygonLL = PolygonLL(northEast, southEast, p10, p15, p20, p30, capeGramvousa)
}

/** [[PolygonLL]] graphic for Cyprus depends on nothing. */
object Cyprus extends EArea2("Cyprus", 34.98 ll 33.15, Hill)
{ val northEast: LatLong = 35.69 ll 34.58
  val southEast: LatLong = 34.96 ll 34.09
  val p30: LatLong = 34.57 ll 33.04
  val pontiBaba: LatLong = 35.10 ll 32.28
  val korucamBurnu: LatLong = 35.40 ll 32.92

  override val polygonLL: PolygonLL = PolygonLL(northEast, southEast, p30, pontiBaba, korucamBurnu)
}

/** [[PolygonLL]] graphic for Rhodes depends on nothing. */
object Rhodes extends EArea2("Rhodes", 36.22 ll 27.95, Hill)
{ val north: LatLong = 36.46 ll 28.22
  val akraLindos: LatLong = 36.05 ll 28.09
  val p40: LatLong = 35.93 ll 27.86
  val south: LatLong = 35.88 ll 27.76
  val p50: LatLong = 35.94 ll 27.72
  val p65: LatLong = 36.15 ll 27.69
  val p70: LatLong = 36.27 ll 27.81

  override val polygonLL: PolygonLL = PolygonLL(north, akraLindos, p40, south, p50, p65, p70)
}

/** [[PolygonLL]] graphic for Corsica depends on nothing. */
object Corsica extends EArea2("Corsica", 42.18 ll 9.17, Hill)
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
object Sardina extends EArea2("Sardina", 40.12 ll 9.07, Hill)
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