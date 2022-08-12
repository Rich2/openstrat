/* Copyright 2018-22 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package egrid
import geom._, prid._, phex._, pgui._

case class HSysProjectionEarth(gridSys: EGridSys, panel: Panel) extends HSysProjection
{
  override type GridT = EGridSys

//  val sides0 = sTerrs.truesMap(_.lineSegHC.map(gridSys.hCoordLL(_)))
//
//  def sides1: LineSegM3Arr = sides0.map {
//    _.map(_.toMetres3)
//  }
//
//  def sides2: LineSegM3Arr = sides1.map {
//    _.map(_.fromLatLongFocus(focus))
//  }
//
//  def sides3: LineSegM3Arr = sides2.filter(_.zsPos)
//
//  def sides4: LineSegArr = sides3.map {
//    _.map(_.xy / scale)
//  }

  //def sides: GraphicElems = sides4.map { ls => Rectangle.fromAxisRatio(ls, 0.3).fill(Red) }

  override def sides: LineSegArr = ???

  override def innerSides: LineSegArr = ???

  override def outerSides: LineSegArr = ???
}
