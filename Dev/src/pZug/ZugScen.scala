/* Copyright 2018-21 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package pZug
import prid._

trait ZugScen
{ def turn: Int
  implicit val grid: HGrid
  def terrs: HCenArr[ZugTerr]
}

trait ZugScenStart extends ZugScen
{ override def turn: Int = 0
}

object Zug1 extends ZugScenStart
{ override implicit val grid: HGrid = new HGridReg(2, 14, 4, 48)

  val terrs: HCenArr[ZugTerr] = grid.newTileArr[ZugTerr](Plain)
}