/* Copyright 2018-20 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat
package geom

/** Regular Hexagon where two of the sides are parallel to the X Axis */
class Hexlign(val dMin: Double, val xCen: Double, val yCen: Double) extends HexReg
{
  override def x1: Double = rMax / 2
  override def y1: Double = rMin
  override def v1: Vec2 = Vec2(rMax / 2, rMin)

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
  override def v6: Vec2 = Vec2(-rMax / 2, rMin)

  override def vert(index: Int): Vec2 = index match
  { case 1 => v1
    case 2 => v2
    case 3 => v3
    case 4 => v4
    case 5 => v5
    case 6 => v6
    case n => excep("$n is out of range for a Hexagon vertex")
  }
  
  override def s4Cen: Vec2 = ???
  override def s1Cen: Vec2 = ???


  override def ptsArray: Array[Double] = ???

  override def xVertsArray: Array[Double] = ???

  override def yVertsArray: Array[Double] = ???

  override def foreachPairTail[U](f: (Double, Double) => U): Unit = ???

    /** Returns the X component of the vertex of the given number. Will throw an exception if the vertex index is out of range. */
  override def xVert(index: Int): Double = ???

  /** Returns the Y component of the vertex of the given number. Will throw an exception if the vertex index is out of range. */
  override def yVert(index: Int): Double = ???

  override def productArity: Int = ???

  override def productElement(n: Int): Any = ???
}

object Hexlign
{
  def apply(height: Double, cen: Vec2 = Vec2Z): Hexlign = new Hexlign(height, cen.x, cen.y)
}