/* Copyright 2018-20 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat
package geom

/** A [[Polygon]] with at least 3 vertices. */
trait Polygon3Plus extends Polygon
{
  /** The X component of the 1st Vertex. The default convention is for the vertices to be numbered in a clockwise direction with the 1st vertex
   *  immediately clockwise from 12 o'clock. */
  def x1: Double
  /** The Y component of the 1st Vertex. The default convention is for the vertices to be numbered in a clockwise direction with the 1st vertex
   *  immediately clockwise from 12 o'clock. */
  def y1: Double
  /** The 1st Vertex. The default convention is for the vertices to be numbered in a clockwise direction with the 1st vertex immediately clockwise
   *  from 12 o'clock. */
  def v1: Vec2
  /** The X component of the 2nd Vertex. The default convention is for the vertices to be numbered in a clockwise direction with the 1st vertex
   *  immediately clockwise from 12 o'clock. */
  def x2: Double
  /** The Y component of the 2nd Vertex. The default convention is for the vertices to be numbered in a clockwise direction with the 1st vertex
   *  immediately clockwise from 12 o'clock. */
  def y2: Double
  /** The 2nd Vertex. The default convention is for the vertices to be numbered in a clockwise direction with the 1st vertex immediately clockwise from
   *  12 o'clock. */
  def v2: Vec2
  /** The X component of the 3rd Vertex. The default convention is for the vertices to be numbered in a clockwise direction with the 1st vertex
   *  immediately clockwise from 12 o'clock. */
  def x3: Double
  /** The Y component of the 3rd Vertex. The default convention is for the vertices to be numbered in a clockwise direction with the 1st vertex
   *  immediately clockwise from 12 o'clock. */
  def y3: Double
  /** The 3rd Vertex. The default convention is for the vertices to be numbered in a clockwise direction with the 1st vertex immediately clockwise from
   *  12 o'clock. */
  def v3: Vec2

  /** The last vertex. */
  override def vLast: Vec2 = vert(vertsNum)

  /** Side 1. */
  def s1: LineSeg = LineSeg(vLast, v1)

  /** Side 2. */
  def s2: LineSeg = LineSeg(v1, v2)

  /** Side 3. */
  def s3: LineSeg = LineSeg(v2, v3)
}

/** A [[Polygon]] with at least 4 vertices. */
trait Polygon4Plus extends Polygon3Plus
{
  /** The X component of the 4th Vertex. The default convention is for the vertices to be numbered in a clokwise direction with the 1st vertex
   *  immediately clockwise from 12 o'clock. */
  def x4: Double
  /** The Y component of the 4th Vertex. The default convention is for the vertices to be numbered in a clokwise direction with the 1st vertex
   *  immediately clockwise from 12 o'clock. */
  def y4: Double
  /** The 4th Vertex. The default convention is for the vertices to be numbered in a clockwise direction with the 1st vertex immediately clockwise from
   *  12 o'clock. */
  def v4: Vec2
}

/** A [[Polygon]] with at least 5 vertices. */
trait Polygon5Plus extends Polygon4Plus
{
  /** The X component of the 5th Vertex. The default convention is for the vertices to be numbered in a clockwise direction with the 1st vertex
   *  immediately clockwise from 12 o'clock. */
  def x5: Double
  /** The Y component of the 5th Vertex. The default convention is for the vertices to be numbered in a clockwise direction with the 1st vertex
   *  immediately clockwise from 12 o'clock. */
  def y5: Double
  /** The 5th Vertex. The default convention is for the vertices to be numbered in a clockwise direction with the 1st vertex immediately clockwise
   *  from 12 o'clock. */
  def v5: Vec2
}

/** A [[Polygon]] with at least 6 vertices. */
trait Polygon6Plus extends Polygon5Plus
{
  /** The X component of the 6th Vertex. The default convention is for the vertices to be numbered in a clockwise direction with the 1st vertex
   *  immediately clockwise from 12 o'clock. */
  def x6: Double
  /** The Y component of the 6th Vertex. The default convention is for the vertices to be numbered in a clockwise direction with the 1st vertex
   *  immediately clockwise from 12 o'clock. */
  def y6: Double
  /** The 6th Vertex. The default convention is for the vertices to be numbered in a clockwise direction with the 1st vertex immediately clockwise
   *  from 12 o'clock. */
  def v6: Vec2
}