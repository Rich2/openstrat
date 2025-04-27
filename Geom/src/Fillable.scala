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
  }

  /** [[Scale]] type class instance / evidence for [[Fillable]]. */
  implicit val scaleEv: Scale[Fillable] = (obj: Fillable, operand: Double) => obj.scale(operand)

  /** [[Rotate]] type class instance / evidence for [[Fillable]]. */
  implicit val rotateEv: Rotate[Fillable] = (obj: Fillable, angle: AngleVec) => obj.rotate(angle)

  /** [[SlateXY]] type class instance / evidence for [[Fillable]]. */
  implicit val prolignEv: Prolign[Fillable] = (obj, matrix) => obj.prolign(matrix)

  /** [[ScaleXY]] type class instance / evidence for [[Fillable]]. */
  implicit val scaleXYEv: ScaleXY[Fillable] = (obj, xOperand, yOperand) => obj.scaleXY(xOperand, yOperand)

  /** [[Reflect]] type class instance / evidence for [[Fillable]]. */
  implicit val ReflectEv: Reflect[Fillable] = (obj, lineLike) => obj.reflect(lineLike)

  /** [[TransAxes]] type class instance / evidence for [[Fillable]]. */
  implicit val transAxesEv: TransAxes[Fillable] = new TransAxes[Fillable]
  { override def negYT(obj: Fillable): Fillable = obj.negY
    override def negXT(obj: Fillable): Fillable = obj.negX
    override def rotate90(obj: Fillable): Fillable = obj.rotate90
    override def rotate180(obj: Fillable): Fillable = obj.rotate90
    override def rotate270(obj: Fillable): Fillable = obj.rotate90
  }

  /** [[Shear]] type class instance / evidence for [[Fillable]]. */
  implicit val shearEv: Shear[Fillable] = new Shear[Fillable]
  { override def shearXT(obj: Fillable, yFactor: Double): Fillable = obj.shearX(yFactor)
    override def shearYT(obj: Fillable, xFactor: Double): Fillable = obj.shearY(xFactor)
  }

  /** [[Drawing]] type class instance / evidence for [[Fillable]]. */
  implicit val drawTEv: Drawing[Fillable, Graphic2Elem] = (obj, lw, col) => obj.draw(lw, col)
}

/** Type class for drawing. */
trait Drawing[+A, +B]
{ /** The type class's draw method. */
  def drawT(obj: A @uncheckedVariance, lineWidth: Double = 2, lineColour: Colour = Black): B
}

/** Companion object for the [[Drawing]] type class. Contains implicit instances for collections and other container classes. */
object Drawing
{ /** Implicit [[Drawing]] type class instances / evidence for [[Arr]]. */
  implicit def arrEv[A, B, ArrB <: Arr[B]](implicit evA: Drawing[A, B], build: BuilderArrMap[B, ArrB]): Drawing[Arr[A], Arr[B]] =
    (obj, lw, col) => obj.map(evA.drawT(_, lw, col))

  /** Implicit [[Drawing]] type class instances / evidence for [[Functor]]. This provides instances for [[List]], [[Option]] etc. */
  implicit def functorEv[A, B, F[_]](implicit evF: Functor[F], evA: Drawing[A, B]): Drawing[F[A], F[B]] = (obj, lw, col) => evF.mapT(obj, evA.drawT(_, lw, col))

  /** Implicit [[Drawing]] type class instances / evidence for [[Array]]. */
  implicit def arrayEv[A, B](implicit ct: ClassTag[B], ev: Drawing[A, B]): Drawing[Array[A], Array[B]] = (obj, lw, col) => obj.map(ev.drawT(_, lw, col))
}

implicit class DrawerExtensions[A, B](thisDrawable: A)(implicit ev: Drawing[A, B])
{ /** Extension method to draw the object from a [[Drawing]] type class instance. */
  def draw(lineWidth: Double = 2, lineColour: Colour = Black): B = ev.drawT(thisDrawable, lineWidth, lineColour)
}

/** Type class for creating graphical fill objects, */
trait Filling[+A, +B]
{ def fillT(obj: A @uncheckedVariance, fillFacet: FillFacet): B
}

