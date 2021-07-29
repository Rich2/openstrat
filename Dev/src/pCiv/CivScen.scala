/* Copyright 2018-21 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package pCiv
import prid._, pEarth._

trait CivScen  extends HexGridScen
{
  /** tile terrain. */
  def terrs: HCenArr[Terrain]
}

trait CivScenStart extends CivScen
{ override def turn: Int = 0
}

/** Civ scenario 1. */
object Civ11 extends CivScenStart
{
  override implicit val grid: HGrid = HGridReg(2, 14, 4, 40)
  val terrs: HCenArr[Terrain] = grid.newTileArr[Terrain](Plains)
  terrs.setRow(12, 20, Hilly, Mountains * 2)
  terrs.setRow(4, 4, Hilly * 3)
}