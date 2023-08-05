/* Copyright 2018-23 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package geom
import Colour.Black

/** A 2D geometric element that can be drawn producing a [[GraphicElem]]. */
trait Drawable extends Any with GeomElem
{ /** Draws this geometric element to produce a [[GraphElem]] graphical element, that can be displayed or printed.  */
  def draw(lineWidth: Double = 2, lineColour: Colour = Black): GraphicElem

  /** If this element is [[Fillable]] applies the fill method, ignoring the line width parameter, else applies the draws method. */
  def fillOrDraw(lineWidth: Double = 2, colour: Colour = Black): GraphicElem = this match
  { case fl: Fillable => fl.fill(colour)
    case _ => draw(lineWidth, colour)
  }

  /** Translate 2D geometric transformation on this Drawable returns a Drawable. The Return type will be narrowed in sub traits. */
  override def slateXY(xDelta: Double, yDelta: Double): Drawable

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

  /** Rotation positive or anti clockwise 90 degrees, 2D geometric transformation on a Drawable, returns a Drawable. The return type will be narrowed
   *  in sub classes and traits. */
  override def rotate90: Drawable

  /** Rotation of 180 degrees, 2D geometric transformation on a Drawable, returns a Drawable. The return type will be narrowed in sub classes and
   *  traits. */
  override def rotate180: Drawable

  /** Rotation positive or anti clockwise 270 degrees, 2D geometric transformation on a Drawable, returns a Drawable. The return type will be narrowed
   *  in sub classes and traits. */
  override def rotate270: Drawable

  /** Rotation 2D geometric transformation, on this Drawable returns a Drawable. The return type will be narrowed in sub classes and traits. */
  override def rotate(angle: AngleVec): Drawable

  /** Reflect 2D geometric transformation across a line, line segment or ray, on this Drawable returns a Drawable. The return type will be narrowed in
   *  sub classes and traits. */
  override def reflect(lineLike: LineLike): Drawable

  /** XY scaling 2D geometric transformation, on this Drawable returns a Drawable. This allows different scaling factors across X and Y dimensions.
   *  The return type will be narrowed in sub classes and traits. */
  override def scaleXY(xOperand: Double, yOperand: Double): Drawable

  /** Shear 2D geometric transformation along the X Axis, on this Drawable returns a Drawable. The return type will be narrowed in sub classes and
   *  traits. */
  override def shearX(operand: Double): Drawable

  /** Shear 2D geometric transformation along the Y Axis, on this Drawable returns a Drawable. The return type will be narrowed in sub classes and
   *  traits. */
  override def shearY(operand: Double): Drawable
}

/** Companion object for the Drawable trait contains implicit instances for various 2D geometric transformation type classes. */
object Drawable
{ implicit val slateImplicit: Slate[Drawable] = (obj: Drawable, dx: Double, dy: Double) => obj.slateXY(dx, dy)
  implicit val scaleImplicit: Scale[Drawable] = (obj: Drawable, operand: Double) => obj.scale(operand)
  implicit val rotateImplicit: Rotate[Drawable] = (obj: Drawable, angle: AngleVec) => obj.rotate(angle)
  implicit val prolignImplicit: Prolign[Drawable] = (obj, matrix) => obj.prolign(matrix)
  implicit val XYScaleImplicit: ScaleXY[Drawable] = (obj, xOperand, yOperand) => obj.scaleXY(xOperand, yOperand)
  implicit val ReflectImplicit: Reflect[Drawable] = (obj, lineLike) => obj.reflect(lineLike)

  implicit val transAxesImplicit: TransAxes[Drawable] = new TransAxes[Drawable]
  { override def negYT(obj: Drawable): Drawable = obj.negY
    override def negXT(obj: Drawable): Drawable = obj.negX
    override def rotate90(obj: Drawable): Drawable = obj.rotate90
    override def rotate180(obj: Drawable): Drawable = obj.rotate90
    override def rotate270(obj: Drawable): Drawable = obj.rotate90
  }

  implicit val shearImplicit: Shear[Drawable] = new Shear[Drawable]
  { override def shearXT(obj: Drawable, yFactor: Double): Drawable = obj.shearX(yFactor)
    override def shearYT(obj: Drawable, xFactor: Double): Drawable = obj.shearY(xFactor)
  }
}

/** A 2D geometric element that can be drawn and filled producing [[GraphicElem]]s. */
trait Fillable extends Any with Drawable
{ /** Returns a fill graphic of this geometric object. */
  def fill(fillColour: Colour): GraphicElem

  /** Returns a fill graphic of this geometric object from the Int RGBA value. */
  def fillInt(intValue: Int): GraphicElem

  /** Returns a fill and draw graphic of this geometric object. */
  def fillDraw(fillColour: Colour, lineColour: Colour = Black, lineWidth: Double = 2): GraphicElem
}