/* Copyright 2018-23 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package pEarth; package pEurope
import geom._, pglobe._, egrid._, WTiles._

/** [[PolygonLL]] graphic for Italy depends on [[ItalySouth]]. */
object ItalyNorth extends EArea2("ItalyNorth", 43.61 ll 11.82, hilly)
{ val venice: LatLong = 45.42 ll 12.21
  val ven1: LatLong = 44.96 ll 12.55
  val cervia: LatLong = 44.25 ll 12.35
  val ancona: LatLong = 43.62 ll 13.51
  val guilianova: LatLong = 42.76 ll 13.96
  val vasto: LatLong = 42.10 ll 14.71
  val campomarina: LatLong = 41.92 ll 15.13
  val vieste: LatLong = 41.88 ll 16.17

  val gaeta = 41.20 ll 13.57
  val p55 = 41.23 ll 13.04
  val anzio = 41.45 ll 12.62
  val santaMarinella = 42.03 ll 11.83
  val puntaAla = 42.80 ll 10.73
  val livorno = 43.54 ll 10.29
  val forteDeiMarmi = 43.96 ll 10.16
  val palmaria = 44.03 ll 9.84
  val recco = 44.35 ll 9.14
  val genoa = 44.39 ll 8.94
  val voltri = 44.42 ll 8.75

  override val polygonLL = PolygonLL(venice, ven1, cervia, ancona, guilianova, vasto, campomarina, vieste, ItalySouth.siponto, ItalySouth.diProcida,
    gaeta, p55, anzio, santaMarinella, puntaAla, livorno, forteDeiMarmi, palmaria, recco, genoa, voltri)
}

/** [[PolygonLL]] graphic for the south of Italy depends on [[ItalyHeel]] and [[ItalyToe]]. */
object ItalySouth extends EArea2("ItalySouth", 40.81 ll 15.86, hilly)
{ val siponto: LatLong = 41.60 ll 15.89
  val barletta: LatLong = 41.32 ll 16.28

  val p70: LatLong = 39.99 ll 15.42
  val licosa: LatLong = 40.25 ll 14.91
  val diProcida: LatLong = 40.79 ll 14.04

  val polygonLL: PolygonLL = PolygonLL(siponto, barletta, ItalyHeel.north, ItalyHeel.northWest, ItalyToe.northEast, ItalyToe.northWest, p70, licosa, diProcida)
}

/** [[PolygonLL]] graphic for the heel of Italy depends on nothing. */
object ItalyHeel extends EArea2("Italy Heel", 40.81 ll 15.86, savannah)
{ val north = 40.905 ll 17.350
  val brindisi: LatLong = 40.65 ll 17.97
  val otranto: LatLong = 40.12 ll 18.45
  val leuca: LatLong = 39.79 ll 18.34
  val puntaPizzo: LatLong = 40.00 ll 17.99
  val p40: LatLong = 40.05 ll 17.97
  val p42: LatLong = 40.21 ll 17.86
  val capoSanVito = 40.408 ll 17.203
  val taranto: LatLong = 40.463 ll 17.245
  val northWest = 40.506 ll 17.149

  val polygonLL = PolygonLL(north, brindisi, otranto, leuca, puntaPizzo, p40, p42, capoSanVito, taranto, northWest)
}

/** [[PolygonLL]] graphic for the toe of Italy depends on nothing. */
object ItalyToe extends EArea2("Italy Toe", 40.81 ll 15.86, mtainOld)
{ val northEast: LatLong = 40.079 ll 16.605
  val bruscata: LatLong = 39.76 ll 16.48
  val mirto: LatLong = 39.62 ll 16.77
  val puntaAlice = 39.401 ll 17.156
  val capoColonna: LatLong = 39.02 ll 17.20
  val capoRizzuto: LatLong = 38.894 ll 17.093
  val stilaroMouth: LatLong = 38.43 ll 16.57

  val palizzi: LatLong = 37.95 ll 16.03
  val riaciCapo: LatLong = 37.95 ll 15.67
  val giovanni: LatLong = 38.23 ll 15.63
  val bagnaraCalabra: LatLong = 38.28 ll 15.79
  val capoVaticano: LatLong = 38.619 ll 15.828
  val lamezia: LatLong = 38.89 ll 16.22
  val northWest: LatLong = 40.062 ll 15.626

  val polygonLL: PolygonLL = PolygonLL(northEast, bruscata, mirto, puntaAlice, capoColonna, capoRizzuto, stilaroMouth, palizzi, riaciCapo, giovanni,
    bagnaraCalabra, capoVaticano, lamezia, northWest)
}