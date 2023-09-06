/* Copyright 2018-23 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package pEarth; package pAmericas
import geom._, pglobe._, egrid._, WTile._

object Florida extends EArea2("Florida", 28.29 ll -81.59, jungle)
{ val stJohnsMouth = 30.40 ll -81.40
  val seFlorida: LatLong = 25.34 ll -80.39
  val swFlorida: LatLong = 25.19 ll -81.13

  val wakullaMouth = 30.09 ll -83.99
  override def polygonLL: PolygonLL = PolygonLL(stJohnsMouth, seFlorida, swFlorida, wakullaMouth)
}


/** [polygonLL]] Graphical object for the east of the United States. Dependant on [[CanadaSouthEast]], [[LakeSuperior]], [[LakeMichigan]],
 *  [[LakeHuron]], [[LakeErie]] and [[LakeOntario]]. */
object UsaEast extends EArea2("United States\neast", 39.8 ll -85.0, plain)
{ val p10: LatLong = 42.41 ll -71.00
  val chatham: LatLong = 41.67 ll -69.95
  val stattenS: LatLong = 40.50 ll -74.25
  val stumpyPoint: LatLong = 35.69 ll -75.73

  /** Camden County Georgia USA */
  val NAtlanticSW: LatLong = 31 ll  -81.47

  val capeSanBlas = 29.67 ll -85.35
  val p70 = 30.39 ll -86.65
  val gulfPort = 30.37 ll -89.08

  override def polygonLL: PolygonLL = LakeSuperior.southCoast +% LakeHuron.pineMouth ++
    LakeMichigan.coast ++ LakeHuron.usCoastSouth ++ LakeErie.usCoast ++ LakeOntario.usCoast |++| LinePathLL(NewBrunswick.east, NewBrunswick.maineE,
    p10, chatham, stattenS, stumpyPoint, NAtlanticSW, Florida.stJohnsMouth, Florida.wakullaMouth, capeSanBlas, p70, gulfPort)
}

/** [polygonLL]] Graphical object for the mid United States. Dependant on [[UsaWest]], [[CanadaSouthWest]], [[LakeWinnipeg]], [[CanadaCentral]],
 *  [[LakeSuperior]] and [[UsaEast]]. */
object UsaMid extends EArea2("United States\nmid", 40 ll -97.0, plain)
{ val newOrleansSE: LatLong = 29.38 ll -89.57
  val calcasieuMouth = 29.76 ll -93.34
  val galveston: LatLong = 29.31 ll -94.77

  override def polygonLL: PolygonLL = PolygonLL(LakeSuperior.west, UsaEast.gulfPort, newOrleansSE, calcasieuMouth, galveston, UsaWest.southEast,
    CanadaSouthWest.montanaNE, LakeWinnipeg.redMouth)
}

/** [polygonLL]] Graphical object for the east of the United States. Dependant on [[CanadaSouthWest]] and [[Baja]]. */
object UsaWest extends EArea2("United States\nwest", 40.0 ll -108.0, desert)
{ val sanDiego: LatLong = 32.57 ll -117.11
  val carlsbad: LatLong = 33.16 ll -117.36
  val pointVicente: LatLong = 33.74 ll -118.41
  val conceptionPoint: LatLong = 34.45 ll -120.47
  val pointReyes: LatLong = 38.00 ll -123.02
  val pointArena: LatLong = 38.95 ll -123.74
  val humboldt: LatLong = 40.44 ll -124.40
  val capeBlanco: LatLong = 42.84 ll -124.56
  val neahBay: LatLong = 48.37 ll -124.67

  val southEast = 29.31 ll -104
  val rockyPoint: LatLong = 31.16 ll -113.02

  override def polygonLL: PolygonLL = PolygonLL(sanDiego, carlsbad, pointVicente, conceptionPoint, pointReyes, pointArena, humboldt, capeBlanco,
    neahBay, CanadaSouthWest.w49th, CanadaSouthWest.montanaNE, southEast, rockyPoint, Baja.coloradoMouthWest, Baja.ensenada)

  val lasVegas: LocationLL = LocationLL("Las Vegas", 36.17, -115.14, 2)
  val denver: LocationLL = LocationLL("Denver", 39.74, -105, 2)
  val losAngeles: LocationLL = LocationLL("Los Angeles", 34.05, -118.24, 1)

  override val places: LocationLLArr = LocationLLArr(lasVegas, denver, losAngeles)
}

/** [[polygonLL]] graphical representation for Baja. Depends on nothing. */
object Baja extends EArea2("Baja", 27.80 ll -113.31, plain)
{ val coloradoMouthWest: LatLong = 31.76 ll -114.82
  val cabotPulmo: LatLong = 23.37 ll -109.44
  val sanLucas: LatLong = 22.87 ll -109.91
  val p55: LatLong = 24.80 ll -112.29
  val wBaja: LatLong = 27.84 ll -115.07
  val p70: LatLong = 28.08 ll -114.13
  val ensenada: LatLong = 31.86 ll -116.63

  override def polygonLL: PolygonLL = PolygonLL(coloradoMouthWest, cabotPulmo, sanLucas, p55, wBaja, p70, ensenada)
}