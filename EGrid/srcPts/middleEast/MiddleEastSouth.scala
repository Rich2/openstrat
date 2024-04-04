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

/** [[PolygonLL]] graphic for Arabian Peninsular. Depends on [[ArabiaSouth]] and [[Levant]]. */
object ArabiaNorth extends EArea2("Arabia north", 26.438 ll 43.106, deshot)
{ val alFaw: LatLong = 29.93 ll 48.47
  val rasAlKhair: LatLong = 27.550 ll 49.201
  val dammam: LatLong = 26.496 ll 50.216
  val qatarNorth: LatLong = 26.15 ll 51.26
  val doha: LatLong = 25.25 ll 51.61

  val p80: LatLong = 21.31 ll 39.10
  val p85: LatLong = 24.281 ll 37.516
  val sharmas: LatLong = 28.03 ll 35.23

  override val polygonLL: PolygonLL = PolygonLL(alFaw, rasAlKhair, dammam, qatarNorth, doha, ArabiaSouth.p5, ArabiaSouth.northWest, p80, p85, sharmas,
    pMed.Sinai.eilat)
}

/** [[PolygonLL]] graphic for Arabian Peninsular. Depends on nothing. */
object ArabiaSouth extends EArea2("Arabia south", 19.192 ll 50.439, deshot)
{ val p5: LatLong = 24.295 ll 51.296
  val rasGhumeis: LatLong = 24.358 ll 51.577
  val sirBaniyas: LatLong = 24.371 ll 52.617
  val rasAlAysh: LatLong = 24.170 ll 53.139
  val icad: LatLong = 24.30 ll 54.44
  val p25: LatLong = 25.585 ll 55.558
  val kumzar: LatLong = 26.36 ll 56.38

  val alKhaburah: LatLong = 23.98 ll 57.10
  val omanEast: LatLong = 22.48 ll 59.72
  val masirahSouth: LatLong = 20.173 ll 58.639
  val rasMadrakah: LatLong = 19.000 ll 57.831
  val mirbat: LatLong = 16.94 ll 54.80
  val dhahawnMouth: LatLong = 16.20 ll 52.23
  val haswayn: LatLong = 15.63 ll 52.22
  val aden: LatLong = 12.758 ll 45.035
  val yemenSouth: LatLong = 12.65 ll 43.98
  val p75: LatLong = 13.256 ll 43.244
  val p90: LatLong = 16.42 ll 42.77
  val northWest: LatLong = 19.800 ll 40.731

  override val polygonLL: PolygonLL = PolygonLL(p5, rasGhumeis, sirBaniyas, rasAlAysh, icad, p25, kumzar, alKhaburah, omanEast, masirahSouth, rasMadrakah,
    mirbat, dhahawnMouth, haswayn, aden, yemenSouth, p75, p90, northWest)
}