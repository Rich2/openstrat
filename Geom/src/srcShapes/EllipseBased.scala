/* Copyright 2018-25 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package geom

/** Common trait for [[Ellipse]] and EArc. The main purpose of this trait is not to share code, but to establish naming conventions for elliptical
 * properties. */
trait EllipseBased extends Geom2Elem, WithCentre
{
  /** Radius 1 of the ellipse. This extends from the centre to point 1 or point 3. By default, this is the horizontal axis of the ellipse. This can be the major
   * or minor axis. */
  def radius1: Double

  /** Diameter 1 of the ellipse. This extends from point 3 to point1 By default, this is the horizontal axis of the ellipse. This can be the major or minor
   * axis. */
  final def diameter1: Double = radius1 * 2

  /** Radius 2 of the ellipse. By default, this is the vertical axis of the ellipse. This can be the major or minor axis. */
  def radius2: Double

  /** Diameter 2 of this ellipse. By default, this is the vertical axis of the ellipse. This can be the major or minor axis. */
  final def diameter2: Double = radius2 * 2

  /** The end point of axis 2. By default, this is at the top of the Ellipse. Mathematically this can be referred to as a vertex for the major axis or a
   * co-vertex for the minor axis. */
  def p0: Pt2 = Pt2(p0X, p0Y)

  /** The X component of the end point of axis 2. By default, this is at the top of the Ellipse. Mathematically this can be referred to as a vertex for the
   * major axis or  a co-vertex for the minor axis. */
  def p0X: Double

  /** The Y component of the end point of axis 2. By default, this is at the top of the Ellipse. Mathematically this can be referred to as a vertex for the
   * major axis or  a co-vertex for the minor axis. */
  def p0Y: Double


  /** The end point of axis 1. By default, this is on the right of the Ellipse. Mathematically this can be referred to as a vertex for the major axis or a
   * co-vertex for the minor axis. */
  def p1: Pt2

  /** The X component of the end point of axis 1. By default, this is on the right of the Ellipse. Mathematically this can be referred to as a vertex for the
   * major axis or a co-vertex for the minor axis. */
  def p1X: Double

  /** The Y component of the end point of axis 1. By default, this is on the right of the Ellipse. Mathematically this can be referred to as a vertex for the
   * major axis or a co-vertex for the minor axis. */
  def p1Y: Double

  /** The start point of axis 2. By default, this is at the bottom of the Ellipse. Mathematically this can be referred to as a vertex for the major axis or a
   * co-vertex for the minor axis. */
  def p2: Pt2

  /** The X component of the start point of axis 2. By default, this is at the bottom of the Ellipse. Mathematically this can be referred to as a vertex for the
   * major axis or a co-vertex for the minor axis. */
  def p2X: Double

  /** The Y component of the start point of axis 2. By default, this is at the bottom of the Ellipse. Mathematically this can be referred to as a vertex for the
   * major axis or a co-vertex for the minor axis. */
  def p2Y: Double

  /** The start point of axis 1. By default, this is on the left of the Ellipse. Mathematically this can be referred to as a vertex for the major axis or a
   * co-vertex for the minor axis. */
  def p3: Pt2

  /** The X component of the start point of axis 1. By default, this is on the left of the Ellipse. Mathematically this can be referred to as a vertex for the
   * major axis or a co-vertex for the minor axis. */
  def p3X: Double

  /** The Y component of the start point of axis 1. By default, this is on the left of the Ellipse. Mathematically this can be referred to as a vertex for the
   * major axis or a co-vertex for the minor axis. */
  def p3Y: Double

  /** The 2D vector [[Vec2]] from the centre point to pAxes4, the end point of axis 2, by default at the top of the Ellipse this arc is based on. */
  def cenP0: Vec2 = cen >> p0

  /** The 2D vector [[Vec2]] from the centre point to pAxes1, the end point of axis 1, by default on the right of the Ellipse this arc is based on. */
  def cenP1: Vec2 = cen >> p1

  /** The 2D vector [[Vec2]] from the centre point to pAxes2, the start point of axis 2, by default at the bottom of the Ellipse this arc is based on. */
  def cenP2: Vec2 = cen >> p2

  /** The 2D vector [[Vec2]] from the centre point to pAxes3, the start point of axis 1 , by default on the left of the Ellipse this arc is based on. */
  def cenP3: Vec2 = cen >> p3
}