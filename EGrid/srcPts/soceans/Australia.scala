/* Copyright 2018-24 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package pEarth; package soceans
import geom._, pglobe._, egrid._, WTiles._

/** [[polygonLL]] graphical representation of Australia. Depends on [[AustraliaNorthTerr]]. */
object WesternAustralia extends EarthArea("Western\nAustralia", -24.839 ll 124, deshot)
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
  val dempierNorth = -16.360 ll 123.033
  val degerandoIsland = -15.337 ll 124.188
  val drysdaleRiver: LatLong = -13.77 ll 126.95

  override val polygonLL: PolygonLL = PolygonLL(AustraliaNorthTerr.northWest, AustraliaNorthTerr.southWest, southEast, nuytsland1, nuytsland2, westCapeHowe,
    windyHarbour, capeLeeuwin, capeNaturaliste, busselton, p60, p64, p68, dirkHartog1, dirkHartogN, capePeron, west, legendreIsland, eightyMile, couloumbPoint,
    dempierNorth, degerandoIsland, drysdaleRiver)
}

/** [[polygonLL]] graphical representation of Australia. Depends on nothing. */
object AustraliaNorthTerr extends EarthArea("Australia\nNorthern Territory", -23 ll 134.6, sahel)
{ val north: LatLong = -12.01 ll 133.56
  val eastArnhem: LatLong = -12.31 ll 136.92
  val limmen: LatLong = -14.73 ll 135.36
  val northEast: LatLong = -16.544 ll 138

  val southEast: LatLong = -26 ll 138
  val southWest: LatLong = -26 ll 129
  val northWest: LatLong = -14.879 ll 129
  val victoriaMouth: LatLong = -15.13 ll 129.65
  val thamarrurr: LatLong = -14.42 ll 129.36
  val coxPeninsular: LatLong = -12.41 ll 130.64

  override val polygonLL: PolygonLL = PolygonLL(north, eastArnhem, limmen, northEast, southEast, southWest, northWest, victoriaMouth, thamarrurr, coxPeninsular)
}

/** [[polygonLL]] graphical representation of Australia. Depends on [[WesternAustralia]]. */
object Queensland extends EarthArea("Queensland", -21.28 ll 144.5, sahel)
{ val nQueensland: LatLong = -11 ll 142.43
  val p5: LatLong = -12.840 ll 143.531
  val nKennedy: LatLong = -14.49 ll 143.95
  val capeMelville: LatLong = -14.17 ll 144.51
  val p9: LatLong = -14.945 ll 145.347
  val p14: LatLong = -17.643 ll 146.142
  val coolbie: LatLong = -18.86 ll 146.27
  val p18: LatLong = -19.406 ll 147.473
  val p25: LatLong = -22.683 ll 150.831
  val harveyBay: LatLong = -25.29 ll 152.89
  val doubleIslandPoint: LatLong = -25.932 ll 153.191
  val brisbane: LatLong = -27.05 ll 153.03
  val byronBay: LatLong = -28.64 ll 153.62

  val bynoeMouth: LatLong = -17.153 ll 140.732
  val p95: LatLong = -13.895 ll 141.483

  override val polygonLL: PolygonLL = PolygonLL(nQueensland, p5, nKennedy, capeMelville, p9, p14, coolbie, p18, p25, harveyBay, doubleIslandPoint, brisbane,
    byronBay, AustraliaSouthEast.northEast, SouthAustralia.cameronPoint, SouthAustralia.northEast, AustraliaNorthTerr.southEast, AustraliaNorthTerr.northEast,
    bynoeMouth, p95)
}

/** [[polygonLL]] graphical representation of Australia. Depends on [[AustraliaSouthEast]], [[WesternAustralia]], [[AustraliaNorthTerr]]. */
object SouthAustralia extends EarthArea("South Austraia", -27.1 ll 146.73, sahel)
{ val northEast: LatLong = -26 ll 141
  val cameronPoint = -29 ll 141
  val sleaford: LatLong = -34.92 ll 135.64
  val smokyBay: LatLong = -32.52 ll 133.86
  val yalata: LatLong = -31.35 ll 131.21

  override val polygonLL: PolygonLL = PolygonLL(northEast, cameronPoint, AustraliaSouthEast.portAugusta, sleaford, smokyBay, yalata, WesternAustralia.southEast,
    AustraliaNorthTerr.southWest, AustraliaNorthTerr.southEast)
}

/** [[polygonLL]] graphical representation of Australia. Depends on [[WesternAustralia]]. */
object AustraliaSouthEast extends EarthArea("Australia\nsouth east", -27.1 ll 146.73, savannah)
{ val northEast: LatLong = -29 ll 153.476
  val sealRocks: LatLong = -32.440 ll 152.538
  val greenCape: LatLong = -37.257 ll 150.047
  val victoriaEast: LatLong = -37.505 ll 149.976
  val p40: LatLong = -37.542 ll 149.906
  val p45: LatLong = -37.934 ll 147.822
  val wilsonsProm: LatLong = -39.12 ll 146.38
  val barwonHeads: LatLong = -38.27 ll 144.53
  val capeOtway: LatLong = -38.85 ll 143.51
  val portMacdonnell: LatLong = -38.06 ll 140.66
  val carpenterRocks: LatLong = -37.89 ll 140.28
  val p75: LatLong = -36.958 ll 139.672
  val hardwicke: LatLong = -34.91 ll 137.46
  val portAugusta: LatLong = -32.53 ll 137.77


  override val polygonLL: PolygonLL = PolygonLL(northEast, sealRocks, greenCape, victoriaEast, p40, p45, wilsonsProm, barwonHeads, capeOtway, portMacdonnell,
    carpenterRocks, p75, hardwicke, portAugusta, SouthAustralia.cameronPoint)
}