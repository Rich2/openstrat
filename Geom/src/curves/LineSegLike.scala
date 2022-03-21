/* Copyright 2018-22 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package geom

/** A class that is like a LineSeg, includes [[LineSeg]] and [[LineSegMetre]]. The trait takes the type parameter of the vertex. */
trait LineSegLike[VT]
{
  /** The start point of the [[LineSeglike]]. The type of start point will depend on the VT vertex type. For example a [[Pt2]] for a [[LineSeg]] a
   * [[PtM2]] for a [[LineSegMetre]]. */
  def startPt: VT

  /** The end point of the [[LineSeglike]]. The type of start point will depend on the VT vertex type. For example a [[Pt2]] for a [[LineSeg]] a
   * [[PtM2]] for a [[LineSegMetre]]. */
  def endPt: VT
}