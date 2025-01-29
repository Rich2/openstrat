/* Copyright 2018-25 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package pEarth; package pMalay
import geom.*, pglobe.*, egrid.*, WTiles.*

/** Bismark archipelago. */
object BismarkArchipelago extends IslandPolyGroup("Bismark Archipelago")
{ override def elements: RArr[IslandPolyLike] = RArr(NewBritain, NewIreland)
  override def area: Kilares = 49700.kilares
}

/** [[PolygonLL]] graphical representation of New Britain 35144.6km². Depends on nothing. */
object NewBritain extends IslandPoly("New Britain", -5.251 ll 151.402, mtainJungle)
{ override def oGroup: Some[BismarkArchipelago.type] = Some(BismarkArchipelago)
  override val area: Kilares = 35144.6.kilares

  val north: LatLong = -4.133 ll 152.166
  val northEast: LatLong = -4.336 ll 152.404
  val baronga: LatLong = -6.246 ll 150.463
  val umbolWest: LatLong = -5.502 ll 147.754
  val p75: LatLong = -5.480 ll 150.908
  val takis: LatLong = -4.213 ll 151.489

  override val polygonLL: PolygonLL = PolygonLL(north, northEast, baronga, umbolWest, p75, takis)
}

/** [[PolygonLL]] graphical representation 8990km² of New Ireland 7404km² + 1186km² + 400km². Depends on nothing. */
object NewIreland extends IslandPoly("New Ireland", -5.251 ll 151.402, mtainJungle)
{ override def oGroup: Some[BismarkArchipelago.type] = Some(BismarkArchipelago)
  override val area: Kilares = 8990.kilares

  val newHanoverIsland: LatLong = -2.360 ll 150.190
  val newIreland20: LatLong = -3.977 ll 152.926
  val south: LatLong = -4.840 ll 152.882
  val p60: LatLong = -4.182 ll 152.685

  override val polygonLL: PolygonLL = PolygonLL(newHanoverIsland, newIreland20, south, p60)
}

/** [[PolygonLL]] graphical representation of the Solomon Islands 28896km². Depends on nothing. */
object SolomonIslands extends IslandPoly("Solomon Islands", -8.220 ll 157.576, mtainJungle)
{ override val area: Kilares = 28896.kilares

  val bougainvilleNorth: LatLong = -5.003 ll 154.620
  val malaitaNorth: LatLong = -8.322 ll 160.743
  val sanCritobolEast: LatLong = -10.840 ll 162.376
  val rendovaSouth: LatLong = -8.681 ll 157.308

  override val polygonLL: PolygonLL = PolygonLL(bougainvilleNorth, malaitaNorth, sanCritobolEast, rendovaSouth)
}