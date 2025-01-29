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

/** Solomon Islands. */
object SolomonIslands extends IslandPolyGroup("Solomon Islands")
{
  override def elements: RArr[IslandPolyLike] = RArr(Bougainville, SolomonMiddle)

  override def area: Kilares = 28896.kilares
}

/** [[PolygonLL]] graphical representation of Bougainville, Sotland and Fauro Islands in the Solomon Islands 9518km². Depends on nothing. */
object Bougainville extends IslandPoly("Bougainvile Island", -6.170 ll 155.289, hillyJungle)
{ override def oGroup: Some[SolomonIslands.type] = Some(SolomonIslands)

  val bourgainville: Kilares = 9318.kilares
  override val area: Kilares = bourgainville + 200.kilares

  val north: LatLong = -5.003 ll 154.620
  val fauroEast: LatLong = -6.925 ll 156.136
  val shortlandSE: LatLong = -7.119 ll 155.845
  val p65: LatLong = -6.519 ll 155.159

  override val polygonLL: PolygonLL = PolygonLL(north, fauroEast, shortlandSE, p65)
}

/** [[PolygonLL]] graphical representation of the Solomon Islands 6379km². Depends on nothing. */
object SolomonMiddle extends IslandPoly("Solomon Middle", -8.220 ll 157.576, mtainJungle)
{ override def oGroup: Some[SolomonIslands.type] = Some(SolomonIslands)

  val choiseul: Kilares = 2971.kilares
  val santaIsabel: Kilares = 2999.kilares
  override val area: Kilares = SolomonIslands.area - Bougainville.area - SolomonSE.area

  val choiseulNorth: LatLong = -6.587 ll 156.467
  val santaIsabelNE: LatLong = -8.342 ll 159.853
  val rendovaSouth: LatLong = -8.681 ll 157.308
  val vellaLavellaWest: LatLong = -7.638 ll 156.504

  override val polygonLL: PolygonLL = PolygonLL(choiseulNorth, santaIsabelNE, rendovaSouth, vellaLavellaWest)
}

/** [[PolygonLL]] graphical representation of the Solomon Islands 12999km². Depends on nothing. */
object SolomonSE extends IslandPoly("Solomon South East", -8.220 ll 157.576, mtainJungle)
{ override def oGroup: Some[SolomonIslands.type] = Some(SolomonIslands)

  val malaita: Kilares = 4307.kilares
  val sanCristobal: Kilares = 3190.kilares
  val guadalcanal: Kilares = 5302.kilares

  override val area: Kilares = malaita + sanCristobal + guadalcanal + 200.kilares

  val malaitaNorth: LatLong = -8.322 ll 160.743
  val sanCritobolEast: LatLong = -10.840 ll 162.376
  val guadalcanalWest: LatLong = -9.522 ll 159.587

  override val polygonLL: PolygonLL = PolygonLL(malaitaNorth, sanCritobolEast, guadalcanalWest)
}