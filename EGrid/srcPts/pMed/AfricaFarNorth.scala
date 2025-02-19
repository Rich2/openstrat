/* Copyright 2018-25 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package pEarth; package pMed
import geom._, pglobe._, egrid._, WTiles._

/** [[PolygonLL]] graphic for Mag west Sahara depends on nothing. */
object MaghrebWest extends EarthPoly("Maghreb west", 33 ll 2.32, hillySahel)
{ val ceuta: LatLong = 35.88 ll -5.31
  val p5: LatLong = 35.531 ll -5.173
  val p7: LatLong = 35.302 ll -4.868
  val dharMalek: LatLong = 35.158 ll -4.406
  val pointeLosFrailes: LatLong = 35.261 ll -3.928
  val capQuilates: LatLong = 35.288 ll -3.706
  val p18: LatLong = 35.206 ll -3.450
  val sidiAmar: LatLong = 35.439 ll -2.973
  val p28: LatLong = 35.147 ll -2.423
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

  override val polygonLL: PolygonLL = PolygonLL(ceuta, p5, p7, dharMalek, pointeLosFrailes, capQuilates, p18, sidiAmar, p28, northEast, southEast,
    agadir, agadirPort, capGhir, capSim, capTin, elJadida, rabat, tangierW)
}

/** [[PolygonLL]] graphic for west Sahara depends on nothing. */
object MaghrebEast extends EarthPoly("Maghreb east", 33 ll 2.32, hillySahel)
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
object SaharaCentral extends EarthPoly("SaharaCentral", 26 ll 16, deshot)
{ val southEast: LatLong = 17 ll 16.75
  val southWest: LatLong = 17 ll 10.08
  val p84: LatLong = 33.89 ll 10.75
  val djerbaMidun: LatLong = 33.825 ll 11.052
  val p87: LatLong = 33.368 ll 11.138
  val tripoli: LatLong = 32.911 ll 13.194
  val misrata: LatLong = 32.373 ll 15.213
  val buerat: LatLong = 31.41 ll 15.72
  val p80: LatLong = 31.07 ll 17.39
  val elAgheila: LatLong = 30.12 ll 19.08
  val p90: LatLong = 30.780 ll 18.187

  override val polygonLL: PolygonLL = PolygonLL(elAgheila, southEast, southWest, MaghrebEast.southEast, MaghrebEast.p35, p84, djerbaMidun, p87, tripoli, misrata, buerat,
    p80, p90)
}


/** [[PolygonLL]] graphic for the Sinai peninsular depends on nothing. */
object Sinai extends EarthPoly("Sinai", 29.88 ll 33.75, deshot)
{ val deadSeaSE: LatLong = 30.97 ll 35.37
  val eilat: LatLong = 29.54 ll 34.98
  val south: LatLong = 27.73 ll 34.25
  val suez: LatLong = 29.93 ll 32.56
  val portSaid: LatLong = 31.27 ll 32.32
  val eGaza: LatLong = 31.32 ll 34.22

  override val polygonLL: PolygonLL = PolygonLL(deadSeaSE, eilat, south, suez, portSaid, eGaza)
  override def toString: String = "Sinai"
}

/** [[PolygonLL]] graphic for north-east Africa. Depends on [[SaharaCentral]] and [[Sinai]]. */
object SaharaEast extends EarthPoly("SaharaEast", 24 ll 25, deshot)
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