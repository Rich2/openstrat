/* Copyright 2018-24 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package pEarth; package pOceans
import geom._, pglobe._, egrid._, WTiles._

/** [[polygonLL]] graphical representation of the Artic. Depends on nothing. */
case object Artic extends EArea2("Artic", 89.9 ll 0, ice)
{ val long0: LatLong = 82 ll 0
  val long15: LatLong = 82 ll 15
  val long30: LatLong = 82 ll 30
  val long45: LatLong = 82 ll 45
  val long60: LatLong = 82 ll 60
  val long75: LatLong = 82 ll 75
  val long90: LatLong = 82 ll 90
  val long105: LatLong = 82 ll 105
  val long120: LatLong = 82 ll 120
  val long135: LatLong = 82 ll 135
  val long150: LatLong = 82 ll 150
  val long165: LatLong = 82 ll 165
  val long180: LatLong = 82 ll 180
  val long195: LatLong = 82 ll 195
  val long210: LatLong = 82 ll 210
  val long225: LatLong = 82 ll 225
  val long240: LatLong = 82 ll 240
  val long255: LatLong = 82 ll 255
  val long270: LatLong = 82 ll 270
  val long285: LatLong = 82 ll 285

  override val polygonLL: PolygonLL = PolygonLL(long0, long15, long30, long45, long60, long75, long90, long105, long120, long135, long150, long165, long180, long195, long210, long225, long240,
    long255, long270, long285, Greenland.nwGreenland, Greenland.nGreenland, Greenland.neGreenland)
}

/** [[polygonLL]] graphical representation of Greenland. Depends on nothing. */
object Greenland extends EArea2("Greenland", 75 ll -42, ice)
{ val neGreenland: LatLong = 81.44 ll -11.77
  val p10: LatLong = 75.036 ll -17.426
  val semersooq: LatLong = 70.03 ll -23.07
  val p35: LatLong = 68.819 ll -25.906
  val kulusuk: LatLong = 65.53 ll -37.05
  val ortit: LatLong = 65.260 ll -39.516
  val sGreenland: LatLong = 59.87 ll -43.95

  val swGreenland: LatLong = 60.82 ll -48.07
  val aasiaat: LatLong = 68.68 ll -53.00
  val pt1: LatLong = 75.73 ll -58.98
  val wGreenland: LatLong = 78.20 ll -72.77
  val nwGreenland: LatLong = 80.33 ll -67.27
  val nGreenland: LatLong = 83.60 ll -34.19

  override val polygonLL: PolygonLL = PolygonLL(neGreenland, p10, semersooq, p35, kulusuk, ortit, sGreenland, swGreenland, aasiaat, pt1, wGreenland,
    nwGreenland, nGreenland)
}

object Svalbard extends EArea2("Svalbard", 78.94 ll 17.78, ice)
{ val south = 76.59 ll 16.70
  val wSpitsbergen = 79.54 ll 10.64
  val nSpitzbergen = 79.87 ll 13.75
  val north = 80.06 ll 16.23
  val east = 78.83 ll 21.51
  val sEdgeoya = 77.25 ll 22.67
  val pt1: LatLong = 78.47 ll 18.93
   
  val polygonLL: PolygonLL = PolygonLL(south, wSpitsbergen, nSpitzbergen, north, east, sEdgeoya, pt1)
}      

object Nordauslandet extends EArea2("Nordauslandet", 79.85 ll 23.71, ice)
{ val south = 79.22 ll 23.61
  val southWest = 79.36 ll 20.76
  val northWest = 80.13 ll 17.72
  val north1 = 80.50 ll 19.65
  val north2 = 80.00 ll 22.23
  val north3 = 80.51 ll 22.79
  val northEast: LatLong = 80.15 ll 26.83
   
  val polygonLL: PolygonLL = PolygonLL(south, southWest, northWest, north1, north2, north3, northEast)
}