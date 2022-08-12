/* Copyright 2018-22 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package egrid
import geom._, prid._, phex._, pgui._

case class HSysProjectionEarth(gridSys: EGridSys, panel: Panel) extends HSysProjection
{
  override type GridT = EGridSys

  override def sides: LineSegArr = ???

  override def innerSides: LineSegArr = ???

  override def outerSides: LineSegArr = ???
}
