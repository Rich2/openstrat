/* Copyright 2018-25 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package geom
import Colour.Black, reflect.ClassTag, annotation.unchecked.uncheckedVariance

/** A 2D geometric element that can be drawn and filled producing [[Graphic2Elem]]s. */
trait Fillable extends Any, Drawable
{ /** Returns a fill graphic of this geometric object. */
  def fill(fillfacet: FillFacet): Graphic2Elem

  /** Returns a fill graphic of this geometric object from the Int RGBA value. */
  def fillInt(intValue: Int): Graphic2Elem

  /** Graphically fill and draws this object. */
  def fillDraw(fillColour: Colour, lineColour: Colour = Black, lineWidth: Double = 2): Graphic2Elem

  override def slate(operand: VecPt2): Fillable
  override def slate(xOperand: Double, yOperand: Double): Fillable
  override def slateX(xOperand: Double): Fillable
  override def slateY(yOperand: Double): Fillable
  override def scale(operand: Double): Fillable
  override def negY: Fillable
  override def negX: Fillable
  override def prolign(matrix: AxlignMatrix): Fillable
  override def rotate90: Fillable
  override def rotate180: Fillable
  override def rotate270: Fillable
  override def rotate(rotation: AngleVec): Fillable
  override def reflect(lineLike: LineLike): Fillable
  override def scaleXY(xOperand: Double, yOperand: Double): Fillable
  override def shearX(operand: Double): Fillable
  override def shearY(operand: Double): Fillable
}

object Fillable
{ /** [[Slate2]] type class instance / evidence for [[Fillable]]. */
  implicit val slate2Ev: Slate2[Fillable] = new Slate2[Fillable]
  { override def slate(obj: Fillable, operand: VecPt2): Fillable = obj.slate(operand)
    override def slateXY(obj: Fillable, xOperand: Double, yOperand: Double): Fillable = obj.slate(xOperand, yOperand)
    override def slateX(obj: Fillable, xOperand: Double): Fillable = obj.slateX(xOperand)
    override def slateY(obj: Fillable, yOperand: Double): Fillable = obj.slateY(yOperand)
  }

  /** [[Scale]] type class instance / evidence for [[Fillable]]. */
  given scaleEv: Scale[Fillable] = (obj: Fillable, operand: Double) => obj.scale(operand)

  /** [[Rotate]] type class instance / evidence for [[Fillable]]. */
  given rotateEv: Rotate[Fillable] = (obj: Fillable, angle: AngleVec) => obj.rotate(angle)

  /** [[SlateXY]] type class instance / evidence for [[Fillable]]. */
  given prolignEv: Prolign[Fillable] = (obj, matrix) => obj.prolign(matrix)

  /** [[ScaleXY]] type class instance / evidence for [[Fillable]]. */
  given scaleXYEv: ScaleXY[Fillable] = (obj, xOperand, yOperand) => obj.scaleXY(xOperand, yOperand)

  /** [[Reflect]] type class instance / evidence for [[Fillable]]. */
  given ReflectEv: Reflect[Fillable] = (obj, lineLike) => obj.reflect(lineLike)

  /** [[TransAxes]] type class instance / evidence for [[Fillable]]. */
  given transAxesEv: TransAxes[Fillable] = new TransAxes[Fillable]
  { override def negYT(obj: Fillable): Fillable = obj.negY
    override def negXT(obj: Fillable): Fillable = obj.negX
    override def rotate90(obj: Fillable): Fillable = obj.rotate90
    override def rotate180(obj: Fillable): Fillable = obj.rotate90
    override def rotate270(obj: Fillable): Fillable = obj.rotate90
  }

  /** [[Shear]] type class instance / evidence for [[Fillable]]. */
  given shearEv: Shear[Fillable] = new Shear[Fillable]
  { override def shearXT(obj: Fillable, yFactor: Double): Fillable = obj.shearX(yFactor)
    override def shearYT(obj: Fillable, xFactor: Double): Fillable = obj.shearY(xFactor)
  }

  /** [[Drawing]] type class instance / evidence for [[Fillable]]. */
  given drawTEv: Drawing[Fillable, Graphic2Elem] = (obj, lw, col) => obj.draw(lw, col)
}

/** Type class for creating graphical fill objects, */
trait Filling[-A, +B]
{ def fillT(obj: A, fillFacet: FillFacet): B
}

/** Companion object for the [[Filling]] type class. Contains implicit instances for collections and other container classes. */
object Filling
{ /** Implicit [[Filling]] type class instances / evidence for [[Arr]]. */
  given arrEv[A, B, ArrB <: Arr[B]](using evA: Filling[A, B], build: BuilderArrMap[B, ArrB]): Filling[Arr[A], ArrB] =
    (obj, ff) => obj.map(evA.fillT(_, ff))

  /** Implicit [[Filling]] type class instances / evidence for [[Functor]]. This provides instances for [[List]], [[Option]] etc. */
  given functorEv[A, B, F[_]](using evF: Functor[F], evA: Filling[A, B]): Filling[F[A], F[B]] = (obj, ff) => evF.mapT(obj, evA.fillT(_, ff))

  /** Implicit [[Filling]] type class instances / evidence for [[Array]]. */
  given arrayEv[A, B](using ct: ClassTag[B], ev: Filling[A, B]): Filling[Array[A], Array[B]] = (obj, ff) => obj.map(ev.fillT(_, ff))
}

extension[A, B](value: A)(using ev: Filling[A, B])
{
  def fill(fillFacet: FillFacet): B = ev.fillT(value, fillFacet)
}

/** A 2-dimensional geometric object defined in [[Length]] units that can have a fill graphic. */
trait FillableLen2 extends Any, DrawableLen2
{  /** Graphically fills this object. */
  def fill(fillFacet: FillFacet): GraphicLen2Elem

  /** Graphically fills and draws this object. */
  def fillDraw(fillFacet: FillFacet, lineColour: Colour = Black, lineWidth: Double = 2): GraphicLen2Elem

  override def slate(operand: VecPtLen2): FillableLen2
  override def slate(xOperand: Length, yOperand: Length): FillableLen2
  override def slateX(xOperand: Length): FillableLen2
  override def slateY(yOperand: Length): FillableLen2
  override def scale(operand: Double): FillableLen2
}

object FillableLen2
{ /** [[SlateLen2]] type class instance / evidence for [[FillableLen2]]. */
  given slateLen2Ev: SlateLen2[FillableLen2] = new SlateLen2[FillableLen2]
  { override def slateT(obj: FillableLen2, delta: VecPtLen2): FillableLen2 = obj.slate(delta)
    override def slateXY(obj: FillableLen2, xDelta: Length, yDelta: Length): FillableLen2 = obj.slate(xDelta, yDelta)
    override def slateX(obj: FillableLen2, xDelta: Length): FillableLen2 = obj.slateX(xDelta)
    override def slateY(obj: FillableLen2, yDelta: Length): FillableLen2 = obj.slateY(yDelta)
  }

  /** [[Scale]] type class instance / evidence for [[FillableLen2]]. */
  given scaleEv: Scale[FillableLen2] = (obj, operand) => obj.scale(operand)

  /** [[Drawing]] type class instance / evidence for [[FillableLen2]] and [[GraphicLen2Elem]]. */
  given drawTEv: Drawing[FillableLen2, GraphicLen2Elem] = (obj, lineWidth, colour) => obj.draw(lineWidth, colour)
}