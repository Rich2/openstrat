/* Copyright 2018 Richard Oliver. Licensed under Apache Licence version 2.0 */
package ostrat
package pEarth
import geom._
import pGrid._

object EuropeEast extends Area1('EuropeEast, 60 ll 60)
{
   override def fill = false
   //override val gridMaker = E80Empty//EuropeEastGrid
   import EuropePts._
   override val a2Seq: List[Area2] = List(Balkans, Finlandia, Crimea, asiaMinor, caucasus)
}

object EuropeEastGrid extends EGridMaker
{          
   def apply[TileT <: Tile](fTile: (Int, Int, Terrain) => TileT)(implicit evTile: IsType[TileT]): EGrid80km[TileT] =
   {     
      val grid = new EGFarNorth[TileT]("EuropeEast", 30.east, xOffset = 400, xTileMin = 314, xTileMax = 486)//{}
      grid.fTilesSetAll(Ocean)(fTile)
//      val gs: (Int, Int, Multiple[Terrain]*) => Unit = grid.setRow[Terrain](fTile) _ 
      //gs(430, 518, hills * 3)
      grid
   }
}