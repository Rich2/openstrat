/* Copyright 2018-23 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package pEarth; package pAmericas
import geom._, pglobe._

/** [[polygonLL]] graphical representation for south South America, Argentine and Chile. Depends on nothing. */
object SouthAmericaSouth extends EArea2("South America\nsouth", -27.0 ll -56.0, Plain)
{ val swArgentine: LatLong = -54.28 ll -65.06
  val sChile: LatLong = -55.26 ll -69.48
  val islaEsmeralda : LatLong= -48.86 ll -75.62
  val puntaLavapie: LatLong = -37.15 ll -73.59
  val curamilla: LatLong = -33.10 ll -71.74
  val lenguaDeVaca : LatLong= -30.24 ll -71.63
  val north: Latitude = 21.south
  val sSAmericaNW: LatLong = north * 70.16.west
  val nwAmericaE: Longitude = 58.west
  val nwSAmericaES: LatLong = north * nwAmericaE
  val sSAmericaNE: LatLong = north * 40.81.west
  val saoPaulo: LatLong = -24 ll -46
  val puntaDelEste: LatLong = -35 ll -54
  val buenosAires: LatLong = -34 ll -59
  val sBuenos: LatLong = -36 ll -57
  val cabotBlanco: LatLong = -47.20 ll -65.75

  override val polygonLL: PolygonLL = PolygonLL(sChile, islaEsmeralda, puntaLavapie, curamilla, lenguaDeVaca, sSAmericaNW, nwSAmericaES, sSAmericaNE, saoPaulo, puntaDelEste,
    buenosAires, sBuenos, cabotBlanco, swArgentine)
}

/** [[polygonLL]] graphical representation for the west of South America. Dependant on [[SouthAmericaSouth]] [[AmericasCentral]]. */
object SouthAmericaWest extends EArea2("South America\nwest", -5.0 ll -70.0, Jungles)
{ val nChile: LatLong = -18 ll -70
  val nPeru: LatLong = -5 ll -81
  val bahiaSolano10: LatLong = 6.55 ll -77.32

  val nColumbia: LatLong = 12.19 ll -71.27
  val caracas: LatLong = 11 ll -71
  val nwSAmericaEN: LatLong = 6.77.north * SouthAmericaSouth.nwAmericaE

  override val polygonLL: PolygonLL = PolygonLL(SouthAmericaSouth.sSAmericaNW, nChile, nPeru, bahiaSolano10, AmericasCentral.sePanama,
    AmericasCentral.nePanama, nColumbia, caracas, nwSAmericaEN, SouthAmericaSouth.nwSAmericaES)
}

/** [[polygonLL]] graphical representation for the east of South America. Dependant on [[AmericasCentral]]. */
object SouthAmericaEast extends EArea2("South America\neast", -10.04 ll -45.81, Jungles)
{
  val nAmapa: LatLong = 4.39 ll -51.51
  val amazonMouthS: LatLong = -0.18 ll -49.3
  val paraiba: LatLong = -7.15 ll -34.82

  override val polygonLL: PolygonLL = PolygonLL(SouthAmericaWest.nwSAmericaEN, nAmapa, amazonMouthS, paraiba, SouthAmericaSouth.sSAmericaNE,
    SouthAmericaSouth.nwSAmericaES)
}