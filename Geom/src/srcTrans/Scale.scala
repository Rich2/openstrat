/* Copyright 2018-25 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package geom
import reflect.ClassTag

/** Type class for scale geometric transformations. Can be 2D or 3D, scalars or [[Length]] units, pure geometric or graphical types. Allows the scale extension
 * method on objects that can summon the type class instance. */
trait ScaleLike[A, B]
{ /** Scales object of type T. */
  def scaleT(obj: A, operand: Double): B
}

/** Companion object for the [[ScaleLike]] transformations type class Contains instances for [[Functor]]s [[Arr]]s and [[Array]]s. */
object ScaleLike
{ /** Implicit [[ScaleLike]] type class instances / evidence for [[Arr]]. */
  given arrEv[A, B, ArrA <: Arr[A], ArrB <: Arr[B]](using evAB: ScaleLike[A, B], build: BuilderArrMap[B, ArrB]): ScaleLike[ArrA, ArrB] =
    (obj, offset) => obj.map(evAB.scaleT(_, offset))

  /** Implicit [[ScaleLike]] type class instances / evidence for [[Functor]]. Provides instances for [[List]], [[Option]] etc. */
  given functorEv[A, B, F[_]](using evF: Functor[F], evAB: ScaleLike[A, B]): ScaleLike[F[A], F[B]] = (obj, operand) => evF.mapT(obj, evAB.scaleT(_, operand))

  /** Implicit [[ScaleLike]] type class instances / evidence for [[Array]]. */
  given arrayEv[A, B](using evAB: ScaleLike[A, B], ctB: ClassTag[B]): ScaleLike[Array[A], Array[B]] = (obj, operand) => obj.map(evAB.scaleT(_, operand))

  /** Subtype type class instances / evidence for [[ScaleLike]]. */
  given subTypesEv[A, B >: A](using evB: Scale[B]): ScaleLike[A, B] = (obj, op) => evB.scaleT(obj, op)
}

/** Special cases of [[ScaleLike]] Type class where the type of the object is preserved for scale geometric transformations. Can be 2D or 3D, scalars or
 * [[Length]] units, pure geometric or graphical types. Allows the scale extension method on objects that can summon the type class instance. */
trait Scale[A] extends ScaleLike[A, A]

/** Extension methods for the [[Scale]] type class. */
extension[A, B](value: A)(using ev: ScaleLike[A, B])
{ /** Performs scale transformation on objects of type T. */
  def scale(operand: Double): B = ev.scaleT(value, operand)
}