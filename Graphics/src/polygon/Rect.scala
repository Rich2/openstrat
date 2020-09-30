/* Copyright 2018-20 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat
package geom
import pWeb._

/** A Rectangle aligned to the X and Y axes. */
trait Rect extends Rectangle with Rectangularlign
{ @inline final override def x0: Double = xTopRight
  @inline final override def y0: Double = yTopRight
  @inline final override def v0: Vec2 = x0 vv y0
  @inline final override def x1: Double = xTopLeft
  @inline final override def y1: Double = yTopLeft
  @inline final override def v1: Vec2 = x1 vv y1
  @inline final override def cen: Vec2 = xCen vv yCen
  override def rotation: Angle = 0.degs
  @inline final def x2: Double = xBottomLeft
  @inline final def y2: Double = yBottomLeft
  @inline final def v2: Vec2 = bottomLeft
  @inline final def x3: Double = xTopLeft
  @inline final def y3: Double = yTopLeft
  @inline final def v3: Vec2 = topLeft
}

object Rect
{
  /** Implementation class for Rectanglelign, a rectangle aligned to the X and Y axes. */
  final case class RectImplement(width: Double, height: Double, xCen: Double, yCen: Double) extends Rect
  { override def fTrans(f: Vec2 => Vec2): RectImplement = ???
    override def attribs: Arr[XANumeric] = ???
    override def rotateRadians(radians: Double): Rectangle = ???
    override def reflectX: RectImplement = fTrans(_.reflectX)
    override def reflectY: RectImplement = fTrans(_.reflectY)
    override def reflectXParallel(yOffset: Double): RectImplement = fTrans(_.reflectXParallel(yOffset))
    override def reflectYParallel(xOffset: Double): RectImplement = fTrans(_.reflectYParallel(xOffset))
    //override def reflect(line: Line): Polygon = ???
    //override def reflect(line: Sline): Polygon = ???

    override def xyScale(xOperand: Double, yOperand: Double): Polygon = ???

    //override def fill(fillColour: Colour): ShapeFill = ???

    // override def draw(lineWidth: Double, lineColour: Colour): ShapeDraw = ???
  }

  /** Companion object for the Rectlign class */
  object RectImplement
  { def apply(width: Double, height: Double, cen: Vec2 = Vec2Z): RectImplement = new RectImplement(width, height, cen.x, cen.y)
  }
}