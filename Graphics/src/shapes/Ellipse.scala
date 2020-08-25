/* Copyright 2018-20 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat
package geom

trait Ellipse extends Shape with ProlignPreserve
{ type ThisT <: Ellipse
  def xCen: Double
  def yCen: Double
  final def cen: Vec2 = xCen vv yCen
  def x1: Double
  def y1: Double
  final def v1: Vec2 = x1 vv y1
  def x2: Double
  def y2: Double
  def v2: Vec2 = x2 vv y2
  def x3: Double
  def y3: Double
  def v3: Vec2 = x3 vv y3
  def majorRadius: Double
  def minorRadius: Double

  override def rotateRadians(radians: Double): Ellipse 
  /* override def canEqual(that: Any): Boolean = that match
  { case e: Ellipse => true
    case _ => false
  }*/
  /*override def slate(offset: Vec2): Ellipse

  override def slate(xOffset: Double, yOffset: Double): Ellipse*/
  
  override def scaleXY(xOperand: Double, yOperand: Double): Ellipse = ???

  //override def mirrorX: Ellipse
  def fill(fillColour: Colour): EllipseGraphic = EllipseGraphic(this, Arr(FillColour(fillColour)), Arr())
}

object Ellipse
{ /** The apply factory methods default to an EllipseClass. */
  def apply(xCen: Double, yCen: Double, x1: Double, y1: Double, x3: Double, y3: Double): EllipseGen =
    new EllipseGen(xCen, yCen, x1, y1, x3, y3)

  /** The apply factory methods default to an EllipseClass. */
  def apply(cen: Vec2, v1: Vec2, v3: Vec2): EllipseGen = new EllipseGen(cen.x, cen.y, v1.x, v1.y, v3.x,  v3.y)
  
  implicit def slateImplicit: Slate[Ellipse] = (ell, offset) => Ellipse(ell.cen + offset, ell.v1 + offset, ell.v3 + offset)

  
}
