/* Copyright 2018-20 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat
package geom
import math._

/** The implementation class for Ellipses that are not Circles. The Ellipse is encoded as 3 Vec2s or 6 scalars although it is possible to encode an
 * ellipse with 5 scalars. Encoding the Ellipse this way greatly helps human visualisation of transformations upon an ellipse. */
case class EllipseGen(xCen: Double, yCen: Double, x1: Double, y1: Double, x3: Double, y3: Double) extends Ellipse with AffinePreserve
{ override type ThisT = EllipseGen
  def x2: Double = 2 * xCen - x1
  def y2: Double = 2 * yCen - y1
  override def r1: Double = (v1 - cen).magnitude
  def r2: Double = (v3 - cen).magnitude
  def a: Double = r1.max(r2)
  def b: Double = r1.min(r2)
  override def area: Double = Pi * r1 * r2
  override def e: Double = sqrt(a.squared - b.squared) / a
  override def h: Double = (a - b).squared / (a + b).squared
  override def fTrans(f: Vec2 => Vec2): EllipseGen = EllipseGen(f(cen), f(v1), f(v3))
  override def fillOld(fillColour: Colour): EllipseFill = EllipseFill(this, fillColour)
  override def fill(fillColour: Colour): EllipseGenGraphic = EllipseGenGraphic(this, Arr(FillColour(fillColour)), Arr())
  override def drawOld(lineWidth: Double, lineColour: Colour): ShapeDraw = ???
  override def fillDrawOld(fillColour: Colour, lineWidth: Double, lineColour: Colour): ShapeFillDraw = ???  
  
  def boundingRect: BoundingRect =
  { val xd0: Double = r1.squared * (ellipeRotation.cos).squared + r2.squared * (ellipeRotation.sin).squared
    val xd = xd0.sqrt
    val yd0: Double = r1.squared * (ellipeRotation.sin).squared + r2.squared * (ellipeRotation.cos).squared
    val yd = yd0.sqrt
    BoundingRect(xCen - xd, xCen + xd, yCen - yd, yCen + yd)
  }
  
  override def ellipeRotation: Angle = (v1 - cen).angle
}

/** Companion object for the EllipseClass. Contains various factory methods for the creation of ellipses from different starting points. */
object EllipseGen
{ def apply(vLeft: Vec2, vRight: Vec2, vUp: Vec2): EllipseGen = new EllipseGen(vLeft.x, vLeft.y, vRight.x, vRight.y, vUp.x, vUp.y)
  def cenV1V3(cen: Vec2, v1: Vec2, v3: Vec2): EllipseGen = new EllipseGen(cen.x, cen.y, v1.x, v1.y, v3.x, v3.y)
}