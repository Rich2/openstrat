/* Copyright 2018-20 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat
package geom
import pWeb._

/** The Ellipse trait can either be implemented as an [[EllipseGen]] class or as a [[Circle]]. Which also fulfills the Ellipse interface. */
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
  def radiusA: Double
  def radiusB: Double
  def majorRadius: Double
  def minorRadius: Double
  def ellipeRotation: Angle
  def cxAttrib: XANumeric = XANumeric("cx", xCen)
  def cyAttrib: XANumeric = XANumeric("cy", yCen)
  override def rotateRadians(radians: Double): Ellipse
  def rxAttrib: XANumeric = XANumeric("rx", radiusA)
  def ryAttrib: XANumeric = XANumeric("ry", radiusB)
  def shapeAttribs: Arr[XANumeric] = Arr(cxAttrib, cyAttrib, rxAttrib, ryAttrib)
  def boundingRect: BoundingRect
      
  override def scaleXY(xOperand: Double, yOperand: Double): Ellipse = ???

  //override def mirrorX: Ellipse
  def fill(fillColour: Colour): EllipseGraphic = EllipseGraphic(this, Arr(FillColour(fillColour)), Arr())
}

object Ellipse
{ /** The apply factory methods default to an EllipseClass. */
  def apply(radiusA: Double, radiusB: Double): EllipseGen = new EllipseGen(0, 0, radiusA, 0, 0, radiusB)

  /** The apply factory methods default to an EllipseClass. */
  def apply(radiusA: Double, radiusB: Double, cen: Vec2): EllipseGen = new EllipseGen(cen.x, cen.y, cen.x + radiusA, cen.y, cen.x, cen.y + radiusB)

  /** The apply factory methods default to an EllipseClass. */
  def apply(radiusA: Double, radiusB: Double, xCen: Double, yCen: Double): EllipseGen =
    new EllipseGen(xCen, yCen, xCen + radiusA, yCen, xCen, yCen + radiusB)
  
  implicit def slateImplicit: Slate[Ellipse] = (ell, offset) => EllipseGen(ell.cen + offset, ell.v1 + offset, ell.v3 + offset)  
}