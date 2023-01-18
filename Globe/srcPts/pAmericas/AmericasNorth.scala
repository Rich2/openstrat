/* Copyright 2018-23 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package pEarth; package pAmericas
import geom._, pglobe._, LatLong._, WTile._

object LakeMichigan extends EArea2("Lake Michigan", 43.82 ll -87.1, lake)
{ val mouthNorth: LatLong = 45.84 ll -84.75
  val north: LatLong = 46.10 ll -85.42
  val northWest: LatLong = 45.91 ll -86.97
  val west: LatLong = 43.04 ll -87.89
  val south: LatLong = 41.62 ll -87.25
  val pointBetsie: LatLong = 44.69 ll -86.26
  val mouthSouth: LatLong = 45.78 ll -84.75

  val coast: LinePathLL = LinePathLL(mouthNorth, north, northWest, west, south, pointBetsie, mouthSouth)

  override def polygonLL: PolygonLL = coast.reverseClose
}

object UsaEast extends EArea2("United States\neast", degs(39.8, -85.0), plain)
{ val p10 = 42.41 ll -71.00
  val chatham = 41.67 ll -69.95
  val stattenS = 40.50 ll -74.25
  val stumpyPoint = 35.69 ll -75.73

  /** Camden County Georgia USA */
  val NAtlanticSW = 31 ll  -81.47

  val seFlorida = 25.34 ll -80.39
  val swFlorida = 25.19 ll -81.13
  val nwFlorida = 30.14 ll -84.06

  override def polygonLL: PolygonLL = LinePathLL(UsaWest.galveston, SouthWestCanada.wUsaNE) ++ LakeSuperior.usCoast ++ LakeHuron.pineMouth ++ LakeMichigan.coast ++
    LakeHuron.usCoastSouth  ++ LakeErie.usCoast ++ LakeOntario.usCoast ++!
    (EastCanada.maineE, p10, chatham, stattenS, stumpyPoint, NAtlanticSW, seFlorida, swFlorida, nwFlorida, UsaWest.galveston, UsaWest.rockyPoint)
}

object UsaWest extends EArea2 ("United States\nwest", 40.0 ll - 108.0, desert)
{ val sanDiego = 32.57 ll -117.11
  val humboldt = 40.44 ll -124.40
  val capeBlanco = 42.84 ll -124.56
  val neahBay = 48.37 ll -124.67
  val galveston = 29.31 ll -94.77
  val rockyPoint = 31.16 ll -113.02
  val montague = 31.70 ll -114.71

  override def polygonLL: PolygonLL = PolygonLL(sanDiego, humboldt, capeBlanco, neahBay, SouthWestCanada.w49th, SouthWestCanada.wUsaNE, galveston, rockyPoint, montague)

  val lasVegas = LocationLL("Las Vegas", 36.17, -115.14, 2)
  val denver = LocationLL("Denver", 39.74, -105, 2)
  val losAngeles = LocationLL("Los Angeles", 34.05, -118.24, 1)

  override val places: LocationLLArr = LocationLLArr(lasVegas, denver, losAngeles)
}