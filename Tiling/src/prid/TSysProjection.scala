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
}
