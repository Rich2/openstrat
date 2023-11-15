/* Copyright 2018-23 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package pEarth; package pAsia
import geom._, pglobe._, LatLong._, egrid._, WTiles._

object Himalayas extends EArea2("Himalayas", degs(32, 75), mtain)
{
  override val polygonLL: PolygonLL = PolygonLL(CentralAsia.southEast, middleEast.Persia.persiaNE, India.mianiHor, India.indiaNE)
}

object CentralAsia extends EArea2("CAsia", degs(47, 76), plain)
{
  val southEast = middleEast.Persia.persiaN * 91.5.east//RusNorth.indiaE
  override val polygonLL: PolygonLL = PolygonLL(RusNorth.cAsiaNE, RusNorth.kazakNE, middleEast.Persia.persiaNE, southEast)
}
