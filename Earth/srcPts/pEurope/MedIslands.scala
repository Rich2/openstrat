/* Copyright 2018-23 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package pEarth; package pEurope
import geom._, pglobe._, WTile._

object Majorca extends EArea2("Majorca", 39.59 ll 3.01, plain)
{ val south = 39.26 ll 3.05
  val palma = 39.56 ll 2.63
  val portalsVells = 39.45 ll 2.51
  val santElm = 39.59 ll 2.34
  val capFormentor = 39.96 ll 3.21
  val east = 39.71 ll 3.47

  override val polygonLL: PolygonLL = PolygonLL(south, palma, portalsVells, santElm, capFormentor, east)
}

object Sicily extends EArea2("Sicily", cen = 37.58 ll 14.27, plain)
{ val sSicily = 36.66 ll 15.08
  val kartibubbo = 37.56 ll 12.67
  val marsala = 37.80 ll 12.42
  val calaRossa = 38.18 ll 12.73
  val mondello = 38.22 ll 13.32
  val n1 =37.97 ll 13.74
  val torreFaro = 38.26 ll 15.65
  val contradoFortino = 38.24 ll 15.58
  val messina = 38.18 ll 15.56
  val catania = 37.48 ll 15.08

  override val polygonLL: PolygonLL = PolygonLL(sSicily, kartibubbo, marsala, calaRossa, mondello, n1, torreFaro, contradoFortino, messina, catania)
}

object Canarias extends EArea2("Canarias", 27.96 ll -15.60, plain)
{ val elHierro = 27.72 ll -18.15
  val laPalma = 28.85 ll -17.92
  val lanzarote = 29.24 ll -13.47
  val fuerteventura = 28.24 ll -13.94
  val granCanaria = 27.74 ll -15.60

  val polygonLL: PolygonLL = PolygonLL(elHierro, laPalma, lanzarote, fuerteventura, granCanaria)
}

object Crete extends EArea2("Crete", 35.23 ll 24.92, hills)
{
  val northEast = 35.32 ll 26.31
  val southEast = 35.02 ll 26.19
  val p10 = 34.92 ll 24.73
  val p15 = 35.09 ll 24.72
  val p20 = 35.23 ll 23.59
  val p30 = 35.29 ll 23.52
  val capeGramvousa = 35.62 ll 23.60
  override val polygonLL: PolygonLL = PolygonLL(northEast, southEast, p10, p15, p20, p30, capeGramvousa)
}