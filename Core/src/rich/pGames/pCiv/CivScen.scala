/* Copyright 2018 Richard Oliver. Licensed under Apache Licence version 2.0 */
package rich
package pGames
package pCiv
import pEarth._
import pGrid._

class CivGrid(xTileMin: Int, xTileMax: Int, yTileMin: Int, yTileMax: Int) extends HexGridReg[CTile](xTileMin, xTileMax, yTileMin, yTileMax)
{
  
}

object Civ1 extends CivGrid(4, 40, 4, 16)
{
   fTilesSetAll(Terrain.plain)
   import Civ1.{setRow => gs}
   import Terrain._
   gs(yRow = 12, xStart = 20, hills, mtain * 2)
   modTiles(_.settlement = true, (24, 8), (34, 6))
   getTile(18, 10).lunits ::= Warrior(Uruk, 18, 10)
   getTile(10, 6).lunits ::= Warrior(Eridu, 10, 6)
}

object Cunit
