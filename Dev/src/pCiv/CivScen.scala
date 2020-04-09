/* Copyright 2018 Richard Oliver. Licensed under Apache Licence version 2.0 */
package ostrat
package pCiv
import pEarth._, pGrid._

trait CivScen
{ implicit def grid: TileGrid
  def terrs: TilesRef[Terrain]
  def lunits: Refs[List[Warrior]]
}

object Civ1 extends CivScen
{
  implicit val grid = HexGridReg(4, 16, 4, 40)
  val terrs = grid.newTilesRefSet[Terrain](Plains)
  terrs.setRow(12, 20, Hilly, Mountains * 2)
  terrs.setRow(4, 4, Hilly * 3)
  val lunits: Refs[List[Warrior]] = grid.newRefsSetOld[List[Warrior]](Nil)
  lunits.gridPrepend(10, 18, Warrior(Uruk))
  lunits.gridPrepend(6, 10, Warrior(Eridu))
}
