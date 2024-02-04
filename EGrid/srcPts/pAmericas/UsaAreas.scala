/* Copyright 2018-24 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package pEarth; package pAmericas
import geom._, pglobe._, egrid._, WTiles._

/** [[PolygonLL]] Graphical object for Florida. Dependant on nothing. */
object Florida extends EArea2("Florida", 28.29 ll -81.59, jungle)
{ val stJohnsMouth: LatLong = 30.40 ll -81.40
  val seFlorida: LatLong = 25.34 ll -80.39
  val swFlorida: LatLong = 25.19 ll -81.13

  val wakullaMouth: LatLong = 30.09 ll -83.99

  override def polygonLL: PolygonLL = PolygonLL(stJohnsMouth, seFlorida, swFlorida, wakullaMouth)
}

/** [[PolygonLL]] Graphical object for the north east of the United States. Dependant on [[UsaSouth]] [[CanadaSouthEast]], [[LakeMichigan]],
 *  [[LakeHuron]], [[LakeErie]] and [[LakeOntario]]. */
object UsaNorthEast extends EArea2("United States\nnorth east", 39.8 ll -85.0, land)
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
  val delawareMouth: LatLong = 39.270 ll -75.342
  val capeHenlopen: LatLong = 38.803 ll -75.092
  val delawareSE: LatLong = 38.451 ll -75.049
  val fishermanIsland: LatLong = 37.085 ll -75.961

  override def polygonLL: PolygonLL =
    LakeMichigan.coastEast ++ LakeHuron.usCoastSouth ++ LakeErie.usCoast ++ LakeOntario.usCoast |++| LinePathLL(NewBrunswick.east,
      NewBrunswick.maineE, marshallPoint, landsEnd, nahantEast, thacherIsland, deerIsland, scituateNeck, brantRock, racePoint, chatham,
      natucketIsland, sakonnetPoint, quinniapacMouth, pineIsland, plumIsland, montaukPoint, saltaire, stattenS, sandyHookNorth, sedgeIslandSouth,
      barnegat, capeMayPoint, delawareMouth, capeHenlopen, delawareSE, fishermanIsland, UsaSouth.northEast, UsaSouth.northWest)
}

/** [[PolygonLL]] Graphical object for the United States South. Dependant on [[Florida]]. */
object UsaSouth extends EArea2("United States\nThe South", 34.479 ll -83.109, land)
{ val northEast: LatLong = 36.987 ll -76.303
  val capeHenry: LatLong = 36.928 ll -76.006
  val stumpyPoint: LatLong = 35.69 ll -75.73
  val NAtlanticSW: LatLong = 31 ll  -81.47
  val capeSanBlas: LatLong = 29.67 ll -85.35
  val p70: LatLong = 30.39 ll -86.65
  val gulfPort: LatLong = 30.37 ll -89.08
  val northWest: LatLong = 36.679 ll -88.070

  override def polygonLL: PolygonLL = PolygonLL(northEast, capeHenry, stumpyPoint, NAtlanticSW, Florida.stJohnsMouth, Florida.wakullaMouth,
    capeSanBlas, p70, gulfPort, northWest)
}

/** [[PolygonLL]] Graphical object for the mid United States. Dependant on [[UsaNorthWest]], [[UsaSouthWest]], [[CanadaSouthWest]], [[LakeWinnipeg]],
 * [[CanadaCentral]],  [[LakeSuperior]] and [[UsaNorthEast]]. */
object UsaMidWest extends EArea2("United States\nMid West", 44 ll -97.0, savannah)
{
  override def polygonLL: PolygonLL = LakeSuperior.southCoast.reverse +% LakeHuron.pineMouth ++ LakeMichigan.coastWest |++|
    LinePathLL(UsaPrariesSouth.northEast, UsaSouthWest.kansasNW, UsaNorthWest.wyomingSE, UsaNorthWest.wyomingNE, UsaNorthWest.montanaSE,
      CanadaSouthWest.montanaNE, LakeWinnipeg.redMouth)
}

/** [[PolygonLL]] Graphical object for the United States south Praries. Dependant on [[UsaSouthWest]], [[UsaSouth]], and [[UsaNorthEast]]. */
object UsaPrariesSouth extends EArea2("Uunited States Praries\nsouth", 35 ll -97.0, sahel)
{ val northEast: LatLong = 40 ll -87.532
  val newOrleansSE: LatLong = 29.38 ll -89.57
  val calcasieuMouth: LatLong = 29.76 ll -93.34
  val galveston: LatLong = 29.31 ll -94.77

