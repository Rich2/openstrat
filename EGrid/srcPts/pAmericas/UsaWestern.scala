/* Copyright 2018-24 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package pEarth; package pAmericas
import geom._, pglobe._, egrid._, WTiles._

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

/** [[PolygonLL]] Graphical object for the mid United States. Dependant on [[UsaNorthWest]], [[UsaSouthWest]], [[CanadaSouthWest]], [[LakeWinnipeg]],
 * [[CanadaCentral]],  [[LakeSuperior]] and [[UsaNorthEast]]. */
object UsaMidWest extends EArea2("United States\nMid West", 44 ll -97.0, savannah)
{
  override def polygonLL: PolygonLL = LakeSuperior.southCoast.reverse +% LakeHuron.pineMouth ++ LakeMichigan.coastWest |++|
    LinePathLL(UsaPrariesSouth.northEast, UsaSouthWest.kansasNW, UsaNorthWest.wyomingSE, UsaNorthWest.wyomingNE, UsaNorthWest.montanaSE,
      CanadaSouthWest.montanaNE, LakeWinnipeg.redMouth)
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

/** [[PolygonLL]] Graphical object for the United States south Praries. Dependant on [[UsaSouthWest]], [[UsaSouth]], and [[UsaNorthEast]]. */
object UsaPrariesSouth extends EArea2("Uunited States Praries\nsouth", 35 ll -97.0, sahel)
{ val northEast: LatLong = 40 ll -87.532
  val newOrleansSE: LatLong = 29.38 ll -89.57
  val calcasieuMouth: LatLong = 29.76 ll -93.34
  val galveston: LatLong = 29.31 ll -94.77

  override def polygonLL: PolygonLL = PolygonLL(northEast, UsaSouth.northWest, UsaSouth.gulfPort, newOrleansSE, calcasieuMouth, galveston,
    UsaSouthWest.southEast, UsaSouthWest.kansasNW)
}

/** [[PolygonLL]] graphical representation for Baja. Depends on nothing. */
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