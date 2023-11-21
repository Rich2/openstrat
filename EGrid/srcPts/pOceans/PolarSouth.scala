/* Copyright 2018-23 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package pEarth; package pOceans
import geom._, pglobe._, egrid._, WTiles._


object AntarticaEast extends EArea2("Antartica East", -89.91 ll 0, ice)
{ val filchnerNorth = -77.888 ll -37.453
  val p10: LatLong = -70.710 ll -11.645
  val p12: LatLong = -69.654 ll -0.541
  val p15: LatLong = -70.7 ll 4.77
  val p18: LatLong = -69.251 ll 15.496
  val p22: LatLong = -68.355 ll 35.688
  val p25: LatLong = -66.955 ll 46.914
  val p30: LatLong = -66.38 ll 53.46
  val p32: LatLong = -67.000 ll 61.143
  val p34: LatLong = -67.445 ll 61.207
  val p35: LatLong = -67.478 ll 63.116
  val p37: LatLong = -71.39 ll 71.22
  val p38: LatLong = -69.486 ll 75.705
  val p40: LatLong = -65.764 ll 81.840
  val p41: LatLong = -66.651 ll 84.182
  val p42: LatLong = -66.109 ll 95.496
  val p45: LatLong = -66.48 ll 102.33
  val p50: LatLong = -65.707 ll 113.318
  val p55: LatLong = -66.2 ll 130.98
  val p65: LatLong = -68.82 ll 153.13
  val p70: LatLong = -71.59 ll 169.13
  val westEnd: LatLong = -77.948 ll 166.434
  val whitmore: LatLong = -82.5 ll -104.5
  val elizabeth: LatLong = -83 ll -55
  val filchnerEast: LatLong = -79 ll -36

  override val polygonLL: PolygonLL = PolygonLL(filchnerNorth, p10, p12, p15, p18, p22, p25, p30, p32, p34, p35, p37, p38, p40, p41, p42, p45, p50, p55, p65, p70, westEnd,
    whitmore, elizabeth, filchnerEast)
}

object AntarticaWest extends EArea2("Antartica West", -85 ll -100, desert)
{ val p20: LatLong =  -77.37 ll -156.41
  val p30: LatLong = -74.85 ll -127.23
  val p35: LatLong = -73.36 ll -98.40
  val p45: LatLong = -72.375 ll -79.011
  val p65: LatLong = -69.2 ll -65.71
  val p85: LatLong = -74.819 ll -61.594
  val ronneWest: LatLong = -82.6 ll -60.61
  val ronneEast: LatLong = -77.969 ll -44.436

  override val polygonLL: PolygonLL = PolygonLL(AntarticaEast.filchnerEast, AntarticaEast.elizabeth, AntarticaEast.whitmore, AntarticaEast.westEnd,
    p20, p30, p35, p45, p65, p85, ronneWest, ronneEast)
}