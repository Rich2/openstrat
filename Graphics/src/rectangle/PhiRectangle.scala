/* Copyright 2018-20 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat
package geom

trait PhiRectangle extends Rectangle
{
  def width2: Double = width1 * Phi
}

object PhiRectangle
{
  case class PhiRectangleImp(xS1Cen: Double, yS1Cen: Double, xS3Cen: Double, yS3Cen: Double) extends PhiRectangle
  {
    override def xCen: Double = (xS1Cen + xS1Cen) / 2
    override def yCen: Double = (yS1Cen + yS1Cen) / 2
    override def s1Cen: Vec2 = Vec2(xS1Cen, yS1Cen)
    override def s3Cen: Vec2 = Vec2(xS3Cen, yS3Cen)
    override def rotation: Angle = (s1Cen - s3Cen).angle - Deg90
    override def v1: Vec2 = ???
    override def x1: Double = ???

    override def y1: Double = ???


    /** length from v1 to v2 and v3 to v4. */
    override def width1: Double = ???


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



    /** The X component of the 2nd Vertex. The default convention is for the vertices to be numbered in a clockwise direction with the 1st vertex
     * immediately clockwise from 12 o'clock. */
    override def x2: Double = ???

    /** The Y component of the 2nd Vertex. The default convention is for the vertices to be numbered in a clockwise direction with the 1st vertex
     * immediately clockwise from 12 o'clock. */
    override def y2: Double = ???

    /** The 2nd Vertex. The default convention is for the vertices to be numbered in a clockwise direction with the 1st vertex immediately clockwise
     * from 12 o'clock. */
    override def v2: Vec2 = ???

    /** The X component of the 3rd Vertex. The default convention is for the vertices to be numbered in a clockwise direction with the 1st vertex
     * immediately clockwise from 12 o'clock. */
    override def x3: Double = ???

    /** The Y component of the 3rd Vertex. The default convention is for the vertices to be numbered in a clockwise direction with the 1st vertex
     * immediately clockwise from 12 o'clock. */
    override def y3: Double = ???

    /** The 3rd Vertex. The default convention is for the vertices to be numbered in a clockwise direction with the 1st vertex immediately clockwise from
     * 12 o'clock. */
    override def v3: Vec2 = ???

    /** The centre or half way point of side 2 of this polygon. Side 2 starts at the v1 vertex and ends at the v2 vertex. */
    override def s2Cen: Vec2 = ???
  }
}

case class PhiRect(xCen: Double, yCen: Double, height: Double) extends Rect with PhiRectangle
{
  override def width: Double = height * Phi

  override def slateTo(newCen: Vec2): PhiRect = ???
}

object PhiRect
{
  case class PhiRectImp(xCen: Double, yCen: Double, xLs3Cen: Double, yLs3Cen: Double)
  { def ls3Cen: Vec2 = Vec2(xLs3Cen, yLs3Cen)
  }
}