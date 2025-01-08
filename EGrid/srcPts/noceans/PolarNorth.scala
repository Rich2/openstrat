/* Copyright 2018-24 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package pEarth; package noceans
import geom._, pglobe._, egrid._, WTiles._

/** [[polygonLL]] graphical representation of the near to Greenwich Artic. Depends on [[Greenland]]. */
case object ArticNear extends EarthPoly("Artic near", 85 ll 0, ice)
{ val south0: LatLong = 81.5 ll 0
  val south15: LatLong = 81.5 ll 15
  val south30: LatLong = 81.5 ll 30
  val south45: LatLong = 81.5 ll 45
  val north45: LatLong = 89 ll 45
  val north135: LatLong = 89 ll 135
  val north225: LatLong = 89 ll -135
  val north315: LatLong = 89 ll -45

  override val polygonLL: PolygonLL = LinePathLL(north135, north45, south45, south30, south15, south0) ++< Greenland.northCoast |++| LinePathLL(north315, north225)
}

/** [[polygonLL]] graphical representation of the eastern Artic. Depends on [[ArticNear]]. */
case object ArticEast extends EarthPoly("Artic east", 85 ll 0, ice)
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
case object ArticFar extends EarthPoly("Artic far", 85 ll 180, ice)
{ val south150: LatLong = 79 ll 150
  val south165: LatLong = 79 ll 165
  val south180: LatLong = 79 ll 180
  val south195: LatLong = 79 ll 195
  val south210: LatLong = 79 ll 210
  val south225: LatLong = 79 ll 225

  override val polygonLL: PolygonLL = PolygonLL(ArticNear.north225, south225, south210, south195, south180, south165, south150, ArticEast.south135, ArticNear.north135)
}

/** [[polygonLL]] graphical representation of the western Artic. Depends on [[Greenland]], [[ArticNear]] and [[ArticFar]]. */
case object ArticWest extends EarthPoly("Artic west", 85 ll -90, ice)
{ val south255: LatLong = 79 ll 255

  import pnAmer.MackenzieIslands
  override val polygonLL: PolygonLL = LinePathLL(ArticNear.north315, Greenland.naresNorth) ++< pnAmer.EllesmereIsland.northCoast |++| LinePathLL(south255,
    pnAmer.RingnesIslands.ellefNorth, MackenzieIslands.bordenNE, MackenzieIslands.bordenNorth, MackenzieIslands.west, ArticFar.south225, ArticNear.north225)
}

/** Svalbard island grouping. */
object SvalBard extends IslandPolyGroup("Svalbard")
{ override def elements: RArr[IslandPolyLike] = RArr(SpitsbergenEdge, Nordauslandet)
  override val area: Kilares = 62045.kilares
}

/** Ilsand grouping for [[Spitsbergen]] and [[EdgeIsland]]. */
object SpitsbergenEdge extends IslandPolyGroup("Svitsbergen Edge")
{ override def elements: RArr[IslandPolyLike] = RArr(Spitsbergen, EdgeIsland)
  override def oGroup: Option[IslandPolyGroup] = Some(SvalBard)
}

/** [[polygonLL]] graphical representation of Spitsbergen, Barentsoya and PrinsKarls islands. Depends on nothing. */
object Spitsbergen extends IslandPoly("Spitsbergen", 78.94 ll 17.78, ice)
{ val spitsbergen0: Kilares = 39044.kilares
  val barentsoya: Kilares = 1288.kilares
  val prinsKarls: Kilares = 615.kilares
  override val area: Kilares = spitsbergen0 + barentsoya + prinsKarls
  override def oGroup: Option[IslandPolyGroup] = Some(SpitsbergenEdge)

  val north: LatLong = 80.059 ll 16.224
  val east: LatLong = 78.83 ll 21.51
  val barentsSE: LatLong = 78.286 ll 22.077
  val barentsSW: LatLong = 78.236 ll 20.738
  val p30: LatLong = 78.47 ll 18.93
  val p40: LatLong = 77.510 ll 18.210
  val south: LatLong = 76.59 ll 16.70
  val prinsKarlNorth: LatLong = 78.839 ll 10.479
  val p80: LatLong = 78.971 ll 11.380
  val northWest: LatLong = 79.758 ll 10.698
   
  val polygonLL: PolygonLL = PolygonLL(north, east, barentsSE, barentsSW, p30, p40, south, prinsKarlNorth, p80, northWest)
}

/** [[polygonLL]] graphical representation of Nordauslandet and kvitoya islands. Depends on nothing. */
object Nordauslandet extends IslandPoly("Nordauslandet", 79.85 ll 23.71, ice)
{ val nordauslandet0: Kilares = 14443.kilares
  val kvitoya: Kilares = 682.kilares
  override val area: Kilares = nordauslandet0 + kvitoya
  override def oGroup: Option[IslandPolyGroup] = Some(SvalBard)

  val south: LatLong = 79.22 ll 23.61
  val southWest: LatLong = 79.36 ll 20.76
  val northWest: LatLong = 80.13 ll 17.72
  val north1: LatLong = 80.50 ll 19.65
  val north2: LatLong = 80.00 ll 22.23
  val north3 : LatLong= 80.51 ll 22.79
  val northEast: LatLong = 80.15 ll 26.83

  val polygonLL: PolygonLL = PolygonLL(south, southWest, northWest, north1, north2, north3, northEast)
}

/** [[polygonLL]] graphical representation of Edge island. Depends on nothing. */
object EdgeIsland extends IslandPoly("Edgeøya", 77.880 ll 22.652, hillyTundra)
{ override val area: Kilares = 5073.kilares
  override def oGroup: Option[IslandPolyGroup] = Some(SpitsbergenEdge)

  val north: LatLong = 78.262 ll 22.850
  val east: LatLong = 77.841 ll 24.239
  val south: LatLong = 77.249 ll 22.664
  val northWest: LatLong = 78.117 ll 20.835
  
  val polygonLL: PolygonLL = PolygonLL(north, east, south, northWest)
}

/** [[polygonLL]] graphical representation of the Severnaya Zemlya archipelago. Depends on nothing. */
object SevernayaZemyla extends EarthPoly("Severnaya Zemyla", 79.593 ll 96.400, ice)
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