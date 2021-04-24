/* Copyright 2018-21 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package pZug
import prid._

trait ZugScen extends HexGridScen
{ /** tile terrain. */
  def terrs: HCenArr[ZugTerr]
}

trait ZugScenStart extends ZugScen
{ override def turn: Int = 0
}

object Zug1 extends ZugScenStart
{ override implicit val grid: HGrid = new HGridReg(2, 14, 4, 48)

  val terrs: HCenArr[ZugTerr] = grid.newTileArr[ZugTerr](Plain)
  def tr(yRow: Int, cStart: Int, tileValues: Multiple[ZugTerr]*) = terrs.setRow(yRow, cStart, tileValues :_*)(grid)
  tr(yRow = 12, cStart = 4, WheatField * 2)
  tr(10, 6, WheatField, Plain * 2, StoneBuilding, Plain * 4, WoodBuilding)
  tr(8, 4, WheatField * 3, StoneBuilding * 1, WheatField * 2, Lake)
  tr(6, 6, WheatField, Plain * 2, StoneBuilding, Plain, Lake)
  tr(4, 4, WheatField * 2)
  tr(2, 6, WheatField)
}