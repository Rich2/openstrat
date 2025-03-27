/* Copyright 2018-25 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package geom
import reflect.ClassTag, annotation.unchecked.uncheckedVariance

/** Type class for translate 2-dimensional vector transformations. Each transformation method has been given its own Type class and associated extension class.
 * Different sets of transformations can then be combined. */
trait Slate[A]
{ /** Translate 2D geometric transformation, taking the xOffset and yOffset as parameters, on an object of type T, returning an object of type T. For many types
   * the implementation of this method will delegate to the object itself. */
  def slateXY(obj: A, xOperand: Double, yOperand: Double): A

  /** Translate 2D geometric transformation, taking a [[Pt2]] or [[Vec2]] as a parameter, on an object of type T, returning an object of type T. */
  def slate(obj: A, operand: VecPt2): A = slateXY(obj, operand.x, operand.y)

  /** Translate 2D geometric transformation along the X axis, on an object of type T, returning an object of type T. */
  def SlateX(obj: A, xOperand: Double): A = slateXY(obj, xOperand, 0)

  /** Translate 2D geometric transformation along the Y axis, on an object of type T, returning an object of type T. */
  def SlateY(obj: A, yOperand: Double): A = slateXY(obj, 0, yOperand)
}

/** Companion object for the Slate type class. Contains implicit instances for collections and other container classes. */
object Slate
{ implicit def transSimerImplicit[T <: SimilarPreserve]: Slate[T] = (obj: T, dx: Double, dy: Double) => obj.slateXY(dx, dy).asInstanceOf[T]

  /** Implicit [[Slate]] instance / evidence for [[RArr]]. */
  implicit def rArrImplicit[A](implicit ev: Slate[A]): Slate[RArr[A]] = (obj, dx, dy) => obj.smap(ev.slateXY(_, dx, dy))

  /** Implicit [[Slate]] instance / evidence for [[Functor]]. This provides instances for List, Option etc. */
  implicit def functorImplicit[A, F[_]](implicit evF: Functor[F], evA: Slate[A]): Slate[F[A]] = (obj, dx, dy) => evF.mapT(obj, evA.slateXY(_, dx, dy))

  /** Implicit [[Slate]] instance / evidence for [[Array]]. */
  implicit def arrayImplicit[A](implicit ct: ClassTag[A], ev: Slate[A]): Slate[Array[A]] = (obj, dx, dy) => obj.map(ev.slateXY(_, dx, dy))
}

/** Extension class for instances of the Slate type class. */
class SlateExtensions[A](value: A, ev: Slate[A])
{ /** Translate 2D geometric transformation extension method, along the X axis, on this object of type T, returning an object of Type T. */
  def slateX(xOffset: Double): A = ev.slateXY(value, xOffset, 0)

  /** Translate 2D geometric transformation extension method, along the Y axis, on this object of type T, returning an object of Type T. */
  def slateY(yOffset: Double): A = ev.slateXY(value, 0, yOffset)

  /** Translate 2D geometric transformation extension method, taking the X offset and Y offset as parameters, on this object of type T, returning an object of
   * Type T. */
  def SlateXY(xDelta: Double, yDelta: Double): A = ev.slateXY(value, xDelta, yDelta)

  /** Translate 2D geometric transformation extension method, taking a [[Pt2]] or a [[Vec2]] as a parameter, on this object of type T, returning an object of
   * Type T. */
  def slate(offset: VecPt2): A = ev.slateXY(value, offset.x, offset.y)

  /** Translate 2D geometric transformation extension method, taking a 2-dimensional vector as its operand, specified in terms of its angle and magnitude
   * parameters, on this object of type T, returning an object of Type T. */
  def slateAngle(angle: Angle, magnitude: Double): A = ev.slate(value, angle.toVec2(magnitude))

  /** Translate in 2D geometric transformation extension method by the delta or vector from the first parameter point to the second parameter point, on this
   * type T, returning an object of type T. */
  def slateDelta(ptA: Pt2, ptB: Pt2): A = ev.slateXY(value, ptB.x - ptA.x, ptA.y - ptB.y)
}

/** Type class for translate 2-dimensional vector transformations. Each transformation method has been given its own Type class and associated extension class.
 * Different sets of transformations can then be combined. */
