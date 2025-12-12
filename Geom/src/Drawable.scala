/* Copyright 2018-25 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package geom
import annotation.unchecked.uncheckedVariance, reflect.ClassTag, Colour.Black

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
  override def slateX(xOperand: Double): Drawable
  override def slateY(yOperand: Double): Drawable
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
  given slate2Ev: Slate2[Drawable] = new Slate2[Drawable]
  { override def slate(obj: Drawable, operand: VecPt2): Drawable = obj.slate(operand)
    override def slateXY(obj: Drawable, xOperand: Double, yOperand: Double): Drawable = obj.slate(xOperand, yOperand)
    override def slateX(obj: Drawable, xOperand: Double): Drawable = obj.slateX(xOperand)
    override def slateY(obj: Drawable, yOperand: Double): Drawable = obj.slateY(yOperand)
  }

  /** [[Scale]] type class instance / evidence for [[Drawable]]. */
  given scaleEv: Scale[Drawable] = (obj: Drawable, operand: Double) => obj.scale(operand)

  /** [[Rotate]] type class instance / evidence for [[Drawable]]. */
  given rotateEv: Rotate[Drawable] = (obj: Drawable, angle: AngleVec) => obj.rotate(angle)

  /** [[SlateXY]] type class instance / evidence for [[Drawable]]. */
  given prolignEv: Prolign[Drawable] = (obj, matrix) => obj.prolign(matrix)

  /** [[ScaleXY]] type class instance / evidence for [[Drawable]]. */
  given scaleXYEv: ScaleXY[Drawable] = (obj, xOperand, yOperand) => obj.scaleXY(xOperand, yOperand)

  /** [[Reflect]] type class instance / evidence for [[Drawable]]. */
  given ReflectEv: Reflect[Drawable] = (obj, lineLike) => obj.reflect(lineLike)

  /** [[TransAxes]] type class instance / evidence for [[Drawable]]. */
  given transAxesEv: TransAxes[Drawable] = new TransAxes[Drawable]
  { override def negYT(obj: Drawable): Drawable = obj.negY
    override def negXT(obj: Drawable): Drawable = obj.negX
    override def rotate90(obj: Drawable): Drawable = obj.rotate90
    override def rotate180(obj: Drawable): Drawable = obj.rotate90
    override def rotate270(obj: Drawable): Drawable = obj.rotate90
  }
  
  /** [[Shear]] type class instance / evidence for [[Drawable]]. */
  given shearEv: Shear[Drawable] = new Shear[Drawable]
  { override def shearXT(obj: Drawable, yFactor: Double): Drawable = obj.shearX(yFactor)
    override def shearYT(obj: Drawable, xFactor: Double): Drawable = obj.shearY(xFactor)
  }
  
  /** [[Drawing]] type class instance / evidence for [[Drawable]]. */
  given drawTEv: Drawing[Drawable, Graphic2Elem] = (obj, lw, col) => obj.draw(lw, col)
}

/** Type class for drawing. */
trait Drawing[A, B]
{ /** The type class's draw method. */
  def drawT(obj: A /* @uncheckedVariance */, lineWidth: Double = 2, lineColour: Colour = Black): B
}

/** Companion object for the [[Drawing]] type class. Contains implicit instances for collections and other container classes. */
object Drawing
{ /** Implicit [[Drawing]] type class instances / evidence for [[Arr]]. */
  given arrEv[A, B, ArrB <: Arr[B]](using evA: Drawing[A, B], build: BuilderArrMap[B, ArrB]): Drawing[Arr[A], Arr[B]] =
    (obj, lw, col) => obj.map(evA.drawT(_, lw, col))

  /** Implicit [[Drawing]] type class instances / evidence for [[Functor]]. This provides instances for [[List]], [[Option]] etc. */
  given functorEv[F[_], A, B](using evF: Functor[F], evA: Drawing[A, B]): Drawing[F[A], F[B]] = (obj, lw, col) => evF.mapT(obj, evA.drawT(_, lw, col))

  /** Implicit [[Drawing]] type class instances / evidence for [[Array]]. */
  given arrayEv[A, B](using ct: ClassTag[B], ev: Drawing[A, B]): Drawing[Array[A], Array[B]] = (obj, lw, col) => obj.map(ev.drawT(_, lw, col))
}

extension[A, B](thisDrawable: A)(using ev: Drawing[A, B])
{ /** Extension method to draw the object from a [[Drawing]] type class instance. */
  def draw(lineWidth: Double = 2, lineColour: Colour = Black): B = ev.drawT(thisDrawable, lineWidth, lineColour)
}

/** A 2-dimensional geometric object defined in [[Length]] units that can have a fill graphic. */
trait DrawableLen2 extends Any, GeomLen2Elem
{ /** Draws the object. The line width is defined in pixels. */
  def draw(lineWidth: Double = 2, lineColour: Colour = Black):  GraphicLen2Elem

  override def slate(operand: VecPtLen2): DrawableLen2
  override def slate(xOperand: Length, yOperand: Length): DrawableLen2
  override def slateX(xOperand: Length): DrawableLen2
  override def slateY(yOperand: Length): DrawableLen2
  override def scale(operand: Double): DrawableLen2
}

object DrawableLen2
{ /** [[SlateLen2]] type class instance / evidence for [[DrawableLen2]]. */
  implicit val slateLen2Ev: SlateLen2[DrawableLen2] = new SlateLen2[DrawableLen2]
  { override def slateT(obj: DrawableLen2, delta: VecPtLen2): DrawableLen2 = obj.slate(delta)
    override def slateXY(obj: DrawableLen2, xDelta: Length, yDelta: Length): DrawableLen2 = obj.slate(xDelta, yDelta)
    override def slateX(obj: DrawableLen2, xDelta: Length): DrawableLen2 = obj.slateX(xDelta)
    override def slateY(obj: DrawableLen2, yDelta: Length): DrawableLen2 = obj.slateY(yDelta)
  }

  /** [[Scale]] type class instance / evidence for [[DrawableLen2]]. */
  implicit val scaleEv: Scale[DrawableLen2] = (obj, operand) => obj.scale(operand)

  /** [[Drawing]] type class instance / evidence for [[DrawableLen2]] and [[GraphicLen2Elem]]. */
  implicit val drawTEv: Drawing[DrawableLen2, GraphicLen2Elem] = (obj, lineWidth, colour) => obj.draw(lineWidth, colour)
}