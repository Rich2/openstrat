/* Copyright 2018-23 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package pEarth; package pMalay
import geom._, pglobe._, egrid._, WTiles._

/** [[polygonLL]] graphical representation of Luzon island. Depends on nothing. */
object Luzon extends EArea2("Philippines North", 16.779 ll 121.353, jungle)
{ val north: LatLong = 18.650 ll 120.844
  val northEast: LatLong = 18.522 ll 122.225
  val p10: LatLong = 17.141 ll 122.510
  val p15: LatLong = 15.324 ll 121.37
  val jomaigEast: LatLong = 14.703 ll 122.439
  val northWest: LatLong = 16.359 ll 119.815
  val calintaanSouth: LatLong = 12.524 ll 124.091
  val southWest: LatLong = 13.768 ll 120.658

  override val polygonLL: PolygonLL = PolygonLL(north, northEast, p10, jomaigEast, calintaanSouth, southWest, northWest)
}

/** [[polygonLL]] graphical representation of Sumatra. Depends on nothing. */
object PhilippinesCen extends EArea2("Philippines Central", 13.259 ll 122.615, jungle)
{
  val batagNE: LatLong = 12.685 ll 125.063
  val samarSE: LatLong = 10.939 ll 125.837
  val negrosS: LatLong = 9.043 ll 123.013

  override val polygonLL: PolygonLL = PolygonLL(batagNE, samarSE, negrosS)
}