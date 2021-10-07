/* Copyright 2018-21 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package pEarth
import pGrid._, reflect.ClassTag

/** An all world map, parametrised by Tile and Tile side types. */
class EarthAllMap[TileT <: TileAncient, SideT <: TileSideAncient](fTile: (Int, Int, WTile) => TileT, fSide: (Int, Int, SideTerr) => SideT)
  (implicit evTile: ClassTag[TileT], evSide: ClassTag[SideT]) extends OldWorldMap[TileT, SideT](fTile, fSide)(evTile, evSide)
{ override val tops: Arr[EarthLevel1] = EarthAreas.allTops
}

class OldWorldMap[TileT <: TileAncient, SideT <: TileSideAncient](val fTile: (Int, Int, WTile) => TileT, fSide: (Int, Int, SideTerr) => SideT)
  (implicit evTile: ClassTag[TileT], evSide: ClassTag[SideT])
{
  def tile(x: Int, y: Int): TileT = grids(0).getTile(x, y)
  def tile(cood: Cood): TileT = tile(cood.xi, cood.yi)
  def setTile(x: Int, y: Int, newTile: TileT): Unit = grids(0).setTile(x, y, newTile)
  def fTiles[D](f: (TileT, D) => Unit, data: (Int, Int, D)*) = data.foreach(tr => f(tile(tr._1, tr._2), tr._3))
  /** Needs looking at */
  def modTiles[A](f: (TileT, Cood, A) => TileT, data: (Int, Int, A)*): Unit = data.foreach{tr =>
    val oldTile = tile(tr._1, tr._2)
    val newTile = f(oldTile, Cood(tr._1, tr._2), tr._3)
    setTile(tr._1, tr._2, newTile)
  }
  val tops: Arr[EarthLevel1] = EarthAreas.oldWorld
  val grids: Arr[EGridAncient[TileT, SideT]] = EarthAreas.grids.map(_.apply[TileT, SideT](fTile, fSide, evTile, evSide))
  grids(0).rightGrid = Some(grids(1))
}

/** Object for irregular areas and hexagonal tile grids to represent the Earths surface. */
object EarthAreas
{ import pPts._, pEurope._
  val oldWorld: Arr[EarthLevel1] = Arr(EuropeNW, EuropeSW, EuropeEast, AsiaWest, PolarNorth, AfricaWest, AfricaEast, AsiaEast, NorthAtantic)
  val newWorld: Arr[EarthLevel1] = Arr(PolarSouth, AmericasNorth, AmericasSouth, Australasia, PacificTop, AfricaSouthern)
  val grids: Arr[EGridMaker] = Arr(EuropeNWGridAncient, EuropeNEGridAncient)

  def allTops: Arr[EarthLevel1] =  oldWorld ++ newWorld
}