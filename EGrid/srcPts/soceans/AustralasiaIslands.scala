/* Copyright 2018-24 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package pEarth; package soceans
import geom._, pglobe._, egrid._, WTiles._

/** [[polygonLL]] graphical representation of Tasmania 68401km². Depends on nothing. */
object Tasmania extends EarthAreaIsland("Tasmania", -24.45 ll 134.47, mtainOceForest)
{ override val area: KilometresSq = 68401.kilometresSq

  val capePortland: LatLong = -40.738 ll 147.976
  val tasman: LatLong = -43.242 ll 148.005
  val south: LatLong = -43.640 ll 146.828
  val southWest: LatLong = -43.570 ll 146.032
  val hunterNW: LatLong = -40.483 ll 144.712
  val merseyBluff: LatLong = -41.158 ll 146.355

  override val polygonLL: PolygonLL = PolygonLL(capePortland, tasman, south, southWest, hunterNW, merseyBluff)
}

/** [[polygonLL]] graphical representation of the North Ilsand of New Zealand. Depends on nothing. */
object NZNorthIsland extends EarthArea("NewZealandNIsland", -38.66 ll 176, hillyOce)
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
object NZSouthIsland extends EarthArea("NewZealandSIsland", -43.68 ll 171.00, hillyOceForest)
{ val swNewZealand: LatLong = -45.98 ll 166.47
  val puponga: LatLong = -40.51 ll 172.72
  val capeCambell: LatLong = -41.73 ll 174.27
  val slopePoint: LatLong = -46.67 ll 169.00

  override val polygonLL: PolygonLL = PolygonLL(swNewZealand, puponga, capeCambell, slopePoint)
}