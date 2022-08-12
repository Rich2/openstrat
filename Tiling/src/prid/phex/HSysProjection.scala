/* Copyright 2018-22 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package prid; package phex
import geom._, pgui._

/** Hex grid system projection. */
trait HSysProjection
{ type GridT <: HGridSys
  def gridSys: GridT
  def sides: LineSegArr
  def innerSides: LineSegArr
  def outerSides: LineSegArr
  def sidesDraw(lineWidth: Double = 2, colour: Colour = Colour.Black): LinesDraw = sides.draw(lineWidth, colour)
  def innerSidesDraw(lineWidth: Double = 2, colour: Colour = Colour.Black): LinesDraw = innerSides.draw(lineWidth, colour)
  def outerSidesDraw(lineWidth: Double = 2, colour: Colour = Colour.Black): LinesDraw = outerSides.draw(lineWidth, colour)
  def panel: Panel
}