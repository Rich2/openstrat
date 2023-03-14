/* Copyright 2018-23 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package pEarth;package middleEast
import geom._, pglobe._, LatLong._

/** [[PolygonLL]] graphic for the Levant depends on [[Kurdistan]] and [[Sinai]]. */
object Levant extends EArea2("Levant", 33 ll 35.5, Hill)
{ val damascus = 33.51 ll 36.82
  val haifa = 32.83 ll 34.98
  val p50 = 35.58 ll 35.72
  val p60 = 36.32 ll 35.78
  override val polygonLL: PolygonLL = PolygonLL(Kurdistan.cizre, damascus, pMed.Sinai.eilat, pMed.Sinai.eGaza, haifa, p50, p60,
    Kurdistan.delicaymouth)
}

/** [[PolygonLL]] graphic for Arabian Peninsular. Depends on [[Levant]]. */
object Arabia extends EArea2("Arabia", degs (25, 45.0), Deserts)
{ val salwa = 24.71 ll 50.77
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

  override val polygonLL: PolygonLL = PolygonLL(alFaw, salwa, nQatar, doha, alGharbia, icad, kumzar, alKhaburah, eOman, mirbat,
    dhahawnMouth, haswayn, sYemen, sharmas, pMed.Sinai.eilat)
}

/** [[PolygonLL]] graphic for Persia. Depends on [[Caspian]] and [[pAsia.India]]. */
object Persia extends EArea2("Persia", 32.4 ll 60, Hill)
{ /** 38.86N */
  val persiaN = 38.86.north

  val mahshahr = 30.22.north * Armenia.asiaMinorE

  val persiaNE = persiaN * pAsia.India.wAsiaE

  val seIran = degs(25.37, 61.67)
  val kuhmobarak = 25.80 ll 57.30
  val nHormuz = 27.17 ll 56.47
  val nwHormuz = 26.49 ll 54.79
  val zeydan = 27.88 ll 51.50
  override val polygonLL: PolygonLL = PolygonLL(mahshahr, Caspian.southWest, Caspian.southEast, Caspian.persiaN, persiaNE, pAsia.India.mianiHor,
    seIran, kuhmobarak, nHormuz, nwHormuz, zeydan)
}