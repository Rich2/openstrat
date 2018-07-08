/* Copyright 2018 Richard Oliver. Licensed under Apache Licence version 2.0 */
package ostrat
package pEarth
import pGrid._

class EarthAllMap[TileT <: GridElem, SideT <: GridElem](fTile: (Int, Int, Terrain) => TileT)(implicit evTile: IsType[TileT]) extends
OldWorldMap[TileT, SideT](fTile)(evTile)
{
   override val tops: List[Area1] = EarthAreas.allTops
   def topsMap[A](f :Area1 => A): Seq[A] = tops.map(f)
}

class OldWorldMap[TileT <: GridElem, SideT <: GridElem](val fTile: (Int, Int, Terrain) => TileT)(implicit evTile: IsType[TileT])
{
   def tile(x: Int, y: Int): TileT = grids(0).getTile(x, y)
   def tile(cood: Cood): TileT = tile(cood.x, cood.y)
   def setTile(x: Int, y: Int, newTile: TileT): Unit = grids(0).setTile(x, y, newTile)
   def fTiles[D](f: (TileT, D) => Unit, data: (Int, Int, D)*) = data.foreach(tr => f(tile(tr._1, tr._2), tr._3))
   def modTiles[A](f: (TileT, Cood, A) => TileT, data: (Int, Int, A)*): Unit = data.foreach{tr =>
      val oldTile = tile(tr._1, tr._2)
      val newTile = f(oldTile, Cood(tr._1, tr._2), tr._3)
      setTile(tr._1, tr._2, newTile)      
   }
   val tops: List[Area1] = EarthAreas.oldWorld
   val grids/*: List[EGrid[TileT, SideT]]*/ = EarthAreas.grids.map(_.apply[TileT, SideT](fTile)(evTile))
   //val euWest: AreaT = a1Fac(EuropeWest)
}

trait EarthAreas[TileT <: ETile, SideT <: GridElem]
{   
   def tops: Seq[Area1]   
}

object EarthAreas// extends AreaTop
{
   import pAmericas._
   val oldWorld: List[Area1] = List(EuropeWest, EuropeEast, AsiaWest, PolarNorth, AfricaWest, AfricaEast, AsiaEast, AtlanticNorth)
   val newWorld: List[Area1] = List(PolarSouth, AmericasNorth, AmericasSouth, Australasia, PacificTop, AfricaSouthern)
   val grids: List[EGridMaker] = List(EuropeWestGrid, EuropeEastGrid)
   //val otherTops = oldWorld ::: newWorld
   def allTops =  oldWorld ::: newWorld// otherTops
}