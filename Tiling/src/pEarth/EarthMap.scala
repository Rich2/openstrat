/* Copyright 2018-22 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package pEarth
import pGrid._, reflect.ClassTag

/** An all world map, parametrised by Tile and Tile side types. */
class EarthAllMap[TileT <: TileAncient, SideT <: TileSideAncient](fTile: (Int, Int, WTile) => TileT, fSide: (Int, Int, SideTerr) => SideT)
  (implicit evTile: ClassTag[TileT], evSide: ClassTag[SideT]) extends OldWorldMap[TileT, SideT](fTile, fSide)(evTile, evSide)
{ override val tops: RArr[EArea1] = EarthAreas.allTops
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
  val tops: RArr[EArea1] = EarthAreas.oldWorld
  val grids: RArr[EGridAncient[TileT, SideT]] = EarthAreas.grids.map(_.apply[TileT, SideT](fTile, fSide, evTile, evSide))
  grids(0).rightGrid = Some(grids(1))
}

/** Object for irregular areas and hexagonal tile grids to represent the Earths surface. */
object EarthAreas
{ import pPts._, pEurope._
  val oldWorld: RArr[EArea1] = RArr(EuropeNW, EuropeSW, EuropeEast, AsiaWest, PolarNorth, AfricaWest, AfricaEast, AsiaEast, NorthAtantic)
  val newWorld: RArr[EArea1] = RArr(PolarSouth, AmericasNorth, AmericasSouth, Australasia, PacificTop, AfricanTrangle)
  val grids: RArr[EGridMaker] = RArr(EuropeNWGridAncient, EuropeNEGridAncient)

  def allTops: RArr[EArea1] =  oldWorld ++ newWorld
}