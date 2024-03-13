/* Copyright 2018-24 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package pEarth; package pMed
import geom._, pglobe._, egrid._, WTiles._

/** [[PolygonLL]] graphic for Crete depends on nothing. */
object Cephalonia extends EArea2("Cephalonia", 38.22 ll 20.59, hillyOce)
{ val north: LatLong = 38.50 ll 20.66
  val southEast: LatLong = 38.11 ll 20.82
  val southWest: LatLong = 38.18 ll 20.34
  val northWest: LatLong = 38.36 ll 20.40

  override val polygonLL: PolygonLL = PolygonLL(north, southEast, southWest, northWest)
}

/** [[PolygonLL]] graphic for Crete depends on nothing. */
object Crete extends EArea2("Crete", 35.23 ll 24.92, hillyOce)
{ val northEast: LatLong = 35.32 ll 26.31
  val southEast: LatLong = 35.02 ll 26.19
  val p10: LatLong = 34.92 ll 24.73
  val p15: LatLong = 35.09 ll 24.72
  val p20: LatLong = 35.23 ll 23.59
  val p30: LatLong = 35.29 ll 23.52
  val capeGramvousa: LatLong = 35.62 ll 23.60

  override val polygonLL: PolygonLL = PolygonLL(northEast, southEast, p10, p15, p20, p30, capeGramvousa)
}

/** [[PolygonLL]] graphic for Corfu depends on nothing. */
object Corfu extends EArea2("Corfu", 39.63 ll 19.82, hillyOce)
{ val north: LatLong = 39.82 ll 19.85
  val northEast: LatLong = 39.78 ll 19.96
  val gouvia: LatLong = 39.65 ll 19.84
  val southEast: LatLong = 39.38 ll 20.12
  val south: LatLong = 39.36 ll 20.11
  val p65: LatLong = 39.46 ll 19.87
  val capeKefali: LatLong = 39.75 ll 19.63
  val capeDrastis: LatLong = 39.78 ll 19.67

  override val polygonLL: PolygonLL = PolygonLL(north, northEast, gouvia, southEast, south, p65, capeKefali, capeDrastis)
}

/** [[PolygonLL]] graphic for Lesbos depends on nothing. */
object Lesbos extends EArea2("Lesbos", 39.19 ll 26.30, hillyOce)
{ val north: LatLong = 39.39 ll 26.34
  val northEast: LatLong = 39.34 ll 26.42
  val southEast: LatLong = 39.02 ll 26.61
  val south: LatLong = 38.96 ll 26.40
  val p70: LatLong = 39.19 ll 25.83
  val p75: LatLong = 39.26 ll 25.86
  val poseidonsLungs: LatLong = 39.37 ll 26.17

  override val polygonLL: PolygonLL = PolygonLL(north, northEast, southEast, south, p70, p75, poseidonsLungs)
}

/** [[PolygonLL]] graphic for Chios depends on nothing. */
object Chios extends EArea2("Chios", 38.19 ll 26.30, hillyOce)
{ val north: LatLong = 38.60 ll 26.00
  val northEast: LatLong = 38.55 ll 26.16
  val p20: LatLong = 38.33 ll 26.16
  val south: LatLong = 38.15 ll 26.01
  val southEast: LatLong = 38.24 ll 25.86
  val northWest: LatLong = 38.58 ll 25.85

  override val polygonLL: PolygonLL = PolygonLL(north, northEast, p20, south, southEast, northWest)
}

/** [[PolygonLL]] graphic for Cyprus depends on nothing. */
object Cyprus extends EArea2("Cyprus", 34.98 ll 33.15, hillyOce)
{ val northEast: LatLong = 35.69 ll 34.58
  val southEast: LatLong = 34.96 ll 34.09
  val p30: LatLong = 34.57 ll 33.04
  val pontiBaba: LatLong = 35.10 ll 32.28
  val korucamBurnu: LatLong = 35.40 ll 32.92

  override val polygonLL: PolygonLL = PolygonLL(northEast, southEast, p30, pontiBaba, korucamBurnu)
}

/** [[PolygonLL]] graphic for Rhodes depends on nothing. */
object Rhodes extends EArea2("Rhodes", 36.22 ll 27.95, hillyOce)
{ val north: LatLong = 36.46 ll 28.22
  val akraLindos: LatLong = 36.05 ll 28.09
  val p40: LatLong = 35.93 ll 27.86
  val south: LatLong = 35.88 ll 27.76
  val p50: LatLong = 35.94 ll 27.72
  val p65: LatLong = 36.15 ll 27.69
  val p70: LatLong = 36.27 ll 27.81

  override val polygonLL: PolygonLL = PolygonLL(north, akraLindos, p40, south, p50, p65, p70)
}

/** [[PolygonLL]] graphic for the Sinai peninsular depends on nothing. */
 object Sinai extends EArea2("Sinai", 29.88 ll 33.75, deshot)
{ val deadSeaSE: LatLong = 30.97 ll 35.37
  val eilat: LatLong = 29.54 ll 34.98
  val south: LatLong = 27.73 ll 34.25
  val suez: LatLong = 29.93 ll 32.56
  val portSaid: LatLong = 31.27 ll 32.32
  val eGaza: LatLong = 31.32 ll 34.22

  override val polygonLL: PolygonLL = PolygonLL(deadSeaSE, eilat, south, suez, portSaid, eGaza)
  override def toString: String = "Sinai"
}

/** [[PolygonLL]] graphic for north east Africa. Depends on [[SaharaCentral]] and [[Sinai]]. */
object SaharaEast extends EArea2("SaharaEast", 24 ll 25, deshot)
{ val p5: LatLong = 29.59 ll 32.34
  val p20: LatLong = 23.95 ll 35.76
  val p30: LatLong = 18.83 ll 37.44
  val p35: LatLong = 17 ll 39.4

  val newBrega = 30.481 ll 19.718
  val p58: LatLong = 31.14 ll 20.18
  val benghazi: LatLong = 32.12 ll 20.05
  val p60: LatLong = 32.94 ll 21.71
  val derna: LatLong = 32.93 ll 22.15
  val p65: LatLong = 32.628 ll 23.115
  val p70: LatLong = 31.935 ll 25.035
  val p75: LatLong = 31.535 ll 25.172
  val sidiiBarrani: LatLong = 31.628 ll 25.903
  val miniHasheesh: LatLong = 31.373 ll 27.339
  val p80: LatLong = 30.82 ll 29.09
  val p85: LatLong = 31.478 ll 30.376
  val baltim: LatLong = 31.60 ll 31.01
  val p90: LatLong = 31.510 ll 31.949

  override val polygonLL: PolygonLL = PolygonLL(Sinai.portSaid, Sinai.suez, p5, p20, p30, p35, pAfrica.AfricaHorn.p0, pAfrica.AfricaHorn.tekeze,
    SaharaCentral.southEast, SaharaCentral.elAgheila, newBrega, p58, benghazi, p60, derna, p65, p70, p75, sidiiBarrani, miniHasheesh, p80, p85, baltim, p90)
}