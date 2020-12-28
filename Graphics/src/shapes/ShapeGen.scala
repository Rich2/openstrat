/* Copyright 2018-20 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat
package geom
import Colour.Black, pWeb._

/** The new version of ShapeGen. Will prioritise easy and simplicity of functionality over efficiency. A generalised implementation of a [[Shape]]. A
 *  closed sequence of curve segments. */
class ShapeGen(val unsafeArray: Array[CurveSeg]) extends Shape with AxisFree
{
  override type ThisT = ShapeGen
  override def fill(fillColour: Colour): ShapeFill = ???

  override def fillHex(intValue: Int): ShapeFill = ???

  override def draw(lineColour: Colour = Black, lineWidth: Double = 2): ShapeDraw = ???

  override def attribs: Arr[XANumeric] = ???

  /** Translate 2D geometric transformation on a ShapeGen returns a Shape. The Return type will be narrowed in sub traits / classes. */
  override def xySlate(xOffset: Double, yOffset: Double): ShapeGen = new ShapeGen(unsafeArray.xySlate(xOffset, yOffset))

  /** Uniform scaling 2D geometric transformation on a ShapeGen returns a Shape. The Return type will be narrowed in sub traits / classes. Use the
   * xyScale method for differential scaling on the X and Y axes. */
  override def scale(operand: Double): ShapeGen = new ShapeGen(unsafeArray.scale(operand))

  /** 2D Transformation using a [[ProlignMatrix]] on a Shape, returns a Shape. The Return type will be narrowed in sub traits / classes. */
  override def prolign(matrix: ProlignMatrix): ShapeGen = ???

  /** Rotation 2D geometric transformation on a ShapeGen taking the rotation as a scalar measured in radians, returns a Shape. The Return type will be
   * narrowed in sub traits / classes. */
  override def rotate(angle: AngleVec): ShapeGen = ???

  /** Reflect 2D geometric transformation across a line, line segment or ray on a Shape, returns a Shape. The Return type will be narrowed in sub
   * traits / classes. */
  override def reflect(lineLike: LineLike): ShapeGen = ???

  /** XY scaling 2D geometric transformation on a ShapeGen returns a Shape. This allows different scaling factors across X and Y dimensions. The return
   * type will be narrowed in sub classes and traits. */
  override def xyScale(xOperand: Double, yOperand: Double): ShapeGen = ???

  /** Shear 2D geometric transformation along the X Axis on a Shape, returns a Shape. The return type will be narrowed in sub classes and traits. */
  override def xShear(operand: Double): ShapeGen = ???

  /** Shear 2D geometric transformation along the Y Axis on a Shape, returns a Shape. The return type will be narrowed in sub classes and traits. */
  override def yShear(operand: Double): ShapeGen = ???

  /** The bounding Rectangle provides an initial exclusion test as to whether the pointer is inside the polygon / shape */
  override def boundingRect: BoundingRect = ???

  override def fillDraw(fillColour: Colour, lineColour: Colour, lineWidth: Double): GraphicElem = ???

  override def productArity: Int = ???

  override def productElement(n: Int): Any = ???
}

object ShapeGen
{
  implicit val slateImplicit: Slate[ShapeGen ] = (obj: ShapeGen , dx: Double, dy: Double) => obj.xySlate(dx, dy)
  implicit val scaleImplicit: Scale[ShapeGen ] = (obj: ShapeGen , operand: Double) => obj.scale(operand)
  implicit val rotateImplicit: Rotate[ShapeGen ] = (obj: ShapeGen , angle: AngleVec) => obj.rotate(angle)
  implicit val prolignImplicit: Prolign[ShapeGen ] = (obj, matrix) => obj.prolign(matrix)
  implicit val XYScaleImplicit: XYScale[ShapeGen ] = (obj, xOperand, yOperand) => obj.xyScale(xOperand, yOperand)
  implicit val ReflectImplicit: Reflect[ShapeGen ] = (obj, lineLike) => obj.reflect(lineLike)

  implicit val transAxesImplicit: ReflectAxes[ShapeGen ] = new ReflectAxes[ShapeGen ]
  { override def negYT(obj: ShapeGen ): ShapeGen  = obj.negY
    override def negXT(obj: ShapeGen ): ShapeGen  = obj.negX
  }

  implicit val shearImplicit: Shear[ShapeGen ] = new Shear[ShapeGen ]
  { override def xShearT(obj: ShapeGen , yFactor: Double): ShapeGen  = obj.xShear(yFactor)
    override def yShearT(obj: ShapeGen , xFactor: Double): ShapeGen  = obj.yShear(xFactor)
  }
}