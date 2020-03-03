/* Copyright 2018 Richard Oliver. Licensed under Apache Licence version 2.0 */
package ostrat
package pEarth

import pGrid._

import reflect.ClassTag

/** An all world map, parametised by Tile and Tile side types. */
class EarthAllMap[TileT <: TileOld, SideT <: TileSideOld](fTile: (Int, Int, WTile) => TileT, fSide: (Int, Int, SideTerr) => SideT)(
      implicit evTile: ClassTag[TileT], evSide: ClassTag[SideT]) extends OldWorldMap[TileT, SideT](fTile, fSide)(evTile, evSide)
{
  override val tops: ArrOld[Area1] = EarthAreas.allTops
  def topsMap[A](f :Area1 => A): ArrOld[A] = tops.map(f)
}

class OldWorldMap[TileT <: TileOld, SideT <: TileSideOld](val fTile: (Int, Int, WTile) => TileT, fSide: (Int, Int, SideTerr) => SideT)(
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
  val tops: ArrOld[Area1] = EarthAreas.oldWorld
  val grids: ArrOld[EGrid[TileT, SideT]] = EarthAreas.grids.map(_.apply[TileT, SideT](fTile, fSide, evTile, evSide))
  grids(0).rightGrid = Some(grids(1))
  //val euWest: AreaT = a1Fac(EuropeWest)
}

trait EarthAreas[TileT <: ETileOld, SideT <: TileSideOld]
{   
  def tops: ArrOld[Area1]
}

object EarthAreas
{
  import pPts._, pEurope._
  val oldWorld: ArrOld[Area1] = ArrOld(EuropeNW, EuropeSW, EuropeEast, AsiaWest, PolarNorth, AfricaWest, AfricaEast, AsiaEast, AtlanticNorth)
  val newWorld: ArrOld[Area1] = ArrOld(PolarSouth, AmericasNorth, AmericasSouth, Australasia, PacificTop, AfricaSouthern)
  val grids: ArrOld[EGridMaker] = ArrOld(EuropeNWGrid, EuropeEastGrid)
  //val otherTops = oldWorld ::: newWorld
  def allTops =  oldWorld ++ newWorld// otherTops
}