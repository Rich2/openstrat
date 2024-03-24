/* Copyright 2018-24 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package pEarth; package pOceans
import geom._, pglobe._, egrid._, WTiles._

/** [[polygonLL]] graphical representation of Greenland. Depends on nothing. */
object Greenland extends EArea2("Greenland", 75 ll -42, ice)
{ val neGreenland: LatLong = 81.44 ll -11.77
  val p10: LatLong = 75.036 ll -17.426
  val semersooq: LatLong = 70.03 ll -23.07
  val p35: LatLong = 68.819 ll -25.906
  val p37: LatLong = 67.858 ll -32.180
  val p39: LatLong = 67.056 ll -33.424
  val kulusuk: LatLong = 65.53 ll -37.05

  val ortit: LatLong = 65.260 ll -39.516
  val p42: LatLong = 63.691 ll -40.521
  val p44: LatLong = 61.865 ll -42.095
  val sGreenland: LatLong = 59.87 ll -43.95

  val swGreenland: LatLong = 60.82 ll -48.07
  val kanqeq: LatLong = 64.119 ll -52.090
  val p72: LatLong = 66.339 ll -53.702
  val aasiaat: LatLong = 68.68 ll -53.00
  val diskoIsland: LatLong = 69.713 ll -54.961
  val p73: LatLong = 71.695 ll -55.864
  val p74: LatLong = 73.635 ll -57.026
  val p75: LatLong = 75.73 ll -58.98
  val p80: LatLong = 76.082 ll -68.409
  val northumberland: LatLong = 77.414 ll -72.508
  val wGreenland: LatLong = 78.166 ll -72.977
  val p85: LatLong =  78.515 ll -72.572
  val nwGreenland: LatLong = 80.33 ll -67.27
  val naresNorth = 82.079 ll -59.599
  val p90 = 82.366 ll -55.227
  val nGreenland: LatLong = 83.60 ll -34.19

  val northCoast = LinePathLL(naresNorth, p90, nGreenland, neGreenland)

  override val polygonLL: PolygonLL = LinePathLL(neGreenland, p10, semersooq, p35, p37, p39, kulusuk, ortit, p42, p44, sGreenland, swGreenland, kanqeq, p72,
    aasiaat, diskoIsland, p73, p74, p75, p80, northumberland, wGreenland, p85, nwGreenland) |++-| northCoast
}