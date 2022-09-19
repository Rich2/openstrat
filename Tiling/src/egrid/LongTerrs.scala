/* Copyright 2018-22 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package egrid
import prid._, phex._, pEarth._

/** Contains Earth longitude range grid, [[WTile]] layer and a [[Boolean]] tile side data layer. */
trait LongTerrs
{ implicit val grid: EGridLongFull
  def terrs: HCenLayer[WTile]
  def sTerrs: HSideBoolLayer
}