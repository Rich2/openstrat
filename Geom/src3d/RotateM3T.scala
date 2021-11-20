/* Copyright 2018-21 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package geom
import reflect.ClassTag

/** Type class for 3D [[Length]] geometric rotation transformations of objects of type T. */
trait RotateM3T[T]
{
  def rotateYT(obj: T, angle: AngleVec): T
}

/** Companion object for the Rotate[T] type class, contains implicit instances for collections and other container classes. */
object RotateM3T
{
  implicit def arrImplicit[A, AA <: SeqImut[A]](implicit build: ArrBuilder[A, AA], ev: RotateM3T[A]): Rotate[AA] =
    (obj, angle) => obj.map(ev.rotateYT(_, angle))

  implicit def functorImplicit[A, F[_]](implicit evF: Functor[F], evA: RotateM3T[A]): RotateM3T[F[A]] =
    (obj, radians) => evF.mapT(obj, evA.rotateYT(_, radians))

  implicit def arrayImplicit[A](implicit ct: ClassTag[A], ev: RotateM3T[A]): RotateM3T[Array[A]] = (obj, radians) => obj.map(ev.rotateYT(_, radians))
}

/** Extension class for instances of the RotateM3 type class. */
class RotateM3Extensions[T](value: T, ev: RotateM3T[T])
{
  /** Rotate around the Y axis, 3D geometric transformation of the object by the [[AngleVec]] parameter. */
  def rotateY(angle: AngleVec): T = ev.rotateYT(value, angle)
}