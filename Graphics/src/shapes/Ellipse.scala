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
  
  /** radius 1. This will normally be the value of a, the major ellipse radius, but even if it starts as a in certain transformations it may become b,
   *  the minor ellipse radius. */
  def r1: Double

  /** radius 2. This will normally be the value of b, the minor ellipse radius, but even if it starts as b in certain transformations it may become a,
   *  the major ellipse radius. */
  def r2: Double
  
  /** The major radius of this ellipse. */
  def a: Double

  /** The major radius of this ellipse. */
  def b: Double
  
  /** The h value of this ellipse. */
  def h: Double
  
  def ellipeRotation: Angle
  
  /** Eccentricity of ellipse. */
  def e: Double
  
  def area: Double
  def cxAttrib: XANumeric = XANumeric("cx", xCen)
  def cyAttrib: XANumeric = XANumeric("cy", yCen)
  override def rotateRadians(radians: Double): Ellipse
  def rxAttrib: XANumeric = XANumeric("rx", r1)
  def ryAttrib: XANumeric = XANumeric("ry", r2)
  def shapeAttribs: Arr[XANumeric] = Arr(cxAttrib, cyAttrib, rxAttrib, ryAttrib)
  def boundingRect: BoundingRect
      
  override def scaleXY(xOperand: Double, yOperand: Double): Ellipse = ???

  //override def mirrorX: Ellipse
  def fill(fillColour: Colour): EllipseGraphic = EllipseGraphic(this, Arr(FillColour(fillColour)), Arr())
}

object Ellipse
{ /** Factory method for an [[Ellipse]. The apply factory methods in this Ellipse companion object default to an [[EllipseGen]] class. */
  def apply(radiusA: Double, radiusB: Double): EllipseGen = new EllipseGen(0, 0, radiusA, 0, 0, radiusB)

  /** Factory method for an [[Ellipse]]. The apply factory methods in this Ellipse companion object default to an [[EllipseGen]] class. */
  def apply(radiusA: Double, radiusB: Double, cen: Vec2): EllipseGen = new EllipseGen(cen.x, cen.y, cen.x + radiusA, cen.y, cen.x, cen.y + radiusB)

  /** The apply factory methods default to an EllipseClass. */
  def apply(radiusA: Double, radiusB: Double, xCen: Double, yCen: Double): EllipseGen =
    new EllipseGen(xCen, yCen, xCen + radiusA, yCen, xCen, yCen + radiusB)
  
  implicit def slateImplicit: Slate[Ellipse] = (ell, offset) => EllipseGen(ell.cen + offset, ell.v1 + offset, ell.v3 + offset)  
}