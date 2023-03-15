/* Copyright 2018-23 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package pnorm
import prid.phex._

trait NormScen
{ def title = "Normandy 1944"
  implicit val grid: HGrid
  val terrs: HCenLayer[Terr]
  val corners: HCornerLayer
}

object NormScen1 extends NormScen
{ override implicit val grid  = HGridReg(2, 6, 2, 10)
  debvar(grid.numTiles)
  override val terrs = grid.newHCenLayer[Terr](Sea)
  override val corners: HCornerLayer = grid.newHVertOffsetLayer
}