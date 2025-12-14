/* Copyright 2018-25 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package geom
import reflect.ClassTag

/** Type class for reflect 2-dimensional vector transformations. Each transformation method has been given its own Type class and associated extension class.
 * Different sets of transformations can then be combined. */
trait Mirror[T]
{ /** Reflects object of type T. */
  def mirrorT(obj: T, linelike: LineLike): T
}

/** Companion object for the Reflect type class. Contains instances. 2-dimensional vector transformations type class. */
object Mirror
{
  given transSimerEv[T <: SimilarPreserve]: Mirror[T] = (obj, lineLike) => obj.mirror(lineLike).asInstanceOf[T]

  /** Implicit [[RotateM3T]] type class instances / evidence for [[Arr]]. */
  given arrEv[A, AA <: Arr[A]](using build: BuilderArrMap[A, AA], ev: Mirror[A]): Mirror[AA] = (obj, offset) => obj.map(ev.mirrorT(_, offset))

  given functorEv[A, F[_]](using evF: Functor[F], evA: Mirror[A]): Mirror[F[A]] = (obj, lineLike) => evF.mapT(obj, evA.mirrorT(_, lineLike))

  given arrayEv[A](using ct: ClassTag[A], ev: Mirror[A]): Mirror[Array[A]] = (obj, lineLike) => obj.map(ev.mirrorT(_, lineLike))
}

/** Extension methods for the Reflect type class. */
extension[T](value: T)(using ev: Mirror[T])
{ /** Performs 2d vector reflect transformation on objects of type T. */
  def mirror(lineLike: LineLike): T = ev.mirrorT(value, lineLike)
}