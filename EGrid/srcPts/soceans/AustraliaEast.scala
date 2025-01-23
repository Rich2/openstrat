/* Copyright 2018-25 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package pEarth; package soceans
import geom._, pglobe._, egrid._, WTiles._

/** [[polygonLL]] graphical representation of Australia. Depends on [[WesternAustralia]]. */
object Queensland extends EarthPoly("Queensland", -21.28 ll 144.5, sahel)
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
    byronBay, NewSouthWales.northEast, NewSouthWales.cameronPoint, SouthAustraliaEast.northEast, AustraliaNorthTerr.southEast, AustraliaNorthTerr.northEast,
    bynoeMouth, p95)
}

/** [[polygonLL]] graphical representation for Lake Eyre in South Australia. Depends on nothing. */
object LakeEyre extends LakePoly("Lake Eyre", -28.503 ll 137.292, lake)
{ override val area: Kilares = 9500.kilares

  val north: LatLong = -27.841 ll 137.042
  val east: LatLong = -28.500 ll 137.622
  val southEast: LatLong = -29.087 ll 137.992
  val south: LatLong = -29.505 ll 137.242
  val eastCoast: LinePathLL = LinePathLL(north, east, southEast, south)

  val west: LatLong = -28.779 ll 136.878
  val northWest: LatLong = -27.971 ll 136.832
  val westCoast = LinePathLL(south, west, northWest, north)

  override def polygonLL: PolygonLL = eastCoast |-++-| westCoast
}

/** [[polygonLL]] graphical representation of Australia. Depends on [[NewSouthWales]], [[WesternAustralia]], [[AustraliaNorthTerr]]. */
object SouthAustraliaEast extends EarthPoly("South-Australia\neast", -30.181 ll 139.195, sahel)
{  val northEast: LatLong = -26 ll 141

  val portMacdonnell: LatLong = -38.06 ll 140.66
  val carpenterRocks: LatLong = -37.89 ll 140.28
  val p75: LatLong = -36.958 ll 139.672
  val robeTip: LatLong = -37.156 ll 139.744

  val mundooIsland: LatLong = -35.569 ll 138.906
  val KangarooIslandSW: LatLong = -36.063 ll 136.704
  val kangarooIslandNW: LatLong = -35.784 ll 136.587
  val aldinga: LatLong = -35.270 ll 138.445
  val capeSpencer: LatLong = -35.299 ll 136.879
  val cornyPoint: LatLong = -34.895 ll 137.009
  val hardwicke: LatLong = -34.91 ll 137.46

  val portAugusta: LatLong = -32.53 ll 137.77

  val p95: LatLong = -26 ll 137.042

  override val polygonLL: PolygonLL = LinePathLL(northEast, NewSouthWales.cameronPoint, NewSouthWales.southWest, portMacdonnell, carpenterRocks, robeTip, p75,
    mundooIsland, KangarooIslandSW, kangarooIslandNW, aldinga, capeSpencer, cornyPoint, hardwicke,
    portAugusta) ++< LakeEyre.eastCoast |++| LinePathLL(p95, AustraliaNorthTerr.southEast)
}

/** [[polygonLL]] graphical representation of Australia. Depends on [[WesternAustralia]]. */
object NewSouthWales extends EarthPoly("New-South-Wales", -27.1 ll 146.73, savannah)
{ val northEast: LatLong = -29 ll 153.476
  val sealRocks: LatLong = -32.440 ll 152.538
  val greenCape: LatLong = -37.257 ll 150.047
  val victoriaEast: LatLong = -37.505 ll 149.976
  val p40: LatLong = -37.542 ll 149.906
  val p45: LatLong = -37.934 ll 147.822
  val wilsonsProm: LatLong = -39.12 ll 146.38
  val barwonHeads: LatLong = -38.27 ll 144.53
  val capeOtway: LatLong = -38.85 ll 143.51
  val southWest: LatLong = -38.055 ll 140.965
  val cameronPoint = -29 ll 141

  override val polygonLL: PolygonLL = PolygonLL(northEast, sealRocks, greenCape, victoriaEast, p40, p45, wilsonsProm, barwonHeads, capeOtway, southWest,
    cameronPoint)
}