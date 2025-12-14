/* Copyright 2018-25 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package geom
import reflect.ClassTag

/** Type class for scale 2-dimensional vector transformations. Each transformation method has been given its own Type class and associated extension class.
 * Different sets of transformations can then be combined. */
trait Scale[T]
{ /** Scales object of type T. */
  def scaleT(obj: T, operand: Double): T
}

/** Companion object for the Scale type class. Contains instances. 2-dimensional vector transformations type class. */
object Scale
{given transSimerEv[T <: SimilarPreserve]: Scale[T] = (obj, operand) => obj.scale(operand).asInstanceOf[T]

  /** Implicit [[Scale]] type class instances / evidence for [[Arr]]. */
  given arrEv[A, AA <: Arr[A]](using build: BuilderArrMap[A, AA], ev: Scale[A]): Scale[AA] = (obj, offset) => obj.map(ev.scaleT(_, offset))

  /** Implicit [[Scale]] type class instances / evidence for [[Functor]]. Provides instances for [[List]], [[Option]] etc. */
  given functorEv[A, F[_]](using evF: Functor[F], evA: Scale[A]): Scale[F[A]] = (obj, operand) => evF.mapT(obj, evA.scaleT(_, operand))

  /** Implicit [[Scale]] type class instances / evidence for [[Array]]. */
  given arrayEv[A](using ct: ClassTag[A], ev: Scale[A]): Scale[Array[A]] = (obj, operand) => obj.map(ev.scaleT(_, operand))
}

/** Extension methods for the [[Scale]] type class. */
extension[T](value: T)(using ev: Scale[T])
{ /** Performs 2d vector scale transformation on objects of type T. */
  def scale(operand: Double): T = ev.scaleT(value, operand)
  def scale2: T = ev.scaleT(value, 2)
  def scale10: T = ev.scaleT(value, 10)
}