/** Companion object for the [[Filling]] type class. Contains implicit instances for collections and other container classes. */
object Filling
{ /** Implicit [[Filling]] type class instances / evidence for [[Arr]]. */
  implicit def arrEv[A, B, ArrB <: Arr[B]](implicit evA: Filling[A, B], build: BuilderArrMap[B, ArrB]): Filling[Arr[A], Arr[B]] =
    (obj, ff) => obj.map(evA.fillT(_, ff))

  /** Implicit [[Filling]] type class instances / evidence for [[Functor]]. This provides instances for [[List]], [[Option]] etc. */
  implicit def functorEv[A, B, F[_]](implicit evF: Functor[F], evA: Filling[A, B]): Filling[F[A], F[B]] = (obj, ff) => evF.mapT(obj, evA.fillT(_, ff))

  /** Implicit [[Filling]] type class instances / evidence for [[Array]]. */
  implicit def arrayEv[A, B](implicit ct: ClassTag[B], ev: Filling[A, B]): Filling[Array[A], Array[B]] = (obj, ff) => obj.map(ev.fillT(_, ff))
}

/** A 2-dimensional geometric object defined in [[Length]] units that can have a fill graphic. */
trait DrawableLen2 extends Any, GeomLen2Elem
{ /** Draws the object. The line width is defined in pixels. */
  def draw(lineWidth: Double = 2, lineColour: Colour = Black):  GraphicLen2Elem

  override def slate(operand: VecPtLen2): DrawableLen2
  override def slate(xOperand: Length, yOperand: Length): DrawableLen2
  override def scale(operand: Double): DrawableLen2
}

object DrawableLen2
{ /** [[SlateLen2]] type class instance / evidence for [[DrawableLen2]]. */
  implicit val slateLen2Ev: SlateLen2[DrawableLen2] = new SlateLen2[DrawableLen2]
  { override def slateT(obj: DrawableLen2, delta: VecPtLen2): DrawableLen2 = obj.slate(delta)
    override def slateT(obj: DrawableLen2, xDelta: Length, yDelta: Length): DrawableLen2 = obj.slate(xDelta, yDelta)
  }

  /** [[Scale]] type class instance / evidence for [[DrawableLen2]]. */
  implicit val scaleEv: Scale[DrawableLen2] = (obj, operand) => obj.scale(operand)

  /** [[Drawing]] type class instance / evidence for [[DrawableLen2]] and [[GraphicLen2Elem]]. */
  implicit val drawTEv: Drawing[DrawableLen2, GraphicLen2Elem] = (obj, lineWidth, colour) => obj.draw(lineWidth, colour)
}

/** A 2-dimensional geometric object defined in [[Length]] units that can have a fill graphic. */
trait FillableLen2 extends DrawableLen2
{  /** Graphically fills this object. */
  def fill(fillFacet: FillFacet): GraphicLen2Elem

  /** Graphically fills and draws this object. */
  def fillDraw(fillFacet: FillFacet, lineColour: Colour = Black, lineWidth: Double = 2): GraphicLen2Elem

  override def slate(operand: VecPtLen2): FillableLen2
  override def slate(xOperand: Length, yOperand: Length): FillableLen2
  override def scale(operand: Double): FillableLen2
}

object FillableLen2
{ /** [[SlateLen2]] type class instance / evidence for [[FillableLen2]]. */
  implicit val slateLen2Ev: SlateLen2[FillableLen2] = new SlateLen2[FillableLen2]
  { override def slateT(obj: FillableLen2, delta: VecPtLen2): FillableLen2 = obj.slate(delta)
    override def slateT(obj: FillableLen2, xDelta: Length, yDelta: Length): FillableLen2 = obj.slate(xDelta, yDelta)
  }

  /** [[Scale]] type class instance / evidence for [[FillableLen2]]. */
  implicit val scaleEv: Scale[FillableLen2] = (obj, operand) => obj.scale(operand)

  /** [[Drawing]] type class instance / evidence for [[FillableLen2]] and [[GraphicLen2Elem]]. */
  implicit val drawTEv: Drawing[FillableLen2, GraphicLen2Elem] = (obj, lineWidth, colour) => obj.draw(lineWidth, colour)
}