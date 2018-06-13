/* Copyright 2018 Richard Oliver. Licensed under Apache Licence version 2.0 */
package rich
package pDung
import geom._
import pGrid._

class DungGrid extends SquareGrid[DTile](2, 48, 2, 30)
{
   //setSides(SideOnly.apply)
}

object Dungeon1 extends DungGrid 
{
   fTilesSetAll(Wall)
   import SquareGrid._
   setTerrPath(Open, 4, 6, Rt * 11, Up * 4, Lt * 5, Up * 3, Rt * 7, Dn * 7)
   this.setColumn(22, 8,  Open * 2)
   getTile(22, 8).player = true   
   getTile(18, 12).player = true
}