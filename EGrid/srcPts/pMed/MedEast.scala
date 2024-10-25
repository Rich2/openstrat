/* Copyright 2018-24 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package pEarth; package pMed
import geom._, pglobe._, egrid._, WTiles._


/** [[PolygonLL]] graphic for Greek Island of Cephalonia. Depends on nothing. */
object Cephalonia extends EarthAreaIsland("Cephalonia", 38.22 ll 20.59, mtainSavannah)
{ override val area: KilometresSq = 773.kilometresSq

  val north: LatLong = 38.50 ll 20.66
  val southEast: LatLong = 38.11 ll 20.82
  val southWest: LatLong = 38.18 ll 20.34
  val northWest: LatLong = 38.36 ll 20.40

  override val polygonLL: PolygonLL = PolygonLL(north, southEast, southWest, northWest)
}

/** [[PolygonLL]] graphic for Greek Island of kythira. Depends on nothing. */
object Kythira extends EarthAreaIsland("Kythira", 36.243 ll 22.989, mtainSavannah)
{ override val area: KilometresSq = 300.kilometresSq

  val north: LatLong = 36.385 ll 22.950
  val east: LatLong = 36.249 ll 23.099
  val southEast: LatLong = 36.130 ll 23.047
  val southWest: LatLong = 36.200 ll 22.908

  override val polygonLL: PolygonLL = PolygonLL(north, east, southEast, southWest)
}

/** [[PolygonLL]] graphic for Crete. Depends on nothing. */
object Crete extends EarthAreaIsland("Crete", 35.23 ll 24.92, mtainSavannah)
{ override val area: KilometresSq = 8450.kilometresSq

  val northEast: LatLong = 35.32 ll 26.31
  val southEast: LatLong = 35.02 ll 26.19
  val p10: LatLong = 34.92 ll 24.73
  val p15: LatLong = 35.09 ll 24.72
  val p20: LatLong = 35.23 ll 23.59
  val p30: LatLong = 35.29 ll 23.52
  val capeGramvousa: LatLong = 35.62 ll 23.60

  override val polygonLL: PolygonLL = PolygonLL(northEast, southEast, p10, p15, p20, p30, capeGramvousa)
}

/** [[PolygonLL]] graphic for Corfu. Depends on nothing. */
object Corfu extends EarthAreaIsland("Corfu", 39.63 ll 19.82, hillyOce)
{ override val area: KilometresSq = 610.9.kilometresSq

  val north: LatLong = 39.82 ll 19.85
  val northEast: LatLong = 39.78 ll 19.96
  val gouvia: LatLong = 39.65 ll 19.84
  val southEast: LatLong = 39.38 ll 20.12
  val south: LatLong = 39.36 ll 20.11
  val p65: LatLong = 39.46 ll 19.87
  val capeKefali: LatLong = 39.75 ll 19.63
  val capeDrastis: LatLong = 39.78 ll 19.67

  override val polygonLL: PolygonLL = PolygonLL(north, northEast, gouvia, southEast, south, p65, capeKefali, capeDrastis)
}

/** [[PolygonLL]] graphic for Cyprus. Depends on nothing. */
object Cyprus extends EarthAreaIsland("Cyprus", 34.98 ll 33.15, hillyOce)
{ override val area: KilometresSq = 9251.kilometresSq

  val northEast: LatLong = 35.69 ll 34.58
  val southEast: LatLong = 34.96 ll 34.09
  val p30: LatLong = 34.57 ll 33.04
  val pontiBaba: LatLong = 35.10 ll 32.28
  val korucamBurnu: LatLong = 35.40 ll 32.92

  override val polygonLL: PolygonLL = PolygonLL(northEast, southEast, p30, pontiBaba, korucamBurnu)
}