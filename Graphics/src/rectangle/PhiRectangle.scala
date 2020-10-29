/* Copyright 2018-20 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat
package geom

trait PhiRectangle extends Rectangle
{
  def width1: Double = width2 * Phi
}

object PhiRectangle
{
  case class PhiRectangleImp(xS1Cen: Double, yS1Cen: Double, xS3Cen: Double, yS3Cen: Double) extends PhiRectangle
  {
    override def xCen: Double = (xS1Cen + xS1Cen) / 2
    override def yCen: Double = (yS1Cen + yS1Cen) / 2
    override def s1Cen: Vec2 = Vec2(xS1Cen, yS1Cen)
    override def s3Cen: Vec2 = Vec2(xS3Cen, yS3Cen)
    override def width2: Double = (s1Cen - s3Cen).magnitude
    override def rotation: Angle = (s1Cen - s3Cen).angle - Deg90
    override def v1: Vec2 = s1Cen + Vec2(width2 / 2, 0).rotate(rotation)
    override def x1: Double = v1.x
    override def y1: Double = v1.y
    override def v2: Vec2 = s3Cen + Vec2(width2 / 2, 0).rotate(rotation)
    override def x2: Double = v2.x
    override def y2: Double = v2.y
    override def v3: Vec2 = s3Cen - Vec2(width2 / 2, 0).rotate(rotation)
    override def x3: Double = v2.x
    override def y3: Double = v2.y

    /** The X component of the 4th Vertex. The default convention is for the vertices to be numbered in a clockwise direction with the 1st vertex
     * immediately clockwise from 12 o'clock. */
    override def x4: Double = ???

    /** The Y component of the 4th Vertex. The default convention is for the vertices to be numbered in a clockwise direction with the 1st vertex
     * immediately clockwise from 12 o'clock. */
    override def y4: Double = ???

    /** The 4th Vertex. The default convention is for the vertices to be numbered in a clockwise direction with the 1st vertex immediately clockwise from
     * 12 o'clock. */
    override def v4: Vec2 = ???

    /** The centre or half way point of side 4 of this polygon. Side 4 starts at the v3 vertex and ends at the v4 vertex. */
    override def s4Cen: Vec2 = ???

    /** The centre or half way point of side 2 of this polygon. Side 2 starts at the v1 vertex and ends at the v2 vertex. */
    override def s2Cen: Vec2 = ???
  }
}

case class PhiRect(xCen: Double, yCen: Double, height: Double) extends Rect with PhiRectangle
{
  override def width: Double = width1
  override def width2: Double = height

  override def slateTo(newCen: Vec2): PhiRect = ???
}

object PhiRect
{
  case class PhiRectImp(xCen: Double, yCen: Double, xLs3Cen: Double, yLs3Cen: Double)
  { def ls3Cen: Vec2 = Vec2(xLs3Cen, yLs3Cen)
  }
}