  override def polygonLL: PolygonLL = PolygonLL(northEast, UsaSouth.northWest, UsaSouth.gulfPort, newOrleansSE, calcasieuMouth, galveston,
    UsaSouthWest.southEast, UsaSouthWest.kansasNW)
}

/** [[PolygonLL]] Graphical object for the northwest of the United States. Dependant on [[CanadaSouthWest]]. */
object UsaNorthWest extends EArea2("United States\nnorth west", 45.5 ll -108.0, hillySahel)
{ val montanaEast: Longitude = 104.04.west
  val wyomingNorth: Latitude = 45.north
  val wyomingEast: Longitude = 104.053.west
  val wyomingSouth: Latitude = 41.north
  val wyomingWest: Longitude = 111.046.west
  val oregonSouth: Latitude = 42.north

  val montanaSE: LatLong =  wyomingNorth * montanaEast
  val wyomingNE: LatLong = wyomingNorth * wyomingEast
  val wyomingSE: LatLong = wyomingSouth * wyomingEast
  val wyomingSW: LatLong = wyomingSouth * wyomingWest
  val idahoSE: LatLong = oregonSouth * wyomingWest
  val oregonSW: LatLong = 42 ll -124.211

  val capeBlanco: LatLong = 42.84 ll -124.56
  val columbiiaMouthNorth: LatLong = 46.270 ll -124.076
  val neahBay: LatLong = 48.37 ll -124.67
  val capeGeorge: LatLong = 48.104 ll -122.885

  override def polygonLL: PolygonLL = PolygonLL(CanadaSouthWest.montanaNE, montanaSE, wyomingNE, wyomingSE, wyomingSW, idahoSE, oregonSW,
    capeBlanco, columbiiaMouthNorth, neahBay, capeGeorge, CanadaSouthWest.w49th,
  )
}

/** [[PolygonLL]] graphical object for the southwest of the United States. Dependant on [[CanadaNorthhWest]] and [[Baja]]. */
object UsaSouthWest extends EArea2("United States\nsouth west", 40.0 ll -108.0, hillySahel)
{ val sanDiego: LatLong = 32.57 ll -117.11
  val carlsbad: LatLong = 33.16 ll -117.36
  val pointVicente: LatLong = 33.74 ll -118.41
  val conceptionPoint: LatLong = 34.45 ll -120.47
  val cypressPoint: LatLong = 36.580 ll -121.977
  val elkhornMouth: LatLong = 36.806 ll -121.789
  val p80: LatLong = 36.978 ll -121.938
  val pigeonPoint: LatLong = 37.181 ll -122.394
  val pointReyes: LatLong = 38.00 ll -123.02
  val pointArena: LatLong = 38.95 ll -123.74
  val humboldt: LatLong = 40.44 ll -124.40

  val kansasNW: LatLong = 40 ll -102.051
  val southEast: LatLong = 29.31 ll -104
  val rockyPoint: LatLong = 31.16 ll -113.02

  override def polygonLL: PolygonLL = PolygonLL(sanDiego, carlsbad, pointVicente, conceptionPoint, cypressPoint, elkhornMouth, p80, pigeonPoint, pointReyes, pointArena, humboldt,
    UsaNorthWest.oregonSW, UsaNorthWest.idahoSE, UsaNorthWest.wyomingSW, UsaNorthWest.wyomingSE, kansasNW, southEast, rockyPoint, Baja.coloradoMouthWest, Baja.ensenada)

  val lasVegas: LocationLL = LocationLL("Las Vegas", 36.17, -115.14, 2)
  val denver: LocationLL = LocationLL("Denver", 39.74, -105, 2)
  val losAngeles: LocationLL = LocationLL("Los Angeles", 34.05, -118.24, 1)

  override val places: LocationLLArr = LocationLLArr(lasVegas, denver, losAngeles)
}

/** [[PolygonLL]] graphical representation for Baja. Depends on nothing. */
object Baja extends EArea2("Baja", 27.80 ll -113.31, land)
{ val coloradoMouthWest: LatLong = 31.76 ll -114.82
  val cabotPulmo: LatLong = 23.37 ll -109.44
  val sanLucas: LatLong = 22.87 ll -109.91
  val p55: LatLong = 24.80 ll -112.29
  val wBaja: LatLong = 27.84 ll -115.07
  val p70: LatLong = 28.08 ll -114.13
  val ensenada: LatLong = 31.86 ll -116.63

  override def polygonLL: PolygonLL = PolygonLL(coloradoMouthWest, cabotPulmo, sanLucas, p55, wBaja, p70, ensenada)
}