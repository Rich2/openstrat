/* Copyright 2018-23 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package pEarth; package pAmericas
import geom._, pglobe._, LatLong._, WTile._

object SouthAmericaSouth extends EArea2("South America\nsouth", degs (- 27.0, - 56.0), plain)
{
  val swArgentine = -54.28 ll -65.06
  val sChile = -55.26 ll -69.48
  val islaEsmeralda = -48.86 ll -75.62
  val puntaLavapie = -37.15 ll -73.59
  val sSAmericaN: Latitude = 21.south
  val sSAmericaNW = sSAmericaN * 70.16.west
  val nwAmericaE = 58.west
  val nwSAmericaES = sSAmericaN * nwAmericaE
  val sSAmericaNE = sSAmericaN * 40.81.west
  val saoPaulo = -24 ll -46
  val puntaDelEste = -35 ll -54
  val buenosAires = -34 ll -59
  val sBuenos = -36 ll -57
  val cabotBlanco = -47.20 ll -65.75
  override val polygonLL: PolygonLL = PolygonLL(sChile, islaEsmeralda, puntaLavapie, sSAmericaNW, nwSAmericaES, sSAmericaNE, saoPaulo, puntaDelEste, buenosAires, sBuenos, cabotBlanco, swArgentine)
}

object SouthAmericaWest extends EArea2("South America\nwest", degs (- 5.0, - 70.0), jungle)
{
  val nChile = -18 ll -70
  val nPeru = -5 ll -81
  val bahiaSolano10 = 6.55 ll -77.32

  val nColumbia = 12.19 ll -71.27
  val caracas = 11 ll -71
  val nwSAmericaEN = 6.77.north * SouthAmericaSouth.nwAmericaE
  override val polygonLL: PolygonLL = PolygonLL(SouthAmericaSouth.sSAmericaNW, nChile, nPeru, bahiaSolano10, CentralAmerica.sePanama, CentralAmerica.nePanama, nColumbia, caracas, nwSAmericaEN, SouthAmericaSouth.nwSAmericaES)
}

object SouthAmericaEast extends EArea2("South America\neast", degs (- 10.04, - 45.81), jungle)
{ val nAmapa = 4.39 ll -51.51
  val amazonMouthS = -0.18 ll -49.3
  val paraiba = -7.15 ll -34.82

  override val polygonLL: PolygonLL = PolygonLL(SouthAmericaWest.nwSAmericaEN, nAmapa, amazonMouthS, paraiba, SouthAmericaSouth.sSAmericaNE,
    SouthAmericaSouth.nwSAmericaES)
}