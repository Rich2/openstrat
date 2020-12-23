/* Copyright 2018-20 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat
package geom
import Colour.Black, pWeb._

/** The new version of ShapeGen. Will prioritise easy and simplicity of fucntionalisty over efficency. A generalised implementation of a [[Shape]]. A
 *  closed sequence of curve segments. */
class ShapeGenNew(val unsafeArray: Array[CurveSeg]) extends Shape
{
  override def fill(fillColour: Colour): ShapeFill = ???

  override def fillHex(intValue: Int): ShapeFill = ???

  override def draw(lineColour: Colour, lineWidth: Double): ShapeDraw = ???

  override def attribs: Arr[XANumeric] = ???

  /** The centre of this Shape. */
  override def cen: Pt2 = ???

  /** Translate 2D geometric transformation on a Shape returns a Shape. The Return type will be narrowed in sub traits / classes. */
  override def slate(offset: Vec2Like): Shape = ???

  /** Translate 2D geometric transformation on a Shape returns a Shape. The Return type will be narrowed in sub traits / classes. */
  override def xySlate(xOffset: Double, yOffset: Double): Shape = ???

  /** Uniform scaling 2D geometric transformation on a Shape returns a Shape. The Return type will be narrowed in sub traits / classes. Use the
   * xyScale method for differential scaling on the X and Y axes. */
  override def scale(operand: Double): Shape = ???

  /** Mirror, reflection 2D geometric transformation across the X axis on a Shape returns a Shape. The Return type will be narrowed in sub traits /
   * classes. */
  override def negY: Shape = ???

  /** Mirror, reflection 2D geometric transformation across the X axis on a Shape returns a Shape. The Return type will be narrowed in sub traits /
   * classes. */
  override def negX: Shape = ???

  /** 2D Transformation using a [[ProlignMatrix]] on a Shape, returns a Shape. The Return type will be narrowed in sub traits / classes. */
  override def prolign(matrix: ProlignMatrix): Shape = ???

  /** Rotation 2D geometric transformation on a Shape taking the rotation as a scalar measured in radians, returns a Shape. The Return type will be
   * narrowed in sub traits / classes. */
  override def rotate(angle: AngleVec): Shape = ???

  /** Reflect 2D geometric transformation across a line, line segment or ray on a Shape, returns a Shape. The Return type will be narrowed in sub
   * traits / classes. */
  override def reflect(lineLike: LineLike): Shape = ???

  /** XY scaling 2D geometric transformation on a Shape returns a Shape. This allows different scaling factors across X and Y dimensions. The return
   * type will be narrowed in sub classes and traits. */
  override def xyScale(xOperand: Double, yOperand: Double): Shape = ???

  /** Shear 2D geometric transformation along the X Axis on a Shape, returns a Shape. The return type will be narrowed in sub classes and traits. */
  override def xShear(operand: Double): Shape = ???

  /** Shear 2D geometric transformation along the Y Axis on a Shape, returns a Shape. The return type will be narrowed in sub classes and traits. */
  override def yShear(operand: Double): Shape = ???

  /** The bounding Rectangle provides an initial exclusion test as to whether the pointer is inside the polygon / shape */
  override def boundingRect: BoundingRect = ???

  override def fillDraw(fillColour: Colour, lineColour: Colour, lineWidth: Double): GraphicElem = ???

  override def productArity: Int = ???

  override def productElement(n: Int): Any = ???
}