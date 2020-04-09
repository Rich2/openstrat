/* Copyright 2018 Richard Oliver. Licensed under Apache Licence version 2.0 */
package ostrat
package pCiv
import pEarth._, pGrid._

trait CivScen
{ implicit def grid: TileGrid
  def terrs: TilesRef[Terrain]
  /** Not sure about this collection type. */
  def lunits: TilesRef[List[Warrior]]
}

object Civ1 extends CivScen
{
  implicit val grid = HexGridReg(4, 16, 4, 40)
  val terrs = grid.newTilesRefSet[Terrain](Plains)
  terrs.setRow(12, 20, Hilly, Mountains * 2)
  terrs.setRow(4, 4, Hilly * 3)
  val lunits: TilesRef[List[Warrior]] = grid.newTilesRefSet[List[Warrior]](Nil)
  lunits.prependAt(10, 18, Warrior(Uruk))
  lunits.prependAt(6, 10, Warrior(Eridu))
}
