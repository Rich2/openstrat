/* Copyright 2018-20 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat
package geom
import Colour.Black

/** A 2D geometric element that can be drawn producing a [[GraphicElem]]. */
trait Drawable extends GeomElem
{
  /** Draws this geometric element to produce a [[GraphElem]] graphical element, tht can be displayed or printed.  */
  def draw(lineColour: Colour = Black, lineWidth: Double = 2): GraphicElem

  /** Translate 2D geometric transformation on this Drawable returns a Drawable. The Return type will be narrowed in sub traits. */
  override def xySlate(xOffset: Double, yOffset: Double): Drawable

  /** Uniform 2D geometric scaling transformation on this Drawable returns a Drawable. The Return type will be narrowed in sub traits / classes. */
  override def scale(operand: Double): Drawable

  /** Mirror, reflection 2D geometric transformation across the X axis by negating Y, on this Drawable returns a Drawable. The return type will be
   *  narrowed in sub traits / classes. */
  override def negY: Drawable

  /** Mirror, reflection 2D geometric transformation across the Y axis by negating X, on this Drawable returns a Drawable. The return type will be
   *  narrowed in sub traits / classes. */
  override def negX: Drawable

  /** 2D Transformation using a [[ProlignMatrix]] on this Drawable returns a Drawable. The return type will be narrowed in sub classes / traits. */
  override def prolign(matrix: ProlignMatrix): Drawable

  override def rotate90: Drawable

  /** Rotation 2D geometric transformation, on this Drawable returns a Drawable. The return type will be narrowed in sub classes and traits. */
  override def rotate(angle: AngleVec): Drawable

  /** Reflect 2D geometric transformation across a line, line segment or ray, on this Drawable returns a Drawable. The return type will be narrowed in
   *  sub classes and traits. */
  override def reflect(lineLike: LineLike): Drawable

  /** XY scaling 2D geometric transformation, on this Drawable returns a Drawable. This allows different scaling factors across X and Y dimensions.
   *  The return type will be narrowed in sub classes and traits. */
  override def xyScale(xOperand: Double, yOperand: Double): Drawable

  /** Shear 2D geometric transformation along the X Axis, on this Drawable returns a Drawable. The return type will be narrowed in sub classes and
   *  traits. */
  override def xShear(operand: Double): Drawable

  /** Shear 2D geometric transformation along the Y Axis, on this Drawable returns a Drawable. The return type will be narrowed in sub classes and
   *  traits. */
  override def yShear(operand: Double): Drawable
}

object Drawable
{ implicit val slateImplicit: Slate[Drawable] = (obj: Drawable, dx: Double, dy: Double) => obj.xySlate(dx, dy)
  implicit val scaleImplicit: Scale[Drawable] = (obj: Drawable, operand: Double) => obj.scale(operand)
  implicit val rotateImplicit: Rotate[Drawable] = (obj: Drawable, angle: AngleVec) => obj.rotate(angle)
  implicit val prolignImplicit: Prolign[Drawable] = (obj, matrix) => obj.prolign(matrix)
  implicit val XYScaleImplicit: XYScale[Drawable] = (obj, xOperand, yOperand) => obj.xyScale(xOperand, yOperand)
  implicit val ReflectImplicit: Reflect[Drawable] = (obj, lineLike) => obj.reflect(lineLike)

  implicit val transAxesImplicit: ReflectAxes[Drawable] = new ReflectAxes[Drawable]
  { override def negYT(obj: Drawable): Drawable = obj.negY
    override def negXT(obj: Drawable): Drawable = obj.negX
    override def rotate90(obj: Drawable): Drawable = obj.rotate90
  }

  implicit val shearImplicit: Shear[Drawable] = new Shear[Drawable]
  { override def xShearT(obj: Drawable, yFactor: Double): Drawable = obj.xShear(yFactor)
    override def yShearT(obj: Drawable, xFactor: Double): Drawable = obj.yShear(xFactor)
  }
}

/** A 2D geometric element that can be drawn and filled producing [[GraphicElem]]s. */
trait Fillable extends Drawable
{
  def fill(fillColour: Colour): GraphicElem
  def fillHex(intValue: Int): GraphicElem
  def fillDraw(fillColour: Colour, lineColour: Colour = Black, lineWidth: Double = 2): GraphicElem
}