/* Copyright 2018-23 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package pEarth; package pEurope
import geom._, pglobe._, egrid._, WTiles._

/** [[PolygonLL]] graphical representation of the Orkney's. */
object Orkneys extends EArea2("Orkneys", 59.06 ll -3.15, land)
{ val north: LatLong = 59.38 ll -2.88
  val stronsayE: LatLong = 59.09 ll -2.53
  val south: LatLong = 58.73 ll -2.96
  val hoyS: LatLong = 58.77 ll -3.29
  val marwick: LatLong = 59.10 ll -3.36

  override val polygonLL = PolygonLL(north, stronsayE, south, hoyS, marwick)
}

/** [[polygonLL]] graphical representation of Lewis Island. Depends on nothing. */
object IsleLewis extends EArea2("Lewis", 57.83 ll -6.09, hillyTundra)
{ val nLewis: LatLong = 58.51 ll -6.26
  val swLewis: LatLong = 57.94 ll -6.47
  val sHarris: LatLong = 57.73 ll -6.97

  val wLewis: LatLong = 58.12 ll -7.13
   
  override val polygonLL: PolygonLL = PolygonLL(nLewis, swLewis, sHarris, wLewis)
}

/** [[polygonLL]] graphical representation of Uist islands. Depends on nothing. */
object Uist extends EArea2("Uist", 57.384 ll -7.32, land)
{ val pabbay = 57.785 ll -7.225
  val northEast = 57.660 ll -7.047
  val sandray: LatLong = 56.88 ll -7.50
  val barraHead = 56.779 ll -7.636
  val wUist: LatLong = 57.60 ll -7.53

  override val polygonLL: PolygonLL = PolygonLL(pabbay, northEast, sandray, barraHead, wUist)
}


/** [[polygonLL]] Graphical representation of Ireland. Depends on nothing. */
object Ireland extends EArea2("Ireland", 53.36 ll -7.63, land)
{ val north: LatLong = 55.38 ll -7.37
  val torHead: LatLong = 55.19 ll -6.06
  val nIrelandE: LatLong = 54.48 ll -5.43
  val stJohnsPoint: LatLong = 54.23 ll -5.66
  val dundalk: LatLong = 54.01 ll -6.34
  val p25: LatLong = 53.56 ll -6.08
  val wicklowHead: LatLong = 52.97 ll -6.00
  val southEast: LatLong = 52.17 ll -6.36
  val harryLock: LatLong = 51.99 ll -7.59
  val baltimore: LatLong = 51.47 ll -9.37
  val dunquin: LatLong = 52.11 ll -10.45
  val loopHead: LatLong = 52.56 ll -9.94
  val p65: LatLong = 52.93 ll -9.47
  val rockIsland: LatLong = 53.15 ll -9.86
  val p70: LatLong = 53.41 ll -10.18
  val ardoone: LatLong = 54.30 ll -10.00
  val derkmorePoint: LatLong = 54.27 ll -8.65
  val malinBeg: LatLong = 54.66 ll -8.79
  val p95: LatLong = 55.16 ll -8.28

  override val polygonLL = PolygonLL(north, torHead, nIrelandE, stJohnsPoint, dundalk, p25, wicklowHead, southEast, harryLock, baltimore, dunquin,
    loopHead, p65, rockIsland, p70, ardoone, derkmorePoint, malinBeg, p95)
}

/** [[polygonLL]] Graphical representation of Shetland. Depends on nothing. */
object Shetland extends EArea2("Shetland", 60.34 ll -1.23, land)
{ val south: LatLong = 59.85 ll -1.27
  val sSoundsound: LatLong = 60.20 ll -1.34
  val sSilwick: LatLong = 60.14 ll -1.46
  val papaStour: LatLong = 60.33 ll -1.73
  val eshaness: LatLong = 60.48 ll -1.63
  val nUnst: LatLong = 60.84 ll -0.87
  val eWhalsay: LatLong = 60.38 ll -0.90
   
  override val polygonLL: PolygonLL = PolygonLL(south, sSoundsound, sSilwick, papaStour, eshaness, nUnst, eWhalsay)
}