/* Copyright 2018-24 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package pEarth; package pAmericas
import geom._, pglobe._, egrid._, WTiles._

/** [[PolygonLL]] Graphical object for the Lower Penisular of Michigan. Dependant on [[LakeMichigan]], [[LakeHuron]] and [[LakeErie]]. */
object MicheganLower extends EArea2("Lower Peninsular", 43.585 ll -84.611, taiga)
{ override def polygonLL: PolygonLL = LakeMichigan.coastEast ++ LakeHuron.usCoastSouth |++| LinePathLL(LakeErie.detroitMouth, LakeErie.maumeeMouth)
}

/** [[PolygonLL]] Graphical object for the north east of the United States. Dependant on [[UsaSouth]] [[CanadaSouthEast]], [[LakeMichigan]],
 *  [[LakeHuron]], [[LakeErie]] and [[LakeOntario]]. */
object UsaNorthEast extends EArea2("United States\nnorth east", 39.8 ll -85.0, hilly)
{ val marshallPoint: LatLong = 43.916 ll -69.258
  val landsEnd: LatLong = 42.635 ll -70.594
  val nahantEast: LatLong = 42.419 ll -70.902
  val thacherIsland: LatLong = 42.639 ll -70.573
  val deerIsland: LatLong = 42.344 ll -70.953
  val scituateNeck: LatLong = 42.253 ll -70.767
  val brantRock: LatLong = 42.082 ll -70.639
  val racePoint: LatLong = 42.082 ll -70.207
  val chatham: LatLong = 41.67 ll -69.95
  val natucketIsland: LatLong = 41.255 ll -69.972
  val sakonnetPoint: LatLong = 41.456 ll -71.194

  val quinniapacMouth: LatLong = 41.257 ll -72.917
  val pineIsland: LatLong = 40.898 ll -73.764
  val plumIsland: LatLong = 41.190 ll -72.163
  val montaukPoint: LatLong = 41.070 ll -71.856
  val saltaire: LatLong = 40.635 ll -73.195

  val stattenS: LatLong = 40.50 ll -74.25
  val sandyHookNorth: LatLong = 40.478 ll -74.016
  val sedgeIslandSouth: LatLong = 39.766 ll -74.097
  val barnegat: LatLong = 39.759 ll -74.100
  val capeMayPoint: LatLong = 38.932 ll -74.962
  val fortHoward: LatLong = 39.196 ll -76.443
  val covePoint: LatLong = 38.385 ll -76.381
  val littleWicomicaMouth: LatLong = 37.890 ll -76.236

  override def polygonLL: PolygonLL = LakeErie.southCoast ++ LakeOntario.usCoast |++|
    LinePathLL(NewBrunswick.east, NewBrunswick.maineE, marshallPoint, landsEnd, nahantEast, thacherIsland, deerIsland, scituateNeck, brantRock,
      racePoint, chatham, natucketIsland, sakonnetPoint, quinniapacMouth, pineIsland, plumIsland, montaukPoint, saltaire, stattenS, sandyHookNorth,
      sedgeIslandSouth, barnegat, capeMayPoint, Delmarva.northEast, Delmarva.northWest, fortHoward, covePoint, littleWicomicaMouth,
      UsaSouth.northEast, UsaSouth.northWest, LakeMichigan.south)
}

/** [[PolygonLL]] Graphical object for the north east of the United States. Dependant on nothing. */
object Delmarva extends EArea2("Delmarva Peninsula", 38.777 ll -75.233, land)
{
  val northEast: LatLong = 39.589 ll -75.599
  val capeHenlopen: LatLong = 38.803 ll -75.092
  val delawareSE: LatLong = 38.451 ll -75.049
  val assateagueHook: LatLong = 37.852 ll -75.382
  val fishermanIsland: LatLong = 37.085 ll -75.961
  val p70: LatLong = 37.925 ll -75.896
  val taylorsIsland: LatLong = 38.481 ll -76.333
  val northWest: LatLong = 39.592 ll -75.945

  override def polygonLL: PolygonLL = PolygonLL(northEast, capeHenlopen, delawareSE, assateagueHook, fishermanIsland, p70, taylorsIsland, northWest)
}

/** [[PolygonLL]] Graphical object for the United States South. Dependant on [[Florida]]. */
object UsaSouth extends EArea2("United States\nThe South", 34.479 ll -83.109, land)
{ val northEast: LatLong = 36.987 ll -76.303
  val capeHenry: LatLong = 36.928 ll -76.006
  val rodanthe: LatLong = 35.591 ll -75.460
  val hatterasSE: LatLong = 35.221 ll -75.525
  val capeLookout: LatLong = 34.582 ll -76.533
  val capeFear: LatLong = 33.843 ll -77.958
  val p40: LatLong = 33.900 ll -78.382
  val p45: LatLong = 33.207 ll -79.173
  val morrisNorth = 32.742 ll -79.872
  val NAtlanticSW: LatLong = 31 ll  -81.47
  val capeSanBlas: LatLong = 29.67 ll -85.35
  val p70: LatLong = 30.39 ll -86.65
  val gulfPort: LatLong = 30.37 ll -89.08
  val northWest: LatLong = 36.679 ll -88.070

  override def polygonLL: PolygonLL = PolygonLL(northEast, capeHenry, rodanthe, hatterasSE, capeLookout, capeFear, p40, p45, morrisNorth, NAtlanticSW, Florida.stJohnsMouth, Florida.wakullaMouth,
    capeSanBlas, p70, gulfPort, northWest)
}

/** [[PolygonLL]] Graphical object for Florida. Dependant on nothing. */
object Florida extends EArea2("Florida", 28.29 ll -81.59, jungle)
{ val stJohnsMouth: LatLong = 30.40 ll -81.40
  val p35 = 26.796 ll -80.031
  val seFlorida: LatLong = 25.34 ll -80.39
  val swFlorida: LatLong = 25.19 ll -81.13

  val wakullaMouth: LatLong = 30.09 ll -83.99

  override def polygonLL: PolygonLL = PolygonLL(stJohnsMouth, p35, seFlorida, swFlorida, wakullaMouth)
}