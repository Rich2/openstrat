/* Copyright 2018-20 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat
package geom

case class Hexlign(width: Double, xCen: Double, yCen: Double) extends HexReg
{
  override def apply(index: Int): Vec2 = ???
  /** The X component of the 6th Vertex. The default convention is for the vertices to be numbered in a clockwise direction with the 1st vertex
   * immediately clockwise from 12 o'clock. */
  override def x6: Double = ???

  /** The Y component of the 6th Vertex. The default convention is for the vertices to be numbered in a clockwise direction with the 1st vertex
   * immediately clockwise from 12 o'clock. */
  override def y6: Double = ???

  /** The 6th Vertex. The default convention is for the vertices to be numbered in a clockwise direction with the 1st vertex immediately clockwise
   * from 12 o'clock. */
  override def v6: Vec2 = ???

  /** The X component of the 5th Vertex. The default convention is for the vertices to be numbered in a clockwise direction with the 1st vertex
   * immediately clockwise from 12 o'clock. */
  override def x5: Double = ???

  /** The Y component of the 5th Vertex. The default convention is for the vertices to be numbered in a clockwise direction with the 1st vertex
   * immediately clockwise from 12 o'clock. */
  override def y5: Double = ???

  /** The 5th Vertex. The default convention is for the vertices to be numbered in a clockwise direction with the 1st vertex immediately clockwise
   * from 12 o'clock. */
  override def v5: Vec2 = ???

  /** The X component of the 4th Vertex. The default convention is for the vertices to be numbered in a clokwise direction with the 1st vertex
   * immediately clockwise from 12 o'clock. */
  override def x4: Double = ???

  /** The Y component of the 4th Vertex. The default convention is for the vertices to be numbered in a clokwise direction with the 1st vertex
   * immediately clockwise from 12 o'clock. */
  override def y4: Double = ???

  /** The 4th Vertex. The default convention is for the vertices to be numbered in a clockwise direction with the 1st vertex immediately clockwise from
   * 12 o'clock. */
  override def v4: Vec2 = ???

  /** The X component of the 1st Vertex. The default convention is for the vertices to be numbered in a clockwise direction with the 1st vertex
   * immediately clockwise from 12 o'clock. */
  override def x1: Double = ???

  /** The Y component of the 1st Vertex. The default convention is for the vertices to be numbered in a clockwise direction with the 1st vertex
   * immediately clockwise from 12 o'clock. */
  override def y1: Double = ???

  /** The 1st Vertex. The default convention is for the vertices to be numbered in a clockwise direction with the 1st vertex immediately clockwise
   * from 12 o'clock. */
  override def v1: Vec2 = ???

  /** The X component of the 2nd Vertex. The default convention is for the vertices to be numbered in a clokwise direction with the 1st vertex
   * immediately clockwise from 12 o'clock. */
  override def x2: Double = ???

  /** The Y component of the 2nd Vertex. The default convention is for the vertices to be numbered in a clokwise direction with the 1st vertex
   * immediately clockwise from 12 o'clock. */
  override def y2: Double = ???

  /** The 2nd Vertex. The default convention is for the vertices to be numbered in a clokwise direction with the 1st vertex immediately clockwise from
   * 12 o'clock. */
  override def v2: Vec2 = ???

  /** The X component of the 3rd Vertex. The default convention is for the vertices to be numbered in a clokwise direction with the 1st vertex
   * immediately clockwise from 12 o'clock. */
  override def x3: Double = ???

  /** The Y component of the 3rd Vertex. The default convention is for the vertices to be numbered in a clokwise direction with the 1st vertex
   * immediately clockwise from 12 o'clock. */
  override def y3: Double = ???

  /** The 3rd Vertex. The default convention is for the vertices to be numbered in a clokwise direction with the 1st vertex immediately clockwise from
   * 12 o'clock. */
  override def v3: Vec2 = ???

  override def foreachVert(f: Vec2 => Unit): Unit = ???

  override def foreachVertTail[U](f: Vec2 => U): Unit = ???

  override def ptsArray: Array[Double] = ???

  override def xVertsArray: Array[Double] = ???

  override def yVertsArray: Array[Double] = ???

  override def foreachPairTail[U](f: (Double, Double) => U): Unit = ???

  /** The number of vertices and also the number of sides in this Polygon. */
  override def vertsNum: Int = ???

  /** Returns the X component of the vertex of the given number. Will throw an exception if the vertex index is out of range. */
  override def xVertGet(index: Int): Double = ???

  /** Returns the Y component of the vertex of the given number. Will throw an exception if the vertex index is out of range. */
  override def yVertGet(index: Int): Double = ???
}
