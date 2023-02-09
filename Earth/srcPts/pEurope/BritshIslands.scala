/* Copyright 2018-23 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package pEarth; package pEurope
import geom._, pglobe._, WTile._

/** [[PolygonLL]] graphical representation of the Orkney's */
object Orkneys extends EArea2("Orkneys", 59.06 ll -3.15, plain)
{ val north: LatLong = 59.38 ll -2.88
  val stronsayE: LatLong = 59.09 ll -2.53
  val south: LatLong = 58.73 ll -2.96
  val hoyS: LatLong = 58.77 ll -3.29
  val marwick: LatLong = 59.10 ll -3.36

  override val polygonLL = PolygonLL(north, stronsayE, south, hoyS, marwick)
}

/** [[polygonLL]] graphical representation of Outer Hebrides. Depends on nothing. */
object OuterHebrides extends EArea2("OuterHebrides", 57.83 ll -6.09, plain)
{ val nLewis: LatLong = 58.51 ll -6.26
  val swLewis: LatLong = 57.94 ll -6.47
  val sHarris: LatLong = 57.73 ll -6.97
  val sandray: LatLong = 56.88 ll -7.50
  val wUist: LatLong = 57.60 ll -7.53
  val wLewis: LatLong = 58.12 ll -7.13
   
  override val polygonLL: PolygonLL = PolygonLL(nLewis, swLewis, sHarris, sandray, wUist, wLewis)
}

/** [[polygonLL]] Graphical representation of Ireland. Depends on nothing. */
object Ireland extends EArea2("Ireland", 53.36 ll -7.63, plain)
{ val north: LatLong = 55.38 ll -7.37
  val torHead: LatLong = 55.19 ll -6.06
  val nIrelandE: LatLong = 54.48 ll -5.43
  val dundalk: LatLong = 54.01 ll -6.34
  val irelandSE: LatLong = 52.17 ll -6.36
  val baltimore: LatLong = 51.47 ll -9.37
  val dunquin: LatLong = 52.11 ll -10.45
  val oranmore: LatLong = 53.27 ll -8.93
  val carrowteige: LatLong = 53.32 ll -9.85
  val ardoone: LatLong = 54.30 ll -10.00
  val derkmorePoint: LatLong = 54.27 ll -8.65
  val malinBeg: LatLong = 54.66 ll -8.79

  val polygonLL = PolygonLL(north, torHead, nIrelandE, dundalk,irelandSE, baltimore, dunquin, oranmore, carrowteige, ardoone, derkmorePoint, malinBeg)
}

/** [[polygonLL]] Graphical representation of Shetland. Depends on nothing. */
object Shetland extends EArea2("Shetland", 60.34 ll -1.23, plain)
{ val south = 59.85 ll -1.27
  val sSoundsound = 60.20 ll -1.34
  val sSilwick = 60.14 ll -1.46
  val papaStour = 60.33 ll -1.73
  val eshaness = 60.48 ll -1.63
  val nUnst = 60.84 ll -0.87
  val eWhalsay = 60.38 ll -0.90
   
  override val polygonLL = PolygonLL(south, sSoundsound, sSilwick, papaStour, eshaness, nUnst, eWhalsay)
}