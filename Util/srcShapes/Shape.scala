/* Copyright 2018-21 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package geom
import pWeb._, Colour.Black

/** A closed shape. It has vertices and the vertices are connected by straight lines or curved lines. Shape does not extend CurvePath but it does
 *  extend [[Fillable]] which extends [[Drawable]].Not sure if Shape and Fillable should be seperate classes. */
trait Shape extends Fillable with BoundedElem
{
  override def fill(fillColour: Colour): ShapeFill
  override def fillInt(intValue: Int): ShapeFill
  def draw(lineColour: Colour = Black, lineWidth: Double = 2): ShapeDraw
  def attribs: RArr[XAttNumeric]
  
  /** This canEqual override allow the comparison of [[Shape]]s. */
  def canEqual(that: Any): Boolean = that match
  { case t: Shape => true
    case _ => false
  }

  /** Translate 2D geometric transformation on a Shape returns a Shape. The Return type will be narrowed in sub traits / classes. */
  override def slateXY(xDelta: Double, yDelta: Double): Shape

  /** Uniform scaling 2D geometric transformation on a Shape returns a Shape. The Return type will be narrowed in sub traits / classes. Use the
   *  xyScale method for differential scaling on the X and Y axes. */
  override def scale(operand: Double): Shape

  /** Mirror, reflection 2D geometric transformation across the X axis on a Shape returns a Shape. The Return type will be narrowed in sub traits /
   *  classes. */
  override def negY: Shape

  /** Mirror, reflection 2D geometric transformation across the X axis on a Shape returns a Shape. The Return type will be narrowed in sub traits /
   *  classes. */
  override def negX: Shape

  /** 2D Transformation using a [[ProlignMatrix]] on a Shape, returns a Shape. The Return type will be narrowed in sub traits / classes. */
  override def prolign(matrix: ProlignMatrix): Shape

  override def rotate90: Shape
  override def rotate180: Shape
  override def rotate270: Shape

  /** Rotation 2D geometric transformation on a Shape taking the rotation as a scalar measured in radians, returns a Shape. The Return type will be
   *  narrowed in sub traits / classes. */
  override def rotate(angle: AngleVec): Shape

  /** Reflect 2D geometric transformation across a line, line segment or ray on a Shape, returns a Shape. The Return type will be narrowed in sub
   *  traits / classes. */
  override def reflect(lineLike: LineLike): Shape

  /** XY scaling 2D geometric transformation on a Shape returns a Shape. This allows different scaling factors across X and Y dimensions. The return
   *  type will be narrowed in sub classes and traits. */
  override def scaleXY(xOperand: Double, yOperand: Double): Shape

  /** Shear 2D geometric transformation along the X Axis on a Shape, returns a Shape. The return type will be narrowed in sub classes and traits. */
  override def shearX(operand: Double): Shape

  /** Shear 2D geometric transformation along the Y Axis on a Shape, returns a Shape. The return type will be narrowed in sub classes and traits. */
  override def shearY(operand: Double): Shape
}

/** Companion object for the [[Shape]] trait. Contains implicit instances of type TransElem for all the 2d geometric transformation type classes. */
object Shape
{
  implicit val slateImplicit: Slate[Shape] = (obj: Shape, dx: Double, dy: Double) => obj.slateXY(dx, dy)
  implicit val scaleImplicit: Scale[Shape] = (obj: Shape, operand: Double) => obj.scale(operand)
  implicit val rotateImplicit: Rotate[Shape] = (obj: Shape, angle: AngleVec) => obj.rotate(angle)
  implicit val prolignImplicit: Prolign[Shape] = (obj, matrix) => obj.prolign(matrix)
  implicit val XYScaleImplicit: ScaleXY[Shape] = (obj, xOperand, yOperand) => obj.scaleXY(xOperand, yOperand)

  implicit val reflectAxesImplicit: TransAxes[Shape] = new TransAxes[Shape]
  { override def negYT(obj: Shape): Shape = obj.negY
    override def negXT(obj: Shape): Shape = obj.negX
    override def rotate90(obj: Shape): Shape = obj.rotate90
    override def rotate180(obj: Shape): Shape = obj.rotate180
    override def rotate270(obj: Shape): Shape = obj.rotate270
  }

  implicit val shearImplicit: Shear[Shape] = new Shear[Shape]
  { override def shearXT(obj: Shape, yFactor: Double): Shape = obj.shearX(yFactor)
    override def shearYT(obj: Shape, xFactor: Double): Shape = obj.shearY(xFactor)
  }
}