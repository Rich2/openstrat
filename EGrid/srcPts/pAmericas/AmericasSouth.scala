/* Copyright 2018-23 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package pEarth; package pAmericas
import geom._, pglobe._, egrid._, WTiles._

/** [[polygonLL]] graphical representation for south Argentine and south Chile. Depends on nothing. */
object SouthAmericaSouth extends EArea2("South America\nsouth", -27.0 ll -70.22, savannah)
{ val northEast: LatLong = -40.76 ll -65.02
  val fuegoEast: LatLong = -54.66 ll -65.14
  val swArgentine: LatLong = -54.28 ll -65.06

  /** Southern most point of Chile. */
  val sChile: LatLong = -55.26 ll -69.48

  val cabotBlanco: LatLong = -47.20 ll -65.75
  val islaEsmeralda : LatLong= -48.86 ll -75.62
  val northWest = -39.97 ll -73.67
  
  override val polygonLL: PolygonLL = PolygonLL(northEast,cabotBlanco, swArgentine, fuegoEast, sChile, islaEsmeralda, northWest)
}

/** [[polygonLL]] graphical representation for middle South America. Depends on [[SouthAmericaSouth]]. */
object SouthAmericaMiddle extends EArea2("South America\nmiddle", -27.0 ll -56.0, oceanic)
{ val north: Latitude = 21.south
  val nwAmericaE: Longitude = 58.west

  val nwSAmericaES: LatLong = north * nwAmericaE
  val sSAmericaNE: LatLong = north * 40.81.west
  val grutaAzul: LatLong = -23.01 ll -42.00
  val taquari: LatLong = -25.03 ll -44.67
  val barra: LatLong = -28.50 ll -48.75
  val puntaDelEste: LatLong = -35 ll -54
  val buenosAires: LatLong = -34 ll -59
  val sBuenos: LatLong = -36 ll -57

  val puntaLavapie: LatLong = -37.15 ll -73.59
  val curamilla: LatLong = -33.10 ll -71.74
  val lenguaDeVaca : LatLong= -30.24 ll -71.63

  val northWest: LatLong = north * 70.16.west

  override val polygonLL: PolygonLL = PolygonLL(nwSAmericaES, sSAmericaNE, grutaAzul, taquari, barra, puntaDelEste, buenosAires, sBuenos,
    SouthAmericaSouth.northEast, SouthAmericaSouth.northWest, puntaLavapie, curamilla, lenguaDeVaca, northWest)
}

/** [[polygonLL]] graphical representation for Columbia dnd Venezuela . Dependant on [[ElSalPanama]]. */
object ColomVenez extends EArea2("Columbia and\nVenezuela", 0 ll -70.0, mtainOld)
{ val southDegs: Double = -2.665

  val nColumbia: LatLong = 12.19 ll -71.27
  val caracas: LatLong = 11 ll -71
  val caicara: LatLong = 10.11 ll -64.74
  val margaritaE: LatLong = 10.98 ll -64.41
  val trinidadNE: LatLong = 10.84 ll -60.93
  val northEast: LatLong = 6.77 ll -58//SouthAmericaMiddle.nwAmericaE
  val manus: LatLong = -3.170 ll -59.982

  val southWest: LatLong = southDegs ll -79.790
  val punaSouth: LatLong = -3.041 ll -80.197
  val p20: LatLong = -2.188 ll -81.010
  val sanLorenzo: LatLong = -1.06 ll -80.91
  val p90: LatLong = -2.187 ll -81.008
  val puntaTortuga: LatLong = 0.775 ll -80.089
  val p95: LatLong = 4.258 ll -77.524
  val bahiaSolano10: LatLong = 6.55 ll -77.32

  override val polygonLL: PolygonLL = PolygonLL(nColumbia, caracas, caicara, margaritaE, trinidadNE, northEast, manus,
    southWest, punaSouth, p20, sanLorenzo, p90, puntaTortuga, p95, bahiaSolano10, ElSalPanama.sePanama, ElSalPanama.nePanama)
}

/** [[polygonLL]] graphical representation for the north west of South America. Dependant on [[SouthAmericaMiddle]] [[ElSalPanama]]. */
object SouthAmericaWest extends EArea2("South America\nwest", -20 ll -70.0, jungle)
{ val nChile: LatLong = -18 ll -70
  val p60: LatLong = -13.91 ll -76.39
  val p61 = -13.461 ll -76.187
  val p68 = -7.711 ll -79.465
  val p70 = -6.054 ll -81.115
  val nPeru: LatLong = -5 ll -81
  val west: LatLong = -4.68 ll -81.33

  override val polygonLL: PolygonLL = PolygonLL(ColomVenez.manus, SouthAmericaMiddle.nwSAmericaES,
    SouthAmericaMiddle.northWest, nChile, p60, p61, p68, p70, nPeru, west, ColomVenez.southWest)
}

/** [[polygonLL]] graphical representation for the east of South America. Dependant on [[ElSalPanama]]. */
object SouthAmericaEast extends EArea2("South America\neast", -10.04 ll -45.81, jungle)
{ val nAmapa: LatLong = 4.39 ll -51.51
  val amazonMouthS: LatLong = -0.18 ll -49.3
  val marajoMouth: LatLong = -0.430 ll -48.136
  val p10: LatLong = -2.34 ll -43.32
  val pedraFurada: LatLong = -2.784 ll -40.500
  val p25: LatLong = -5.152 ll -35.496
  val caboBranco: LatLong = -7.146 ll -34.796
  val paulista: LatLong = -7.918 ll -34.820
  val p50 = -9.078 ll -35.245
  val p70: LatLong = -13.23 ll -38.94
  val jequitinhonhaMouth: LatLong = -15.84 ll -38.85

  override val polygonLL: PolygonLL = PolygonLL(ColomVenez.northEast, nAmapa, amazonMouthS, marajoMouth, p10, pedraFurada, p25, caboBranco, paulista, p50, p70,
    jequitinhonhaMouth, SouthAmericaMiddle.sSAmericaNE, SouthAmericaMiddle.nwSAmericaES, ColomVenez.manus)
}