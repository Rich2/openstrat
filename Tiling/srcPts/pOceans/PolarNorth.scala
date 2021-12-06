/* Copyright 2018-21 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package pEarth; package pPts
import geom._, pglobe._, LatLong._, WTile._

object PolarNorth extends EArea1("NPole", 89.5 ll 0)
{ val sGreenland = degs(59.87, -43.95)
  val swGreenland = degs(60.82, -48.07)
  val pt1 = 75.73 ll -58.98
  val wGreenland = degs(78.20, -72.77)
  val nwGreenland = degs(80.33, -67.27)
  val nGreenland = degs(83.60, -34.19)
  val neGreenland = degs(81.44, -11.77)
  val semersooq = degs(70.03, -23.07)
  val kulusuk = 65.53 ll -37.05
   
  val greenland: EArea2 = EArea2("Greenland", degs(75, -42), ice, sGreenland, swGreenland, pt1, wGreenland, nwGreenland,
     nGreenland, neGreenland, semersooq, kulusuk)
   
  val artic: EArea2 = EArea2("Artic", degs(89.9, 0), ice, WestCanada.nwAlaska, AsiaEastPts.iultinsky, AsiaEastPts.krasnoyarsk,
     degs(81.21, 15.83), semersooq, neGreenland, nGreenland, nwGreenland)
  
  override val a2Arr: Arr[EArea2] = Arr(greenland, artic, Svalbard, Nordauslandet)
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