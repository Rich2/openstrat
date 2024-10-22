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

/** [[PolygonLL]] graphic for Lesbos. Depends on nothing. */
object Lesbos extends EarthAreaIsland("Lesbos", 39.19 ll 26.30, hillyOce)
{ override val area: KilometresSq = 1633.kilometresSq

  val north: LatLong = 39.39 ll 26.34
  val northEast: LatLong = 39.34 ll 26.42
  val southEast: LatLong = 39.02 ll 26.61
  val south: LatLong = 38.96 ll 26.40
  val p70: LatLong = 39.19 ll 25.83
  val p75: LatLong = 39.26 ll 25.86
  val poseidonsLungs: LatLong = 39.37 ll 26.17

  override val polygonLL: PolygonLL = PolygonLL(north, northEast, southEast, south, p70, p75, poseidonsLungs)
}

/** [[PolygonLL]] graphic for Chios. Depends on nothing. */
object Chios extends EarthAreaIsland("Chios", 38.19 ll 26.30, mtainSavannah)
{ override val area: KilometresSq = 842.3.kilometresSq

  val north: LatLong = 38.60 ll 26.00
  val northEast: LatLong = 38.55 ll 26.16
  val p20: LatLong = 38.33 ll 26.16
  val south: LatLong = 38.15 ll 26.01
  val southEast: LatLong = 38.24 ll 25.86
  val northWest: LatLong = 38.58 ll 25.85

  override val polygonLL: PolygonLL = PolygonLL(north, northEast, p20, south, southEast, northWest)
}

/** [[PolygonLL]] graphic for Samos. Depends on nothing. */
object Samos extends EarthAreaIsland("Samos", 37.748 ll 26.829, mtainSavannah)
{ override val area: KilometresSq = 477.4.kilometresSq

  val north: LatLong = 37.813 ll 26.741
  val northEast: LatLong = 37.779 ll 27.066
  val east: LatLong = 37.712 ll 27.066
  val p35: LatLong = 37.687 ll 26.952
  val southEast: LatLong = 37.640 ll 26.878
  val west: LatLong = 37.731 ll 26.566

  override val polygonLL: PolygonLL = PolygonLL(north, northEast, east, p35, southEast, west)
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

/** [[PolygonLL]] graphic for Rhodes. Depends on nothing. */
object Rhodes extends EarthAreaIsland("Rhodes", 36.22 ll 27.95, hillyOce)
{ override val area: KilometresSq = 1400.68.kilometresSq

  val north: LatLong = 36.46 ll 28.22
  val akraLindos: LatLong = 36.05 ll 28.09
  val p40: LatLong = 35.93 ll 27.86
  val south: LatLong = 35.88 ll 27.76
  val p50: LatLong = 35.94 ll 27.72
  val p65: LatLong = 36.15 ll 27.69
  val p70: LatLong = 36.27 ll 27.81

  override val polygonLL: PolygonLL = PolygonLL(north, akraLindos, p40, south, p50, p65, p70)
}

/** [[PolygonLL]] graphic for Thasos island. Depends on nothing. */
object Thasos extends EarthAreaIsland("Thasos", 40.686 ll 24.659, mtainSubForest)
{ override def area: KilometresSq = 380.kilometresSq

  val north: LatLong = 40.801 ll 24.650
  val northEast: LatLong = 40.736 ll 24.784
  val southEast: LatLong = 40.606 ll 24.774
  val south: LatLong = 40.567 ll 24.644
  val southWest: LatLong = 40.602 ll 24.530

  override val polygonLL: PolygonLL = PolygonLL(north, northEast, southEast, south, southWest)
}