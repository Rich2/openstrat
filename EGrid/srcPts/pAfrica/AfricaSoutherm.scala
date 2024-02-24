/* Copyright 2018-24 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package pEarth; package pAfrica
import geom._, pglobe._, egrid._, WTiles._

/** [[PolygonLL]] graphic object for southern Africa depends on nothing. */
object SouthAfrica extends EArea2("South Africa", -25 ll 24, sahel)
{ val sAfricaN: Latitude = 17.south

  val sAfricaNW: LatLong = - 17 ll 11.76
  val p95: LatLong = sAfricaN * 31.east

  val agulhas: LatLong = -34.83 ll 20.00
  val capeTown: LatLong = -34 ll 19
  val nNamibia: LatLong = -17.12 ll 11.3
  val beira: LatLong = -19.35 ll 34.3
  val inhambane: LatLong = -23.38 ll 35.2
  val maputo: LatLong = -25.4 ll 32.2
  val richardsBay: LatLong = -29 ll 32
  val portLiz: LatLong = -34 ll 26

  override def polygonLL: PolygonLL = PolygonLL(agulhas, capeTown, nNamibia, sAfricaNW, p95, beira, inhambane, maputo, richardsBay,
    portLiz)
}

/** [[PolygonLL]] graphic object for Madagascar depends on nothing. */
object Madagascar extends EArea2("Madagascar", -19.42 ll 46.57, level)
{ val north: LatLong = -11.95 ll 49.26
  val east: LatLong = -15.33 ll 50.48
  val southEast: LatLong = -25.03 ll 46.99
  val south: LatLong = -25.60 ll 45.16
  val tambohorano: LatLong = -17.51 ll 43.93

  override def polygonLL: PolygonLL = PolygonLL(north, east, southEast, south, tambohorano)
}