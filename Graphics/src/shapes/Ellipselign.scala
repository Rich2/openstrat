/* Copyright 2018-21 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package geom

/** An ellipse whose axes are aligned to the X and Y axes. This is a trait as both [[Circle]] and [[Ellipselign.EllipselignImp]] classes implement
 *  this interface. */
trait Ellipselign extends Ellipse
{
  /** The radius of the axis of the ellipse aligned to the X axis. */
  def xRadius: Double

  /** The radius of the axis of the ellipse aligned to the Y axis. */
  def yRadius: Double

  /** The alignment angle of axis 1 in an [[Ellipselign]] including the special case of [[Circle]] is by definition 0°. */
  override final def alignAngle: Angle = Angle(0)

  override def slateXY(xDelta: Double, yDelta: Double): Ellipselign = Ellipselign(xRadius, yRadius, cenX + xDelta, cenY + yDelta)

  override def scale(operand: Double): Ellipselign = Ellipselign(xRadius * operand, yRadius * operand, cenX * operand, cenY * operand)

  override def negX: Ellipselign = Ellipselign(xRadius, yRadius, -cenX, cenY)

  override def negY: Ellipselign = Ellipselign(xRadius, yRadius, cenX, -cenY)

  override def rotate90: Ellipselign = ???
  override def rotate180: Ellipselign = ???
  override def rotate270: Ellipselign = ???
}

object Ellipselign
{
  def apply(xRadius: Double, yRadius: Double, cen: Pt2 = Pt2Z): Ellipselign = new EllipselignImp(xRadius, yRadius, cen.x, cen.y)
  def apply(xRadius: Double, yRadius: Double, xCen: Double, yCen: Double): Ellipselign = new EllipselignImp(xRadius, yRadius, xCen, yCen)

  class EllipselignImp(val xRadius: Double, val yRadius: Double, val cenX: Double, val cenY: Double) extends Ellipselign
  {
    override def rMajor: Double = ???
    override def rMinor: Double = ???

    /** The h value of this ellipse. */
    override def h: Double = ???




    /** Eccentricity of ellipse. */
    override def e: Double = ???

    override def area: Double = ???

    override def boundingRect: BoundingRect = ???

    /** Radius 1 of the ellipse. By default this is the horizontal axis of the ellipse. This can be the major or minor axis. */
    override def radius1: Double = ???

    /** Radius 2 of the ellipse. By default this is the vertical axis of the ellipse. This can be the major or minor axis. */
    override def radius2: Double = ???

    /** The X component of the end point of axis 1. By default this is on the right of the Ellipse. Mathematically this can be referred to as a vertex
     * for the major axis or a co-vertex for the minor axis. */
    override def axesPt1x: Double = ???

    /** The Y component of the end point of axis 1. By default this is on the right of the Ellipse. Mathematically this can be referred to as a vertex
     * for the major axis or a co-vertex for the minor axis. */
    override def axesPt1y: Double = ???

    /** The X component of the start point of axis 2. By default this is at the bottom of the Ellipse. Mathematically this can be referred to as a vertex for the major
     * axis or a co-vertex for the minor axis.. */
    override def axesPt2x: Double = ???

    /** The y component of the start point of axis 2. By default this is at the bottom of the Ellipse. Mathematically this can be referred to as a
     * vertex for the major axis or a co-vertex for the minor axis. */
    override def axesPt2y: Double = ???

    override def axesPt3x: Double = ???

    override def axesPt3y: Double = ???

    /** The end point of axis 2. By default this is at the top of the Ellipse. Mathematically this can be referred to as a vertex for the major axis or
     * a co-vertex for the minor axis. */
    override def axesPt4: Pt2 = ???

    /** The X component of the end point of axis 2. By default this is at the top of the Ellipse. Mathematically this can be referred to as a vertex for the major axis or
     * a co-vertex for the minor axis. */
    override def axesPt4x: Double = ???

    /** The Y component of the end point of axis 2. By default this is at the top of the Ellipse. Mathematically this can be referred to as a vertex for the major axis or
     * a co-vertex for the minor axis. */
    override def axesPt4y: Double = ???

    /** The 2D vector [[Vec2]] from the centre point to pAxes1, the end point of axis 1 , by default on the right of the Ellipse this arc is based
     * on. */
    override def cenP1: Vec2 = ???

    /** The 2D vector [[Vec2]] from the centre point to pAxes2, the start point of axis 2, by default at the bottom of the Ellipse this arc is based
     * on. */
    override def cenP2: Vec2 = ???

    /** The 2D vector [[Vec2]] from the centre point to pAxes3, the start point of axis 1 , by default on the left of the Ellipse this arc is based
     * on. */
    override def cenP3: Vec2 = ???

    /** The 2D vector [[Vec2]] from the centre point to pAxes4, the end point of axis 2, by default at the top of the Ellipse this arc is based on. */
    override def cenP4: Vec2 = ???

    //override def productArity: Int = ???

    //override def productElement(n: Int): Any = ???
  }
}