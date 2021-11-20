/* Copyright 2018-21 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package geom
import reflect.ClassTag

/** Type class for 3D [[Length]] geometric rotation transformations of objects of type T. */
trait RotateM3[T]
{
  def rotateYT(obj: T, angle: AngleVec): T
}

/** Companion object for the Rotate[T] type class, contains implicit instances for collections and other container classes. */
object RotateM3T
{
  implicit def arrImplicit[A, AA <: SeqImut[A]](implicit build: ArrBuilder[A, AA], ev: RotateM3[A]): Rotate[AA] =
    (obj, angle) => obj.map(ev.rotateYT(_, angle))

  implicit def functorImplicit[A, F[_]](implicit evF: Functor[F], evA: RotateM3[A]): RotateM3[F[A]] =
    (obj, radians) => evF.mapT(obj, evA.rotateYT(_, radians))

  implicit def arrayImplicit[A](implicit ct: ClassTag[A], ev: RotateM3[A]): RotateM3[Array[A]] = (obj, radians) => obj.map(ev.rotateYT(_, radians))
}