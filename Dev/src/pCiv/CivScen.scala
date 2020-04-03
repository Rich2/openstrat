/* Copyright 2018 Richard Oliver. Licensed under Apache Licence version 2.0 */
package ostrat
package pCiv
import pEarth._, pGrid._, WTile._

class CivGridOld(xTileMin: Int, xTileMax: Int, yTileMin: Int, yTileMax: Int, turnNum: Int) extends HexGridRegOld[CTileOld, SideOldBare](xTileMin,
    xTileMax, yTileMin, yTileMax, turnNum)
{
  
}

object Civ1Old extends CivGridOld(4, 40, 4, 16, 0)
{ setTilesAll(plain)
  import Civ1Old.{setRow => gs}
 
  gs(yRow = 12, xStart = 20, hills, mtain * 2)
  modTilesAll(_.settlement = true, (24, 8), (34, 6))
  getTile(18, 10).lunits +-= WarriorOld(Uruk, 18, 10)
  getTile(10, 6).lunits +-= WarriorOld(Eridu, 10, 6)
}

trait CivScen
{ implicit def grid: TileGrid
  def terrs: Refs[Terrain]
  def lunits: Refs[List[Warrior]]
}

object Civ1 extends CivScen
{
  implicit val grid = HexGridReg(4, 16, 4, 40)
  val terrs = grid.newRefsSet[Terrain](Plains)
  terrs.setRow(12, 20, Hilly, Mountains * 2)
  terrs.setRow(4, 4, Hilly * 3)
  val lunits: Refs[List[Warrior]] = grid.newRefsSet[List[Warrior]](Nil)
  lunits.gridPrepend(10, 18, Warrior(Uruk))
  lunits.gridPrepend(6, 10, Warrior(Eridu))
}
