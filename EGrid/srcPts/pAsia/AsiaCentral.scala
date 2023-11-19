/* Copyright 2018-23 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package pEarth; package pAsia
import geom._, pglobe._, LatLong._, egrid._, WTiles._

object Himalayas extends EArea2("Himalayas", degs(32, 75), mtain)
{
  override val polygonLL: PolygonLL = PolygonLL(Xinjiang.east, Xinjiang.south, Mongolia.southWest, India.indiaNE, India.mianiHor, middleEast.Persia.persiaNE)
}

object CentralAsia extends EArea2("CAsia", degs(47, 76), land)
{ val cAsiaNE = 55 ll 91.5//indiaE
  //val southEast = middleEast.Persia.persiaN * 91.5.east//RusNorth.indiaE
  override val polygonLL: PolygonLL = PolygonLL(Kazak.kazakNE, cAsiaNE, Mongolia.west, Xinjiang.east, middleEast.Persia.persiaNE)
}
