/* Copyright 2018-24 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package pEarth; package noceans
import geom._, pglobe._, egrid._, WTiles._

/** [[polygonLL]] graphical representation of Iceland. Depends on nothing. */
object Iceland extends EarthPoly("Iceland", 64.78 ll -18.07, taiga)
{ val reykjavik: LatLong = 64.17 ll -21.75
  val w1: LatLong = 64.75 ll -22.30
  val wIceland: LatLong = 64.87 ll -24.04
  val kleifar: LatLong = 65.45 ll -21.68
  val breidavik: LatLong = 65.50 ll -24.52
  val homstrandir: LatLong = 66.46 ll -22.93
  val n1: LatLong = 65.99 ll -21.31
  val hrutafjodur: LatLong = 65.15 ll -21.07
  val n2: LatLong = 66.08 ll -20.41
  val nIceland: LatLong = 66.53 ll -16.15
  val n3: LatLong = 65.55 ll -13.67
  val eIceland: LatLong = 65.07 ll -13.49
  val vattames: LatLong = 64.92 ll -13.68
  val sIceland: LatLong = 63.40 ll -18.73
  val swIceland: LatLong = 63.8 ll -22.70

  val polygonLL: PolygonLL = PolygonLL(reykjavik, w1, wIceland, kleifar, breidavik, homstrandir, n1, hrutafjodur, n2, nIceland, n3, eIceland,
    vattames, sIceland, swIceland)
}

/** [[polygonLL]] graphical representation of Jan Mayen island. Depends on nothing. */
object JanMayen extends EarthPoly("JanMayen", 71.02 ll -8.29, taiga)
{ val south: LatLong = 70.82 ll -9.03
  val west: LatLong = 70.86 ll -9.07
  val susabu: LatLong = 71.01 ll -8.46
  val point1: LatLong = 71.08 ll -8.38
  val northEast: LatLong = 71.16 ll -7.94
  val southEast: LatLong = 71.02 ll -7.98

  override val polygonLL: PolygonLL = PolygonLL(south, west, susabu, point1, northEast, southEast)
}

/** [[PolygonLL]] graphic for the Faroe Islands. */
object Faroe extends EarthAreaIsland("Faroe", 62.14 ll -6.91, hillyOce)
{ override val area: Kilares = 1399.kilares
  
  val sSuduroy: LatLong = 61.39 ll -6.68
  val wValgar: LatLong = 62.3 ll -7.46
  val nEysturoy: LatLong = 62.34 ll -6.98
  val eFugloy: LatLong = 62.33 ll -6.25
  override val polygonLL: PolygonLL = PolygonLL(sSuduroy, wValgar, nEysturoy, eFugloy)
}