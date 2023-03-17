/* Copyright 2018-23 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package pnorm
import prid.phex._

trait NormScen
{ def title = "Normandy 1944"
  implicit val grid: HGrid
  val terrs: HCenLayer[Tile]
  val corners: HCornerLayer
}

object NormScen1 extends NormScen
{ override implicit val grid  = HGridReg(2, 12, 2, 40)
  override val terrs = grid.newHCenLayer[Tile](Sea)
  terrs.setRowSame(2, Plain)
  terrs.set(6, 6, Island)
  terrs.set(6, 10, Head1Land(0))
  terrs.set(6, 14, Head1Land(1))
  override val corners: HCornerLayer = grid.newHVertOffsetLayer
}