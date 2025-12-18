/* Copyright 2018-25 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package geom
import reflect.ClassTag

/** A 2D geometric element to which 2D geometric transformations can be applied. Not all elements preserve their full properties under all transformations. So
 * for example a [[Circle]] is no longer a [[Circle]] after a Shear transformation, but remains an [[Ellipse]]. [[Graphic2Elem]] inherits from GeomElem. A
 * [[Circle]] is not a [[Graphic2Elem]] but if we add a fill colour to make a [[CircleFill]], or a line width and line  colour to it, we have a [[Graphic2Elem]]
 * a graphical element that can be displayed on a canvas or output to SVG. It is expected that all elements that inherit from GeomElem that are not
 * [[Graphic2Elem]]s will be [[Drawable]] elements, but this has not been finalised. */
trait Aff2Elem extends Any, Simil2Elem
{ /** Scaling X and Y axes independently, 2D geometric transformation on this [[Aff2Elem]], returns a new [[Aff2Elem]]. This allows different scaling factors
   * across X and Y dimensions. The return type will be narrowed in subclasses and traits. This is an affine transformation, but it is not a similar
   * transformation. */
  def scaleXY(xOperand: Double, yOperand: Double): Aff2Elem

  /** Shear 2D geometric transformation along the X Axis on a GeomElem. The return type will be narrowed in subclasses and traits. This is an affine
   *  transformation, but it is not a similar transformation. */
  def shearX(operand: Double): Aff2Elem

  /** Shear 2D geometric transformation along the Y Axis on a GeomElem. The return type will be narrowed in subclasses and traits. This is an affine
   * transformation, but it is not a similar transformation. */
  def shearY(operand: Double): Aff2Elem

  override def slate(operand: VecPt2): Aff2Elem
  override def slate(xOperand: Double, yOperand: Double): Aff2Elem
  override def slateFrom(operand: VecPt2): Aff2Elem
  override def slateFrom(xOperand: Double, yOperand: Double): Aff2Elem
  override def slateX(operand: Double): Aff2Elem
  override def slateY(operand: Double): Aff2Elem
  override def scale(operand: Double): Aff2Elem
  override def negX: Aff2Elem
  override def negY: Aff2Elem
  override def rotate90: Aff2Elem
  override def rotate180: Aff2Elem
  override def rotate270: Aff2Elem
  override def prolign(matrix: AxlignMatrix): Aff2Elem
  override def rotate(rotation: AngleVec): Aff2Elem
  def mirror(lineLike: LineLike): Aff2Elem
}

/** Companion object for the [[Aff2Elem]] trait. Contains implicit instances of type GeomElem for all the 2D geometric transformation type classes. */
object Aff2Elem
{ /** Implicit [[Slate2]] type class instance / evidence for [[Aff2Elem]]. */
  given slate2Ev: Slate2[Aff2Elem] = new Slate2[Aff2Elem]
  { override def slate(obj: Aff2Elem, operand: VecPt2): Aff2Elem = obj.slate(operand)
    override def slateXY(obj: Aff2Elem, xOperand: Double, yOperand: Double): Aff2Elem = obj.slate(xOperand, yOperand)
    override def slateFrom(obj: Aff2Elem, operand: VecPt2): Aff2Elem = obj.slateFrom(operand)
    override def slateFromXY(obj: Aff2Elem, xOperand: Double, yOperand: Double): Aff2Elem = obj.slateFrom(xOperand, yOperand)
    override def slateX(obj: Aff2Elem, xOperand: Double): Aff2Elem = obj.slateX(xOperand)
    override def slateY(obj: Aff2Elem, yOperand: Double): Aff2Elem = obj.slateY(yOperand)
  }

  /** Implicit [[Scale]] type class instance / evidence for [[Aff2Elem]]. */
  given scaleEv: Scale[Aff2Elem] = (obj: Aff2Elem, operand: Double) => obj.scale(operand)

  /** Implicit [[Rotate]] type class instance / evidence for [[Aff2Elem]]. */
  given rotateEv: Rotate[Aff2Elem] = (obj: Aff2Elem, angle: AngleVec) => obj.rotate(angle)

  /** Implicit [[Prolign]] type class instance / evidence for [[Aff2Elem]]. */
  given prolignEv: Prolign[Aff2Elem] = (obj, matrix) => obj.prolign(matrix)

  /** Implicit [[ScaleXY]] type class instance / evidence for [[Aff2Elem]]. */
  given scaleXYEv: ScaleXY[Aff2Elem] = (obj, xOperand, yOperand) => obj.scaleXY(xOperand, yOperand)

  /** Implicit [[Mirror]] type class instance / evidence for [[Aff2Elem]]. */
  given MirrorEv: Mirror[Aff2Elem] = (obj, lineLike) => obj.mirror(lineLike)

  /** Implicit [[TransAxes]] type class instance / evidence for [[Aff2Elem]]. */
  given transAxesEv: TransAxes[Aff2Elem] = new TransAxes[Aff2Elem]
  { override def negYT(obj: Aff2Elem): Aff2Elem = obj.negY
    override def negXT(obj: Aff2Elem): Aff2Elem = obj.negX
    override def rotate90(obj: Aff2Elem): Aff2Elem = obj.rotate90
    override def rotate180(obj: Aff2Elem): Aff2Elem = obj.rotate180
    override def rotate270(obj: Aff2Elem): Aff2Elem = obj.rotate270
  }

  /** Implicit [[Shear]] type class instance / evidence for [[Aff2Elem]]. */
  given shearEv: Shear[Aff2Elem] = new Shear[Aff2Elem]
  { override def shearXT(obj: Aff2Elem, yFactor: Double): Aff2Elem = obj.shearX(yFactor)
    override def shearYT(obj: Aff2Elem, xFactor: Double): Aff2Elem = obj.shearY(xFactor)
  }
}

/** The type class trait for transforming an object in 2d geometry. Note overrides necessary to preserve type. */
trait Aff2Trans[T] extends Similar2Trans[T]
{ def trans(obj: T, f: Pt2 => Pt2):  T
  override def slate(obj: T, offset: VecPt2): T = trans(obj, _.slate(offset))
  override def scale(obj: T, operand: Double): T = trans(obj, _.scale(operand))
  def shear(obj: T, xScale: Double, yScale: Double): T = trans(obj, v => Pt2(v.x * yScale, v.y * xScale))
  override def rotate(obj: T, angle: AngleVec): T = trans(obj, _.rotate(angle))
  override def reflectT(obj: T, line: LineLike): T = trans(obj, _.mirror(line))
}

/** The companion object for the Trans[T] type class, containing instances for common classes. */
object Aff2Trans
{
  given arrEv[A, AA <: Arr[A]](using build: BuilderArrMap[A, AA], ev: Aff2Trans[A]): Aff2Trans[AA] = (obj, f) => obj.map(el => ev.trans(el, f))

  given fromTranserAllEv[T <: AffinePreserve]: Aff2Trans[T] = (obj, f) => obj.ptsTrans(f).asInstanceOf[T]

  given functorEv[A, F[_]](using evF: Functor[F], evA: Aff2Trans[A]): Aff2Trans[F[A]] = (obj, f) => evF.mapT(obj, el => evA.trans(el, f))

  given arrayEv[A](using ct: ClassTag[A], ev: Aff2Trans[A]): Aff2Trans[Array[A]] = (obj, f) => obj.map(el => ev.trans(el, f))
}