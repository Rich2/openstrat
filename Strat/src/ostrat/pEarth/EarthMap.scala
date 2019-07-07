/* Copyright 2018 Richard Oliver. Licensed under Apache Licence version 2.0 */
package ostrat
package pEarth

import pGrid._

import reflect.ClassTag

/** An all world map, parametised by Tile and Tile side types. */
class EarthAllMap[TileT <: Tile, SideT <: TileSide](fTile: (Int, Int, Terrain) => TileT, fSide: (Int, Int, SideTerr) => SideT)(
      implicit evTile: ClassTag[TileT], evSide: ClassTag[SideT]) extends OldWorldMap[TileT, SideT](fTile, fSide)(evTile, evSide)
{
  override val tops: Arr[Area1] = EarthAreas.allTops
  def topsMap[A](f :Area1 => A): Arr[A] = tops.map(f)
}

class OldWorldMap[TileT <: Tile, SideT <: TileSide](val fTile: (Int, Int, Terrain) => TileT, fSide: (Int, Int, SideTerr) => SideT)(
      implicit evTile: ClassTag[TileT], evSide: ClassTag[SideT])
{
  def tile(x: Int, y: Int): TileT = grids(0).getTile(x, y)
  def tile(cood: Cood): TileT = tile(cood.x, cood.y)
  def setTile(x: Int, y: Int, newTile: TileT): Unit = grids(0).setTile(x, y, newTile)
  def fTiles[D](f: (TileT, D) => Unit, data: (Int, Int, D)*) = data.foreach(tr => f(tile(tr._1, tr._2), tr._3))
  /** Needs looking at */
  def modTiles[A](f: (TileT, Cood, A) => TileT, data: (Int, Int, A)*): Unit = data.foreach{tr =>
    val oldTile = tile(tr._1, tr._2)
    val newTile = f(oldTile, Cood(tr._1, tr._2), tr._3)
    setTile(tr._1, tr._2, newTile)
  }
  val tops: Arr[Area1] = EarthAreas.oldWorld
  val grids: Arr[EGrid[TileT, SideT]] = EarthAreas.grids.map(_.apply[TileT, SideT](fTile, fSide, evTile, evSide))
  grids(0).rightGrid = Some(grids(1))
  //val euWest: AreaT = a1Fac(EuropeWest)
}

trait EarthAreas[TileT <: ETile, SideT <: TileSide]
{   
  def tops: Arr[Area1]
}

object EarthAreas
{
  import pPts._, pEurope._
  val oldWorld: Arr[Area1] = Arr(EuropeNW, EuropeSW, EuropeEast, AsiaWest, PolarNorth, AfricaWest, AfricaEast, AsiaEast, AtlanticNorth)
  val newWorld: Arr[Area1] = Arr(PolarSouth, AmericasNorth, AmericasSouth, Australasia, PacificTop, AfricaSouthern)
  val grids: Arr[EGridMaker] = Arr(EuropeNWGrid, EuropeEastGrid)
  //val otherTops = oldWorld ::: newWorld
  def allTops =  oldWorld ++ newWorld// otherTops
}