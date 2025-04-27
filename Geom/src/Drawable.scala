/* Copyright 2018-25 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package geom
import Colour.Black, reflect.ClassTag, annotation.unchecked.uncheckedVariance

/** A 2D geometric element that can be drawn producing a [[Graphic2Elem]]. */
trait Drawable extends Any, Aff2Elem
{ /** Draws this geometric element to produce a [[GraphElem]] graphical element, that can be displayed or printed.  */
  def draw(lineWidth: Double = 2, lineColour: Colour = Black): Graphic2Elem

  /** If this element is [[Fillable]] applies the fill method, ignoring the line width parameter, else applies the draw method. */
  def fillOrDraw(lineWidth: Double = 2, colour: Colour = Black): Graphic2Elem = this match
  { case fl: Fillable => fl.fill(colour)
    case _ => draw(lineWidth, colour)
  }

  override def slate(operand: VecPt2): Drawable
  override def slate(xOperand: Double, yOperand: Double): Drawable
  override def scale(operand: Double): Drawable
  override def negY: Drawable
  override def negX: Drawable
  override def prolign(matrix: AxlignMatrix): Drawable
  override def rotate90: Drawable
  override def rotate180: Drawable
  override def rotate270: Drawable
  override def rotate(rotation: AngleVec): Drawable
  override def reflect(lineLike: LineLike): Drawable
  override def scaleXY(xOperand: Double, yOperand: Double): Drawable
  override def shearX(operand: Double): Drawable
  override def shearY(operand: Double): Drawable
}

/** Companion object for the [[Drawable]] trait contains implicit instances for various 2D geometric transformation type classes. */
object Drawable
{ /** [[Slate2]] type class instance / evidence for [[Drawable]]. */
  implicit val slateEv: Slate2[Drawable] = (obj: Drawable, operand: VecPt2) => obj.slate(operand)

  /** [[SlateXY]] type class instance / evidence for [[Drawable]]. */
  implicit val slateXYEv: SlateXY[Drawable] = (obj: Drawable, dx: Double, dy: Double) => obj.slate(dx, dy)

  /** [[Scale]] type class instance / evidence for [[Drawable]]. */
  implicit val scaleEv: Scale[Drawable] = (obj: Drawable, operand: Double) => obj.scale(operand)

  /** [[Rotate]] type class instance / evidence for [[Drawable]]. */
  implicit val rotateEv: Rotate[Drawable] = (obj: Drawable, angle: AngleVec) => obj.rotate(angle)

  /** [[SlateXY]] type class instance / evidence for [[Drawable]]. */
  implicit val prolignEv: Prolign[Drawable] = (obj, matrix) => obj.prolign(matrix)

  /** [[ScaleXY]] type class instance / evidence for [[Drawable]]. */
  implicit val scaleXYEv: ScaleXY[Drawable] = (obj, xOperand, yOperand) => obj.scaleXY(xOperand, yOperand)

  /** [[Reflect]] type class instance / evidence for [[Drawable]]. */
  implicit val ReflectEv: Reflect[Drawable] = (obj, lineLike) => obj.reflect(lineLike)

  /** [[TransAxes]] type class instance / evidence for [[Drawable]]. */
  implicit val transAxesEv: TransAxes[Drawable] = new TransAxes[Drawable]
  { override def negYT(obj: Drawable): Drawable = obj.negY
    override def negXT(obj: Drawable): Drawable = obj.negX
    override def rotate90(obj: Drawable): Drawable = obj.rotate90
    override def rotate180(obj: Drawable): Drawable = obj.rotate90
    override def rotate270(obj: Drawable): Drawable = obj.rotate90
  }
  
  /** [[Shear]] type class instance / evidence for [[Drawable]]. */
  implicit val shearEv: Shear[Drawable] = new Shear[Drawable]
  { override def shearXT(obj: Drawable, yFactor: Double): Drawable = obj.shearX(yFactor)
    override def shearYT(obj: Drawable, xFactor: Double): Drawable = obj.shearY(xFactor)
  }
  
  /** [[Drawing]] type class instance / evidence for [[Drawable]]. */
  implicit val drawTEv: Drawing[Drawable, Graphic2Elem] = (obj, lw, col) => obj.draw(lw, col)
}