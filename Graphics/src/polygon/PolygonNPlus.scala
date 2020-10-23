/* Copyright 2018-20 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat
package geom

trait Polygon3Plus extends Polygon
{
  /** The X component of the 1st Vertex. */
  def x1: Double
  /** The Y component of the 1st Vertex. */
  def y1: Double
  /** The 1st Vertex. */
  def v1: Vec2
  /** The X component of the 2nd Vertex. */
  def x2: Double
  def y2: Double
  def v2: Vec2
  def x3: Double
  def y3: Double
  def v3: Vec2
}

trait Polygon4Plus extends Polygon3Plus
{ def x4: Double
  def y4: Double
  def v4: Vec2
}