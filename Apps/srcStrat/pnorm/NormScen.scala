/* Copyright 2018-25 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package pnorm
import prid.phex.*, MultExt.*

trait NormScen
{ def title = "Normandy 1944"
  implicit val grid: HGrid
  val terrs: LayerHcRefSys[Tile]
  val corners: HCornerLayer
}

object NormScen1 extends NormScen
{ override implicit val grid  = HGridRect.minMax(2, 12, 2, 40)
  override val terrs = LayerHcRefSys[Tile](Sea)

  terrs.set(6, 6, Island())
//  terrs.set(6, 10, Head1Land(3))
//  terrs.set(6, 14, Head1Land(2))
  terrs.setRowStartSame(4, 5, Land())
  terrs.setRowEnd(4, 32, Land(Hill) * 2, Land())
  terrs.setRowSame(2, Land())
  override val corners: HCornerLayer = HCornerLayer()
}