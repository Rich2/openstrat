/* Copyright 2018-20 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat
package geom
import pWeb._

/** A closed shape. It has vertices and the vertices are connected by straight lines or curved lines. Shape does not extend CurvePath but it does
 *  extend [[Fillable]] which extends [[Drawable]].Not sure if Shape and Fillable should be seperate classes. */
trait Shape extends Fillable
{
  //def fill(fillColour: Colour): ShapeCompound
  override def fill(fillColour: Colour): ShapeFill
  def draw(lineWidth: Double, lineColour: Colour): ShapeDraw
  def attribs: Arr[XANumeric]
  
  /** This canEqual override allow the comparison of [[Shape]]s. */
  def canEqual(that: Any): Boolean = that match
  { case t: Shape => true
    case _ => false
  }
  def boundingRect: BoundingRect

  def cen: Vec2
  
  /** Translate 2D geometric transformation on a Shape returns a Shape. The Return type will be narrowed in sub traits / classes. */
  override def slate(offset: Vec2): Shape

  /** Translate 2D geometric transformation on a Shape returns a Shape. The Return type will be narrowed in sub traits / classes. */
  override def slate(xOffset: Double, yOffset: Double): Shape

  /** Uniform scaling 2D geometric transformation on a Shape returns a Shape. The Return type will be narrowed in sub traits / classes. Use the
   *  xyScale method for differential scaling on the X and Y axes. */
  override def scale(operand: Double): Shape

  /** Mirror, reflection 2D geometric transformation across the X axis on a Shape returns a Shape. The Return type will be narrowed in sub traits /
   *  classes. */
  override def negY: Shape

  /** Mirror, reflection 2D geometric transformation across the X axis on a Shape returns a Shape. The Return type will be narrowed in sub traits /
   *  classes. */
  override def negX: Shape

  /** Rotate 90 degrees anti clockwise or rotate 270 degrees clockwise 2D geometric transformation on a Shape, returns a Shape. The return type will
   *  be narrowed in sub traits / classes. */
  override def rotate90: Shape

  /** Rotate 180 degrees 2D geometric transformation on a Shape, returns a Shape. The return type will be narrowed in sub traits / classes. */
  override def rotate180: Shape

  /** Rotate 270 degrees anti clockwise or rotate 90 degrees clockwise 2D geometric transformation on a Shape, returns a Shape. The return type will
   *  be narrowed in sub traits / classes. */
  override def rotate270: Shape

  /** 2D Transformation using a [[ProlignMatrix]] on a Shape, returns a Shape. The Return type will be narrowed in sub traits / classes. */
  override def prolign(matrix: ProlignMatrix): Shape

  /** Rotation 2D geometric transformation on a Shape taking the rotation as a scalar measured in radians, returns a Shape. The Return type will be
   *  narrowed in sub traits / classes. */
  override def rotateRadians(radians: Double): Shape

  /** Reflect 2D geometric transformation across a line, line segment or ray on a Shape, returns a Shape. The Return type will be narrowed in sub
   *  traits / classes. */
  override def reflect(lineLike: LineLike): Shape

  /** XY scaling 2D geometric transformation on a Shape returns a Shape. This allows different scaling factors across X and Y dimensions. The return
   *  type will be narrowed in sub classes and traits. */
  override def xyScale(xOperand: Double, yOperand: Double): Shape

  /** Shear 2D geometric transformation along the X Axis on a Shape, returns a Shape. The return type will be narrowed in sub classes and traits. */
  override def xShear(operand: Double): Shape

  /** Shear 2D geometric transformation along the Y Axis on a Shape, returns a Shape. The return type will be narrowed in sub classes and traits. */
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

  implicit val reflectAxesImplicit: ReflectAxes[Shape] = new ReflectAxes[Shape]
  { override def negYT(obj: Shape): Shape = obj.negY
    override def negXT(obj: Shape): Shape = obj.negX
  }

  implicit val shearImplicit: Shear[Shape] = new Shear[Shape]
  { override def xShearT(obj: Shape, yFactor: Double): Shape = obj.xShear(yFactor)
    override def yShearT(obj: Shape, xFactor: Double): Shape = obj.yShear(xFactor)
  }
}