/* Copyright 2018-22 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package prid
import geom._, pgui._

/** Tile system graphical projection. */
trait TSysProjection
{ /** The type of the grid system this projects from. */
  type GridT <: TGridSys

  /** The parent grid system of the scenario, from which the this projection projects from. */
  def parent: GridT

  /** The panel this projection outputs to. */
  def panel: Panel

  /** Gives the projector access to the scenarios tile graphic creation. */
  var getFrame: () => GraphicElems = () => RArr()

  /** Filters the [[GraphicElem]]s away if the grid scale is too small to display the elements satisfactorily.  */
  def ifGScale(minScale: Double, elems: => GraphicElems): GraphicElems

  var setStatusText: String => Unit = s => {}
  val buttons: RArr[PolygonCompound]

  /** Projected [[Polygon]]s of the tiles. */
  def tilePolygons: PolygonArr

  /** Active projected [[Polygon]]s of the tiles. */
  def tileActives: RArr[PolygonActive]

  /** The visible hex sides. */
  def sideLines: LineSegArr

  /** The visible inner hex sides. */
  def innerSideLines: LineSegArr

  /** The visible outer hex sides. */
  def outerSideLines: LineSegArr

  /** Draws visible hex sides. */
  def sidesDraw(lineWidth: Double = 2, colour: Colour = Colour.Black): LinesDraw = sideLines.draw(lineWidth, colour)

  /** Draws visible inner hex sides. */
  def innerSidesDraw(lineWidth: Double = 2, colour: Colour = Colour.Black): LinesDraw = innerSideLines.draw(lineWidth, colour)

  /** Draws visible outer hex sides. */
  def outerSidesDraw(lineWidth: Double = 2, colour: Colour = Colour.Black): LinesDraw = outerSideLines.draw(lineWidth, colour)
}