/* Copyright 2018 Richard Oliver. Licensed under Apache Licence version 2.0 */
package ostrat
package pCiv
import pEarth._, pGrid._

class CivGrid(xTileMin: Int, xTileMax: Int, yTileMin: Int, yTileMax: Int, turnNum: Int) extends HexGridRegOld[CTileOld, SideOldBare](xTileMin,
    xTileMax, yTileMin, yTileMax, turnNum)
{
  
}

object Civ1 extends CivGrid(4, 40, 4, 16, 0)
{
  import WTile._
  setTilesAll(plain)
  import Civ1.{setRow => gs}
 
  gs(yRow = 12, xStart = 20, hills, mtain * 2)
  modTilesAll(_.settlement = true, (24, 8), (34, 6))
  getTile(18, 10).lunits +-= Warrior(Uruk, 18, 10)
  getTile(10, 6).lunits +-= Warrior(Eridu, 10, 6)
}
