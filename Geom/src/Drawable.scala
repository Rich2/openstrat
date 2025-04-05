/* Copyright 2018-25 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package geom
import Colour.Black, reflect.ClassTag, annotation.unchecked.uncheckedVariance

/** A 2D geometric element that can be drawn producing a [[Graphic2Elem]]. */
trait Drawable extends Any with Geom2Elem
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
  override def prolign(matrix: ProlignMatrix): Drawable
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
{ /** [[Slate]] type class instance / evidence for [[Drawable]]. */
  implicit val slateEv: Slate[Drawable] = (obj: Drawable, operand: VecPt2) => obj.slate(operand)

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

/** A 2D geometric element that can be drawn and filled producing [[Graphic2Elem]]s. */
trait Fillable extends Any with Drawable
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
  override def prolign(matrix: ProlignMatrix): Fillable
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
{ /** [[Slate]] type class instance / evidence for [[Fillable]]. */
  implicit val slateEv: Slate[Fillable] = (obj: Fillable, operand: VecPt2) => obj.slate(operand)

  /** [[SlateXY]] type class instance / evidence for [[Fillable]]. */
  implicit val slateXYEv: SlateXY[Fillable] = (obj: Fillable, dx: Double, dy: Double) => obj.slate(dx, dy)

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
  implicit val slateLen2Ev: SlateLen2[DrawableLen2] = (obj, op) => obj.slate(op)
  
  /** [[SlateLenXY]] type class instance / evidence for [[DrawableLen2]]. */
  implicit val slateLenXY: SlateLenXY[DrawableLen2] = (obj, dx, dy) => obj.slate(dx, dy)
  
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
  implicit val slateLen2Ev: SlateLen2[FillableLen2] = (obj, op) => obj.slate(op)

  /** [[SlateLenXY]] type class instance / evidence for [[FillableLen2]]. */
  implicit val slateLenXY: SlateLenXY[FillableLen2] = (obj, dx, dy) => obj.slate(dx, dy)

  /** [[Scale]] type class instance / evidence for [[FillableLen2]]. */
  implicit val scaleEv: Scale[FillableLen2] = (obj, operand) => obj.scale(operand)

  /** [[Drawing]] type class instance / evidence for [[FillableLen2]] and [[GraphicLen2Elem]]. */
  implicit val drawTEv: Drawing[FillableLen2, GraphicLen2Elem] = (obj, lineWidth, colour) => obj.draw(lineWidth, colour)
}