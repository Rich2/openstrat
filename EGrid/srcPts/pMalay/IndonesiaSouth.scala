/* Copyright 2018-23 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package pEarth; package pMalay
import geom._, pglobe._, egrid._, WTiles._

/** [[polygonLL]] graphical representation of Sumatra. Depends on nothing. */
object Sumatra extends EArea2("Sumatra", -0.02 ll 101.63, jungle)
{ val nSumatra: LatLong = 5.65 ll 95.43
  val neSumatra: LatLong = 5.24 ll 97.49
  val tanjungDatuk: LatLong = -0.005 ll 103.812
  val eSumatra: LatLong = -3.22 ll 106.09
  val seSumatra: LatLong = -5.90 ll 105.71
  val hutan: LatLong = -5.94 ll 104.58
  val p70: LatLong = -2.154 ll 100.820

  override val polygonLL: PolygonLL = PolygonLL(nSumatra, neSumatra, tanjungDatuk, eSumatra, seSumatra, hutan, p70)
}

/** [[polygonLL]] graphical representation of the island of Java. Depends on nothing. */
object javaIsland extends EArea2("Java", -7.39 ll 110.03, jungle)
{ val ePulauMadura: LatLong = -6.96 ll 114.11
  val pasuruan: LatLong = -7.627 ll 112.919
  val baliNorth: LatLong = -8.061 ll 115.184
  val baliWest: LatLong = -8.377 ll 115.709
  val penida: LatLong = -8.811 ll 115.591

  val seJava: LatLong = -8.75 ll 114.58
  val p30: LatLong = -8.382 ll 111.701
  val p40: LatLong = -7.382 ll 106.405
  val p44: LatLong = -6.993 ll 106.377
  val swJava: LatLong = -6.83 ll 105.24
  val nwJava: LatLong = -5.88 ll 106.04
  val p65: LatLong = -5.920 ll 107.007
  val p72: LatLong = -6.245 ll 108.348
  val p75: LatLong = -6.758 ll 108.599
  val p80: LatLong = -6.931 ll 110.462
  val p82: LatLong = -6.448 ll 110.722

  override val polygonLL: PolygonLL = PolygonLL(ePulauMadura, pasuruan, baliNorth, baliWest, penida, seJava, p30, p40, p44, swJava, nwJava, p65, p72, p75, p80,
    p82)
}

/** [[polygonLL]] graphical representation of the islands of Lambok and Sumbawa. Depends on nothing. */
object Sumbawa extends EArea2("Sumbawa", -8725 ll 117.442, jungle)
{ val north: LatLong = -8.080 ll 117.925
  val sangeangeIsland: LatLong = -8.138 ll 119.093
  val kelpaIsland: LatLong = -8.664 ll 119.238
  val southWest: LatLong = -8.829 ll 118.944
  val south: LatLong = -9.104 ll 117.013
  val lombokSW: LatLong = -8.804 ll 115.835
  val lambokNW: LatLong = -8.210 ll 116.350

  override val polygonLL: PolygonLL = PolygonLL(north, sangeangeIsland, kelpaIsland, southWest, south, lombokSW, lambokNW)
}

  /** [[polygonLL]] graphical representation of Lesser Sunda Islands except eastern islands. Depends on nothing. */
object LesserSunda extends EArea2("Lesser Sunda", -8.538 ll 118.510, jungle)
{
  val wetarNorth: LatLong = -7.559 ll 126.627
  val timorEast: LatLong = -8.402 ll 127.288
  val roteSE: LatLong = -10.936 ll 122.848

  override val polygonLL: PolygonLL = PolygonLL(wetarNorth, timorEast, roteSE)
}