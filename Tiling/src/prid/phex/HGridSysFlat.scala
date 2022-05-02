/* Copyright 2018-22 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package prid; package phex
import geom._, Colour.Black

trait HGridSysFlat extends Any with HGridSys with TGridSysFlat
{
  def polygons: Arr[Polygon]

  /** The active tiles without any PaintElems. */
  def activeTiles: Arr[PolygonActive]



  /** The line segments [[LineSeg]]s for the sides of the tiles.
   *  @group SidesGroup */
  def sideLines(implicit grider: HGridSysFlat): LineSegArr

  /** This gives the all tile grid lines in a single colour and line width.
   *  @group SidesGroup  */
  final def sidesDraw(colour: Colour = Black, lineWidth: Double = 2.0)(implicit grider: HGridSysFlat): LinesDraw = sideLines.draw(lineWidth, colour)
}