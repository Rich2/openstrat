/* Copyright 2018-24 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package pEarth; package pAmericas
import geom._, pglobe._, egrid._, WTiles._

/** [[polygonLL]] graphical representation for north Andes. Dependent on [[ElSalPanama]]. */
object AndesMiddle extends EarthArea("Andes middle", 5.105 ll -75.212, mtainSahel)
{
  val northEast: LatLong = -17.845 ll -63.402
  val lenguaDeVaca : LatLong= -30.24 ll -71.63

  override val polygonLL: PolygonLL = PolygonLL(AndesNearNorth.southEast, northEast, SouthAmericaNS.mendoza, SouthAmericaNS.northWest, lenguaDeVaca, AndesNearNorth.chileNW)
}

/** [[polygonLL]] graphical representation for middle South America. Depends on [[SouthAmericaFS]]. */
object SouthAmericaMiddle extends EarthArea("South America\nmiddle", -27.0 ll -56.0, subtrop)
{ val north: Latitude = 21.south
  val nwAmericaE: Longitude = 58.west

  val nwSAmericaES: LatLong = north * nwAmericaE
  val sSAmericaNE: LatLong = north * 40.81.west
  val grutaAzul: LatLong = -23.01 ll -42.00
  val taquari: LatLong = -25.03 ll -44.67
  val barra: LatLong = -28.50 ll -48.75
  val puntaDelEste: LatLong = -35 ll -54
  val puntaCarretas = -34.931 ll -56.159

  override val polygonLL: PolygonLL = PolygonLL(nwSAmericaES, sSAmericaNE, grutaAzul, taquari, barra, puntaDelEste, puntaCarretas, SouthAmericaNS.northEast,
    SouthAmericaNS.mendoza, AndesMiddle.northEast)
}