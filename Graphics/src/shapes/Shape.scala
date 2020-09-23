/* Copyright 2018-20 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat
package geom
import pWeb._

/** A closed shape. It has vertices and the vertices are connected by straight lines or curved lines. Shape does not extend CurvePath but it does
 *  extend [[Fillable]] which extends [[Drawable]].Not sure if Shape and Fillable should be seperate classes. */
trait Shape extends Fillable
{
  def fill(fillColour: Colour): ShapeCompound
  def fillOld(fillColour: Colour): ShapeFill
  def drawOld(lineWidth: Double, lineColour: Colour): ShapeDraw
  def attribs: Arr[XANumeric]
  
  /** This canEqual override allow the comparison of [[Shape]]s. */
  def canEqual(that: Any): Boolean = that match
  { case t: Shape => true
    case _ => false
  }
  def boundingRect: BoundingRect

  /** Translate geometric transformation on a Shape returns a Shape. */
  override def slate(offset: Vec2): Shape

  /** Translate geometric transformation. */
  override def slate(xOffset: Double, yOffset: Double): Shape

  /** Uniform scaling transformation. The scale name was chosen for this operation as it is normally the desired operation and preserves Circles and
   * Squares. Use the xyScale method for differential scaling. */
  override def scale(operand: Double): Shape

  /** Mirror, reflection transformation across the line x = xOffset, which is parallel to the X axis. */
  override def reflectYOffset(xOffset: Double): Shape

  /** Mirror, reflection transformation across the line y = yOffset, which is parallel to the X axis. */
  override def reflectXOffset(yOffset: Double): Shape

  /** Mirror, reflection transformation across the X axis. This method has been left abstract in GeomElemNew to allow the return type to be narrowed
   * in sub classes. */
  override def reflectX: Shape

  /** Mirror, reflection transformation across the X axis. This method has been left abstract in GeomElemNew to allow the return type to be narrowed
   * in sub classes. */
  override def reflectY: Shape

  override def prolign(matrix: ProlignMatrix): Shape

  override def rotateRadians(radians: Double): Shape

  override def reflect(line: Line): Shape
  override def reflect(line: LineSeg): Shape
  override def xyScale(xOperand: Double, yOperand: Double): Shape

  override def xShear(operand: Double): Shape
  override def yShear(operand: Double): Shape
}

/** Companion object for the [[Shape]] trait. Contains implicit instances of type TransElem for all the 2d geometric transformation type classes. */
object Shape
{
  implicit val slateImplicit: Slate[Shape] = (obj: Shape, offset: Vec2) => obj.slate(offset)
  implicit val scaleImplicit: Scale[Shape] = (obj: Shape, operand: Double) => obj.scale(operand)
  implicit val rotateImplicit: Rotate[Shape] = (obj: Shape, radians: Double) => obj.rotateRadians(radians)
  implicit val prolignImplicit: Prolign[Shape] = (obj, matrix) => obj.prolign(matrix)
  implicit val XYScaleImplicit: XYScale[Shape] = (obj, xOperand, yOperand) => obj.xyScale(xOperand, yOperand)
  
  /*implicit val rotateAxesImplicit: RotateAxes[Shape] = new RotateAxes[Shape]
  { override def rotateT90(obj: Shape): Shape = obj.rotate90
    override def rotateT180(obj: Shape): Shape = obj.rotate180
    override def rotateT270(obj: Shape): Shape = obj.rotate270
  }*/

  implicit val mirrorAxisImplicit: ReflectAxisOffset[Shape] = new ReflectAxisOffset[Shape]
  { override def reflectXOffsetT(obj: Shape, yOffset: Double): Shape = obj.reflectXOffset(yOffset)
    override def reflectYOffsetT(obj: Shape, xOffset: Double): Shape = obj.reflectYOffset(xOffset)
  }

  implicit val shearImplicit: Shear[Shape] = new Shear[Shape]
  { override def xShearT(obj: Shape, yFactor: Double): Shape = obj.xShear(yFactor)
    override def yShearT(obj: Shape, xFactor: Double): Shape = obj.yShear(xFactor)
  }
}