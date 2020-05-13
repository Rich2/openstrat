/* Copyright 2018-20 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat
package geom
import reflect.ClassTag

/** Type class for tranScale 2 dimensional vector transformations. Each transformation method has been given its own Type class and associated
 * extension class. Different sets of transformations can then be combined. */
trait Scale[T]
{ def scale(obj: T, operand: Double): T
}

object Scale
{
  implicit def transAlignerImplicit[T <: TransAligner]: Scale[T] = (obj, operand) => obj.scaleOld(operand).asInstanceOf[T]

  implicit def arrImplicit[A, AA <: ArrBase[A]](implicit build: ArrBuild[A, AA], ev: Scale[A]): Scale[AA] =
    (obj, offset) => obj.map(ev.scale(_, offset))

  implicit def functorImplicit[A, F[_]](implicit evF: Functor[F], evA: Scale[A]): Scale[F[A]] = (obj, operand) => evF.map(obj, evA.scale(_, operand))

  implicit def arrayImplicit[A](implicit ct: ClassTag[A], ev: Scale[A]): Scale[Array[A]] = (obj, operand) => obj.map(ev.scale(_, operand))
}

class ScaleExtension[T](value: T, ev: Scale[T])
{
  /** Scale. */
  def scale(operand: Double): T = ev.scale(value, operand)
}