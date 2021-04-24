/* Copyright 2018 Richard Oliver. Licensed under Apache Licence version 2.0 */
package ostrat
package pCiv
import pEarth._, pGrid._

trait CivScen
{ implicit def grid: TileGridOld
  def terrs: TilesArr[Terrain]
  /** Not sure about this collection type. */
  def lunits: TilesArr[List[Warrior]]
}

object Civ1 extends CivScen
{
  implicit val grid = HexGridRegOld(4, 16, 4, 40)
  val terrs = grid.newTileArr[Terrain](Plains)
  terrs.setRow(12, 20, Hilly, Mountains * 2)
  terrs.setRow(4, 4, Hilly * 3)
  val lunits: TilesArr[List[Warrior]] = grid.newTileArr[List[Warrior]](Nil)
  lunits.prependAt(10, 18, Warrior(Uruk))
  lunits.prependAt(6, 10, Warrior(Eridu))
}
