/* Copyright 2018-25 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package pEarth; package pEurope
import geom.*, pglobe.*, egrid.*, WTiles.*

/** [[PolygonLL]] graphical representation for the Jutland peninsular. */
object Jutland extends EarthPoly("Jutland", 56.29 ll 9.33, oceanic)
{ val swJutland: LatLong = 53.89 ll 8.96
  val sanktPeterOrding: LatLong = 54.32 ll 8.59
  val blavandshuk: LatLong = 55.56 ll 8.07
  val ferring: LatLong = 56.52 ll 8.11
  val hanstholm: LatLong = 57.12 ll 8.61
  val slettestrand: LatLong = 57.15 ll 9.37
  val hirtshals: LatLong = 57.59 ll 9.95
  val wSkivern: LatLong = 57.61 ll 10.25
  val skagen: LatLong = 57.71 ll 10.52
  val albaek: LatLong = 57.59 ll 10.42
  val hevring: LatLong = 56.52 ll 10.41
  val grenaa: LatLong = 56.53 ll 10.83
  val kirkeskov: LatLong = 55.63 ll 9.86
  val skaerbaek: LatLong = 55.51 ll 9.64

  val lubeck: LatLong = 53.97 ll 10.84

  val polygonLL: PolygonLL = PolygonLL(swJutland, sanktPeterOrding, blavandshuk, ferring, hanstholm,
    slettestrand, hirtshals, wSkivern, skagen, albaek, hevring, grenaa, kirkeskov, skaerbaek, lubeck)
}

/** [[PolygonLL]] graphical representation for the island of Funen. */
object Funen extends EarthPoly("Funen", 55.27 ll 10.39, oceanic)
{ val north: LatLong = 55.62 ll 10.30
  val nyborg: LatLong = 55.29 ll 10.85
  val dovnsKlint: LatLong = 54.72 ll 10.69
  val torohuse: LatLong = 55.24 ll 9.89

  val polygonLL: PolygonLL = PolygonLL(north, nyborg, dovnsKlint, torohuse)
}

/** [[polygonLL]] graphical representation of Zealand. Depends on nothing. */
object Zealand extends EarthPoly("Zealand", 55.58 ll 11.90, oceanic)
{ val zealandN: LatLong = 56.13 ll 12.29
  val helsingor: LatLong = 56.04 ll 12.62
  val mikkelborg: LatLong = 55.91 ll 12.51
  val copenhagen: LatLong = 55.62 ll 12.68
  val gedser: LatLong = 54.56 ll 11.97
  val nakskov: LatLong = 54.76 ll 11.00
  val zealandNW = 55.74 ll 10.87

  override val polygonLL: PolygonLL = PolygonLL(zealandN, helsingor, mikkelborg, copenhagen, gedser, nakskov, zealandNW)
}