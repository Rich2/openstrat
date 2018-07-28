/* Copyright 2018 Richard Oliver. Licensed under Apache Licence version 2.0 */
package ostrat
package pGames
package pZug
import pGrid._

class ZugGrid(xTileMin: Int, xTileMax: Int, yTileMin: Int, yTileMax: Int) extends HexGridReg[ZugTile, ZugSide](xTileMin, xTileMax, yTileMin, yTileMax)
{
   //setSides(SideOnly.apply)
   val fSquad: (ZugTile, Polity) => Unit = (tile, p: Polity) =>
      {
         tile.lunits = Squad(p, tile.cood) :: tile.lunits
      } 
}

object Zug1 extends ZugGrid(4, 48, 2, 14)
{
   fTilesSetAll(Plain)
   fSidesSetAll(false)
   fSetSides(true, 35 -> 11, 36 -> 10, 37 -> 9)
   import Zug1.{setRow => gs}
   gs(yRow = 12, xStart = 4, WheatField * 2)
   gs(10, 6, WheatField)
   gs(8, 4, WheatField * 2, Stone * 2, WheatField * 2, Lake)
   gs(6, 6, WheatField, Plain * 4, Lake)
   gs(4, 4, WheatField * 2)
   gs(2, 6, WheatField)    
   //fSetSide(30, 11, true)
   fTiles[Polity](fSquad, (18, 6, Germany), (30, 6, Germany), (22, 10, Britain), (30, 10, Britain))
}