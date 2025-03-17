/* Copyright 2018-25 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package geom
import reflect.ClassTag, annotation.unchecked.uncheckedVariance

/** Type class for translate 2-dimensional vector transformations. Each transformation method has been given its own Type class and associated extension class.
 * Different sets of transformations can then be combined. */
trait Slate[T]
{
  /** Translate 2D geometric transformation, taking the xOffset and yOffset as parameters, on an object of type T, returning an object of type T. For many types
   * the implementation of this method will delegate to the object itself. */
  def slateXYT(obj: T, xDelta: Double, yDelta: Double): T

  /** Translate 2D geometric transformation, taking a [[Pt2]] or [[Vec2]] as a parameter, on an object of type T, returning an object of type T. */
  def slateT(obj: T, delta: VecPt2): T = slateXYT(obj, delta.x, delta.y)

  /** Translate 2D geometric transformation along the X axis, on an object of type T, returning an object of type T. */
  def SlateXT(obj: T @uncheckedVariance, xOffset: Double): T = slateXYT(obj, xOffset, 0)

  /** Translate 2D geometric transformation along the Y axis, on an object of type T, returning an object of type T. */
  def SlateYT(obj: T @uncheckedVariance, yOffset: Double): T = slateXYT(obj, 0, yOffset)
}

/** Companion object for the Slate type class. Contains implicit instances for collections and other container classes. */
object Slate
{
  implicit def transSimerImplicit[T <: SimilarPreserve]: Slate[T] = (obj: T, dx: Double, dy: Double) => obj.slateXY(dx, dy).asInstanceOf[T]

  implicit def arrImplicit[A](implicit ev: Slate[A]): Slate[RArr[A]] = (obj, dx, dy) => obj.smap(ev.slateXYT(_, dx, dy))

  /** Implicit [[Slate]] instance / evidence for [[Functor]]. This provides instances for List, Option etc. */
  implicit def functorImplicit[A, F[_]](implicit evF: Functor[F], evA: Slate[A]): Slate[F[A]] = (obj, dx, dy) => evF.mapT(obj, evA.slateXYT(_, dx, dy))

  /** Implicit [[Slate]] instance / evidence for [[Array]]. */
  implicit def arrayImplicit[A](implicit ct: ClassTag[A], ev: Slate[A]): Slate[Array[A]] = (obj, dx, dy) => obj.map(ev.slateXYT(_, dx, dy))
}

/** Extension class for instances of the Slate type class. */
class SlateExtensions[T](value: T, ev: Slate[T])
{ /** Translate 2D geometric transformation extension method, along the X axis, on this object of type T, returning an object of Type T. */
  def slateX(xOffset: Double): T = ev.slateXYT(value, xOffset, 0)

  /** Translate 2D geometric transformation extension method, along the Y axis, on this object of type T, returning an object of Type T. */
  def slateY(yOffset: Double): T = ev.slateXYT(value, 0, yOffset)

  /** Translate 2D geometric transformation extension method, taking the X offset and Y offset as parameters, on this object of type T, returning an object of
   * Type T. */
  def SlateXY(xDelta: Double, yDelta: Double): T = ev.slateXYT(value, xDelta, yDelta)

  /** Translate 2D geometric transformation extension method, taking a [[Pt2]] or a [[Vec2]] as a parameter, on this object of type T, returning an object of
   * Type T. */
  def slate(offset: VecPt2): T = ev.slateXYT(value, offset.x, offset.y)

  /** Translate 2D geometric transformation extension method, taking a 2-dimensional vector as its operand, specified in terms of its angle and magnitude
   * parameters, on this object of type T, returning an object of Type T. */
  def slateAngle(angle: Angle, magnitude: Double): T = ev.slateT(value, angle.toVec2(magnitude))

  /** Translate in 2D geometric transformation extension method by the delta or vector from the first parameter point to the second parameter point, on this
   * type T, returning an object of type T. */
  def slateDelta(ptA: Pt2, ptB: Pt2): T = ev.slateXYT(value, ptB.x - ptA.x, ptA.y -ptB.y)
}

/** Type class for translate 2-dimensional vector transformations. Each transformation method has been given its own Type class and associated extension class.
 * Different sets of transformations can then be combined. */
trait SlateLength[T]
{ /** Translate 2D geometric transformation, taking a [[Pt2]] or [[Vec2]] as a parameter, on an object of type T, returning an object of type T. */
  def slateT(obj: T, delta: VecPtLength2): T
}

/** Companion object for the Slate type class. Contains implicit instances for collections and other container classes. */
object SlateLength
{ /** Implicit [[SlateLength]] instance / evidence for [[RArr]]. */
  implicit def rArrEv[A](implicit ev: SlateLength[A]): SlateLength[RArr[A]] = (obj, delta) => obj.smap(ev.slateT(_, delta))

  /** Implicit [[SlateLength]] instance / evidence for [[Functor]]. This provides instances for List, Option etc. */
  implicit def functorImplicit[A, F[_]](implicit evF: Functor[F], evA: SlateLength[A]): SlateLength[F[A]] = (obj, delta) => evF.mapT(obj, evA.slateT(_, delta))

  /** Implicit [[SlateLength]] instance / evidence for [[Array]]. */
  implicit def arrayImplicit[A](implicit ct: ClassTag[A], ev: SlateLength[A]): SlateLength[Array[A]] = (obj, delta) => obj.map(ev.slateT(_, delta))
}

/** Type class for translate with X and Y parameters, 2 [[Length]] dimensional point and vector transformations. */
trait SlateLengthXY[T]
{ /** Translate 2 [[Length]] dimension, geometric transformation, taking the xOffset and yOffset [[Length]]s as parameters, on an object of type T, returning an
   * object of type T. For many types * the implementation of this method will delegate to the object itself. */
  def slateXYT(obj: T, xDelta: Length, yDelta: Length): T
}

/** Companion object for the Slate type class. Contains implicit instances for collections and other container classes. */
object SlateLengthXY
{ /** Implicit [[SlateLengthXY]] instance / evidence for [[RArr]]. */
  implicit def rArrImplicit[A](implicit ev: SlateLengthXY[A]): SlateLengthXY[RArr[A]] =(obj, dx, dy) => obj.smap(ev.slateXYT(_, dx, dy))

  /** Implicit [[SlateLengthXY]] instance / evidence for [[Functor]]. This provides instances for List, Option etc. */
  implicit def functorImplicit[A, F[_]](implicit evF: Functor[F], evA: SlateLengthXY[A]): SlateLengthXY[F[A]] =
    (obj, dx, dy) => evF.mapT(obj, evA.slateXYT(_, dx, dy))

  /** Implicit [[SlateLengthXY]] instance / evidence for [[Array]]. */
  implicit def arrayImplicit[A](implicit ct: ClassTag[A], ev: SlateLengthXY[A]): SlateLengthXY[Array[A]] = (obj, dx, dy) => obj.map(ev.slateXYT(_, dx, dy))
}