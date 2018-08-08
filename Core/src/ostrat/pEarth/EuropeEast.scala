/* Copyright 2018 Richard Oliver. Licensed under Apache Licence version 2.0 */
package ostrat
package pEarth
import geom._
import pGrid._
import Terrain._

object EuropeEast extends Area1('EuropeEast, 60 ll 60)
{
   override def fill = false
   //override val gridMaker = E80Empty//EuropeEastGrid
   import EuropePts._
   override val a2Seq: List[Area2] = List(Balkans, Finlandia, Crimea, asiaMinor, caucasus)
}

object EuropeEastGrid extends EGridMaker
{          
   def apply[TileT <: GridElem, SideT <: GridElem](fTile: (Int, Int, Terrain) => TileT, fSide: (Int, Int, SideTerr) => SideT)(implicit evTile: IsType[TileT], evSide: IsType[SideT]):
   EGrid80km[TileT, SideT] =
   {     
      val grid = new EGFarNorth[TileT, SideT]("EuropeEast", 30.east, xOffset = 400, xTileMin = 314, xTileMax = 486)//{}
      grid.fTilesSetAll(Ocean)(fTile)
      grid.fSidesSetAll(SideNone)(fSide)
      val gs: (Int, Int, Multiple[Terrain]*) => Unit = grid.fSetRow[Terrain](fTile) _
      gs(478, 358, sea * 4, plain * 19)
      gs(476, 356, sea * 4, plain * 19)
      gs(474, 354, sea, plain * 23) 
      gs(472, 356, plain * 24)
      gs(470, 354, plain * 25)
      gs(468, 352, plain * 25)
      gs(466, 354, plain * 25)
      gs(464, 352, plain * 26)
      gs(462, 350, hills, plain * 25)
      gs(460, 352, hills, plain * 25)
      gs(458, 350, hills * 3, mtain * 3, plain * 21) 
      gs(456, 348, hills, plain, hills * 4, mtain * 2, hills, plain * 18)
      gs(454, 350, plain * 7, mtain * 2, hills * 3, plain * 15)
      gs(452, 348, mtain, plain * 6, hills, mtain * 2, hills * 3, plain * 15)
      gs(450, 346, hills, plain * 5, hills * 3, mtain *2, hills * 3, plain * 7, sea, plain * 6)
      gs(448, 348, hills, plain, hills, plain * 3, hills * 3, mtain, hills, plain * 3, sea * 2, plain * 2, sea * 3, plain * 7)
      gs(446, 346, hills * 2, plain * 4, mtain * 4, hills * 1, plain * 3, sea * 3, plain * 3, sea, plain * 8)
      grid
   }
}