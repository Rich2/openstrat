/* Copyright 2018-21 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package geom

/** This is an object where the boundary points for top left, top right, bottom right and bottom left are defined. This trait is used by some
 *  geometric elements as well as [[pCanv.CanvasPlatform]] and [[pCanv.Panel]]. */
trait OrdinalEdgePoints extends WithCentre
{
  def topRight: Pt2

  /** Top right offset from centre. */
  def trOffset: Vec2 = topRight << cen

  def bottomRight: Pt2

  def brOffset: Vec2 = bottomRight << cen

  def bottomLeft: Pt2
  def blOffset: Vec2 = bottomLeft << cen
  def topLeft: Pt2
  def tlOffset: Vec2 = topLeft << cen
  //def topCen: Vec2
}