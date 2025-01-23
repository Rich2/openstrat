/* Copyright 2018-25 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package pEarth; package soceans
import geom._, pglobe._, egrid._, WTiles._

/** [[polygonLL]] graphical representation of Australia. Depends on [[AustraliaNorthTerr]]. */
object WesternAustralia extends EarthPoly("Western\nAustralia", -24.839 ll 124, deshot)
{ val southEast: LatLong = -31.687 ll 129
  val nuytsland1: LatLong = -32.96 ll 124.33
  val nuytsland2: LatLong = -33.86 ll 123.63
  val westCapeHowe: LatLong = -35.129 ll 117.612
  val windyHarbour: LatLong = -34.84 ll 116
  val capeLeeuwin: LatLong = -34.367 ll 115.137
  val capeNaturaliste: LatLong = -33.530 ll 115.004
  val busselton: LatLong =  -33.630 ll 115.388
  val p60: LatLong = -32.681 ll 115.607
  val p64: LatLong = -30.509 ll 115.058
  val p68: LatLong = -28.111 ll 114.175
  val dirkHartog1: LatLong = -25.516 ll 112.938
  val dirkHartogN: LatLong = -25.481 ll 112.907
  val capePeron: LatLong = -25.503 ll 113.510
  val west: LatLong = -22.58 ll 113.95
  val legendreIsland: LatLong = -20.354 ll 116.835
  val eightyMile: LatLong = -19.41 ll 121.24
  val couloumbPoint: LatLong = -17.30 ll 122.12
  val dempierNorth: LatLong = -16.360 ll 123.033
  val marvNorth: LatLong = -17.236 ll 123.544
  val longIsland: LatLong = -16.534 ll 123.349
  val degerandoIsland: LatLong = -15.337 ll 124.188
  val drysdaleRiver: LatLong = -13.77 ll 126.95

  override val polygonLL: PolygonLL = PolygonLL(AustraliaNorthTerr.northWest, AustraliaNorthTerr.southWest, southEast, nuytsland1, nuytsland2, westCapeHowe,
    windyHarbour, capeLeeuwin, capeNaturaliste, busselton, p60, p64, p68, dirkHartog1, dirkHartogN, capePeron, west, legendreIsland, eightyMile, couloumbPoint,
    dempierNorth, marvNorth, longIsland, degerandoIsland, drysdaleRiver)
}

/** [[polygonLL]] graphical representation of Australia. Depends on nothing. */
object AustraliaNorthTerr extends EarthPoly("Australia\nNorthern Territory", -23 ll 134.6, sahel)
{ val north: LatLong = -12.01 ll 133.56
  val eastArnhem: LatLong = -12.31 ll 136.92
  val grooteNW: LatLong = -13.805 ll 136.923
  val eberrumilya: LatLong = -14.299 ll 136.963
  val p20: LatLong = -14.188 ll 135.901
  val limmen: LatLong = -14.73 ll 135.36
  val northEast: LatLong = -16.544 ll 138

  val southEast: LatLong = -26 ll 138
  val southWest: LatLong = -26 ll 129
  val northWest: LatLong = -14.879 ll 129
  val victoriaMouth: LatLong = -15.13 ll 129.65
  val thamarrurr: LatLong = -14.42 ll 129.36
  val coxPeninsular: LatLong = -12.41 ll 130.64

  override val polygonLL: PolygonLL = PolygonLL(north, eastArnhem, grooteNW, eberrumilya, p20, limmen, northEast, southEast, southWest, northWest,
    victoriaMouth, thamarrurr, coxPeninsular)
}

/** [[polygonLL]] graphical representation of Australia. Depends on [[NewSouthWales]], [[WesternAustralia]], [[AustraliaNorthTerr]]. */
object SouthAustraliaWest extends EarthPoly("South-Australia\n west", -27.1 ll 146.73, sahel)
{
  val p25: LatLong = -33.686 ll 137.169
  val sleaford: LatLong = -34.92 ll 135.64
  val smokyBay: LatLong = -32.52 ll 133.86
  val yalata: LatLong = -31.35 ll 131.21

  override val polygonLL: PolygonLL = LinePathLL(SouthAustraliaEast.p95) ++< LakeEyre.westCoast |++| LinePathLL(SouthAustraliaEast.portAugusta, p25, sleaford,
    smokyBay, yalata, WesternAustralia.southEast, AustraliaNorthTerr.southWest)
}