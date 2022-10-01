/* Copyright 2018-22 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package prid
import geom._, pgui._

/** Tile system graphical projection. */
trait TSysProjection
{
  type GridT <: TGridSys

  def gridSys: GridT

  def panel: Panel
  var getFrame: () => GraphicElems = () => Arr()

  def ifGScale(minScale: Double, elems: => GraphicElems): GraphicElems

  var setStatusText: String => Unit = s => {}
  val buttons: Arr[PolygonCompound]

  def tiles: PolygonArr

  def tileActives: Arr[PolygonActive]

  /** The visible hex sides. */
  def sides: LineSegArr

  /** The visible inner hex sides. */
  def innerSides: LineSegArr

  /** The visible outer hex sides. */
  def outerSides: LineSegArr

  /** Draws visible hex sides. */
  def sidesDraw(lineWidth: Double = 2, colour: Colour = Colour.Black): LinesDraw = sides.draw(lineWidth, colour)

  /** Draws visible inner hex sides. */
  def innerSidesDraw(lineWidth: Double = 2, colour: Colour = Colour.Black): LinesDraw = innerSides.draw(lineWidth, colour)

  /** Draws visible outer hex sides. */
  def outerSidesDraw(lineWidth: Double = 2, colour: Colour = Colour.Black): LinesDraw = outerSides.draw(lineWidth, colour)
}