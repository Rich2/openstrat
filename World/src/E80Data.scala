/* Copyright 2018-21 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat
package pEarth
import pGrid._

trait E80Data
{ implicit val grid: HexGridIrr
  def terrs: TilesArr[WTile]
  def sTerrs: SideBooleans
  def vTerrs: VertInts
}

object EuropeNWGrid extends HexGridIrr(446, EGrid80Km.getBounds(200, 446, 540))

object EuropeNEGrid extends HexGridIrr(446, EGrid80Km.getBounds(400, 446, 540))