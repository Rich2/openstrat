/* Copyright 2018 Richard Oliver. Licensed under Apache Licence version 2.0 */
package ostrat
package pEarth
import geom._, pGrid._, reflect.ClassTag

object AfricaWest extends Area1('WAfrica, 20 ll 40)
{
   //override val gridMaker = E80Empty//AfricaWestGrid  
      
   type A2Type = Area2
   override def fill = false   
   override val a2Seq: List[Area2] = List(Canarias, Sicily, Majorca, SaharaWest, AfricaMidWest)
   //override def iTiles: Seq[ITile] = Seq()
}

object AfricaWestGrid extends EGridMaker
{          
   def apply[TileT <: Tile, SideT <: TileSide](implicit fTile: (Int, Int, Terrain) => TileT, fSide: (Int, Int, SideTerr) => SideT,
       evTile: ClassTag[TileT], evSide: ClassTag[SideT]): EGrid80km[TileT, SideT] =
   {
     // import Terrain._
      val grid: EGNorth[TileT, SideT] = new EGNorth[TileT, SideT](new Array[Int](0), "AfricaWest", 0.east, xOffset = 200, 18, 300)
      grid.setAllTiles(Ocean)(fTile)
      grid.setAllSides(SideNone)(fSide)
//      val gs: (Int, Int, Multiple[Terrain]*) => Unit = grid.setRow[Terrain](fTile) _
//     // gs(444, 124, sea * 18, plain * 3, hills * 3, mtain * 3 ,plain * 5, sea * 2, hills * 4, plain)
//      gs(442, 126, sea * 17, plain * 3, hills * 4, mtain * 5, hills * 2, sea * 3, hills * 5)
//      gs(440, 124, sea * 18, plain * 4, hills * 3, mtain * 2, sea * 3, hills * 3, sea * 2, hills * 5)
//      gs(438, 122, sea * 11, hills * 7, mtain * 3, hills * 2, sea * 8, hills * 3, sea * 3, hills * 3)
//      gs(436, 120, sea * 11, hills * 3, plain * 4, hills * 2, mtain * 3, hills, sea * 5, hills, sea * 2, hills * 3, sea * 4, hills * 2)
//      gs(434, 122, sea * 11, hills * 3, plain * 3, hills, plain * 4, hills, sea * 6, hills, sea * 3,  hills * 3, plain, sea * 3, hills)
//      gs(432, 120, sea * 11, hills * 3, plain * 3, hills * 6, sea * 11, hills * 3, plain, sea * 3)      
//      gs(430, 118, sea * 12, hills * 10, sea * 8, hills, sea * 5, hills * 2, plain * 2, sea, hills)      
//      gs(428, 120, sea * 11, hills * 6, desert, hills * 3, sea * 8, plain, hills, sea * 6, hills, sea, plain , sea)
//      gs(426, 118, sea * 11, hills * 6, desert * 2, hills * 2, sea * 9, hills, sea * 7, hills, sea * 3)
//     // gs(300, 92, sea * 40, jungle * 15)//, sea * 3, plain * 12, sea * 3)
      grid
   }
}

