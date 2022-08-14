/* Copyright 2018-22 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package prid; package phex
import geom._, pgui._

/** Hex grid system projection. */
trait HSysProjection
{ type GridT <: HGridSys
  def gridSys: GridT

  /** The visible hex sides. */
  def sides: LineSegArr

  /** The visible inner hex sides. */
  def innerSides: LineSegArr

  /** The visible outer hex sides. */
  def outerSides: LineSegArr

  /** transforms and filters out non visible [[HSide]]s. */
  def transHSides(inp: HSideArr): LineSegArr

  /** Draws visible hex sides. */
  def sidesDraw(lineWidth: Double = 2, colour: Colour = Colour.Black): LinesDraw = sides.draw(lineWidth, colour)

  /** Draws visible inner hex sides. */
  def innerSidesDraw(lineWidth: Double = 2, colour: Colour = Colour.Black): LinesDraw = innerSides.draw(lineWidth, colour)

  /** Draws visible outer hex sides. */
  def outerSidesDraw(lineWidth: Double = 2, colour: Colour = Colour.Black): LinesDraw = outerSides.draw(lineWidth, colour)

  def panel: Panel
  def setView(view: Any): Unit = {}
}