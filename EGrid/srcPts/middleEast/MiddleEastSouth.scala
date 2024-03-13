/* Copyright 2018-24 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package pEarth;package middleEast
import geom._, pglobe._, egrid._, WTiles._

/** [[PolygonLL]] graphic for the Levant depends on [[Kurdistan]] and [[Sinai]]. */
object Levant extends EArea2("Levant", 33 ll 35.5, hillySavannah)
{ val damascus: LatLong = 33.51 ll 36.82
  val ashkelon: LatLong = 31.68 ll 34.56
  val haifa: LatLong = 32.825 ll 34.955
  val p40: LatLong = 34.312 ll 35.680
  val p43: LatLong = 34.572 ll 35.990
  val p50: LatLong = 35.586 ll 35.716
  val p55: LatLong = 35.853 ll 35.80
  val p57: LatLong = 36.010 ll 35.981
  val p59: LatLong = 36.291 ll 35.783
  val p60: LatLong = 36.321 ll 35.780

  override val polygonLL: PolygonLL = PolygonLL(Kurdistan.cizre, damascus, pMed.Sinai.deadSeaSE, pMed.Sinai.eGaza, ashkelon, haifa, p40, p43, p50, p55, p57, p59, p60,
    Kurdistan.delicaymouth)
}

/** [[PolygonLL]] graphic for Arabian Peninsular. Depends on [[Levant]]. */
object Arabia extends EArea2("Arabia", 25 ll 45.0, deshot)
{ val salwa: LatLong = 24.71 ll 50.77
  val nQatar: LatLong = 26.15 ll 51.26
  val doha: LatLong = 25.25 ll 51.61
  val alGharbia: LatLong = 23.97 ll 51.94
  val icad: LatLong = 24.30 ll 54.44
  val kumzar: LatLong = 26.36 ll 56.38
  val alKhaburah: LatLong = 23.98 ll 57.10
  val eOman: LatLong = 22.48 ll 59.72
  val mirbat: LatLong = 16.94 ll 54.80
  val dhahawnMouth: LatLong = 16.20 ll 52.23
  val haswayn: LatLong = 15.63 ll 52.22
  val sYemen: LatLong = 12.65 ll 43.98
  val p75: LatLong = 16.42 ll 42.77
  val p80: LatLong = 21.31 ll 39.10
  val sharmas: LatLong = 28.03 ll 35.23
  val alFaw: LatLong = 29.93 ll 48.47

  override val polygonLL: PolygonLL = PolygonLL(alFaw, salwa, nQatar, doha, alGharbia, icad, kumzar, alKhaburah, eOman, mirbat,
    dhahawnMouth, haswayn, sYemen, p75, p80, sharmas, pMed.Sinai.eilat)
}