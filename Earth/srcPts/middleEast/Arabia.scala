/* Copyright 2018-23 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package pEarth;package middleEast
import geom._, pglobe._, LatLong._, WTile._

/** [[PolygonLL]] graphic for the Sinai peninsular depends on nothing. */
object Sinai extends EArea2 ("Sinai", 29.88 ll 33.75, desert)
{ val eGaza = 31.32 ll 34.22
  val eilat = 29.54 ll 34.98
  val south = 27.73 ll 34.25
  val suez = 29.93 ll 32.56
  val portSaid = 31.27 ll 32.32

  override val polygonLL: PolygonLL = PolygonLL(eGaza, eilat, south, suez, portSaid)
}

/** Displays Arabian Peninsular. Depends on [[Anatolia]]. */
object Arabia extends EArea2 ("Arabia", degs (25, 45.0), desert)
{
  /** South West Turkey */
  val salwa = 24.71 ll 50.77
  val nQatar = 26.15 ll 51.26
  val doha = 25.25 ll 51.61
  val alGharbia = degs(23.97, 51.94)
  val icad = 24.30 ll 54.44
  val kumzar = 26.36 ll 56.38
  val alKhaburah = 23.98 ll 57.10
  val eOman = degs(22.48, 59.72)
  val mirbat = 16.94 ll 54.80
  val dhahawnMouth = 16.20 ll 52.23
  val haswayn = 15.63 ll 52.22
  val sYemen = degs(12.65, 43.98)
  val sharmas = degs(28.03, 35.23)
  val alFaw = degs(29.93, 48.47)

  override val polygonLL: PolygonLL = PolygonLL(Levant.damascus, alFaw, salwa, nQatar, doha, alGharbia, icad, kumzar, alKhaburah, eOman, mirbat,
    dhahawnMouth, haswayn, sYemen, sharmas, Sinai.eilat)
}

/** [[PolygonLL]] graphic for the Sinai peninsular depends on nothing. */
object Levant extends EArea2 ("Levant", 33 ll 35.5, hills)
{ val damascus = 33.51 ll 36.82
  val haifa = 32.83 ll 34.98
  val p50 = 35.58 ll 35.72
  val p60 = 36.32 ll 35.78
  override val polygonLL: PolygonLL = PolygonLL(Anatolia.cizre, damascus, Sinai.eilat, Sinai.eGaza, haifa, p50, p60,
    Anatolia.delicaymouth)
}
