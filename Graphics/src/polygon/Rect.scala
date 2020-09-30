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

  /** Translate geometric transformation on a Rect returns a Rect. */
  override def slate(offset: Vec2): Rect = Rect(width, height, cen + offset)

  /** Translate geometric transformation on a Rect returns a Rect. */
  override def slate(xOffset: Double, yOffset: Double): Rectangle = Rect(width, height, xCen + xOffset, yCen + yOffset)

  /** Uniform scaling transformation on a Rect returns a Rect. */
  override def scale(operand: Double): Rectangle = Rect(width * operand, height * operand, cen * operand)

  /** Mirror, reflection transformation across the X axis on a Rect, returns a Rect. */
  override def reflectX: Rect = Rect(width, height, cen.reflectX)

  /** Mirror, reflection transformation across the X axis on a Rect, returns a Rect. */
  override def reflectY: Rectangle = Rect(width, height, cen.reflectY)
}

/** Companion object for the [[Rect]] trait contains factory methods for the Rect trait which delegate to the [[RectImp]] class. */
object Rect
{
  def apply(width: Double, height: Double, cen: Vec2 = Vec2Z): Rect = new RectImp(width, height, cen.x, cen.y)
  def apply(width: Double, height: Double, xCen: Double, yCen: Double): Rect = new RectImp(width, height, xCen, yCen)
  
  /** Implementation class for Rect, a rectangle aligned to the X and Y axes. */
  final case class RectImp(width: Double, height: Double, xCen: Double, yCen: Double) extends Rect
  { override def fTrans(f: Vec2 => Vec2): RectImp = ???
    override def attribs: Arr[XANumeric] = ???
    
    /** Translate geometric transformation on a RectImp returns a RectImp. */
    override def slate(offset: Vec2): RectImp = RectImp(width, height, cen + offset)

    /** Translate geometric transformation on a RectImp returns a RectImp. */
    override def slate(xOffset: Double, yOffset: Double): RectImp = RectImp(width, height, xCen + xOffset, yCen + yOffset)

    /** Uniform scaling transformation on a RectImp returns a RectImp. */
    override def scale(operand: Double): RectImp = RectImp(width * operand, height * operand, cen * operand)
    
    /** Mirror, reflection transformation across the X axis on a Rect, returns a Rect. */
    override def reflectX: RectImp = RectImp(width, height, cen.reflectX)

    /** Mirror, reflection transformation across the X axis on a Rect, returns a Rect. */
    override def reflectY: Rectangle = Rect(width, height, cen.reflectY)
    
    override def reflectXParallel(yOffset: Double): RectImp = fTrans(_.reflectXParallel(yOffset))
    override def reflectYParallel(xOffset: Double): RectImp = fTrans(_.reflectYParallel(xOffset))
    //override def reflect(line: Line): Polygon = ???
    //override def reflect(line: Sline): Polygon = ???

    override def xyScale(xOperand: Double, yOperand: Double): Polygon = ???

    //override def fill(fillColour: Colour): ShapeFill = ???

    // override def draw(lineWidth: Double, lineColour: Colour): ShapeDraw = ???
  }

  /** Companion object for the Rectlign class */
  object RectImp
  { def apply(width: Double, height: Double, cen: Vec2 = Vec2Z): RectImp = new RectImp(width, height, cen.x, cen.y)
  }
}