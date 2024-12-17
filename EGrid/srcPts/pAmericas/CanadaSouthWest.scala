/* Copyright 2018-24 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package pEarth; package pAmericas
import geom._, pglobe._, egrid._, WTiles._

/** [[polygonLL]] graphical representation for the Canadian Rockies. Depends on [[Yukon]]. */
object CanadaRockies extends EarthPoly("Canada Rockies", 54.479 ll -124.778, mtainOceForest)
{ val p10: LatLong = 58.113 ll -122.009
  val southEast: LatLong = 49 ll -113.474
  val w49th: LatLong = 49 ll -122.754
  val vancouverIslandSouth: LatLong = 48.310 ll -123.563
  val vancouverIslandWest: LatLong = 50.776 ll -128.428
  val priceIslandSouth: LatLong = 52.290 ll -128.677
  val kunghitIslandSouth: LatLong = 51.944 ll -131.027
  val p50: LatLong = 54.71 ll -132.81

  override def polygonLL: PolygonLL = PolygonLL(Yukon.southEast, p10, southEast, w49th, vancouverIslandSouth, vancouverIslandWest, priceIslandSouth,
    kunghitIslandSouth, p50, Yukon.yakut50)
}

/** [[polygonLL]] graphical representation for Alberta and Saskatchewan. Depends on [[Yukon]]. */
object AlbertaSask extends EarthPoly("Alberta\nSaskatchewan", 55.5 ll -111.5, taiga)
{ val northEast: LatLong = 60 ll -102
  val montanaNE: LatLong = 49 ll -104
  val eggIsland : LatLong= 59.91 ll -94.85

  override def polygonLL: PolygonLL = ReindeerLake.westCoast +<+ LinePathLL(montanaNE, CanadaRockies.southEast, CanadaRockies.p10) |++<| LakeAthabasaca.southCoast
}

/** [[polygonLL]] graphical representation for Reindeer Lake. Depends on nothing. */
object ReindeerLake extends EarthPoly("Reindeer\nLake", 66.00 ll -120.25, Lake)
{ val north: LatLong = 58.136 ll -101.994
  val beaver: LatLong = 57.437 ll -102.192
  val southEast: LatLong = 56.359 ll -102.954
  val southWest: LatLong = 56.363 ll -103.056
  val eastCoast: LinePathLL = LinePathLL(north, beaver, southEast, southWest)

  val p55: LatLong = 56.445 ll -103.069
  val p75: LatLong = 57.478 ll -102.618
  val westCoast = LinePathLL(southWest, p55, p75, north)

  override def polygonLL: PolygonLL = eastCoast |-++-| westCoast
}

/** [[polygonLL]] graphical representation for Manitiba. Depends on [[NunavutSouth]] and [[AlbertaSask]]. */
object Manitoba extends EarthPoly("Manitoba", 56.284 ll -95.559, lakesTaiga)
{ val churchillMouth = 58.79 ll -94.20
  val manitoba10 = 58.75 ll -93.24
  val nelsonMouth: LatLong = 57.09 ll -92.47

  override def polygonLL: PolygonLL = LinePathLL(AlbertaSask.northEast, NunavutSouth.hudsonBay60W, churchillMouth, manitoba10, nelsonMouth) ++<
    LakeWinnipeg.westCoast +% AlbertaSask.montanaNE |++<| ReindeerLake.eastCoast
}

/** [[polygonLL]] graphical representation for Great Bear Lake. Depends on nothing. */
object LakeWinnipeg extends EarthPoly("Lake Winnipeg", 52.78 ll -97.83, Lake)
{ val playGreenMouth: LatLong = 53.70 ll -97.86
  val bloodveinMouth: LatLong = 51.79 ll -96.72
  val winnipegMouth: LatLong = 50.63 ll -96.32
  val redMouth: LatLong = 50.30 ll -96.86

  val eastCoast: LinePathLL = LinePathLL(playGreenMouth, bloodveinMouth, winnipegMouth, redMouth)

  val p75: LatLong = 52.16 ll -97.71
  val grandRapids: LatLong = 53.187 ll -99.256
  val northWest: LatLong = 53.87 ll -98.94
  val north: LatLong = 53.86 ll -98.46

  val westCoast: LinePathLL = LinePathLL(redMouth, p75, grandRapids, northWest, north, playGreenMouth)

  override def polygonLL: PolygonLL =  eastCoast |-++-| westCoast
}