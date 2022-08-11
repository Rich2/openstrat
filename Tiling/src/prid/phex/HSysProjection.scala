/* Copyright 2018-22 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package prid; package phex
import geom._

/** Hex grid system projection. */
trait HSysProjection
{ type GridT <: HGridSys
  def gridSys: GridT
  def sides: LineSegArr
}

final case class HProjectionFlat(gridSys: HGridSys) extends HSysProjection
{ type GridT = HGridSys

  var cPScale: Double = 10
  var focus: Vec2 = Vec2(0, 0)
  override def sides: LineSegArr = gridSys.sideLines
  def sidesDraw(lineWidth: Double = 2, colour: Colour = Colour.Black): LinesDraw = sides.draw(lineWidth, colour)
}