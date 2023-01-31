/* Copyright 2018-23 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package pEarth; package pAmericas
import geom._, pglobe._, LatLong._, WTile._

/** Graphical object for the east of the United States. Dependant on [[UsaWest]], [[SouthWestCanada]], [[EastCanada]], [[LakeSuperior]],
 *  [[LakeMichigan]], [[LakeHuron]], [[LakeErie]] and [[LakeOntario]]. */
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

/** Graphical object for the east of the United States. Dependant on [[SouthWestCanada]]. */
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