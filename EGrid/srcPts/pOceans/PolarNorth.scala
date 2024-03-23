/* Copyright 2018-24 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package pEarth; package pOceans
import geom._, pglobe._, egrid._, WTiles._

/** [[polygonLL]] graphical representation of the western Artic. Depends on [[Greenland]], [[ArticNear]] and [[ArticFar]]. */
case object ArticWest extends EArea2("Artic west", 85 ll -90, ice)
{ val long240: LatLong = 82 ll 240
  val long255: LatLong = 82 ll 255
  val long270: LatLong = 82 ll 270
  val long285: LatLong = 82 ll 285

  override val polygonLL: PolygonLL = PolygonLL(ArticFar.long225, long240, long255, long270, long285, Greenland.nwGreenland, Greenland.nGreenland,
    ArticNear.north315, ArticNear.north225)
}

/** [[polygonLL]] graphical representation of the far from Greenwich Artic. Depends on [[ArticNear]] and [[ArticEast]]. */
case object ArticFar extends EArea2("Artic far", 85 ll 180, ice)
{ val long150: LatLong = 82 ll 150
  val long165: LatLong = 82 ll 165
  val long180: LatLong = 82 ll 180
  val long195: LatLong = 82 ll 195
  val long210: LatLong = 82 ll 210
  val long225: LatLong = 82 ll 225

  override val polygonLL: PolygonLL = PolygonLL(ArticEast.long135, long150, long165, long180, long195, long210, long225, ArticNear.north225, ArticNear.north135)
}
/** [[polygonLL]] graphical representation of the near to Greenwich Artic. Depends on [[Greenland]]. */
case object ArticNear extends EArea2("Artic near", 85 ll 0, ice)
{ val long0: LatLong = 82 ll 0
  val long15: LatLong = 82 ll 15
  val long30: LatLong = 82 ll 30
  val long45: LatLong = 82 ll 45
  val north45: LatLong = 89 ll 45
  val north135: LatLong = 89 ll 135
  val north225: LatLong = 89 ll -135
  val north315: LatLong = 89 ll -45

  override val polygonLL: PolygonLL = PolygonLL(Greenland.nGreenland, Greenland.neGreenland, long0, long15, long30, long45, north45, north135, north225,
    north315)
}

/** [[polygonLL]] graphical representation of the eastern Artic. Depends on [[ArticNear]]. */
case object ArticEast extends EArea2("Artic east", 85 ll 0, ice)
{ val long60: LatLong = 82 ll 60
  val long75: LatLong = 82 ll 75
  val long90: LatLong = 82 ll 90
  val long105: LatLong = 82 ll 105
  val long120: LatLong = 82 ll 120
  val long135: LatLong = 82 ll 135

  override val polygonLL: PolygonLL = PolygonLL(ArticNear.long45, long60, long75, long90, long105, long120, long135, ArticNear.north135, ArticNear.north45)
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
  val malyTaymyr: LatLong = 78.017 ll 107.563
  val southWest: LatLong = 77.948 ll 99.516
  val west: LatLong = 79.543 ll 90.523
  val pioneerNW: LatLong  = 80.068 ll 91.023
  val komsomoletsNW: LatLong  = 80.930 ll 93.174
  val p95: LatLong = 81.243 ll 95.177

  val polygonLL: PolygonLL = PolygonLL(north, komsomoletsEast, octoberNE, malyTaymyr, southWest, west, pioneerNW, komsomoletsNW, p95)
}