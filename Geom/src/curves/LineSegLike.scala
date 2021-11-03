/* Copyright 2018-21 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package geom

/** A class that is like a LineSeg, includes [[LineSeg]] and [[LineSegMetre]]. */
trait LineSegLike[VT]
{ def startPt: VT
  def endPt: VT
}