/* Copyright 2018-24 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package pEarth; package soceans
import geom._, pglobe._, egrid._, WTiles._

/** [[polygonLL]] graphical representation of east Antarctica. Depends on nothing. */
object AntarticaEast extends EArea2("Antartica East", -89.91 ll 0, ice)
{ val filchnerNorth = -77.888 ll -37.453
  val w12: LatLong = -70.710 ll -11.645
  val w1: LatLong = -69.654 ll -0.541
  val p15: LatLong = -70.7 ll 4.77
  val p18: LatLong = -69.251 ll 15.496
  val p22: LatLong = -68.355 ll 35.688
  val p25: LatLong = -66.955 ll 46.914
  val p30: LatLong = -66.38 ll 53.46
  val p32: LatLong = -67.000 ll 61.143
  val p34: LatLong = -67.445 ll 61.207
  val p35: LatLong = -67.478 ll 63.116
  val p36: LatLong = -67.915 ll 69.063
  val p37: LatLong =  -67.744 ll 69.721
  val p38: LatLong = -67.577 ll 70.513
  val p39 :LatLong = -68.699 ll 70.382
  val p40: LatLong = -67.773 ll 81.414

  val p41: LatLong = -66.651 ll 84.182
  val p42: LatLong = -66.109 ll 95.496
  val p45: LatLong = -66.48 ll 102.33
  val p50: LatLong = -65.707 ll 113.318
  val p55: LatLong = -66.2 ll 130.98
  val p65: LatLong = -68.82 ll 153.13
  val p70: LatLong = -71.59 ll 169.13
  val e170: LatLong = -71.333 ll 170.143
  val westEnd: LatLong = -77.948 ll 166.434
  val whitmore: LatLong = -82.5 ll -104.5
  val elizabeth: LatLong = -83 ll -55
  val filchnerEast: LatLong = -79 ll -36

  override val polygonLL: PolygonLL = PolygonLL(filchnerNorth, w12, w1, p15, p18, p22, p25, p30, p32, p34, p35, p36, p37, p38, p38, p39, p40, p41, p42,
    p45, p50, p55, p65, p70, e170, westEnd, whitmore, elizabeth, filchnerEast)
}

/** [[polygonLL]] graphical representation of east Antarctica. Depends on [[AntarticaEast]]. */
object AntarticaWest extends EArea2("Antartica\nWest", -80 ll -100, ice)
{ val w156: LatLong =  -77.37 ll -156.41
  val w143: LatLong = -74.533 ll -143.411
  val w127: LatLong = -74.85 ll -127.23
  val w102: LatLong = -72.009 ll -102.270
  val p35: LatLong = -73.36 ll -98.40
  val w79: LatLong = -72.375 ll -79.011
  val w65: LatLong = -69.2 ll -65.71
  val w63: LatLong = -64.282 ll -63.451
  val joinvilleEast: LatLong = -63.221 ll -55.112
  val w62: LatLong = -74.819 ll -61.594
  val ronneWest: LatLong = -82.6 ll -60.61
  val w45: LatLong = -77.956 ll -45
  val ronneEast: LatLong = -77.969 ll -44.436

  val weddelCoast: LinePathLL = LinePathLL(joinvilleEast, w62, ronneWest, ronneEast)

  override val polygonLL: PolygonLL = LinePathLL(AntarticaEast.filchnerEast, AntarticaEast.elizabeth, AntarticaEast.whitmore, AntarticaEast.westEnd,
    w156, w143, w127, w102, p35, w79, w65, w63) |++| weddelCoast// joinvilleEast, w62, ronneWest, w45, ronneEast)
}

/** [[polygonLL]] graphical representation of Weddel Sea seasonal ice. Depends on [[AntarticaEast]] and [[AntarticaWest]]. */
object WeddelSeaIce extends EArea2("Weddel Sea\nIce", -70 ll -40, SeaIcePerm)
{ val northWest: LatLong = -63 ll AntarticaWest.joinvilleEast.longDegs
  val ow50: LatLong = -63 ll -50
  val ow40: LatLong = -63 ll -40
  val ow30: LatLong = -63 ll -30
  val ow20: LatLong = -63 ll -20
  val northEast: LatLong = -63 ll AntarticaEast.w12.longDegs

  override val polygonLL: PolygonLL = LinePathLL(northEast, AntarticaEast.w12, AntarticaEast.filchnerNorth, AntarticaEast.filchnerEast) ++<
    AntarticaWest.weddelCoast |++| LinePathLL(northWest, ow50, ow40, ow30, ow20)
}

/** [[polygonLL]] graphical representation of Weddel Sea seasonal ice. Depends on [[AntarticaEast]] and [[AntarticaWest]]. */
object RossSeaIce extends EArea2("Ross Sea\nIce", -70 ll -165, siceWin)
{ val northEast: LatLong = -63 ll AntarticaWest.w143.longDegs
  val northWest: LatLong = -63 ll AntarticaEast.e170.longDegs
  val oe180: LatLong = -63 ll 180
  val ow170: LatLong = -63 ll -170
  val ow160: LatLong = -63 ll -160
  val ow150: LatLong = -63 ll -150

  override val polygonLL: PolygonLL = PolygonLL(northEast, AntarticaWest.w143, AntarticaWest.w156, AntarticaEast.westEnd, AntarticaEast.e170, oe180,
    ow170, ow160, ow150)
}