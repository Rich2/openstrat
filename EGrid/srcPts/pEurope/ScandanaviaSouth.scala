/* Copyright 2018-23 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package pEarth; package pEurope
import geom._, pglobe._, egrid._, WTiles._

/** [[PolygonLL]] graphical representation for the Jutland peninsular. */
object Jutland extends EArea2("Jutland", 56.29 ll 9.33, temperate)
{ val swJutland: LatLong = 53.89 ll 8.96
  val sanktPeterOrding = 54.32 ll 8.59
  val blavandshuk = 55.56 ll 8.07
  val ferring = 56.52 ll 8.11
  val hanstholm = 57.12 ll 8.61
  val slettestrand = 57.15 ll 9.37
  val hirtshals = 57.59 ll 9.95
  val wSkivern = 57.61 ll 10.25
  val skagen = 57.71 ll 10.52
  val albaek = 57.59 ll 10.42
  val hevring = 56.52 ll 10.41
  val grenaa = 56.53 ll 10.83
  val kirkeskov = 55.63 ll 9.86
  val skaerbaek = 55.51 ll 9.64

  val lubeck: LatLong = 53.97 ll 10.84
   
  val polygonLL = PolygonLL(swJutland, sanktPeterOrding, blavandshuk, ferring, hanstholm,
      slettestrand, hirtshals, wSkivern, skagen, albaek, hevring, grenaa, kirkeskov, skaerbaek, lubeck)
}

/** [[PolygonLL]] graphical representation for the island of Funen. */
object Funen extends EArea2("Funen", 55.27 ll 10.39, temperate)
{ val north: LatLong = 55.62 ll 10.30
  val nyborg = 55.29 ll 10.85
  val dovnsKlint = 54.72 ll 10.69
  val torohuse = 55.24 ll 9.89

  val polygonLL = PolygonLL(north, nyborg, dovnsKlint, torohuse)
}

/** [[polygonLL]] graphical representation of Zealand. Depends on nothing. */
object Zealand extends EArea2("Zealand", 55.58 ll 11.90, temperate)
{ val zealandN = 56.13 ll 12.29
  val helsingor = 56.04 ll 12.62
  val mikkelborg = 55.91 ll 12.51
  val copenhagen = 55.62 ll 12.68
  val gedser = 54.56 ll 11.97
  val nakskov = 54.76 ll 11.00
  val zealandNW = 55.74 ll 10.87
   
  override val polygonLL = PolygonLL(zealandN, helsingor, mikkelborg, copenhagen, gedser, nakskov, zealandNW)
}

/** [[polygonLL]] graphical representation of south Sweden. Depends on nothing. */
object SwedenSouth extends EArea2("SwedenSouth", 58.25 ll 15.14, temperate)
{ //South Baltic Coast
  val gavie: LatLong = 60.68 ll 17.21
  val gardskarE: LatLong = 60.63 ll 17.67
  val klungstenN: LatLong = 60.60 ll 17.99
  val stenskar = 60.36 ll 18.31
  val orskar = 60.53 ll 18.39
  val kappelskar = 59.75 ll 19.08
  val herrhamra = 58.80 ll 17.84
  val hummelvik = 58.62 ll 17.01
  val torhamn = 56.07 ll 15.84
   
  //South Baltic Coast
  val stenshamn = 56.01 ll 15.78
  val pukavik = 56.16 ll 14.68
  val ahus = 55.92 ll 14.32
  val simrishamn = 55.55 ll 14.35
  val sandhammaren = 55.38 ll 14.19
  val vellinge = 55.41 ll 13.02
  val helsingborg = 56.04 ll 12.69
  val kullens = 56.30 ll 12.44
  val torekov = 56.42 ll 12.62
  val bastad = 56.43 ll 12.85
  val andersberg = 56.65 ll 12.87
  val sTylosand = 56.64 ll 12.72
  val wHono = 57.69 ll 11.60
  val p95 = 59.07 ll 10.85
  val rauer = 59.23 ll 10.68
  val oslo: LatLong = 59.57 ll 10.59
   
  override val polygonLL = PolygonLL(gavie, gardskarE, klungstenN, stenskar, orskar, kappelskar, herrhamra, hummelvik, torhamn, stenshamn, pukavik,
    ahus, simrishamn, sandhammaren, vellinge, helsingborg, kullens, torekov, bastad, andersberg, sTylosand, wHono, p95, rauer, oslo)
}

/** [[PolygonLL]] graphical representation for the island of Oland. */
object Oland extends EArea2("Oland", 56.77 ll 16.67, temperate)
{ val north: LatLong = 57.37 ll 17.08
  val p10: LatLong = 57.31 ll 17.15
  val p20: LatLong = 56.85 ll 16.87
  val south: LatLong = 56.19 ll 16.39
  val p30: LatLong = 56.22 ll 16.37
  val p40: LatLong = 56.88 ll 16.65
  val p50: LatLong = 57.29 ll 16.96

  override val polygonLL = PolygonLL(north, p10, p20, south, p30, p40, p50)
}

/** [[PolygonLL]] graphic for the FaroeIslands. */
object Faroe extends EArea2("Faroe", 62.14 ll -6.91, hillyTemp)
{ val sSuduroy = 61.39 ll -6.68
  val wValgar = 62.3 ll -7.46
  val nEysturoy = 62.34 ll -6.98
  val eFugloy = 62.33 ll -6.25
  override val polygonLL: PolygonLL = PolygonLL(sSuduroy, wValgar, nEysturoy, eFugloy)
}

object JanMayen extends EArea2("JanMayen", 71.02 ll -8.29, taiga)
{ val south: LatLong = 70.82 ll -9.03
  val west: LatLong = 70.86 ll -9.07
  val susabu = 71.01 ll -8.46
  val point1: LatLong = 71.08 ll -8.38
  val northEast: LatLong = 71.16 ll -7.94
  val southEast: LatLong = 71.02 ll -7.98
   
  override val polygonLL: PolygonLL = PolygonLL(south, west, susabu, point1, northEast, southEast)
}