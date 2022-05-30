/* Copyright 2018-22 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package egrid
import prid._, phex._, pEarth._

trait WarmTerrs
{
  implicit val grid: EGridWarm
  def terrs: HCenDGrid[WTile]
  def sTerrs(): HSideBoolDGrid
}