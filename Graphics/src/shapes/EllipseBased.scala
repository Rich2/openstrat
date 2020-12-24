/* Copyright 2018-20 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat
package geom

/** Common trait for [[Ellipse]] and EArc. The main purpose of this trait is not to share code, but to establish naming conventions for elliptical
 * properties. */
trait EllipseBased extends GeomElem
{
  /** The end point of axis 1. By default this is on the right of the Ellipse. Mathematically this can be referred to as a vertex for the major axis
   *  or a co-vertex for the minor axis. */
  def pAxes1: Pt2

  /** The start point of axis 2. By default this is at the bottom of the Ellipse. Mathematically this can be referred to as a vertex for the major
   *  axis or a co-vertex for the minor axis. */
  def pAxes2: Pt2

  /** The start point of axis 1. By default this is on the left of the Ellipse. Mathematically this can be referred to as a vertex for the major axis
   *  or a co-vertex for the minor axis. */
  def pAxes3: Pt2

  /** The end point of axis 2. By default this is at the top of the Ellipse. Mathematically this can be referred to as a vertex for the major axis or
   *  a co-vertex for the minor axis. */
  def pAxes4: Pt2
}
