/* Copyright 2018 Richard Oliver. Licensed under Apache Licence version 2.0 */
package ostrat
package pEarth
import geom._
import Terrain._
import pGrid._

object EuropeWest extends Area1('EuropeWest, 20 ll 0)  
{
   //override val gridMaker = EuropeWestGrid      
   
   override def fill = false
   import EuropePts._
   import pEuropeWest._
   override val a2Seq: List[Area2] = List(Ireland, England, Scotland, OuterHebrides, Shetland, Faroe, JanMayen,
         Frankia, Iberia, Jutland, Zealand,  Germania,
         Alpsland, Sardina, Italy, Corsica, EuropeCentral, eEurope, Scandanavia )
   //override def iTiles: Seq[ITile] = Seq()//,CBritain, NBritain ,SBritain, Denmark, NSea, BalticSea)
   //override def iSides: Seq[ISide] = iTiles.flatMap(_.ownISides)
}

object EuropeWestGrid extends EGridMaker
{
   def apply[TileT <: GridElem, SideT <: GridElem](fTile: (Int, Int, Terrain) => TileT)(implicit evTile: IsType[TileT], evSide: IsType[SideT]):
   EGrid80km[TileT, SideT] =
   {
      val grid: EGFarNorth[TileT, SideT] = new EGFarNorth[TileT, SideT]("WEurope", 0.east, xOffset = 200, xTileMin = 114, xTileMax = 286)
      grid.fTilesSetAll(Ocean)(fTile)
      val gs: (Int, Int, Multiple[Terrain]*) => Unit = grid.fSetRow[Terrain](fTile) _
      gs(518, 230, taiga)
      gs(516, 232, taiga)
      gs(514, 230, taiga) 
      gs(512, 232, taiga)
      gs(510, 230, taiga * 2)
      gs(508, 228, taiga * 3)
      gs(506, 226, taiga * 3)
      gs(504, 224, taiga * 4)
      gs(502, 218, taiga * 6)
      gs(500, 216, taiga * 6) 
      gs(498, 182, taiga, sea * 7, taiga * 7)
      gs(496, 216, taiga * 7)
      gs(494, 198, taiga, sea * 3, taiga * 7)
      gs(492, 216, taiga * 7)
      gs(490, 218, taiga * 3, sea, taiga *3)
      gs(488, 220, taiga * 2, sea * 2, taiga * 3)//, sea * 2)
      gs(486, 186, hills * 2, sea * 10, plain * 3)// plain, sea * 2)
      gs(484, 180, hills * 4, sea * 10, plain * 3)//, plain , sea * 2)
      gs(482, 182, hills * 3, sea * 8, plain * 2, sea, plain * 3)//, sea * 3)
      gs(480, 184, hills * 2, sea * 9, plain *2, sea, plain * 2)//, plain, sea * 3)
      gs(478, 182, hills, plain * 3, sea * 7, plain * 2, sea, plain * 2, sea)//, sea * 4)
      gs(476, 176, plain * 2, sea, hills * 2, sea * 8, plain * 3, sea * 3)//, sea * 3)
      gs(474, 170, plain * 3, sea * 2, hills, plain, sea * 8, plain * 2, sea * 3)//, sea, plain * 3)
      gs(472, 168, plain * 3, sea * 3, plain * 2, sea * 7, plain * 6)//, plain * 4)
      gs(470, 170, plain * 3, sea, hills * 2, plain * 2, sea * 5, plain * 8)//* 4)
      gs(468, 168, plain * 3, sea, hills * 3, plain * 3, sea * 3, plain * 8)//, plain * 4)
      gs(466, 170, plain, sea * 3, hills * 2, plain * 3, sea * 2, plain * 4, hills * 2, plain * 4)//, plain * 4)
      gs(464, 188, plain * 5, sea, plain * 2, hills * 7, plain, hills)//, plain * 3)
      gs(462, 182, hills * 3, sea * 3, plain * 4, hills * 8)//, hills * 2, plain * 3)
      gs(460, 204, plain * 3, hills * 10) //, hills * 2, plain * 2)
      gs(458, 194, plain * 8, hills * 3, plain, hills * 3, plain)//, plain, hills * 3)
      gs(456, 188, plain, sea, plain * 7, hills * 3, plain * 3, hills * 2)//, hills, plain, hills * 3)
      gs(454, 186, plain * 10, hills * 2, plain * 4, hills, mtain)//, mtain, plain * 2, hills)
      gs(452, 192, plain * 9, hills * 2, mtain * 6)// mtain, plain * 3)
      gs(450, 194, plain * 7, hills, mtain * 8)//mtain * 2, hills, plain * 2)      
      gs(448, 196, plain * 4, hills * 2, plain, mtain * 9)//, plain * 5)
      gs(446, 198, plain * 2, hills * 4, mtain * 3, plain * 2, mtain, plain * 2, hills * 2)//, hills * 2, plain * 2)
//      val hc1 = pGrid.HexCood(188, 472)
//      deb(grid.vec2ToLL(hc1.toVec2).toString)
//      deb(grid.getLL(hc1).toString)
//      grid.setLL(hc1, LatLong.deg(10, 10))
//      deb(grid.getLL(hc1).toString)
      grid
   }
}

