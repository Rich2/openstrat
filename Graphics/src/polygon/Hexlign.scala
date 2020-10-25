/* Copyright 2018-20 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat
package geom

/** Regular Hexagan where two of the sides are parallel to the X Axis */
case class Hexlign(dMax: Double, xCen: Double, yCen: Double) extends HexReg
{
  override def x1: Double = rMax / 2
  override def y1: Double = rMin
  override def v1: Vec2 = Vec2(rMax / 2, rMax)
  override def x2: Double = rMax
  override def y2: Double = 0
  override def v2: Vec2 = Vec2(rMax, 0)
  override def x3: Double = rMax / 2
  override def y3: Double = -rMin
  override def v3: Vec2 = Vec2(rMax / 2, -rMin)
  override def x4: Double = -rMax / 2
  override def y4: Double = -rMin
  override def v4: Vec2 = Vec2(-rMax / 2, -rMin)
  override def x5: Double = -rMax
  override def y5: Double = 0
  override def v5: Vec2 = Vec2(-rMax, 0)
  override def x6: Double = -rMax / 2
  override def y6: Double = rMin
  override def v6: Vec2 = Vec2(-rMax /2, rMin)

  override def apply(index: Int): Vec2 = ???
  
  override def s4Cen: Vec2 = ???
  override def s1Cen: Vec2 = ???

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