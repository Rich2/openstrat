/* Copyright 2018-24 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package pEarth; package pEurope
import geom._, pglobe._, egrid._, WTiles._

/** Alsace Lorraine, no map dependencies. */
object Alsace extends EArea2("Alsace", 48.75 ll 7.42, hillyOce)
{ val northWest: LatLong = 49.50 ll 5.89
  val luxSE: LatLong = 49.46 ll 6.37
  val east: LatLong = 48.97 ll 8.23
  val basel: LatLong = 47.56 ll 7.58
  val southWest: LatLong = 47.50 ll 7.13
  val hautRhinNW: LatLong = 48.31 ll 7.19
  val moselleSW: LatLong = 48.54 ll 7.07

  val p10: LatLong = 49.258 ll 6.03
  override val polygonLL: PolygonLL = PolygonLL(northWest, luxSE, east, basel, southWest, hautRhinNW, moselleSW, p10)
}

/** Belgium and Luxembourg. Depends on Alsace. */
object BelgLux extends EArea2("BelgLux", 50.6 ll 4.78, oceanic)
{ val northWest: LatLong = 51.09 ll 2.54
  val zeebrugge: LatLong = 51.339 ll 3.185
  val coastEast: LatLong = 51.36 ll 3.37
  val north: LatLong = 51.49 ll 5.04
  val aachen: LatLong = 50.78 ll 6.08
  val p10: LatLong = 49.50 ll 5.47

  override val polygonLL: PolygonLL = PolygonLL(northWest, zeebrugge, coastEast, north, aachen, Alsace.luxSE, Alsace.northWest, p10)
}

/** Displays most of France. Depends on [[Alsace]] and [[BelgLux]]. */
object FranceNorth extends EArea2("France north", 47.28 ll 1.93, oceanic)
{ val southWest: LatLong = divN45 ll -1.29
  val southEast: LatLong = divN45 ll 5.53
  val sLAmelie: LatLong = 45.47 ll -1.15
  val royan: LatLong = 45.61 ll -1.04
  val laCoubre: LatLong = 45.69 ll -1.23
  val laRochelle: LatLong = 46.15 ll -1.22
  val niortaise: LatLong = 46.30 ll -1.13
  val sablesdOlonne: LatLong = 46.49 ll -1.80
  val penmarch: LatLong = 47.80 ll -4.37
  val vilaineMouth: LatLong = 47.49 ll -2.44
  val brest: LatLong = 48.42 ll - 4.73
  val landunvez: LatLong = 48.56 ll -4.72
  val pleubian: LatLong = 48.86 ll -3.10
  val yffiniac: LatLong = 48.49 ll -2.68
  val capFrehel: LatLong = 48.68 ll -2.31
  val pointeDuGrouin: LatLong = 48.71 ll -1.84
  val vildeLaMarine: LatLong = 48.61 ll -1.84
  val avranches: LatLong = 48.66 ll -1.45
  val cabaneVauban: LatLong = 48.74 ll -1.57
  val auderville: LatLong = 49.73 ll -1.94
  val gatteville: LatLong = 49.69 ll -1.26
  val carentan: LatLong = 49.36 ll -1.16
  val cabourg: LatLong = 49.29 ll -0.18
  val villierville: LatLong = 49.40 ll 0.13
  val seineMouth: LatLong = 49.43 ll 0.23
  val wLeHavre: LatLong = 49.51 ll 0.06
  val capAntifer: LatLong = 49.69 ll 0.16
  val cayeux: LatLong = 50.18 ll 1.49
  val capGrisNez: LatLong = 50.87 ll 1.58
  val calais: LatLong = 50.93 ll 1.74

  val bourgeEnBresse: LatLong = 46.20 ll 5.22

  override val polygonLL: PolygonLL = PolygonLL(southEast, southWest, sLAmelie, royan, laCoubre, laRochelle, niortaise, sablesdOlonne, vilaineMouth, penmarch,
    brest, landunvez, pleubian, yffiniac, capFrehel, pointeDuGrouin, vildeLaMarine, avranches,  cabaneVauban, auderville, gatteville, carentan,
    cabourg,villierville, seineMouth, wLeHavre, capAntifer, cayeux, capGrisNez, calais, BelgLux.northWest, BelgLux.p10, Alsace.northWest, Alsace.p10,
    Alsace.moselleSW, Alsace.hautRhinNW, Alsace.southWest, bourgeEnBresse)

  val paris: LocationLL = LocationLL("Paris", 48.86, 2.35, 1)
  val lille: LocationLL = LocationLL("Lille", 50.63, 3.06, 2)

  override val places: LocationLLArr = LocationLLArr(paris)
}