trait SlateLen2[A]
{ /** Translate 2D geometric transformation, taking a [[Pt2]] or [[Vec2]] as a parameter, on an object of type T, returning an object of type T. */
  def slateT(obj: A, delta: VecPtLen2): A
}

/** Companion object for the [[SlateLen2]] type class. Contains implicit instances for collections and other container classes. */
object SlateLen2
{ /** Implicit [[SlateLen2]] type class instances / evidence for [[RArr]]. */
  implicit def rArrEv[A](implicit ev: SlateLen2[A]): SlateLen2[RArr[A]] = (obj, delta) => obj.smap(ev.slateT(_, delta))

  /** Implicit [[SlateLen2]] type class instances / evidence for [[Functor]]. This provides instances for [[List]], [[Option]] etc. */
  implicit def functorEv[A, F[_]](implicit evF: Functor[F], evA: SlateLen2[A]): SlateLen2[F[A]] = (obj, op) => evF.mapT(obj, evA.slateT(_, op))

  /** Implicit [[SlateLen2]] type class instances / evidence for [[Array]]. */
  implicit def arrayEv[A](implicit ct: ClassTag[A], ev: SlateLen2[A]): SlateLen2[Array[A]] = (obj, op) => obj.map(ev.slateT(_, op))
}

/** Type class for translate with X and Y parameters, 2 [[Length]] dimensional point and vector transformations. */
trait SlateLenXY[A]
{ /** Translate 2 [[Length]] dimension, geometric transformation, taking the xOffset and yOffset [[Length]]s as parameters, on an object of type T, returning an
   * object of type T. For many types * the implementation of this method will delegate to the object itself. */
  def slateXYT(obj: A, xDelta: Length, yDelta: Length): A
}

/** Companion object for the Slate type class. Contains implicit instances for collections and other container classes. */
object SlateLenXY
{ /** Implicit [[SlateLenXY]] type class instances / evidence for [[RArr]]. */
  implicit def rArrEv[A](implicit ev: SlateLenXY[A]): SlateLenXY[RArr[A]] =(obj, dx, dy) => obj.smap(ev.slateXYT(_, dx, dy))

  /** Implicit [[SlateLenXY]] type class instances / evidence for [[Functor]]. This provides instances for List, Option etc. */
  implicit def functorEv[A, F[_]](implicit evF: Functor[F], evA: SlateLenXY[A]): SlateLenXY[F[A]] =
    (obj, dx, dy) => evF.mapT(obj, evA.slateXYT(_, dx, dy))

  /** Implicit [[SlateLenXY]] type class instances / evidence for [[Array]]. */
  implicit def arrayEv[A](implicit ct: ClassTag[A], ev: SlateLenXY[A]): SlateLenXY[Array[A]] = (obj, dx, dy) => obj.map(ev.slateXYT(_, dx, dy))
}

/** Type class to translate from [[GeomLen2Elem]]s to [[Geom2]]s. */
trait MapGeom2[A, B]
{ /** Maps from [[GeomLen2Elem]]s to [[Geom2]]s */
  def mapGeom2T(obj: A, operand: Length): B
}

/** Companion object for the Slate type class. Contains implicit instances for collections and other container classes. */
object MapGeom2
{ /** Implicit [[MapGeom2]] type class instance / evidence for [[RArr]]. */
  implicit def rArrEv[A, B](implicit ev: MapGeom2[A, B], ct: ClassTag[B]): MapGeom2[RArr[A], RArr[B]] = (obj, len) => obj.map(ev.mapGeom2T(_, len))

  /** Implicit [[MapGeom2]] type class instance / evidence for [[Functor]]. This provides instances for List, Option etc. */
  implicit def functorEv[A, B, F[_]](implicit evF: Functor[F], evA: MapGeom2[A, B]): MapGeom2[F[A], F[B]] =
    (obj, len) => evF.mapT(obj, evA.mapGeom2T(_, len))

  /** Implicit [[MapGeom2]] type class instance / evidence for [[Array]]. */
  implicit def arrayEv[A, B](implicit ct: ClassTag[B], evAL: MapGeom2[A, B]): MapGeom2[Array[A], Array[B]] = (obj, len) => obj.map(evAL.mapGeom2T(_, len))
}