/* Copyright 2018-21 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package pEarth; package pPts
import geom._, pglobe._, LatLong._, WTile._

object WestCanada extends EArea2("West Canada", degs(64.051, -129.98), taiga)
{ val w49th: LatLong = 49 ll -125.66
  val yakut: LatLong = 59.93 ll -141.03
  val swAlaska: LatLong = 60.50 ll -164.55
  val nwAlaska: LatLong = 70.11 ll -161.87
  val east = 110.west
  val northEast: LatLong = 72.97.north * east
  val southEast: LatLong = 49.north * east
  override def polygonLL: PolygonLL = PolygonLL(w49th, yakut, swAlaska, nwAlaska, northEast, southEast)
}

object CentralCanada extends EArea2("Central Canada", 56.0 ll -98.0, taiga)
{ val nwPass: LatLong = 69.5 ll -82.82
  val eggIsland : LatLong= 59.91 ll -94.85
  val jamesBayNW: LatLong = 55.07 ll -82.31
  val jamesBayS: LatLong = 51.14 ll -79.79

  override def polygonLL: PolygonLL = LinePathLL(WestCanada.southEast, WestCanada.northEast, nwPass, eggIsland, jamesBayNW, jamesBayS, LakeHuron.northEast) ++!
    LakeSuperior.canadaCoast
}

object EastCanada extends EArea2("East Canada", degs(53.71, -70), taiga)
{ val hudsonBayMouthE: LatLong = 62.57 ll -77.99
  val ungavaW: LatLong = 61.04 ll -69.56
  val ungavaS: LatLong = 58.26 ll -67.45

  val ungavaE: LatLong = 60.49 ll -64.74
  val labradorE: LatLong = 52.42 ll -56.05
  val newFoundlandE: LatLong = 47.52 ll -52.64
  val eNovaScotia: LatLong = 46.16 ll -59.86

  //val e49th = deg(49, -64.41) Removed as Newfoundland is the close
  val maineE = degs(44.87, -66.93)

  val eCanadaCoast = LinePathLL(ungavaE, labradorE, newFoundlandE, eNovaScotia)

  override val polygonLL: PolygonLL = LinePathLL(LakeHuron.south, LakeHuron.tobermory, LakeHuron.geogianSouth, LakeHuron.east, LakeHuron.northEast,
    CentralCanada.jamesBayS, hudsonBayMouthE, ungavaW, ungavaS) ++
    eCanadaCoast ++!
    (maineE, LakeOntario.wolfeSW, LakeOntario.northEast, LakeOntario.frenchmansBay, LakeOntario.southWest, LakeOntario.niagraMouth,
      LakeErie.niagraMouth, LakeErie.portStanley, LakeErie.detroitMouth)
}

object LakeSuperior extends EArea2("Lake Superior", 47.5 ll -88, lake)
{ val east: LatLong = 46.52 ll -84.61
  val michipicoten: LatLong = 47.96 ll -84.86
  val north: LatLong = 48.80 ll -87.31
  val west48: LatLong = 48.00 ll -89.57
  val canadaCoast = LinePathLL(east, michipicoten, north, west48)

  val west: LatLong = 46.77 ll -92.11
  val montrealMouth: LatLong = 44.57 ll -90.42
  val highRock : LatLong = 47.42 ll -87.71
  val chocolayMouth: LatLong = 46.50 ll -87.35
  val usCoast = LinePathLL(west48, west, montrealMouth, highRock, chocolayMouth, east)

  override def polygonLL: PolygonLL = canadaCoast.reverse +--! usCoast.reverse
}

object LakeHuron extends EArea2("Lake Michigan", 43.82 ll -87.1, lake)
{ val south: LatLong = 43.00 ll -82.42
  val northEast: LatLong = 45.90 ll -80.70
  val east: LatLong = 44.86 ll -79.89
  val geogianSouth: LatLong = 44.47 ll -80.11
  val tobermory: LatLong = 45.26 ll -81.69
  override def polygonLL: PolygonLL = PolygonLL(south, LakeMichigan.mouthSouth, LakeMichigan.mouthNorth, northEast, east, geogianSouth,
    tobermory)
}

object LakeErie extends EArea2("Lake Erie", 42.24 ll -81.03, lake)
{ val maumeeMouth: LatLong = 41.70 ll -83.47
  val detroitMouth: LatLong = 42.05 ll -83.15
  val niagraMouth: LatLong = 42.89 ll -78.91
  val portStanley: LatLong = 42.66 ll -81.24
  val east: LatLong = 42.78 ll -78.85
  val south: LatLong = 41.38 ll -82.49
  override def polygonLL: PolygonLL = PolygonLL(maumeeMouth, detroitMouth, niagraMouth, portStanley, east, south)
}

object LakeOntario extends EArea2("Lake Ontario", 43.65 ll -77.84, lake)
{ val southWest: LatLong = 43.30 ll -79.82
  val frenchmansBay: LatLong = 43.81 ll -79.09
  val northEast: LatLong = 44.20 ll -76.51
  val wolfeSW: LatLong = 44.10 ll -76.44
  val tibbettsPoint =  44.10 ll -76.37
  val southEast: LatLong = 43.53 ll -76.22
  val niagraMouth: LatLong = 43.26 ll -79.07
  override def polygonLL: PolygonLL = PolygonLL(southWest, frenchmansBay, northEast, wolfeSW, tibbettsPoint, southEast, niagraMouth)
}