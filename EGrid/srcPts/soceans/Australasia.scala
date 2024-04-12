/* Copyright 2018-24 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package pEarth; package soceans
import geom._, pglobe._, egrid._, WTiles._

/** [[polygonLL]] graphical representation of Australia. Depends on nothing. */
object WesternAustralia extends EArea2("Western\nAustralia", -24.839 ll 124, deshot)
{ val northEast: LatLong = -14.879 ll 129
  val southEast: LatLong = -31.687 ll 129
  val nuytsland1: LatLong = -32.96 ll 124.33
  val nuytsland2: LatLong = -33.86 ll 123.63
  val westCapeHowe: LatLong = -35.129 ll 117.612
  val windyHarbour: LatLong = -34.84 ll 116
  val capeLeeuwin: LatLong = -34.367 ll 115.137
  val capeNaturaliste: LatLong = -33.530 ll 115.004
  val dirkHartog: LatLong = -25.516 ll 112.938
  val west: LatLong = -22.58 ll 113.95
  val legendreIsland: LatLong = -20.354 ll 116.835
  val eightyMile: LatLong = -19.41 ll 121.24
  val couloumbPoint: LatLong = -17.30 ll 122.12
  val dempierNorth = -16.360 ll 123.033
  val degerandoIsland = -15.337 ll 124.188
  val drysdaleRiver: LatLong = -13.77 ll 126.95

  override val polygonLL: PolygonLL = PolygonLL(northEast, southEast, nuytsland1, nuytsland2, westCapeHowe, windyHarbour, capeLeeuwin, capeNaturaliste,
    dirkHartog, west, legendreIsland, eightyMile, couloumbPoint, dempierNorth, degerandoIsland, drysdaleRiver)
}

/** [[polygonLL]] graphical representation of Australia. Depends on [[WesternAustralia]]. */
object AustraliaNorthTerr extends EArea2("Australia\nNorthern Territory", -23 ll 134.6, sahel)
{ val victoriaMouth: LatLong = -15.13 ll 129.65
  val thamarrurr: LatLong = -14.42 ll 129.36
  val coxPeninsular: LatLong = -12.41 ll 130.64

  val nAustralia: LatLong = -12.01 ll 133.56
  val eastArnhem: LatLong = -12.31 ll 136.92
  val limmen: LatLong = -14.73 ll 135.36
  val northEast: LatLong = -16.544 ll 138

  val southEast: LatLong = -26 ll 138
  val southWest: LatLong = -26 ll 129

  override val polygonLL: PolygonLL = PolygonLL(WesternAustralia.northEast, victoriaMouth, thamarrurr, coxPeninsular, nAustralia, eastArnhem, limmen,
    northEast, southEast, southWest
    )
}

/** [[polygonLL]] graphical representation of Australia. Depends on [[WesternAustralia]]. */
object Queensland extends EArea2("Queensland", -21.28 ll 144.5, sahel)
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

  override val polygonLL: PolygonLL = PolygonLL(nQueensland, p5, nKennedy, capeMelville, p9, p14, coolbie, p18, p25, harveyBay, doubleIslandPoint, brisbane, byronBay,
    AustraliaSouthEast.northEast, SouthAustralia.cameronPoint, SouthAustralia.northEast, AustraliaNorthTerr.southEast, AustraliaNorthTerr.northEast, bynoeMouth,
    p95)
}

/** [[polygonLL]] graphical representation of Australia. Depends on [[WesternAustralia]]. */
object SouthAustralia extends EArea2("South Austraia", -27.1 ll 146.73, sahel)
{ val northEast: LatLong = -26 ll 141
  val cameronPoint = -29 ll 141
  val sleaford: LatLong = -34.92 ll 135.64
  val smokyBay: LatLong = -32.52 ll 133.86
  val yalata: LatLong = -31.35 ll 131.21

  override val polygonLL: PolygonLL = PolygonLL(northEast, cameronPoint, AustraliaSouthEast.portAugusta, sleaford, smokyBay, yalata, WesternAustralia.southEast,
    AustraliaNorthTerr.southWest)
}

/** [[polygonLL]] graphical representation of Australia. Depends on [[WesternAustralia]]. */
object AustraliaSouthEast extends EArea2("Australia\nsouth east", -27.1 ll 146.73, savannah)
{
  val northEast: LatLong = -29 ll 153.476
  val southEast: LatLong = -37.4 ll 149.58

  val wilsonsProm: LatLong = -39.12 ll 146.38
  val barwonHeads: LatLong = -38.27 ll 144.53
  val capeOtway: LatLong = -38.85 ll 143.51
  val portMacdonnell: LatLong = -38.06 ll 140.66
  val carpenterRocks: LatLong = -37.89 ll 140.28
  val p75: LatLong = -36.958 ll 139.672
  val hardwicke: LatLong = -34.91 ll 137.46
  val portAugusta: LatLong = -32.53 ll 137.77


  override val polygonLL: PolygonLL = PolygonLL(northEast, southEast, wilsonsProm, barwonHeads, capeOtway, portMacdonnell, carpenterRocks, p75, hardwicke,
    portAugusta, SouthAustralia.cameronPoint)
}

/** [[polygonLL]] graphical representation of Tasmania. Depends on nothing. */
object Tasmania extends EArea2("Tasmania", -24.45 ll 134.47, mtainOceForest)
{ val capePortland: LatLong = -40.738 ll 147.976
  val tasman: LatLong = -43.242 ll 148.005
  val south: LatLong = -43.640 ll 146.828
  val southWest: LatLong = -43.570 ll 146.032
  val hunterNW: LatLong = -40.483 ll 144.712
  val merseyBluff: LatLong = -41.158 ll 146.355

  override val polygonLL: PolygonLL = PolygonLL(capePortland, tasman, south, southWest, hunterNW, merseyBluff)
}

/** [[polygonLL]] graphical representation of the North Ilsand of New Zealand. Depends on nothing. */
object NZNorthIsland extends EArea2("NewZealandNIsland", -38.66 ll 176, hillyOce)
{ val capeReinga: LatLong = -34.42 ll 172.68
  val teHapua: LatLong = -34.41 ll 173.05
  val aukland: LatLong = -36.83 ll 174.81
  val eCape: LatLong = -37.69 ll 178.54
  val capePalliser: LatLong = -41.61 ll 175.29
  val makara: LatLong = -41.29 ll 174.62
  val himtangi: LatLong = -40.36 ll 175.22
  val capeEgmont: LatLong = -39.28 ll 173.75

  override val polygonLL: PolygonLL = PolygonLL(capeReinga, teHapua, aukland, eCape, capePalliser, makara, himtangi, capeEgmont)
}

/** [[polygonLL]] graphical representation of the South Island of New Zealand. Depends on nothing. */
object NZSouthIsland extends EArea2("NewZealandSIsland", -43.68 ll 171.00, hillyOceForest)
{ val swNewZealand: LatLong = -45.98 ll 166.47
  val puponga: LatLong = -40.51 ll 172.72
  val capeCambell: LatLong = -41.73 ll 174.27
  val slopePoint: LatLong = -46.67 ll 169.00

  override val polygonLL: PolygonLL = PolygonLL(swNewZealand, puponga, capeCambell, slopePoint)
}