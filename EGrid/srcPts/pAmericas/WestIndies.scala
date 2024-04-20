/* Copyright 2018-24 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package pEarth; package pAmericas
import geom._, pglobe._, egrid._, WTiles._

/** [[polygonLL]] graphical representation for Cuba. Depends on nothing. */
object Cuba extends EarthArea("Cuba", 21.97 ll -78.96, jungle)
{ val west: LatLong = 21.86 ll -84.95
  val havana: LatLong = 23.14 ll -82.39
  val p15 = 22.967 ll -79.827
  val east: LatLong = 20.22 ll -74.13
  val cabotCruz: LatLong = 19.84 ll -77.73
  val yara: LatLong = 20.45 ll -77.07
  val p60: LatLong = 20.702 ll -78.039
  val surgidero: LatLong = 22.68 ll -82.29

  override def polygonLL: PolygonLL = PolygonLL(west, havana, p15, east, cabotCruz, yara, p60, surgidero)
}

/** [[polygonLL]] graphical representation for the island of Hispaniola. Depends on nothing. */
object Hispaniola extends EarthArea("Hispaniola", 19.099 ll -70.863, hillyJungle)
{ val north: LatLong = 19.931 ll -70.999
  val east: LatLong = 18.609 ll -68.324
  val islaBeata: LatLong = 17.543 ll -71.542
  val southWest: LatLong = 18.349 ll -74.447
  val northWest: LatLong = 19.843 ll -73.408

  override def polygonLL: PolygonLL = PolygonLL(north, east, islaBeata, southWest, northWest)
}

/** [[polygonLL]] graphical representation for Jamaica. Depends on nothing. */
object Jamaica extends EarthArea("Jamaica", 21.97 ll -78.96, hillyJungle)
{ val north: LatLong = 18.525 ll -77.824
  val galinaPoint: LatLong = 18.407 ll -76.887
  val morantPoint: LatLong = 17.919 ll -76.184
  val portlandPoint: LatLong = 17.705 ll -77.166
  val southWest: LatLong = 18.242 ll -78.356

  override def polygonLL: PolygonLL = PolygonLL(north, galinaPoint, morantPoint, portlandPoint, southWest)
}