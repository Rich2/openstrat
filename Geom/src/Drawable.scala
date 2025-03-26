/* Copyright 2018-25 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package geom
import Colour.Black

import reflect.ClassTag
import scala.annotation.unchecked.uncheckedVariance

/** A 2D geometric element that can be drawn producing a [[Graphic2Elem]]. */
trait Drawable extends Any with Geom2Elem
{ /** Draws this geometric element to produce a [[GraphElem]] graphical element, that can be displayed or printed.  */
  def draw(lineWidth: Double = 2, lineColour: Colour = Black): Graphic2Elem

  /** If this element is [[Fillable]] applies the fill method, ignoring the line width parameter, else applies the draw method. */
  def fillOrDraw(lineWidth: Double = 2, colour: Colour = Black): Graphic2Elem = this match
  { case fl: Fillable => fl.fill(colour)
    case _ => draw(lineWidth, colour)
  }

  /** Translate 2D geometric transformation on this Drawable returns a Drawable. The Return type will be narrowed in sub traits. */
  override def slateXY(xDelta: Double, yDelta: Double): Drawable

  /** Uniform 2D geometric scaling transformation on this Drawable returns a Drawable. The Return type will be narrowed in sub traits / classes. */
  override def scale(operand: Double): Drawable

  /** Mirror, reflection 2D geometric transformation across the X axis by negating Y, on this Drawable returns a Drawable. The return type will be narrowed in
   * sub traits / classes. */
  override def negY: Drawable

  /** Mirror, reflection 2D geometric transformation across the Y axis by negating X, on this Drawable returns a Drawable. The return type will be narrowed in
   * sub traits / classes. */
  override def negX: Drawable

  /** 2D Transformation using a [[ProlignMatrix]] on this Drawable returns a Drawable. The return type will be narrowed in sub classes / traits. */
  override def prolign(matrix: ProlignMatrix): Drawable

  /** Rotation positive or anti-clockwise 90 degrees, 2D geometric transformation on a Drawable, returns a Drawable. The return type will be narrowed in
   * subclasses and traits. */
  override def rotate90: Drawable

  /** Rotation of 180 degrees, 2D geometric transformation on a Drawable, returns a Drawable. The return type will be narrowed in subclasses and traits. */
  override def rotate180: Drawable

  /** Rotation positive or anti-clockwise 270 degrees, 2D geometric transformation on a Drawable, returns a Drawable. The return type will be narrowed in
   * subclasses and traits. */
  override def rotate270: Drawable

  /** Rotation 2D geometric transformation, on this Drawable returns a Drawable. The return type will be narrowed in subclasses and traits. */
  override def rotate(angle: AngleVec): Drawable

  /** Reflect 2D geometric transformation across a line, line segment or ray, on this Drawable returns a Drawable. The return type will be narrowed in
   * subclasses and traits. */
  override def reflect(lineLike: LineLike): Drawable

  /** XY scaling 2D geometric transformation, on this Drawable returns a Drawable. This allows different scaling factors across X and Y dimensions. The return
   * type will be narrowed in subclasses and traits. */
  override def scaleXY(xOperand: Double, yOperand: Double): Drawable

  /** Shear 2D geometric transformation along the X Axis, on this Drawable returns a Drawable. The return type will be narrowed in subclasses and traits. */
  override def shearX(operand: Double): Drawable

  /** Shear 2D geometric transformation along the Y Axis, on this Drawable returns a Drawable. The return type will be narrowed in subclasses and traits. */
  override def shearY(operand: Double): Drawable
}

/** Companion object for the [[Drawable]] trait contains implicit instances for various 2D geometric transformation type classes. */
object Drawable
{ implicit val slateEv: Slate[Drawable] = (obj: Drawable, dx: Double, dy: Double) => obj.slateXY(dx, dy)
  implicit val scaleEv: Scale[Drawable] = (obj: Drawable, operand: Double) => obj.scale(operand)
  implicit val rotateEv: Rotate[Drawable] = (obj: Drawable, angle: AngleVec) => obj.rotate(angle)
  implicit val prolignEv: Prolign[Drawable] = (obj, matrix) => obj.prolign(matrix)
  implicit val XYScaleEv: ScaleXY[Drawable] = (obj, xOperand, yOperand) => obj.scaleXY(xOperand, yOperand)
  implicit val ReflectEv: Reflect[Drawable] = (obj, lineLike) => obj.reflect(lineLike)

  implicit val transAxesEv: TransAxes[Drawable] = new TransAxes[Drawable]
  { override def negYT(obj: Drawable): Drawable = obj.negY
    override def negXT(obj: Drawable): Drawable = obj.negX
    override def rotate90(obj: Drawable): Drawable = obj.rotate90
    override def rotate180(obj: Drawable): Drawable = obj.rotate90
    override def rotate270(obj: Drawable): Drawable = obj.rotate90
  }

