/* Copyright 2018-24 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package pEarth; package pMed
import geom._, pglobe._, egrid._, WTiles._

/** [[PolygonLL]] graphic for Majorca depends on nothing. */
object Balearics extends EarthAreaIsland("Balearics", 39.59 ll 3.01, oceanic)
{ override def area: KilometresSq = 5040.kilometresSq

  val south: LatLong = 39.26 ll 3.05
  val palma: LatLong = 39.56 ll 2.63
  val portalsVells: LatLong = 39.45 ll 2.51
  val santElm: LatLong = 39.59 ll 2.34
  val capFormentor: LatLong = 39.96 ll 3.21
  val east: LatLong = 39.71 ll 3.47

  override val polygonLL: PolygonLL = PolygonLL(south, palma, portalsVells, santElm, capFormentor, east)
}

/** [[PolygonLL]] graphic for Corsica depends on nothing. */
object Corsica extends EarthAreaIsland("Corsica", 42.18 ll 9.17, hillySavannah)
{ override val area: KilometresSq = 8680.kilometresSq

  val nCorsica: LatLong = 43.00 ll 9.42
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
object Sardina extends EarthArea("Sardina", 40.12 ll 9.07, hillySub)
{ val north: LatLong = 41.25 ll 9.23
  val east: LatLong = 40.52 ll 9.82
  val p20: LatLong = 40.24 ll 9.62
  val capoMonteSanto: LatLong = 40.08 ll 9.74
  val calaCaterina: LatLong = 39.10 ll 9.51
  val p45: LatLong = 39.159 ll 9.026
  val p47: LatLong = 39.057 ll 9.045
  val perdaLonga: LatLong = 38.87 ll 8.84
  val capoTeulada: LatLong = 38.86 ll 8.64
  val portscuso: LatLong = 39.21 ll 8.36
  val alghero: LatLong = 40.56 ll 8.31
  val capoCaccia: LatLong = 40.56 ll 8.16
  val capoFalcone: LatLong = 40.97 ll 8.20
  val platamona: LatLong = 40.81 ll 8.46

  override val polygonLL = PolygonLL(north, east, p20, capoMonteSanto, calaCaterina, p45, p47, perdaLonga, capoTeulada, portscuso, alghero, capoCaccia,
    capoFalcone, platamona)
}

/** [[PolygonLL]] graphic for Sicily depends on nothing. */
object Sicily extends EarthAreaIsland("Sicily", cen = 37.58 ll 14.27, oceanic)
{ override def area: KilometresSq = 25832.kilometresSq

  val sSicily: LatLong = 36.66 ll 15.08
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