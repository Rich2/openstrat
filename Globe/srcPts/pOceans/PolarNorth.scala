/* Copyright 2018-23 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package pEarth; package pOceans
import geom._, pglobe._, LatLong._, WTile._

object Artic extends EArea2("Artic", 89.9 ll 0, ice)
{ val long0 = 82 ll 0
  val long15 = 82 ll 15
  val long30 = 82 ll 30
  val long45 = 82 ll 45
  val long60 = 82 ll 60
  val long75 = 82 ll 75
  val long90 = 82 ll 90
  val long105 = 82 ll 105
  val long120 = 82 ll 120
  val long135 = 82 ll 135
  val long150 = 82 ll 150
  val long165 = 82 ll 165
  val long180 = 82 ll 180
  val long195 = 82 ll 195
  val long210 = 82 ll 210
  val long225 = 82 ll 225
  val long240 = 82 ll 240
  val long255 = 82 ll 255
  val long270 = 82 ll 270
  val long285 = 82 ll 285
  override val polygonLL: PolygonLL = PolygonLL(long0, long15, long30, long45, long60, long75, long90, long105, long120, long135, long150, long165, long180, long195, long210, long225, long240,
    long255, long270, long285, Greenland.nwGreenland, Greenland.nGreenland, Greenland.neGreenland)
}

object Greenland extends EArea2("Greenland", degs(75, -42), ice){

  val sGreenland = degs(59.87, -43.95)
  val swGreenland = degs(60.82, -48.07)
  val aasiaat = 68.68 ll -53.00
  val pt1 = 75.73 ll -58.98
  val wGreenland = degs(78.20, -72.77)
  val nwGreenland = degs(80.33, -67.27)
  val nGreenland = degs(83.60, -34.19)
  val neGreenland = degs(81.44, -11.77)
  val semersooq = degs(70.03, -23.07)
  val kulusuk = 65.53 ll -37.05

  override val polygonLL: PolygonLL = PolygonLL(sGreenland, swGreenland, aasiaat, pt1, wGreenland, nwGreenland,
    nGreenland, neGreenland, semersooq, kulusuk)
}

object Svalbard extends EArea2("Svalbard", 78.94 ll 17.78, ice)
{ val south = 76.59 ll 16.70
  val wSpitsbergen = 79.54 ll 10.64
  val nSpitzbergen = 79.87 ll 13.75
  val north = 80.06 ll 16.23
  val east = 78.83 ll 21.51
  val sEdgeoya = 77.25 ll 22.67
  val pt1 = 78.47 ll 18.93
   
  val polygonLL: PolygonLL = PolygonLL(south, wSpitsbergen, nSpitzbergen, north, east, sEdgeoya, pt1)
}      

object Nordauslandet extends EArea2("Nordauslandet", 79.85 ll 23.71, ice)
{ val south = 79.22 ll 23.61
  val southWest = 79.36 ll 20.76
  val northWest = 80.13 ll 17.72
  val north1 = 80.50 ll 19.65
  val north2 = 80.00 ll 22.23
  val north3 = 80.51 ll 22.79
  val northEast = 80.15 ll 26.83
   
  val polygonLL: PolygonLL = PolygonLL(south, southWest, northWest, north1, north2, north3, northEast)
}