  implicit val shearEv: Shear[Drawable] = new Shear[Drawable]
  { override def shearXT(obj: Drawable, yFactor: Double): Drawable = obj.shearX(yFactor)
    override def shearYT(obj: Drawable, xFactor: Double): Drawable = obj.shearY(xFactor)
  }

  implicit val drawTEv: Drawer[Drawable, Graphic2Elem] = (obj, lw, col) => obj.draw(lw, col)
}

/** A 2D geometric element that can be drawn and filled producing [[Graphic2Elem]]s. */
trait Fillable extends Any with Drawable
{ /** Returns a fill graphic of this geometric object. */
  def fill(fillfacet: FillFacet): Graphic2Elem

  /** Returns a fill graphic of this geometric object from the Int RGBA value. */
  def fillInt(intValue: Int): Graphic2Elem
  
  def fillDraw(fillColour: Colour, lineColour: Colour = Black, lineWidth: Double = 2): Graphic2Elem
}

/** Type class for drawing. */
trait Drawer[+A, +B]
{ /** The type class's draw method. */
  def drawT(obj: A @uncheckedVariance, lineWidth: Double = 2, lineColour: Colour = Black): B
}

/** Companion object for the [[Drawer]] type class. Contains implicit instances for collections and other container classes. */
object Drawer
{ /** Implicit [[Drawer]] type class instances / evidence for [[Arr]]. */
  implicit def arrEv[A, B, ArrB <: Arr[B]](implicit evA: Drawer[A, B], build: BuilderArrMap[B, ArrB]): Drawer[Arr[A], Arr[B]] =
    (obj, lw, col) => obj.map(evA.drawT(_, lw, col))

  /** Implicit [[Drawer]] type class instances / evidence for [[Functor]]. This provides instances for [[List]], [[Option]] etc. */
  implicit def functorEv[A, B, F[_]](implicit evF: Functor[F], evA: Drawer[A, B]): Drawer[F[A], F[B]] = (obj, lw, col) => evF.mapT(obj, evA.drawT(_, lw, col))

  /** Implicit [[Drawer]] type class instances / evidence for [[Array]]. */
  implicit def arrayEv[A, B](implicit ct: ClassTag[B], ev: Drawer[A, B]): Drawer[Array[A], Array[B]] = (obj, lw, col) => obj.map(ev.drawT(_, lw, col))
}

implicit class DrawerExtensions[A, B](thisDrawable: A)(implicit ev: Drawer[A, B])
{ /** Extension method to draw the object from a [[Drawer]] type class instance. */
  def draw(lineWidth: Double = 2, lineColour: Colour = Black): B = ev.drawT(thisDrawable, lineWidth, lineColour)
}

trait Filler[+A, +B]
{
  def fillT(obj: A @uncheckedVariance, fillFacet: FillFacet): B
}

/** Companion object for the [[Filler]] type class. Contains implicit instances for collections and other container classes. */
object Filler
{ /** Implicit [[Filler]] type class instances / evidence for [[Arr]]. */
  implicit def arrEv[A, B, ArrB <: Arr[B]](implicit evA: Filler[A, B], build: BuilderArrMap[B, ArrB]): Filler[Arr[A], Arr[B]] =
    (obj, ff) => obj.map(evA.fillT(_, ff))

  /** Implicit [[Filler]] type class instances / evidence for [[Functor]]. This provides instances for [[List]], [[Option]] etc. */
  implicit def functorEv[A, B, F[_]](implicit evF: Functor[F], evA: Filler[A, B]): Filler[F[A], F[B]] = (obj, ff) => evF.mapT(obj, evA.fillT(_, ff))

  /** Implicit [[Filler]] type class instances / evidence for [[Array]]. */
  implicit def arrayEv[A, B](implicit ct: ClassTag[B], ev: Filler[A, B]): Filler[Array[A], Array[B]] = (obj, ff) => obj.map(ev.fillT(_, ff))
}

trait DrawableLen2 extends GeomLen2Elem
{
  def draw(lineWidth: Double = 2, lineColour: Colour = Black):  GraphicLen2Elem

  override def slate(operand: VecPtLen2): DrawableLen2
  override def slate(xOperand: Length, yOperand: Length): DrawableLen2
  override def scale(operand: Double): DrawableLen2
}

object DrawableLen2
{ implicit val slateLen2Ev: SlateLen2[DrawableLen2] = (obj, op) => obj.slate(op)
  implicit val slateLenXY: SlateLenXY[DrawableLen2] = (obj, dx, dy) => obj.slate(dx, dy)
  implicit val scaleEv: Scale[DrawableLen2] = (obj, operand) => obj.scale(operand)
  implicit val drawTEv: Drawer[DrawableLen2, GraphicLen2Elem] = (obj, lineWidth, colour) => obj.draw(lineWidth, colour)
}

trait FillableLen2 extends DrawableLen2
{
  def fillDraw(fillColour: Colour, lineColour: Colour = Black, lineWidth: Double = 2): GraphicLen2Elem
}