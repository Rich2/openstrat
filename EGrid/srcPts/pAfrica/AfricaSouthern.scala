/* Copyright 2018-24 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package pEarth; package pAfrica
import geom._, pglobe._, egrid._, WTiles._

/** [[PolygonLL]] graphic object for southern Africa depends on nothing. */
object SouthAfricaWest extends EarthPoly("South Africa west", -25 ll 24, sahel)
{

  val p95: LatLong = -17 ll 31
  val northEast: LatLong = -25.648 ll 25.568
  val lesothoWest: LatLong = -29.644 ll 27.019
  val southEast: LatLong = -32.979 ll 27.963

  val portLiz: LatLong = -34 ll 26
  val agulhas: LatLong = -34.83 ll 20.00
  val capeOfGoodHope: LatLong = -34.356 ll 18.476
  val capeColumbine: LatLong = -32.825 ll 17.844
  val shelleyPoint: LatLong = -32.701 ll 17.967
  val p65: LatLong = -27.727 ll 15.535
  val p72: LatLong = -23.983 ll 14.457
  val swakopmund: LatLong = -22.674 ll 14.520
  val nNamibia: LatLong = -17.252 ll 11.751
  val northWest: LatLong = - 17 ll 11.76

  override def polygonLL: PolygonLL = PolygonLL(p95, northEast, lesothoWest, southEast, portLiz, agulhas, capeOfGoodHope, capeColumbine, shelleyPoint, p65, p72,
    swakopmund, nNamibia, northWest)
}

/** [[PolygonLL]] graphic object for southern Africa depends on nothing. */
object SouthAfricaEast extends EarthPoly("South Africa east", -25 ll 24, hillySavannah)
{ val beira: LatLong = -19.35 ll 34.3
  val inhambane: LatLong = -23.38 ll 35.2
  val guinjala: LatLong = -24.106 ll 35.496
  val maputoMouth: LatLong = -25.996 ll 32.580
  val inhaca: LatLong = -25.970 ll 32.992
  val richardsBay: LatLong = -29 ll 32

  override def polygonLL: PolygonLL = PolygonLL(beira, inhambane, guinjala, maputoMouth, inhaca, richardsBay, SouthAfricaWest.southEast,
    SouthAfricaWest.lesothoWest, SouthAfricaWest.northEast, SouthAfricaWest.p95)
}

/** [[PolygonLL]] graphic object for Madagascar depends on nothing. */
object Madagascar extends EarthPoly("Madagascar", -19.42 ll 46.57, oceanic)
{ val north: LatLong = -11.95 ll 49.26
  val east: LatLong = -15.33 ll 50.48
  val southEast: LatLong = -25.03 ll 46.99
  val south: LatLong = -25.60 ll 45.16
  val andavadoaka: LatLong = -22.051 ll 43.239
  val tambohorano: LatLong = -17.51 ll 43.93
  val vilamatsa: LatLong = -16.187 ll 44.450

  override def polygonLL: PolygonLL = PolygonLL(north, east, southEast, south, andavadoaka, tambohorano, vilamatsa)
}