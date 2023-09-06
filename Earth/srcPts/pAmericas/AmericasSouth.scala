/* Copyright 2018-23 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package pEarth; package pAmericas
import geom._, pglobe._

/** [[polygonLL]] graphical representation for south South America, Argentine and Chile. Depends on nothing. */
object SouthAmericaSouth extends EArea2("South America\nsouth", -27.0 ll -70.22, Desert)
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

/** [[polygonLL]] graphical representation for south South America, Argentine and Chile. Depends on nothing. */
object SouthAmericaMiddle extends EArea2("South America\nmiddle", -27.0 ll -56.0, Plain)
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

  val sSAmericaNW: LatLong = north * 70.16.west

  override val polygonLL: PolygonLL = PolygonLL(nwSAmericaES, sSAmericaNE, grutaAzul, taquari, barra, puntaDelEste, buenosAires, sBuenos,
    SouthAmericaSouth.northEast, SouthAmericaSouth.northWest, puntaLavapie, curamilla, lenguaDeVaca, sSAmericaNW)
}

/** [[polygonLL]] graphical representation for the west of South America. Dependant on [[SouthAmericaMiddle]] [[AmericasCentral]]. */
object SouthAmericaWest extends EArea2("South America\nwest", -5.0 ll -70.0, Jungles)
{ val nColumbia: LatLong = 12.19 ll -71.27
  val caracas: LatLong = 11 ll -71
  val caicara: LatLong = 10.11 ll -64.74
  val margaritaE: LatLong = 10.98 ll -64.41
  val trinidadNE: LatLong = 10.84 ll -60.93
  val nwSAmericaEN: LatLong = 6.77.north * SouthAmericaMiddle.nwAmericaE

  val nChile: LatLong = -18 ll -70
  val p60: LatLong = -13.91 ll -76.39
  val nPeru: LatLong = -5 ll -81
  val west: LatLong = -4.68 ll -81.33
  val sanLorenzo: LatLong = -1.06 ll -80.91
  val bahiaSolano10: LatLong = 6.55 ll -77.32

  override val polygonLL: PolygonLL = PolygonLL(nColumbia, caracas, caicara, margaritaE, trinidadNE, nwSAmericaEN, SouthAmericaMiddle.nwSAmericaES,
    SouthAmericaMiddle.sSAmericaNW, nChile, p60, nPeru, west, sanLorenzo, bahiaSolano10,AmericasCentral.sePanama, AmericasCentral.nePanama,
  )
}

/** [[polygonLL]] graphical representation for the east of South America. Dependant on [[AmericasCentral]]. */
object SouthAmericaEast extends EArea2("South America\neast", -10.04 ll -45.81, Jungles)
{ val nAmapa: LatLong = 4.39 ll -51.51
  val amazonMouthS: LatLong = -0.18 ll -49.3
  val p10: LatLong = -2.34 ll -43.32
  val paraiba: LatLong = -7.15 ll -34.82
  val p70: LatLong = -13.23 ll -38.94
  val jequitinhonhaMouth: LatLong = -15.84 ll -38.85

  override val polygonLL: PolygonLL = PolygonLL(SouthAmericaWest.nwSAmericaEN, nAmapa, amazonMouthS, p10, paraiba, p70, jequitinhonhaMouth,
    SouthAmericaMiddle.sSAmericaNE, SouthAmericaMiddle.nwSAmericaES)
}