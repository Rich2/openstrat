/* Copyright 2018-24 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package pEarth; package pEurope
import geom._, pglobe._, egrid._, WTiles._

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

/** [[polygonLL]] graphical representation of south Sweden. Depends on nothing. */
object SwedenSouth extends EarthPoly("SwedenSouth", 58.25 ll 15.14, oceanic)
{ //South Baltic Coast
  val gavie: LatLong = 60.68 ll 17.21
  val gardskarE: LatLong = 60.63 ll 17.67
  val klungstenN: LatLong = 60.60 ll 17.99
  val stenskar: LatLong = 60.36 ll 18.31
  val orskar: LatLong = 60.53 ll 18.39
  val kappelskar: LatLong = 59.75 ll 19.08
  val herrhamra: LatLong = 58.80 ll 17.84
  val hummelvik: LatLong = 58.62 ll 17.01
  val torhamn: LatLong = 56.07 ll 15.84
   
  //South Baltic Coast
  val stenshamn: LatLong = 56.01 ll 15.78
  val pukavik: LatLong = 56.16 ll 14.68
  val ahus: LatLong = 55.92 ll 14.32
  val simrishamn: LatLong = 55.55 ll 14.35
  val sandhammaren: LatLong = 55.38 ll 14.19
  val vellinge: LatLong = 55.41 ll 13.02
  val helsingborg: LatLong = 56.04 ll 12.69
  val kullens: LatLong = 56.30 ll 12.44
  val torekov: LatLong = 56.42 ll 12.62
  val bastad: LatLong = 56.43 ll 12.85
  val andersberg: LatLong = 56.65 ll 12.87
  val sTylosand: LatLong = 56.64 ll 12.72
  val wHono: LatLong = 57.69 ll 11.60
  val p95: LatLong = 59.07 ll 10.85
  val rauer: LatLong = 59.23 ll 10.68
  val oslo: LatLong = 59.57 ll 10.59

  override val polygonLL: PolygonLL = PolygonLL(gavie, gardskarE, klungstenN, stenskar, orskar, kappelskar, herrhamra, hummelvik, torhamn, stenshamn, pukavik,
    ahus, simrishamn, sandhammaren, vellinge, helsingborg, kullens, torekov, bastad, andersberg, sTylosand, wHono, p95, rauer, oslo)
}

/** [[PolygonLL]] graphical representation for the island of Oland. */
object Oland extends EarthPoly("Oland", 56.77 ll 16.67, oceanic)
{ val north: LatLong = 57.37 ll 17.08
  val p10: LatLong = 57.31 ll 17.15
  val p20: LatLong = 56.85 ll 16.87
  val south: LatLong = 56.19 ll 16.39
  val p30: LatLong = 56.22 ll 16.37
  val p40: LatLong = 56.88 ll 16.65
  val p50: LatLong = 57.29 ll 16.96

  override val polygonLL: PolygonLL = PolygonLL(north, p10, p20, south, p30, p40, p50)
}