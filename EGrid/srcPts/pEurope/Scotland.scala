/* Copyright 2018-24 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package pEarth; package pEurope
import geom._, pglobe._, egrid._, WTiles._

/** [[polygonLL]] Graphical representation of Shetland. Depends on nothing. */
object Shetland extends EArea2("Shetland", 60.34 ll -1.23, oceanic)
{ val south: LatLong = 59.85 ll -1.27
  val sSoundsound: LatLong = 60.20 ll -1.34
  val sSilwick: LatLong = 60.14 ll -1.46
  val papaStour: LatLong = 60.33 ll -1.73
  val eshaness: LatLong = 60.48 ll -1.63
  val nUnst: LatLong = 60.84 ll -0.87
  val eWhalsay: LatLong = 60.38 ll -0.90

  override val polygonLL: PolygonLL = PolygonLL(south, sSoundsound, sSilwick, papaStour, eshaness, nUnst, eWhalsay)
}

/** [[PolygonLL]] graphical representation of the Orkney's. */
object Orkneys extends EArea2("Orkneys", 59.06 ll -3.15, oceanic)
{ val north: LatLong = 59.38 ll -2.88
  val stronsayE: LatLong = 59.09 ll -2.53
  val south: LatLong = 58.73 ll -2.96
  val hoyS: LatLong = 58.77 ll -3.29
  val marwick: LatLong = 59.10 ll -3.36

  override val polygonLL = PolygonLL(north, stronsayE, south, hoyS, marwick)
}

/** [[polygonLL]] graphical representation of Scotland. Depends on nothing. */
object ScotlandHigh extends EArea2("Scotland\n highlands", 56.82 ll -4.07, mtainOld)
{ val johnOGroats: LatLong = 58.62 ll -3.08
  val girnigoe: LatLong = 58.47 ll -3.05
  val inverness: LatLong = 57.49 ll -4.22
  val lossieMouth: LatLong = 57.72 ll -3.33
  val aberdeenshire: LatLong = 57.58 ll -1.85
  val craighead = 56.279 ll -2.586
  val firthForth: LatLong = 56.00 ll -3.39

  val prestwick = 55.497 ll -4.620
  val buteSouth = 55.722 ll -5.029
  val arranSE = 55.445 ll -5.101
  val sKintyre: LatLong = 55.29 ll -5.77
  val addMouth = 56.091 ll -5.547

  val wScarba: LatLong = 56.17 ll -5.75
  val kerrera: LatLong = 56.38 ll -5.58
  val wMull: LatLong = 56.28 ll -6.38
  val canna: LatLong = 57.05 ll -6.60
  val wRum: LatLong = 57.00 ll -6.45
  val wSkye: LatLong = 57.43 ll -6.78
  val nSkye: LatLong = 57.70 ll -6.29

  val nRona: LatLong = 57.58 ll -5.96
  val fearnmore: LatLong = 57.585 ll -5.814
  val p95: LatLong = 57.859 ll -5.812
  val nwScotland: LatLong = 58.61 ll -4.99
  val eileanNahRon: LatLong = 58.560 ll -4.341

  override val polygonLL: PolygonLL = PolygonLL(johnOGroats, girnigoe, inverness, lossieMouth, aberdeenshire, craighead, firthForth,
    prestwick, buteSouth, arranSE, sKintyre, addMouth, wScarba, kerrera, wMull, canna, wRum,
    wSkye, nSkye, nRona, fearnmore, p95, nwScotland, eileanNahRon
  )
}

/** [[polygonLL]] graphical representation of Scottish lowlands. Depends on [[ScotlandHigh]]. */
object ScotlandLow extends EArea2("Scotland\nlowlands", 56.82 ll -4.07, hillyOce)
{ val archerfield: LatLong = 56.06 ll -2.81
  val tantallion: LatLong = 56.05 ll -2.65
  val stAbbs: LatLong = 55.91 ll -2.14
  val bamburgh: LatLong = 55.60 ll -1.68
  val tyneMouth: LatLong = 55.01 ll -1.41

  val solwayMouth: LatLong = 54.89 ll -3.37
  val eggerness: LatLong = 54.79 ll -4.34
  val sWhithorn: LatLong = 54.67 ll -4.39
  val dunragit: LatLong = 54.86 ll -4.84
  val cairngaan: LatLong = 54.63 ll -4.88
  val northCairn: LatLong = 55.00 ll -5.15

  override val polygonLL: PolygonLL = PolygonLL(ScotlandHigh.firthForth, archerfield, tantallion, stAbbs, bamburgh, tyneMouth,
    solwayMouth, eggerness, sWhithorn, dunragit, cairngaan, northCairn, ScotlandHigh.prestwick)
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
object Uist extends EArea2("Uist", 57.384 ll -7.32, oceanic)
{
  val pabbay: LatLong = 57.785 ll -7.225
  val northEast: LatLong = 57.660 ll -7.047
  val sandray: LatLong = 56.88 ll -7.50
  val barraHead: LatLong = 56.779 ll -7.636
  val wUist: LatLong = 57.60 ll -7.53

  override val polygonLL: PolygonLL = PolygonLL(pabbay, northEast, sandray, barraHead, wUist)
}

/** [[polygonLL]] graphical representation of Islay, jura and Colonsay islands. Depends on nothing. */
object IslayJura extends EArea2("Islay Jura", 55.879 ll -6.006, oceanic)
{ val juraNorth = 56.149 ll -5.709
  val islaySouth = 55.580 ll -6.293
  val portahaven: LatLong = 55.671 ll -6.514
  val snaigmore: LatLong = 55.85 ll -6.45
  val colonsayNorth = 56.133 ll -6.147

  override val polygonLL: PolygonLL = PolygonLL(juraNorth, islaySouth, portahaven, snaigmore, colonsayNorth)
}