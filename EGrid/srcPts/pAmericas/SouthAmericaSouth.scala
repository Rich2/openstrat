/* Copyright 2018-24 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package pEarth; package pAmericas
import geom._, pglobe._, egrid._, WTiles._

/** [[polygonLL]] graphical representation for middle South America. Depends on [[SouthAmericaSouth]]. */
object SouthAmericaMiddle extends EarthArea("South America\nmiddle", -27.0 ll -56.0, oceanic)
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

/** [[polygonLL]] graphical representation for south Argentine and south Chile. Depends on nothing. */
object SouthAmericaSouth extends EarthArea("South America\nsouth", -27.0 ll -70.22, savannah)
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

/** [[polygonLL]] graphical representation for the Falkland Islands 12173km². Depends on nothing. */
object Falklands extends EarthAreaIsland("Falkland\nIslands", -51.781 ll -59.211, hillySteppe)
{ override val area: KilometresSq = 12173.kilometresSq

  val eiNorthWest: LatLong = -51.233 ll -58.970
  val capePembroke: LatLong = -51.682 ll -57.715
  val barren: LatLong = -52.394 ll -59.724
  val beaver: LatLong = -51.834 ll -61.346
  val westPoint: LatLong = -51.335 ll -60.734

  override def polygonLL: PolygonLL = PolygonLL(eiNorthWest, capePembroke, barren, beaver, westPoint)
}