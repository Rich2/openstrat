/* Copyright 2018-22 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package prid
import ostrat.geom._

/** Tile system graphical projection. */
trait TSysProjection
{
  type GridT <: TGridSys

  def gridSys: GridT

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
}

trait TSysProjectionFlat extends TSysProjection
{
  /** The number of pixels per column unit. */
  var pixCScale: Double

  /** The number of pixels per tile from side to opposite side. */
  def pixTileScale: Double

  var focus: Vec2

  def pixTileScaleStr = s"scale = ${pixTileScale.str2} pixels per tile"
}