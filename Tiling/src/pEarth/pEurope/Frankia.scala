/* Copyright 2018-21 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package pEarth; package pEurope
import geom._, pglobe._, WTile._

object Frankia extends EarthLevel2("Frankia", 47.28 ll 1.93, plain)
{
  val southWest = divN45 ll -1.29
  val southEast = divN45 ll 5.53

  val sLAmelie = 45.47 ll -1.15
  val royan = 45.61 ll -1.04
  val laCoubre = 45.69 ll -1.23
  val laRochelle = 46.15 ll -1.22
  val niortaise = 46.30 ll -1.13
  val sablesdOlonne = 46.49 ll -1.80
  val penmarch = 47.80 ll -4.37
  val vilaineMouth = 47.49 ll -2.44
  val brest = 48.42 ll - 4.73
  val landunvez = 48.56 ll -4.72
  val pleubian = 48.86 ll -3.10
  val yffiniac = 48.49 ll -2.68
  val capFrehel = 48.68 ll -2.31
  val pointeDuGrouin = 48.71 ll -1.84
  val vildeLaMarine = 48.61 ll -1.84
  val avranches = 48.66 ll -1.45
  val cabaneVauban = 48.74 ll -1.57
  val auderville = 49.73 ll -1.94
  val gatteville = 49.69 ll -1.26
  val carentan = 49.36 ll -1.16
  val cabourg = 49.29 ll -0.18
  val villierville = 49.40 ll 0.13
  val seineMouth = 49.43 ll 0.23
  val wLeHavre = 49.51 ll 0.06
  val capAntifer = 49.69 ll 0.16
  val cayeux = 50.18 ll 1.49
  val capGrisNez = 50.87 ll 1.58
  val calais = 50.93 ll 1.74
  val belgianCoast = 51.09 ll 2.54

  //val f1 = 49.42 ll 6.54
  val basel = 47.56 ll 7.58
  val bourgeEnBresse = 46.20 ll 5.22


  val polygonLL = PolygonLL(southEast, southWest, sLAmelie, royan, laCoubre, laRochelle, niortaise, sablesdOlonne, vilaineMouth, penmarch, brest,
    landunvez, pleubian, yffiniac, capFrehel, pointeDuGrouin, vildeLaMarine, avranches,
    cabaneVauban, auderville, gatteville, carentan, cabourg,villierville, seineMouth,
    wLeHavre, capAntifer, cayeux, capGrisNez, calais, belgianCoast,
    basel, bourgeEnBresse)
}