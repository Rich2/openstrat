/* Copyright 2018-23 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package pEarth; package pPts
import geom._, pglobe._, WTile._

object BanksIsland extends EArea2("Banks Island", 73.12 ll -121.13, tundra)
{
  val p0 = 74.28 ll -118
  val p10 = 73.54 ll -115.34
  val p18 = 72.66 ll -119.15
  val p30 = 71.58 ll -120.50
  val south = 71.11 ll -123.06
  val southWest = 71.96 ll -125.81
  val northWest = 74.35 ll -124.70
  val north = 74.56 ll -121.49
  override def polygonLL: PolygonLL = PolygonLL(p0, p10, p18, p30, south, southWest, northWest, north)
}

object VictoriaIsland extends EArea2("Victoria Island", 70.65 ll -109.36, tundra)
{
  val stefanssonN = 73.75 ll -105.29
  val vic5 = 71.12 ll -104.60
  val vic10 = 70.21 ll -101.34
  val southEast = 69.00 ll -101.79
  val southWest = 68.46 ll -113.21
  val vic30 = 69.22 ll -113.69
  val pointCaen = 69.30 ll -115.95
  val vic40 = 71.60 ll -118.90
  val northWest = 73.36 ll -114.57
  override def polygonLL: PolygonLL = PolygonLL(stefanssonN, vic5, vic10, southEast, southWest, vic30, pointCaen, vic40, northWest)
}

object SouthamptonIsland extends EArea2("Southampton Island", 64.5 ll -84.35, tundra)
{
  val north = 66.02 ll -85.08
  val p05 = 65.27 ll -84.26
  val east = 63.78 ll -80.16
  val p30 = 63.45 ll -81.00
  val p32 = 63.44 ll -80.97
  val south = 63.11 ll -85.46
  val southEast = 63.56 ll -87.13
  val p52 = 64.09 ll -86.18
  val p80 = 65.73 ll -85.96
  override def polygonLL: PolygonLL = PolygonLL(north, p05, east, p30, p32, south, southEast, p52, p80)
}