/* Copyright 2018-22 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package prid; package phex
import geom._

/** Hex grid system projection. */
trait HSysProj
{ type GridT <: HGridSys
  def gridSys: GridT
  def sides: LineSegArr
}

final case class HProjFlat(gridSys: HGridSys) extends HSysProj
{ type GridT = HGridSys

  var cPScale: Double = 10
  var focus: Vec2 = Vec2(0, 0)
  override def sides: LineSegArr = ???
}