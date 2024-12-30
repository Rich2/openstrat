/* Copyright 2018-24 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package pEarth; package pAfrica
import geom._, pglobe._, egrid._, WTiles._

/** [[PolygonLL]] graphic object for Zimbabwe depends on nothing. */
object Zimbabwe extends EarthPoly("Zimbawe", -18.970 ll 29.955, savannah)
{ val luangwa: LatLong = -15.621 ll 30.428

  override def polygonLL: PolygonLL = PolygonLL(luangwa, Mozambique.tete, Mozambique.mutare, SouthAfricaEast.luvuvhuMouth, SouthAfricaWest.p12,
    SouthAfricaWest.kazungula)
}

/** [[PolygonLL]] graphic object for southern Africa West depends on nothing. */
object SouthAfricaWest extends EarthPoly("South Africa west", -25 ll 24, sahel)
{ val p12: LatLong = -22.180 ll 29.371
  val p20: LatLong = -25.648 ll 25.568

  val kazungula = -17.790 ll 25.261
  val gaberone: LatLong = -24.754 ll 25.847
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

  override def polygonLL: PolygonLL = PolygonLL(kazungula, p12, p20, lesothoWest, southEast, portLiz, agulhas, capeOfGoodHope, capeColumbine, shelleyPoint, p65, p72,
    swakopmund, nNamibia, northWest)
}

/** [[PolygonLL]] graphic object for southern Africa east depends on [[SouthAfricaWest]]. */
object SouthAfricaEast extends EarthPoly("South Africa east", -25 ll 24, hillySavannah)
{ val northEast: LatLong = -25.950 ll 31.974

  val maputoMouth: LatLong = -25.996 ll 32.580
  val inhaca: LatLong = -25.970 ll 32.992
  val richardsBay: LatLong = -29 ll 32
  val luvuvhuMouth: LatLong = -22.424 ll 31.307

  override def polygonLL: PolygonLL = PolygonLL(maputoMouth, inhaca, richardsBay, SouthAfricaWest.southEast,
    SouthAfricaWest.lesothoWest, SouthAfricaWest.p20, SouthAfricaWest.p12, luvuvhuMouth)
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