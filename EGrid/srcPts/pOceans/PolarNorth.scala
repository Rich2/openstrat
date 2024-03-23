/* Copyright 2018-24 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package pEarth; package pOceans
import geom._, pglobe._, egrid._, WTiles._

/** [[polygonLL]] graphical representation of the near to Greenwich Artic. Depends on [[Greenland]]. */
case object ArticNear extends EArea2("Artic near", 85 ll 0, ice)
{ val south0: LatLong = 81.5 ll 0
  val south15: LatLong = 81.5 ll 15
  val south30: LatLong = 81.5 ll 30
  val south45: LatLong = 81.5 ll 45
  val north45: LatLong = 89 ll 45
  val north135: LatLong = 89 ll 135
  val north225: LatLong = 89 ll -135
  val north315: LatLong = 89 ll -45

  override val polygonLL: PolygonLL = PolygonLL(north135, north45, south45, south30, south15, south0, Greenland.neGreenland, Greenland.nGreenland, north315, north225)
}

/** [[polygonLL]] graphical representation of the eastern Artic. Depends on [[ArticNear]]. */
case object ArticEast extends EArea2("Artic east", 85 ll 0, ice)
{ val south135: LatLong = 79 ll 135
  val south120: LatLong = 79 ll 120
  val south110: LatLong = 79 ll 110
  val south90: LatLong = 80.5 ll 90
  val south75: LatLong = 81 ll 75
  val south60: LatLong = 81 ll 60

  override val polygonLL: PolygonLL = LinePathLL(ArticNear.north135, south135, south120, south110) ++< SevernayaZemyla.coast1 ++< SevernayaZemyla.coast2 |++|
    LinePathLL(south90, south75, south60, ArticNear.south45, ArticNear.north45)
}

/** [[polygonLL]] graphical representation of the far from Greenwich Artic. Depends on [[ArticNear]] and [[ArticEast]]. */
case object ArticFar extends EArea2("Artic far", 85 ll 180, ice)
{ val south150: LatLong = 79 ll 150
  val south165: LatLong = 79 ll 165
  val south180: LatLong = 79 ll 180
  val south195: LatLong = 79 ll 195
  val south210: LatLong = 79 ll 210
  val south225: LatLong = 79 ll 225

  override val polygonLL: PolygonLL = PolygonLL(ArticNear.north225, south225, south210, south195, south180, south165, south150, ArticEast.south135, ArticNear.north135)
}

/** [[polygonLL]] graphical representation of the western Artic. Depends on [[Greenland]], [[ArticNear]] and [[ArticFar]]. */
case object ArticWest extends EArea2("Artic west", 85 ll -90, ice)
{ val long240: LatLong = 79 ll 240
  val long255: LatLong = 79 ll 255
  val long270: LatLong = 79 ll 270
  val long285: LatLong = 79 ll 285

  override val polygonLL: PolygonLL = PolygonLL(ArticFar.south225, long240, long255, long270, long285, Greenland.nwGreenland, Greenland.nGreenland,
    ArticNear.north315, ArticNear.north225)
}

/** [[polygonLL]] graphical representation of Svalbard Island. Depends on nothing. */
object Svalbard extends EArea2("Svalbard", 78.94 ll 17.78, ice)
{ val south: LatLong = 76.59 ll 16.70
  val wSpitsbergen: LatLong = 79.54 ll 10.64
  val nSpitzbergen: LatLong = 79.87 ll 13.75
  val north: LatLong = 80.06 ll 16.23
  val east: LatLong = 78.83 ll 21.51
  val sEdgeoya: LatLong = 77.25 ll 22.67
  val pt1: LatLong = 78.47 ll 18.93
   
  val polygonLL: PolygonLL = PolygonLL(south, wSpitsbergen, nSpitzbergen, north, east, sEdgeoya, pt1)
}

/** [[polygonLL]] graphical representation of Nordauslandet Island. Depends on nothing. */
object Nordauslandet extends EArea2("Nordauslandet", 79.85 ll 23.71, ice)
{ val south: LatLong = 79.22 ll 23.61
  val southWest: LatLong = 79.36 ll 20.76
  val northWest: LatLong = 80.13 ll 17.72
  val north1: LatLong = 80.50 ll 19.65
  val north2: LatLong = 80.00 ll 22.23
  val north3 : LatLong= 80.51 ll 22.79
  val northEast: LatLong = 80.15 ll 26.83

  val polygonLL: PolygonLL = PolygonLL(south, southWest, northWest, north1, north2, north3, northEast)
}

/** [[polygonLL]] graphical representation of the Severnaya Zemlya archipelago. Depends on nothing. */
object SevernayaZemyla extends EArea2("Severnaya Zemyla", 79.593 ll 96.400, ice)
{ val north: LatLong = 81.269 ll 95.705
  val komsomoletsEast: LatLong = 80.790 ll 97.881
  val octoberNE: LatLong = 80.0588 ll 99.305
  val bolshevikNE = 78.794 ll 105.275

  val coast1 = LinePathLL(north, komsomoletsEast, octoberNE, bolshevikNE)

  val malyTaymyr: LatLong = 78.017 ll 107.563
  val southWest: LatLong = 77.948 ll 99.516
  val west: LatLong = 79.543 ll 90.523

  val pioneerNW: LatLong  = 80.068 ll 91.023
  val komsomoletsNW: LatLong  = 80.930 ll 93.174
  val p95: LatLong = 81.243 ll 95.177

  val coast2 = LinePathLL(pioneerNW, komsomoletsNW, p95)

  val polygonLL: PolygonLL = coast1 ++ LinePathLL(malyTaymyr, southWest, west) |++| coast2
}