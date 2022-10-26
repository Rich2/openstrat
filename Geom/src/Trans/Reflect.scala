/* Copyright 2018-21 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package geom
import reflect.ClassTag

/** Type class for reflect 2 dimensional vector transformations. Each transformation method has been given its own Type class and associated
 * extension class. Different sets of transformations can then be combined. */
trait Reflect[T]
{ /** Reflects object of type T. */
  def reflectT(obj: T, linelike: LineLike): T
}

/** Companion object for the Reflect type class. Contains instances. 2 dimensional vector transformations type class. */
object Reflect
{
  implicit def transSimerImplicit[T <: SimilarPreserve]: Reflect[T] = (obj, lineLike) => obj.reflect(lineLike).asInstanceOf[T]

  implicit def arrImplicit[A, AA <: Arr[A]](implicit build: ArrMapBuilder[A, AA], ev: Reflect[A]): Reflect[AA] =
    (obj, offset) => obj.map(ev.reflectT(_, offset))

  implicit def functorImplicit[A, F[_]](implicit evF: Functor[F], evA: Reflect[A]): Reflect[F[A]] =
    (obj, lineLike) => evF.mapT(obj, evA.reflectT(_, lineLike))

  implicit def arrayImplicit[A](implicit ct: ClassTag[A], ev: Reflect[A]): Reflect[Array[A]] = (obj, lineLike) => obj.map(ev.reflectT(_, lineLike))
}

/** Extension methods for the Reflect type class. */
class ReflectExtensions[T](val value: T, ev: Reflect[T])
{ /** Performs 2d vector reflect transformation on objects of type T. */
  def reflect(lineLike: LineLike): T = ev.reflectT(value, lineLike)
}